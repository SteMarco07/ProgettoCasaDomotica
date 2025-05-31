import graphics.Color;
import graphics.Line;
import graphics.Picture;
import graphics.Text;

import java.io.Serializable;

/**
 * Classe Mappa che disegna la piantina della casa
 * @author Stellino Marco
 * @author Robolini Paolo
 * @version 1.0
 */
public class Mappa implements Serializable {
    Picture piantina;

    /**
     * Costruttore
     * @param percorsoFile Percorso della piantina. Essa deve essere un file png o jpg
     */
    public Mappa (String percorsoFile) {
        piantina = new Picture();
        piantina.load(percorsoFile);
    }

    /**
     * Funzione che disegna la griglia/assi cartesiani
     * @param posPartenza Posizione (intera) da cui inizia la griglia (offset rispetto all'origine degli assi)
     * @param spostamento Distanza da una linea a quella successiva
     * @param altezza Lunghezza delle "barrette" più spesse che indicano la distanza
     */
    private void disegnaGriglia(int posPartenza, int spostamento, int altezza){
        Line asseX = new Line(0, posPartenza, piantina.getWidth(), posPartenza);
        asseX.draw();
        Text testo = new Text(15,15,"10");
        testo.draw();
        Line asseY = new Line(posPartenza,0,posPartenza, piantina.getHeight());
        asseY.draw();
        for (int i = posPartenza+spostamento; i < piantina.getWidth(); i+=spostamento) {
            Line barretta = new Line(i, posPartenza, i, posPartenza+altezza);
            Text posX = new Text(i,posPartenza+altezza,Integer.toString(i));
            Line griglia = new Line(i, posPartenza, i, piantina.getHeight());
            griglia.setColor(new Color(200,200,200));
            griglia.draw();
            barretta.draw();
            posX.draw();
        }
        for (int i = posPartenza+spostamento; i < piantina.getWidth(); i+=spostamento) {
            Line barretta = new Line(posPartenza,i,posPartenza+altezza,i);
            Text posX = new Text(posPartenza+altezza,i,Integer.toString(i));
            Line griglia = new Line(posPartenza, i, piantina.getWidth(),i);
            griglia.setColor(new Color(200,200,200));
            griglia.draw();
            barretta.draw();
            posX.draw();
        }
    }

    /**
     * Funzione che disegna la mappa caricata dal programma con sopra la griglia
     */
    public void disegna() {
        piantina.draw();
        disegnaGriglia(10,100,5);
    }
}