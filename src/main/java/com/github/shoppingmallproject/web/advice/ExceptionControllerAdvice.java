package com.github.shoppingmallproject.web.advice;
import com.github.shoppingmallproject.service.exceptions.*;
import com.github.shoppingmallproject.service.exceptions.response.ErrorRequestResponse;
import com.github.shoppingmallproject.service.exceptions.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {



    @ExceptionHandler(AccessDenied.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) //권한이 없을때
    public ResponseEntity<ErrorRequestResponse> handleNotAccessDenied(AccessDenied ex) {
        ErrorRequestResponse errorRequestResponse = new ErrorRequestResponse(ex.getCode(), ex.getMessage(), ex.getRequest());
        return new ResponseEntity<>(errorRequestResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AccountLockedException.class)
    @ResponseStatus(HttpStatus.LOCKED) //계정이 잠겼을때
    public ResponseEntity<ErrorRequestResponse> handleAccountLockedException(AccountLockedException ex) {
        ErrorRequestResponse errorRequestResponse = new ErrorRequestResponse(ex.getCode(), ex.getMessage(), ex.getRequest());
        return new ResponseEntity<>(errorRequestResponse, HttpStatus.LOCKED);
    }


    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 요청 에러
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        ErrorResponse errorResponse = new ErrorResponse("VF", "Validation Failed");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.CONFLICT) //입력한 키가 이미 존재할때
    public ResponseEntity<ErrorRequestResponse> handleDuplicateKeyException(DuplicateKeyException ex) {
        ErrorRequestResponse errorRequestResponse = new ErrorRequestResponse(ex.getCode(), ex.getMessage(), ex.getRequest());
        return new ResponseEntity<>(errorRequestResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotAcceptException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE) //처리할 수 없는 요청
    public ResponseEntity<ErrorRequestResponse> handleNotAcceptException(NotAcceptException ex) {
        ErrorRequestResponse errorRequestResponse = new ErrorRequestResponse(ex.getCode(), ex.getMessage(), ex.getRequest());
        return new ResponseEntity<>(errorRequestResponse, HttpStatus.NOT_ACCEPTABLE);
    }


    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) //찾을 수 없을때
    public ResponseEntity<ErrorRequestResponse> handleNotFoundException(NotFoundException ex) {
        ErrorRequestResponse errorRequestResponse = new ErrorRequestResponse(ex.getCode(), ex.getMessage(), ex.getRequest());
        return new ResponseEntity<>(errorRequestResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // DB 에러
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse("DBE",  "Database Error");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomBindException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) //양식과 맞지 않을때
    public ResponseEntity<ErrorRequestResponse> handleCustomBindException(CustomBindException ex) {
        ErrorRequestResponse errorRequestResponse = new ErrorRequestResponse(ex.getCode(), ex.getMessage(), ex.getRequest());
        return new ResponseEntity<>(errorRequestResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) //비밀번호가 틀렸을때
    public ResponseEntity<ErrorRequestResponse> handleBadCredentialsException(BadCredentialsException ex) {
        ErrorRequestResponse errorRequestResponse = new ErrorRequestResponse(ex.getCode(), ex.getMessage(), ex.getRequest());
        return new ResponseEntity<>(errorRequestResponse, HttpStatus.UNAUTHORIZED);
    }

    // 다른 예외에 대한 핸들러 메소드들을 추가할 수 있습니다.
}

