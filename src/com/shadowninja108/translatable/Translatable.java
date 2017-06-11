package com.shadowninja108.translatable;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.concurrent.Callable;

import com.shadowninja108.util.reader.FallbackOptionReader;
import com.shadowninja108.util.reader.OptionReader;

public class Translatable {

	private static FallbackOptionReader reader;

	static {
		reader = new FallbackOptionReader(new OptionReader(new Callable<InputStream>() {

			@Override
			public InputStream call() throws Exception {
				return Translatable.class.getClassLoader().getResourceAsStream("default_translatable.txt");
			}
		}));
		reader.getFallback().setAutoUpdateable(false);
	}

	public static void setInput(File in) {
		reader.setMain(new OptionReader(new Callable<InputStream>() {
			@Override
			public InputStream call() throws Exception {
				return new FileInputStream(in);
			}
		}));
	}

	public static String get(String key) {
		return reader.get(key);
	}

}
