package com.shadowninja108.util.reader;

import static com.shadowninja108.bot.shell.ShellProcessor.processString;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class ScriptReader extends AutoReader<String, String> {

	public ScriptReader(Callable<InputStream> callable) {
		super(callable);
	}

	@Override
	public void read() throws IOException {
		int i = 1;
		BufferedReader br = new BufferedReader(new InputStreamReader(getInputStream(), "UTF-8"));
		for (String line; (line = br.readLine()) != null; i++) {
			String[] params = processString(line);
			if (params.length > 2 && params[1].equals("responds"))
					this.options.put(params[0], params[2]);
			else
				System.out.println("Line " + i + " is too short!");
		}
	}

}
