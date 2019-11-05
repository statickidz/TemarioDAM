/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paginationdemo;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author DAM2
 */
public class PaginationController implements Initializable {
    
    private Stage primaryStage;
    
    @FXML
    private Pagination pagination;
    @FXML
    private VBox outerBox;
    
    private final Image[] images = new Image[7];

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init(primaryStage);
    }    
    
    
    private void init(Stage primaryStage) {
        for (int i = 0; i < 7; i++) {
            images[i] = new Image(getClass().getResource("animal" + (i + 1) + ".jpg").toExternalForm(), false);
        }
        
        pagination.setPageCount(images.length);
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                return createPage(pageIndex);
            }
        });
        
        

    }
    
    //Creates the page content
    private VBox createPage(int pageIndex) {
        VBox box = new VBox();
        ImageView iv = new ImageView(images[pageIndex]);
        box.setAlignment(Pos.CENTER);
        Label desc = new Label("PAGE " + (pageIndex + 1));
        box.getChildren().addAll(iv, desc);
        return box;
    }
    
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
}
