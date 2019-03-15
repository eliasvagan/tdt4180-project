import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;


public class AppController {
    @FXML TextArea screen;

    @FXML TextField aparatOvelse;
    @FXML TextField aparatAparat;
    @FXML TextField aparatKg;
    @FXML TextField aparatSett;

    @FXML TextField utenOvelse;
    @FXML TextArea utenBeskrivelse;

    private String s;

    public AppController() {
        s = "Appen har startet";
    }

    @FXML public void initialize(){
        screen.setText(s);
    }

    @FXML public void regOvingAparat(){
        if (aparatOvelse.getText().equals("") ||
            aparatAparat.getText().equals("") ||
            aparatKg.getText().equals("") ||
            aparatSett.getText().equals(""))
        {
            this.s = "Ett eller flere tomme felter.";
            updateScreen();
            return;
        }
        try {
            Double.parseDouble(aparatKg.getText());
            Integer.parseInt(aparatSett.getText());
        } catch (Exception e) {
            this.s = "Ulovlig input " + e.getMessage();
            updateScreen();
            return;
        }
        s = aparatOvelse.getText() + "\n" +
            aparatAparat.getText() + "\n" +
            aparatKg.getText() + "\n" +
            aparatSett.getText();
        updateScreen();
    }

    @FXML public void regOvingUten(){
        if (utenOvelse.getText().equals("") ||
            utenBeskrivelse.getText().equals(""))
        {
            s = "Ett eller flere tomme felter";
            updateScreen();
            return;
        }
        s = utenOvelse.getText() + "\n" +
            utenBeskrivelse.getText();
        updateScreen();

    }

    private void updateScreen() {
        screen.setText(s);
    }


}
