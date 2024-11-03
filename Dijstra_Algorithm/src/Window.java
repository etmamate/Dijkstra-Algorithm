import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Window extends JPanel {

    public void WindowConfiguration(JFrame windowFrame) {

        JLabel mapa_title = new JLabel("Mapa do Brasil");
        ImageIcon mapa_Brasil = new ImageIcon(
                "C:\\Users\\mateus\\ws-java\\Dijkstra-Algorithm\\Dijkstra-Algorithm\\Dijstra_Algorithm\\utils\\image\\mapaBrasil.jpg");

        mapa_title.setBounds(360, 0, 84, 20);
        windowFrame.add(mapa_title);

        windowFrame.add(new JLabel(mapa_Brasil));
        windowFrame.setVisible(true);
        windowFrame.setSize(800, 800);
        windowFrame.setResizable(false);
        windowFrame.setTitle("PathFinder");
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
