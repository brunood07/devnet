package com.brunood.social_network.infra.database.mappers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GenericMapper {

    private final ObjectMapper objectMapper;

    public GenericMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // Generic method to map an object of one type to another type
    public <T> T map(Object source, Class<T> targetClass) {
        return objectMapper.convertValue(source, targetClass);
    }

    // Generic method to map an object to a list of a specific type
    public <T> T map(Object source, TypeReference<T> typeReference) {
        return objectMapper.convertValue(source, typeReference);
    }

    // Example for mapping a list
    public <T> List<T> mapList(Object source, Class<T> targetClass) {
        return objectMapper.convertValue(source, new TypeReference<List<T>>() {});
    }
}
