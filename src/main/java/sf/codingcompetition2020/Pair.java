package sf.codingcompetition2020;

public class Pair {
    public final int first;
    public final int second;

    public Pair(int first, int second) {
        this.first = first;
        this.second = second;
    }
    
    public Pair add(Pair other) {
        return new Pair(first + other.first, second + other.second);
    }
}
