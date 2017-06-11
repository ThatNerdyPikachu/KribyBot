package com.shadowninja108.bot.shell.role;

import java.awt.Color;
import static com.shadowninja108.util.MessageUtil.*;

import com.shadowninja108.bot.shell.Selected;
import com.shadowninja108.bot.shell.Selected.selected;
import com.shadowninja108.bot.shell.ShellCommand;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.RoleManager;

public class ColorRole extends ShellCommand {

	@Override
	public String getPrefix() {
		return "color";
	}

	@Override
	public String getDescription() {
		return "Changes the color of the selected role.";
	}

	@Override
	public Byte[] getParameterCount() {
		return new Byte[] { 1 };
	}

	@Override
	public void execute(String[] args, Selected selected, MessageReceivedEvent event) {
		RoleManager manager = selected.getRole().getManager();
		switch (args[0]) {
		case "red":
			manager.setColor(Color.red).complete();
			break;
		case "blue":
			manager.setColor(Color.blue).complete();
			break;
		case "green":
			manager.setColor(Color.green).complete();
			break;
		case "orange":
			manager.setColor(Color.orange).complete();
			break;
		case "yellow":
			manager.setColor(Color.yellow).complete();
			break;
		case "violet":
			manager.setColor(new Color(127, 0, 255)).complete();
			break;
		case "dank":
			manager.setColor(new Color(54, 104, 1)).complete();
			break;
		default:
			try {
				manager.setColor(Color.decode(args[0])).complete();
			} catch (NumberFormatException e) {
				sendMessage("That's no color!", event.getChannel());
			}
			break;
		}
	}

	@Override
	public boolean meetsRequirements(selected select) {
		return select.equals(selected.ROLE);
	}

}
