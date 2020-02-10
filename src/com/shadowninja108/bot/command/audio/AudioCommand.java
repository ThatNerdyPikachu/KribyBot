package com.shadowninja108.bot.command.audio;

import static com.shadowninja108.util.MessageUtil.sendMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.shadowninja108.bot.command.Command;
import com.shadowninja108.bot.command.audio.switches.AudioSwitch;
import com.shadowninja108.bot.command.audio.switches.ClearSwitch;
import com.shadowninja108.bot.command.audio.switches.CommandListSwitch;
import com.shadowninja108.bot.command.audio.switches.ConnectSwitch;
import com.shadowninja108.bot.command.audio.switches.DisconnectSwitch;
import com.shadowninja108.bot.command.audio.switches.HelpSwitch;
import com.shadowninja108.bot.command.audio.switches.InfoSwitch;
import com.shadowninja108.bot.command.audio.switches.ListSwitch;
import com.shadowninja108.bot.command.audio.switches.LoopSwitch;
import com.shadowninja108.bot.command.audio.switches.PauseSwitch;
import com.shadowninja108.bot.command.audio.switches.PausedSwitch;
import com.shadowninja108.bot.command.audio.switches.PlaySwitch;
import com.shadowninja108.bot.command.audio.switches.QueueSwitch;
import com.shadowninja108.bot.command.audio.switches.SearchSwitch;
import com.shadowninja108.bot.command.audio.switches.SilentSwitch;
import com.shadowninja108.bot.command.audio.switches.SkipSwitch;
import com.shadowninja108.bot.command.audio.switches.SkipToSwitch;
import com.shadowninja108.bot.command.audio.switches.VolumeSwitch;
import com.shadowninja108.bot.shell.ShellProcessor;
import com.shadowninja108.translatable.Translatable;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class AudioCommand implements Command {

	public static List<AudioSwitch> switches;

	static {
		switches = new ArrayList<>();
		register();
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
		switches.add(new LoopSwitch());
		switches.add(new InfoSwitch());
		switches.add(new SilentSwitch());
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
		String content = event.getMessage().getContentRaw();
		String[] split = ShellProcessor.processString(content);
		if (split.length > 1) {
			String audio_switch = split[1];
			String[] params = Arrays.copyOfRange(split, 2, split.length);
			Iterator<AudioSwitch> it = switches.iterator();
			boolean success = false;
			while (it.hasNext()) {
				AudioSwitch sw = it.next();
				if (sw.getSwitch().equals(audio_switch)) {
					sw.execute(event, params);
					success = true;
					break;
				}
			}
			if (!success)
				sendMessage(Translatable.get("audio.invalid_switch"), event.getChannel());
		}
	}



	@Override
	public void privateMessage(MessageReceivedEvent event) {
		sendMessage(Translatable.get("audio.private_rejection"), event.getAuthor());
	}
}
