import javax.xml.soap.Node;

public class NodeWrapper {

    private WeightedDirectedNode weightedDirectedNode;
    private double weight;

    public NodeWrapper(double weight){
        weightedDirectedNode = null;
        this.weight = weight;
    }

    public WeightedDirectedNode getWeightedDirectedNode() {
        return weightedDirectedNode;
    }

    public void setWeightedDirectedNode(WeightedDirectedNode weightedDirectedNode) {
        this.weightedDirectedNode = weightedDirectedNode;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
