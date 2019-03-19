public class Ovelse {

    private boolean harApparat;

    private String name;
    private String apparat;
    private double kg;
    private int sett;

    private String beskrivelse;

    public Ovelse(String name, String apparat, double kg, int sett){
        if (kg <= 0 || sett <= 0) {
            throw new IllegalArgumentException("Negative tall.");
        }
        this.harApparat = true; // Apparat true eller false
        this.name = name;
        this.apparat = apparat;
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
            return this.name + ", " + this.apparat + ", " + this.kg + "kg * " + this.sett;
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

    public String getApparat() {
        return apparat;
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
