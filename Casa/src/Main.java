import Eccezioni.*;
import graphics.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

/**
 * Programma che gestisce un sistema domotico, composto di varie stanze e lampadine
 * @author Stellino Marco
 * @author Robolini Paolo
 * @version 1.0
 */

public class Main {
    /**
     * Stampa i diversi sottomenù per la gestione del sistema domotico
     * @param scelta_menu Valore edl sottomenù da stampare
     *
     */
    public static void stampaMenu(int scelta_menu){
        String opzioni = switch (scelta_menu) {
            case 0 -> """
                    ____________________________________ \s
                    OPZIONI SISTEMA DOMOTICO:
                    0: Esci\s
                    1: Seleziona la stanza in cui lavorare\s
                    2: Aggiungi una stanza \s
                    3: Leggi la potenza istantanea \s
                    4: Accendi tutte le lampadine del sistema domotico \s
                    5: Spegni tutte le lampadine del sistema domotico \s
                    6: Stampa il sistema domotico \s
                    7: Esegui un backup del sistema domotico in un file CSV \s
                    """;
            case 1 -> """
                    ____________________________________ \s
                    OPZIONI STANZA:
                    0: Esci \s
                    1: Opzioni prese \s
                    2: Opzioni lampadine \s
                    3: Accendi tutte le lampadine della stanza
                    4: Spegni tutte le lampadine della stanza
                    5: Leggi la potenza istantanea \s
                    6: Stampa la stanza \s
                    """;
            case 2 -> //presa
                    """
                            ____________________________________ \s
                            \t OPZIONI PRESA:
                             0: Esci\s
                             1: Aggiungi una presa \s
                             2: Rimuovi una presa \s
                             3: Seleziona la presa \s
                            """;
            case 3 -> """
                    ____________________________________ \s
                    \t OPZIONI SINGOLA PRESA:
                     0: Esci\s
                     1: Verifica se è occupata: \s
                     2: Stampa la presa \s
                    """;
            case 4 -> //lampadina
                    """
                            ____________________________________ \s
                            \t OPZIONI LAMPADINA:
                             0: Esci \s
                             1: Aggiungi una lampadina \s
                             2: Rimuovi una lampadina \s
                             3: Seleziona la lampadina \s
                            """;
            case 5 -> """
                    ____________________________________ \s
                    \t OPZIONI SINGOLA LAMPADINA:
                     0: Esci \s
                     1: Accendi la lampadina \s
                     2: Spegni la lampadina \s
                     3: Verifica se è accesa \s
                     4: Modifica il colore \s
                     5: Leggi potenza istantanea \s
                     6: Imposta la luminosità \s
                     7: Aumenta la luminosità \s
                     8: Diminuisci la luminosità \s
                     9: Stampa la lampadina \s
                    """;
            default -> "";
        };
        System.out.println(opzioni);
    }

    /**
     * Gestisce una singola lampadina del sistema domotico
     * @param sistemaDomotico Sistema domotico in cui lavorare
     * @param in Scanner
     * @param nomeStanza nome della stanza in cui è presente la lampadina
     * @return Valore per lo switch case della gestione delle lampadine
     */
    public static int gestioneSingolaLampadina(SistemaDomotico sistemaDomotico, Scanner in, String nomeStanza) {
        if (sistemaDomotico.getNumLampadineStanza(nomeStanza) != 0) {
            System.out.println("Inserisci il nome della presa in cui vuoi lavorare: ");
            String nomeLampadina = in.nextLine();
            try {
                sistemaDomotico.getLampadina(nomeStanza, nomeLampadina);
                while (true) {
                    sistemaDomotico.disegna();
                    stampaMenu(5);
                    int scelta = Integer.parseInt(in.nextLine());
                    switch (scelta) {
                        case 0:
                            return 0;
                        case 1:
                            sistemaDomotico.accendiLampadina(nomeStanza, nomeLampadina);
                            System.out.println("La lampadina " + nomeLampadina + " è stata accesa");
                            break;
                        case 2:
                            sistemaDomotico.spegniLampadina(nomeStanza,nomeLampadina);
                            System.out.println("La lampadina " + nomeLampadina + " è stata spenta");
                            break;
                        case 3:
                            if (sistemaDomotico.isLampadinaAccesa(nomeStanza, nomeLampadina)) {
                                System.out.println("La lampadina " + nomeLampadina + " è accesa");
                            } else {
                                System.out.println("La lampadina " + nomeLampadina + " è spenta");
                            }
                            break;
                        case 4:
                            System.out.println("Inserisci il valore del canale rosso (0-255): ");
                            int R = Integer.parseInt(in.nextLine());
                            System.out.println("Inserisci il valore del canale verde (0-255): ");
                            int G = Integer.parseInt(in.nextLine());
                            System.out.println("Inserisci il valore del canale blu (0-255): ");
                            int B = Integer.parseInt(in.nextLine());
                            sistemaDomotico.modificaColoreLampadina(nomeStanza,nomeLampadina, new Color(R,G,B));
                            break;
                        case 5:
                            System.out.println("La potenza istantanea della lampadina " + nomeLampadina + " vale: " + sistemaDomotico.getStanza(nomeStanza).getLampadina(nomeLampadina).getPotenzaIstantanea());
                            break;
                        case 6:
                            System.out.println("Inserisci il valore a cui impostare la luminosità: ");
                            int valore = Integer.parseInt(in.nextLine());
                            sistemaDomotico.modificaLumLampadina(nomeStanza,nomeLampadina,valore);
                            break;
                        case 7:
                            sistemaDomotico.aumentaLumLampadina(nomeStanza, nomeLampadina);
                            System.out.println("La luminosità della lampadina " + nomeLampadina + " è stata incrementata");
                            break;
                        case 8:
                            sistemaDomotico.diminuisciLumLampadina(nomeStanza, nomeLampadina);
                            System.out.println("La luminosità della lampadina " + nomeLampadina + " è stata decrementata");
                            break;
                        case 9:
                            System.out.println(sistemaDomotico.getLampadina(nomeStanza, nomeLampadina));
                            break;
                    }
                }

            } catch (LampadinaNonTrovata e) {
                System.out.println("La lampadina " + nomeLampadina + " non esiste o non è stata trovata");
                return -1;
            }
        } else {
            System.out.println("Non sono presenti lampadine su cui lavorare");
            return -1;
        }
    }

    /**
     * Gestisce le lampadine del sistema domotico
     * @param sistemaDomotico Sistema domotico in cui lavorare
     * @param in Scanner
     * @param nomeStanza Nome della stanza in cui si desidera lavorare
     * @return Valore per lo switch case della gestione della stanza
     */
    public static int gestioneLampadine(SistemaDomotico sistemaDomotico, Scanner in, String nomeStanza) {
        String nomeLampadina, nomePresa;
        float potenza;
        while (true) {
            sistemaDomotico.disegna();
            stampaMenu(4);
            int scelta = Integer.parseInt(in.nextLine());
            switch (scelta) {
                case 0:
                    return -1;
                case 1:
                    System.out.println("Inserisci il nome della presa a cui collegare la lampadina: ");
                    nomePresa = in.nextLine();
                    System.out.println("Inserisci il nome della lampadina che vuoi creare: ");
                    nomeLampadina = in.nextLine();
                    System.out.println("Inserisci la potenza della lampadina: ");
                    potenza = Float.parseFloat(in.nextLine());
                    try {
                        sistemaDomotico.aggiungiLampadina(nomeStanza, nomePresa, new Lampadina(nomeLampadina, potenza));
                        System.out.println("La lampadina " + nomeLampadina + " è stata creata con successo");
                    } catch (LampadinaEsistente e) {
                        System.out.println("Esiste già una lampadina con questo nome all'interno della stanza");
                    } catch (PresaOccupata e) {
                        System.out.println("La presa " + nomePresa + " ha già una lampadina associata");
                    } catch (PresaNonTrovata e) {
                        System.out.println("La presa " + nomePresa + " non esiste");
                    }
                    break;
                case 2:
                    System.out.println("Inserisci il nome della lampadina che vuoi eliminare: ");
                    nomeLampadina = in.nextLine();
                    try {
                        sistemaDomotico.rimuoviLampadina(nomeStanza, nomeLampadina);
                        System.out.println("La lampadina " + nomeLampadina + " è stata eliminata con successo");
                    } catch (LampadinaNonTrovata e) {
                        System.out.println("Non è stato possibile eliminare la lampadina " + nomeLampadina + " perché non esiste o il nome non è stato scritto correttamente");
                    }
                    break;
                case 3:
                     gestioneSingolaLampadina(sistemaDomotico,in,nomeStanza);
                    break;
                default:
                    System.out.println("Opzione non valida");
                    break;
            }

        }
    }

    /**
     * Gestisce una singola presa del sistema domotico
     * @param sistemaDomotico Sistema domotico su cui lavorare
     * @param in Scanner
     * @param nomeStanza Nome della stanza in cui è presente la presa
     * @return Valore per lo switch case della gestione delle prese
     */
    public static int gestioneSingolaPresa (SistemaDomotico sistemaDomotico, Scanner in, String nomeStanza) {
        if (sistemaDomotico.getNumPreseStanza(nomeStanza) != 0) {
            System.out.println("Inserisci il nome della presa in cui vuoi lavorare: ");
            String nome = in.nextLine();
            try {
                sistemaDomotico.getStanza(nomeStanza).getPresa(nome);
                while (true) {
                    sistemaDomotico.disegna();
                    stampaMenu(3);
                    int scelta = Integer.parseInt(in.nextLine());
                    switch (scelta) {
                        case 0:
                            return -1;
                        case 1:
                            if (sistemaDomotico.isPresaOccupata(nomeStanza, nome)) {
                                System.out.println("La presa è occupata");
                            } else {
                                System.out.println("La presa non è occupata");
                            }
                            break;
                        case 2:
                            System.out.println(sistemaDomotico.getPresa(nomeStanza, nome));
                            break;
                        default:
                            System.out.println("Opzione non valida");
                            break;
                    }
                }
            } catch (PresaNonTrovata e){
                System.out.println("La presa " + nome + " non esiste o non è stata trovata");
                return -1;
            }
        } else {
            System.out.println("La stanza " + nomeStanza + " non contiene prese su cui lavorare");
            return -1;
        }
    }
    /**
     * Gestisce le prese del sistema domotico
     * @param sistemaDomotico Sistema domotico in cui lavorare
     * @param in Scanner
     * @param nomeStanza Nome della stanza
     * @return Valore per lo siwtch case della gestione della stanza
     */
    public static int gestionePrese (SistemaDomotico sistemaDomotico, Scanner in, String nomeStanza) {
        while (true) {
            sistemaDomotico.disegna();
            stampaMenu(2);
            int scelta = Integer.parseInt(in.nextLine());
            String nome;
            int x,y;
            switch (scelta) {
                case 0:
                    return -1;
                case 1:
                    System.out.println("Inserisci il nome della presa: ");
                    nome = in.nextLine();
                    System.out.println("Inserisci la coordinata x: ");
                    x = Integer.parseInt(in.nextLine());
                    System.out.println("Inserisci la coordinata y: ");
                    y = Integer.parseInt(in.nextLine());
                    try{
                        sistemaDomotico.aggiungiPresa(nomeStanza,new Presa(nome, x,y));
                        System.out.println("La presa " + nome + " è stata creata con successo");
                    } catch (PresaEsistente e) {
                        System.out.println("Esiste già una presa con questo nome all'interno della stanza");
                    }
                    break;
                case 2:
                    System.out.println("Inserisci il nome della presa: ");
                    nome = in.nextLine();
                    try {
                        sistemaDomotico.rimuoviPresa(nomeStanza, nome);
                        System.out.println("La presa " + nome + " è stata eliminata con successo");
                    } catch (PresaNonTrovata e) {
                        System.out.println("Non è stato possibile eliminare la presa " + nome + " perché non esiste o il nome non è stato scritto correttamente");
                    }
                    break;
                case 3:
                    gestioneSingolaPresa(sistemaDomotico,in,nomeStanza);
                    break;
                default:
                    System.out.println("Opzione non valida");
                    break;
            }
        }
    }

    /**
     * Gestisce una stanza nel sistema domotico
     * @param sistemaDomotico Sistema domotico in cui lavorare
     * @param in Scanner
     * @return Valore per lo siwtch case della gestione delle stanze
     */
    public static  int gestioneStanza (SistemaDomotico sistemaDomotico, Scanner in) {
        System.out.println("Inserisci il nome della stanza in cui vuoi lavorare: ");
        String nomeStanza = in.nextLine();
        int scelta;
        try {
            sistemaDomotico.getStanza(nomeStanza);
            System.out.println("Entrato nel menu di modifica della stanza " + nomeStanza);
            while (true) {
                sistemaDomotico.disegna();
                stampaMenu(1);
                scelta = Integer.parseInt(in.nextLine());
                switch (scelta) {
                    case 0:
                        return -1;

                    case 1:
                        gestionePrese(sistemaDomotico,in,nomeStanza);
                        break;

                    case 2:
                        gestioneLampadine(sistemaDomotico,in,nomeStanza);
                        break;
                    case 5:
                        System.out.println("La potenza istantanea della stanza " + nomeStanza + " vale: " + sistemaDomotico.getStanza(nomeStanza).getPotenzaSistema());
                        break;
                    case 3:
                        sistemaDomotico.accendiStanza(nomeStanza);
                        System.out.println("Tutte le lampadine della stanza " + nomeStanza + " sono state accese");
                        break;
                    case 4:
                        sistemaDomotico.spegniStanza(nomeStanza);
                        System.out.println("Tutte le lampadine della stanza " + nomeStanza + " sono state accese");
                        break;
                    case 6:
                        System.out.println(sistemaDomotico.getStanza(nomeStanza));
                        break;
                    default:
                        System.out.println("Opzione non valida");
                        break;
                }

            }
        } catch (StanzaNonTrovata e) {
            System.out.println("La stanza " + nomeStanza + " non esiste o non è stata trovata");
            return -1;
        }
    }
    public static SistemaDomotico deserializza(String nomeFile) throws IOException, ClassNotFoundException {
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(nomeFile))) {
            return (SistemaDomotico)is.readObject();
        }
    }

    public static void main(String[] args) throws IOException {

        Canvas canvas = Canvas.getInstance();

        SistemaDomotico sistemaDomotico = null;
        Scanner in = new Scanner(System.in);
        Mappa mappa = new Mappa("assets\\casa.jpg");
        mappa.disegna();

        sistemaDomotico = new SistemaDomotico("file\\SistemaDomotico.bin","file\\SistemaDomotico.csv");



        int scelta = 1;
        while (scelta != 0) {
            sistemaDomotico.disegna();
            stampaMenu(0);
            scelta = Integer.parseInt(in.nextLine());
            switch (scelta) {
                //Gestisce una stanza
                case 1:
                    scelta = gestioneStanza(sistemaDomotico,in);
                    break;

                //Aggiunge una stanza
                case 2:
                    System.out.println("Inserisci il mode della stanza che vuoi aggiungere: ");
                    String nomeStanza = in.nextLine();
                    try {
                        sistemaDomotico.aggiungiStanza(new Stanza(nomeStanza));
                        System.out.println("La stanza " + nomeStanza + " è stata creata con successo");
                    } catch (StanzaEsistente e) {
                        System.out.println("Esiste già una stanza con lo stesso nome all'interno del sistema domotico");
                    }
                break;

                //Stampa la potenza istantanea del sistema
                case 3:
                    System.out.println("La potenza istantanea del sistema vale: " + sistemaDomotico.getPotenzaSistema());
                break;

                //Accende tutte le lampadine
                case 4:
                    sistemaDomotico.accendiTutte();
                    System.out.println("Tutte le lampadine del sistema sono state accese");
                break;

                //Spegne tutte le lampadine
                case 5:
                    sistemaDomotico.spegniTutte();
                    System.out.println("Tutte le lampadine del sistema sono state spente");
                break;


                case 6:
                    System.out.println(sistemaDomotico);
                    break;
                case 7:
                    try {
                        sistemaDomotico.salvaInFileCSV("file\\SistemaDomotico.csv");
                    } catch (IOException e) {
                        System.out.println("Non è stato possibile salvare il sistema domotico");
                    }
                    break;
                default:
                    if (scelta != 0) {
                        System.out.println("Opzione non valida");
                    }
                    break;
            }
        }
        canvas.getInstance().Stop();
        try {
            sistemaDomotico.salvaInFileCSV("file\\SistemaDomotico.csv");
            sistemaDomotico.serializza("file\\SistemaDomotico.bin");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}