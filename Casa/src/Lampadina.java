import graphics.Color;

/**
 * Classe che rappresenta una lampadina intelligente
 * che ha una potenza, nome, colore ed è accesa o spenta
 */
public class Lampadina {
    private String nome;
    private int lum;
    private final float potenza;
    private boolean acceso;
    Color colore;

    /**
     * Costruttore che inizializza una lampadina (colore giallo, luminosità 0, spenta)
     * @param nome nome lampadina
     * @param potenza potenza lampadina
     */
    public Lampadina(String nome, int potenza) {
        this.nome = nome;
        this.potenza = potenza;
        this.acceso = false;
        this.lum = 0;
        colore = new Color(255,255,255);
    }

    /**
     * Ritorna una stringa con tutte le caratteristiche della lampadina.
      * @return Nome + Colore + Luminosità + Stato accensione (String)
     */
    @Override
    public String toString() {
        String s = this.nome + ";" + this.potenza + ";" + this.lum + ";" + this.colore + ";";

        if (this.acceso) {
            s += "accesa";
        } else {
            s += "spenta";
        }
        return s + "\n";
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

    /**
     * Accende la lampadina
     */
    public void accendi(){
        this.acceso = true;
    }

    /**
     * Spegne la lampadina
     */
    public void spegni(){
        this.acceso = false;
    }

    /**
     * Ritorna per un boolean se è la lampadina è accesa o spenta
     * @return stato di accensione (Boolean)
     */
    public boolean isAcceso(){
        return this.acceso;
    }

    /**
     * Metodo che ritorna la potenza istantanea
     * @return Potenza Istantanea
     */
    public float getPotenzaIstantanea(){
        return (this.potenza/(float)this.lum)*100.0f;
    }
}


