package Eccezioni;

public class PresaEsistente extends RuntimeException{
    public PresaEsistente(){
        super("La presa esiste già");
    }
}
