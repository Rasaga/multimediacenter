/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.rsg.imagen;

import java.awt.image.ByteLookupTable;
import java.awt.image.LookupTable;

/**
 *
 * @author Ramon
 */
public class RFuncionPropiaFinal {

    /**
     * Método funcionPropia. Filtro del tipo LookupTable para la práctica Final.
     * Dicha función utiliza la función sin(x) + cos(x) para aplicarselo a todos
     * los componentes. Hay que tener en cuenta que el valor máximo de dicha
     * función es Raiz de 2, por lo que mi valor K será 255/raiz de 2.
     *
     * @param x Valor de entrada. En caso de que quiera editarse, pero no
     * aconsejo darle valores diferentes a 1.
     * @return Tabla del tipo LookupTable con los nuevos valores que se usará
     * para la transformación de la imagen.
     */
    public static LookupTable funcionPropia(double x) { // habrá que dejarla en static para mejor uso
        //double Max = Math.abs(Math.sin(Math.toRadians(255.0/2)));
        //System.out.println(Max);
        //double K = 255.0/1; // Porque 1 es el maximo del seno
        //double Max = (1.0/(byte) (Math.abs(Math.sin(Math.toRadians((double)(3.27592)))) +
        //           Math.abs(Math.cos(Math.toRadians((double)(Math.sqrt(3)* 3.27592))))));
        //double Max = (1.0/(Math.abs(-(Math.pow(255, 2)/100)+200)));
        //System.out.println(Max);
        double K = 255.0 / Math.sqrt(2);
        byte[] f = new byte[256];
        for (int i = 0; i < 256; i++) {
            //f[i] = (byte)(K*Math.abs(Math.cos(Math.toRadians((double)(w*i)))));
            //f[i] = (byte) (Math.abs(Math.sin(Math.toRadians((double)(w * i)))) +
            //       Math.abs(Math.cos(Math.toRadians((double)(Math.sqrt(3)* w * i)))));
            //f[i] = (byte)(K * (Math.abs(-(Math.pow(x*i, 2)/100)+200)));
            f[i] = (byte) (K * (Math.abs(Math.sin(Math.toRadians((double) x * i)))
                    + Math.sin(Math.toRadians((double) x * i))));
            //f[i] = (byte)(K * Math.abs((Math.pow(x*i, 3) + Math.pow(x*i,2) + x*i + 20)));
            //f[i] = (byte) (K * Math.abs(Math.sin(Math.toRadians((double)(w * i))))); // hay que pasarlo a radianes
            System.out.println(f[i]);
        }

        ByteLookupTable slt = new ByteLookupTable(0, f);
        return slt;
    }

}
