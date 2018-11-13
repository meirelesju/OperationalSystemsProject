import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

 
public class Main {

    public static ArrayList<Integer> lista = new ArrayList<>();
//Ler arquivo e adicionar na lista
    private static ArrayList<Integer> arquivo(String arq) {
        Scanner ler = null;
        try {
            ler = new Scanner(new File(arq));
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo \n");
            e.getMessage();
        }
        while (ler.hasNext()) {
            lista.add(ler.nextInt());
        }
        ler.close();
        return lista;
    }
    
//Algoritmo FIFO
    public static void FIFO() {
        //Essa variável sera utilizada pra indicar o numero de erros - ou miss
        int faltaPag = 0;
       // Esse numQuadros pega o primeiro elemento do array lista - Esse array é o que ega o valor que foi fornecido na entrada
        int quadrosDisp = lista.get(0);
       //Cria um array auxiliar - que vai ser necessário
        ArrayList<Integer> memoria = new ArrayList<>(quadrosDisp);
        int indice = 0;
                //enquanto tiver elementos na lista ele roda
	  	for(int i = 1; i < lista.size(); i++){ 
	  		//o numero da pagina vai sendo atualizado com o i
                        int numPagina = lista.get(i); 
	  		/*Se a "memória" não tiver os elementos da lista - ou seja, se a memória tiver vazia,
                        ele começa */
                        if (!memoria.contains(numPagina)) {
                            /*Se o tamanho do array memória for menor que o numero de quadros - ou seja, se a memória tiver vazia,
                            ele vai adicionar os elementos normalmente */
                            if (memoria.size() < quadrosDisp) {
	    				memoria.add(numPagina);
	    				//e da page miss
                                        faltaPag++;
	    				continue;
	   			}   //se a memória estiver cheia, ele vai começar a procurar a página que entrou primeiro 
                                    else {
	    				//e remover ela
                                        memoria.remove(indice);
	    				//e adiciona a que era pra entrar no  indice certo
                                        memoria.add(indice, numPagina);
	    				indice++;
	    				faltaPag++;
                                        //se o indice for igual aos quadros disponiveis
			    		if (indice == quadrosDisp) {
                                            //ele seta o valor pra 0
                                            indice = 0;
			    		}
                                }
	   		}	
	    }

	    System.out.println("FIFO " + faltaPag);
    }
	
//Algoritmo Otimo
    public static void OTM() {
    //Essa variável sera utilizada pra indicar o numero de erros - ou miss
    int faltaPag = 0;
    // Esse numQuadros pega o primeiro elemento do array lista - Que é o valor fornecido na entrada do programa
    int numQuadros = lista.get(0); 
    int[] distancia = new int[numQuadros];
    ArrayList<Integer> quadros = new ArrayList<>(numQuadros);
    int i = 0;
       //inicializa todos os quadros com -1 pois eles comecam com 0 e isso altera o resultado final
        for (int j = 0; j < numQuadros; j++) {quadros.add(-1);}
        
	    for (int ref = 1; ref < lista.size(); ref++) {
            /*Se a "memória" não tiver os elementos da lista - ou seja, se a memória tiver vazia,
            ele começa */
            int valor = lista.get(ref);
            	if (!quadros.contains(valor)) {
                //page miss
                faltaPag++;
                /*Se o tamanho do array memória for menor que o numero de quadros - ou seja, se a memória tiver vazia,
                ele vai adicionar os elementos normalmente */
                if (quadros.size() < numQuadros) {  
                    quadros.set(i, valor);
        }       //Se a memoria tiver cheia, hora de verificar qual quadro que vai sair
                else {
                    distancia = new int[numQuadros]; 
                    int target=0;
                     //seta o indice para 0
                     int indexOTM = 0;  
                     //Esse for vai varrer a memoria e vai atribuindo os valores em f
                   for (Integer f : quadros) {
                       for (int k = i; k < lista.size(); k++) {
                       	if (lista.get(k) == f){
                           break;
                       }
                       //caso contrário, incrementa a distância no indice target
                       distancia[target]++;
                     } 
                       target++;
                }
                    target = -1;
                    indexOTM = 0;
                    //a partir daqui ele vai procurar qual dos numeros do array distancia está mais longe
                    for (int j = 0; j < numQuadros; j++) { 
                        if (distancia[j] > target) {
			//atualiza o valor do target e o do índice
                            target = distancia[j]; 
                            indexOTM = j;
                        }   
                    }
                    //colocará na memória a página que precisa ser substituída no índice da página que tivesse mais distante
                    quadros.set(indexOTM, valor); 
                    //System.out.println(valor+" -> "+quadros);
                } 
                }i++;  
        }
        System.out.println("OTM " + faltaPag);     
    }
	
//Algoritmo Menos usado recentemente
    public static void LRU() {
        int faltaPag = 0;
        int numQuadros = lista.get(0);
        int clock = 0;
        int[] frequencia = new int[numQuadros];
        ArrayList<Integer> quadros = new ArrayList<>(numQuadros);  
        //inicia
        for (int i = 1; i < lista.size(); i++) {
            //atualiza o clock
            clock++;
            //valor vai conter o valor da lista no indice i
            int valor = lista.get(i);
            /*Se a "quadros" não tiver os elementos da lista - ou seja, se a memória tiver vazia,
            ele começa */
            if (!quadros.contains(valor)) {
                //ja da page miss
                faltaPag++;
                //Verifica quando os quadros tao cheios
                if (quadros.size() == (numQuadros)) {
                    // pega a frequencia do primeiro item do array de freq
                    int LRU = frequencia[0]; 
                    //seta o indice pra 0
                    int indexLRU = 0;   
                    
                    // vê qual é o menor
                    for (int j = 1; j < frequencia.length; j++) {
                        //se a frequencia no indice j for menor que lru
                        if (frequencia[j] < LRU) {
                            LRU = frequencia[j]; 
                            indexLRU = j;
                            }
                    } //vai colocar nos quadros o valor no indice da menor frequencia
                    quadros.set(indexLRU, valor);
                    //atualiza o clock
                    frequencia[indexLRU] = clock;
                } else {
                   //se nao tiver cheio, adiciona normalmente
                    quadros.add(valor); 
                   //a tabela de frequencia vai receber o clock no indice
                    frequencia[quadros.size() - 1] = clock;   
                    
                }
            } else {
		//page hit
                frequencia[quadros.indexOf(valor)] = clock; 
            }
        //System.out.println(valor+" -> "+quadros);
        }
        System.out.println("LRU " + faltaPag);
    }

    public static void main(String[] args) {
        arquivo("src/1.txt");
        
        FIFO();
        OTM();
        LRU();
    }
}
