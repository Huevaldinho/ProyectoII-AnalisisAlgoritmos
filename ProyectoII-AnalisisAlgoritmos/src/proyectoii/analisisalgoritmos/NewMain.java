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

       
    public static void main(String[] args) {
        System.out.println("ALGORITMO GENÉTICO CON 3 PRESENTACIONES"); 
        AlgoritmoGenetico g3 = new AlgoritmoGenetico(new int[]{3,7,2},13);
        g3.generaciones();
        System.out.println("////////////FIN DE GENÉTICO CON 3 PRESENT////////////////");
        System.out.println("");
        System.out.println("");
        System.out.println("ALGORITMO GENÉTICO CON 5 PRESENTACIONES"); 
        AlgoritmoGenetico g5 = new AlgoritmoGenetico(new int[]{2,3,7,5,10},55);
        g5.generaciones();     
        System.out.println("////////////FIN DE GENÉTICO CON 5 PRESENT////////////////");
        System.out.println("");
        System.out.println("");
        System.out.println("ALGORITMO GENÉTICO CON 6 PRESENTACIONES"); 
        AlgoritmoGenetico g6 = new AlgoritmoGenetico(new int[]{7,9,10,11,13,15},25);
        g6.generaciones(); 
        System.out.println("////////////FIN DE GENÉTICO CON 6 PRESENT////////////////");
        System.out.println("");
        System.out.println("");
        System.out.println("ALGORITMO GENÉTICO CON 9 PRESENTACIONES"); 
        AlgoritmoGenetico g9 = new AlgoritmoGenetico(new int[]{30,41,11,13,15,17,19,23,29},47);
        g9.generaciones(); 
    }
    
}
