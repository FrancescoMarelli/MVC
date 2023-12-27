package usuario;
import  Vista.*;
import modelo.*;

public class Usuario {
    private String name;
    private Vista vista;
    private Modelo modelo;

    public Usuario(String name, Modelo modelo, Vista vista) {
        this.name = name;
        this.modelo = modelo;
        this.vista = vista;
    }

    public String getName() {
        return name;
    }

    public void setVista(Vista vista){
        this.vista = vista;
    }

    public Vista getVista() {
        return vista;
    }

    public Modelo getModelo() {
        return modelo;
    }

}
