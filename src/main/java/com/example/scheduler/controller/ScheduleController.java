package com.example.scheduler.controller;

import com.example.scheduler.dto.ScheduleRequestDTO;
import com.example.scheduler.dto.ScheduleResponseDTO;
import com.example.scheduler.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {
    private final ScheduleService service;

    public ScheduleController(ScheduleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDTO> create(@RequestBody ScheduleRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDTO>> getAll(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String date
    ) {
        List<ScheduleResponseDTO> schedules = service.getAll(author, date);
        return ResponseEntity.ok(schedules);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDTO> getById(@PathVariable Long id) {
        ScheduleResponseDTO response = service.getById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDTO> update(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDTO dto
    ) {
        ScheduleResponseDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id,
            @RequestBody Map<String, String> body
    ) {
        String password = body.get("password");
        service.delete(id, password);
        return ResponseEntity.ok("삭제 완료");
    }
}