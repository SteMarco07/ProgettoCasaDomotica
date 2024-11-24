import Eccezioni.*;
import graphics.*;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Scanner;

@SuppressWarnings("SpellCheckingInspection")
public class Main {

    public static void stampaMenu(int scelta_menu){;
        String opzioni;
        switch(scelta_menu){
            case 0:
                opzioni = """
                    ____________________________________ \s
                    OPZIONI SISTEMA DOMOTICO:
                    0: Esci\s
                    1: Seleziona la stanza in cui lavorare\s
                    2: Aggiungi una stanza \s
                    3: Leggi la potenza istantanea \s
                    4: Accendi tutte le lampadine del sistema domotico \s
                    5: Spegni tutte le lampadine del sistema domotico \s
                    6: Stampa il sistema domotico \s
                    7: Salva il sistema domotico \s
                    """;
                break;
            case 1:
                opzioni = """
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

                break;
            case 2: //presa
                opzioni = """
                    ____________________________________ \s
                    \t OPZIONI PRESA:
                     0: Esci\s
                     1: Aggiungi una presa \s
                     2: Rimuovi una presa \s
                     3: Seleziona la presa \s
                    """;
                break;
            case 3:
                opzioni = """
                    ____________________________________ \s
                    \t OPZIONI PRESA:
                     0: Esci\s
                     1: Verifica se è occupata: \s
                     2: Stampa la presa \s
                    """;
                break;
            case 4: //lampadina
                opzioni = """
                    ____________________________________ \s
                    \t OPZIONI LAMPADINA:
                     0: Esci \s
                     1: Aggiungi una lampadina \s
                     2: Rimuovi una lampadina \s
                     3: Seleziona la lampadina \s
                    """;
                break;
            case 5:
                opzioni = """
                    ____________________________________ \s
                    \t OPZIONI LAMPADINA:
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
                break;

            default:
                opzioni = "";
                break;
        }
        System.out.println(opzioni);
    }

    public static void ridisegna(SistemaDomotico sistema,Mappa mappa,Canvas canvas) {
        canvas.Stop();
        mappa.disegna();
        sistema.disegna();
    }


    public static void main(String[] args) {

        Canvas canvas = Canvas.getInstance();

        SistemaDomotico sistemaDomotico = new SistemaDomotico("file\\SistemaDomotico.csv");
        Scanner in = new Scanner(System.in);
        Mappa mappa = new Mappa("assets\\casa.jpg");
        mappa.disegna();

        int scelta = 1;
        while (scelta != 0) {
            ridisegna(sistemaDomotico,mappa,canvas);
            stampaMenu(0);
            scelta = Integer.parseInt(in.nextLine());
            switch (scelta) {
                //TODO: Gestione delle stanze
                case 1:
                    System.out.println("Inserisci il nome della stanza in cui vuoi lavorare: ");
                    String nomeStanza = in.nextLine();
                    try {
                        sistemaDomotico.getStanza(nomeStanza);
                        System.out.println("Entrato nel menu di modifica della stanza " + nomeStanza);
                        while (scelta != -1) {
                            ridisegna(sistemaDomotico,mappa, canvas);
                            stampaMenu(1);
                            scelta = Integer.parseInt(in.nextLine());
                            switch (scelta) {
                                case 0:
                                    scelta = -1;
                                    break;

                                case 1:
                                    //TODO: Gestione delle prese
                                    while (scelta != -2) {
                                        ridisegna(sistemaDomotico,mappa,canvas);
                                        stampaMenu(2);
                                        scelta = Integer.parseInt(in.nextLine());
                                        String nome;
                                        int x,y;
                                        switch (scelta) {
                                            case 0:
                                                scelta =-2;
                                                break;
                                            case 1:
                                                System.out.println("Inserisci il nome della presa: ");
                                                nome = in.nextLine();
                                                System.out.println("Inserisci la coordinata x: ");
                                                x = Integer.parseInt(in.nextLine());
                                                System.out.println("Inserisci la coordinata y: ");
                                                y = Integer.parseInt(in.nextLine());
                                                try{
                                                    sistemaDomotico.aggiungiPresa(nomeStanza,new Presa(nome, x,y, "assets\\presa corrente.jpg"));
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
                                                //TODO: Lavoro su una singola presa
                                                if (sistemaDomotico.getNumPreseStanza(nomeStanza) != 0) {
                                                    System.out.println("Inserisci il nome della presa in cui vuoi lavorare: ");
                                                    nome = in.nextLine();
                                                    try {
                                                        sistemaDomotico.getStanza(nomeStanza).getPresa(nome);
                                                        stampaMenu(3);
                                                        scelta = Integer.parseInt(in.nextLine());
                                                        while (scelta != -1) {
                                                            ridisegna(sistemaDomotico,mappa,canvas);
                                                            switch (scelta) {
                                                                case 0:
                                                                    scelta = -1;
                                                                    break;
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
                                                    }
                                                } else {
                                                    System.out.println("La stanza " + nomeStanza + " non contiene prese su cui lavorare");
                                                }
                                                break;
                                        }
                                    }

                                    break;
                                case 2:
                                    //TODO: Gestione delle lampadine
                                    String nomeLampadina, nomePresa;
                                    float potenza;
                                    int R,G,B, valore;
                                    while (scelta != -2) {
                                        ridisegna(sistemaDomotico,mappa,canvas);
                                        stampaMenu(4);
                                        scelta = Integer.parseInt(in.nextLine());
                                        switch (scelta) {
                                            case 0:
                                                scelta =-2;
                                                break;
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
                                                //TODO: Lavoro su una singola lampadina
                                                if (sistemaDomotico.getNumLampadineStanza(nomeStanza) != 0) {
                                                    System.out.println("Inserisci il nome della presa in cui vuoi lavorare: ");
                                                    nomeLampadina = in.nextLine();
                                                    try {
                                                        sistemaDomotico.getLampadina(nomeStanza, nomeLampadina);
                                                        while (scelta != -1) {
                                                            ridisegna(sistemaDomotico, mappa, canvas);
                                                            stampaMenu(5);
                                                            scelta = Integer.parseInt(in.nextLine());
                                                            switch (scelta) {
                                                                case 0:
                                                                    scelta = -1;
                                                                    break;
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
                                                                    R = Integer.parseInt(in.nextLine());
                                                                    System.out.println("Inserisci il valore del canale verde (0-255): ");
                                                                    G = Integer.parseInt(in.nextLine());
                                                                    System.out.println("Inserisci il valore del canale blu (0-255): ");
                                                                    B = Integer.parseInt(in.nextLine());
                                                                    sistemaDomotico.modificaColoreLampadina(nomeStanza,nomeLampadina, new Color(R,G,B));
                                                                    break;
                                                                case 5:
                                                                    System.out.println("La potenza istantanea della lampadina " + nomeLampadina + " vale: " + sistemaDomotico.getStanza(nomeStanza).getLampadina(nomeLampadina).getPotenzaIstantanea());
                                                                    break;
                                                                case 6:
                                                                    System.out.println("Inserisci il valore a cui impostare la luminosità: ");
                                                                    valore = Integer.parseInt(in.nextLine());
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
                                                    }
                                                } else {
                                                    System.out.println("Non sono presenti lampadine su cui lavorare");
                                                }
                                                break;
                                            default:
                                                System.out.println("Opzione non valida");
                                                break;
                                        }

                                    }
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
                    }

                    break;
                case 2:
                    System.out.println("Inserisci il mode della stanza che vuoi aggiungere: ");
                    nomeStanza = in.nextLine();
                    try {
                        sistemaDomotico.aggiungiStanza(new Stanza(nomeStanza));
                        System.out.println("La stanza " + nomeStanza + " è stata creata con successo");
                    } catch (StanzaEsistente e) {
                        System.out.println("Esiste già una stanza con lo stesso nome all'interno del sistema domotico");
                    }
                    break;
                case 3:
                    System.out.println("La potenza istantanea del sistema vale: " + sistemaDomotico.getPotenzaSistema());
                    break;
                case 4:
                    sistemaDomotico.accendiTutte();
                    System.out.println("Tutte le lampadine del sistema sono state accese");
                    break;
                case 5:
                    sistemaDomotico.spegniTutte();
                    System.out.println("Tutte le lampadine del sistema sono state spente");
                    break;
                case 6:
                    System.out.println(sistemaDomotico);
                    break;
                case 7:
                    try {
                        sistemaDomotico.salvaInFile("file\\SistemaDomotico.csv");
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
            sistemaDomotico.salvaInFile("file\\SistemaDomotico.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /*
      tomi, meisei, chikara
      kono yo no subete o te ni ireta otoko
      kaizoku ou, Gold Roger
      kare no shi ni kiwa ni
      hanatte hito koto wa
      hitobito o umi e kari tatte ta
      ore no zaihou ka?
      houshikerya kurete yaru
      sagase!!
      kono yo no subete o soko ni oitekita
      otokotachi wa
      "Grand Line" wo mezashi
      yume o oisuzukeru
      yo wa masa ni
      Dai Kaizoku Jidai ni!
     */
}