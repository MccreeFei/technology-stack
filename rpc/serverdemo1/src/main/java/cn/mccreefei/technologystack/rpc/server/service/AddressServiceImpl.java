package cn.mccreefei.technologystack.rpc.server.service;

import cn.mccreefei.technologystack.rpc.api.RpcService;
import cn.mccreefei.technologystack.rpc.api.entity.Address;
import cn.mccreefei.technologystack.rpc.api.service.AddressService;

/**
 * @author MccreeFei
 * @create 2018-10-25 上午9:27
 */
@RpcService(AddressService.class)
public class AddressServiceImpl implements AddressService{
    @Override
    public Address getAddress(String province, String city) {
        return new Address(city, province);
    }
}
