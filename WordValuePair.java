
import java.io.Serializable;
import java.util.ArrayList;


public class WordValuePair implements Serializable{
    
    ArrayList<String> words = new ArrayList<>();
    ArrayList<Integer> frequency = new ArrayList<>();
    
    public void setWords(ArrayList<String> w){
        words = w;
    }
    
     public void setFrequencies(ArrayList<Integer> f){
        frequency = f;
    }
     
     public ArrayList<String> getWords(){
         return words;
     }
     
      public ArrayList<Integer> getFrequency(){
         return frequency;
     }
}
