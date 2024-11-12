
import graphics.Canvas;

import java.util.Scanner;

    public class Main {
        public static void stampaMenu(){
            String opzioni;
            opzioni = """
                    ____________________________________ \s
                     0: Esci\s
                     1: Aggiungi una nuova lampadina \s
                     2: Rimuovi una lampadina \s
                     3: Modifica il colore di una lampadina \s
                     4: Modifica la luminosita' di una lampadina \s
                     5: Ottieni la potenza istantanea del sistema \s
                     6: Accendi una lampadina \s
                     7: Spegni una lampadina \s
                     8: Accendi tutte le lampadine \s
                     9: Spegni tutte le lampadine \s
                    10: Stampa il nome di ogni lampadina \s
                   ____________________________________ \s
                    """;
            System.out.println(opzioni);
        }

        public static void main(String[] args) {

            String nomeTemp, coloreTemp;
            int lumTemp;
            int poTemp;

            int scelta = -1;
            Scanner in = new Scanner(System.in);

            SistemaDomotico sistemaDomotico = new SistemaDomotico();

            while(scelta != 0) {
                switch (scelta) {
                    default -> {
                        stampaMenu();
                        System.out.println("Inserisci un numero:");
                        scelta = in.nextInt();
                    }
                    case 1 -> {
                        System.out.println("Inserire il nome della lampadina:");
                        nomeTemp = in.next();
                        System.out.println("Inserire la potenza della lampadina:");
                        poTemp = in.nextInt();
                        sistemaDomotico.aggiungi(new Lampadina(nomeTemp, poTemp));
                        scelta = -1;
                    }
                    case 2 -> {
                        System.out.println("Inserire il nome della lampadina:");
                        nomeTemp = in.next();
                        sistemaDomotico.rimuovi(nomeTemp);
                        scelta = -1;
                    }
                    case 3 -> {
                        System.out.println("Inserire il nome della lampadina:");
                        nomeTemp = in.next();
                        System.out.println("Inserire il nuovo colore della lampadina:");
                        coloreTemp = in.next();
                        sistemaDomotico.modificaColore(nomeTemp, coloreTemp);
                        scelta = -1;
                    }
                    case 4 -> {
                        System.out.println("Inserire il nome della lampadina:");
                        nomeTemp = in.next();
                        System.out.println("Inserire la luminosita della lampadina:");
                        lumTemp = in.nextInt();
                        sistemaDomotico.modificaLum(nomeTemp, lumTemp);
                        scelta = -1;
                    }
                    case 5 -> {
                        System.out.println("Potenza totale del sistema: " + sistemaDomotico.getPotenzaSistema());
                        scelta = -1;
                    }
                    case 6 -> {
                        System.out.println("Inserire il nome della lampadina:");
                        nomeTemp = in.next();
                        sistemaDomotico.accendiLampadina(nomeTemp);
                        System.out.println("Lampadina accesa.");
                        scelta = -1;
                    }
                    case 7 -> {
                        System.out.println("Inserire il nome della lampadina:");
                        nomeTemp = in.next();
                        sistemaDomotico.spegniLampadina(nomeTemp);
                        System.out.println("Lampadina spenta.");
                        scelta = -1;
                    }
                    case 8 -> {
                        System.out.println("Accese tutte le lampadine.");
                        sistemaDomotico.accendiTutte();
                    }
                    case 9 -> {
                        System.out.println("Spente tutte le lampadine.");
                        sistemaDomotico.spegniTutte();
                    }
                    case 10 -> {
                        sistemaDomotico.stampaSistema();
                        scelta = -1;
                    }
                }
            }
            Canvas.getInstance().Stop();
        }
    }