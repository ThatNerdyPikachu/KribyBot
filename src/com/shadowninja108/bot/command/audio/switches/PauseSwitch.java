package com.shadowninja108.bot.command.audio.switches;

import com.shadowninja108.bot.command.CommandProcessor;
import com.shadowninja108.translatable.Translatable;
import com.shadowninja108.util.audio.GuildMusicManager;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PauseSwitch implements AudioSwitch {

	@Override
	public String getSwitch() {
		return Translatable.get("audio.pause.name");
	}

	@Override
	public String getDescription() {
		return Translatable.get("audio.pause.description");
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args) {
		GuildMusicManager manager = CommandProcessor.getGuildData(event.getGuild()).getMusicManager();
		manager.player.setPaused(true);
	}

}
