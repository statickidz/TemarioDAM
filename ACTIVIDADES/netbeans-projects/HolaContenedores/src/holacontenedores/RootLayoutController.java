/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package holacontenedores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 *
 * @author DAM2
 */
public class RootLayoutController implements Initializable {
    
    private MainLayoutController mainLayoutController;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    @FXML
    public void handleSaludo() {
        mainLayoutController.handleSaludo();
    }
    
    
    public void setMainLayoutController(MainLayoutController mlc) {
        this.mainLayoutController = mlc;
    }
    
}
