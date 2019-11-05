package ch.makery.controlador.Vista;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ch.makery.controlador.Principal;
import ch.makery.controlador.Modelo.Person;
import ch.makery.controlador.util.DateUtil;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class VistaPersonControlador {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;

    // Referencia a la aplicación  main.
    private Principal mainApp;
    
    /**
     *  El constructor.
     *  El constructor es ejecutado al ejecutar el método initialize().
     */
    public VistaPersonControlador() {
    }
    
    /**
     * Inicializa la clase controller. Este método se ejecuta automáticamente
     * después de haber sido cargado el fichero fxml.
     */
    @FXML
    private void initialize() {
        // Inicializa la tabla persona con dos columnas.
    firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
    lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

    // Borrar los detalles de persona.
    showPersonDetails(null);

    // Escucha si cambia la selección y muestra los detalles de la persona cuando cambia.
    personTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showPersonDetails(newValue));


    }

    private void showPersonDetails(Person person) {
        if (person != null) {
            // Rellena las etiquetas con la información del objeto Person.
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            cityLabel.setText(person.getCity());

            // TODO: Hay que convertir la fecha de cumpleaños a un string. 
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
        } else {
            // Si el objeto peson es null dejar todo en blanco.
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
        }
    }
    /**
 * Lo ejecuta cuando el usuario pulsa el botón nuevo. Abre la ventana de edición
 * que muestra los detalles de la clase persona.
 */
@FXML
private void handleNewPerson() {
    Person tempPerson = new Person();
    boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
    if (okClicked) {
        mainApp.getPersonData().add(tempPerson);
    }
}

/**
 * Lo ejecuta cuando el usuario pulsa el botón editar. Abre la ventana de edición. 
* que muestra los detalles de la clase persona.
 */
@FXML
private void handleEditPerson() {
    Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
    if (selectedPerson != null) {
        boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
        if (okClicked) {
            showPersonDetails(selectedPerson);
        }

    } else {
        
        Alert alert =  new  Alert (AlertType.INFORMATION); 
            alert.setTitle ("INFORMACIÓN DE ERROR"); 
            alert.setHeaderText ("Ha ocurrido un error."); 
            alert.setContentText ("Tienes que elegir a alguien para poder editar");

            alert. showAndWait ();
    }
}

    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
        } else {
            //Nothing selected.
            System.out.println("NO HAY NADA PARA BORRAR");
            //COMPROBAR PORQUE NO FUNCIONA
           
            Alert alert =  new  Alert (AlertType.INFORMATION); 
            alert.setTitle ("INFORMACIÓN DE ERROR"); 
            alert.setHeaderText ("Ha ocurrido un error."); 
            alert.setContentText ("Tienes que elegir a alguien para poder borrar");

            alert. showAndWait ();
            // ventana de sistema de swing  
             //javax.swing.JOptionPane.showMessageDialog(null, "Error, no has seleccionado a nadie");

        }

    }


        /**
     * Se llama a la aplicación main para devolver las referencias a si misma.
     * @param Principal
     */
    public void setMainApp(Principal mainApp) {
        this.mainApp = mainApp;

        // Añade los datos de la  observable list a la tabla.
        personTable.setItems(mainApp.getPersonData());
    }


}
