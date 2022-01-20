
public class Graph {
    private final int VERTICES = 5;
    private final int[][] MATRIX;

    /**
     * Constructor
     */
    public Graph() {
        MATRIX = new int[VERTICES][VERTICES];
    }

    /**
     * Getter methods
     */
    public int getVertices() {
        return VERTICES;
    }
    public int[][] getMatrix() {
        return MATRIX;
    }

    /**
     * Gets the minimum unvisited vertex for the dijkstra's algorithm
     */
    public int getMinimumVertex(boolean[] visited, int[] distances){
        int minKey = Integer.MAX_VALUE, min_index= -1;
        for(int i = 0; i< VERTICES; i++){
            if(!visited[i] && distances[i]<= minKey){
                minKey = distances[i];
                min_index = i;
            }
        }
        return min_index;
    }

    /**
     * Gets the minimum distances of each vertex from the inputted starting vertex using dijkstra's algorithm
     */
    public void getMinDistances(int sourceVertex){
        boolean[] spt = new boolean[VERTICES];
        int [] distance = new int[VERTICES];
        int INFINITY = Integer.MAX_VALUE;
        for (int i = 0; i < VERTICES; i++) {
            distance[i] = INFINITY;
        }
        distance[sourceVertex] = 0;
        for (int i = 0; i < VERTICES; i++) {
            int U = getMinimumVertex(spt, distance);
            spt[U] = true;
            for (int V = 0; V < VERTICES; V++) {
                if(MATRIX[U][V]>0){
                    if(!spt[V] && MATRIX[U][V]!=INFINITY){
                        int newKey = MATRIX[U][V] + distance[U];
                        if(newKey<distance[V])
                            distance[V] = newKey;
                    }
                }
            }
        }
        printMinimumDistance(sourceVertex, distance);
    }

    /**
     * Prints the minimum distances outputted by the getMinDistances method
     */
    public void printMinimumDistance(int sourceVertex, int [] key){
        for (int i = 0; i < VERTICES; i++) {
            if(key[i] != Integer.MAX_VALUE) {
                System.out.println("Source Vertex: " + sourceVertex + " to vertex  " + +i +
                        " distance:  " + key[i]);
            }
        }
    }

    /**
     * Adds an undirected weighted edge to the graph
     */
    public void addEdge(int source, int destination, int weight) {
        MATRIX[source][destination]=weight;
        MATRIX[destination][source]=weight;
    }

    /**
     * Adds a directed weighted edge to the graph
     */
    public void addDirectedEdge(int source, int destination, int weight) {
        MATRIX[source][destination]=weight;
    }

    /**
     * Print the adjacency matrix
     */
    public void printGraph() {
        for (int i = 0; i< VERTICES; i++) {
            for (int j = 0; j< VERTICES; j++) {
                System.out.print(MATRIX[i][j] + " ");
            }
            System.out.println();
        }
    }
}
