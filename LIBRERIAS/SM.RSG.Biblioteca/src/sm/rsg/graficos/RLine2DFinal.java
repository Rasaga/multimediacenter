/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.rsg.graficos;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * Clase propia de la figura Line. Hereda de RFigura2D
 * @author Ramon
 */
public class RLine2DFinal extends RFigura2D {

    /**
     * Constructor de la figura Line. Creará el objeto Line2D a partir de los
     * dos puntos que se le pasan por parámetro. He decidido crearla asi por
     * comodidad, ya que ya la tenía hecha, pero podríah haber hecho como mis 
     * fuguras RRectangle2DFinal y REllipse2DFinal y haber creado el constructor
     * sin argumentos.
     * @param p Punto de inicio de la línea.
     * @param p2 Punto final de la linea.
     */
    public RLine2DFinal(Point2D p, Point2D p2){
        geometria = new Line2D.Float(p,p2);
    }
    
    /**
     * Método isNear. El método isNear no existe en Line2D, por lo que
     * voy a tener que crearlo a partgir del método ptLineDist. Este método
     * lo usaré para comprobar si un punto p está en la figura o cerca de ella.
     * @param p El punto que se quiere comprobar si está dentro o cerca.
     * @return Variable booleana que indicará true si cumple las condiciones, 
     * y en caso contrario, false.
     */
    public boolean isNear(Point2D p){
        //return this.ptLineDist(p)<=2.0;
        return ((Line2D)geometria).ptLineDist(p)<=2.0;
    }
    
    /**
     * Sobrecarga del método contains. En esta ocasión, llamará al método
     * isNear con el parámetro p.
     * @param p El punto que se quiere comprobar
     * @return True si contiene dicho punto o está cerca, false si no.
     */
    @Override
    public boolean contains(Point2D p){
        return isNear(p);
    }
    
    /**
     * Sobrecarga del método setLocation. Para ello, utilizaré el método
     * setLine de la clase Line2D, donde moveré el primer punto de la linea
     * al la posición deseada, y el segundo se moverá dependiendo de la distancia
     * entre el primer y segundo punto de mi Linea.
     * @param pos Posición a donde se quiere mover la figura Line.
     */
    @Override
    public void setLocation(Point2D pos) {
        double dx = pos.getX() - ((Line2D)geometria).getX1();
        double dy = pos.getY() - ((Line2D)geometria).getY1();
        Point2D newp2 = new Point2D.Double(((Line2D)geometria).getX2() + dx, ((Line2D)geometria).getY2() + dy);
        ((Line2D)geometria).setLine(pos, newp2);
    }
    
    /**
     * Sobrecarga del método actualizarFigura. Modificará los puntos de mi 
     * figura a los dos puntos que le paso por parámetro.
     * @param p1 Nuevo punto de inicio de Line2D
     * @param p2 Nuevo punto de final de Line2D
     */
    @Override
    public void actualizarFigura(Point2D p1, Point2D p2){
        ((Line2D)geometria).setLine(p1, p2);
    }
    
    /**
     * Sobrecarga del metodo getX. Devuelve la coordenada x
     * de mi figura, llamando al método getX1 de Line2D.
     * @return Valor double con la posición de la coordenada X.
     */    
    
    @Override
    public double getX() {
        return ((Line2D)geometria).getX1();
    }

    /**
     * Sobrecarga del metodo getY. Devuelve la coordenada y
     * de mi figura, llamando al método getX1 de Line2D.
     * @return Valor double con la posición de la coordenada y.
     */       
    
    @Override
    public double getY() {
        return ((Line2D)geometria).getY1();
    }
    
}
