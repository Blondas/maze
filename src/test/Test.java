package test;

import maze.Maze;

import java.io.IOException;

public class Test {
    public static void main (String [] args) throws IOException {
        Maze maze = new Maze();
        maze.setInputFile("input/input.txt");
        maze.consumeInput();
    }
}
