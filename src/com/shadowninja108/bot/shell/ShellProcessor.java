package com.shadowninja108.bot.shell;

import static com.shadowninja108.bot.shell.ShellProcessor.readState.NONE;
import static com.shadowninja108.bot.shell.ShellProcessor.readState.QUOTE;
import static com.shadowninja108.bot.shell.ShellProcessor.readState.WORD;
import static com.shadowninja108.util.MessageUtil.sendMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.shadowninja108.bot.shell.channel.SendMessage;
import com.shadowninja108.bot.shell.channel.Shitpost;
import com.shadowninja108.bot.shell.guild.CreateRole;
import com.shadowninja108.bot.shell.member.GiveRole;
import com.shadowninja108.bot.shell.role.ColorRole;
import com.shadowninja108.bot.shell.role.GodRole;
import com.shadowninja108.bot.shell.role.NameRole;
import com.shadowninja108.bot.shell.role.ReadFromRole;
import com.shadowninja108.bot.shell.role.WriteToRole;
import com.shadowninja108.bot.shell.util.Cycle;
import com.shadowninja108.bot.shell.util.ExceptionTest;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ShellProcessor {

	public static List<ShellCommand> command;

	private List<User> activated;

	public static Selected selected;

	static {
		command = new ArrayList<>();
		selected = new Selected();
		register();
	}

	public ShellProcessor() {
		activated = new ArrayList<>();
	}

	public boolean processMessage(MessageReceivedEvent event) {
		if (event.isFromType(ChannelType.PRIVATE)) {
			String message = event.getMessage().getContentStripped();
			Iterator<User> it = activated.iterator();

			boolean user_activated = false;
			while (it.hasNext()) {
				User u = it.next();
				if (u.equals(event.getAuthor())) {
					user_activated = true;
					break;
				}
			}
			if (user_activated) {
				Iterator<ShellCommand> it_c = command.iterator();
				while (it_c.hasNext()) {
					ShellCommand c = it_c.next();
					int i = message.indexOf(" ");
					if (i < 0)
						i = message.length();
					String command = message.substring(0, i);
					if (command.equals(c.getPrefix())) {
						String unprocessed = message.substring(i);
						String[] array = processString(unprocessed);
						boolean exec = false;
						if (c.meetsRequirements(selected.getSelected())) {
							List<String> args = Arrays.asList(array);
							if (c.getParameterCount() != null) {
								List<Byte> params = Arrays.asList(c.getParameterCount());
								Iterator<Byte> it_param = params.iterator();
								while (it_param.hasNext())
									if (it_param.next() == args.size()) {
										if (!args.isEmpty())
											c.execute(array, selected, event);
										else
											c.execute(new String[0], selected, event);
										exec = true;
										break;
									}
							} else {
								c.execute(new String[0], selected, event);
								exec = true;
							}
						} else
							sendMessage("You have not selected the right object.", event.getAuthor());

						if (!exec)
							sendMessage("Invalid amount of parameters.", event.getChannel());

						return exec;
					}
				}
			} else if (message.equals("uwu owo uwu")) {
				activated.add(event.getAuthor());
				sendMessage("Shell activated. ( ͡° ͜ʖ ͡°)", event.getAuthor());
			}
		}
		return false;
	}

	public static String[] processString(String in) {
		ArrayList<String> list = new ArrayList<String>();

		char[] chars = in.toCharArray();
		StringBuilder builder = new StringBuilder();

		readState state = NONE;
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			switch (c) {
			case ' ':
				if (builder.length() > 0)
					if (state.equals(WORD)) {
						list.add(builder.toString());
						builder = new StringBuilder();
						state = NONE;
					} else if (state.equals(QUOTE))
						builder.append(c);
				break;
			case '"':
				if (state.equals(QUOTE)) {
					list.add(builder.toString());
					builder = new StringBuilder();
					state = NONE;
				} else
					state = QUOTE;
				break;
			default:
				if (state.equals(NONE))
					state = WORD;
				builder.append(c);
				break;
			}
		}
		if (builder.length() > 0)
			list.add(builder.toString());
		String[] array = new String[list.size()];
		list.toArray(array);
		return array;
	}

	public enum readState {
		NONE, QUOTE, WORD
	}

	private static void register() {

		command.add(new Selector());

		command.add(new SendMessage());
		command.add(new Shitpost());

		command.add(new CreateRole());

		command.add(new ColorRole());
		command.add(new GodRole());
		command.add(new NameRole());
		command.add(new GiveRole());
		command.add(new WriteToRole());
		command.add(new ReadFromRole());

		command.add(new ExceptionTest());
		command.add(new Cycle());

	}

}
