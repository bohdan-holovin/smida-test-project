package com.holovin.smidatestproject.repository.mapper;

import com.holovin.smidatestproject.model.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class RoleSetMapper implements AttributeConverter<Set<Role>, String> {

    @Override
    public String convertToDatabaseColumn(Set<Role> roles) {
        return roles != null ? roles.stream().map(Role::name).collect(Collectors.joining(",")) : "";
    }

    @Override
    public Set<Role> convertToEntityAttribute(String dbData) {
        return dbData != null && !dbData.isEmpty() ? 
            Stream.of(dbData.split(",")).map(Role::valueOf).collect(Collectors.toSet()) : 
            Set.of();
    }
}
