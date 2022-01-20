import java.io.*;
import java.util.*;

public class GraphApplication {
    /**
     * Main method
     */
    public static void main(String[] args) {
        GraphApplication test = new GraphApplication();
        test.run();
    }

    /**
     * Run method
     */
    public void run() {
        int choice;
        Graph g = new Graph();
        while (true) {
            try {
                System.out.println("\nMenu ");
                System.out.println("----------------------------------------------------");
                System.out.println("1. Load file containing the graph's data");
                System.out.println("2. Perform Depth First Traversal of the graph");
                System.out.println("3. Perform Breadth First Traversal of the graph");
                System.out.println("4. Show the shortest path from one vertex to another");
                System.out.println("5. Exit");
                System.out.println("----------------------------------------------------");
                choice = Integer.parseInt(userInput(1));
                switch (choice) {
                    case 1 -> {
                        System.out.println("\nLoading file...");
                        char type = userInput(2).charAt(0);
                        g = constructGraph("res/weights.csv", type); // Note: For this example, there are 5 vertices in the undirected graph
                        System.out.println("The file has been read successfully");
                        g.printGraph();
                        enterKeyToContinue();
                    }
                    case 2 -> {
                        System.out.print("\nPlease input the starting vertex ");
                        choice = Integer.parseInt(userInput(1));
                        depthTraversal(g, choice);
                        enterKeyToContinue();
                    }
                    case 3 -> {
                        System.out.print("\nPlease input the starting vertex ");
                        choice = Integer.parseInt(userInput(1));
                        breadthTraversal(g, choice);
                        enterKeyToContinue();
                    }
                    case 4 -> {
                        System.out.print("\nPlease input the starting vertex ");
                        choice = Integer.parseInt(userInput(1));
                        shortestPath(g, choice);
                        enterKeyToContinue();
                    }
                    case 5 -> {
                        System.out.println("Closing application...");
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid input, ensure that you enter a number from 1 to 5.\n");
                }
            } catch (Exception e) {
                System.out.println("(an error during the process, try again)");
            }
        }
    }

    /**
     * Returns the user input and checks if the input is valid
     */
    public static String userInput(int task) {
        Scanner kbd = new Scanner(System.in);
        int temp = 0;
        boolean proceed;

        switch (task) {
            case 1 -> {

                do {
                    try {
                        proceed = true;
                        System.out.print("> ");
                        temp = Integer.parseInt(kbd.nextLine());
                    } catch (NumberFormatException exception) {
                        System.out.println("Invalid input, need to enter a number.\n");
                        proceed = false;
                    }
                } while (!proceed);
            }
            case 2 -> {
                String str;
                do {
                    proceed = true;
                    System.out.print("What type of graph [d(directed)/n(not directed)]? ");
                    str = kbd.nextLine();
                    if (!str.equalsIgnoreCase("d") && !str.equalsIgnoreCase("n")) {
                        System.out.println("Input must be d or n, try again.\n");
                        proceed = false;
                    }
                } while (!proceed);
                return str;
            }
            default -> System.out.println("There is an error.\n");
        }
        return Integer.toString(temp);
    }

    /** Populates a directionless graph with values from a csv.
     * csv format: source vertex, destination vertex, edge weight
     * 0,3,2 (source vertex = 0, destination vertex = 3, edge weight = 2)
     * Type represents how you want to construct the graph
     * 'd' means construct a directed graph, otherwise, this function will construct an undirected graph
     */
    public static Graph constructGraph(String filepath, char type) throws Exception {
        Graph out = new Graph();
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            while ((line = br.readLine()) != null) {
                String[] value = line.split(",");
                if (type == 'd') {
                    out.addDirectedEdge(Integer.parseInt(value[0]), Integer.parseInt(value[1]), Integer.parseInt(value[2]));
                } else {
                    out.addEdge(Integer.parseInt(value[0]), Integer.parseInt(value[1]), Integer.parseInt(value[2]));
                }
            }
        } catch (Exception e) {
            System.out.println("There was an error in constructing the graph, must exit the program immediately.");
            throw new Exception();
        }
        return out;
    }

    /**
     * Traverses the graph using a stack data structure
     */
    public static void depthTraversal(Graph g, int sVertex) throws Exception {
        Stack<Integer> stack = new Stack<>();
        ArrayList<Integer> visitedList = new ArrayList<>();
        int[][] adjacencyMatrix = g.getMatrix();
        checkStartingVertex(g, sVertex);

        stack.push(sVertex);
        while(!(stack.isEmpty())) {
            int vertex = stack.pop();
            if(!(visitedList.contains(vertex))) {
                for(int i=0; i<adjacencyMatrix[0].length; i++) {
                    if(adjacencyMatrix[vertex][i]>0) {
                        stack.push(i);
                    }
                }
                visitedList.add(vertex);
            }
        }

        System.out.println(sVertex+ " -> " + visitedList);
    }

    /**
     * Traverses the graph using a queue data structure
     */
    public static void breadthTraversal(Graph g, int sVertex) throws Exception {
        Queue<Integer> queue = new LinkedList<>();
        ArrayList<Integer> visitedList = new ArrayList<>();
        int[][] adjacencyMatrix = g.getMatrix();
        checkStartingVertex(g, sVertex);

        queue.add(sVertex);

        while(!(queue.isEmpty())) {
            int vertex = queue.poll();
            if(!(visitedList.contains(vertex))) {
                for(int i=0; i<adjacencyMatrix[0].length; i++) {
                    if(adjacencyMatrix[vertex][i]>0) {
                        queue.add(i);
                    }
                }
                visitedList.add(vertex);
            }
        }

        System.out.println(sVertex+ " -> " + visitedList);
    }

    /**
     * Check if the inputted starting vertex is valid
     */
    private static void checkStartingVertex(Graph g, int sVertex) throws Exception {
        boolean proceed = false;
        for(int i=0; i < g.getVertices(); i++) {
            if (sVertex == i) {
                proceed = true;
                break;
            }
        }
        if(!proceed) {
            System.out.println("The starting vertex is not valid or there is no" +
                    " successfully loaded graph.");
            throw new Exception();
        }
    }

    /**
     * Get the shortest path from a starting vertex using dijkstra's algorithm
     */
    public static void shortestPath(Graph g, int sourceVertex) {
        g.getMinDistances(sourceVertex);
    }

    /**
     * Waits for user input before continuing
     */
    private static void enterKeyToContinue() {
        System.out.print("Press Enter key to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
