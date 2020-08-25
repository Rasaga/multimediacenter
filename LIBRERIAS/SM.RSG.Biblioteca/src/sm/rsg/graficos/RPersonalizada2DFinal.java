/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.rsg.graficos;

import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Clase propia para mi Figura Personalizada. Hereda de RFigura2D.
 * @author Ramon
 */
public class RPersonalizada2DFinal extends RFiguraRellenaFinal {
    
    Rectangle2D rectangulo = new Rectangle2D.Float();
    Rectangle2D rectangulo2 = new Rectangle2D.Float();
    Ellipse2D circulo = new Ellipse2D.Float();

    Area area1;
    Area area2;
    Area area3;
    
    /**
     * Constructor sin parámetros de la Figura Personalizada. Para diseñarla
     * he optado por la idea de Rectangle y Ellipse: crearé mi figura vacia, y
     * en actualizarFigura utilizaré los puntos para construirla. Para
     * crear dicha figura, crearé un objeto del tipo "Area".
     */
    
    public RPersonalizada2DFinal(){
        geometria = new Area();
    }

    /**
     * Sobrecarga del método contains. Para ello, llamo al propio
     * de Area.
     * @param p Punto a comprobar si lo contiene la figura.
     * @return Devolverá true o false si contiene el punto p o no.
     */      
    
    @Override
    public boolean contains(Point2D p){
        return ((Area)geometria).contains(p);
    }
    
    /**
     * Método sobrecargado para indicar nueva posición. Para hacer esto, el
     * procedimiento ha sido algo lioso:
     *      - Obtengo el alto y ancho de todas las figuras que componen
     *      mi area. Dado el diseño, puedo consultarlas porque son
     *      atributos de mi clase Figura Personalizada.
     *      - Calculo la diferencias entre las X e Y de mis rectangulos
     *      con los del circulo. Recordemos que el circulo abarca toda la 
     *      figura, por lo que su X e Y será el mismo que el de mi propia
     *      figura.
     *      - Muevo todas las figuras a la vez al nuevo punto utilizando
     *      las diferencias calculadas para cada figura. Cada una de ellas
     *      se moverá de forma diferente. Utilizo para ello el método setFrame
     *      para cada una de las figuras con los parámetros comentados (diferencia,
     *      punto nuevo, etc...)
     *      - Tras moverlas, las vuelvo a juntar, para ello creo nuevas áreas
     *      con las figuras modificadas (no he encontrado ningun metodo que permita
     *      modificar areas sin tener que crear una nueva).
     *      - Uso el método reset para borrar lo que había antes en mi figura.
     *      - Finalmente, añado todas mis areas, obteniendo mi figura personalzada.
     * @param pos Punto al que moveré mi figura.
     */
    
    @Override
    public void setLocation(Point2D pos) {
        // Primer Rectangulo
        double ancho_r1 = ((Rectangle2D)rectangulo).getWidth();
        double alto_r1 = ((Rectangle2D)rectangulo).getHeight();

        // Segundo Rectangulo
        double ancho_r2 = ((Rectangle2D)rectangulo2).getWidth();
        double alto_r2 = ((Rectangle2D)rectangulo2).getHeight();
        
        // Esfera
        double ancho_c = ((Ellipse2D)circulo).getWidth();
        double alto_c = ((Ellipse2D)circulo).getHeight();

        // Hago los set Frame
        
        // diferencia de rectangulos con el punto
        double d1_x = circulo.getX() - rectangulo.getX();
        //System.out.println("Diferencia: " + d1_x);
        //System.out.println(pos.getX());
        //System.out.println(rectangulo.getX());
        double d1_y = circulo.getY() - rectangulo.getY();
        
        double d2_x = circulo.getX() - rectangulo2.getX();
        double d2_y = circulo.getY() - rectangulo2.getY();
        
        // Le asigno las nuevas posiciones a todas las figuras
        ((Rectangle2D)rectangulo).setFrame(pos.getX() + Math.abs(d1_x), pos.getY()+Math.abs(d1_y), ancho_r1, alto_r1);
        ((Rectangle2D)rectangulo2).setFrame(pos.getX() + Math.abs(d2_x), pos.getY() + Math.abs(d2_y), ancho_r2, alto_r2);
        ((Ellipse2D)circulo).setFrame(pos.getX(), pos.getY(), ancho_c, alto_c);

        // Los convierto en área
        area1 = new Area(rectangulo);
        area2 = new Area(rectangulo2);
        area3 = new Area(circulo);

        // Tras editarlo, para no volver a pintar encima, necesito hacer un reset
        ((Area) geometria).reset();

        // Finalmente lo añado a mi area figura
        ((Area) geometria).add(area1);
        ((Area) geometria).add(area2);
        ((Area) geometria).add(area3);
    }
    
    /**
     * Metodo sobrecargado actualizarFigura para Figura Personalizada. Como dije
     * al igual que con Rectangle y Ellipse, recibirá dos puntos donde le dará
     * los valores iniciales a las figuras que la componen, las cuales da la
     * casualidad que son dos cuadrados y una esfera. Utilizará dichos parámetros
     * para:
     *      - Crear el primer rectangulo desde el primer punto hasta el punto
     *      que hay en la mitad de p1 y p2.
     *      - Crear el segundo rectangulo desde la mitad de distancias entre
     *      p1 y p2, hasta el segundo punto p2.
     *      - Crear el circulo con p1 y p2.
     *      - Convertirlos en area y unirlos en mi figura.
     *      - Hay que resetear mi figura cada vez que se modiquen, si no se hace
     *      se seguirán añadiendo areas a ella.
     * @param p1 El primer Point, de inicio
     * @param p2 El Point donde acabará la figura
     */
    
    @Override
    public void actualizarFigura(Point2D p1, Point2D p2){
        Point2D otro = new Point2D.Double((p1.getX() + p2.getX()) / 2,
                (p1.getY() + p2.getY()) / 2);
        circulo.setFrameFromDiagonal(p1, p2);
        rectangulo.setFrameFromDiagonal(p1, otro);
        rectangulo2.setFrameFromDiagonal(otro, p2);
        
        area1 = new Area(rectangulo);
        area2 = new Area(rectangulo2);
        area3 = new Area(circulo);
        
        // Tras editarlo, para no volver a pintar encima, necesito hacer un reset
        ((Area)geometria).reset();
        
        //figura = new Area(rectangulo);
        ((Area)geometria).add(area1);
        ((Area)geometria).add(area2);
        ((Area)geometria).add(area3);
    }

    /**
     * Sobrecarga del metodo getX. Devuelve la coordenada X
     * de mi figura, llamando al método getY de la figura circulo, dada la
     * forma de mi figura.
     * @return Valor double con la posición de la coordenada X.
     */      
    
    @Override
    public double getX() {
        // Como la figura principal es el circulo, con devolver las cosas de esta, bastaría
        return this.circulo.getX();
    }

    /**
     * Sobrecarga del metodo getY. Devuelve la coordenada Y
     * de mi figura, llamando al método getY de la figura circulo, dada la
     * forma de mi figura.
     * @return Valor double con la posición de la coordenada Y.
     */        
    
    @Override
    public double getY() {
        // Como la figura principal es el circulo, con devolver las cosas de esta, bastaría
        return this.circulo.getY();
    }

    /**
     * Método para obtener el ancho de la figura. Devuelve el valor
     * del ancho de la figura. En esta ocasión, y dada la composición de mi figura,
     * como el circulo la envuelve toda, con devolver el ancho de este circulo, bastaría.
     * @return Devuelve el valor del ancho de la figura
     */     
    
    @Override
    public double getWidth() {
        // Como la figura principal es el circulo, con devolver las cosas de esta, bastaría
        return this.circulo.getWidth();
    }

    /**
     * Método para obtener el alto de la figura. Devuelve el valor
     * del alto de la figura. En esta ocasión, y dada la composición de mi figura,
     * como el circulo la envuelve toda, con devolver el alto de este circulo, bastaría.
     * @return Devuelve el valor del alto de la figura
     */      
    
    @Override
    public double getHeight() {
        // Como la figura principal es el circulo, con devolver las cosas de esta, bastaría
        return this.circulo.getHeight();
    }
    
}
