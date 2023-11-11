package com.codeforinca.bytsoftapi.controllers;


import com.codeforinca.bytsoftapi.models.request.ModulModelRequest;
import com.codeforinca.bytsoftapi.models.response.ApiResponse;
import com.codeforinca.bytsoftapi.persistence.entites.Modul;
import com.codeforinca.bytsoftapi.services.impl.IModulService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/modul")
@RequiredArgsConstructor
public class ModulController {


    private final IModulService modulService;


    @GetMapping
    public
    ResponseEntity<ApiResponse>
    getFindAll(

    ){
        return new ResponseEntity<>(
                new ApiResponse(
                        "OK",
                        modulService.findAll()
                ),
                HttpStatus.OK
        );
    }


    @GetMapping("/{id}")
    public
    ResponseEntity<ApiResponse>
    getFindById(
            @PathVariable("id") Long id
    ){
        return new ResponseEntity<>(
                new ApiResponse(
                        "OK",
                        modulService.findById(id)
                ),
                HttpStatus.OK
        );
    }


    @PostMapping
    public
    ResponseEntity<ApiResponse>
    postPersist(
            @RequestBody ModulModelRequest modulModelRequest
    ){
        return new ResponseEntity<>(
                new ApiResponse(
                        "OK",
                        modulService.persist(
                                new ObjectMapper()
                                        .convertValue(modulModelRequest,Modul.class)
                        )
                ),
                HttpStatus.OK
        );
    }


    @PutMapping
    public
    ResponseEntity<ApiResponse>
    putMerge(
            @RequestBody ModulModelRequest modulModelRequest
    ){
        return new ResponseEntity<>(
                new ApiResponse(
                        "OK",
                        modulService.merge(
                                new ObjectMapper()
                                        .convertValue(modulModelRequest,Modul.class)
                        )
                ),
                HttpStatus.OK
        );
    }


    @DeleteMapping("/{id}")
    public
    ResponseEntity<ApiResponse>
    deleteRemove(
            @PathVariable("id") Long id
    ){
        return new ResponseEntity<>(
                new ApiResponse(
                        "OK",
                        modulService.remove(id)
                ),
                HttpStatus.OK
        );
    }



}
