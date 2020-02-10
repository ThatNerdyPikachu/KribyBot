package com.shadowninja108.bot.command.audio.switches;

import static com.shadowninja108.util.MessageUtil.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;

import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackState;
import com.shadowninja108.bot.command.CommandProcessor;
import com.shadowninja108.translatable.Translatable;
import com.shadowninja108.util.audio.GuildMusicManager;
import com.shadowninja108.util.audio.QueueInfo;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class InfoSwitch implements AudioSwitch {

	@Override
	public String getSwitch() {
		return Translatable.get("audio.info.name");
	}

	@Override
	public String getDescription() {
		return Translatable.get("audio.info.description");
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args) {
		GuildMusicManager manager = CommandProcessor.getGuildData(event.getGuild()).getMusicManager();
		QueueInfo info = manager.scheduler.current;
		if (info != null) {
			EmbedBuilder builder = new EmbedBuilder();
			AudioTrackInfo trackInfo = manager.player.getPlayingTrack().getInfo();
			String uri = trackInfo.uri;

			if (trackInfo.title != null)
				builder.setTitle(trackInfo.title);

			if (trackInfo.author != null)
				builder.setAuthor(trackInfo.author, uri, event.getJDA().getSelfUser().getAvatarUrl());

			if (info.track.getState() == AudioTrackState.PLAYING) {
				String formattedProgress = DurationFormatUtils.formatDuration(manager.player.getPlayingTrack().getPosition(), "HH:mm:ss");
				builder.addField("Progress", formattedProgress, true);
			} else
				builder.addField("Progress", StringUtils.capitalize(info.track.getState().toString().toLowerCase()),
						true);

			String formattedLength = DurationFormatUtils.formatDuration(trackInfo.length, "HH:mm:ss");
			builder.addField("Length", formattedLength, true);

			builder.setFooter("Queued by: " + info.author.getEffectiveName(), info.author.getUser().getAvatarUrl());

			if (uri.contains("youtube")) {
				final String prefix = "https://www.youtube.com/watch?v=";
				String id = uri.substring(prefix.length());
				final String base_url = "https://img.youtube.com/vi/VIDEO_ID/maxresdefault.jpg";
				String thumbnail = base_url.replace("VIDEO_ID", id);
				builder.setThumbnail(thumbnail);
			}

			sendEmbed(builder.build(), event.getChannel());
		} else
			sendMessage("Nothing to display.", event.getChannel());
	}

}
