package com.example.demo.controller;

import com.example.demo.model.Vehicle;
import com.example.demo.repository.VehicleRepository;
import com.example.demo.model.VehicleForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private VehicleRepository vehicles;

    public VehicleController(VehicleRepository vehicles) {
        this.vehicles = vehicles;
    }

    @GetMapping("")
    public ResponseEntity all() {
        return ResponseEntity.ok(this.vehicles.findAll());
    }

    @PostMapping("")
    public ResponseEntity save(@RequestBody VehicleForm form, HttpServletRequest request) {
        Vehicle saved = vehicles.save(Vehicle.builder().name(form.getName()).build());
        return ResponseEntity.created(
            ServletUriComponentsBuilder
                .fromContextPath(request)
                .path("/v1/vehicles/{id}")
                .buildAndExpand(saved.getId())
                .toUri())
            .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Vehicle> vehicle = vehicles.findById(id);
        if (vehicle.isPresent()) {
            return ResponseEntity.ok(vehicle.get());
        }
        return new ResponseEntity<>("Vehicle not found", HttpStatus.NOT_FOUND);
    }


    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody VehicleForm form) {
        Optional<Vehicle> vehicle = vehicles.findById(id);
        if (vehicle.isPresent()) {
            Vehicle existed = vehicle.get();
            existed.setName(form.getName());
            vehicles.save(existed);
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>("Vehicle not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        Optional<Vehicle> vehicle = vehicles.findById(id);
        if (vehicle.isPresent()) {
            vehicles.delete(vehicle.get());
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>("Vehicle not found", HttpStatus.NOT_FOUND);
    }
}
