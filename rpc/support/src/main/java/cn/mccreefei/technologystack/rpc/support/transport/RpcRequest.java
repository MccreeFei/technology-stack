package cn.mccreefei.technologystack.rpc.support.transport;

import lombok.Builder;
import lombok.Data;

/**
 * @author MccreeFei
 * @create 2018-10-12 下午3:36
 */
@Data
public class RpcRequest {
    private String requestId;
    private String className;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;

    public RpcRequest(){}

    @Builder
    public RpcRequest(String requestId, String className, String methodName,
                      Class<?>[] parameterTypes, Object[] parameters) {
        this.requestId = requestId;
        this.className = className;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.parameters = parameters;
    }
}
