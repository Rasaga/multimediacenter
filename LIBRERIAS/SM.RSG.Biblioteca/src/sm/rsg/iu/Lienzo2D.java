/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.rsg.iu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import sm.rsg.graficos.RCurve2DFinal;
import sm.rsg.graficos.REllipse2DFinal;
import sm.rsg.graficos.RFigura2D;
import sm.rsg.graficos.RLine2DFinal;
import sm.rsg.graficos.RPersonalizada2DFinal;
import sm.rsg.graficos.RRectangle2DFinal;
import sm.rsg.graficos.RTrazo2DFinal;

/**
 *
 * @author Ramon
 */
public class Lienzo2D extends javax.swing.JPanel {
    

    private boolean pintarBordes = true;
    
    private int herramienta = 1; // inicializado a 0 por si da algun error
    private Color color = Color.black; // Color por defecto
    private Color colorRelleno = Color.black; // necesario practica final
    private Color colorRellenoDos = Color.white;
    private int grosor = 1; // no existe con valor 0
    //private Stroke stroke = new BasicStroke(grosor);
    private int tipoTrazo = 0;
    
    private boolean relleno = false;
    private boolean transparente = false;
    private boolean alisado = false;
    private boolean editable = false;
    
    
    // Vector de formas del Lienzo
    //private List<Shape> vShape = new ArrayList();
    private List<RFigura2D> vShape = new ArrayList();
    
    // Punto auxiliar para dibujar cuadrados y ovalos
    private Point2D pAux = null;
    // Otro punto auxiliar que guarda el punto original
    private Point2D p_ori = null;
    // probando
    private double xp,yp;
    private Point2D p3 = new Point2D.Double(-10,-10);
    private Point2D punto_nuevo = null;
    
    // Forma auxiliar
    //private Shape selectedShape = null;
    private RFigura2D selectedShape = null;
    
    // bounds de la figura
    private Rectangle2D bounds = null;
    
    // Forma para el contorno del lienzo, por defecto 300x300
    private Shape contorno = new Rectangle2D.Float(0,0,300,300);
    // y su contorno
    // Vamos a crear un trazo discontinuo de trazo 10
    float patronDiscontinuidad[] = {15.0f, 15.0f};
    private Stroke trazo_contorno = new BasicStroke(10.0f,
            BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_MITER, 1.0f,
            patronDiscontinuidad, 0.0f);
    
    private int tipoRelleno = 0;
    
    private int valorTransparencia = 100;
    
    // para el cursor
    private Cursor cursorMovimiento = new Cursor(Cursor.MOVE_CURSOR);
    
    // para el punto de control de la curva
    private boolean faltaPuntoControl = false;
    
    // Variables que me permitiran comprobar si una figura puede subir o bajar en el array
    boolean puedeSubir = false;
    boolean puedeBajar = false;
    
    /**
     * Creo un nuevo lienzo2D(). Dicho lienzo2D se inicializa con todos los
     * atributos que tendrá inicialmente cuando cree una Nueva Ventana, entre
     * ellos que la herramienta selecionada es la linea, que el color es negro,
     * etc...
     */
    public Lienzo2D() {
        initComponents();
    }

    /* Metodos auxiliares usados en Lienzo2D, para la modificación y consulta
    de los parámetros.
     */
    
    /**
     * Obtener Herramienta. Devuelve la última herramienta selecionada
     * en un lienzo o el tipo de herramienta que con el que se pinto la figura
     * seleccionada.
     * @return Indice de dicha herramienta
     */
    
    public int getHerramienta() {
        return herramienta;
    }

    /**
     * Cambiar herramienta seleccionada. Modifica el valor de la 
     * herramienta seleccionada que tiene el lienzo y el shape seleccionado
     *
     * @param herramienta Indice de la nueva herramienta seleccionada
     */
    public void setHerramienta(int herramienta) {
        this.herramienta = herramienta;
    }
    
    /**
     * Obtener color. Devuelve el color del shape seleccionado o el último 
     * color seleccionado en una ventana Interna.
     * @return Color del shape o de la ventana activa
     */

    public Color getColor() {
        return color;
    }

    /**
     * Cambiar color del trazo. Modifica el color del trazo que
     * tiene el lienzo seleccionado y el shape seleccionado
     *
     * @param color Nuevo color del trazo
     */
    
    public void setColor(Color color) {
        this.color = color;
        if (selectedShape != null) {
            this.selectedShape.setColor(color);
        }
    }
    
     /**
     * Obtener color de relleno. Devuelve el color de relleno del shape 
     * seleccionado o el último 
     * color seleccionado en una ventana Interna.
     * @return Color de relleno del shape o de la ventana activa
     */

    public Color getColorRelleno() {
        return this.colorRelleno;
    }

    /**
     * Cambiar color del relleno. Modifica el color del relleno que
     * tiene el lienzo seleccionado y el shape seleccionado
     *
     * @param colorRelleno Nuevo color del relleno
     */
    
    public void setColorRelleno(Color colorRelleno) {
        this.colorRelleno = colorRelleno;
        if (selectedShape != null) {
            this.selectedShape.setColorRelleno(colorRelleno);
        }
    }
     /**
     * Obtener color de relleno secundario. Devuelve el color secundario de relleno 
     * del shape seleccionado o el último 
     * color seleccionado en una ventana Interna.
     * @return Color de relleno secundario del shape o de la ventana activa
     */
    
    public Color getColorRellenoDos() {
        return this.colorRellenoDos;
    }

    /**
     * Cambiar color de relleno secundario. Modifica el color de rellen secundario
     * que tiene el lienzo seleccionado y el shape seleccionado
     *
     * @param colorRelleno Nuevo color de relleno secundario del trazo
     */
    
    public void setColorRellenoDos(Color colorRelleno) {
        this.colorRellenoDos = colorRelleno;
        if (selectedShape != null) {
            this.selectedShape.setColorRellenoDos(colorRelleno);
        }
    }
    
    /**
     * Obtener el grosor del trazo. Devuelve el valor del trazo de la
     * figura seleccionada o el último valor que seleccionadmos
     * en el Lienzo2D.
     * @return Valor del grosor del trazo.
     */

    public int getGrosor() {
        return grosor;
    }

    /**
     * Cambiar grosor del trazo. Modifica el grosor del trazo de la figura
     * selecionada o asigna un nuevo valor de trazo a la figura que se vaya
     * a pintar a continuación.
     * 
     * @param grosor Nuevo valor del grosor del trazo
     */
    public void setGrosor(int grosor) {
        this.grosor = grosor;
        if (selectedShape != null) {
            this.selectedShape.setGrosor(grosor);
        }
    }
    
    /**
     * Metodo para ver si está relleno. Metodo para obtener si una figura está
     * rellena o no.
     * @return Si dicha figura está rellena, o si la última seleccion fue
     * de relleno o no.
     */

    public boolean getEstaRelleno() {
        return relleno;
    }

    /**
     * Metodo para indicar que está relleno o no. Indica que la figura seleccionada
     * o la siguiente en dibujarse estará rellena o no.
     *
     * @param relleno Valor booleano para indicar que está rellena o no
     */
    public void setEstaRelleno(boolean relleno) {
        this.relleno = relleno;
        if (selectedShape != null) {
            this.selectedShape.setRelleno(relleno);
        }
    }
    
    /**
     * Metodo getEstaAlisado. Comprueba si dicha figura o si la última elección
     * en el Lienzo2D es que la figura que se vaya a dibujar está alisada o no.
     * @return Variable booleana con el resultado de dicha comprobación.
     */

    public boolean getEstaAlisado() {
        return alisado;
    }

    /**
     * Método setEstáAlisado. Hace que una figura seleccionada o indica al
     * Lienzo2D que la siguiente figura estará en modo "alisado.
     * @param alisado Variable booleana con el valor de dicha elección
     */
    
    public void setEstaAlisado(boolean alisado) {
        this.alisado = alisado;
        if (selectedShape != null) {
            this.selectedShape.setAlisada(alisado);
        }
    }
    
    /**
     * Metodo getEditable. Método que devuelve si mi lienzo2D está en modo
     * edición o no.
     * @return Variable booleana indicando el resultado de dicho modo.
     */

    public boolean getEstaEditable() {
        return editable;
    }
    
    /**
     * Metodo setEditable. Método para indicar al Lienzo2D que está en modo
     * de edición.
     * @param editable Variable booleana con el resultado de si está en dicho
     * modo.
     */

    public void setEstaEditable(boolean editable) {
        this.editable = editable;
    }
    
    /**
     * Metodo para obtener el contorno. Devuelve el contorno actual del lienzo2D.
     * @return Devuelve un shape del contorno del Lienzo2D.
     */

    public Shape getContorno() {
        return contorno;
    }
    
    /**
     * Asignar un nuevo contorno. Asigna un nuevo contorno a la variable
     * de contorno de Lienzo.
     * @param contorno Nueva figura que servirá de contorno al Lienzo2D
     */

    public void setContorno(Shape contorno) {
        this.contorno = contorno;
    }
    
    // La transparencia de 50 al final no esta incluida, pero sí implementada
    
    /**
     * Metodo para ver si una figura es transparente.
     * @return Valor booleano en función de si lo es o no.
     */
    
    public boolean getEstaTransparente(){
        return this.transparente;
    }
    
    /**
     * Método para indicar transparencia. Hace que el valor de la figura
     * que se vaya a pintar lo haga con transparencia.
     * @param transparente Valor booleano que indica que se pintará transparente
     * o no
     */
    
    public void setEstaTransparente(boolean transparente){
        this.transparente=transparente;
    }
    
    /**
     * Metodo para obtener el tipo de Trazo. Devuelve el tipo de trazo con el 
     * que se ha pintado la figura, o el tipo de trazo que hay seleccionado
     * en el Lienzo.
     * @return El tipo de trazo asignado al lienzo y la figura seleccionada
     */
    

    public int getTipoTrazo() {
        return tipoTrazo;
    }
    
    /**
     * Metodo para indicar el tipo de trazo. Asigna el tipo de trazo con el
     * que se pintará la figura. También lo asigna al lienzo.
     * @param tipoTrazo Valor int que indica el tipo de trazo que quiero que 
     * tenga
     */
    

    public void setTipoTrazo(int tipoTrazo) {
        this.tipoTrazo = tipoTrazo;
        if (selectedShape != null) {
            this.selectedShape.setTipoTrazo(tipoTrazo);
        }
    }
    
    /**
     * Obtener el tipo de Relleno. Devuelve el tipo de relleno que tiene el
     * lienzo seleccionado o la figura seleccionada.
     * @return Valor int que hace referencia al tipo de relleno
     */

    public int getTipoRelleno() {
        return tipoRelleno;
    }

    /**
     * Cambiar tipo de Relleno. Modifica tipo de relleno que tiene el lienzo y
     * el shape seleccionado
     *
     * @param tipoRelleno Nuevo valor del tipo de Relleno
     */
    public void setTipoRelleno(int tipoRelleno) {
        this.tipoRelleno = tipoRelleno;
        if (selectedShape != null) {
            this.selectedShape.setTipoRelleno(tipoRelleno);
        }
    }

    /**
     * Cambiar valor de transparencia. Modifica el valor de la transparencia que
     * tiene el lienzo y el shape seleccionado
     *
     * @param valorTransparencia Nuevo valor de transparencia
     */
    public void setTransparencia(int valorTransparencia) {
        this.valorTransparencia = valorTransparencia;
        if (selectedShape != null) {
            this.selectedShape.setValorTransparencia(valorTransparencia);
        }
    }

    /**
     * Obtener transparencia.
     *
     * @return el valor actual de la transparencia
     */
    public int getTransparencia() {
        return valorTransparencia;
    }

    /**
     * Quitar bounds. Elimina el contorno de la figura.
     */
    public void quitarBounds() {
        this.bounds = null;
        this.repaint();
    }
    
    /**
     * Metodo setPintarBordes. Método que indica que quiero que se pinten
     * los bordes o no cuando se haga el volcado de la imagen.
     * @param pintarBordes Valor booleano con dicha afirmación de si quiero
     * que se pinten o no.
     */
    public void setPintarBordes(boolean pintarBordes) {
        this.pintarBordes = pintarBordes;
    }
    
    /**
     * Modificar el contorno del Lienzo. Modificará el contorno del lienzo
     * con el valor del ancho y alto que se le pasan por parámetro
     * @param alto nuevo valor de alto
     * @param ancho nuevo valor de ancho
     */
    
    public void modificarContorno(int alto, int ancho){
        // cambiare el tamaño del lienzo, y usare 0, 0 como coordenada
        ((Rectangle2D.Float)contorno).setFrame(0,0,alto, ancho);
    }
    
    /**
     * Obtiene si una figura puede subir.
     * 
     * @return el valor booleando de dicha acción.
     */
    
    public boolean puedeSubir(){
        return puedeSubir;
    }
    
    /**
     * Obtiene si una figura puede bajar.
     *
     * @return el valor booleando de dicha acción.
     */
    
    public boolean puedeBajar(){
        return puedeBajar;
    }
    
    /**
     * Comprobar Shape. Comprueba la posición del selectedShape del Lienzo2D,
     * si puede bajar o subir en el array de shapes. Dependiendo de su disponibilidad,
     * modificará el valor de dichas variables.
     * @return Devuelve un valor enteros dependiendo de la acción que pueda hacer
     * (util para el metodo cambiarPosicion())
     */
    
    public int comprobarShape() {
        int index = -1;

        if (vShape != null && selectedShape != null) {
            index = vShape.indexOf(selectedShape);
            if (index == 0) {
                puedeBajar = false;
            }
            if (index == vShape.size() - 1) {
                puedeSubir = false;
            }
            if (index != 0) {
                puedeBajar = true;
            }

            if (index != vShape.size() - 1) {
                puedeSubir = true;
            }
        }
        return index;
    }
    
    /**
     * Metodo para cambiar la posición de una figura. Dependiendo de la acción
     * que entre, irá al fondo, hacia delante, etc.
     * @param accion El tipo de movimiento que hará la figura
     */
    
    public void cambiarPosicion(int accion){
        int index = comprobarShape();

        // Hacemos el intercambio
        if (index != -1) {
            if (accion == 0 && puedeBajar) {
                // El metodo rotate solo me permite subir, asi que voy a invertir el vShape, subir, y volver a invertir
                Collections.reverse(vShape);
                Collections.rotate(vShape.subList(vShape.size() - 1 - index, vShape.size()), -1);
                Collections.reverse(vShape);
            } else if (accion == 1 && puedeBajar) {
                // Aplico el mismo truco aquí
                Collections.reverse(vShape);
                Collections.rotate(vShape.subList(vShape.size() - 1 - index, vShape.size() - 1 - index + 2), -1);
                Collections.reverse(vShape);
            } else if (accion == 2 && puedeSubir) {
                Collections.rotate(vShape.subList(index, index + 2), -1);
            } else if (accion == 3 && puedeSubir) {
                Collections.rotate(vShape.subList(index, vShape.size() - 1 + 1), -1);
            }

            this.repaint();
        }
    }
    
    /**
     * Método para seleccionar una figura. A partir de un punto, obtengo
     * la figura que esté en dicho lugar. (O en su defecto, la mas cercana)
     * @param p, punto donde he hecho el clic.
     * @return la figura más cercana a ese punto, o "null" si no hay ninguna
     */
    
    private RFigura2D getSelectedShape(Point2D p){
        for(RFigura2D s:vShape)
            if(s.contains(p))
                return s;
        return null;
    }
    
    /** Metodo paint. Solo se ocupará del dibujo de formas. Para ello recorrerá
     * el vector de RFigura2D y llamará al método paint de cada una de ellas.
     * Adicionalmente, también pintará los bordes del Lienzo2D y los bounds
     * de la figura seleccionada, en caso de que lo deseemos.
     * @param g Objecto Graphics necesario para usar el método super.paint(g)
    */
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        
        /*He añadido otro metodo aqui para pintar los bordes del Lienzo
        que no se verán en las imagenes, como el borde del lienzo o de las
        figuras seleccionadas */
        
        this.pintarBordes(g2d,pintarBordes);
        
        /* Pinto cada una de las figuras del vector */
        for(RFigura2D s:vShape){
            s.dibujarFigura(g2d);
        }
    }
    
    /**
     * Pintar Bordes. Este método se encargará de pintar los bordes del lienzo2D
     * y de las figuras selecionadas (bounds). Para ello recibe un objeto Graphics 2D,
     * que usará para pintar, y un valor booleano, que permitirá modificar la visibilidad
     * de dichos bordes.
     * 
     * @param g2d Objeto Graphics2D que se usará para pintar dichos bordes
     * @param pintarBordes Valor booleano para controlar la visibilidad de los bordes.
     */
    
    private void pintarBordes(Graphics2D g2d, boolean pintarBordes) {
        float patronDiscontinuidad[] = {3.0f, 3.0f};
        //Stroke trazoAux = new BasicStroke(grosor);
        Stroke stroke2 = new BasicStroke(3.0f,
                BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_MITER, 1.0f,
                patronDiscontinuidad, 0.0f);
        // Si queremos que aparezcan
        if (pintarBordes) {
            g2d.setStroke(trazo_contorno); // trazo discontinuo del contorno
            g2d.draw(contorno); // lo dibujo
            g2d.setClip(contorno); // y el clicp de la zona
            // También tenemos que dibujar el contorno de la figura seleccionada
            if (bounds != null && this.editable) {
                g2d.setStroke(stroke2);
                g2d.draw(bounds);
            }
        }
    }
    
    /**
     * Creación de la figura. Dependiendo de la figura seleccionada, 
     * (valor de la herramienta), creará un tipo u otro de figura. Después
     * asignará a la figura los atributos que haya actualmente seleccionados
     * y guardará dicha figura en un array de figuras del tipo RFigura2D.
     * @param p El punto desde donde se creará la figura inicialmente.
     */
    
    private void createShape(Point2D p){
        RFigura2D v = null;
        // Dependiendo de la herramienta seleccionada, RFigura2D será de un tipo
        // u otro
        if (herramienta == 1) {
            v = new RLine2DFinal(p, p);
        }
        else if (herramienta == 2) {
            v = new RRectangle2DFinal();
        } else if (herramienta == 3) {
            v = new REllipse2DFinal();
        } else if (herramienta == 4) {
            v = new RCurve2DFinal(p, p, p);
        } else if (herramienta == 5) {
            v = new RTrazo2DFinal(p);
        } else if (herramienta == 6) {
            v = new RPersonalizada2DFinal();
        }
        
        // Actualizo la figura con los atributos que tiene el Lienzo2D
        if (v != null) {
            v.setColor(this.getColor());
            v.setRelleno(this.getEstaRelleno());
            v.setColorRelleno(this.getColorRelleno());
            v.setColorRellenoDos(this.getColorRellenoDos());
            v.setGrosor(this.getGrosor());
            v.setTipoTrazo(this.getTipoTrazo());
            v.setTipoRelleno(this.getTipoRelleno());
            v.setValorTransparencia(this.getTransparencia());
            v.setAlisada(this.getEstaAlisado());
            vShape.add(v);
        }
        pAux = p; // punto donde empezara la figura   
    }
    
    private void updateShape(Point2D p) {
        // Cojo la ultima figura, que es la que acabo de crear
        if (vShape != null) {
            RFigura2D s = vShape.get(vShape.size() - 1);
            s.actualizarFigura(pAux, p);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed

        if(editable==true){
            // Para evitar creacion y destruccion del objeto, voy a crear selectedShape fuera
            selectedShape = this.getSelectedShape(evt.getPoint());
            if(selectedShape!=null){
                this.actualizarAtributosLienzo2D(selectedShape);
                bounds = selectedShape.getBounds();
                //System.out.println("NO ES NULO 1");
                //if(selectedShape instanceof rLine2D)
                //    ((rLine2D)selectedShape).setLocation(evt.getPoint());
                //else 
                    //if(selectedShape instanceof rRectangle2D_DOS){
                    System.out.println("ENTRA AQUI=?");
                    //((rRectangle2D)selectedShape).setLocation(evt.getPoint());
                //    p_ori = evt.getPoint();
                xp = evt.getX() - selectedShape.getX();
                yp = evt.getY() - selectedShape.getY();
                    // 
                    // Calculo la diferencia
                    //x = ((rRectangle2D_DOS)selectedShape).getX();
                    //y = ((rRectangle2D_DOS)selectedShape).getY();
                    //dif_x = p_ori.getX() - x;
                    //dif_y = p_ori.getY() - y;
                    //p2.setLocation(evt.getX()-dif_x,evt.getY()-dif_y);
                    //((rRectangle2D)selectedShape).setLocation(p2);
                //}
                //else if(selectedShape instanceof rEllipse2D)
                //    ((rEllipse2D)selectedShape).setLocation(evt.getPoint());
            }
        }else
            if(faltaPuntoControl){
                // suponemos que la curva es el ultimo del vector
                RFigura2D aux = vShape.get(vShape.size()-1);
                if(aux instanceof RCurve2DFinal)
                    ((RCurve2DFinal)vShape.get(vShape.size()-1)).setPuntoControl(evt.getPoint());
        }else{
            this.createShape(evt.getPoint());
        }
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        if (editable == true) {
            if (selectedShape != null) {
                System.out.println("NO ES NULO 2");
                //if (selectedShape instanceof rLine2D) {
                //    ((rLine2D) selectedShape).setLocation(evt.getPoint());
                //} else 
                //if (selectedShape instanceof rRectangle2D_DOS) {
                    //((rRectangle2D) selectedShape).setLocation(evt.getPoint());
                    //((rRectangle2D_DOS)selectedShape).setLocation(p2);
                p3.setLocation(evt.getX() - xp, evt.getY() - yp);
                selectedShape.setLocation(p3);
                
                //}// else if (selectedShape instanceof rEllipse2D) {
                //    ((rEllipse2D) selectedShape).setLocation(evt.getPoint());
                //}
                bounds = selectedShape.getBounds();
                this.setCursor(cursorMovimiento);
            }
        }else
            if(faltaPuntoControl){
                this.formMousePressed(evt);
            }
        else{
            this.updateShape(evt.getPoint());
        }
        this.repaint();
    }//GEN-LAST:event_formMouseDragged

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        this.formMouseDragged(evt);
        //if (faltaPuntoControl) {
        //    faltaPuntoControl = false;
        //}
        //cursor = new Cursor(CROSSHAIR_CURSOR);
        //this.setCursor(cursor);
        if(!vShape.isEmpty()){
            RFigura2D aux = vShape.get(vShape.size() - 1);
            if (aux instanceof RCurve2DFinal) {
                faltaPuntoControl = !faltaPuntoControl;
            }
        }
        //if(cursorAux!=null)
        //    this.setCursor(cursorAux);
        this.setCursor(null);
    }//GEN-LAST:event_formMouseReleased

    /**
     * Actualiza los atributos del Lienzo2D a través de la figura. Para ello
     * recibe una figura por parámetro de la que extrae sus atributos y 
     * actualiza con ellos los atributos del Lienzo2D para comunicarse con
     * la VentanaInterna y, por ende, con la ventana Principal.
     * @param f La figura de la que consultaré sus atributos.
     */
    
    private void actualizarAtributosLienzo2D(RFigura2D f){
        this.setColor(f.getColor());
        this.setColorRelleno(f.getColorRelleno());
        this.setColorRellenoDos(f.getColorRellenoDos());
        this.setEstaAlisado(f.getAlisada());
        this.setEstaRelleno(f.isRelleno());
        this.setGrosor(f.getGrosor());
        //this.setHerramienta(herramienta); NO ES NECESARIO
        this.setTipoRelleno(f.getTipoRelleno());
        this.setTipoTrazo(f.getTipoTrazo());
        this.setTransparencia(f.getValorTransparencia());
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
