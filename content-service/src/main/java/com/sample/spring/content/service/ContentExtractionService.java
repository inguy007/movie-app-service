package com.sample.spring.content.service;

import java.util.Map;

public interface ContentExtractionService {

	Map<String, Object> extractContent(String source) throws Exception;

}
