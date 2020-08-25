/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.rsg.graficos;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Clase propia para Rectangle. Hereda de RFigura2D.
 * @author Ramon
 */

/* Rectangle2D no dispone del metodo setLocation. Lo tiene su clase hija
Rectangle. Lo que si tiene Rectangle2D.Double es un metodo llamado
setFrame, que permite cambiar la ubicacion y tamaño de un rectangulo.
Siguiendo ese pensamiento, he declarado esta clase propia, que hereda
de Rectangle2D.Double para implementar mi propio setLocation.
*/

public class RRectangle2DFinal extends RFiguraRellenaFinal {
    
    /**
     * Constructor sin parámetros de la figura Rectangle. Creará una Rectangle vacia,
     * sin ningún punto asignado a ella. La uso sin argumentos y la inicializo
     * en el apartado actualizarFigura, ya que no conozco el ancho ni el alto
     * que le daré a mi Rectangle.
     */
    
    public RRectangle2DFinal() {
        // utilizo figura de shape
        geometria = new Rectangle2D.Double();
    }
    
    /**
     * Sobrecarga del método setLocation. Para ello, llamo al propio
     * de Rectangle2D. Moverá mi figura hacia el nuevo punto.
     * @param p Point con la posición a donde quiero mover mi Curva.
     */
    
    @Override
    public void setLocation(Point2D p) {      
        double ancho = ((Rectangle2D)geometria).getWidth();
        double alto = ((Rectangle2D)geometria).getHeight();
        ((Rectangle2D)geometria).setFrame(p.getX(), p.getY(), ancho, alto);
    }
    
    /**
     * Sobrecarga del método actualizarFigura. Utilizará el método 
     * setFrameFromDiagonal de Rectangle2D con los dos puntos que corresponden 
     * al principio y final de la figura. Utilizará este método para crear
     * la figura.
     * @param p1 Point de inicio de la figura
     * @param p2 Point de final de la figura
     */    
    
    @Override
    public void actualizarFigura(Point2D p1, Point2D p2){
        ((Rectangle2D)geometria).setFrameFromDiagonal(p1, p2);
    }
 
    /**
     * Sobrecarga del método contains. Para ello, llamo al propio
     * de Rectangle2D.
     * @param p1 Punto a comprobar si lo contiene la figura.
     * @return Devolverá true o false si contiene el punto p o no.
     */     
    
    @Override
    public boolean contains(Point2D p1){
        return ((Rectangle2D)geometria).contains(p1);
    }

    /**
     * Sobrecarga del metodo getX. Devuelve la coordenada y
     * de mi figura, llamando al método getX de Rectangle2D.
     * @return Valor double con la posición de la coordenada X.
     */    
    
    @Override
    public double getX() {
        return ((Rectangle2D)geometria).getX();
    }
    
    /**
     * Sobrecarga del metodo getY. Devuelve la coordenada y
     * de mi figura, llamando al método getY de Rectangle2D.
     * @return Valor double con la posición de la coordenada Y.
     */    

    @Override
    public double getY() {
        return ((Rectangle2D)geometria).getY();
    }
    
    /**
     * Método para obtener el ancho de la figura. Devuelve el valor
     * del ancho de la figura, utilizando el de Rectangle2D.
     * @return Devuelve el valor del ancho de la figura
     */    

    @Override
    public double getWidth() {
        return ((Rectangle2D)geometria).getWidth();
    }

    /**
     * Método para obtener el alto de la figura. Devuelve el valor
     * del alto de la figura, utilizando el de Rectangle2D.
     * @return Devuelve el valor del alto de la figura
     */        
    
    @Override
    public double getHeight() {
        return ((Rectangle2D)geometria).getHeight();
    }


}
