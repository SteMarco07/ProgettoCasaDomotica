import java.util.ArrayList;

/**
 * Classe che rappresenta una stanza, con un numero indefinito di lampadine.
 */
public class Stanza {
    private final String nome;
    private ArrayList<Presa> prese;

    /**
     * Costruttore che inizializza la stanza con un nome.
     * @param nome nome_stanza
     */
    public Stanza (String nome){
        this.nome = nome;
        this.prese = new ArrayList<Presa>();
    }

    //TODO: Metodi di ricerca
    /**
     * Cerca una presa
     * @param nomePresa Nome della presa (String)
     * @return Ritorna la presa se è stata trovata, altrimenti ritorna null
     */
    public Presa getPresa (String nomePresa){
        for(var i : prese){
            if(i.getNome().equals(nomePresa)){
                return i;
            }
        }
        return null;
    }

    /**
     * Dato il nome di una lampadina
     * @param nomeLampadina Nome della presa (String)
     * @return Ritorna la presa se è stata trovata, altrimenti ritorna null
     */
    public Lampadina getLampadina (String nomeLampadina){
        for(var i : prese){
            if(i.getLampadina().getNome().equals(nomeLampadina)){
                return i.getLampadina();
            }
        }
        return null;
    }

    /**
     * Cerca una presa in base al nome della lampadina
     * @param nomeLampadina Nome della lampadina (String)
     * @return La presa se è stata trovata, altrimenti null
     */
    public Presa getPresaNomeLampadina(String nomeLampadina){
        Lampadina lampadina = getLampadina(nomeLampadina);
        for(var i : prese){
            if(i.getLampadina() == lampadina){
                return i;
            }
        }
        return null;
    }


    //TODO: Metodi di aggiunta
    /**
     * Aggiunge una nuova presa alla stanza.
     * @param presa Presa
     * @return Ritorna true se è stata aggiunta, altrimenti false se è già presente
     */
    public boolean aggiungiPresa(Presa presa){
        Presa p = getPresa(presa.getNome());
        if (p == null) {
            prese.add(p);
            return true;
        }
        return false;
    }

    /**
     * Aggiunge una nuova lampadina
     * @param nomePresa Nome della presa a cui si vuole aggiungere una presa
     * @param lampadina Lampadina
     * @return
     */
    public boolean aggiungiLampadina (String nomePresa, Lampadina lampadina) {
        Presa p = getPresa(nomePresa);
        if (p != null && p.getLampadina() == null) {
            Lampadina l  = getLampadina(lampadina.getNome());
            if (l == null) {
                p.setLampadina(lampadina);
                return true;
            }
        }
        return false;
    }


    //TODO: Metodi di rimozione

    /**
     * Rimuove una presa
     * @param nomePresa Nome della presa (String)
     * @return true se la presa è stata rimossa, false se non è stata rimossa
     */
    public boolean rimuoviPresa(String nomePresa) {
        Presa p = getPresa(nomePresa);
        if (p != null) {
            prese.remove(p);
            return true;
        }
        return false;
    }

    /**
     * Rimuove una lampadina dato il nome.
     * @param nomeLampadina Nome (String)
     * @return true se la lampadina è stata rimossa, false se non è stata rimossa
     */
    public boolean rimuoviLampadina(String nomeLampadina){
        Presa p = getPresaNomeLampadina(nomeLampadina);
        if(p != null){
            p.setLampadina(null);
            return true;
        } else {
            return false;
        }
    }

    //TODO: metodi vari


    /**
     * Accende tutte le lampadine della stanza
     */
    public void accendiTutte(){
        for (var i : prese) {
            if (i != null) {
                i.getLampadina().accendi();
            }
        }
    }
    /**
     * Spegne tutte le lampadine della stanza
     */
    public void spegniTutte(){
        for (var i : prese) {
            i.getLampadina().spegni();
        }
    }

    /**
     * Ritorna la potenza istantanea totale del sistema (ovvero la somma delle singole). Non conta se le lampadine sono spente.
     * @return Somma delle potenze istantanee (Float)
     */
    public float getPotenzaSistema(){
        float ritorno = 0;
        for(var i : prese){
            if(i.getLampadina().isAcceso()){
                ritorno+=i.getLampadina().getPotenzaIstantanea();
            }
        }
        return ritorno;
    }

    /**
     * Modifica il colore di una lampadina dato il suo nome.
     * @param nomeLampadina Nome (String)
     * @param colore Colore (String)
     */
    public void setColore(String nomeLampadina, String colore){
        Lampadina l = getLampadina(nomeLampadina);
        if (l != null) {
            l.setColore(colore);
        }
    }

    /**
     * Modifica la luminosità di una lampadina dato il suo nome.
     * @param nomeLampadina Nome (String)
     * @param lum Luminosità (int)
     */
    public void setLum(String nomeLampadina, int lum){
        Lampadina l = getLampadina(nomeLampadina);
        if (l != null) {
            l.setLum(lum);
        }
    }

    /**
     * Accende una lampadina di cui si passa il nome.
     * @param nomeLampadina Nome della lampadina (String)
     */
    public void accendiLampadina(String nomeLampadina){
        for(var i : prese){
            if(i.getLampadina().getNome().equals(nomeLampadina)){
                i.getLampadina().accendi();
            }
        }
    }
    /**
     * Spegne una lampadina di cui si passa il nome.
     * @param nomeLampadina Nome della lampadina (String)
     */
    public void spegniLampadina(String nomeLampadina){
        for(var i : prese){
            if(i.getLampadina().getNome().equals(nomeLampadina)){
                i.getLampadina().spegni();
            }
        }
    }

    /**
     * Stampa tutto il sistema e le caratteristiche di ciascuna lampadina.
     */
    public void stampaSistema(){
        for (var i : prese){
            System.out.println(i.getLampadina());
        }
    }

    public String getNome() {
        return nome;
    }
}
