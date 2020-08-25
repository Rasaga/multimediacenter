/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.rsg.graficos;

import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Clase propia de la figura Trazo. Hereda de RFigura2D
 * @author Ramon
 */
public class RTrazo2DFinal extends RFigura2D {
    
    ArrayList<Integer> xPoints = new ArrayList();
    ArrayList<Integer> yPoints = new ArrayList();

    /**
     * Constructor de la figura Trazo. Crear un trazo a partir de un Punto p.
     * Dicho punto será el primero punto del trazo.
     * @param p Variable Point2D con la que crearé el Trazo.
     */
    public RTrazo2DFinal(Point2D p){
        this.xPoints.add((int)p.getX());
        this.yPoints.add((int)p.getY());
        geometria = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
        this.crearRecorrido();
    }

    /**
     * Sobrecarga del método contains. Para ello, llamo al propio
     * de GeneralPath.
     * @param p Punto a comprobar si lo contiene la figura.
     * @return Devolverá true o false si contiene el punto p o no.
     */    
    
    @Override
    public boolean contains(Point2D p){
        return ((GeneralPath)geometria).contains(p);
    }
    
    /**
     * Metodo que mueve mi figura Trazo. Siguiendo la idea que se dijo en clase
     * y que GeneralPath lo permite, utilizaré el método transform de la clase
     * GeneralPath para mover mi figura. Para ello:
     *      - Creo un AffineTransform vacio.
     *      - Despues calculo la diferencia entre la pos de entrada y el getCurrentPoint 
     *      de mi figura. 
     *      - Hago la diferencia para utilizar el método translate 
     *      de AffineTransform y mover esa cantidad mi trazo. Puede salir
     *      negativa, lo que indicará que se moverá negativamente por ese eje.
     * @param pos El punto hacia donde quiero mover mi figura.
     */
    @Override
    public void setLocation(Point2D pos) {
        // Tengo que crear un desplazamiento de mi figura hacia la nueva posicion
        // Para poder calcular esto, tengo que ver QUE CANTIDAD ha de desplazarse
        double dx = pos.getX() - ((GeneralPath)geometria).getCurrentPoint().getX();
        double dy = pos.getY() - ((GeneralPath)geometria).getCurrentPoint().getY();
        // Cuando sepa la cantidad, puedo aplicar un affine transform con dicha
        // cantidad y mover mi trazo
        AffineTransform desplazo = new AffineTransform();
        desplazo.translate(dx, dy);
        //desplazo.translate(pos.getX(),pos.getY());
        //desplazo.setToTranslation(pos.getX(), pos.getY());
        ((GeneralPath)geometria).transform(desplazo);
        //System.out.println(xPoints.toString());
    }
    
    /**
     * Sobrecarga del método actualizarFigura. Recibe un primer punto de inicio,
     * que no es usado para nada, y un segundo punto, el cual se añadirá a mis
     * arrays de puntos para crear el generalPath después, al llamar al metodo
     * crearRecorrido.
     * @param p1 Punto de inicio (aunque no se usa para nada).
     * @param p2 Point2D que hace referencia al nuevo punto que se añade al
     * generalPath.
     */
    
    @Override
    public void actualizarFigura(Point2D p1, Point2D p2){
       // Añado los puntos al array
       this.xPoints.add((int)p2.getX());
       this.yPoints.add((int)p2.getY());
       // Y recreo mi figura
       this.crearRecorrido();
    }
    
    /**
     * Método para crear el recorrido. En él, utilizo los métodos moveTo y 
     * lineTo para crear mi generalPath con los puntos que he ido almacenando.
     * Cada vez que se añada un punto, volveré a crear el recorrido (metodo
     * actualizarFigura)
     */
    public void crearRecorrido(){
        if (!xPoints.isEmpty()) {
            ((GeneralPath)geometria).moveTo(xPoints.get(0), yPoints.get(0));
        }

        if (xPoints.size() > 1) {
            for (int i = 1; i < xPoints.size() - 1; i++) {
                ((GeneralPath)geometria).lineTo(xPoints.get(i), yPoints.get(i));
            }
        }
    }
 
    /**
     * Sobrecarga del metodo getX. Devuelve la coordenada x
     * de mi figura, llamando al método getCurrentPoint().getX() 
     * de GeneralPath.
     * @return Valor double con la posición de la coordenada X.
     */    
    
    @Override
    public double getX() {
        return ((GeneralPath)geometria).getCurrentPoint().getX();
    }
   
    /**
     * Sobrecarga del metodo getY. Devuelve la coordenada y
     * de mi figura, llamando al método getCurrentPoint().getY() 
     * de GeneralPath.
     * @return Valor double con la posición de la coordenada Y.
     */       
    
    @Override
    public double getY() {
        return ((GeneralPath)geometria).getCurrentPoint().getY();
    }
    
}
