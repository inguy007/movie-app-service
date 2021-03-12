package com.sample.spring.service;

import java.util.List;

import com.sample.spring.model.Movie;

public interface MovieService {
	
	List<Movie> getAllMovies();
	
	Movie getMovie(String movieId);
	
	Movie extractMovieDetails(String source);

}
