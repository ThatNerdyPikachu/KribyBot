package com.shadowninja108.bot.command.audio.switches;

import static com.shadowninja108.util.MessageUtil.sendMessage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeSearchProvider;
import com.sedmelluq.discord.lavaplayer.track.AudioItem;
import com.sedmelluq.discord.lavaplayer.track.AudioReference;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.BasicAudioPlaylist;
import com.shadowninja108.bot.command.audio.AudioCommand;
import com.shadowninja108.bot.command.audio.AudioResult;
import com.shadowninja108.translatable.Translatable;
import com.shadowninja108.util.audio.GuildMusicManager;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SearchSwitch implements AudioSwitch {

	Map<Long, BasicAudioPlaylist> lastSearch;

	public SearchSwitch() {
		lastSearch = new HashMap<>();
	}

	@Override
	public String getSwitch() {
		return Translatable.get("audio.search.name");
	}

	@Override
	public String getDescription() {
		return Translatable.get("audio.search.description");
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args, AudioCommand command) {
		GuildMusicManager manager = command.getManager(event.getGuild());
		YoutubeAudioSourceManager youtube_manager = new YoutubeAudioSourceManager();
		YoutubeSearchProvider searchProvider = new YoutubeSearchProvider(youtube_manager);
		try {
			int index = Integer.parseInt(args[0]) - 1;
			command.playerManager.loadItem(
					lastSearch.get(Long.parseLong(event.getGuild().getId())).getTracks().get(index).getIdentifier(),
					new AudioResult(manager, event));
		} catch (NumberFormatException | NullPointerException e) {
			AudioItem item = searchProvider.loadSearchResult(args[0]);
			if (item != AudioReference.NO_TRACK) {
				lastSearch.put(Long.parseLong(event.getGuild().getId()), (BasicAudioPlaylist) item);
				Iterator<AudioTrack> tracks = lastSearch.get(Long.parseLong(event.getGuild().getId())).getTracks()
						.iterator();
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
	}

}
