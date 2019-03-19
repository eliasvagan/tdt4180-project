import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Okt {
    private List<Ovelse> ovelser;
    private Date dato;
    private Time tidspunk;
    private Time varighet;
    private int form;
    private int prestasjon;

    public Okt(Date dato, Time tidspunk, Time varighet, int form, int prestasjon) {
        this.dato = dato;
        this.tidspunk = tidspunk;
        this.varighet = varighet;
        this.form = form;
        this.prestasjon = prestasjon;
        this.ovelser = new ArrayList<Ovelse>();
    }
    public void addOvelse(Ovelse o) {
        this.ovelser.add(o);
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(
                "Økt: " + this.dato +
                ", kl." + this.tidspunk +
                ", varighet: " + this.varighet +
                ", form: " + this.form +
                ", prestasjon: " + this.prestasjon + "\n"
        );
        for (Ovelse o : this.ovelser) {
            sb.append(" - ");
            if (o.getHarApparat() == true) {
                sb.append("Apparatøvelse: " + o.toString());
            } else {
                sb.append("Øvelse uten apparat: " + o.toString());
            }
        }
        return sb.toString();
    }

    public List<Ovelse> getOvelser() {
        return this.ovelser;
    }
}
