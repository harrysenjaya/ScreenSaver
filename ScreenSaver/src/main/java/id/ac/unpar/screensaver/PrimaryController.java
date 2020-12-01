package id.ac.unpar.screensaver;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {
    private Scraper scraper;
    private String session;
    
    public PrimaryController() throws IOException {
        this.scraper = new Scraper();
        this.session = this.scraper.login();         
        System.out.println(this.session);

    }

    
    
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    
    public void login(){
        
    }
}
