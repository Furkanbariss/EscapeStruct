import java.io.IOException;
import java.util.Scanner;

/**
 * Main: Uygulamanın başlangıç noktası, önce kapak sayfası, sonra parametre girişi ve oyuna geçiş.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        // --------- KAPAK SAYFASI ---------
        System.out.println("*=====================================================*");
        System.out.println("|                                                     |");
        System.out.println("|     (⌐■_■)  ESCAPE STRUCT SIMULATOR v1.0            |");
        System.out.println("|                                                     |");
        System.out.println("*=====================================================*");
        System.out.println("|                                                     |");
        System.out.println("|   Geliştiren: Furkan Barış Sönmezışık (220401072)   |");
        System.out.println("|                                                     |");
        System.out.println("*=====================================================*");

        System.out.println();
        System.out.print("Başlamak için ENTER tuşuna basın...");
        sc.nextLine();  // Enter bekle
        System.out.println("\nHoş geldiniz! Oyuna geçiliyor...\n");

        // --------- PARAMETRELERİ OKU ---------
        System.out.print("Maze Width (recomended 8 or more): ");
        int width = sc.nextInt();
        System.out.print("Maze Height (recomended 8 or more) : ");
        int height = sc.nextInt();
        System.out.println("       (⌐■_■)                    (⌐■_■)   (⌐■_■)");
        System.out.println("        /|\\                       /|\\      /|\\");
        System.out.println("       / | \\                     / | \\    / | \\");
        System.out.println("        / \\                       / \\      / \\");
        System.out.println("       /   \\                     /   \\    /   \\");
        System.out.println("       1 Player                      2 Player ");
        System.out.print("Agent count (1-2): ");
        int agents = sc.nextInt();
        System.out.print("Max turns (maksimum tur): ");
        int maxTurns = sc.nextInt();
        System.out.print("Trap frequency in the maze (%) (recomended 10): ");
        double trapF = sc.nextDouble();
        System.out.print("Power-up frequency in the maze (%) (recomended 10): ");
        double powerF = sc.nextDouble();
        System.out.print("Wall frequency (%) (recomended 30): ");
        double wallF = sc.nextDouble();
        System.out.print("Log file name (ex: \"log.txt\"): ");
        String logFile = sc.next();

        System.out.println("\nSimülasyon hazırlanıyor...\n");
        GameController gm = new GameController(
                width, height, agents, maxTurns,
                wallF, trapF, powerF,
                logFile
        );
        gm.runSimulation();

        sc.close();
    }
}
