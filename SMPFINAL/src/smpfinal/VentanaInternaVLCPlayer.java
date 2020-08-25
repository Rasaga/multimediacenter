/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smpfinal;

import java.awt.image.BufferedImage;
import java.io.File;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 *
 * @author Ramon
 */
public class VentanaInternaVLCPlayer extends VentanaInternaSM {
    
    private EmbeddedMediaPlayer vlcplayer = null;
    // Ha de creare para almacenar file, debido a como trabaja File
    private File fMedia;

    /**
     * Ventana de Video del tipo VLCPlayer. Es la usada por el proyecto para
     * reproducir video (y algún tipo de audio).
     */
    private VentanaInternaVLCPlayer(File f) {
        initComponents();
        fMedia = f;
        EmbeddedMediaPlayerComponent aVisual = new EmbeddedMediaPlayerComponent();
        getContentPane().add(aVisual, java.awt.BorderLayout.CENTER);
        vlcplayer = aVisual.getMediaPlayer();
        // Asignamos al manejador el generador, que tambien creamos
        vlcplayer.addMediaPlayerEventListener(new VideoListener());
    }
    
    // Al igual que los anteriores, necesito getInstance()
    public static VentanaInternaVLCPlayer getInstance(File f) {
        VentanaInternaVLCPlayer v = new VentanaInternaVLCPlayer(f);
        return (v.vlcplayer != null ? v : null);
    }
    
    /* Metodo para obtener el Player
    
    */
    public EmbeddedMediaPlayer getPlayer(){
        return this.vlcplayer;
    }
    
    /*
    Sobreescribo el metodo getSnapshot para realizar la captura
    de pantalla del video
    */
    @Override
    public BufferedImage getSnapshot(){
        return this.getPlayer().getSnapshot();
    }
    
    /* Metodo para iniciar la reproducción
    */
    public void play() {
        if (vlcplayer != null) {
            if (vlcplayer.isPlayable()) {
                //Si se estaba reproduciendo
                vlcplayer.play();
            } else {
                vlcplayer.playMedia(fMedia.getAbsolutePath());
            }
        }
    }

    /* Metodo para parar la reproducción
    
    */
    public void stop() {
        if (vlcplayer != null) {
            if (vlcplayer.isPlaying()) {
                vlcplayer.pause();
            } else {
                vlcplayer.stop();
            }
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

        panelInferiorVLC = new javax.swing.JPanel();
        botonPlayVLC = new javax.swing.JButton();
        botonStopVLC = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setMinimumSize(new java.awt.Dimension(500, 400));
        setPreferredSize(new java.awt.Dimension(500, 400));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
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

        panelInferiorVLC.setLayout(new java.awt.GridLayout(1, 0));

        botonPlayVLC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/play24x24.png"))); // NOI18N
        botonPlayVLC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPlayVLCActionPerformed(evt);
            }
        });
        panelInferiorVLC.add(botonPlayVLC);

        botonStopVLC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/stop24x24.png"))); // NOI18N
        botonStopVLC.setEnabled(false);
        botonStopVLC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonStopVLCActionPerformed(evt);
            }
        });
        panelInferiorVLC.add(botonStopVLC);

        getContentPane().add(panelInferiorVLC, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        this.stop();
        this.vlcplayer=null;
    }//GEN-LAST:event_formInternalFrameClosing

    private void botonPlayVLCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPlayVLCActionPerformed
        this.play();
    }//GEN-LAST:event_botonPlayVLCActionPerformed

    private void botonStopVLCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonStopVLCActionPerformed
        this.stop();
    }//GEN-LAST:event_botonStopVLCActionPerformed

    // Clase interna, para el control de eventos y manejadores
    private class VideoListener extends MediaPlayerEventAdapter {
        @Override
        public void playing(MediaPlayer mediaPlayer){
            botonPlayVLC.setEnabled(false);
            botonStopVLC.setEnabled(true);
        }
        @Override
        // Podria usar el metodo stope, pero realmente lo que hace el boton
        // es poner en pausa la reproduccion
        public void paused(MediaPlayer mediaPlayer){
            botonPlayVLC.setEnabled(true);
            botonStopVLC.setEnabled(false);
        }
        @Override
        public void finished(MediaPlayer mediaPlayer){
            botonPlayVLC.setEnabled(true);
            botonStopVLC.setEnabled(false);
        }
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonPlayVLC;
    private javax.swing.JButton botonStopVLC;
    private javax.swing.JPanel panelInferiorVLC;
    // End of variables declaration//GEN-END:variables
}
