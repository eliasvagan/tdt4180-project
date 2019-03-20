import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TreningsApp {
    private Database database;
    private List<Ovelse> ovelser;
    private List<Ovelse> ovelserDownloaded;
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

    public String listOvelserDownloaded() {
        StringBuilder sb = new StringBuilder();

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
            this.database.update(
            "INSERT INTO `apparat` (`apparatnavn`, `apparatbrukbeskrivelse`) VALUES ('" +
                navn + "', '" + beskrivelse + "')"
            );
        }
    }

    public String getApparatValg() {
        String[] c = {"apparatid", "apparatNavn"};
        String t = "apparat";
        return this.database.select(c, t);
    }

    public void registrerOkt(Okt okt) { // TODO: Send inn økt og tilhørende øvelser i databasen
        try {
            for (Ovelse o : this.ovelser) { // For hver øvelse i økten, insert i ovelse
                if (o.getHarApparat()) {
                    this.database.update(
                    "INSERT INTO `ovelse` (`navn`, `apparatID`, `antallkg`, `antallSett`) VALUES ('" +
                        o.getName() + "', " + o.getApparatID() + ", " + o.getKg() + ", " + o.getSett() + ");"
                    );
                } else {
                    this.database.update(
                      "INSERT INTO `ovelse` (`navn`, `tekstBeskrivelse`) VALUES('" +
                      o.getName() + "', '" + o.getBeskrivelse() + "')"
                    );
                }
            }
            this.database.update(
            "INSERT INTO `treningsokt` (`dato`, `tidspunkt`, `varighet`, `form`, `prestasjon`) VALUES (" +
                okt.getDato() + ", " + okt.getTidspunkt() + ", " + okt.getVarighet() + ", " + okt.getForm() + ", "+ okt.getPrestasjon() +
                ");"
            );
            this.database.update( // Of
            "INSERT INTO `treningsoktOvelse` (`oktid`, `ovelseid`) VALUES (" +
                //TODO: Hvordan får vi idene til øvelserne egt?
                ");"
            );
            //Flush dat shit
            this.ovelser = new ArrayList<Ovelse>();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }

    }
}