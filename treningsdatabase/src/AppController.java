public class AppController {
    @FXML public TextArea screen;

    private String s = "Her er det tekst";

    @FXML
    public void initialize(){
        screen.setText(s);
    }


}
