package Eccezioni;

public class PresaOccupata extends RuntimeException //eccezione di tipo unchecked, per essere ti tipo checked bisogna inserire Exception
{
    public PresaOccupata(){
        super("La presa selezionata ha già attaccata una lampadina");                                  //costruttore per le eccezioni
    }
}
