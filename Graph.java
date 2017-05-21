

import java.io.Serializable;
import java.util.ArrayList;

public class Graph implements Serializable {

    WebParser parser = new WebParser();
    ArrayList<Node> nodes = new ArrayList<>();
    int nodeCount = 0;
    int edgeCount = 0;
    int foundNodeCount = 0;

    static String startUrl = "/wiki/Miklós_Ajtai";

    public ArrayList<String> getNames() {

        ArrayList<String> names = new ArrayList<>();
        String name;
        for (int i = 0; i < nodes.size(); i++) {
            name = nodes.get(i).getName();
            name = name.substring(6);
            names.add(name);
        }
        return names;
    }

    public void buildGraph() {

        Node n;
        Edge e;

        WebParser parser = new WebParser();
        n = parser.loadNode(startUrl);

        nodes.add(0, n);
        nodeCount++;

        ArrayList<String> urls = nodes.get(0).getUrl();

        System.out.println(" number of urls: " + urls.size());

        //Adds Five nodes from start url page
        for (int i = 0; i < urls.size(); i++) {

            if (nodeCount < 5) {

                Node node;
                node = parser.loadNode(urls.get(i));

                if (node != null && node.date != null) {

                    nodeCount = nodeCount + 1;
                    nodes.add(nodeCount - 1, node);
                    e = MakeEdge(nodes.get(0), nodes.get(nodeCount - 1));
                    nodes.get(0).addEdge(e);
                    edgeCount++;

                }

            }
        }
        //Adds the rest of the nodes till nodeCount is more than 500
        int i = 1;
        while (nodeCount < 500) {
            addNodes(i);
            i++;
            System.out.println("\n\n ----------I------ " + i);
        }
//Fills in the edges for all of the nodes
        for (int j = 0; j < nodes.size(); j++) {
            updateEdges(j);
        }
        System.out.println("\nThe Total amount of nodes for the graph is: " + nodeCount);
        System.out.println("The Total amount of edges for the graph is: " + edgeCount);

    }

    public void printNodeNames() {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i) != null) {
                System.out.println("Node at i " + i + " is equal to " + nodes.get(i).getName());
            }
        }
    }

    public void addNodes(int n) {

        int ind;
        Node node;

        ArrayList<String> urls = nodes.get(n).getUrl();
        System.out.println(" number of urls: " + urls.size());
        for (int i = 0; i < urls.size(); i++) {
            if (i < 25) {
                System.out.println("...");
                ind = isNode(urls.get(i));

                if (ind == -1) {

                    node = parser.loadNode(urls.get(i));
                    if (node.date != null) {

                        nodeCount = nodeCount + 1;
                        nodes.add((nodeCount - 1), node);
                        nodes.get(n).addEdge(MakeEdge(nodes.get(n), nodes.get((nodeCount - 1))));
                        edgeCount++;
                    }

                } else {
                    if (!nodes.get(n).isEdge(nodes.get(ind))) {
                        nodes.get(n).addEdge(MakeEdge(nodes.get(n), nodes.get((ind))));
                        edgeCount++;
                    }
                }
            }
        }
    }

    public void updateEdges(int n) {
        ArrayList<String> urls = nodes.get(n).getUrl();

        int ind;

        System.out.println(" number of urls: " + urls.size());
        for (int i = 0; i < urls.size(); i++) {

            ind = isNode(urls.get(i));

            if (ind == -1) {

            } else {
                if (!nodes.get(n).isEdge(nodes.get(ind))) {

                    nodes.get(n).addEdge(MakeEdge(nodes.get(n), nodes.get((ind))));
                    edgeCount++;
                }
            }
        }
    }

    public int isNode(String s) {

        int ind = -1;
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i) != null) {
                if (nodes.get(i).getName().equals(s)) {
                    ind = i;
                }
            }
        }
        return ind;
    }
//Makes edge and calculates weight based off of the frequency of words in the two webpages
    public Edge MakeEdge(Node n1, Node n2) {

        double weight = 0;
        int n = 0;
        Edge e;

        MyHash words1 = new MyHash();
        MyHash words2 = new MyHash();
        WordValuePair pair1 = new WordValuePair();
        WordValuePair pair2 = new WordValuePair();
        WordValuePair pair3 = new WordValuePair();
        ArrayList<String> wordList1 = new ArrayList<>();
        ArrayList<String> wordList2 = new ArrayList<>();
        ArrayList<Integer> freqList1 = new ArrayList<>();
        ArrayList<Integer> freqList2 = new ArrayList<>();
        words1 = n1.words;
        words2 = n2.words;

        pair1 = words1.getWordValuePair();
        pair2 = words2.getWordValuePair();

        wordList1 = pair1.getWords();
        wordList2 = pair2.getWords();
        freqList1 = pair1.getFrequency();
        freqList2 = pair2.getFrequency();

        String word;

        if (wordList1.size() < wordList2.size()) {
            for (int i = 0; i < wordList1.size(); i++) {

                word = wordList1.get(i);
                int j = wordList2.indexOf(word);
                if (j != -1) {

                    weight = weight + Math.pow((freqList1.get(i) - freqList2.get(j)), 2);
                    n = n + freqList1.get(i) + freqList2.get(j);
                    freqList2.remove(j);
                    wordList2.remove(j);
                } else {
                    weight = weight + Math.pow(freqList1.get(i), 2);
                    n = n + freqList1.get(i);

                }
            }

            for (int i = 0; i < wordList2.size(); i++) {
                weight = weight + Math.pow(freqList2.get(i), 2);
                n = n + freqList2.get(i);
            }
        } else {

            for (int i = 0; i < wordList2.size(); i++) {

                word = wordList2.get(i);
                int j = wordList1.indexOf(word);
                if (j != -1) {

                    weight = weight + Math.pow((freqList2.get(i) - freqList1.get(j)), 2);
                    n = n + freqList1.get(j) + freqList2.get(i);
                    freqList1.remove(j);
                    wordList1.remove(j);
                } else {
                    weight = weight + Math.pow(freqList2.get(i), 2);
                    n = n + freqList2.get(i);

                }
            }

            for (int i = 0; i < wordList1.size(); i++) {
                weight = weight + Math.pow(freqList1.get(i), 2);
                n = n + freqList1.get(i);
            }
        }

        weight = weight / n;
        e = new Edge(n2, weight);

        return e;

    }

    public void printEdges(int i) {
        Node n = nodes.get(i);
        ArrayList<Edge> edges = n.getEdges();
        for (int j = 0; j < edges.size(); j++) {
            System.out.println(n.getName() + "----" + edges.get(j).getWeight() + "---->" + edges.get(j).getDestName());
        }
    }

    public void printEdges(String s) {
        System.out.println(" printing edges for " + s);
        int i = isNode("/wiki/" + s);
        System.out.println("\n Node number: " + i);
        if (i != -1) {
            printEdges(i);
        }
    }

    public ArrayList<Edge> getEdges(String s) {
        ArrayList<Edge> edges = new ArrayList<>();
        String nodeName = "/wiki/" + s;

        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getName().equals(nodeName)) {
                edges = nodes.get(i).getEdges();
            }
        }
        return edges;

    }

    public void kruskalsAlg() {

        ArrayList<Set> sets = new ArrayList<>();
        Vertex[] vSet = new Vertex[nodes.size()];
        ArrayList<Edge> edges = new ArrayList<>();
        EdgeSet eSet = new EdgeSet();
        int numberOfVertices = nodes.size();

        for (int i = 0; i < nodes.size(); i++) {

            vSet[i] = new Vertex(nodes.get(i).getName());

            edges = nodes.get(i).getEdges();
            for (int j = 0; j < edges.size(); j++) {
                eSet.addEdge(nodes.get(i).getName(), edges.get(j));
            }
        }

        System.out.println("Number of eSet edges " + eSet.size());
        System.out.println("Number of nodes " + nodes.size());

        TwoSidedEdge e;
        Vertex v1;
        Vertex v2;
        int ind1 = -1;
        int ind2 = -1;
        boolean isCycle = false;

        Set set1 = new Set();
        Set set2 = new Set();
        Set set3 = new Set();

        int count = 0;

        while (count != (numberOfVertices - 1)) {

            e = eSet.getSmallestEdge();

            for (int i = 0; i < vSet.length; i++) {
                if (vSet[i].name.equals(e.vertex1)) {
                    ind1 = i;
                }
                if (vSet[i].name.equals(e.vertex2)) {
                    ind2 = i;
                }
            }

            v1 = vSet[ind1];
            v2 = vSet[ind2];

            for (int i = 0; i < sets.size(); i++) {
                set3 = sets.get(i);
                if (set3.localSet.contains(v1.name) && set3.localSet.contains(v2.name)) {
                    isCycle = true;
                }
            }

            if (!isCycle) {

                int s1 = -1;
                for (int i = 0; i < sets.size(); i++) {
                    if (sets.get(i).localSet.contains(v1.name)) {
                        set1 = sets.get(i);
                        s1 = i;
                    }
                }

                int s2 = -1;
                for (int i = 0; i < sets.size(); i++) {
                    if (sets.get(i).localSet.contains(v2.name)) {
                        set2 = sets.get(i);
                        s2 = i;
                    }
                }

                if (s1 == -1 && s2 == -1) {
                    Set s = new Set();
                    s.localSet.add(v2.name);
                    s.localSet.add(v1.name);
                    sets.add(s);

                }

                if (s1 != -1 && s2 != -1) {

                    for (int i = 0; i < set2.localSet.size(); i++) {

                        set1.localSet.add(set2.localSet.get(i));
                    }
                    sets.remove(s2);
                }

                if (s1 == -1 && s2 != -1) {

                    set2.localSet.add(v1.name);
                }
                if (s1 != -1 && s2 == -1) {

                    set1.localSet.add(v2.name);
                }
                count++;
            }

            set1 = null;
            set2 = null;
            set3 = null;
            ind1 = -1;
            ind2 = -1;
            isCycle = false;
            v1 = null;
            v2 = null;
        }

        System.out.println("\n\nSets have a size of " + sets.size());
    }

    public String findClosestNode(String s) {
        String output = "\n\n\n The closest node to " + s + " is ";
        Node n1;
        Node n2;
        Node closest = null;

        Edge e;
        double weight = 99999999;

        int i = isNode("/wiki/" + s);

        if (i == -1) {
            output = "Node " + s + " was not found";
            return output;
        } else {
            n1 = nodes.get(i);
            for (int j = 0; j < nodes.size(); j++) {
                n2 = nodes.get(j);
                e = MakeEdge(n1, n2);
                if ((e.weight < weight) && !n2.getName().substring(6).equals(s)) {
                    weight = e.weight;
                    closest = n2;
                }
            }
        }

        output = output + closest.getName().substring(6) + ". They have a similarity value of: " + weight;

        return output;
    }

    public boolean update() {

        String oldDate;
        String newDate;
        Boolean bool = false;

        for (int i = 0; i < nodes.size(); i++) {

            oldDate = nodes.get(i).date;
            newDate = parser.getDate(nodes.get(i).getName());

            if (!oldDate.equals(newDate)) {
                nodes.get(i).words = parser.getHashMap(nodes.get(i).getName());
                bool = true;
            }
        }
        return bool;
    }

}
