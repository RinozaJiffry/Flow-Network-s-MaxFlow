package company;

// Implementation of Ford Fulkerson using Java


import java.lang.*;
import java.util.LinkedList;

class algo {
    /* Returns true if there is a path from source 's' to
    sink 't' in residual graph. Also fills parent[] to
    store the path */
    static boolean bfs(int[][] resGraph, int s, int t, int[] parent, int Vert)
    {
        // Create a visited array and mark all vertices as
        // not visited
        boolean visit[] = new boolean[Vert];
        for (int i = 0; i < Vert; ++i)
            visit[i] = false;
        // Create a queue, enqueue source vertex and mark
        // source vertex as visited
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(s);
        visit[s] = true;
        parent[s] = -1;
        // Standard bfs Loop
        while (queue.size() != 0) {
            int u = queue.poll();
            for (int v = 0; v < Vert; v++) {
                if (visit[v] == false && resGraph[u][v] > 0) {
                    /*
                    If we find a connection to the sink node, then there is no point in bfs
                    anymore We just have to set its parent and can return true
                    */
                    if (v == t) {
                        parent[v] = u;
                        return true;
                    }
                    queue.add(v);
                    parent[v] = u;
                    visit[v] = true;
                }
            }
        }
        // We didn't reach sink in bfs starting from source,so return false
        return false;
    }
    // Returning maximum flow from s to t in the given graph
    static int fordFulkerson(int graph[][], int s, int t)
    {
        int Vert;
        Vert = t + 1;
        int u, v;

        /*
         Creating  a residual graph and fill the residual graph with given capacities in the original graph
         as residual capacities in residual graph
         Residual graph where residualGraph[i][j] indicates
         residual capacity of edge from i to j (if there is an edge.
         If residualGraph[i][j] is 0, then there is not)
         */

        int residualGraph[][] = new int[Vert][Vert];
        for (u = 0; u < Vert; u++)
            for (v = 0; v < Vert; v++)
                residualGraph[u][v] = graph[u][v];

        // This array is filled by bfs and to store path

        int parent[] = new int[Vert];
        int maxFlow = 0;// Initially there is no flow
        long start = System.currentTimeMillis();

        // Augment the flow while there is a path from source to sink

        while (bfs(residualGraph, s, t, parent, Vert)) {

            // Finding the minimum residual capacity of the edges along the path filled by bfs.
            // finding the maximum flow through the path found.

            int path_flow = Integer.MAX_VALUE;
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                path_flow = Math.min(path_flow, residualGraph[u][v]);
            }
            // updating the residual capacities of the edges and reversing the edges along the path
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                residualGraph[u][v] -= path_flow;
                residualGraph[v][u] += path_flow;
            }
            // Adding path flow to the overroll flow
            System.out.print("\nAugment path : " + s);
            for (int i=0; i < parent.length; i++){
                if (parent[i]!=-1 && parent[i]!=0){
                    System.out.print("-->"+parent[i]);
                }
            }
            System.out.print("-->"+t);
            System.out.println("\nFlow units which can be carried along this route : " + path_flow);
            System.out.println("So, final max flow is : "+ maxFlow + " + " + path_flow + " = " + (maxFlow +path_flow));
            maxFlow += path_flow;


        }
        long last = System.currentTimeMillis();
        double elapsed = (last - start) / 1000.0;
        System.out.println("Elapsed time = " + elapsed + " seconds");

        // Returing overoll flow
        return maxFlow;


    }


}
