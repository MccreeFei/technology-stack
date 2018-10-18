package cn.mccreefei.technologystack.rpc.support.transport;

import lombok.Builder;
import lombok.Data;

/**
 * @author MccreeFei
 * @create 2018-10-12 下午4:06
 */
@Data
public class RpcResponse {
    private String requestId;
    private Exception exception;
    private Object result;


    public RpcResponse(){}

    @Builder
    public RpcResponse(String requestId, Exception exception, Object result) {
        this.requestId = requestId;
        this.exception = exception;
        this.result = result;
    }


}
