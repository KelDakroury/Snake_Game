import java.util.ArrayList;

//To deal with snakes  

public class Snake {

    boolean alive;                       //Snake state
    Position P = new Position(0, 0);    //Head Position
    ArrayList<Position> positions;       //Tracking the snake's body

    //Snake constructor
    Snake(int c1, int c2) {
        positions = new ArrayList<>();
        this.alive = true;
        this.P.row = c1;
        this.P.column = c2;
    }

    // Return current head position
    Position getHead() {
        return new Position(P.row, P.column);
    }

}
