import java.util.ArrayList;

public class WeightedDirectedNode {

    //this is the set of nodes and their corresponding weights
    private ArrayList<NodeWrapper> wraps;
    //inside of it would be wrappers with references to other nodes and then weights


    public ArrayList<NodeWrapper> getWraps() {
        return wraps;
    }

    public void setWraps(ArrayList<NodeWrapper> wraps) {
        this.wraps = wraps;
    }
}
