import java.util.List;
import java.util.ArrayList;

public class TurnQueue {
    private CustomQueue<Agent> queue;
    private List<Agent> allAgents;

    public TurnQueue(Agent[] agents) {
        this.allAgents = new ArrayList<>();
        this.queue = new CustomQueue<>();
        for (Agent a : agents) {
            this.allAgents.add(a);
            this.queue.enqueue(a);
        }
    }

    // Sıradaki ajanı döner ve kuyruğun sonuna koyar
    public Agent nextAgent() {
        Agent a = queue.dequeue();
        queue.enqueue(a);
        return a;
    }

    // Tüm ajanlar hedefe ulaşmadan oyunu bitirmiyoruz. ben bitti demeden bitmez aga!
    public boolean allAgentsFinished() {
        for (Agent a : allAgents) {
            if (!a.hasReachedGoal()) return false;
        }
        return true;
    }
}
