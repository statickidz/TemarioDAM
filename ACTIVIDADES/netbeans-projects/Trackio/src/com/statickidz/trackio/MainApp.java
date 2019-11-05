package com.statickidz.trackio;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.statickidz.trackio.model.TrackList;
import com.statickidz.trackio.view.TrackListEditDialogController;
import com.statickidz.trackio.view.PlayerController;
import javafx.stage.StageStyle;

/**
 *
 * @author Adrián Barrio Andrés
 * @email statickidz@gmail.com
 * @web https://statickidz.com
 */
public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    /**
     * Constructor
     */
    public MainApp() {
        
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Trackio");
        
        // Undecorated window
        this.primaryStage.initStyle(StageStyle.TRANSPARENT);

        // Set the application icon
        this.primaryStage.getIcons().add(new Image("file:src/com/statickidz/trackio/view/img/icon.png"));

        initRootLayout();

        initPlayer();
    }
    
    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
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
     * Show player and init controller.
     */
    public void initPlayer() {
        try {
            // Load player overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Player.fxml"));
            AnchorPane playerOverview = (AnchorPane) loader.load();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(playerOverview);
            
            // Give the controller access to the main app.
            PlayerController controller = loader.getController();
            controller.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Opens a dialog to add or edit a tracklist details.
     *
     * @param trackList
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showTrackListDialog(TrackList trackList) {
        try {
            // Load the fxml file and create a new stage for the popup dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/TrackListEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Nueva lista de reproducción");
            dialogStage.initStyle(StageStyle.TRANSPARENT);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.centerOnScreen();
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the tracklist into the controller
            TrackListEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setTrackList(trackList);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns the main stage.
     * @return primaryStage
     */
    public Stage getPrimaryStage() {
    	return primaryStage;
    }
    
    
}