package com.shadowninja108.bot.command.image;

import static com.shadowninja108.util.MemeUtil.compress;
import static com.shadowninja108.util.MemeUtil.redraw;
import static com.shadowninja108.util.MessageUtil.sendFile;
import static com.shadowninja108.util.MessageUtil.sendMessage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.shadowninja108.bot.command.Command;
import com.shadowninja108.util.JarFile;

import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Bamboozle implements Command {

	@Override
	public String getName() {
		return "bamboozle";
	}

	@Override
	public String getDescription() {
		return "Bamboozle a random person.";
	}

	@Override
	public void serverMessage(MessageReceivedEvent event) {
		try {
			boolean valid = false;
			User user = null;
			Member member = null;
			List<Member> members = event.getGuild().getMembers();
			while (!valid) {
				member = members.get((int) (Math.random() * members.size()));
				user = member.getUser();
				try {
					user.openPrivateChannel().complete();
				} catch (Exception e) {
					valid = false;
					continue;
				}
				valid = !user.isBot() && member.getOnlineStatus().equals(OnlineStatus.ONLINE);
			}
			BufferedImage image = ImageIO.read(new JarFile("/resources/images/cancer.png").getStream());

			BufferedImage jpeg = redraw(image);

			sendFile(compress(jpeg, 0.05f), "cancer.jpg", user.openPrivateChannel().complete());

			sendMessage("Bamboozled " + member.getEffectiveName(), event.getChannel());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void privateMessage(MessageReceivedEvent event) {
		sendMessage("You are a failure.", event.getChannel());
	}

}
