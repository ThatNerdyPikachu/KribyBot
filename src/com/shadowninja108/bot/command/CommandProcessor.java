package com.shadowninja108.bot.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.shadowninja108.bot.command.audio.AudioCommand;
import com.shadowninja108.bot.command.basic.YoCommand;
import com.shadowninja108.bot.command.communication.DeleteCommand;
import com.shadowninja108.bot.command.communication.SayCommand;
import com.shadowninja108.bot.command.communication.SayDiscreteCommand;
import com.shadowninja108.bot.command.communication.YodaCommand;
import com.shadowninja108.bot.command.util.GTFOCommand;
import com.shadowninja108.bot.command.util.HelpCommand;
import com.shadowninja108.main.Launcher;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CommandProcessor {

	public static List<Command> commands;

	static {
		commands = new ArrayList<>();
		register();
	}

	public static void register() {
		commands.add(new AudioCommand());

		commands.add(new YoCommand());

		commands.add(new HelpCommand());
		commands.add(new GTFOCommand());

		commands.add(new SayCommand());
		commands.add(new SayDiscreteCommand());
		commands.add(new DeleteCommand());
		commands.add(new YodaCommand());
	}

	public boolean messageRecieved(MessageReceivedEvent event) {
		String message = event.getMessage().getContent();
		if (message.startsWith(Launcher.getPrefix())) {
			String command = event.getMessage().getContent();
			int i = message.indexOf(" ");
			if (i < 0)
				i = message.length();
			command = command.substring(Launcher.getPrefix().length(), i);
			Iterator<Command> it = commands.iterator();
			while (it.hasNext()) {
				Command com = it.next();
				if (command.equals(com.getName()))
					switch (event.getChannelType()) {
					case PRIVATE:
						com.privateMessage(event);
						return true;
					case TEXT:
						com.serverMessage(event);
						return true;
					default:
						System.out.println("Unsupported opperation: " + event.getChannelType().toString());
					}
			}
		}
		return false;
	}
}
