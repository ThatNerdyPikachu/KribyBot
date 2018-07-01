package com.shadowninja108.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.concurrent.Callable;

import com.shadowninja108.bot.UberCoolBot;
import com.shadowninja108.translatable.Translatable;
import com.shadowninja108.util.MemeUtil;
import com.shadowninja108.util.reader.FallbackOptionReader;
import com.shadowninja108.util.reader.OptionReader;

public class Launcher {

	private UberCoolBot bot;

	public static FallbackOptionReader options;

	public static final String author_mentionable = "<@128656451236397056>";

	static {
		options = new FallbackOptionReader(new OptionReader(new Callable<InputStream>() {

			@Override
			public InputStream call() throws Exception {
				return getClass().getClassLoader().getResourceAsStream("default_options.txt");
			}
		}));
	}

	public Launcher() {
		setup();

		bot = new UberCoolBot();

		bot.register();

		bot.start(false);
	}

	private void setup() {
		File file = new File("./options.txt");
		if (file.exists())
			Launcher.options.setMain(new OptionReader(new Callable<InputStream>() {
				@Override
				public InputStream call() throws Exception {
					return new FileInputStream(file);
				}
			}));
		else {
			System.out.println("No options.txt detected! Closing...");
			System.exit(1);
		}

		if (MemeUtil.anyAreEmpty(Launcher.options.get("token"))) {
			System.out.println("Token is not found in options.txt! Closing...");
			System.exit(1);
		}

		File translatable = new File("./translatable.txt");
		if (translatable.exists())
			Translatable.setInput(translatable);
		else
			System.out.println("No translatable.txt found. Falling back on defaults.");
	}

	public static String getPrefix() {
		return options.get("prefix");
	}

	public static void main(String[] args) {
		new Launcher();
	}

}
