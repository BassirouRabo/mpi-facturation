import play.http.HttpErrorHandler;
import play.mvc.Http.RequestHeader;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Singleton
public class ErrorHandler implements HttpErrorHandler {
    public CompletionStage<Result> onClientError(RequestHeader request, int statusCode, String message) {
        return CompletableFuture.completedFuture(
                Results.redirect(controllers.routes.HomeController.errorClient())
                //Results.status(statusCode, "A client error occurred: " + message + "statusCode "+statusCode)
        );
    }

    public CompletionStage<Result> onServerError(RequestHeader request, Throwable exception) {
        return CompletableFuture.completedFuture(
                // Results.internalServerError("Une erreur serveur, contacter l'administrateur en lui envoyant le message suivant: " + exception.getMessage())
                Results.redirect(controllers.routes.HomeController.errorServer(exception.getMessage()))
        );
    }
}