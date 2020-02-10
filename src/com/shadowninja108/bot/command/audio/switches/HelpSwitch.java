package com.shadowninja108.bot.command.audio.switches;

import static com.shadowninja108.util.MessageUtil.sendMessage;

import java.util.Iterator;

import com.shadowninja108.bot.command.audio.AudioCommand;
import com.shadowninja108.translatable.Translatable;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class HelpSwitch implements AudioSwitch {

	@Override
	public String getSwitch() {
		return Translatable.get("audio.help.name");
	}

	@Override
	public String getDescription() {
		return Translatable.get("audio.help.description");
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args) {
		if (args.length > 0) {
			Iterator<AudioSwitch> switches = AudioCommand.switches.iterator();
			while (switches.hasNext()) {
				AudioSwitch sw = switches.next();
				if (sw.getSwitch().equals(args[0])) {
					sendMessage("```" + sw.getDescription() + "```", event.getChannel());
					break;
				}
			}
		} else
			sendMessage(Translatable.get("audio.help.no_input"), event.getChannel());
	}

}
