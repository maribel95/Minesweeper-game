package FicherosES;

import Juego.Tauler;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
////////////////////////////////////////////////////////////////////////////////
//  Esta clase sólo se utiliza para escribir un objeto Tauler (necesario para poder
//  guardar una partida que queramos reutilizar más tarde).
//
public class TaulerOut {

    FileOutputStream fos;
    ObjectOutputStream oos;

    //  Establecemos enlace con el fichero de directorio s.
    public TaulerOut(String s) {
        try {

            fos = new FileOutputStream(s);
            oos = new ObjectOutputStream(fos);

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    //  Escribimos el Tauler introducido por parámetro en el fichero.
    public void escribirTauler(Tauler t) {
        try {

            oos.writeObject(t);

        } catch (IOException e) {
            System.out.println(e);
        }
    }

        //  Cerramos los enlaces creados por el constructor.
    public void cerrar() {
        try {

            oos.close();
            fos.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
