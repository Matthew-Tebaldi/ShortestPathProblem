

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Gui extends javax.swing.JFrame {

    Graph g = new Graph();
    static String filePath = "Path to your file";

    public Gui() throws IOException, ClassNotFoundException {
        initComponents();

        if (fileEmpty()) {
            System.out.println("Loading Graph");
            g.buildGraph();
            
            Boolean update = g.update();
            if(update){
                save();
            }


            try {
                save();
            } catch (IOException ex) {
                Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Opening File");
            ObjectInputStream ois;

            try {
                FileInputStream streamIn = new FileInputStream(filePath);
                ois = new ObjectInputStream(streamIn);
                g = (Graph) ois.readObject();
            } catch (FileNotFoundException e) {
                e.printStackTrace();

            }
        }

        g.kruskalsAlg();
        
        ArrayList<String> names = g.getNames();

        for (int i = 0; i < names.size(); i++) {
            node1Box.addItem(names.get(i));
            node2Box.addItem(names.get(i));
        }
       
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        node1Box = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        node2Box = new javax.swing.JComboBox<>();
        shortestPathButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        resultPane = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Node 1:");

        jLabel2.setText("Node 2:");

        shortestPathButton.setText("Find Shortest Path");
        shortestPathButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shortestPathButtonActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(resultPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(289, 289, 289)
                                        .addComponent(shortestPathButton, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(node2Box, javax.swing.GroupLayout.PREFERRED_SIZE, 731, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(node1Box, javax.swing.GroupLayout.PREFERRED_SIZE, 731, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 357, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(node1Box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(node2Box, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(shortestPathButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void shortestPathButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shortestPathButtonActionPerformed

        String name1;
        String name2;
        String output;
        name1 = (String) node1Box.getSelectedItem();
        name2 = (String) node2Box.getSelectedItem();

        output = "Name 1 is " + name1 + " And name2 is " + name2;

        output = dijkstra(name1, name2);
        
        output = output + g.findClosestNode(name1) + g.findClosestNode(name2);

        resultPane.setText(output);
    }//GEN-LAST:event_shortestPathButtonActionPerformed

    public String dijkstra(String src, String des) {

        String s = "None";

        g.printEdges(src);

        ArrayList<String> vertices = new ArrayList<>();
        PriorityQueue queue = new PriorityQueue();

        vertices = g.getNames();

        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).equals(src)) {
                queue.add(src, 0);
            } else {
                queue.add(vertices.get(i), 999999999);
            }
        }

        Path u;
        ArrayList<Edge> edges = new ArrayList<>();
        double alt = 999999999;
        double dist = 0;

        while (!vertices.isEmpty()) {

            u = queue.getSmallestWeight();

            if (u.getName().equals(des)) {
                if (u.previous == null) {

                    return "No path available ";
                }

                Path v = u;
                ArrayList<String> output = new ArrayList<>();
                s = "\n\n       Path: \n\n";

                while (v.previous != null) {

                    v = v.previous;
                    output.add("    <" + v.getName() + ">                     Distance: " + v.getLength());
                }

                for (int i = 1; i <= output.size(); i++) {
                    s = s + output.get(output.size() - i) + "\n     | \n     v \n";
                }
                return s = s + "    <" + u.getName() + ">                     Distance: " + u.getLength();
            }

            edges = g.getEdges(u.getName());

            if (edges != null) {

                String current = "null";
                for (int i = 0; i < edges.size(); i++) {
                    current = edges.get(i).destName;
                    current = current.substring(6);

                    alt = u.length + edges.get(i).getWeight();

                    dist = queue.getWeight(current);

                    if (alt < dist) {
                        queue.decreaseWeight(current, alt);
                        queue.setPrevious(current, u);
                    }
                }
            }
        }

        return s;
    }
    
   

    public void save() throws IOException {

        try {
            FileOutputStream fout = new FileOutputStream(filePath, false);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(g);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public boolean fileEmpty() {

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            if (br.readLine() == null) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            return true;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<String> node1Box;
    private javax.swing.JComboBox<String> node2Box;
    private javax.swing.JTextPane resultPane;
    private javax.swing.JButton shortestPathButton;
    // End of variables declaration//GEN-END:variables
}
