package Eccezioni;

/**
 * Eccezione che viene lanciata quando, in un metodo di ricerca, non la si trova
 * @author Stellino Marco
 * @author Robolini Paolo
 * @version 1.0
 */
public class PresaNonTrovata extends RuntimeException //eccezione di tipo unchecked, per essere ti tipo checked bisogna inserire Exception
{
    /**
     * Costruttore
     */
    public PresaNonTrovata(){
        super("Non ho trovato la presa");                                  //costruttore per le eccezioni
    }
}
