package com.shadowninja108.bot.command.audio;

import static com.shadowninja108.util.MessageUtil.sendMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.shadowninja108.bot.command.Command;
import com.shadowninja108.bot.command.audio.switches.AudioSwitch;
import com.shadowninja108.bot.command.audio.switches.ClearSwitch;
import com.shadowninja108.bot.command.audio.switches.CommandListSwitch;
import com.shadowninja108.bot.command.audio.switches.ConnectSwitch;
import com.shadowninja108.bot.command.audio.switches.DisconnectSwitch;
import com.shadowninja108.bot.command.audio.switches.HelpSwitch;
import com.shadowninja108.bot.command.audio.switches.ListSwitch;
import com.shadowninja108.bot.command.audio.switches.PauseSwitch;
import com.shadowninja108.bot.command.audio.switches.PausedSwitch;
import com.shadowninja108.bot.command.audio.switches.PlaySwitch;
import com.shadowninja108.bot.command.audio.switches.QueueSwitch;
import com.shadowninja108.bot.command.audio.switches.SearchSwitch;
import com.shadowninja108.bot.command.audio.switches.SkipSwitch;
import com.shadowninja108.bot.command.audio.switches.SkipToSwitch;
import com.shadowninja108.bot.command.audio.switches.VolumeSwitch;
import com.shadowninja108.bot.shell.ShellProcessor;
import com.shadowninja108.translatable.Translatable;
import com.shadowninja108.util.audio.GuildMusicManager;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class AudioCommand implements Command {

	public AudioPlayerManager playerManager;
	Map<Long, GuildMusicManager> managers;

	public static List<AudioSwitch> switches;

	static {
		switches = new ArrayList<>();
		register();
	}

	public AudioCommand() {
		managers = new HashMap<>();

		playerManager = new DefaultAudioPlayerManager();
		AudioSourceManagers.registerRemoteSources(playerManager);
		AudioSourceManagers.registerLocalSource(playerManager);
	}

	private static void register() {
		switches.add(new ClearSwitch());
		switches.add(new CommandListSwitch());
		switches.add(new ConnectSwitch());
		switches.add(new DisconnectSwitch());
		switches.add(new ListSwitch());
		switches.add(new PausedSwitch());
		switches.add(new PauseSwitch());
		switches.add(new PlaySwitch());
		switches.add(new QueueSwitch());
		switches.add(new SearchSwitch());
		switches.add(new SkipSwitch());
		switches.add(new SkipToSwitch());
		switches.add(new VolumeSwitch());
		switches.add(new HelpSwitch());
	}

	@Override
	public String getName() {
		return Translatable.get("audio.prefix");
	}

	@Override
	public String getDescription() {
		return Translatable.get("audio.description");
	}

	@Override
	public void serverMessage(MessageReceivedEvent event) {
		String content = event.getMessage().getContent();
		int i = content.indexOf(" ");
		if (i > 0) {
			String audio_switch = content.split(" ")[1]; // yea im lazy and im
															// not afraid to
															// show it
			i = content.indexOf(" ", i + 1);

			String args = "";
			if (i > 0) {
				args = content.substring(i);
			}
			String[] params = ShellProcessor.processString(args);
			Iterator<AudioSwitch> it = switches.iterator();
			boolean success = false;
			while (it.hasNext()) {
				AudioSwitch sw = it.next();
				if (sw.getSwitch().equals(audio_switch)) {
					sw.execute(event, params, this);
					success = true;
					break;
				}
			}
			if (!success)
				sendMessage(Translatable.get("audio.invalid_switch"), event.getChannel());
		}
	}

	public GuildMusicManager getManager(Guild guild) {
		long guildId = Long.parseLong(guild.getId());
		GuildMusicManager musicManager = managers.get(guildId);

		if (musicManager == null) {
			musicManager = new GuildMusicManager(playerManager);
			managers.put(guildId, musicManager);
		}

		guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

		return musicManager;
	}

	@Override
	public void privateMessage(MessageReceivedEvent event) {
		sendMessage(Translatable.get("audio.private_rejection"), event.getAuthor());
	}
}
