/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.rsg.imagen;

import java.awt.image.BufferedImage;
import static java.lang.Math.min;
import sm.image.BufferedImageOpAdapter;

/**
 *
 * @author Ramon
 */
public class RFiltroEfectoTunelOp extends BufferedImageOpAdapter {
    
     /** Clase propia que aplica el Filtro "Efecto tunel" a una imagen. Dicho
     * filtro lo realiza componente a componente.
     */

    public RFiltroEfectoTunelOp() {
    }
    
    /**
     * Sobrecarga el metodo filter. En esta ocasión, está diseñado para aplicar
     * un filtro de "vision tunel" a la imagen. Para ello, a cada componente le 
     * realiza la siguiente transformación:
     *      - Al recorrer la imagen, dependiendo de la coordenada, si dicha
     *      coordenada está cerca de una esquina, oscurecerá la muestra. Cuanto
     *      más cerca esté del centro, más claro ser verá.
     *      - Para oscurecer la imagen, aplicaré un procentaje de oscuridad a
     *      al componente. Cuanto más cerca esté de las esquinas, mayor será el
     *      procentaje de oscuridad que se le aplique.
     *      - El procentaje se calcula dependiendo de lo lejos que esté
     *      la coordenada del centro. Para ello necesitaré calcular la longitud
     *      media de la altura y la anchura. Dependiendo de la posición leida, 
     *      calcularé el procentaje de una forma u otra (si está antes de la mitad,
     *      bastará con dividir entre el valor de la mitad, si está después, será
     *      del total).
     * @param src Imagen origen
     * @param dest Imagen destino
     * @return Variable BufferedImage de la imagen destino con el filtro
     * aplicado.
     */

    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        if (src == null) {
            throw new NullPointerException("src image is null");
        }
        if (dest == null) {
            dest = createCompatibleDestImage(src, null);
        }

        // vamos a obtener el ancho y el largo
        int malto = src.getHeight() / 2;
        int mancho = src.getWidth() / 2;

        // Defino los porcentajes, en double porque seran decimales
        double xPor = 0;
        double yPor = 0; 

        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                for (int band = 0; band < src.getRaster().getNumBands(); band++) {
                    int sample = src.getRaster().getSample(x, y, band);
                    
                    // en caso que estemos en la parte izquierda
                    if (x < mancho) {
                        xPor = x /(double)mancho;
                        //xPor = x / pizq; // calculo el procentaje de oscuro dependiendo de la posicion de x en comparacion
                        // al punto izquierda
                        //System.out.println(xPerCent);
                        // en caso que estemos en la parte derecha    
                    } else if (x > mancho) { //xPor = x/ancho;
                        xPor = Math.abs(x-(double)src.getWidth())/(double)mancho;
                        //xPor = (pizq - (x - pder)) / pizq; // calculo el porcentaje de oscuro dependiendo de la posicion de x
                        // en comparacion al punto derecha
                        // Para realizar esto, 
                    }

                    // hago lo mismo para los puntos arriba y abajo y el punto "y"
                    if (y < malto) {
                        yPor = y /(double)malto;
                    } else if (y > malto){
                        yPor = Math.abs(y - (double)src.getHeight())/(double)malto;
                    }

                    // multiplico sample por el nuevo resultado
                    sample = min(255, (int) (sample * xPor * yPor));
                    dest.getRaster().setSample(x, y, band, sample);
                }
            }
        }
        return dest;
    }
}
