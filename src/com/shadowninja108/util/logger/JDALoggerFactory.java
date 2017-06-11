package com.shadowninja108.util.logger;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDALoggerFactory implements ILoggerFactory {

	private final Map<String, Logger> loggerMap = new HashMap<>();

	@Override
	public Logger getLogger(String name) {
		loggerMap.computeIfAbsent(name, k -> {
			Logger logger = LoggerFactory.getLogger(name);
			BasicConfigurator.configure();
			return logger;
		});

		return loggerMap.get(name);
	}
}