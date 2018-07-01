package com.shadowninja108.util.audio;

import static com.shadowninja108.util.MessageUtil.sendMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import com.shadowninja108.translatable.Translatable;
import com.shadowninja108.util.audio.QueueInfo.QueueType;

public class TrackScheduler extends AudioEventAdapter {
	private final AudioPlayer player;
	private final BlockingQueue<QueueInfo> queue;
	private boolean loop = false;

	public boolean silent = false;
	public QueueInfo current;

	/**
	 * @param player
	 *            The audio player this scheduler uses
	 */
	public TrackScheduler(AudioPlayer player) {
		this.player = player;
		this.queue = new LinkedBlockingQueue<>();
	}

	/**
	 * Add the next track to queue or play right away if nothing is in the
	 * queue.
	 *
	 * @param info
	 *            The track to play or add to queue.
	 */
	public void queue(QueueInfo info) {
		switch (info.type) {
		case PLAYLIST:
			Iterator<AudioTrack> it = info.list.getTracks().iterator();
			while (it.hasNext()) {
				QueueInfo track = new QueueInfo();
				track.author = info.author;
				track.origin = info.origin;
				track.track = it.next();
				track.type = QueueType.TRACK;
				queue(track);
			}
			break;
		case TRACK:
			if (!player.startTrack(info.track, true))
				queue.offer(info);
			else
				current = info;
			break;
		}
	}

	public Iterator<QueueInfo> iterator() {
		return queue.iterator();
	}
	
	public Collection<QueueInfo> getList(){
		List<QueueInfo> list = new ArrayList<>();	
		queue.forEach(info -> {
			list.add(info);
		});
		return Collections.unmodifiableCollection(list);
	}

	/**
	 * Start the next track, stopping the current one if it is playing.
	 */
	public void nextTrack() {
		if (isLooping()) {
			current.makeClone();
			queue.offer(current);
		}
		QueueInfo info = queue.poll();
		boolean firstPlay = current == null;
		this.current = info;
		if (current != null) {
			player.startTrack(current.track, false);
			if (!silent)
				if (!firstPlay) {
					AudioTrackInfo trackInfo = current.track.getInfo();
					StringBuilder builder = new StringBuilder();
					builder.append(Translatable.get("audio.general.now_playing"));
					builder.append(" ");
					if (trackInfo.title != null)
						builder.append(trackInfo.title);
					else
						builder.append(Translatable.get("audio.general.unknown_title"));
					builder.append("\n");
					builder.append(Translatable.get("audio.general.request_by"));
					builder.append(info.author.getAsMention());
					sendMessage(builder.toString(), info.origin);
				}
		} else
			player.startTrack(null, false);
	}

	public void clear() {
		queue.clear();
	}

	@Override
	public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
		if (endReason.mayStartNext)
			nextTrack();
	}

	public int size() {
		return queue.size();
	}

	public boolean isLooping() {
		return loop;
	}

	public void setLooping(boolean loop) {
		this.loop = loop;
	}

}