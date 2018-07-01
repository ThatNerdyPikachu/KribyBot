package com.shadowninja108.translatable;

import java.io.File;
import java.io.FileInputStream;

import com.shadowninja108.util.reader.FallbackOptionReader;
import com.shadowninja108.util.reader.OptionReader;

public class Translatable {

	private static FallbackOptionReader reader;

	static {
		reader = new FallbackOptionReader(new OptionReader(
				() -> Translatable.class.getClassLoader().getResourceAsStream("default_translatable.txt")));
		reader.getFallback().setAutoUpdatable(false);
	}

	public static void setInput(File in) {
		reader.setMain(new OptionReader(() -> new FileInputStream(in)));
	}

	public static String get(String key) {
		return reader.get(key);
	}

}
