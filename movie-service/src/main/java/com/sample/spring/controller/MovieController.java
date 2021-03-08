package com.sample.spring.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sample.spring.model.Movie;
import com.sample.spring.repository.MovieRepository;
import com.sample.spring.service.MovieGraphQL;

@RestController
public class MovieController {

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private MovieGraphQL movieGraphQL;

	@GetMapping("/movies")
	public ResponseEntity<List<Movie>> getAllMovies() {
		return ResponseEntity.of(Optional.of(movieRepository.findAll()));
	}

	@PostMapping("/movies/graphQL")
	public ResponseEntity<Object> moviesGraphQL(@RequestBody String query) {
		return ResponseEntity.of(Optional.of(movieGraphQL.execute(query)));
	}

}
