import graphics.Color;

/**
 * Classe che rappresenta una lampadina intelligente
 * che ha una potenza, nome, colore ed è accesa o spenta
 */
public class Lampadina {
    private String nome;
    private int lum;
    private final float potenza;
    private boolean accesa;
    Color colore;

    /**
     * Costruttore che inizializza una lampadina (colore giallo, luminosità 0, spenta)
     * @param nome nome lampadina
     * @param potenza potenza lampadina
     */
    public Lampadina(String nome, float potenza) {
        this.nome = nome;
        this.potenza = potenza;
        this.accesa = false;
        this.lum = 0;
        colore = new Color(255,255,255);
    }




    /**
     * Modifica il nome della lampadina
     * @param nome Nome (String)
     */
    public void setNome(String nome) {
        if(nome != null){
            this.nome = nome;
        }
    }


    /**
     * Ritorna come String il nome della lampadina
     * @return nome
     */
    public String getNome(){return this.nome;}

    /**
     * Modifica il colore della lampadina
     * @param colore Colore (String)
     */
    public void setColore(Color colore) {
        this.colore = colore;
    }

    /**
     * Imposta la luminosità. Siccome è richiesto un valore a modulo di cinque e in un intervallo tra 0 e 100:
     * -> Se il valore è < 0, diventa 0
     * -> Se il valore è > 100, diventa 100
     * -> Se non è un multiplo di 5, arrotonda per eccesso o per difetto
     * @param lum Valore della luminosità (intero)
     */
    public void setLum(int lum){
        if(lum < 0){
            lum = 0;
        } else if (lum > 100) {
            lum = 100;
        }
        int resto = lum%10;
        if(resto >= 5) {
            lum+=10;
        }
        lum-=resto;
        this.lum = lum;
    }

    public void aumentaLum () {
        if (this.lum <= 90) {
           lum += 10;
        }
    }

    public void diminuisciLum () {
        if (this.lum >= 10) {
            lum -= 10;
        }
    }

    /**
     * Accende la lampadina
     */
    public void accendi(){
        this.accesa = true;
    }

    /**
     * Spegne la lampadina
     */
    public void spegni(){
        this.accesa = false;
    }

    /**
     * Ritorna per un boolean se è la lampadina è accesa o spenta
     * @return stato di accensione (Boolean)
     */
    public boolean isAcceso(){
        return this.accesa;
    }

    /**
     * Metodo che ritorna la potenza istantanea
     * @return Potenza Istantanea
     */
    public float getPotenzaIstantanea(){
        if (this.accesa && this.lum != 0) {
            return (float)this.lum/100.0f*this.potenza;

        } else {
            return 0.0f;
        }

    }

    /**
     * Ritorna una stringa con tutte le caratteristiche della lampadina.
     * @return Nome + Colore + Luminosità + Stato accensione (String)
     */
    public String toStringCSVFile() {
        String s = this.nome + ";" + this.potenza + ";" + this.lum + ";" + this.colore + ";";

        if (this.accesa) {
            s += "accesa";
        } else {
            s += "spenta";
        }
        return s + "\n";
    }
    @Override
    public String toString () {
        return  "\n\tLAMPADINA " + this.nome +
                "\n\t\tPotenza: " + this.potenza +
                "\n\t\tLuminosità: " + this.lum +
                "\n\t\tColore: " + this.colore +
                "\n\t\tAccesa: " + this.accesa;

    }
}


