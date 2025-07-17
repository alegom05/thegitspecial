package com.example.ef_buscar_20242;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-listar")
public interface ServiceBuscarClient {

    @GetMapping("/empleados/listar/{ordercolum}")
    String listar(@PathVariable("ordercolum") String ordercolum);

}