package usuario;
import  Vista.*;

public class Usuario {
    String name;
    Vista vista;

    // Constructor que recibe el nombre como parámetro
    public Usuario(String name) {
        this.name = name;
    }

    // Getter para acceder al atributo "name"
    public String getName() {
        return name;
    }

    public void setVista(Vista vista){
        this.vista = vista;
    }

    public Vista getVista() {
        return vista;
    }

}
