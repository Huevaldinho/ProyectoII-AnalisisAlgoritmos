
package proyectoii.analisisalgoritmos;

public class ProgramacionDinamica{
    //Referencia: 
    //https://jcsis.wordpress.com/2013/12/22/programacion-dinamica-algoritmo-cambio-monedas/
    
    private int [] combinacionPresentaciones;
    private int [] presentaciones;
    private int kilos;
    
    ProgramacionDinamica (int kilosArroz, int[]  presentaciones){
        this.presentaciones = presentaciones;
        this.kilos=kilosArroz;
        //Crear matriz y la rellena.
        int [][] matriz = minDesperdicio(kilosArroz, presentaciones);
        //Una vez completa la matriz, se lee el resultado de la matriz.
        this.combinacionPresentaciones = leerMatriz(kilosArroz, presentaciones, matriz);
    }
    /**
     *  Muestra la combinacion de presentaciones y sus cantidades 
     * para obtener la menor cantidad de desperdicio de arroz cuandos se 
     * quiere obtener n cantidad de kilos.
     * 
     */
    public void getCombinacion(){
        System.out.println("Kilos arroz necesarios: "+kilos);
        System.out.println("La combinacion que desperdicia menor cantidad de arroz es: ");
        int desperdicio=0;
        int totalKilos =0;
        for (int i=0;i<presentaciones.length;i++){
            System.out.println("\t Presentacion de "+presentaciones[i]+" :"+combinacionPresentaciones[i]);
            totalKilos+=  combinacionPresentaciones[i]*presentaciones[i];
//            if (combinacionPresentaciones[i]!=0){//Si es 0 es porque no se usa esa presentacion.
//                
//                desperdicio+= (kilos- (combinacionPresentaciones[i]*presentaciones[i]));
//            }
        }
        //Desperdicio es: kilos - suma de todos los kilos por presentacion.
        System.out.println("Total kilos: "+totalKilos);
        System.out.println("Desperdicio: "+Math.abs(kilos-totalKilos)+'\n');
    }
    /**
     *  Metodo para calcular la combinacion que desperdicia menos arroz. Programacion Dinamica
     * @param kilos: Numero entero que representa la cantidad de kg de arroz que se quieren comprar.
     * @param presentaciones: Array de enteros que representa todas la presentaciones de arroz disponibles.
     * @return int [][]: Matriz con combinaciones de presentaciones.
     */
    private int[][] minDesperdicio(int kilos, int[]  presentaciones){
        /*
            Matriz de tamanno Presentaciones+1 filas y kilos+1 columnas.
            Cada fila de la matriz representa una presentacion. Tiene una fila mas para el cero.
            Cada columna son los kilos que hay entre 0 y la cantidad de kilos que se necesitan.
        */
        int[][] matriz = new int[presentaciones.length + 1][kilos + 1];
        
        //Columna del 0 siempre es 0 porque no se necesita ninguna
        //presentacion para obtener 0 kilos.
        for (int fila = 0; fila < presentaciones.length; fila++)
            matriz[fila][0] = 0;
            
        //Completa la primera fila a partir de la segunda columna
        //con un numero grande porque no se puede obtener n kilos con presentacion
        //de 0 kilos.
        for (int columna = 1; columna <= kilos; columna++)
            matriz[0][columna] = 100;//Integer.MAX_VALUE;
        
        //Variable para escoger cual combinacion que desperdicia menos kilos.
        int minimoDesperdicio;
        
        //Empieza en la segunda fila porque la primera es la del 0 y ya se completo anteriormente.
        for (int fila = 1; fila <= presentaciones.length; fila++)//For para filas (presentaciones)
            //Empieza con la segunda columna porque la del 0 ya se completo anteriormente.
            for (int columna = 1; columna <= kilos; columna++) {//For para columnas (kilos)
                //Si la presentacion es mayor que los kilos que se ocupan(columna),
                //se toma el valor de la celda de arriba.
                if (presentaciones[fila - 1] > columna){//Se resta 1 a la fila porque el array no tiene el cero.
                    matriz[fila][columna] = matriz[fila - 1][columna];//Toma el valor de la celda de arriba.
                }
                else{
                    /*
                        Si el valor de la presentacion es menor o igual al kilo
                        Saca la combinacion que desperdicie menos la combinacion
                    de la celda de arriba o sea, con presentaciones mas pequenas o
                    con la combinacion que se genera de restar kilo - presentacion.
                    Porque n se puede construir con muchas presentaciones.
                    Ejemplo. si se tiene 5 puedo hacer 5-3 que es 2, entonces yo se
                    que puedo comprar una presentacion de 2 sin tener desperdicio y 
                    si le agrego una de 3, obtengo 5 kilos.
                    */
                    //Math.abs((columna - (int )Math.ceil(columna/presentaciones[fila-1])*presentaciones[fila-1]))
                    minimoDesperdicio = min(matriz[fila - 1][columna] , Math.abs(matriz[fila][columna-1-fila]));//-1 porque empieza en 1
                    matriz[fila][columna] = minimoDesperdicio;
                }
            }
         return matriz;
        }
    /**
     * Compara cual combinacion de presentaciones desperdicia menos kilos de arroz.
     * @param celdaArriba: Kilos de desperdicio de la combinacion que no toma en cuenta la presentacion actual de la fila.
     * @param filaActual: Kilos que desperdicia la fila tomando en cuenta esa presentacion de la fila.
     * @return 
     */
    private int min(int celdaArriba, int filaActual){
        if(celdaArriba<filaActual)
            return celdaArriba;
        else 
            return filaActual;
    }
    /**
     * 
     * @param kilosArroz: Columnas matriz.
     * @param presentaciones: Filas matriz.
     * @param matrizResultado: Matriz con resultado de combinaciones que desperdician menos arroz.
     * @return: Lista con cantidad de presentaciones de cada tipo para desperdiciar la menor cantidad de arroz posible.
     */
    private int[] leerMatriz(int kilosArroz, int[] presentaciones, int[][] matrizResultado ){
        
        //La lista de combinacion es del mismo tamano que la de presentaciones
        //porque cada indice de la presentacion tendra la cantidad de 
        //preesentaciones que necesita de ese tipo.
        int[] combinacion = new int[presentaciones.length];
        
        //Pone 0 por defecto en todas las presentaciones.
        for(int i = 0; i< presentaciones.length; i++){             
            combinacion[i]=0;         
        }         
        
        int fila = presentaciones.length;
        int columna = kilosArroz;         
        
        /*
            Para leer la matriz, se empieza desde la ultima celda hasta la primera celda.
                Si el numero de la celda actual es igual al de la celda de arriba 
            significa que la presentacion de esa fila no se tomo en cuenta porque
            el valor se heredo de arriba, es decir, esa presentacion es mayor que la cantidad de kg
            en este caso solo se sube a la siguiente fila.
                Si el numero de la celda actual es diferente al de la celda de arriba
            significa que la presentacion de la fila actual si se utilizo, asi que se agrega
            1 a esa presentacion, y se salta a la siguiente columna que se determina con 
            la resta del kilo - presentacion.
        */
        //Mientras no se la columna del 0 se hace la lectura.
        while(columna>0){
            //Si el valor de la celda actual es igual que la celda superior
            //no lo toma en cuenta para el resultado porque lo heredo de arriba.
            //La fila tiene que ser mayor que 1 porque si la fila es 1, al hacer la resta
            //caeria a la fila del 0 que tiene valores que no sirven para la solucion 
            //porque son valores que representan una suma de kilos con presentacion de 0 kilos.
            if(fila>1 && matrizResultado[fila][columna]==matrizResultado[fila-1][columna]){
                fila--;//Sube una fila
            }else{//El valor de la celda actual es diferente al de la celda superior o es la fila 1.
                //Suma una bolsa de arroz de ese tipo de presentacion.
                combinacion[fila-1] += 1;
                columna = columna - presentaciones[fila-1];//Salta a la siguiente columna. 
                //Se se salta a la columna con valor kilo - presesentacion.
            }
        }
        //imprimirMatriz(matrizResultado,presentaciones);
        return combinacion;
    }
    static void imprimirMatriz(int [][]matriz,int array[]){
//        int fila=0;
//        int columna=0;
//        while (fila < matriz.length){
//            while (columna<matriz[fila].length){
//                System.out.println("Fila: "+fila+" - Columna: "+columna+" = "+matriz[fila][columna]);
//                columna++;
//            }
//            System.out.println("");
//            fila++;
//            columna=0;
//        }
        for (int x=0; x < matriz.length; x++) {
          System.out.print("|");
          for (int y=0; y < matriz[x].length; y++) {
            System.out.print (matriz[x][y]);
            if (y!=matriz[x].length-1) System.out.print("\t");
          }
          System.out.println("|");
        }
    }
    public static void main(String[] args) {
        ProgramacionDinamica a = new ProgramacionDinamica(10, new int[]{2,3,7});
        a.getCombinacion();

        ProgramacionDinamica b = new ProgramacionDinamica(11, new int[]{2,3,7});
        b.getCombinacion();

        ProgramacionDinamica c = new ProgramacionDinamica(5, new int[]{2,3,7});
        c.getCombinacion();
        
        ProgramacionDinamica d = new ProgramacionDinamica(102, new int[]{2,3,7,10});
        d.getCombinacion();
        
        ProgramacionDinamica e = new ProgramacionDinamica(55, new int[]{2,3,7,5,10});
        e.getCombinacion();
        
        ProgramacionDinamica f= new ProgramacionDinamica(71, new int[]{3,5,11});
        f.getCombinacion();
        
        ProgramacionDinamica g= new ProgramacionDinamica(71, new int[]{11,13,100});
        g.getCombinacion();
        //Este deberia ser 5*20 =100 + 13*1 = 113
        ProgramacionDinamica h= new ProgramacionDinamica(113, new int[]{5,11,13});
        h.getCombinacion();
        
        ProgramacionDinamica i= new ProgramacionDinamica(8, new int[]{2,3,5});
        i.getCombinacion();
        //IMPORTANTE ORDENAR LAS PRESENTACIONES ANTES DE TODO.
    }
}