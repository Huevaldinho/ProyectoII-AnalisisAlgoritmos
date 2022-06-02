/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoii.analisisalgoritmos;

import java.util.Arrays;

/**
 *
 * @author Harrick Mc Lean M
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
        private int[][] poblacionInicial = null;
        private static int cantFilas = 3;
        private static int cantPresentaciones = 2;

    public static void main(String[] args) {
            int presentaciones[] = {2,4,7};
            int kilos = 19;
            //AlgoritmoGenetico p = new AlgoritmoGenetico(presentaciones,kilos);
            int[][] temp;
            temp = new int [2][3];
            temp[0][0] = 4;
            temp[0][1] = 3;
            temp[0][2] = 10;
            temp[1][0] = 2;
            temp[1][1] = 4;
            temp[1][2] = 5;
            
           Arrays.sort(temp);
           
           System.out.println("POBLACION INICIAL DESPUÉS:");
            System.out.print("[");
            for(int i=0; i<2; i++){
                System.out.print("[");
                for(int j=0; j<3; j++){
                    System.out.print(temp[i][j]+",");
                }
                System.out.print("]");
            }
            System.out.print("]");

            
            
           
//            System.out.println("PRUEBA CON 3: Debería dar 4 filas, 3 columnas");
//            p.poblacionInicial(presentaciones);
//            System.out.println("PRUEBA CON 5: Debería dar 6 filas, 5 columnas");
//            int presentaciones2[] = {2,4,7,8,9};
//            p.poblacionInicial(presentaciones2);
//            System.out.println("PRUEBA CON 6: Debería dar 11 filas, 6 columnas");
//            int presentaciones3[] = {2,4,7,8,9,7};
//            p.poblacionInicial(presentaciones3);
//            System.out.println("PRUEBA CON 9: Debería dar 21 filas, 9 columnas");
//            int presentaciones4[] = {1,2,3,4,5,6,7,8,9};
//            p.poblacionInicial(presentaciones4);
        // TODO code application logic here
    }
    
}
