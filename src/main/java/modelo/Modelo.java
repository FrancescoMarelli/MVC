package modelo;

import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class Modelo {

    private final NewsApiClient newsApiClient;
    ArrayList<Article> noticias;

    public Modelo() {
        this.newsApiClient = new NewsApiClient("895c1b9e570349cc830c4571482d4758");
        noticias = new ArrayList<>();
    }

    public ArrayList<Article> getNoticias(){
        return noticias;
    }

    public CompletableFuture<List<Article>> parseEverythingToList(String query) {
        CompletableFuture<List<Article>> future = new CompletableFuture<>();

        newsApiClient.getEverything(
                new EverythingRequest.Builder()
                        .q(query)
                        .sortBy("publishedAt")
                        .pageSize(100)
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        System.out.println("Buscando nuevos articulos ...");
                        future.complete(response.getArticles());
                    }
                    @Override
                    public void onFailure(Throwable throwable) {
                        System.out.println("Error fetching everything articles: " + throwable.getMessage());
                        future.completeExceptionally(throwable);
                    }
                }
        );
        return future;
    }
}

class ArticleUtils {

    public static Article findByTitle(List<Article> articles, String title) {
        for (Article article : articles) {
            if (article.getTitle().equals(title)) {
                return article;
            }
        }
        return null; // Retorna null si no se encuentra ninguna coincidencia
    }
}
