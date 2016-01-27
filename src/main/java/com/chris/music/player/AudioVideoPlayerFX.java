package com.chris.music.player;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
/**
 * First try to play streamed music with JavaFX
 * 
 * @author christian
 *
 */
public class AudioVideoPlayerFX extends Application {
	// Video
//	 private final static String MEDIA_URL = "http://download.oracle.com/otndocs/products/javafx/JavaRap/prog_index.m3u8";
	// Radio
	 private final static String MEDIA_URL = "http://stream-eu1.radioparadise.com/aac-320";
	// MP3
//	private final static String MEDIA_URL = "file:////Users/christian/Desktop/AudioTest/CadaBeso.mp3";

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Media media = new Media(MEDIA_URL);
		
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setAutoPlay(true);
		
		MediaView mediaView = new MediaView(mediaPlayer);
		
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(mediaView);
		borderPane.setStyle("-fx-background-color: Black");
		
		Scene scene = new Scene(borderPane, 600, 600);
		scene.setFill(Color.BLACK);
		
		stage.setTitle("MEDIA TEST");
		stage.setScene(scene);
		stage.show();
	}

}
