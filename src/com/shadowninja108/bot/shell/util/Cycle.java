package com.shadowninja108.bot.shell.util;

import com.shadowninja108.bot.shell.Selected;
import com.shadowninja108.bot.shell.Selected.selected;
import com.shadowninja108.bot.shell.ShellCommand;
import com.shadowninja108.bot.shell.ShellProcessor;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Cycle extends ShellCommand {

	@Override
	public String getPrefix() {
		return "cycle";
	}

	@Override
	public String getDescription() {
		return "prank'd.";
	}

	@Override
	public Byte[] getParameterCount() {
		return new Byte[] { 1 };
	}

	@Override
	public void execute(String[] args, Selected selected, MessageReceivedEvent event) {
		String[] split = ShellProcessor.processString(args[0]);
		for (int i = 0; i < split.length; i++) {
			selected.getGuild().modifyNickname(selected.getMember(), split[i]).complete();
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean meetsRequirements(selected selected) {
		return selected.equals(Selected.selected.MEMBER);
	}

}
