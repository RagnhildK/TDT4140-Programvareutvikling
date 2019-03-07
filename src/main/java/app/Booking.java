package app;

public class Booking {
    /*
     * Ikke i bruk per nå
     */
    private String dato, tidspunkt, emne, studass, varighet;

    public Booking(String dato, String tidspunkt, String emne, String studass, String varighet){
        this.dato = dato;
        this.tidspunkt = tidspunkt;
        this.emne = emne;
        this.studass = studass;
        this.varighet = varighet;
    }

    public String getDato(){
        return dato;
    }

    public String getTidspunkt(){
        return tidspunkt;
    }

    public String getEmne(){
        return emne;
    }

    public String getStudass(){
        return studass;
    }

    public String getVarighet(){
        return varighet;
    }



}
