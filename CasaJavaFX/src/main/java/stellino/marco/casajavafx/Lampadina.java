package stellino.marco.casajavafx;

import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import java.io.Serializable;

/**
 * Classe che rappresenta una lampadina intelligente che ha una potenza, nome, colore ed è accesa o spenta
 */
public class Lampadina implements Serializable {
    private String nome;
    private int lum;
    private final float potenza;
    private boolean accesa;
    private String coloreRGB; // Salva il colore come stringa RGB invece di un oggetto Color

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
        this.coloreRGB = "#FFFFFF"; // Colore bianco di default
    }

    /**
     * Modifica il costruttore per accettare un colore come stringa RGB.
     * @param nome Nome della lampadina
     * @param potenza Potenza della lampadina
     * @param coloreRGB Stringa RGB del colore
     */
    public Lampadina(String nome, float potenza, String coloreRGB) {
        this.nome = nome;
        this.potenza = potenza;
        this.accesa = false;
        this.lum = 100;
        this.coloreRGB = coloreRGB;
    }

    /**
     * Modifica il nome della lampadina
     * @param nome Nome (String)
     */
    public void setNome(String nome) {
        if(nome != null) {
            this.nome = nome;
        }
    }

    /**
     * Ritorna la stringa RGB del colore della lampadina.
     * @return Stringa RGB del colore
     */
    public String getColoreRGB() {
        return this.coloreRGB;
    }

    /**
     * Ritorna la luminosità
     * @return luminosità
     */
    public int getLum() {
        return this.lum;
    }

    /**
     * Ritorna come String il nome della lampadina
     * @return nome
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Modifica il colore della lampadina.
     * @param coloreRGB Stringa RGB del colore
     */
    public void setColoreRGB(String coloreRGB) {
        this.coloreRGB = coloreRGB;
    }

    /**
     * Imposta la luminosità. Siccome è richiesto un valore a modulo di cinque e in un intervallo tra 0 e 100 compresi:
     * -> Se il valore inserito è inferiore al minimo, viene settato al minimo
     * -> Se il valore è più grande del massimo, viene settato al massimo
     * -> Se non è un multiplo di 5, il valore viene arrotondato per eccesso o per difetto
     * @param lum Valore della luminosità (intero)
     */
    public void setLum(int lum) {
        //controlla minimo e massimo
        if(lum < lumMin) {
            this.lum = lumMin;
        } else if (lum > lumMax) {
            this.lum = lumMax;
        } else {
            //arrotonda
            int resto = lum % 10;
            if(resto >= 5) {
                lum += 10;
            }
            this.lum = lum - resto;
        }
    }

    /**
     * Aumenta la luminosità di 10 unità. Se il valore aumentato va a superare quello massimo, viene settato al massimo.
     */
    public void aumentaLum() {
        if (this.lum <= lumMax - 10) {
           lum += 10;
        } else {
            this.lum = lumMax;
        }
    }

    /**
     * Diminuisce la luminosità di 10 unità. Se il valore aumentato è inferiore del minimo, viene settata al minimo.
     */
    public void diminuisciLum() {
        if (this.lum >= lumMin + 10) {
            lum -= 10;
        } else {
            this.lum = lumMin;
        }
    }

    /**
     * Accende la lampadina
     */
    public void accendi() {
        this.accesa = true;
    }

    /**
     * Spegne la lampadina
     */
    public void spegni() {
        this.accesa = false;
    }

    /**
     * Ritorna per un booleano se è la lampadina è accesa o spenta
     * @return stato di accensione
     */
    public boolean isAcceso() {
        return this.accesa;
    }

    /**
     * Ritorna la potenza istantanea. Se una lampadina è spenta, allora la potenza istantanea è zero.
     * @return Potenza Istantanea
     */
    public float getPotenzaIstantanea() {
        if (this.accesa && this.lum != 0) {
            return (float)this.lum/100.0f*this.potenza;
        } else {
            return 0.0f;
        }
    }

    /**
     * Converte le caratteristiche della lampadina in una stringa per il file CSV
     * @return Nome + Potenza + Luminosità + Colore + Stato di Accensione
     */
    public String toStringCSVFile() {
        String s = this.nome + ";" + this.potenza + ";" + this.lum + ";" + 
                  this.coloreRGB + ";";
        s += this.accesa ? "accesa" : "spenta";
        return s + "\n";
    }

    /**
     * Converte le caratteristiche della lampadina in una stringa
     * @return Nome + Potenza + Luminosità + Colore + Stato di Accensione
     */
    @Override
    public String toString() {
        return  "\n\tLAMPADINA " + this.nome +
                "\n\t\tPotenza: " + this.potenza +
                "\n\t\tLuminosità: " + this.lum +
                "\n\t\tColore: " + this.coloreRGB +
                "\n\t\tAccesa: " + this.accesa;
    }

    /**
     * Disegna la lampadina nel canvas
     * @param gc GraphicsContext su cui disegnare
     * @param x coordinata X della presa
     * @param y coordinata Y della presa
     */
    public void disegna(GraphicsContext gc, int x, int y) {
        final int bulbSize = 20;  // Dimensione del bulbo
        final int baseWidth = 10;  // Larghezza della base
        final int baseHeight = 5;  // Altezza della base

        // Disegna base (rettangolo)
        gc.setFill(Color.GRAY);
        gc.fillRect(x - baseWidth/2, y - baseHeight, baseWidth, baseHeight);

        // Disegna il bordo del bulbo (ellisse)
        gc.setStroke(Color.BLACK);
        gc.strokeOval(x - bulbSize/2, y - bulbSize - baseHeight, bulbSize, bulbSize);

        // Disegna il bulbo (ellisse con colore)
        if (accesa) {
            // Modifica l'opacità in base alla luminosità
            double brightness = lum / 100.0;
            Color fxColor = Color.web(coloreRGB, brightness);
            gc.setFill(fxColor);
        } else {
            gc.setFill(Color.WHITE);
        }
        gc.fillOval(x - bulbSize/2, y - bulbSize - baseHeight, bulbSize, bulbSize);

        // Disegna il nome della lampadina
        gc.setFill(Color.BLACK);
        gc.fillText(nome, x - bulbSize/2, y - bulbSize - baseHeight - 5);
    }
}


