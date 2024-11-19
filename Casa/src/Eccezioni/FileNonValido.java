package Eccezioni;

public class FileNonValido extends RuntimeException{
    public FileNonValido(){
        super("Il file non e' valido");
    }
}
