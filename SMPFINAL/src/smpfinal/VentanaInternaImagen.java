/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smpfinal;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.image.BufferedImage;
import sm.rsg.iu.Lienzo2DImagen;

/**
 *
 * @author Ramon
 */
public class VentanaInternaImagen extends VentanaInternaSM {

    /**
     * VentanaInterna de imágenes. Sobre ella estará el Lienzo2D
     * donde dibujaremos todas las formas. Además, en dicha VentanaInterna
     * se consultarán y modificarán el estado de los componentes de los
     * atributos de las figuras de la VentanaPrincipal.
     */
    //Line2D l = new Line2D.Float(10,10,200,2000);
    private VentanaPrincipal vPertenece = new VentanaPrincipal();
    private Point coordenada = new Point();
    
    // Variable para cambiar el cursor a Cruz cuando esté sobre él
    private Cursor cursorCruz = new Cursor(Cursor.CROSSHAIR_CURSOR);
    
    public VentanaInternaImagen(VentanaPrincipal vPertenece) {
        super();
        initComponents();
        this.vPertenece=vPertenece;
    }
    
    /* Metodo que devuelva la clase Lienzo2D usado
    para utilizar sus metodos desde otras clases del paquete.
    */
    
    
    //@Override
    public Lienzo2DImagen getLienzo2D(){
        return lienzo2DImagen;
    }
    
    //@Override
    public BufferedImage getImage(){
        return lienzo2DImagen.getImage();
    }
    
    //@Override
    public BufferedImage getImage(boolean var){
        return lienzo2DImagen.getImage(var);
    }
    
    //@Override
    public void setImage(BufferedImage img){
        this.lienzo2DImagen.setImage(img);
    }
    
    //@Override
    public boolean esImagen(){
        return true;
    }
    
    public VentanaPrincipal getVPertenece(){
        return vPertenece;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelScroll = new javax.swing.JScrollPane();
        lienzo2DImagen = new sm.rsg.iu.Lienzo2DImagen();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameActivated(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        lienzo2DImagen.setBackground(new java.awt.Color(240, 240, 240));
        lienzo2DImagen.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                lienzo2DImagenMouseMoved(evt);
            }
        });
        lienzo2DImagen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lienzo2DImagenMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lienzo2DImagenMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lienzo2DImagenMousePressed(evt);
            }
        });

        javax.swing.GroupLayout lienzo2DImagenLayout = new javax.swing.GroupLayout(lienzo2DImagen);
        lienzo2DImagen.setLayout(lienzo2DImagenLayout);
        lienzo2DImagenLayout.setHorizontalGroup(
            lienzo2DImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 382, Short.MAX_VALUE)
        );
        lienzo2DImagenLayout.setVerticalGroup(
            lienzo2DImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 269, Short.MAX_VALUE)
        );

        panelScroll.setViewportView(lienzo2DImagen);

        getContentPane().add(panelScroll, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Existe un evento, InternalFrameActivateed, que cuando este activo,
    // permitirá hacer lo que le indiquemos.
    // Lo voy a aprovechar para que cada vez que cambie de ventana
    // se actualicen las opciones de la VentanaPrincipal
    // a las que tiene dicha VentanaInterna
    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        //System.out.print("\nHerramienta seleccionada: " + this.getLienzo2D().getHerramienta());
        this.actualizarVentanaPrincipal();
    }//GEN-LAST:event_formInternalFrameActivated

    private void lienzo2DImagenMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lienzo2DImagenMouseEntered
        this.lienzo2DImagenMouseMoved(evt);
        this.setCursor(cursorCruz);
    }//GEN-LAST:event_lienzo2DImagenMouseEntered

    private void lienzo2DImagenMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lienzo2DImagenMouseExited
        this.vPertenece.setTextoCoordenada("");
        this.setCursor(null);
    }//GEN-LAST:event_lienzo2DImagenMouseExited

    private void lienzo2DImagenMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lienzo2DImagenMouseMoved
        coordenada = evt.getPoint();
        this.vPertenece.setTextoCoordenada("("+(int)coordenada.getX()+", "
                +(int)coordenada.getY()+")");
    }//GEN-LAST:event_lienzo2DImagenMouseMoved

    private void lienzo2DImagenMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lienzo2DImagenMousePressed
        // Esto lo añado porque tambien necesito actualizar cuando se haya seleccionado una figura
        // aprovecho el metodo pressed
        this.actualizarVentanaPrincipal();
    }//GEN-LAST:event_lienzo2DImagenMousePressed

    /* Metodo que actualiza la ventana principal.
    Es un engorro importante, pero dada la implementación, necesito conocer:
        - Qué atributos tiene cada ventana Interna de Imagen, para cuando
        cambie de una a otra, se seleccionen.
        - Qué atributos tiene cada RFigura2D, dado que cuando se seleccione,
        se modifiquen los botones dependiendo de los atributos del RFigura2D
        seleccionado.
    */
    
    @Override
    public void actualizarVentanaPrincipal() {
        //¿Como actualizar VentanaPrincipal desde VentanaInterna?
        //Idea: Cada ventanaInterna tendra un atributo de la ventanaPrincipal
        //A la que pertenece. Para ello, cada vez que se construya una VI, le indicamos
        // la ventanaPrincipal a la que pertenece. Hay que modificar el constructor
        int mi_herramienta = this.getLienzo2D().getHerramienta();
        Color mi_color = this.getLienzo2D().getColor();
        Color mi_color_relleno = this.getLienzo2D().getColorRelleno();
        Color mi_color_relleno_dos = this.getLienzo2D().getColorRellenoDos();
        int mi_grosor = this.getLienzo2D().getGrosor();
        int miTipoTrazo = this.getLienzo2D().getTipoTrazo();
        
        int tipoRelleno = this.getLienzo2D().getTipoRelleno();

        boolean mi_relleno = this.getLienzo2D().getEstaRelleno();
        boolean mi_alisado = this.getLienzo2D().getEstaAlisado();
        boolean mi_transparencia = this.getLienzo2D().getEstaTransparente();
        boolean mi_edicion = this.getLienzo2D().getEstaEditable();

        if (mi_herramienta == 1) {
            vPertenece.getBotonLinea().setSelected(true);
            vPertenece.getInfoHerramienta().setText("Línea");
        } else if (mi_herramienta == 2) {
            vPertenece.getBotonRectangulo().setSelected(true);
            vPertenece.getInfoHerramienta().setText("Rectángulo");
        } else if (mi_herramienta == 3) {
            vPertenece.getBotonOvalo().setSelected(true);
            vPertenece.getInfoHerramienta().setText("Óvalo");
        }

        int index = 0;
        if (mi_color == Color.black) {
            index = 0;
        } else if (mi_color == Color.red) {
            index = 1;
        } else if (mi_color == Color.blue) {
            index = 2;
        } else if (mi_color == Color.white) {
            index = 3;
        } else if (mi_color == Color.yellow) {
            index = 4;
        } else if (mi_color == Color.green) {
            index = 5;
        } else
            index = -1;
        
       //if(index==-1){
       //    vPertenece
       //}
       if (index != -1) {
            vPertenece.getComboBoxColores().setSelectedIndex(index);
            vPertenece.getBotonOtrosColores().setEnabled(false);
            vPertenece.getComboBoxColores().setEnabled(true);
            vPertenece.getBotonElegirMas().setSelected(false);
        } else {
            vPertenece.getComboBoxColores().setEnabled(false);
            vPertenece.getBotonOtrosColores().setEnabled(true);
            vPertenece.getBotonElegirMas().setSelected(true);
        }
       vPertenece.getBotonOtrosColores().setBackground(mi_color);
        //vPertenece.getComboboxColoresRelleno().setSelectedIndex(indexR);

        if (mi_relleno) {
            if (tipoRelleno == 0) {
                vPertenece.getColoresRellenoUno().setEnabled(true);
                vPertenece.getColoresRellenoDos().setEnabled(false);
                vPertenece.getBotonRellenoNormal().setSelected(true);
            } else {
                vPertenece.getColoresRellenoUno().setEnabled(false);
                vPertenece.getColoresRellenoDos().setEnabled(true);
                if(tipoRelleno == 1){
                    vPertenece.getBotonRellenoHorizontal().setSelected(true);
                }else if(tipoRelleno ==2){
                    vPertenece.getBotonRellenoVertical().setSelected(true);
                }
            }
        }

        vPertenece.getColoresRellenoUno().setBackground(mi_color_relleno);
        vPertenece.getColoresRellenoDos().setBackground(mi_color_relleno_dos);

        // Lo referene a las otras 4 opciones
        vPertenece.getBotonRelleno().setSelected(mi_relleno);
        vPertenece.getColoresRellenoUno().setEnabled(mi_relleno);
        if(!mi_relleno)
            vPertenece.getColoresRellenoDos().setEnabled(mi_relleno);
        
        vPertenece.getBotonRellenoNormal().setEnabled(mi_relleno);
        vPertenece.getBotonRellenoHorizontal().setEnabled(mi_relleno);
        vPertenece.getBotonRellenoVertical().setEnabled(mi_relleno);
        //vPertenece.getBotonTransparencia().setSelected(mi_transparencia);
        vPertenece.getBotonAlisado().setSelected(mi_alisado);
        vPertenece.getBotonEditable().setSelected(mi_edicion);
        
        // Trazo
        // Lo referente al grosor
        vPertenece.getSpinnerGrosor().setValue(mi_grosor);
        if(miTipoTrazo == 0)
            vPertenece.getBotonContinuo().setSelected(true);
        else if (miTipoTrazo == 1)
            vPertenece.getBotonDiscontinuo().setSelected(true);
        
        int valorTransparencia = this.getLienzo2D().getTransparencia();
        vPertenece.getSliderTransparencia().setValue(valorTransparencia);
     
        int a = this.getLienzo2D().comprobarShape();
        vPertenece.getBotonBajadaTope().setEnabled(this.getLienzo2D().puedeBajar());
        //System.out.println(this.getLienzo2D().puedeBajar());
        vPertenece.getBotonBajada().setEnabled(this.getLienzo2D().puedeBajar());
        vPertenece.getBotonSubida().setEnabled(this.getLienzo2D().puedeSubir());
        vPertenece.getBotonSubidaTope().setEnabled(this.getLienzo2D().puedeSubir());

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private sm.rsg.iu.Lienzo2DImagen lienzo2DImagen;
    private javax.swing.JScrollPane panelScroll;
    // End of variables declaration//GEN-END:variables
}
