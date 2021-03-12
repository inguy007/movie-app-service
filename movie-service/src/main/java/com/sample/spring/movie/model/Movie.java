package com.sample.spring.movie.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name="MOVIE_INFO")
public class Movie {
	
	@Id
	@Column(name="ID")
	private String movieId;
	
	@Column(name="TITLE")
	private String title;
	
	@Column(name="RELEASE_DATE")
	private Date releaseDate;
	
	@Column(name="RELEASE_YEAR")
	private int releaseYear;
	
	@Column(name="GENRE")
	private String[] genres;
	
	@Column(name="ACTORS")
	private String[] actors;
	
	@Column(name="POSTER_URL")
	private String posterurl;
	
	@Column(name="DURATION")
	private String duration;
	
	@Column(name="PLOT")
	private String storyline;

}
