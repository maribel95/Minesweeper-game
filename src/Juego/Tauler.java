package Juego;

import java.io.Serializable;
import java.util.Random;

////////////////////////////////////////////////////////////////////////////////
//  La clase Tauler controla todo el funcionamiento del juego. Absolutamente todos
//  los datos de la partida los contiene el objeto Tauler que vayamos controlando 
//  a lo largo de la partida, de modo que cambiar de partida o generar partidas
//  nuevas será una tarea muy sencilla. Cada método está comentado con su explicación.
//
public class Tauler implements Serializable {

    //  Atributos de un Tauler.
    private final Casella[][] t;
    private final int longitud_casillas;
    private final int minas_totales;
    private int casillas_marcadas;
    private boolean victoria;

    //  Nuestro constructor por defecto crea una partida con un tablero de
    //  8x8 con 10 minas (tal y cómo indica el enunciado de la práctica).
    //  Para otros modos de dificultad, se utiliza el constructor con parámetros
    //  que hay a continuación.
    public Tauler() {

        longitud_casillas = 8;
        minas_totales = 10;
        casillas_marcadas = 0;

        //  Inicializamos el array de casillas.
        t = new Casella[longitud_casillas][longitud_casillas];

        //  Inicializaremos cada casilla.
        iniciarCasillas();

        //  Colocaremos las minas aleatoriamente (y además incrementaremos el
        //  contador de minas cercanas de las casillas vecinas en 1 por mina).
        colocarMinas();
    }

    //  Es lo mismo que el constructor anterior, pero cuando queramos crear una
    //  partida más difícil (o simplemente con diferentes características), usaremos
    //  este constructor para modificar el número de casillas de cada lado y el
    //  número total de minas.
    public Tauler(int casillas, int minas) {

        longitud_casillas = casillas;
        minas_totales = minas;
        casillas_marcadas = 0;

        t = new Casella[longitud_casillas][longitud_casillas];

        iniciarCasillas();
        colocarMinas();
    }

    //  Inicializamos todas las casillas del array del tablero a por defecto.
    private void iniciarCasillas() {

        for (int i = 0; i < longitud_casillas; i++) {
            for (int j = 0; j < longitud_casillas; j++) {
                t[i][j] = new Casella();
            }
        }
    }

    //  Método que coloca el número de minas que indica el propio tablero.
    private void colocarMinas() {

        //  Empezamos con 0 minas.
        int minas_colocadas = 0;
        Random rnd = new Random();

        //  Hasta que no haya el número de minas que toca no dejaremos de intentar
        //  colocar minas.
        while (minas_colocadas < minas_totales) {

            //  Elegimos unas coordenadas x e y (una casilla) aleatoria para 
            //  colocar una mina.
            int x = rnd.nextInt(longitud_casillas);
            int y = rnd.nextInt(longitud_casillas);

            //  Si no hay una mina en la posición, la colocaremos.
            //
            //  Si SI había una mina, no contará para el límite y seguiremos 
            //  generando coordenadas aleatorias.
            if (!t[x][y].hayMina()) {

                t[x][y].ponMina();
                minas_colocadas++;

                //  Las casillas cercanas deberán indicar que tienen otra mina
                //  en sus alrededores.
                incrementarContadorDeCasillasCercanas(x, y);
            }
        }
    }

    //  Comprobaremos cada casilla de alrededor de nuestra mina,
    //  para aumentarles en 1 el contador de minas cercanas. Empezaremos
    //  desde la posición de arriba a la izquierda, y rotaremos hacia la derecha
    //  completando un círculo.
    //
    //  Hay que comprobar si la casilla está en alguno de los límites del array
    //  bidimensional de casillas, porque hay que tener en cuenta que no podemos
    //  acceder a posiciones menores al cero ni mayores del límite del array.
    private void incrementarContadorDeCasillasCercanas(int x, int y) {

        int borde = longitud_casillas - 1;

        //  x=0 representa el borde izquierdo
        //  y=0 representa el borde superior
        //  x = limite-1 representa el borde derecho
        //  y = limite-1 representa el borde inferior.
        //  Arriba izquierda.
        if ((x != 0) && (y != 0)) {

            //  Simplemente le sumaremos 1 al contador de minas cercanas de la
            //  casilla.
            t[x - 1][y - 1].incrementarContadorMina();
        }

        //  Arriba.
        if (y != 0) {
            t[x][y - 1].incrementarContadorMina();
        }

        //  Arriba derecha.
        if ((x != borde) && (y != 0)) {
            t[x + 1][y - 1].incrementarContadorMina();
        }

        //  Derecha.
        if (x != borde) {
            t[x + 1][y].incrementarContadorMina();
        }

        //  Abajo derecha.
        if ((x != borde) && (y != borde)) {
            t[x + 1][y + 1].incrementarContadorMina();
        }

        //  Abajo.
        if (y != borde) {
            t[x][y + 1].incrementarContadorMina();
        }

        //  Abajo izquierda.
        if ((x != 0) && (y != borde)) {
            t[x - 1][y + 1].incrementarContadorMina();
        }

        //  Izquierda.
        if (x != 0) {
            t[x - 1][y].incrementarContadorMina();
        }

    }

    //  Para comprobar si ha acabado la partida miraremos cada casilla del tablero.
    //  Si una casilla destapada tenía una bomba, significa que la habíamos clicado
    //  y por tanto hemos perdido. Victoria se pone a false y el método retorna true
    //  para indicar que ha acabado la partida y hemos perdido. Si acabamos el recorrido
    //  y no se ha dado el caso anterior, comprobaremos que únicamente queden casillas con
    //  mina (sencillo cálculo matemático). En ese caso Victoria = true y también retornaríamos
    //  true para finalizar la partida. En otro caso, la partida no ha acabado y retorna false.
    public boolean finPartida() {

        int contador_casillas = 0;

        for (int i = 0; i < longitud_casillas; i++) {
            for (int j = 0; j < longitud_casillas; j++) {

                if (t[i][j].estaDestapada()) {

                    contador_casillas++;

                    if (t[i][j].hayMina()) {
                        victoria = false;
                        return true;
                    }
                }
            }
        }

        if (contador_casillas == (longitud_casillas * longitud_casillas) - minas_totales) {
            victoria = true;
            return true;
        }

        return false;

    }

    //  Nos retorna si hemos ganado o perdido.
    public boolean esVictoria() {
        return victoria;
    }

    //  El método lo ejecutaremos cuando clickeemos una casilla (CLICK IZQUIERDO).
    //  Recibimos las coordenadas de la casilla por parámetro.
    public void casillaClicada(int x, int y) {

        //  Si está destapada o está marcada no debe ser clicada.
        if ((!t[x][y].estaDestapada()) && !t[x][y].estaMarcada()) {

            //  Destapamos la casilla.
            t[x][y].destapar();

            //  Si la casilla era un 0, entonces procederemos a destapar las casillas
            //  de alrededor.
            if ((t[x][y].getNumeroBombas() == 0) && (!t[x][y].hayMina())) {
                destaparCasillasCercanas(x, y);
            }
        }
    }

    //  Siguiendo el esquema de recorrer las casillas cercanas (visto a la hora de
    //  incrementar el número de bombas cercanas cuando colocábamos minas), ahora
    //  haremos lo mismo, pero revelando sólo casillas que no estén marcadas y cuyo
    //  nombre de minas cercanas sea DIFERENTE DE CERO (Las que tengan cero las
    //  destaparemos a continuación).
    private void destaparCasillasCercanas(int x, int y) {

        int borde = longitud_casillas - 1;

        //  Recordamos: hay que tener cuidado con no salirse de los márgenes del array.
        //  Arriba izquierda.
        if ((x != 0) && (y != 0)) {

            if (t[x - 1][y - 1].getNumeroBombas() != 0 && !t[x - 1][y - 1].estaMarcada()) {
                t[x - 1][y - 1].destapar();
            }
        }

        //  Arriba.
        if (y != 0) {

            if (t[x][y - 1].getNumeroBombas() != 0 && !t[x][y - 1].estaMarcada()) {
                t[x][y - 1].destapar();
            }
        }

        //  Arriba derecha.
        if ((x != borde) && (y != 0)) {

            if (t[x + 1][y - 1].getNumeroBombas() != 0 && !t[x + 1][y - 1].estaMarcada()) {
                t[x + 1][y - 1].destapar();
            }
        }

        //  Derecha.
        if (x != borde) {

            if (t[x + 1][y].getNumeroBombas() != 0 && !t[x + 1][y].estaMarcada()) {
                t[x + 1][y].destapar();
            }
        }

        //  Abajo derecha.
        if ((x != borde) && (y != borde)) {

            if (t[x + 1][y + 1].getNumeroBombas() != 0 && !t[x + 1][y + 1].estaMarcada()) {
                t[x + 1][y + 1].destapar();
            }
        }

        //  Abajo.
        if (y != borde) {

            if (t[x][y + 1].getNumeroBombas() != 0 && !t[x][y + 1].estaMarcada()) {
                t[x][y + 1].destapar();
            }
        }

        //  Abajo izquierda.
        if ((x != 0) && (y != borde)) {

            if (t[x - 1][y + 1].getNumeroBombas() != 0 && !t[x - 1][y + 1].estaMarcada()) {
                t[x - 1][y + 1].destapar();
            }
        }

        //  Izquierda.
        if (x != 0) {

            if (t[x - 1][y].getNumeroBombas() != 0 && !t[x - 1][y].estaMarcada()) {
                t[x - 1][y].destapar();
            }
        }

        //  Y ahora sí nos encargaremos de extender los ceros cercanos.
        extenderCeros(x, y);

    }

    //  Ahora echaremos mano de la recursividad. Destaparemos nuestra propia casilla,
    //  e iremos comprobando al igual que antes nuestros alrededores. Si la casilla
    //  tiene cero bombas cercanas, no está destapada, y no está marcada, entonces
    //  será aceptada como otro foco para revelar las casillas cercanas y para
    //  extender los ceros contiguos a la propia casilla.
    private void extenderCeros(int x, int y) {

        //  Destapamos la nuestra propia
        t[x][y].destapar();

        int borde = longitud_casillas - 1;

        //  Recordamos: hay que tener cuidado con no salirse de los márgenes del array.
        //  Arriba izquierda.
        if ((x != 0) && (y != 0)) {

            if (t[x - 1][y - 1].getNumeroBombas() == 0 && !t[x - 1][y - 1].estaDestapada() && !t[x - 1][y - 1].estaMarcada()) {
                destaparCasillasCercanas(x - 1, y - 1);
            }
        }

        //  Arriba.
        if (y != 0) {

            if (t[x][y - 1].getNumeroBombas() == 0 && !t[x][y - 1].estaDestapada() && !t[x][y - 1].estaMarcada()) {
                destaparCasillasCercanas(x, y - 1);
            }
        }

        //  Arriba derecha.
        if ((x != borde) && (y != 0)) {

            if (t[x + 1][y - 1].getNumeroBombas() == 0 && !t[x + 1][y - 1].estaDestapada() && !t[x + 1][y - 1].estaMarcada()) {
                destaparCasillasCercanas(x + 1, y - 1);
            }
        }

        //  Derecha.
        if (x != borde) {

            if (t[x + 1][y].getNumeroBombas() == 0 && !t[x + 1][y].estaDestapada() && !t[x + 1][y].estaMarcada()) {
                destaparCasillasCercanas(x + 1, y);
            }
        }

        //  Abajo derecha.
        if ((x != borde) && (y != borde)) {

            if (t[x + 1][y + 1].getNumeroBombas() == 0 && !t[x + 1][y + 1].estaDestapada() && !t[x + 1][y + 1].estaMarcada()) {
                destaparCasillasCercanas(x + 1, y + 1);
            }
        }

        //  Abajo.
        if (y != borde) {

            if (t[x][y + 1].getNumeroBombas() == 0 && !t[x][y + 1].estaDestapada() && !t[x][y + 1].estaMarcada()) {
                destaparCasillasCercanas(x, y + 1);
            }
        }

        //  Abajo izquierda.
        if ((x != 0) && (y != borde)) {

            if (t[x - 1][y + 1].getNumeroBombas() == 0 && !t[x - 1][y + 1].estaDestapada() && !t[x - 1][y + 1].estaMarcada()) {
                destaparCasillasCercanas(x - 1, y + 1);
            }
        }

        //  Izquierda.
        if (x != 0) {

            if (t[x - 1][y].getNumeroBombas() == 0 && !t[x - 1][y].estaDestapada() && !t[x - 1][y].estaMarcada()) {
                destaparCasillasCercanas(x - 1, y);
            }
        }
    }

    //  MODO CONSOLA   //   Este método imprime por consola el tablero según
    //  las casillas estén destapadas o no (no implementa casillas marcadas).
    public void imprime() {
        for (int i = 0; i < longitud_casillas; i++) {
            for (int j = 0; j < longitud_casillas; j++) {

                if (!t[i][j].estaDestapada()) {
                    System.out.print("? ");
                } else {
                    if (t[i][j].hayMina()) {
                        System.out.print("X ");
                    } else {
                        System.out.print(t[i][j].getNumeroBombas() + " ");
                    }
                }
            }
            System.out.println();
        }
    }

    //  Una vez acabada la partida para poder mostrar todas las casillas,
    //  simplemente estableceremos todas las casillas a destapadas y no marcadas.
    //  El método paint luego pintará el auténtico contenido de todas las casillas.
    public void revelarTablero() {

        for (int i = 0; i < longitud_casillas; i++) {
            for (int j = 0; j < longitud_casillas; j++) {

                t[i][j].destapar();
                t[i][j].marcar(false);
            }
        }
    }

    //  Getter del número de casillas que tiene cada lado del tablero.
    public int getLongitudCasillas() {
        return longitud_casillas;
    }

    //  Getter del número de minas que contiene el tablero.
    public int getMinas() {
        return minas_totales;
    }

    //  Método que aplica el correcto funcionamiento de la mecánica de marcar
    //  casillas. No puede haber más casillas marcadas que minas en el tablero.
    //  Y sólo se pueden marcar casillas tapadas.
    public void casillaMarcada(int x, int y) {

        if (t[x][y].estaMarcada()) {

            t[x][y].marcar(false);
            casillas_marcadas--;
        } else {

            if (!t[x][y].estaDestapada() && casillas_marcadas < minas_totales) {
                t[x][y].marcar(true);
                casillas_marcadas++;
            }

        }
    }

    //  Devuelve la casilla indicada por las coordenadas.
    public Casella getCasella(int x, int y) {
        return t[x][y];
    }

}
