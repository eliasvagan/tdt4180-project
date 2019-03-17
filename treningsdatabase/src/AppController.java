import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;


public class AppController {
    @FXML TextArea screen;

    @FXML TextField aparatOvelse;
    @FXML TextField aparatAparat;
    @FXML TextField aparatKg;
    @FXML TextField aparatSett;

    @FXML TextField utenOvelse;
    @FXML TextArea utenBeskrivelse;

    private DBConn dbconn;
    private String errors = "";
    private List<Ovelse> ovelser;

    public AppController() {
        this.ovelser = new ArrayList<Ovelse>();
        // TODO: Få dette til å funke -> updateScreen("Appen har startet.");
    }

    @FXML public void initialize(){
        try {
            this.dbconn.connect();
        } catch(Exception e) {
            this.errors = "Databasefeil: " + e.getMessage();
            updateScreen();
        }

    }

    @FXML public void regOvingAparat(){
        if (aparatOvelse.getText().equals("") ||
            aparatAparat.getText().equals("") ||
            aparatKg.getText().equals("") ||
            aparatSett.getText().equals(""))
        {
            this.errors += "Ett eller flere tomme felter.";
            updateScreen();
            return;
        }
        try {
            Double.parseDouble(aparatKg.getText());
            Integer.parseInt(aparatSett.getText());
        } catch (Exception e) {
            this.errors += "Ulovlig input " + e.getMessage();
            updateScreen();
            return;
        }
        this.addOvelse(new Ovelse(
                aparatOvelse.getText(),
                aparatAparat.getText(),
                Double.parseDouble(aparatKg.getText()),
                Integer.parseInt(aparatSett.getText())
        ));

        updateScreen();
    }

    @FXML public void regOvingUten(){
        if (utenOvelse.getText().equals("") ||
            utenBeskrivelse.getText().equals(""))
        {
            errors = "Ett eller flere tomme felter";
            updateScreen();
            return;
        }
        this.addOvelse(new Ovelse(
                utenOvelse.getText(),
                utenBeskrivelse.getText()
        ));
        updateScreen();

    }

    private void addOvelse(Ovelse o) {
        this.ovelser.add(o);
    }
    private void updateScreen(String message) {
        String s = message;
        s += ("\n" + this.errors);
        screen.setText(s);
        this.errors = "";
    }
    private void updateScreen() {
        StringBuilder s = new StringBuilder();
        this.ovelser.forEach((e) -> s.append(e.toString() + "\n"));

        s.append("\n" + this.errors);

        screen.setText(s.toString());
        this.errors = "";
    }


}
