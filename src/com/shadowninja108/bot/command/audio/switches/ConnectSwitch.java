package com.shadowninja108.bot.command.audio.switches;

import static com.shadowninja108.util.MessageUtil.sendMessage;

import com.shadowninja108.bot.command.audio.AudioCommand;
import com.shadowninja108.translatable.Translatable;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;

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
	public void execute(MessageReceivedEvent event, String[] args, AudioCommand command) {
		VoiceChannel v = connectToUser(event.getGuild().getAudioManager(), event.getMember());
		if (v != null) {
			sendMessage("Joined " + v.getName() + "! " + Translatable.get("audio.connect.joined"), event.getChannel());
		} else
			sendMessage(Translatable.get("audio.connect.error"), event.getChannel());
	}

	private static VoiceChannel connectToUser(AudioManager audioManager, Member user) {
		if (user.getGuild().getSelfMember().hasPermission(Permission.VOICE_CONNECT))
			if (!audioManager.isAttemptingToConnect()) {
				if (audioManager.isConnected())
					audioManager.closeAudioConnection();
				VoiceChannel channel = user.getVoiceState().getChannel();
				if (channel != null)
					audioManager.openAudioConnection(channel);
				return channel;
			} else
				return null;
		else
			return null;
	}
}
