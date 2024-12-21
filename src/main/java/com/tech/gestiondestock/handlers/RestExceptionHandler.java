package com.tech.gestiondestock.handlers;

import com.tech.gestiondestock.exception.EntityNotFoundException;
import com.tech.gestiondestock.exception.ErrorCodes;
import com.tech.gestiondestock.exception.InvalidEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.Date;

@RestControllerAdvice //v11 difference avec ControllerAdvice est qu'on a pas besoin d'ajouter @ResponseBody à chaque methode
public class RestExceptionHandler extends ResponseEntityExceptionHandler { //v11 min42 cad on va utiliser le modele ResponseEntity pour la gestion des excep

    @ExceptionHandler(EntityNotFoundException.class)//v11 min44 lorsque on leve une excep de type EntityNotFound dans notre application, elle sera catché par le RestControllerAdvice (de l14) et on va aller dans la methode de l18
    public ResponseEntity<ErrorDto> entityNotFoundException(EntityNotFoundException ex, WebRequest webRequest){

        final HttpStatus notFound = HttpStatus.NOT_FOUND;
        final ErrorDto errorDto = ErrorDto.builder().errorCodes(ex.getErrorCodes()).message(ex.getMessage())
                                    .timestamp(new Date()).httpCode(notFound.value()).build();
        return new ResponseEntity<>(errorDto, notFound);
    }

    @ExceptionHandler(InvalidEntityException.class)
    public ResponseEntity<ErrorDto> invalidEntityException(InvalidEntityException ex, WebRequest webRequest){

        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        final ErrorDto errorDto = ErrorDto.builder().errorCodes(ex.getErrorCodes()).errors(ex.getErrors())
        		.message(ex.getMessage()).timestamp(new Date()).httpCode(badRequest.value()).build();
        return new ResponseEntity<>(errorDto, badRequest);
    }
    
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDto> handleException(BadCredentialsException exception, WebRequest webRequest) {
      final HttpStatus badRequest = HttpStatus.BAD_REQUEST;

      final ErrorDto errorDto = ErrorDto.builder()
          .errorCodes(ErrorCodes.BAD_CREDENTIALS)
          .httpCode(badRequest.value())
          .message(exception.getMessage())
          .errors(Collections.singletonList("Login et / ou mot de passe incorrecte"))
          .build();

      return new ResponseEntity<>(errorDto, badRequest);
    }
}
