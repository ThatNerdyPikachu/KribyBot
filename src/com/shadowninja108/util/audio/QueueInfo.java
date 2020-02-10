package com.shadowninja108.util.audio;

import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;

public class QueueInfo {
	public Member author;
	public MessageChannel origin;
	public AudioTrack track;
	public AudioPlaylist list;
	public QueueType type;

	public void makeClone() {
		if (track != null)
			track = track.makeClone();
	}

	public enum QueueType {
		TRACK, PLAYLIST;
	}
}
