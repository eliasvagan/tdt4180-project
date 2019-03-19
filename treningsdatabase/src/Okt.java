import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Okt {
    private List<Ovelse> ovelser;
    private LocalDate dato;
    private String tidspunkt;
    private String varighet;
    private int form;
    private int prestasjon;

    public Okt(LocalDate dato, String tidspunkt, String varighet, int form, int prestasjon) {
        this.dato = dato;
        this.tidspunkt = tidspunkt;
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
                ", kl." + this.tidspunkt +
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

    public LocalDate getDato() {
        return dato;
    }

    public String getTidspunkt() {
        return tidspunkt;
    }

    public String getVarighet() {
        return varighet;
    }

    public int getForm() {
        return form;
    }

    public int getPrestasjon() {
        return prestasjon;
    }

    public List<Ovelse> getOvelser() {
        return this.ovelser;
    }
}
