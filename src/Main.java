import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main{

    public static List<Integer> findAllCenters(HashMap<Integer, ArrayList<Integer>> graph){
        final int n = graph.size();
        int remainingNodes = n;
        List<Integer> centers = new ArrayList<>();
        HashMap<Integer, ArrayList<Integer>> currentLeaves = new HashMap<>();

        if(n == 0) return centers;
        else if(n == 1 || n == 2) return graph.keySet().stream().toList();

        while(true){
            currentLeaves.clear();
            centers.clear();
            for(Map.Entry<Integer, ArrayList<Integer>> node : graph.entrySet()){
                int size = node.getValue().size();
                if(size == 1){
                    currentLeaves.put(node.getKey(), node.getValue());
                    continue;
                }
                if(size != 0) centers.add(node.getKey());
            }
            for(Map.Entry<Integer, ArrayList<Integer>> leaf : currentLeaves.entrySet()){
                graph.get(leaf.getValue().get(0)).remove(leaf.getKey());
                leaf.getValue().clear();
                remainingNodes--;
            }
            if (remainingNodes == 1 || remainingNodes == 2) return centers;
        }
    }
    public static void rootGraph(HashMap<Integer, ArrayList<Integer>> graph, Integer root){
        if(root == null) return;
        ArrayList<Integer> rootChildren = graph.get(root);
        for (Integer rootChild : rootChildren) {
            graph.get(rootChild).remove(root);
            rootGraph(graph, rootChild);
        }
    }
    public static void addUndirectedEdge(HashMap<Integer, ArrayList<Integer>> graph, Integer vertex1, Integer vertex2){
        if(!graph.containsKey(vertex1)) graph.put(vertex1, new ArrayList<>());
        if(!graph.containsKey(vertex2)) graph.put(vertex2, new ArrayList<>());
        graph.get(vertex1).add(vertex2);
        graph.get(vertex2).add(vertex1);
    }
    public static void cloneGraph(HashMap<Integer, ArrayList<Integer>> graph,
                                  HashMap<Integer, ArrayList<Integer>> clonedGraph){
        clonedGraph.clear();
        for (Map.Entry<Integer, ArrayList<Integer>> node : graph.entrySet()){
            clonedGraph.put(node.getKey(), (ArrayList<Integer>) node.getValue().clone());
        }
    }
    public static void solve(HashMap<Integer, ArrayList<Integer>> graph,
                             HashMap<Integer, ArrayList<Integer>> clonedGraph){
        cloneGraph(graph, clonedGraph);
        List<Integer> centers = findAllCenters(clonedGraph);
        if(centers.isEmpty()){
            rootGraph(graph, null);
        }else{
            rootGraph(graph, centers.get(0));
        }
        System.out.println(graph);
    }
    public static void main(String[] args) {

        HashMap<Integer, ArrayList<Integer>> clonedGraph = new HashMap<>();

        // Centers are 2
        HashMap<Integer, ArrayList<Integer>> graph = new HashMap<>(9);
        addUndirectedEdge(graph, 0, 1);
        addUndirectedEdge(graph, 2, 1);
        addUndirectedEdge(graph, 2, 3);
        addUndirectedEdge(graph, 3, 4);
        addUndirectedEdge(graph, 5, 3);
        addUndirectedEdge(graph, 2, 6);
        addUndirectedEdge(graph, 6, 7);
        addUndirectedEdge(graph, 6, 8);
        solve(graph, clonedGraph);

        // Centers are 0
        HashMap<Integer, ArrayList<Integer>> graph2 = new HashMap<>(1);
        solve(graph2, clonedGraph);

        // Centers are 0,1
        HashMap<Integer, ArrayList<Integer>> graph3 = new HashMap<>(2);
        addUndirectedEdge(graph3, 0, 1);
        solve(graph3, clonedGraph);

        // Centers are 1
        HashMap<Integer, ArrayList<Integer>> graph4 = new HashMap<>(3);
        addUndirectedEdge(graph4, 0, 1);
        addUndirectedEdge(graph4, 1, 2);
        solve(graph4, clonedGraph);

        // Centers are 1,2
        HashMap<Integer, ArrayList<Integer>> graph5 = new HashMap<>(4);
        addUndirectedEdge(graph5, 0, 1);
        addUndirectedEdge(graph5, 1, 2);
        addUndirectedEdge(graph5, 2, 3);
        solve(graph5, clonedGraph);

        // Centers are 2,3
        HashMap<Integer, ArrayList<Integer>> graph6 = new HashMap<>(7);
        addUndirectedEdge(graph6, 0, 1);
        addUndirectedEdge(graph6, 1, 2);
        addUndirectedEdge(graph6, 2, 3);
        addUndirectedEdge(graph6, 3, 4);
        addUndirectedEdge(graph6, 4, 5);
        addUndirectedEdge(graph6, 4, 6);
        solve(graph6, clonedGraph);
    }
}