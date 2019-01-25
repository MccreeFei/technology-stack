package cn.mccreefei.technologystack.rpc.support.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author MccreeFei
 * @create 2018-10-12 下午3:16
 * Json序列化反序列化
 */
@Component
public class JsonSerialization implements Serialization {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> byte[] serialize(T obj) {
        try {
            return objectMapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T deSerialize(byte[] bytes, Class<T> cls) {
        try {
            return objectMapper.readValue(bytes, cls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
