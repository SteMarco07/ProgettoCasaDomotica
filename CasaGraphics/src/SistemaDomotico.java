import Eccezioni.*;
import graphics.Color;

import java.io.*;
import java.util.ArrayList;

/**
 * Classe che rappresenta un sistema domotico, con un numero indefinito di stanze.
 * @author Stellino Marco
 * @author Robolini Paolo
 * @version 1.0
 */

public class SistemaDomotico implements Serializable{

    private ArrayList<Stanza> stanze;

    /**
     * Costruttore che carica prima da un file tramite la deserializzazione, se fallisce prova il caricamento tramite file CSV, se fallisce anche quest'ultima lo cra vuoto
     * @param nomeFileSerializzato Nome del file da cui fare la deserializzazione
     * @param nomeFileCSV Nome del file CSV
     */
    public SistemaDomotico(String nomeFileSerializzato, String nomeFileCSV) {
        this.stanze = new ArrayList<>();
        try {
            SistemaDomotico deserializzato = deserializza(nomeFileSerializzato);
            this.stanze = deserializzato.stanze;
        } catch (IOException | ClassNotFoundException e) {
            this.caricaDaFileCSV(nomeFileCSV);
        }
    }

    /**
     * Costruttore vuoto
     */
    public SistemaDomotico() {
        this.stanze = new ArrayList<>();
    }

    /**
     * Deserializza l'oggetto sistemaDomotico
     * @param nomeFile Nome del file da cui l'oggetto dev'essere deserializzato
     * @return sistemaDomotico
     * @throws IOException In caso che non riesca ad aprire il file, lancia un'eccezione
     * @throws ClassNotFoundException In caso in cui non riesca ad assegnare correttamente la classe, lancia un'eccezione
     */
    public SistemaDomotico deserializza(String nomeFile) throws IOException, ClassNotFoundException {
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(nomeFile))) {
            return (SistemaDomotico)is.readObject();
        }
    }

    /**
     * Serializza l'oggetto sistemaDomotico
     * @param nomeFile Nome del file in cui l'oggetto dev'essere serializzato
     * @throws IOException In caso in cui non riesca a scrivere su nel file, lancia un'eccezione
     */
    public void serializza(String nomeFile) throws IOException {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(nomeFile));
        os.writeObject(this);
    }

    /**
     * Carica le caratteristiche della classe da un file CSV
     * @param nomeFile Percorso del file dove viene salvato (lo stesso del salvataggio)
     * @throws IOException In caso in cui non riesca a trovare o aprire il file CSV, lancia un'eccezione
     */
    public void caricaDaFileCSV(String nomeFile) {
        this.stanze = new ArrayList<>();
        try{
            FileReader fr = new FileReader(nomeFile);
            BufferedReader br = new BufferedReader(fr);
            String riga = br.readLine();
            //Ogni file idoneo deve avere scritto alla prima riga "SistemaDomotico"
            if(!riga.equals("SistemaDomotico")){
                throw new IOException();
            } else {
                //carica il numero di stanze
                riga = br.readLine();
                int nStanze = Integer.parseInt(riga);
                for(int i = 0; i < nStanze; ++i){
                    riga = br.readLine();
                    String[] v = riga.split(";");
                    //nome della stanza
                    this.aggiungiStanza(new Stanza(v[0]));
                    //legge il numero di prese in una stanza
                    int nPrese = Integer.parseInt(v[1]);
                    for(int j = 0; j < nPrese; ++j){

                        //legge la presa
                        String presa = br.readLine();
                        String[] pr = presa.split(";");
                        Presa p = new Presa(pr[0],Integer.parseInt(pr[1]),Integer.parseInt(pr[2]));
                        this.aggiungiPresa(v[0],p);

                        //controlla se la lampadina è occupata attraverso la lunghezza
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
                            //attacca la presa alla lampadina
                            p.setLampadina(l);
                        }
                    }
                }

            }
            //chiude file reader e buffered reader
            fr.close();
            br.close();
        } catch (IOException e){
            //se il file non esiste o non è valido, il sistema viene inizializzato come vuoto
            this.stanze = new ArrayList<>();
        }
    }
    /**
     * Salca in un file CSV le caratteristiche del sistema
     * @param nomeFile Percorso del file dove viene salvato (lo stesso del caricamento)
     * @throws IOException In caso il file non sia valido, lancia un'eccezione
     */
    public void salvaInFileCSV(String nomeFile) throws IOException{
            BufferedWriter bw = new BufferedWriter(new FileWriter(nomeFile));
            bw.write(this.toStringCSVFile());
            bw.close();
    }


    /**
     * Cerca una stanza all'interno del sistema domotico
     * @param nomeStanza Nome della stanza che si desidera cercare (String)
     * @return Se trovata, ritorna la stanza
     * @throws StanzaNonTrovata Se non trova la stanza, lancia un'eccezione
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
     * @param nomeStanza Nome della stanza
     * @param nomeLampadina Nome della lampadina
     * @return Se trovata, ritorna la lampadina
     * @throws LampadinaNonTrovata Se il metodo getLampadina non trova la lampadina, lancia un eccezione
     */

    public Lampadina getLampadina(String nomeStanza, String nomeLampadina) throws StanzaNonTrovata, LampadinaNonTrovata{
        Stanza stanza = cercaStanza(nomeStanza);
        return stanza.getLampadina(nomeLampadina);
    }

    /**
     * Metodo che cerca una presa all'interno del sistema
     * @param nomeStanza Nome della stanza in cui si vuole cercare la presa
     * @param nomePresa Nome della presa
     * @return Presa
     * @throws StanzaNonTrovata Se il nome della stanza è errato, lancia un'eccezione
     * @throws PresaNonTrovata  Se la presa non viene trovata, lancia un'eccezione
     */
    public Presa getPresa (String nomeStanza, String nomePresa) throws StanzaNonTrovata,PresaNonTrovata{
        Stanza stanza = cercaStanza(nomeStanza);
        return stanza.getPresa(nomePresa);
    }

    /**
     * Aggiunge una nuova lampadina a una presa, dato il nome della stanza e della presa che si desidera.
     * @param nomeStanza Nome della stanza in cui si vuole aggiungere la lampadina
     * @param nomePresa Nome della presa a cui si vuole collegare la lampadina
     * @param lampadina Lampadina (Lampadina)
     * @throws StanzaNonTrovata Se non trova la stanza, lancia un'eccezione
     * @throws PresaNonTrovata Se non trova la presa, lancia un'eccezione
     * @throws PresaOccupata Se la presa esiste, ma è già occupata, lancia un'eccezione
     * @throws LampadinaEsistente Se la lampadina esiste già, lancia un'eccezione
     */
    public void aggiungiLampadina(String nomeStanza, String nomePresa, Lampadina lampadina) throws StanzaNonTrovata, PresaNonTrovata, PresaOccupata, LampadinaEsistente{

        Stanza stanza = cercaStanza(nomeStanza);
        stanza.aggiungiLampadina(nomePresa,lampadina);
    }

    /**
     * Aggiunge una nuova presa all'interno del sistema Domotico
     * @param nomeStanza Nome della stanza
     * @param presa Presa che si vuole aggiungere
     * @throws StanzaNonTrovata Se non trova la stanza, lancia un'eccezione
     * @throws PresaEsistente Se nella stanza esiste già una presa, lancia un'eccezione
     */
    public void aggiungiPresa(String nomeStanza, Presa presa) throws StanzaNonTrovata, PresaEsistente{
        Stanza s;
        s = cercaStanza(nomeStanza);
        s.aggiungiPresa(presa);
    }

    /**
     * Aggiunge una stanza al sistema domotico
     * @param s Stanza
     * @throws StanzaEsistente Se è già presente una stanza con lo stesso nome, lancia un'eccezione
     */
    public void aggiungiStanza(Stanza s) throws StanzaEsistente{
        try {
            cercaStanza(s.getNome());
        } catch (StanzaNonTrovata e) {
            stanze.add(s);
        }

    }


    /**
     * Rimuove una presa dal sistema domotico
     * @param nomeStanza Nome della stanza in cui si vuole cercare
     * @param nomePresa Nome della presa da rimuovere
     * @throws StanzaNonTrovata Se non trova la stanza, lancia un'eccezione
     * @throws PresaNonTrovata Se non trova la presa, lancia un'eccezione
     */
    public void rimuoviPresa(String nomeStanza, String nomePresa) throws  StanzaNonTrovata, PresaNonTrovata{
        Stanza s;
        s = this.cercaStanza(nomeStanza);
        s.rimuoviPresa(nomePresa);
    }

    /**
     * Rimuove una lampadina passando il suo nome e quello della stanza
     * @param nomeStanza Nome della stanza in cui si vuole cercare
     * @param nomeLampadina Nome della lampadina da rimuovere
     * @throws StanzaNonTrovata Se non trova la stanza, lancia un'eccezione
     * @throws LampadinaNonTrovata Se non trova la lampadina nella stanza, lancia un'eccezione
     */
    public void rimuoviLampadina(String nomeStanza, String nomeLampadina) throws  StanzaNonTrovata, LampadinaNonTrovata{
        Stanza s;
        s = this.cercaStanza(nomeStanza);
        s.rimuoviLampadina(nomeLampadina);
    }

    /**
     * Cerca una stanza all'interno del sistema domotico
     * @param nomeStanza Nome della stanza
     * @return Stanza
     * @throws StanzaNonTrovata Se non esiste una stanza con quel nome, lancia un'eccezione
     */
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
     * @throws StanzaNonTrovata Se non trova la stanza, lancia un'eccezione
     */
    public void accendiStanza(String nomeStanza) throws StanzaNonTrovata{
        Stanza stanza = cercaStanza(nomeStanza);
        stanza.accendiTutte();
    }
    /**
     * Spegne tutte le lampadine di una stanza, dato il suo nome
     * @param nomeStanza Nome stanza
     * @throws StanzaNonTrovata Se non trova la stanza, lancia un'eccezione
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
     * Accende tutte le lampadine del sistema
     */
    public void accendiTutte(){
        for (var i : stanze) {
            i.accendiTutte();
        }
    }

    /**
     * Ritorna la potenza istantanea totale del sistema (ovvero la somma delle singole lampadine)
     * @return Somma delle potenze istantanee
     */
    public float getPotenzaSistema(){
        float ritorno = 0;
        for(var i : stanze){
                ritorno+=i.getPotenzaSistema();
        }
        return ritorno;
    }


    /**
     * Modifica il colore di una lampadina dato il suo nome e quello della stanza in cui è
     * @param nomeLampadina Nome della lampadina
     * @param nomeStanza Nome della stanza
     * @param colore Colore (del packaging graphics)
     * @throws StanzaNonTrovata Se la stanza non esiste, lancia un'eccezione
     * @throws LampadinaNonTrovata Se non trova la lampadina, lancia un'eccezione
     */
    public void modificaColoreLampadina(String nomeStanza,String nomeLampadina, Color colore) throws StanzaNonTrovata, LampadinaNonTrovata{
        Stanza s = cercaStanza(nomeStanza);
        s.setColore(nomeLampadina, colore);

    }

    /**
     * Modifica la luminosità di una lampadina dato il suo nome e quello della stanza in cui si vuole cercare
     * @param nomeLampadina Nome della lampadina
     * @param nomeStanza Nome della stanza
     * @param lum Luminosità
     * @throws StanzaNonTrovata Se la stanza non esiste, lancia un'eccezione
     * @throws LampadinaNonTrovata Se non trova la lampadina, lancia un'eccezione
     */
    public void modificaLumLampadina(String nomeStanza, String nomeLampadina, int lum) throws StanzaNonTrovata, LampadinaNonTrovata{
        Stanza s = cercaStanza(nomeStanza);
        s.setLumLampadina(nomeLampadina, lum);
    }

    /**
     Aumenta il valore della luminosità della lampadina di 10, dato il suo nome e quello della stanza
     * @param nomeStanza Nome della stanza
     * @param nomeLampadina Nome della lampadina
     * @throws StanzaNonTrovata Se non trova la stanza, lancia un'eccezione
     * @throws LampadinaNonTrovata Se non trova la lampadina, lancia un'eccezione
     */
    public void aumentaLumLampadina(String nomeStanza, String nomeLampadina) throws StanzaNonTrovata, LampadinaNonTrovata{
        Stanza s = cercaStanza(nomeStanza);
        s.aumentaLumLampadina(nomeLampadina);
    }

    /**
     Diminuisce il valore della luminosità della lampadina di 10, dato il suo nome e quello della stanza
     * @param nomeStanza Nome della stanza
     * @param nomeLampadina Nome della lampadina
     * @throws StanzaNonTrovata Se non trova la stanza, lancia un'eccezione
     * @throws LampadinaNonTrovata Se non trova la lampadina, lancia un'eccezione
     */
    public void diminuisciLumLampadina(String nomeStanza, String nomeLampadina) throws StanzaNonTrovata, LampadinaNonTrovata{
        Stanza s = cercaStanza(nomeStanza);
        s.diminuisciLumLampadina(nomeLampadina);
    }


    /**
     * Accende una lampadina di cui si passa il suo nome e quello della stanza
     * @param nomeStanza Nome della stanza
     * @param nomeLampadina Nome della lampadina
     * @throws StanzaNonTrovata Se la stanza non esiste, lancia un'eccezione
     * @throws LampadinaNonTrovata Se non trova la lampadina, lancia un'eccezione
     */
    public void accendiLampadina(String nomeStanza, String nomeLampadina) throws StanzaNonTrovata, LampadinaNonTrovata{
        Stanza s = getStanza(nomeStanza);
        s.accendiLampadina(nomeLampadina);
    }
    /**
     * Spegne una lampadina di cui si passa il suo nome e quello della stanza.
     * @param nomeStanza Nome della stanza
     * @param nomeLampadina Nome della lampadina
     * @throws StanzaNonTrovata Se la stanza non esiste, lancia un'eccezione
     * @throws LampadinaNonTrovata Se non trova la lampadina, lancia un'eccezione
     */
    public void spegniLampadina(String nomeStanza, String nomeLampadina)throws StanzaNonTrovata, LampadinaNonTrovata{
        Stanza s = getStanza(nomeStanza);
        s.spegniLampadina(nomeLampadina);
    }

    /**
     * Restituisce lo stato di accensione di una lampadina cercata attraverso il suo nome e quello della stanza
     * @param nomeStanza Nome della stanza
     * @param nomeLampadina Nome della lampadina
     * @return Stato di accensione
     * @throws StanzaNonTrovata Se la stanza non esiste, lancia un'eccezione
     * @throws LampadinaNonTrovata Se non trova la lampadina, lancia un'eccezione
     */
    public boolean isLampadinaAccesa(String nomeStanza, String nomeLampadina)throws StanzaNonTrovata, LampadinaNonTrovata {
        Stanza s = getStanza(nomeStanza);
        return s.isLampadinaAccesa(nomeLampadina);
    }

    /**
     * Conta quante lampadine sono presenti in una stanza
     * @param nomeStanza Nome della stanza
     * @return Numero di lampadine
     * @throws StanzaNonTrovata Se la stanza non esiste, lancia un'eccezione
     */
    public int getNumLampadineStanza(String nomeStanza) throws StanzaNonTrovata {
        Stanza s = this.cercaStanza(nomeStanza);
        return s.getNumLampadine();
    }


    /**
     * Ritorna se la presa è occupata cercandola attraverso il suo nome
     * @param nomeStanza Nome della stanza
     * @param nomePresa Nome della presa
     * @return Stato di occupazione
     * @throws StanzaNonTrovata Se la stanza non esiste, lancia un'eccezione
     * @throws PresaNonTrovata Se non trova la presa, lancia un'eccezione
     */
    public boolean isPresaOccupata (String nomeStanza, String nomePresa) throws StanzaNonTrovata, PresaNonTrovata {
        Stanza s = this.cercaStanza(nomeStanza);
        Presa p = s.getPresa(nomePresa);
        return p.isOccupata();
    }

    /**
     * Conta quante prese ci sono in una stanza di cui si passa il nome
     * @param nomeStanza Nome della stanza
     * @return Numero di prese nella stanza
     * @throws StanzaNonTrovata Se la stanza non esiste, lancia un'eccezione
     */
    public int getNumPreseStanza(String nomeStanza) throws StanzaNonTrovata {
        Stanza s = this.cercaStanza(nomeStanza);
        return  s.getNumPrese();
    }

    /**
     * Disegna tutte le stanze del sistema domotico
     */
    public void disegna() {
        for (var i : stanze) {
            i.disegna();
        }
    }

    /**
     * Ritorna una stringa con tutte le caratteristiche del sistema domotico, che poi verrà stampata nel file CSV.
     * @return Prima riga: la stringa SistemaDomotico, seconda riga: nome delle stanze, dalla terza in poi scrive le stanze
     */
    public String toStringCSVFile(){
        StringBuilder ritorno = new StringBuilder("SistemaDomotico\n");
        ritorno.append(stanze.size()).append("\n");
        for(var i : stanze){
            ritorno.append(i.toStringCSVFile());
        }
        return ritorno.toString();
    }

    /**
     * Converte le caratteristiche della presa in una stringa pensata per stamparla in console
     * @return elenco di tutte le stanze
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (var i : stanze) {
            s.append(i.toString());
        }
        return s.toString();
    }
}
