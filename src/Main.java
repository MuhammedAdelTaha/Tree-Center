import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main{

    //O(n), where n is the size of the graph
    public static List<Integer> findAllCenters(HashMap<Integer, ArrayList<Integer>> graph){
        HashMap<Integer, ArrayList<Integer>> clonedGraph = cloneGraph(graph);
        final int n = clonedGraph.size();
        int remainingNodes = n;
        List<Integer> centers = new ArrayList<>();
        HashMap<Integer, ArrayList<Integer>> currentLeaves = new HashMap<>();

        if(n == 0) return centers;
        else if(n == 1 || n == 2) return clonedGraph.keySet().stream().toList();

        while(true){
            currentLeaves.clear();
            centers.clear();
            for(Map.Entry<Integer, ArrayList<Integer>> node : clonedGraph.entrySet()){
                int size = node.getValue().size();
                if(size == 1){
                    currentLeaves.put(node.getKey(), node.getValue());
                    continue;
                }
                if(size != 0) centers.add(node.getKey());
            }
            for(Map.Entry<Integer, ArrayList<Integer>> leaf : currentLeaves.entrySet()){
                //for unconnected graphs, a leaf with no parent
                if(leaf.getValue().isEmpty()) continue;
                //for connected graphs, leaf has parent
                clonedGraph.get(leaf.getValue().get(0)).remove(leaf.getKey());
                leaf.getValue().clear();
                remainingNodes--;
            }
            if (remainingNodes == 1 || remainingNodes == 2) return centers;
        }
    }
    //uses DFS, then takes O(lg(n))
    public static void rootGraph(HashMap<Integer, ArrayList<Integer>> graph, Integer root){
        if(root == null) return;
        ArrayList<Integer> rootChildren = graph.get(root);
        for (Integer rootChild : rootChildren) {
            graph.get(rootChild).remove(root);
            rootGraph(graph, rootChild);
        }
    }
    //get the center O(n) & root the graph around this center O(lg(n))
    public static void rootGraphAroundCenter(HashMap<Integer, ArrayList<Integer>> graph, int centerIdx){
        List<Integer> centers = findAllCenters(graph);
        if(centers.isEmpty()){
            rootGraph(graph, null);
        }else{
            rootGraph(graph, centers.get(centerIdx));
        }
    }
    static void sort(ArrayList<String> labels)
    {
        for (int i=1 ;i<labels.size(); i++)
        {
            String temp = labels.get(i);
            int j = i - 1;
            while (j >= 0 && temp.length() > labels.get(j).length())
            {
                labels.set(j+1, labels.get(j));
                j--;
            }
            labels.set(j+1, temp);
        }
    }
    public static String encoding(HashMap<Integer, ArrayList<Integer>> graph, Integer root){
        if(root == null) return "";
        ArrayList<Integer> rootChildren = graph.get(root);
        ArrayList<String> labels = new ArrayList<>();
        for (Integer rootChild : rootChildren) {
            labels.add(encoding(graph, rootChild));
        }
        sort(labels);
        StringBuilder temp = new StringBuilder();
        for (String label : labels) {
            temp.append(label);
        }
        return "(" + temp + ")";
    }
    public static boolean isIsomorphic(HashMap<Integer, ArrayList<Integer>> graph1, HashMap<Integer, ArrayList<Integer>> graph2){
        List<Integer> centers1 = findAllCenters(graph1);
        rootGraphAroundCenter(graph1, 0);
        String graph1Encoding = (centers1.isEmpty()) ? encoding(graph1, null) : encoding(graph1, centers1.get(0));

        List<Integer> centers2 = findAllCenters(graph2);
        String graph2Encoding = "";
        for(int i = 0; i < centers2.size(); i++){
            HashMap<Integer, ArrayList<Integer>> tempGraph = cloneGraph(graph2);
            rootGraphAroundCenter(tempGraph, i);
            graph2Encoding = encoding(tempGraph, centers2.get(i));
            if(graph1Encoding.equals(graph2Encoding)) return true;
        }
        return graph1Encoding.equals(graph2Encoding);
    }
    public static boolean addUndirectedEdge(HashMap<Integer, ArrayList<Integer>> graph, Integer vertex1, Integer vertex2){
        if(graph.containsKey(vertex1) && graph.containsKey(vertex2)) return false;
        if(!graph.containsKey(vertex1)) graph.put(vertex1, new ArrayList<>());
        if(!graph.containsKey(vertex2)) graph.put(vertex2, new ArrayList<>());
        graph.get(vertex1).add(vertex2);
        graph.get(vertex2).add(vertex1);
        return true;
    }
    public static HashMap<Integer, ArrayList<Integer>> cloneGraph(HashMap<Integer, ArrayList<Integer>> graph){
        HashMap<Integer, ArrayList<Integer>> clonedGraph = new HashMap<>();
        for (Map.Entry<Integer, ArrayList<Integer>> node : graph.entrySet()){
            clonedGraph.put(node.getKey(), (ArrayList<Integer>) node.getValue().clone());
        }
        return clonedGraph;
    }
    public static void solve(HashMap<Integer, ArrayList<Integer>> graph){
        rootGraphAroundCenter(graph, 0);
        System.out.println(graph);
    }
    public static void main(String[] args) {
        // Centers are 2
        HashMap<Integer, ArrayList<Integer>> graph = new HashMap<>();
        addUndirectedEdge(graph, 0, 1);
        addUndirectedEdge(graph, 2, 1);
        addUndirectedEdge(graph, 2, 1);
        addUndirectedEdge(graph, 2, 3);
        addUndirectedEdge(graph, 3, 4);
        addUndirectedEdge(graph, 5, 3);
        addUndirectedEdge(graph, 2, 6);
        addUndirectedEdge(graph, 6, 7);
        addUndirectedEdge(graph, 6, 8);
        solve(graph);

        // Centers are 0
        HashMap<Integer, ArrayList<Integer>> graph2 = new HashMap<>();
        solve(graph2);

        // Centers are 0,1
        HashMap<Integer, ArrayList<Integer>> graph3 = new HashMap<>();
        addUndirectedEdge(graph3, 0, 1);
        solve(graph3);

        // Centers are 1
        HashMap<Integer, ArrayList<Integer>> graph4 = new HashMap<>();
        addUndirectedEdge(graph4, 0, 1);
        addUndirectedEdge(graph4, 1, 2);
        solve(graph4);

        // Centers are 1,2
        HashMap<Integer, ArrayList<Integer>> graph5 = new HashMap<>();
        addUndirectedEdge(graph5, 0, 1);
        addUndirectedEdge(graph5, 1, 2);
        addUndirectedEdge(graph5, 2, 3);
        solve(graph5);

        // Centers are 2,3
        HashMap<Integer, ArrayList<Integer>> graph6 = new HashMap<>();
        addUndirectedEdge(graph6, 0, 1);
        addUndirectedEdge(graph6, 1, 2);
        addUndirectedEdge(graph6, 2, 3);
        addUndirectedEdge(graph6, 3, 4);
        addUndirectedEdge(graph6, 4, 5);
        addUndirectedEdge(graph6, 4, 6);
        solve(graph6);

        // Trees Isomorphism
        HashMap<Integer, ArrayList<Integer>> graph9 = new HashMap<>();
        addUndirectedEdge(graph9, 9, 1);
        addUndirectedEdge(graph9, 9, 4);
        addUndirectedEdge(graph9, 9, 15);
        addUndirectedEdge(graph9, 15, 0);
        addUndirectedEdge(graph9, 15, 90);
        addUndirectedEdge(graph9, 2, 1);
        addUndirectedEdge(graph9, 5, 1);
        addUndirectedEdge(graph9, 6, 4);
        HashMap<Integer, ArrayList<Integer>> graph10 = new HashMap<>();
        addUndirectedEdge(graph10, 0, 1);
        addUndirectedEdge(graph10, 1, 2);
        addUndirectedEdge(graph10, 2, 3);
        addUndirectedEdge(graph10, 2, 6);
        addUndirectedEdge(graph10, 3, 4);
        addUndirectedEdge(graph10, 3, 5);
        addUndirectedEdge(graph10, 6, 8);
        addUndirectedEdge(graph10, 6, 7);
        System.out.println(isIsomorphic(graph10, graph9));
    }
}