# Rpc
- 核心模块：`rpc-client`, `rpc-server`,`rpc-support`,`rpc-api`
- 整个项目基于SpringBoot，各模块解耦
- 底层传输使用Netty，传输可靠性高
- 服务注册与发现使用Zookeeper
- 代理支持JDK动态代理与Cglib代理，使用注解方式发现服务与代理，使得配置更加灵活简洁
- 序列化使用Json，传输可视化更好，支持扩展


## 整体流程
![rpc.png](https://i.loli.net/2018/10/19/5bc992a9d5a16.png)

-----

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
### Zookeeper配置
```java
public interface ConfigUtil {
    /**
     * zookeeper session超时事件 ms
     */
    int SESSION_TIME_OUT = 10000;
    /**
     * zookeeper中rpc根节点路径
     */
    String ROOT_PATH = "/rpcRoot";
    /**
     * zookeeper地址
     */
    String ADDRESS = "127.0.0.1:2181";
}
```
-----

## rpc-server
### 服务注册
#### 定义RpcService注解
@RpcService注解使用在服务端提供的接口实现类上，属性value表示实现的是哪个Api接口。另外继承@Component注解以便Spring扫描注入。
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface RpcService {
    Class<?> value();
}
```
#### 注册流程
1. 连接Zookeeper服务器
2. 创建rpc根节点
3. 对应每一个服务实现类，创建服务接口节点并在该节点下创建该服务的地址节点
比如现在注册一个`cn.mccreefei.xxx.XService`的服务接口，那么在Zookeeper的注册路径为`/rpcRoot/cn.mccreefei.xxx.XService/serverAddress`.
```java
@Component
@Slf4j
public class ServiceRegistry implements ApplicationContextAware{
    private ZooKeeper zookeeper;
    private String rootPath = ConfigUtil.ROOT_PATH;
    @Value("${netty.host}")
    private String serverHost;
    @Value("${netty.port}")
    private int serverPort;
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    /**
     * 连接Zookeeper服务器
     * @throws IOException
     */
    private void connect() throws IOException {
        String address = ConfigUtil.ADDRESS;
        Integer sessionTimeOut = ConfigUtil.SESSION_TIME_OUT;
        zookeeper = new ZooKeeper(address, sessionTimeOut, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if (watchedEvent.getState().equals(Event.KeeperState.SyncConnected)){
                    countDownLatch.countDown();
                }
            }
        });
    }

    /**
     * 创建根节点
     */
    private void createRootPath() {
        try {
            Stat stat = zookeeper.exists(rootPath, false);
            if (stat == null){
                zookeeper.create(rootPath, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException | InterruptedException e) {
            log.error("", e);
        }
    }

    /**
     * 创建服务接口节点
     * @param serviceName 服务接口名
     */
    private void createServiceNode(String serviceName){
        try {
            String servicePath = rootPath + "/" + serviceName;
            Stat stat = zookeeper.exists(servicePath, false);
            if (stat == null){
                zookeeper.create(servicePath, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException | InterruptedException e) {
            log.error("", e);
        }
    }

    /**
     * 创建服务接口地址节点
     * @param serviceName 服务接口名
     */
    private void createServiceAddressNode(String serviceName){
        createServiceNode(serviceName);
        String serverAddress = serverHost + ":" + serverPort;
        String serviceAddressPath = rootPath + "/" + serviceName + "/" + serverAddress;
        try {
            zookeeper.create(serviceAddressPath, serverAddress.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        } catch (KeeperException | InterruptedException e) {
            log.error("", e);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        //连接zookeeper
        try {
            connect();
            countDownLatch.await();
        } catch (IOException | InterruptedException e) {
            log.error("", e);
        }
        //创建根节点路径
        createRootPath();
        Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(RpcService.class);
        if (!CollectionUtils.isEmpty(beansWithAnnotation)){
            beansWithAnnotation.values().forEach(serviceBean -> {
                String serviceName = serviceBean.getClass().getAnnotation(RpcService.class).value().getName();
                log.info("register @RpcService : " + serviceName);
                createServiceAddressNode(serviceName);
            });
        }
    }
}
```
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
```java
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

-----

## rpc-client
### 发现Api接口创建代理类注入
利用反射发现api包下所有含有RpcProxy注解的Service，根据注解配置的动态代理类型，实现该动态代理类型的实现并且注入到Spring容器。
```java
@Configuration
@Slf4j
public class RpcConfig implements ApplicationContextAware, InitializingBean {
    private ApplicationContext context;
    @Resource
    private RpcProxyFactory proxyFactory;
    @Override
    public void afterPropertiesSet() throws Exception {
        Reflections reflections = new Reflections("cn.mccreefei.technologystack.rpc.api");
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(RpcProxy.class);
        if (!CollectionUtils.isEmpty(typesAnnotatedWith)){
            DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getAutowireCapableBeanFactory();
            typesAnnotatedWith.forEach(cls -> {
                RpcProxy annotation = cls.getAnnotation(RpcProxy.class);
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
### 服务发现
连接Zookeeper服务器，基于反射发现api包下所有具有@RpcProxy的接口，在Zookepper中查找服务所在的地址信息，维护Service -> Address的映射关系在AddressMap当中。
```java
@Slf4j
public class ServiceRecovery {
    private Map<String, String> serviceAddressMap;
    private ZooKeeper zooKeeper;
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private final String rootPath = ConfigUtil.ROOT_PATH;

    public ServiceRecovery(Map<String, String> serviceAddressMap){
        this.serviceAddressMap = serviceAddressMap;
    }
    /**
     * 连接Zookeeper
     * @throws IOException
     * @throws InterruptedException
     */
    private void connect() throws IOException, InterruptedException {
        String zookeeperAddress = ConfigUtil.ADDRESS;
        zooKeeper = new ZooKeeper(zookeeperAddress, ConfigUtil.SESSION_TIME_OUT, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if (watchedEvent.getState().equals(Event.KeeperState.SyncConnected)){
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();
    }

    /**
     * 发现服务对应的地址
     * @throws IOException
     * @throws InterruptedException
     */
    public void recoverService() throws IOException, InterruptedException {
        connect();
        Reflections reflections = new Reflections("cn.mccreefei.technologystack.rpc.api");
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(RpcProxy.class);
        Set<String> services = typesAnnotatedWith.stream().map(cls -> cls.getName()).collect(Collectors.toSet());
        services.forEach(serviceName -> {
            try {
                String servicePath = rootPath + "/" + serviceName;
                if (zooKeeper.exists(servicePath, false) != null){
                    List<String> addressChildren = zooKeeper.getChildren(servicePath, false);
                    if (!StringUtils.isEmpty(addressChildren)){
                        //地址多于一个取第一个，可以扩展做负载均衡
                        byte[] bytes = zooKeeper.getData(servicePath + "/" + addressChildren.get(0), false, null);
                        String address = new String(bytes);
                        serviceAddressMap.put(serviceName, address);
                    }
                }
            } catch (KeeperException | InterruptedException e) {
                log.error("", e);
            }
        });
    }
}
```

### 创建Netty连接
#### ChannelHold
ChannelHold为Channel与对应EventLoopGroup的封装类，封装便于在Bean销毁时能够有效释放连接资源。
```java
@Data
public class ChannelHold {
    private Channel channel;
    private EventLoopGroup eventLoopGroup;
    public ChannelHold(Channel channel, EventLoopGroup eventLoopGroup){
        this.channel = channel;
        this.eventLoopGroup = eventLoopGroup;
    }
}
```
#### 创建连接
现对于服务发现的每一个服务地址，都创建一个Netty连接，并维护Address -> ChannelHold的映射。之所以这么设计，是为了同一个服务地址提供的服务能够使用同一个频道进行通讯，减少连接数提升效率。
```java
     /**
     * 创建每一个服务地址的Netty连接
     */
    private void createNettyConnection(){
        try {
            serviceRecovery.recoverService();
        } catch (IOException | InterruptedException e) {
            log.error("service recover fail!", e);
            return;
        }
        Set<String> addressSet = serviceAddressMap.values().stream().distinct().collect(Collectors.toSet());
        if (StringUtils.isEmpty(addressSet)) {
            return;
        }
        for (String address : addressSet){
            String host = null;
            Integer port = null;
            try {
                String[] split = address.split(":");
                host = split[0];
                port = Integer.valueOf(split[1]);
            }catch (IndexOutOfBoundsException e){
                log.error("address [{}] invalid!", address);
                continue;
            }
            Bootstrap bootstrap = new Bootstrap();
            EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
            bootstrap.channel(NioSocketChannel.class)
                    .group(eventLoopGroup)
                    .remoteAddress(host, port)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new RpcClientEncoder(serialization));
                            pipeline.addLast(new RpcClientDecoder(serialization));
                            pipeline.addLast(rpcResponseHandler);
                        }
                    });
            Channel channel = bootstrap.connect().channel();
            ChannelHold channelHold = new ChannelHold(channel, eventLoopGroup);
            addressChannelMap.put(address, channelHold);
        }
    }
```
#### 释放资源
实现Spring提供的DisposableBean接口，在Bean销毁之前，释放Netty连接。
```java
  @Override
    public void destroy() throws Exception {
        if (addressChannelMap != null){
            Collection<ChannelHold> channelHolds = addressChannelMap.values();
            if (!CollectionUtils.isEmpty(channelHolds)){
                channelHolds.forEach(channelHold -> {
                    channelHold.getChannel().closeFuture();
                    channelHold.getEventLoopGroup().shutdownGracefully();
                });
            }
        }
    }
```
### 动态代理
动态代理技术使得客户端进行Rpc服务调用时感觉与往常的本地调用一样。Spring Aop也使用了这个技术。动态代理有两种形式：Jdk动态代理和Cglib代理。区别就是Jdk动态代理由Jdk提供但只能基于有接口的类进行代理，没有接口的类是无法进行代理的。而Cglib是一个基于ASM的字节生成库，允许运行时对字节码进行修改和生成，Cglib本质是通过修改字节码使得代理类继承目标类进行实现。
Demo同样实现了两种方式的代理，isTargetClass=true代表Cglib代理反之Jdk代理，代理工厂实现：
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

### 测试
现创建两个Server模块`rpc-server-demo1`和`rpc-server-demo2`，都添加对`rpc-api`,`rpc-server`的依赖,配置不同的服务端口，分别实现`HelloService`与`AddressService`。在`rpc-client`下创建`RpcClientTest`测试类.
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
        Address address = addressService.getAddress("zhejiang", "hangzhou");
        System.out.println(address);
    }
}
```
成功调用，运行结果：

> hello, MccreeFei!

> Address(province=zhejiang, city=hangzhou)



