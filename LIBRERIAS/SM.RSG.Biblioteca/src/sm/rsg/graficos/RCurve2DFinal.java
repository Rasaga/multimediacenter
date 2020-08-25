/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.rsg.graficos;

import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;

/**
 * Clase propia para Curva. Hereda de RFigura2D.
 * @author Ramon
 */
public class RCurve2DFinal extends RFigura2D {

    // Necesitare crear el constructor, que llamara al padre
    /**
     * Constructor de la figura "RCurve2DFinal". Recie por parámetro
     * los 3 puntos que constituyen la curva
     * @param p Primer Point de la curva
     * @param p2 Segundo Point de la curva
     * @param p3 Tercer Point de la curva
     */
    public RCurve2DFinal(Point2D p, Point2D p2, Point2D p3){
        geometria = new QuadCurve2D.Double(p.getX(),p.getY(),p2.getX(),p2.getY(),
                p3.getX(),p3.getY());
    }
    
    /**
     * Sobrecarga del método contains. Para ello, llamo al propio
     * de QuadCurve2D.
     * @param p Punto a comprobar si lo contiene la figura.
     * @return Devolverá true o false si contiene el punto p o no.
     */
    @Override
    public boolean contains(Point2D p){
        return ((QuadCurve2D)geometria).contains(p);
    }
    
    /**
     * Sobrecarga del método setLocation. Para ello, llamo al propio
     * de QuadCurve2D. Se encargará de calcular, a partir del punto que le paso,
     * la posición de los otros dos puntos de la curva. Después moverá
     * toda mi figura.
     * @param pos Point con la posición a donde quiero mover mi Curva.
     */
    @Override
    public void setLocation(Point2D pos) {
        double dx = pos.getX() - ((QuadCurve2D)geometria).getX1();
        double dy = pos.getY() - ((QuadCurve2D)geometria).getY1();
        Point2D newp2 = new Point2D.Double(((QuadCurve2D)geometria).getX2() + dx, ((QuadCurve2D)geometria).getY2() + dy);
        Point2D newpc = new Point2D.Double(((QuadCurve2D)geometria).getCtrlX() + dx, ((QuadCurve2D)geometria).getCtrlY() + dy);
        ((QuadCurve2D)geometria).setCurve(pos, newpc, newp2);
    }
    
    /**
     * Sobrecarga del método actualizarFigura. Utilizará el método setCurve
     * de QuadCurve2D con los dos puntos que corresponden al principio y final
     * de la figura.
     * @param p1 Point de inicio de la figura
     * @param p2 Point de final de la figura
     */
    @Override
    public void actualizarFigura(Point2D p1, Point2D p2){
       ((QuadCurve2D)geometria).setCurve(p1,p2,p2);
    }
    
    /**
     * Método setCurve. Recibirá tres puntos que modificarán la
     * posición de mi curva. 
     * @param p1 Punto de inicio
     * @param p2 Punto de control
     * @param p3 Punto final
     */
    
    public void setCurve(Point2D p1, Point2D p2, Point2D p3){
       ((QuadCurve2D)geometria).setCurve(p1,p2,p3); 
    }
    
    /**
     * Método para modificar el punto de control de la curva. Recibirá 
     * un nuevo punto que modificará la posición del punto de control
     * de la curva.
     * @param pc Point con la posición del nuevo punto de control de mi curva.
     */
    
    public void setPuntoControl(Point2D pc){
       Point2D p1 = ((QuadCurve2D)geometria).getP1();
       Point2D p2 = ((QuadCurve2D)geometria).getP2();
       ((QuadCurve2D)geometria).setCurve(p1,pc,p2); 
    }

    /**
     * Sobrecarga del metodo getX. Devuelve la coordenada x
     * de mi figura, llamando al método getX1 de QuadCurve.
     * @return Valor double con la posición de la coordenada X.
     */
    
    @Override
    public double getX() {
        return ((QuadCurve2D)geometria).getX1();
    }

    /**
     * Sobrecarga del metodo getY. Devuelve la coordenada y
     * de mi figura, llamando al método getY1 de QuadCurve.
     * @return Valor double con la posición de la coordenada Y.
     */
    @Override
    public double getY() {
        return ((QuadCurve2D)geometria).getY1();
    }
    
}
