package com.shadowninja108.bot.command.audio.switches;

import static com.shadowninja108.util.MessageUtil.sendMessage;

import com.shadowninja108.translatable.Translatable;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class DisconnectSwitch implements AudioSwitch {

	@Override
	public String getSwitch() {
		return Translatable.get("audio.disconnect.name");
	}

	@Override
	public String getDescription() {
		return Translatable.get("audio.disconnect.description");
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args) {
		event.getGuild().getAudioManager().closeAudioConnection();
		sendMessage(Translatable.get("audio.disconnect.message"), event.getChannel());
	}

}
