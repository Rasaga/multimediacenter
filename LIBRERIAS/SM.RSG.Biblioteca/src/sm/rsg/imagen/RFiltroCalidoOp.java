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
public class RFiltroCalidoOp extends BufferedImageOpAdapter{
    
    /** Clase propia que aplica el Filtro "Cálido" a una imagen. Dicho
     * filtro lo realiza pixel a pixel.
     */

    public RFiltroCalidoOp() {
    }

    /**
     * Sobrecarga el metodo filter. En esta ocasión, está diseñado para aplicar
     * un filtro cálido a la imagen. Para ello, a cada pixel le realiza la
     * siguiente transformación:
     *      - Para canales R: aumentar su valor, multiplicando por 1.75
     *      - Para canales G: aumentar su valor, multiplicando por 1.3
     *      - Para canales B: diminuir su valor, multiplicando por 0.9
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

        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                 int[] pixelComp=null;
                 pixelComp = src.getRaster().getPixel(x, y, pixelComp);
                 //System.out.println(pixelComp[0]);

                 // Aumento el color rojo y un poco del verde, el azul lo dejo igual
                 int calidoR = (int)min(255 , 1.75*(float)pixelComp[0]);
                 int calidoG = (int)min(255, 1.3*(float)pixelComp[1]);
                 int calidoB = (int)min(255, 0.9*(float)pixelComp[2]);
                 
                 /*
                 int calidoR = (int)min(255 ,0.8*(float)pixelComp[0]+ 0.1*(float)pixelComp[1] + 0.1*(float)pixelComp[2]);
                 int calidoG = (int)min(255, 0.2*(float)pixelComp[0]+ 0.6*(float)pixelComp[1] + 0.2*(float)pixelComp[2]);
                 int calidoB = (int)min(255, 0.1*(float)pixelComp[0]+ 0.1*(float)pixelComp[1] + 0.7*(float)pixelComp[2]);
                 */
                 // Hay que vigiliar el canal alpha
                 int calidoAlpha=0;
                 if(pixelComp.length==4)
                    calidoAlpha = pixelComp[3];
                 
                 int[] resultadoSin = {calidoR, calidoG, calidoB};
                 int[] resultadoCon = {calidoR, calidoG, calidoB, calidoAlpha};
                 if(pixelComp.length==3)
                    dest.getRaster().setPixel(x,y,resultadoSin);
                 else if(pixelComp.length==4)
                    dest.getRaster().setPixel(x,y,resultadoCon);
            }
        }
            return dest;
    }
}

