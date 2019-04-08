/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacapp;

import java.io.File;

import NewDatabase.SQLTranslator;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import NewDatabase.ContentDAO;
import NewDatabase.Content;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author andrewmenezes
 */
public class PacApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        SQLTranslator sql = new SQLTranslator();
        Transfer t = new Transfer();
        t.initializeDesk();

        AnchorPane root2 = new AnchorPane();
        Scene primary = new Scene(root2);

        primary.fillProperty().set(Paint.valueOf("505050"));
        stage.setScene(primary);
        root2.setMinSize(750, 510);
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
        HBox musicControll = new HBox(10);
        HBox videoControll = new HBox(10);
        HBox podcastControll = new HBox(10);
        HBox ebookControll = new HBox(10);
        HBox audioBoookControll = new HBox(10);
        HBox phoneProgress = new HBox(10);
        Button throwaway = new Button("");
        VBox phoneStack = new VBox();



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


        throwaway.backgroundProperty().set(buBack);

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





        music.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                mainStack.getChildren().clear();//(phoneMidRow, musicPane);
                mainStack.getChildren().add(musicPane);
                musicCont.getChildren().clear();
                ContentDAO dao = new ContentDAO();
                Set mset = dao.getAllContentByType("Music");
                System.out.println("Music Pressed");
                Iterator miter = mset.iterator();
                int setSize = mset.size();
                Button[] listings = new Button[setSize];
                int i = 0;
                while (miter.hasNext()) {
                    Content content = new Content();
                    content = (NewDatabase.Content) miter.next();
                    musButt(content, listings, i,musicCont,buBack);
                    i++;
                }

            }


        });

        apps.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                mainStack.getChildren().clear();//(phoneMidRow, musicPane);
                mainStack.getChildren().add(appPane);
                appCont.getChildren().clear();
                ContentDAO dao = new ContentDAO();
                Set appset = dao.getAllContentByType("App");
                System.out.println("App Pressed");
                Iterator appiter = appset.iterator();
                int setSize = appset.size();
                Button[] listings = new Button[setSize];
                int i = 0;
                while (appiter.hasNext()) {
                    Content content = new Content();
                    content = (NewDatabase.Content) appiter.next();
                    appButt(content, listings, i,appCont,buBack);
                    i++;
                }

            }


        });

        book.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                mainStack.getChildren().clear();
                mainStack.getChildren().add(bookPane);
                bookCont.getChildren().clear();
                ContentDAO dao = new ContentDAO();
                Set set = dao.getAllContentByType("EBook");
                System.out.println("Book Pressed");
                Iterator iter = set.iterator();
                int setSize = set.size();
                Button[] listings = new Button[setSize];
                int i = 0;
                while (iter.hasNext()) {
                    Content content = new Content();
                    content = (NewDatabase.Content) iter.next();
                    ebkButt(content, listings, i,bookCont,buBack);
                    i++;
                }

            }
        });

        aBook.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                mainStack.getChildren().clear();
                mainStack.getChildren().add(audioBookPane);
                audioBookCont.getChildren().clear();
                ContentDAO dao = new ContentDAO();
                Set abset = dao.getAllContentByType("audioBook");
                System.out.println("Audio Book Pressed");
                Iterator abiter = abset.iterator();
                int setSize = abset.size();
                Button[] listings = new Button[setSize];
                int i = 0;
                while (abiter.hasNext()) {
                    Content content = new Content();
                    content = (NewDatabase.Content) abiter.next();
                    abkButt(content, listings, i,audioBookCont,buBack);
                    i++;
                }

            }


        });

        podcast.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                mainStack.getChildren().clear();
                mainStack.getChildren().add(podcastPane);
                podcastCont.getChildren().clear();
                ContentDAO dao = new ContentDAO();
                Set podcastset = dao.getAllContentByType("Podcast");
                System.out.println("podcast Pressed");
                Iterator podcastiter = podcastset.iterator();
                int setSize = podcastset.size();
                Button[] listings = new Button[setSize];
                int i = 0;
                while (podcastiter.hasNext()) {
                    Content content = new Content();
                    content = (NewDatabase.Content) podcastiter.next();
                    podButt(content, listings, i,podcastCont,buBack);
                    i++;
                }

            }


        });

        video.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                mainStack.getChildren().clear();
                mainStack.getChildren().add(videoPane);
                videoCont.getChildren().clear();
            ContentDAO dao = new ContentDAO();
            Set videoset = dao.getAllContentByType("Video");
            System.out.println("video Pressed");
            Iterator videoiter = videoset.iterator();
            int setSize = videoset.size();
            Button[] listings = new Button[setSize];
            int i = 0;
            while (videoiter.hasNext()) {
                Content content = new Content();
                content = (NewDatabase.Content) videoiter.next();
                vidButt(content, listings, i,videoCont,buBack);
                i++;
            }

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

        vbutt.getChildren().addAll(music, book, aBook, podcast, video, apps, phoneB,throwaway);
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


        // Create audio book pane
        audioBookCont.backgroundProperty().set(cenBack);
        audioBookPane.backgroundProperty().set(cenBack);
        audioBookPane.setFitToWidth(true);
        audioBookPane.setFitToHeight(true);
        audioBookPane.setPannable(false);


        // Create app pane
        appCont.backgroundProperty().set(cenBack);
        appPane.backgroundProperty().set(cenBack);
        appPane.setFitToWidth(true);
        appPane.setFitToHeight(true);
        appPane.setPannable(false);


        // Create podcast pane
        podcastCont.backgroundProperty().set(cenBack);
        podcastPane.backgroundProperty().set(cenBack);
        podcastPane.setFitToWidth(true);
        podcastPane.setFitToHeight(true);
        podcastPane.setPannable(false);


        // Create video pane
        videoCont.backgroundProperty().set(cenBack);
        videoPane.backgroundProperty().set(cenBack);
        videoPane.setFitToWidth(true);
        videoPane.setFitToHeight(true);
        videoPane.setPannable(false);


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
//
//        if(t.checkConnection()){
//        Label battery = new Label("" + t.getPhoneBattery());
//        Label phoneName = new Label("" + t.getPhoneModel());}
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       // phoneStack.getChildren().addAll(Android,phoneName,battery);

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
                t.initializePhone(0);
                t.sync();
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
                t.initializePhone(0);
                t.backup();
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
//        });   2EB900 green color
        copy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                t.backup();
                System.out.println("copy Pressed");

            }
        });

        Button android = new Button("Duplicate Phone", Android);
        copy.backgroundProperty().set(buBack);


        VBox midButt = new VBox();
        midButt.setPadding(new Insets(5, 5, 5, 5));
        midButt.setSpacing(50);
        midButt.getChildren().addAll(sync, backup, copy,throwaway);

        phoneB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                mainStack.getChildren().clear();//(phoneMidRow, musicPane);
                mainStack.getChildren().add(phoneMidRow);
                System.out.println("Phone Pressed");

            }
        });

        Insets sliderIn = new Insets(8.0,0.0,8.0,0.0);

        //Music Controll

        //music volume slider
        Slider musicVolumeSlider = new Slider();
        musicVolumeSlider.setMin(0);
        musicVolumeSlider.setMax(100);
        musicVolumeSlider.setValue(0);
        musicVolumeSlider.setPadding(sliderIn);
        //music progress slider
        Slider musicProgressSlider = new Slider();
        musicProgressSlider.setMin(0);
        musicProgressSlider.setMax(100);
        musicProgressSlider.setValue(0);
        musicProgressSlider.setPadding(sliderIn);
        //Play
        Image PlayIcon = new Image("PlayButton.png");   //Load play  Icon for imageview
        ImageView musicPlayIcon = new ImageView();
        musicPlayIcon.setImage(PlayIcon);                  //adds icon to imageview
        Button musicPlay = new Button("",musicPlayIcon);       //Creates button

        musicPlay.backgroundProperty().set(buBack);         //adds transparent background
        musicPlay.setPadding(inset);

        musicPlay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {

                System.out.println("Music Play Pressed");

            }
        });
        // forward
        Image forwardIcon = new Image("ForwardButton.png");   //Load forward  Icon for imageview
        ImageView forwardMusicIcon = new ImageView();
        forwardMusicIcon.setImage(forwardIcon);                  //adds icon to imageview
        Button musicForward = new Button("",forwardMusicIcon);       //Creates button

        musicForward.backgroundProperty().set(buBack);         //adds transparent background
        musicForward.setPadding(inset);

        musicForward.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {

                System.out.println("Music Forward Pressed");

            }
        });
    //Backward
        Image BackIcon = new Image("backwardButton.png");   //Load Back  Icon for imageview
        ImageView musicBackIcon = new ImageView();
        musicBackIcon.setImage(BackIcon);                  //adds icon to imageview
        Button musicBack = new Button("",musicBackIcon);       //Creates button

        musicBack.backgroundProperty().set(buBack);         //adds transparent background
        musicBack.setPadding(inset);

        musicBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {

                System.out.println("Music Back Pressed");

            }
        });

        //Volume
        Image muteIcon = new Image("Mute.png");   //Load play  Icon for imageview
        ImageView musicMuteIcon = new ImageView();
        musicMuteIcon.setImage(muteIcon);                  //adds icon to imageview
        Button musicMute = new Button("",musicMuteIcon);       //Creates button
        Image lowVolIcon = new Image("LowVol.png");   //Load play  Icon for imageview
        ImageView musicLowIcon = new ImageView();
        if(musicVolumeSlider.getValue() <= 0.33 && musicVolumeSlider.getValue() != 0.0){musicMuteIcon.setImage(lowVolIcon);  }                //adds icon to imageview
        Image highVol = new Image("HighVol.png");   //Load play  Icon for imageview
        ImageView highVolIcon = new ImageView();
        if(musicVolumeSlider.getValue() >= 0.66){musicMuteIcon.setImage(highVol);     }             //adds icon to imageview
        Image midVolIcon = new Image("MidVol.png");   //Load play  Icon for imageview
        ImageView midVol = new ImageView();
        if(musicVolumeSlider.getValue() <= 0.66 && musicVolumeSlider.getValue() >= 0.33){musicMuteIcon.setImage(midVolIcon);    }              //adds icon to imageview

        musicMute.backgroundProperty().set(buBack);         //adds transparent background
        musicMute.setPadding(inset);

        musicMute.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {

                System.out.println("Music Mute Pressed");
                musicVolumeSlider.setValue(0);
                musicMuteIcon.setImage(muteIcon);


            }
        });


        musicControll.getChildren().addAll(musicBack,musicPlay,musicForward,musicMute,musicVolumeSlider);
//// end music controll


        //Podcast Controll

        //Podcast volume slider
        Slider podcastVolumeSlider = new Slider();
        podcastVolumeSlider.setMin(0);
        podcastVolumeSlider.setMax(100);
        podcastVolumeSlider.setValue(0);
        podcastVolumeSlider.setPadding(sliderIn);
        //podcast progress slider
        Slider podcastProgressSlider = new Slider();
        podcastProgressSlider.setMin(0);
        podcastProgressSlider.setMax(100);
        podcastProgressSlider.setValue(0);
        podcastProgressSlider.setPadding(sliderIn);
        //Play
        Button podcastPlay = new Button("",musicPlayIcon);       //Creates button

        podcastPlay.backgroundProperty().set(buBack);         //adds transparent background
        podcastPlay.setPadding(inset);

        podcastPlay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {

                System.out.println("Podcast Play Pressed");

            }
        });
        // forward
        Button podcastForward = new Button("",forwardMusicIcon);       //Creates button
        podcastForward.backgroundProperty().set(buBack);         //adds transparent background
        podcastForward.setPadding(inset);

        podcastForward.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {

                System.out.println("Podcast Forward Pressed");

            }
        });
        //Backward
        Button podcastBack = new Button("",musicBackIcon);       //Creates button

        podcastBack.backgroundProperty().set(buBack);         //adds transparent background
        podcastBack.setPadding(inset);

        podcastBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {

                System.out.println("podcst Back Pressed");

            }
        });

        //Volume

        Button podcastMute = new Button("",musicMuteIcon);       //Creates button
        if(podcastVolumeSlider.getValue() <= 0.33 && podcastVolumeSlider.getValue() != 0.0){musicMuteIcon.setImage(lowVolIcon);  }                //adds icon to imageview
        if(podcastVolumeSlider.getValue() >= 0.66){musicMuteIcon.setImage(highVol);     }             //adds icon to imageview
        if(podcastVolumeSlider.getValue() <= 0.66 && podcastVolumeSlider.getValue() >= 0.33){musicMuteIcon.setImage(midVolIcon);    }              //adds icon to imageview

        podcastMute.backgroundProperty().set(buBack);         //adds transparent background
        podcastMute.setPadding(inset);

        podcastMute.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {

                System.out.println("podcast Mute Pressed");
                musicVolumeSlider.setValue(0);
                musicMuteIcon.setImage(muteIcon);


            }
        });


       podcastControll.getChildren().addAll(podcastBack,podcastPlay,podcastForward,podcastMute,podcastVolumeSlider);
//// end podcast controll


         Label musicTester = new Label("");
        musicCont.getChildren().add(musicTester);
        //drag and drop music
        musicCont.setOnDragOver(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != musicCont
                        && event.getDragboard().hasFiles()) {

                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        musicCont.setOnDragDropped(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    sql.addContent("" + db.getFiles().toString(),"Music");
                    System.out.println("music added");
                    musicTester.setText(db.getFiles().toString());
                    success = true;
                }
                /* let the source know whether the string was successfully
                 * transferred and used */
                event.setDropCompleted(success);

                event.consume();
            }
        });

        Label ebookTester = new Label("");
        bookCont.getChildren().add(ebookTester);
        //drag and drop ebooks
        bookCont.setOnDragOver(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != bookCont
                        && event.getDragboard().hasFiles()) {

                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        bookCont.setOnDragDropped(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    // addContent("EBook,);
                    sql.addContent("" + db.getFiles().toString(),"EBook");
                    System.out.println("eBook added");
                    ebookTester.setText(db.getFiles().toString());
                    success = true;
                }
                /* let the source know whether the string was successfully
                 * transferred and used */
                event.setDropCompleted(success);

                event.consume();
            }
        });
        Label audioBookTester = new Label("");
        audioBookCont.getChildren().add(audioBookTester);
        //drag and drop audio books
        audioBookCont.setOnDragOver(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != audioBookCont
                        && event.getDragboard().hasFiles()) {

                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        audioBookCont.setOnDragDropped(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {

                    sql.addContent("" + db.getFiles().toString(),"audioBook");
                    System.out.println("audio book added");
                    audioBookTester.setText(db.getFiles().toString());
                    success = true;
                }
                /* let the source know whether the string was successfully
                 * transferred and used */
                event.setDropCompleted(success);

                event.consume();
            }
        });

        Label videoTester = new Label("");
        videoCont.getChildren().add(videoTester);
        //drag and drop music
        videoCont.setOnDragOver(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != videoCont
                        && event.getDragboard().hasFiles()) {

                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        videoCont.setOnDragDropped(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    sql.addContent("" + db.getFiles().toString(),"Video");
                    System.out.println("video added");
                    videoTester.setText(db.getFiles().toString());
                    success = true;
                }
                /* let the source know whether the string was successfully
                 * transferred and used */
                event.setDropCompleted(success);

                event.consume();
            }
        });
        Label podcastTester = new Label("");
        podcastCont.getChildren().add(podcastTester);
        //drag and drop podcasts
        podcastCont.setOnDragOver(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != podcastCont
                        && event.getDragboard().hasFiles()) {

                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        podcastCont.setOnDragDropped(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    sql.addContent("" + db.getFiles().toString(),"Podcast");
                    System.out.println("podcast added");
                    podcastTester.setText(db.getFiles().toString());
                    success = true;
                }
                /* let the source know whether the string was successfully
                 * transferred and used */
                event.setDropCompleted(success);

                event.consume();
            }
        });
        Label appTester = new Label("");
        appCont.getChildren().add(appTester);
        //drag and drop apps
        appCont.setOnDragOver(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != appCont
                        && event.getDragboard().hasFiles()) {

                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        appCont.setOnDragDropped(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    sql.addContent("" + db.getFiles().toString(),"App");
                    System.out.println("app added");
                    appTester.setText(db.getFiles().toString());
                    success = true;
                }
                /* let the source know whether the string was successfully
                 * transferred and used */
                event.setDropCompleted(success);

                event.consume();
            }
        });


       // bottomButt.getChildren().addAll();

        mainStack.getChildren().setAll(phoneMidRow, musicPane);
        tAnchor.getChildren().addAll(mainStack);
        tAnchor.setRightAnchor(mainStack, 25.0);// area around
        tAnchor.setLeftAnchor(mainStack, 25.0);// area around
        tAnchor.setBottomAnchor(mainStack, 25.0);// area around
        tAnchor.setTopAnchor(mainStack, 25.0);// area around

        //MusicPane
        Label noMusic = new Label("You have no content\nImport content, into the appropriate pane, via drag and drop.");
        noMusic.setFont(new Font(25.0));
        noMusic.backgroundProperty().set(buBack);
        musicCont.getChildren().addAll(noMusic);

        phoneMidRow.getChildren().addAll(phoneStack, midButt);
        centerAnchorPane.getChildren().addAll(tAnchor);
        centerAnchorPane.setRightAnchor(tAnchor, 5.0);
        centerAnchorPane.setLeftAnchor(tAnchor, 5.0);
        centerAnchorPane.setTopAnchor(tAnchor, 5.0);
        centerAnchorPane.setBottomAnchor(tAnchor, 5.0);

        bottomAnchorPane.getChildren().addAll(musicControll);
        bottomAnchorPane.setRightAnchor(musicControll, 10.0);
        bottomAnchorPane.setLeftAnchor(musicControll, 10.0);
        bottomAnchorPane.setTopAnchor(musicControll, 10.0);
        bottomAnchorPane.setBottomAnchor(musicControll, 10.0);
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

    public static void musButt(Content objs, Button[] L, int i, VBox cont, Background b) {

        String name = objs.getContentName();
        L[i] = new Button(name);
        L[i].setTextFill(Paint.valueOf("BBBBBB"));
        L[i].backgroundProperty().set(b);
        L[i].setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent press) {

                System.out.println(name + " Pressed");

            }
        });
        cont.getChildren().add(L[i]);
    }

    public static void podButt(Content objs, Button[] L, int i, VBox cont, Background b) {

        String name = objs.getContentName();
        L[i] = new Button(name);
        L[i].setTextFill(Paint.valueOf("BBBBBB"));
        L[i].backgroundProperty().set(b);
        L[i].setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent press) {

                System.out.println(name + " Pressed");

            }
        });
        cont.getChildren().add(L[i]);
    }

    public static void ebkButt(Content objs, Button[] L, int i, VBox cont, Background b) {

         String name = objs.getContentName();
        L[i] = new Button(name);
        L[i].setTextFill(Paint.valueOf("BBBBBB"));
        L[i].backgroundProperty().set(b);
         L[i].setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent press) {

                System.out.println(name + " Pressed");

            }
         });
        cont.getChildren().add(L[i]);
    }

    public static void abkButt(Content objs, Button[] L, int i, VBox cont, Background b) {

        String name = objs.getContentName();
        L[i] = new Button(name);
        L[i].setTextFill(Paint.valueOf("BBBBBB"));
        L[i].backgroundProperty().set(b);
        L[i].setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent press) {

                System.out.println(name + " Pressed");

            }
        });
        cont.getChildren().add(L[i]);
    }
    public static void vidButt(Content objs, Button[] L, int i, VBox cont, Background b) {

        String name = objs.getContentName();
        L[i] = new Button(name);
        L[i].setTextFill(Paint.valueOf("BBBBBB"));
        L[i].backgroundProperty().set(b);
        L[i].setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent press) {

                System.out.println(name + " Pressed");

            }
        });
        cont.getChildren().add(L[i]);
    }

    public static void appButt(Content objs, Button[] L, int i, TilePane cont, Background b) {

        String name = objs.getContentName();
        L[i] = new Button(name);
        L[i].setTextFill(Paint.valueOf("BBBBBB"));
        L[i].backgroundProperty().set(b);
        L[i].setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent press) {

                System.out.println(name + " Pressed");

            }
        });
        cont.getChildren().add(L[i]);
    }

}
