
//import javafx.beans.property.SetPropertyBase;
import org.jgrapht.Graphs;
import org.jgrapht.alg.cycle.JohnsonSimpleCycles;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.BellmanFordShortestPath;
import org.jgrapht.alg.shortestpath.NegativeCycleDetectedException;
import org.json.JSONObject;
import org.omg.CORBA.portable.InputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.*;
import static java.lang.Math.log;



public class ExchangeRatesGraph {

    private String currencyTag;
    private static String[] currencies = {"usd" , "jpy", "chf", "aoa", "gbp", "eur", "pyg"};
    public static DefaultDirectedWeightedGraph<String, DefaultWeightedEdge> exchangeGraph;

    public static void buildDirectedGraph(){
        //make the graph
        exchangeGraph = new DefaultDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        //make the vertices
        for(String currency: currencies){
            exchangeGraph.addVertex(currency);
        }
        //okay now you have all the vertices
    }

    public static void addWeights() throws Exception{
        for(String currency: currencies) {
            System.out.println("this is the currency that is currently being compared    " + currency);
            //make temporary currency array without the one you're currently on
            String[] otherCurrencies = new String[currencies.length-1];
            int index = 0;
            for(int i = 0; i<currencies.length;i++){
                //if the current currency in array isn't the one currently processed
                //add it to the otherCurrencies array and incrememnt index
                if(!currencies[i].equals(currency)){
                    otherCurrencies[index] = currencies[i];
                    index++;
                }
            }
            URL url = null;
            try {
                url = new URL("http://www.floatrates.com/daily/"+currency+".json");
            } catch (Exception e) {
            }
            //open the connection
            URLConnection con = url.openConnection();
            //get input stream
            InputStreamReader read = new InputStreamReader((con.getInputStream()));
            //get a result from the stream
            BufferedReader read2 = new BufferedReader(read);
            //the first line of the stream ought to be the json from the webpage
            String rates = read2.readLine();
            while (read2.readLine() != null) {
            }
            read2.close();

            for(String otherCurrency:otherCurrencies){
                //if the json isn't empty
                if (rates != null && rates.length() > 0) {
                    //make the json object for the whole plaintext
                    JSONObject tempJSON = new JSONObject(rates);
                    //get the object from that file with that name
                    //make this string variable against the other ones
                    JSONObject s = tempJSON.getJSONObject(otherCurrency);
                    System.out.print(s.getString("code")+":  ");
                    System.out.println(s.getDouble("rate"));
                    //adds an edge between every vertex from the current one to each of the others
                    DefaultWeightedEdge temp = exchangeGraph.addEdge(currency, otherCurrency);
                    //sets that edges weight to be the thing it should be
                    exchangeGraph.setEdgeWeight(temp,-1*log(s.getDouble("rate")));
                }
            }
        }
    }



    public BellmanReturn BellmanFordAlgorithm(Set<String> vertices, Set<DefaultWeightedEdge> edges, String vertexSource){

        //possible ideas to get past our indexing problem:
        //1.)pass the set of vertices through a wrapper to wrap them with an index yeah lets do that.
        //make n array of vertices with an index
        Vertices[] vertices1 = new Vertices[vertices.size()];
        int j = 0;
        //for each vertex in the set of vertices
        for(String vertex: vertices){
            //add them to the wrapper array with an index
            vertices1[j] = new Vertices(vertex, j);
            j++;
        }

        Edge[] e = new Edge[edges.size()];
        j = 0;
        //for each edge, get the source, find the int value of the source, find the int value of the edge
        for(DefaultWeightedEdge edge:edges){
            String source= exchangeGraph.getEdgeSource(edge);
            String end = exchangeGraph.getEdgeTarget(edge);
            double weight = exchangeGraph.getEdgeWeight(edge);
            Vertices s = vertices1[0];
            int i = 0;
            //iterate until you find source vertex
            while(!s.getVertex().equals(source)){
                s = vertices1[i];
                i++;
            }
            Vertices testEnd = vertices1[0];
            i = 0;
            while(!testEnd.getVertex().equals(end)){
                testEnd = vertices1[i];
                i++;
            }

            //now you have the index of the start vertex, end vertex, and weight
            //and thus you can make an edge that works with the edges loop
            e[j] = new Edge(s.getIndex(), testEnd.getIndex(), weight);
            j++;
        }

        //make arrays of the distances and predecessor
        double[] distances = new double[vertices1.length];
        String[] predecessor = new String[vertices1.length];
        //STEP 1!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        for(int i = 0; i < vertices1.length; i++){
            distances[i] = Double.POSITIVE_INFINITY;
            predecessor[i] = null;
        }
        //the source one is just the first one in the thing.
        distances[0] = 0;
        predecessor[0] = vertices1[0].getVertex();
        System.out.println(Arrays.toString(distances));
        System.out.println(Arrays.toString(predecessor));
        //the relax sTEP 2!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //for each vertex in the vertex array with indices
        System.out.println(e.length);
        for(int i = 0; i<vertices1.length; i++){
            //for each edge in the graph with start u and end v
            System.out.println(Arrays.toString(distances));
            int z = 0;
            for(Edge uv: e){
                int u = uv.getVertexSource();
                int v = uv.getVertexEnd();
                System.out.println(i+1 + "    " + z + "       " + Arrays.toString(distances));
                System.out.println("\t\t\t\t\t\tu=   " + u + " v=   " +v);
                if(distances[u] + uv.getWeight() + 10e-10 < distances[v]){
                    distances[v] = distances[u] + uv.getWeight();
                    //for each vertex in the vertices thing
                    //if the int v = the index of a vertex then that's the string to set predecessor
                    for(Vertices v2: vertices1){
                        if(v == v2.getIndex()){
                            predecessor[v] = v2.getVertex();
                        }
                    }
                }
                z += 1;
            }
        }
        /*
        for each edge (u, v) with weight w in edges:
        if distance[u] + w < distance[v]:
        error "Graph contains a negative-weight cycl
         */
        for(Edge uv:e){
            int u = uv.getVertexSource();
            int v = uv.getVertexEnd();
            if(distances[u] + uv.getWeight() < distances[v]){
                throw new RuntimeException();
            }
        }

        return new BellmanReturn(predecessor, distances);
    }



    public static void main(String[] args){
        //this makes the vertexes
        buildDirectedGraph();
        try{
            //this makes the edges and their weights
            addWeights();
        }catch (Exception e){
        }
        for(DefaultWeightedEdge e : exchangeGraph.edgeSet()){
            System.out.println(exchangeGraph.getEdgeSource(e) + " --> " + exchangeGraph.getEdgeTarget(e)+ "   " + exchangeGraph.getEdgeWeight(e));
        }
        System.out.println("SAM THE STUFF ABOVE THIS IS NOT A PART OF WHAT WE ARE LOOKING AT!!!!!");
        ExchangeRatesGraph e = new ExchangeRatesGraph();
        BellmanFordShortestPath<String, DefaultWeightedEdge> bell = new BellmanFordShortestPath<>(exchangeGraph, 10e-6);
        for(String currency: currencies) {
            try {
                ShortestPathAlgorithm.SingleSourcePaths<String, DefaultWeightedEdge> cycle = bell.getPaths(currency);
            } catch (NegativeCycleDetectedException cycle) {
                System.out.println("hey pal you got a cycle");
            }
        }
        JohnsonSimpleCycles<String, DefaultWeightedEdge> johnsonSimpleCycles = new JohnsonSimpleCycles<>(exchangeGraph);
        List<List<String>> cycles = johnsonSimpleCycles.findSimpleCycles();
        Set<DefaultWeightedEdge> thing = exchangeGraph.edgeSet();
        ArrayList<Wrapper> edgesToTest = new ArrayList<>();

        List<List<String>> negativeCycles = new ArrayList<>();
        for(List<String> listy: cycles){
            listy.add(listy.get(0));

            double weightSum =0;
            for(int z = 0; z<listy.size()-1; z++){
                DefaultWeightedEdge y = exchangeGraph.getEdge(listy.get(z), listy.get(z+1));
                double aaaaaa = exchangeGraph.getEdgeWeight(y);
                weightSum += aaaaaa;

            }
            if(weightSum < -1e-7){
                negativeCycles.add(listy);
            }
        }
        for(List<String> negatives: negativeCycles){
            System.out.println(Arrays.toString(negatives.toArray()));
        }


    }

}
