import Eccezioni.*;
import graphics.Color;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe che rappresenta una stanza, con un numero indefinito di lampadine.
 * @author Stellino Marco
 * @author Robolini Paolo
 * @version 1.0
 */
public class Stanza implements Serializable {
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

    /**
     * Cerca una presa all'interno della stanza
     * @param nomePresa Nome della presa da cercare
     * @return Presa
     * @throws PresaNonTrovata Se non trova la presa, lancia un'eccezione
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
     * Cerca una lampadina all'interno della stanza
     * @param nomeLampadina Nome della lampadina da cercare
     * @return Lampadina
     * @throws LampadinaNonTrovata Se non trova la lampadina, lancia un'eccezione
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
     * Cerca una presa all'interno della stanza in base al nome della lampadina cui è associata
     * @param nomeLampadina Nome della lampadina
     * @return Presa
     * @throws LampadinaNonTrovata Se non trova la lampadina a cui è associata la presa, lancia un'eccezione
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

    /**
     * Aggiunge una presa alla stanza
     * @param presa Presa che si desidera aggiungere
     * @throws PresaEsistente Se esiste già una presa con lo stesso nome all'interno della stanza, lancia un'eccezione
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
     * Aggiunge una nuova lampadina alla stanza
     * @param nomePresa Nome della presa a cui si vuole aggiungere una lampadina
     * @param lampadina Lampadina che si desidera aggiungere
     * @throws LampadinaEsistente Se esiste già una lampadina con lo stesso nome all'interno della stanza, lancia un'eccezione
     * @throws PresaNonTrovata Se non trova la presa all'interno della stanza, lancia un'eccezione
     * @throws PresaOccupata Se la presa selezionata contiene già una lampadina, lancia un'eccezione
     */
    public void aggiungiLampadina(String nomePresa, Lampadina lampadina) throws LampadinaEsistente, PresaNonTrovata, PresaOccupata {
        Presa presa = getPresa(nomePresa);
        try{
            getLampadina(lampadina.getNome());
            throw new LampadinaEsistente();
        } catch (LampadinaNonTrovata e){
            presa.setLampadina(lampadina);
        }

    }

    /**
     * Rimuove una presa all'interno della stanza
     * @param nomePresa Nome della presa che si desidera rimuovere
     * @throws PresaNonTrovata Se non si trova la presa all'interno della stanza, lancia un'eccezione
     */
    public void rimuoviPresa(String nomePresa) throws PresaNonTrovata{
        Presa p = getPresa(nomePresa);
        p.setX(-100);
        p.disegna();
        prese.remove(p);
    }

    /**
     * Rimuove una lampadina all'interno della stanza
     * @param nomeLampadina Nome della lampadina che si desidera rimuovere
     * @throws LampadinaNonTrovata Se non trova la lampadina all'interno della stanza, lancia un'eccezione
     */
    public void rimuoviLampadina(String nomeLampadina) throws LampadinaNonTrovata{
        Presa p = getPresaNomeLampadina(nomeLampadina);
        Lampadina l = p.getLampadina();
        l.disegna(-100,0);
        p.rimuoviLampadina();
    }

    /**
     * Accende tutte le lampadine della stanza
     */
    public void accendiTutte(){
        for (var i : prese) {
            if (i.getLampadina() != null) {
                i.getLampadina().accendi();
            }
        }
    }
    /**
     * Spegne tutte le lampadine della stanza
     */
    public void spegniTutte(){
        for (var i : prese) {
            if (i.getLampadina() != null ){
                i.getLampadina().spegni();
            }
        }
    }

    /**
     * Ritorna la potenza istantanea totale della stanza (ovvero la somma delle singole). Non conta se le lampadine sono spente o con luminosità pari a 0.
     * @return Somma delle potenze istantanee
     */
    public float getPotenzaSistema(){
        float ritorno = 0;
        for(var i : prese){
            if(i.isOccupata() && i.getLampadina().isAcceso()){
                ritorno+=i.getLampadina().getPotenzaIstantanea();
            }
        }
        return ritorno;
    }

    /**
     * Modifica il colore di una lampadina
     * @param nomeLampadina Nome della lampadina di cui si desidera modificare il colore
     * @param colore Colore
     * @throws LampadinaNonTrovata Se non trova la lampadina all'interno della stanza, lancia un'eccezione
     */
    public void setColore(String nomeLampadina, Color colore) throws LampadinaNonTrovata{
        Lampadina l;
        l = getLampadina(nomeLampadina);
        l.setColore(colore);
    }

    /**
     * Accende una lampadina dato il suo nome
     * @param nomeLampadina Nome della lampadina che si desidera accendere
     *@throws LampadinaNonTrovata Se non trova la lampadina all'interno della stanza, lancia un'eccezione
     */
    public void accendiLampadina(String nomeLampadina) throws LampadinaNonTrovata{
        Lampadina l;
        l = getLampadina(nomeLampadina);
        l.accendi();
    }
    /**
     * Spegne una lampadina dato il suo nome
     * @param nomeLampadina Nome della lampadina che si desidera accendere
     * @throws LampadinaNonTrovata Se non trova la lampadina all'interno della stanza, lancia un'eccezione
     */
    public void spegniLampadina(String nomeLampadina) throws LampadinaNonTrovata{
        Lampadina l;
        l = getLampadina(nomeLampadina);
        l.spegni();
    }

    /**
     * Ritorna se una lampadina è accesa o spenta
     * @param nomeLampadina Nome della lampadina di cui si vuole sapere lo stato
     * @return True se la lampadina è accesa, se è spenta ritorna False
     * @throws LampadinaNonTrovata Se non trova la lampadina all'interno della stanza, lancia un'eccezione
     */
    public  boolean isLampadinaAccesa (String nomeLampadina) throws LampadinaNonTrovata{
        Lampadina l = getLampadina(nomeLampadina);
        return l.isAcceso();
    }

    /**
     * Modifica il valore della luminosità in una lampadina, dato il suo nome e il valore di lumionosità
     * @param nomeLampadina Nome della lapadina di cui si desidera modificare la luminosità
     * @param valore Valore della luminosità da impostare
     * @throws LampadinaNonTrovata Se non trova la lampadina all'interno della stanza, lancia un'eccezione
     */
    public void setLumLampadina (String nomeLampadina, int valore) throws LampadinaNonTrovata{
        Lampadina l = getLampadina(nomeLampadina);
        l.setLum(valore);
    }

    /**
     * Aumenta il valore della luminosità della lampadina di 10, dato il nome della lampadina
     * @param nomeLampadina Nome della lampadina di cui si desidera aumentare la luminosità
     * @throws LampadinaNonTrovata
     */
    public void aumentaLumLampadina (String nomeLampadina) throws LampadinaNonTrovata{
        Lampadina l = getLampadina(nomeLampadina);
        l.aumentaLum();
    }

    /**
     * Diminuisce il valore della luminosità della lampadina di 10, dato il nome della lampadina
     * @param nomeLampadina Nome della lampadina di cui si desidera diminuire la luminosità
     * @throws LampadinaNonTrovata Se non trova la lampadina, lancia un'eccezione
     */
    public void diminuisciLumLampadina (String nomeLampadina) throws LampadinaNonTrovata{
        Lampadina l = getLampadina(nomeLampadina);
        l.diminuisciLum();
    }


    /**
     * Restituisce il nome della stanza
     * @return Nome della stanza
     */
    public String getNome() {
        return nome;
    }


    /**
     * Conta quante prese sono presenti nella stanza
     * @return Numero di prese
     */
    public int getNumPrese() {
        return prese.size();
    }

    /**
     * Conta quante lampadine sono presenti nella stanza
     * @return Numero di lampadine
     */
    public int getNumLampadine () {
        int count = 0;
        for (var i : prese) {
            if (i.isOccupata()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Disegna tutte le prese della stanza
     */
    public void disegna() {
        for (var i : prese) {
            i.disegna();
        }
    }

    /**
     * Ritorna una stringa con tutte le caratteristiche della stanza, che poi verrà stampata nel file CSV.
     * @return Prima riga: nomeStanza + numero di prese, dalla seconda in poi scrive le prese
     */
    public String toStringCSVFile(){
        StringBuilder ritorno = new StringBuilder(nome + ';' + prese.size() + "\n");
        for (var i : prese) {
            ritorno.append(i.toStringCSVFile());
        }
        return ritorno.toString();
    }

    /**
     * Converte le caratteristiche della stanza in una stringa pensata per stamparla in console
     * @return Stanza + nomeStanza + elenco prese
     */
    @Override
    public String toString () {
        String s =  "\n____________________________________ " +
                "\nStanza " + this.nome;
        for(var i : prese) {
            s += i.toString();
        }
        return s;
    }
}
