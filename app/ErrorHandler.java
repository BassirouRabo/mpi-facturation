import play.api.http.HttpErrorHandler;
import play.api.mvc.RequestHeader;
import play.api.mvc.Result;
import scala.concurrent.Future;


public class ErrorHandler implements HttpErrorHandler {
    @Override
    public Future<Result> onClientError(RequestHeader request, int statusCode, String message) {
        return null;
    }

    @Override
    public String onClientError$default$3() {
        return null;
    }

    @Override
    public Future<Result> onServerError(RequestHeader request, Throwable exception) {
        return null;
    }
}
