package cn.mccreefei.technologystack.rpc.api.entity;

import lombok.Data;

/**
 * @author MccreeFei
 * @create 2018-10-25 上午9:22
 */
@Data
public class Address {
    private String city;
    private String province;

    public Address(String city, String province) {
        this.city = city;
        this.province = province;
    }

    public Address() {
    }
}
