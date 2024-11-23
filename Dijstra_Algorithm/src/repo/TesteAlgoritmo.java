package repo;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TesteAlgoritmo {
    private static final int RR  = 0;
    private static final int AP = 1;
    private static final int AM = 2;
    private static final int AC = 3;
    private static final int RO = 4;
    private static final int MT = 5;
    private static final int MS = 6;
    private static final int GO = 7;
    private static final int TO = 8;
    private static final int PA = 9;
    private static final int RS = 10;
    private static final int SC = 11;
    private static final int PR = 12;
    private static final int SP = 13;
    private static final int RJ = 14;
    private static final int ES = 15;
    private static final int MG = 16;
    private static final int BA = 17;
    private static final int SE = 18;
    private static final int AL = 19;
    private static final int PE = 20;
    private static final int PB = 21;
    private static final int CE = 22;
    private static final int RN = 23;
    private static final int PI = 24;
    private static final int MA = 25;

    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for(Integer numVertices: vertices[][] ){

        }
    }

    // private static int lerEstacao(final String tipo, final Scanner in) {
		
	// 	while (true) {
	// 		System.out.println(tipo+":");
	// 		String linha = in.nextLine().trim();
	// 		if (linha.isEmpty()) {
	// 			System.out.println("Até a próxima!");
	// 			System.exit(0);
	// 		}

	// 		try {
	// 			Integer estacao = Integer.parseInt(linha);
	// 			if (estacao >= 1 && estacao <= 26) {
	// 				return estacao-1;
	// 			}
	// 		}
	// 		catch (Exception e) {
	// 			System.out.println("Digite uma estação válida. Opções disponíveis: 1 a 26");
	// 		}			
	// 	}
	// }
    public static void main(String[] args) {
        Grafo grafo = new Grafo(26);

        //ESPIRITO SANTO 1
        grafo.criarArestas(ES, RJ, 24);
        grafo.criarArestas(ES, MG, 18);
        grafo.criarArestas(ES, BA, 23);

        //MG
        grafo.criarArestas(MG, ES, 18);
        grafo.criarArestas(MG, RJ, 11);
        grafo.criarArestas(MG, SP, 24);
        grafo.criarArestas(MG, MS, 15);
        grafo.criarArestas(MG, GO, 29);
        grafo.criarArestas(MG, BA, 16);

        //BA 2
        grafo.criarArestas(BA, ES, 23);
        grafo.criarArestas(BA, GO, 18);
        grafo.criarArestas(BA, MG, 16);
        grafo.criarArestas(BA, TO, 10);
        grafo.criarArestas(BA, PI, 27);
        grafo.criarArestas(BA, PE, 19);
        grafo.criarArestas(BA, AL, 37);
        grafo.criarArestas(BA, SE, 29);

        //RJ 3
        grafo.criarArestas(RJ, ES, 24);
        grafo.criarArestas(RJ, MG, 11);
        grafo.criarArestas(RJ, SP, 27);

        //SP 4
        grafo.criarArestas(SP, MG, 24);
        grafo.criarArestas(SP, RJ, 27);
        grafo.criarArestas(SP, PR, 10);
        grafo.criarArestas(SP, MS, 14);

        //MS 5
        grafo.criarArestas(MS, SP, 14);
        grafo.criarArestas(MS, PR, 23);
        grafo.criarArestas(MS, MG, 15);
        grafo.criarArestas(MS, GO, 16);
        grafo.criarArestas(MS, MT, 11);

        //PR 6
        grafo.criarArestas(PR, SP, 10);
        grafo.criarArestas(PR, MS, 23);
        grafo.criarArestas(PR, SC, 21);

        //SC 7
        grafo.criarArestas(SC, PR, 21);
        grafo.criarArestas(SC, RS, 31);

        //RS 8
        grafo.criarArestas(RS, SC, 31);

        //MT 9
        grafo.criarArestas(MT, RO, 40);
        grafo.criarArestas(MT, AM, 18);
        grafo.criarArestas(MT, PA, 28);
        grafo.criarArestas(MT, TO, 23);
        grafo.criarArestas(MT, GO, 28);
        grafo.criarArestas(MT, MS, 11);

        //RO 10
        grafo.criarArestas(RO, AM, 15);
        grafo.criarArestas(RO, AC, 18);
        grafo.criarArestas(RO, MT, 40);

        //AC 11
        grafo.criarArestas(AC, AM, 35);
        grafo.criarArestas(AC, RO, 18);

        //AM 12 
        grafo.criarArestas(AM, RR, 33);
        grafo.criarArestas(AM, PA, 13);
        grafo.criarArestas(AM, MT, 18);
        grafo.criarArestas(AM, RO, 15);
        grafo.criarArestas(AM, AC, 35);

        //RR 13 
        grafo.criarArestas(RR, AM, 33);
        grafo.criarArestas(RR, PA, 12);

        //AP 14
        grafo.criarArestas(AP, PA, 27);

        //MA 15
        grafo.criarArestas(MA, PA, 15);
        grafo.criarArestas(MA, PI, 15);
        grafo.criarArestas(MA, TO, 15);

        //PI 16
        grafo.criarArestas(PI, MA, 13);
        grafo.criarArestas(PI, TO, 11);
        grafo.criarArestas(PI, BA, 27);
        grafo.criarArestas(PI, PE, 32);
        grafo.criarArestas(PI, CE, 15);

        //CE 17
        grafo.criarArestas(CE, PI, 15);
        grafo.criarArestas(CE, PE, 19);
        grafo.criarArestas(CE, PB, 11);
        grafo.criarArestas(CE, RN, 19);

        //RN 18
        grafo.criarArestas(RN, CE, 19);
        grafo.criarArestas(RN, PB,10);

        //PB 19
        grafo.criarArestas(PB, RN, 10);
        grafo.criarArestas(PB, CE, 11);
        grafo.criarArestas(PB, PE, 10);

        //PE 20
        grafo.criarArestas(PE, PB, 10);
        grafo.criarArestas(PE, CE, 19);
        grafo.criarArestas(PE, PI, 32);
        grafo.criarArestas(PE, BA, 19);
        grafo.criarArestas(PE, AL, 16);

        //AL 21
        grafo.criarArestas(AL, SE, 16);
        grafo.criarArestas(AL, PE, 13);

        //SE 22
        grafo.criarArestas(SE, AL, 13);
        grafo.criarArestas(SE, BA, 29);

        //TO 23
        grafo.criarArestas(TO, PA, 13);
        grafo.criarArestas(TO, MA, 34);
        grafo.criarArestas(TO, PI, 11);
        grafo.criarArestas(TO, BA, 10);
        grafo.criarArestas(TO, GO, 20);
        grafo.criarArestas(TO, MT, 23);

        //PA 24

        grafo.criarArestas(PA, AM, 12);
        grafo.criarArestas(PA, RR, 27);
        grafo.criarArestas(PA, AP, 27);
        grafo.criarArestas(PA, MA, 15);
        grafo.criarArestas(PA, TO, 13);
        grafo.criarArestas(PA, MT, 28);

        //GO 25
        grafo.criarArestas(GO, MT, 28);
        grafo.criarArestas(GO, TO, 20);
        grafo.criarArestas(GO, BA, 18);
        grafo.criarArestas(GO, MG, 29);
        grafo.criarArestas(GO, MS, 26);

        

        // Scanner scn = new Scanner(System.in);
        // System.out.println("Algoritmo de Dijkstra");
        // System.out.println("--------------------------------------------------");

        // while (true) {
        //     System.out.println("Por favor, entre com sua rota ou precione ENTER para sair do programa");
        //     int origem = lerEstacao("Origem", scn);
        //     int destino = lerEstacao("Destino", scn);

        //     for(Integer estacao : grafo.caminhoMinimo(origem, destino)){
        //         System.out.print((estacao+1)+"-->");
        //     }
        //     System.out.println("fim da rota");
        // }

        JFrame windowFrame = new JFrame();
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
