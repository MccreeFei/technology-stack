package cn.mccreefei.technologystack.rpc.api.service;

import cn.mccreefei.technologystack.rpc.api.RpcProxy;
import cn.mccreefei.technologystack.rpc.api.entity.Address;

/**
 * @author MccreeFei
 * @create 2018-10-25 上午9:23
 */
@RpcProxy(proxyTargetClass = true)
public interface AddressService {
    public Address getAddress(String province, String city);
}
