import java.util.*;
import java.io.*;

public class Game {

    // log file and maze
    static String log = "";
    static char[][] maze = new char[8][8];

    static int delay = 3000;

    // AE = Apple eaten
    static boolean AE = true;


    static void SnakeP(Snake snake0, Snake snake1) {
        //Add the snake position inside tha maze
        for (int i = 0; i < snake0.positions.size() - 1; i++) {
            maze[snake0.positions.get(i).row][snake0.positions.get(i).column] = 'o';
        }
        maze[snake0.P.row][snake0.P.column] = 'O';

        for (int i = 0; i < snake1.positions.size() - 1; i++) {
            maze[snake1.positions.get(i).row][snake1.positions.get(i).column] = 'x';
        }
        maze[snake1.P.row][snake1.P.column] = 'X';

    }

    //Game Interface
    static void showMaze(Snake snake0, Snake snake1) {

        if (AE == false) {
            //Leave apple in the same place
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (maze[i][j] != 'A') {
                        maze[i][j] = '.';
                    }

                }
            }
            SnakeP(snake0, snake1);
        } else {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    maze[i][j] = '.';
                }
            }

            SnakeP(snake0, snake1);

            //Add random apple in an empty place 
            while (true) {
                int r = new Random().nextInt(8);
                int c = new Random().nextInt(8);
                if (maze[r][c] == '.') {
                    maze[r][c] = 'A';
                    break;
                }
            }
            AE = false;
        }

        //Print and log the maze
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(maze[i][j]);
                log += maze[i][j];
            }
            System.out.println("");
            log += "\n";
        }
        System.out.println("");
        log += "\n";

    }

    public static void main(String[] args) {

        //Creating the snakes
        Snake snake0 = new Snake(2, 2);
        Snake snake1 = new Snake(5, 5);


        // Initializing
        snake0.positions.add(new Position(0, 2));
        snake0.positions.add(new Position(1, 2));
        snake0.positions.add(new Position(2, 2));

        snake1.positions.add(new Position(7, 5));
        snake1.positions.add(new Position(6, 5));
        snake1.positions.add(new Position(5, 5));

        while (snake0.alive == true && snake1.alive == true) {

            //Printing the maze
            showMaze(snake0, snake1);

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // To hold all possible moves for the snake0
            ArrayList<Direction> AP0 = new ArrayList<>();

            // Initially it can move anywhere
            AP0.add(Direction.N);
            AP0.add(Direction.S);
            AP0.add(Direction.E);
            AP0.add(Direction.W);

            // Avoid moving backwards
            if (snake0.positions.get(snake0.positions.size() - 1).column == snake0.positions.get(snake0.positions.size() - 2).column - 1) {
                AP0.remove(Direction.E);
            }
            if (snake0.positions.get(snake0.positions.size() - 1).column == snake0.positions.get(snake0.positions.size() - 2).column + 1) {
                AP0.remove(Direction.W);
            }
            if (snake0.positions.get(snake0.positions.size() - 1).row == snake0.positions.get(snake0.positions.size() - 2).row - 1) {
                AP0.remove(Direction.S);
            }
            if (snake0.positions.get(snake0.positions.size() - 1).row == snake0.positions.get(snake0.positions.size() - 2).row + 1) {
                AP0.remove(Direction.N);
            }

            // Avoid hitting the wall
            if (snake0.positions.get(snake0.positions.size() - 1).row == 0) {
                AP0.remove(Direction.N);
            }
            if (snake0.positions.get(snake0.positions.size() - 1).row == 7) {
                AP0.remove(Direction.S);
            }
            if (snake0.positions.get(snake0.positions.size() - 1).column == 0) {
                AP0.remove(Direction.W);
            }
            if (snake0.positions.get(snake0.positions.size() - 1).column == 7) {
                AP0.remove(Direction.E);
            }

            //The same with snake1
            ArrayList<Direction> AP = new ArrayList<>();

            AP.add(Direction.N);
            AP.add(Direction.S);
            AP.add(Direction.E);
            AP.add(Direction.W);

            if (snake1.positions.get(snake1.positions.size() - 1).column == snake1.positions.get(snake1.positions.size() - 2).column - 1) {
                AP.remove(Direction.E);
            }
            if (snake1.positions.get(snake1.positions.size() - 1).column == snake1.positions.get(snake1.positions.size() - 2).column + 1) {
                AP.remove(Direction.W);
            }
            if (snake1.positions.get(snake1.positions.size() - 1).row == snake1.positions.get(snake1.positions.size() - 2).row - 1) {
                AP.remove(Direction.S);
            }
            if (snake1.positions.get(snake1.positions.size() - 1).row == snake1.positions.get(snake1.positions.size() - 2).row + 1) {
                AP.remove(Direction.N);
            }

            if (snake1.positions.get(snake1.positions.size() - 1).row == 0) {
                AP.remove(Direction.N);
            }
            if (snake1.positions.get(snake1.positions.size() - 1).row == 7) {
                AP.remove(Direction.S);
            }
            if (snake1.positions.get(snake1.positions.size() - 1).column == 0) {
                AP.remove(Direction.W);
            }
            if (snake1.positions.get(snake1.positions.size() - 1).column == 7) {
                AP.remove(Direction.E);
            }

            // Creating a bot for handling moves
            Bot_K_ElDakroury b = new Bot_K_ElDakroury();

            // Randomizing moves
            Direction moves[] = {b.chooseDirection(AP0), b.chooseDirection(AP)};

            //showing moves
            System.out.println(moves[0] + " " + moves[1]);
            log += moves[0] + " " + moves[1] + "\n";

            //Moving snake0
            switch (moves[0]) {
                case E:
                    snake0.P.column++;
                    break;
                case W:
                    snake0.P.column--;
                    break;
                case N:
                    snake0.P.row--;
                    break;
                case S:
                    snake0.P.row++;
                    break;
                default:
                    break;
            }

            // If snake0 EAT the apple, Increase its size, mark apple as eaten
            if (maze[snake0.P.row][snake0.P.column] == 'A') {
                AE = true;
            } else {
                snake0.positions.remove(0);
            }

            //Moving snake1
            switch (moves[1]) {
                case E:
                    snake1.P.column++;
                    break;
                case W:
                    snake1.P.column--;
                    break;
                case N:
                    snake1.P.row--;
                    break;
                case S:
                    snake1.P.row++;
                    break;
                default:
                    break;
            }

            // If snake1 ate the apple, Increase in size, mark apple as eaten
            if (maze[snake1.P.row][snake1.P.column] == 'A') {
                AE = true;
            } else {
                snake1.positions.remove(0);
            }

            // Adding the new heads positions
            snake0.positions.add(new Position(snake0.P.row, snake0.P.column));
            snake1.positions.add(new Position(snake1.P.row, snake1.P.column));

            //Check if any snake collides with the other one
            for (int i = 0; i < snake1.positions.size(); i++) {
                if (snake0.P.row == snake1.positions.get(i).row && snake0.P.column == snake1.positions.get(i).column) {
                    snake0.alive = false;
                }
            }
            for (int i = 0; i < snake0.positions.size(); i++) {
                if (snake1.P.row == snake0.positions.get(i).row && snake1.P.column == snake0.positions.get(i).column) {
                    snake1.alive = false;
                }
            }
            //Check if any snake collides with itself
            for (int i = 0; i < snake0.positions.size() - 1; i++) {
                if (snake0.P.row == snake0.positions.get(i).row && snake0.P.column == snake0.positions.get(i).column) {
                    snake0.alive = false;
                }
            }
            for (int i = 0; i < snake1.positions.size() - 1; i++) {
                if (snake1.P.row == snake1.positions.get(i).row && snake1.P.column == snake1.positions.get(i).column) {
                    snake1.alive = false;
                }
            }
        }

        //GameOver
        //Print the result
        System.out.print("Game OVER\n");
        log += "Game OVER\n";

        if (snake0.alive == true) {
            System.out.print("1-0\n");
            log += "1-0\n";
        } else if (snake1.alive == true) {
            System.out.print("0-1\n");
            log += "0-1\n";
        } else {
            System.out.println("0-0\n");
            log += "0-0\n";
        }

        //Write the output into log
        try {
            try (FileWriter fw = new FileWriter("log.txt")) {
                fw.write(log);
            }
        } catch (IOException e) {
            System.out.println("Error writing into log file :(\n");
        }

    }
}
