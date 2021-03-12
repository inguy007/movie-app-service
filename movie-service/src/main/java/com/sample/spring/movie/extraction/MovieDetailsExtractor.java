package com.sample.spring.movie.extraction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.sample.spring.movie.model.Movie;

import io.micrometer.core.instrument.util.StringUtils;

@Component
public class MovieDetailsExtractor {

	private static BiFunction<String, String, String> titleExtractor = (key, value) -> {
		if (key.toLowerCase().matches("(.*)title(.*)")) {
			return value;
		}
		return null;
	};
	
	private static BiFunction<String, String, String> posterExtractor = (key, value) -> {
		if (key.toLowerCase().matches("((.*)poster(.*)|(.*)image(.*))")) {
			return value;
		}
		return null;
	};

	private static BiFunction<String, String, String> storyExtractor = (key, value) -> {
		if (key.toLowerCase().matches("((.*)plot(.*)|(.*)story(.*)|(.*)storyline(.*))")) {
			return value;
		}
		return null;
	};
	
	private static BiFunction<String,String,String> boxOfficeDetailsExtractor = (key,value) -> {
		if(key.toLowerCase().matches("(.*)box office(.*)")) {
			return value;
		}
		return value;
	};
	
	private static BiFunction<String,String,String[]> actorsExtractor = (key,value) -> {
		List<String> actors = new ArrayList<>();
		if(key.toLowerCase().matches("(.*)cast(.*)|(.*)actor(.*)") && StringUtils.isNotBlank(value)) {
			value = value.replaceAll("[\\s]{1,}\\.\\.\\.[\\s]{1,}","##");
			value = value.replaceAll("[\\s]{1,}as[\\s]{1,}","##");
			Pattern pattern = Pattern.compile("(([\\w]+)[\\s]([\\w]+))[##]", Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(value);
			while(matcher.find()) {
				actors.add(matcher.group(1));
			}
		}
		return actors.toArray(new String[actors.size()]);
	};

	public static Movie extractMovieDetails(Map<String, Object> contentMap) {
		Movie movie = new Movie();
		for(Entry<String,Object> contentEntry : contentMap.entrySet() ) {
			String key = contentEntry.getKey();
			String value = String.valueOf(contentEntry.getValue());
			String title = titleExtractor.apply(key,value);
			if(title != null) {
				movie.setTitle(title);
			}
			String story = storyExtractor.apply(key,value);
			if(story != null) {
				movie.setStoryline(story);
			}
			String[] actors = actorsExtractor.apply(key,value);
			if(actors != null && actors.length > 0) {
				movie.setActors(actors);
			}
			String boxOffice = boxOfficeDetailsExtractor.apply(key,value);
			if(boxOffice != null) {
				movie.setBoxOffice(boxOffice);
			}
			String posterUrl = posterExtractor.apply(key,value);
			if(posterUrl != null) {
				movie.setPosterurl(posterUrl);
			}
			
		}
		return movie;
	}
	
	
	public static void main(String[] args) {
		String key = "og: image";
		System.out.println(key.matches("(.*)image(.*)"));
	}
	
	

//	public static void main(String[] args) {
//		//String text = "Cast overview, first billed only: Anil Kapoor ... Afaaque Baagraan John Abraham ... Manya Surve Manoj Bajpayee ... Zubair Imtiaz Haskar Tusshar Kapoor ... Sheikh Munir Kangana Ranaut ... Vidya Sonu Sood ... Dilawar Imtiaz Haskar Ronit Roy ... Rajan Ambat Mahesh Manjrekar ... Inspector Bhende Raju Mavani ... Yakub Lala Arif Zakaria ... Sadik Pankaj Kalra ... Sayed Batia Karan Patel ... Jamal Hussain Sheikh ... Abdul Phelwan Vineet Sharma ... Bhargav Surve Raju Kher ... Inspector AmbolkarSee full cast »View production, box office, & company info";
//		String text = "John Abraham as Manya Surve Anil Kapoor as ACP Afaaque Baaghran (character based on Isaque Bagwan) Kangana Ranaut as Vidya Joshi Tusshar Kapoor as Sheikh Munir[7] Manoj Bajpayee as Zubair Imtiaz Haksar (character based on Shabir Ibrahim Kaskar) Sonu Sood as Dilawar Imtiaz Haksar (character based on Dawood Ibrahim Kaskar) Akbar Khan as Haji Maqsood (character based on Haji Mastan) (cameo) Ronit Roy as Inspector Raja Ambat (character based on Raja Tambat) Mahesh Manjrekar as Inspector Bhinde (character based on Madhukar Shinde) Siddhanth Kapoor as Gyancho (character based on Vishnu Patil) Ranjeet as Bhatkar Dada Jackie Shroff as Police Commissioner (fictional) (cameo) Raju Kher as Inspector Ambolkar (character based on Inspector Dabholkar) Arif Zakaria as Sadiq (news reporter) (character based on Iqbal Naatiq) Raju Mavani as Yakub Lala Chetan Hansraj as Potya (character based on Suhas Bhatkar) Karan Patel as Jamal (Bhatkar's bodyguard) (fictional) Sanjeev Chadda as Veera (character based on Uday Shetty) Vineet Sharma as Bhargav Surve[8][9] Pankaj Kalra as Sayed Batla";
//		text = text.replaceAll("[\\s]{1,}\\.\\.\\.[\\s]{1,}","##");
//		text = text.replaceAll("[\\s]{1,}as[\\s]{1,}","##");
//		System.out.println(text);
//		Pattern pattern = Pattern.compile("(([\\w]+)[\\s]([\\w]+))[##]", Pattern.CASE_INSENSITIVE);
//		Matcher matcher = pattern.matcher(text);
//		while(matcher.find()) {
//			System.out.println(matcher.group(1));
//		}
//	}

}
