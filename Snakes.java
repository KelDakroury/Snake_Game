package week7;
// By Karim ElDakroury 


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Snakes {

    static char[][] maze = new char[8][8];

    static Snake snake0 = new Snake(2, 2);
    static Snake snake1 = new Snake(5, 5);

    static final String logFile = "snakes/output.txt";

    public static void main(String[] args) {

        snake0.positions.add(new Position(0, 2));
        snake0.positions.add(new Position(1, 2));
        snake0.positions.add(new Position(2, 2));

        snake1.positions.add(new Position(7, 5));
        snake1.positions.add(new Position(6, 5));
        snake1.positions.add(new Position(5, 5));

        while (snake0.alive && snake1.alive) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            showMaze();
            //appendMazeToFile(logFile);
            //Scanner scanner = new Scanner(System.in);
            //String inputString = scanner.nextLine();

            // System.out.println(moves[0]); // snake 0
            ArrayList<String> m1 = new ArrayList<>();
            // m1 will hold all possible moves to chose from
            m1.add("n");
            m1.add("s");
            m1.add("e");
            m1.add("w");

            if (snake0.positions.get(2).column == snake0.positions.get(1).column - 1) {
                m1.remove("e");
            }
            if (snake0.positions.get(2).column == snake0.positions.get(1).column + 1) {
                m1.remove("w");
            }
            if (snake0.positions.get(2).row == snake0.positions.get(1).row - 1) {
                m1.remove("s");
            }
            if (snake0.positions.get(2).row == snake0.positions.get(1).row + 1) {
                m1.remove("n");
            }
            //ADD THIS PART TO MAKE SNAKE0 SMART :)
            if (snake0.positions.get(2).row == 0) {
                m1.remove("n");
            }
            if (snake0.positions.get(2).row == 7) {
                m1.remove("s");
            }
            if (snake0.positions.get(2).column == 0) {
                m1.remove("w");
            }
            if (snake0.positions.get(2).column == 7) {
                m1.remove("e");
            }
            //---------------------------------------
            ArrayList<String> m2 = new ArrayList<>();
            m2.add("n");
            m2.add("s");
            m2.add("e");
            m2.add("w");

            if (snake1.positions.get(2).column == snake1.positions.get(1).column - 1) {
                m2.remove("e");
            }
            if (snake1.positions.get(2).column == snake1.positions.get(1).column + 1) {
                m2.remove("w");
            }
            if (snake1.positions.get(2).row == snake1.positions.get(1).row - 1) {
                m2.remove("s");
            }
            if (snake1.positions.get(2).row == snake1.positions.get(1).row + 1) {
                m2.remove("n");
            }

            //ADD THIS PART TO MAKE SNAKE1 SMART
            if (snake1.positions.get(2).row == 0) {
                m2.remove("n");
            }
            if (snake1.positions.get(2).row == 7) {
                m2.remove("s");
            }
            if (snake1.positions.get(2).column == 0) {
                m2.remove("w");
            }
            if (snake1.positions.get(2).column == 7) {
                m2.remove("e");
            }
            //---------------------------------

            String moves[] = {snake0.nextMove(m1), snake1.nextMove(m2)};

            assert (moves.length == 2) : "There should be two moves";

            boolean validMove0 = validateMove(moves[0], snake0);

            if (validMove0) {
                if (moves[0].equalsIgnoreCase("N")) {
                    snake0.positions.add(new Position(snake0.positions.get(2).row - 1, snake0.positions.get(2).column));
                }
                if (moves[0].equalsIgnoreCase("S")) {
                    snake0.positions.add(new Position(snake0.positions.get(2).row + 1, snake0.positions.get(2).column));
                }
                if (moves[0].equalsIgnoreCase("W")) {
                    snake0.positions.add(new Position(snake0.positions.get(2).row, snake0.positions.get(2).column - 1));
                }
                if (moves[0].equalsIgnoreCase("E")) {
                    snake0.positions.add(new Position(snake0.positions.get(2).row, snake0.positions.get(2).column + 1));
                }
            } else {
                snake0.alive = false;
                break;
            }
            // System.out.println(moves[1]); // snake 1
            boolean validMove1 = validateMove(moves[1], snake1);

            if (validMove1) {
                if (moves[1].equalsIgnoreCase("N")) {
                    snake1.positions.add(new Position(snake1.positions.get(2).row - 1, snake1.positions.get(2).column));
                }
                if (moves[1].equalsIgnoreCase("S")) {
                    snake1.positions.add(new Position(snake1.positions.get(2).row + 1, snake1.positions.get(2).column));
                }
                if (moves[1].equalsIgnoreCase("W")) {
                    snake1.positions.add(new Position(snake1.positions.get(2).row, snake1.positions.get(2).column - 1));
                }
                if (moves[1].equalsIgnoreCase("E")) {
                    snake1.positions.add(new Position(snake1.positions.get(2).row, snake1.positions.get(2).column + 1));
                }

            } else {
                snake1.alive = false;
                break;
            }

            snake0.positions.remove(0);
            snake1.positions.remove(0);

            // COLLISION
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (snake0.positions.get(i).row == snake1.positions.get(j).row && snake0.positions.get(i).column == snake1.positions.get(j).column) {
                        snake0.alive = false;
                        snake1.alive = false;
                    }
                }
            }
            // TODO Print and log the moves and an indicator if each was legal
            /**
             * For the future: when a snake takes an apple, the position of the
             * apple becomes the new head position.
             */
        }
        System.out.println("Game Over");
        if (snake0.alive == true) {
            System.out.println("1-0");
        } else if (snake1.alive == true) {
            System.out.println("0-1");
        } else {
            System.out.println("0-0");
        }
    }

    private static boolean validateMove(String move, Snake snake) {
        assert move.equalsIgnoreCase("N") || move.equalsIgnoreCase("S") || move.equalsIgnoreCase("W")
                || move.equalsIgnoreCase("E") : "The input (move) should be in {N, S, W, E}.";

        if (move.equalsIgnoreCase("N") && snake.positions.get(2).row == 0) {
            return false;
        }
        if (move.equalsIgnoreCase("S") && snake.positions.get(2).row == 8) {
            return false;
        }
        if (move.equalsIgnoreCase("W") && snake.positions.get(2).column == 0) {
            return false;
        }
        if (move.equalsIgnoreCase("E") && snake.positions.get(2).column == 8) {
            return false;
        }

        return true;
    }

    private static void showMaze() {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                maze[i][j] = '.';
            }
        }
        for (int i = 0; i < 2; i++) {
            maze[snake0.positions.get(i).row][snake0.positions.get(i).column] = 'a';
        }
        maze[snake0.positions.get(2).row][snake0.positions.get(2).column] = 'A';

        for (int i = 0; i < 2; i++) {
            maze[snake1.positions.get(i).row][snake1.positions.get(i).column] = 'b';
        }
        maze[snake1.positions.get(2).row][snake1.positions.get(2).column] = 'B';

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(maze[i][j]);
            }
            System.out.println("");
        }
        System.out.println("");

    }


}

class Position {

    int row;
    int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

}

class Snake {

    boolean alive = true;
    ArrayList<Position> positions;

    String nextMove(ArrayList al) {

        int r = new Random().nextInt(al.size());
        return (String) al.get(r);

    }

    public Snake(int row, int column) {
        positions = new ArrayList<>();
        alive = true;
    }

}
