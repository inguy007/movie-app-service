package com.sample.spring.movie.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sample.spring.movie.model.Movie;
import com.sample.spring.movie.repository.MovieRepository;
import com.sample.spring.movie.service.MovieGraphQL;
import com.sample.spring.movie.service.MovieService;

@RestController
public class MovieController {

	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private MovieService movieService;

	@Autowired
	private MovieGraphQL movieGraphQL;

	@GetMapping("/movies")
	public ResponseEntity<List<Movie>> getAllMovies() {
		return ResponseEntity.of(Optional.of(movieRepository.findAll()));
	}
	
	@PostMapping("/movie")
	public ResponseEntity<String> saveMovie(Movie movie){
		movieRepository.saveAndFlush(movie);
		return ResponseEntity.ok("Movie added successfully");
	}
	
	@GetMapping("/movies/{movieId}")
	public ResponseEntity<Movie> getMovie(@PathVariable String movieId){
		return ResponseEntity.ok(movieRepository.getOne(movieId));
	}

	@PostMapping("/movies/graphQL")
	public ResponseEntity<Object> moviesGraphQL(@RequestBody String query) {
		return ResponseEntity.of(Optional.of(movieGraphQL.execute(query)));
	}
	
	@PostMapping("/movies/extract")
	public ResponseEntity<Movie> extractMovieDetails(@RequestBody String source){
		return ResponseEntity.ok(movieService.extractMovieDetails(source));
	}

}
