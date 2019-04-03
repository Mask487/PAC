/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacapp;

import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author andrewmenezes
 */
public class PacApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        AnchorPane root2 = new AnchorPane();
        Scene primary = new Scene(root2);

        primary.fillProperty().set(Paint.valueOf("505050"));
        stage.setScene(primary);
        root2.setMinSize(720, 480);
        //root2.setMaxSize(1600,900);

        Insets bFillIn = new Insets(0);
        CornerRadii bFillCR = new CornerRadii(0);

        BorderPane bp = new BorderPane();
        root2.getChildren().add(bp);

        AnchorPane leftButtonPane = new AnchorPane();
        AnchorPane topAnchorPane = new AnchorPane();
        AnchorPane bottomAnchorPane = new AnchorPane();
        AnchorPane centerAnchorPane = new AnchorPane();
        AnchorPane backAnchor = new AnchorPane();
        AnchorPane tAnchor = new AnchorPane();
        StackPane mainStack = new StackPane();
        VBox musicCont = new VBox();
        ScrollPane musicPane = new ScrollPane(musicCont);
        VBox bookCont = new VBox();
        ScrollPane bookPane = new ScrollPane(bookCont);
        VBox audioBookCont = new VBox();
        ScrollPane audioBookPane = new ScrollPane(audioBookCont);
        VBox podcastCont = new VBox();
        ScrollPane podcastPane = new ScrollPane(podcastCont);
        VBox videoCont = new VBox();
        ScrollPane videoPane = new ScrollPane(videoCont);
        TilePane appCont = new TilePane();
        ScrollPane appPane = new ScrollPane(appCont);
        VBox settingsList = new VBox();

        //create background anchor
        root2.setRightAnchor(bp, 0.0);
        root2.setLeftAnchor(bp, 0.0);
        root2.setBottomAnchor(bp, 0.0);
        root2.setTopAnchor(bp, 0.0);
        BackgroundFill stageFill = new BackgroundFill(Paint.valueOf("505050"), bFillCR, bFillIn);
        Background stageBackFill = new Background(stageFill);
        root2.backgroundProperty().set(stageBackFill);

        //Left panel Color
        BackgroundFill lBgFill = new BackgroundFill(Paint.valueOf("454545"), bFillCR, bFillIn);
        Background lPaneColor = new Background(lBgFill);
        Insets inset = new Insets(-1.0);
        leftButtonPane.backgroundProperty().set(lPaneColor);

        //Button panel Color
        BackgroundFill buFill = new BackgroundFill(Paint.valueOf("TRANSPARENT"), bFillCR, bFillIn);
        Background buBack = new Background(buFill);

        //CenterPane preferences
        BackgroundFill cenFill = new BackgroundFill(Paint.valueOf("414141"), bFillCR, bFillIn);
        Background cenBack = new Background(cenFill);
        centerAnchorPane.setBackground(cenBack);

        // Sidebar Button for the Music Section
        Image musicIconP = new Image("MusicIcon.png");   //Load Music Icon for imageview
        ImageView musicIcon = new ImageView();
        musicIcon.setImage(musicIconP);                  //adds icon to imageview           
        Button music = new Button("", musicIcon);       //Creates button with image
        music.backgroundProperty().set(buBack);         //adds transparent background
        music.setPadding(inset);

        // Sidebar Button for the Books Section
        Image bookIconP = new Image("BookIcon.png");   //Load book Icon for imageview
        ImageView bookIcon = new ImageView();
        bookIcon.setImage(bookIconP);                  //adds icon to imageview           
        Button book = new Button("", bookIcon);       //Creates button with image
        book.backgroundProperty().set(buBack);         //adds transparent background
        book.setPadding(inset);

        // Sidebar Button for the Audio Books Section
        Image aBookIconP = new Image("AudioBook.png");   //Load audio book Icon for imageview
        ImageView aBookIcon = new ImageView();
        aBookIcon.setImage(aBookIconP);                  //adds icon to imageview           
        Button aBook = new Button("", aBookIcon);       //Creates button with image
        aBook.backgroundProperty().set(buBack);         //adds transparent background
        aBook.setPadding(inset);

        // Sidebar Button for the podcasts Section
        Image podcastIconP = new Image("PodcastIcon.png");   //Load podcasts Icon for imageview
        ImageView podcastIcon = new ImageView();
        podcastIcon.setImage(podcastIconP);                  //adds icon to imageview           
        Button podcast = new Button("", podcastIcon);       //Creates button with image
        podcast.backgroundProperty().set(buBack);         //adds transparent background
        podcast.setPadding(inset);

        // Sidebar Button for the video Section
        Image videoIconP = new Image("VideoIcon.png");   //Load video Icon for imageview
        ImageView videoIcon = new ImageView();
        videoIcon.setImage(videoIconP);                  //adds icon to imageview           
        Button video = new Button("", videoIcon);       //Creates button with image
        video.backgroundProperty().set(buBack);         //adds transparent background
        video.setPadding(inset);

        // Sidebar Button for the apps Section
        Image appsIconP = new Image("AppsIcon.png");   //Load apps Icon for imageview
        ImageView appsIcon = new ImageView();
        appsIcon.setImage(appsIconP);                  //adds icon to imageview           
        Button apps = new Button("", appsIcon);       //Creates button with image
        apps.backgroundProperty().set(buBack);         //adds transparent background
        apps.setPadding(inset);

        Image phoneIcon = new Image("Phone.png");   //Load Phone Icon for imageview
        ImageView phone = new ImageView();
        phone.setImage(phoneIcon);                  //adds icon to imageview           
        Button phoneB = new Button("", phone);       //Creates button with image
        phoneB.backgroundProperty().set(buBack);         //adds transparent background
        phoneB.setPadding(inset);

        Image settingsIcon = new Image("Settings.png");   //Load Phone Icon for imageview
        ImageView settings = new ImageView();
        settings.setImage(settingsIcon);                  //adds icon to imageview           
        Button settingsB = new Button("", settings);       //Creates button with image
        settingsB.backgroundProperty().set(buBack);         //adds transparent background
        settingsB.setPadding(inset);

        //Create vertical box to store side buttons
        BackgroundFill vbox = new BackgroundFill(Paint.valueOf("454545"), bFillCR, bFillIn);
        Background vfill = new Background(vbox);
        VBox vbutt = new VBox();
        vbutt.backgroundProperty().set(vfill);
        vbutt.setPadding(new Insets(5, 5, 5, 5));
        vbutt.setSpacing(5);

        VBox bvbutt = new VBox();
        bvbutt.backgroundProperty().set(vfill);
        bvbutt.setPadding(new Insets(5, 5, 5, 5));
        bvbutt.setSpacing(5);

        //testing colors
        BackgroundFill testFill = new BackgroundFill(Paint.valueOf("Green"), bFillCR, bFillIn);
        Background testBack = new Background(testFill);

        //Create Horizontal Box for top border and Search
        HBox hSearch = new HBox();
        hSearch.setPadding(new Insets(5, 5, 5, 5));
        TextField search = new TextField("");
        search.setPromptText("Search");
        search.backgroundProperty().set(buBack);
        hSearch.getChildren().add(search);

//        AnchorPane bottomPadL = new AnchorPane();
//        bottomPadL.backgroundProperty().set(testBack);
//        bottomPadL.setMinWidth(300);
//        AnchorPane bottomPadR = new AnchorPane();
//        bottomPadR.backgroundProperty().set(testBack);
//        bottomPadR.setMinWidth(300);
        //Create horizontal Box for bottom controlls
        HBox bottomButt = new HBox();
        bottomButt.setPadding(new Insets(5));
        ProgressBar mainProg = new ProgressBar(0); //setProgress()
        bottomButt.getChildren().addAll(mainProg);

        music.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                mainStack.getChildren().clear();//(phoneMidRow, musicPane);
                mainStack.getChildren().add(musicPane);
                System.out.println("Music Pressed");

            }
        });

        book.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                mainStack.getChildren().clear();
                mainStack.getChildren().add(bookPane);
                System.out.println("Book Pressed");

            }
        });

        aBook.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                mainStack.getChildren().clear();
                mainStack.getChildren().add(audioBookPane);
                System.out.println("Audio Books Pressed");

            }
        });

        podcast.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                mainStack.getChildren().clear();
                mainStack.getChildren().add(podcastPane);
                System.out.println("podcast Pressed");

            }
        });

        video.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                mainStack.getChildren().clear();
                mainStack.getChildren().add(videoPane);
                System.out.println("video Pressed");

            }
        });

        apps.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                mainStack.getChildren().clear();
                mainStack.getChildren().add(appPane);
                System.out.println("apps Pressed");

            }
        });

        settingsB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                mainStack.getChildren().clear();
                mainStack.getChildren().add(settingsList);
                System.out.println("settings Pressed");

            }
        });

        vbutt.getChildren().addAll(music, book, aBook, podcast, video, apps, phoneB);
        bvbutt.getChildren().addAll(settingsB);

        // Create music pane
        musicCont.backgroundProperty().set(cenBack);  
        musicPane.backgroundProperty().set(cenBack); 
        musicPane.setFitToWidth(true);
        musicPane.setFitToHeight(true);
        musicPane.setPannable(false);

        // Create book pane
        bookCont.backgroundProperty().set(cenBack);  
        bookPane.backgroundProperty().set(cenBack);  
        bookPane.setFitToWidth(true);
        bookPane.setFitToHeight(true);
        bookPane.setPannable(false);
        Label noBook = new Label("You have no Books \n import Books to get started.");
        
        noBook.backgroundProperty().set(cenBack);
        bookCont.getChildren().addAll(noBook);

        // Create audio book pane
        audioBookCont.backgroundProperty().set(cenBack);  
        audioBookPane.backgroundProperty().set(cenBack); 
        audioBookPane.setFitToWidth(true);
        audioBookPane.setFitToHeight(true);
        audioBookPane.setPannable(false);
        Label noAudioBook = new Label("You have no audio books \n import audio Books to get started.");
        noAudioBook.backgroundProperty().set(buBack);
        audioBookCont.getChildren().addAll(noAudioBook);

        // Create app pane
        appCont.backgroundProperty().set(cenBack);  
        appPane.backgroundProperty().set(cenBack); 
        appPane.setFitToWidth(true);
        appPane.setFitToHeight(true);
        appPane.setPannable(false);
        Label noApp = new Label("You have no apps \n import apps to get started.");
        noApp.setTextFill(Paint.valueOf("101010"));
        noApp.backgroundProperty().set(buBack);
        appCont.getChildren().addAll(noApp);

        // Create podcast pane
        podcastCont.backgroundProperty().set(cenBack);  
        podcastPane.backgroundProperty().set(cenBack); 
        podcastPane.setFitToWidth(true);
        podcastPane.setFitToHeight(true);
        podcastPane.setPannable(false);
        Label nopodcast = new Label("You have no podcasts \n import podcasts to get started.");
        nopodcast.backgroundProperty().set(buBack); 
        podcastCont.getChildren().addAll(nopodcast);

        // Create video pane
        videoCont.backgroundProperty().set(cenBack);  
        videoPane.backgroundProperty().set(cenBack); 
        videoPane.setFitToWidth(true);
        videoPane.setFitToHeight(true);
        videoPane.setPannable(false);
        Label noVideo = new Label("You have no videos \n import videos to get started.");
        noVideo.backgroundProperty().set(buBack); 
        videoCont.getChildren().addAll(noVideo);

        // create settings VBox
        Button lightMode = new Button("Change to light mode.");       //Creates button 
        lightMode.backgroundProperty().set(buBack);         //adds transparent background
        lightMode.setTextFill(Paint.valueOf("D146FF"));
        lightMode.setPadding(inset);

        lightMode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                Alert lMode = new Alert(Alert.AlertType.WARNING);
                lMode.setTitle("Suspicious activity detected");
                lMode.setHeaderText("Light Mode Avoided.");
                //lMode.setContentText('h');
                lMode.showAndWait();
                System.out.println("lightMode Pressed");

            }
        });
        settingsList.getChildren().add(lightMode);

        // Create Phone Transfer Section
        Image androidImage = new Image("Android.png");   //Load video Icon for imageview
        ImageView Android = new ImageView();
        Android.setImage(androidImage);
        Android.setFitWidth(200);
        Android.setPreserveRatio(true);
        Android.setSmooth(true);
        Android.setCache(true);

        HBox phoneMidRow = new HBox();
        phoneMidRow.setPadding(new Insets(5, 5, 5, 5));
        phoneMidRow.setSpacing(50);

        Button sync = new Button("Sync Phone");       //Creates button 
        sync.setTextFill(Paint.valueOf("D146FF"));
        sync.backgroundProperty().set(buBack);         //adds transparent background
        sync.setPadding(inset);

        sync.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {

                System.out.println("Sync Pressed");

            }
        });

        Button backup = new Button("Backup Phone");       //Creates button 
        backup.setTextFill(Paint.valueOf("D146FF"));
        backup.backgroundProperty().set(buBack);         //adds transparent background
        backup.setPadding(inset);

        backup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {

                System.out.println("backup Pressed");

            }
        });

        Button copy = new Button("Duplicate Phone");       //Creates button 
        copy.setTextFill(Paint.valueOf("D146FF"));
        copy.backgroundProperty().set(buBack);         //adds transparent background

//        copy.setOnMouseEntered(new EventHandler<MouseEvent>() {
//            @Override
//            public void enter(MouseEvent enter) {
//
//                System.out.println("copy entered");
//
//            }
//        });
        copy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {

                System.out.println("copy Pressed");

            }
        });

        Button android = new Button("Duplicate Phone", Android);
        copy.backgroundProperty().set(buBack);

        VBox midButt = new VBox();
        midButt.setPadding(new Insets(5, 5, 5, 5));
        midButt.setSpacing(50);
        midButt.getChildren().addAll(sync, backup, copy);

        phoneB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                mainStack.getChildren().clear();//(phoneMidRow, musicPane);
                mainStack.getChildren().add(phoneMidRow);
                System.out.println("Phone Pressed");

            }
        });

        mainStack.getChildren().setAll(phoneMidRow, musicPane);
        tAnchor.getChildren().addAll(mainStack);
        tAnchor.setRightAnchor(mainStack, 25.0);// area around
        tAnchor.setLeftAnchor(mainStack, 25.0);// area around
        tAnchor.setBottomAnchor(mainStack, 25.0);// area around
        tAnchor.setTopAnchor(mainStack, 25.0);// area around

        // test buttons
        Button buttonSave = new Button("right");
        Button buttonCancel = new Button("left");
        Button buttonTop = new Button("Top");

        //MusicPane
        Label noMusic = new Label("You have no Music \n import music to get started.");
        noMusic.backgroundProperty().set(buBack);
        musicCont.getChildren().addAll(noMusic);

        phoneMidRow.getChildren().addAll(Android, midButt);
        // Button buttonbottom = new Button("bottom");
        centerAnchorPane.getChildren().addAll(tAnchor);
        centerAnchorPane.setRightAnchor(tAnchor, 5.0);
        centerAnchorPane.setLeftAnchor(tAnchor, 5.0);
        centerAnchorPane.setTopAnchor(tAnchor, 5.0);
        centerAnchorPane.setBottomAnchor(tAnchor, 5.0);

        bottomAnchorPane.getChildren().addAll(bottomButt);
        bottomAnchorPane.setRightAnchor(bottomButt, 5.0);
        bottomAnchorPane.setLeftAnchor(bottomButt, 5.0);
        bottomAnchorPane.setTopAnchor(bottomButt, .0);
        bottomAnchorPane.setBottomAnchor(bottomButt, .0);
        leftButtonPane.getChildren().addAll(vbutt, bvbutt);
        topAnchorPane.getChildren().addAll(hSearch);
        leftButtonPane.setTopAnchor(vbutt, 0.0);
        leftButtonPane.setBottomAnchor(bvbutt, 0.0);
        topAnchorPane.setRightAnchor(hSearch, 0.0);
        bp.setLeft(leftButtonPane);
        bp.setTop(topAnchorPane);
        bp.setBottom(bottomAnchorPane);
        bp.setCenter(centerAnchorPane);

        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public static void musButt() {
        
    }
    public static void podButt() {
        
    }
    public static void ebkButt() {
        
    }
    public static void abkButt() {
        
    }
    public static void vidButt() {
        
    }
    public static void infButt() {
        
    }
    

}