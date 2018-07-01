package com.shadowninja108.bot.command.audio.switches;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public interface AudioSwitch {
	public String getSwitch();

	public String getDescription();

	public void execute(MessageReceivedEvent event, String[] args);
}
