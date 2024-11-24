import graphics.Color;
import graphics.Ellipse;
import graphics.Rectangle;
import graphics.Text;

/**
 * Classe che rappresenta una lampadina intelligente
 * che ha una potenza, nome, colore ed è accesa o spenta
 */
public class Lampadina {
    private String nome;
    private int lum;
    private final float potenza;
    private boolean accesa;
    private Color colore;
    private Rectangle base;
    private Ellipse bulbo, bordo;
    private Text testo;

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
     * Ritorna il colore della lampadina
     * @return colore (Color)
     */
    public Color getColore () {
        return this.colore;
    }

    /**
     * Ritorna la luminosità
     * @return luminosità (int)
     */
    public int getLum(){
        return this.lum;
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

    public void disegna(int X, int Y){
        final int dim = 10;
        int raggio = this.getLum()/2;
        X -= dim/2;
        Y -= dim/2;
        this.base = new Rectangle(X,Y,dim,dim);
        this.bulbo = new Ellipse(X + (dim-raggio)/2, Y - raggio, raggio,raggio);
        this.bordo = new Ellipse(X - 2 + (dim-raggio)/2, Y - 2 - raggio, raggio+4,raggio+4);
        this.testo = new Text(X + (dim-raggio)/2, Y - raggio - 20, this.nome);
        if(accesa){
            bulbo.setColor(getColore());

        }
        base.draw();
        bordo.draw();
        bulbo.draw();
        bordo.fill();
        base.fill();
        bulbo.fill();
        testo.draw();
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


