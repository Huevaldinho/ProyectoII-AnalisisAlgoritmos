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
    private int cantFilas = 0;
    private int cantGeneraciones = 0;
    private int cantHijos = 0;
    private int cantPresentaciones = presentaciones.length;
    private int kilosSolicitados = 0;
    private static int[][] poblacionInicial = null;
    private int[][] cruce1 = null;
    private int[][] cruce2 = null;
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
        System.out.println("Presentaciones: "+
        presentaciones[0] +","+ presentaciones[1] + "," + presentaciones[2]);
        System.out.println("Kilos solicitados: " + kilosSolicitados);

        //Se trabaja con los tamaños base 3,5,6,9
        //La presentación no debe repetirse dentro del mismo individuo (VALIDARLO EN EL MAIN?)
        switch(cantPresentaciones){
            case 6:
                cantFilas = 11; //1 representa la fila de presentaciones las otras las combinaciones                
                //cantIndividuos = 10;
                cantGeneraciones = 20;
                cantHijos = 20;
                break;
            case 9: 
                //cantIndividuos = 20;
                cantFilas = 21;//1 1 por las presentaciones, las otras los diferentes individuos
                cantGeneraciones = 20;
                cantHijos = 40;
                break;
            default:
                //cantIndividuos = cantPresentaciones;  
                cantFilas = cantPresentaciones + 1;
                cantGeneraciones = 10;
                if(cantPresentaciones == 3){ //****Será mejor poner un switch?
                   cantHijos = 6; 
                }
                else{ //En caso de que sea 5
                    cantHijos = 10;
                }
                break;
        }
        //Matriz que guarda la población incial
        poblacionInicial = new int [cantFilas][cantPresentaciones+1];        
        int elemento; //Inecesaria pero se ocupa para imprimir
        int faltante;
        for(int i= 0; i<cantFilas;i++){
            int kilosIndividuo = 0;
            //La columna 0 de cada individuo guardará su fitness
            //Se representa con "-1" para saber que no ha sido cambiado
            poblacionInicial[i][0] = -1;
            System.out.println("Fila " + i);
            for(int j=1;j<cantPresentaciones;j++){
                System.out.print("Columna " + j + ": ");
                if(i == 0){ //Es la fila que guarda las presentaciones
                   poblacionInicial[i][j] = presentaciones[j-1]; //j-1 porque matriz tiene 1 col más que presentaciones
                }
                else{ //Filas correspondientes a cada individuo
                //Se genera un número aleatorio entre 0 y la cantidad de kilos que se ocupan
                //para así evitar obtener cantidades excesivamente grandes.
                elemento = (int)(Math.random()*(kilosSolicitados+1));  
                poblacionInicial[i][j] = (int)(Math.random()*(kilosSolicitados+1));
                System.out.print(elemento+",");
                kilosIndividuo += elemento * presentaciones[j];
                }
            }
            if(i!=0){
                //cuanto falta para llegar a 0
                faltante = funcionFitness(presentaciones,poblacionInicial[i]);
                //System.out.println("\n Kilos del individuo " + i + " = " + kilosIndividuo);
                //int diferencia = kilosSolicitados - kilosIndividuo;
                //El individuo no puede tener menos kilos de los solicitados
                //La población inicial no debe ser con desperdicio cero, sino ya tiene la respuesta.
                if(faltante<=0){ //************ Faltante negativo->menos kilos de los solicitados
                   //Se le asigna a la primera presentación una cantidad
                   //aleatoria que permita mantener la condición de no tener menos kilos
                   //que los solicitados ni desperdicio cero
                   poblacionInicial[i][1] = Math.abs(faltante);
                   faltante = funcionFitness(presentaciones,poblacionInicial[i]);
                   System.out.println("HUBO MENOS KILOS");
                   System.out.println("\n Kilos del individuo " + i + " = " + kilosIndividuo);
                } 
                //guardar el fitness en matriz 
                poblacionInicial[i][0] = faltante;
            }
        }
        return poblacionInicial;
    }

    //FUNCIÓN APTITUD O FITNESS
    //Esta función obtiene cuánto le falta a la población para alcanzar los
    // kilos que me pide el usuario. 
    public int funcionFitness(int[] presentaciones, int[] individuo){
        int kilosIndividuo = 0;
        for(int i=0; i<cantPresentaciones ; i++){
           kilosIndividuo += individuo[i]* presentaciones[i];
        }
        //Una vez que se tiene la cantidad de kilos del individuo, 
        //se calcula cuántos le faltan para tener desperdicio 0 
        return kilosIndividuo - kilosSolicitados;
    }
    //Llamar a esta funcion por cada individuo y guardar en un arreglo cuántos
    //kilos de desperdicio
    //NOTA: kilosIndividuo siempre >= kilosSolicitados por la definicion de pob.Inicial
    
    public int[][] ordenarArreglo(){
        //ordenar según su fitness
        int[] temp; //arreglo temporal para saber por cual individuo(fila) va.
        for(int i=1;i<cantFilas-1;i++) { //Se inicia en 1 porque la primera fila es de presentaciones
          for(int j=i+1;j<cantFilas-1;j++) {
            //Se ordena de menor a mayor según el fitness de cada individuo
            //El fitness siempre está en col 0 de cada individuo.  
            if(poblacionInicial[i][0] > poblacionInicial[j][0]){
              //intercambia los lugares de cada individuo
              temp = poblacionInicial[i];
              poblacionInicial[i] = poblacionInicial[j];
              poblacionInicial[j] = temp;
            }
          }
        }
        return poblacionInicial;
        
    }
    
    public void generarCruce1(){
        
        
    }
    
    public void generarCruce2(){
        
    }
    
    public void mutacion(){
        
    }

    
}
