package com.shadowninja108.bot.command.image;

import static com.shadowninja108.util.MemeUtil.compress;
import static com.shadowninja108.util.MemeUtil.redraw;
import static com.shadowninja108.util.MessageUtil.sendFile;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.shadowninja108.bot.command.Command;
import com.shadowninja108.bot.shell.ShellProcessor;
import com.shadowninja108.util.DiscordUtils;
import com.shadowninja108.util.FontHelper;
import com.shadowninja108.util.JarFile;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class OKMom implements Command {

	private String fontName;
	
	public OKMom() {
		try {
			fontName = FontHelper.registerFont("Arial", new JarFile("/resources/font/arial.ttf").getStream());
		} catch (FontFormatException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getName() {
		return "okmom";
	}

	@Override
	public String getDescription() {
		return "Makes a dank may-may";
	}

	/**
	 * @param event
	 */
	@Override
	public void serverMessage(MessageReceivedEvent event) {
		String[] params = ShellProcessor.processString(event.getMessage().getContentRaw());
		if (params.length > 1)
			try {
				BufferedImage image = ImageIO.read(new JarFile("/resources/images/byemom.png").getStream());

				Graphics2D g = image.createGraphics();
				int width = image.getWidth(), height = image.getHeight();
				int x = 240, y = 585, angle = -25;

				List<User> mentions = event.getMessage().getMentionedUsers();
				if (!mentions.isEmpty()) {
					BufferedImage avatar = DiscordUtils.getAvatarFromUser(mentions.get(0));
					g.drawImage(avatar, 525, 10, 84, 84, null);
					g.drawImage(avatar, 60, 320, 155, 155, null);
				}

				g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				g.setColor(Color.BLACK);
				String text = params[1];
				g.setFont(Font.decode(fontName + "-12"));

				g.setTransform(AffineTransform.getRotateInstance(Math.toRadians(angle), width / 2, height / 2));
				g.drawString(text, x, y);
				g.dispose();

				BufferedImage jpeg = redraw(image);

				sendFile(compress(jpeg, .5f), "okmom.jpg", event.getChannel());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
	}

	@Override
	public void privateMessage(MessageReceivedEvent event) {
		serverMessage(event);
	}

}
