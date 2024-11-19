import Eccezioni.FileNonValido;
import Eccezioni.LampadinaNonTrovata;
import Eccezioni.PresaNonTrovata;
import Eccezioni.StanzaNonTrovata;
import graphics.Color;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe che rappresenta un sistema domotico, con un numero indefinito di lampadine.
 */
public class SistemaDomotico {

    private ArrayList<Stanza> stanze;


    //TODO: Costruttore
    public SistemaDomotico(String nomeFile){
        try{
            FileReader fr = new FileReader(nomeFile);
            BufferedReader br = new BufferedReader(fr);
            String riga = br.readLine();
            if(!riga.equals("SistemaDomotico")){
                throw new IOException();
            } else {
                //numero di stanze
                int nStanze = br.read();
                for(int i = 0; i < nStanze; ++i){
                    riga = br.readLine();
                    String[] v = riga.split(";");
                    //Nome della stanza
                    this.aggiungiStanza(new Stanza(v[0]));
                    //numero di prese
                    int nPrese = Integer.parseInt(v[1]);
                    for(int j = 0; j < nPrese; ++j){
                        //legge la presa
                        String presa = br.readLine();
                        String[] pr = presa.split(";");
                        Presa p = new Presa(pr[0],Integer.parseInt(pr[1]),Integer.parseInt(pr[2]));
                        this.aggiungiPresa(v[0],p);
                        if(pr[3].equals("occupata")){
                            Lampadina l = new Lampadina(pr[4],Integer.parseInt(pr[5]));
                            l.setLum(Integer.parseInt(pr[6]));
                            //Colori
                            int R = Integer.parseInt(pr[7]);
                            int G = Integer.parseInt(pr[8]);
                            int B = Integer.parseInt(pr[9]);
                            l.setColore(new Color(R,G,B));
                            if(pr[10].equals("accesa")){
                                l.accendi();
                            }
                        }
                    }
                }
            }
        } catch (IOException e){
            this.stanze = new ArrayList<>();
        }
    }

    //TODO: Salva in un file
    public void salvaInFile(String nomeFile){

    }

    //TODO: Metodi di ricerca
    /**
     * Cerca una stanza all'interno del sistema domotico
     * @param nomeStanza Nome della stanza che si desidera cercare (String)
     * @return Ritorna la stanza se è stata trovata, altrimenti ritorna null
     */
    private Stanza cercaStanza (String nomeStanza) throws StanzaNonTrovata{
        for (var i : stanze) {
            if (i.getNome().equals(nomeStanza)){
                return i;
            }
        }
        throw new StanzaNonTrovata();
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
        throw new LampadinaNonTrovata();
    }

    //TODO: Metodi di aggiunta
    /**
     * Aggiunge una nuova lampadina a una presa, dato il nome della stanza e della presa che si desidera.
     * Se è già presente una lampadina, non la sostituisce.
     * @param nomeStanza Nome della stanza in cui si vuole aggiungere la lampadina
     * @param nomePresa Nome della presa a cui si vuole collegare la lampadina
     * @param lampadina Lampadina (Lampadina)
     */
    public void aggiungiLampadina(String nomeStanza, String nomePresa, Lampadina lampadina){

        try {
            Stanza stanza = cercaStanza(nomeStanza);
            stanza.aggiungiLampadina(nomePresa,lampadina);
        } catch (StanzaNonTrovata e){
            throw e;
        }
    }

    public void aggiungiPresa(String nomeStanza, Presa presa){
        Stanza s = cercaStanza(nomeStanza);
        if(s == null){
            throw new StanzaNonTrovata();
        } else {
            s.aggiungiPresa(presa);
        }
    }

    public void aggiungiStanza(Stanza s){
        stanze.add(s);
    }


    /**
     * Rimuove una lampadina dato il nome della stanza e quello della presa.
     * @param nomePresa Nome della presa (String)
     * @param nomeStanza Nome della stanza (String)
     */
    public void RimuoviLampadinaNomePresa(String nomeStanza, String nomePresa){
        Stanza stanza = cercaStanza(nomeStanza);
        if(stanza != null){
            Presa presa = stanza.getPresa(nomePresa);
            if(presa != null){
                presa.setLampadina(null);
            } else {
                throw new PresaNonTrovata();
            }
        } else {
            throw new StanzaNonTrovata();
        }
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
     * Modifica il colore di una lampadina dato il suo nome e quello della stanza in cui è.
     * @param nomeLampadina Nome della lampadina (String)
     * @param nomeStanza Nome della stanza (String)
     * @param colore Colore (String)
     */
    public void modificaColoreLampadina(String nomeStanza,String nomeLampadina, Color colore){
        Lampadina lampadina = getLampadinaNome(nomeStanza, nomeLampadina);
        lampadina.setColore(colore);

    }

    /**
     * Modifica la luminosità di una lampadina dato il suo nome.
     * @param nomeLampadina Nome della lampadina (String)
     * @param nomeStanza Nome della stanza (String)
     * @param lum Luminosità (int)
     */
    public void modificaLum(String nomeStanza, String nomeLampadina, int lum){
        Lampadina l = this.getLampadinaNome(nomeStanza, nomeLampadina);
        l.setLum(lum);
    }

    /**
     * Spegne una lampadina di cui si passa il suo nome e quello della stanza.
     * @param nomeStanza Nome della stanza (String)
     * @param nomeLampadina Nome della lampadina (String)
     */
    public void accendiLampadina(String nomeStanza, String nomeLampadina){
        Lampadina l = getLampadinaNome(nomeStanza, nomeLampadina);
        l.accendi();
    }
    /**
     * Spegne una lampadina di cui si passa il suo nome e quello della stanza.
     * @param nomeStanza Nome della stanza (String)
     * @param nomeLampadina Nome della lampadina (String)
     */
    public void spegniLampadina(String nomeStanza, String nomeLampadina){
        Lampadina l = getLampadinaNome(nomeStanza, nomeLampadina);
        l.spegni();
    }

    /**
     * Stampa tutto il sistema e le caratteristiche di ciascuna lampadina.
     */
    public void stampaSistema(){
        for (var i : stanze){
                i.stampaSistema();
        }
    }

    @Override
    public String toString(){
        String ritorno = "SistemaDomotico\n";
        ritorno += stanze.size() + "\n";
        for(var i : stanze){
            ritorno += i.toString();
        }
        return ritorno;
    }
}
