import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AppController {
    @FXML TextArea screen;

    @FXML TextField apparatNavn;
    @FXML TextArea apparatBeskrivelse;

    @FXML TextField aparatOvelse;
    @FXML ComboBox aparatAparat;
    @FXML TextField aparatKg;
    @FXML TextField aparatSett;
    @FXML TextField utenOvelse;
    @FXML TextArea utenBeskrivelse;

    @FXML DatePicker oktDato;
    @FXML TextField oktTidspunkt;
    @FXML TextField oktVarighet;
    @FXML ComboBox oktForm;
    @FXML ComboBox oktPrestasjon;
    @FXML TextArea oktNotat;

    @FXML ComboBox treningsOktComboBox;

    @FXML TextField queryAntallOkter;

    @FXML ComboBox velgOktMellom;
    @FXML DatePicker dateFrom;
    @FXML DatePicker dateTo;

    @FXML ComboBox queryApparat;

    private String errors = "";
    private TreningsApp app;


    public AppController() {
        this.app = new TreningsApp();
    }

    @FXML public void initialize(){
        updateScreen(this.app.testConnection());
        updateDropdowns();

        oktForm.getItems().removeAll(oktForm.getItems());
        oktPrestasjon.getItems().removeAll(oktPrestasjon.getItems());

        for (int i = 0; i <= 10; i++) {
            oktForm.getItems().add(i);
            oktPrestasjon.getItems().add(i);
        }
        oktForm.getSelectionModel().select(5);
        oktPrestasjon.getSelectionModel().select(5);

    }

    private void updateDropdowns() {
        try {
            aparatAparat.getItems().removeAll(aparatAparat.getItems());
            queryApparat.getItems().removeAll(queryApparat.getItems());
            treningsOktComboBox.getItems().removeAll(treningsOktComboBox.getItems());
            velgOktMellom.getItems().removeAll(velgOktMellom.getItems());

            for (String a : this.app.getApparatValg().split("\n")) {
                aparatAparat.getItems().add(a);
                queryApparat.getItems().add(a);
            }
            for (String a: this.app.getTreningsOktValg().split("\n")) {
                treningsOktComboBox.getItems().add(a);
            }
            for (String a: this.app.getOvelserMellomValg().split("\n")) {
                velgOktMellom.getItems().add(a);
            }

            treningsOktComboBox.getSelectionModel().select(0);
            aparatAparat.getSelectionModel().select(0);
            queryApparat.getSelectionModel().select(0);
            velgOktMellom.getSelectionModel().select(0);
        } catch(Exception e) {
            this.updateScreen(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML private void regApparat() {
        try {
            this.app.registrerApparat(
                apparatNavn.getText(),
                apparatBeskrivelse.getText()
            );
            this.showApparater();
            this.clearFields();
            this.updateDropdowns();
        } catch (Exception e){
            this.updateScreen(e.getMessage());
        }
    }

    @FXML private void regOvingAparat(){
        if (aparatOvelse.getText().equals("") ||
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
            this.app.addOvelse(
                new Ovelse(
                    aparatOvelse.getText(),
                    Character.getNumericValue(aparatAparat.getSelectionModel().getSelectedItem().toString().charAt(0)),
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
            this.app.addOvelse(
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

    @FXML private void registrerOkt() {

        try {
            Okt okt = new Okt(
                oktDato.getValue(),
                oktTidspunkt.getText(),
                oktVarighet.getText(),
                Integer.parseInt(oktForm.getSelectionModel().getSelectedItem().toString()),
                Integer.parseInt(oktPrestasjon.getSelectionModel().getSelectedItem().toString()),
                oktNotat.getText()
            );
            this.app.registrerOkt(okt);
            this.showTreningsokter();
            this.updateDropdowns();
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

    @FXML private void showApparater() {
        String[] kolonner = {"apparatid", "apparatnavn", "apparatbrukbeskrivelse"};
        String tabell = "apparat";
        this.updateScreen(app.listTable(kolonner, tabell));
    }
    @FXML private void showOvelser() {
        String[] kolonner = {"ovelseid", "navn", "antallkg", "aparat", "apparatid", "antallSett", "apparatID", "tekstBeskrivelse"};
        String tabell = "ovelse";
        this.updateScreen(app.listTable(kolonner, tabell));
    }
    @FXML private void showTreningsokter() {
        String[] kolonner = {"oktid", "dato", "tidspunkt", "varighet", "form", "prestasjon"};
        String tabell = "treningsokt";
        this.updateScreen(app.listTable(kolonner, tabell));
//        this.updateScreen(app.listTreningsOkter());
    }
    @FXML private void showTreningsOktOvingRelasjoner() {
        String[] kolonner = {"oktid", "ovelseid"};
        String tabell = "treningsoktOvelse";
        this.updateScreen(app.listTable(kolonner, tabell));
    }
    @FXML private void showSelectedOkt() {
        this.updateScreen(
            this.app.getOktMedOvelser(
                Character.getNumericValue(treningsOktComboBox.getSelectionModel().getSelectedItem().toString().charAt(0))
            )
        );
    }
    @FXML private void showOkterN() {
        this.updateScreen(
            app.getOkterN(Integer.parseInt(this.queryAntallOkter.getText()))
        );
    }
    @FXML private void showOkterBetween() {
        try {
            this.updateScreen(
                app.getOkterBetween(
                    dateFrom.getValue().toString(),
                    dateTo.getValue().toString(),
                    velgOktMellom.getSelectionModel().getSelectedItem().toString().trim()
                )
            );
        } catch (NullPointerException e) {
            this.updateScreen("Ugyldig dato.");
        } catch (Exception e) {
            this.updateScreen(e.getMessage());
        }
    }
    @FXML private void showOvelserApparat() {
        try {
            this.updateScreen(
                this.app.getOvelserApparat(
                    Character.getNumericValue(queryApparat.getSelectionModel().getSelectedItem().toString().charAt(0))
                )
            );
        } catch (Exception e) {
            this.updateScreen(e.getMessage());
        }
    }

    private void clearFields() {
        this.aparatOvelse.setText("");
        this.aparatKg.setText("");
        this.aparatSett.setText("");
        this.utenOvelse.setText("");
        this.utenBeskrivelse.setText("");
        this.apparatNavn.setText("");
        this.apparatBeskrivelse.setText("");
        this.oktNotat.setText("");
        this.queryAntallOkter.setText("");
    }


}
