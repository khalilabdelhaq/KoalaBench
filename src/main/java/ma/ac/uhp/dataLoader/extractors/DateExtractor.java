package ma.ac.uhp.dataLoader.extractors;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.AbstractIterator;

import ma.ac.uhp.dataLoader.tables.DateTable;

public class DateExtractor implements Iterable<DateTable> {

	private String filePath;

	public DateExtractor(String filePath) {
		super();
		this.filePath = filePath;
	}

	@Override
	public Iterator<DateTable> iterator() {
		// TODO Auto-generated method stub
		return new DateExtractorIterator(filePath);
	}

	public static class DateExtractorIterator extends AbstractIterator<DateTable> {
		private String filePath;
		private BufferedReader reader;
		private ObjectMapper mapper;

		public DateExtractorIterator(String filePath) {
			super();
			this.filePath = filePath;
			mapper = new ObjectMapper();
			try {
				reader = new BufferedReader(new FileReader(filePath));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			;
		}

		@Override
		protected DateTable computeNext() {
			DateTable row = null;
			String line;
			try {
				if ((line=reader.readLine()) == null) {
					reader.close();
					return endOfData();
				}
				row = mapper.readValue(line, DateTable.class);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				return endOfData();
			}
			return row;
		}

	}

}
