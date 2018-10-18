package cn.mccreefei.technologystack.rpc.support.service;

import cn.mccreefei.technologystack.rpc.support.model.Address;

import java.util.Date;

/**
 * @author MccreeFei
 * @create 2018-10-17 下午4:35
 */
@RpcService
public interface AddressService {
    public Address getAddress();
}
