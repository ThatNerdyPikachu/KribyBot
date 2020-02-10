package com.shadowninja108.bot.shell.channel;

import static com.shadowninja108.util.MessageUtil.sendMessage;

import com.shadowninja108.bot.shell.Selected;
import com.shadowninja108.bot.shell.Selected.selected;
import com.shadowninja108.bot.shell.ShellCommand;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Shitpost extends ShellCommand {

	@Override
	public String getPrefix() {
		return "shitpost";
	}

	@Override
	public String getDescription() {
		return "Be efficient.";
	}

	@Override
	public Byte[] getParameterCount() {
		return new Byte[] { 2 };
	}

	@Override
	public void execute(String[] args, Selected selected, MessageReceivedEvent event) {
		for (int i = 0; i < Integer.parseInt(args[0]); i++)
			sendMessage(args[1], selected.getTextChannel());
	}

	@Override
	public boolean meetsRequirements(selected selected) {
		return selected.equals(Selected.selected.TEXT_CHANNEL);
	}

}
