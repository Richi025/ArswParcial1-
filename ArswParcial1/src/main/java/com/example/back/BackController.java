package com.example.back;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
*REST controller for handling data.
*/
@RestController
public class BackController {

    private HashMap<String, String> cache = new HashMap<>();

    @Autowired
    private BackService service;
    
    
	/**
     * 
     * @param function 
     * @param symbol
     * @param interval
     * @return
     */
    @RequestMapping(
            value = "/mercado",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    public String getMercado(@RequestParam String function,@RequestParam String symbol, @RequestParam(required = false) String interval){

        String clave =  function + symbol + interval;
        if(cache.get(clave) != null){
            String cacheRespuesta = cache.get(clave);
            System.out.println("cache = " + cacheRespuesta);
            return cacheRespuesta;
        }else{
            String respuesta = service.getMercadoService(function, symbol, interval);
            cache.put(clave, respuesta);
            
            return respuesta;
        }
    
    }
}
