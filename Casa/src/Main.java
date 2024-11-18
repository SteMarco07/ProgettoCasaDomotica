
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
                     0: Esci\s
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

        Picture picture = new Picture("../assets/casa.jpg");

        picture.draw();

        String a;

        Scanner in = new Scanner(System.in);

        a = in.next();



        Canvas.getInstance().Stop();
        }
    }