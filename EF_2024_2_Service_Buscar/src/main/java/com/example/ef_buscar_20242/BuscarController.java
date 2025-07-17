package com.example.ef_buscar_20242;

import com.example.ef_buscar_20242.entity.Employee;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BuscarController {

    @Autowired
    private ServiceBuscarClient serviceBuscarClient;

    @Autowired
    private CircuitBreakerFactory<?, ?> circuitBreakerFactory;

    private final Gson gson = new Gson();

    @GetMapping(value = "/buscar/{word}/{order}", produces = "application/json")
    public String listarBuscar(@PathVariable("word") String item_a,
                               @PathVariable("order") String item_b) {

        ArrayList<Employee> buscarempleado = new ArrayList<>();

        // Circuit Breaker para evitar fallos duros si el servicio remoto está caído
        circuitBreakerFactory.create("buscar-listar")
                .run(
                        () -> {
                            String json = serviceBuscarClient.listar(item_b); // item_b = orden de columna
                            Type listType = new TypeToken<List<Employee>>() {}.getType();
                            List<Employee> empleados = gson.fromJson(json, listType);

                            for (Employee e : empleados) {
                                if (e.getFirstName().toLowerCase().contains(item_a.toLowerCase()) ||
                                        e.getLastName().toLowerCase().contains(item_a.toLowerCase())) {
                                    buscarempleado.add(e);
                                }
                            }

                            return null;
                        },
                        throwable -> {
                            // fallback en caso de error
                            System.out.println("Servicio remoto no disponible: " + throwable.getMessage());
                            return null;
                        }
                );

        return gson.toJson(buscarempleado);
    }

}
