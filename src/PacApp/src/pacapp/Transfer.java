/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacapp;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
 *
 * @author andrewmenezes
 */
public class Transfer extends PacApp {

    public void pTrans(Stage stage) throws Exception {
        AnchorPane tAnchor = new AnchorPane();

        Insets inset = new Insets(-1, -1, -1, -1);

        javafx.geometry.Insets bFillIn = new javafx.geometry.Insets(0);

        CornerRadii bFillCR = new CornerRadii(0);

        BackgroundFill buFill = new BackgroundFill(Paint.valueOf("TRANSPARENT"), bFillCR, bFillIn);
        Background buBack = new Background(buFill);

        Button sync = new Button("Sync Phone");       //Creates button with image
        sync.backgroundProperty().set(buBack);         //adds transparent background
        sync.setPadding(inset);

        Button backup = new Button("Backup Phone");       //Creates button with image
        backup.backgroundProperty().set(buBack);         //adds transparent background
        backup.setPadding(inset);

        Button Copy = new Button("Duplicate Phone");       //Creates button with image
        Copy.backgroundProperty().set(buBack);         //adds transparent background

    }
}
