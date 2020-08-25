/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.rsg.imagen;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.LookupTable;
import java.awt.image.WritableRaster;

/**
 * Clase con funciones propias vistas durante el curso.
 * @author Ramon
 */
public class RFuncionesPropias {
    
    // Constructor por defecto, en java ya existe si no se declara, asi que no hay que hacer nada
    /**
     * Metodo copiaImagen. Realiza la copia de una imagen en profundidad, copindo
     * desde el raster, el colorMode, y el alphapremultiplied.
     * @param imgSource La imagen que quiero copiar
     * @return Devuelve una copia de imgSource, como una variable del tipo BufferedImage
     */
    public static BufferedImage copiaImagen(BufferedImage imgSource){
        ColorModel cm = imgSource.getColorModel();
        WritableRaster raster = imgSource.copyData(null);
        boolean alfaPre = imgSource.isAlphaPremultiplied();
        return new BufferedImage(cm, raster, alfaPre, null);
    }
    
    /**
     * Método seno. Filtro del tipo LookupTable para la práctica Final.
     * Dicha función utiliza la función sinuidal para aplicarselo a todos
     * los componentes.
     *
     * @param w Valor de entrada. En la práctica vimos que el valor máximo
     * era 180.0/255.0
     * @return Tabla del tipo LookupTable con los nuevos valores que se usará
     * para la transformación de la imagen.
     */
    
    public static LookupTable seno(double w){ // habrá que dejarla en static para mejor uso
        //double Max = Math.abs(Math.sin(Math.toRadians(255.0/2)));
        //System.out.println(Max);
        double K = 255.0/1; // Porque 1 es el maximo del seno
        byte[] f = new byte[256];
        for (int i = 0; i < 256; i++) {
            f[i] = (byte) (K * Math.abs(Math.sin(Math.toRadians((double)(w * i))))); // hay que pasarlo a radianes
            System.out.println(f[i]);
        }

        
        ByteLookupTable slt = new ByteLookupTable(0, f);
        return slt;
    }
    
     /**
     * Método seno. Filtro del tipo LookupTable para la práctica Final.
     * Dicha función utiliza la función invertida para aplicarselo a todos
     * los componentes, y obtener así después una imagen con los colores
     * invertidos.
     *
     * @return Tabla del tipo LookupTable con los nuevos valores que se usará
     * para la transformación de la imagen.
     */
    
    public static LookupTable invertir(){ // habrá que dejarla en static para mejor uso
        //double Max = Math.abs(Math.sin(Math.toRadians(255.0/2)));
        //System.out.println(Max);
        double K = 255.0/1; // Porque 1 es el maximo del seno
        byte[] f = new byte[256];
        for (int i = 0; i < 256; i++) {
            f[i] = (byte) (Math.abs(255-i));
            System.out.println(f[i]);
        }

        
        ByteLookupTable slt = new ByteLookupTable(0, f);
        return slt;
    }
    
    /**
     * Función cambioEspacioColor. Realiza el cambio de espacio de colores a una
     * imagen que se pasa por parámetro. También se le pasa el tipo de ColorSpace
     * que se quiere usar a través de una variable de tipo entero.
     * @param imgSource Imagen origen
     * @param mi_cs Valor entero que hace referencia al espacio de colores que
     * quiero usar
     * @return Una nueva imagen con el espacio de colores aplicado
     */
    
    public static BufferedImage cambioEspacioColor(BufferedImage imgSource, int mi_cs){
        ColorSpace cs = ColorSpace.getInstance(mi_cs);
        ColorConvertOp cop = new ColorConvertOp(cs, null);
        return cop.filter(imgSource, null);
    }
}

// para la rotacion, hay que hacerla sobre la imagen del lienzo anterior
// en este caso, las dimensiones son distintas que las del lienzo

// transparencia, contructor de sclaeop, alfa a 1 y beta a 0

// drawImage(con todos los parametros, sobretodo el quinto y el sexo)