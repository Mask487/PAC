/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pac.desktop;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author andrewmenezes
 */
public class FXMLPacController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    @FXML
    private void musicClicked(ActionEvent event) {
        System.out.println("You clicked music!");
        
    }
    @FXML
    private void eBookClicked(ActionEvent event) {
        System.out.println("You clicked eBooks!");
        
    }
    
    @FXML
    private void audioBookClicked(ActionEvent event) {
        System.out.println("You clicked audio books!");
        
    }
    
    @FXML
    private void podcastClicked(ActionEvent event) {
        System.out.println("You clicked podcasts!");
        
    }
    
    @FXML
    private void videoClicked(ActionEvent event) {
        System.out.println("You clicked videos!");
        
    }
    
    @FXML
    private void appsClicked(ActionEvent event) {
        System.out.println("You clicked apps!");
        
    }
   
     @FXML
    private void settingsClicked(ActionEvent event) {
        System.out.println("You clicked settings!");
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
