package com.statickidz.trackio.util;

import com.statickidz.trackio.model.TrackList;
import java.util.prefs.Preferences;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 * Utilities to deal with Tracklists.
 *
 * @author Adrián Barrio Andrés
 * @email statickidz@gmail.com
 * @web https://statickidz.com
 */
public class TrackListUtil {

    private static final String TRACKLIST_NODE = "trackio.tracklists";
    private static final String TRACKLIST_NUMBER = "TRACKLIST_NUMBER";
    private static final String TRACKLIST_NAME = "TRACKLIST_";
    private static final String TRACKLIST_PATH = "TRACKLIST_PATH_";
    
    private static final Preferences prefs = Preferences.userRoot().node(TRACKLIST_NODE);

    /**
     * Save tracklist in preferences.
     *
     * @param trackList
     */
    public static void saveTrackList(TrackList trackList) {
        int trackListNumber = getTrackListsNumber() + 1;
        prefs.put(TRACKLIST_NAME + trackListNumber, trackList.getName().getValue());
        prefs.put(TRACKLIST_PATH + trackListNumber, trackList.getPath().getValue());
        prefs.putInt(TRACKLIST_NUMBER, trackListNumber);
    }
    
    /**
     * Get number of tracklists.
     *
     * @return int
     */
    public static int getTrackListsNumber() {
        return prefs.getInt(TRACKLIST_NUMBER, 0);
    }
    
    /**
     * Get all tracklists stored in user preferences.
     *
     * @return List
     */
    public static ObservableList<TrackList> getAll() {
        
        ObservableList<TrackList> trackLists = FXCollections.observableArrayList();
        
        for(int index = 1; index <= getTrackListsNumber(); index++) {
            String listName = prefs.get(TRACKLIST_NAME + index, null);
            String listPath = prefs.get(TRACKLIST_PATH + index, null);
            TrackList trackList = new TrackList(index, listName, listPath);
            trackLists.add(trackList);
        }

        return trackLists;
    }
    
    /**
     * Remove all tracklists stored in user preferences.
     *
     */
    public static void deleteAll() {
        for(int index = 0; index < getTrackListsNumber(); index++) {
            prefs.remove(TRACKLIST_NAME + index);
            prefs.remove(TRACKLIST_PATH + index);
            prefs.putInt(TRACKLIST_NUMBER, 0);
        }
    }
    
    /**
     * Remove single tracklist.
     *
     * @param trackList
     */
    public static void delete(TrackList trackList) {
        prefs.remove(TRACKLIST_NAME + trackList.getId().getValue());
        prefs.remove(TRACKLIST_PATH + trackList.getId().getValue());
        prefs.putInt(TRACKLIST_NUMBER, prefs.getInt(TRACKLIST_NUMBER, 0) - 1);
        
    }
    
    /**
     * Refresh table columns util
     *
     * @param listView
     */
    public static void refreshList(ListView listView) {
        ObservableList<TrackList> items = getAll();
        listView.setItems(null);
        listView.setItems(items);
    }
    
}