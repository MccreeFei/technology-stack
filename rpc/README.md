
# Rpc
整个项目基于SpringBoot，底层传输使用Netty提供支持，序列化使用Json，支持扩展。整个Rpc项目包含三个模块`rpc-client`，`rpc-sever`，`rpc-support`。另外整个项目使用了lombok减少代码量，请先下载lombok插件。

## 整体流程
![rpc.png](https://i.loli.net/2018/10/19/5bc992a9d5a16.png)

## rpc-support
`rpc-support`模块为`rpc-client`和`rpc-server`提供支持。
### 传输协议
所有Client的请求封装成RpcRequest对象，所有Server的响应封装成RpcResponse对象，经过序列化发送给对方。Netty中传输协议为1个Int（表示传输的对象字节数量）+对象字节数。
```java
@Data
public class RpcRequest {
    private String requestId;  //请求id
    private String className;  //调用类名
    private String methodName; //调用方法名
    private Class<?>[] parameterTypes; //方法参数类型
    private Object[] parameters;   //方法参数
}

@Data
public class RpcResponse {
    private String requestId;  //对应请求id
    private Exception exception; //失败抛出的异常
    private Object result;   //结果
}
```
### 序列化
序列化有一个公共的接口，可以根据自身的需求实现自己的序列化对象，Demo使用Json实现序列化和反序列化。
```java
public interface Serialization {
    /**
     * 序列化
     * @param obj 序列化对象
     * @return 字节数组
     */
    public <T> byte[] serialize(T obj);
    /**
     * 反序列化
     * @param bytes 字节数组
     * @param cls 转化对象类型
     * @return 转化对象
     */
    public <T> T deSerialize(byte[] bytes, Class<T> cls);
}

@Component
public class JsonSerialization implements Serialization {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public <T> byte[] serialize(T obj) {
        try {
            return objectMapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public <T> T deSerialize(byte[] bytes, Class<T> cls) {
        try {
            return objectMapper.readValue(bytes, cls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
```
### 服务发现
Demo目前并没有实现诸如Zookeeper的服务注册发现机制，只是在当前模块下添加service包，表示该包模块下的所有Service已经被Server实现并且允许客户端调用。另外所有Service需添加RpcService的注解。
```java
/**
 * @author MccreeFei
 * @create 2018-10-17 下午3:21
 * RpcService注解 默认使用jdk动态代理 proxyTargetClass=true使用Cglib代理
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcService {
    boolean proxyTargetClass() default false;
}
```

## rpc-server
### Rpc服务端编码解码
根据传输协议，与序列化进行解码
```java
public class RpcServerDecoder extends ByteToMessageDecoder {
    private Serialization serialization;
    public RpcServerDecoder(Serialization serialization){
        this.serialization = serialization;
    }
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() <= 4) {
            return;
        }
        int length = in.readInt();
        in.markReaderIndex();
        if (in.readableBytes() < length){
            in.resetReaderIndex();
        }else {
            byte[] dst = new byte[length];
            in.readBytes(dst);
            RpcRequest rpcRequest = serialization.deSerialize(dst, RpcRequest.class);
            out.add(rpcRequest);
        }
    }
}

@Slf4j
public class RpcServerEncoder extends MessageToByteEncoder<RpcResponse> {
    private Serialization serialization;
    public RpcServerEncoder(Serialization serialization){
        this.serialization = serialization;
    }
    @Override
    protected void encode(ChannelHandlerContext ctx, RpcResponse rpcResponse, ByteBuf out) throws Exception {
        log.info("send response to client : " + rpcResponse);
        byte[] bytes = serialization.serialize(rpcResponse);
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }
}
```
### 反射调用
Server根据发送过来的RpcRequest对象信息，进行反射调用，将结果写入Netty当中。
```
 protected void channelRead0(ChannelHandlerContext ctx, RpcRequest rpcRequest) throws Exception {
        log.info("request from client : " + rpcRequest);
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setRequestId(rpcRequest.getRequestId());
        try {
            String className = rpcRequest.getClassName();
            Object target = context.getBean(Class.forName(className));
            Method targetMethod = target.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
            Object result = targetMethod.invoke(target, rpcRequest.getParameters());
            rpcResponse.setResult(result);
        }catch (Exception e){
            log.error("RpcRequestHandler Error!", e);
            rpcResponse.setException(e);
        }
        ctx.writeAndFlush(rpcResponse);
    }
```

## rpc-client
### 需求池
Netty中的发送与接受都是异步的，所以需要一个需求池来暂存客户端发送的请求，客户端调用服务发送请求后会同步等待，当异步获取到服务端发送的响应时。响应与需求池中的请求通过requestId匹配，通知客户端获取到响应。
```java
@Component
public class RpcRequestPool {
    private final Map<String, Promise<RpcResponse>> requestPool = new ConcurrentHashMap<>();

    public void addRequest(String requestId, EventExecutor executor){
        requestPool.put(requestId, new DefaultPromise<RpcResponse>(executor));
    }
    public RpcResponse getResponse(String requestId) throws Exception {
        //获取远程调用结果 10s超时
        RpcResponse rpcResponse = requestPool.get(requestId).get(10, TimeUnit.SECONDS);
        requestPool.remove(requestId);
        return rpcResponse;
    }
    public void notifyRequest(String requestId, RpcResponse rpcResponse){
        Promise<RpcResponse> promise = requestPool.get(requestId);
        if (promise != null){
            promise.setSuccess(rpcResponse);
        }
    }
}
```
### 动态代理
动态代理技术使得客户端进行Rpc服务调用时感觉与往常的本地调用一样。Spring Aop也使用了这个技术。动态代理有两种形式：Jdk动态代理和Cglib代理。区别就是Jdk动态代理由Jdk提供但只能基于有接口的类进行代理，没有接口的类是无法进行代理的。而Cglib是一个基于ASM的字节生成库，允许运行时对字节码进行修改和生成，Cglib本质是通过修改字节码使得代理类继承目标类进行实现。
Demo同样实现了两种方式的代理，isTargetClass=true代表Cglib代理反之jdk代理，代理工厂实现：
```java
@Component
@Slf4j
public class RpcProxyFactory {
    @Resource
    private RpcInvoker rpcInvoker;

    public <T> T createInstance(Class<T> interfaceClass){
        return createInstance(interfaceClass, false);
    }
    @SuppressWarnings("unchecked")
    public <T> T createInstance(Class<T> cls, boolean isTargetClass){
        if (isTargetClass){
            log.info("use cglib : " + cls.getSimpleName());
            Enhancer enhancer = new Enhancer();
            enhancer.setCallback(rpcInvoker);
            enhancer.setSuperclass(cls);
            return (T) enhancer.create();
        }else {
            log.info("use jdk dynamic proxy : " + cls.getSimpleName());
            return (T) Proxy.newProxyInstance(cls.getClassLoader(),
                    new Class[]{cls}, rpcInvoker);
        }
    }
}
```
Invoker实现时有一点需要注意，鉴于Result对象为Object类型，Json反序列化时不知道怎么反序列化为实际的结果对象类型，所以就会将结果对象的所有属性反序列化为一个Map，使用Json Cast解决：
```java
@Component
public class RpcInvoker implements InvocationHandler, MethodInterceptor {
    @Resource
    private RpcClient rpcClient;
    @Resource
    private RpcRequestPool requestPool;
    @Override
    public Object invoke(Object proxy, Method method, Object[] parameters) throws Throwable {
        return doInvoke(method, parameters);
    }
    @Override
    public Object intercept(Object obj, Method method, Object[] parameters, MethodProxy proxy) throws Throwable {
       return doInvoke(method, parameters);
    }

    private Object doInvoke(Method method, Object[] parameters) throws Throwable {
        String requestId = UUID.randomUUID().toString();
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        Class<?> returnType = method.getReturnType();

        RpcRequest rpcRequest = RpcRequest.builder()
                .requestId(requestId)
                .className(className)
                .methodName(methodName)
                .parameterTypes(parameterTypes)
                .parameters(parameters).build();
        rpcClient.send(rpcRequest);
        RpcResponse response = requestPool.getResponse(requestId);
        Object result = response.getResult();
        if (result == null){
            throw response.getException();
        }
        //json会将对象内部的Object对象反序列化为Map形式，这里需要手动cast result类型
        if (result instanceof Map){
            result = TypeUtils.cast(result, method.getReturnType(), null);
        }
        return result;
    }
}
```
### 发现RpcService注入代理类
利用反射发现service包下所有含有RpcService注解的Service，根据注解配置的动态代理类型，实现该动态代理类型的实现并且注入到Spring容器。
```java
@Configuration
@Slf4j
public class RpcConfig implements ApplicationContextAware, InitializingBean {
    private ApplicationContext context;
    @Resource
    private RpcProxyFactory proxyFactory;
    @Override
    public void afterPropertiesSet() throws Exception {
        Reflections reflections = new Reflections("cn.mccreefei.technologystack.rpc.support.service");
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(RpcService.class);
        if (!CollectionUtils.isEmpty(typesAnnotatedWith)){
            DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getAutowireCapableBeanFactory();
            typesAnnotatedWith.forEach(cls -> {
                RpcService annotation = cls.getAnnotation(RpcService.class);
                if (annotation.proxyTargetClass()){
                    beanFactory.registerSingleton(cls.getName(), proxyFactory.createInstance(cls, true));
                }else {
                    beanFactory.registerSingleton(cls.getName(), proxyFactory.createInstance(cls, false));
                }
            });
        }
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
```
### 测试
Server端启动之后，调用如下测试方法：
```java
@SpringBootTest(classes = ClientApplication.class)
@RunWith(SpringRunner.class)
public class RpcClientTest {
    @Resource
    private ApplicationContext context;
    @Test
    public void test(){
        HelloService helloService = context.getBean(HelloService.class);
        String content = helloService.sayHello("MccreeFei");
        System.out.println(content);
        AddressService addressService = context.getBean(AddressService.class);
        Address address = addressService.getAddress();
        System.out.println(address);
    }
}
```
运行结果：

> hello, MccreeFei!
> Address(province=zhejiang, city=hangzhou)

