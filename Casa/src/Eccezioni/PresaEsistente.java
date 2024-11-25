package Eccezioni;

/**
 * Eccezione che viene lanciata quando, nel tentativo di aggiungere una presa, essa esiste già
 * @author Stellino Marco
 * @author Robolini Paolo
 * @version 1.0
 */
public class PresaEsistente extends RuntimeException{
    /**
     * Costruttore
     */
    public PresaEsistente(){
        super("La presa esiste già");
    }
}
