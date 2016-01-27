package com.chris.music.player.vlcj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

public class Tutorial {
	final static Logger logger = LoggerFactory.getLogger(Tutorial.class);
	
	public static void main(String[] args) {
		// Find the VLC installation
		boolean foundVLC = new NativeDiscovery().discover();
		logger.info("VLC found={}", foundVLC);
		logger.info("VLC version {}", LibVlc.INSTANCE.libvlc_get_version());
	}

}
