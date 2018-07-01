package com.shadowninja108.bot.shell.channel;

import com.shadowninja108.bot.shell.Selected;
import com.shadowninja108.bot.shell.Selected.selected;
import com.shadowninja108.bot.shell.ShellCommand;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import static com.shadowninja108.util.MessageUtil.*;

public class SendMessage extends ShellCommand {

	@Override
	public String getPrefix() {
		return "send";
	}

	@Override
	public String getDescription() {
		return "Sends message to selected text channel.";
	}

	@Override
	public Byte[] getParameterCount() {
		return null;
	}

	@Override
	public void execute(String[] args, Selected selected, MessageReceivedEvent event) {
		String message = event.getMessage().getContentRaw();
		message = message.substring(message.indexOf(" "));
		TextChannel channel = selected.getTextChannel();
		if (channel.canTalk())
			sendMessage(message, channel);
		else
			sendMessage("I can't talk in that channel.", event.getAuthor());
	}

	@Override
	public boolean meetsRequirements(selected selected) {
		return selected.equals(Selected.selected.TEXT_CHANNEL);
	}

}
