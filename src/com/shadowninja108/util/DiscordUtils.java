package com.shadowninja108.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.requests.Requester;

public class DiscordUtils {
	public static Message findMessage(Guild guild, String messageId) {
		List<TextChannel> channels = guild.getTextChannels();
		for (int i = 0; i < channels.size(); i++) {
			TextChannel c = channels.get(i);
			try {
				return c.getMessageById(messageId).complete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static BufferedImage getImageFromURL(String url) throws IOException {
		BufferedImage image = ImageIO.read(getInputStreamFromURL(url));
		if (image != null)
			return image;
		else
			throw new IOException();

	}

	public static InputStream getInputStreamFromURL(String in) throws MalformedURLException, IOException {
		HttpURLConnection conn = (HttpURLConnection) new URL(in).openConnection();
		conn.setRequestProperty("User-Agent", Requester.USER_AGENT);
		return conn.getInputStream();
	}
	
	public static BufferedImage getAvatarFromUser(User user) throws IOException {
		return getImageFromURL(user.getEffectiveAvatarUrl());
	}
}
