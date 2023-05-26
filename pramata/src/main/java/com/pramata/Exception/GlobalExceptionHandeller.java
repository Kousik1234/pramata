package com.pramata.Exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandeller {


    @ExceptionHandler(UserException.class)
    public ResponseEntity<MyErorDetails> userExceptionHandler(UserException ce, WebRequest req) {


        MyErorDetails err = new MyErorDetails();
        err.setTimeStamp(LocalDateTime.now());
        err.setMessage(ce.getMessage());
        err.setHttpStatus(HttpStatus.NOT_FOUND);
        err.setDetails(req.getDescription(false));

        return new ResponseEntity<MyErorDetails>(err, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(JwtAuthException.class)
    public ResponseEntity<MyErorDetails> JwtAuthExceptionHandler(JwtAuthException ce, WebRequest req) {


        MyErorDetails err = new MyErorDetails();
        err.setTimeStamp(LocalDateTime.now());
        err.setMessage(ce.getMessage());
        err.setHttpStatus(HttpStatus.NOT_FOUND);
        err.setDetails(req.getDescription(false));

        return new ResponseEntity<MyErorDetails>(err, HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler(ChannelException.class)
    public ResponseEntity<MyErorDetails> teamExceptionHandler(ChannelException ce, WebRequest req) {


        MyErorDetails err = new MyErorDetails();
        err.setTimeStamp(LocalDateTime.now());
        err.setMessage(ce.getMessage());
        err.setHttpStatus(HttpStatus.NOT_FOUND);
        err.setDetails(req.getDescription(false));

        return new ResponseEntity<MyErorDetails>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MessageException.class)
    public ResponseEntity<MyErorDetails> formExceptionHandler(MessageException ce, WebRequest req) {


        MyErorDetails err = new MyErorDetails();
        err.setTimeStamp(LocalDateTime.now());
        err.setMessage(ce.getMessage());
        err.setHttpStatus(HttpStatus.NOT_FOUND);
        err.setDetails(req.getDescription(false));

        return new ResponseEntity<MyErorDetails>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnAuthorizeException.class)
    public ResponseEntity<MyErorDetails> unAuthorizeExceptionHandler(UnAuthorizeException ce, WebRequest req) {


        MyErorDetails err = new MyErorDetails();
        err.setTimeStamp(LocalDateTime.now());
        err.setMessage(ce.getMessage());
        err.setHttpStatus(HttpStatus.NOT_FOUND);
        err.setDetails(req.getDescription(false));

        return new ResponseEntity<MyErorDetails>(err, HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<MyErorDetails> otherExceptionHandler(Exception se, WebRequest req){


        MyErorDetails err= new MyErorDetails();
        err.setTimeStamp(LocalDateTime.now());
        err.setMessage(se.getMessage());
        err.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        err.setDetails(req.getDescription(false));

        return new ResponseEntity<MyErorDetails>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
