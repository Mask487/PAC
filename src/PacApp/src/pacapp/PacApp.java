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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ProgressBar;
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
        root2.setMinSize(200, 270);
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

        //Create vertical box to store side buttons
        BackgroundFill vbox = new BackgroundFill(Paint.valueOf("454545"), bFillCR, bFillIn);
        Background vfill = new Background(vbox);
        VBox vbutt = new VBox();
        vbutt.backgroundProperty().set(vfill);
        vbutt.setPadding(new Insets(5, 5, 5, 5));
        vbutt.setSpacing(5);

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

                System.out.println("Music Pressed");

            }
        });

        book.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {

                System.out.println("Book Pressed");

            }
        });

        aBook.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {

                System.out.println("Audio Books Pressed");

            }
        });

        podcast.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {

                System.out.println("podcast Pressed");

            }
        });

        video.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {

                System.out.println("video Pressed");

            }
        });

        apps.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {

                System.out.println("apps Pressed");

            }
        });

        vbutt.getChildren().addAll(music, book, aBook, podcast, video, apps);
        // test buttons
        Button buttonSave = new Button("right");
        Button buttonCancel = new Button("left");
        Button buttonTop = new Button("Top");
       // Button buttonbottom = new Button("bottom");
        centerAnchorPane.getChildren().addAll(buttonSave, buttonCancel, buttonTop, Prefill.buttonbottom);
        centerAnchorPane.setRightAnchor(buttonCancel, 5.0);
        centerAnchorPane.setLeftAnchor(buttonSave, 5.0);
        centerAnchorPane.setTopAnchor(buttonTop, 5.0);
        centerAnchorPane.setBottomAnchor(Prefill.buttonbottom, 5.0);

        bottomAnchorPane.getChildren().addAll(bottomButt);
        bottomAnchorPane.setRightAnchor(bottomButt,5.0);
        bottomAnchorPane.setLeftAnchor(bottomButt,5.0);
        bottomAnchorPane.setTopAnchor(bottomButt,.0);
        bottomAnchorPane.setBottomAnchor(bottomButt,.0);
        leftButtonPane.getChildren().addAll(vbutt);
        topAnchorPane.getChildren().addAll(hSearch);
        leftButtonPane.setTopAnchor(vbutt, 0.0);
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

}