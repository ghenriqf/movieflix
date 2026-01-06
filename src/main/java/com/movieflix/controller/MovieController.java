package com.movieflix.controller;

import com.movieflix.controller.request.MovieRequest;
import com.movieflix.controller.response.MovieResponse;
import com.movieflix.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movieflix/movie")
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    private ResponseEntity<List<MovieResponse>> getAll () {
        return ResponseEntity.ok(movieService.listAll());
    }

    @PostMapping
    public ResponseEntity<MovieResponse> save (@RequestBody MovieRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.save(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getById (@PathVariable Long id) {
        try {
            return ResponseEntity.ok(movieService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById (@PathVariable Long id) {
        movieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> update (@PathVariable Long id, @RequestBody MovieRequest request) {
        try {
            return ResponseEntity.ok(movieService.update(id, request));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieResponse>> findByCategory (@RequestParam Long category) {
        return ResponseEntity.ok(movieService.findByCategory(category));
    }
}
