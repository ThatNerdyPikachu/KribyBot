package com.shadowninja108.bot.shell.role;

import static com.shadowninja108.util.MessageUtil.sendMessage;

import java.util.EnumSet;

import com.shadowninja108.bot.shell.Selected;
import com.shadowninja108.bot.shell.Selected.selected;
import com.shadowninja108.bot.shell.ShellCommand;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.RoleManager;

public class GodRole extends ShellCommand {

	@Override
	public String getPrefix() {
		return "god";
	}

	@Override
	public String getDescription() {
		return "Gives given role ultimate powers.";
	}

	@Override
	public Byte[] getParameterCount() {
		return new Byte[] { 0 };
	}

	@Override
	public void execute(String[] args, Selected selected, MessageReceivedEvent event) {
		Member bot = selected.getGuild().getSelfMember();
		EnumSet<Permission> perms = bot.getPermissions();

		if (bot.canInteract(selected.getRole())) {
			switch (selected.getSelected()) {
			case MEMBER:
				RoleManager member_manager = selected.getMember().getRoles().get(0).getManager();
				member_manager.givePermissions(perms).complete();
				break;
			case ROLE:
				RoleManager role_manager = selected.getRole().getManager();
				role_manager.givePermissions(perms).complete();
				break;
			default:
				sendMessage("How did you do this?", event.getAuthor());
				break;
			}
		} else {
			sendMessage("Cannot interact with that role.", event.getAuthor());
			return;
		}
		sendMessage("Done.", event.getAuthor());

	}

	@Override
	public boolean meetsRequirements(selected select) {
		switch (select) {
		case GUILD:
			return true;
		case MEMBER:
			return true;
		case ROLE:
			return true;
		default:
			return false;
		}
	}

}
