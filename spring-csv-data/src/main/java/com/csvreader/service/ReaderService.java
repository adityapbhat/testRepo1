package com.csvreader.service;

import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Service
public class ReaderService {

	private static final char COMMA = ',';
	private static final Integer DEFAULT_PAGE = 1;

	@Value("${csv.filepath}")
	String filepath;

	@Value("${csv.page.limit}")
	int recordsPerPage;

	CSVReader csvReader;
	Reader fileReader;

	private CSVReader configureReader(Reader reader) throws IOException {
		CSVParser parser = new CSVParserBuilder().withSeparator(COMMA).withIgnoreQuotations(false).build();
		CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(0).withCSVParser(parser).build();
		return csvReader;
	}

	private Integer getPageIndex(Integer page) {
		return null != page && page >= 0 ? page : DEFAULT_PAGE;
	}

	public List<String[]> readData(Integer pageNumber, Model model) throws IOException, URISyntaxException {
		List<String[]> list = new ArrayList<>();
		Reader reader = Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource("Workbook2.csv").toURI()),
				Charset.forName("Cp1252"));
		CSVReader csvReader = configureReader(reader);
		String[] line = null;
		int rowCount = 0;
		pageNumber = getPageIndex(pageNumber);
		int startIndex = ((pageNumber - 1) * recordsPerPage) + 2;
		while ((line = csvReader.readNext()) != null) {
			++rowCount;
			if (rowCount == 1 || rowCount >= startIndex) {
				list.add(line);
			}
			if (rowCount == (startIndex + recordsPerPage - 1)) {
				break;
			}
		}
		if ((recordsPerPage == list.size() - 1) && (line = csvReader.readNext()) != null) {
			model.addAttribute("nextPage", pageNumber + 1);
		}
		if(pageNumber>1) {
			model.addAttribute("previousPage", pageNumber - 1);
		}
		reader.close();
		csvReader.close();
		model.addAttribute("csvdata", list);
		return list;
	}

}
