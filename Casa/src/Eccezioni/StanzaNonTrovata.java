package Eccezioni;

public class StanzaNonTrovata extends RuntimeException //eccezione di tipo unchecked, per essere ti tipo checked bisogna inserire Exception
{
    public StanzaNonTrovata(){
        super("Non ho trovato la stanza");                                  //costruttore per le eccezioni
    }
}
