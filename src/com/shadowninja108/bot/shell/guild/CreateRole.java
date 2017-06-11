package com.shadowninja108.bot.shell.guild;

import com.shadowninja108.bot.shell.Selected;
import com.shadowninja108.bot.shell.Selected.selected;
import com.shadowninja108.bot.shell.ShellCommand;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CreateRole extends ShellCommand {

	@Override
	public String getPrefix() {
		return "create";
	}

	@Override
	public String getDescription() {
		return "Creates role.";
	}

	@Override
	public Byte[] getParameterCount() {
		return new Byte[] { 0 };
	}

	@Override
	public void execute(String[] args, Selected selected, MessageReceivedEvent event) {
		selected.getGuild().getController().createRole().complete();
	}

	@Override
	public boolean meetsRequirements(selected select) {
		return select.equals(selected.GUILD);
	}
}
