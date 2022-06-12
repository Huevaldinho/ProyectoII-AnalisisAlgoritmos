/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoii.analisisalgoritmos;

/**
 *
 * @author Sophya Mc Lean Morales
 */
public class AlgoritmoGenetico {


    private int [] presentaciones;
    private boolean bandera = false;
    private int cantFilas;
    private int cantGeneraciones;
    private int cantHijos;
    private int cantPresentaciones;
    private int kilosSolicitados;
    private int filasComp;
    private static int[][] poblacionInicial;
    private int[][] hijosOneP; //Guarda los hijos que generen estos cruces
    private int[][] hijosTwoP; //Guarda los hijos que generen estos cruces
    private int[][] poblacionOneP; //Copia de la poblacion inicial para el cruce1
    private int[][] poblacionTwoP; //Copia de la poblacion inicial para el cruce2
    private int compTotal;
    private int asigTotal = 7;
    private static long startTimeTotal;
    private static long endTimeTotal;
    private static int memoriaConsumidaTotal = 15 * 32 + 1 + 6 * 64; //en bits
   
    private int compCruce1;
    private int asigCruce1 = 7;
    private static long startTimeCruce1;
    private static long endTimeCruce1;
    private static int memoriaConsumidaCruce1 = 15 * 32 + 1 + 6*64;//en bits
    
    private int compCruce2;
    private int asigCruce2 = 7;
    private static long startTimeCruce2;
    private static long endTimeCruce2;
    private static int memoriaConsumidaCruce2 =  15* 32 + 1 + 6*64;//en bits
    
    public AlgoritmoGenetico(int []present, int kilos) {
        this.kilosSolicitados = kilos;
        this.presentaciones = present; 
        this.cantPresentaciones = present.length;
        asigCruce2 += 5;
        asigCruce1 += 5;
        asigTotal += 5;
    }
    public AlgoritmoGenetico() {
    }

    
    /**
     * Ciclo para generar la cantidad que se usará de cada presentación
     * @return int [][]: Matriz con la población inicial. 
     */
    public int[][] poblacionInicial(){
        System.out.println("Kilos solicitados: " + kilosSolicitados);
        //Se trabaja con los tamaños base 3,5,6,9
        switch(cantPresentaciones){
            case 6:
                cantFilas = 11; //1 representa la fila de presentaciones las otras las combinaciones                
                cantGeneraciones = 20;
                cantHijos = 20;
                break;
            case 9: 
                cantFilas = 21;//1 por las presentaciones, las otras los diferentes individuos
                cantGeneraciones = 20;
                cantHijos = 40;
                break;
            default: 
                cantFilas = cantPresentaciones + 1;
                cantGeneraciones = 10;
                //en el caso de 3 son 6, en el caso de 5 son 10
                cantHijos = cantPresentaciones * 2;
                break;
        }
        compTotal += 3;
        asigCruce1+=3;
        asigCruce2 +=3;
        asigTotal +=3;

        //Se inicializan las mtrices que guardarán la información de los cruces
        hijosOneP = new int[cantHijos][cantPresentaciones+1];
        hijosTwoP = new int[cantHijos][cantPresentaciones+1];
        //Se inicializa la matriz que guarda la población inicial 
        poblacionInicial = new int [cantFilas][cantPresentaciones+1];  //Matriz que guarda la población incial      
        int elemento; 
        int faltante;
        asigCruce1+=5;
        asigCruce2 +=5;
        asigTotal +=5; 
        //memoria de las matrices
        memoriaConsumidaCruce1 += (32 * cantHijos * (cantPresentaciones+1)) * 2 + 32*cantFilas*(cantPresentaciones+1);
        memoriaConsumidaCruce2 += (32 * cantHijos * (cantPresentaciones+1)) * 2 + 32*cantFilas*(cantPresentaciones+1) ;;
        memoriaConsumidaTotal += (32 * cantHijos * (cantPresentaciones+1)) * 2 + 32*cantFilas*(cantPresentaciones+1) ; ;
        
        memoriaConsumidaCruce1 += 32 * 2;
        memoriaConsumidaCruce2 += 32 * 2;
        memoriaConsumidaTotal += 32 * 2;
        
        System.out.println("Cantidad presentaciones: " + cantPresentaciones);
        System.out.println("Cantidad de generaciones: " + cantGeneraciones);
        System.out.println("Cantidad de hijos: " + cantHijos);
        System.out.println("Población inicial: ");
        
        //int i = 0
        asigCruce1+= 1;
        asigCruce2 +=1;
        asigTotal +=1; 
        for(int i= 0; i<cantFilas;i++){
            asigCruce1 ++; //Incremento del for
            asigCruce2 ++; 
            asigTotal ++;
            compCruce1 ++;//True del for
            compCruce2++;
            compTotal++;
            System.out.print("|");
            int kilosIndividuo = 0;
            //La columna 0 de cada individuo guardará su fitness
            //Se representa con "-1" para saber que no ha sido cambiado
            poblacionInicial[i][0] = -1;
            asigCruce1+= 3; //kilos, poblacion[i][0] = -1, int j = 1
            asigCruce2+= 3;
            asigTotal+= 3;
            for(int j=1;j<cantPresentaciones+1;j++){
                asigCruce1 ++; //Incremento del for
                asigCruce2 ++; 
                asigTotal ++;
                compCruce1 ++;//True del for
                compCruce2++;
                compTotal++;
                
                if(i == 0){ //Es la fila de PRESENTACIONES
                   poblacionInicial[i][j] = presentaciones[j-1]; //j-1 porque matriz tiene 1 col más que presentaciones
                   System.out.print(poblacionInicial[i][j] + " ");
                    asigCruce1 ++; //poblacionInicial[i][j] = presentaciones
                    asigCruce2 ++; 
                    asigTotal ++;
                }
                else{ //Filas correspondientes a cada INDIVIDUO
                    //Se genera un número aleatorio entre 0 y la cantidad de kilos que se ocupan
                    //para así evitar obtener cantidades excesivamente grandes.
                    elemento = (int)(Math.random()*(kilosSolicitados+1));  
                    poblacionInicial[i][j] = elemento;
                    System.out.print(elemento + " ");
                    kilosIndividuo += elemento * presentaciones[j-1];
                    asigCruce1 += 3; 
                    asigCruce2 += 3; 
                    asigTotal += 3;
                }
                compCruce1 ++;//Comparación del if
                compCruce2++;
                compTotal++;
            }
            compCruce1 ++;//Comparación Falsa for "j"
            compCruce2++;
            compTotal++;
            
            //Revisar que elemento NO estén en la matriz de poblacionInicial

            if(i!=0){ //Si no es la fila de presentaciones. 
                faltante = funcionFitness(poblacionInicial[i]);
                //Asig,comp, memoria de la función Fitness
                memoriaConsumidaCruce1 += 32*2;
                memoriaConsumidaCruce2 += 32*2;
                asigCruce1 = 2 + 2*cantPresentaciones;
                asigCruce2 = 2 + 2*cantPresentaciones;
                compCruce1 = cantPresentaciones+1;
                compCruce2 = cantPresentaciones+1;
                asigCruce1 ++; 
                asigCruce2 ++; 
                //Comparación para asegurar que el individuo no tenga desperdicio cero
                // ni tenga menos kilos de los solicitados. 
                if(faltante<=0){ //Faltante negativo->menos kilos de los solicitados
                   //Se le asigna a la primera presentación una cantidad
                   //aleatoria que permita mantener la condición 
                   System.out.println("|");
                   System.out.println("Hubo un faltante de: " + faltante);
                   poblacionInicial[i][1] = Math.abs(faltante);
                   System.out.println("Cambio en el elemento "+ i + ","+1+":"+poblacionInicial[i][1]);
                   faltante = funcionFitness(poblacionInicial[i]);
                   System.out.println("\n Kilos del individuo " + i + " = " + kilosIndividuo);
                   //Asig,comp, memoria de la función Fitness
                   memoriaConsumidaCruce1 += 32*2;
                   memoriaConsumidaCruce2 += 32*2;

                   asigCruce1 = 2 + 2*cantPresentaciones;
                   asigCruce2 = 2 + 2*cantPresentaciones;

                   compCruce1 = cantPresentaciones+1;
                   compCruce2 = cantPresentaciones+1;

                   asigCruce1 ++; 
                   asigCruce2 ++; 

                   
                   asigCruce1 += 2; //poblacionInicial[][]  y faltante
                   asigCruce2 += 2; 

                } 
                compCruce1 ++;//Comparación del if "faltante"
                compCruce2++;
                compTotal++;
                //guardar el fitness en matriz 
                poblacionInicial[i][0] = faltante;
                System.out.println("|" + " Fitness: " + faltante);
                asigCruce1 ++; //poblacionInicial[i][0] = faltante;
                asigCruce2 ++; 
                asigTotal ++;
            }
            else{
            System.out.println("|");
            }
            compCruce1 ++;//Comparación del if "!=0"
            compCruce2++;
            compTotal++;
        }
        compCruce1 ++; //Comparación falsa
        compCruce2++;
        compTotal++;
        return poblacionInicial;
    }
    
    
     /**
     *Esta función obtiene cuánto le falta a la población para alcanzar los
     *kilos que me pide el usuario. 
     * @param individuo: Individuo al que se le desea calcular el fitness.
     * @return int: Npumero que representa cuántos kilos le faltan al individuo para alcanzar los solicitados.
     */
    //Cuánto consume función fitness
    //memoria = 32*2 asignaciones = 2 + 2*cantPresentaciones compaciones = cantPresetaciones+1
    public int funcionFitness(int[] individuo){
        int kilosIndividuo = 0;
        asigTotal += 2;  //int i, kilos
        memoriaConsumidaTotal += 32 * 2;
        for(int i=0; i<cantPresentaciones; i++){
           compTotal ++; //comparacion true
           //comp++; comp del for
           //asig+ = 2; incrementos y kilos.
           kilosIndividuo += individuo[i+1]* presentaciones[i];
           asigTotal += 2; //kilos e incremento 
        }
        compTotal ++; //falsa for
        //Una vez que se tiene la cantidad de kilos del individuo, 
        //se calcula cuántos le faltan para tener desperdicio 0 
        return kilosIndividuo - kilosSolicitados;
    }
    
    /**
     *  Metodo para calcular la combinacion que desperdicia menos arroz. Programacion Dinamica
     * @param flag: Variable entera que indica cuál matriz se desea ordenar. 
     * @param arregloOrdenar: Matriz que se desea ordenar.
     * @return int [][]: Matriz con el arreglo ordenado según el fitness.
     */
    public int[][] ordenarArreglo(int flag, int[][] arregloOrdenar){
        //ordenar según su fitness
        int tope; //para el primer ciclo
        int tope2; //para el segundo ciclo
        int inicio;
        asigTotal += 3; 
        memoriaConsumidaTotal += 32*3;
        switch (flag) {
            case 2:
                //Ordenar arreglo de 10 MEJORES
                inicio = 0;
                tope= filasComp;//**********
                tope2 = filasComp;//**********
                break;
            case 1:
                //Ordenar arreglo de hijos
                inicio = 0;
                tope=cantHijos;
                tope2 = cantHijos;
                break;
            default:
                //Ordenar poblacion inicial
                inicio = 1;
                tope = cantFilas-1;
                tope2=cantFilas;
                break;
        }
        compTotal+=3;
        asigTotal+=3;
        int[] temp; //arreglo temporal para saber por cual individuo(fila) va.
        memoriaConsumidaTotal+= 32*(cantPresentaciones+1);
        //memoriaConsumida += 32 * cantPresentaciones+1 + 32 * 2
        //peor de los casos es "cant hijos"
        //comparaciones= tope + 1 + tope*(1+(tope*comp)) 
        //asignaciones = tope + tope*(tope + 3)
        asigTotal++;
        memoriaConsumidaTotal += 32;
        for(int i=inicio;i<tope;i++) { //Se inicia en 1 porque la primera fila es de presentaciones
          compTotal++; //true for
          asigTotal += 2; //incremento y for "j"
          for(int j=i+1;j<tope2;j++) {
            compTotal++; //true for
            asigTotal ++; //incremento 
            //Se ordena de menor a mayor según el fitness de cada individuo
            //El fitness siempre está en col 0 de cada individuo.  
            if(arregloOrdenar[i][0] > arregloOrdenar[j][0]){
              //intercambia los lugares de cada individuo
              temp = arregloOrdenar[i];
              arregloOrdenar[i] = arregloOrdenar[j];
              arregloOrdenar[j] = temp;
              asigTotal += 3;
            }
            compTotal++;//comp if;
          }
          compTotal++;//comp falsa for;
        }
        compTotal++;//comp falsa for;
        return arregloOrdenar; 
    }    
    /**
     *  Metodo que se encarga de generar una copia de la población inicial para ser trabajado en cada cruce
     */    
    public void copiarPoblacion(){
        //primero se checkea que la matriz de población inical esté ordenado según FITNESS 
       ordenarArreglo(0,poblacionInicial);
       poblacionOneP = poblacionInicial.clone();
       poblacionTwoP = poblacionInicial.clone();
       asigTotal += 2;
    }

     /**
     *  Metodo para imprimir matrices
     */
    public void imprimirMatriz(int[][] matrizImprimir){ 
        for(int[]e: matrizImprimir){ //for fila matriz 
            imprimirIndividuo(e); //asignaciones * largo individuo
        }
    }
    
     /**
     *  Método que cruza todos los individuos de la población hasta 
     * obtener "n" hijos. Partiendo a los padres por un solo punto
     */
    //Se usará cruce en 1 punto(por la mitad) 
    public void cruceOnePoint(){
        startTimeCruce1 = System.nanoTime();
        System.out.println("~~~~~~~~~~~~~~CRUCE ONE POINT~~~~~~~~~~~~~~");
        //El punto de cruce será por la mitad de cada padre. 
        int puntoCruce = cantPresentaciones/2;
        int conteoHijos = 0;
        int[]padre1;
        int[]padre2;
        memoriaConsumidaCruce1 += 32 * 2 + (32*(cantPresentaciones+1)) * 2; 
        memoriaConsumidaTotal += 32 * 2 + (32*(cantPresentaciones+1)) * 2;
        //i= 1 es el mejor individuo de la población, por eso puede cruzarse con todos. 
        //i tiene que cruzarse con todos los individuos hasta tener n hijos.
        asigCruce1 += 1; //for int = 1;
        asigTotal += 1; //for int = 1;
        for(int i=1; i<cantFilas-1;i++){  //recorre cada individuo
            padre1 = poblacionOneP[i];
            asigCruce1 += 2; //for int j y padre1;
            asigTotal += 2; //for int j y padre1;
            for(int j=i+1; j<cantFilas;j++){ //lo cruza con los otros individuos.
              padre2 = poblacionOneP[j]; 
              asigCruce1 ++; //padre2
              asigTotal ++; //padre2
              //Si no se inicializan de nuevo se altera la matriz de hijos
              int[] hijo1 = new int[cantPresentaciones+1];
              int[] hijo2 = new int[cantPresentaciones+1];
              memoriaConsumidaCruce1 += (32 * (cantPresentaciones+1))* 2;
              memoriaConsumidaTotal += (32 * (cantPresentaciones+1))* 2;
              asigCruce1 += 2;
              asigTotal += 2;
              //HACER EL CRUCE
              //"CABEZA": Recorrer los elementos desde el primero(sin contar fitness) hasta
              //el punto de cruce y los asigna a los hijos.
              asigCruce1 += 1; //for k
              asigTotal += 1; //for k
              memoriaConsumidaCruce1 += 32;
              memoriaConsumidaTotal += 32;
              for(int k=1; k<= puntoCruce;k++){ 
                compCruce1 ++; //comparación true del for
                compTotal ++; //comparación true del for
                hijo1[k] = padre1[k];
                hijo2[k] = padre2[k];
                asigCruce1 += 3; //incremento del for, hijo 1, hijo 2
                asigTotal += 3; //incremento del for, hijo 1, hijo 2
              }
              compCruce1 ++; //comparacion falsa for
              compTotal ++; //comparacion falsa for
              //"COLA": Recorrer los elementos desde el punto cruce hasta
              //el final y los asigna a los hijos.
              asigCruce1++; //for m
              asigTotal++; //for m
              memoriaConsumidaCruce1 += 32;
              memoriaConsumidaTotal += 32;
              for(int m = puntoCruce+1;m<=cantPresentaciones; m++){
                compCruce1 ++; //comparación true del for
                compTotal ++; //comparación true del for
                hijo1[m] = padre2[m];
                hijo2[m] = padre1[m];
                asigCruce1 += 3; //incremento del for, hijo 1, hijo 2
              }
              compCruce1 ++; //comparacion falsa for
              compTotal ++; //comparacion falsa for
              //
              //agregar los hijos generados a la MATRIZ DE HIJOS;
              //Se asigna el fitness de los individuos
              hijo1[0] = funcionFitness(hijo1);
              hijo2[0] = funcionFitness(hijo2);
              asigCruce1+= 2; //asignacion hijos
              asigTotal+= 2; //asignacion hijos
             //ANTES de imprimir y agregar los HIJOS, revisar que NO estén
             //en la matriz de hijos y tampoco en la matriz de padres
                           //IMPRIMIR HIJOS Y PADRES
              System.out.println("TENTATIVO");
              System.out.print("Padre 1: ");
              imprimirIndividuo(padre1);
              System.out.print("Padre 2: ");
              imprimirIndividuo(padre2);
              System.out.print("Hijo 1: ");
              imprimirIndividuo(hijo1);
              System.out.print("Hijo 2: ");
              imprimirIndividuo(hijo2);
              System.out.println("--------------------------");
              conteoHijos += 2; 
              asigCruce1+= 2; //asignacion conteo hijos
              asigTotal+= 2; //asignacion conteo hijos
             if(revisarExiste(hijo1,poblacionOneP) || revisarExiste(hijo1,hijosOneP)){
                //Debe mutarse porque ya existe. HASTA que ya no esté en la población
                System.out.println("Se aplicará mutación porque el hijo 1 YA EXISTE");
                mutacion(hijo1,1);
             }
             compCruce1 ++; //comparacion if
             compTotal ++; //comparacion if
             //después de que se hace la mutación se agrega ese hijo
             //La matriz de hijos NO tiene fila de presentaciones. Inicia en 0 
             hijosOneP[conteoHijos-2] = hijo1;
             if(revisarExiste(hijo2,poblacionOneP) || revisarExiste(hijo2,hijosOneP)){
                System.out.println("Se aplicará mutación porque el hijo 2 YA EXISTE");
                //Debe mutarse porque ya existe. HASTA que ya no esté en la población
                mutacion(hijo2,1);
             }
             compCruce1 ++; //comparacion if
             compTotal ++; //comparacion if
             hijosOneP[conteoHijos-1] = hijo2;
             asigCruce1+= 2; //asignacion hijos
             asigTotal+= 2; //asignacion hijos
              //IMPRIMIR HIJOS Y PADRES
              System.out.println("FIJO");
              System.out.print("Padre 1: ");
              imprimirIndividuo(padre1);
              System.out.print("Padre 2: ");
              imprimirIndividuo(padre2);
              System.out.print("Hijo 1: ");
              imprimirIndividuo(hijo1);
              System.out.print("Hijo 2: ");
              imprimirIndividuo(hijo2);
              System.out.println("--------------------------");

              
              //revisar si alguno tiene fitness 0 y terminar todo. 
              if(hijo1[0] == 0 || hijo2[0] == 0){
                bandera = true;
                asigTotal ++; 
                asigCruce1 ++;
                System.out.println("PRESENTACIONES:");
                imprimirIndividuo(presentaciones);
                System.out.println("----Se encontró un individuo con DESPERDICIO 0----");
                if(hijo1[0] == 0){
                    imprimirIndividuo(hijo1);
                }
                else{
                    imprimirIndividuo(hijo2);
                }
                compCruce1 ++; //comparacion if
                compTotal ++; //comparacion if
                endTimeCruce1 += System.nanoTime() - startTimeCruce1; 
                return; //Se sale de la función
              }
              compCruce1 ++; //comparacion if
              compTotal ++; //comparacion if
              if(conteoHijos == cantHijos){
                break; //Se sale del 1ER FOR
              }
              compCruce1 ++; //comparacion if
              compTotal ++; //comparacion if
            }
            compCruce1 ++; //comparacion falsa for
            compTotal ++; //comparacion falsa for
            if(conteoHijos == cantHijos){
                break; //Se sale del 2do FOR
            }
            compCruce1 ++; //comparacion if
            compTotal ++; //comparacion if
        }
        compCruce1 ++; //comparacion falsa for
        compTotal ++; //comparacion falsa for
        //Ya se alcanzó la cantidad de hijos
        //ORDENAR matriz HIJOS según su FITNESS
        ordenarArreglo(1,hijosOneP); //Deja individuos a "borrar" en primeros lugares
        System.out.println("MATRIZ HIJOS OP.Ordenada");
        imprimirMatriz(hijosOneP);
        
        //hay que evaluarlos con sus papás.
        evaluarPoblacion(poblacionOneP,hijosOneP);  
        System.out.println("-----NUEVA POBLACION ONE POINT------");
        imprimirMatriz(poblacionOneP);
        endTimeCruce1 += System.nanoTime() - startTimeCruce1;
    }
    
     /**
     *  Método que cruza todos los individuos de la población hasta 
     * obtener "n" hijos. Partiendo a los padres por dos puntos
     */    
    public void cruceTwoPoints(){
        startTimeCruce2 = System.nanoTime();
        System.out.println("~~~~~~~~~~~~~~CRUCE TWO POINTS~~~~~~~~~~~~~~");
        //El punto de cruce serán los extremos y el centro. 
        int puntoCruce1 = 1;
        int puntoCruce2 = cantPresentaciones;
        int conteoHijos = 0;
        int[]padre1;
        int[]padre2;
        asigCruce2 += 4; //punto1, punto 2, conteo, int i
        asigTotal += 4*32; //punto1, punto 2, conteo, int i
        memoriaConsumidaCruce2 += 32 * 4;//punto1, punto 2, conteo, int i
        memoriaConsumidaTotal += 32 * 4;
        //i= 1 es el mejor individuo de la población, por eso puede cruzarse con todos. 
        //i tiene que cruzarse con todos los individuos hasta tener n hijos.
        for(int i=1; i<cantFilas-1;i++){  //recorre cada individuo
            compCruce2++; //comparacion True 
            compTotal++; //comparacion True 
            padre1 = poblacionTwoP[i];
            asigCruce2+= 3; //padre 1, incremneto, "j"
            asigTotal+= 3; 
            memoriaConsumidaCruce2 += 32;//int j for. 
            memoriaConsumidaTotal += 32;
            for(int j=i+1; j<cantFilas;j++){ //lo cruza con los otros individuos.
              compCruce2++; //comparacion True 
              compTotal++; //comparacion True  
              System.out.println("HIJOS TP ANTES CRUCE");
              imprimirMatriz(hijosTwoP);
              padre2 = poblacionTwoP[j]; 
              asigCruce2+= 2; //padre 2, incremneto,
              asigTotal+= 2; 
              //Si no se inicializan de nuevo se altera la matriz de hijos
              int[] hijo1 = new int[cantPresentaciones+1];
              int[] hijo2 = new int[cantPresentaciones+1];
              memoriaConsumidaCruce2 += 32 * (cantPresentaciones+1);//int j for. 
              memoriaConsumidaTotal += 32 * (cantPresentaciones+1);
              //HACER EL CRUCE
              //"CABEZA": Recorrer los elementos desde el primero(sin contar fitness) hasta
              //el punto de cruce y los asigna a los hijos. 
              asigCruce2++; // k = 1
              asigTotal++;
              memoriaConsumidaCruce2+=32;
              memoriaConsumidaTotal += 32;
              for(int k=1; k<= puntoCruce1;k++){ 
                compCruce2++; //comparacion True 
                compTotal++; //comparacion True  
                hijo1[k] = padre1[k];
                hijo2[k] = padre2[k];
                asigCruce2 += 3; //hijos e incremento 
                asigTotal += 3;
              }
              compCruce2++; //comparacion Falsa
              compTotal++; //comparacion Falsa  
              //"MEDIO": Recorre los elementos desde el 1er punto de cruce hasta el 2do
              for(int m = puntoCruce1+1;m<puntoCruce2; m++){
                compCruce2++; //comparacion True 
                compTotal++; //comparacion True   
                hijo1[m] = padre2[m];
                hijo2[m] = padre1[m];
                asigCruce2 += 3; //hijos e incremento 
                asigTotal += 3;
              }
              compCruce2++; //comparacion Falsa
              compTotal++; //comparacion Falsa  
              //"COLA": Recorrer los elementos desde el 2do punto cruce hasta
              //el final y los asigna a los hijos. 
              for(int x = puntoCruce2;x<=cantPresentaciones; x++){
                compCruce2++; //comparacion True 
                compTotal++; //comparacion True 
                hijo1[x] = padre1[x];
                hijo2[x] = padre2[x];
                asigCruce2 += 3; //hijos e incremento 
                asigTotal += 3;
              }
              compCruce2++; //comparacion Falsa
              compTotal++; //comparacion Falsa  
              //
              //agregar los hijos generados a la MATRIZ DE HIJOS;
              //Se asigna el fitness de los individuos
              hijo1[0] = funcionFitness(hijo1);
              hijo2[0] = funcionFitness(hijo2);
              asigCruce2+= 2; //hijos.
              asigTotal+= 2; //hijos.
             //ANTES de imprimir y agregar los HIJOS, revisar que NO estén
             //en la matriz de hijos y tampoco en la matriz de padres
              System.out.println("TENTATIVO");
              System.out.print("Padre 1: ");
              imprimirIndividuo(padre1);
              System.out.print("Padre 2: ");
              imprimirIndividuo(padre2);
              System.out.print("Hijo 1: ");
              imprimirIndividuo(hijo1);
              System.out.print("Hijo 2: ");
              imprimirIndividuo(hijo2);
              System.out.println("--------------------------");
              conteoHijos += 2; 
              asigTotal ++;  //conteoHijos
              asigCruce2++;
             if(revisarExiste(hijo1,poblacionTwoP) || revisarExiste(hijo1,hijosTwoP)){
                //Debe mutarse porque ya existe. HASTA que ya no esté en la población
                System.out.println("Se aplicará mutación porque el hijo 1 YA EXISTE");
                mutacion(hijo1,2);
             }  
             //La matriz de hijos NO tiene fila de presentaciones. Inicia en 0 
             hijosTwoP[conteoHijos-2] = hijo1;
             compCruce2+=3; //comparacion if
             compTotal +=3;
             asigCruce2 ++; //hijos
             asigTotal ++; //hijos
             if(revisarExiste(hijo2,poblacionTwoP) || revisarExiste(hijo2,hijosTwoP)){
                //Debe mutarse porque ya existe. HASTA que ya no esté en la población
                System.out.println("Se aplicará mutación porque el hijo 2 YA EXISTE");
                mutacion(hijo2,2);
             } 
             hijosTwoP[conteoHijos-1] = hijo2;
             compCruce2+=3; //comparacion if
             compTotal +=3;
             asigCruce2 ++; //hijos
             asigTotal ++; //hijos
              //IMPRIMIR HIJOS Y PADRES
              System.out.println("FIJO");
              System.out.print("Padre 1: ");
              imprimirIndividuo(padre1);
              System.out.print("Padre 2: ");
              imprimirIndividuo(padre2);
              System.out.print("Hijo 1: ");
              imprimirIndividuo(hijo1);
              System.out.print("Hijo 2: ");
              imprimirIndividuo(hijo2);
              System.out.println("--------------------------");
              

              //revisar si alguno tiene fitness 0 y terminar todo. 
              if(hijo1[0] == 0 || hijo2[0] == 0){
                bandera = true;
                asigCruce2 ++; 
                asigTotal++;
                System.out.println("PRESENTACIONES:");
                imprimirIndividuo(presentaciones);
                System.out.println("----Se encontró un individuo con DESPERDICIO 0----");
                if(hijo1[0] == 0){
                    imprimirIndividuo(hijo1);
                }
                else{
                     imprimirIndividuo(hijo2);
                }
                compCruce2 ++;  //comparacion if
                compTotal ++;  //comparacion if
                endTimeCruce2 += System.nanoTime() - startTimeCruce2; 
                return; //Se sale de la función
              }
              compCruce2 ++;  //comparacion if
              compTotal ++;  //comparacion if
              if(conteoHijos == cantHijos){
                  break; //Se sale del 1ER FOR
              }
              compCruce2 ++;  //comparacion if
              compTotal ++;  //comparacion if
            }
            compCruce2 ++;  //comparacion falsa for
            compTotal ++;  //comparacion falsa for
            if(conteoHijos == cantHijos){
                break; //Se sale del 2do FOR
            }
            compCruce2 ++;  //comparacion if
            compTotal ++;  //comparacion if
        }
        
        //Ya se alcanzó la cantidad de hijos
        //ORDENAR matriz HIJOS según su FITNESS
        ordenarArreglo(1,hijosTwoP); 
        System.out.println("MATRIZ HIJOS TP.Ordenada");
        imprimirMatriz(hijosTwoP);
        //Evaluar hijos con sus papás.
        evaluarPoblacion(poblacionTwoP,hijosTwoP);
        System.out.println("------NUEVA POBLACION TWO POINTS--------");
        imprimirMatriz(poblacionTwoP);
        endTimeCruce2 += System.nanoTime() - startTimeCruce2; 
    }
    /**
     * Evalúa los padres con los hijos y deja en la matriz de población inicial a aquellos individuos más
     * adaptados.
     * @param arregloPadres: Matriz con la poblacipon inicial del respectivo cruce de ese momento. 
     * @param arregloHijos: Matriz con todos los hijos que se generaron al realizar el cruce respectivo.
     */ 
    public void evaluarPoblacion(int[][] arregloPadres, int[][]arregloHijos){
        asigTotal+= 2;
        memoriaConsumidaTotal += 32 * 2;
        int iteradorHijo = 0;
        int iteradorPadre = 1;
        asigTotal++; //int i= 1 for 
        for(int i = 1;iteradorPadre<cantFilas;i++){
           asigTotal ++; //incremento 
           compTotal ++; //comparacion true
           if(iteradorHijo == cantHijos){
               return;
            }
           compTotal++; //comparacion if 
           //Se revisa que el fitness sea mayor que 0 para NO tomar en cuenta 
           //HIJOS BORRADOS LÓGICAMENTE porque en mutacion se les pone "-2" a los "borrados"
           if(arregloHijos[iteradorHijo][0] >= 0){
               //Sabiendo que no es un hijo borrado
              //Se revisa si el fitness del hijo es mejor que el del padre
              if(arregloPadres[iteradorPadre][0]>arregloHijos[iteradorHijo][0]){
                //Se mueven todos los elementos del arreglo de padres 1 campo hacia abajo, 
                //hasta llegar al padre que representa el iteradorPadre en ese momento.
                //si el campo iterPadre es el último de la matriz, se asigna ese campo directamente al hijo.
                asigTotal ++; //int j
                for(int j=cantFilas-2; j>= iteradorPadre;j--){
                    compTotal ++; //comparación true for
                    arregloPadres[j+1] = arregloPadres[j];
                    asigTotal += 2; //incremento y arreglo padres
                }
                arregloPadres[iteradorPadre] = arregloHijos[iteradorHijo];
                iteradorHijo ++;
                asigTotal += 2; 
                compTotal++; //comparacion falsa for
              }
              iteradorPadre ++;
              compTotal++; //comparacion if
              asigTotal ++; //iteradorPadre
            }
           else{
               iteradorHijo++;
            }
            compTotal++; //comparacion if 
        }
        compTotal ++; //comparacion true
    }
    /**
     * Revisa si el individuo está en la matriz que se envía por parámetro. 
     * @param individuoABuscar
     * @param matrizBuscar: Matriz en la que se busca el individuo.
     * @return true si lo encuentra, false si no. 
     */
    public boolean revisarExiste(int[]individuoABuscar,int[][] matrizBuscar){
        int inicio;
        boolean indicador = false; //Indica si se encontró o no el individuo
        int largoMatriz = matrizBuscar.length;
        memoriaConsumidaTotal += 32*2 + 1;
        asigTotal += 3;
        if(largoMatriz == cantHijos){ //Ocupamos revisar en matriz de HIJOS
            //se empieza a recorrer desde 0
            inicio = 0;
        }
        else{ //Se necesita revisar la matriz POBLACIONES, inicia en 1. (1era col es de present)
            inicio = 1;   
        }
        asigTotal += 2; //inicio y "int i = inicio"
        compTotal ++; //comparacion if
        memoriaConsumidaTotal += 32; // int i
        //Recorrer cada FILA/INDIVIDUO de la matriz a buscar.
        for(int i=inicio;i<largoMatriz;i++){
            asigTotal ++; //"j"
            memoriaConsumidaTotal += 32;
            for(int j=0;j<=cantPresentaciones;j++){
                //Si elemento NO es igual. 
                compTotal ++; //comparacion true for 
                asigTotal++; //incremento
                if(individuoABuscar[j] != matrizBuscar[i][j]){
                    indicador = false;
                    break; 
                }
                else{ //Significa que el elemento está, deben revisarse los otros
                    indicador = true;
                }
                compTotal ++; //comparacion if
                asigTotal ++; //indicador
            }
            compTotal ++; 
            //Cuando se terminaron de revisar los elementos del individuo i con 
            //los elementos del individuo a buscar
            compTotal++; //comparacion if
            if(indicador) //Si la bandera está en true ENCONTRÓ ELEMENTO
                return indicador;
        }
        //Significa que recorrió la matriz y NUNCA encontró un elemento igual 
        return false;
    }
    
    //recorrer todos los elementos del individuo 
    //ver con cuál mejora el fitness.
    //NOTA: Termina de recorrer apenas encuentre 1ERO mejore el FITNESS ACTUAL
    //tMatriz me ayuda a saber sobre cuál matriz estoy aplicando mutación
    //para garantizar que no se genere un elemento repetido
    //razón es si se está haciendo por falta de kilos, fitness 0 o repetidos
    /**
    *  Metodo para calcular la combinacion que desperdicia menos arroz. Programacion Dinamica
    * @param individuo: individuo que se desea mutae
    * @param tMatriz: indica a cuál matriz pertenece el individuo que se desea mutar. 
    * 1 --> cruce One Point, 2--> cruce Two Point.
    */
    public void mutacion(int[] individuo, int tMatriz){
        int equivalente;
        int resta;
        //Variable para guardar el individuo que se genera
        int[] temporal; //********
        boolean indMutacion = false; // *************
        memoriaConsumidaTotal += 3 * 32 + 32*(cantPresentaciones+1) + 1;
        asigTotal ++; 
        for(int i=1;i<cantPresentaciones;i++){
            compTotal++; //incremento 
            //Temp se reestablece
            temporal = individuo.clone(); 
            //Kilos que representa la columna i del inviduo.
            equivalente = individuo[i] * presentaciones[i-1];
            //Al FITNESS ACTUAL se le resta los kilos que representa esa columna
            //Eso me permite saber cuál sería el FITNESS si SE HACE LA MUTACIÓN
            resta = individuo[0] - equivalente; 
            asigTotal +=3;
            //revisar que no me genere un fitness negativo (FALTARÍAN KILOS)
            //revisar que sea MEJOR que el FITNESS ACTUAL
            //revisar que NO exista
            if(resta>=0 && resta<individuo[0]){ //Si la mutación mejora mi fitness actual, se aplica
                //REVISAR que NO esté en la lista que ocupe en ese momento
                temporal[i] = 0; //**********
                temporal[0] = resta;//********
                asigTotal += 3;
                if(tMatriz == 1){ //****C.O.P
                    //No puede estar en ninguna de las dos
                    if((revisarExiste(temporal,poblacionOneP)==false) && (revisarExiste(temporal,hijosOneP)==false)){
                        indMutacion = true;
                    }
                    compTotal+= 3;
                }
                compTotal++;
                if(tMatriz == 2){ //**********C.T.P
                    if((revisarExiste(temporal,poblacionTwoP) ==false) && (revisarExiste(temporal,hijosTwoP) == false)){
                        indMutacion = true;
                        asigTotal++;
                    } 
                    compTotal += 3;
                }
                compTotal++;
                if(indMutacion){ //La mutación se puede realizar. 
                    System.out.println("^^^^^^Se aplicará una mutación^^^^^^");
                    System.out.print("Individuo: ");
                    imprimirIndividuo(individuo);
                    System.out.println("Puntuación: " + individuo[0]);  
                    //Quitarle esos kilos a la columna 1 me mejora el fitness
                    individuo[i] = 0;
                    individuo[0] = resta;
                    //individuo = temporal;
                    //imprimirIndividuo(temporal);
                    System.out.print("Mutación: ");
                    imprimirIndividuo(individuo);
                    System.out.println("Puntuación: " + resta);
                    asigTotal += 2;
                    return; //TERMINA APENAS ENCUENTRA UNO QUE MEJORE EL FITNESS                    
                }
                compTotal ++;
            }
            compTotal +=3;
        }
        //Nunca encontré uno con mejor fitness
        //se elimina ese individuo
        System.out.println("Se eliminará el individuo, no se pudo hacer mutación.");
        individuo[0] = -2; //El "-2" me indica que ese individuo fue BORRADO LOGICAMENTE. 
        asigTotal++;
        //no se elimina de la matriz para no alterar largo de la misma.
    }
    /**
     *  Metodo que recorre cada elemento del individuo y lo 
     *  imprime
     */    
    public void imprimirIndividuo(int[] individuo){
        System.out.print("| ");
        for(int e: individuo){ 
            System.out.print( e + " ");
        }
        System.out.println("| ");
    }
    /**
     * Esta es como la función principal, desde acá inicia el algoritmo
     */
    public void generaciones(){
        startTimeTotal = System.nanoTime();
        //llamar a poblacion inicial 
        System.out.println("---------POBLACION INICIAL------------");
        poblacionInicial();
        //llamar a ordenar arreglo y copiar
        System.out.println("---------POBLACION ORDENADA------------");
        //Cuanto consume ordenar arreglo
        //memoria = 32 * 6,32 * cantPresentaciones+1 
        //comparaciones= 3 + cantHijos + 1 + tope*(1+(tope*comp)) 
        //asignaciones = 3 + cantHijos + tope*(tope + 3)
        //Asig,comp, memoria de la función Fitness
        memoriaConsumidaCruce1 += 32 * 6 +32 * cantPresentaciones+1;
        memoriaConsumidaCruce2 += 32 * 6 +32 * cantPresentaciones+1;
        memoriaConsumidaTotal += 32 * 6+32 * cantPresentaciones+1;
        compCruce1 += 3 + cantHijos + 1 + cantFilas*(1+(cantFilas*cantFilas));
        compCruce2 += 3 + cantHijos + 1 + cantFilas*(1+(cantFilas*cantFilas));
        compTotal += 3 + cantHijos + 1 + cantFilas*(1+(cantFilas*cantFilas));
        asigCruce1 += 3 + cantHijos + cantFilas*(cantFilas + 3); 
        asigCruce2 += 3 + cantHijos + cantFilas*(cantFilas + 3); 
        asigTotal += 3 + cantHijos + cantFilas*(cantFilas + 3); 
        ordenarArreglo(0,poblacionInicial);
        imprimirMatriz(poblacionInicial);
        
        copiarPoblacion();
        System.out.println("---------POBLACION INICIAL ONE POINT------------");
        imprimirMatriz(poblacionOneP);
        System.out.println("---------POBLACION INICIAL TWO POINTS------------");
        imprimirMatriz(poblacionTwoP);
        asigCruce1 ++; //del int i del for 
        asigCruce2 ++; 
        asigTotal ++; 
        for(int i = 1; i<=cantGeneraciones;i++){
            asigCruce1 ++; //del incremento
            asigCruce2 ++; 
            asigTotal ++; 
            compCruce1 ++; //de la comparación verdadera 
            compCruce2 ++; 
            compTotal ++; 
            System.out.println("---------POBLACION ONE POINT------------");
            imprimirMatriz(poblacionOneP);
            System.out.println("---------GEN: " + i+ " ONE POINT------------");
            //se llaman a los cruces n veces
            cruceOnePoint();
            //significa que en este cruce encontró desperdicio 0
            if(bandera){ 
                System.out.println("Asignaciones: " + asigTotal );
                System.out.println("Comparaciones: " + compTotal);
                System.out.println("Memoria consumida: " + memoriaConsumidaTotal);
                endTimeTotal = System.nanoTime() - startTimeTotal; 
                System.out.println("Tiempo total: " + endTimeTotal);
                System.out.println("Asignaciones CRUCE 1: " + asigCruce1 );
                System.out.println("Comparaciones CRUCE 1: " + compCruce1);
                System.out.println("Memoria consumida CRUCE 1: " + memoriaConsumidaCruce1);
                System.out.println("Tiempo total CRUCE 1: " + endTimeCruce1);
                
                System.out.println("Asignaciones CRUCE 2: " + asigCruce2);
                System.out.println("Comparaciones CRUCE 2: " + compCruce2);
                System.out.println("Memoria consumida CRUCE 2: " + memoriaConsumidaCruce2);
                System.out.println("Tiempo total CRUCE 2: " + endTimeCruce2);
                return;
            }
            else{
              System.out.println("---------POBLACION TWO POINTS------------");
              imprimirMatriz(poblacionTwoP);
              System.out.println("---------GEN: " + i+ " TWO POINTS------------");
              cruceTwoPoints(); 
              if(bandera){ 
                System.out.println("Asignaciones: " + asigTotal);
                System.out.println("Comparaciones: " + compTotal);
                System.out.println("Memoria consumida: " + memoriaConsumidaTotal);
                endTimeTotal = System.nanoTime() - startTimeTotal; 
                System.out.println("Tiempo total: " + endTimeTotal);

                System.out.println("Asignaciones CRUCE 1: " + asigCruce1 );
                System.out.println("Comparaciones CRUCE 1: " + compCruce1);
                System.out.println("Memoria consumida CRUCE 1: " + memoriaConsumidaCruce1);
                System.out.println("Tiempo total CRUCE 1: " + endTimeCruce1);
                
                System.out.println("Asignaciones CRUCE 2: " + asigCruce2);
                System.out.println("Comparaciones CRUCE 2: " + compCruce2);
                System.out.println("Memoria consumida CRUCE 2: " + memoriaConsumidaCruce2);
                System.out.println("Tiempo total CRUCE 2: " + endTimeCruce2);
                return;
              }
              compCruce1 ++; //de la comparación verdadera 
              compCruce2 ++; 
              compTotal ++; 
            }
            compCruce1 ++; //de la comparación verdadera 
            compCruce2 ++; 
            compTotal ++; 
        }
        compCruce1 ++; //comparación falsa del for 
        compCruce2 ++; 
        compTotal ++; 
        //Revisar los mejores 5 individuos de cada cruce 
        System.out.println("COMPARANDO LOS MEJORES ELEMENTOS DE LOS CRUCES:");
        int stop;
        memoriaConsumidaCruce1 += 32;
        memoriaConsumidaCruce2 += 32;
        memoriaConsumidaTotal += 32;        
        //NOTA: Se hace la distinción con 3 y no con los otros porque con 3 presentaciones
        //cada cruce genera una población de 3 individuos, por lo que NO se puede 
        //determinar los mejores 5 de cada cruce porque solo hay 3, en los demás no pasa ese problema. 
        if(cantPresentaciones == 3){
            //Sirve para determinar cuándo acaba el primer for
            stop = 3;
            //Variable para saber cuántas filas tiene matriz de comparaciones
            filasComp = 6;
        }
        else{
            stop = 5;
            filasComp = 10;
        }
        asigCruce1 ++; //stop y filas comp
        asigCruce2 ++; 
        asigTotal ++;         
        compCruce1 ++; //comparación del if 
        compCruce2 ++; 
        compTotal ++; 
        //Matriz que guarda los 5 mejores de Cruce OnePoint y los mejores 5 de cruce TwoPoints
        int[][]comparacion = new int[filasComp][cantPresentaciones+1];
        memoriaConsumidaCruce1 += 32 * filasComp * (cantPresentaciones+1);
        memoriaConsumidaCruce2 += 32 * filasComp * (cantPresentaciones+1);
        memoriaConsumidaTotal += 32 * filasComp * (cantPresentaciones+1);        
        //Mejores 5 ONEPOINT
        
        asigCruce1 ++; //int i = 1
        asigCruce2 ++; 
        asigTotal ++;         
        for(int i=1;i<=stop;i++){
            compCruce1 ++; //comparación true del for
            compCruce2 ++; 
            compTotal ++; 
            comparacion[i-1] = poblacionOneP[i];
            asigCruce1 += 2; //comparacion[i-1] e incremento
            asigCruce2 += 2; 
            asigTotal +=2;    
        }
        compCruce1 ++; //comparación falsa del for 
        compCruce2 ++; 
        compTotal ++; 
        //Mejores 5 TWOPPOINTS
        asigCruce1 ++; //int i = 1
        asigCruce2 ++; 
        asigTotal ++;          
        for(int j=1;stop<filasComp;j++){
            compCruce1 ++; //comparación true del for
            compCruce2 ++; 
            compTotal ++; 
            comparacion[stop] = poblacionTwoP[j];
            stop ++;
            asigCruce1 += 3; //comparacion[i-1], incremento, stop
            asigCruce2 += 3; 
            asigTotal +=3;  
        }
        compCruce1 ++; //comparación falsa del for 
        compCruce2 ++; 
        compTotal ++; 
        System.out.println("COMPARACIÓN SIN ORDENAR");
        imprimirMatriz(comparacion);
        ordenarArreglo(2,comparacion);
        System.out.println("COMPARACIÓN ORDENADA");
        imprimirMatriz(comparacion);       
        System.out.println("**EL MEJOR INDIVIDUO ALGORITMO GENÉTICO ES: **");
        imprimirIndividuo(comparacion[0]);
        System.out.println("**SU DESPERDICIO ES DE: "+comparacion[0][0]+ " **");
        System.out.println("Asignaciones totales: " + asigTotal);
        System.out.println("Comparaciones: " + compTotal);
        System.out.println("Memoria consumida: " + memoriaConsumidaTotal);
        endTimeTotal = System.nanoTime() - startTimeTotal; 
        System.out.println("Tiempo total: " + endTimeTotal);
        System.out.println("Asignaciones CRUCE 1: " + asigCruce1 );
        System.out.println("Comparaciones CRUCE 1: " + compCruce1);
        System.out.println("Memoria consumida CRUCE 1: " + memoriaConsumidaCruce1);
        System.out.println("Tiempo total CRUCE 1: " + endTimeCruce1);

        System.out.println("Asignaciones CRUCE 2: " + asigCruce2);
        System.out.println("Comparaciones CRUCE 2: " + compCruce2);
        System.out.println("Memoria consumida CRUCE 2: " + memoriaConsumidaCruce2);
        System.out.println("Tiempo total CRUCE 2: " + endTimeCruce2);
    }


    
}
