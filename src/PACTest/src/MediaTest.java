import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MediaTest{
     Media pick = new Media("put.mp3"); // replace this with your own audio file
        MediaPlayer player = new MediaPlayer(pick);

        // Add a mediaView, to display the media. Its necessary !
        // This mediaView is added to a Pane
        MediaView mediaView = new MediaView(player);

        // Add to scene
        Group root = new Group(mediaView);
        Scene scene = new Scene(root, 500, 200);

        // Show the stage
        primaryStage.setTitle("Media Player");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Play the media once the stage is shown
        player.play();
}


//EXAMPLE SCRIPT
//link to documentation: https://docs.oracle.com/javafx/2/api/javafx/scene/media/MediaPlayer.html
//
//
//Once MediaView and needed buttons are in place
//Use a get() to get the desired file, most likely from database
//Can use built in methods to play() pause() stop() and set things like
//autoPlay() mute() and volume()

//not sure which once you want to use but if we implement playlists or "series"
//for podcast and other audio/video files then autoplay would be a must.

//not sure how to set metadata for when user pauses media and comes back later to start at the same spot
