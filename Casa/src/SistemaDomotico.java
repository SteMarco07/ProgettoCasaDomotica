import java.util.ArrayList;

/**
 * Classe che rappresenta un sistema domotico, con un numero indefinito di lampadine.
 */
public class SistemaDomotico {


    private ArrayList<Stanza> stanze;


    //TODO: Costruttori
    /**
     * Costruttore vuoto
     */
    public SistemaDomotico (){
        this.stanze = new ArrayList<Stanza>();
    }



    //TODO: Metodi di ricerca
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
     * Cerca una lampadina data la sua presa
     * @param presa Presa
     * @return Ritorna la lampadina se è stata trovata, altrimenti ritorna null
     */
    public Lampadina getLampadinaPresa(Presa presa){
        return presa.getLampadina();
    }
    /**
     * Cerca una lampadina all'interno del sistema
     * @param nomeStanza Nome della stanza (String)
     * @param nomeLampadina Nome della lampadina (String)
     * @return Ritorna la lampadina; se è stata trovata, altrimenti ritorna null
     */

    public Lampadina getLampadinaNome(String nomeStanza, String nomeLampadina){
        Stanza stanza = cercaStanza(nomeStanza);
        if(stanza != null){
            return stanza.getLampadina(nomeLampadina);
        }
        return null;
    }

    //TODO: Metodi di aggiunta
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
            Presa presa = stanza.getPresa(nomePresa);
            if(presa != null && presa.getLampadina() == null){
                presa.setLampadina(lampadina);
                return true;
            }
        }
        return false;
    }



    /**
     * Rimuove una lampadina dato il nome della stanza e quello della presa.
     * @param nomePresa Nome della presa (String)
     * @param nomeStanza Nome della stanza (String)
     */
    public boolean RimuoviLampadinaNomePresa(String nomeStanza, String nomePresa){
        Stanza stanza = cercaStanza(nomeStanza);
        if(stanza != null){
            Presa presa = stanza.getPresa(nomePresa);
            if(presa != null){
                presa.setLampadina(null);
                return true;
            }
        }
        return false;
    }

    /**
     * Accende tutte le lampadine di una stanza dato il suo nome
     * @param nomeStanza Nome della stanza (String)
     */
    public void accendiTutteStanza(String nomeStanza){
       Stanza stanza = cercaStanza(nomeStanza);
       if(stanza != null){
           stanza.accendiTutte();
       }
    }

    /**
     * Accende tutte le lampadine di una stanza, dato il suo nome
     * @param nomeStanza Nome stanza
     */
    public void accendiStanza(String nomeStanza){
        Stanza stanza = cercaStanza(nomeStanza);
        if(stanza != null){
            stanza.accendiTutte();
        }
    }

    /**
     * Spegne tutte le lampadine del sistema
     */
    public void spegniTutte(){
        for (var i : stanze) {
                i.spegniTutte();
        }
    }

    /**
     * Ritorna la potenza istantanea totale del sistema (ovvero la somma delle singole). Non conta se le lampadine sono spente.
     * @return Somma delle potenze istantanee (Float)
     */
    public float getPotenzaSistema(){
        float ritorno = 0;
        for(var i : stanze){
                ritorno+=i.getPotenzaSistema();
        }
        return ritorno;
    }

    /**
     * Modifica il colore di una lampadina dato il suo nome.
     * @param nome Nome (String)
     * @param colore Colore (String)
     */
    public void modificaColoreLampadina(String nomeStanza, String nomePresa, String nomeLampadina, String colore){
        Lampadina lampadina = getLampadinaNome(nomeStanza, nomePresa, nomeLampadina);

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
        cercaLampadina(nome);
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
