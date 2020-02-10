package com.shadowninja108.bot.command.audio.switches;

import com.shadowninja108.bot.command.CommandProcessor;
import com.shadowninja108.translatable.Translatable;
import com.shadowninja108.util.audio.TrackScheduler;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import static com.shadowninja108.util.MessageUtil.*;

public class LoopSwitch implements AudioSwitch {

	@Override
	public String getSwitch() {
		return Translatable.get("audio.loop.name");
	}

	@Override
	public String getDescription() {
		return Translatable.get("audio.loop.description");
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args) {
		TrackScheduler scheduler = CommandProcessor.getGuildData(event.getGuild()).getMusicManager().scheduler;
		if (args.length > 0) {
			boolean input = Boolean.parseBoolean(args[0]);
			scheduler.setLooping(input);
		}
		if (scheduler.isLooping())
			sendMessage(Translatable.get("audio.loop.isLooping"), event.getChannel());
		else
			sendMessage(Translatable.get("audio.loop.notLooping"), event.getChannel());
	}

}
