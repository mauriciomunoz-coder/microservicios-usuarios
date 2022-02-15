package com.formacionbdi.microservicios.app.usuarios.controllers;


import com.formacionbdi.microservicios.app.usuarios.models.entity.Alumno;
import com.formacionbdi.microservicios.app.usuarios.services.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
public class AlumnoController {

    @Autowired
    private AlumnoService service;


    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok().body(service.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {

        Optional<Alumno> o = service.findById(id);  //se crea un objeto Optional de tipo ALumno

        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();

        }
        return ResponseEntity.ok(o.get());  //con o.get()  retornamos el alumno encontrado
    }


    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Alumno alumno) {
        Alumno alumnoDB = service.save(alumno);
        return ResponseEntity.status(HttpStatus.CREATED).body(alumnoDB);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Alumno alumno, @PathVariable Long id) {
        Optional<Alumno> o = service.findById(id);  //se crea un objeto Optional de tipo ALumno

        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Alumno alumnoDB = o.get();  // con o.get()  obtenemos el alumno encontrado

        alumnoDB.setNombre(alumno.getNombre());
        alumnoDB.setApellido(alumno.getApellido());
        alumnoDB.setEmail(alumno.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnoDB));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Alumno> o = service.findById(id);  //se crea un objeto Optional de tipo ALumno

        if (o.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.deleteById(id);
        return ResponseEntity.noContent().build();

    }
}
