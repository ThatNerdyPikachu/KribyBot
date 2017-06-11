package com.shadowninja108.util;

import com.shadowninja108.translatable.Translatable;

import net.dv8tion.jda.core.entities.Game;

public class MyGame implements Game {

	private static MyGame game;

	private String url, name;
	private GameType type;

	static {
		game = new MyGame();
	}

	public MyGame() {
		url = Translatable.get("global.game_url");
		name = Translatable.get("global.game_name");
		type = GameType.DEFAULT;
	}

	public String getUrl() {
		return url;
	}

	public GameType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(GameType type) {
		this.type = type;
	}

	public static MyGame getGame() {
		return game;
	}
}
