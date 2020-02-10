package com.shadowninja108.bot.command.basic;

import com.shadowninja108.bot.command.Command;
import com.shadowninja108.util.MessageUtil;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class FlipCommand implements Command {

	@Override
	public String getName() {
		return "flip";
	}

	@Override
	public String getDescription() {
		return "Flips a coin.";
	}

	@Override
	public void serverMessage(MessageReceivedEvent event) {
		int num = (int)(Math.random() * 2);
		MessageUtil.sendMessage((num == 0) ? "Tails." : "Heads.", event.getChannel());
	}

	@Override
	public void privateMessage(MessageReceivedEvent event) {
		serverMessage(event);
	}

}
