package com.sample.spring.content.service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class HtmlContentExtractionService implements ContentExtractionService {

	@Override
	public Map<String, Object> extractContent(String source) throws Exception {
		return HtmlContentExtractor.getExtractedContent(source);
	}

}
