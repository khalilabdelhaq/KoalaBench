package ma.ac.uhp.dataLoader.extractors;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.AbstractIterator;

import ma.ac.uhp.dataLoader.tables.PartTable;

public class PartExtractor implements Iterable<PartTable> {

	private String filePath;

	public PartExtractor(String filePath) {
		super();
		this.filePath = filePath;
	}

	@Override
	public Iterator<PartTable> iterator() {
		// TODO Auto-generated method stub
		return new PartExtractorIterator(filePath);
	}

	public static class PartExtractorIterator extends AbstractIterator<PartTable> {
		private String filePath;
		private BufferedReader reader;
		private ObjectMapper mapper;

		public PartExtractorIterator(String filePath) {
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
		protected PartTable computeNext() {
			PartTable row = null;
			String line;
			try {
				if ((line=reader.readLine()) == null) {
					reader.close();
					return endOfData();
				}
				row = mapper.readValue(line, PartTable.class);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return endOfData();
			}
			return row;
		}

	}
}
