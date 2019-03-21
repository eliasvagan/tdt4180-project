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
    private int antallOkter;
    private int antallOvelser;

    public TreningsApp() {
        this.ovelser = new ArrayList<Ovelse>();
        this.database = new Database("TreningsDatabasen");

        this.antallOkter = database.getRowCount("treningsokt");
        this.antallOvelser = database.getRowCount("ovelse");
        System.out.println("Antall økter: " + antallOkter + ", antall øvelser: " + antallOvelser);
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

    public void registrerApparat(String navn, String beskrivelse) {
        if (navn.equals("") || beskrivelse.equals("")) {
            throw new IllegalArgumentException("Ugyldig navn eller beskrivelse.");
        } else {
            this.database.update(
            "INSERT INTO `apparat` (`apparatnavn`, `apparatbrukbeskrivelse`) VALUES ('" +
                navn + "', '" + beskrivelse + "')"
            );
        }
    }

    public String getOktMedOvelser(int id) {
        return this.database.getOkt(id) + "\n" +
               this.database.getOvelserOkt(id);
    }

    public String getApparatValg() {
        String[] c = {"apparatid", "apparatNavn"};
        String t = "apparat";
        return this.database.select(c, t);
    }

    public String getTreningsOktValg() {
        String[] c = {"oktid", "dato", "tidspunkt"};
        String t = "treningsokt";
        return this.database.select(c, t);
    }

    public String getOvelserMellomValg() {
        String[] c = {"DISTINCT (navn)"}; //Dette funker:)
        String t = "ovelse";
        return this.database.select(c, t);
    }

    public String getOkterN(int n) {
        return this.database.getOkterN(n);
    }

    public String getOkterBetween(String date0, String date1, String name) {
        return this.database.getOkterBetween(date0, date1, name);
    }

    public String getOvelserApparat(int apparatID) {
        return this.database.getOvelserMedApparat(apparatID);
    }

    public void registrerOkt(Okt okt) { // TODO: Send inn økt og tilhørende øvelser i databasen
        if (this.ovelser.size() == 0) {
            throw new IllegalArgumentException("Du har ikke lagt til noen øvelser!");
        }
        try {
            this.antallOkter ++;
            this.database.update("INSERT INTO `treningsokt` (`oktid`, `dato`, `tidspunkt`, `varighet`, `form`, `prestasjon`, `oktnotat`) VALUES " +
                    "(" + this.antallOkter + ", '" + okt.getDato() + "', '" + okt.getTidspunkt() +
                    "', '" + okt.getVarighet() + "', " + okt.getForm() + ", " + okt.getPrestasjon() +
                    ", '" + okt.getNotat() + "')"
            );

            for (Ovelse o : this.ovelser) { // For hver øvelse i økten, insert i ovelse
                this.antallOvelser ++;
                if (o.getHarApparat()) {
                    this.database.update(
                    "INSERT INTO `ovelse` (`ovelseID`, `aparat`, `navn`, `apparatID`, `antallkg`, `antallSett`) VALUES (" +
                        this.antallOvelser + ", 1, '" + o.getName() + "', " + o.getApparatID() + ", " + o.getKg() + ", " + o.getSett() + ");"
                    );
                } else {
                    this.database.update(
                      "INSERT INTO `ovelse` (`ovelseID`, `aparat`, `navn`, `tekstBeskrivelse`) VALUES(" +
                          this.antallOvelser + ", 0, '" + o.getName() + "', '" + o.getBeskrivelse() + "')"
                    );
                }
                this.database.update( // Of
                "INSERT INTO `treningsoktOvelse` (`oktid`, `ovelseid`) VALUES (" +
                        this.antallOkter + ", " + this.antallOvelser +
                    ");"
                );
            }
            //Flush dat shit
            this.ovelser = new ArrayList<Ovelse>();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }

    }
}