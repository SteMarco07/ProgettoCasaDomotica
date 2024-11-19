import graphics.Canvas;
import graphics.Picture;
import java.util.Scanner;

    public class Main {

        public static void stampaMenu(){
            String opzioni;
            opzioni = """
                    ____________________________________ \s
                     0: Esci\s
                     1: Seleziona la stanza \s
                     2: Opzioni prese \s
                     3: Opzioni lampadine \s
                    """;
            System.out.println(opzioni);
        }

        public static void stampaSottoMenu(int scelta_menu){
            String opzioni;
            switch(scelta_menu){
                case 2: //presa
                    opzioni = """
                    ____________________________________ \s
                    \t OPZIONI PRESA:
                     0: Esci\s
                     1: Seleziona la presa \s
                     2: Aggiungi una presa \s
                     3: Rimuovi una presa \s
                    """;
                break;
                case 3: //lampadina
                    opzioni = """
                    ____________________________________ \s
                    \t OPZIONI LAMPADINA:
                     0: Esci \s
                     1: Seleziona la lampadina \s
                     2: Aggiungi una lampadina \s
                     3: Rimuovi una lampadina \s
                     4: Accendi la lampadina \s
                     5: Spegni la lampadina \s
                     6: Modifica il colore \s
                    """;
                break;

                default:
                    opzioni = "";
                break;
            }
            System.out.println(opzioni);
        }

        public static void main(String[] args) {

       SistemaDomotico sistemaDomotico = new SistemaDomotico();
       sistemaDomotico.aggiungiStanza(new Stanza("Stanza 1"));
       sistemaDomotico.aggiungiPresa("Stanza 1", new Presa("Presa 1",1,1));
       sistemaDomotico.aggiungiLampadina("Stanza 1", "Presa 1", new Lampadina("Lampadina 1", 1));
       sistemaDomotico.aggiungiPresa("Stanza 1", new Presa("Presa 2",2,2));
       sistemaDomotico.getLampadinaNome("Stanza 1", "Lampadina 1").setLum(1);
       sistemaDomotico.getLampadinaNome("Stanza 1", "Lampadina 1").setColore("Arancione");
       sistemaDomotico.aggiungiLampadina("Stanza 1", "Presa 2", new Lampadina("Lampadina 2", 2));
            System.out.println(sistemaDomotico.toString());
        }
    }