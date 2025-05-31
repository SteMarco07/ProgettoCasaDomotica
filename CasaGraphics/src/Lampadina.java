import graphics.Color;
import graphics.Ellipse;
import graphics.Rectangle;
import graphics.Text;

import java.io.Serializable;

/**
 * Classe che rappresenta una lampadina intelligente che ha una potenza, nome, colore ed è accesa o spenta
 * @author Stellino Marco
 * @author Robolini Paolo
 * @version 1.0
 */
public class Lampadina implements Serializable {
    private String nome;
    private int lum;
    private final float potenza;
    private boolean accesa;
    private Color colore;
    private Rectangle base;
    private Ellipse bulbo, bordo;
    private Text testo;

    private final static int lumMin = 0;
    private final static int lumMax = 100;

    /**
     * Costruttore che inizializza una lampadina (colore bianco, luminosità 100, spenta)
     * @param nome Nome della lampadina
     * @param potenza Potenza della lampadina
     */
    public Lampadina(String nome, float potenza) {
        this.nome = nome;
        this.potenza = potenza;
        this.accesa = false;
        this.lum = 100;
        colore = new Color(255,255,255);
        this.base = new Rectangle(-10,-10,0,0);
        this.bulbo = new Ellipse(-10, -10, 0,0);
        this.bordo = new Ellipse(-10, -10, 0,0);
        this.testo = new Text(-10, -10, this.nome);
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
     * Ritorna il colore (del packaging graphics) della lampadina
     * @return colore (Color)
     */
    public Color getColore () {
        return this.colore;
    }

    /**
     * Ritorna la luminosità
     * @return luminosità
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
     * @param colore Colore (del packaging Graphics)
     */
    public void setColore(Color colore) {
        this.colore = colore;
    }

    /**
     * Imposta la luminosità. Siccome è richiesto un valore a modulo di cinque e in un intervallo tra 0 e 100 compresi:
     * -> Se il valore inserito è inferiore al minimo, viene settato al minimo
     * -> Se il valore è più grande del massimo, viene settato al massimo
     * -> Se non è un multiplo di 5, il valore viene arrotondato per eccesso o per difetto
     * @param lum Valore della luminosità (intero)
     */
    public void setLum(int lum){

        //controlla minimo e massimo
        if(this.lum < lumMin){
            this.lum = lumMin;
        } else if (this.lum > lumMax) {
            this.lum = lumMax;
        }

        //arrotonda
        int resto = this.lum % 10;
        if(resto >= 5) {
            lum+=10;
        }

        this.lum = lum - resto;
    }

    /**
     * Aumenta la luminosità di 10 unità. Se il valore aumentato va a superare quello massimo, viene settato al massimo.
     */
    public void aumentaLum () {
        if (this.lum <= lumMax - 10) {
           lum += 10;
        } else {
            this.lum = lumMax;
        }
    }

    /**
     * Diminuisce la luminosità di 10 unità. Se il valore aumentato è inferiore del minimo, viene settata al minimo.
     */
    public void diminuisciLum () {
        if (this.lum >= lumMin + 10) {
            lum -= 10;
        } else {
            this.lum = lumMin;
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
     * Ritorna per un booleano se è la lampadina è accesa o spenta
     * @return stato di accensione
     */
    public boolean isAcceso(){
        return this.accesa;
    }

    /**
     * Ritorna la potenza istantanea. Se una lampadina è spenta, allora la potenza istantanea è zero.
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
     * Disegna la lampadina
     * @param X Coordinata X della presa
     * @param Y Coordinata Y della presa
     */
    public void disegna(int X, int Y){

        this.base.translate(-2000,0);
        this.bulbo.translate(-2000,0);
        this.bordo.translate(-2000,0);
        this.testo.translate(-2000,0);
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
     * Ritorna una stringa con tutte le caratteristiche della lampadina, che poi verrà stampata nel file CSV.
     * @return Nome + Potenza + Luminosità + Colore + Stato di Accensione
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

    /**
     * Converte le caratteristiche della lampadina in una stringa
     * @return Nome + Potenza + Luminosità + Colore + Stato di Accensione
     */
    @Override
    public String toString () {
        return  "\n\tLAMPADINA " + this.nome +
                "\n\t\tPotenza: " + this.potenza +
                "\n\t\tLuminosità: " + this.lum +
                "\n\t\tColore: " + this.colore +
                "\n\t\tAccesa: " + this.accesa;

    }
}


