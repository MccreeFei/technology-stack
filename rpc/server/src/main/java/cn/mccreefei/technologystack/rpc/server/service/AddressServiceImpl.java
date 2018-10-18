package cn.mccreefei.technologystack.rpc.server.service;

import cn.mccreefei.technologystack.rpc.support.model.Address;
import cn.mccreefei.technologystack.rpc.support.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author MccreeFei
 * @create 2018-10-17 下午4:36
 */
@Service
public class AddressServiceImpl implements AddressService {
    @Override
    public Address getAddress() {
        return Address.builder().province("zhejiang").city("hangzhou").build();
    }
}
