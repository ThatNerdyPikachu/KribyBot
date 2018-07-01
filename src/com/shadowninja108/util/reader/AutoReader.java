package com.shadowninja108.util.reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.codec.digest.DigestUtils;

public abstract class AutoReader<K, V> {

	protected boolean autoUpdate = true;

	protected Map<K, V> options;
	protected Callable<InputStream> callable;
	
	protected String previousHash;
	
	public AutoReader(Callable<InputStream> callable){
		this.callable =  callable;
		this.options = new HashMap<>();
	}

	public abstract void read() throws IOException;

	private void handleRead() {
		options.clear();
		try {
			read();
		} catch (IOException e) {
			System.out.println("An error occurred on reading.");
			e.printStackTrace();
		}
		previousHash = getNewHash();
	}

	private boolean hasChanged() {
		return !getNewHash().equals(previousHash);
	}

	private String getNewHash() {
		try {
			return DigestUtils.md5Hex(getInputStream());
		} catch (IOException e) {
			System.out.println("Failed to get hash!");
			e.printStackTrace();
		}
		return null;
	}

	public void setAutoUpdatable(boolean b) {
		if (autoUpdate && !b)
			handleRead();
		autoUpdate = b;
	}

	public Map<K, V> getOptions() {
		if (autoUpdate)
			if (hasChanged())
				handleRead();
		return Collections.unmodifiableMap(options);
	}

	protected InputStream getInputStream() {
		try {
			return callable.call();
		} catch (Exception e) {
			System.out.println("An exception occured while calling for InputStream!");
			e.printStackTrace();
		}
		return null;
	}

}
