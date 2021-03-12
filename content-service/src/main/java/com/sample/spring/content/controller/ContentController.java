package com.sample.spring.content.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sample.spring.content.service.ContentExtractionService;
import com.sample.spring.content.vo.ExtractionRequestVO;

@RestController
public class ContentController {

	@Autowired
	private ContentExtractionService contentExtractionService;
	
	@PostMapping("/content/extraction")
	public ResponseEntity<Object> getContent(@RequestBody ExtractionRequestVO extractionRequestVO) throws Exception{
		String source = extractionRequestVO.getContentPath();
		return ResponseEntity.ok(contentExtractionService.extractContent(source));
	}
	
}
