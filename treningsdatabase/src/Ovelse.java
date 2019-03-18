public class Ovelse {

    private final char type;

    private String name;
    private String aparat;
    private double kg;
    private int sett;

    private String beskrivelse;

    public Ovelse(String name, String aparat, double kg, int sett){
        if (kg <= 0 || sett <= 0) {
            throw new IllegalArgumentException("Negative tall.");
        }
        this.type = 'A'; // Aparat
        this.name = name;
        this.aparat = aparat;
        this.kg = kg;
        this.sett = sett;
    }

    public String toString() {
        if (this.type == 'A') {
            return this.name + ", " + this.aparat + ", " + this.kg + "kg * " + this.sett;
        } else {
            return this.name + ", " + this.beskrivelse;
        }
    }

    public Ovelse(String name, String beskrivelse) {
        this.type = 'U'; //Uten aparat
        this.name = name;
        this.beskrivelse = beskrivelse;
    }

    public char getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getAparat() {
        return aparat;
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
