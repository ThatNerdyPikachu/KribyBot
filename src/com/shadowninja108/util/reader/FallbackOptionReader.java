package com.shadowninja108.util.reader;

import java.util.Map;

public class FallbackOptionReader {

	private OptionReader main, fallback;

	public FallbackOptionReader(OptionReader fallback) {
		this.fallback = fallback;
	}

	public FallbackOptionReader(OptionReader main, OptionReader fallback) {
		this.main = main;
		this.fallback = fallback;
	}

	public String get(Object obj) {
		if (main != null) {
			Map<String, String> main = this.main.getOptions();
			if (main.containsKey(obj))
				return main.get(obj);
		}
		if (fallback != null)
			return fallback.getOptions().get(obj);
		return null;
	}

	public OptionReader getReader() {
		if (main != null)
			return main;
		return fallback;
	}

	public OptionReader getMain() {
		return main;
	}

	public void setMain(OptionReader main) {
		this.main = main;
	}

	public OptionReader getFallback() {
		return fallback;
	}

	public void setFallback(OptionReader reader) {
		fallback = reader;
	}

}
