package com.shadowninja108.bot.command.audio.switches;

import static com.shadowninja108.util.MessageUtil.sendMessage;

import java.util.Collection;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.shadowninja108.bot.command.CommandProcessor;
import com.shadowninja108.translatable.Translatable;
import com.shadowninja108.util.audio.GuildMusicManager;
import com.shadowninja108.util.audio.QueueInfo;

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
	public void execute(MessageReceivedEvent event, String[] args) {
		GuildMusicManager manager = CommandProcessor.getGuildData(event.getGuild()).getMusicManager();
		Collection<QueueInfo> list = manager.scheduler.getList();
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
		if (!list.isEmpty()) {
			int start;
			int end;
			final int pageSize = 10;
			if (args.length > 0) {
				try {
					int in = Integer.parseInt(args[0])-1;
					start = in * pageSize;
					end = start + pageSize;
				} catch (NumberFormatException e) {
					sendMessage("That's no number!", event.getChannel());
					return;
				}
			} else {
				start = 0;
				end = pageSize;
			}
			
			start = Math.min(start, list.size());
			end = Math.min(end, list.size());
			if (start == end) {
				start = 0;
				end = pageSize;
			}
			for (int i = start; i < end; i++) {
				AudioTrack track = ((QueueInfo) list.toArray()[i]).track;
				builder.append("\n");
				builder.append(i + 1);
				builder.append(". ");
				builder.append(track.getInfo().title);
				builder.append(" - ");
				builder.append(track.getInfo().author);
			}
			if (manager.scheduler.isLooping())
				builder.append("\n...");
			builder.append("\n(");
			builder.append(1+(start / pageSize));
			builder.append("/");
			builder.append(1+(int)Math.ceil(list.size()/pageSize));
			builder.append(")");
		}
		builder.append("\n```");
		sendMessage(builder.toString(), event.getChannel());
	}
}
