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


    private int [] presentaciones = null;
    private boolean bandera = false;
    private int cantFilas = 0;
    private int cantGeneraciones = 0;
    private int cantHijos = 0;
    private int cantPresentaciones = 0;
    private int kilosSolicitados = 0;
    private static int[][] poblacionInicial = null;
    private int[][] hijosOneP = null; //Guarda los hijos que generen estos cruces
    private int[][] hijosTwoP = null; //Guarda los hijos que generen estos cruces
    private int[][] poblacionOneP = null; //Copia de la poblacion inicial para el cruce1
    private int[][] poblacionTwoP = null; //Copia de la poblacion inicial para el cruce2
    private int asig = 0;
    private int comp = 0;
    private static long startTime;
    private static long endTime;
    private static int memoriaConsumida; //¿Contar en bytes o bits?
    
    public AlgoritmoGenetico(int []present, int kilos) {
        this.kilosSolicitados = kilos;
        this.presentaciones = present; 
        this.cantPresentaciones = present.length;
    }
    public AlgoritmoGenetico() {
    }

    public int[] getPresentaciones() {
        return presentaciones;
    }
    public void setPresentaciones(int[] presentaciones) {
        this.presentaciones = presentaciones;
    }
    public int getKilosSolicitados() {
        return kilosSolicitados;
    }
    public void setKilosSolicitados(int kilosSolicitados) {
        this.kilosSolicitados = kilosSolicitados;
    }
    public int getAsig() {
        return asig;
    }
    public void setAsig(int asig) {
        this.asig = asig;
    }
    public int getComp() {
        return comp;
    }
    public void setComp(int comp) {
        this.comp = comp;
    }
    public static long getStartTime() {
        return startTime;
    }
    public static void setStartTime(long startTime) {
        AlgoritmoGenetico.startTime = startTime;
    }
    public static long getEndTime() {
        return endTime;
    }
    public static void setEndTime(long endTime) {
        AlgoritmoGenetico.endTime = endTime;
    }
    public static int getMemoriaConsumida() {
        return memoriaConsumida;
    }
    public static void setMemoriaConsumida(int memoriaConsumida) {
        AlgoritmoGenetico.memoriaConsumida = memoriaConsumida;
    }

    public int getCantPresentaciones() {
        return cantPresentaciones;
    }

    public void setCantPresentaciones(int cantPresentaciones) {
        this.cantPresentaciones = cantPresentaciones;
    }
    
    

//-	Los individuos deben ser distintos.
    //Ciclo para generar la cantidad que se usará de cada presentación
    public int[][] poblacionInicial(int[] presentaciones){
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
                if(cantPresentaciones == 3){ 
                   cantHijos = 6; 
                }
                else{ //En caso de que sea 5
                    cantHijos = 10;
                }
                break;
        }
        //Se inicializan las mtrices que guardarán la información de los cruces
        hijosOneP = new int[cantHijos][cantPresentaciones+1];
        hijosTwoP = new int[cantHijos][cantPresentaciones+1];
        //Se inicializa la matriz que guarda la población inicial 
        poblacionInicial = new int [cantFilas][cantPresentaciones+1];  //Matriz que guarda la población incial      
        int elemento; 
        int faltante;
        System.out.println("Cantidad presentaciones: " + cantPresentaciones);
        System.out.println("Cantidad de generaciones: " + cantGeneraciones);
        System.out.println("Cantidad de hijos: " + cantHijos);
        System.out.println("Población inicial: ");
        for(int i= 0; i<cantFilas;i++){
            System.out.print("|");
            int kilosIndividuo = 0;
            //La columna 0 de cada individuo guardará su fitness
            //Se representa con "-1" para saber que no ha sido cambiado
            poblacionInicial[i][0] = -1;
            
            for(int j=1;j<cantPresentaciones+1;j++){
                //System.out.println("Columna " + j + ": ");
                if(i == 0){ //Es la fila de PRESENTACIONES
                   poblacionInicial[i][j] = presentaciones[j-1]; //j-1 porque matriz tiene 1 col más que presentaciones
                   System.out.print(poblacionInicial[i][j] + " ");
                }
                else{ //Filas correspondientes a cada INDIVIDUO
                //Se genera un número aleatorio entre 0 y la cantidad de kilos que se ocupan
                //para así evitar obtener cantidades excesivamente grandes.
                elemento = (int)(Math.random()*(kilosSolicitados+1));  
                poblacionInicial[i][j] = elemento;
                System.out.print(elemento + " ");
                kilosIndividuo += elemento * presentaciones[j-1];
                }
            }
            if(i!=0){
                faltante = funcionFitness(presentaciones,poblacionInicial[i]);
                //Comparación para asegurar que el individuo no tenga desperdicio cero
                // ni tenga menos kilos de los solicitados. 
                if(faltante<=0){ //Faltante negativo->menos kilos de los solicitados
                   //Se le asigna a la primera presentación una cantidad
                   //aleatoria que permita mantener la condición 
                   System.out.println("|");
                   System.out.println("Hubo un faltante de: " + faltante +", se aplicó mutación");
                   
                   poblacionInicial[i][1] = Math.abs(faltante);
                   System.out.println("Cambio en el elemento "+ i + ","+1+":"+poblacionInicial[i][1]);
                   faltante = funcionFitness(presentaciones,poblacionInicial[i]);
                   
                   System.out.println("\n Kilos del individuo " + i + " = " + kilosIndividuo);
                } 
                //guardar el fitness en matriz 
                poblacionInicial[i][0] = faltante;
                System.out.println("|" + " Fitness: " + faltante);
            }
            else{
            System.out.println("|");
            }
        }
        
        return poblacionInicial;
    }

    //FUNCIÓN APTITUD O FITNESS
    //Esta función obtiene cuánto le falta a la población para alcanzar los
    // kilos que me pide el usuario. 
    public int funcionFitness(int[] presentaciones, int[] individuo){
        int kilosIndividuo = 0;
        for(int i=0; i<cantPresentaciones; i++){
           kilosIndividuo += individuo[i+1]* presentaciones[i];
        }
        //Una vez que se tiene la cantidad de kilos del individuo, 
        //se calcula cuántos le faltan para tener desperdicio 0 
        return kilosIndividuo - kilosSolicitados;
    }
    public int[][] ordenarArreglo(int flag, int[][] arregloOrdenar){
        //ordenar según su fitness
        int tope; //para el primer ciclo
        int tope2; //para el segundo ciclo
        int inicio;
        if(flag == 2){ //Ordenar arreglo de 10 MEJORES
            inicio = 0;
            tope = 10;
            tope2 = 10;
        }
        if(flag == 1){ //Ordenar arreglo de hijos 
            inicio = 0;
            tope=cantHijos;
            tope2 = cantHijos;
        }
        else{ //Ordenar poblacion inicial 
            inicio = 1;
            tope = cantFilas-1;
            tope2=cantFilas;
        }
        
        int[] temp; //arreglo temporal para saber por cual individuo(fila) va.
        for(int i=inicio;i<tope;i++) { //Se inicia en 1 porque la primera fila es de presentaciones
          for(int j=i+1;j<tope2;j++) {
            //Se ordena de menor a mayor según el fitness de cada individuo
            //El fitness siempre está en col 0 de cada individuo.  
            if(arregloOrdenar[i][0] > arregloOrdenar[j][0]){
              //intercambia los lugares de cada individuo
              temp = arregloOrdenar[i];
              arregloOrdenar[i] = arregloOrdenar[j];
              arregloOrdenar[j] = temp;
            }
          }
        }
        return arregloOrdenar; 
    }    
    
    public void copiarPoblacion(){
        //primero se checkea que la matriz de población inical esté ordenado según FITNESS 
       ordenarArreglo(0,poblacionInicial);
       poblacionOneP = poblacionInicial.clone();
       poblacionTwoP = poblacionInicial.clone();
    }


    public void imprimirMatriz(int[][] matrizImprimir){ 
        for(int[]e: matrizImprimir){
            imprimirIndividuo(e);
        }
    }
    //Se usará cruce en 1 punto(por la mitad) 
    public void cruceOnePoint(){
        System.out.println("~~~~~~~~~~~~~~CRUCE ONE POINT~~~~~~~~~~~~~~");
        //El punto de cruce será por la mitad de cada padre. 
        int puntoCruce = cantPresentaciones/2;
        int conteoHijos = 0;
        int[]padre1;
        int[]padre2;
//        int[] hijo1 = new int[cantPresentaciones+1];
//        int[] hijo2 = new int[cantPresentaciones+1];
        //i= 1 es el mejor individuo de la población, por eso puede cruzarse con todos. 
        //i tiene que cruzarse con todos los individuos hasta tener n hijos.
        for(int i=1; i<cantFilas-1;i++){  //recorre cada individuo
            padre1 = poblacionOneP[i];
            for(int j=i+1; j<cantFilas;j++){ //lo cruza con los otros individuos.
              padre2 = poblacionOneP[j]; 
              //Si no se inicializan de nuevo se altera la matriz de hijos
              int[] hijo1 = new int[cantPresentaciones+1];
              int[] hijo2 = new int[cantPresentaciones+1];
              //HACER EL CRUCE
              //"CABEZA": Recorrer los elementos desde el primero(sin contar fitness) hasta
              //el punto de cruce y los asigna a los hijos. 
              for(int k=1; k<= puntoCruce;k++){ 
                hijo1[k] = padre1[k];
                hijo2[k] = padre2[k];
              }
              //"COLA": Recorrer los elementos desde el punto cruce hasta
              //el final y los asigna a los hijos. 
              for(int m = puntoCruce+1;m<=cantPresentaciones; m++){
                hijo1[m] = padre2[m];
                hijo2[m] = padre1[m];
              }
              //
              //agregar los hijos generados a la MATRIZ DE HIJOS;
              //Se asigna el fitness de los individuos
              hijo1[0] = funcionFitness(presentaciones, hijo1);
              hijo2[0] = funcionFitness(presentaciones, hijo2);
              //IMPRIMIR HIJOS Y PADRES
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

              //La matriz de hijos NO tiene fila de presentaciones. Inicia en 0 
              hijosOneP[conteoHijos-2] = hijo1;
              hijosOneP[conteoHijos-1] = hijo2;
              
              //revisar si alguno tiene fitness 0 y terminar todo. 
              if(hijo1[0] == 0 || hijo2[0] == 0){
                bandera = true;
                System.out.println("PRESENTACIONES:");
                imprimirIndividuo(presentaciones);
                System.out.println("----Se encontró un individuo con DESPERDICIO 0----");
                if(hijo1[0] == 0){
                    imprimirIndividuo(hijo1);
                }
                else{
                     imprimirIndividuo(hijo2);
                }
                return; //Se sale de la función
              }
              if(conteoHijos == cantHijos){
                  break; //Se sale del 1ER FOR
              }
            }
            if(conteoHijos == cantHijos){
                break; //Se sale del 2do FOR
            }
        }
        //Ya se alcanzó la cantidad de hijos
        //ORDENAR matriz HIJOS según su FITNESS
//        System.out.println("MATRIZ HIJOS");
//        imprimirMatriz(hijosOneP);
        
        ordenarArreglo(1,hijosOneP); 
        //revisar repetidos y aplicar mutación
        //revisarRepetidos(); Esto va más arriba
        System.out.println("MATRIZ HIJOS OP.Ordenada");
        imprimirMatriz(hijosOneP);
        
        //hay que evaluarlos con sus papás.
        evaluarPoblacion(poblacionOneP,hijosOneP);  
        System.out.println("-----NUEVA POBLACION ONE POINT------");
        imprimirMatriz(poblacionOneP);
    }
    
    
    public void cruceTwoPoints(){
        System.out.println("~~~~~~~~~~~~~~CRUCE TWO POINTS~~~~~~~~~~~~~~");
        //El punto de cruce serán los extremos y el centro. 
        int puntoCruce1 = 1;
        int puntoCruce2 = cantPresentaciones;
        int conteoHijos = 0;
        int[]padre1;
        int[]padre2;

        //i= 1 es el mejor individuo de la población, por eso puede cruzarse con todos. 
        //i tiene que cruzarse con todos los individuos hasta tener n hijos.
        for(int i=1; i<cantFilas-1;i++){  //recorre cada individuo
            padre1 = poblacionTwoP[i];
            for(int j=i+1; j<cantFilas;j++){ //lo cruza con los otros individuos.
              padre2 = poblacionTwoP[j]; 
              //Si no se inicializan de nuevo se altera la matriz de hijos
              int[] hijo1 = new int[cantPresentaciones+1];
              int[] hijo2 = new int[cantPresentaciones+1];
              //HACER EL CRUCE
              //"CABEZA": Recorrer los elementos desde el primero(sin contar fitness) hasta
              //el punto de cruce y los asigna a los hijos. 
              for(int k=1; k<= puntoCruce1;k++){ 
                hijo1[k] = padre1[k];
                hijo2[k] = padre2[k];
              }
              //"MEDIO": Recorre los elementos desde el 1er punto de cruce hasta el 2do
              for(int m = puntoCruce1+1;m<puntoCruce2; m++){
                hijo1[m] = padre2[m];
                hijo2[m] = padre1[m];
              }
              //"COLA": Recorrer los elementos desde el 2do punto cruce hasta
              //el final y los asigna a los hijos. 
              for(int x = puntoCruce2;x<=cantPresentaciones; x++){
                hijo1[x] = padre1[x];
                hijo2[x] = padre2[x];
              }
              //
              //agregar los hijos generados a la MATRIZ DE HIJOS;
              //Se asigna el fitness de los individuos
              hijo1[0] = funcionFitness(presentaciones, hijo1);
              hijo2[0] = funcionFitness(presentaciones, hijo2);
              //IMPRIMIR HIJOS Y PADRES
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

              //La matriz de hijos NO tiene fila de presentaciones. Inicia en 0 
              hijosTwoP[conteoHijos-2] = hijo1;
              hijosTwoP[conteoHijos-1] = hijo2;
              
              //revisar si alguno tiene fitness 0 y terminar todo. 
              if(hijo1[0] == 0 || hijo2[0] == 0){
                bandera = true;
                System.out.println("PRESENTACIONES:");
                imprimirIndividuo(presentaciones);
                System.out.println("----Se encontró un individuo con DESPERDICIO 0----");
                if(hijo1[0] == 0){
                    imprimirIndividuo(hijo1);
                }
                else{
                     imprimirIndividuo(hijo2);
                }
                return; //Se sale de la función
              }
              if(conteoHijos == cantHijos){
                  break; //Se sale del 1ER FOR
              }
            }
            if(conteoHijos == cantHijos){
                break; //Se sale del 2do FOR
            }
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
    }

//    public void revisarRepetidos(){
//        if (Arrays.deepEquals(individuo1, matriz2))
//    }
    //cuando ya tengo los hijos tengo que evaluar quiénes se quedan y volver a hacer cruce. 
    public void evaluarPoblacion(int[][] arregloPadres, int[][]arregloHijos){
        //Ciclos o memoria, ciclos. 
        for(int i = 1;i<cantFilas;i++){
           if(arregloPadres[i][0]>arregloHijos[i-1][0]){
                //Se "abre espacio" i para el hijo. 
                //Mueve los elementos 1 abajo, hasta llegar al campo i
                //si el campo i es el último de la matriz, se asigna ese campo para el hijo i-1.
                for(int j = cantFilas-2; j>=i;j--){
                    arregloPadres[j+1] = arregloPadres[j];
                }
                //Se guarda al hijo en el campo que se abrió 
                arregloPadres[i] = arregloHijos[i-1];
            }
        }
    }
    public void imprimirIndividuo(int[] individuo){
        System.out.print("| ");
        for(int e: individuo){ 
            System.out.print( e + " ");
        }
        System.out.println("| ");
    }
    //Esta es como la función PRINCIPAL
    public void generaciones(){

        //llamar a poblacion inicial 
        System.out.println("---------POBLACION INICIAL------------");
        poblacionInicial(presentaciones);
        //llamar a ordenar arreglo y copiar
        System.out.println("---------POBLACION ORDENADA------------");
        ordenarArreglo(0,poblacionInicial);
        imprimirMatriz(poblacionInicial);
        copiarPoblacion();
        System.out.println("---------POBLACION INICIAL ONE POINT------------");
        imprimirMatriz(poblacionOneP);
        System.out.println("---------POBLACION INICIAL TWO POINTS------------");
        imprimirMatriz(poblacionTwoP);        
        for(int i = 1; i<=cantGeneraciones;i++){
            System.out.println("---------GEN: " + i+ " ONE POINT------------");
            //se llaman a los cruces n veces
            cruceOnePoint();
            //significa que en este cruce encontró desperdicio 0
            if(bandera){ 
                return;
            }
            else{
              //ordenar la matriz de OnePoint
              //ordenarArreglo();
              System.out.println("---------GEN: " + i+ " TWO POINTS------------");
              cruceTwoPoints(); 
              if(bandera){ 
                return;
              }
            }
        }
        //Revisar los mejores 5 individuos de cada cruce 
        System.out.println("COMPARANDO LOS MEJORES ELEMENTOS DE LOS CRUCES");
        int[][]comparacion = new int[10][cantPresentaciones+1];
        System.arraycopy(poblacionOneP, 1, comparacion, 0, 5);
        System.arraycopy(poblacionTwoP, 1, comparacion, 0, 5);
        ordenarArreglo(2,comparacion);
        System.out.println("**EL MEJOR INDIVIDUO ALGORITMO GENÉTICO ES: **");
        imprimirIndividuo(comparacion[0]);
        System.out.println("**SU DESPERDICIO ES DE: "+comparacion[0][0]+ " **");

        
    }


    
}
