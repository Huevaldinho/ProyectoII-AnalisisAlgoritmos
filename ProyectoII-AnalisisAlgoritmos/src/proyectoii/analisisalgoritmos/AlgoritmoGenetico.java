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
    private int filasComp;
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
    //int[] presentaciones PAAMETRO
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
            //Revisar que elemento NO estén en la matriz de poblacionInicial

            if(i!=0){ //Si no es la fila de presentaciones. 
                faltante = funcionFitness(presentaciones,poblacionInicial[i]);
                //Comparación para asegurar que el individuo no tenga desperdicio cero
                // ni tenga menos kilos de los solicitados. 
                if(faltante<=0){ //Faltante negativo->menos kilos de los solicitados
                   //Se le asigna a la primera presentación una cantidad
                   //aleatoria que permita mantener la condición 
                   System.out.println("|");
                   System.out.println("Hubo un faltante de: " + faltante);

                   
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
            tope = filasComp;//**********
            tope2 = filasComp;//**********
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
             if(revisarExiste(hijo1,poblacionOneP) || revisarExiste(hijo1,hijosOneP)){
                //Debe mutarse porque ya existe. HASTA que ya no esté en la población
                System.out.println("Se aplicará mutación porque el hijo 1 YA EXISTE");
                mutacion(hijo1,1);
             }
             //después de que se hace la mutación se agrega ese hijo
             //La matriz de hijos NO tiene fila de presentaciones. Inicia en 0 
             hijosOneP[conteoHijos-2] = hijo1;
             if(revisarExiste(hijo2,poblacionOneP) || revisarExiste(hijo2,hijosOneP)){
                 System.out.println("Se aplicará mutación porque el hijo 2 YA EXISTE");
                //Debe mutarse porque ya existe. HASTA que ya no esté en la población
                mutacion(hijo2,1);
             }
             hijosOneP[conteoHijos-1] = hijo2;

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
        ordenarArreglo(1,hijosOneP); //Deja individuos a "borrar" en primeros lugares
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
              System.out.println("HIJOS TP ANTES CRUCE");
              imprimirMatriz(hijosTwoP);
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
             if(revisarExiste(hijo1,poblacionTwoP) || revisarExiste(hijo1,hijosTwoP)){
                //Debe mutarse porque ya existe. HASTA que ya no esté en la población
                System.out.println("Se aplicará mutación porque el hijo 1 YA EXISTE");
                mutacion(hijo1,2);
             }  
             //La matriz de hijos NO tiene fila de presentaciones. Inicia en 0 
              hijosTwoP[conteoHijos-2] = hijo1;
              
             if(revisarExiste(hijo2,poblacionTwoP) || revisarExiste(hijo2,hijosTwoP)){
                //Debe mutarse porque ya existe. HASTA que ya no esté en la población
                System.out.println("Se aplicará mutación porque el hijo 2 YA EXISTE");
                mutacion(hijo2,2);
             } 
             hijosTwoP[conteoHijos-1] = hijo2;
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
        int iteradorHijo = 0;
        int iteradorPadre = 1;
        for(int i = 1;iteradorPadre<cantFilas;i++){
           if(iteradorHijo == cantHijos){
               return;
            }
           //Se revisa que el fitness sea mayor que 0 para NO tomar en cuenta 
           //HIJOS BORRADOS LÓGICAMENTE porque en mutacion se les pone "-2" a los "borrados"
           if(arregloHijos[iteradorHijo][0] >= 0){
               //Sabiendo que no es un hijo borrado
              //Se revisa si el fitness del hijo es mejor que el del padre
              if(arregloPadres[iteradorPadre][0]>arregloHijos[iteradorHijo][0]){
                //Se mueven todos los elementos del arreglo de padres 1 campo hacia abajo, 
                //hasta llegar al padre que representa el iteradorPadre en ese momento.
                //si el campo iterPadre es el último de la matriz, se asigna ese campo directamente al hijo.
                for(int j=cantFilas-2; j>= iteradorPadre;j--){
                    arregloPadres[j+1] = arregloPadres[j];
                }
                arregloPadres[iteradorPadre] = arregloHijos[iteradorHijo];
                iteradorHijo ++;
              }
              iteradorPadre ++;
           }
           else{
               iteradorHijo++;
           }
        }
    }
//    public void evaluarPoblacion(int[][] arregloPadres, int[][]arregloHijos){
//        //Ciclos o memoria, ciclos. 
//        for(int i = 1;i<cantFilas;i++){
//           //Se revisa que el fitness sea mayor que 0 para NO tomar en cuenta 
//           //HIJOS BORRADOS LÓGICAMENTE porque en mutacion se les pone "-2" a los "borrados"
//           //También revisa si el fitness del hijo es mejor que el del padre
//           if(arregloPadres[i][0]>arregloHijos[i-1][0]){ //*****
//           //if(arregloPadres[i][0]>arregloHijos[i-1][0]){
//                //Se "abre espacio" i para el hijo. 
//                //Mueve los elementos 1 abajo, hasta llegar al campo i
//                //si el campo i es el último de la matriz, se asigna ese campo para el hijo i-1.
//                for(int j = cantFilas-2; j>=i;j--){
//                    arregloPadres[j+1] = arregloPadres[j];
//                }
//                //Se guarda al hijo en el campo que se abrió 
//                arregloPadres[i] = arregloHijos[i-1];
//            }
//        }
//    }    
    //Función que revisa si 
    public boolean revisarExiste(int[]individuoABuscar,int[][] matrizBuscar){
        int inicio;
        boolean indicador = false; //Indica si se encontró o no el individuo
        int largoMatriz = matrizBuscar.length;
        if(largoMatriz == cantHijos){ //Ocupamos revisar en matriz de HIJOS
            //se empieza a recorrer desde 0
            inicio = 0;
        }
        else{ //Se necesita revisar la matriz POBLACIONES, inicia en 1. (1era col es de present)
            inicio = 1;   
        }
        //Recorrer cada FILA/INDIVIDUO de la matriz a buscar. 
        for(int i=inicio;i<largoMatriz;i++){
            for(int j=0;j<=cantPresentaciones;j++){
                //Si elemento NO es igual. 
                if(individuoABuscar[j] != matrizBuscar[i][j]){
                    indicador = false;
                    break; 
                }
                else{ //Significa que el elemento está, deben revisarse los otros
                    indicador = true;
                    
                }
            }
            //Cuando se terminaron de revisar los elementos del individuo i con 
            //los elementos del individuo a buscar
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
     
    public void mutacion(int[] individuo, int tMatriz){
        int equivalente;
        int resta;
        //Variable para guardar el individuo que se genera
        int[] temporal; //********
        boolean indMutacion = false; // *************
        
        for(int i=1;i<cantPresentaciones;i++){
            //Temp se reestablece
            temporal = individuo.clone();
            //Kilos que representa la columna i del inviduo.
            equivalente = individuo[i] * presentaciones[i-1];
            //Al FITNESS ACTUAL se le resta los kilos que representa esa columna
            //Eso me permite saber cuál sería el FITNESS si SE HACE LA MUTACIÓN
            resta = individuo[0] - equivalente; 
            //revisar que no me genere un fitness negativo (FALTARÍAN KILOS)
            //revisar que sea MEJOR que el FITNESS ACTUAL
            //revisar que NO exista
            if(resta>=0 && resta<individuo[0]){ //Si la mutación mejora mi fitness actual, se aplica
                //REVISAR que NO esté en la lista que ocupe en ese momento
                temporal[i] = 0; //**********
                temporal[0] = resta;//********
                if(tMatriz == 1){ //****C.O.P
                    //No puede estar en ninguna de las dos
                    if((revisarExiste(temporal,poblacionOneP)==false) && (revisarExiste(temporal,hijosOneP)==false)){
                        indMutacion = true;
                    }
                }
                if(tMatriz == 2){ //**********C.T.P
                    if((revisarExiste(temporal,poblacionTwoP) ==false) && (revisarExiste(temporal,hijosTwoP) == false)){
                        indMutacion = true;
                    }                    
                }
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
                    return; //TERMINA APENAS ENCUENTRA UNO QUE MEJORE EL FITNESS                    
                }
            }
        }
        //Nunca encontré uno con mejor fitness
        //se elimina ese individuo
        System.out.println("Se eliminará el individuo, no se pudo hacer mutación.");
        individuo[0] = -2; //El "-2" me indica que ese individuo fue BORRADO LOGICAMENTE. 
        //no se elimina de la matriz para no alterar largo de la misma.
    }
//    
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
        poblacionInicial();
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
            System.out.println("---------POBLACION ONE POINT------------");
            imprimirMatriz(poblacionOneP);
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
              System.out.println("---------POBLACION TWO POINTS------------");
              imprimirMatriz(poblacionTwoP);
              System.out.println("---------GEN: " + i+ " TWO POINTS------------");
              cruceTwoPoints(); 
              if(bandera){ 
                return;
              }
            }
        }
        //Revisar los mejores 5 individuos de cada cruce 
        System.out.println("COMPARANDO LOS MEJORES ELEMENTOS DE LOS CRUCES:");
        int stop;
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
        //Matriz que guarda los 5 mejores de Cruce OnePoint y los mejores 5 de cruce TwoPoints
        int[][]comparacion = new int[filasComp][cantPresentaciones+1];
        //Mejores 5 ONEPOINT
        for(int i=1;i<=stop;i++){
            comparacion[i-1] = poblacionOneP[i];
        }
        //Mejores 5 TWOPPOINTS
        for(int j=1;stop<filasComp;j++){
            comparacion[stop] = poblacionTwoP[j];
            stop ++;
        }
        System.out.println("COMPARACIÓN SIN ORDENAR");
        imprimirMatriz(comparacion);
        ordenarArreglo(2,comparacion);
        System.out.println("COMPARACIÓN ORDENADA");
        imprimirMatriz(comparacion);       
        System.out.println("**EL MEJOR INDIVIDUO ALGORITMO GENÉTICO ES: **");
        imprimirIndividuo(comparacion[0]);
        System.out.println("**SU DESPERDICIO ES DE: "+comparacion[0][0]+ " **");

        
    }


    
}
