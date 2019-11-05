package ch.makery.controlador.Vista;

import java.io.File;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import ch.makery.controlador.Principal;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


/**
 * Controlador de la ventana principal.  La ventana principal muestra 
 * una barra de menú y espacio donde dejaremos los objetos javaFX
 */

public class VistaPrincipalControlador {

    // Referencia a la aplicación principal
    private Principal mainApp;

    /**
     * Es llamado por la aplicación principal para devolver las referencias a la misma.
     * @param Principal
     */
    public void setMainApp(Principal mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Crea una agenda vacía.
     */
    @FXML
    private void handleNew() {
        mainApp.getPersonData().clear();
        mainApp.setPersonFilePath(null);
    }

    /**
     * Abre un  FileChooser para que el usuario seleccione un archivo a abrir.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Filtra la extensión.
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Muestra la ventana de guardar archivo
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadPersonDataFromFile(file);
        }
    }

    /**
     * Guarda en el fichero la persona actual.
     * Si no hay   archivo abierto, muestra el cuadro de diálogo "Guardar como".
     */
    @FXML
    private void handleSave() {
        File personFile = mainApp.getPersonFilePath();
        if (personFile != null) {
            mainApp.savePersonDataToFile(personFile);
        } else {
            handleSaveAs();
        }
    }

    /**
     * Abre un  FileChooser para que el usuario indique el archivo a guardar.
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Selección de la extensión.
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Muestra la ventana de guardar.
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            mainApp.savePersonDataToFile(file);
        }
    }

    /**
     * Abre una ventana de diálogo.
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Sin selección ");
        alert.setHeaderText("No hay ninguna persona seleccionada ");
        alert.setContentText("Seleccione una persona en la tabla.");
        alert.showAndWait();

    }

    /**
     * Cierra la aplicación.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
    
    /**
    * Abre la estadística de cumpleaños.
    */
    @FXML
    private void handleShowBirthdayStatistics() {
      mainApp.showBirthdayStatistics();
    }

}

