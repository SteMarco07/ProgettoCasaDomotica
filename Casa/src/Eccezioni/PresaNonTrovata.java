package Eccezioni;

public class PresaNonTrovata extends RuntimeException //eccezione di tipo unchecked, per essere ti tipo checked bisogna inserire Exception
{
    public PresaNonTrovata(){
        super("Non ho trovato la presa");                                  //costruttore per le eccezioni
    }
}
