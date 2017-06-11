package com.shadowninja108.bot.command.audio.switches;

import static com.shadowninja108.util.MessageUtil.sendMessage;

import java.util.Iterator;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.shadowninja108.bot.command.audio.AudioCommand;
import com.shadowninja108.translatable.Translatable;
import com.shadowninja108.util.audio.GuildMusicManager;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ListSwitch implements AudioSwitch {

	@Override
	public String getSwitch() {
		return Translatable.get("audio.list.name");
	}

	@Override
	public String getDescription() {
		return Translatable.get("audio.list.description");
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args, AudioCommand command) {
		GuildMusicManager manager = command.getManager(event.getGuild());
		Iterator<AudioTrack> it = manager.scheduler.iterator();
		StringBuilder builder = new StringBuilder();
		builder.append("```");
		AudioTrack current = manager.player.getPlayingTrack();
		if (current != null) {
			builder.append("\n");
			builder.append(Translatable.get("audio.list.current_prefix"));
			builder.append(" ");
			builder.append(current.getInfo().title);
			builder.append(" - ");
			builder.append(current.getInfo().author);
		} else
			builder.append(Translatable.get("audio.list.nothing_queued"));
		int t = 0;
		int end = Math.min(10, manager.scheduler.size());
		if (args.length > 1) {
			try {
				int page = Integer.parseInt(args[1]) - 1;
				t = page * 10;
				end = t + 10;
				for (int j = 0; j < t; j++)
					it.next();
			} catch (NumberFormatException e) {
				sendMessage(Translatable.get("global.invalid_number"), event.getChannel());
				return;
			}
		}
		while (it.hasNext()) {
			t++;
			if (t > end)
				break;
			AudioTrack track = it.next();
			builder.append("\n");
			builder.append(t);
			builder.append(". ");
			builder.append(track.getInfo().title);
			builder.append(" - ");
			builder.append(track.getInfo().author);
		}
		builder.append("\n```");
		sendMessage(builder.toString(), event.getChannel());
	}

}
