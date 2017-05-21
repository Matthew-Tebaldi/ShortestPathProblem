

import java.io.Serializable;

public class Edge implements Serializable{

    double weight;
    Node dest;
    String destName;
    
    public Edge(Node d, double w){
        dest = d;
        weight = w;
        destName = d.getName();
    }
    
    public double getWeight() {
        return weight;
    }

    public Node getDest() {
        return dest;
    }
    public String getDestName(){
        return destName;
    }
}
