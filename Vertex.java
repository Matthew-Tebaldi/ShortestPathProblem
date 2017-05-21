
public class Vertex {
    String name;
    Vertex parent;
    Vertex child;
    
    public Vertex(String s){
        name = s;
    }
   
    public void setParent(Vertex v){
        parent = v;
    }
    
    public void setChild(Vertex c){
        child = c;
    }
}
