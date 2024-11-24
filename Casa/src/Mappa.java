import graphics.Color;
import graphics.Line;
import graphics.Picture;
import graphics.Text;

public class Mappa {
    Picture piantina;

    public Mappa (String percorsoFile) {
        piantina = new Picture();
        piantina.load(percorsoFile);
    }
    public void disegna() {
        piantina.draw();
        int posPartenza = 10, spostamento = 100, altezza = 5;
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
}