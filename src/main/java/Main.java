import GUI.NewsGUI;
import com.kwabenaberko.newsapilib.models.Article;
import modelo.Modelo;
import org.app.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Modelo modelo = new Modelo();
        ArrayList<Article> noticias = (ArrayList<Article>) modelo.parseEverythingToList("bitcoin").join();
        for (Article noticia : noticias) {
            System.out.println(noticia.getTitle());
        }
        // Vista vista = new Vista();
       // Controlador controlador = new Controlador(modelo, vista);

    }
}
