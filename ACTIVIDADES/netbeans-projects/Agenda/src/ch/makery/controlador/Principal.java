package ch.makery.controlador;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ch.makery.controlador.Modelo.Person;  
import ch.makery.controlador.Vista.VistaEditarDialogoPersona;
import ch.makery.controlador.Vista.VistaPersonControlador;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import java.io.File;
import java.util.prefs.Preferences;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import ch.makery.controlador.Modelo.ListaPersona;
import javafx.scene.control.Alert;
import ch.makery.controlador.Vista.VistaPrincipalControlador;
import ch.makery.controlador.Vista.EstadisticasCumpleanosControlador;




public class Principal extends Application {
    
    //Creamos las variables
    private Stage primaryStage;
    private BorderPane rootLayout;
    /**
     * Los datos como una  observable list de Personas.
     */
    private ObservableList<Person> personData = FXCollections.observableArrayList();
    
     /**
     * Constructor
     */
    public Principal() {
        // Add some sample data
        personData.add(new Person("Hans", "Muster"));
        personData.add(new Person("Ruth", "Mueller"));
        personData.add(new Person("Heinz", "Kurz"));
        personData.add(new Person("Cornelia", "Meier"));
        personData.add(new Person("Werner", "Meyer"));
        personData.add(new Person("Lydia", "Kunz"));
        personData.add(new Person("Anna", "Best"));
        personData.add(new Person("Stefan", "Meier"));
        personData.add(new Person("Martin", "Mueller"));
    }
     /**
     * Devuelve los datos como una  observable list de Personas. 
     * @return
     */
    public ObservableList<Person> getPersonData() {
        return personData;
    }

    
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        //Ponemos el titulo
        this.primaryStage.setTitle("Agenda");
        this.primaryStage.getIcons().add(new Image("resources/images/Agenda.png"));

        initRootLayout();

        showPersonOverview();
    }

    /**
        * Carga la VistaPrincipal e intenta cargar el último fichero abierto.
    */

    public void initRootLayout() {
        try {
            // Carga la VistaPrincipal desde el fichero fxml.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Principal.class.getResource("Vista/VistaPrincipal.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Muestra la escena conteniendo la Vista Principal.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Da acceso al controlador a al método principal de la aplicación MainApp.
            VistaPrincipalControlador controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Intenta abrir el último fichero cargado.
        File file = getPersonFilePath();
        if (file != null) {
            loadPersonDataFromFile(file);
        }

    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showPersonOverview() {
        try {
            // Carga person overview.fxml
            FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Principal.class.getResource("Vista/VistaPersona.fxml"));
        AnchorPane personOverview = (AnchorPane) loader.load();


            // Carga person overview.fxml  into the center of root layout.xml.
        rootLayout.setCenter(personOverview);

        // El controlador da acceso a la aplicación principal.
        VistaPersonControlador controller = loader.getController();
        controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean showPersonEditDialog(Person person) {
        try {
            // Carga el fichero fxml y creo un nuevo escenario con la ventana emergente.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Principal.class.getResource("Vista/EditarDialogoPersona.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Crea el escenario de diálogo.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editar persona");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Añade la clase persona al controlador.
            VistaEditarDialogoPersona controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            //Muestra el diálogo y espera a que el usuario la cierre.
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }   
    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(Principal.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }
        /**
     * Establecer la ruta del fichero cargado.
     * La ruta es persistente en el registro específico del SO 
     * 
     * @param Archiva el fichero o nulo si cambia la ruta.
     */
    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(Principal.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Actualiza el Título.
            primaryStage.setTitle("AddressApp - " + file.getName());
        } else {
            prefs.remove("filePath");
            primaryStage.setTitle("AddressApp");
        }
    }

        /**
     * Carga los datos de persona desde el archivo especificado. 
     * Los datos actuales persona serán reemplazados.
    * @param file
     */

    public void loadPersonDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(ListaPersona.class);
            Unmarshaller um = context.createUnmarshaller();

            // La lectura de XML desde el archivo y unmarshalling.
            ListaPersona wrapper = (ListaPersona) um.unmarshal(file);

            personData.clear();
            personData.addAll(wrapper.getPersons());

            // Guarda la ruta del fichero en el registro.
            setPersonFilePath(file);

        } catch (Exception e) { // Captura cualquier excepción
            
            Alert alert =  new  Alert (Alert.AlertType.INFORMATION); 
            alert.setTitle ("INFORMACIÓN DE ERROR"); 
            alert.setHeaderText ("Ha ocurrido un error."); 
            alert.setContentText ("No se pudo guardar los datos en el archivo:\n" + file.getPath());
            alert.showAndWait();
        }
    }

    /**
     * Guarda los datos actuales de persona en el fichero específico.
     * @param file
     */

    public void savePersonDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(ListaPersona.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Envolviendo los datos de persona.
            ListaPersona wrapper = new ListaPersona();
            wrapper.setPersons(personData);

            // Clasifica y guarda el XML en el fichero.
            m.marshal(wrapper, file);

            // Guarda la ruta en el registro.
            setPersonFilePath(file);
        } catch (Exception e) { // catches ANY exception
            
            
            Alert alert =  new  Alert (Alert.AlertType.INFORMATION); 
            alert.setTitle ("INFORMACIÓN DE ERROR"); 
            alert.setHeaderText ("Ha ocurrido un error."); 
            alert.setContentText ("No se pudo guardar los datos en el archivo:\n" + file.getPath());
            alert.showAndWait();
        }
    }
    /**
    * Abre el menú para mostrar la estadística de cumpleaños.
    */
    public void showBirthdayStatistics() {
        try {
            // Carga el fichero fxml y crea un escenario en una ventana emergente.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Principal.class.getResource("Vista/EstadisticasCumpleanos.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage(); 
            dialogStage.setTitle("Estadisticas Cumpleaños");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Mete las personas dentro del controlador.
            EstadisticasCumpleanosControlador controller = loader.getController();
            controller.setPersonData(personData);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
