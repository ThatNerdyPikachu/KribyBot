package com.shadowninja108.bot.shell.role;

import com.shadowninja108.bot.shell.Selected;
import static com.shadowninja108.util.MessageUtil.*;
import com.shadowninja108.bot.shell.Selected.selected;
import com.shadowninja108.bot.shell.ShellCommand;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class NameRole extends ShellCommand {

	@Override
	public String getPrefix() {
		return "name";
	}

	@Override
	public String getDescription() {
		return "Name selected role.";
	}

	@Override
	public Byte[] getParameterCount() {
		return new Byte[] { 1 };
	}

	@Override
	public void execute(String[] args, Selected selected, MessageReceivedEvent event) {
		selected.getRole().getManager().setName(args[0]);
		sendMessage("Done.", event.getAuthor());
	}

	@Override
	public boolean meetsRequirements(selected select) {
		return select.equals(selected.ROLE);
	}

}
