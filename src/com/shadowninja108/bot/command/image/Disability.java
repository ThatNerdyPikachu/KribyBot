package com.shadowninja108.bot.command.image;

import static com.shadowninja108.util.MemeUtil.compress;
import static com.shadowninja108.util.MemeUtil.redraw;
import static com.shadowninja108.util.MessageUtil.sendFile;
import static com.shadowninja108.util.MessageUtil.sendMessage;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.shadowninja108.bot.command.Command;
import com.shadowninja108.main.Launcher;
import com.shadowninja108.util.DiscordUtils;
import com.shadowninja108.util.JarFile;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Disability implements Command {

	@Override
	public String getName() {
		return "disability";
	}

	@Override
	public String getDescription() {
		return "It's the truth...";
	}

	@Override
	public void serverMessage(MessageReceivedEvent event) {
		try {
			BufferedImage image = ImageIO.read(new JarFile("/resources/images/disabilities.png").getStream());

			Graphics2D g = image.createGraphics();

			List<User> mentions = event.getMessage().getMentionedUsers();
			if (!mentions.isEmpty()) {
				BufferedImage avatar = DiscordUtils.getAvatarFromUser(mentions.get(0));
				g.drawImage(avatar, 500, 364, 182, 182, null);
			}
			g.dispose();

			BufferedImage jpeg = redraw(image);

			sendFile(compress(jpeg, .5f), "disabilities.jpg", event.getChannel());
		} catch (IOException e) {
			sendMessage("Sorry! An error occured making the image! Tell " + Launcher.author_mentionable
					+ " about this!\n" + e.toString(), event.getChannel());
			e.printStackTrace();
		}

	}

	@Override
	public void privateMessage(MessageReceivedEvent event) {
		serverMessage(event);
	}

}
