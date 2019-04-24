package pacapp;

import java.awt.*;
import java.io.File;

import NewDatabase.SQLTranslator;
import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory;
import com.sun.javafx.application.HostServicesDelegate;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import NewDatabase.ContentDAO;
import NewDatabase.Content;
import NewDatabase.PlaylistDAO;
import NewDatabase.Playlist;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;


import java.io.IOException;
import java.net.URI;
import java.util.*;

import javafx.scene.input.MouseButton;
import javafx.util.Duration;
import org.json.JSONException;

import static javafx.scene.input.MouseButton.SECONDARY;

/**
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
    static Insets bFillIn = new Insets(0);
    static CornerRadii bFillCR = new CornerRadii(0);
    //Button panel Color
    static BackgroundFill buFill = new BackgroundFill(Paint.valueOf("TRANSPARENT"), bFillCR, bFillIn);
    static Background buBack = new Background(buFill);
    //highlight background
    static BackgroundFill hiFill = new BackgroundFill(Paint.valueOf("515151"), bFillCR, bFillIn);
    static Background hiBack = new Background(hiFill);
    //controll icons
    static Image PlayIcon = new Image("PlayButton.png");   //Load play  Icon for imageview
    static ImageView musicPlayIcon = new ImageView();
    static Image PauseIcon = new Image("pause.png");   //Load play  Icon for imageview
    static ImageView musicPauseIcon = new ImageView();
    static Image BackIcon = new Image("backwardButton.png");   //Load Back  Icon for imageview
    static ImageView musicBackIcon = new ImageView();    //adds icon to imageview
    static Image muteIcon = new Image("Mute.png");   //Load play  Icon for imageview
    static ImageView musicMuteIcon = new ImageView();
    static Image highVolIcon = new Image("HighVol.png");   //Load play  Icon for imageview
    static ImageView musicHighIcon = new ImageView();
    static Image lowVolIcon = new Image("LowVol.png");   //Load play  Icon for imageview
    static ImageView musiclowIcon = new ImageView();
    static Image medVolIcon = new Image("MidVol.png");   //Load play  Icon for imageview
    static ImageView musicMedIcon = new ImageView();
    static Slider musicVolumeSlider = new Slider(0.0, 1.0, 1.0);
    static Slider podcastVolumeSlider = new Slider(0.0, 1.0, 1.0);
    static Slider videoVolumeSlider = new Slider(0.0, 1.0, 1.0);
    static Slider abookVolumeSlider = new Slider(0.0, 1.0, 1.0);
    static int type = 0;//music - 0,abook - 1, podcast - 2,video - 3
    static VBox midButt = new VBox();
    static Transfer t = new Transfer();
    static Recommend R = new Recommend();
    static BookSearch S = new BookSearch();
    static PlaylistDAO pdao = new PlaylistDAO();
    static Insets inset = new Insets(-1.0);



    @Override
    public void start(Stage stage) throws Exception {
        SQLTranslator sql = new SQLTranslator();

         t.initializeDesk();
        // t.initializePhone(0);

        AnchorPane root2 = new AnchorPane();
        Scene primary = new Scene(root2);

        primary.fillProperty().set(Paint.valueOf("505050"));
        stage.setScene(primary);
        root2.setPrefSize(750, 510);
        //root2.setMaxSize(1600,900);

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
        Button throwaway = new Button("");
        VBox phoneStack = new VBox();
        VBox searchResults = new VBox();
        ScrollPane searchPane = new ScrollPane(searchResults);
        StackPane controllStack = new StackPane();
        VBox pBooksTall = new VBox();
        ScrollPane overallPBooks = new ScrollPane(pBooksTall);
        VBox reccomendAuthor = new VBox();//genre author series publisher
        ScrollPane authorReccomend = new ScrollPane(reccomendAuthor);
        VBox reccomendgenre = new VBox();//genre author series publisher
        ScrollPane genreReccomend = new ScrollPane(reccomendgenre);
        VBox reccomendSeries = new VBox();//genre author series publisher
        ScrollPane seriesReccomend = new ScrollPane(reccomendSeries);
        VBox reccomendpublisher = new VBox();//genre author series publisher
        ScrollPane publisherReccomend = new ScrollPane(reccomendpublisher);
        VBox insertedBooks = new VBox();//genre author series publisher
        ScrollPane sInsertedBooks = new ScrollPane(insertedBooks);
        TilePane playlists = new TilePane();
        ScrollPane playScroll = new ScrollPane(playlists);
        VBox playlistMusic = new VBox();

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

        leftButtonPane.backgroundProperty().set(lPaneColor);


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
        search.setStyle("-fx-text-inner-color: BBBBBB;");
        // search.
        search.backgroundProperty().set(buBack);
        hSearch.getChildren().add(search);
        hSearch.backgroundProperty().set(buBack);

        //reccomendations
        pBooksTall.backgroundProperty().set(buBack);
        reccomendAuthor.backgroundProperty().set(buBack);
        reccomendgenre.backgroundProperty().set(buBack);
        reccomendpublisher.backgroundProperty().set(buBack);
        reccomendSeries.backgroundProperty().set(buBack);
        overallPBooks.backgroundProperty().set(buBack);
        seriesReccomend.backgroundProperty().set(buBack);
        authorReccomend.backgroundProperty().set(buBack);
        genreReccomend.backgroundProperty().set(buBack);
        publisherReccomend.backgroundProperty().set(buBack);
        insertedBooks.backgroundProperty().set(buBack);
        sInsertedBooks.backgroundProperty().set(buBack);
        playlistMusic.backgroundProperty().set(buBack);



// Arrows checks ▼ ▲ ✓ ✗


        searchPane.backgroundProperty().set(buBack);
        searchResults.backgroundProperty().set(buBack);

        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Enter) {
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
                    searchButt(content, listings, i, searchResults, MediaArray);
                    i++;
                }


            }
        });


        HBox bottomButt = new HBox();
        bottomButt.setPadding(new Insets(5));


        TextField RSSLookup = new TextField("");
        RSSLookup.setPromptText("Import RSS feed URL");
        RSSLookup.setStyle("-fx-text-inner-color: BBBBBB;");
        RSSLookup.backgroundProperty().set(buBack);

        TextField ISBNAdd = new TextField("");
        ISBNAdd.setPromptText("Add ISBN");
        ISBNAdd.setStyle("-fx-text-inner-color: BBBBBB;");
        ISBNAdd.backgroundProperty().set(buBack);

        Image playlistImage = new Image("Playlists.png");
        ImageView playlistpic = new ImageView(playlistImage);
        Button plL = new Button("",playlistpic);
        plL.backgroundProperty().set(buBack);
        plL.setPadding(inset);
        plL.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Enter) {
                System.out.println("playists pressed");
                type = 0;
                mainStack.getChildren().clear();
                mainStack.getChildren().add(playlists);
                playlists.getChildren().clear();
                bottomAnchorPane.getChildren().clear();
                bottomAnchorPane.getChildren().addAll(controllStack,plL);
                bottomAnchorPane.setRightAnchor(plL, 10.0);

                Set mset = pdao.getAllPlaylists();
                System.out.println("Playlists Pressed");
                Iterator miter = mset.iterator();
                int setSize = mset.size();
                Button[] playlistings = new Button[setSize];
                int i = 0;
                while (miter.hasNext()) {
                    Playlist content = new Playlist();
                    content = (NewDatabase.Playlist) miter.next();
                    plButt(content, playlistings, i, playlists,playlistMusic,mainStack);
                    i++;
                }

                try {
                    controllStack.getChildren().addAll(musicControll);
                } catch (IllegalArgumentException Error) {
                    System.out.println("Tried to add controlls again");
                }

            }
        });

        Image libimage = new Image("lib.png");
        ImageView library = new ImageView(libimage);
        Button lib = new Button("",library);
        lib.backgroundProperty().set(buBack);
        lib.setPadding(inset);
        lib.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Enter) {
                List<Book> authors = new ArrayList<>();
                List<Book> genres = new ArrayList<>();
                List<Book> serieses = new ArrayList<>();
                List<Book> publishers = new ArrayList<>();
                System.out.println("lib pressed");
                mainStack.getChildren().clear();
                mainStack.getChildren().add(overallPBooks);
                pBooksTall.getChildren().clear();
                try {
                    authors = R.RecommendBook("author");
                    genres = R.RecommendBook("genre");
                    serieses = R.RecommendBook("series");
                    publishers = R.RecommendBook("publisher");
                }catch(IOException |SQLException |ClassNotFoundException | JSONException | NullPointerException c){
                    c.printStackTrace();
                    System.out.println(c);
                    System.out.println("this is the error...shame shame");
                }
                ListIterator<Book> iter = new ListIterator<Book>() {
                    @Override
                    public boolean hasNext() {
                        return false;
                    }

                    @Override
                    public Book next() {
                        return null;
                    }

                    @Override
                    public boolean hasPrevious() {
                        return false;
                    }

                    @Override
                    public Book previous() {
                        return null;
                    }

                    @Override
                    public int nextIndex() {
                        return 0;
                    }

                    @Override
                    public int previousIndex() {
                        return 0;
                    }

                    @Override
                    public void remove() {

                    }

                    @Override
                    public void set(Book book) {

                    }

                    @Override
                    public void add(Book book) {

                    }
                };
                reccomendAuthor.getChildren().clear();
                iter = authors.listIterator();
                while(iter.hasNext()){
                    addBook(iter.next(),reccomendAuthor);

                }
                reccomendgenre.getChildren().clear();
                iter = genres.listIterator();
                while(iter.hasNext()){
                    addBook(iter.next(),reccomendgenre);
                }
                reccomendpublisher.getChildren().clear();
                iter = serieses.listIterator();
                while(iter.hasNext()){
                    addBook(iter.next(),reccomendpublisher);
                }
                reccomendSeries.getChildren().clear();
                iter = publishers.listIterator();
                while(iter.hasNext()){
                    addBook(iter.next(),reccomendSeries);
                }
                pBooksTall.getChildren().addAll(ISBNAdd,reccomendAuthor,reccomendgenre,reccomendpublisher,reccomendSeries);

            }
        });


        ISBNAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Enter) {

                List<String> ISBN = new ArrayList<>();
                List<Book> books = new ArrayList<>();
                ISBN.add(ISBNAdd.getCharacters().toString());
                try {
                    books = S.BookLookUp(ISBN);
                    for(Book b : books){
                        System.out.println(b.getTitle());
                        System.out.println(b.getSubtitle());
                        System.out.println(b.getAuthors());
                        System.out.println();

                        dao.insertBook(b);
                    }
                }catch(IOException | JSONException E){
                    System.out.println(E);
                }
                ISBNAdd.setText("");

            }
        });

        RSSLookup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent Enter) {
                String newrl;
                newrl = RSSLookup.getCharacters().toString();
                System.out.println("URL added: " + newrl);
                RSSLookup.setText("");
                RSSReader rede = new RSSReader();
                class csc490 implements Runnable {

                    csc490(String stringName) {

                    }

                    public void run() {
                        RSSReader rede = new RSSReader();
                        rede.DownloadPodcast(newrl);
                    }
                }
                Thread sheets = new Thread(new csc490(newrl));
                sheets.start();


            }
        });


        music.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                type = 0;
                mainStack.getChildren().clear();
                mainStack.getChildren().add(musicPane);
                musicCont.getChildren().clear();
                bottomAnchorPane.getChildren().clear();
                bottomAnchorPane.getChildren().addAll(controllStack,plL);
                bottomAnchorPane.setRightAnchor(plL, 10.0);

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
                    musButt(content, listings, i, musicCont, mediaSet);
                    i++;
                }

               try {
                    controllStack.getChildren().addAll(musicControll);
               } catch (IllegalArgumentException Error) {
                    System.out.println("Tried to add controlls again");
                }
            }


        });

        apps.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {

                mainStack.getChildren().clear();
                mainStack.getChildren().add(appPane);
                appCont.getChildren().clear();

                Set appset = dao.getAllContentByType("pacapp.App");
                System.out.println("pacapp.App Pressed");
                Iterator appiter = appset.iterator();
                int setSize = appset.size();
                if (setSize == 0) {
                    Label noapps = new Label("You have no Apps.\n\nDrag and Drop apps into this window to add to your collection.");
                    noapps.setFont(new Font(20.0));
                    noapps.backgroundProperty().set(buBack);
                    appCont.getChildren().addAll(noapps);
                } else {
                    appCont.getChildren().clear();
                    Button[] listings = new Button[setSize];
                    int i = 0;
                    while (appiter.hasNext()) {
                        Content content = new Content();
                        content = (NewDatabase.Content) appiter.next();
                        appButt(content, listings, i, appCont);
                        i++;
                    }
                }

            }


        });

        book.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                mainStack.getChildren().clear();
                mainStack.getChildren().add(bookPane);
                bookCont.getChildren().clear();
                bottomAnchorPane.getChildren().clear();
                bottomAnchorPane.getChildren().addAll(controllStack,lib);
                bottomAnchorPane.setRightAnchor(lib, 10.0);

                Set set = dao.getAllContentByType("EBook");
                System.out.println("pacapp.Book Pressed");
                Iterator iter = set.iterator();
                int setSize = set.size();
                if (setSize == 0) {
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
                    ebkButt(content, listings, i, bookCont);
                    i++;
                }
                //bottomAnchorPane.getChildren().add();

            }
        });

        aBook.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                type = 1;
                mainStack.getChildren().clear();
                mainStack.getChildren().add(audioBookPane);
                audioBookCont.getChildren().clear();

                Set abset = dao.getAllContentByType("audioBook");
                System.out.println("Audio pacapp.Book Pressed");
                Iterator abiter = abset.iterator();
                int setSize = abset.size();
                Media[] mediaSet = new Media[setSize];
                if (setSize == 0) {
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
                    abkButt(content, listings, i, audioBookCont, mediaSet);
                    i++;
                }
            }


        });

        podcast.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                type = 2;
                mainStack.getChildren().clear();
                mainStack.getChildren().add(podcastPane);
                podcastCont.getChildren().clear();
                bottomAnchorPane.getChildren().clear();
                bottomAnchorPane.getChildren().addAll(controllStack,RSSLookup);
                bottomAnchorPane.setRightAnchor(RSSLookup, 10.0);

                Set podcastset = dao.getAllContentByType("pacapp.Podcast");
                System.out.println("podcast Pressed");
                Iterator podcastiter = podcastset.iterator();
                int setSize = podcastset.size();
                Media[] mediaSet = new Media[setSize];
                if (setSize == 0) {
                }
                Button[] listings = new Button[setSize];
                int i = 0;
                while (podcastiter.hasNext()) {
                    Content content = new Content();
                    content = (NewDatabase.Content) podcastiter.next();
                    podButt(content, listings, i, podcastCont, mediaSet);
                    i++;
                }
               // controllStack.getChildren().clear();

               // controllStack.getChildren().addAll(podcastControll);
            }


        });

        video.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                type = 3;
                mainStack.getChildren().clear();
                mainStack.getChildren().add(videoPane);
                videoCont.getChildren().clear();

                Set videoset = dao.getAllContentByType("pacapp.Video");
                System.out.println("video Pressed");
                Iterator videoiter = videoset.iterator();
                int setSize = videoset.size();
                Media[] mediaSet = new Media[setSize];

                if (setSize == 0) {
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
                    vidButt(content, listings, i, videoCont, mediaSet);
                    i++;
                }
                //controllStack.getChildren().clear();
                //controllStack.getChildren().addAll(videoControll);
            }


        });

        apps.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                mainStack.getChildren().clear();
                mainStack.getChildren().add(appPane);

                Set appset = dao.getAllContentByType("pacapp.App");
                System.out.println("Apps Pressed");
                Iterator appiter = appset.iterator();
                int setSize = appset.size();
                if (setSize == 0) {
                    Label noApps = new Label("You have no Apps.\n\nDrag and Drop Apps into this window to add to your collection.");
                    noApps.setFont(new Font(20.0));
                    noApps.backgroundProperty().set(buBack);
                    appCont.getChildren().addAll(noApps);
                } else {
                    appCont.getChildren().remove(0);
                }
                Button[] listings = new Button[setSize];
                int i = 0;
                while (appiter.hasNext()) {
                    Content content = new Content();
                    content = (NewDatabase.Content) appiter.next();
                    appButt(content, listings, i, appCont);
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

        vbutt.getChildren().addAll(music, book, aBook, podcast, video, apps, phoneB, throwaway);
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

        // Create Search Pane
        searchResults.backgroundProperty().set(cenBack);
        searchPane.backgroundProperty().set(cenBack);
        searchPane.setFitToWidth(true);
        searchPane.setFitToHeight(true);
        searchPane.setPannable(false);

        pBooksTall.backgroundProperty().set(cenBack);
        overallPBooks.backgroundProperty().set(cenBack);
        overallPBooks.setFitToWidth(true);
        overallPBooks.setFitToHeight(true);
        overallPBooks.setPannable(false);

        // create settings VBox
        Button lightMode = new Button("Change to light mode.");       //Creates button
        lightMode.backgroundProperty().set(buBack);         //adds transparent background
        lightMode.setTextFill(Paint.valueOf("BBBBBB"));
        lightMode.setPadding(inset);

        lightMode.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent entered) {
                lightMode.backgroundProperty().set(hiBack);
            }
        });

        lightMode.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent entered) {
                lightMode.backgroundProperty().set(buBack);
            }
        });

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
        Image androidImage = new Image("Phone.png");   //Load video Icon for imageview
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
        sync.setTextFill(Paint.valueOf("BBBBBB"));
        sync.backgroundProperty().set(buBack);         //adds transparent background

        Button stopSync = new Button("Cancel Sync");       //Creates button
        stopSync.setTextFill(Paint.valueOf("BBBBBB"));
        stopSync.backgroundProperty().set(buBack);         //adds transparent background
        stopSync.setPadding(inset);

        Button stopRestore = new Button("Cancel Restore");       //Creates button
        stopRestore.setTextFill(Paint.valueOf("BBBBBB"));
        stopRestore.backgroundProperty().set(buBack);         //adds transparent background
        stopRestore.setPadding(inset);


        Button stopBackup = new Button("Cancel Sync");       //Creates button
        stopBackup.setTextFill(Paint.valueOf("BBBBBB"));
        stopBackup.backgroundProperty().set(buBack);         //adds transparent background
        stopBackup.setPadding(inset);


        highLight(sync);
        sync.setPadding(inset);

        sync.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                t.initializePhone(0);
                t.sync();
                System.out.println("Sync Pressed");
                midButt.getChildren().add(stopSync);

            }
        });

        stopSync.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                t.initializePhone(0);

                System.out.println("stopo Sync Pressed");
                t.setRunningR();
                midButt.getChildren().remove(stopSync);

            }
        });



        Button backup = new Button("Backup Phone");       //Creates button
        backup.setTextFill(Paint.valueOf("BBBBBB"));
        backup.backgroundProperty().set(buBack);         //adds transparent background
        backup.setPadding(inset);

        highLight(backup);
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
                midButt.getChildren().add(stopBackup);

            }
        });
        stopBackup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                t.initializePhone(0);

                System.out.println("stopo backup Pressed");
                t.setRunningB();
                midButt.getChildren().remove(stopBackup);

            }
        });

        Button copy = new Button("Restore Phone");       //Creates button
        copy.setTextFill(Paint.valueOf("BBBBBB"));
        copy.backgroundProperty().set(buBack);         //adds transparent background

//        });   2EB900 green color
        highLight(copy);

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
        stopRestore.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                t.initializePhone(0);
                try {
                    t.backup();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("stop restore Pressed");
                midButt.getChildren().add(stopRestore);

            }
        });

        Button android = new Button("", Android);
        copy.backgroundProperty().set(buBack);
        copy.setPadding(inset);



        midButt.setPadding(new Insets(5, 5, 5, 5));
        midButt.setSpacing(50);
        midButt.getChildren().addAll(sync, backup, copy, throwaway);

        phoneB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                mainStack.getChildren().clear();//(phoneMidRow, musicPane);
                if (t.checkConnection()) {
                    Label battery = new Label("" + t.getPhoneBattery());
                    Label phoneName = new Label("" + t.getPhoneModel());
                    //phoneStack.getChildren().addAll(Android, phoneName, battery);
                } else
                    try {
                        phoneStack.getChildren().addAll(Android);
                    } catch (IllegalArgumentException E) {
                        System.out.println(E);
                    }


                mainStack.getChildren().add(phoneMidRow);


            }
        });

        Insets sliderIn = new Insets(8.0, 0.0, 8.0, 0.0);

        //Music Controll

//music volume slider

        musicPlayIcon.setImage(PlayIcon);
        musicPauseIcon.setImage(PauseIcon);   //adds icon to imageview
        musicBackIcon.setImage(BackIcon);
        musicVolumeSlider.setPadding(sliderIn);
//        //music progress slider
//        Slider musicProgressSlider = new Slider();
//        musicProgressSlider.setMin(0);
//        musicProgressSlider.setMax(100);
//        musicProgressSlider.setValue(0);
//        musicProgressSlider.setPadding(sliderIn);
        //Play


        Button musicPlay = new Button("", musicPlayIcon);       //Creates button
        Button musicMute = new Button("", musicMuteIcon);       //Creates button

        musicMuteIcon.setImage(highVolIcon);
        musicPlay.backgroundProperty().set(buBack);         //adds transparent background
        musicPlay.setPadding(inset);

        musicPlay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                play(videoCont);
            }
        });

        // forward
        Image forwardIcon = new Image("ForwardButton.png");   //Load forward  Icon for imageview
        ImageView forwardMusicIcon = new ImageView();
        forwardMusicIcon.setImage(forwardIcon);                  //adds icon to imageview
        Button musicForward = new Button("", forwardMusicIcon);       //Creates button

        musicForward.backgroundProperty().set(buBack);         //adds transparent background
        musicForward.setPadding(inset);

        musicForward.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
            forward();
            }
        });
        //Backward


        Button musicBack = new Button("", musicBackIcon);       //Creates button

        musicBack.backgroundProperty().set(buBack);         //adds transparent background
        musicBack.setPadding(inset);

        musicBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
            backward();
            }
        });

        //Volume


        musicMute.backgroundProperty().set(buBack);         //adds transparent background
        musicMute.setPadding(inset);

        musicMute.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
            mute();

            }
        });


        musicControll.getChildren().addAll(musicBack, musicPlay, musicForward, musicMute, musicVolumeSlider);
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
                    sql.addContent("" + db.getFiles().toString(), "Music");
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
                    sql.addContent("" + db.getFiles().toString(), "EBook");
                    System.out.println("eBook added");
                    ebookTester.setText(db.getFiles().toString());
                    success = true;
                }
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

                    sql.addContent("" + db.getFiles().toString(), "audioBook");
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
                    sql.addContent("" + db.getFiles().toString(), "pacapp.Video");
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
                    sql.addContent("" + db.getFiles().toString(), "pacapp.Podcast");
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
                    sql.addContent("" + db.getFiles().toString(), "pacapp.App");
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

        bottomAnchorPane.getChildren().add(controllStack);

        //MusicPane
        mainStack.getChildren().clear();
        mainStack.getChildren().add(musicPane);
        musicCont.getChildren().clear();

        Set mset = dao.getAllContentByType("Music");
        System.out.println("Music Showing");
        Iterator miter = mset.iterator();
        int setSize = mset.size();

        if (setSize == 0) {
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
            musButt(content, listings, i, musicCont, mediaSet);
            i++;
        }


        phoneMidRow.getChildren().addAll(phoneStack, midButt);

        centerAnchorPane.getChildren().addAll(tAnchor);
        centerAnchorPane.setRightAnchor(tAnchor, 0.0);
        centerAnchorPane.setLeftAnchor(tAnchor, 5.0);
        centerAnchorPane.setTopAnchor(tAnchor, 5.0);
        centerAnchorPane.setBottomAnchor(tAnchor, 5.0);


        controllStack.getChildren().addAll(musicControll);
        bottomAnchorPane.getChildren().addAll(RSSLookup);
        bottomAnchorPane.setRightAnchor(RSSLookup, 10.0);
        bottomAnchorPane.setLeftAnchor(controllStack, 10.0);
        bottomAnchorPane.setTopAnchor(controllStack, 10.0);
        bottomAnchorPane.setBottomAnchor(controllStack, 10.0);
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

    public static void musButt(Content objs, Button[] L, int i, VBox cont, Media[] M) {
        String name = objs.getContentName();
        HBox doubleButt = new HBox();
        doubleButt.setPadding(inset);
        StackPane syncStatus = new StackPane();
        Button syncer = new Button("✔");
        syncer.setTextFill(Paint.valueOf("Green"));
        Button unsyncer = new Button("✘");
        syncer.backgroundProperty().set(buBack);
        unsyncer.backgroundProperty().set(buBack);
        MenuBar men = new MenuBar();
        syncer.setPadding(inset);

        men.setStyle("-fx-selection-bar: #515151;");

        men.backgroundProperty().set(buBack);
        addMusicMenu(men,objs);

        if (objs.getWantToSync()) {

            syncStatus.getChildren().add(syncer);
        } else {
            syncStatus.getChildren().add(unsyncer);
        }

        syncer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                dao.setSyncStatus(objs);
                syncStatus.getChildren().clear();
                syncStatus.getChildren().add(unsyncer);

                System.out.println(name + " switched to sync = " + objs.getWantToSync());

            }
        });
        unsyncer.setOnAction(new EventHandler<ActionEvent>() {
                                 @Override
                                 public void handle(ActionEvent press) {

                                     dao.setSyncStatus(objs);
                                     syncStatus.getChildren().clear();
                                     syncStatus.getChildren().add(syncer);

                                     System.out.println(name + " switched to sync = " + objs.getWantToSync());

                                 }
                             }

        );


        String thefuquwant = objs.getLocation();
        thefuquwant = "file:" + thefuquwant.replace("\\", "/");
        File file = new File(objs.getLocation());
        URI uri = file.toURI();
        M[i] = new Media(uri.toString());
        musicList.add(i, M[i]);
        L[i] = new Button(name);
        L[i].setTextFill(Paint.valueOf("BBBBBB"));
        L[i].backgroundProperty().set(buBack);
        highLight(L[i]);

        L[i].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                try {
                    musicView.getMediaPlayer().dispose();
                } catch (Exception E) {
                    System.out.println(E);
                }

                playahMakah(musicList.get(i), musicView, musicVolumeSlider);
                System.out.println(musicList.get(i).toString());
                System.out.println(name + " Pressed");

                musicView.getMediaPlayer().play();


            }
        });
        doubleButt.getChildren().addAll(men,L[i], syncStatus);
        cont.getChildren().add(doubleButt);


    }

    public static void podButt(Content objs, Button[] L, int i, VBox cont, Media[] M) {
        String name = objs.getContentName();
        HBox doubleButt = new HBox();
        StackPane syncStatus = new StackPane();
        Button syncer = new Button("✔");
        syncer.setTextFill(Paint.valueOf("Green"));
        Button unsyncer = new Button("✘");
        syncer.backgroundProperty().set(buBack);
        unsyncer.backgroundProperty().set(buBack);
        MenuBar men = new MenuBar();
        men.setStyle("-fx-selection-bar: #515151;");
        men.backgroundProperty().set(buBack);
        addMenu(men,objs);


        if (objs.getWantToSync()) {

            syncStatus.getChildren().add(syncer);
        } else {
            syncStatus.getChildren().add(unsyncer);
        }
        syncer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {

                dao.setSyncStatus(objs);
                syncStatus.getChildren().clear();
                syncStatus.getChildren().add(unsyncer);

                System.out.println(name + " switched to sync = " + objs.getWantToSync());

            }
        });
        unsyncer.setOnAction(new EventHandler<ActionEvent>() {
                                 @Override
                                 public void handle(ActionEvent press) {

                                     dao.setSyncStatus(objs);
                                     syncStatus.getChildren().clear();
                                     syncStatus.getChildren().add(syncer);

                                     System.out.println(name + " switched to sync = " + objs.getWantToSync());

                                 }
                             }

        );

        String thefuquwant = objs.getLocation();
        thefuquwant = "file:" + thefuquwant.replace("\\", "/");
        File file = new File(objs.getLocation());
        URI uri = file.toURI();
        M[i] = new Media(uri.toString());
        podcastList.add(i, M[i]);
        L[i] = new Button(name);
        L[i].setTextFill(Paint.valueOf("BBBBBB"));
        L[i].backgroundProperty().set(buBack);

        highLight(L[i]);
        L[i].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                try {
                    podcastView.getMediaPlayer().dispose();
                } catch (Exception E) {
                    System.out.println(E);
                }
                System.out.println(i);
                try {
                    playahMakah(podcastList.get(i), podcastView, podcastVolumeSlider);
                } catch (Exception T) {

                }
                System.out.println(podcastList.get(i).toString());
                System.out.println(name + " Pressed");

                podcastView.getMediaPlayer().play();


            }
        });
        doubleButt.getChildren().addAll(men,L[i], syncStatus);
        cont.getChildren().add(doubleButt);
    }

    public static void ebkButt(Content objs, Button[] L, int i, VBox cont) {
        System.out.println(i);
        String name = objs.getContentName();
        HBox doubleButt = new HBox();
        StackPane syncStatus = new StackPane();
        Button syncer = new Button("✔");
        syncer.setTextFill(Paint.valueOf("Green"));
        Button unsyncer = new Button("✘");
        syncer.backgroundProperty().set(buBack);
        unsyncer.backgroundProperty().set(buBack);
        MenuBar men = new MenuBar();
        men.setStyle("-fx-selection-bar: #515151;");
        men.backgroundProperty().set(buBack);
        addMenu(men,objs);

        if (objs.getWantToSync()) {

            syncStatus.getChildren().add(syncer);
        } else {
            syncStatus.getChildren().add(unsyncer);
        }
        syncer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {

                dao.setSyncStatus(objs);
                syncStatus.getChildren().clear();
                syncStatus.getChildren().add(unsyncer);
                System.out.println(name + " switched to sync = " + objs.getWantToSync());

            }
        });
        unsyncer.setOnAction(new EventHandler<ActionEvent>() {
                                 @Override
                                 public void handle(ActionEvent press) {
                                     dao.setSyncStatus(objs);
                                     syncStatus.getChildren().clear();
                                     syncStatus.getChildren().add(syncer);
                                     System.out.println(name + " switched to sync = " + objs.getWantToSync());

                                 }
                             }

        );
        String thefuquwant = objs.getLocation();
        thefuquwant = "file:" + thefuquwant.replace("\\", "/");
        File file = new File(objs.getLocation());
        URI uri = file.toURI();
        L[i] = new Button(name);
        L[i].setTextFill(Paint.valueOf("BBBBBB"));
        L[i].backgroundProperty().set(buBack);

        highLight(L[i]);

        L[i].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                try {
                    String book = new Scanner(new File(objs.getLocation())).useDelimiter("\\Z").next();
                    cont.getChildren().clear();
                    Text bookT = new Text(book);
                    bookT.setFill(Paint.valueOf("BBBBBB"));//.setTextFill(Paint.valueOf("BBBBBB"));
                    cont.getChildren().add(bookT);

                } catch (IOException E) {
                    System.out.println(E);
                }
                System.out.println(name + " Pressed");

            }
        });
        doubleButt.getChildren().addAll(men,L[i], syncStatus);
        cont.getChildren().add(doubleButt);
    }

    public static void abkButt(Content objs, Button[] L, int i, VBox cont, Media[] M) {
        String name = objs.getContentName();
        HBox doubleButt = new HBox();
        StackPane syncStatus = new StackPane();
        Button syncer = new Button("✔");
        syncer.setTextFill(Paint.valueOf("Green"));
        Button unsyncer = new Button("✘");
        syncer.backgroundProperty().set(buBack);
        unsyncer.backgroundProperty().set(buBack);
        MenuBar men = new MenuBar();
        men.setStyle("-fx-selection-bar: #515151;");
        men.backgroundProperty().set(buBack);
        addMenu(men,objs);

        if (objs.getWantToSync()) {

            syncStatus.getChildren().add(syncer);
        } else {
            syncStatus.getChildren().add(unsyncer);
        }

        syncer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                dao.setSyncStatus(objs);
                syncStatus.getChildren().clear();
                syncStatus.getChildren().add(unsyncer);

                System.out.println(name + " switched to sync = " + objs.getWantToSync());

            }
        });
        unsyncer.setOnAction(new EventHandler<ActionEvent>() {
                                 @Override
                                 public void handle(ActionEvent press) {

                                     dao.setSyncStatus(objs);
                                     syncStatus.getChildren().clear();
                                     syncStatus.getChildren().add(syncer);

                                     System.out.println(name + " switched to sync = " + objs.getWantToSync());

                                 }
                             }

        );


        String thefuquwant = objs.getLocation();
        thefuquwant = "file:" + thefuquwant.replace("\\", "/");
        File file = new File(objs.getLocation());
        URI uri = file.toURI();
        M[i] = new Media(uri.toString());
        abookList.add(i, M[i]);
        L[i] = new Button(name);
        L[i].setTextFill(Paint.valueOf("BBBBBB"));
        L[i].backgroundProperty().set(buBack);

        highLight(L[i]);

        L[i].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                try {
                    aBookView.getMediaPlayer().dispose();
                } catch (Exception E) {
                    System.out.println(E);
                }
                System.out.println(i);
                try {
                    playahMakah(abookList.get(i), aBookView, abookVolumeSlider);
                } catch (Exception T) {

                }
                System.out.println(abookList.get(i).toString());
                System.out.println(name + " Pressed");

                aBookView.getMediaPlayer().play();

            }
        });
        doubleButt.getChildren().addAll(men,L[i], syncStatus);
        cont.getChildren().add(doubleButt);
    }

    public static void vidButt(Content objs, Button[] L, int i, VBox cont, Media[] M) {
        String name = objs.getContentName();
        HBox doubleButt = new HBox();
        StackPane syncStatus = new StackPane();
        Button syncer = new Button("✔");
        syncer.setTextFill(Paint.valueOf("Green"));
        Button unsyncer = new Button("✘");
        syncer.backgroundProperty().set(buBack);
        unsyncer.backgroundProperty().set(buBack);
        MenuBar men = new MenuBar();
        men.setStyle("-fx-selection-bar: #515151;");
        men.backgroundProperty().set(buBack);
        addMenu(men,objs);
        if (objs.getWantToSync()) {

            syncStatus.getChildren().add(syncer);
        } else {
            syncStatus.getChildren().add(unsyncer);
        }

        syncer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                dao.setSyncStatus(objs);
                syncStatus.getChildren().clear();
                syncStatus.getChildren().add(unsyncer);

                System.out.println(name + " switched to sync = " + objs.getWantToSync());

            }
        });
        unsyncer.setOnAction(new EventHandler<ActionEvent>() {
                                 @Override
                                 public void handle(ActionEvent press) {
                                     dao.setSyncStatus(objs);
                                     syncStatus.getChildren().clear();
                                     syncStatus.getChildren().add(syncer);

                                     System.out.println(name + " switched to sync = " + objs.getWantToSync());

                                 }
                             }

        );

        String thefuquwant = objs.getLocation();
        thefuquwant = "file:" + thefuquwant.replace("\\", "/");
        File file = new File(objs.getLocation());
        URI uri = file.toURI();
        M[i] = new Media(uri.toString());
        videoList.add(i, M[i]);
        L[i] = new Button(name);
        L[i].setTextFill(Paint.valueOf("BBBBBB"));
        L[i].backgroundProperty().set(buBack);

        highLight(L[i]);

        L[i].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                try {
                    videoView.getMediaPlayer().dispose();
                } catch (Exception E) {
                    System.out.println(E);
                }
                System.out.println(i);
                try {
                    playahMakah(videoList.get(i), videoView, videoVolumeSlider);
                } catch (Exception T) {

                }
                System.out.println(videoList.get(i).toString());
                System.out.println(name + " Pressed");

                videoView.getMediaPlayer().play();
                cont.getChildren().clear();
                cont.getChildren().add(videoView);

            }
        });
        doubleButt.getChildren().addAll(men,L[i], syncStatus);
        cont.getChildren().add(doubleButt);
    }

    public static void appButt(Content objs, Button[] L, int i, TilePane cont) {

        String name = objs.getContentName();

        Image appsIcon = new Image("AppDefault.png");   //Load Phone Icon for imageview
        ImageView appview = new ImageView();
        appview.setImage(appsIcon);
        L[i] = new Button(name, appview);
        L[i].setTextFill(Paint.valueOf("707070"));
        L[i].setContentDisplay(ContentDisplay.TOP);
        L[i].backgroundProperty().set(buBack);
        L[i].setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent press) {

                dao.setSyncStatus(objs);
                System.out.println(name + " switched to sync = " + objs.getWantToSync());
                if (objs.getWantToSync()) {
                    L[i].setTextFill(Paint.valueOf("BBBBBB"));
                } else {
                    L[i].setTextFill(Paint.valueOf("707070"));
                }

            }
        });
        cont.getChildren().add(L[i]);
    }

    public static void searchButt(Content objs, Button[] L, int i, VBox cont, Media[] M) {

        String name = objs.getContentName();
        HBox doubleButt = new HBox();
        StackPane syncStatus = new StackPane();
        Button syncer = new Button("✔");
        syncer.setTextFill(Paint.valueOf("Green"));
        Button unsyncer = new Button("✘");
        syncer.backgroundProperty().set(buBack);
        unsyncer.backgroundProperty().set(buBack);
        MenuBar men = new MenuBar();
        men.setStyle("-fx-selection-bar: #515151;");
        men.backgroundProperty().set(buBack);
        addMenu(men,objs);
        if (objs.getWantToSync()) {

            syncStatus.getChildren().add(syncer);
        } else {
            syncStatus.getChildren().add(unsyncer);
        }
        syncer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {

                dao.setSyncStatus(objs);
                syncStatus.getChildren().clear();
                syncStatus.getChildren().add(unsyncer);

                System.out.println(name + " switched to sync = " + objs.getWantToSync());

            }
        });
        unsyncer.setOnAction(new EventHandler<ActionEvent>() {
                                 @Override
                                 public void handle(ActionEvent press) {
                                     dao.setSyncStatus(objs);
                                     syncStatus.getChildren().clear();
                                     syncStatus.getChildren().add(syncer);

                                     System.out.println(name + " switched to sync = " + objs.getWantToSync());

                                 }
                             }

        );

        File file = new File(objs.getLocation());
        URI uri = file.toURI();
        try {
            M[i] = new Media(uri.toString());
        } catch (MediaException E) {
            System.out.println(E);
        }
        L[i] = new Button(name);
        L[i].setTextFill(Paint.valueOf("BBBBBB"));
        L[i].backgroundProperty().set(buBack);


        highLight(L[i]);

        L[i].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent press) {
                System.out.println(name + " Pressed");

            }
        });
        doubleButt.backgroundProperty().set(buBack);
        doubleButt.getChildren().addAll(men,L[i], syncStatus);
        cont.getChildren().add(doubleButt);

    }

    public static MediaPlayer playahMakah(Media M, MediaView V, Slider S) {
        MediaPlayer player = new MediaPlayer(M);
        V.setMediaPlayer(player);
        player.volumeProperty().bindBidirectional(S.valueProperty());
        return player;
    }

    public static void highLight(Button butt) {
        butt.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent entered) {
                butt.backgroundProperty().set(hiBack);
            }
        });

        butt.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent entered) {
                butt.backgroundProperty().set(buBack);
            }
        });
    }

    public static void play(VBox box){
        //music - 0,abook - 1, podcast - 2,video - 3
        MediaView view = new MediaView();
        List<Media> List = new ArrayList<>();
        Slider slider = new Slider();
        switch (type) {
            case 0:
                view = musicView;
                List = musicList;
                slider = musicVolumeSlider;
                break;
            case 1:
                view = aBookView;
                List = abookList;
                slider = abookVolumeSlider;
                break;
            case 2:
                view = podcastView;
                List = podcastList;
                slider = podcastVolumeSlider;
                break;
            case 3:
                view = videoView;
                List = videoList;
                slider = videoVolumeSlider;

                break;
            default:
            System.out.println("error");
            break;
        }

        System.out.println("Play Pressed");
        try {
            if(type == 3){
                box.getChildren().clear();
                box.getChildren().add(videoView);
                videoView.setFitWidth(videoView.getFitWidth());
                videoView.setFitHeight(videoView.getFitHeight());
            }
            view.getMediaPlayer().play();

        } catch (NullPointerException E) {
            playahMakah(List.get(0), view, slider);
            if(type == 3){
                box.getChildren().clear();
                box.getChildren().add(videoView);
            }
            view.getMediaPlayer().play();
        }

        musicPlayIcon.setImage(PlayIcon);
        if (view.getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING) {
            view.getMediaPlayer().pause();
            musicPlayIcon.setImage(PauseIcon);

        }
    }
    public static void forward(){
        //music - 0,abook - 1, podcast - 2,video - 3
        MediaView view = new MediaView();
        List<Media> List = new ArrayList<>();
        Slider slider = new Slider();
        switch (type) {
            case 0:
                view = musicView;
                List = musicList;
                slider = musicVolumeSlider;
                break;
            case 1:
                view = aBookView;
                List = abookList;
                slider = abookVolumeSlider;
                break;
            case 2:
                view = podcastView;
                List = podcastList;
                slider = podcastVolumeSlider;
                break;
            case 3:
                view = videoView;
                List = videoList;
                slider = videoVolumeSlider;
                break;
            default:
                System.out.println("error");
                break;
        }
        view.getMediaPlayer().dispose();
        i++;
        view.getMediaPlayer().pause();
        view.setMediaPlayer(playahMakah(List.get(i %= List.size()), view, slider));
        view.getMediaPlayer().play();
        System.out.println("Forward Pressed");
    }

    public static void backward(){

        //music - 0,abook - 1, podcast - 2,video - 3
        MediaView view = new MediaView();
        List<Media> List = new ArrayList<>();
        Slider slider = new Slider();
        switch (type) {
            case 0:
                view = musicView;
                List = musicList;
                slider = musicVolumeSlider;
                break;
            case 1:
                view = aBookView;
                List = abookList;
                slider = abookVolumeSlider;
                break;
            case 2:
                view = podcastView;
                List = podcastList;
                slider = podcastVolumeSlider;
                break;
            case 3:
                view = videoView;
                List = videoList;
                slider = videoVolumeSlider;
                break;
            default:
                System.out.println("error");
                break;
        }

        System.out.println(view.getMediaPlayer().getCurrentTime());
        if (!view.getMediaPlayer().getCurrentTime().lessThan(new Duration(5000.0))) {
            view.getMediaPlayer().stop();
            view.getMediaPlayer().play();

        } else {
            view.getMediaPlayer().dispose();
            if (i == 0) {
                i = List.size();
            } else
                i--;
            view.setMediaPlayer(playahMakah(List.get(i %= List.size()), view, slider));
            view.getMediaPlayer().play();
        }
        System.out.println("Back Pressed");
    }

    public static void mute(){
        //music - 0,abook - 1, podcast - 2,video - 3
        MediaView view = new MediaView();
        List<Media> List = new ArrayList<>();
        Slider slider = new Slider();
        switch (type) {
            case 0:
                view = musicView;
                List = musicList;
                slider = musicVolumeSlider;
                break;
            case 1:
                view = aBookView;
                List = abookList;
                slider = abookVolumeSlider;
                break;
            case 2:
                view = podcastView;
                List = podcastList;
                slider = podcastVolumeSlider;
                break;
            case 3:
                view = videoView;
                List = videoList;
                slider = videoVolumeSlider;
                break;
            default:
                System.out.println("error");
                break;
        }
        view.getMediaPlayer().setMute(!view.getMediaPlayer().isMute());
        System.out.println("Music Mute Pressed");
        if (view.getMediaPlayer().isMute()) {
            System.out.println(view.getMediaPlayer().isMute());
            slider.setValue(0);
            musicMuteIcon.setImage(muteIcon);
        } else {
            System.out.println("other");
            if (slider.getValue() <= 0.33) {
                musicMuteIcon.setImage(lowVolIcon);
            } else if (slider.getValue() <= 0.66) {
                musicMuteIcon.setImage(medVolIcon);
            } else {
                musicMuteIcon.setImage(highVolIcon);
            }
            view.getMediaPlayer().setVolume(slider.getValue() / 100.0);
            slider.setValue(view.getMediaPlayer().getVolume() * 100);

        }

    }

    public static void addBook(Book B,VBox h){
    Button link = new Button();
    link.backgroundProperty().set(buBack);
    link.setTextFill(Paint.valueOf("BBBBBB"));
    link.setText(B.getTitle() +":   " +  B.getSubtitle() +" By: " +   B.getAuthors());
    link.wrapTextProperty().setValue(false);



        link.setOnAction(new EventHandler<ActionEvent>() {
                               @Override public void handle(ActionEvent e) {
                                   try {
                                       Desktop.getDesktop().browse(new URI("https://www.amazon.com/s?k=" + B.isbn + "&ref=nb_sb_noss" ));
                                   } catch (IOException e1) {
                                       e1.printStackTrace();
                                   } catch (URISyntaxException e1) {
                                       e1.printStackTrace();
                                   }
                               }
                           }

        );
    h.getChildren().add(link);
    }
    public static void addMenu(MenuBar M,Content objs){

        Menu menu = new Menu("▼");
        M.getMenus().add(menu);
        MenuItem rename = new MenuItem("Rename");
        //rename.setStyle("-fx-background-color: #454545;");


        rename.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                TextInputDialog newNameD = new TextInputDialog("");
                newNameD.setTitle("Rename");
                newNameD.setContentText("Please enter the new Content name: ");
                newNameD.setHeaderText(null);
                newNameD.getModality().equals(false);
                Optional<String> newName = newNameD.showAndWait();
                if (newName.isPresent()) {
                    dao.updateContentName(objs,newName.get());
                }
                System.out.println("rename");

            }
        });
        MenuItem delete = new MenuItem("Delete");
        //delete.setStyle("-fx-menu-item: #454545;");
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("delete");
                dao.deleteContent(objs);
            }
        });



        menu.getItems().addAll(rename);
        menu.getItems().add(delete);


    }
    public static void addMusicMenu(MenuBar M,Content objs){

        Menu menu = new Menu("▼");
        M.getMenus().add(menu);
        MenuItem rename = new MenuItem("Rename");
        //rename.setStyle("-fx-background-color: #454545;");


        rename.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                TextInputDialog newNameD = new TextInputDialog("");
                newNameD.setTitle("Rename");
                newNameD.setContentText("Please enter the new Content name: ");
                newNameD.setHeaderText(null);
                newNameD.getModality().equals(false);
                Optional<String> newName = newNameD.showAndWait();
                if (newName.isPresent()) {
                    dao.updateContentName(objs,newName.get());
                }
                System.out.println("rename");

            }
        });
        menu.getItems().addAll(rename);

        Set pset = pdao.getAllPlaylists();
        Iterator piter = pset.iterator();
        int setSize = pset.size();
        int i = 0;

        while (piter.hasNext()) {

            final Playlist playlist = (NewDatabase.Playlist) piter.next();
            //content =
            MenuItem addPl = new MenuItem("Add to " + playlist.getPlaylistName());
            addPl.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    pdao.insertContentIntoPlaylist(objs,playlist);
                    System.out.println(objs.getContentName() + " added to " + playlist.getPlaylistName());

                }
            });
            menu.getItems().add(addPl);
            i++;
        }



        MenuItem delete = new MenuItem("Delete");
        //delete.setStyle("-fx-menu-item: #454545;");
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("delete");
                dao.deleteContent(objs);
            }
        });




        menu.getItems().add(delete);


    }
    public static void plButt(Playlist objs, Button[] L, int i, TilePane cont,VBox pane,StackPane anch) {

        String name = objs.getPlaylistName();
        Image appsIcon = new Image("AlbumDefault.png");   //Load Phone Icon for imageview
        ImageView appview = new ImageView();
        appview.setImage(appsIcon);
        appview.setFitWidth(200);
        appview.setPreserveRatio(true);
        appview.setSmooth(true);
        appview.setCache(true);
        L[i] = new Button(name, appview);
        L[i].setTextFill(Paint.valueOf("707070"));
        L[i].setContentDisplay(ContentDisplay.TOP);
        L[i].setPadding(inset);
        L[i].backgroundProperty().set(buBack);
        System.out.println("sout;asdhgo");
        L[i].setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent press) {
                System.out.println("in playlist individual button");
                anch.getChildren().clear();
                anch.getChildren().add(pane);
                Set pset = objs.geContents();
                Iterator piter = pset.iterator();
                int setSize = pset.size();
                Button[] L = new Button[setSize];
                Media[] mediaSet = new Media[setSize];
                int i = 0;
                while (piter.hasNext()) {
                    Content content = (Content)piter.next();

                    musButt(content,L,i,pane,mediaSet);
                }
            }
        });
            cont.getChildren().add(L[i]);
    }
}
