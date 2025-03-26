package cn.gking.hupaispring.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class Util {
    static ObjectMapper om=new ObjectMapper();
    static String toJson(Object object){
        try {
            return om.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    static Map readValue(String str) throws JsonProcessingException {
        return om.readValue(str, new TypeReference<Map>(){});
    }
}
