package com.sample.spring.movie.service;

import java.util.List;

import com.sample.spring.movie.model.Movie;

public interface MovieService {
	
	List<Movie> getAllMovies();
	
	Movie getMovie(String movieId);
	
	Movie extractMovieDetails(String source);

}
