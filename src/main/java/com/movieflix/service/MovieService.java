package com.movieflix.service;

import com.movieflix.controller.request.MovieRequest;
import com.movieflix.controller.response.MovieResponse;
import com.movieflix.entity.Movie;
import com.movieflix.mapper.MovieMapper;
import com.movieflix.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public List<MovieResponse> listAll () {
        return movieRepository.findAll()
                .stream()
                .map(MovieMapper::toResponse)
                .toList();
    }

    public MovieResponse save (MovieRequest request) {
        Movie entity = MovieMapper.toEntity(request);
        Movie entitySaved = movieRepository.save(entity);
        return MovieMapper.toResponse(entitySaved);
    }

    public MovieResponse findById (Long id) throws Exception {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent()) {
            return MovieMapper.toResponse(movie.get());
        } else {
            throw new Exception();
        }
    }

    public void deleteById (Long id) {
        movieRepository.deleteById(id);
    }
}
