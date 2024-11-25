package Eccezioni;

/**
 * Eccezione che viene lanciata quando in un metodo di ricerca non si trova la lampadina
 * @author Stellino Marco
 * @author Robolini Paolo
 * @version 1.0
 */
public class LampadinaNonTrovata extends RuntimeException //eccezione di tipo unchecked, per essere ti tipo checked bisogna inserire Exception
{
    /**
     * Costruttore
     */
    public LampadinaNonTrovata(){
        super("La lampadina non è stata trovata");
    }
}
