package com.movieflix.mapper;

import com.movieflix.controller.request.MovieRequest;
import com.movieflix.controller.response.MovieResponse;
import com.movieflix.entity.Category;
import com.movieflix.entity.Movie;
import com.movieflix.entity.Streaming;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class MovieMapper {

    public static Movie toEntity (MovieRequest request) {

        List<Category> categories = request.categories()
                .stream()
                .map(categoryId -> Category.builder().id(categoryId).build()).toList();

        List<Streaming> streamings = request.streamings()
                .stream()
                .map(streamingId -> Streaming.builder().id(streamingId).build())
                .toList();

        return Movie
                .builder()
                .title(request.title())
                .description(request.description())
                .releaseDate(request.releaseDate())
                .rating(request.rating())
                .categories(categories)
                .streamings(streamings)
                .build();
    }

    public static MovieResponse toResponse (Movie entity) {
        return MovieResponse
                .builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .releaseDate(entity.getReleaseDate())
                .rating(entity.getRating())
                .categories(entity.getCategories().stream().map(CategoryMapper::toResponse).toList())
                .streamings(entity.getStreamings().stream().map(StreamingMapper::toResponse).toList())
                .build();
    }
}
