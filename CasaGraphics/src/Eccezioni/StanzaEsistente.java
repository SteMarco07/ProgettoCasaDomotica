package Eccezioni;

/**
 * Eccezione che viene lanciata quando, nel tentativo di aggiungere una stanza, ne esista già una col medesimo nome
 * @author Stellino Marco
 * @author Robolini Paolo
 * @version 1.0
 */
public class StanzaEsistente extends RuntimeException {
    /**
     * Costruttore
     */
    public StanzaEsistente() {
        super("La stanza esiste già");
    }
}
