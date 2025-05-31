package stellino.marco.casajavafx;

import Eccezioni.*;
import java.io.Serializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Classe che rappresenta una presa a cui è collegata una e una sola lampadina.
 */
public class Presa implements Serializable {
    private final String nome;
    private final int x;
    private final int y;
    private boolean occupata;
    private Lampadina lampadina;    private final int dimPresa = 16;

    /**
     * Costruttore che inizializza una presa
     * @param nome nome della presa
     * @param x coordinate X per la rappresentazione grafica
     * @param y coordinate Y per la rappresentazione grafica
     */
    public Presa(String nome, int x, int y) {
        this.nome = nome;
        this.occupata = false;
        this.x = x;
        this.y = y;
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
     * @return coordinata X
     */
    public int getX() {
        return x;
    }

    /**
     * Ritorna la coordinata Y della presa
     * @return coordinata Y
     */
    public int getY() {
        return y;
    }

    /**
     * Ritorna se la presa è occupata o meno
     * @return stato di occupazione
     */
    public boolean isOccupata() {
        return occupata;
    }

    /**
     * Restituisce la lampadina collegata alla presa
     * @return Lampadina (ritorna null se non ce n'è nessuna attaccata)
     */
    public Lampadina getLampadina() {
        return lampadina;
    }

    /**
     * Modifica la lampadina collegata
     * @param lampadina lampadina da collegare
     * @throws PresaOccupata Se la presa è già occupata, lancia un'eccezione
     */
    public void setLampadina(Lampadina lampadina) throws PresaOccupata {
        if (this.occupata) {
            throw new PresaOccupata();
        } else {
            this.lampadina = lampadina;
            this.occupata = true;
        }
    }

    /**
     * Rimuove la lampadina collegata alla presa
     */
    public void rimuoviLampadina() {
        this.lampadina = null;
        this.occupata = false;
    }

    /**
     * Disegna la presa sul canvas di JavaFX
     * @param gc GraphicsContext su cui disegnare
     */
    public void disegna(GraphicsContext gc) {
        // Disegna il bordo della presa
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeRect(x - dimPresa/2, y - dimPresa/2, dimPresa, dimPresa);

        // Disegna l'interno della presa
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(x - dimPresa/2 + 2, y - dimPresa/2 + 2, dimPresa - 4, dimPresa - 4);

        // Disegna le barrette della presa
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.strokeLine(x - 2, y - 3, x - 2, y + 3);
        gc.strokeLine(x + 2, y - 3, x + 2, y + 3);

        // Disegna il nome della presa
        gc.setFill(Color.BLACK);
        gc.setLineWidth(1);
        gc.fillText(nome, x - dimPresa/2, y + dimPresa/2 + 15);
    }

    /**
     * Converte le caratteristiche della presa in una stringa per il file CSV
     * @return Nome + Coordinate X e Y + Lampadina (se presente)
     */
    public String toStringCSVFile() {
        String ritorno = nome + ';' + x + ';' + y;
        if (occupata) {
            ritorno += ';' + lampadina.toStringCSVFile();
        }
        return ritorno + '\n';
    }

    /**
     * Converte le caratteristiche della presa in una stringa
     * @return Nome + Coordinate X e Y + Lampadina (se presente)
     */
    @Override
    public String toString() {
        return "\nPRESA " + nome +
                "\n\tPosizione x: " + x +
                "\n\tPosizione y: " + y +
                "\n\tOccupata: " + occupata +
                (occupata ? "\n" + lampadina.toString() : "");
    }
}
