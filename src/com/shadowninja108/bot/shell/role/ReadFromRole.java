package com.shadowninja108.bot.shell.role;

import static com.shadowninja108.util.MessageUtil.sendMessage;

import java.util.EnumSet;
import java.util.Iterator;

import com.shadowninja108.bot.shell.Selected;
import com.shadowninja108.bot.shell.Selected.selected;
import com.shadowninja108.bot.shell.ShellCommand;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ReadFromRole extends ShellCommand {

	@Override
	public String getPrefix() {
		return "read";
	}

	@Override
	public String getDescription() {
		return "Reads permissions from role.";
	}

	@Override
	public Byte[] getParameterCount() {
		return new Byte[] {0};
	}

	@Override
	public void execute(String[] args, Selected selected, MessageReceivedEvent event) {
		EnumSet<Permission> list = selected.getRole().getPermissions();
		Iterator<Permission> it = list.iterator();
		StringBuilder builder = new StringBuilder("```Permissions: \n");
		while (it.hasNext()) {
			Permission next = it.next();
			builder.append(next.getName());
			builder.append('\n');
		}
		builder.append("```");
		sendMessage(builder.toString(), event.getChannel());
	}

	@Override
	public boolean meetsRequirements(selected selected) {
		return selected.equals(Selected.selected.ROLE);
	}

}
