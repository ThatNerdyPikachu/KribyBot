package com.shadowninja108.bot.command.audio.switches;

import static com.shadowninja108.util.MessageUtil.sendMessage;

import com.shadowninja108.bot.command.audio.AudioCommand;
import com.shadowninja108.translatable.Translatable;
import com.shadowninja108.util.audio.GuildMusicManager;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SkipToSwitch implements AudioSwitch {

	@Override
	public String getSwitch() {
		return Translatable.get("audio.skipto.name");
	}

	@Override
	public String getDescription() {
		return Translatable.get("audio.skipto.description");
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args, AudioCommand command) {
		GuildMusicManager manager = command.getManager(event.getGuild());
		String input = args[0];
		int split = input.indexOf(':');
		if (split > 0) {
			try {
				int min = Integer.parseInt(input.substring(0, split));
				int sec = Integer.parseInt(input.substring(split + 1));
				int total = (min * 60) + sec;
				if (manager.player.getPlayingTrack() != null)
					manager.player.getPlayingTrack().setPosition(total * 1000);
			} catch (NumberFormatException e) {
				sendMessage(Translatable.get("global.invalid_number"), event.getChannel());
			}
		} else
			sendMessage(Translatable.get("global.invalid_input"), event.getChannel());
	}

}
