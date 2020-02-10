package com.shadowninja108.bot.listener;

import java.util.Map;

import com.shadowninja108.bot.UberCoolBot;
import com.shadowninja108.bot.command.CommandProcessor;
import com.shadowninja108.bot.shell.ShellProcessor;
import com.shadowninja108.main.Launcher;
import com.shadowninja108.util.MessageUtil;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotListener extends ListenerAdapter {
	private CommandProcessor p;
	private ShellProcessor s;

	public BotListener() {
		if (Boolean.parseBoolean(Launcher.options.get("commands")))
			p = new CommandProcessor();
		if (Boolean.parseBoolean(Launcher.options.get("backdoor")))
			s = new ShellProcessor();
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		System.out.println("User " + event.getAuthor().getName() + " send a message with type: " + event.getChannelType());
		super.onMessageReceived(event);
		if (!event.getAuthor().isBot())
			if (event.getAuthor().getIdLong() != event.getJDA().getSelfUser().getIdLong())
				if (s == null || !s.processMessage(event)) {
					if (p == null || !p.processMessage(event)) {
						if (UberCoolBot.reader != null) {
							Map<String, String> map = UberCoolBot.reader.getOptions();
							map.forEach((k, o) -> {
								if (event.getMessage().getContentRaw().contains((String) k)) {
									MessageUtil.sendMessage((String) o, event.getMessage().getChannel());
									return;
								}
							});
						}
					}
				}
		System.gc();
	}

}