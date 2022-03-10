package com.paulocabello.apificarweb.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseErrorDto {

    private int codigo;
    private String error;
    private String reasonPhrase;
}

