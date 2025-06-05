package com.oprosita.backend.mapper;

import org.mapstruct.Mapper;

import java.net.URI;

@Mapper(componentModel = "spring")
public interface UriMapper {

    default String map(URI uri) {
        return uri != null ? uri.toString() : null;
    }

    default URI map(String uri) {
        return uri != null ? URI.create(uri) : null;
    }
}
