

import java.util.ArrayList;

public class EdgeSet {

    ArrayList<TwoSidedEdge> edges = new ArrayList<>();

    public boolean containsEdge(String n1, String n2) {
        boolean bool = false;

        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).vertex1.equals(n1) && edges.get(i).vertex2.equals(n2)) {
                bool = true;
            }
            if (edges.get(i).vertex2.equals(n1) && edges.get(i).vertex1.equals(n2)) {
                bool = true;
            }
        }
        return bool;
    }

    public void addEdge(String s, Edge e) {
        String name1 = s;
        String name2 = e.getDestName();
        double d = e.getWeight();

        if (!name1.equals(name2)) {
            if (!containsEdge(name1, name2)) {
                edges.add(new TwoSidedEdge(name1, name2, d));
            }
        }
    }

    public TwoSidedEdge getSmallestEdge() {
        TwoSidedEdge e = edges.get(0);
        int index = 0;

        for (int i = 1; i < edges.size(); i++) {
            if (edges.get(i).weight < e.weight) {
                e = edges.get(i);
                index = i;
            }
        }
        edges.remove(index);
        return e;
    }

    public int size() {
        return edges.size();
    }
}
