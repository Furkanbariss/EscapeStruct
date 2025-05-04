import java.util.Random;

public class MazeManager {
    private MazeTile[][] grid;
    private int width;
    private int height;
    private Agent[] agents;
    private CircularCorridor corridor;
    private double trapFreq;
    private double wallFreq;
    private double powerFreq;


    public MazeManager(int width, int height, Agent[] agents, double wallFreqPercent, double trapFreqPercent, double powerFreqPercent) {
        this.width     = width;  // Labirent genişliği
        this.height    = height; // Labirent yüksekliği
        this.agents    = agents; // Ajan dizisi (start konumları içinde tanımlı)
        // yüzdeleri kullanıcan input olarak alacağım dokunmayın beyler!
        this.wallFreq  = wallFreqPercent  / 100.0; // İç duvar
        this.trapFreq  = trapFreqPercent  / 100.0; // Tuzak
        this.powerFreq = powerFreqPercent / 100.0; // power
        this.grid      = new MazeTile[height][width];
        this.corridor  = new CircularCorridor(width);
    }

    /** Rastgele labirent üretir; start→goal arası yol yoksa yeniden dener. Powered by ChatCPT :) */
    public void generateMaze() {
        Random rand = new Random();
        do {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    char type;
                    // Kenarları duvarla
                    if (y == 0 || y == height - 1 || x == 0 || x == width - 1) {
                        type = 'W';
                    } else {
                        double r = rand.nextDouble();
                        if      (r < wallFreq)
                            type = 'W';
                        else if (r < wallFreq + trapFreq)
                            type = 'T';
                        else if (r < wallFreq + trapFreq + powerFreq)
                            type = 'P';
                        else
                            type = 'E';
                    }
                    grid[y][x] = new MazeTile(x, y, type);
                }
            }
            // Start ve goal hücrelerini ve çevresini kesin açık bırak
            Agent start = agents[0];
            int sx = start.getCurrentX();
            int sy = start.getCurrentY();
            grid[sy][sx] = new MazeTile(sx, sy, 'E');

            // çevresindeki dört hücreyi de boş bırak
            if (sy-1 > 0)
                grid[sy-1][sx] = new MazeTile(sx, sy-1, 'E');
            if (sx-1 > 0)
                grid[sy][sx-1] = new MazeTile(sx-1, sy, 'E');
            if (sy+1 < height-1)
                grid[sy+1][sx] = new MazeTile(sx, sy+1, 'E');
            if (sx+1 < width-1)
                grid[sy][sx+1] = new MazeTile(sx+1, sy, 'E');


            int gx = width - 2, gy = height - 2;
            grid[gy][gx] = new MazeTile(gx, gy, 'G');
            if (gy-1 > 0)
                grid[gy-1][gx] = new MazeTile(gx, gy-1, 'E');
            if (gx-1 > 0)
                grid[gy][gx-1] = new MazeTile(gx-1, gy, 'E');
            if (gy+1 < height-1)
                grid[gy+1][gx] = new MazeTile(gx, gy+1, 'E');
            if (gx+1 < width-1)
                grid[gy][gx+1] = new MazeTile(gx+1, gy, 'E');
        } while (!isSolvable());

        // Ajanları yerleştir
        for (Agent a : agents) {
            grid[a.getCurrentY()][a.getCurrentX()].setHasAgent(true);
        }
    }

    /** BFS ile start→goal arası yol var mı diye kontrol eder. */
    private boolean isSolvable() {
        boolean[][] vis = new boolean[height][width];
        CustomQueue<Point> q = new CustomQueue<>();
        Agent s = agents[0];
        q.enqueue(new Point(s.getCurrentX(), s.getCurrentY()));
        vis[s.getCurrentY()][s.getCurrentX()] = true;
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        while (!q.isEmpty()) {
            Point p = q.dequeue();
            if (grid[p.y][p.x].getType() == 'G') return true;
            for (int i = 0; i < 4; i++) {
                int nx = p.x + dx[i];
                int ny = p.y + dy[i];
                if (nx >= 0 && nx < width && ny >= 0 && ny < height && !vis[ny][nx] && grid[ny][nx].isTraversable()) {
                    vis[ny][nx] = true;
                    q.enqueue(new Point(nx, ny));
                }
            }
        }
        return false;
    }

    private static class Point {
        int x;
        int y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public boolean isValidMove(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height && grid[y][x].isTraversable();
    }

    public MazeTile getTile(int x, int y) {
        return grid[y][x];
    }

    public void updateAgentLocation(Agent a, int oldX, int oldY) {
        grid[oldY][oldX].setHasAgent(false);
        grid[a.getCurrentY()][a.getCurrentX()].setHasAgent(true);
    }

    public void rotateCorridor(int rowId) {
        corridor.rotate(grid, rowId);
    }

    public void printMazeSnapshot() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(grid[y][x]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
