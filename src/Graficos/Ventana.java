package Graficos;

import static Juego.Casella.LADO_CASILLA;
import javax.swing.JFrame;

////////////////////////////////////////////////////////////////////////////////
//  La clase Ventana representa precisamente la ventana sobre la que se ejecutará
//  el juego. Nuestra ventana únicamente contiene un panel, dónde sí sucederán
//  todas las acciones necesarias para ir jugando, y el entero MARGEN_MENU, que
//  es el tamaño del menú en el que pondremos las opciones para modificar el juego
//  (es necesario para establecer las dimensiones de la ventana).
public class Ventana extends JFrame {

    //  Atributos.
    private final Panel panel;
    private final int MARGEN_MENU = 44;

    public Ventana() {

        //  Título de la ventana.
        this.setTitle("BUSCAMINAS PRO");   

        //  Creamos el panel que contendrá el juego.
        panel = new Panel();

        //  Ponemos el tamaño de la ventana relativo a las casillas que contenga
        //  el tablero.
        this.setTamaño(panel.getCasillas());
        
        //  Al cerrar la ventana queremos que se acabe el programa.
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);    
        
        //  Tamaño invariable (ya lo controlamos nosotros).
        this.setResizable(false);       
        
        //  La ventana aparece en el centro.
        this.setLocationRelativeTo(null);
        
        //  Añadimos el menú que creamos en panel a la ventana.
        this.setJMenuBar(panel.getMenu());
        
        //  Y ahora añadimos el propio panel a la misma.
        this.add(panel);

        //  Para que aparezca la ventana.
        this.setVisible(true);                           
    }

    //  Cuando cambiemos a un modo de dificultad diferente (cuyas dimensiones del
    //  tablero serán diferentes), también tendremos que modificar las dimensiones
    //  de nuestra ventana. Este setter nos permitirá reajustar el tamaño.
    public void setTamaño(int casillas) {
        this.setSize(casillas * LADO_CASILLA, MARGEN_MENU + casillas * LADO_CASILLA);
    }

}
