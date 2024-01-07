package modelo;

import com.kwabenaberko.newsapilib.models.Article;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public interface Modelo {
    public ArrayList<Article> getArticles() throws ExecutionException, InterruptedException;
    public String getQueryName();
    public void setQueryName(String queryName);
}
