package Eccezioni;

public class LampadinaEsistente extends RuntimeException //eccezione di tipo unchecked, per essere ti tipo checked bisogna inserire Exception
{
    public LampadinaEsistente(){
        super("La lampadina esiste già");                                  //costruttore per le eccezioni
    }
}
