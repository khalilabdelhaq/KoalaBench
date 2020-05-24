package ma.ac.uhp.dataLoader.extractors;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.AbstractIterator;

import ma.ac.uhp.dataLoader.tables.LineItemTable;

public class LineItemExtractor implements Iterable<LineItemTable> {

	private String filePath;

	public LineItemExtractor(String filePath) {
		super();
		this.filePath = filePath;
	}

	@Override
	public Iterator<LineItemTable> iterator() {
		// TODO Auto-generated method stub
		return new LineItemExtractorIterator(filePath);
	}

	public static class LineItemExtractorIterator extends AbstractIterator<LineItemTable> {
		private String filePath;
		private BufferedReader reader;
		private ObjectMapper mapper;

		public LineItemExtractorIterator(String filePath) {
			super();
			this.filePath = filePath;
			mapper = new ObjectMapper();
			try {
				reader = new BufferedReader(new FileReader(filePath));
			} catch (FileNotFoundException e) {
				System.out.println("chemin spécifié introuvable");
				e.printStackTrace();
			}
			;
		}

		@Override
		protected LineItemTable computeNext() {
			LineItemTable row = null;
			String line;
			try {
				if ((line=reader.readLine()) == null) {
					reader.close();
					return endOfData();
				}
				row = mapper.readValue(line, LineItemTable.class);
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