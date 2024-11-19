import Eccezioni.*;
import graphics.Color;

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
     * @throws PresaNonTrovata
     */
    public Presa getPresa (String nomePresa) throws PresaNonTrovata{
        for(var i : prese){
            if(i.getNome().equals(nomePresa)){
                return i;
            }
        }
        throw new PresaNonTrovata();
    }

    /**
     * Dato il nome di una lampadina
     * @param nomeLampadina Nome della presa (String)
     * @return Ritorna la presa se è stata trovata, altrimenti ritorna null
     * @throws LampadinaNonTrovata
     */
    public Lampadina getLampadina(String nomeLampadina) throws LampadinaNonTrovata {
        for(var i : prese){
            if(i.getLampadina() != null && i.getLampadina().getNome().equals(nomeLampadina)){
                return i.getLampadina();
            }
        }
        //lancia l'eccezione "Eccezioni.LampadinaNonTrovata"
        throw new LampadinaNonTrovata();
    }

    /**
     * Cerca una presa in base al nome della lampadina
     * @param nomeLampadina Nome della lampadina (String)
     * @return La presa se è stata trovata, altrimenti null
     * @throws LampadinaNonTrovata Se la lampadina non è presente nella stanza
     */
    public Presa getPresaNomeLampadina(String nomeLampadina) throws  LampadinaNonTrovata{
        Lampadina lampadina = getLampadina(nomeLampadina);
        for(var i : prese){
            if(i.getLampadina() == lampadina){
                return i;
            }
        }
        throw new LampadinaNonTrovata();
    }


    //TODO: Metodi di aggiunta

    /**
     * Aggiunge una nuova presa alla stanza
     * @param presa La presa da aggiunfere (Presa)
     * @throws PresaEsistente
     */
    public void aggiungiPresa(Presa presa) throws PresaEsistente{
        try {
            getPresa(presa.getNome());
            throw new PresaEsistente();
        } catch (PresaNonTrovata e){
            prese.add(presa);
        }
    }

    /**
     * Aggiunge una nuova lampadina
     * @param nomePresa Nome della presa a cui si vuole aggiungere una presa
     * @param lampadina Lampadina
     * @throws PresaEsistente
     * @throws LampadinaEsistente
     * @throws PresaNonTrovata
     */
    public void aggiungiLampadina(String nomePresa, Lampadina lampadina) throws LampadinaEsistente, PresaNonTrovata, PresaOccupata {
        try {

            Presa presa = getPresa(nomePresa);

            try{
                getLampadina(lampadina.getNome());
                throw new LampadinaEsistente();
            } catch (LampadinaNonTrovata e){
                try{
                    presa.setLampadina(lampadina);
                } catch (PresaOccupata e1){
                    throw e1;
                }
            }

        } catch (PresaNonTrovata e){
         throw e;
        }
    }


    //TODO: Metodi di rimozione

    /**
     * Rimuove una presa
     * @param nomePresa Nome della presa (String)
     * @return true se la presa è stata rimossa, false se non è stata rimossa
     */
    public void rimuoviPresa(String nomePresa) {
        Presa p = getPresa(nomePresa);
        if (p != null) {
            prese.remove(p);
        } else {
            throw new PresaNonTrovata();
        }
    }

    /**
     * Rimuove una lampadina dato il nome.
     * @param nomeLampadina Nome (String)
     * @return true se la lampadina è stata rimossa, false se non è stata rimossa
     */
    public void rimuoviLampadina(String nomeLampadina) throws LampadinaNonTrovata{
        Presa p = getPresaNomeLampadina(nomeLampadina);
        if(p != null){
            p.setLampadina(null);
        } else {
            throw new LampadinaNonTrovata();
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
     * @throws LampadinaNonTrovata
     */
    public void setColore(String nomeLampadina, Color colore) throws LampadinaNonTrovata{
        Lampadina l;
        try {
            l = getLampadina(nomeLampadina);
            l.setColore(colore);
        } catch (LampadinaNonTrovata e) {
            throw e;
        }
    }

    /**
     * Modifica la luminosità di una lampadina dato il suo nome.
     * @param nomeLampadina Nome (String)
     * @param lum Luminosità (int)
     * @throws LampadinaNonTrovata
     */
    public void setLum(String nomeLampadina, int lum) throws LampadinaNonTrovata{
        try {
            Lampadina l = getLampadina(nomeLampadina);
            l.setLum(lum);
        } catch (LampadinaNonTrovata e){
            throw e;
        }
    }

    /**
     * Accende una lampadina di cui si passa il nome.
     * @param nomeLampadina Nome della lampadina (String)
     */
    public void accendiLampadina(String nomeLampadina) throws LampadinaNonTrovata{
        Lampadina l;
        try {
            l = getLampadina(nomeLampadina);
            l.accendi();
        } catch (LampadinaNonTrovata e){
            throw e;
        }
    }
    /**
     * Spegne una lampadina di cui si passa il nome.
     * @param nomeLampadina Nome della lampadina (String)
     */
    public void spegniLampadina(String nomeLampadina) throws LampadinaNonTrovata{
        Lampadina l;
        try {
            l = getLampadina(nomeLampadina);
            l.spegni();
        } catch (LampadinaNonTrovata e){
            throw e;
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

    /**
     * Restituisce il nome della stanza
     * @return Nome della stanza (Stringa)
     */
    public String getNome() {
        return nome;
    }

    @Override
    public String toString(){
        String ritorno = nome + ';' + prese.size() + "\n";
        for (var i : prese) {
            ritorno += i.toString();
        }
        return ritorno;
    }
}
