/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.rsg.graficos;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Clase propia de Figura personalizada. De ella heredarán todas las demás 
 * figuras. En esta clase estarán definidos los métodos que tendrán todas las
 * figuras.
 * @author Ramon
 */
public abstract class RFigura2D{
    
    // Atributos que tendran las figuras
    // Atributo color, referene al color que tendrá la figura las lineas y 
    // el color de relleno
    private Color color = Color.black;
    private boolean relleno = false;
    private Color colorRelleno = Color.black;
    private Color colorRellenoDos = Color.white;
    
    // Trazo, varia tanto el tamaño como el tipo
    private int grosor = 1; // el menor, por defecto
    private int tipoTrazo = 0; // el trazo contiuo hara referencia a 1
          
    private float patronDiscontinuidad[] = {3.0f, 3.0f};    

    Stroke trazoContinuo = null;
    Stroke trazoDiscontinuo = null;
    
    private int tipoRelleno = 0;
    private Paint rellenoDos = null;
    private Point2D pc1 = new Point2D.Double(-15,-15);
    private Point2D pc2 = new Point2D.Double(-15,-15);
    
    private Composite composicion = null;
    //AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
    private int valorTransparencia = 100;
    
    private boolean alisada = false;
    RenderingHints render = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
    RenderingHints renderAux = null;
    
    // necesito un objeto Shape
    public Shape geometria; // lo instanciare en las clases hijas
    
    /**
     * Método para dibujar las figuras. Compartido por todas las figuras, 
     * se encargará de pintar la figura con los atributos correspondientes. Cada
     * figura tiene atributos diferentes, por eso recorrerá todos estos atributos
     * y dependiendo del valor, los pintará. 
     * @param g2d Objeto Graphics2D que usará para pintarlo
     */
    
    public void dibujarFigura(Graphics2D g2d){
        g2d.setColor(color);
        if(tipoTrazo==0){
            // Lo inicializo aqui porque tendra un grosor diferente cada vez,
            // ya que no dispone de ningun metodo que permita editar el grosor
            trazoContinuo = new BasicStroke(grosor);
            g2d.setStroke(trazoContinuo);
        }else if(tipoTrazo==1){
            // Los inicializo aquí porque tendran un grosor diferente cada vez
            trazoDiscontinuo = new BasicStroke(grosor,
                                BasicStroke.CAP_ROUND,
                                BasicStroke.JOIN_MITER, 1.0f,
                                patronDiscontinuidad, 0.0f);
            g2d.setStroke(trazoDiscontinuo);
        }
        
        if(alisada){
            g2d.setRenderingHints(render);
        }else{
            renderAux  = (RenderingHints)render.clone();
            renderAux.clear(); // si lo hago sobre la original, me la cargo
            g2d.setRenderingHints(renderAux);
        }
        
        //Algo parecido ocurre con este metodo, no puedo inicializarlo fuera, necesito el valor
        composicion = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)valorTransparencia/100.0F);
        g2d.setComposite(composicion);
        g2d.draw(geometria);
        
        if (esRellenable()) {
            if (this.relleno) {
                if(this.tipoRelleno==0){
                    g2d.setColor(colorRelleno);
                }else{
                    // El primer punto es común para ambos
                    pc1.setLocation(this.getX(),this.getY());
                    if(this.tipoRelleno==1){ // Relleno horizontal  
                        pc2.setLocation(this.getX()+this.getWidth(),this.getY());
                    }else if(this.tipoRelleno==2){ // Relleno vertical
                        pc2.setLocation(this.getX(),this.getY()+this.getHeight());                    
                    }
                    // El gradiente he de crearlo aqui con los puntos de la figura
                    // no existe ningun metodo que permita modificarlo, he de crearlo de nuevo cada vez
                    rellenoDos = new GradientPaint(pc1, this.colorRelleno, pc2, this.colorRellenoDos);
                    g2d.setPaint(rellenoDos);
                }
                g2d.fill(geometria);
            }
        }
    }
    
    /**
     * Método para ver si una figura es Rellenable. Por defecto, este
     * método devuelve falso.
     * @return Valor false
     */
    
    public boolean esRellenable(){
        return false;
    }
    
    /**
     * Método para obtener los bounds de una figura. Se encargará de llamar
     * al método getBounds() de mi figura.
     * @return Devuelve una figura del tipo Rectangle2D cuya forma será
     * la del contorno de mi figura.
     */
    
    public Rectangle2D getBounds(){
        return geometria.getBounds2D();
    }
    
    /**
     * Método para obtener la coordenada X. Cada clase hija
     * lo implementa de una forma u otra
     * 
     * @return Coordenada X de la figura.
     */
    
    public abstract double getX();
    
    /**
     * Método para obtener la coordenada Y. Cada clase hija lo
     * implementa de una forma u otra.
     * 
     * @return Coordeanda Y de la figura.
     */
    
    public abstract double getY();
    
    /**
     * Método para obtener el ancho de la figura. Devuelve por
     * defecto 0, ya que no todas las figuras tienen ancho. Se 
     * sobrecargará en las figuras que pueden ser rellenas.
     * @return Devuelve el valor 0
     */
    
    public double getWidth(){
        return 0;
    }
    
    /**
     * Método para obtener el alto de la figura. Devuelve por defecto 0, ya que
     * no todas las figuras tienen alto. Se sobrecargará en las figuras que
     * pueden ser rellenas.
     *
     * @return Devuelve el valor 0
     */
    
    public double getHeight(){
        return 0;
    }
    
    /**
     * Método actualizar figura. Actualiza la figura que acabamos de crear
     * gracias a dos puntos, que dependiendo del método donde se use, la figura
     * se actualizará de una forma u otra.
     * @param p1 Point referente al primer punto
     * @param p2 Point referente al segundo punto
     */
    
    public abstract void actualizarFigura(Point2D p1, Point2D p2);
    
    /**
     * Método setAlisada. Hace que la figura esté "alisada". 
     * @param alisada Recibe una variable booleana que hará que sea
     * alisada o no.
     */
    
    public void setAlisada(boolean alisada){
        this.alisada=alisada;
    }
    
    /**
     * Método getAlisada. Comprueba si mi figura está "alisada" o no.
     * @return Devuelve un valor booleano dependiendo de si está alisada o no
     * lo está.
     */
    
    public boolean getAlisada(){
        return alisada;
    }
    
    /**
     * Metodo para asignar color. Recibe por parámetro un nuevo color que se
     * le asignará a la figura. Dicho color es el del trazo.
     * @param color Variable de tipo Color con el nuevo color del trazo.
     */

    public void setColor(Color color) {
        this.color = color;
    }
    
    /**
     * Método setRelleno. Asigna a mi figura un valor booleano para indicar
     * que mi figura está rellena o no.
     * @param relleno Valor booleano que hará que mi figuras sea rellena o no.
     */

    public void setRelleno(boolean relleno) {
        this.relleno = relleno;
    }
    
    /**
     * Método para asignar color de relleno. Asignará a mi figura el color de
     * relleno que se le pase por parámetro.
     * @param colorRelleno Variable que indica el nuevo color de relleno que
     * quiero asignar.
     */

    public void setColorRelleno(Color colorRelleno) {
        this.colorRelleno = colorRelleno;
    }
    
    /**
     * Metodo set Color Relleno Secundario. Se encargará de asignar un nuevo
     * color de relleno secundario, para degradados.
     * @param colorRellenoDos Nuevo color que quiero asignar como color
     * secundario de relleno.
     */

    public void setColorRellenoDos(Color colorRellenoDos) {
        this.colorRellenoDos = colorRellenoDos;
    }
    
    /**
     * Método para obtener el color del trazo. Devolverá la variable que 
     * indica dicho color de mi figura.
     * 
     * @return Devuelve una variable de tipo Color indicado el color de mi
     * figura.
     */

    public Color getColor() {
        return color;
    }
    
    /**
     * Método isRelleno. Este método me indicará si mi figura está rellana
     * o no.
     * 
     * @return Variable booleana que indicará si está rellena o no. 
     */

    public boolean isRelleno() {
        return relleno;
    }
    
    /**
     * Método getColorRelleno. Devuelve el color de relleno, el normal, que
     * tiene mi figura.
     * @return Variable de tipo Color con el color de relleno de la figura.
     */

    public Color getColorRelleno() {
        return colorRelleno;
    }
    
    /**
     * Metodo para obtener el color secundario de relleno. Devuelve dicho
     * valor de la figura.
     * @return Variable Color que representa el color secundario de relleno.
     */

    public Color getColorRellenoDos() {
        return colorRellenoDos;
    }
    
    /**
     * Metodo getGrosor. Variable para obtener el grosor del trazo. Estará
     * indicado por una variable númérica
     * 
     * @return Variable del tipo int que indica el grosor de la figura.
     */

    public int getGrosor() {
        return grosor;
    }
    
    /**
     * Metodo geTipoTrazo. Variable para obtener el tipo de trazo de la
     * figura. Devolverá un valor que hace referencia al tipo de trazo que usa.
     * 
     * @return Variable int que indica el tipo de trazo que tiene la figura.
     */

    public int getTipoTrazo() {
        return tipoTrazo;
    }
    
    /**
     * Método para obtener el tipo de Relleno. Devolverá un valor entero
     * que representa el tipo de relleno que tiene la figura.
     * @return Valor int con el tipo de relleno que tiene la figura.
     */

    public int getTipoRelleno() {
        return tipoRelleno;
    }
    
    /**
     * Método para obtener el valor Transparencia. Devolverá el valor 
     * de transparencia que tiene la figura.
     * @return Devuelve un entero que representa el valor de transparencia.
     */

    public int getValorTransparencia() {
        return valorTransparencia;
    }
    
    /**
     * Método setGrosor. Método para indicar el tamaño del grosor del trazo
     * de mi figura RFigura2D. 
     * @param grosor Recibe un valor entero que hará referencia al tamaño del
     * trazo de mi figura.
     */
    
    public void setGrosor(int grosor){
        this.grosor=grosor;
    }
    
    /**
     * Metodo para mover la figura. Recibirá un punto hacia donde moverá mi 
     * figura.
     * @param p Variable Point2D que indica hacia donde moveré mi figura. 
     */
    
    public abstract void setLocation(Point2D p);
    
    /**
     * Metodo para asignar el tipo de Trazo. Recibirá un valor entero
     * que hace referencia al tipo de trazo que tendrá la figura y se lo
     * asignará.
     * @param tipoTrazo Valor entero que hace referencia al tipo de trazo
     * que tendrá mi figura
     */
    
    public void setTipoTrazo(int tipoTrazo){
        this.tipoTrazo=tipoTrazo;
    }
    
    /**
     * Metodo para asignar el tipo de Relleno. Recibirá un valor entero
     * que hace referencia al tipo de relleno que tendrá la figura y se lo
     * asignará.
     * @param tipoRelleno Tipo de relleno que tendrá mi figura 
     */
    
    public void setTipoRelleno(int tipoRelleno){
        this.tipoRelleno=tipoRelleno;
    }
    
    /**
     * Metodo para darle un valor de transparencia. Recibe un valor entero
     * que será el valor que tendrá de transparencia mi figura.
     * @param valorTransparencia El valor de transparencia.
     */
    
    
    public void setValorTransparencia(int valorTransparencia){
        this.valorTransparencia=valorTransparencia;
    }
    
    /**
     * Método contains. Devuelve true o false dependiendo de si el punto
     * que le paso por parámetro está dentro o cerca de una RFigura2D.
     * @param p El punto donde quiero buscar.
     * @return Valor booleano si contiene o no ese punto
     */
    
    public abstract boolean contains(Point2D p);
}
