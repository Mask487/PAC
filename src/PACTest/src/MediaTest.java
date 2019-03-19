/*import javafx.scene.media.Media;
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


public void setDrag(JTextArea t){
        JTextArea myPanel = t;
        if(fileList.isEmpty()){
            myPanel.setDragEnabled(true);
        }
        else if(!fileList.isEmpty()){
            myPanel.setDragEnabled(false);
            fileList.clear();
        }
    }
    public void DragAndDrop(JTextArea t) {
        fileList = new ArrayList<>();
        final JTextArea myPanel = t;
        myPanel.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                String path;
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>) evt
                            .getTransferable().getTransferData(
                                    DataFlavor.javaFileListFlavor);
                    System.out.println(droppedFiles);
                    for (File file : droppedFiles) {
                        if (file.isDirectory()) {
                            getFiles(file.getAbsolutePath());
                        } else if (!file.isDirectory() && file.isFile() && file.getName().endsWith(".txt")) {
                            path = file.getAbsolutePath();
                            fileList.add(path);
                        } else {
                            System.out.println("ERROR, Unsupported File Type"
                                    + " Detected");
                        }
                    }
                    /*for (String s : fileList) {
                        try {
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                                    new FileInputStream(s)));
                            String line = null;
                            String text = "";
                            while ((line = bufferedReader.readLine()) != null) {
                                text += line + System.lineSeparator();
                            }
                            //myPanel.setText(text);
                            bufferedReader.close();
                            System.out.println("isFile " + text);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        //return fileList;
        setDrag(myPanel);
    } 
}


*/