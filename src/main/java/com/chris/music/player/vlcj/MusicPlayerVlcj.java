package com.chris.music.player.vlcj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaDetails;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

public class MusicPlayerVlcj {
	private static final Logger log = LoggerFactory.getLogger(MusicPlayerVlcj.class);
	private static final String ONLINE_SOURCE = "http://server1.radiodanz.com:8020/";
	private AudioMediaPlayerComponent mediaPlayerComponent;
	
	public static void main(String[] args) throws InterruptedException {
		new NativeDiscovery().discover();
		
		MusicPlayerVlcj player = new MusicPlayerVlcj();
		player.start(ONLINE_SOURCE);
		try {
			Thread.currentThread().join();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	private void start(String source) throws InterruptedException {
		MediaPlayer mediaPlayer = mediaPlayerComponent.getMediaPlayer();
		mediaPlayer.playMedia(source);
		System.out.println(".:. Wait 5 seconds...");
		Thread.sleep(5000);
		printMediaInfo(mediaPlayer);
	}

	private void printMediaInfo(MediaPlayer m) {
		MediaDetails details = m.getMediaDetails();
		System.out.println(".:. " + details);
		System.out.println(".:. " + m.getMediaMeta().toString());
		System.out.println(".:. " + m.getMediaMetaData().toString());
		m.getAudioDescriptions().forEach(ad -> System.out.println(".:. AD=" + ad.toString()));
		m.getExtendedChapterDescriptions().forEach(ed -> System.out.println(".:.ED=" + ed.toString()));
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
