


import java.util.ArrayList;


public class PriorityQueue {
    
    
    ArrayList<Path> paths = new ArrayList<>();
    
    
    public void add(String s, double d){
        
       paths.add(new Path(s,d));
    }
    
    public double getWeight(String s){
        double d = 0;
        
        for (int i = 0; i < paths.size(); i++) {
            if(paths.get(i).getName().equals(s)){
                d = paths.get(i).getLength();
            }
        }
        return d;
    }
    
    public Path getSmallestWeight(){
        
        Path p = paths.get(0);
        int index = 0;
        for (int i = 0; i < paths.size(); i++) {
            if(paths.get(i).getLength() < p.getLength() ){
                p = paths.get(i);
                index = i;
            }
        }
        paths.remove(index);
        return p;
    }
    
    public void decreaseWeight(String s, double d){
        
        for (int i = 0; i < paths.size(); i++) {
            if(paths.get(i).getName().equals(s)){
                paths.get(i).setLength(d);
            }
        }
    }
    
     public void setPrevious(String s, Path p){
        
        for (int i = 0; i < paths.size(); i++) {
            if(paths.get(i).getName().equals(s)){
                paths.get(i).setPrevious(p);
            }
        }
    }
    
}
