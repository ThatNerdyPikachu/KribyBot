package com.shadowninja108.bot.listener;

import java.util.Map;

import com.shadowninja108.bot.UberCoolBot;
import com.shadowninja108.bot.command.CommandProcessor;
import com.shadowninja108.bot.shell.ShellProcessor;
import com.shadowninja108.main.Launcher;
import com.shadowninja108.util.MessageUtil;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class BotListener extends ListenerAdapter {
	private CommandProcessor p;
	private ShellProcessor s;

	public BotListener() {
		p = new CommandProcessor();
		if (Boolean.parseBoolean(Launcher.options.get("backdoor")))
			s = new ShellProcessor();
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		Launcher.factory.getLogger("main")
				.info("User " + event.getAuthor().getName() + " send a message with type: " + event.getChannelType());
		super.onMessageReceived(event);
		if (!event.getAuthor().isBot())
			if (event.getAuthor() != event.getJDA().getSelfUser())
				if (s == null || !s.processMessage(event)) {
					if (!p.messageRecieved(event)) {
						if (UberCoolBot.reader != null) {
							Map<String, String> map = UberCoolBot.reader.getOptions();
							map.forEach((k, o) -> {
								if (event.getMessage().getContent().contains((String) k)) {
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