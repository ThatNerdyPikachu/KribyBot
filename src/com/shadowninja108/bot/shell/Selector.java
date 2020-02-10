package com.shadowninja108.bot.shell;

import static com.shadowninja108.util.MessageUtil.sendMessage;

import java.util.List;

import com.shadowninja108.bot.shell.Selected.selected;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Selector extends ShellCommand {

	@Override
	public String getPrefix() {
		return "select";
	}

	@Override
	public String getDescription() {
		return "Select a Discord object.";
	}

	@Override
	public Byte[] getParameterCount() {
		return new Byte[] { 1, 2 };
	}

	@Override
	public void execute(String[] args, Selected select, MessageReceivedEvent event) {
		switch (args[0]) {
		case "server":
			selected s = select.getSelected();
			if (s == selected.NONE || args.length > 1) {
				List<Guild> guilds = event.getJDA().getGuildsByName(args[1], true);
				if (!guilds.isEmpty()) {
					select.setGuild(guilds.get(0));
					select.select(selected.GUILD);
					sendMessage("Selected: " + guilds.get(0).getName(), event.getAuthor());
				} else
					sendMessage("No servers found with that name.", event.getAuthor());

			} else {
				select.select(selected.GUILD);
			}
			break;
		case "role":
			if (select.getSelected().equals(selected.GUILD)) {
				List<Role> roles = select.getGuild().getRolesByName(args[1], true);
				if (!roles.isEmpty()) {
					select.setRole(roles.get(0));
					select.select(selected.ROLE);
					sendMessage("Selected: " + roles.get(0).getName(), event.getAuthor());
				}
			} else
				sendMessage("A server is not selected. You are currently selecting a "
						+ select.getSelected().name().toLowerCase(), event.getChannel());
			break;
		case "member":
			if (select.getSelected().equals(selected.GUILD)) {
				List<Member> members = select.getGuild().getMembersByName(args[1], true);
				if (!members.isEmpty()) {
					select.setMember(members.get(0));
					select.select(selected.MEMBER);
					sendMessage("Selected: " + members.get(0).getAsMention(), event.getAuthor());
				}
			} else
				sendMessage("A server is not selected. You are currently selecting a "
						+ select.getSelected().name().toLowerCase(), event.getChannel());
			break;
		case "user":
			if (select.getSelected().equals(selected.GUILD)) {
				List<Member> members = select.getGuild().getMembersByName(args[1], true);
				if (!members.isEmpty()) {
					select.setUser(members.get(0).getUser());
					select.select(selected.USER);
					sendMessage("Selected: " + members.get(0).getUser().getName(), event.getAuthor());
				}
			} else
				sendMessage("A server is not selected. You are currently selecting a "
						+ select.getSelected().name().toLowerCase(), event.getChannel());
			break;
		case "voice-channel":
			if (select.getSelected().equals(selected.GUILD)) {
				List<VoiceChannel> channels = select.getGuild().getVoiceChannelsByName(args[1], true);
				if (!channels.isEmpty()) {
					select.setVoiceChannel(channels.get(0));
					select.select(selected.VOICE_CHANNEL);
					sendMessage("Selected: " + channels.get(0).getName(), event.getAuthor());
				}
			} else
				sendMessage("A server is not selected. You are currently selecting a "
						+ select.getSelected().name().toLowerCase(), event.getChannel());

			break;
		case "text-channel":
			if (select.getSelected().equals(selected.GUILD)) {
				List<TextChannel> channels = select.getGuild().getTextChannelsByName(args[1], true);
				if (!channels.isEmpty()) {
					select.setTextChannel(channels.get(0));
					select.select(selected.TEXT_CHANNEL);
					sendMessage("Selected: " + channels.get(0).getName(), event.getAuthor());
				} else
					sendMessage("Nothing was found.", event.getAuthor());
			} else
				sendMessage("A server is not selected. You are currently selecting a "
						+ select.getSelected().name().toLowerCase(), event.getChannel());

			break;

		}

	}

	@Override
	public boolean meetsRequirements(selected selected) {
		return true;
	}

}
