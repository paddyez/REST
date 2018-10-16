import org.paddy.gui.*;
import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        /*
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RestJFrame();
            }
        });
        SwingUtilities.invokeLater(() -> new RestJFrame());
        */
        SwingUtilities.invokeLater(RestJFrame::new);
    }
}
