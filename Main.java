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
            //printa os pagemiss
	    System.out.println("FIFO " + faltaPag);
    }
//Algoritmo Otimo
    public static void OTM() {
    //Essa variável sera utilizada pra indicar o numero de erros - ou miss
    int faltaPag = 0;
    // Esse quadrosDisp pega o primeiro elemento do array lista - Que é o valor fornecido na entrada do programa
    int numQuadros = lista.get(0);
    //Esse frequencia é um array de ints do tamanho do quadrosDisp que vai armazenar 
    int[] distancia = new int[numQuadros];
    //Cria um array auxiliar - que vai ser necessário
    ArrayList<Integer> quadros = new ArrayList<>(numQuadros);
    //esse i incrementa toda vez que o otimo roda
    int i = 0;
       //inicializa todos os quadors com -1 pois eles comecam com 0 e isso altera o resultado final
        for (int j = 0; j < numQuadros; j++) {quadros.add(-1);}
        //Percorre o array lista e armazena os valores em ref 
        
        /* O ERRO ESTAVA AQUIIIIIIIIIIIIIIIIIIIIIIIII 
        ESSE FOR TAVA PEGANDO TODOS OS VALORES DO ARQUIVO DE ENTRADA E SETANDO NA MEMORIA, SO QUE O PRIMEIRO ELEMENTO
        QUE TA NO ARQUIVO É O NUMERO DE PAGINAS DISPONIVEIS, OU SEJA 
        ELE NAO ENTRA NA MEMÓRIA!!!!!!!!!!!!!!!!!!!!
        */
        
        //for(Integer ref : lista)
        for (int ref = 1; ref < lista.size(); ref++) {
            /*Se a "memória" não tiver os elementos da lista - ou seja, se a memória tiver vazia,
            ele começa */
            
            int valor = lista.get(ref);
            
            
            if (!quadros.contains(valor)) {
                //page miss
                faltaPag++;
                System.out.println(" page miss"+faltaPag);
              
                /*Se o tamanho do array memória for menor que o numero de quadros - ou seja, se a memória tiver vazia,
                ele vai adicionar os elementos normalmente */
                if (quadros.size() < numQuadros) {  
                    quadros.set(i, valor);
        }       //Se a memoria tiver cheia, hora de verificar qual quadro que vai sair
                else {
                    //essa distância vai iniciar com 13 em todos os elementos, e vai sendo atualizada toda vez que forem encontrados os numeros que estao na memória
                    distancia = new int[numQuadros]; 
                    int target=0;
                     //seta o indice para 0
                     int indexOTM = 0;  
                     //Esse for vai varrer a memoria e vai atribuindo os valores em f
                   for (Integer f : quadros) {
                       //esse for vai percorrer a lista --- k = numero atual da vez que o programa ta rodando
                       for (int k = i; k < lista.size(); k++) {
                           //System.out.println("k "+k);
                       //se o numero da lista no indice k for igual a f, ele breka
                           if (lista.get(k) == f){
                           break;
                       }
                       //caso contrário, incrementa a distância no indice target
                       distancia[target]++;
                     } 
                       target++;
                }
                     //reseta o target pra -1
                    target = -1;
                    indexOTM = 0;
                    //System.out.println("distancia " +Arrays.toString(distancia));
                    //Pronto, a partir daqui ele vai procurar qual dos numeros do array distancia está mais longe
                    for (int j = 0; j < numQuadros; j++) { 
                        //System.out.println("procurando o mais longe ");
                        //se o elemento da distancia no indice j for maior que o target
                        if (distancia[j] > target) {
                           // System.out.println("se "+distancia[j]+"for maior que"+target);
                            //atualiza o valor do target e o do índice
                            target = distancia[j]; 
                            indexOTM = j;
                           // System.out.println("target = "+distancia[j]);
                           // System.out.println("index = "+j);
                        }   
                    }
                    //colocará na memória a página que precisa ser substituída no índice da página que tivesse mais distante
                    quadros.set(indexOTM, valor); 
                 
                    System.out.println(valor+" -> "+quadros);
                    
                } 
                }i++;  
        }
        //printa os page miss
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
            //System.out.println("frequencia fora" +Arrays.toString(frequencia));
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
                            //atualiza o lru e o indice
                           // System.out.println("frequencia dentro" +Arrays.toString(frequencia));
                           // System.out.println("se "+frequencia[j]+"for menor que"+LRU);
                            LRU = frequencia[j]; 
                            indexLRU = j;
                            //System.out.println("lru = "+frequencia[j]);
                           // System.out.println("index = "+j);
                        }
                    } //vai colocar nos quadros o valor no indice da menor frequencia
                    quadros.set(indexLRU, valor);
                    //atualiza o clock
                    frequencia[indexLRU] = clock; 
                    //System.out.println("lru " +LRU);
                    
                } else {
                   //se nao tiver cheio, adiciona normalmente
                    quadros.add(valor); 
                   //a tabela de frequencia vai receber o clock no indice
                    frequencia[quadros.size() - 1] = clock;   
                    
                }
            } else {
                // só atualiza a tabela de frequencia com o clock
                //page hit
                frequencia[quadros.indexOf(valor)] = clock; 
            }
        //System.out.println(valor+" -> "+quadros);
        }
        //printa os page miss
        System.out.println("LRU " + faltaPag);
    }

    public static void main(String[] args) {
        arquivo("src/1.txt");
        
        FIFO();
        OTM();
        LRU();
    }
}