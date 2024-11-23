import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class DijkstraVisualization extends JPanel {
    private static final int NUM_NODOS = 26; // Número de estados brasileiros
    private static final String[] ESTADOS = { // Lista dos estados brasileiros
            "AC", "AL", "AP", "AM", "BA", "CE", "TO", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI",
            "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE"
    };

    // Posições fixas para cada estado (x, y)
    private static final Point[] POSICOES_FIXAS = {
            new Point(100, 280), // AC
            new Point(660, 310), // AL
            new Point(360, 100), // AP
            new Point(180, 220), // AM
            new Point(580, 370), // BA
            new Point(630, 170), // CE
            new Point(450, 300), // DF
            new Point(615, 438), // ES
            new Point(500, 400), // GO
            new Point(490, 180), // MA
            new Point(350, 430), // MT
            new Point(480, 535), // MS
            new Point(568, 438), // MG
            new Point(360, 180), // PA
            new Point(700, 220), // PB
            new Point(555, 600), // PR
            new Point(660, 245), // PE
            new Point(550, 220), // PI
            new Point(610, 480), // RJ
            new Point(700, 180), // RN
            new Point(470, 750), // RS
            new Point(200, 320), // RO
            new Point(120, 120), // RR
            new Point(530, 670), // SC
            new Point(570, 540), // SP
            new Point(640, 355) // SE
    };

    private final int[][] grafo = new int[NUM_NODOS][NUM_NODOS]; // Matriz de adjacência
    private List<Integer> menorCaminho = new ArrayList<>(); // Lista do menor caminho encontrado

    public DijkstraVisualization() {
        inicializarGrafo(); // Inicializa o grafo com as arestas e pesos fornecidos
        configurarJanela(); // Configura a janela e eventos de interação
    }

    private void inicializarGrafo() {
        // Adiciona conexões específicas ao grafo
        adicionarAresta("RS", "SC", 31);
        adicionarAresta("SC", "PR", 21);
        adicionarAresta("PR", "SP", 10);
        adicionarAresta("PR", "MS", 23);
        adicionarAresta("SP", "MS", 14);
        adicionarAresta("SP", "RJ", 27);
        adicionarAresta("SP", "MG", 24);
        adicionarAresta("MS", "MT", 11);
        adicionarAresta("MS", "GO", 16);
        adicionarAresta("MS", "MG", 15);
        adicionarAresta("RJ", "MG", 11);
        adicionarAresta("RJ", "ES", 24);
        adicionarAresta("ES", "BA", 23);
        adicionarAresta("ES", "MG", 18);
        adicionarAresta("BA", "GO", 18);
        adicionarAresta("BA", "TO", 10);
        adicionarAresta("BA", "PI", 27);
        adicionarAresta("BA", "PE", 19);
        adicionarAresta("BA", "AL", 37);
        adicionarAresta("BA", "SE", 29);
        adicionarAresta("BA", "MG", 16);
        adicionarAresta("SE", "AL", 13);
        adicionarAresta("PE", "PB", 10);
        adicionarAresta("PE", "CE", 19);
        adicionarAresta("PE", "PI", 32);
        adicionarAresta("PE", "AL", 16);
        adicionarAresta("PB", "RN", 10);
        adicionarAresta("PB", "CE", 11);
        adicionarAresta("RN", "CE", 19);
        adicionarAresta("CE", "PI", 15);
        adicionarAresta("PI", "MA", 13);
        adicionarAresta("PI", "TO", 11);
        adicionarAresta("MA", "PA", 15);
        adicionarAresta("MA", "TO", 34);
        adicionarAresta("PA", "AP", 27);
        adicionarAresta("PA", "RR", 27);
        adicionarAresta("PA", "AM", 12);
        adicionarAresta("PA", "MT", 28);
        adicionarAresta("TO", "GO", 20);
        adicionarAresta("TO", "MT", 23);
        adicionarAresta("TO", "PA", 13);
        adicionarAresta("GO", "MT", 28);
        adicionarAresta("GO", "MG", 29);
        adicionarAresta("MT", "RO", 40);
        adicionarAresta("RO", "AC", 18);
        adicionarAresta("AM", "RR", 33);
        adicionarAresta("AM", "AC", 35);
        adicionarAresta("AM", "RO", 15);
        adicionarAresta("AM", "MT", 18);
    }

    private void adicionarAresta(String origem, String destino, int peso) {
        // Adiciona uma aresta ao grafo
        int i = Arrays.asList(ESTADOS).indexOf(origem);
        int j = Arrays.asList(ESTADOS).indexOf(destino);
        grafo[i][j] = peso;
        grafo[j][i] = peso; // Grafo não direcionado
    }

    private void configurarJanela() {
        JFrame frame = new JFrame("Algoritmo de Dijkstra");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 900);
        frame.setResizable(false);
        frame.add(this);
        frame.setVisible(true);

        String origem = (String) JOptionPane.showInputDialog(
                frame, "Selecione o estado de origem:",
                "Entrada", JOptionPane.PLAIN_MESSAGE, null, ESTADOS, ESTADOS[0]);
        String destino = (String) JOptionPane.showInputDialog(
                frame, "Selecione o estado de destino:",
                "Entrada", JOptionPane.PLAIN_MESSAGE, null, ESTADOS, ESTADOS[1]);
        
        if (origem != null && destino != null) {
            // Executa o algoritmo de Dijkstra para encontrar o menor caminho
            menorCaminho = dijkstra(Arrays.asList(ESTADOS).indexOf(origem), Arrays.asList(ESTADOS).indexOf(destino));
            repaint(); // Atualiza a interface gráfica para destacar o caminho
        }
    }

    private List<Integer> dijkstra(int origem, int destino) {
        int[] dist = new int[NUM_NODOS]; // Array de distâncias mínimas
        boolean[] visitados = new boolean[NUM_NODOS]; // Array de nodos visitados
        int[] anterior = new int[NUM_NODOS]; // Array para reconstruir o caminho

        Arrays.fill(dist, Integer.MAX_VALUE); // Inicializa as distâncias com valor máximo
        dist[origem] = 0; // Distância para o nodo de origem é 0

        for (int i = 0; i < NUM_NODOS; i++) {
            int u = -1;
            for (int j = 0; j < NUM_NODOS; j++) {
                if (!visitados[j] && (u == -1 || dist[j] < dist[u])) {
                    u = j;
                }
            }
            if (dist[u] == Integer.MAX_VALUE)
                break;

            visitados[u] = true;

            for (int v = 0; v < NUM_NODOS; v++) {
                if (grafo[u][v] > 0 && !visitados[v]) {
                    int novaDist = dist[u] + grafo[u][v];
                    if (novaDist < dist[v]) {
                        dist[v] = novaDist;
                        anterior[v] = u;
                    }
                }
            }
        }

        List<Integer> caminho = new ArrayList<>();
        for (int v = destino; v != origem; v = anterior[v]) {
            caminho.add(v);
        }
        caminho.add(origem);
        Collections.reverse(caminho);
        return caminho;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        setBackground(new Color(3,2,8)); //Cor de fundo

        for (int i = 0; i < NUM_NODOS; i++) {
            for (int j = i + 1; j < NUM_NODOS; j++) {
                if (grafo[i][j] > 0) {
                    Point p1 = POSICOES_FIXAS[i];
                    Point p2 = POSICOES_FIXAS[j];
                    g.setColor(menorCaminho.contains(i) && menorCaminho.contains(j) &&
                            Math.abs(menorCaminho.indexOf(i) - menorCaminho.indexOf(j)) == 1
                                    ? Color.GREEN
                                    : Color.GRAY);
                    g2.setStroke(new BasicStroke(8));
                    g.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
                if (grafo[i][j] > 0) {
                    Point p1 = POSICOES_FIXAS[i];
                    Point p2 = POSICOES_FIXAS[j];

                    g.setColor(menorCaminho.contains(i) && menorCaminho.contains(j) &&
                            Math.abs(menorCaminho.indexOf(i) - menorCaminho.indexOf(j)) == 1
                                    ? Color.YELLOW
                                    : Color.DARK_GRAY);
                    g2.setStroke(new BasicStroke(4));
                    g.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }
        }

        for (int i = 0; i < NUM_NODOS; i++) {
            Point p = POSICOES_FIXAS[i];

            g.setColor(Color.WHITE);
            g.fillOval(p.x - 10, p.y - 10, 20, 20);
            g.setColor(Color.WHITE);
            g.drawString(ESTADOS[i], p.x - 10, p.y + 25);
            g.drawString(ESTADOS[i], p.x - 11, p.y + 26);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DijkstraVisualization::new); // Inicia a aplicação
    }
}
