package cn.mccreefei.technologystack.rpc.support.config;

/**
 * @author MccreeFei
 * @create 2018-10-24 下午1:52
 */
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
