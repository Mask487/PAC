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
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import NewDatabase.ContentDAO;
import NewDatabase.Content;



import java.io.IOException;
import java.net.URI;
import java.util.*;

import javafx.scene.input.MouseButton;
import javafx.util.Duration;

import static javafx.scene.input.MouseButton.SECONDARY;

/**
 *
 * @author andrewmenezes
 */
public class PacApp extends Application {

   static MediaView musicView = new MediaView();
   static MediaView podcastView = new MediaView();
   static MediaView aBookView = new MediaView();
   static MediaView videoView = new MediaView();
   static ContentDAO dao = new ContentDAO();
   static List<Media> musicList = new ArrayList<>();
    static List<Media> podcastList = new ArrayList<>();
    static List<Media> videoList = new ArrayList<>();
    static List<Media> abookList = new ArrayList<>();
    static int i = 0;

    @Override
    public void start(Stage stage) throws Exception {
        SQLTranslator sql = new SQLTranslator();
        Transfer t = new Transfer();
        t.initializeDesk();

           // t.initializePhone(0);

        AnchorPane root2 = new AnchorPane();
        Scene primary = new Scene(root2);

        primary.fillProperty().set(Paint.valueOf("505050"));
        stage.setScene(primary);
        root2.setPrefSize(750, 510);
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
        VBox podcastPane1 = new VBox();
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
        VBox searchResults = new VBox();
        ScrollPane searchPane = new ScrollPane(searchResults);







        //create background anchor
        AnchorPane.setRightAnchor(bp, 0.0);
        AnchorPane.setLeftAnchor(bp, 0.0);
        AnchorPane.setBottomAnchor(bp, 0.0);
        AnchorPane.setTopAnchor(bp, 0.0);
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
        hSearch.backgroundProperty().set(buBack);



    podcastCont.backgroundProperty().set(testBack);

        searchPane.backgroundProperty().set(buBack);
        searchResults.backgroundProperty().set(buBack);

        search.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent Enter){
                String term = search.getCharacters().toString();
                System.out.println("Search Term " + term);
                search.setText("");
                ContentDAO searchSql = new ContentDAO();
                Set searchSet = searchSql.searchAllTablesBySearchTerm(term);
                mainStack.getChildren().clear();
                mainStack.getChildren().add(searchPane);
                searchResults.getChildren().clear();
                Iterator miter = searchSet.iterator();
                int setSize = searchSet.size();
                Button[] listings = new Button[setSize];
                Media[] MediaArray = new Media[setSize];
                int i = 0;
                while (miter.hasNext()) {
                    Content content = new Content();
                    content = (NewDatabase.Content) miter.next();
                    searchButt(content, listings, i,searchResults,buBack,MediaArray);
                    i++;
                }



            }
        });


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
                Media[] mediaSet = new Media[setSize];
                Button[] listings = new Button[setSize];
                musicList.clear();
                int i = 0;
                while (miter.hasNext()) {
                    Content content = new Content();
                    content = (NewDatabase.Content) miter.next();
                    musButt(content, listings, i,musicCont,buBack,mediaSet);
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
                if(setSize == 0){
                    Label noapps = new Label("You have no Apps.\n\nDrag and Drop apps into this window to add to your collection.");
                    noapps.setFont(new Font(20.0));
                    noapps.backgroundProperty().set(buBack);
                    appCont.getChildren().addAll(noapps);
                }
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
                if(setSize == 0){
                    Label noEBooks = new Label("You have no eBooks.\n\nDrag and Drop eBooks into this window to add to your collection.");
                    noEBooks.setFont(new Font(20.0));
                    noEBooks.backgroundProperty().set(buBack);
                    bookCont.getChildren().addAll(noEBooks);
                }
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
                Media[] mediaSet = new Media[setSize];
                if(setSize == 0){
                    Label noABooks = new Label("You have no Audio Books.\n\nDrag and Drop Audio Books into this window to add to your collection.");
                    noABooks.setFont(new Font(20.0));
                    noABooks.backgroundProperty().set(buBack);
                    audioBookCont.getChildren().addAll(noABooks);
                }
                Button[] listings = new Button[setSize];
                int i = 0;
                while (abiter.hasNext()) {
                    Content content = new Content();
                    content = (NewDatabase.Content) abiter.next();
                    abkButt(content, listings, i,audioBookCont,buBack,mediaSet);
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
                Media[] mediaSet = new Media[setSize];
                if(setSize == 0){
                }
                Button[] listings = new Button[setSize];
                int i = 0;
                while (podcastiter.hasNext()) {
                    Content content = new Content();
                    content = (NewDatabase.Content) podcastiter.next();
                    podButt(content, listings, i,podcastCont,buBack,mediaSet);
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
                Media[] mediaSet = new Media[setSize];

                if(setSize == 0){
                    Label noVideos = new Label("You have no videos.\n\nDrag and Drop videos into this window to add to your collection.");
                    noVideos.setFont(new Font(20.0));
                    noVideos.backgroundProperty().set(buBack);
                    videoCont.getChildren().addAll(noVideos);
                }
                Button[] listings = new Button[setSize];
                int i = 0;
                while (videoiter.hasNext()) {
                    Content content = new Content();
                    content = (NewDatabase.Content) videoiter.next();
                    vidButt(content, listings, i,videoCont,buBack,mediaSet);
                    i++;
                }

            }


        });

        apps.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                mainStack.getChildren().clear();
                mainStack.getChildren().add(appPane);
                ContentDAO dao = new ContentDAO();
                Set appset = dao.getAllContentByType("App");
                System.out.println("Apps Pressed");
                Iterator appiter = appset.iterator();
                int setSize = appset.size();
                if(setSize == 0){
                    Label noApps = new Label("You have no Apps.\n\nDrag and Drop Apps into this window to add to your collection.");
                    noApps.setFont(new Font(20.0));
                    noApps.backgroundProperty().set(buBack);
                    appCont.getChildren().addAll(noApps);
                }else{
                    appCont.getChildren().remove(0);
                }
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
        lightMode.setTextFill(Paint.valueOf("BBBBBB"));
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

        if(t.checkConnection()){
        Label battery = new Label("" + t.getPhoneBattery());
        Label phoneName = new Label("" + t.getPhoneModel());
        phoneStack.getChildren().addAll(Android,phoneName,battery);}else
            phoneStack.getChildren().addAll(Android);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         //phoneStack.getChildren().addAll(Android,phoneName,battery);

        HBox phoneMidRow = new HBox();
        phoneMidRow.setPadding(new Insets(5, 5, 5, 5));
        phoneMidRow.setSpacing(50);

        Button sync = new Button("Sync Phone");       //Creates button
        sync.setTextFill(Paint.valueOf("BBBBBB"));
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
        backup.setTextFill(Paint.valueOf("BBBBBB"));
        backup.backgroundProperty().set(buBack);         //adds transparent background
        backup.setPadding(inset);

        backup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                t.initializePhone(0);
                try {
                    t.backup();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("backup Pressed");

            }
        });

        Button copy = new Button("Restore Phone");       //Creates button
        copy.setTextFill(Paint.valueOf("BBBBBB"));
        copy.backgroundProperty().set(buBack);         //adds transparent background

//        });   2EB900 green color
        copy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                t.initializePhone(0);
                try {
                    t.restore();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Restore Pressed");

            }
        });

        Button android = new Button("", Android);
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

        Button rssImport = new Button("Import.");       //Creates button
        rssImport.backgroundProperty().set(buBack);         //adds transparent background
        rssImport.setTextFill(Paint.valueOf("BBBBBB"));
        rssImport.setPadding(inset);


        TextField RSSLookup = new TextField("");
        RSSLookup.setPromptText("Import RSS feed URL");
        RSSLookup.backgroundProperty().set(buBack);



        RSSLookup.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent Enter){
                String newrl;
                newrl = RSSLookup.getCharacters().toString();
                System.out.println("URL added: " + newrl);
                RSSLookup.setText("");
                RSSReader rede = new RSSReader();
                class csc490 implements Runnable{

                    //rede.DownloadPodcast(newrl);
                    csc490(String stringName){

                    }
                    public void run(){
                        RSSReader rede = new RSSReader();
                        rede.DownloadPodcast(newrl);
                    }
                }
                Thread sheets = new Thread(new csc490(newrl));
                sheets.start();
    /////////////////////////////////////Add rss import here

            }
        });


     //   podcastPane1.backgroundProperty().setValue(buBack);



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
                musicView.getMediaPlayer().play();
                if(musicView.getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING){
                    musicView.getMediaPlayer().pause();
                }
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
                musicView.getMediaPlayer().dispose();
                i++;
                musicView.getMediaPlayer().pause();
                musicView.setMediaPlayer(playahMakah(musicList.get(i %= musicList.size()),musicView));
                musicView.getMediaPlayer().play();
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
                //musicView.getMediaPlayer().dispose();
                i++;
                //musicView.getMediaPlayer().pause();
                System.out.println(musicView.getMediaPlayer().getCurrentTime());
                if(musicView.getMediaPlayer().getCurrentTime().greaterThan(new Duration(5000.0))  ){
                    musicView.getMediaPlayer().stop();
                    musicView.getMediaPlayer().play();

                }else{
                    musicView.getMediaPlayer().dispose();
                    i--;
                    musicView.setMediaPlayer(playahMakah(musicList.get(i %= musicList.size()),musicView));
                    musicView.getMediaPlayer().play();

                }

                //musicView.setMediaPlayer(playahMakah(musicList.get(i %= musicList.size()),musicView));
               // musicView.getMediaPlayer().play();
                System.out.println("Music back Pressed");
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
        AnchorPane.setRightAnchor(mainStack, 25.0);// area around
        AnchorPane.setLeftAnchor(mainStack, 25.0);// area around
        AnchorPane.setBottomAnchor(mainStack, 25.0);// area around
        AnchorPane.setTopAnchor(mainStack, 25.0);// area around

        //MusicPane
        mainStack.getChildren().clear();//(phoneMidRow, musicPane);
        mainStack.getChildren().add(musicPane);
        musicCont.getChildren().clear();
        ContentDAO dao = new ContentDAO();
        Set mset = dao.getAllContentByType("Music");
        System.out.println("Music Showing");
        Iterator miter = mset.iterator();
        int setSize = mset.size();

        if(setSize == 0){
            Label noMusic = new Label("You have no music.\n\nDrag and Drop music into this window to add to your collection.");
            noMusic.setFont(new Font(25.0));
            noMusic.backgroundProperty().set(buBack);
            musicCont.getChildren().addAll(noMusic);
        }
        Button[] listings = new Button[setSize];
        Media[] mediaSet = new Media[setSize];
        int i = 0;
        while (miter.hasNext()) {
            Content content = new Content();
            content = (NewDatabase.Content) miter.next();
            musButt(content, listings, i,musicCont,buBack,mediaSet);
            i++;
        }


        phoneMidRow.getChildren().addAll(phoneStack, midButt);

        centerAnchorPane.getChildren().addAll(tAnchor);
        centerAnchorPane.setRightAnchor(tAnchor, 0.0);
        centerAnchorPane.setLeftAnchor(tAnchor, 5.0);
        centerAnchorPane.setTopAnchor(tAnchor, 5.0);
        centerAnchorPane.setBottomAnchor(tAnchor, 5.0);

        bottomAnchorPane.getChildren().addAll(musicControll,RSSLookup);
        bottomAnchorPane.setRightAnchor(RSSLookup, 10.0);
        bottomAnchorPane.setLeftAnchor(musicControll, 10.0);
        bottomAnchorPane.setTopAnchor(musicControll, 10.0);
        bottomAnchorPane.setBottomAnchor(musicControll, 10.0);
        leftButtonPane.getChildren().addAll(vbutt, bvbutt);
        topAnchorPane.getChildren().addAll(hSearch);
        AnchorPane.setTopAnchor(vbutt, 0.0);
        AnchorPane.setBottomAnchor(bvbutt, 0.0);
        AnchorPane.setRightAnchor(hSearch, 0.0);
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

    public static void musButt(Content objs, Button[] L, int i, VBox cont, Background b,Media[] M) {
        String name = objs.getContentName();
        HBox doubleButt = new HBox();
        CheckBox syncer = new CheckBox();
        syncer.backgroundProperty().set(b);
        syncer.setSelected(objs.getWantToSync());
        syncer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                ContentDAO dao = new ContentDAO();
                dao.setSyncStatus(objs);

                System.out.println(name + " switched to sync = " + objs.getWantToSync());

            }
        });


        String thefuquwant = objs.getLocation();
        thefuquwant = "file:" + thefuquwant.replace("\\" , "/");
        File file = new File(objs.getLocation());
        URI uri = file.toURI();
        M[i] = new Media(uri.toString());
        musicList.add(i,M[i]);
        L[i] = new Button(name);
        L[i].setTextFill(Paint.valueOf("BBBBBB"));
        L[i].backgroundProperty().set(b);

        L[i].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                try{
                    musicView.getMediaPlayer().dispose();
                }catch(Exception E){
                    System.out.println(E);
                }

                playahMakah(musicList.get(i) ,musicView);
                System.out.println(musicList.get(i).toString());
                System.out.println(name + " Pressed");

                musicView.getMediaPlayer().play();

            }
        });
        doubleButt.getChildren().addAll(syncer,L[i]);
        cont.getChildren().add(doubleButt);

    }

    public static void podButt(Content objs, Button[] L, int i, VBox cont, Background b, Media[] M) {
        String name = objs.getContentName();
        HBox doubleButt = new HBox();
        CheckBox syncer = new CheckBox();
        syncer.backgroundProperty().set(b);
        syncer.setSelected(objs.getWantToSync());
        syncer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                ContentDAO dao = new ContentDAO();
                dao.setSyncStatus(objs);
                System.out.println(name + " switched to sync = " + objs.getWantToSync());

            }
        });


    String thefuquwant = objs.getLocation();
        thefuquwant = "file:" + thefuquwant.replace("\\" , "/");
        File file = new File(objs.getLocation());
        URI uri = file.toURI();
        M[i] = new Media(uri.toString());
        L[i] = new Button(name);
        L[i].setTextFill(Paint.valueOf("BBBBBB"));
        L[i].backgroundProperty().set(b);

        L[i].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                System.out.println(name + " Pressed");

            }
        });
        doubleButt.getChildren().addAll(syncer,L[i]);
        cont.getChildren().add(doubleButt);
    }

    public static void ebkButt(Content objs, Button[] L, int i, VBox cont, Background b) {
        System.out.println(i);
        String name = objs.getContentName();
        HBox doubleButt = new HBox();
        CheckBox syncer = new CheckBox();
        syncer.backgroundProperty().set(b);
        syncer.setSelected(objs.getWantToSync());
        syncer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                ContentDAO dao = new ContentDAO();
                dao.setSyncStatus(objs);
                System.out.println(name + " switched to sync = " + objs.getWantToSync());

            }
        });




        L[i] = new Button(name);
        L[i].setTextFill(Paint.valueOf("BBBBBB"));
        L[i].backgroundProperty().set(b);

        L[i].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                System.out.println(name + " Pressed");

            }
        });
        doubleButt.getChildren().addAll(syncer,L[i]);
        cont.getChildren().add(doubleButt);
    }

    public static void abkButt(Content objs, Button[] L, int i, VBox cont, Background b, Media[] M) {
        String name = objs.getContentName();
        HBox doubleButt = new HBox();
        CheckBox syncer = new CheckBox();
        syncer.backgroundProperty().set(b);
        syncer.setSelected(objs.getWantToSync());
        syncer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                ContentDAO dao = new ContentDAO();
                dao.setSyncStatus(objs);
                System.out.println(name + " switched to sync = " + objs.getWantToSync());

            }
        });


        String thefuquwant = objs.getLocation();
        thefuquwant = "file:" + thefuquwant.replace("\\" , "/");
        File file = new File(objs.getLocation());
        URI uri = file.toURI();
        M[i] = new Media(uri.toString());
        L[i] = new Button(name);
        L[i].setTextFill(Paint.valueOf("BBBBBB"));
        L[i].backgroundProperty().set(b);

        L[i].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                System.out.println(name + " Pressed");

            }
        });
        doubleButt.getChildren().addAll(syncer,L[i]);
        cont.getChildren().add(doubleButt);
    }
    public static void vidButt(Content objs, Button[] L, int i, VBox cont, Background b, Media[] M) {
        String name = objs.getContentName();
        HBox doubleButt = new HBox();
        CheckBox syncer = new CheckBox();
        syncer.backgroundProperty().set(b);
        syncer.setSelected(objs.getWantToSync());
        syncer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                ContentDAO dao = new ContentDAO();
                dao.setSyncStatus(objs);
                System.out.println(name + " switched to sync = " + objs.getWantToSync());

            }
        });


        String thefuquwant = objs.getLocation();
        thefuquwant = "file:" + thefuquwant.replace("\\" , "/");
        File file = new File(objs.getLocation());
        URI uri = file.toURI();
        M[i] = new Media(uri.toString());
        L[i] = new Button(name);
        L[i].setTextFill(Paint.valueOf("BBBBBB"));
        L[i].backgroundProperty().set(b);

        L[i].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                System.out.println(name + " Pressed");

            }
        });
        doubleButt.getChildren().addAll(syncer,L[i]);
        cont.getChildren().add(doubleButt);
    }

    public static void appButt(Content objs, Button[] L, int i, TilePane cont, Background b) {

        String name = objs.getContentName();
        Image appsIcon = new Image("AppDefault.png");   //Load Phone Icon for imageview
        ImageView appview = new ImageView();
        appview.setImage(appsIcon);
        L[i] = new Button(name,appview);
        L[i].setTextFill(Paint.valueOf("BBBBBB"));
        L[i].setContentDisplay(ContentDisplay.TOP);
        L[i].backgroundProperty().set(b);
        L[i].setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent press) {

                System.out.println(name + " Pressed");

            }
        });
        cont.getChildren().add(L[i]);
    }

    public static void searchButt(Content objs, Button[] L, int i, VBox cont, Background b,Media[] M) {

        String name = objs.getContentName();
        HBox doubleButt = new HBox();
        CheckBox syncer = new CheckBox();
        syncer.backgroundProperty().set(b);
        syncer.setSelected(objs.getWantToSync());
        syncer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                ContentDAO dao = new ContentDAO();
                dao.setSyncStatus(objs);
                System.out.println(name + " switched to sync = " + objs.getWantToSync());

            }
        });



        File file = new File(objs.getLocation());
        URI uri = file.toURI();
        M[i] = new Media(uri.toString());
        L[i] = new Button(name);
        L[i].setTextFill(Paint.valueOf("BBBBBB"));
        L[i].backgroundProperty().set(b);

        L[i].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                System.out.println(name + " Pressed");

            }
        });
        doubleButt.backgroundProperty().set(b);
        doubleButt.getChildren().addAll(syncer,L[i]);
        cont.getChildren().add(doubleButt);

}

    public static MediaPlayer playahMakah(Media M,MediaView V){
        MediaPlayer player = new MediaPlayer(M);
        V.setMediaPlayer(player);
        return player;
    }
}

