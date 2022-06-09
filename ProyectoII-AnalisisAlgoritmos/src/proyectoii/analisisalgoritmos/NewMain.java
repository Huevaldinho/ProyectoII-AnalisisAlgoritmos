/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoii.analisisalgoritmos;

import java.util.Arrays;

/**
 *
 * @author Sophya Mc Lean M
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
        private int[][] poblacionInicial = null;
        private static int cantFilas = 3;
        private static int cantPresentaciones = 2;
        private static int presentaciones[] = {2,4,7};
       
        public static void prueba(){
            int cantF = 10;
            for(int e: presentaciones){
                for(int i=0;i<cantF;cantFilas++){
                    if(cantFilas == 3){
                        System.out.println("SALIENDO DEL PRIMER CICLO");
                        return;
                    }
                }
                if(cantFilas == 3){
                    System.out.println("SALIENDO DEL 2DO CICLO");
                    
                }
            }
            if(cantFilas == 3){
                System.out.println("SALIENDO DE FUNCION");
                
            }
            System.out.println("SIGUE HACIENDO ESTO?");
        }
        
        
    public static void main(String[] args) {
            int kilos = 19;
            AlgoritmoGenetico p = new AlgoritmoGenetico(presentaciones,kilos);
            p.generaciones();
    }
    
}
