import Eccezioni.*;
import graphics.Color;

import java.io.*;
import java.util.ArrayList;

/**
 * Classe che rappresenta un sistema domotico, con un numero indefinito di lampadine.
 */

public class SistemaDomotico {

    private ArrayList<Stanza> stanze;


    //TODO: Costruttore
    public SistemaDomotico(String nomeFile){
        this.stanze = new ArrayList<>();
        try{
            FileReader fr = new FileReader(nomeFile);
            BufferedReader br = new BufferedReader(fr);
            String riga = br.readLine();
            if(!riga.equals("SistemaDomotico")){
                throw new IOException();
            } else {
                //numero di stanze
                riga = br.readLine();
                int nStanze = Integer.parseInt(riga);
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

                        //controlla se la lampadina è occupata
                        if(pr.length > 3){

                            Lampadina l = new Lampadina(pr[3],Float.parseFloat(pr[4]));
                            l.setLum(Integer.parseInt(pr[5]));
                            //Colori
                            int R = Integer.parseInt(pr[6]);
                            int G = Integer.parseInt(pr[7]);
                            int B = Integer.parseInt(pr[8]);
                            l.setColore(new Color(R,G,B));
                            if(pr[9].equals("accesa")){
                                l.accendi();
                            }
                            p.setLampadina(l);
                        }
                    }
                }

            }
            fr.close();
            br.close();
        } catch (IOException e){
            this.stanze = new ArrayList<>();
        }
    }

    public SistemaDomotico() {
        stanze = new ArrayList<>();
    }

    //TODO: Salva in un file
    public void salvaInFile(String nomeFile) throws IOException{
            BufferedWriter bw = new BufferedWriter(new FileWriter(nomeFile));
            bw.write(this.toStringCSVFile());
            bw.close();
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
     * Cerca una lampadina all'interno del sistema
     * @param nomeStanza Nome della stanza (String)
     * @param nomeLampadina Nome della lampadina (String)
     * @return Ritorna la lampadina; se è stata trovata, altrimenti ritorna null
     */

    public Lampadina getLampadina(String nomeStanza, String nomeLampadina) throws StanzaNonTrovata, LampadinaNonTrovata{
        Stanza stanza = cercaStanza(nomeStanza);
        return stanza.getLampadina(nomeLampadina);
    }

    public Presa getPresa (String nomeStanza, String nomePresa) throws StanzaNonTrovata,PresaNonTrovata{
        Stanza stanza = cercaStanza(nomeStanza);
        return stanza.getPresa(nomePresa);
    }

    //TODO: Metodi di aggiunta
    /**
     * Aggiunge una nuova lampadina a una presa, dato il nome della stanza e della presa che si desidera.
     * Se è già presente una lampadina, non la sostituisce.
     * @param nomeStanza Nome della stanza in cui si vuole aggiungere la lampadina
     * @param nomePresa Nome della presa a cui si vuole collegare la lampadina
     * @param lampadina Lampadina (Lampadina)
     */
    public void aggiungiLampadina(String nomeStanza, String nomePresa, Lampadina lampadina) throws StanzaNonTrovata, PresaNonTrovata, PresaOccupata, LampadinaEsistente{

        Stanza stanza = cercaStanza(nomeStanza);
        stanza.aggiungiLampadina(nomePresa,lampadina);
    }

    public void aggiungiPresa(String nomeStanza, Presa presa) throws StanzaNonTrovata, PresaEsistente{
        Stanza s;
        s = cercaStanza(nomeStanza);
        s.aggiungiPresa(presa);
    }



    public void aggiungiStanza(Stanza s) throws StanzaEsistente{
        try {
            cercaStanza(s.getNome());
        } catch (StanzaNonTrovata e) {
            stanze.add(s);
        }

    }


    //TODO: metodi di rimozione
    public void rimuoviPresa(String nomeStanza, String nomePresa) throws  StanzaNonTrovata, PresaNonTrovata{
        Stanza s;
        s = this.cercaStanza(nomeStanza);
        s.rimuoviPresa(nomePresa);
    }

    public void rimuoviLampadina(String nomeStanza, String nomeLampadina) throws  StanzaNonTrovata, LampadinaNonTrovata{
        Stanza s;
        s = this.cercaStanza(nomeStanza);
        s.rimuoviLampadina(nomeLampadina);
    }

    /**
     * Rimuove una lampadina dato il nome della stanza e quello della presa.
     * @param nomePresa Nome della presa (String)
     * @param nomeStanza Nome della stanza (String)
     */
    public void RimuoviLampadinaNomePresa(String nomeStanza, String nomePresa) throws StanzaNonTrovata, PresaNonTrovata, LampadinaNonTrovata{
        Stanza stanza = cercaStanza(nomeStanza);
        Presa presa = stanza.getPresa(nomePresa);
        presa.setLampadina(null);
    }
    //TODO: Metodi vari
    public Stanza getStanza(String nomeStanza) throws StanzaNonTrovata{
        for(var i :stanze){
            if (i.getNome().equals(nomeStanza)){
                return i;
            }
        }
        throw new StanzaNonTrovata();
    }


    /**
     * Accende tutte le lampadine di una stanza, dato il suo nome
     * @param nomeStanza Nome stanza
     * @throws StanzaNonTrovata
     */
    public void accendiStanza(String nomeStanza) throws StanzaNonTrovata{
        Stanza stanza = cercaStanza(nomeStanza);
        stanza.accendiTutte();
    }
    /**
     * Spegne tutte le lampadine di una stanza, dato il suo nome
     * @param nomeStanza Nome stanza
     * @throws StanzaNonTrovata
     */
    public void spegniStanza(String nomeStanza) throws StanzaNonTrovata{
        Stanza stanza = cercaStanza(nomeStanza);
        stanza.spegniTutte();
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
     * Spegne tutte le lampadine del sistema
     */
    public void accendiTutte(){
        for (var i : stanze) {
            i.accendiTutte();
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

    //TODO: Metodi vari per la lampadina

    /**
     * Modifica il colore di una lampadina dato il suo nome e quello della stanza in cui è.
     * @param nomeLampadina Nome della lampadina (String)
     * @param nomeStanza Nome della stanza (String)
     * @param colore Colore (String)
     */
    public void modificaColoreLampadina(String nomeStanza,String nomeLampadina, Color colore) throws StanzaNonTrovata, LampadinaNonTrovata{
        Stanza s = cercaStanza(nomeStanza);
        s.setColore(nomeLampadina, colore);

    }

    /**
     * Modifica la luminosità di una lampadina dato il suo nome.
     * @param nomeLampadina Nome della lampadina (String)
     * @param nomeStanza Nome della stanza (String)
     * @param lum Luminosità (int)
     */
    public void modificaLumLampadina(String nomeStanza, String nomeLampadina, int lum) throws StanzaNonTrovata, LampadinaNonTrovata{
        Stanza s = cercaStanza(nomeStanza);
        s.setLumLampadina(nomeLampadina, lum);
    }

    public void aumentaLumLampadina(String nomeStanza, String nomeLampadina) throws StanzaNonTrovata, LampadinaNonTrovata{
        Stanza s = cercaStanza(nomeStanza);
        s.aumentaLumLampadina(nomeLampadina);
    }
    public void diminuisciLumLampadina(String nomeStanza, String nomeLampadina) throws StanzaNonTrovata, LampadinaNonTrovata{
        Stanza s = cercaStanza(nomeStanza);
        s.diminuisciLumLampadina(nomeLampadina);
    }


    /**
     * Spegne una lampadina di cui si passa il suo nome e quello della stanza.
     * @param nomeStanza Nome della stanza (String)
     * @param nomeLampadina Nome della lampadina (String)
     */
    public void accendiLampadina(String nomeStanza, String nomeLampadina) throws StanzaNonTrovata, LampadinaNonTrovata{
        Stanza s = getStanza(nomeStanza);
        s.accendiLampadina(nomeLampadina);
    }
    /**
     * Spegne una lampadina di cui si passa il suo nome e quello della stanza.
     * @param nomeStanza Nome della stanza (String)
     * @param nomeLampadina Nome della lampadina (String)
     */
    public void spegniLampadina(String nomeStanza, String nomeLampadina)throws StanzaNonTrovata, LampadinaNonTrovata{
        Stanza s = getStanza(nomeStanza);
        s.spegniLampadina(nomeLampadina);
    }

    public boolean isLampadinaAccesa(String nomeStanza, String nomeLampadina)throws StanzaNonTrovata, LampadinaNonTrovata {
        Stanza s = getStanza(nomeStanza);
        return s.isLampadinaAccesa(nomeLampadina);
    }

    public int getNumLampadineStanza(String nomeStanza) throws StanzaNonTrovata {
        Stanza s = this.cercaStanza(nomeStanza);
        return  s.getNumLampadine();
    }



    //TODO: metodi vari per la presa
    public boolean isPresaOccupata (String nomeStanza, String nomePresa) throws StanzaNonTrovata, PresaNonTrovata {
        Stanza s = this.cercaStanza(nomeStanza);
        Presa p = s.getPresa(nomePresa);
        return p.isOccupata();
    }

    public int getNumPreseStanza(String nomeStanza) throws StanzaNonTrovata {
        Stanza s = this.cercaStanza(nomeStanza);
        return  s.getNumPrese();
    }



    public String toStringCSVFile(){
        StringBuilder ritorno = new StringBuilder("SistemaDomotico\n");
        ritorno.append(stanze.size()).append("\n");
        for(var i : stanze){
            ritorno.append(i.toStringCSVFile());
        }
        return ritorno.toString();
    }
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (var i : stanze) {
            s.append(i.toString());
        }
        return s.toString();
    }
}
