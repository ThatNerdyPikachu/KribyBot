package com.shadowninja108.bot.command.mention;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.shadowninja108.bot.command.Command;
import com.shadowninja108.bot.shell.ShellProcessor;
import com.shadowninja108.util.DiscordUtils;
import com.shadowninja108.util.Emotes;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ReactText implements Command {

	@Override
	public String getName() {
		return "reacttext";
	}

	@Override
	public String getDescription() {
		return "Spells out given message using mentions. Limited by max 20 reactions.";
	}

	@Override
	public void serverMessage(MessageReceivedEvent event) {
		Message sourceMessage = event.getMessage();
		Message targetMessage = null;
		String[] params = ShellProcessor.processString(sourceMessage.getContentRaw());
		String message = null;
		if (params.length == 2) {
			targetMessage = sourceMessage;
			message = params[1];
		} else if (params.length == 3) {
			String messageId = params[1];
			message = params[2];
			targetMessage = DiscordUtils.findMessage(event.getGuild(), messageId);
		}

		if (targetMessage != null) {
			int reactions = targetMessage.getReactions().size();
			Map<Character, List<String>> emotes = Emotes.emotes;
			List<String> usedEmotes = new ArrayList<>();
			for (int i = 0; i < message.length(); i++) {
				char c = Character.toLowerCase(message.charAt(i));
				if (reactions == 20)
					break;
				if (emotes.containsKey(c)) {
					List<String> possibleEmotes = emotes.get(c);
					for (String s : possibleEmotes)
						if (!usedEmotes.contains(s)) {
							targetMessage.addReaction(s).queue();
							usedEmotes.add(s);
							reactions++;
							break;
						}
				}
			}
		}
	}

	@Override
	public void privateMessage(MessageReceivedEvent event) {
		serverMessage(event);
	}

}
