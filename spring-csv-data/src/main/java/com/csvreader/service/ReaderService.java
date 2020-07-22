package com.csvreader.service;

import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.csvreader.Constants;
import com.csvreader.config.ConfigProperties;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Service
public class ReaderService {

	@Autowired
	ConfigProperties configProperties;

	CSVReader csvReader;
	Reader fileReader;

	private CSVReader configureReader(Reader reader) throws IOException {
		CSVParser parser = new CSVParserBuilder().withSeparator(Constants.COMMA).withIgnoreQuotations(false).build();
		CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(0).withCSVParser(parser).build();
		return csvReader;
	}

	private Integer getPageIndex(Integer page) {
		return null != page && page >= 0 ? page : Constants.DEFAULT_PAGE;
	}

	public void readData(Integer pageNumber, Model model) throws IOException, URISyntaxException {
		List<String[]> list = new ArrayList<>();
		int recordsPerPage = configProperties.getRecordsPerPage();
		Reader reader = null;
		CSVReader csvReader = null;
		try {
			reader = Files.newBufferedReader(
					Paths.get(ClassLoader.getSystemResource(configProperties.getFilepath()).toURI()),
					Charset.forName(Constants.ANSI_CHARSET_NAME));
			csvReader = configureReader(reader);
			String[] line = null;
			int rowCount = 0;
			pageNumber = getPageIndex(pageNumber);
			int startIndex = ((pageNumber - 1) * recordsPerPage) + 2;
			while ((line = csvReader.readNext()) != null) {
				++rowCount;
				if (rowCount == 1 || rowCount >= startIndex) {
					list.add(line);
				}
				if (rowCount == ((startIndex + recordsPerPage) - 1)) {
					break;
				}
			}
			if ((recordsPerPage == list.size() - 1) && (line = csvReader.readNext()) != null) {
				model.addAttribute(Constants.NEXT_PAGE, pageNumber + 1);
			}
			if (pageNumber > 1) {
				model.addAttribute(Constants.PREVIOUS_PAGE, pageNumber - 1);
			}
		} finally {
			if (null != csvReader) {
				csvReader.close();
			}
			if (null != reader) {
				reader.close();
			}
		}
		model.addAttribute(Constants.CSV_DATA, list);
	}

}
