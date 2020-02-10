package com.shadowninja108.bot.command.audio.switches;

import static com.shadowninja108.util.MessageUtil.sendMessage;

import java.util.Iterator;
import java.util.List;

import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioTrack;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeSearchProvider;
import com.sedmelluq.discord.lavaplayer.track.AudioItem;
import com.sedmelluq.discord.lavaplayer.track.AudioReference;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.BasicAudioPlaylist;
import com.shadowninja108.bot.command.CommandProcessor;
import com.shadowninja108.translatable.Translatable;
import com.shadowninja108.util.audio.GuildData;
import com.shadowninja108.util.audio.QueueInfo;
import com.shadowninja108.util.audio.QueueInfo.QueueType;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SearchSwitch implements AudioSwitch {
	@Override
	public String getSwitch() {
		return Translatable.get("audio.search.name");
	}

	@Override
	public String getDescription() {
		return Translatable.get("audio.search.description");
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args) {
		if (args.length > 0) {
			YoutubeAudioSourceManager ytManager = new YoutubeAudioSourceManager();
			YoutubeSearchProvider searchProvider = new YoutubeSearchProvider();
			GuildData audioData = CommandProcessor.getGuildData(event.getGuild());
			if (!getFromSearch(args[0], audioData, event)) {
				AudioItem item = searchProvider.loadSearchResult(args[0], (info) -> new YoutubeAudioTrack(info, ytManager));
				if (item != AudioReference.NO_TRACK) {
					audioData.lastSearch = (BasicAudioPlaylist) item;
					Iterator<AudioTrack> tracks = audioData.lastSearch.getTracks().iterator();
					StringBuilder builder = new StringBuilder();
					builder.append("```");
					int index = 0;
					while (tracks.hasNext()) {
						index++;
						AudioTrack track = tracks.next();
						builder.append(index);
						builder.append(". ");
						builder.append(track.getInfo().title);
						builder.append('\n');
					}
					builder.append("```");
					sendMessage(builder.toString(), event.getChannel());
				} else
					sendMessage(Translatable.get("audio.search.nothing_found"), event.getChannel());
			}
		} else
			sendMessage("Search what?", event.getChannel());
	}

	public boolean getFromSearch(String i, GuildData audioData, MessageReceivedEvent event) {
		if (audioData.lastSearch != null) {
			int index = -1;
			try {
				index = Integer.parseInt(i) - 1;
			} catch (NumberFormatException e) {
				return false;
			}
			if (index < 0) {
				sendMessage("You entered a number that is negative.", event.getChannel());
				return true;
			}
			QueueInfo info = new QueueInfo();
			List<AudioTrack> tracks = audioData.lastSearch.getTracks();
			if (index < tracks.size()) {
				AudioTrack track = tracks.get(index);
				info.author = event.getMember();
				info.origin = event.getChannel();
				info.track = track.makeClone();
				info.type = QueueType.TRACK;
				audioData.getMusicManager().scheduler.queue(info);
				sendMessage(Translatable.get("audio.general.queued_track") + " " + event.getAuthor().getAsMention(),
						event.getChannel());
				return true;
			} else {
				sendMessage("Entered number is greater than tracks found. (" + tracks.size() + ")", event.getChannel());
				return true;
			}
		} else
			return false;
	}
}
