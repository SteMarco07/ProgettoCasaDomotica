import Eccezioni.PresaOccupata;

/**
 * Classe che rappresenta una presa a cui è collegata una e una sola lampadina.
 */
public class Presa {
    private final String nome;
    private final int X;
    private final int Y;
    private boolean occupata;
    Lampadina lampadina;

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
    }



    /**
     * Metodo che ritorna il nome della presa
     * @return nome presa
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo che ritorna la coordinata X della presa
     * @return X della presa
     */
    public int getX() {
        return X;
    }

    /**
     * Metodo che ritorna la coordinata Y della presa
     * @return Y della presa
     */
    public int getY() {
        return Y;
    }

    /**
     * Metodo che ritorna la lampadina collegata alla presa
     * @return Lampadina (ritorna null se non ce n'è nessuna attaccata)
     */
    public Lampadina getLampadina() {
        return lampadina;
    }

    public boolean isOccupata(){
        return this.occupata;
    }

    /**
     * Metodo che modifica la lampadina collegata
     * @param lampadina lampadina
     */
    public void setLampadina(Lampadina lampadina) throws PresaOccupata{
        if (this.occupata) {
            throw new PresaOccupata();
        } else {
            this.lampadina = lampadina;
            this.occupata = true;
        }
    }

    public void rimuoviLampadina(){
        this.occupata = false;
        this.lampadina = null;
    }


    public String toStringCSVFile(){
        String ritorno = nome + ';'+ X + ';' + Y;

        if(this.occupata){
            ritorno+=";" + getLampadina().toStringCSVFile();
        } else {
            ritorno+="\n";
        }

        return ritorno;
    }

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
