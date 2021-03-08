package com.sample.spring.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;
import com.sample.spring.model.Movie;
import com.sample.spring.repository.MovieRepository;

//@Configuration
public class DataLoader {
	
	//@Autowired
	private MovieRepository movieRepository;

	//@PostConstruct
	public void loadData() throws FileNotFoundException {
		JsonReader jsonReader = new JsonReader(new FileReader(new File("/Users/idas/data/movies.json")));
		JsonArray jsonArray = new Gson().fromJson(jsonReader, JsonArray.class);
		//String errorTitles = "";
		for(int i=0;i<jsonArray.size();i++) {
			JsonElement jsonElement = jsonArray.get(i);
			//System.out.println("Processing :"+jsonElement);
			try {
				Movie movieInfo = new Gson().fromJson(jsonElement,Movie.class);
				movieInfo.setMovieId(UUID.randomUUID().toString());
				//System.out.println(movieInfo);
				//break;
				movieRepository.save(movieInfo);
			}catch(Exception e) {
				System.out.println("ERROR****"+jsonElement);
				//errorTitles += jsonElement +",";
				e.printStackTrace();
				continue;
			}
			//System.out.println(movieInfo);
		}
		System.out.println("Data load complete");
	}
	
}
