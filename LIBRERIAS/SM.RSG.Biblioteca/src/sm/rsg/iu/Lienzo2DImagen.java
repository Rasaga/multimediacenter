/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.rsg.iu;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Ramon
 */
public class Lienzo2DImagen extends Lienzo2D {
    
    private BufferedImage img;

    /**
     * Crea un nuevo Lienzo2D pero del tipo Imagen. Usaremos este lienzo
     * para cuando abramos imágenes y queramos realizar cosas sobre ellas.
     */
    public Lienzo2DImagen() {
        initComponents();
    }
    
    /**
     * Devuelve la imagen asignada al Lienzo2D.
     * @return Variable BufferedImage con la imagen de este Lienzo2D();
     */
    
    public BufferedImage getImage(){
        return this.img;
    }
    
    // Con parametros, para volcar las shapes en la imagen
    // En java, no existe ningun metodo para volcar imagen, se tendria que hacer
    // que se volcase en g2d con aux.getGraphics() y luego
    // g2d.drawImage().
    
    /**
     * Metodo get Imagen pero con parámetro. Devuelve la imagen después de hacerle
     * un volcado de los shapes que hemos usado sobre ella.
     * @param drawVector Variable booleana indicando si quiero pintar o no el vector de shapes.
     * @return Devuelve una BufferedImage con la imagen y sus shapes.
     */
    
    public BufferedImage getImage(boolean drawVector){
        if(drawVector){
            // Crear un bufferedImage nuevo con los mismos parametros
            BufferedImage aux = null;
            //if(img.isAlphaPremultiplied())
            int tipo = img.getType();
            if(tipo!=0)
                aux = new BufferedImage(img.getWidth(),img.getHeight(),img.getType());
            else
                aux = new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_ARGB);
            // Aqui habrá que poner algo mas en un futuro
            
            // Sobre este, volcar mi imagen y las shapes, aprovechando del metodo paint de Lienzo2D
            // Truco: en lugar de llamar a todos los set, draw y demás.... puedo llamar a paint que los tiene ya todos!
            
            // EDIT 10/05/2018: La imagen se guarda con el borde, quiero imprimir la imagen sin él
            //Shape contorno_aux = this.getContorno();
            //Shape contorno_vacio = null;
            //this.setContorno(contorno_vacio);
            this.setPintarBordes(false);
            // Vuelvo la imagen
            this.paint(aux.createGraphics());
            // vuelvo a poner los bordes a true
            this.setPintarBordes(true);
            // vuelvo a poner mi contorno
            //this.setContorno(contorno_aux);
            // devolver esa nueva Bufferedimag
            return aux;
        }else
            return getImage();
    }
    
    /**
     * Metodo setImage. Este método se encarga de asignar una imagen al Lienzo2D().
     * Para ello crea el Lienzo2D y el contorno con las metidas de la nueva imagen.
     * @param img La imagen que añadirá al lienzo2D
     */
    
    public void setImage(BufferedImage img) {
        this.img = img;
        if (img != null) {
            setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
            // Cuando haga un set de la imagen, se modificara el contorno por el de la imagen abierta
            //Graphics2D graficos = img.createGraphics();
            //Rectangle bounds2 = graficos.getDeviceConfiguration().getBounds();
            //Rectangle bounds = getContorno().getBounds();
            //int x = img.getWidth() - bounds.width;
            //int y = img.getHeight() - bounds.height;
            //System.out.println(bounds2.x+ " " +bounds2.y);
            //this.modificarContorno(bounds2.x, bounds2.y, img.getWidth(),img.getHeight());
            this.modificarContorno(img.getWidth(),img.getHeight());
        } 
    }

    /**
     * Método sobrecargado paintComponent. Utilizamos este método para 
     * pintar los componentes de la imagen.Si en lugar hubiesemos sobrecargado 
     * el metodo paint, el problema
     * es que en primer lugar cargaria el metodo paint y luego seria
     * machacado por el resto de cosa.
     * Tampoco podria haberse hecho alreves, primero el drawImage y luego
     * el paint, ya que al usar paint, borra todo lo que había. La solución
     * habría sido hacerlo alreves. Hacer que Imagen2D heredase de Imagen2DImagen
     * Nosotros hemos usado un "truco" para arreglarlo:
     *   - El paint hace llamad a 3 metodos: paintComponent, paintBorder y paintChildren
     *   - Al llamar en lugar de paint a paintComponent, al llamar a PaintComponent
     *   se podra pintar la imagen antes del vector y ninguno machaca al otro.
     * @param g Objeto Graphics para realizar el paintComponent
     */
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(img!=null){
            g.drawImage(img,0,0,this);
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
