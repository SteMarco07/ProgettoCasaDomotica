package Eccezioni;

/**
 * Eccezione che viene lanciata quando il tipo di file del CSV non inizia con "SistemaDomotico"
 * @author Stellino Marco
 * @author Robolini Paolo
 * @version 1.0
 */
public class FileNonValido extends RuntimeException{

    /**
     * Costruttore
     */
    public FileNonValido(){
        super("Il file non e' valido");
    }
}
