package com.shadowninja108.bot.shell.member;

import java.util.List;

import com.shadowninja108.bot.shell.Selected;
import com.shadowninja108.bot.shell.Selected.selected;
import com.shadowninja108.bot.shell.ShellCommand;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class GiveRole extends ShellCommand {

	@Override
	public String getPrefix() {
		return "give";
	}

	@Override
	public String getDescription() {
		return "Give role to a member.";
	}

	@Override
	public Byte[] getParameterCount() {
		return new Byte[] { 1 };
	}

	@Override
	public void execute(String[] args, Selected selected, MessageReceivedEvent event) {
		List<Role> roles = selected.getGuild().getRolesByName(args[0], true);
		selected.getGuild().modifyMemberRoles(selected.getMember(), roles.get(0)).complete();
	}

	@Override
	public boolean meetsRequirements(selected selected) {
		return selected.equals(Selected.selected.MEMBER);
	}

}
