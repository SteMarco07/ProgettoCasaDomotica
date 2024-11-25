package Eccezioni;

/**
 * Eccezione che viene lanciata quando, nel tentativo di aggiungere una lampadina a una presa, quest'ultima sia già occupata
 * @author Stellino Marco
 * @author Robolini Paolo
 * @version 1.0
 */
public class PresaOccupata extends RuntimeException //eccezione di tipo unchecked, per essere ti tipo checked bisogna inserire Exception
{
    /**
     * Costruttore
     */
    public PresaOccupata(){
        super("La presa selezionata ha già attaccata una lampadina");
    }
}
