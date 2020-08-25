/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.rsg.graficos;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * Clase propia para Ellipse. Hereda de RFigura2D.
 * @author Ramon
 */



public class REllipse2DFinal extends RFiguraRellenaFinal {
    
    /**
     * Constructor sin parámetros de la figura Ellipse. Creará una Ellipse vacia,
     * sin ningún punto asignado a ella. La uso sin argumentos y la inicializo
     * en el apartado actualizarFigura, ya que no conozco el ancho ni el alto
     * que le daré a mi Ellipse.
     */
    
    public REllipse2DFinal() {
        // utilizo figura de shape
        geometria = new Ellipse2D.Double();
    }
    
    /**
     * Sobrecarga del método setLocation. Para ello, llamo al propio
     * de Ellipse2D. Moverá mi figura hacia el nuevo punto.
     * @param p Point con la posición a donde quiero mover mi Curva.
     */
    
    @Override
    public void setLocation(Point2D p) {        
        double ancho = ((Ellipse2D)geometria).getWidth();
        double alto = ((Ellipse2D)geometria).getHeight();
        ((Ellipse2D)geometria).setFrame(p.getX(), p.getY(), ancho, alto);
    }
    
    /**
     * Sobrecarga del método actualizarFigura. Utilizará el método 
     * setFrameFromDiagonal de Ellipse2D con los dos puntos que corresponden 
     * al principio y final de la figura. Utilizará este método para crear
     * la figura.
     * @param p1 Point de inicio de la figura
     * @param p2 Point de final de la figura
     */
    @Override
    public void actualizarFigura(Point2D p1, Point2D p2){
        ((Ellipse2D)geometria).setFrameFromDiagonal(p1, p2);
    }
    
    /**
     * Sobrecarga del método contains. Para ello, llamo al propio
     * de Ellipse2D.
     * @param p1 Punto a comprobar si lo contiene la figura.
     * @return Devolverá true o false si contiene el punto p o no.
     */ 
    
    @Override
    public boolean contains(Point2D p1){
        return ((Ellipse2D)geometria).contains(p1);
    }

    /**
     * Sobrecarga del metodo getX. Devuelve la coordenada y
     * de mi figura, llamando al método getX de Ellipse2D.
     * @return Valor double con la posición de la coordenada X.
     */
    
    @Override
    public double getX() {
        return ((Ellipse2D)geometria).getX();
    }

    /**
     * Sobrecarga del metodo getY. Devuelve la coordenada y
     * de mi figura, llamando al método getY de Ellipse2D.
     * @return Valor double con la posición de la coordenada Y.
     */
    
    @Override
    public double getY() {
        return ((Ellipse2D) geometria).getY();
    }
   
    /**
     * Método para obtener el ancho de la figura. Devuelve el valor
     * del ancho de la figura, utilizando el de Ellipse2D.
     * @return Devuelve el valor del ancho de la figura
     */
    
    @Override
    public double getWidth() {
        return ((Ellipse2D) geometria).getWidth();
    }
    
    /**
     * Método para obtener el alto de la figura. Devuelve el valor
     * del alto de la figura, utilizando el de Ellipse2D.
     * @return Devuelve el valor del alto de la figura
     */    

    @Override
    public double getHeight() {
        return ((Ellipse2D) geometria).getHeight();
    }


}
