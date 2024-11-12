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

    /**
     * Dato il nome di una presa, restutisce la sua posizione, se non la trova dà -1
     * @param nomePresa Nome della presa (String)
     * @return Ritorna la presa se è stata trovata, altrimenti ritorna null
     */
    public Presa cercaPresa (String nomePresa){
        for(var i : prese){
            if(i.getNome().equals(nomePresa)){
                return i;
            }
        }
        return null;
    }

    /**
     * Cerca la posizione di una lampadina dato il nome. Se non la trova, restituisce -1.
     * @param nome Nome della lampadina (String)
     * @return Posizione lampadina (int)
     */
    public int cercaLampadina(String nome){

        for(int i = 0; i < prese.size(); ++i){
            if(prese.get(i).getLampadina().getNome().equals(nome)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Aggiunge una nuova lampadina al sistema.
     * @param l Lampadina (Lampadina)
     */
    public void aggiungi(Presa l){
        prese.add(l);
    }

    /**
     * Rimuove una lampadina dato il nome.
     * @param nome Nome (String)
     * @return true se la lampadina è stata rimossa, false se non è stata rimossa
     */
    public boolean rimuovi(String nome){
        int pos = cercaLampadina(nome);
        if(pos >= 0){
            prese.remove(pos);
            return true;
        } else {
            return false;
        }
    }

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
     * @param nome Nome (String)
     * @param colore Colore (String)
     */
    public void modificaColore(String nome, String colore){
        int pos = this.cercaLampadina(nome);
        if(pos >= 0){
            this.prese.get(pos).getLampadina().setColore(colore);
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
            this.prese.get(pos).getLampadina().setLum(lum);
        }
    }

    /**
     * Accende una lampadina di cui si passa il nome.
     * @param nome Nome (String)
     */
    public void accendiLampadina(String nome){
        int pos = this.cercaLampadina(nome);
        if(pos >= 0){
            prese.get(pos).getLampadina().accendi();
        }
    }
    /**
     * Spegne una lampadina di cui si passa il nome.
     * @param nome Nome (String)
     */
    public void spegniLampadina(String nome){
        prese.get(this.cercaLampadina(nome)).getLampadina().spegni();
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
