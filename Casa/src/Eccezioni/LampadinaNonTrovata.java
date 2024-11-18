package Eccezioni;

public class LampadinaNonTrovata extends RuntimeException //eccezione di tipo unchecked, per essere ti tipo checked bisogna inserire Exception
{
    public LampadinaNonTrovata(){
        super("La lampadina non è stata trovata");                                  //costruttore per le eccezioni
    }
}
