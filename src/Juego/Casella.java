package Juego;

import java.io.Serializable;

////////////////////////////////////////////////////////////////////////////////
//  Podríamos decir que un tablero es un array bidimensional de casillas. Cada
//  casilla tiene sus propios atributos, como el contener una mina (o no), estar
//  destapada o no, estar marcada o no, y un contador que indica cuántas minas tiene
//  alrededor. Como queremos encapsular bien nuestros objetos, éstos atributos son
//  todos privados y para modificarlos u obtenerlos hará falta hacerlo a través 
//  de los setters y getters respectivamente.
//
public class Casella implements Serializable {

    //  Para el apartado gráfico: longitud en píxeles de la casilla.
    public static final int LADO_CASILLA = 50;
    
    //  Atributos de una casilla.
    private boolean destapada;
    private boolean mina;
    private boolean marcada;
    private int numero_bombas;

    //  Cuando creemos un Tauler desde cero, también inicializaremos cada
    //  casilla a un estado por defecto.
    public Casella() {

        destapada = false;
        mina = false;
        marcada = false;
        numero_bombas = 0;
    }

    //  Nos devuelve true si hay una mina en la casilla.
    public boolean hayMina() {
        return mina;
    }

    //  De ningún modo quitaremos una mina de una casilla, de modo que el setter
    //  únicamente pone el boolean mina a true.
    public void ponMina() {
        mina = true;
    }
    
    //  Cuando coloquemos una mina usaremos este método para incrementar en 1 el
    //  contador de minas de las casillas cercanas.
    public void incrementarContadorMina() {
        numero_bombas++;
    }
    
    //  Nos devuelve true si la casilla está destapada.
    public boolean estaDestapada() {
        return destapada;
    }
    
    //  Al igual que el método ponMina, nunca tendremos que tapar una casilla.
    //  De modo que siempre establece el atributo a true.
    public void destapar() {
        destapada = true;
    }
    
    //  Nos devuelve el número de bombas que contiene la casilla.
    public int getNumeroBombas() {
        return numero_bombas;
    }
    
    //  Nos devuelve true si la casilla está marcada.
    public boolean estaMarcada() {
        return marcada;
    }
    
    //  Una casilla puede estar marcada y desmarcarse y viceversa. Hay un límite
    //  de casillas que pueden estar marcadas, pero eso lo tratamos en otra zona
    //  donde se trata el funcionamiento del juego.
    public void marcar(boolean b) {
        marcada = b;
    }
    
}
