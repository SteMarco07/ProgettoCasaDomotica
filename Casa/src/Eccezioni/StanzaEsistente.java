package Eccezioni;

public class StanzaEsistente extends RuntimeException {
    public StanzaEsistente() {
        super("La stanza esiste già");
    }
}
