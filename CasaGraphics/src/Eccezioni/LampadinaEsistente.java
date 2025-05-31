package Eccezioni;

/**
 * Eccezione che viene lanciata quando una lampadina esiste già
 * @author Stellino Marco
 * @author Robolini Paolo
 * @version 1.0
 */
public class LampadinaEsistente extends RuntimeException //eccezione di tipo unchecked, per essere ti tipo checked bisogna inserire Exception
{
    /**
     * Costruttore
     */

    public LampadinaEsistente(){
        super("La lampadina esiste già");
    }
}
