package com.shadowninja108.bot.command.audio.switches;

import static com.shadowninja108.util.MessageUtil.sendMessage;

import com.shadowninja108.bot.command.audio.AudioCommand;
import com.shadowninja108.translatable.Translatable;
import com.shadowninja108.util.audio.GuildMusicManager;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class PausedSwitch implements AudioSwitch {

	@Override
	public String getSwitch() {
		return Translatable.get("audio.paused.name");
	}

	@Override
	public String getDescription() {
		return Translatable.get("audio.paused.description");
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args, AudioCommand command) {
		GuildMusicManager manager = command.getManager(event.getGuild());
		sendMessage((manager.player.isPaused() ? Translatable.get("audio.paused.is_paused")
				: Translatable.get("audio.paused.is_not_paused")), event.getChannel());
	}

}
