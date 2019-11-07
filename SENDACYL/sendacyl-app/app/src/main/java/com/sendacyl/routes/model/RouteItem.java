package com.sendacyl.routes.model;

import java.io.Serializable;

public class RouteItem implements Serializable {

    //Auto generated
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String date;
    private String dateState;
    private String url;
    private String description;
    private String thumbnailUrl;
    private String difficulty;
    private String distance;
    private String time;
    private String kmlUrl;
    private String attachmentUrl;
    private String author;
    private String province;
    private String locality;
    private String streetViewId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getDateState() {
        return dateState;
    }

    public void setDateState(String dateState) {
        this.dateState = dateState;
    }

    public String getKmlUrl() {
        return kmlUrl;
    }

    public void setKmlUrl(String kmlUrl) {
        this.kmlUrl = kmlUrl;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    @Override
    public String toString() {
        return "[ title=" + name + ", date=" + date + "]";
    }

    public String getStreetViewId() {
        return streetViewId;
    }

    public void setStreetViewId(String streetViewId) {
        this.streetViewId = streetViewId;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDistance() {
        return distance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
