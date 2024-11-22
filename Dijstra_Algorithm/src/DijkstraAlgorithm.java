import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JPanel;

public class DijkstraAlgorithm extends JPanel{
    private static final int NUM_STATES = 27; //Número de Estados do Brasil
    private static final String[] STATES = {
        // Abreviações dos estados brasileiros.
        "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT",
        "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RO", "RR",
        "RS", "SC", "SE", "SP", "TO"
    };

    /* 
    //Posições
    private static final int[][] positions = {
        {50, 100}, {200, 400}, {70, 50}, {100, 300}, // Ajustar as posições para cada estado.
        {400, 200}, {300, 150}, {350, 300}, {450, 200},
        {400, 250}, {200, 100}, {300, 300}, {500, 400},
        {600, 200}, {350, 50}, {550, 100}, {650, 150},
        {700, 200}, {800, 300}, {900, 100}, {750, 250},
        {200, 250}, {300, 50}, {450, 500}, {550, 550},
        {600, 450}, {750, 600}, {850, 100}
    };
    */

    private int [][] matrizAdj; // Matriz de Adjacencia para armazenar as distâncias entre os nós
    private int [] distances; // Vetor que guarda a menor distancia para cada nó.
    private int [] predecessors; // Vetor que armazena o nó anterior no caminho mais curto.
    private boolean[] visited; // Vetor que marca os nós já visitados.
    private int startNode; //Nó de origem escolhido pelo usuario.
    private int endNode; // Nó de destino escolhido peli usuário.
    private java.util.List<Integer> path; //Lista para armazenar o caminho mais curto.

    //Construtor da classe, inicializa o grafo, nós de origem e destino, e executa o algoritmo.
    public DijkstraAlgorithm(int [][] matrizAdj, int startNode, int endNode){
        this.matrizAdj = matrizAdj; //Define a matriz de adjacencia
        this.startNode = startNode;//Define o nó inicio 
        this.endNode = endNode; //Define o nó de destino
        this.distances = new int[NUM_STATES]; // Inicializa o vetor de distâncias.
        this.predecessors = new int[NUM_STATES]; // Inicializa o vetor de predecessores
        this.visited = new boolean[NUM_STATES]; // Inicializa o vetor de nós visitados
        this.path = new ArrayList<>(); // Inicializa a lista para armazenar os caminhos mais curtos
        Arrays.fill(distances, Integer.MAX_VALUE); // Define todas as distancias inicialmente como infinito.
        Arrays.fill(predecessors, -1); // Define todos predecessores como -1 (não definido);
        distances[startNode] = 0; //Define a distância do nó de origem como 0;
        
        runDijkstra();
        reconstructPath();
        }
                
        //Método que executo o algoritmo de Dijkstra
        private void runDijkstra() {
            for (int i = 0; i < NUM_STATES; i++) { //Itera para processar todos os nós
                int currentNode = getMinDistanceNode(); //Obtem o nó com a menor distância não visitado
                visited[currentNode] = true; // Marca o nó atual como visitado.

                for(int neighbor = 0; neighbor < NUM_STATES; neighbor++){ //Verifica se tem uma aresta válida e se o nó não foi visitado.
                    if (!visited[neighbor] && matrizAdj[currentNode] [neighbor] > 0) {
                        int newDist = distances[currentNode] + matrizAdj[currentNode][neighbor]; // calcula a nova distancia
                        if (newDist < distances[neighbor]) { //Atualiza se a nova distancia for menor.
                            distances[neighbor] = newDist;
                            predecessors[neighbor] = currentNode; //Define o predecessor
                        }
                    }

                }
            }
        }

        //Método para obter o nó não visitado com a menor distancia
        private int getMinDistanceNode(){
            int minDist = Integer.MAX_VALUE; // Inicializa com infinito
            int minNode = -1; // Iniciliza com o nó invalido
            for (int i = 0; i < NUM_STATES; i++) {
                if (!visited[i] && distances[i] < minDist) { // Verifica a menor distancia não visitada
                    minDist = distances[i];
                    minNode = i;
                }
            }
            return minNode; //Retorna o nó com menor distancia
        }

        // Método para reconstruir o caminho mais curto usando os predecessores.
        private void reconstructPath() {
            for (int at = endNode; at != -1; at = predecessors[at]) {
                path.add(0, at); // Adiciona os nós do caminho na ordem correta.
            }
        }
        
        
    
}
