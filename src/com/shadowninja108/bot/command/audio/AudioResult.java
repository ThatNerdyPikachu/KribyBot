package com.shadowninja108.bot.command.audio;

import static com.shadowninja108.util.MessageUtil.sendMessage;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.shadowninja108.translatable.Translatable;
import com.shadowninja108.util.audio.GuildMusicManager;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class AudioResult implements AudioLoadResultHandler {

	MessageReceivedEvent event;
	GuildMusicManager manager;

	public AudioResult(GuildMusicManager manager, MessageReceivedEvent event) {
		this.event = event;
		this.manager = manager;
	}

	@Override
	public void trackLoaded(AudioTrack track) {
		manager.scheduler.queue(track);
		sendMessage(Translatable.get("audio.general.queued_track") + " " + event.getAuthor().getAsMention(),
				event.getChannel());
	}

	@Override
	public void playlistLoaded(AudioPlaylist playlist) {
		manager.scheduler.queue(playlist);
		sendMessage(Translatable.get("audio.general.queued_playlist") + " " + event.getAuthor().getAsMention(),
				event.getChannel());
	}

	@Override
	public void noMatches() {
		sendMessage(Translatable.get("audio.general.invalid_input"), event.getChannel());
	}

	@Override
	public void loadFailed(FriendlyException exception) {
		sendMessage(Translatable.get("audio.general.error_occured") + "\n" + exception.getMessage(),
				event.getChannel());
	}
}
