//To handle 2D positions
public class Position implements Comparable<Position> {

    int row, column;

    @Override
    public int compareTo(Position other) {

        if (this.row < other.row) {
            return +1;
        }
        if (this.row == other.row) {
            return 0;
        }
        return -1;
    }

    Position(int c1, int c2) {
        this.row = c1;
        this.column = c2;
    }
}
