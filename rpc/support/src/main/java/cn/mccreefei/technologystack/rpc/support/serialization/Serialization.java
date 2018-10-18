package cn.mccreefei.technologystack.rpc.support.serialization;

/**
 * @author MccreeFei
 * @create 2018-10-12 下午3:04
 */
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
