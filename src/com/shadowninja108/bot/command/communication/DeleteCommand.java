package com.shadowninja108.bot.command.communication;

import static com.shadowninja108.util.MessageUtil.sendMessage;

import java.util.List;

import com.shadowninja108.bot.command.Command;
import com.shadowninja108.bot.shell.ShellProcessor;
import com.shadowninja108.translatable.Translatable;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class DeleteCommand implements Command {

	@Override
	public String getName() {
		return Translatable.get("delete.prefix");
	}

	@Override
	public String getDescription() {
		return Translatable.get("delete.description");
	}

	@Override
	public void serverMessage(MessageReceivedEvent event) {
		String message = event.getMessage().getContentDisplay();
		message = message.substring(message.indexOf(" "));
		String[] params = ShellProcessor.processString(message);
		int amount = 1;
		if (params.length > 0) {
			try {
				amount += Integer.parseInt(params[0]);
			} catch (NumberFormatException e) {
				sendMessage(Translatable.get("global.invalid_number"), event.getChannel());
				return;
			}
			List<Message> messages = event.getChannel().getHistory().retrievePast(amount).complete();
			messages.forEach((obj) -> obj.delete().queue());
		}

	}

	@Override
	public void privateMessage(MessageReceivedEvent event) {
		sendMessage(Translatable.get("delete.private_rejection"), event.getAuthor());
	}

}
