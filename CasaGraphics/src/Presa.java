import Eccezioni.PresaOccupata;
import graphics.*;

import java.io.Serializable;

/**
 * Classe che rappresenta una presa a cui è collegata una e una sola lampadina.
 * @author Stellino Marco
 * @author Robolini Paolo
 * @version 1.0
 */
public class Presa implements Serializable {
    private final String nome;
    private int X;
    private int Y;
    private boolean occupata;
    private Lampadina lampadina;
    private Text testo;
    private Rectangle bordoPresa;
    private Line barrettePresa1, barrettePresa2;
    private Ellipse internoPresa;
    private int dimPresa, spaziaturaPresa;

    /**
     * Costruttore che inizializza una presa
     * @param nome nome della presa
     * @param X coordinate X per la rappresentazione grafica
     * @param Y coordinate Y per la rappresentazione grafica
     */
    public Presa(String nome, int X, int Y){
        this.nome = nome;
        this.occupata = false;
        this.X = X;
        this.Y = Y;
        this.dimPresa = 16;
        this.spaziaturaPresa = 2;
        this.barrettePresa1 = new Line(0,0,0,0);
        this.barrettePresa2 = new Line(0,0,0,0);
        this.testo = new Text(0,0,"");
        this.bordoPresa = new Rectangle(0,0,0,0);
        this.internoPresa = new Ellipse(0,0,0,0);
    }

    /**
     * Ritorna il nome della presa
     * @return nome presa
     */
    public String getNome() {
        return nome;
    }

    /**
     * Ritorna la coordinata X della presa
     * @return X della presa
     */
    public int getX() {
        return X;
    }

    /**
     * Ritorna la coordinata Y della presa
     * @return Y della presa
     */
    public int getY() {
        return Y;
    }

    /**
     * Imposta la coordinata Y della presa
     * @param y Coordinata Y (verticale)
     */
    public void setY(int y) {
        this.Y = y;
    }

    /**
     * Imposta la coordinata X della presa
     * @param x Coordinata X (orizzontale)
     */
    public void setX(int x) {
        this.X = x;
    }

    /**
     * Restituisce la lampadina collegata alla presa
     * @return Lampadina (ritorna null se non ce n'è nessuna attaccata)
     */
    public Lampadina getLampadina() {
        return lampadina;
    }

    /**
     * Ritorna un booleano per indicare se è attaccata o meno una lampadina
     * @return Occupata
     */
    public boolean isOccupata(){
        return this.occupata;
    }

    /**
     * Modifica la lampadina collegata
     * @param lampadina Una Lampadina
     */
    public void setLampadina(Lampadina lampadina) throws PresaOccupata{
        if (this.occupata) {
            throw new PresaOccupata();
        } else {
            this.lampadina = lampadina;
            this.occupata = true;
        }
    }

    /**
     * Rimuove la lampadina dalla presa e setta l'attributo "occupata" a false
     */
    public void rimuoviLampadina(){

        this.occupata = false;
        this.lampadina = null;

    }

    /**
     * Ritorna una stringa con tutte le caratteristiche della presa, che poi verrà stampata nel file CSV.
     * @return Nome della presa + X + Y + Lampadina
     */
    public String toStringCSVFile(){
        String ritorno = nome + ';'+ X + ';' + Y;

        if(this.occupata){
            ritorno+=";" + getLampadina().toStringCSVFile();
        } else {
            ritorno+="\n";
        }

        return ritorno;
    }

    /**
     * Disegna la presa e la lampadina a essa collegata
     */
    public void disegna () {

        this.testo.translate(-2000,0);
        this.bordoPresa.translate(-2000,0);
        this.internoPresa.translate(-2000,0);
        this.barrettePresa1.translate(-2000,0);
        this.barrettePresa2.translate(-2000,0);

        this.bordoPresa = new Rectangle(this.X-this.dimPresa/2, this.Y - this.dimPresa/2, this.dimPresa, this.dimPresa);
        this.bordoPresa.draw();
        this.testo = new Text(this.X-this.dimPresa/2, this.Y +this.dimPresa/2 + 10, this.nome);
        this.testo.draw();
        this.internoPresa = new Ellipse(this.X-this.dimPresa/2+this.spaziaturaPresa, this.Y - this.dimPresa/2+this.spaziaturaPresa, this.dimPresa-this.spaziaturaPresa*2, this.dimPresa-this.spaziaturaPresa*2);
        this.internoPresa.draw();
        this.barrettePresa1 = new Line(this.X-2, this.Y-3,this.X-2,this.Y+3);
        this.barrettePresa2 = new Line(this.X+2, this.Y-3,this.X+2,this.Y+3);
        this.barrettePresa1.draw();
        this.barrettePresa2.draw();
        if(this.occupata){
            this.lampadina.disegna(X,Y);
        }
        Canvas.getInstance().repaint();
    }

    /**
     * Converte le caratteristiche della presa in una stringa pensata per stamparla in console
     * @return Presa + nome + X + Y + isOccupata ("libera" se è libera, altrimenti stampa la lampadina
     */
    @Override
    public String toString() {
        String s =  "\n____________________________________\s" +
                "\nPRESA " + this.nome +
                "\n\tPosizione x: " + this.X +
                "\n\tPosizione y: " + this.Y;
        if (occupata) {
            s += getLampadina().toString();
        } else {
            s += "\n\tLa presa è libera";
        }
        return s;
    }
}
