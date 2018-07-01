package com.shadowninja108.bot.command.image;

import static com.shadowninja108.util.MemeUtil.anyAreEmpty;
import static com.shadowninja108.util.MemeUtil.centerRect;
import static com.shadowninja108.util.MemeUtil.compress;
import static com.shadowninja108.util.MemeUtil.drawRect;
import static com.shadowninja108.util.MemeUtil.drawRect2D;
import static com.shadowninja108.util.MemeUtil.getScale;
import static com.shadowninja108.util.MemeUtil.redraw;
import static com.shadowninja108.util.MemeUtil.scaleShape;
import static com.shadowninja108.util.MemeUtil.transformAbsolute;
import static com.shadowninja108.util.MessageUtil.sendFile;
import static com.shadowninja108.util.MessageUtil.sendMessage;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.shadowninja108.bot.UberCoolBot;
import com.shadowninja108.bot.command.Command;
import com.shadowninja108.bot.shell.ShellProcessor;
import com.shadowninja108.util.FontHelper;
import com.shadowninja108.util.JarFile;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Understandable implements Command {

	private static Rectangle intro = new Rectangle(19, 504, 734, 157), response = new Rectangle(800, 504, 710, 157),
			outro = new Rectangle(39, 1118, 1458, 231);

	private String fontName;

	public Understandable() {
		try {
			fontName = FontHelper.registerFont("Impact", new JarFile("/resources/font/impact.ttf").getStream());
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getName() {
		return "understandable";
	}

	@Override
	public String getDescription() {
		return ", have a nice day.";
	}

	@Override
	public void serverMessage(MessageReceivedEvent event) {
		String[] params = ShellProcessor.processString(event.getMessage().getContentDisplay());
		if (params.length > 3)
			try {
				BufferedImage image = ImageIO.read(new JarFile("/resources/images/understandable.png").getStream());

				Graphics2D g = image.createGraphics();

				g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

				String text1 = params[1];
				String text2 = params[2];
				String text3 = params[3];
				if (anyAreEmpty(text1, text2, text3)) {
					sendMessage("One of the parameters is empty.", event.getChannel());
					return;
				}
				List<ProcessInfoWrapper> info = new ArrayList<>();

				info.add(new ProcessInfoWrapper(text1, intro));
				info.add(new ProcessInfoWrapper(text2, response));
				info.add(new ProcessInfoWrapper(text3, outro));

				g.setFont(Font.decode(fontName + "-100"));
				process(info, g);

				BufferedImage jpeg = redraw(image);

				sendFile(compress(jpeg, .1f), "understandable.jpg", event.getChannel());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		else
			sendMessage("You need 3 paramaters!", event.getChannel());
	}

	public void process(List<ProcessInfoWrapper> map, Graphics2D g) {
		map.forEach(info -> {
			TextLayout layout = new TextLayout(info.text, g.getFont(), g.getFontRenderContext());
			Shape shape = layout.getOutline(new AffineTransform());
			double scale = getScale(info.rec, shape.getBounds2D());
			g.setStroke(new BasicStroke((float) Math.min(5,5 * (scale/2))));
			shape = scaleShape(shape, scale);
			Rectangle2D position = centerRect(info.rec, shape.getBounds2D());
			shape = transformAbsolute(shape, position.getX(), position.getY());

			if (UberCoolBot.debug) { // probably should of done this earlier
				g.setColor(Color.RED);
				drawRect2D(g, shape.getBounds2D());

				g.setColor(Color.ORANGE);
				drawRect(g, info.rec);
			}

			g.setColor(Color.WHITE);
			g.fill(shape);

			g.setColor(Color.BLACK);
			g.draw(shape);
		});

		g.dispose();
	}

	// very dumb wrapper to fix extreme edge case
	// used a map before, but if user put the same text more than once, it would only display once
	public class ProcessInfoWrapper {
		public ProcessInfoWrapper(String text, Rectangle rec) {
			this.text = text;
			this.rec = rec;
		}

		public String text;
		public Rectangle rec;
	}

	@Override
	public void privateMessage(MessageReceivedEvent event) {
		serverMessage(event);
	}

}
