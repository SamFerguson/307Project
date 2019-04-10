import org.jgrapht.GraphPath;
import org.jgrapht.alg.cycle.JohnsonSimpleCycles;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.BellmanFordShortestPath;
import org.jgrapht.alg.shortestpath.NegativeCycleDetectedException;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class test {
    public static void main(String[] args){
        DefaultDirectedWeightedGraph<String,DefaultWeightedEdge> test = new DefaultDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        for(int i = 1; i<9; i++){
            test.addVertex(Integer.toString(i));
        }
        String[] vertices = {"1","2","3","4","5","6","7","8"};
        System.out.println(test.vertexSet());
        DefaultWeightedEdge a =test.addEdge("1","2");
        DefaultWeightedEdge b = test.addEdge("1","3");
        DefaultWeightedEdge c =test.addEdge("4","3");
        DefaultWeightedEdge d = test.addEdge("4","1");
        DefaultWeightedEdge e = test.addEdge("3","6");
        DefaultWeightedEdge f =test.addEdge("3","5");
        DefaultWeightedEdge g = test.addEdge("5","4");
        DefaultWeightedEdge h =test.addEdge("5","7");
        DefaultWeightedEdge i =test.addEdge("6","2");
        DefaultWeightedEdge j =test.addEdge("6","5");
        DefaultWeightedEdge k = test.addEdge("7","6");
        DefaultWeightedEdge l = test.addEdge("7","8");
        DefaultWeightedEdge m= test.addEdge("8","5");
        test.setEdgeWeight(a, 4);
        test.setEdgeWeight(b,4);
        test.setEdgeWeight(c,2);
        test.setEdgeWeight(d,3);
        test.setEdgeWeight(e,-2);
        test.setEdgeWeight(f,4);
        test.setEdgeWeight(g,1);
        test.setEdgeWeight(h,-2);
        test.setEdgeWeight(i,3);
        test.setEdgeWeight(j,-3);
        test.setEdgeWeight(k,2);
        test.setEdgeWeight(l,2);
        test.setEdgeWeight(m,-2);

        ExchangeRatesGraph graphygraph = new ExchangeRatesGraph();
        graphygraph.exchangeGraph = test;
        BellmanFordShortestPath<String, DefaultWeightedEdge> bell = new BellmanFordShortestPath<>(test);
        for(String number: vertices) {
            try {
                ShortestPathAlgorithm.SingleSourcePaths<String, DefaultWeightedEdge> cycle = bell.getPaths("1");
            } catch (NegativeCycleDetectedException cycle) {
                System.out.println(cycle.getCycle().getVertexList());
            }
        }
        JohnsonSimpleCycles<String, DefaultWeightedEdge> johnsonSimpleCycles = new JohnsonSimpleCycles<>(test);
        List<List<String>> cycles = johnsonSimpleCycles.findSimpleCycles();
        Set<DefaultWeightedEdge> thing = test.edgeSet();
        ArrayList<Wrapper> edgesToTest = new ArrayList<>();

        List<List<String>> negativeCycles = new ArrayList<>();
        for(List<String> listy: cycles){
            listy.add(listy.get(0));

            System.out.println();
            double weightSum =0;
            for(int z = 0; z<listy.size()-1; z++){
                DefaultWeightedEdge y = test.getEdge(listy.get(z), listy.get(z+1));
                double aaaaaa = test.getEdgeWeight(y);
                weightSum += aaaaaa;
                System.out.print(listy.get(z) + "--->" + listy.get(z+1)+ "  " +aaaaaa + "   ");
            }
            System.out.println(weightSum);
            if(weightSum <0){
                negativeCycles.add(listy);
            }
        }
        for(List<String> negatives: negativeCycles){
            System.out.println(Arrays.toString(negatives.toArray()));
        }











    }

}
