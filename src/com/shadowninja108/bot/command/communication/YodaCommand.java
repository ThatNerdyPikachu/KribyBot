package com.shadowninja108.bot.command.communication;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.shadowninja108.bot.command.Command;
import com.shadowninja108.main.Launcher;
import com.shadowninja108.translatable.Translatable;

import static com.shadowninja108.util.MessageUtil.*;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class YodaCommand implements Command {

	@Override
	public String getName() {
		return Translatable.get("yoda.name");
	}

	@Override
	public String getDescription() {
		return Translatable.get("yoda.description");
	}

	@Override
	public void serverMessage(MessageReceivedEvent event) {
		String message = event.getMessage().getContent();
		if (message.contains(" ")) {
			message = message.substring(message.indexOf(" ")).trim();
			if (!message.isEmpty()) {
				try {
					HttpResponse<String> response = Unirest
							.get("https://yoda.p.mashape.com/yoda?sentence=" + URLEncoder.encode(message, "UTF-8"))
							.header("X-Mashape-Key", Launcher.marketplace_api_key).header("Accept", "text/plain")
							.asString();
					String string_response = IOUtils.toString(response.getRawBody(), "UTF-8");
					if (!string_response.isEmpty())
						sendMessage(string_response, event.getChannel());
					else
						sendMessage(Translatable.get("yoda.error"), event.getChannel());
				} catch (UnirestException | IOException e) {
					sendMessage(Translatable.get("yoda.error"), event.getChannel());
					e.printStackTrace();
				}
			} else
				sendMessage(Translatable.get("yoda.empty"), event.getChannel());
		} else
			sendMessage(Translatable.get("yoda.empty"), event.getChannel());
	}

	@Override
	public void privateMessage(MessageReceivedEvent event) {
		String message = event.getMessage().getContent();
		if (message.contains(" ")) {
			message = message.substring(message.indexOf(" ")).trim();
			if (!message.isEmpty()) {
				try {
					HttpResponse<String> response = Unirest
							.get("https://yoda.p.mashape.com/yoda?sentence=" + URLEncoder.encode(message, "UTF-8"))
							.header("X-Mashape-Key", Launcher.marketplace_api_key).header("Accept", "text/plain")
							.asString();
					String string_response = IOUtils.toString(response.getRawBody(), "UTF-8");
					if (!string_response.isEmpty())
						sendMessage(string_response, event.getChannel());
					else
						sendMessage(Translatable.get("yoda.error"), event.getAuthor());
				} catch (UnirestException | IOException e) {
					sendMessage(Translatable.get("yoda.error"), event.getAuthor());
					e.printStackTrace();
				}
			} else
				sendMessage(Translatable.get("yoda.empty"), event.getAuthor());
		} else
			sendMessage(Translatable.get("yoda.empty"), event.getAuthor());
	}

}
