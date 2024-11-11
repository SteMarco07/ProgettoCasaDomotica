import java.util.ArrayList;

/**
 * Classe che rappresenta una stanza, con un numero indefinito di lampadine.
 */
public class Stanza {
    private final String nome;
    private ArrayList<Lampadina> lampadine;

    /**
     * Costruttore che inizializza la stanza con un nome.
     * @param nome nome_stanza
     */
    public Stanza (String nome){
        this.nome = nome;
        this.lampadine = new ArrayList<Lampadina>();
    }

    /**
     * Cerca la posizione di una lampadina dato il nome. Se non la trova, restituisce -1.
     * @param nome Nome della lampadina (String)
     * @return Posizione lampadina (int)
     */
    public int cercaLampadina(String nome){

        for(int i = 0; i < lampadine.size(); ++i){
            if(lampadine.get(i).getNome().equals(nome)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Aggiunge una nuova lampadina al sistema.
     * @param l Lampadina (Lampadina)
     */
    public void aggiungi(Lampadina l){
        lampadine.add(l);
    }

    /**
     * Rimuove una lampadina dato il nome.
     * @param nome Nome (String)
     * @return true se la lampadina è stata rimossa, false se non è stata rimossa
     */
    public boolean rimuovi(String nome){
        int pos = cercaLampadina(nome);
        if(pos >= 0){
            lampadine.remove(pos);
            return true;
        } else {
            return false;
        }
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
        lampadine.get(this.cercaLampadina(nome)).spegni();
    }

    /**
     * Stampa tutto il sistema e le caratteristiche di ciascuna lampadina.
     */
    public void stampaSistema(){
        for (var i : lampadine){
            System.out.println(i);
        }
    }

    public String getNome() {
        return nome;
    }
}
