package ma.ac.uhp.dataLoader.extractors;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.AbstractIterator;

import ma.ac.uhp.dataLoader.tables.SupplierTable;

public class SupplierExtractor implements Iterable<SupplierTable> {

	private String filePath;

	public SupplierExtractor(String filePath) {
		super();
		this.filePath = filePath;
	}

	@Override
	public Iterator<SupplierTable> iterator() {
		// TODO Auto-generated method stub
		return new SupplierExtractorIterator(filePath);
	}

	public static class SupplierExtractorIterator extends AbstractIterator<SupplierTable> {
		private String filePath;
		private BufferedReader reader;
		ObjectMapper mapper;

		public SupplierExtractorIterator(String filePath) {
			super();
			this.filePath = filePath;
			try {
				reader = new BufferedReader(new FileReader(filePath));
				mapper = new ObjectMapper();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			;
		}

		@Override
		protected SupplierTable computeNext() {
			SupplierTable row = null;
			String line;
			try {
				if ((line=reader.readLine()) == null) {
					reader.close();
					return endOfData();
				}
				row = mapper.readValue(line, SupplierTable.class);
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
