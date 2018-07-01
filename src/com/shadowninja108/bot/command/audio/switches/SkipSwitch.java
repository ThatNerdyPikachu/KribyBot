package com.shadowninja108.bot.command.audio.switches;

import com.shadowninja108.bot.command.CommandProcessor;
import com.shadowninja108.translatable.Translatable;

import static com.shadowninja108.util.MessageUtil.*;
import com.shadowninja108.util.audio.GuildMusicManager;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SkipSwitch implements AudioSwitch {

	@Override
	public String getSwitch() {
		return Translatable.get("audio.skip.name");
	}

	@Override
	public String getDescription() {
		return Translatable.get("audio.skip.description");
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args) {
		GuildMusicManager manager = CommandProcessor.getGuildData(event.getGuild()).getMusicManager();
		int skips = 1;
		if (args.length > 0)
			try {
				skips = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				sendMessage(Translatable.get("global.invalid_number"), event.getChannel());
				return;
			}
		for (int i = 0; i < skips; i++)
			manager.scheduler.nextTrack();

		manager.player.setPaused(false);
	}

}
