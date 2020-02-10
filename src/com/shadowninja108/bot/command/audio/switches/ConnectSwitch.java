package com.shadowninja108.bot.command.audio.switches;

import static com.shadowninja108.util.MessageUtil.sendMessage;

import com.shadowninja108.translatable.Translatable;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.internal.utils.PermissionUtil;

public class ConnectSwitch implements AudioSwitch {

	@Override
	public String getSwitch() {
		return Translatable.get("audio.connect.name");
	}

	@Override
	public String getDescription() {
		return Translatable.get("audio.connect.description");
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args) {
		connectToUser(event.getGuild(), event.getMember(), event.getChannel());
	}

	private static void connectToUser(Guild guild, Member user, MessageChannel txtChannel) {
		AudioManager audioManager = guild.getAudioManager();
		VoiceChannel channel = user.getVoiceState().getChannel();
		if (!user.getVoiceState().inVoiceChannel()) {
			sendMessage("You are not in a voice channel.", txtChannel);
			return;
		}
		if (PermissionUtil.checkPermission(guild.getSelfMember(), Permission.VOICE_CONNECT)
				&& PermissionUtil.checkPermission(channel, guild.getSelfMember(), Permission.VOICE_CONNECT)) {
			if (audioManager.isConnected() || audioManager.isAttemptingToConnect()) {
				audioManager.closeAudioConnection();
				while (audioManager.isConnected()) {
				}
			}
			audioManager.openAudioConnection(channel);

			sendMessage("Joined " + "<#" + channel.getId() + ">! " + Translatable.get("audio.connect.joined"), txtChannel);
		} else {
			sendMessage("I don't have permission to connect!", txtChannel);
		}
	}
}
