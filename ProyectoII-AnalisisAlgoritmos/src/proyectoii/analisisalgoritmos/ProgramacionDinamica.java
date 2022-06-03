
package proyectoii.analisisalgoritmos;

import java.util.Arrays;

public class ProgramacionDinamica{
    //Referencia: 
    //https://jcsis.wordpress.com/2013/12/22/programacion-dinamica-algoritmo-cambio-monedas/
    
    private int [] combinacionPresentaciones=null;
    private int [] presentaciones=null;
    private int kilos =0;
    private int asig=0;
    private int comp=0;
    private static long startTime;
    private static long endTime;
    //FALTA MEMORIA
    
    
    public int[] getCombinacionPresentaciones() {return combinacionPresentaciones;}
    public void setCombinacionPresentaciones(int[] combinacionPresentaciones) {this.combinacionPresentaciones = combinacionPresentaciones;}
    public int[] getPresentaciones() {return presentaciones;}
    public void setPresentaciones(int[] presentaciones) {this.presentaciones = presentaciones;}
    public int getKilos() {return kilos;}
    public void setKilos(int kilos) {this.kilos = kilos;}
    public int getAsig(){return asig;}
    public void setAsig(int asig) {this.asig = asig;}
    public int getComp(){return comp;}
    public void setComp(int comp) {this.comp = comp;}
    public static long getStartTime(){return startTime;}
    public static void setStartTime(long startTime){ProgramacionDinamica.startTime = startTime;}
    public static long getEndTime(){return endTime;}
    public static void setEndTime(long endTime){ProgramacionDinamica.endTime = endTime;}
    //FALTA SETTERS Y GETTERS DE MEMORIA.
    
    ProgramacionDinamica (int kilosArroz, int[] presentaciones){
        this.asig+=2;//parametros
        Arrays.sort(presentaciones);
        this.presentaciones = presentaciones;
        this.kilos=kilosArroz;
        this.asig+=2;
        //Crear matriz y la rellena con las combinaciones que desperdicien menos cantidad de arroz.
        int [][] matriz = minDesperdicio(kilosArroz, presentaciones);
        //Una vez completa la matriz, se lee el resultado de la matriz.
        this.combinacionPresentaciones = leerMatriz(kilosArroz, presentaciones, matriz);
        this.asig+=2;
    }
    /**
     * ESTO EN REALIDAD SE HACE EN EL MAIN LLAMANDO A LOS GETTERS.
     *  Muestra la combinacion de presentaciones y sus cantidades 
     * para obtener la menor cantidad de desperdicio de arroz cuandos se 
     * quiere obtener n cantidad de kilos.
     * 
     */
    public void getCombinacion(){
        System.out.println("Kilos arroz necesarios: "+kilos);
        System.out.println("La combinacion obtenida: ");
        int totalKilos =0;
        for (int i=0;i<presentaciones.length;i++){
            System.out.println("\t Presentacion de "+presentaciones[i]+": "+combinacionPresentaciones[i]+" unidades.");
            totalKilos+=  combinacionPresentaciones[i]*presentaciones[i];
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
        this.asig+=2;//parametros
        /*
            Matriz de tamanno Presentaciones+1 filas y kilos+1 columnas.
            Cada fila de la matriz representa una presentacion. Tiene una fila mas para el cero.
            Cada columna son los kilos que hay entre 0 y la cantidad de kilos que se necesitan.
        */
        int[][] matriz = new int[presentaciones.length + 1][kilos + 1];
        this.asig+=1;
        
        //Columna del 0 siempre es 0 porque no se necesita ninguna
        //presentacion para obtener 0 kilos.
        this.asig+=1;//fila = 0 del for
        for (int fila = 0; fila < presentaciones.length; fila++){
            matriz[fila][0] = 0;
            this.asig+=2;//incremento del for y matriz[fila][0]= 0    
            this.comp+=1;//true del for
            
        }
        this.comp+=1;//false del for
            
            
        //Completa la primera fila a partir de la segunda columna
        //con un numero grande porque no se puede obtener n kilos con presentacion
        //de 0 kilos.
        this.asig+=1;//columna =1
        for (int columna = 1; columna <= kilos; columna++){
            matriz[0][columna] = 100;//Integer.MAX_VALUE;
            this.asig+=2;//incremento del for y matriz[0][columna]=100
            this.comp+=1;//true del for
        }
        this.comp+=1;//false del for
            
        
        //Variable para escoger cual combinacion que desperdicia menos kilos.
        int minimoDesperdicio;
        
        //Empieza en la segunda fila porque la primera es la del 0 y ya se completo anteriormente.
        this.asig+=1;//fila = 1 del for
        for (int fila = 1; fila <= presentaciones.length; fila++){//For para filas (presentaciones)
            this.asig+=1;//incremento del for de fila
            this.comp+=1;//true del for de fila
            //Empieza con la segunda columna porque la del 0 ya se completo anteriormente.
            this.asig+=1;//columna = 1 for columna
            for (int columna = 1; columna <= kilos; columna++){//For para columnas (kilos)
                this.asig+=1;//incremento del for columna
                this.comp+=1;//true del for columna
                //Si es mayor toma el menor entre la fila de arriba o el desperdicio de presentaciones-kilo
                if (presentaciones[fila - 1] > columna){
                    matriz[fila][columna] = min(matriz[fila-1][columna],Math.abs(columna-presentaciones[fila-1]));
                    this.asig+=1;//matriz[fila][columna]=
                }
                else{
                    //Compara si la el desperdicio de la fila de arriba es menor que
                    //el desperdicio al restar la kilos-presentacion. Esto es como decir
                    //si tengo 5 kilos y le resto 3, aun me faltan 2 kilos por comprar,
                    //y como tengo presentacion de 2 kilos el desperdicio es 0.
                    minimoDesperdicio = min(matriz[fila - 1][columna] ,matriz[fila][Math.abs(columna-presentaciones[fila-1])]);
                    matriz[fila][columna] = minimoDesperdicio;
                    this.asig+=2;//dos asignaciones mindesperdicio y matriz.
                }
                this.comp+=1;//comparacion del if
            }
            this.comp+=1;//false for columna
        }
        this.comp+=1;//false for fila
         return matriz;
        }
    /**
     * Compara cual combinacion de presentaciones desperdicia menos kilos de arroz.
     * @param celdaArriba: Kilos de desperdicio de la combinacion que no toma en cuenta la presentacion actual de la fila.
     * @param filaActual: Kilos que desperdicia la fila tomando en cuenta esa presentacion de la fila.
     * @return 
     */
    private int min(int celdaArriba, int filaActual){
        this.asig+=2;//parametros
        this.comp+=1;//comparacion del if
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
    /**
     * Metodo que imprime matriz de resultados.
     * @param matriz: Matriz con resultado de combinaciones.
     * @param array: Arreglo con las presentaciones.
     */
    static void imprimirMatriz(int [][]matriz,int array[]){
        for (int[] matriz1 : matriz) {
            System.out.print("|");
            for (int y = 0; y < matriz1.length; y++) {
                System.out.print(matriz1[y]);
                if (y != matriz1.length - 1) {
                    System.out.print("\t");
                }
            }
            System.out.println("|");
        }
    }
    public static void main(String[] args) {
        /*
            LAS PRESENTACIONES PUEDEN SER QUEMADAS. Pero preferiblemente usar presentaciones que no sean multiplos entre si.
            Entre mas pequenas sean las presentaciones mas aproximado sera.
            Cuando n es multiplo de alguna preentacion no hay desperdicio, eso
        implica que cuando hay presentacion de 1 no hay desperdicio.
          */  
        
        //Buenos
        ProgramacionDinamica c = new ProgramacionDinamica(5, new int[]{3,7,2});
        c.getCombinacion();
        
        ProgramacionDinamica i= new ProgramacionDinamica(8, new int[]{2,3,5});
        i.getCombinacion();
        
        ProgramacionDinamica a = new ProgramacionDinamica(10, new int[]{2,3,7});
        a.getCombinacion();
        
        ProgramacionDinamica e = new ProgramacionDinamica(55, new int[]{2,3,7,5,10});
        e.getCombinacion();
        
        ProgramacionDinamica f= new ProgramacionDinamica(71, new int[]{3,5,11});
        f.getCombinacion();
        
        ProgramacionDinamica d = new ProgramacionDinamica(102, new int[]{2,3,7,10});
        d.getCombinacion();
        
        ProgramacionDinamica b = new ProgramacionDinamica(11, new int[]{2,3,7});
        b.getCombinacion();
        
        ProgramacionDinamica j= new ProgramacionDinamica(13, new int[]{2,3,5});
        j.getCombinacion();
        
        ProgramacionDinamica h= new ProgramacionDinamica(113, new int[]{5,11,13});
        h.getCombinacion();
        
      
 

//        System.out.println("prueba: "+Integer.SIZE/8);
//        
        
    }
}