package com.shadowninja108.bot;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.concurrent.Callable;

import com.shadowninja108.bot.listener.BotListener;
import com.shadowninja108.main.Launcher;
import com.shadowninja108.translatable.Translatable;
import com.shadowninja108.util.reader.ScriptReader;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Activity.ActivityType;;

public class UberCoolBot {
	private JDABuilder builder;

	public static ScriptReader reader;
	public static boolean debug;

	boolean started = false;

	public UberCoolBot() {
		String token = Launcher.options.get("token");
		boolean isBot = Boolean.parseBoolean(Launcher.options.get("bot"));
		boolean needScript = Boolean.parseBoolean(Launcher.options.get("script"));
		debug = Boolean.parseBoolean(Launcher.options.get("debug"));

		if (needScript) {
			File file = new File("./script.txt");
			if (file.exists()) {
				reader = new ScriptReader(new Callable<InputStream>() {

					@Override
					public InputStream call() throws Exception {
						return new FileInputStream(file);
					}
				});
			} else
				System.out.println("Script.txt does not exist! Skipping...");
		}
		System.out.println("Booting as a " + (isBot ? "bot" : "user"));
		builder = new JDABuilder((isBot) ? AccountType.BOT : AccountType.CLIENT);
		builder.setToken(token);
		builder.setActivity(Activity.of(ActivityType.WATCHING, Translatable.get("global.game_name"), Translatable.get("global.game_url")));
	}

	public void register() {
		builder.addEventListeners(new BotListener());
	}

	public void start() {
		try {
			if (!started) {
				builder.build();
				started = true;
			} else {
				System.out.println("Bot already started!");
			}
		} catch (Exception e) {
			new Exception("Failed to start bot! Exception occured in starting!", e).printStackTrace();
		}
	}
}
