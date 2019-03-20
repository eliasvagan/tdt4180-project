import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TreningsApp {
    private Database database;
    private List<Ovelse> ovelser;
    private List<Okt> okter;

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

    public void registrerApparat(String navn, String beskrivelse) throws Exception {
        if (navn.equals("") || beskrivelse.equals("")) {
            throw new IllegalArgumentException("Ugyldig navn eller beskrivelse.");
        } else {
            this.database.query(
              "INSERT INTO `apparat` (`apparatnavn`, `apparatbrukbeskrivelse`) VALUES ('Tredemølle', 'Her kan man løpe.')"
            );

//            this.database.query(
//            "INSERT INTO `apparat` (`apparatnavn`, `apparatbrukbeskrivelse`) VALUES (" +
//                 "'" + navn + "', '" + beskrivelse + "'" +
//                ")"
//            );
        }
    }

    public void registrerOvelseApparat(String navn, String apparat, int kg, int sett) throws Exception {
        //TODO: query for insertion ovesele m/apparat
        this.database.query(
        "INSERT INTO ovelseMedApparat (apparatnavn, apparatbruksbeskrivelse) VALUES (" +

            ");"
        );
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

    public void registrerOkt(Okt okt) { // TODO: Send inn økt og tilhørende øvelser i databasen
        try {
            for (Ovelse o : okt.getOvelser()) { // For hver øvelse i økten, insert i ovelse
                this.database.query(
                "INSERT INTO ovelse (navn, antallkg, aparat, antallSett, tekstBeskrivelse) VALUES (" +
                    o.getName() + ", " + o.getKg() + ", " + o.getApparat() + ", " + o.getSett() + " mangler beskrivelse atm " +
                    ");"
                );
            }
            this.database.query( // TODO: Man må parse okt.getTidspunkt() og okt.getVarighet() til sql-tid fra String som de er nå!
            "INSERT INTO treningsokt (dato, tidspunkt, varighet, form, prestasjon) VALUES (" +
                okt.getDato() + ", " + okt.getTidspunkt() + ", " + okt.getVarighet() + ", " + okt.getForm() + ", "+ okt.getPrestasjon() +
                ");"
            );
            this.database.query( // Of
            "INSERT INTO treningsoktOvelse (oktid, ovelseid) VALUES (" +
                //TODO: Hvordan får vi idene til øvelserne egt?
                ");"
            );

        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}