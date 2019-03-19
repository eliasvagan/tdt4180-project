import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TreningsApp {
    private Database database;
    private List<Ovelse> ovelser;
    private List<Okt> okter;

    public TreningsApp() {
        this.ovelser = new ArrayList<Ovelse>();
        this.database = new Database("TreningsDatabasen");
        this.syncObjects();
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
        this.syncObjects();
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

    public void registrerOvelseApparat(String navn, String apparat, int kg, int sett) {
        try {
            //TODO: query for insertion apparat
            this.database.query(
            "INSERT INTO ovelseMedApparat () VALUES (" +

                ");"
            );
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void registrerOvelseUtenApparat(String navn, String beskrivelse) {
        try {
            //TODO: query for insertion uten apparat
            this.database.query(
            "INSERT INTO ovelseUtenApparat () VALUES (" +

                ");"
            );
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void registrerOkt(Date dato, Time tidspunkt, Time varighet, int form, int prestasjon) {

        Okt okt = new Okt(dato, tidspunkt, varighet, form, prestasjon);
        try {
            this.database.query(
                    "INSERT INTO treningsokt (dato, tidspunkt, varighet, form, prestasjon) VALUES (" +
                    dato + ", " + tidspunkt + ", " + varighet + ", " + form + ", "+ prestasjon +
                    ");"
            );

        } catch(Exception e) {
            e.printStackTrace();
        }
        // TODO: Send inn økt og tilhørende øvelser i databasen
    }


    private void syncObjects() {
        String[] c = {"oktid", "dato", "tidspunkt", "varighet", "form", "prestasjon"};
        String t = "ovelse";
        // TODO: Oppdater appens objekter med spørringer fra databasen.

    }
}