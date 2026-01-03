package com.movieflix.mapper;

import com.movieflix.controller.request.StreamingRequest;
import com.movieflix.controller.response.StreamingResponse;
import com.movieflix.entity.Streaming;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StreamingMapper {

    public static StreamingResponse toResponse (Streaming entity) {
        return StreamingResponse
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }


    public static Streaming toEntity (StreamingRequest request) {
        return Streaming
                .builder()
                .name(request.name())
                .build();
    }
}
