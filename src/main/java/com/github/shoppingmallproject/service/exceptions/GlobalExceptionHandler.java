package com.github.shoppingmallproject.service.exceptions;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        ErrorResponse errorResponse = new ErrorResponse("VF", "Validation failed");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
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

@Getter
class ErrorResponse {
    private String code;
    private String message;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

}

@Getter
class ErrorRequestResponse {
    private String code;
    private String message;
    private String request;

    public ErrorRequestResponse(String code, String message, String request) {
        this.code = code;
        this.message = message;
        this.request = request;
    }
}