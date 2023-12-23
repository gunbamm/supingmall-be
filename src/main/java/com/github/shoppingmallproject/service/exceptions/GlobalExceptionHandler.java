package com.github.shoppingmallproject.service.exceptions;
import com.github.shoppingmallproject.service.exceptions.response.ErrorRequestResponse;
import com.github.shoppingmallproject.service.exceptions.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(AccessDenied.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorRequestResponse> AccessDenied(AccessDenied ex) {
        ErrorRequestResponse errorRequestResponse = new ErrorRequestResponse(ex.getCode(), ex.getMessage(), ex.getRequest());
        return new ResponseEntity<>(errorRequestResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AccountLockedException.class)
    @ResponseStatus(HttpStatus.LOCKED)
    public ResponseEntity<ErrorRequestResponse> AccountLockedException(AccountLockedException ex) {
        ErrorRequestResponse errorRequestResponse = new ErrorRequestResponse(ex.getCode(), ex.getMessage(), ex.getRequest());
        return new ResponseEntity<>(errorRequestResponse, HttpStatus.LOCKED);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        ErrorResponse errorResponse = new ErrorResponse("VF", "Validation failed");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorRequestResponse> DuplicateKeyException(DuplicateKeyException ex) {
        ErrorRequestResponse errorRequestResponse = new ErrorRequestResponse(ex.getCode(), ex.getMessage(), ex.getRequest());
        return new ResponseEntity<>(errorRequestResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotAcceptException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<ErrorRequestResponse> NotAcceptException(NotAcceptException ex) {
        ErrorRequestResponse errorRequestResponse = new ErrorRequestResponse(ex.getCode(), ex.getMessage(), ex.getRequest());
        return new ResponseEntity<>(errorRequestResponse, HttpStatus.NOT_ACCEPTABLE);
    }



    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorRequestResponse> handleNotFoundException(NotFoundException ex) {
        ErrorRequestResponse errorRequestResponse = new ErrorRequestResponse(ex.getCode(), ex.getMessage(), ex.getRequest());
        return new ResponseEntity<>(errorRequestResponse, HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse("DBE",  "Database error");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 다른 예외에 대한 핸들러 메소드들을 추가할 수 있습니다.
}

