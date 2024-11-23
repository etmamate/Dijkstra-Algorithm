package repo;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.*;

public class DijkstraAlgorithm extends JPanel {
    private static final int NUM_STATES = 26; // Número de Estados do Brasil
    private static final String[] STATES = {
            // Abreviações dos estados brasileiros.
            "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT",
            "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RO", "RR",
            "RS", "SC", "SE", "SP", "TO"
    };

    // Posições
    private static final int[][] positions = {
            { 50, 100 }, { 200, 400 }, { 70, 50 }, { 100, 300 }, // Ajustar as posições para cada estado
            { 400, 200 }, { 300, 150 }, { 350, 300 }, { 450, 200 },
            { 400, 250 }, { 200, 100 }, { 300, 300 }, { 500, 400 },
            { 600, 200 }, { 350, 50 }, { 550, 100 }, { 650, 150 },
            { 700, 200 }, { 800, 300 }, { 900, 100 }, { 750, 250 },
            { 200, 250 }, { 300, 50 }, { 450, 500 }, { 550, 550 },
            { 600, 450 }, { 750, 600 }, { 850, 100 }
    };

    private int[][] matrizAdj; // Matriz de Adjacencia para armazenar as distâncias entre os nós
    private int[] distances; // Vetor que guarda a menor distancia para cada nó.
    private int[] predecessors; // Vetor que armazena o nó anterior no caminho mais curto.
    private boolean[] visited; // Vetor que marca os nós já visitados.
    private int startNode; // Nó de origem escolhido pelo usuario.
    private int endNode; // Nó de destino escolhido peli usuário.
    private java.util.List<Integer> path; // Lista para armazenar o caminho mais curto.

    // Construtor da classe, inicializa o grafo, nós de origem e destino, e executa
    // o algoritmo.
    public DijkstraAlgorithm(int[][] matrizAdj, int startNode, int endNode) {
        this.matrizAdj = matrizAdj; // Define a matriz de adjacencia
        this.startNode = startNode;// Define o nó inicio
        this.endNode = endNode; // Define o nó de destino
        this.distances = new int[NUM_STATES]; // Inicializa o vetor de distâncias.
        this.predecessors = new int[NUM_STATES]; // Inicializa o vetor de predecessores
        this.visited = new boolean[NUM_STATES]; // Inicializa o vetor de nós visitados
        this.path = new ArrayList<>(); // Inicializa a lista para armazenar os caminhos mais curtos
        Arrays.fill(distances, Integer.MAX_VALUE); // Define todas as distancias inicialmente como infinito.
        Arrays.fill(predecessors, -1); // Define todos predecessores como -1 (não definido);
        distances[startNode] = 0; // Define a distância do nó de origem como 0;

        runDijkstra();
        reconstructPath();
    }

    // Método que executo o algoritmo de Dijkstra
    private void runDijkstra() {
        for (int i = 0; i < NUM_STATES; i++) { // Itera para processar todos os nós
            int currentNode = getMinDistanceNode(); // Obtem o nó com a menor distância não visitado
            visited[currentNode] = true; // Marca o nó atual como visitado.

            for (int neighbor = 0; neighbor < NUM_STATES; neighbor++) { // Verifica se tem uma aresta válida e se o nó
                                                                        // não foi visitado.
                if (!visited[neighbor] && matrizAdj[currentNode][neighbor] > 0) {
                    int newDist = distances[currentNode] + matrizAdj[currentNode][neighbor]; // calcula a nova distancia
                    if (newDist < distances[neighbor]) { // Atualiza se a nova distancia for menor.
                        distances[neighbor] = newDist;
                        predecessors[neighbor] = currentNode; // Define o predecessor
                    }
                }

            }
        }
    }

    // Método para obter o nó não visitado com a menor distancia
    private int getMinDistanceNode() {
        int minDist = Integer.MAX_VALUE; // Inicializa com infinito
        int minNode = 1; // Iniciliza com o nó invalido
        for (int i = 0; i < NUM_STATES; i++) {
            if (!visited[i] && distances[i] < minDist) { // Verifica a menor distancia não visitada
                minDist = distances[i];
                minNode = i;
            }
        }
        return minNode; // Retorna o nó com menor distancia
    }

    // Método para reconstruir o caminho mais curto usando os predecessores.
    private void reconstructPath() {
        for (int at = endNode; at != -1; at = predecessors[at]) {
            path.add(0, at); // Adiciona os nós do caminho na ordem correta.
        }
    }

    // Desenha os nós no mapa
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Chama o método de pintura padrão.
        Graphics2D g2d = (Graphics2D) g; // Converte o gráfico para Graphics2D.

        // Desenha o mapa de fundo.
        Image map = new ImageIcon("mapaBrasil.png").getImage(); // Carrega a imagem do mapa.
        g2d.drawImage(map, 0, 0, getWidth(), getHeight(), this); // Desenha o mapa no painel.

        // Desenha as arestas entre os estados.
        g2d.setColor(Color.BLACK); // Define a cor das arestas.
        for (int i = 0; i < NUM_STATES; i++) {
            for (int j = 0; j < NUM_STATES; j++) {
                if (matrizAdj[i][j] > 0) { // Verifica se há conexão entre os nós.
                    int x1 = positions[i][0]; // Posição X do primeiro nó.
                    int y1 = positions[i][1]; // Posição Y do primeiro nó.
                    int x2 = positions[j][0]; // Posição X do segundo nó.
                    int y2 = positions[j][1]; // Posição Y do segundo nó.
                    g2d.drawLine(x1, y1, x2, y2); // Desenha a linha entre os nós.
                }
            }
        }

        // Desenha os nós (estados) no mapa.
        for (int i = 0; i < NUM_STATES; i++) {
            int x = positions[i][0]; // Coordenada X do nó.
            int y = positions[i][1]; // Coordenada Y do nó.
            g2d.setColor(Color.WHITE); // Cor de preenchimento do nó.
            g2d.fill(new Ellipse2D.Double(x - 15, y - 15, 30, 30)); // Desenha um círculo preenchido.
            g2d.setColor(Color.BLACK); // Cor da borda do nó.
            g2d.draw(new Ellipse2D.Double(x - 15, y - 15, 30, 30)); // Desenha a borda do círculo.
            g2d.drawString(STATES[i], x - 10, y + 5); // Desenha a sigla do estado dentro do nó.
        }

        // Destaca o caminho mais curto encontrado.
        g2d.setColor(Color.RED); // Define a cor do caminho mais curto.
        for (int i = 0; i < path.size() - 1; i++) {
            int x1 = positions[path.get(i)][0]; // Posição X do nó atual no caminho.
            int y1 = positions[path.get(i)][1]; // Posição Y do nó atual no caminho.
            int x2 = positions[path.get(i + 1)][0]; // Posição X do próximo nó no caminho.
            int y2 = positions[path.get(i + 1)][1]; // Posição Y do próximo nó no caminho.
            g2d.drawLine(x1, y1, x2, y2); // Desenha a linha do caminho mais curto.
        }
    }

    // Método principal para inicializar a interface gráfica.
    public static void main(String[] args) {
        int[][] graph = new int[27][27]; // Inicializa a matriz de adjacência (exemplo real deve ser preenchido).
        JFrame frame = new JFrame("Mapa do Brasil - Dijkstra"); // Cria o JFrame.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Configura o fechamento da janela.
        frame.setSize(1000, 700); // Define o tamanho da janela.

        ImageIcon mapa_Brasil = new ImageIcon(
                "C:\\Users\\mateus\\ws-java\\Dijkstra-Algorithm\\Dijkstra-Algorithm\\Dijstra_Algorithm\\utils\\image\\mapaBrasil.jpg");

        // Painel para entrada de dados do usuário.
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout()); // Define o layout do painel.
        JTextField startField = new JTextField(2); // Campo de entrada para o nó de origem.
        JTextField endField = new JTextField(2); // Campo de entrada para o nó de destino.
        JButton calculateButton = new JButton("Calcular"); // Botão para calcular o caminho.

        inputPanel.add(new JLabel("Origem (0-22):")); // Rótulo para o nó de origem.
        inputPanel.add(startField); // Adiciona o campo de entrada para origem.
        inputPanel.add(new JLabel("Destino (0-22):")); // Rótulo para o nó de destino.
        inputPanel.add(endField); // Adiciona o campo de entrada para destino.
        inputPanel.add(calculateButton); // Adiciona o botão de calcular.

        frame.add(inputPanel, BorderLayout.SOUTH); // Adiciona o painel de entrada na parte inferior da janela.
        
        frame.revalidate(); // Atualiza a interface gráfica.
        calculateButton.addActionListener(e -> { // Adiciona ação ao botão de calcular.
            int startNode = Integer.parseInt(startField.getText()); // Lê o nó de origem inserido.
            int endNode = Integer.parseInt(endField.getText()); // Lê o nó de destino inserido.
            frame.add(new DijkstraAlgorithm(graph, startNode, endNode),BorderLayout.CENTER); // Adiciona o painel do mapa com o cálculo.
        });

        frame.setVisible(true); // Torna a janela visível.
    }

}
