package cz.cvut.fit.mi_paa.booolean_satisfability;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;

import cz.cvut.fit.mi_paa.booolean_satisfability.domain.Row;

public class SATReader implements Iterator<Row> {

	private BufferedReader br;

	private String line;

	public SATReader(File file) throws FileNotFoundException {
		this.br = new BufferedReader(new FileReader(file));
	}

	@Override
	public boolean hasNext() {
		boolean next = true;
		try {
			line = br.readLine();
			if (line.trim().length() <= 0) {
				next = false;
				try {
					br.close();
				} catch (Exception e) {

				}
			}
		} catch (Exception e) {
			next = false;
		}
		return next;
	}

	@Override
	public Row next() {
		return new Row(line);
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
