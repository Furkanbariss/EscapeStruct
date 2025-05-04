public class MazeTile {
    private int x;
    private int y;
    private char type;
    private boolean hasAgent;

    public MazeTile(int x, int y, char type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.hasAgent = false;
    }

    public boolean isTraversable() {
        return type == 'E' || type == 'T' || type == 'P' || type == 'G';
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getType() {
        return type;
    }

    public boolean hasAgent() {
        return hasAgent;
    }

    public void setHasAgent(boolean hasAgent) {
        this.hasAgent = hasAgent;
    }

    @Override
    public String toString() {
        if (hasAgent) return "A";
        return String.valueOf(type);
    }
}
