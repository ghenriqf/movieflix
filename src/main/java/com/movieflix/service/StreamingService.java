package com.movieflix.service;

import com.movieflix.controller.request.StreamingRequest;
import com.movieflix.controller.response.StreamingResponse;
import com.movieflix.entity.Streaming;
import com.movieflix.mapper.StreamingMapper;
import com.movieflix.repository.StreamingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StreamingService {

    private final StreamingRepository streamingRepository;

    public List<StreamingResponse> findAll () {
        return streamingRepository
                .findAll()
                .stream()
                .map(StreamingMapper::toResponse)
                .toList();
    }

    public StreamingResponse save (StreamingRequest request) {
        Streaming entity = StreamingMapper.toEntity(request);
        Streaming entitySaved = streamingRepository.save(entity);
        return StreamingMapper.toResponse(entitySaved);
    }

    public StreamingResponse findById (Long id) throws Exception {
        return streamingRepository
                .findById(id)
                .map(StreamingMapper::toResponse)
                .orElseThrow(Exception::new);
    }

    public void deleteById (Long id) {
        streamingRepository.deleteById(id);
    }
}
