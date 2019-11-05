package com.statickidz.trackio.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import com.statickidz.trackio.MainApp;
import com.statickidz.trackio.model.Track;
import com.statickidz.trackio.model.TrackList;
import com.statickidz.trackio.util.TrackListUtil;
import com.statickidz.trackio.util.EffectUtil;
import com.statickidz.trackio.util.TrackUtil;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.InputStream;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableRow;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.text.TextAlignment;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * Player controller.
 *
 * @author Adrián Barrio Andrés
 * @email statickidz@gmail.com
 * @web https://statickidz.com
 */
public class PlayerController {
    
    //Tracklists list
    @FXML
    private ListView<TrackList> trackListView;
    private ObservableList<TrackList> observableTrackListsView;
    
    //Tracks table
    @FXML
    private TableView<Track> trackTableView;
    private ObservableList<Track> observableTracksView;
    @FXML
    private TableColumn<Track, String> titleColumn;
    @FXML
    private TableColumn<Track, String> authorColumn;
    @FXML
    private TableColumn<Track, String> albumColumn;
    @FXML
    private TableColumn<Track, String> yearColumn;
    
    //Toolbar
    @FXML
    private ToolBar mainToolBar;
    @FXML
    private ImageView maximizeImageView;
    
    //Window vars
    private boolean maximized = false;
    
    //Player info
    @FXML
    private ProgressBar progressBar;
    private ChangeListener<Duration> progressChangeListener;
    @FXML
    private ImageView albumImageView;
    @FXML
    private ImageView albumImageViewLeft;
    @FXML
    private ImageView albumImageViewRight;
    @FXML
    private Label titleLabel;
    @FXML
    private Label artistLabel;
    @FXML
    private Label timeLabel;

    // Reference to the main application
    private MainApp mainApp;
  
    //Initialize player and current media
    private MediaPlayer player;
    private Track currentTrack;
    private Track prevTrack;
    private Track nextTrack;
    private Media currentMedia;
    
    //Player controls
    @FXML
    private Button playButton;
    @FXML
    private FontAwesomeIconView playButtonIcon;
    

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     * 
     */
    public PlayerController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * 
     */
    @FXML
    private void initialize() {
        
        // Add drag listener to toolbar
        EffectUtil.addDragListeners(mainToolBar);
        
        // Initialize trackslists and tracks
        setupTrackListView();
        setupTrackTableView();
        
        // Handle maximize window on double click on toolbar
        mainToolBar.setOnMouseClicked((MouseEvent click) -> {
            if (click.getClickCount() == 2) {
                handleMaximize();
            }
        });

    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Trigger play and pause button
     * 
     */
    @FXML
    private void handlePlayTrigger() {
        if(player != null) {
            boolean playing = player.getStatus().equals(Status.PLAYING);
            if(playing) {
                player.pause();
            } else {
                player.play();
            }
        }
    }

    /**
     * Called when the user clicks on the next song button.
     * 
     */
    @FXML
    private void handleNextTrack() {
        if(nextTrack != null) playTrack(nextTrack);
    }
    
    /**
     * Called when the user clicks on the prev song button.
     * 
     */
    @FXML
    private void handlePrevTrack() {
        if(prevTrack != null) playTrack(prevTrack);
    }

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     * 
     */
    @FXML
    private void handleNewTrackList() {
        TrackList tempTrackList = new TrackList();
        boolean okClicked = mainApp.showTrackListDialog(tempTrackList);
        if (okClicked) {
            TrackListUtil.saveTrackList(tempTrackList);
            observableTrackListsView.add(tempTrackList);
        }
    }
    
    /**
     * Custom close button
     * 
     */
    @FXML
    private void handleClose() {
        Platform.exit();
        System.exit(0);
    }
    
    /**
     * Custom mazimize button
     * 
     */
    @FXML
    private void handleMaximize() {
        if(maximized) {
            maximized = false;
            mainApp.getPrimaryStage().setMaximized(false);
            mainApp.getPrimaryStage().setMaxWidth(1040);
            mainApp.getPrimaryStage().setMaxHeight(650);
            mainApp.getPrimaryStage().centerOnScreen();
            
            InputStream minimizeImage = PlayerController.class.getResourceAsStream("img/top-square.png");
            maximizeImageView.setImage(new Image(minimizeImage));
        } else {
            maximized = true;
            mainApp.getPrimaryStage().setMaximized(true);
            
            InputStream maximizeImage = PlayerController.class.getResourceAsStream("img/top-shrink.png");
            maximizeImageView.setImage(new Image(maximizeImage));
        }
    }
    
    /**
     * Custom minimize button
     * 
     */
    @FXML
    private void handleMinimize() {
        mainApp.getPrimaryStage().setIconified(true);
    }
    
    /**
     * Show alert with credits
     * 
     */
    @FXML
    private void handleCredits() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Créditos");
        alert.setHeaderText(null);
        alert.setContentText("Adrián Barrio Andrés\r\nhttps://statickidz.com/");

        alert.showAndWait();
    }

    /**
     * Setup tracklist cells, observers and actions
     * 
     */
    private void setupTrackListView() {
        
        observableTrackListsView = TrackListUtil.getAll();
        trackListView.setItems(observableTrackListsView);
        
        // Set custom cell view
        trackListView.setCellFactory((ListView<TrackList> p) -> {
            ListCell<TrackList> cell = new ListCell<TrackList>() {
                @Override
                protected void updateItem(TrackList trackList, boolean bln) {
                    super.updateItem(trackList, bln);
                    if (trackList != null) {
                        FontAwesomeIconView playIcon = new FontAwesomeIconView();
                        playIcon.setIcon(FontAwesomeIcon.PLAY);
                        playIcon.setStyleClass("tracklist-play-icon");
                        setGraphic(playIcon);
                        setText("  "+trackList.getName().getValue());
                    }
                }
            };
            return cell;
        });
        
        // Handle edit tracklist on double click
        trackListView.setOnMouseClicked((MouseEvent click) -> {
            if (click.getClickCount() == 2) {
                // Use ListView's getSelected Item
                TrackList selectedTrackList = trackListView.getSelectionModel().getSelectedItem();
                if (selectedTrackList != null) {
                    boolean okClicked = mainApp.showTrackListDialog(selectedTrackList);
                    if (okClicked) {
                        TrackListUtil.saveTrackList(selectedTrackList);
                        observableTrackListsView.add(selectedTrackList);
                    }
                    Platform.runLater(() -> {
                        TrackListUtil.refreshList(trackListView);
                        TrackUtil.refreshTable(trackTableView);
                    });
                }
            }
        });
         
        // Listen for selection changes and show the tracklist tracks
        trackListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showTrackListDetails(newValue));
    }
    
    /**
     * Setup tracks cells, observers and actions
     * 
     */
    private void setupTrackTableView() {
        // Initialize the tracks table with the columns
        titleColumn.setCellValueFactory(
                cellData -> cellData.getValue().getTitle());
        authorColumn.setCellValueFactory(
                cellData -> cellData.getValue().getArtist());
        albumColumn.setCellValueFactory(
                cellData -> cellData.getValue().getAlbum());
        yearColumn.setCellValueFactory(
                cellData -> cellData.getValue().getYear());
        
        trackTableView.setRowFactory( tv -> {
            TableRow<Track> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Track track = row.getItem();
                    playTrack(track);
                }
            });
            return row ;
        });
        
    }
    
    /**
     * Show tracklist tracks inside a tracklist
     *
     * @param TrackList
     */
    private void showTrackListDetails(TrackList trackList) {
        Platform.runLater(() -> {
            observableTracksView = TrackUtil.getAll(trackList, trackTableView);
            trackTableView.setItems(observableTracksView);
        });
        
        Platform.runLater(() -> {
            TrackUtil.refreshTable(trackTableView);
        });
    }
    
    /**
     * Play track on player.
     *
     * @param track
     */
    private void playTrack(Track track) {
        
        // Set next, current and previous track
        currentTrack = track;
        int totalTracks = observableTracksView.size();
        int currentTrackNumber = observableTracksView.indexOf(currentTrack);
        int nextTrackNumber = currentTrackNumber+1;
        int prevTrackNumber = currentTrackNumber-1;
        if(prevTrackNumber >= 0) prevTrack = observableTracksView.get(prevTrackNumber);
        else prevTrack = observableTracksView.get(totalTracks-1);
        if(nextTrackNumber < totalTracks) nextTrack = observableTracksView.get(nextTrackNumber);
        else nextTrack = observableTracksView.get(0);
        
        // Set selected item in tracktable
        trackTableView.getSelectionModel().select(currentTrack);
        
        // Play track and set media info
        if (player != null) {
            player.stop();
            player = null;
        }
        currentMedia = currentTrack.getMedia();
        player = new MediaPlayer(currentMedia);
        player.play();
        setCurrentlyPlaying(player);
        setMediaInfo(currentTrack);
    }
    
    /**
     * Set all listeners to UI on play.
     *
     * @param mediaPlayer
     */
    private void setCurrentlyPlaying(MediaPlayer mediaPlayer) {
        mediaPlayer.seek(Duration.ZERO);
        progressBar.setProgress(0);
        
        // Add progressbar click listener to change song position
        progressBar.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton() == MouseButton.PRIMARY){
                Bounds b1 = progressBar.getLayoutBounds();
                double mouseX = event.getSceneX();
                double percent = (((b1.getMinX() + mouseX ) * 100) / b1.getMaxX());
                percent -= 2;
                double totalDurationMillis = mediaPlayer.getTotalDuration().toMillis();
                double seek = (totalDurationMillis * percent) / 100;
                mediaPlayer.seek(Duration.millis(seek));
            }
        });
        
        // Add progressbar listener to show current song percent
        progressChangeListener = (ObservableValue<? extends Duration> observableValue,
                 Duration oldValue, Duration newValue) -> {
            double currentTimeMillis = mediaPlayer.getCurrentTime().toMillis();
            double totalDurationMillis = mediaPlayer.getTotalDuration().toMillis();
            progressBar.setProgress(1.0 * currentTimeMillis / totalDurationMillis);
            
            // Set time count in label
            double currentTimeSeconds = mediaPlayer.getCurrentTime().toSeconds();
            int minutes = (int) (currentTimeSeconds % 3600) / 60;
            int seconds = (int) currentTimeSeconds % 60;
            String formattedMinutes = String.format("%02d", minutes);
            String formattedSeconds = String.format("%02d", seconds);
            timeLabel.setText(formattedMinutes + ":" + formattedSeconds);
            
        };
        mediaPlayer.currentTimeProperty().addListener(progressChangeListener);
        
        //Add player action on finish
        player.setOnEndOfMedia(() -> {
            player.currentTimeProperty().removeListener(progressChangeListener);
            handleNextTrack();
        });
        
        // Add player action on pause
        player.setOnPaused(() -> {
            playButtonIcon.setIcon(FontAwesomeIcon.PLAY);
            playButtonIcon.setSize("30");
            playButton.setStyle("-fx-padding: 5 22 5 29;");
        });
        
        // Add player action on playing
        player.setOnPlaying(() -> {
            playButtonIcon.setIcon(FontAwesomeIcon.PAUSE);
            playButtonIcon.setSize("25");
            playButton.setStyle("-fx-padding: 5 22 5 24;");
        });
    }
    
    /**
     * Set song info on UI and image transitions.
     *
     * @param track
     */
    private void setMediaInfo(Track track) {
        titleLabel.setText(track.getTitle().getValue());
        Tooltip titleTooltip = new Tooltip(track.getTitle().getValue() + "\r" + track.getArtist().getValue().toUpperCase());
        titleTooltip.setTextAlignment(TextAlignment.CENTER);
        titleLabel.setTooltip(titleTooltip);
        artistLabel.setText(track.getArtist().getValue().toUpperCase());
        
        InputStream defaultAlbumStream = PlayerController.class.getResourceAsStream("img/noalbumart.png");
        Image defaultAlbumImage = new Image(defaultAlbumStream);
        InputStream defaultAlbumStreamLeft = PlayerController.class.getResourceAsStream("img/noalbumart.png");
        Image defaultAlbumImageLeft = new Image(defaultAlbumStreamLeft);
        InputStream defaultAlbumStreamRight = PlayerController.class.getResourceAsStream("img/noalbumart.png");
        Image defaultAlbumImageRight = new Image(defaultAlbumStreamRight);
        
        if(track.getImage() == null) {
            SequentialTransition transitionImageView = 
                    EffectUtil.fadeTransition(albumImageView, defaultAlbumImage);
            transitionImageView.play();
        } else {
            SequentialTransition transitionImageView = 
                    EffectUtil.fadeTransition(albumImageView, track.getImage());
            transitionImageView.play();
        }
        
        if(nextTrack == null || nextTrack.getImage() == null) {
            SequentialTransition transitionImageView = 
                    EffectUtil.translateTransition(albumImageViewRight, defaultAlbumImageLeft, 0, 400);
            transitionImageView.play();
        } else {
            SequentialTransition transitionImageView = 
                    EffectUtil.translateTransition(albumImageViewRight, nextTrack.getImage(), 0, 400);
            transitionImageView.play();
        }
        
        if(prevTrack == null || prevTrack.getImage() == null) {
            SequentialTransition transitionImageView = 
                    EffectUtil.translateTransition(albumImageViewLeft, defaultAlbumImageRight, 0, -400);
            transitionImageView.play();
        } else {
            SequentialTransition transitionImageView = 
                    EffectUtil.translateTransition(albumImageViewLeft, prevTrack.getImage(), 0, -400);
            transitionImageView.play();
        }
        
    }

}
