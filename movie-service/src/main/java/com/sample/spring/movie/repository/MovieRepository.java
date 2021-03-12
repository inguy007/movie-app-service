package com.sample.spring.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.spring.movie.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String>{

}
