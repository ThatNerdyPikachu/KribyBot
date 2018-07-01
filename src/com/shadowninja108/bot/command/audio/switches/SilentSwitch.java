package com.shadowninja108.bot.command.audio.switches;

import static com.shadowninja108.util.MessageUtil.sendMessage;

import com.shadowninja108.bot.command.CommandProcessor;
import com.shadowninja108.translatable.Translatable;
import com.shadowninja108.util.audio.TrackScheduler;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SilentSwitch implements AudioSwitch {

	@Override
	public String getSwitch() {
		return Translatable.get("audio.silent.name");
	}

	@Override
	public String getDescription() {
		return Translatable.get("audio.silent.description");
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args) {
		TrackScheduler scheduler = CommandProcessor.getGuildData(event.getGuild()).getMusicManager().scheduler;
		if (args.length > 0) {
			boolean input = Boolean.parseBoolean(args[0]);
			scheduler.silent = input;
		}
		if (scheduler.silent)
			sendMessage(Translatable.get("audio.silent.isSilent"), event.getChannel());
		else
			sendMessage(Translatable.get("audio.silent.notSilent"), event.getChannel());
	}

}
