import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AppController {
    @FXML TextArea screen;

    @FXML TextField apparatNavn;
    @FXML TextArea apparatBeskrivelse;

    @FXML TextField aparatOvelse;
    @FXML TextField aparatAparat;
    @FXML TextField aparatKg;
    @FXML TextField aparatSett;
    @FXML TextField utenOvelse;
    @FXML TextArea utenBeskrivelse;

    @FXML DatePicker oktDato;
    @FXML TextField oktTidspunkt;
    @FXML TextField oktVarighet;
    @FXML ComboBox oktForm;
    @FXML ComboBox oktPrestasjon;

    private String errors = "";
    private TreningsApp app;


    public AppController() {
        this.app = new TreningsApp();
    }

    @FXML public void initialize(){
        updateScreen(this.app.testConnection());

        oktForm.getItems().removeAll(oktForm.getItems());
        oktPrestasjon.getItems().removeAll(oktPrestasjon.getItems());
        for (int i = 0; i <= 10; i++) {
            oktForm.getItems().add(i);
            oktPrestasjon.getItems().add(i);
        }
        oktForm.getSelectionModel().select(5);
        oktPrestasjon.getSelectionModel().select(5);
    }

    @FXML private void regApparat() {
        try {
            this.app.registrerApparat(
                apparatNavn.getText(),
                apparatBeskrivelse.getText()
            );
        } catch (Exception e){
            this.updateScreen(e.getMessage());
        }

    }

    @FXML private void regOvingAparat(){
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
        try {
            this.addOvelse(
                new Ovelse(
                    aparatOvelse.getText(),
                    aparatAparat.getText(),
                    Double.parseDouble(aparatKg.getText()),
                    Integer.parseInt(aparatSett.getText())
                )
            );
            clearFields();
        } catch(Exception e ) {
            this.errors = e.getMessage();
        }
        updateScreen();
    }

    @FXML private void regOvingUten(){
        if (utenOvelse.getText().equals("") ||
            utenBeskrivelse.getText().equals(""))
        {
            errors = "Ett eller flere tomme felter.";
            updateScreen();
            return;
        }
        try {
            this.addOvelse(
                new Ovelse(
                    utenOvelse.getText(),
                    utenBeskrivelse.getText()
                )
            );
            clearFields();
        } catch(Exception e ) {
            this.errors = e.getMessage();
        }
        updateScreen();
    }

    private void addOvelse(Ovelse o) {
        this.app.addOvelse(o);
    }

    @FXML private void registrerOkt() {

        try {
            Okt okt = new Okt(
                oktDato.getValue(),
                oktTidspunkt.getText(),
                oktVarighet.getText(),
                Integer.parseInt(oktForm.getSelectionModel().getSelectedItem().toString()),
                Integer.parseInt(oktPrestasjon.getSelectionModel().getSelectedItem().toString())
            );
            this.app.registrerOkt(okt);
        } catch(Exception e) {
            updateScreen(e.getMessage());
        }

    }

    private void updateScreen(String message) {
        String s = message;
        s += ("\n" + this.errors);
        screen.setText(s);
        this.errors = "";
    }

    private void updateScreen() {
        screen.setText(this.app.listOvelser() + this.errors);
        this.errors = "";
    }

    @FXML
    private void showApparater() {
        String[] kolonner = {"apparatid", "apparatnavn", "apparatbrukbeskrivelse"};
        String tabell = "apparat";
        this.updateScreen(app.listTable(kolonner, tabell));
    }
    @FXML
    private void showOvelser() {
        String[] kolonner = {"ovelseid", "navn"};
        String tabell = "ovelse";
        this.updateScreen(app.listTable(kolonner, tabell));
    }
    @FXML
    private void showOvelsesgrupper() {
        String[] kolonner = {"ovelsesgruppeid", "ovelsesgruppenavn"};
        String tabell = "ovelsesgruppe";
        this.updateScreen(app.listTable(kolonner, tabell));
    }

    @FXML
    private void showTreningsokter() {
        String[] kolonner = {"oktid", "dato", "tidspunkt", "varighet", "form", "prestasjon"};
        String tabell = "treningsokt";
        this.updateScreen(app.listTable(kolonner, tabell));
    }

    private void clearFields() {
        this.aparatOvelse.setText("");
        this.aparatAparat.setText("");
        this.aparatKg.setText("");
        this.aparatSett.setText("");
        this.utenOvelse.setText("");
        this.utenBeskrivelse.setText("");
    }


}
