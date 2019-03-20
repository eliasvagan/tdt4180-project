public class Ovelse {

    private boolean harApparat;

    private String name;
    private int apparatID;
    private double kg;
    private int sett;

    private String beskrivelse;

    public Ovelse(String name, int apparatID, double kg, int sett){
        if (kg <= 0 || sett <= 0) {
            throw new IllegalArgumentException("Negative tall.");
        }
        this.harApparat = true; // Apparat true eller false
        this.name = name;
        this.apparatID = apparatID;
        this.kg = kg;
        this.sett = sett;
    }
    public Ovelse(String name, String beskrivelse) {
        this.harApparat = false; //Uten apparat
        this.name = name;
        this.beskrivelse = beskrivelse;
    }

    public String toString() {
        if (this.harApparat) {
            return this.name + ", ApparatID: " + this.apparatID + ", " + this.kg + "kg * " + this.sett;
        } else {
            return this.name + ", " + this.beskrivelse;
        }
    }

    public boolean getHarApparat() {
        return harApparat;
    }

    public String getName() {
        return name;
    }

    public int getApparatID() {
        return apparatID;
    }

    public double getKg() {
        return kg;
    }

    public int getSett() {
        return sett;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }
}
