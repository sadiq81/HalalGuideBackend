package dk.eazyit.halalguide.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created with IntelliJ IDEA.
 * User: Privat
 * Date: 07/02/16
 * Time: 15.53
 * To change this template use File | Settings | File Templates.
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleException(final RuntimeException e, WebRequest request) {
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace(printWriter);
        printWriter.flush();
        String stackTrace = writer.toString();

        logger.error(stackTrace);

        return handleExceptionInternal(e, stackTrace, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

}