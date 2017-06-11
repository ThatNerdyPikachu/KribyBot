package com.shadowninja108.bot.command.audio.switches;

import com.shadowninja108.bot.command.audio.AudioCommand;
import com.shadowninja108.bot.command.audio.AudioResult;
import com.shadowninja108.translatable.Translatable;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import static com.shadowninja108.util.MessageUtil.*;

public class QueueSwitch implements AudioSwitch {

	@Override
	public String getSwitch() {
		return Translatable.get("audio.queue.name");
	}

	@Override
	public String getDescription() {
		return Translatable.get("audio.queue.description");
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args, AudioCommand command) {
		if (args.length > 0)
			command.playerManager.loadItem(args[0], new AudioResult(command.getManager(event.getGuild()), event));
		else
			sendMessage(Translatable.get("audio.queue.no_input"), event.getChannel());
	}

}
