
//import javafx.beans.property.SetPropertyBase;
import org.json.JSONObject;
import org.omg.CORBA.portable.InputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Set;

import org.jgrapht.graph.*;
import static java.lang.Math.log;



public class ExchangeRatesGraph {

    private String currencyTag;
    private static String[] currencies = {"usd", "jpy", "chf", "eur", "gbp", "aoa", "yer", "pyg"};
    private static DefaultDirectedWeightedGraph<String, DefaultWeightedEdge> exchangeGraph;

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
                    System.out.println(-1*(log(s.getDouble("rate"))));
                    //adds an edge between every vertex from the current one to each of the others
                    DefaultWeightedEdge temp = exchangeGraph.addEdge(currency, otherCurrency);
                    //sets that edges weight to be the thing it should be
                    exchangeGraph.setEdgeWeight(temp,-1*(log(s.getDouble("rate"))));
                }
            }
        }
    }

    public static double bellmanFord(Set<String> vertices, Set<DefaultWeightedEdge> edges, int source){

        double[] distance = new double[vertices.size()];
        String[] previous = new String[vertices.size()];

        // Set weight to infinity for all edges, then assigns NULL to the previous vertex
        for(int i = 0; i < vertices.size(); i++){

            distance[i] = Double.POSITIVE_INFINITY;
            previous[i] = null;
        }

        distance[source] = 0; // Sets distance from start to itself to 0 (duh)

        // "relaxes" edges
        for(int i =1; i<vertices.size(); i++){
            for(DefaultWeightedEdge e : edges){
                String start = exchangeGraph.getEdgeSource(e);
                String end = exchangeGraph.getEdgeTarget(e);
                //NOTE: I just don't know what to assign start and end as
                //  I know they need to be ints but I'm using what I have as a place holder.
                //  If we can get the indices of a vertex and use those to get the edges then thats what
                //  they need to be but I'm not sure how
                Double weight = exchangeGraph.getEdgeWeight(e);

                if(distance[start] + weight < distance[end]) {
                    distance[end] = distance[start] + weight;
                    previous[end] = start;
                }
            }
            /*// Step 2: relax edges repeatedly
            for i from 1 to size(vertices)-1:
            for each edge (u, v) with weight w in edges:
            if distance[u] + w < distance[v]:
            distance[v] := distance[u] + w
            predecessor[v] := u*/
        }

        for(DefaultWeightedEdge e : edges) {
            String start = exchangeGraph.getEdgeSource(e);
            String end = exchangeGraph.getEdgeTarget(e);
            //NOTE: I just don't know what to assign start and end as
            //  I know they need to be ints but I'm using what I have as a place holder.
            //  If we can get the indices of a vertex and use those to get the edges then thats what
            //  they need to be but I'm not sure how
            Double weight = exchangeGraph.getEdgeWeight(e);

            if (distance[start] + weight < distance[end]) {
                System.out.println("Negative cycle detected: Great!");
            }
            /*// Step 3: Check for negative-weight cycles
            for each edge (u, v) with weight w in edges:
            if distance[u] + w < distance[v]:
            error "Graph contains a negative-weight cycle"

            return distance[], predecessor[]*/
        }



        return distance, previous;
        //NOTE: Multiple returns? Not to sure about this either.
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
        System.out.println(exchangeGraph.vertexSet());
    }

}
