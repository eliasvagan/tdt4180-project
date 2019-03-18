import java.util.ArrayList;
import java.util.List;

public class TreningsApp {
    private Database database;
    private List<Ovelse> ovelser;

    public TreningsApp() {
        this.ovelser = new ArrayList<Ovelse>();
        this.database = new Database("TreningsDatabasen");
    }

    public String testConnection() {
        try {
            this.database.connect();
            this.database.disconnect();
            return "Koblet til: " + this.database.getConnection();
        } catch(Exception e) {
            return "Feil: "+ e.getMessage();
        }
    }

    public void addOvelse(Ovelse o) {
        this.ovelser.add(o);
    }

    public String listOvelser() {
        StringBuilder sb = new StringBuilder();
        this.ovelser.forEach((e) -> sb.append(e.toString() + "\n"));
        return sb.toString();
    }

    public String listTable(String[] columns, String table) {
        String s;
        try {
            s = this.database.select(columns, table);
        } catch(Exception e) {
            s = e.getMessage();
        }
        return s;
    }
}