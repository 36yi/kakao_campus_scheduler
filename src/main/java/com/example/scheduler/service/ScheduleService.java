package com.example.scheduler.service;

import com.example.scheduler.dto.ScheduleRequestDTO;
import com.example.scheduler.dto.ScheduleResponseDTO;
import com.example.scheduler.model.Schedule;
import com.example.scheduler.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    private final ScheduleRepository repo;

    public ScheduleService(ScheduleRepository repo) {
        this.repo = repo;
    }

    public ScheduleResponseDTO create(ScheduleRequestDTO dto) {
        Schedule schedule = new Schedule();
        schedule.setTask(dto.getTask());
        schedule.setAuthor(dto.getAuthor());
        schedule.setPassword(dto.getPassword());
        schedule.setCreatedAt(LocalDateTime.now());
        schedule.setUpdatedAt(LocalDateTime.now());

        Long id = repo.save(schedule);
        schedule.setId(id);

        return new ScheduleResponseDTO(
                schedule.getId(), schedule.getTask(), schedule.getAuthor(),
                schedule.getCreatedAt(), schedule.getUpdatedAt()
        );
    }

    public List<ScheduleResponseDTO> getAll(String author, String date) {
        return repo.findAll(author, date).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ScheduleResponseDTO toResponse(Schedule s) {
        return new ScheduleResponseDTO(
                s.getId(),
                s.getTask(),
                s.getAuthor(),
                s.getCreatedAt(),
                s.getUpdatedAt()
        );
    }

    public ScheduleResponseDTO getById(Long id) {
        Schedule schedule = repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 일정이 존재하지 않습니다."));
        return toResponse(schedule);
    }

    public ScheduleResponseDTO update(Long id, ScheduleRequestDTO dto) {
        Schedule schedule = repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("일정이 존재하지 않습니다."));

        if (!schedule.getPassword().equals(dto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        schedule.setTask(dto.getTask());
        schedule.setAuthor(dto.getAuthor());
        schedule.setUpdatedAt(LocalDateTime.now());

        repo.update(schedule);
        return toResponse(schedule);
    }

    public void delete(Long id, String password) {
        Schedule schedule = repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("일정이 존재하지 않습니다."));

        if (!schedule.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        repo.deleteById(id);
    }
}