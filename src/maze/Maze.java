package maze;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Maze {
    private Node [][] mazeNodes;
    private String inputFile;

    private enum NodeType {
        START, END, PASSAGE, WALL
    }

    private class Node {
        public int distance;
        public Node parent;
        public boolean visited;
    }

    public void consumeInput() throws IOException{
        FileReader fr = new FileReader(inputFile);
        BufferedReader bufr = new BufferedReader(fr);

        int line_no = 1;
        String line = bufr.readLine();

        while (line != null) {
            if (line_no == 1) {
                setMazeDimension(line);
            } else if (line_no == 2) {
                setStart(line);
            } else if (line_no == 3) {
                setEnd(line);
            } else {
                System.out.println(line_no + " : " + line);
            }

            line = bufr.readLine();
            line_no++;
        }

        bufr.close();
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public void setStart(String startLine) {
//        this.start = start;
    }

    public void setEnd(String endLine end) {
        this.end = end;
    }

    public void setMazeDimension(String line) {
        mazeNodes = new Node[10][10];
    }

    private void lineToNode(String line) {

    }
}
