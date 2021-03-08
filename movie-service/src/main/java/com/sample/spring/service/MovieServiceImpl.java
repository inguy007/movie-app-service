package com.sample.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.spring.model.Movie;
import com.sample.spring.repository.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService{
	
	@Autowired
	private MovieRepository movieRepository;

	@Override
	public List<Movie> getAllMovies() {
		return movieRepository.findAll();
	}

	@Override
	public Movie getMovie(String movieId) {
		return movieRepository.getOne(movieId);
	}

}
