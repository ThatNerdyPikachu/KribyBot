package com.shadowninja108.util;

import java.io.InputStream;

public class JarFile {

	private InputStream stream;

	public JarFile(String file) {
		InputStream stream = getClass().getResourceAsStream(file);
		if (stream != null)
			this.stream = stream;
		else
			throw new RuntimeException("File not found.");
	}

	public InputStream getStream() {
		return stream;
	}
}
