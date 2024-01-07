package modelo;

import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class ModeloAPI implements Modelo{

    private final NewsApiClient newsApiClient;
    ArrayList<Article> noticias;
    String queryName;

    public ModeloAPI() {
        this.newsApiClient = new NewsApiClient("08cd793f0c014d7a9cac64799a6e948a");
        //this.newsApiClient = new NewsApiClient("895c1b9e570349cc830c4571482d4758");
        noticias = new ArrayList<>();
    }

    public CompletableFuture<List<Article>> parseEverythingToList() {
        CompletableFuture<List<Article>> future = new CompletableFuture<>();

        newsApiClient.getEverything(
                new EverythingRequest.Builder()
                        .q(queryName)
                        .sortBy("popularity")
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

    public ArrayList<Article> getArticles() throws ExecutionException, InterruptedException {
        return (ArrayList<Article>) parseEverythingToList().get();

    }

    public String getQueryName(){
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }
}

