

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebParser implements Serializable {

    public Node loadNode(String s) {

        MyHash hashMap = new MyHash();
        ArrayList urls = new ArrayList();
        String title = null;
        String date = null;

        try {

            Document doc = Jsoup.connect("https://en.wikipedia.org" + s).get();
            title = s;
            Element content = doc.getElementById("content");
            Elements links = content.getElementsByTag("a");

            Elements paragraphs = content.getElementsByTag("p");

             Element nDate = doc.getElementById("footer-info-lastmod");
            date = nDate.text();
            System.out.println("\n\n\n\n DATE " + date);
            
            for (Element p : paragraphs) {
                String linkHref = p.text();

                String delims = "[ .,!;:()?!@#$%^&*-/]+";
                String[] words = linkHref.split(delims);
                for (int i = 0; i < words.length; i++) {
                    String word = words[i];
                    hashMap.put(word, 1);
                }
            }

            for (Element link : links) {
                String linkHref = link.attr("href");

                if (isWikiLink(linkHref)) {
                    if (!linkHref.equals(s)) {
                        urls.add(linkHref);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Node current = new Node(title);
        current.setUrls(urls);
        current.setWords(hashMap);
        current.date = date;
        
        

        return current;

    }
    
    public String getDate(String s) {

        String date = null;

        try {

            Document doc = Jsoup.connect("https://en.wikipedia.org" + s).get();
            Element nDate = doc.getElementById("footer-info-lastmod");
            date = nDate.text();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return date;
    }

    public MyHash getHashMap(String s) {
        MyHash hash = new MyHash();

        try {

            Document doc = Jsoup.connect("https://en.wikipedia.org" + s).get();
            Element content = doc.getElementById("content");
            Elements paragraphs = content.getElementsByTag("p");

            for (Element p : paragraphs) {
                String linkHref = p.text();

                String delims = "[ .,!;:()?!@#$%^&*-/]+";
                String[] words = linkHref.split(delims);
              
                for (int i = 0; i < words.length; i++) {
                    String word = words[i];
                    hash.put(word, 1);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return hash;
    }


    public boolean isWikiLink(String str) {

        if (str.contains("/wiki/")) {
            if (str.contains(":") || str.contains("#") || str.contains("%") || str.contains(".")) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }

    }

}
