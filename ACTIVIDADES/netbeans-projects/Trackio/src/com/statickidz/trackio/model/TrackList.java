package com.statickidz.trackio.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a TrackList.
 * 
 * @author Adrián Barrio Andrés
 * @email statickidz@gmail.com
 * @web https://statickidz.com
 */
public class TrackList {
    
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty path;

    /**
     * Default constructor.
     */
    public TrackList() {
        this(0, null, null);
    }

    /**
     * Constructor with some initial data.
     * 
     * @param id
     * @param name
     * @param path
     */
    public TrackList(Integer id, String name, String path) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.path = new SimpleStringProperty(path);
    }

    /**
     * @return the name
     */
    public StringProperty getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(StringProperty name) {
        this.name = name;
    }

    /**
     * @return the path
     */
    public StringProperty getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(StringProperty path) {
        this.path = path;
    }

    /**
     * @return the id
     */
    public IntegerProperty getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(IntegerProperty id) {
        this.id = id;
    }
    
}