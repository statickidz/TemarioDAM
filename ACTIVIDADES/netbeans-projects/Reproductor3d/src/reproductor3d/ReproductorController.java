/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reproductor3d;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class ReproductorController implements Initializable {
    
    @FXML
    private Label etiqueta;
    @FXML
    private TextField cuadroTexto;
    @FXML
    private Image imagen;

    // Reference to the main application.
    private Reproductor3d reproductor;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public ReproductorController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Inicializa el Pane.
        etiqueta.setText("Probando");
        cuadroTexto.setText("Para poner el path");
        imagen.getClass().cast("Logo.jpg");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     
}
