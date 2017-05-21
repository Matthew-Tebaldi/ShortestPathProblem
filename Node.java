

import java.io.Serializable;
import java.util.ArrayList;

public class Node implements Serializable {

    String name;
    String date;
    ArrayList<String> urls = new ArrayList<>();
    MyHash words = new MyHash();
    ArrayList<Edge> edges = new ArrayList<>();

    public Node(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public void setWords(MyHash h) {
        words = h;
    }

    public ArrayList getUrl() {
        return urls;
    }

    public void setUrls(ArrayList l) {
        urls = l;
    }

    public void addUrl(String u) {
        urls.add(u);
    }

    public void addEdge(Edge e) {
        edges.add(e);
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public boolean isEdge(Node n) {

        boolean bool = false;

        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).destName.equals(n.getName())) {

                bool = true;
            }
        }
        return bool;
    }

}
