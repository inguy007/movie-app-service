package com.sample.spring.content.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlContentExtractor {

	public static Map<String, Object> getExtractedContent(String htmlSource) throws MalformedURLException, IOException {
		Map<String, Object> contentMap = new HashMap<>();
		Document doc = HttpConnection.connect(new URL(htmlSource)).get();
		doc.getElementsByTag("meta").stream()
				.filter(metaElement -> StringUtils.isNotBlank(metaElement.attr("property"))).forEach(metaElement -> {
					contentMap.put(metaElement.attr("property"), metaElement.attr("content"));
				});
		processHeaders(doc, "h1", contentMap);
		processHeaders(doc, "h2", contentMap);
		processHeaders(doc, "h3", contentMap);
		return contentMap;

	}

	public static void main(String[] args) throws MalformedURLException, IOException {
		Document doc = Jsoup.parse(new URL("https://en.wikipedia.org/wiki/Shootout_at_Wadala"), 500000);
		Map<String, Object> contentMap = new HashMap<>();
		processHeaders(doc, "h1", contentMap);
		processHeaders(doc, "h2", contentMap);
		processHeaders(doc, "h3", contentMap);
		System.out.println(contentMap);

	}

	private static void processHeaders(Document doc, String headerTag, Map<String, Object> contentMap) {
		Elements headers = doc.select(headerTag);
		if (headers != null && headers.size() > 0) {
			for (int i = 0; i < headers.size(); i++) {
				System.out.println("HEADER :" + headers.get(i).text());
				StringBuilder contentBuilder = new StringBuilder("");
				Element currentHeaderElement = headers.get(i);
				if (currentHeaderElement.nextElementSibling() != null && !currentHeaderElement.nextElementSibling().tagName().matches("^h[1-3]")) {
					readContent(currentHeaderElement.nextElementSibling(), headerTag, contentBuilder);
				}
				contentMap.put(headers.get(i).text(), contentBuilder.toString());
			}
		}
	}

	private static void readContent(Element currentHeaderElement, String headerTag, StringBuilder contentBuilder) {
		if (!currentHeaderElement.tagName().equals(headerTag)) {
			contentBuilder.append(currentHeaderElement.text());
			if (currentHeaderElement.nextElementSibling() != null) {
				readContent(currentHeaderElement.nextElementSibling(), headerTag, contentBuilder);
			}

		}

	}

}
