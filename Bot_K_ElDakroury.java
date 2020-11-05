import java.util.ArrayList;
import java.util.Random;

public class Bot_K_ElDakroury implements Bot {

    // sort the possible coordinates and return the first one
    @Override
    public Direction chooseDirection(ArrayList<Direction> a) {
        int r = new Random().nextInt(a.size());
        return (Direction) a.get(r);
    }

}
