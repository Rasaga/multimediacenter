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
public class RSepiaOp extends BufferedImageOpAdapter{
    
    /**
     * Clase propia con la operación de filtro SepiaOp.
     */

    public RSepiaOp() {
    }

    /**
     * Método sobrecargado filter. Se encargará de, para cada Raster de la imagen
     * obtener sus píxeles, y para cada pixel realizar una serie de transformaciones, donde
     * al recontruir la imagen, esta tendrá un tono sepia.
     * @param src Imagen origen
     * @param dest Imagen destino
     * @return La imagen destino
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
                //Por hacer: efecto sepia
                 int[] pixelComp=null;
                 pixelComp = src.getRaster().getPixel(x, y, pixelComp);
                 //System.out.println(pixelComp[0]);
                 int sepiaR = (int)min(255 , 0.393*(float)pixelComp[0] + 0.769*(float)pixelComp[1] + 0.189*(float)pixelComp[2]);
                 int sepiaG = (int)min(255, 0.349*(float)pixelComp[0] + 0.686*(float)pixelComp[1] + 0.168*(float)pixelComp[2]);
                 int sepiaB = (int)min(255, 0.272*(float)pixelComp[0] + 0.534*(float)pixelComp[1] + 0.131*(float)pixelComp[2]);
                 
                 // Hay que vigiliar el canal alpha
                 int sepiaAlpha=0;
                 if(pixelComp.length==4)
                    sepiaAlpha = pixelComp[3];
                 
                 int[] resultadoSin = {sepiaR, sepiaG, sepiaB};
                 int[] resultadoCon = {sepiaR, sepiaG, sepiaB, sepiaAlpha};
                 if(pixelComp.length==3)
                    dest.getRaster().setPixel(x,y,resultadoSin);
                 else if(pixelComp.length==4)
                    dest.getRaster().setPixel(x,y,resultadoCon);
            
                 
                 //dest.getRaster().setPixel(x,y,resultado); 
            }
        }
            return dest;
    }
}

