

import java.awt.EventQueue;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            try {
                Gui gForm = new Gui();
                gForm.setVisible(true);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
        });

    }

}
