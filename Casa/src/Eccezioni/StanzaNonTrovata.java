package Eccezioni;

/**
 * Eccezione che viene lanciata quando, nel tentativo di ricerca di una stanza, essa non esista
 *@author Stellino Marco
 *@author Robolini Paolo
 *@version 1.0
 */
public class StanzaNonTrovata extends RuntimeException //eccezione di tipo unchecked, per essere ti tipo checked bisogna inserire Exception
{
    /**
     * Costruttore
     */
    public StanzaNonTrovata(){
        super("Non ho trovato la stanza");                                  //costruttore per le eccezioni
    }
}
