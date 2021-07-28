package com.corona.main.service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.corona.main.model.Corona;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CoronaVirusDataService {
	private static String VIRUS_DATA_URL="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	
	
	@PostConstruct // this will call when spring is initialized
	@Scheduled(cron = "* * * * * *")
	public List<Corona> fetchVirusData() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
											.uri(URI.create(VIRUS_DATA_URL))
											.build();
		
		HttpResponse<String> res = client.send(request, HttpResponse.BodyHandlers.ofString());
		StringReader csvreader = new StringReader(res.body());
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvreader);
		
		ArrayList<Corona> list = new ArrayList<>();
		for (CSVRecord record : records) {
			Corona c = new Corona();
			c.setState(record.get("Province/State"));
			c.setCountry(record.get("Country/Region"));
			c.setDiffFromPrevDay(0);
			c.setLatestTotalCases(Integer.parseInt(record.get(record.size()-1)));
			list.add(c);
		}
		return list;
	}
}
