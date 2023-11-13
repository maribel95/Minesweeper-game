package Graficos;

import FicherosES.TaulerIn;
import FicherosES.TaulerOut;
import Juego.Casella;
import static Juego.Casella.LADO_CASILLA;
import static Juego.Principal.v;
import Juego.Tauler;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

////////////////////////////////////////////////////////////////////////////////
//  El Panel implementa el funcionamiento del juego para el modo ventana. Pintaremos
//  el tablero entero sobre el mismo. Los clics y sus consecuencias se implementan en
//  el mismo. Tambien tenemos una serie de elementos pertenecientes a menús para añadir
//  diferentes opciones a la partida. Cada método viene con su explicación.
//
public class Panel extends JPanel implements MouseListener {

    //  Tauler sobre el que operará el propio panel.
    Tauler t;

    //  OPCIONES DE LA VENTANA
    private JMenuBar barra_menu;
    private JMenu menu;
    private JMenuItem reanudar, guardar, reiniciar, facil, intermedio, dificil, salir;

    //  Desactivamos el diseño por defecto del panel y procedemos a inicializar todos
    //  nuestros elementos en initComponents.
    public Panel() {

        this.setLayout(null);
        initComponents();
    }

    //  Instanciamos los componentes del panel.
    public void initComponents() {

        //  Añadimos el mouse listener para poder clicar en el tablero.
        this.addMouseListener(this);

        //  Creamos una partida con el constructor por defecto (tablero 8x8, 10 minas).
        t = new Tauler();
        
        /////   MENUS

        //  Barra del menú.
        barra_menu = new JMenuBar();
        
        //  Menú
        menu = new JMenu("Menu");

        //  Opciones del menú
        reanudar = new JMenuItem("Abrir partida");
        guardar = new JMenuItem("Guardar partida");
        reiniciar = new JMenuItem("Reiniciar");
        facil = new JMenuItem("Modo fácil");
        intermedio = new JMenuItem("Modo intermedio");
        dificil = new JMenuItem("Modo difícil");
        salir = new JMenuItem("Salir");

        //  Añadimos las distintas opciones en el orden que queremos al menú.
        menu.add(reanudar);
        menu.add(guardar);
        menu.add(reiniciar);
        menu.add(facil);
        menu.add(intermedio);
        menu.add(dificil);
        menu.add(salir);

        //  Añadimos el menu a la barra y la barra al panel
        barra_menu.add(menu);
        this.add(barra_menu);
        
        ///////     CLICKS

        //  Desde aquí controlaremos cada opción del menú.
        reanudar.addActionListener((ActionEvent e) -> {

//            Tauler backup = t;
//
//            try {

                JFileChooser j = new JFileChooser();

                j.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int opcion = j.showDialog(null, "Reanudar");

                if (opcion == JFileChooser.APPROVE_OPTION) {
                    String s = j.getSelectedFile().getAbsolutePath();

                    TaulerIn ti = new TaulerIn(s);
                    t = ti.leerTauler();
                    ti.cerrar();

                    optimizarPartida();
                }

//            } catch (HeadlessException ex) {
//                System.out.println("El formato seleccionado es erróneo.");
//                t = backup;
//            } 

        });

        guardar.addActionListener((ActionEvent e) -> {

            JFileChooser j = new JFileChooser();

            int opcion = j.showDialog(null, "Guardar");

            if (opcion == JFileChooser.APPROVE_OPTION) {
                String s = j.getSelectedFile().getAbsolutePath();

                TaulerOut to = new TaulerOut(s);
                to.escribirTauler(t);
                to.cerrar();
            }

        });

        reiniciar.addActionListener((ActionEvent e) -> {

            //  Creamos el nuevo tauler con los mismos atributos del tauler actual.
            t = new Tauler(t.getLongitudCasillas(), t.getMinas());
            repaint();
        });

        facil.addActionListener((ActionEvent e) -> {

            //  La partida con la que empezamos por defecto es en realidad el modo
            //  fácil, de modo que usaremos el constructor por defecto.
            t = new Tauler();
            optimizarPartida();
        });

        intermedio.addActionListener((ActionEvent e) -> {

            //  Un tablero algo más grande, ahora con más minas.
            int dimension = 12;
            int minas = 20;

            t = new Tauler(dimension, minas);
            optimizarPartida();
        });

        dificil.addActionListener((ActionEvent e) -> {

            //  Un tablero algo más grande, ahora con más minas.
            int dimension = 18;
            int minas = 30;

            t = new Tauler(dimension, minas);
            optimizarPartida();
        });

        salir.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

    }

    //  Después de elegir algún modo, o cargar una partida, tendremos que asegurarnos
    //  de que la ventana tenga el tamaño que corresponde al tablero.
    private void optimizarPartida() {

        v.setTamaño(t.getLongitudCasillas());
        v.setLocationRelativeTo(null);
        repaint();
    }

    ////////////////////////////////////////////////////////////////////////////
    //  Según los datos de la casilla a destapar, deberemos cargar una imagen
    //  u otra. Respetamos una jerarquía para no cargar una imágen errónea.
    private String obtenerStringImagen(int x, int y) {

        Casella c = t.getCasella(x, y);
        String s = "iconos/";

        if (c.estaMarcada()) {

            s += "flag";
        } else if (!c.estaDestapada()) {

            s += "niebla";
        } else if (c.hayMina()) {

            s += "mina";
        } else {

            s += c.getNumeroBombas();
        }

        s += ".png";
        return s;
    }

    //  Con el String que obtuvimos del método anterior podremos cargar finalmente
    //  la imagen correspondiente y dibujar cada casilla.
    @Override
    public void paint(Graphics g) {
        try {

            for (int i = 0; i < t.getLongitudCasillas(); i++) {
                for (int j = 0; j < t.getLongitudCasillas(); j++) {

                    //  Analizaremos la casilla y obtendremos el String.
                    String s = obtenerStringImagen(i, j);

                    //  Leemos la imagen.
                    BufferedImage img = ImageIO.read(new File(s));

                    //  Le ajustamos las dimensiones al tamaño de la casilla.
                    img.getScaledInstance(LADO_CASILLA, LADO_CASILLA, Image.SCALE_SMOOTH);

                    //  La dibujamos.
                    g.drawImage(img, i * LADO_CASILLA, j * LADO_CASILLA, null);
                }
            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    //  Obtenemos el número de casillas que tiene un lado del tablero actual.
    public int getCasillas() {
        return t.getLongitudCasillas();
    }

    //  Retornamos el menú para poder pegarlo en la ventana.
    public JMenuBar getMenu() {
        return barra_menu;
    }

    ////////////////////////////////////////////////////////////////////////////
    //  Uso del ratón.
    //  Cuando pulsemos en alguna parte de la ventana obtendremos las coordenadas
    //  x e y del sitio donde pulsamos. Con un sencillo cálculo sabremos la fila
    //  y la columna de la casilla que hemos pulsado (esto es así porque hemos
    //  implementado el tablero pintando las casillas, no con botones reales).
    @Override
    public void mousePressed(MouseEvent me) {

        //  Obtenemos la fila (x) y columna (y).
        int x = me.getX() / LADO_CASILLA;
        int y = me.getY() / LADO_CASILLA;

        //  Si la casilla ya está destapada no hay que hacer ninguna acción.
        if (!t.getCasella(x, y).estaDestapada()) {

            //  Necesitamos diferenciar la acción de destapar casilla de la de marcar
            //  casilla.
            //
            //  Click izquierdo = destapar casilla (BOTON 1).
            if (me.getButton() == MouseEvent.BUTTON1) {

                //  Clicamos la casilla.
                t.casillaClicada(x, y);

                //  Comprobamos si es fin de partida.
                if (t.finPartida()) {
                    
                    //  Si ha acabado, revelamos todas las casillas y las repintamos.
                    t.revelarTablero();
                    repaint();
                    
                    //  Según hayamos ganado o no, nos aparecerá un mensaje diferente.
                    if (t.esVictoria()) {

                        JOptionPane.showMessageDialog(null, "SIUU HAS GANADO!!");
                    } else {

                        JOptionPane.showMessageDialog(null, "NOO HAS PERDIDO...");
                    }
                }

            }
            //  Click derecho = marcar/desmarcar casilla (BOTON 3).
            else if (me.getButton() == MouseEvent.BUTTON3) {

                //  Dentro del método regulamos que se respeten las normas de que
                //  no haya más casillas marcadas que minas totales.
                t.casillaMarcada(x, y);
            }

            repaint();
        }

    }

    ////////////////////////////////////////////////////////////////////////////
    //  El resto de funciones de mouselistener no las necesitamos y por tanto
    //  las dejaremos vacías.
    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
}
