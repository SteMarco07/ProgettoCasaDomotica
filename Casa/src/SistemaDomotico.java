import java.util.ArrayList;

/**
 * Classe che rappresenta un sistema domotico, con un numero indefinito di lampadine.
 */
public class SistemaDomotico {


    private ArrayList<Stanza> stanze;

    /**
     * Costruttore vuoto
     */
    public SistemaDomotico (){
        this.stanze = new ArrayList<Stanza>();
    }


    /**
     * Cerca una stanza all'interno del sistema domotico
     * @param nomeStanza Nome della stanza che si desidera cercare (String)
     * @return Ritorna la stanza se è stata trovata, altrimenti ritorna null
     */
    private Stanza cercaStanza (String nomeStanza) {
        for (var i : stanze) {
            if (i.getNome().equals(nomeStanza)){
                return i;
            }
        }
        return null;
    }

    /**
     * Cre
     * @param nomeStanza
     * @param nomePresa
     * @param nomeLampadina
     * @return
     */

    public Lampadina cercaLampadina(String nomeStanza, String nomePresa, String nomeLampadina){
        Stanza s = cercaStanza(nomeStanza);
        if (s != null) {
            Presa p = s.cercaPresa(nomePresa);
            if (p != null) {
                return p.getLampadina();
            }
        }
        return  null;
    }


    /**
     * Aggiunge una nuova lampadina a una presa, dato il nome della stanza e della presa che si desidera.
     * Se è già presente una lampadina, non la sostituisce.
     * @param nomeStanza Nome della stanza in cui si vuole aggiungere la lampadina
     * @param nomePresa Nome della presa a cui si vuole collegare la lampadina
     * @param lampadina Lampadina (Lampadina)
     */
    public boolean aggiungiLampadina(String nomeStanza, String nomePresa, Lampadina lampadina){
        Stanza stanza = cercaStanza(nomeStanza);
        if(stanza != null){
            Presa presa = stanza.cercaPresa(nomePresa);
            if(presa != null && presa.getLampadina() == null){
                presa.setLampadina(lampadina);
                return true;
            }
        }
        return false;
    }



    /**
     * Rimuove una lampadina dato il nome.
     * @param nome Nome (String)
     */
    public void rimuoviLampadina(String nomeStanza, String nomePresa){
        cercaStanza(nomeStanza).cercaPresa(nomePresa) = null;
    }

    /**
     * Accende tutte le lampadine del sistema
     */
    public void accendiTutte(){
        for (var i : lampadine) {
            if (i != null) {
                i.accendi();
            }
        }
    }
    /**
     * Spegne tutte le lampadine del sistema
     */
    public void spegniTutte(){
        for (var i : lampadine) {
                i.spegni();
        }
    }

    /**
     * Ritorna la potenza istantanea totale del sistema (ovvero la somma delle singole). Non conta se le lampadine sono spente.
     * @return Somma delle potenze istantanee (Float)
     */
    public float getPotenzaSistema(){
        float ritorno = 0;
        for(var i : lampadine){
                if(i.isAcceso()){
                    ritorno+=i.getPotenzaIstantanea();
            }
        }
        return ritorno;
    }

    /**
     * Modifica il colore di una lampadina dato il suo nome.
     * @param nome Nome (String)
     * @param colore Colore (String)
     */
    public void modificaColore(String nome, String colore){
        int pos = this.cercaLampadina(nome);
        if(pos >= 0){
            this.lampadine.get(pos).setColore(colore);
        }
    }

    /**
     * Modifica la luminosità di una lampadina dato il suo nome.
     * @param nome Nome (String)
     * @param lum Luminosità (int)
     */
    public void modificaLum(String nome, int lum){
        int pos = this.cercaLampadina(nome);
        if(pos >= 0){
            this.lampadine.get(pos).setLum(lum);
        }
    }

    /**
     * Accende una lampadina di cui si passa il nome.
     * @param nome Nome (String)
     */
    public void accendiLampadina(String nome){
        int pos = this.cercaLampadina(nome);
        if(pos >= 0){
            lampadine.get(pos).accendi();
        }
    }
    /**
     * Spegne una lampadina di cui si passa il nome.
     * @param nome Nome (String)
     */
    public void spegniLampadina(String nome){
        cercaLampadina(nome)
        lampadine.get(this.cercaLampadina(nome)).spegni();
    }

    /**
     * Stampa tutto il sistema e le caratteristiche di ciascuna lampadina.
     */
    public void stampaSistema(){
        for (var i : stanze){
                i.stampaSistema();
        }
    }
}
