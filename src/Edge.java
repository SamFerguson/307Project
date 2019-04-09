public class Edge {

    private int vertexSource;
    private int vertexEnd;
    private double weight;

    public Edge(int s, int e, double w){
        vertexEnd = e;
        vertexSource = s;
        weight = w;
    }

    public int getVertexSource() {
        return vertexSource;
    }

    public void setVertexSource(int vertexSource) {
        this.vertexSource = vertexSource;
    }

    public int getVertexEnd() {
        return vertexEnd;
    }

    public void setVertexEnd(int vertexEnd) {
        this.vertexEnd = vertexEnd;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
