/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smpfinal;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamMotionEvent;
import com.github.sarxos.webcam.WebcamMotionListener;
import java.awt.Color;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.ConvolveOp;
import java.awt.image.DataBuffer;
import java.awt.image.Kernel;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.awt.image.RescaleOp;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import sm.image.EqualizationOp;
import sm.image.KernelProducer;
import sm.image.LookupTableProducer;
import sm.image.TintOp;
import sm.rsg.imagen.RFuncionPropiaFinal;
import sm.rsg.imagen.RFuncionesPropias;
import sm.rsg.imagen.RFiltroFrioOp;
import sm.rsg.imagen.RFiltroEfectoTunelOp;
import sm.rsg.imagen.RFiltroCalidoOp;
import sm.rsg.imagen.RSepiaOp;
import sm.sound.SMClipPlayer;
import sm.sound.SMPlayer;
import sm.sound.SMRecorder;
import sm.sound.SMSoundRecorder;

/**
 *
 * @author Ramon
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    // Variable que indica el numero de ventanas creadas
    private static int num_vi = 1;
    private static int cord = 0;
    
    private final static int CANTIDAD = 25;
    //private VentanaInternaImagen vi; // se inicializara cuando se cree a traves del "NuevaVentana"
    private VentanaInternaSM vi; // ahora sera del tipo SM
    
    // Mi variable imgOriginal, que me será util cuando aplique las operaciones de filtro del slider
    private BufferedImage imgOriginal = null;
    
    // Practica 13: Sonido
    private SMPlayer player = null;
    private SMRecorder recorder = null;
    
    private File tmp=null;
    
    // Practica 14: Video YA NO SIRVEN PORQUE LA VENTANA ES DE TIPO SM
    //private VentanaInternaJMFPlayer video = null;
    //private VentanaInternaCamara webcam = null;
    //private VentanaInternaVLCPlayer video = null;
    
    // contador
    private boolean empezar = false;
    private int cantidad = 0;
    private int cantidadSegundos = 0;
    private int cantidadMinutos = 0;
    private Thread t = null;
    
    
    // Detector de movimiento
    private WebcamMotionDetector detector;
    boolean detectorEncendido = false;
    private int decX = 600;
    private int decY = 10;
    

    /**
     * Creates new form VentanaPrincipal
     */
    public VentanaPrincipal() {
        initComponents();
        this.setSize(900, 600);
        // Texto inicial por defecto
        this.infoHerramienta.setText("Punto");
        
        //VentanaInterna vi = new VentanaInterna();
        //escritorio.add(vi);
        //vi.setVisible(true);
    }
    
    /////////////////////////////////////////////////////////////////////////////////
    //////////////////// INICIO DE LOS GET DE LOS ELEMENTOS SWING //////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    
    /*
     * METODOS GET USADOS EN ESTA PRÁCTICA. Dada mi implementación, me permiten
     * poder ser consultados por VentanaInterna, donde los activare, desactivare,
     * habilitaré, etc, dependiendo de los atributos de cada figura.
     */

    
    public javax.swing.JToggleButton getBotonLinea(){
        return this.botonLinea;
    }
    
    public javax.swing.JToggleButton getBotonRectangulo() {
        return this.botonRectangulo;
    }
    public javax.swing.JToggleButton getBotonOvalo() {
        return this.botonOvalo;
    }
    
    public javax.swing.JComboBox getComboBoxColores(){
        return this.comboBoxColores;
    }

    public javax.swing.JSpinner getSpinnerGrosor(){
        return this.cambioGrosor;
    }
    
    public javax.swing.JToggleButton getBotonContinuo(){
        return this.botonLineaContinua;
    }
    public javax.swing.JToggleButton getBotonDiscontinuo(){
        return this.botonLineaDiscontinua;
    }
    
    // Referente a las 4 opciones
    public JToggleButton getBotonRelleno() {
        return this.botonRelleno;
    }
    
    public JToggleButton getBotonAlisado() {
        return this.botonAlisado;
    }
    public JToggleButton getBotonEditable() {
        return this.botonEditar;
    }
    
    public javax.swing.JSlider getSliderTransparencia() {
        return this.sliderTransparencia;
    }
    
    public javax.swing.JToggleButton getBotonElegirMas(){
        return this.botonElegirMas;
    }
    
    public javax.swing.JButton getBotonOtrosColores(){
        return this.botonOtrosColores;
    }
    
    public javax.swing.JButton getColoresRellenoUno(){
        return this.botonColoresRellenoUno;
    }
    
    public javax.swing.JButton getColoresRellenoDos(){
        return this.botonColoresRellenoDos;
    }
    
    public javax.swing.JToggleButton getBotonRellenoNormal(){
        return this.botonRellenoNormal;
    }

    public javax.swing.JToggleButton getBotonRellenoHorizontal() {
        return this.botonRellenoHorizontal;
    }

    public javax.swing.JToggleButton getBotonRellenoVertical() {
        return this.botonRellenoVertical;
    }
    
    public javax.swing.JButton getBotonBajadaTope() {
        return this.botonBajarFondo;
    }

    public javax.swing.JButton getBotonBajada() {
        return this.botonBajar;
    }

    public javax.swing.JButton getBotonSubida() {
        return this.botonSubir;
    }

    public javax.swing.JButton getBotonSubidaTope() {
        return this.botonSubirTope;
    }
    
    public javax.swing.JLabel getInfoHerramienta(){
        return this.infoHerramienta;
    }
    
    /////////////////////////////////////////////////////////////////////////////////
    //////////////////// FIN DE LOS GET DE LOS ELEMENTOS SWING //////////////////////
    /////////////////////////////////////////////////////////////////////////////////
    
    
    // Para el apartado de coordenadas, gestionando el evento
    // mouse entered y mouse existed
    public void setTextoCoordenada(String texto){
        this.infoCoordenada.setText(texto);
    }
    
    /* Me permite obtener el número de ventanas para imprimirlas en el título 
    
    */
    public static int getNumeroVentanas(){
        return num_vi;
    }
    
    /* Cada vez que cree una nueva ventana, llamaré a este método para aumentar
    su número.
    */
    
    public static void aumentarNumeroVentanas(){
        num_vi++;
    }
    
    /* Para obtener las cordenadas donde se cree cada ventana. X e Y
    tendrán el mismo valor.
    */
    
    public static int getCoordenadaVentana(){
        return cord;
    }
    
    /* Aumentar las cordenadas de las ventanas, cada vez que se cree una */
    
    public static void aumentarCoordenadaVentana(){
        cord=cord+CANTIDAD;
    }
    
    
    /* Para las ventanas de deteccion de movimiento, las crearé en la parte
    derecha, para que no se tapen con la ventana de la webcam. Para ello
    he creado un metodo diferente.
    */
    public void aumentarCoordenadaDectec(){
        decX=decX+CANTIDAD;
        decY=decY+CANTIDAD;
    }
    
    /* Para poder consultar o asignar mi ventnaa principal a una ventana Interna
    a veces necesito de un metodo get
    */
    
    public VentanaPrincipal getVentanaPrincipal(){
        return this;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoHerramientas = new javax.swing.ButtonGroup();
        grupoColores = new javax.swing.ButtonGroup();
        grupoTrazo = new javax.swing.ButtonGroup();
        grupoDegradado = new javax.swing.ButtonGroup();
        panelSuperior = new javax.swing.JPanel();
        toolbarOpciones = new javax.swing.JToolBar();
        botonNuevo = new javax.swing.JButton();
        botonAbrir = new javax.swing.JButton();
        botonGuardar = new javax.swing.JButton();
        toolbarHerramientas = new javax.swing.JToolBar();
        separadorH = new javax.swing.JToolBar.Separator();
        botonLinea = new javax.swing.JToggleButton();
        botonRectangulo = new javax.swing.JToggleButton();
        botonOvalo = new javax.swing.JToggleButton();
        separadorP2 = new javax.swing.JToolBar.Separator();
        botonCurva = new javax.swing.JToggleButton();
        botonTrazo = new javax.swing.JToggleButton();
        botonPersonalizada = new javax.swing.JToggleButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        botonEditar = new javax.swing.JToggleButton();
        toolbarPosicion = new javax.swing.JToolBar();
        separadorPos = new javax.swing.JToolBar.Separator();
        botonBajarFondo = new javax.swing.JButton();
        botonBajar = new javax.swing.JButton();
        botonSubir = new javax.swing.JButton();
        botonSubirTope = new javax.swing.JButton();
        toolbarPropiedades = new javax.swing.JToolBar();
        separadorP1 = new javax.swing.JToolBar.Separator();
        // Necesitare crear un array de colores
        Color misColores[] = {
            Color.black,
            Color.red,
            Color.blue,
            Color.white,
            Color.yellow,
            Color.green
        };
        comboBoxColores = new javax.swing.JComboBox();
        botonElegirMas = new javax.swing.JToggleButton();
        panelOtrosColores = new javax.swing.JPanel();
        botonOtrosColores = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        cambioGrosor = new javax.swing.JSpinner();
        botonLineaContinua = new javax.swing.JToggleButton();
        botonLineaDiscontinua = new javax.swing.JToggleButton();
        separadorP3 = new javax.swing.JToolBar.Separator();
        botonRelleno = new javax.swing.JToggleButton();
        panelColoresRelleno = new javax.swing.JPanel();
        botonColoresRellenoUno = new javax.swing.JButton();
        botonColoresRellenoDos = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        botonRellenoNormal = new javax.swing.JToggleButton();
        botonRellenoHorizontal = new javax.swing.JToggleButton();
        botonRellenoVertical = new javax.swing.JToggleButton();
        separadorP4 = new javax.swing.JToolBar.Separator();
        sliderTransparencia = new javax.swing.JSlider();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        botonAlisado = new javax.swing.JToggleButton();
        toolbarSonido = new javax.swing.JToolBar();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        botonPlay = new javax.swing.JButton();
        botonStop = new javax.swing.JButton();
        listaReproduccion = new javax.swing.JComboBox<File>();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        botonRecord = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JToolBar.Separator();
        labelDuracion = new javax.swing.JLabel();
        toolbarVideo = new javax.swing.JToolBar();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        botonWebCam = new javax.swing.JButton();
        botonCapturaImagen = new javax.swing.JButton();
        botonDetection = new javax.swing.JToggleButton();
        escritorio = new javax.swing.JDesktopPane();
        panelInferior = new javax.swing.JPanel();
        panelHerramienta = new javax.swing.JPanel();
        infoHerramienta = new javax.swing.JLabel();
        infoCoordenada = new javax.swing.JLabel();
        panelImagen = new javax.swing.JPanel();
        toolbarImagen = new javax.swing.JToolBar();
        panelDuplicado = new javax.swing.JPanel();
        botonDuplicarImagen = new javax.swing.JButton();
        panelBrillo = new javax.swing.JPanel();
        sliderBrillo = new javax.swing.JSlider();
        panelFiltro = new javax.swing.JPanel();
        // Creamos un array con los filtros
        int filtros[] = {
            KernelProducer.TYPE_MEDIA_3x3,
            KernelProducer.TYPE_BINOMIAL_3x3,
            KernelProducer.TYPE_ENFOQUE_3x3,
            KernelProducer.TYPE_RELIEVE_3x3,
            KernelProducer.TYPE_LAPLACIANA_3x3
        };
        comboBoxFiltros = new javax.swing.JComboBox<>();
        panelContraste = new javax.swing.JPanel();
        botonContraste = new javax.swing.JButton();
        botonIluminar = new javax.swing.JButton();
        botonOscurecer = new javax.swing.JButton();
        panelSeno = new javax.swing.JPanel();
        botonSinuidal = new javax.swing.JButton();
        botonFuncionSenCos = new javax.swing.JButton();
        botonSepia = new javax.swing.JButton();
        botonTintado = new javax.swing.JButton();
        botonEcualizador = new javax.swing.JButton();
        botonEfectoTunel = new javax.swing.JButton();
        botonEfectoCalor = new javax.swing.JButton();
        botonEfectoFrio = new javax.swing.JButton();
        botonInvertir = new javax.swing.JButton();
        panelColoresOpciones = new javax.swing.JPanel();
        botonExtraerBandas = new javax.swing.JButton();
        comboBoxEspaciosColores = new javax.swing.JComboBox<>();
        panelGiro = new javax.swing.JPanel();
        sliderGiro = new javax.swing.JSlider();
        botonGiro90 = new javax.swing.JButton();
        botonGiro180 = new javax.swing.JButton();
        botonGiro270 = new javax.swing.JButton();
        panelZoom = new javax.swing.JPanel();
        botonEscalaAumento = new javax.swing.JButton();
        botonEscalaDescenso = new javax.swing.JButton();
        barraMenu = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        menuNuevo = new javax.swing.JMenuItem();
        menuAbrir = new javax.swing.JMenuItem();
        menuGuardar = new javax.swing.JMenuItem();
        menuAbrirAudio = new javax.swing.JMenuItem();
        menuAbrirVideo = new javax.swing.JMenuItem();
        menuVer = new javax.swing.JMenu();
        checkVerEstado = new javax.swing.JCheckBoxMenuItem();
        checkVerFormas = new javax.swing.JCheckBoxMenuItem();
        checkVerAtributos = new javax.swing.JCheckBoxMenuItem();
        menuImagen = new javax.swing.JMenu();
        menuCambiarTamanio = new javax.swing.JMenuItem();
        botonOtros = new javax.swing.JMenu();
        menuFiltrosClase = new javax.swing.JMenu();
        menuRescaleOp = new javax.swing.JMenuItem();
        menuConvolveOp = new javax.swing.JMenuItem();
        menuAffineTransformOp = new javax.swing.JMenuItem();
        menuLookupOp = new javax.swing.JMenuItem();
        menuBandCombineOp = new javax.swing.JMenuItem();
        menuColorConvertOp = new javax.swing.JMenuItem();
        separadorMenuOtros = new javax.swing.JPopupMenu.Separator();
        menuSobre = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PaintCompleto - SMPFINAL - Ramón Sánchez García - 17/18");
        setMinimumSize(new java.awt.Dimension(1400, 700));
        setPreferredSize(new java.awt.Dimension(1400, 304));

        panelSuperior.setMinimumSize(new java.awt.Dimension(333, 35));
        panelSuperior.setLayout(new javax.swing.BoxLayout(panelSuperior, javax.swing.BoxLayout.LINE_AXIS));

        toolbarOpciones.setFloatable(false);
        toolbarOpciones.setRollover(true);
        toolbarOpciones.setMaximumSize(new java.awt.Dimension(90, 35));
        toolbarOpciones.setMinimumSize(new java.awt.Dimension(90, 35));
        toolbarOpciones.setPreferredSize(new java.awt.Dimension(90, 35));

        botonNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        botonNuevo.setToolTipText("Nueva Imagen");
        botonNuevo.setFocusable(false);
        botonNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonNuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonNuevoActionPerformed(evt);
            }
        });
        toolbarOpciones.add(botonNuevo);

        botonAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/abrir.png"))); // NOI18N
        botonAbrir.setToolTipText("Abrir Imagen");
        botonAbrir.setFocusable(false);
        botonAbrir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonAbrir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAbrirActionPerformed(evt);
            }
        });
        toolbarOpciones.add(botonAbrir);

        botonGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardar.png"))); // NOI18N
        botonGuardar.setToolTipText("Guardar Imagen");
        botonGuardar.setFocusable(false);
        botonGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGuardarActionPerformed(evt);
            }
        });
        toolbarOpciones.add(botonGuardar);

        panelSuperior.add(toolbarOpciones);

        toolbarHerramientas.setFloatable(false);
        toolbarHerramientas.setRollover(true);
        toolbarHerramientas.setMaximumSize(new java.awt.Dimension(252, 35));
        toolbarHerramientas.setMinimumSize(new java.awt.Dimension(252, 35));
        toolbarHerramientas.setName(""); // NOI18N
        toolbarHerramientas.setPreferredSize(new java.awt.Dimension(252, 35));
        toolbarHerramientas.add(separadorH);

        grupoHerramientas.add(botonLinea);
        botonLinea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/linea.png"))); // NOI18N
        botonLinea.setSelected(true);
        botonLinea.setToolTipText("Dibuja una Linea");
        botonLinea.setFocusable(false);
        botonLinea.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonLinea.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonLinea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonLineaActionPerformed(evt);
            }
        });
        toolbarHerramientas.add(botonLinea);

        grupoHerramientas.add(botonRectangulo);
        botonRectangulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/rectangulo.png"))); // NOI18N
        botonRectangulo.setToolTipText("Dibuja un Rectangulo");
        botonRectangulo.setFocusable(false);
        botonRectangulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonRectangulo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonRectangulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRectanguloActionPerformed(evt);
            }
        });
        toolbarHerramientas.add(botonRectangulo);

        grupoHerramientas.add(botonOvalo);
        botonOvalo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/elipse.png"))); // NOI18N
        botonOvalo.setToolTipText("Dibuja un Ovalo");
        botonOvalo.setFocusable(false);
        botonOvalo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonOvalo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonOvalo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonOvaloActionPerformed(evt);
            }
        });
        toolbarHerramientas.add(botonOvalo);
        toolbarHerramientas.add(separadorP2);

        grupoHerramientas.add(botonCurva);
        botonCurva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/vector-radius.png"))); // NOI18N
        botonCurva.setToolTipText("Dibuja una Curva");
        botonCurva.setFocusable(false);
        botonCurva.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonCurva.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonCurva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCurvaActionPerformed(evt);
            }
        });
        toolbarHerramientas.add(botonCurva);

        grupoHerramientas.add(botonTrazo);
        botonTrazo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/pencil (1).png"))); // NOI18N
        botonTrazo.setToolTipText("Dibuja un trazo libre");
        botonTrazo.setFocusable(false);
        botonTrazo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonTrazo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonTrazo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonTrazoActionPerformed(evt);
            }
        });
        toolbarHerramientas.add(botonTrazo);

        grupoHerramientas.add(botonPersonalizada);
        botonPersonalizada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/leaf.png"))); // NOI18N
        botonPersonalizada.setToolTipText("Herramienta \"Hoja\"");
        botonPersonalizada.setFocusable(false);
        botonPersonalizada.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonPersonalizada.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonPersonalizada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPersonalizadaActionPerformed(evt);
            }
        });
        toolbarHerramientas.add(botonPersonalizada);
        toolbarHerramientas.add(jSeparator4);

        grupoHerramientas.add(botonEditar);
        botonEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/seleccion.png"))); // NOI18N
        botonEditar.setToolTipText("Herramienta de seleccion");
        botonEditar.setFocusable(false);
        botonEditar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonEditar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEditarActionPerformed(evt);
            }
        });
        toolbarHerramientas.add(botonEditar);

        panelSuperior.add(toolbarHerramientas);

        toolbarPosicion.setFloatable(false);
        toolbarPosicion.setMaximumSize(new java.awt.Dimension(100, 32769));
        toolbarPosicion.setMinimumSize(new java.awt.Dimension(100, 33));
        toolbarPosicion.add(separadorPos);

        botonBajarFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/chevron-double-down.png"))); // NOI18N
        botonBajarFondo.setToolTipText("Mover figura al fondo");
        botonBajarFondo.setEnabled(false);
        botonBajarFondo.setFocusable(false);
        botonBajarFondo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonBajarFondo.setMaximumSize(new java.awt.Dimension(25, 31));
        botonBajarFondo.setMinimumSize(new java.awt.Dimension(25, 31));
        botonBajarFondo.setPreferredSize(new java.awt.Dimension(25, 31));
        botonBajarFondo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonBajarFondo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBajarFondoActionPerformed(evt);
            }
        });
        toolbarPosicion.add(botonBajarFondo);

        botonBajar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/chevron-down.png"))); // NOI18N
        botonBajar.setToolTipText("Mover figura hacia atrás");
        botonBajar.setEnabled(false);
        botonBajar.setFocusable(false);
        botonBajar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonBajar.setMaximumSize(new java.awt.Dimension(25, 31));
        botonBajar.setMinimumSize(new java.awt.Dimension(25, 31));
        botonBajar.setPreferredSize(new java.awt.Dimension(25, 31));
        botonBajar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonBajar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBajarActionPerformed(evt);
            }
        });
        toolbarPosicion.add(botonBajar);

        botonSubir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/chevron-up.png"))); // NOI18N
        botonSubir.setToolTipText("Mover figura hacia delante");
        botonSubir.setEnabled(false);
        botonSubir.setFocusable(false);
        botonSubir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonSubir.setMaximumSize(new java.awt.Dimension(25, 31));
        botonSubir.setMinimumSize(new java.awt.Dimension(25, 31));
        botonSubir.setPreferredSize(new java.awt.Dimension(25, 31));
        botonSubir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonSubir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSubirActionPerformed(evt);
            }
        });
        toolbarPosicion.add(botonSubir);

        botonSubirTope.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/chevron-double-up.png"))); // NOI18N
        botonSubirTope.setToolTipText("Mover figura al frente");
        botonSubirTope.setEnabled(false);
        botonSubirTope.setFocusable(false);
        botonSubirTope.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonSubirTope.setMaximumSize(new java.awt.Dimension(25, 31));
        botonSubirTope.setMinimumSize(new java.awt.Dimension(25, 31));
        botonSubirTope.setPreferredSize(new java.awt.Dimension(25, 31));
        botonSubirTope.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonSubirTope.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSubirTopeActionPerformed(evt);
            }
        });
        toolbarPosicion.add(botonSubirTope);

        panelSuperior.add(toolbarPosicion);

        toolbarPropiedades.setFloatable(false);
        toolbarPropiedades.setRollover(true);
        toolbarPropiedades.setMaximumSize(new java.awt.Dimension(535, 35));
        toolbarPropiedades.setMinimumSize(new java.awt.Dimension(535, 35));
        toolbarPropiedades.setName(""); // NOI18N
        toolbarPropiedades.setPreferredSize(new java.awt.Dimension(535, 35));
        toolbarPropiedades.add(separadorP1);

        // He de asignar mi renderer personalizado
        comboBoxColores.setRenderer(new sm.rsg.iu.RColoresPersonalizado());
        // Finalmente asignar el array de colores
        comboBoxColores.setModel(new javax.swing.DefaultComboBoxModel(misColores));
        comboBoxColores.setToolTipText("Seleciona el color del trazo");
        comboBoxColores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxColoresActionPerformed(evt);
            }
        });
        toolbarPropiedades.add(comboBoxColores);

        botonElegirMas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/plus.png"))); // NOI18N
        botonElegirMas.setToolTipText("Permitir colores adicionales");
        botonElegirMas.setFocusable(false);
        botonElegirMas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonElegirMas.setMaximumSize(new java.awt.Dimension(25, 25));
        botonElegirMas.setMinimumSize(new java.awt.Dimension(25, 25));
        botonElegirMas.setPreferredSize(new java.awt.Dimension(25, 25));
        botonElegirMas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonElegirMas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonElegirMasActionPerformed(evt);
            }
        });
        toolbarPropiedades.add(botonElegirMas);

        panelOtrosColores.setMaximumSize(new java.awt.Dimension(25, 25));
        panelOtrosColores.setMinimumSize(new java.awt.Dimension(25, 25));
        panelOtrosColores.setPreferredSize(new java.awt.Dimension(25, 25));
        panelOtrosColores.setLayout(new java.awt.BorderLayout());

        botonOtrosColores.setBackground(new java.awt.Color(0, 0, 0));
        botonOtrosColores.setToolTipText("Seleciona el color del trazo (Adicionales)");
        botonOtrosColores.setEnabled(false);
        botonOtrosColores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonOtrosColoresActionPerformed(evt);
            }
        });
        panelOtrosColores.add(botonOtrosColores, java.awt.BorderLayout.CENTER);

        toolbarPropiedades.add(panelOtrosColores);
        toolbarPropiedades.add(jSeparator7);

        // He tenido que añadir esta linea para poder editar mi spinner
        // y ponerle un minimo, maximo y un valor inicial, no queria
        // que 0 fuese el inicial.
        cambioGrosor.setModel(new SpinnerNumberModel(1,1,100,1));
        cambioGrosor.setToolTipText("Tamaño del trazo");
        cambioGrosor.setMaximumSize(new java.awt.Dimension(29, 20));
        cambioGrosor.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                cambioGrosorStateChanged(evt);
            }
        });
        toolbarPropiedades.add(cambioGrosor);

        grupoTrazo.add(botonLineaContinua);
        botonLineaContinua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/border-all-variant.png"))); // NOI18N
        botonLineaContinua.setSelected(true);
        botonLineaContinua.setToolTipText("Trazo Continuo");
        botonLineaContinua.setFocusable(false);
        botonLineaContinua.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonLineaContinua.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonLineaContinua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonLineaContinuaActionPerformed(evt);
            }
        });
        toolbarPropiedades.add(botonLineaContinua);

        grupoTrazo.add(botonLineaDiscontinua);
        botonLineaDiscontinua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/border-none-variant.png"))); // NOI18N
        botonLineaDiscontinua.setToolTipText("Trazo Discontinuo");
        botonLineaDiscontinua.setFocusable(false);
        botonLineaDiscontinua.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonLineaDiscontinua.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonLineaDiscontinua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonLineaDiscontinuaActionPerformed(evt);
            }
        });
        toolbarPropiedades.add(botonLineaDiscontinua);
        toolbarPropiedades.add(separadorP3);

        botonRelleno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/rellenar.png"))); // NOI18N
        botonRelleno.setToolTipText("Habilitar Relleno");
        botonRelleno.setFocusable(false);
        botonRelleno.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonRelleno.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonRelleno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRellenoActionPerformed(evt);
            }
        });
        toolbarPropiedades.add(botonRelleno);

        panelColoresRelleno.setMaximumSize(new java.awt.Dimension(50, 25));
        panelColoresRelleno.setMinimumSize(new java.awt.Dimension(50, 25));
        panelColoresRelleno.setPreferredSize(new java.awt.Dimension(50, 25));
        panelColoresRelleno.setLayout(new java.awt.BorderLayout());

        botonColoresRellenoUno.setBackground(new java.awt.Color(0, 0, 0));
        botonColoresRellenoUno.setToolTipText("Color del Relleno");
        botonColoresRellenoUno.setEnabled(false);
        botonColoresRellenoUno.setFocusable(false);
        botonColoresRellenoUno.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonColoresRellenoUno.setMaximumSize(new java.awt.Dimension(25, 25));
        botonColoresRellenoUno.setMinimumSize(new java.awt.Dimension(25, 25));
        botonColoresRellenoUno.setPreferredSize(new java.awt.Dimension(25, 25));
        botonColoresRellenoUno.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonColoresRellenoUno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonColoresRellenoUnoActionPerformed(evt);
            }
        });
        panelColoresRelleno.add(botonColoresRellenoUno, java.awt.BorderLayout.LINE_START);

        botonColoresRellenoDos.setBackground(new java.awt.Color(255, 255, 255));
        botonColoresRellenoDos.setToolTipText("Color de Relleno Secundario");
        botonColoresRellenoDos.setEnabled(false);
        botonColoresRellenoDos.setFocusable(false);
        botonColoresRellenoDos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonColoresRellenoDos.setMaximumSize(new java.awt.Dimension(25, 25));
        botonColoresRellenoDos.setMinimumSize(new java.awt.Dimension(25, 25));
        botonColoresRellenoDos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonColoresRellenoDos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonColoresRellenoDosActionPerformed(evt);
            }
        });
        panelColoresRelleno.add(botonColoresRellenoDos, java.awt.BorderLayout.CENTER);

        toolbarPropiedades.add(panelColoresRelleno);
        toolbarPropiedades.add(jSeparator5);

        grupoDegradado.add(botonRellenoNormal);
        botonRellenoNormal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cancel.png"))); // NOI18N
        botonRellenoNormal.setSelected(true);
        botonRellenoNormal.setToolTipText("Sin Gradiente");
        botonRellenoNormal.setEnabled(false);
        botonRellenoNormal.setFocusable(false);
        botonRellenoNormal.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonRellenoNormal.setMaximumSize(new java.awt.Dimension(25, 25));
        botonRellenoNormal.setMinimumSize(new java.awt.Dimension(25, 25));
        botonRellenoNormal.setPreferredSize(new java.awt.Dimension(25, 25));
        botonRellenoNormal.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonRellenoNormal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRellenoNormalActionPerformed(evt);
            }
        });
        toolbarPropiedades.add(botonRellenoNormal);

        grupoDegradado.add(botonRellenoHorizontal);
        botonRellenoHorizontal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/swap-horizontal.png"))); // NOI18N
        botonRellenoHorizontal.setToolTipText("Gradiente Horizontal");
        botonRellenoHorizontal.setEnabled(false);
        botonRellenoHorizontal.setFocusable(false);
        botonRellenoHorizontal.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonRellenoHorizontal.setMaximumSize(new java.awt.Dimension(25, 25));
        botonRellenoHorizontal.setMinimumSize(new java.awt.Dimension(25, 25));
        botonRellenoHorizontal.setPreferredSize(new java.awt.Dimension(25, 25));
        botonRellenoHorizontal.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonRellenoHorizontal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRellenoHorizontalActionPerformed(evt);
            }
        });
        toolbarPropiedades.add(botonRellenoHorizontal);

        grupoDegradado.add(botonRellenoVertical);
        botonRellenoVertical.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/swap-vertical.png"))); // NOI18N
        botonRellenoVertical.setToolTipText("Gradiente Vertical");
        botonRellenoVertical.setEnabled(false);
        botonRellenoVertical.setFocusable(false);
        botonRellenoVertical.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonRellenoVertical.setMaximumSize(new java.awt.Dimension(25, 25));
        botonRellenoVertical.setMinimumSize(new java.awt.Dimension(25, 25));
        botonRellenoVertical.setPreferredSize(new java.awt.Dimension(25, 25));
        botonRellenoVertical.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonRellenoVertical.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRellenoVerticalActionPerformed(evt);
            }
        });
        toolbarPropiedades.add(botonRellenoVertical);
        toolbarPropiedades.add(separadorP4);

        sliderTransparencia.setToolTipText("Ajustar transparencia");
        sliderTransparencia.setValue(100);
        sliderTransparencia.setMaximumSize(new java.awt.Dimension(50, 23));
        sliderTransparencia.setMinimumSize(new java.awt.Dimension(50, 23));
        sliderTransparencia.setPreferredSize(new java.awt.Dimension(50, 23));
        sliderTransparencia.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderTransparenciaStateChanged(evt);
            }
        });
        toolbarPropiedades.add(sliderTransparencia);
        toolbarPropiedades.add(jSeparator6);

        botonAlisado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/alisar.png"))); // NOI18N
        botonAlisado.setToolTipText("Alisar");
        botonAlisado.setFocusable(false);
        botonAlisado.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonAlisado.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonAlisado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAlisadoActionPerformed(evt);
            }
        });
        toolbarPropiedades.add(botonAlisado);

        panelSuperior.add(toolbarPropiedades);

        toolbarSonido.setFloatable(false);
        toolbarSonido.setRollover(true);
        toolbarSonido.add(jSeparator2);

        botonPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/play24x24.png"))); // NOI18N
        botonPlay.setToolTipText("Iniciar Reproducción");
        botonPlay.setFocusable(false);
        botonPlay.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonPlay.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPlayActionPerformed(evt);
            }
        });
        toolbarSonido.add(botonPlay);

        botonStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/stop24x24.png"))); // NOI18N
        botonStop.setToolTipText("Parar Reproducción/Grabación");
        botonStop.setFocusable(false);
        botonStop.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonStop.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonStopActionPerformed(evt);
            }
        });
        toolbarSonido.add(botonStop);

        listaReproduccion.setToolTipText("Lista de audios abiertos");
        listaReproduccion.setMinimumSize(new java.awt.Dimension(100, 30));
        listaReproduccion.setPreferredSize(new java.awt.Dimension(100, 30));
        toolbarSonido.add(listaReproduccion);
        toolbarSonido.add(jSeparator1);

        botonRecord.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/record24x24.png"))); // NOI18N
        botonRecord.setToolTipText("Iniciar Grabación");
        botonRecord.setFocusable(false);
        botonRecord.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonRecord.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonRecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRecordActionPerformed(evt);
            }
        });
        toolbarSonido.add(botonRecord);
        toolbarSonido.add(jSeparator8);

        labelDuracion.setText("00:00");
        labelDuracion.setToolTipText("Tiempo Reproducción/Grabación");
        toolbarSonido.add(labelDuracion);

        panelSuperior.add(toolbarSonido);

        toolbarVideo.setFloatable(false);
        toolbarVideo.setRollover(true);
        toolbarVideo.add(jSeparator3);

        botonWebCam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Camara.png"))); // NOI18N
        botonWebCam.setToolTipText("Abrir Webcam");
        botonWebCam.setFocusable(false);
        botonWebCam.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonWebCam.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonWebCam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonWebCamActionPerformed(evt);
            }
        });
        toolbarVideo.add(botonWebCam);

        botonCapturaImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Capturar.png"))); // NOI18N
        botonCapturaImagen.setToolTipText("Tomar instantánea de video o webcam");
        botonCapturaImagen.setFocusable(false);
        botonCapturaImagen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonCapturaImagen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonCapturaImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCapturaImagenActionPerformed(evt);
            }
        });
        toolbarVideo.add(botonCapturaImagen);

        botonDetection.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/eye.png"))); // NOI18N
        botonDetection.setToolTipText("Iniciar sistema de vigilancia");
        botonDetection.setFocusable(false);
        botonDetection.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonDetection.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonDetection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonDetectionActionPerformed(evt);
            }
        });
        toolbarVideo.add(botonDetection);

        panelSuperior.add(toolbarVideo);

        getContentPane().add(panelSuperior, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout escritorioLayout = new javax.swing.GroupLayout(escritorio);
        escritorio.setLayout(escritorioLayout);
        escritorioLayout.setHorizontalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1340, Short.MAX_VALUE)
        );
        escritorioLayout.setVerticalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 189, Short.MAX_VALUE)
        );

        getContentPane().add(escritorio, java.awt.BorderLayout.CENTER);

        panelInferior.setLayout(new java.awt.BorderLayout());

        panelHerramienta.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelHerramienta.setLayout(new java.awt.BorderLayout());

        infoHerramienta.setText("Herramienta");
        panelHerramienta.add(infoHerramienta, java.awt.BorderLayout.LINE_START);
        panelHerramienta.add(infoCoordenada, java.awt.BorderLayout.LINE_END);

        panelInferior.add(panelHerramienta, java.awt.BorderLayout.SOUTH);

        panelImagen.setLayout(new java.awt.BorderLayout());

        toolbarImagen.setRollover(true);

        panelDuplicado.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Duplicar"));
        panelDuplicado.setMaximumSize(new java.awt.Dimension(70, 60));
        panelDuplicado.setMinimumSize(new java.awt.Dimension(70, 60));
        panelDuplicado.setPreferredSize(new java.awt.Dimension(70, 60));
        panelDuplicado.setLayout(new javax.swing.BoxLayout(panelDuplicado, javax.swing.BoxLayout.LINE_AXIS));

        botonDuplicarImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/content-copy.png"))); // NOI18N
        botonDuplicarImagen.setToolTipText("Duplicar la imagen");
        botonDuplicarImagen.setFocusable(false);
        botonDuplicarImagen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonDuplicarImagen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonDuplicarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonDuplicarImagenActionPerformed(evt);
            }
        });
        panelDuplicado.add(botonDuplicarImagen);

        toolbarImagen.add(panelDuplicado);

        panelBrillo.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Brillo"));
        panelBrillo.setMaximumSize(new java.awt.Dimension(145, 46));
        panelBrillo.setMinimumSize(new java.awt.Dimension(145, 46));
        panelBrillo.setPreferredSize(new java.awt.Dimension(145, 60));
        panelBrillo.setLayout(new javax.swing.BoxLayout(panelBrillo, javax.swing.BoxLayout.LINE_AXIS));

        sliderBrillo.setMaximum(255);
        sliderBrillo.setMinimum(-255);
        sliderBrillo.setToolTipText("Cambiar brillo de la imagen");
        sliderBrillo.setValue(0);
        sliderBrillo.setMaximumSize(new java.awt.Dimension(130, 23));
        sliderBrillo.setMinimumSize(new java.awt.Dimension(130, 23));
        sliderBrillo.setPreferredSize(new java.awt.Dimension(130, 23));
        sliderBrillo.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderBrilloStateChanged(evt);
            }
        });
        sliderBrillo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sliderBrilloFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sliderBrilloFocusLost(evt);
            }
        });
        panelBrillo.add(sliderBrillo);

        toolbarImagen.add(panelBrillo);

        panelFiltro.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Filtro"));
        panelFiltro.setMinimumSize(new java.awt.Dimension(100, 43));
        panelFiltro.setPreferredSize(new java.awt.Dimension(110, 60));
        panelFiltro.setLayout(new javax.swing.BoxLayout(panelFiltro, javax.swing.BoxLayout.LINE_AXIS));

        comboBoxFiltros.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Media", "Binomial", "Enfoque", "Relieve", "Lapaciano" }));
        comboBoxFiltros.setToolTipText("Aplicar filtro a la imagen");
        comboBoxFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxFiltrosActionPerformed(evt);
            }
        });
        panelFiltro.add(comboBoxFiltros);

        toolbarImagen.add(panelFiltro);

        panelContraste.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Contraste"));
        panelContraste.setMaximumSize(new java.awt.Dimension(113, 60));
        panelContraste.setMinimumSize(new java.awt.Dimension(113, 60));
        panelContraste.setPreferredSize(new java.awt.Dimension(145, 60));
        panelContraste.setLayout(new javax.swing.BoxLayout(panelContraste, javax.swing.BoxLayout.LINE_AXIS));

        botonContraste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/contraste.png"))); // NOI18N
        botonContraste.setToolTipText("Filtro contraste");
        botonContraste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonContrasteActionPerformed(evt);
            }
        });
        panelContraste.add(botonContraste);

        botonIluminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/iluminar.png"))); // NOI18N
        botonIluminar.setToolTipText("Filtro iluminar");
        botonIluminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonIluminarActionPerformed(evt);
            }
        });
        panelContraste.add(botonIluminar);

        botonOscurecer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/oscurecer.png"))); // NOI18N
        botonOscurecer.setToolTipText("Filtro oscurecer");
        botonOscurecer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonOscurecerActionPerformed(evt);
            }
        });
        panelContraste.add(botonOscurecer);

        toolbarImagen.add(panelContraste);

        panelSeno.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED), " "));
        panelSeno.setMaximumSize(new java.awt.Dimension(400, 56));
        panelSeno.setMinimumSize(new java.awt.Dimension(400, 56));
        panelSeno.setPreferredSize(new java.awt.Dimension(400, 60));
        panelSeno.setLayout(new javax.swing.BoxLayout(panelSeno, javax.swing.BoxLayout.LINE_AXIS));

        botonSinuidal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/sinusoidal.png"))); // NOI18N
        botonSinuidal.setToolTipText("Aplicar funcion seno");
        botonSinuidal.setFocusable(false);
        botonSinuidal.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonSinuidal.setMaximumSize(new java.awt.Dimension(40, 33));
        botonSinuidal.setMinimumSize(new java.awt.Dimension(40, 33));
        botonSinuidal.setPreferredSize(new java.awt.Dimension(40, 33));
        botonSinuidal.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonSinuidal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSinuidalActionPerformed(evt);
            }
        });
        panelSeno.add(botonSinuidal);

        botonFuncionSenCos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/pandora.png"))); // NOI18N
        botonFuncionSenCos.setToolTipText("Lookup Personalizado: Seno + Coseno");
        botonFuncionSenCos.setMaximumSize(new java.awt.Dimension(45, 33));
        botonFuncionSenCos.setMinimumSize(new java.awt.Dimension(45, 33));
        botonFuncionSenCos.setPreferredSize(new java.awt.Dimension(45, 33));
        botonFuncionSenCos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonFuncionSenCosActionPerformed(evt);
            }
        });
        panelSeno.add(botonFuncionSenCos);

        botonSepia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/sepia.png"))); // NOI18N
        botonSepia.setToolTipText("Aplicar filtro sepia");
        botonSepia.setMaximumSize(new java.awt.Dimension(40, 33));
        botonSepia.setMinimumSize(new java.awt.Dimension(40, 33));
        botonSepia.setPreferredSize(new java.awt.Dimension(40, 33));
        botonSepia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSepiaActionPerformed(evt);
            }
        });
        panelSeno.add(botonSepia);

        botonTintado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/tintar.png"))); // NOI18N
        botonTintado.setToolTipText("Aplicar tintado");
        botonTintado.setMaximumSize(new java.awt.Dimension(40, 33));
        botonTintado.setMinimumSize(new java.awt.Dimension(40, 33));
        botonTintado.setPreferredSize(new java.awt.Dimension(40, 33));
        botonTintado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonTintadoActionPerformed(evt);
            }
        });
        panelSeno.add(botonTintado);

        botonEcualizador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ecualizar.png"))); // NOI18N
        botonEcualizador.setToolTipText("Aplicar Ecualizador");
        botonEcualizador.setMaximumSize(new java.awt.Dimension(40, 33));
        botonEcualizador.setMinimumSize(new java.awt.Dimension(40, 33));
        botonEcualizador.setPreferredSize(new java.awt.Dimension(40, 33));
        botonEcualizador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEcualizadorActionPerformed(evt);
            }
        });
        panelSeno.add(botonEcualizador);

        botonEfectoTunel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/smoke-detector.png"))); // NOI18N
        botonEfectoTunel.setToolTipText("Aplicar efecto tunel");
        botonEfectoTunel.setMaximumSize(new java.awt.Dimension(45, 33));
        botonEfectoTunel.setMinimumSize(new java.awt.Dimension(45, 33));
        botonEfectoTunel.setPreferredSize(new java.awt.Dimension(45, 33));
        botonEfectoTunel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEfectoTunelActionPerformed(evt);
            }
        });
        panelSeno.add(botonEfectoTunel);

        botonEfectoCalor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/fire.png"))); // NOI18N
        botonEfectoCalor.setToolTipText("Aplicar Efecto Cálido");
        botonEfectoCalor.setMaximumSize(new java.awt.Dimension(45, 33));
        botonEfectoCalor.setMinimumSize(new java.awt.Dimension(45, 33));
        botonEfectoCalor.setPreferredSize(new java.awt.Dimension(45, 33));
        botonEfectoCalor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEfectoCalorActionPerformed(evt);
            }
        });
        panelSeno.add(botonEfectoCalor);

        botonEfectoFrio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/snowflake.png"))); // NOI18N
        botonEfectoFrio.setToolTipText("Aplicar filtro frío");
        botonEfectoFrio.setMaximumSize(new java.awt.Dimension(45, 33));
        botonEfectoFrio.setMinimumSize(new java.awt.Dimension(45, 33));
        botonEfectoFrio.setPreferredSize(new java.awt.Dimension(45, 33));
        botonEfectoFrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEfectoFrioActionPerformed(evt);
            }
        });
        panelSeno.add(botonEfectoFrio);

        botonInvertir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/invert-colors.png"))); // NOI18N
        botonInvertir.setToolTipText("Invertir colores");
        botonInvertir.setMaximumSize(new java.awt.Dimension(45, 33));
        botonInvertir.setMinimumSize(new java.awt.Dimension(45, 33));
        botonInvertir.setPreferredSize(new java.awt.Dimension(45, 33));
        botonInvertir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonInvertirActionPerformed(evt);
            }
        });
        panelSeno.add(botonInvertir);

        toolbarImagen.add(panelSeno);

        panelColoresOpciones.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Color"));
        panelColoresOpciones.setInheritsPopupMenu(true);
        panelColoresOpciones.setMaximumSize(new java.awt.Dimension(120, 60));
        panelColoresOpciones.setMinimumSize(new java.awt.Dimension(120, 60));
        panelColoresOpciones.setPreferredSize(new java.awt.Dimension(130, 60));
        panelColoresOpciones.setLayout(new javax.swing.BoxLayout(panelColoresOpciones, javax.swing.BoxLayout.LINE_AXIS));

        botonExtraerBandas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/bandas.png"))); // NOI18N
        botonExtraerBandas.setToolTipText("Extraer bandas");
        botonExtraerBandas.setFocusable(false);
        botonExtraerBandas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonExtraerBandas.setMaximumSize(new java.awt.Dimension(45, 33));
        botonExtraerBandas.setMinimumSize(new java.awt.Dimension(45, 33));
        botonExtraerBandas.setPreferredSize(new java.awt.Dimension(45, 33));
        botonExtraerBandas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonExtraerBandas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonExtraerBandasActionPerformed(evt);
            }
        });
        panelColoresOpciones.add(botonExtraerBandas);

        comboBoxEspaciosColores.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "RGB", "YCC", "GREY" }));
        comboBoxEspaciosColores.setToolTipText("Tipo de espacio de colores");
        comboBoxEspaciosColores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxEspaciosColoresActionPerformed(evt);
            }
        });
        panelColoresOpciones.add(comboBoxEspaciosColores);

        toolbarImagen.add(panelColoresOpciones);

        panelGiro.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Rotación"));
        panelGiro.setMaximumSize(new java.awt.Dimension(240, 60));
        panelGiro.setMinimumSize(new java.awt.Dimension(240, 60));
        panelGiro.setPreferredSize(new java.awt.Dimension(240, 60));
        panelGiro.setLayout(new javax.swing.BoxLayout(panelGiro, javax.swing.BoxLayout.LINE_AXIS));

        sliderGiro.setMaximum(360);
        sliderGiro.setMinorTickSpacing(90);
        sliderGiro.setPaintTicks(true);
        sliderGiro.setToolTipText("Rotar Imagen");
        sliderGiro.setValue(0);
        sliderGiro.setMaximumSize(new java.awt.Dimension(100, 31));
        sliderGiro.setMinimumSize(new java.awt.Dimension(100, 31));
        sliderGiro.setPreferredSize(new java.awt.Dimension(100, 31));
        sliderGiro.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderGiroStateChanged(evt);
            }
        });
        sliderGiro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sliderGiroFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sliderGiroFocusLost(evt);
            }
        });
        panelGiro.add(sliderGiro);

        botonGiro90.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/rotacion90.png"))); // NOI18N
        botonGiro90.setToolTipText("Rotar Imagen 90º");
        botonGiro90.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGiro90ActionPerformed(evt);
            }
        });
        panelGiro.add(botonGiro90);

        botonGiro180.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/rotacion180.png"))); // NOI18N
        botonGiro180.setToolTipText("Rotar imagen 180º");
        botonGiro180.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGiro180ActionPerformed(evt);
            }
        });
        panelGiro.add(botonGiro180);

        botonGiro270.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/rotacion270.png"))); // NOI18N
        botonGiro270.setToolTipText("Rotar imagen 270º");
        botonGiro270.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGiro270ActionPerformed(evt);
            }
        });
        panelGiro.add(botonGiro270);

        toolbarImagen.add(panelGiro);

        panelZoom.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Escala"));
        panelZoom.setMaximumSize(new java.awt.Dimension(100, 60));
        panelZoom.setMinimumSize(new java.awt.Dimension(100, 60));
        panelZoom.setPreferredSize(new java.awt.Dimension(100, 60));
        panelZoom.setLayout(new javax.swing.BoxLayout(panelZoom, javax.swing.BoxLayout.LINE_AXIS));

        botonEscalaAumento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/aumentar.png"))); // NOI18N
        botonEscalaAumento.setToolTipText("Aumentar escalado");
        botonEscalaAumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEscalaAumentoActionPerformed(evt);
            }
        });
        panelZoom.add(botonEscalaAumento);

        botonEscalaDescenso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/disminuir.png"))); // NOI18N
        botonEscalaDescenso.setToolTipText("Dirminuir escalado");
        botonEscalaDescenso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEscalaDescensoActionPerformed(evt);
            }
        });
        panelZoom.add(botonEscalaDescenso);

        toolbarImagen.add(panelZoom);

        panelImagen.add(toolbarImagen, java.awt.BorderLayout.CENTER);

        panelInferior.add(panelImagen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelInferior, java.awt.BorderLayout.PAGE_END);

        menuArchivo.setText("Archivo");
        menuArchivo.setToolTipText("Opciones");

        menuNuevo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        menuNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        menuNuevo.setText("Nuevo");
        menuNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNuevoActionPerformed(evt);
            }
        });
        menuArchivo.add(menuNuevo);

        menuAbrir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        menuAbrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/abrir.png"))); // NOI18N
        menuAbrir.setText("Abrir");
        menuAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAbrirActionPerformed(evt);
            }
        });
        menuArchivo.add(menuAbrir);

        menuGuardar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        menuGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardar.png"))); // NOI18N
        menuGuardar.setText("Guardar");
        menuGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuGuardarActionPerformed(evt);
            }
        });
        menuArchivo.add(menuGuardar);

        menuAbrirAudio.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK));
        menuAbrirAudio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/openAudio24x24.png"))); // NOI18N
        menuAbrirAudio.setText("Abrir Audio");
        menuAbrirAudio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAbrirAudioActionPerformed(evt);
            }
        });
        menuArchivo.add(menuAbrirAudio);

        menuAbrirVideo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        menuAbrirVideo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/AbrirVideo.png"))); // NOI18N
        menuAbrirVideo.setText("Abrir Video");
        menuAbrirVideo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAbrirVideoActionPerformed(evt);
            }
        });
        menuArchivo.add(menuAbrirVideo);

        barraMenu.add(menuArchivo);

        menuVer.setText("Ver");

        checkVerEstado.setSelected(true);
        checkVerEstado.setText("Barra de Estado");
        checkVerEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkVerEstadoActionPerformed(evt);
            }
        });
        menuVer.add(checkVerEstado);

        checkVerFormas.setSelected(true);
        checkVerFormas.setText("Barra de Formas");
        checkVerFormas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkVerFormasActionPerformed(evt);
            }
        });
        menuVer.add(checkVerFormas);

        checkVerAtributos.setSelected(true);
        checkVerAtributos.setText("Barra de Atributos");
        checkVerAtributos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkVerAtributosActionPerformed(evt);
            }
        });
        menuVer.add(checkVerAtributos);

        barraMenu.add(menuVer);

        menuImagen.setText("Imagen");

        menuCambiarTamanio.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        menuCambiarTamanio.setText("Tamaño nueva imagen");
        menuCambiarTamanio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCambiarTamanioActionPerformed(evt);
            }
        });
        menuImagen.add(menuCambiarTamanio);

        barraMenu.add(menuImagen);

        botonOtros.setText("Otros");

        menuFiltrosClase.setText("Filtros vistos en Clase");

        menuRescaleOp.setText("RescaleOP");
        menuRescaleOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRescaleOpActionPerformed(evt);
            }
        });
        menuFiltrosClase.add(menuRescaleOp);

        menuConvolveOp.setText("ConvolveOp");
        menuConvolveOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuConvolveOpActionPerformed(evt);
            }
        });
        menuFiltrosClase.add(menuConvolveOp);

        menuAffineTransformOp.setText("AffineTransformOp");
        menuAffineTransformOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAffineTransformOpActionPerformed(evt);
            }
        });
        menuFiltrosClase.add(menuAffineTransformOp);

        menuLookupOp.setText("LookupOp");
        menuLookupOp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuLookupOpActionPerformed(evt);
            }
        });
        menuFiltrosClase.add(menuLookupOp);

        menuBandCombineOp.setText("BandCombineOp");
        menuFiltrosClase.add(menuBandCombineOp);

        menuColorConvertOp.setText("ColorConvertOp");
        menuFiltrosClase.add(menuColorConvertOp);

        botonOtros.add(menuFiltrosClase);
        botonOtros.add(separadorMenuOtros);

        menuSobre.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_MASK));
        menuSobre.setText("Sobre el Programa...");
        menuSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSobreActionPerformed(evt);
            }
        });
        botonOtros.add(menuSobre);

        barraMenu.add(botonOtros);

        setJMenuBar(barraMenu);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNuevoActionPerformed
        vi = new VentanaInternaImagen(this); // la recibe a ella misma
        vi.setTitle("Nueva ventana "+getNumeroVentanas());
        vi.setLocation(getCoordenadaVentana(),getCoordenadaVentana());
        // Tras crearla, aumento mis dos variables
        aumentarNumeroVentanas();
        aumentarCoordenadaVentana();
        // Y finalmente, la añado
        escritorio.add(vi);
        vi.setVisible(true);
        // Referente a la practica 8
        BufferedImage img;
        img = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);
        
        // Por defecto, se crea un rectangulo negro. Usare el metodo createGraphics
        // para crear un cuadrado blanco. (esto me crea un cuadrado blanco por defecto)
        img.createGraphics().fillRect(0, 0, 300, 300);
        vi.setImage(img);
        
        // No puedo crear la "región" ni los "bordes" desde aquí, lo hare
        // desde el metodo dibujar de paintcomponentes de Image, que es el que estoy
        // usando
    }//GEN-LAST:event_menuNuevoActionPerformed

    private void menuAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAbrirActionPerformed
        JFileChooser dlg = new JFileChooser();
        // voy a asegurarme de que no haya filtro "todo tipo de archivo"
        dlg.setAcceptAllFileFilterUsed(false);
        // Creamos el filtro, en este caso, filtro de imagenes
        FileFilter filtro = new FileNameExtensionFilter("Imagenes (jpg, jpeg, png, gif, bmp)",
                "jpg","jpeg","png","gif","bmp");
        // Y la añado
        dlg.addChoosableFileFilter(filtro);
        int resp = dlg.showOpenDialog(this);
        if (resp == JFileChooser.APPROVE_OPTION) {
            try {
                // getAbsolutePath o noseque para tener el nombre completo
                File f = dlg.getSelectedFile();
                BufferedImage img = ImageIO.read(f);
                VentanaInternaImagen vi = new VentanaInternaImagen(this);
                // Al abrir una imagen, modificaremos el tamaño deseado del contorno
                // por eso he puesto el metodo en setImage
                vi.setImage(img);
                this.escritorio.add(vi);
                vi.setTitle(f.getName());
                vi.setVisible(true);
            } catch (Exception ex) {
                System.err.println("Error al leer la imagen");
            }
        }
    }//GEN-LAST:event_menuAbrirActionPerformed

    private void menuGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGuardarActionPerformed
        System.out.println(Arrays.toString(ImageIO.getWriterFormatNames()));
        JFileChooser dlg = new JFileChooser();
        // voy a asegurarme de que no haya filtro "todo tipo de archivo"
        dlg.setAcceptAllFileFilterUsed(false);
        // Creamos el filtro, en este caso, filtro de imagenes
        //FileFilter filtro = new FileNameExtensionFilter("Tipos de imagen (jpg, jpeg, png, gif, bmp)",
        //        "jpg","jpeg","png","gif","bmp");
        // En este caso he creado unpng a parte para ver si funciona
        //FileFilter filtroSoloPNG = new FileNameExtensionFilter("IMAGEN PNG","png");
        // Y la añado
        // PROBLEMA: no detectapng correctamente
        // SOLUCION: obtener el formato a partir de la extension
        //FileFilter filtro = new FileNameExtensionFilter("Tipos de imagen",ImageIO.getWriterFormatNames());
        FileFilter filtroJPG = new FileNameExtensionFilter("Imagen JPG","jpg","JPG");
        FileFilter filtroJPEG = new FileNameExtensionFilter("Imagen JPEG","jpeg","JPEG");
        FileFilter filtroPNG = new FileNameExtensionFilter("Imagen PNG","png","PNG");
        FileFilter filtroGIF = new FileNameExtensionFilter("Imagem GIF","gif","GIF");
        FileFilter filtroBMP = new FileNameExtensionFilter("Imagen BMP","bmp","BMP");
        FileFilter filtroWBMP = new FileNameExtensionFilter("Imagen WBMP","wbmp","WBMP");
        dlg.addChoosableFileFilter(filtroJPG);
        dlg.addChoosableFileFilter(filtroJPEG);
        dlg.addChoosableFileFilter(filtroPNG);
        dlg.addChoosableFileFilter(filtroGIF);
        dlg.addChoosableFileFilter(filtroBMP);
        dlg.addChoosableFileFilter(filtroWBMP);
        //dlg.addChoosableFileFilter(filtroSoloPNG);
        int resp = dlg.showSaveDialog(this);
        if (resp == JFileChooser.APPROVE_OPTION) {
            try {
                BufferedImage img = vi.getImage(true);

                if (img != null  && vi.getLienzo2D()!=null) {
                    File f = dlg.getSelectedFile();
                    FileFilter filtro = dlg.getFileFilter();
                    // Hay que comprobar los canales alpha, es decir, si tiene transparencia
                    if (img.getType() == BufferedImage.TYPE_INT_ARGB && filtro!=filtroPNG) {
                        // Si es png y no la voy a guardar en el mismo formato, le tengo que cambiar el tipo
                        BufferedImage imgAux = new BufferedImage(img.getWidth(),
                                img.getHeight(), BufferedImage.TYPE_INT_RGB);
                        // lo que sea transparente, lo pintaré de color blanco
                        imgAux.createGraphics().drawImage(img, 0, 0, Color.white, null);
                        //img = null;
                        img = RFuncionesPropias.copiaImagen(imgAux);
                    }
                    if (filtro == filtroJPG) {
                        ImageIO.write(img, "jpg", f);
                    } else if (filtro == filtroJPEG) {
                        ImageIO.write(img, "jpeg", f);
                    } else if (filtro == filtroPNG) {
                        ImageIO.write(img, "png", f);
                    } else if (filtro == filtroGIF) {
                        ImageIO.write(img, "gif", f);
                    } else if (filtro == filtroBMP) {
                        ImageIO.write(img, "bmp", f);
                    } else if (filtro == filtroWBMP) {
                        ImageIO.write(img, "wbmp", f);
                    }
                    vi.setTitle(f.getName());
                }
            } catch (Exception ex) {
                System.err.println("Error al guardar la imagen");
            }
 }
    }//GEN-LAST:event_menuGuardarActionPerformed

    private void checkVerEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkVerEstadoActionPerformed
        this.infoHerramienta.setVisible(!this.infoHerramienta.isVisible());
    }//GEN-LAST:event_checkVerEstadoActionPerformed

    private void botonLineaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonLineaActionPerformed
        this.vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null  && vi.getLienzo2D()!=null) {
            this.vi.getLienzo2D().setHerramienta(1);
            this.infoHerramienta.setText("Línea");
            this.vi.getLienzo2D().setEstaEditable(false);
            this.vi.getLienzo2D().quitarBounds();
            this.desactivaBotonesPosicionamiento();
        }
    }//GEN-LAST:event_botonLineaActionPerformed

    private void botonRectanguloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRectanguloActionPerformed
        this.vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null  && vi.getLienzo2D()!=null) {
            this.vi.getLienzo2D().setHerramienta(2);
            this.infoHerramienta.setText("Rectángulo");
            this.vi.getLienzo2D().setEstaEditable(false);
            this.vi.getLienzo2D().quitarBounds();
            this.desactivaBotonesPosicionamiento();
        }
    }//GEN-LAST:event_botonRectanguloActionPerformed

    private void botonOvaloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonOvaloActionPerformed
        this.vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null  && vi.getLienzo2D()!=null) {
            this.vi.getLienzo2D().setHerramienta(3);
            this.infoHerramienta.setText("Óvalo");
            this.vi.getLienzo2D().setEstaEditable(false);
            this.vi.getLienzo2D().quitarBounds();
            this.desactivaBotonesPosicionamiento();
        }
    }//GEN-LAST:event_botonOvaloActionPerformed

    private void cambioGrosorStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_cambioGrosorStateChanged
        // Aquí no hay "actionPerformed", pero existe el evento
        // statechanged, que comprueba si algo ha cambiado
        // Usándolo, me puedo quedar con el número que marque
        this.vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if(vi!=null  && vi.getLienzo2D()!=null){
            // Get value devuelve un "objeto", hace falta un casting a int
            int valor = (Integer)cambioGrosor.getValue();
            this.vi.getLienzo2D().setGrosor(valor);
            this.repaint();
        }    
    }//GEN-LAST:event_cambioGrosorStateChanged

    private void botonNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNuevoActionPerformed
        this.menuNuevoActionPerformed(evt);
    }//GEN-LAST:event_botonNuevoActionPerformed

    private void botonAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAbrirActionPerformed
        this.menuAbrirActionPerformed(evt);
    }//GEN-LAST:event_botonAbrirActionPerformed

    private void botonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGuardarActionPerformed
        this.menuGuardarActionPerformed(evt);
    }//GEN-LAST:event_botonGuardarActionPerformed

    private void botonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEditarActionPerformed
        this.vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if(vi!=null  && vi.getLienzo2D()!=null){
                this.vi.getLienzo2D().setEstaEditable(true);
                this.infoHerramienta.setText("Editar");
                //this.activaBotonesPosicionamiento(true);
        }
    }//GEN-LAST:event_botonEditarActionPerformed

    private void checkVerFormasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkVerFormasActionPerformed
        this.toolbarHerramientas.setVisible(!this.toolbarHerramientas.isVisible());
    }//GEN-LAST:event_checkVerFormasActionPerformed

    private void checkVerAtributosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkVerAtributosActionPerformed
        this.toolbarPropiedades.setVisible(!this.toolbarPropiedades.isVisible());
    }//GEN-LAST:event_checkVerAtributosActionPerformed

    private void menuCambiarTamanioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCambiarTamanioActionPerformed
        this.vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if(vi!=null  && vi.getLienzo2D()!=null){
            if(vi.esImagen()){
                VentanaDialogoTamanio vdt = new VentanaDialogoTamanio(this,true,(VentanaInternaImagen)vi);
                // Para centrar la imagen
                vdt.setLocationRelativeTo(null);
                vdt.setTitle("Editar tamaño de la imagen");
                vdt.setVisible(true);
            }
        }
    }//GEN-LAST:event_menuCambiarTamanioActionPerformed

    private void comboBoxColoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxColoresActionPerformed
        this.vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if(vi!=null  && vi.getLienzo2D()!=null){
            Color color = (Color)this.comboBoxColores.getSelectedItem();
            this.vi.getLienzo2D().setColor(color);
            this.repaint();
        }
    }//GEN-LAST:event_comboBoxColoresActionPerformed

    private void menuRescaleOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRescaleOpActionPerformed
        this.vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null  && vi.getLienzo2D()!=null) {
            BufferedImage imgSource = vi.getImage();
            if (imgSource != null) {
                try {
                    RescaleOp rop = new RescaleOp(1.0F, 100.0F, null);
                    rop.filter(imgSource, imgSource);
                    vi.getLienzo2D().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_menuRescaleOpActionPerformed

    private void menuConvolveOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuConvolveOpActionPerformed

          this.vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null  && vi.getLienzo2D()!=null) {
            BufferedImage imgSource = vi.getImage();
            // Defino mi matriz, emborronado normal
            float m1[]={0.1f,
                        0.1f,
                        0.1f,
                        0.1f,
                        0.2f,
                        0.1f,
                        0.1f,
                        0.1f,
                        0.1f
            };
            // Matriz 2, lapaciano
            float m2[]={1.0f,
                        1.0f,
                        1.0f,
                        1.0f,
                        -8.0f,
                        1.0f,
                        1.0f,
                        1.0f,
                        1.0f
            };
            // Matriz 3, aleatorio de 4 x 4
            float m3[]={5.0f,
                        -7.5f,
                        3.5f,
                        1.2f,
                        -2.5f,
                        3.5f,
                        4.0f,
                        -2.5f,
                        7.0f,
                        9.0f,
                        3.0f,
                        -9.5f,
                        4.5f,
                        1.5f,
                        -10.0f,
                        2.7f,
            };
            // Defino mi kernel
            Kernel k = new Kernel(4,4,m3); // la tercera es 4x4
            if (imgSource != null) {
                try {
                    ConvolveOp cop = new ConvolveOp(k);
                    BufferedImage salida = cop.filter(imgSource, null);
                    vi.setImage(salida);
                    vi.getLienzo2D().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }      
        
        
    }//GEN-LAST:event_menuConvolveOpActionPerformed

    private void sliderBrilloStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderBrilloStateChanged
        int valor = this.sliderBrillo.getValue();
        this.vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D()!=null) {
            BufferedImage imgSource = vi.getImage();
            if (imgSource != null) {
                try {
                    RescaleOp rop = new RescaleOp(1.0F, valor, null);
                    rop.filter(imgOriginal, imgSource);
                    //vi.getLienzo2D().repaint();
                    // Ahora hará un repaint de todas las ventanas del escritorio al añadir el raster
                    escritorio.repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_sliderBrilloStateChanged

    private void sliderBrilloFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderBrilloFocusGained
        vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D()!=null) {
            //ColorModel cm = vi.getLienzo2D().getImage().getColorModel();
            //WritableRaster raster = vi.getLienzo2D().getImage().copyData(null);
            //boolean alfaPre = vi.getLienzo2D().getImage().isAlphaPremultiplied();
            //imgOriginal = new BufferedImage(cm, raster, alfaPre, null);
            
            // Voy a definir dicha funcion en mis funciones propias
            BufferedImage mi_imagen = vi.getImage();
            imgOriginal = RFuncionesPropias.copiaImagen(mi_imagen);
        }
    }//GEN-LAST:event_sliderBrilloFocusGained

    private void sliderBrilloFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderBrilloFocusLost
        imgOriginal=null;
    }//GEN-LAST:event_sliderBrilloFocusLost

    private void comboBoxFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxFiltrosActionPerformed
        this.vi = (VentanaInternaSM) escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D()!=null) {
            BufferedImage imgSource = vi.getImage();
            int opcion = this.comboBoxFiltros.getSelectedIndex();
            int filtro = 0;
            if (opcion == 0) {
                filtro = KernelProducer.TYPE_MEDIA_3x3;
            } else if (opcion == 1) {
                filtro = KernelProducer.TYPE_BINOMIAL_3x3;
            } else if (opcion == 2) {
                filtro = KernelProducer.TYPE_ENFOQUE_3x3;
            } else if (opcion == 3) {
                filtro = KernelProducer.TYPE_RELIEVE_3x3;
            } else if (opcion == 4) {
                filtro = KernelProducer.TYPE_LAPLACIANA_3x3;
            }
            Kernel k = KernelProducer.createKernel(filtro);
            ConvolveOp cop = new ConvolveOp(k, ConvolveOp.EDGE_NO_OP, null);

            if (imgSource != null) {
                try {
                    ;
                    BufferedImage salida = cop.filter(imgSource, null);
                    vi.setImage(salida);
                    vi.getLienzo2D().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_comboBoxFiltrosActionPerformed

    private void menuLookupOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuLookupOpActionPerformed
        this.vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D()!=null) {
            BufferedImage imgSource = vi.getImage();
            if (imgSource != null) {
                LookupTable lt;
                lt=LookupTableProducer.sFuction(128.0,3.0);
                LookupOp op;
                op = new LookupOp(lt,null);
                op.filter(imgSource, imgSource);
                //vi.getLienzo2D().setImage(salida);
                vi.getLienzo2D().repaint();
            }
        }
    }//GEN-LAST:event_menuLookupOpActionPerformed

    private void menuAffineTransformOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAffineTransformOpActionPerformed
        this.vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D()!=null) {
            BufferedImage imgSource = vi.getImage();
            if (imgSource != null) {
                AffineTransform at;
                //at = AffineTransform.getRotateInstance(Math.toRadians(45.0));
                at = AffineTransform.getScaleInstance(1.25,1.25);
                AffineTransformOp op;
                op = new AffineTransformOp(at,AffineTransformOp.TYPE_BILINEAR);
                BufferedImage salida = op.filter(imgSource, null);
                vi.setImage(salida);
                //vi.getLienzo2D().repaint();
            }
        }
    }//GEN-LAST:event_menuAffineTransformOpActionPerformed

    private void botonSinuidalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSinuidalActionPerformed
        this.vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D()!=null) {
            BufferedImage imgSource = vi.getImage();
            if (imgSource != null) {
                //RFuncionesPropias fp = new RFuncionesPropias();               
                LookupTable lt;
                lt=RFuncionesPropias.seno(180.0/255.0);
                LookupOp op;
                op = new LookupOp(lt,null);
                op.filter(imgSource, imgSource);
                vi.getLienzo2D().repaint();
            }
        }    
    }//GEN-LAST:event_botonSinuidalActionPerformed

    private void botonContrasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonContrasteActionPerformed
        vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null  && vi.getLienzo2D()!=null) {
            BufferedImage imgSource = vi.getImage();
            if (imgSource != null) {
                try {
                    int type = LookupTableProducer.TYPE_SFUNCION; // tipo "S"
                    LookupTable lt = LookupTableProducer.createLookupTable(type);
                    LookupOp lop = new LookupOp(lt, null);
                    // Imagen origen y destino iguales
                    lop.filter(imgSource, imgSource);
                    vi.repaint();
                } catch (Exception e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_botonContrasteActionPerformed

    private void botonIluminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonIluminarActionPerformed
        vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D()!=null) {
            BufferedImage imgSource = vi.getImage();
            if (imgSource != null) {
                try {
                    int type = LookupTableProducer.TYPE_LOGARITHM; // logaritmica
                    LookupTable lt = LookupTableProducer.createLookupTable(type);
                    LookupOp lop = new LookupOp(lt, null);
                    // Imagen origen y destino iguales
                    lop.filter(imgSource, imgSource);
                    vi.repaint();
                } catch (Exception e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_botonIluminarActionPerformed

    private void botonOscurecerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonOscurecerActionPerformed
        vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D()!=null) {
            BufferedImage imgSource = vi.getImage();
            if (imgSource != null) {
                try {
                    int type = LookupTableProducer.TYPE_POWER; // exponencial
                    LookupTable lt = LookupTableProducer.createLookupTable(type);
                    LookupOp lop = new LookupOp(lt, null);
                    // Imagen origen y destino iguales
                    lop.filter(imgSource, imgSource);
                    vi.repaint();
                } catch (Exception e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_botonOscurecerActionPerformed

    private void botonGiro90ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGiro90ActionPerformed
        vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D()!=null) {
            BufferedImage imgSource = vi.getImage();
            if (imgSource != null) {
                try {
                    double r = Math.toRadians(90);
                    Point p = new Point(imgSource.getWidth() / 2, imgSource.getHeight() / 2);
                    AffineTransform at = AffineTransform.getRotateInstance(r, p.x, p.y);
                    AffineTransformOp atop;
                    atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                    BufferedImage imgdest = atop.filter(imgSource, null);
                    vi.setImage(imgdest);
                    vi.repaint(); // NO ES NECESARIO EL GETLIENZO2D? PREGUNTAR
                } catch (Exception e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_botonGiro90ActionPerformed

    private void botonGiro180ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGiro180ActionPerformed
        vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D()!=null) {
            BufferedImage imgSource = vi.getImage();
            if (imgSource != null) {
                try {
                    double r = Math.toRadians(180);
                    Point p = new Point(imgSource.getWidth() / 2, imgSource.getHeight() / 2);
                    AffineTransform at = AffineTransform.getRotateInstance(r, p.x, p.y);
                    AffineTransformOp atop;
                    atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                    BufferedImage imgdest = atop.filter(imgSource, null);
                    vi.setImage(imgdest);
                    vi.repaint(); // NO ES NECESARIO EL GETLIENZO2D? PREGUNTAR
                } catch (Exception e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_botonGiro180ActionPerformed

    private void botonGiro270ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGiro270ActionPerformed
        vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D()!=null) {
            BufferedImage imgSource = vi.getImage();
            if (imgSource != null) {
                try {
                    double r = Math.toRadians(270);
                    Point p = new Point(imgSource.getWidth() / 2, imgSource.getHeight() / 2);
                    AffineTransform at = AffineTransform.getRotateInstance(r, p.x, p.y);
                    AffineTransformOp atop;
                    atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                    BufferedImage imgdest = atop.filter(imgSource, null);
                    vi.setImage(imgdest);
                    vi.repaint(); // NO ES NECESARIO EL GETLIENZO2D? PREGUNTAR
                } catch (Exception e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_botonGiro270ActionPerformed

    private void sliderGiroStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderGiroStateChanged
        int valor = this.sliderGiro.getValue();
        this.vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D()!=null) {
            //BufferedImage imgSource = vi.getLienzo2D().getImage(); EN ESTE CASO OPERAMOS CON LA IMAGEN ORIGINAL, NO LA LOCAL
            if (imgOriginal != null) {
                try {
                    double r = Math.toRadians(valor);
                    Point p = new Point(imgOriginal.getWidth() / 2, imgOriginal.getHeight() / 2);
                    AffineTransform at = AffineTransform.getRotateInstance(r, p.x, p.y);
                    AffineTransformOp atop;
                    atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                    BufferedImage imgdest = atop.filter(imgOriginal, null);
                    vi.setImage(imgdest);
                    vi.repaint(); // NO ES NECESARIO EL GETLIENZO2D? PREGUNTAR
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_sliderGiroStateChanged

    private void sliderGiroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderGiroFocusGained
        vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D()!=null) {
            imgOriginal = RFuncionesPropias.copiaImagen(vi.getImage());
            //ColorModel cm = vi.getLienzo2D().getImage().getColorModel();
            //WritableRaster raster = vi.getLienzo2D().getImage().copyData(null);
            //boolean alfaPre = vi.getLienzo2D().getImage().isAlphaPremultiplied();
            //imgOriginal = new BufferedImage(cm, raster, alfaPre, null);
        }
    }//GEN-LAST:event_sliderGiroFocusGained

    private void sliderGiroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sliderGiroFocusLost
        imgOriginal=null;
    }//GEN-LAST:event_sliderGiroFocusLost

    private void botonEscalaAumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEscalaAumentoActionPerformed
        vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D()!=null) {
            BufferedImage imgSource = vi.getImage();
            if (imgSource != null) {
                try {
                    AffineTransform at = AffineTransform.getScaleInstance(1.25,1.25);
                    AffineTransformOp atop;
                    atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                    BufferedImage imgdest = atop.filter(imgSource, null);
                    vi.setImage(imgdest);
                    vi.repaint(); // NO ES NECESARIO EL GETLIENZO2D? PREGUNTAR
                } catch (Exception e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_botonEscalaAumentoActionPerformed

    private void botonEscalaDescensoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEscalaDescensoActionPerformed
        vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D()!=null) {
            BufferedImage imgSource = vi.getImage();
            if (imgSource != null) {
                try {
                    AffineTransform at = AffineTransform.getScaleInstance(0.75,0.75);
                    AffineTransformOp atop;
                    atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                    BufferedImage imgdest = atop.filter(imgSource, null);
                    vi.setImage(imgdest);
                    vi.repaint(); // NO ES NECESARIO EL GETLIENZO2D? PREGUNTAR
                } catch (Exception e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_botonEscalaDescensoActionPerformed

    private void botonExtraerBandasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonExtraerBandasActionPerformed
        vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D()!=null) {
            BufferedImage imgSource = vi.getImage();
            if (imgSource != null) {
                try {
                    // Necesito en primer lugar el raster de mi imagen
                    WritableRaster srcRaster = imgSource.getRaster();

                    // A partir del Raster, obtengo el numero de bandas, donde hare el bucle for gracias a él
                    for (int i = 0; i < srcRaster.getNumBands(); i++) {
                        //Creamos el modelo de color de la nueva imagen basado en un espcio de color GRAY
                        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
                        ComponentColorModel cm = new ComponentColorModel(cs, false, false,
                                Transparency.OPAQUE,
                                DataBuffer.TYPE_BYTE);
                        //Creamos el nuevo raster a partir del raster de la imagen original
                        int bandList[] = {i};
                        WritableRaster bandRaster = (WritableRaster) imgSource.getRaster().createWritableChild(0, 0,
                                imgSource.getWidth(), imgSource.getHeight(), 0, 0, bandList);
                        //Creamos una nueva imagen que contiene como raster el correspondiente a la banda
                        BufferedImage imgBanda = new BufferedImage(cm, bandRaster, false, null);
                        this.crearNuevaVentana(imgBanda, "Raster ["+i+"]");
                        //VentanaInternaImagen vi_nueva = new VentanaInternaImagen(this);
                        // Al abrir una imagen, modificaremos el tamaño deseado del contorno
                        // por eso he puesto el metodo en setImage
                        //vi_nueva.getLienzo2D().setImage(imgBanda);
                        //this.escritorio.add(vi_nueva);
                        //vi_nueva.setTitle("Raster ["+i+"]");
                        //vi_nueva.setVisible(true);
                    }
                } catch (Exception e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }        
    }//GEN-LAST:event_botonExtraerBandasActionPerformed

    private void comboBoxEspaciosColoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxEspaciosColoresActionPerformed
        this.vi = (VentanaInternaSM) escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D()!=null) {
            BufferedImage imgSource = vi.getImage();
            int opcion = this.comboBoxEspaciosColores.getSelectedIndex();
            if (imgSource != null) {
                //try {
                if (opcion == 0) {
                    if (!imgSource.getColorModel().getColorSpace().isCS_sRGB() && imgSource.getRaster().getNumBands() > 1) {
                        BufferedImage imgOut = RFuncionesPropias.cambioEspacioColor(imgSource, ColorSpace.CS_sRGB);
                        // Y la añadimos en una ventana nueva
                        this.crearNuevaVentana(imgOut, vi.getTitle() + "[RBG]");
                        //VentanaInternaImagen vi_nueva = new VentanaInternaImagen(this);
                        // Al abrir una imagen, modificaremos el tamaño deseado del contorno
                        // por eso he puesto el metodo en setImage
                        //vi_nueva.getLienzo2D().setImage(imgOut);
                        //this.escritorio.add(vi_nueva);
                        //vi_nueva.setTitle(vi.getTitle() + "[RBG]");
                        //vi_nueva.setVisible(true);
                    }
                }
                if (opcion == 1) {
                    if (imgSource.getColorModel().getColorSpace().isCS_sRGB()) {
                        BufferedImage imgOut = RFuncionesPropias.cambioEspacioColor(imgSource, ColorSpace.CS_PYCC);
                        // Y la añadimos en una ventana nueva
                        this.crearNuevaVentana(imgOut, vi.getTitle() + "[YCC]");
                        //VentanaInternaImagen vi_nueva = new VentanaInternaImagen(this);
                        // Al abrir una imagen, modificaremos el tamaño deseado del contorno
                        // por eso he puesto el metodo en setImage
                        //vi_nueva.getLienzo2D().setImage(imgOut);
                        //this.escritorio.add(vi_nueva);
                        //vi_nueva.setTitle(vi.getTitle() + "[YCC]");
                        //vi_nueva.setVisible(true);
                    }
                }
                if (opcion == 2) {
                    if (imgSource.getColorModel().getColorSpace().isCS_sRGB() || imgSource.getRaster().getNumBands() > 1) {
                        BufferedImage imgOut = RFuncionesPropias.cambioEspacioColor(imgSource, ColorSpace.CS_GRAY);
                        // Y la añadimos en una ventana nueva
                        this.crearNuevaVentana(imgOut, vi.getTitle() + "[GREY]");
                        //VentanaInternaImagen vi_nueva = new VentanaInternaImagen(this);
                        // Al abrir una imagen, modificaremos el tamaño deseado del contorno
                        // por eso he puesto el metodo en setImage
                        //vi_nueva.getLienzo2D().setImage(imgOut);
                        //this.escritorio.add(vi_nueva);
                        //vi_nueva.setTitle(vi.getTitle() + "[GREY]");
                        //vi_nueva.setVisible(true);
                    }
                }
                //} catch (IllegalArgumentException e) {
                //    System.err.println(e.getLocalizedMessage());
                //}
            }
        }

    }//GEN-LAST:event_comboBoxEspaciosColoresActionPerformed

    private void botonSepiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSepiaActionPerformed
        vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D()!=null) {
            BufferedImage imgSource = vi.getImage();
            if (imgSource != null) {
                try {
                    RSepiaOp sp = new RSepiaOp();
                    //sp = new sepiaOp(at, AffineTransformOp.TYPE_BILINEAR);
                    sp.filter(imgSource,imgSource);
                    //vi.getLienzo2D().setImage(imgdest);
                    vi.repaint(); // NO ES NECESARIO EL GETLIENZO2D? PREGUNTAR
                } catch (Exception e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_botonSepiaActionPerformed

    private void botonRellenoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRellenoActionPerformed
        this.botonColoresRellenoUno.setEnabled(true);
        this.vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if(vi!=null && vi.getLienzo2D()!=null){
            this.vi.getLienzo2D().setEstaRelleno(!this.vi.getLienzo2D().getEstaRelleno());
            this.repaint();
            this.botonRellenoNormal.setEnabled(true);
            this.botonRellenoHorizontal.setEnabled(true);
            this.botonRellenoVertical.setEnabled(true);
        }
    }//GEN-LAST:event_botonRellenoActionPerformed

    private void botonAlisadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAlisadoActionPerformed
        this.vi = (VentanaInternaSM) escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D() != null) {
            this.vi.getLienzo2D().setEstaAlisado(!this.vi.getLienzo2D().getEstaAlisado());
            this.repaint();
        }
    }//GEN-LAST:event_botonAlisadoActionPerformed

    private void botonTintadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonTintadoActionPerformed
        vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D()!=null) {
            BufferedImage imgSource = vi.getImage();
            if (imgSource != null) {
                try {
                    TintOp tp = new TintOp(vi.getLienzo2D().getColor(),0.5F);
                    tp.filter(imgSource,imgSource);
                    //vi.getLienzo2D().setImage(imgdest);
                    vi.repaint(); // NO ES NECESARIO EL GETLIENZO2D? PREGUNTAR
                } catch (Exception e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_botonTintadoActionPerformed

    private void botonEcualizadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEcualizadorActionPerformed
        vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D()!=null) {
            BufferedImage imgSource = vi.getImage();
            if (imgSource != null) {
                try {
                    EqualizationOp ep = new EqualizationOp();
                    ep.filter(imgSource,imgSource);
                    //vi.getLienzo2D().setImage(imgdest);
                    vi.repaint(); // NO ES NECESARIO EL GETLIENZO2D? PREGUNTAR
                } catch (Exception e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_botonEcualizadorActionPerformed

    private void botonCurvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCurvaActionPerformed
        this.vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D()!=null) {
            this.vi.getLienzo2D().setHerramienta(4);
            this.infoHerramienta.setText("Curva");
            this.vi.getLienzo2D().setEstaEditable(false);
            this.vi.getLienzo2D().quitarBounds();
            this.desactivaBotonesPosicionamiento();
        }
    }//GEN-LAST:event_botonCurvaActionPerformed

    private void botonTrazoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonTrazoActionPerformed
        this.vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D()!=null) {
            this.vi.getLienzo2D().setHerramienta(5);
            this.infoHerramienta.setText("Trazo Libre");
            this.vi.getLienzo2D().setEstaEditable(false);
            this.vi.getLienzo2D().quitarBounds();
            this.desactivaBotonesPosicionamiento();
        }
    }//GEN-LAST:event_botonTrazoActionPerformed

    private void botonPersonalizadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPersonalizadaActionPerformed
        this.vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D()!=null) {
            this.vi.getLienzo2D().setHerramienta(6);
            this.infoHerramienta.setText("Figura Personalizada");
            this.vi.getLienzo2D().setEstaEditable(false);
            this.vi.getLienzo2D().quitarBounds();
            this.desactivaBotonesPosicionamiento();
        }
    }//GEN-LAST:event_botonPersonalizadaActionPerformed

    private void menuAbrirAudioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAbrirAudioActionPerformed
        JFileChooser dlg = new JFileChooser();
        // voy a asegurarme de que no haya filtro "todo tipo de archivo"
        dlg.setAcceptAllFileFilterUsed(false);
        // Creamos el filtro, en este caso, filtro de imagenes
        FileFilter filtro = new FileNameExtensionFilter("Audio (wav, au)",
                "wav","au");
        // Y la añado
        dlg.addChoosableFileFilter(filtro);
        int resp = dlg.showOpenDialog(this);
        if (resp == JFileChooser.APPROVE_OPTION) {
            try {
                // getAbsolutePath o noseque para tener el nombre completo
                //File f = dlg.getSelectedFile();
                File f = new File(dlg.getSelectedFile().getAbsolutePath()){
                  @Override
                  public String toString(){
                      return this.getName();
                  }
                };
                // Añado
                this.listaReproduccion.addItem(f);
                this.listaReproduccion.setSelectedItem(f);
                //BufferedImage img = ImageIO.read(f);
                //VentanaInterna vi = new VentanaInterna(this);
                // Al abrir una imagen, modificaremos el tamaño deseado del contorno
                // por eso he puesto el metodo en setImage
                //vi.getLienzo2D().setImage(img);
                //this.escritorio.add(vi);
                //vi.setTitle(f.getName());
                //vi.setVisible(true);
            } catch (Exception ex) {
                System.err.println("Error al leer el audio");
            }
        }
        labelDuracion.setText("00:00");
    }//GEN-LAST:event_menuAbrirAudioActionPerformed

    private void botonPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPlayActionPerformed
        File f = (File) listaReproduccion.getSelectedItem();
        if (f != null) {
            player = new SMClipPlayer(f);
            ((SMClipPlayer)player).addLineListener(new ManejadorAudio());
            if (player != null) {
                player.play();
            }
        }
    }//GEN-LAST:event_botonPlayActionPerformed

    private void botonStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonStopActionPerformed
        File f = (File) listaReproduccion.getSelectedItem();
        if (f != null) {
            //player = new SMClipPlayer(f);
            if (player != null) {
                player.stop();
                player = null;
            }
            if (recorder !=null ){
                recorder.stop();
                recorder = null;
            }
        }
        // Para el caso del archivo tmp
        if (tmp!=null){
            if(recorder!=null){
                recorder.stop();
                recorder = null;
            }
        }
        labelDuracion.setText("00:00");
    }//GEN-LAST:event_botonStopActionPerformed

    private void botonRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRecordActionPerformed
        // Para que me pregunte el nombre al final, voy a iniciar la grabacion cuando se aprete el boton
        // voy a asignarle un nombre auxiliar para poder guardar el fichero
        //File f=null;
        // Creo un archivo temporal de audio y grabo sobre él
        
        try{
            tmp = File.createTempFile("Fichero Temporal", ".tmp");
        }catch(IOException e){
            e.printStackTrace();
        }
        recorder = new SMSoundRecorder(tmp);
        ((SMSoundRecorder)recorder).addLineListener(new ManejadorAudioRec());
        recorder.record();
        // Tras grabar, abro la ventana
        // HARÉ ESTO EN UNA HEBRA PARA PODER GUARDARLO AL FINAL
//        JFileChooser dlg = new JFileChooser();
//        // voy a asegurarme de que no haya filtro "todo tipo de archivo"
//        dlg.setAcceptAllFileFilterUsed(false);
//        // Creamos el filtro, en este caso, filtro de imagenes
//        FileFilter filtro = new FileNameExtensionFilter("Audio (wav, au)",
//                "wav","au");
//        // Y la añado
//        dlg.addChoosableFileFilter(filtro);
//
//
//
//        int resp = dlg.showOpenDialog(this);
//        if (resp == JFileChooser.APPROVE_OPTION) {
//            try {
//                File aux = new File(dlg.getSelectedFile().getAbsolutePath()){
//                    @Override
//                    public String toString(){
//                        return this.getName();
//                    }
//                };
//                f.renameTo(aux);
//                //File f = new File(dlg.getSelectedFile().getAbsolutePath()){
//                //
//                //  @Override
//                //  public String toString(){
//                //      return this.getName();
//                //  }
//                //};
//                //recorder = new SMSoundRecorder(f);
//                //((SMSoundRecorder)recorder).addLineListener(new ManejadorAudio()); // solo esta para 
//                //((SMSoundRecorder)recorder).addLineListener(new ManejadorAudioRec());
//                //recorder.record();
//                // OJO EL RENAME file.renameTo(otronombre);
//                //System.out.println("GRABANDO....");
//                this.listaReproduccion.addItem(f);
//                this.listaReproduccion.setSelectedItem(f);
//                //if (recorder != null) {
//
//                    //FileFilter filtro = dlg.getFileFilter();
//                //}
//            } catch (Exception ex) {
//                System.err.println("Error al guardar la grabacion");
//            }
//
// }

    }//GEN-LAST:event_botonRecordActionPerformed
    
    private void menuAbrirVideoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAbrirVideoActionPerformed
        JFileChooser dlg = new JFileChooser();
        // voy a asegurarme de que no haya filtro "todo tipo de archivo"
        dlg.setAcceptAllFileFilterUsed(false);
        // Creamos el filtro, en este caso, filtro de video, dos de prueba
        FileFilter filtro = new FileNameExtensionFilter("Video (mp4, mpg, avi)",
                "mp4","mpg","avi"); // mp4 no funciona con JMF
        // Y la añado
        dlg.addChoosableFileFilter(filtro);
        int resp = dlg.showOpenDialog(this);
        if (resp == JFileChooser.APPROVE_OPTION) {
            try {
                File f = dlg.getSelectedFile();
                // Vengana JMF, importante usar sdk de 32 bits
                //VentanaInternaJMFPlayer video = VentanaInternaJMFPlayer.getInstance(f);
                // Ventana VLC, la version debe ser de la misma aquitectura que nuestro pc
                //VentanaInternaVLCPlayer 
                vi = VentanaInternaVLCPlayer.getInstance(f);
                this.escritorio.add(vi);
                vi.setTitle(f.getName());
                vi.setVisible(true);
            } catch (Exception ex) {
                System.err.println("Error al leer el video");
            }
        }
    }//GEN-LAST:event_menuAbrirVideoActionPerformed

    private void botonWebCamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonWebCamActionPerformed
        try {
            vi = VentanaInternaCamara.getInstance();
            this.escritorio.add(vi);
            vi.setTitle("Webcam");
            vi.setVisible(true);
        } catch (Exception ex) {
            System.err.println("Error al abrir la Webcam");
        }
    }//GEN-LAST:event_botonWebCamActionPerformed

    private void botonCapturaImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCapturaImagenActionPerformed
        // Regular el getFrame dependiendo de donde herede
        this.vi = (VentanaInternaSM) escritorio.getSelectedFrame();
        if (this.vi != null) {
            BufferedImage img = this.vi.getSnapshot();
            if(img!=null){
                // Creo una nueva VentanaInterna con dicha imagen
                VentanaInternaImagen vi_nueva = new VentanaInternaImagen(this);
                vi_nueva.getLienzo2D().setImage(img);
                this.escritorio.add(vi_nueva);
                vi_nueva.setTitle("Captura Imagen");
                vi_nueva.setVisible(true);
            }
        }
    }//GEN-LAST:event_botonCapturaImagenActionPerformed

    private void botonLineaContinuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonLineaContinuaActionPerformed
        this.vi = (VentanaInternaSM) escritorio.getSelectedFrame();
        if (vi != null  && vi.getLienzo2D()!=null) {
            this.vi.getLienzo2D().setTipoTrazo(0);
            this.repaint();
        }
    }//GEN-LAST:event_botonLineaContinuaActionPerformed

    private void botonLineaDiscontinuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonLineaDiscontinuaActionPerformed
        this.vi = (VentanaInternaSM) escritorio.getSelectedFrame();
        if (vi != null  && vi.getLienzo2D()!=null) {
            this.vi.getLienzo2D().setTipoTrazo(1);
            this.repaint();
        }
    }//GEN-LAST:event_botonLineaDiscontinuaActionPerformed

    private void sliderTransparenciaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderTransparenciaStateChanged
        this.vi = (VentanaInternaSM) escritorio.getSelectedFrame();
        if (vi != null  && vi.getLienzo2D()!=null) {
            this.vi.getLienzo2D().setTransparencia(this.sliderTransparencia.getValue());
            this.repaint();
        }
    }//GEN-LAST:event_sliderTransparenciaStateChanged

    private void botonRellenoNormalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRellenoNormalActionPerformed
        this.vi = (VentanaInternaSM) escritorio.getSelectedFrame();
        if (vi != null  && vi.getLienzo2D()!=null) {
            this.vi.getLienzo2D().setTipoRelleno(0);
            this.botonColoresRellenoDos.setEnabled(false);
            this.repaint();
        }
    }//GEN-LAST:event_botonRellenoNormalActionPerformed

    private void botonRellenoHorizontalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRellenoHorizontalActionPerformed
        this.vi = (VentanaInternaSM) escritorio.getSelectedFrame();
        if (vi != null  && vi.getLienzo2D()!=null) {
            this.vi.getLienzo2D().setTipoRelleno(1);
            this.botonColoresRellenoDos.setEnabled(true);
            this.repaint();
        }
    }//GEN-LAST:event_botonRellenoHorizontalActionPerformed

    private void botonRellenoVerticalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRellenoVerticalActionPerformed
        this.vi = (VentanaInternaSM) escritorio.getSelectedFrame();
        if (vi != null  && vi.getLienzo2D()!=null) {
            this.vi.getLienzo2D().setTipoRelleno(2);
            this.botonColoresRellenoDos.setEnabled(true);
            this.repaint();
        }
    }//GEN-LAST:event_botonRellenoVerticalActionPerformed

    private void botonDuplicarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonDuplicarImagenActionPerformed
        this.vi = (VentanaInternaSM) escritorio.getSelectedFrame();
        if (vi != null  && vi.getLienzo2D()!=null) {
            imgOriginal = this.vi.getImage(true); // si quiero copiar figuras, debo ponerlo a true
            BufferedImage copiaImagen = RFuncionesPropias.copiaImagen(imgOriginal);
            
            this.crearNuevaVentana(copiaImagen, "Copia de " + vi.getTitle());
            // Todo esto es comun cuando se crea una nueva ventana
            //VentanaInternaImagen vc = new VentanaInternaImagen(this); // la recibe a ella misma
            //vc.setTitle("Copia de " + vi.getTitle());
            //vc.setLocation(getCoordenadaVentana(), getCoordenadaVentana());
            // Tras crearla, aumento mis dos variables
            //aumentarNumeroVentanas();
            //aumentarCoordenadaVentana();
            // Y finalmente, la añado
            //escritorio.add(vc);
            //vc.setVisible(true);
            //vc.getLienzo2D().setImage(copiaImagen); 
            imgOriginal = null;
        }
    }//GEN-LAST:event_botonDuplicarImagenActionPerformed

    private void botonEfectoCalorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEfectoCalorActionPerformed
        vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null  && vi.getLienzo2D()!=null) {
            BufferedImage imgSource = vi.getImage();
            if (imgSource != null) {
                try {
                    RFiltroCalidoOp mf = new RFiltroCalidoOp();
                    //sp = new sepiaOp(at, AffineTransformOp.TYPE_BILINEAR);
                    mf.filter(imgSource,imgSource);
                    //vi.getLienzo2D().setImage(imgdest);
                    vi.repaint(); // NO ES NECESARIO EL GETLIENZO2D? PREGUNTAR
                } catch (Exception e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_botonEfectoCalorActionPerformed

    private void botonEfectoFrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEfectoFrioActionPerformed
        vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null  && vi.getLienzo2D()!=null) {
            BufferedImage imgSource = vi.getImage();
            if (imgSource != null) {
                try {
                    RFiltroFrioOp mf2 = new RFiltroFrioOp();
                    //sp = new sepiaOp(at, AffineTransformOp.TYPE_BILINEAR);
                    mf2.filter(imgSource,imgSource);
                    //vi.getLienzo2D().setImage(imgdest);
                    vi.repaint(); // NO ES NECESARIO EL GETLIENZO2D? PREGUNTAR
                } catch (Exception e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_botonEfectoFrioActionPerformed

    private void botonEfectoTunelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEfectoTunelActionPerformed
        vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null  && vi.getLienzo2D()!=null) {
            BufferedImage imgSource = vi.getImage();
            if (imgSource != null) {
                try {
                    RFiltroEfectoTunelOp mc = new RFiltroEfectoTunelOp();
                    //sp = new sepiaOp(at, AffineTransformOp.TYPE_BILINEAR);
                    mc.filter(imgSource,imgSource);
                    //vi.getLienzo2D().setImage(imgdest);
                    vi.repaint(); // NO ES NECESARIO EL GETLIENZO2D? PREGUNTAR
                } catch (Exception e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_botonEfectoTunelActionPerformed

    private void botonFuncionSenCosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonFuncionSenCosActionPerformed
        this.vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D()!=null) {
            BufferedImage imgSource = vi.getImage();
            if (imgSource != null) {               
                LookupTable lt;
                lt=RFuncionPropiaFinal.funcionPropia(1.0); // valor maximo de la funcion x=1
                LookupOp op;
                op = new LookupOp(lt,null);
                op.filter(imgSource, imgSource);
                vi.getLienzo2D().repaint();
            }
        }  
    }//GEN-LAST:event_botonFuncionSenCosActionPerformed

    private void botonBajarFondoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBajarFondoActionPerformed
        this.vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D()!=null) {
            vi.getLienzo2D().cambiarPosicion(0); 
            vi.actualizarVentanaPrincipal();
        }         
    }//GEN-LAST:event_botonBajarFondoActionPerformed

    private void botonBajarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBajarActionPerformed
        this.vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D()!=null) {
            vi.getLienzo2D().cambiarPosicion(1);
            vi.actualizarVentanaPrincipal();
        }  
    }//GEN-LAST:event_botonBajarActionPerformed

    private void botonSubirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSubirActionPerformed
        this.vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D()!=null) {
            vi.getLienzo2D().cambiarPosicion(2);
            vi.actualizarVentanaPrincipal();
        }  
    }//GEN-LAST:event_botonSubirActionPerformed

    private void botonSubirTopeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSubirTopeActionPerformed
        this.vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D()!=null ) {
            vi.getLienzo2D().cambiarPosicion(3);
            vi.actualizarVentanaPrincipal();
        }  
    }//GEN-LAST:event_botonSubirTopeActionPerformed

    private void botonDetectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonDetectionActionPerformed
        if (this.botonDetection.isSelected()) {
            this.vi = (VentanaInternaSM) escritorio.getSelectedFrame();
            if (vi != null && vi.getCamera() != null) {
                //detector = new WebcamMotionDetector(Webcam.getDefault());
                detector = new WebcamMotionDetector(vi.getCamera());
                detector.setInterval(1000); // comprueba si hay movimiento cada 5 segundos
                //detector.setPixelThreshold(150);
                detector.addMotionListener(new ManejadorDetectorMovimiento());
                detectorEncendido = true;
                detector.start();
            }
        }
        else{
            detectorEncendido = false;
        }
    }//GEN-LAST:event_botonDetectionActionPerformed

    private void botonElegirMasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonElegirMasActionPerformed
        this.vi = (VentanaInternaSM) escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D() != null) {
            if (this.botonElegirMas.isSelected()) {
                this.botonOtrosColores.setEnabled(true);
                this.comboBoxColores.setEnabled(false);
                this.vi.getLienzo2D().setColor(this.botonOtrosColores.getBackground());
            } else {
                this.botonOtrosColores.setEnabled(false);
                this.comboBoxColores.setEnabled(true);
                this.comboBoxColoresActionPerformed(evt);
            }
            this.vi.repaint();
        }
    }//GEN-LAST:event_botonElegirMasActionPerformed

    private void botonOtrosColoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonOtrosColoresActionPerformed
        this.vi = (VentanaInternaSM) escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D() != null) {
            Color nuevo = JColorChooser.showDialog(
                    null,
                    "Elige el nuevo color del trazo",
                    this.botonOtrosColores.getBackground());

            this.vi.getLienzo2D().setColor(nuevo);
            this.botonOtrosColores.setBackground(nuevo);
            this.repaint();
        }
    }//GEN-LAST:event_botonOtrosColoresActionPerformed

    private void botonColoresRellenoUnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonColoresRellenoUnoActionPerformed
        this.vi = (VentanaInternaSM) escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D() != null) {
            Color nuevo = JColorChooser.showDialog(
                    null,
                    "Elige el nuevo color de relleno",
                    this.botonColoresRellenoUno.getBackground());

            this.vi.getLienzo2D().setColorRelleno(nuevo);
            this.botonColoresRellenoUno.setBackground(nuevo);
            this.repaint();
        }
    }//GEN-LAST:event_botonColoresRellenoUnoActionPerformed

    private void botonColoresRellenoDosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonColoresRellenoDosActionPerformed
        this.vi = (VentanaInternaSM) escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D() != null) {
            Color nuevo = JColorChooser.showDialog(
                    null,
                    "Elige el nuevo color de relleno secundario",
                    this.botonColoresRellenoDos.getBackground());

            this.vi.getLienzo2D().setColorRellenoDos(nuevo);
            this.botonColoresRellenoDos.setBackground(nuevo);
            this.repaint();
        }
    }//GEN-LAST:event_botonColoresRellenoDosActionPerformed

    private void botonInvertirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonInvertirActionPerformed
        this.vi = (VentanaInternaSM)escritorio.getSelectedFrame();
        if (vi != null && vi.getLienzo2D()!=null) {
            BufferedImage imgSource = vi.getImage();
            if (imgSource != null) {             
                LookupTable lt;
                lt=RFuncionesPropias.invertir();
                LookupOp op;
                op = new LookupOp(lt,null);
                op.filter(imgSource, imgSource);
                vi.getLienzo2D().repaint();
            }
        }  
    }//GEN-LAST:event_botonInvertirActionPerformed

    private void menuSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSobreActionPerformed
        VentanaDialogoAbout vab = new VentanaDialogoAbout(this, true);
        vab.setLocationRelativeTo(null);
        vab.setVisible(true);
    }//GEN-LAST:event_menuSobreActionPerformed

    /* Metodos para crear una nueva ventana de Imagen con la imagen y el titulo deseado,
    Uno de ellos recibirá por parámetro la ubicación. Dicho método solo se usa para 
    capturar cuando esté el modo de deteccion de movimiento activado.
    */
    
    private void crearNuevaVentana(BufferedImage imgEntrada, String texto){
        VentanaInternaImagen vn = new VentanaInternaImagen(getVentanaPrincipal());
        aumentarNumeroVentanas();
        aumentarCoordenadaVentana();
        vn.setImage(imgEntrada);
        vn.setTitle(texto);
        vn.setLocation(cord, cord);
        escritorio.add(vn);
        vn.setVisible(true);
    }
    
    private void crearNuevaVentana(BufferedImage imgEntrada, String texto, int x, int y) {
        VentanaInternaImagen vn = new VentanaInternaImagen(getVentanaPrincipal());
        vn.setImage(imgEntrada);
        vn.setTitle(texto);
        vn.setLocation(x, y);
        //aumentarCoordenadaDectec();
        //aumentarNumeroVentanas();
        //aumentarCoordenadaVentana();
        
        vn.setVisible(true);
        escritorio.add(vn); // la añado despues, para que no se hagan capturas de ventanas de imagen
    }
    
    /* Metodo utilizado para desactivar todos los botones de posicionamiento a
    la vez.
    */
    
    private void desactivaBotonesPosicionamiento(){
        this.botonBajarFondo.setEnabled(false);
        this.botonBajar.setEnabled(false);
        this.botonSubir.setEnabled(false);
        this.botonSubirTope.setEnabled(false);
    }
    
    ///////////////////////// CLASES INTERNAS PARA GESTIONAR EVENTOS PROPIOS /////////////////////
    
    
    
    // Clase interna para la gestion de eventos de Audio
    class ManejadorAudio implements LineListener {
        @Override
        public void update(LineEvent event) {
            if (event.getType() == LineEvent.Type.START) {
                botonPlay.setEnabled (false);
                contador();
                empezar=true;
                t.start();
            }
            if (event.getType() == LineEvent.Type.STOP) {
                botonPlay.setEnabled (true);
                empezar=false;
                labelDuracion.setText("00:00");
            }
            if (event.getType() == LineEvent.Type.CLOSE) {
            }
        }
    }
    
    // Manejador de Grabación
    class ManejadorAudioRec implements LineListener {

        @Override
        public void update(LineEvent event) {
            if (event.getType() == LineEvent.Type.START) {
                botonRecord.setEnabled(false);
                contador();
                empezar=true;
                t.start();
            }
            if (event.getType() == LineEvent.Type.STOP) {
                botonRecord.setEnabled(true);
                empezar=false;
                menuGrabacion(tmp);
                
            }
            if (event.getType() == LineEvent.Type.CLOSE) {
            }
        }
    }
    
    // Manejador para la Detección de movimiento
    class ManejadorDetectorMovimiento implements WebcamMotionListener {

        @Override
        public void motionDetected(WebcamMotionEvent wme) {
            if (vi != null && detectorEncendido) {
                System.out.println("ALARMA, ALARMA");
                BufferedImage snapshot = vi.getSnapshot();
                crearNuevaVentana(snapshot, "ALARMA - MOVIMIENTO DETECTADO",decX,decY);
                aumentarCoordenadaDectec();
            }
        }
    }
    
    //////////////////////////// FIN MANEJADORES PROPIOS //////////////////////////////////////
    
    /* Llamaré a este método cuando quiera guardar mi file. Como está diseñado
    para que se guarde DESPUÉS de parar la grabación, se encargará de recibir
    el archivo temporal F y guardarlo, ejecutando así el proceso de abrir
    archivo, etc.
    */
    
    private void menuGrabacion(File f){
        JFileChooser dlg = new JFileChooser();
        // voy a asegurarme de que no haya filtro "todo tipo de archivo"
        dlg.setAcceptAllFileFilterUsed(false);
        // Creamos el filtro, en este caso, filtro de imagenes
        FileFilter filtro = new FileNameExtensionFilter("Audio (wav, au)",
                "wav", "au");
        // Y la añado
        dlg.addChoosableFileFilter(filtro);

        int resp = dlg.showOpenDialog(this);
        if (resp == JFileChooser.APPROVE_OPTION) {
            try {
                File aux = new File(dlg.getSelectedFile().getAbsolutePath()) {
                    @Override
                    public String toString() {
                        return this.getName();
                    }
                };
                f.renameTo(aux);
                //File f = new File(dlg.getSelectedFile().getAbsolutePath()){
                //
                //  @Override
                //  public String toString(){
                //      return this.getName();
                //  }
                //};
                //recorder = new SMSoundRecorder(f);
                //((SMSoundRecorder)recorder).addLineListener(new ManejadorAudio()); // solo esta para 
                //((SMSoundRecorder)recorder).addLineListener(new ManejadorAudioRec());
                //recorder.record();
                // OJO EL RENAME file.renameTo(otronombre);
                //System.out.println("GRABANDO....");
                this.listaReproduccion.addItem(aux);
                this.listaReproduccion.setSelectedItem(aux);
                //if (recorder != null) {

                //FileFilter filtro = dlg.getFileFilter();
                //}
            } catch (Exception ex) {
                System.err.println("Error al guardar la grabacion");
            }

        }
    }

    /* Método creado para contar los segundos. Se encarga de crear el 
    thread e ir contando los segundos, actualizando para ello JLabel. Cuando
    se invoque al botón parar, dicha hebra se destruirá.
    */
    
    public void contador() {
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                cantidad = 0;
                cantidadSegundos = 0;
                cantidadMinutos = 0;
                while (empezar) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                    }
                    if (cantidad >= 100) {
                        cantidadSegundos++;
                        cantidad = 0;
                    }
                    if (cantidadSegundos >= 60) {
                        cantidadMinutos++;
                        cantidadSegundos = 0;
                    }
                    cantidad++;
                    if (cantidadSegundos < 10 && cantidadMinutos < 10) {
                        labelDuracion.setText("0" + cantidadMinutos + ":" + "0" + cantidadSegundos);
                    } else if (cantidadSegundos < 10) {
                        labelDuracion.setText(cantidadMinutos + ":" + "0" + cantidadSegundos);
                    } else if (cantidadMinutos < 10) {
                        labelDuracion.setText("0" + cantidadMinutos + ":" + cantidadSegundos);
                    }
                    //System.out.println(cantidad);
                }
                labelDuracion.setText("00:00");
            }
        });;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar barraMenu;
    private javax.swing.JButton botonAbrir;
    private javax.swing.JToggleButton botonAlisado;
    private javax.swing.JButton botonBajar;
    private javax.swing.JButton botonBajarFondo;
    private javax.swing.JButton botonCapturaImagen;
    private javax.swing.JButton botonColoresRellenoDos;
    private javax.swing.JButton botonColoresRellenoUno;
    private javax.swing.JButton botonContraste;
    private javax.swing.JToggleButton botonCurva;
    private javax.swing.JToggleButton botonDetection;
    private javax.swing.JButton botonDuplicarImagen;
    private javax.swing.JButton botonEcualizador;
    private javax.swing.JToggleButton botonEditar;
    private javax.swing.JButton botonEfectoCalor;
    private javax.swing.JButton botonEfectoFrio;
    private javax.swing.JButton botonEfectoTunel;
    private javax.swing.JToggleButton botonElegirMas;
    private javax.swing.JButton botonEscalaAumento;
    private javax.swing.JButton botonEscalaDescenso;
    private javax.swing.JButton botonExtraerBandas;
    private javax.swing.JButton botonFuncionSenCos;
    private javax.swing.JButton botonGiro180;
    private javax.swing.JButton botonGiro270;
    private javax.swing.JButton botonGiro90;
    private javax.swing.JButton botonGuardar;
    private javax.swing.JButton botonIluminar;
    private javax.swing.JButton botonInvertir;
    private javax.swing.JToggleButton botonLinea;
    private javax.swing.JToggleButton botonLineaContinua;
    private javax.swing.JToggleButton botonLineaDiscontinua;
    private javax.swing.JButton botonNuevo;
    private javax.swing.JButton botonOscurecer;
    private javax.swing.JMenu botonOtros;
    private javax.swing.JButton botonOtrosColores;
    private javax.swing.JToggleButton botonOvalo;
    private javax.swing.JToggleButton botonPersonalizada;
    private javax.swing.JButton botonPlay;
    private javax.swing.JButton botonRecord;
    private javax.swing.JToggleButton botonRectangulo;
    private javax.swing.JToggleButton botonRelleno;
    private javax.swing.JToggleButton botonRellenoHorizontal;
    private javax.swing.JToggleButton botonRellenoNormal;
    private javax.swing.JToggleButton botonRellenoVertical;
    private javax.swing.JButton botonSepia;
    private javax.swing.JButton botonSinuidal;
    private javax.swing.JButton botonStop;
    private javax.swing.JButton botonSubir;
    private javax.swing.JButton botonSubirTope;
    private javax.swing.JButton botonTintado;
    private javax.swing.JToggleButton botonTrazo;
    private javax.swing.JButton botonWebCam;
    private javax.swing.JSpinner cambioGrosor;
    private javax.swing.JCheckBoxMenuItem checkVerAtributos;
    private javax.swing.JCheckBoxMenuItem checkVerEstado;
    private javax.swing.JCheckBoxMenuItem checkVerFormas;
    private javax.swing.JComboBox<String> comboBoxColores;
    private javax.swing.JComboBox<String> comboBoxEspaciosColores;
    private javax.swing.JComboBox<String> comboBoxFiltros;
    private javax.swing.JDesktopPane escritorio;
    private javax.swing.ButtonGroup grupoColores;
    private javax.swing.ButtonGroup grupoDegradado;
    private javax.swing.ButtonGroup grupoHerramientas;
    private javax.swing.ButtonGroup grupoTrazo;
    private javax.swing.JLabel infoCoordenada;
    private javax.swing.JLabel infoHerramienta;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JToolBar.Separator jSeparator8;
    private javax.swing.JLabel labelDuracion;
    private javax.swing.JComboBox<File> listaReproduccion;
    private javax.swing.JMenuItem menuAbrir;
    private javax.swing.JMenuItem menuAbrirAudio;
    private javax.swing.JMenuItem menuAbrirVideo;
    private javax.swing.JMenuItem menuAffineTransformOp;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenuItem menuBandCombineOp;
    private javax.swing.JMenuItem menuCambiarTamanio;
    private javax.swing.JMenuItem menuColorConvertOp;
    private javax.swing.JMenuItem menuConvolveOp;
    private javax.swing.JMenu menuFiltrosClase;
    private javax.swing.JMenuItem menuGuardar;
    private javax.swing.JMenu menuImagen;
    private javax.swing.JMenuItem menuLookupOp;
    private javax.swing.JMenuItem menuNuevo;
    private javax.swing.JMenuItem menuRescaleOp;
    private javax.swing.JMenuItem menuSobre;
    private javax.swing.JMenu menuVer;
    private javax.swing.JPanel panelBrillo;
    private javax.swing.JPanel panelColoresOpciones;
    private javax.swing.JPanel panelColoresRelleno;
    private javax.swing.JPanel panelContraste;
    private javax.swing.JPanel panelDuplicado;
    private javax.swing.JPanel panelFiltro;
    private javax.swing.JPanel panelGiro;
    private javax.swing.JPanel panelHerramienta;
    private javax.swing.JPanel panelImagen;
    private javax.swing.JPanel panelInferior;
    private javax.swing.JPanel panelOtrosColores;
    private javax.swing.JPanel panelSeno;
    private javax.swing.JPanel panelSuperior;
    private javax.swing.JPanel panelZoom;
    private javax.swing.JToolBar.Separator separadorH;
    private javax.swing.JPopupMenu.Separator separadorMenuOtros;
    private javax.swing.JToolBar.Separator separadorP1;
    private javax.swing.JToolBar.Separator separadorP2;
    private javax.swing.JToolBar.Separator separadorP3;
    private javax.swing.JToolBar.Separator separadorP4;
    private javax.swing.JToolBar.Separator separadorPos;
    private javax.swing.JSlider sliderBrillo;
    private javax.swing.JSlider sliderGiro;
    private javax.swing.JSlider sliderTransparencia;
    private javax.swing.JToolBar toolbarHerramientas;
    private javax.swing.JToolBar toolbarImagen;
    private javax.swing.JToolBar toolbarOpciones;
    private javax.swing.JToolBar toolbarPosicion;
    private javax.swing.JToolBar toolbarPropiedades;
    private javax.swing.JToolBar toolbarSonido;
    private javax.swing.JToolBar toolbarVideo;
    // End of variables declaration//GEN-END:variables
}
