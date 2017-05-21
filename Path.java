


import java.util.ArrayList;


public class Path {
    
    int name;
    
    String vertexName;
    ArrayList<String> history = new ArrayList<>();
    Path previous;
    
    
    double length;
    
    public Path(String s, double d){
        vertexName = s;
        length = d;
    }
    public double getLength(){
        return length;
    }
    
    public String currentVertex(){
        String s = null;
        
        s = history.get(history.size()-1);
        
        return s;
    }
    
    
    
    public void setLength(double d){
        length = d;
    }

    public String getName(){
        return vertexName;
    }
    
    public void setName(String s){
        vertexName = s;
    }
    
    public void setPrevious(Path p){
        previous = p;
    }

}
