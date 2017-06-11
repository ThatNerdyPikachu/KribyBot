package com.shadowninja108.bot.command.audio.switches;

import static com.shadowninja108.util.MessageUtil.sendMessage;

import com.shadowninja108.bot.command.audio.AudioCommand;
import com.shadowninja108.translatable.Translatable;
import com.shadowninja108.util.audio.GuildMusicManager;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class VolumeSwitch implements AudioSwitch {

	@Override
	public String getSwitch() {
		return Translatable.get("audio.volume.name");
	}

	@Override
	public String getDescription() {
		return Translatable.get("audio.volume.description");
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args, AudioCommand command) {
		GuildMusicManager manager = command.getManager(event.getGuild());
		if (args.length > 0) {
			try {
				int vol = Integer.parseInt(args[0]);
				if (vol < 150 && vol > 0)
					manager.player.setVolume(vol);
				else
					sendMessage(Translatable.get("audio.volume.out_of_bounds"), event.getChannel());
			} catch (NumberFormatException e) {
				sendMessage(Translatable.get("global.invalid_number"), event.getChannel());
			}
		} else
			sendMessage(Translatable.get("audio.volume.display") + " " + manager.player.getVolume(),
					event.getChannel());
	}

}
