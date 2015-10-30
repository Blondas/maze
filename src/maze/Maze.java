package maze;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Maze {
    private Node [][] mazeMatrix;
    private String inputFile;
    private Dimension startPoint, endPoint, mazeDimension;
    private boolean mazeSolved = false;

    private enum NodeType {
        NODE_START, NODE_END, NODE_PASSAGE, NODE_PATH, NODE_WALL
    }

    private static final String STRING_WALL = "#";
    private static final String STRING_START = "S";
    private static final String STRING_PATH = "X";
    private static final String STRING_PASSAGE = " ";
    private static final String STRING_END = "E";


    private class Node {
        public int distance;
        public Node parent;
        public boolean visited;
        public Maze.NodeType type;
        public Dimension position;

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

                node.position.height = matrixLine;
                node.position.width = matrixColumn;
                mazeMatrix[matrixLine][matrixColumn] = node;
            }
        }
    }


    public void breadthFirstSearch() {
        Queue<Node> queue = new LinkedList<Node>();
        Node start = mazeMatrix[startPoint.height][startPoint.width];

        start.distance = 0;
        queue.add(start);

        while ( !queue.isEmpty() ) {
            Node currentNode = queue.remove();

            List<Node> adjacentNodes = getAdjacentNodes(currentNode);
        }
    }


    private List<Node> getAdjacentNodes(Node node) {
        List<Node> adjacentNodes = new ArrayList<Node>();

        // get N node
        if (node.position.height > 0) {
            adjacentNodes.add(mazeMatrix[node.position.height - 1][node.position.width]);
        }
        // get W node
        if (node.position.width < mazeDimension.width - 1) {
            adjacentNodes.add(mazeMatrix[node.position.height][node.position.width + 1]);
        }
        // get S node
        if (node.position.height < mazeDimension.height - 1) {
            adjacentNodes.add(mazeMatrix[node.position.height + 1][node.position.width]);
        }
        // get E node
        if (node.position.width > 0) {
            adjacentNodes.add(mazeMatrix[node.position.height][node.position.width - 1]);
        }

        return adjacentNodes;
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
                            line += STRING_WALL;
                            break;
                        case NODE_START:
                            line += STRING_START;
                            break;
                        case NODE_PATH:
                            line += STRING_PATH;
                            break;
                        case NODE_PASSAGE:
                            line += STRING_PASSAGE;
                            break;
                        case NODE_END:
                            line += STRING_END;
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
