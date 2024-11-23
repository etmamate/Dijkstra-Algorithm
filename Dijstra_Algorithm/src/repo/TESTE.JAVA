import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grafo extends JPanel {

    private List<No> nos;
    private List<Aresta> arestas;

    public Grafo() {
        nos = new ArrayList<>();
        arestas = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int x = random.nextInt(400) + 50;
            int y = random.nextInt(400) + 50;
            No no = new No("Cidade " + i, x, y);
            nos.add(no);
        }

        for (int i = 0; i < 15; i++) {
            int tamanho = nos.size();

            int no1Aleatorio = random.nextInt(tamanho);
            int no2Aleatorio = random.nextInt(tamanho);

            No n1 = nos.get(no1Aleatorio);
            No n2 = nos.get(no2Aleatorio);

            double pesoFixo = 5.0;

            if (n1 != n2) {
                Aresta aresta = new Aresta(n1, n2, pesoFixo);
                arestas.add(aresta);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (Aresta aresta : arestas) {
            No no1 = aresta.getNo1();
            No no2 = aresta.getNo2();
            g2d.drawLine(no1.getX(), no1.getY(), no2.getX(), no2.getY());
            float posX = (no2.getX() + no1.getX()) / 2;
            float posY = (no2.getY() + no2.getY()) / 2;
            g2d.drawString(String.valueOf(aresta.getPeso()), posX - 15, posY - 25);
        }

        for (No no : nos) {
            g2d.drawOval(no.getX(), no.getY(), 10, 10);
            g2d.drawString(no.getNome(), no.getX() - 10, no.getY() - 15);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Grafo grafo = new Grafo();
        frame.add(grafo);
        frame.setSize(500,  500);
        frame.setVisible(true);
    }
}