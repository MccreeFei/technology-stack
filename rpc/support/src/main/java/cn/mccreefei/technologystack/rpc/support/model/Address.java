package cn.mccreefei.technologystack.rpc.support.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author MccreeFei
 * @create 2018-10-17 下午5:07
 */
@Data
public class Address {
    private String province;
    private String city;

    public Address(){}

    @Builder
    public Address(String province, String city) {
        this.province = province;
        this.city = city;
    }
}
