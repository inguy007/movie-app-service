package com.sample.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.spring.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String>{

}
