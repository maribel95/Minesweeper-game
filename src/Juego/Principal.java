package Juego;

import Graficos.Ventana;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

////////////////////////////////////////////////////////////////////////////////
//  La clase principal contiene el main y además contiene los dos posibles modos
//  de funcionamiento del programa. Como me recomendó el profe, primero
//  implementamos el juego para poder ser jugado desde la consola, y una vez 
//  funcionaba hicimos el apartado de gráficos. Cabe decir que el modo consola
//  no implementa la mecánica de marcar casillas, de guardar/cargar partida ni
//  la selección de diferentes modos de dificultad. Hemos decidido dejar el modo
//  consola porque no representaba ninguna molestia.
public class Principal {

    //  Cuando cambiemos de dificultad podríamos necesitar modificar el tamaño
    //  de la ventana desde otra clase, por lo que debe ser static.
    public static Ventana v;

    //  Podemos jugar el modo que queramos simplemente descomentándolo.
    public static void main(String[] args) {

        new Principal().ModoVentana();
//        new Principal().ModoConsola();
    }

    //  Mejor encapsulado
    private void ModoVentana() {

        v = new Ventana();
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////    MODO CONSOLA    ////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    //  Para el modo consola (utilizado para comprobar el correcto funcionamiento
    //  del juego sin tener que preocuparnos por los gráficos), hemos diseñado
    //  este método, que sigue el curso de una partida normal.
    private void ModoConsola() {

        Tauler t = new Tauler();

        while (!t.finPartida()) {

            t.imprime();

            int x = llegirNum("Coordenada x: ");
            int y = llegirNum("Coordenada y: ");

            //  Esto lo utilizamos con tal de que el mapa del buscaminas de la
            //  ronda anterior no se visible en la consola, de modo que no
            //  estorbe en la pantalla.
            System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

            t.casillaClicada(x, y);

        }

        if (t.esVictoria()) {
            System.out.println("SIUU HAS GANADO!!");
        } else {
            System.out.println("NOO HAS PERDIDO...");
        }

        t.revelarTablero();
        t.imprime();
    }

    //  Este método sólo se usa para jugar en el modo consola. Lo único que hace
    //  es leer el entero introducido por consola, con tal de seleccionar la fila
    //  y columna de la casilla que queremos destapar en la partida.
    private static int llegirNum(String t) {
        int x = 0;
        try {
            String s;
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.print(t);
            s = in.readLine();
            x = Integer.parseInt(s);
        } catch (IOException | NumberFormatException e) {
            System.out.println(e);
        }
        return x;
    }
}
