public class BellmanFord {

    class Edge{
        protected int source;
        protected int destination;
        protected int weight;
    }

    class Graph {
        protected int vs;
        protected int es;

        protected Edge[] edges;

        public Graph(int v, int e){
            vs = v;
            es = e;
        }


    }




}
