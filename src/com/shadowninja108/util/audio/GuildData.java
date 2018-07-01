package com.shadowninja108.util.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.BasicAudioPlaylist;
import com.shadowninja108.util.setting.SettingsInstance;

import net.dv8tion.jda.core.entities.Guild;

public class GuildData {
	public Guild guild;
	public BasicAudioPlaylist lastSearch;
	public AudioPlayerManager playerManager;
	public int connectionAttempts;
	public SettingsInstance settings;

	private GuildMusicManager manager;

	public GuildData(Guild guild, AudioPlayerManager playerManager) {
		this.playerManager = playerManager;
		this.guild = guild;
		this.connectionAttempts = 0;
		this.settings = new SettingsInstance(guild);
	}

	public GuildMusicManager getMusicManager() {
		if (manager == null) {
			manager = new GuildMusicManager(playerManager);
			guild.getAudioManager().setSendingHandler(manager.getSendHandler());
		}
		return manager;
	}
	
}
