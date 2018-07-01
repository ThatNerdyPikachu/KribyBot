package com.shadowninja108.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class FontHelper {

	public static String registerFont(String name, InputStream stream) throws FontFormatException, IOException {
		GraphicsEnvironment enviroment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		List<String> registeredFonts = Arrays.asList(enviroment.getAvailableFontFamilyNames());
		if (!registeredFonts.contains(name)) {
			System.out.println("Loading font " + name + "...");
			Font font = Font.createFont(Font.TRUETYPE_FONT, stream);
			System.out.println("Loaded font " + font.getFontName() + ". Registering...");
			if (enviroment.registerFont(font))
				System.out.println("Registration complete.");
			else
				System.out.println("Font failed to register.");
			return font.getFontName();
		} else {
			System.out.println("Font " + name + " is already registered.");
			return name;
		}
	}
}
