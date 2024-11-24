import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class DijkstraVisualization extends JPanel {
    // Define o número de vértices do grafo (número de estados brasileiros).

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

    // Construtor da classe, onde o grafo e a interface gráfica são inicializados.
    public DijkstraVisualization() {
        inicializarGrafo(); // Inicializa o grafo com as arestas e pesos fornecidos
        configurarJanela(); // Configura a janela e eventos de interação
    }

    // Método que inicializa o grafo adicionando as arestas e seus respectivos
    // pesos.
    private void inicializarGrafo() {
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

    // Método para adicionar uma aresta entre dois vértices no grafo.
    private void adicionarAresta(String origem, String destino, int peso) {
        // Adiciona uma aresta ao grafo
        int i = Arrays.asList(ESTADOS).indexOf(origem);
        int j = Arrays.asList(ESTADOS).indexOf(destino);
        grafo[i][j] = peso;
        grafo[j][i] = peso; // Grafo não direcionado
    }

    // Configura a janela principal e solicita ao usuário os vértices de origem e
    // destino.
    private void configurarJanela() {
        JFrame frame = new JFrame("Algoritmo de Dijkstra");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define ação ao fechar janela.
        frame.setSize(800, 900); // Define o tamanho da janela.
        frame.add(this); // Adiciona o painel de desenho (esta classe) na janela.
        frame.setVisible(true); // Torna a janela visível.

        // Ação do usuário a seleção do estado de origem
        String origem = (String) JOptionPane.showInputDialog(
                frame, "Selecione o estado de origem:",
                "Entrada", JOptionPane.PLAIN_MESSAGE, null, ESTADOS, ESTADOS[0]);
        // Ação do usuário a seleção do estado de destino.
        String destino = (String) JOptionPane.showInputDialog(
                frame, "Selecione o estado de destino:",
                "Entrada", JOptionPane.PLAIN_MESSAGE, null, ESTADOS, ESTADOS[1]);

        // Executa o algoritmo de Dijkstra para encontrar o menor caminho.
        if (origem != null && destino != null) {
            // Executa o algoritmo de Dijkstra para encontrar o menor caminho
            menorCaminho = dijkstra(Arrays.asList(ESTADOS).indexOf(origem), Arrays.asList(ESTADOS).indexOf(destino));
            repaint(); // Atualiza a interface gráfica para destacar o caminho
        }
    }

    // Algoritmo de Dijkstra para calcular o menor caminho entre dois vértices.
    private List<Integer> dijkstra(int origem, int destino) {
        int[] dist = new int[NUM_NODOS]; // Array que armazena as distâncias mínimas.
        boolean[] visitados = new boolean[NUM_NODOS]; // Array para marcar vértices visitados.
        int[] anterior = new int[NUM_NODOS]; // Array para reconstruir o caminho.

        // Inicializa as distâncias como infinito.
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[origem] = 0; // A distância do vértice de origem é 0.

        // Loop principal do algoritmo.
        for (int i = 0; i < NUM_NODOS; i++) {
            int u = -1; // Encontra o vértice não visitado com menor distância.
            for (int j = 0; j < NUM_NODOS; j++) {
                if (!visitados[j] && (u == -1 || dist[j] < dist[u])) {
                    u = j;
                }
            }
            if (dist[u] == Integer.MAX_VALUE)
                break; // Se a menor distância for infinita, sai do loop.

            visitados[u] = true; // Marca o vértice como visitado.

            // Atualiza as distâncias dos vizinhos do vértice atual.
            for (int v = 0; v < NUM_NODOS; v++) {
                if (grafo[u][v] != 0 && dist[u] + grafo[u][v] < dist[v]) {
                    dist[v] = dist[u] + grafo[u][v];
                    anterior[v] = u; // Salva o vértice anterior para reconstruir o caminho.
                }
            }
        }

        // Reconstrói o menor caminho a partir do vetor "anterior".
        List<Integer> caminho = new ArrayList<>();
        for (int v = destino; v != origem; v = anterior[v]) {
            caminho.add(v);
        }
        caminho.add(origem);
        Collections.reverse(caminho); // Inverte a ordem para ficar origem -> destino.
        return caminho;
    }

    // Método que desenha o grafo e o menor caminho na interface gráfica.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Chama o método original para limpar a tela.
        Graphics2D g2 = (Graphics2D) g;

        setBackground(new Color(3, 2, 8)); // Cor de fundo

        // Desenha todas as arestas do grafo.
        for (int i = 0; i < NUM_NODOS; i++) {
            for (int j = i + 1; j < NUM_NODOS; j++) {
                if (grafo[i][j] > 0) { // Verifica se há uma aresta entre os vértices.
                    Point p1 = POSICOES_FIXAS[i]; // Posições do vértice i.
                    Point p2 = POSICOES_FIXAS[j]; // Posições do vértice j.
                    g.setColor(menorCaminho.contains(i) && menorCaminho.contains(j) &&
                            Math.abs(menorCaminho.indexOf(i) - menorCaminho.indexOf(j)) == 1
                                    ? Color.BLUE // Define a cor da aresta quando é o menor caminho
                                    : Color.GRAY); // Define a cor da aresta
                    g2.setStroke(new BasicStroke(8)); // Configura a largura da aresta
                    g.drawLine(p1.x, p1.y, p2.x, p2.y); // Desenha a aresta
                }
                /*
                 * Foi realizado a repetição do código para fazer um efeito gráfico melhor.
                 * Segue a mesma lógica
                 */
                if (grafo[i][j] > 0) {
                    Point p1 = POSICOES_FIXAS[i];
                    Point p2 = POSICOES_FIXAS[j];

                    g.setColor(menorCaminho.contains(i) && menorCaminho.contains(j) &&
                            Math.abs(menorCaminho.indexOf(i) - menorCaminho.indexOf(j)) == 1
                                    ? Color.PINK
                                    : Color.DARK_GRAY);
                    g2.setStroke(new BasicStroke(4));
                    g.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }
        }

        // Desenhas os vertices
        for (int i = 0; i < NUM_NODOS; i++) {
            Point p = POSICOES_FIXAS[i]; // Posição dos vértices

            g.setColor(Color.WHITE); // Define a cor do vertice como branca
            g.fillOval(p.x - 10, p.y - 10, 20, 20); // Desenha o circulo do vertice

            g.setColor(Color.GREEN); // Define a cor do texto das Siglas
            g.drawString(ESTADOS[i], p.x - 10, p.y + 26); // Desenha o nome do estado
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DijkstraVisualization::new); // Inicia a aplicação
    }
}
