package com.movieflix.controller;


import com.movieflix.controller.request.StreamingRequest;
import com.movieflix.controller.response.StreamingResponse;
import com.movieflix.service.StreamingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movieflix/streaming")
@RequiredArgsConstructor
public class StreamingController {

    private final StreamingService streamingService;

    @GetMapping
    public ResponseEntity<List<StreamingResponse>> getAll () {
        return ResponseEntity.ok(streamingService.findAll());
    }

    @PostMapping
    public ResponseEntity<StreamingResponse> save (@RequestBody StreamingRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(streamingService.save(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StreamingResponse> getById (@PathVariable Long id) {
        try {
            return ResponseEntity.ok(streamingService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById (@PathVariable Long id) {
        streamingService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
