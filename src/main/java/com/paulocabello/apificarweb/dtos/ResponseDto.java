package com.paulocabello.apificarweb.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {

    private long userId;
    private long id;
    private String title;
    private boolean completed;
}

