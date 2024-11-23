import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class DijkstraVisualization extends JPanel {
    private static final int NUM_NODOS = 26; // Número de estados brasileiros
    private static final String[] ESTADOS = { // Lista dos estados brasileiros
            "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI",
            "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE"
    };

    // Posições fixas para cada estado (x, y)
    private static final Point[] POSICOES_FIXAS = {
            new Point(100, 0), // AC
            new Point(200, 100), // AL
            new Point(100, 50), // AP
            new Point(70, 150), // "AM"
            new Point(580, 370), // "BA"
            new Point(400, 150), // "CE"
            new Point(350, 300), // "DF"
            new Point(615, 438), // ES
            new Point(500, 400), // GO
            new Point(200, 180), // MA
            new Point(350, 430), // MT
            new Point(480, 535), // MS
            new Point(568, 438), //MG
            new Point(100, 180), //PA
            new Point(500, 150), //PB
            new Point(555, 600), //PR
            new Point(500, 200), //PE
            new Point(400, 230), //PI
            new Point(610, 480), //RJ
            new Point(520, 200), //RN
            new Point(470, 750), //RS
            new Point(150, 200), //RO
            new Point(80, 120), //RR
            new Point(530, 670), //SC
            new Point(570, 540), //SP
            new Point(500, 350) //SE
    };

    private final int[][] grafo = new int[NUM_NODOS][NUM_NODOS]; // Matriz de adjacência representando pesos das arestas
    private List<Integer> menorCaminho = new ArrayList<>(); // Lista do menor caminho encontrado pelo Dijkstra

    public DijkstraVisualization() {
        gerarPesosAleatorios(); // Gera pesos aleatórios para as arestas do grafo
        configurarJanela(); // Configura a janela e eventos de interação
    }

    private void gerarPesosAleatorios() {
        Random rand = new Random();
        for (int i = 0; i < NUM_NODOS; i++) {
            for (int j = i + 1; j < NUM_NODOS; j++) {
                // Define pesos aleatórios entre 10 e 40 para as arestas
                int peso = rand.nextInt(31) + 10;
                grafo[i][j] = peso;
                grafo[j][i] = peso; // Grafo não direcionado
            }
        }
    }

    private void configurarJanela() {
        JFrame frame = new JFrame();
        frame.setTitle("Algoritmo de Dijkstra - Visualização");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 900);
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
            // Encontra o nodo não visitado com a menor distância
            int u = -1;
            for (int j = 0; j < NUM_NODOS; j++) {
                if (!visitados[j] && (u == -1 || dist[j] < dist[u])) {
                    u = j;
                }
            }
            if (dist[u] == Integer.MAX_VALUE)
                break; // Nenhum caminho disponível

            visitados[u] = true;

            // Atualiza as distâncias para os vizinhos de u
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

        // Reconstrói o caminho mais curto
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

        // Desenha as arestas
        for (int i = 0; i < NUM_NODOS; i++) {
            for (int j = i + 1; j < NUM_NODOS; j++) {
                if (grafo[i][j] > 0) {
                    Point p1 = POSICOES_FIXAS[i];
                    Point p2 = POSICOES_FIXAS[j];
                    g.setColor(menorCaminho.contains(i) && menorCaminho.contains(j) &&
                            Math.abs(menorCaminho.indexOf(i) - menorCaminho.indexOf(j)) == 1
                                    ? Color.RED
                                    : Color.GRAY);
                    g.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }
        }

        // Desenha os nodos
        for (int i = 0; i < NUM_NODOS; i++) {
            Point p = POSICOES_FIXAS[i];
            g.setColor(Color.RED);
            g.fillOval(p.x - 10, p.y - 10, 20, 20);
            g.setColor(Color.BLACK);
            g.drawString(ESTADOS[i], p.x - 10, p.y + 25);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DijkstraVisualization::new); // Inicia a aplicação
    }
}
