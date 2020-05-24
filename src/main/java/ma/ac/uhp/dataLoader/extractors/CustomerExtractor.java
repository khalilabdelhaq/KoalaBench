package ma.ac.uhp.dataLoader.extractors;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.AbstractIterator;

import ma.ac.uhp.dataLoader.tables.CustomerTable;

public class CustomerExtractor implements Iterable<CustomerTable> {

	private String filePath;

	public CustomerExtractor(String filePath) {
		super();
		this.filePath = filePath;
	}

	@Override
	public Iterator<CustomerTable> iterator() {
		// TODO Auto-generated method stub
		return new CustomerGeneratorIterator(filePath);
	}

	public static class CustomerGeneratorIterator extends AbstractIterator<CustomerTable> {
		private String filePath;
		ObjectMapper mapper;
		private BufferedReader reader;
		public CustomerGeneratorIterator(String filePath) {
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
		protected CustomerTable computeNext() {
			CustomerTable row = null;
			String line;
			try {
				if ((line=reader.readLine()) == null) {
					reader.close();
					return endOfData();
				}
				row = mapper.readValue(line, CustomerTable.class);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return row;
		}
	}
}