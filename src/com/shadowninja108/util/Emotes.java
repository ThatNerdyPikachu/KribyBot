package com.shadowninja108.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Emotes {
	public static Map<Character, List<String>> emotes;

	final static String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

	static {
		emotes = new HashMap<>();

		addEmote('#', 0x23, 0x20E3);
		addEmote('*', 0x2A, 0x20E3);

		addEmote('!', 0x2757);
		addEmote('!', 0x2755);

		addEmote('?', 0x2753);
		addEmote('?', 0x2754);

		final int numbersOffset = 0x30;
		for (int i = 0; i < 10; i++)
			addEmote(Character.forDigit(i, 10), numbersOffset + i, 0x20E3);

		final int alphabetOffset = 0x1F1E6;
		for (int i = 0; i < ALPHABET.length(); i++)
			addEmote(ALPHABET.charAt(i), alphabetOffset + i);

		addEmote('a', 0x1F170);
		addEmote('b', 0x1F171);
		addEmote('i', 0x2139);
		addEmote('o', 0x1F17E);
		addEmote('o', 0x2B55);
		addEmote('p', 0x1F17F);
		addEmote('m', 0x24C2);
		addEmote('x', 0x274E);
		addEmote('x', 0x274C);
	}

	public static void addEmote(char c, int... emote) {
		emotes.putIfAbsent(c, new ArrayList<>());
		emotes.get(c).add(toCodepoint(emote));
	}

	public static String toCodepoint(int[] codePoint) {
		return new String(codePoint, 0, codePoint.length);
	}
}
