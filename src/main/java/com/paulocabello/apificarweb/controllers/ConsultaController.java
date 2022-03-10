package com.paulocabello.apificarweb.controllers;

import com.paulocabello.apificarweb.dtos.ResponseDto;
import com.paulocabello.apificarweb.dtos.ResponseErrorDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/consulta")
public class ConsultaController {

    private final RestTemplate restTemplate;
    private final String BASE_URL = "https://jsonplaceholder.typicode.com/todos";

    public ConsultaController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping(path = "/{id}")
    public Object getSubscriber(@PathVariable String id) throws URISyntaxException {

        try {
            long l = Long.parseLong(id);
        } catch (Exception e) {
            return new ResponseErrorDto(101, "Parámetros inválidos", e.getMessage());
        }

        final URI uri = new URI(BASE_URL.concat("/").concat(id));
        ResponseEntity<ResponseDto> response = null;

        try {
            response = restTemplate.getForEntity(uri, ResponseDto.class);
        } catch (Exception e) {
            return new ResponseErrorDto(100,  "Usuario con ID ".concat(id).concat(" no existe"), e.getMessage());
        }

        if ( response != null && response.getStatusCodeValue() != 200) {
            return manageError(response, id);
        }

        return response;
    }

    @GetMapping(path = {"/", ""})
    public List<ResponseDto> getAllSubscribers() {

        return Arrays.asList(restTemplate.getForObject(BASE_URL, ResponseDto[].class));
    }

    private ResponseErrorDto manageError(ResponseEntity<ResponseDto> response, String id) {
        ResponseErrorDto error = new ResponseErrorDto();
        if (response.getStatusCodeValue() == 404) {
            error.setCodigo(100);
            error.setError("Usuario con ID ".concat(id).concat(" no existe"));
            error.setReasonPhrase(response.getStatusCode().getReasonPhrase());
        } else if (String.valueOf(response.getStatusCodeValue()).charAt(0) == '5') {
            error.setCodigo(102);
            error.setError("Error interno del servidor");
            error.setReasonPhrase(response.getStatusCode().getReasonPhrase());
        }
        return error;
    }

}

