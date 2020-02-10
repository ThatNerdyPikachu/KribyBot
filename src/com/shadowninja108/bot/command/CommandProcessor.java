package com.shadowninja108.bot.command;

import static com.shadowninja108.util.MessageUtil.sendMessage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.shadowninja108.bot.command.audio.AudioCommand;
import com.shadowninja108.bot.command.basic.FlipCommand;
import com.shadowninja108.bot.command.basic.YoCommand;
import com.shadowninja108.bot.command.communication.DeleteCommand;
import com.shadowninja108.bot.command.communication.SayCommand;
import com.shadowninja108.bot.command.communication.SayDiscreteCommand;
import com.shadowninja108.bot.command.communication.YodaCommand;
import com.shadowninja108.bot.command.image.Bamboozle;
import com.shadowninja108.bot.command.image.Disability;
import com.shadowninja108.bot.command.image.OKMom;
import com.shadowninja108.bot.command.image.Understandable;
import com.shadowninja108.bot.command.mention.ReactText;
import com.shadowninja108.bot.command.settings.PrintSettings;
import com.shadowninja108.bot.command.settings.RefreshCommand;
import com.shadowninja108.bot.command.util.ExceptionTest;
import com.shadowninja108.bot.command.util.GTFOCommand;
import com.shadowninja108.bot.command.util.HelpCommand;
import com.shadowninja108.bot.command.util.InviteCommand;
import com.shadowninja108.main.Launcher;
import com.shadowninja108.util.DiscordUtils;
import com.shadowninja108.util.MemeUtil;
import com.shadowninja108.util.MessageUtil;
import com.shadowninja108.util.audio.GuildData;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandProcessor {
	
	public static List<Command> commands;
	public static AudioPlayerManager playerManager;

	private static Map<Long, GuildData> guildData;

	static {
		commands = new ArrayList<>();
		guildData = new HashMap<>();
		playerManager = new DefaultAudioPlayerManager();
		AudioSourceManagers.registerRemoteSources(playerManager);
		register();
	}

	public static GuildData getGuildData(Guild guild) {
		guildData.computeIfAbsent(guild.getIdLong(), k -> {
			return new GuildData(guild, playerManager);
		});
		return guildData.get(guild.getIdLong());
	}

	private static void register() {
		commands.add(new AudioCommand());

		commands.add(new YoCommand());
		commands.add(new FlipCommand());

		commands.add(new ExceptionTest());
		commands.add(new HelpCommand());
		commands.add(new GTFOCommand());
		commands.add(new InviteCommand());

		commands.add(new SayCommand());
		commands.add(new SayDiscreteCommand());
		commands.add(new DeleteCommand());
		commands.add(new YodaCommand());

		commands.add(new OKMom());
		commands.add(new Understandable());
		commands.add(new Disability());
		commands.add(new Bamboozle());

		commands.add(new ReactText());

		commands.add(new RefreshCommand());
		commands.add(new PrintSettings());
	}

	public boolean processMessage(MessageReceivedEvent event) {
		try {
			String message = event.getMessage().getContentStripped();
			String prefix = Launcher.getPrefix();
			if (message.startsWith(prefix)) {
				String command = event.getMessage().getContentRaw();
				int i = message.indexOf(" ");
				if (i < 0)
					i = message.length(); // no parameters
				command = command.substring(prefix.length(), i);
				Iterator<Command> it = commands.iterator();
				while (it.hasNext()) {
					Command com = it.next();
					if (command.equals(com.getName()))
						switch (event.getChannelType()) {
						case PRIVATE:
							com.privateMessage(event);
							return true;
						case TEXT:
							com.serverMessage(event);
							return true;
						default:
							System.out.println("Unsupported opperation: " + event.getChannelType().toString());
						}

				}
			} else if (message.toLowerCase().contains("needs more jpeg")) {
				List<Message> history = event.getTextChannel().getHistoryAround(event.getMessage(), 2).complete()
						.getRetrievedHistory();
				if (!history.isEmpty()) {
					Message previous = history.get(history.size() - 1);
					List<Attachment> attachments = previous.getAttachments();
					if (!attachments.isEmpty()) {
						Attachment at = attachments.get(0);
						BufferedImage original;
						try {
							original = DiscordUtils.getImageFromURL(at.getUrl());
						} catch (IOException e) {
							sendMessage("Invalid file!", event.getChannel());
							return true;
						}
						BufferedImage redrawn = MemeUtil.redraw(original);
						InputStream image = MemeUtil.compress(redrawn, 0);
						MessageUtil.sendFile(image, "jpegified.jpeg", event.getChannel());
						return true;
					}
				}
			}
		} catch (Exception e) {
			String exception = "Oh no! An uncaught exception occured! Forward this onto " + Launcher.author_mentionable
					+ " so that they can get it fixed!\n" + ExceptionUtils.getStackTrace(e);
			if (exception.length() >= 2000) {
				exception = exception.substring(0, 2000 - 4);
				exception += "...";
			}
			e.printStackTrace();
			sendMessage(exception, event.getChannel());
			return true;
		}
		return false;
	}
}
