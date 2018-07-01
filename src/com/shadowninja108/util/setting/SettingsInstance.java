package com.shadowninja108.util.setting;

import java.io.ByteArrayInputStream;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.shadowninja108.util.reader.OptionReader;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.entities.TextChannel;

public class SettingsInstance {
	private static final String CHANNEL_NAME = "kriby-settings";

	private final Guild guild;

	private OffsetDateTime lastEdit = OffsetDateTime.MIN;

	private Map<String, String> settings;
	private Map<String, String> defaults;

	public SettingsInstance(Guild guild) {
		this.guild = guild;
		settings = new HashMap<>();
		defaults = new HashMap<>();
		refresh();
	}

	public String refresh() {
		List<TextChannel> channels = guild.getTextChannelsByName(CHANNEL_NAME, false);
		if (!channels.isEmpty()) {
			TextChannel channel = channels.get(0);
			MessageHistory history = channel.getHistory();
			Message message = history.retrievePast(1).complete().get(0);
			if (!history.isEmpty()) {
				OffsetDateTime editTime = message.getEditedTime();
				if (editTime == null) editTime = message.getCreationTime(); // edited time is null if it was never edited
				if (editTime.isAfter(lastEdit)) {
					lastEdit = editTime;
					Map<String, String> map = parse(message.getContentRaw());
					Iterator<String> defaultsIt = defaults.keySet().iterator();
					while (defaultsIt.hasNext()) {
						String k = defaultsIt.next();
						if (!map.containsKey(k)) {
							return "Settings does not contain value \"" + k + "\"";
						}
					}
					settings.clear();
					settings.putAll(map);
					return "Refresh successful.";
				} else
					return "No refresh needed.";
			} else
				return "No posts have been made in \"" + CHANNEL_NAME + "\".";
		} else {
			return "No text channel by the name of \"" + CHANNEL_NAME + "\".";
		}
	}

	public Map<String, String> getSettings() {
		return Collections.unmodifiableMap(settings);
	}

	public void setDefault(String key, String value) {
		defaults.put(key, value);
	}

	private static Map<String, String> parse(String in) {
		OptionReader reader = new OptionReader(() -> {
			return new ByteArrayInputStream(in.getBytes());
		});
		reader.setAutoUpdatable(false);
		return reader.getOptions();
	}
}
