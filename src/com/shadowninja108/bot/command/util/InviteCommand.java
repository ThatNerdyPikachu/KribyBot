package com.shadowninja108.bot.command.util;

import static com.shadowninja108.util.MessageUtil.sendMessage;

import com.shadowninja108.bot.command.Command;
import com.shadowninja108.main.Launcher;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class InviteCommand implements Command {

	@Override
	public String getName() {
		return "invite";
	}

	@Override
	public String getDescription() {
		return "Responds with the URL to invite Kriby to another server.";
	}

	@Override
	public void serverMessage(MessageReceivedEvent event) {
		JDA jda = event.getJDA();
		if (jda.getAccountType() == AccountType.BOT) {
			sendMessage("This bot was created by: " + Launcher.author_mentionable + "!\n" +jda.asBot().getInviteUrl(Permission.getPermissions(2146958591)), event.getChannel());
		} else
			sendMessage("I am not a bot!", event.getChannel());
	}

	@Override
	public void privateMessage(MessageReceivedEvent event) {
		serverMessage(event);
	}

}
