package com.movieflix.service;

import com.movieflix.controller.request.MovieRequest;
import com.movieflix.controller.response.CategoryResponse;
import com.movieflix.controller.response.MovieResponse;
import com.movieflix.entity.Category;
import com.movieflix.entity.Movie;
import com.movieflix.entity.Streaming;
import com.movieflix.mapper.MovieMapper;
import com.movieflix.repository.CategoryRepository;
import com.movieflix.repository.MovieRepository;
import com.movieflix.repository.StreamingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final CategoryRepository categoryRepository;
    private final StreamingRepository streamingRepository;

    public List<MovieResponse> listAll () {
        return movieRepository.findAll()
                .stream()
                .map(MovieMapper::toResponse)
                .toList();
    }

    public MovieResponse save (MovieRequest request) {
        Movie entity = MovieMapper.toEntity(request);

        entity.setCategories(this.findCategories(entity.getCategories()));
        entity.setStreamings(this.findStreamings(entity.getStreamings()));

        Movie entitySaved = movieRepository.save(entity);
        return MovieMapper.toResponse(entitySaved);
    }

    public MovieResponse findById (Long id) throws Exception {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(Exception::new);

        return MovieMapper.toResponse(movie);
    }

    public void deleteById (Long id) {
        movieRepository.deleteById(id);
    }

    public MovieResponse update(Long id, MovieRequest request) throws Exception {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(Exception::new);

        List<Category> categories = findCategories(
                request.categories().stream()
                        .map(categoryId -> Category.builder().id(categoryId).build())
                        .toList()
        );

        List<Streaming> streamings = findStreamings(
                request.streamings().stream()
                        .map(steamingId -> Streaming.builder().id(steamingId).build())
                        .toList()
        );

        movie.setTitle(request.title());
        movie.setDescription(request.description());
        movie.setReleaseDate(request.releaseDate());
        movie.setRating(request.rating());

        movie.getCategories().clear();
        movie.getCategories().addAll(categories);

        movie.getStreamings().clear();
        movie.getStreamings().addAll(streamings);

        return MovieMapper.toResponse(movieRepository.save(movie));
    }

    public List<MovieResponse> findByCategory(Long categoryId) {
        return movieRepository.findByCategories_Id(categoryId)
                .stream()
                .map(MovieMapper::toResponse)
                .toList();
    }

    private List<Category> findCategories (List<Category> categories) {

        List<Category> categoriesFound = new ArrayList<>();

        categories.forEach(category -> {
            Optional<Category> categoryOptional = categoryRepository.findById(category.getId());

            categoryOptional.ifPresent(categoriesFound::add);
        });

        return categoriesFound;
    }

    private List<Streaming> findStreamings(List<Streaming> streamings) {

        List<Streaming> streamingsFound = new ArrayList<>();

        streamings.forEach(streaming -> {
            Optional<Streaming> streamingOptional = streamingRepository.findById(streaming.getId());

            streamingOptional.ifPresent(streamingsFound::add);
        });

        return streamingsFound;
    }

}
