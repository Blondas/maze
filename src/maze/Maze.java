package maze;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Maze {
    private Node [][] mazeMatrix;
    private String inputFile;
    private Dimension startPoint, endPoint, mazeDimension;
    private boolean mazeSolved = false;

    private enum NodeType {
        NODE_START, NODE_END, NODE_PASSAGE, NODE_PATH, NODE_WALL, NODE_VISITED, NODE_NOT_VISITED
    }


    private class Node {
        public int distance;
        public Node parent;
        public boolean visited;
        public Maze.NodeType type;

        public Node(NodeType type) {
            this.type = type;

            distance = -1;
            parent = null;
            visited = false;
        }
    }


    public void consumeInput() throws IOException{
        FileReader fr = new FileReader(inputFile);
        BufferedReader bufr = new BufferedReader(fr);

        int lineNo = 0;
        int matrixLine = 0;
        String line = bufr.readLine();

        while (line != null) {
            if (lineNo == 0) {
                setMazeDimension(line);
            } else if (lineNo == 1) {
                setStartPoint(line);
            } else if (lineNo == 2) {
                setEndPoint(line);
            } else {
                lineToNode(line, matrixLine);
                matrixLine++;
            }

            line = bufr.readLine();
            lineNo++;
        }

        bufr.close();
    }


    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }


    public void setStartPoint(String startLine) {
        String[] dimension = startLine.split("\\s+");
        startPoint = new Dimension( Integer.parseInt(dimension[0]), Integer.parseInt(dimension[1]) );
    }


    public void setEndPoint(String endLine) {
        String[] dimension = endLine.split("\\s+");
        endPoint = new Dimension( Integer.parseInt(dimension[0]), Integer.parseInt(dimension[1]) );
    }


    public void setMazeDimension(String line) {
        String[] dimension = line.split("\\s+");
        mazeDimension = new Dimension( Integer.parseInt(dimension[0]), Integer.parseInt(dimension[1]) );

        mazeMatrix = new Node[mazeDimension.width][mazeDimension.height];
    }


    private void lineToNode(String line, int matrixLine) {
        if (matrixLine < mazeDimension.height) {
            String[] nodesLine = line.split("\\s+");

            for (int matrixColumn = 0; matrixColumn < nodesLine.length; matrixColumn++) {
                Node node;
                Dimension currentPosition = new Dimension(matrixColumn, matrixLine);

                if ( currentPosition.equals(startPoint) ) {
                    node = new Node(NodeType.NODE_START);
                } else if ( currentPosition.equals(endPoint) ) {
                    node = new Node(NodeType.NODE_END);
                } else {
                    int matrixValue = Integer.parseInt( nodesLine[matrixColumn] );

                    switch (matrixValue) {
                        case 0:
                            node = new Node(NodeType.NODE_PASSAGE);
                            break;
                        case 1: default:
                            node = new Node(NodeType.NODE_WALL);
                    }
                }

                mazeMatrix[matrixLine][matrixColumn] = node;
            }
        }
    }


    public void breadthFirstSearch() {

    }


    public void setPath() {
        Node node = mazeMatrix[endPoint.width][endPoint.height];

        while (node.parent instanceof Node) {
            if (node.parent.type == NodeType.NODE_PASSAGE) {
                node.parent.type = NodeType.NODE_PATH;
            }
        }
    }


    public void printSolvedMaze() {
        if (true || mazeSolved) {
            for (Node[] nodeLine : mazeMatrix) {
                String line = "";

                for (Node node : nodeLine) {
                    switch (node.type) {
                        case NODE_WALL:
                            line += "#";
                            break;
                        case NODE_START:
                            line += "S";
                            break;
                        case NODE_PATH:
                            line += "X";
                            break;
                        case NODE_PASSAGE:
                            line += " ";
                            break;
                        case NODE_END:
                            line += "E";
                            break;
                        default:
                            break;
                    }
                }

                System.out.println(line);
            }
        } else {
            System.out.println("No solution is possible");
        }
    }
}
