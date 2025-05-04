
public class CircularCorridor {
    private final int width;

    public CircularCorridor(int width) {
        this.width = width;
    }

    public void rotate(MazeTile[][] grid, int rowId) {
        for (MazeTile t : grid[rowId]) {
            if (t.getType() == 'G') return;
        }
        MazeTile first = grid[rowId][0];
        for (int i = 0; i < width - 1; i++) {
            grid[rowId][i] = grid[rowId][i + 1];
        }
        grid[rowId][width - 1] = first;
    }
}
