package com.chris.music.player.vlcj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

public class MusicPlayerVlcj {
	private static final Logger log = LoggerFactory.getLogger(MusicPlayerVlcj.class);
	private static final String SOURCE = "/Users/christian/Desktop/AudioTest/ArabianHorse.flac";
	private static final String ONLINE_SOURCE = "http://stream-eu1.radioparadise.com/aac-320";
	private AudioMediaPlayerComponent mediaPlayerComponent;
	
	public static void main(String[] args) {
		new NativeDiscovery().discover();
		
		MusicPlayerVlcj player = new MusicPlayerVlcj();
		player.start(ONLINE_SOURCE);
		try {
			Thread.currentThread().join();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	private void start(String source) {
		mediaPlayerComponent.getMediaPlayer().playMedia(source);
	}

	private MusicPlayerVlcj(){
		mediaPlayerComponent = new AudioMediaPlayerComponent();
		mediaPlayerComponent.getMediaPlayer().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
			@Override
			public void stopped(MediaPlayer mediaPlayer) {
				exit(0);
			}
			
			@Override
			public void finished(MediaPlayer mediaPlayer) {
				exit(0);
			}
			
			@Override
			public void error(MediaPlayer mediaPlayer) {
				exit(1);
			}
		});
	}
	
	private void exit(int i) {
		log.info("Exiting with code= {}", i );
		mediaPlayerComponent.release();
		System.exit(i);
		
	}

}
