package com.shadowninja108.util.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class OptionReader extends AutoReader<String, String> {

	public OptionReader(Callable<InputStream> callable) {
		super(callable);
	}

	@Override
	public void read() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(getInputStream(), "UTF-8"));
		for (String line; (line = br.readLine()) != null;)
			if (line.length() > 0)
				if (line.contains("=")) {
					int index = line.indexOf("=");
					String key = line.substring(0, index).trim();
					String value = line.substring(index + 1);
					this.options.put(key, value);
				} else
					System.out.println("Line read is not formatted correctly.");
	}

}
