/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reproductor3d;

import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Joseluis
 */
public class Animacion {
    private final StringProperty ruta;
    //private final Image animacion;
    //private final ImageView animaView;


    /**
     * Default constructor.
     */
    public Animacion() {
        this(null, null);
    }

    
    public Animacion(String ruta, Image animacion) {
        this.ruta = new SimpleStringProperty("ruta");
    //    this.animacion = new SimpleImageProperty ("Logo.jpg");
    }
    
    public String getRuta() {
        return ruta.get();
    }

    public void setRuta(String firstName) {
        this.ruta.set(firstName);
    }

    public StringProperty Ruta() {
        return ruta;
    }
    /*
    public String getAnima() {
        return animacion.getImage();
    }

    public void setAnima(ImageView animacion) {
        this.animacion.setImage(Image "Logo.jpg");
    }

    public StringProperty animaProperty() {
        return animacion;
    }
    */
}
