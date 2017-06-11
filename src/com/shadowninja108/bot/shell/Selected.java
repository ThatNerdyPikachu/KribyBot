package com.shadowninja108.bot.shell;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;

public class Selected {
	private Guild guild;
	private Role role;
	private User user;
	private Member member;
	private TextChannel text_channel;
	private VoiceChannel voice_channel;

	private selected select;

	public Selected() {
		select = selected.NONE;
	}

	public TextChannel getTextChannel() {
		return text_channel;
	}

	public void setTextChannel(TextChannel text_channel) {
		this.text_channel = text_channel;
	}

	public VoiceChannel getVoiceChannel() {
		return voice_channel;
	}

	public void setVoiceChannel(VoiceChannel voice_channel) {
		this.voice_channel = voice_channel;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Guild getGuild() {
		return guild;
	}

	public void setGuild(Guild guild) {
		this.guild = guild;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public void select(selected select) {
		switch (select) {
		case GUILD:
			if (guild != null)
				this.select = select;
			break;
		case MEMBER:
			if (member != null)
				this.select = select;
			break;
		case ROLE:
			if (role != null)
				this.select = select;
			break;
		case TEXT_CHANNEL:
			if (text_channel != null)
				this.select = select;
			break;
		case USER:
			if (user != null)
				this.select = select;
			break;
		case VOICE_CHANNEL:
			if (voice_channel != null)
				this.select = select;
			break;
		case NONE:
			this.select = select;
			break;
		}
	}

	public selected getSelected() {
		return select;
	}

	public enum selected {
		GUILD, ROLE, USER, MEMBER, TEXT_CHANNEL, VOICE_CHANNEL, NONE
	}
}
