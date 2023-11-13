package FicherosES;

import Juego.Tauler;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

////////////////////////////////////////////////////////////////////////////////
//  Esta clase sólo se utiliza para leer un objeto Tauler (necesario para poder
//  cargar una partida anteriormente guardada).
//
public class TaulerIn {

    //  Atributos
    FileInputStream fis;
    ObjectInputStream ois;

    //  Establecemos enlace con el fichero de directorio s.
    public TaulerIn(String s) {
        try {

            fis = new FileInputStream(s);
            ois = new ObjectInputStream(fis);

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    //  Leemos el Tauler del fichero y lo devolvemos.
    public Tauler leerTauler() {

        Tauler t = null;

        try {

            t = (Tauler) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
        }

        return t;
    }

    //  Cerramos los enlaces creados por el constructor.
    public void cerrar() {
        try {

            ois.close();
            fis.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
