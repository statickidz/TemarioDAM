package com.statickidz.trackio.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

/**
 * Model class for a Track.
 * 
 * @author Adrián Barrio Andrés
 * @email statickidz@gmail.com
 * @web https://statickidz.com
 */
public class Track {

    private StringProperty fileName;
    private StringProperty path;
    private Media media;
    private StringProperty album;
    private StringProperty artist;
    private StringProperty title;
    private StringProperty year;
    private Image image;

    /**
     * Default constructor.
     */
    public Track() {
        this(null, null, null);
    }

    /**
     * Constructor with some initial data.
     *
     * @param fileName
     * @param filePath
     * @param media
     */
    public Track(String fileName, String filePath, Media media) {
        this.fileName = new SimpleStringProperty(fileName);
        this.path = new SimpleStringProperty(filePath);
        this.media = media;
    }

    /**
     * @return the fileName
     */
    public StringProperty getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(StringProperty fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the media
     */
    public Media getMedia() {
        return media;
    }

    /**
     * @param media the media to set
     */
    public void setMedia(Media media) {
        this.media = media;
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
     * @return the album
     */
    public StringProperty getAlbum() {
        return album;
    }

    /**
     * @param album the album to set
     */
    public void setAlbum(StringProperty album) {
        this.album = album;
    }

    /**
     * @return the artist
     */
    public StringProperty getArtist() {
        return artist;
    }

    /**
     * @param artist the artist to set
     */
    public void setArtist(StringProperty artist) {
        this.artist = artist;
    }

    /**
     * @return the title
     */
    public StringProperty getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(StringProperty title) {
        this.title = title;
    }

    /**
     * @return the year
     */
    public StringProperty getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(StringProperty year) {
        this.year = year;
    }

    /**
     * @return the image
     */
    public Image getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(Image image) {
        this.image = image;
    }
    
    

}
