package com.sample.spring.movie.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sample.spring.movie.extraction.MovieDetailsExtractor;
import com.sample.spring.movie.model.Movie;
import com.sample.spring.movie.repository.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService{
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<Movie> getAllMovies() {
		return movieRepository.findAll();
	}

	@Override
	public Movie getMovie(String movieId) {
		return movieRepository.getOne(movieId);
	}

	@Override
	public Movie extractMovieDetails(String source) {
		String contentServiceURL = "http://content-service/content/extraction";
		Map<String,String> request = new HashMap<>();
		request.put("contentPath",source);
		request.put("type", "HTML");
		Map<String,Object> contentMap = restTemplate.postForObject(contentServiceURL, request, Map.class);
		System.out.println("Content Map :"+contentMap.keySet());
		return MovieDetailsExtractor.extractMovieDetails(contentMap);
	}

}
