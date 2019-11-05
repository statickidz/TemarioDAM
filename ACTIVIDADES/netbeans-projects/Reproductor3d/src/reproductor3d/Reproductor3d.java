/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reproductor3d;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent;
import javafx.scene.Scene;
// import javafx.scene.control.Alert;
// import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Reproductor3d extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    
    private static final File file = new File("src/reproductor3d/audio/Maid.mp3");
    private static MediaPlayer audioMediaPlayer;
    private static final boolean PLAY_AUDIO = Boolean.parseBoolean(System.getProperty("demo.play.audio","true"));
    private AudioSpectrumListener audioSpectrumListener;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("REPRODUCTOR 3D");

        initRootLayout();

        showPersonOverview();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("PRINCIPAL.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ANIMACION3D.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    @FXML
    private void startAudio() throws MalformedURLException {
        if (PLAY_AUDIO) {
            getAudioMediaPlayer().setAudioSpectrumListener(audioSpectrumListener);
            getAudioMediaPlayer().play();
        }
    }
    
    @FXML
    private void stopAudio() throws MalformedURLException {
        if (getAudioMediaPlayer().getAudioSpectrumListener() == audioSpectrumListener) {
            getAudioMediaPlayer().pause();

        }
    }

   private static MediaPlayer getAudioMediaPlayer() throws MalformedURLException {
        if (audioMediaPlayer == null) {
            // Media audioMedia = new Media(AUDIO_URI);
            Media audioMedia = new Media(file.toURI().toURL().toString());
            audioMediaPlayer = new MediaPlayer(audioMedia);
        }
        return audioMediaPlayer;
    }

}


