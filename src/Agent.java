import java.util.List;

public class Agent {
    private final int id;
    private int currentX;
    private int currentY;
    private AgentStack<String> moveHistory;
    private boolean hasReachedGoal;
    private int totalMoves;
    private int backtracks;
    private int trapTriggers;
    private boolean hasPowerUp;

    public Agent(int id, int startX, int startY) {
        this.id = id;
        this.currentX = startX;
        this.currentY = startY;
        this.moveHistory = new AgentStack<>();
        this.hasReachedGoal = false;
        this.totalMoves = 0;
        this.backtracks = 0;
        this.trapTriggers = 0;
        this.hasPowerUp = false;

        recordMove(currentX, currentY); // Başlangıç pozisyonunu kaydet
    }

    // Yön bilgisini alır, pozisyonu günceller ve yeni konumu hamle geçmişine ekler.
    public void move(String direction) {
        switch (direction) {
            case "UP":
                currentY--;
                break;
            case "DOWN":
                currentY++;
                break;
            case "LEFT":
                currentX--;
                break;
            case "RIGHT":
                currentX++;
                break;
            default:
                return;
        }
        totalMoves++;
        // Yeni pozisyonu kaydet
        recordMove(currentX, currentY);
    }


     // Tuzak cezası uygular. Eğer power-up varsa ceza atlanır, yoksa geri izleme yapılır. 2 kere.
    public void backtrack() {
        if (hasPowerUp) {
            hasPowerUp = false;
            return;
        }
        trapTriggers++;
        String last = moveHistory.pop();
        if (last != null) {
            String prev = moveHistory.pop();
            if (prev != null)
                last = prev;
        }
        if (last != null) {
            String[] parts = last.split(",");
            currentX = Integer.parseInt(parts[0]);
            currentY = Integer.parseInt(parts[1]);
        }
        backtracks++;
        // Geri izlemeden sonra da konumu kaydet
        recordMove(currentX, currentY);
    }

    // Power-up toplandığında bir sonraki tuzak cezasını önler.
    public void applyPowerUp() {
        hasPowerUp = true;
    }

    private void recordMove(int x, int y) {
        moveHistory.push(x + "," + y);
    }


     // Son n hamleyi liste olarak döner (en yeni en sonda).

    public List<String> getLastMoves(int n) {
        List<String> history = moveHistory.toList();
        int size = history.size();
        return history.subList(Math.max(0, size - n), size);
    }

    // Get - set kısmı
    public int getId() {
        return id;
    }
    public int getCurrentX() {
        return currentX;
    }
    public int getCurrentY() {
        return currentY;
    }
    public boolean hasReachedGoal() {
        return hasReachedGoal;
    }
    public void setReachedGoal(boolean reached) {
        this.hasReachedGoal = reached;
    }
    public int getTotalMoves() {
        return totalMoves;
    }
    public int getBacktracks() {
        return backtracks;
    }
    public int getTrapTriggers() {
        return trapTriggers;
    }
    public boolean hasPowerUp() {
        return hasPowerUp;
    }
    public int getMaxStackDepth() {
        return moveHistory.getMaxDepth();
    }
}
