import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class GameController {
    private MazeManager maze;
    private TurnQueue turnQueue;
    private Agent[] agents;
    private int maxTurns;
    private int turnCount;
    private PrintWriter log;
    private Integer winnerId;
    private static final Scanner sc = new Scanner(System.in);

    /*
      width Labirent genişliği
      height Labirent yüksekliği
      numAgents Ajan sayısı
      maxTurns Maksimum tur
      wallFreq Duvar
      trapFreq Tuzak
      powerFreq Power-up
      logFile yapılan işlemleri kaydeden dosya adı "txt olarak ist"
     */
    public GameController(int width, int height, int numAgents, int maxTurns, double wallFreq, double trapFreq, double powerFreq, String logFile) throws IOException {
        agents = new Agent[numAgents];
        agents[0] = new Agent(1, 1, 1);
        if (numAgents > 1) agents[1] = new Agent(2, width - 2, 1);

        this.maze = new MazeManager(width, height, agents,
                wallFreq, trapFreq, powerFreq);
        this.turnQueue = new TurnQueue(agents);
        this.maxTurns = maxTurns;
        this.turnCount = 0;
        this.winnerId = null;
        this.log = new PrintWriter(new FileWriter(logFile), true);
    }

    public void runSimulation() {
        maze.generateMaze();
        log("=== Simulation started ===");
        maze.printMazeSnapshot();

        while (turnCount < maxTurns && !turnQueue.allAgentsFinished()) {
            Agent a = turnQueue.nextAgent();
            processAgentAction(a);
            log(String.format("Round %d, Agent %d", turnCount, a.getId()));
            List<String> last5 = a.getLastMoves(5);
            log("Last 5 moves: " + last5);
            maze.printMazeSnapshot();
            turnCount++;
            if (turnCount % 5 == 0) {
                maze.rotateCorridor(turnCount / 5);
            }
        }

        printFinalStatistics();
        log.close();
    }

    private void processAgentAction(Agent a) {
        System.out.println("Agent " + a.getId() + " için yön girin (UP/DOWN/LEFT/RIGHT):");
        String dir = sc.next().trim().toUpperCase();

        int oldX = a.getCurrentX(), oldY = a.getCurrentY();
        int newX = oldX, newY = oldY;
        switch (dir) {
            case "UP":    newY--; break;
            case "DOWN":  newY++; break;
            case "LEFT":  newX--; break;
            case "RIGHT": newX++; break;
            default:
                System.out.println("Geçersiz komut. Tekrar deneyin.");
                return;
        }

        if (!maze.isValidMove(newX, newY)) {
            System.out.println("Oraya gidemezsiniz. Tekrar deneyin.");
            return;
        }

        a.move(dir);
        maze.updateAgentLocation(a, oldX, oldY);
        MazeTile t = maze.getTile(newX, newY);

        if (t.getType() == 'T') {
            log("Agent " + a.getId() + " hit a trap!");
            maze.updateAgentLocation(a, newX, newY);
            a.backtrack();
            maze.updateAgentLocation(a, newX, newY);
        } else if (t.getType() == 'P') {
            log("Agent " + a.getId() + " collected a power-up.");
            a.applyPowerUp();
        } else if (t.getType() == 'G') {
            log("Agent " + a.getId() + " reached the goal!");
            a.setReachedGoal(true);
            if (winnerId == null) winnerId = a.getId();
        }
    }

    private void printFinalStatistics() {
        String endMsg = "Simulation ended in " + turnCount + " turns.";
        System.out.println(endMsg);
        log(endMsg);

        double sumMoves = 0;
        int maxDepth   = 0;
        for (Agent a : agents) {
            sumMoves  += a.getTotalMoves();
            maxDepth   = Math.max(maxDepth, a.getMaxStackDepth());
        }
        double avgMoves = sumMoves / agents.length;
        String stats = String.format(
                "Avg moves: %.2f, Max stack depth: %d, Winner: %s",
                avgMoves, maxDepth,
                (winnerId == null ? "None" : winnerId)
        );
        System.out.println(stats);
        log(stats);
    }

    private void log(String msg) {
        System.out.println(msg);
        log.println(msg);
    }
}
