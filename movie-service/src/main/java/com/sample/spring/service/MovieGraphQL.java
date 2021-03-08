package com.sample.spring.service;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaGenerator.Options;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

@Component
public class MovieGraphQL {

	private GraphQL graphQL;

	@Value("classpath:movie.graphql")
	private Resource movieGraphQLSchema;

	@Autowired
	private MovieService movieService;

	@PostConstruct
	public void prepareMovieGraphQL() throws IOException {
		TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(movieGraphQLSchema.getFile());
		RuntimeWiring runtimeWiring = buildRuntimeWiring();
		GraphQLSchema graphQLSchema = new SchemaGenerator().makeExecutableSchema(Options.defaultOptions(),
				typeDefinitionRegistry, runtimeWiring);
		graphQL = GraphQL.newGraphQL(graphQLSchema).build();
	}

	private RuntimeWiring buildRuntimeWiring() {
		return RuntimeWiring.newRuntimeWiring()
				.type("Query", typeWiring -> typeWiring.dataFetcher("getAllMovies", env -> {
					return movieService.getAllMovies();
				}).dataFetcher("getMovie", env -> {
					String movieId = env.getArgument("id");
					return movieService.getMovie(movieId);
				})

				).build();

	}

	public Object execute(String query) {
		return graphQL.execute(query).getData();
	}

}
