package com.tech.gestiondestock.handlers;

import com.tech.gestiondestock.exception.ErrorCodes;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorDto {

    ErrorCodes errorCodes;
    String message;
    Date timestamp;
    Integer httpCode;
    List<String> errors = new ArrayList<>();
}
