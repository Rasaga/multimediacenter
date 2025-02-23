/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smpfinal;

import com.github.sarxos.webcam.Webcam;
import java.awt.image.BufferedImage;
import sm.rsg.iu.Lienzo2D;

/**
 *
 * @author Ramon
 */
public class VentanaInternaSM extends javax.swing.JInternalFrame {

    /**
     * VentanaInternaSM, de la que heredarán todas las demás VentanasInternas.
     * Dicha ventana tendrá algunos métodos que son utilizados pero dependiendo
     * de la ventana, tendrán una funcionalidad u otra, por ello aquí
     * son inicializados a null, y ya en sus clases hijas tendrán otra 
     * funcionalidad.
     */
    public VentanaInternaSM() {
        initComponents();
    }
    
    public VentanaInternaSM getSelectedFrame(){
        return this;
    }
    
    public Lienzo2D getLienzo2D(){
       return null;
    }
    
    // Metodo getImage a null, sobreescrito por VIImagen
    public BufferedImage getImage(){
        return null;
    }
    
    public BufferedImage getImage(boolean var){
        return null;
    }
    
    public Webcam getCamera(){
        return null;
    }
    
    public void setImage(BufferedImage img){};
    
    public VentanaInternaSM getFrame(){
        return null;
    }
    
    public boolean esImagen(){
        return false;
    }
    
    public BufferedImage getSnapshot(){
        return null;
    }
    
    public void actualizarVentanaPrincipal(){};

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lienzo2DImagen1 = new sm.rsg.iu.Lienzo2DImagen();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        javax.swing.GroupLayout lienzo2DImagen1Layout = new javax.swing.GroupLayout(lienzo2DImagen1);
        lienzo2DImagen1.setLayout(lienzo2DImagen1Layout);
        lienzo2DImagen1Layout.setHorizontalGroup(
            lienzo2DImagen1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 392, Short.MAX_VALUE)
        );
        lienzo2DImagen1Layout.setVerticalGroup(
            lienzo2DImagen1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 276, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(lienzo2DImagen1);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private sm.rsg.iu.Lienzo2DImagen lienzo2DImagen1;
    // End of variables declaration//GEN-END:variables
}
