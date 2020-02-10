package com.shadowninja108.bot.shell.role;

import java.util.EnumSet;
import java.util.Iterator;

import com.shadowninja108.bot.shell.Selected;
import com.shadowninja108.bot.shell.Selected.selected;
import com.shadowninja108.bot.shell.ShellCommand;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.PermissionException;

import static com.shadowninja108.util.MessageUtil.*;

public class WriteToRole extends ShellCommand {

	@Override
	public String getPrefix() {
		return "write";
	}

	@Override
	public String getDescription() {
		return "Write raw permissions to role.";
	}

	@Override
	public Byte[] getParameterCount() {
		return new Byte[] { 1 };
	}

	@Override
	public void execute(String[] args, Selected selected, MessageReceivedEvent event) {
		long perms = Long.parseLong(args[0]);
		try {
			selected.getRole().getManager().setPermissions(perms).complete();
		} catch (PermissionException e) {
			sendMessage("Unable to write to role due to a permission error.", event.getChannel());
			return;
		}
		EnumSet<Permission> list = Permission.getPermissions(perms);
		Iterator<Permission> it = list.iterator();
		StringBuilder builder = new StringBuilder();
		builder.append("```");
		while (it.hasNext()) {
			Permission next = it.next();
			builder.append(next.getName() + '\n');
		}
		builder.append("```");
		sendMessage(builder.toString(), event.getChannel());
	}

	@Override
	public boolean meetsRequirements(selected selected) {
		return selected.equals(Selected.selected.ROLE);
	}

}
