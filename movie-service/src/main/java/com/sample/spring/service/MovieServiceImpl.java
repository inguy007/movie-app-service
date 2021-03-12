package com.sample.spring.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sample.spring.model.Movie;
import com.sample.spring.model.Movie.MovieBuilder;
import com.sample.spring.repository.MovieRepository;

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
		System.out.println("Content Map :"+contentMap);
		MovieBuilder movie = Movie.builder();
		for(Entry<String,Object> contentEntry : contentMap.entrySet()) {
			String value = String.valueOf(contentEntry.getValue());
			if(contentEntry.getKey().contains("title")) {
				movie.title(value);
			}else if((contentEntry.getKey().contains("image")) || (contentEntry.getKey().contains("poster"))){
				movie.posterurl(value);
			}else if((contentEntry.getKey().contains("Storyline")) || (contentEntry.getKey().contains("story")) || (contentEntry.getKey().contains("Plot"))) {
				movie.storyline(value);
			}
		}
		return movie.build();
	}

}
