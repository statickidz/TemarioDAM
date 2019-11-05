/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package holacontenedores;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author DAM2
 */
public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    
    private RootLayoutController rootLayoutController;
    private MainLayoutController mainLayoutController;

    /**
     * Constructor
     */
    public Main() {
        
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Saludo amoroso");
       
        initRootLayout();

        initMainLayout();
    }
    
    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            
            rootLayoutController = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Show player and init controller.
     */
    public void initMainLayout() {
        try {
            // Load player overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("MainLayout.fxml"));
            AnchorPane mainLayout = (AnchorPane) loader.load();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(mainLayout);
            
            // Give the controller access to the main app.
            mainLayoutController = loader.getController();
            rootLayoutController.setMainLayoutController(mainLayoutController);
            
        } catch (IOException e) {
            e.printStackTrace();
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