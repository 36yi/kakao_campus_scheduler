package com.example.scheduler.repository;

import com.example.scheduler.model.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(Schedule schedule) {
        String sql = "INSERT INTO schedule (task, author, password, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                schedule.getTask(), schedule.getAuthor(), schedule.getPassword(),
                Timestamp.valueOf(schedule.getCreatedAt()), Timestamp.valueOf(schedule.getUpdatedAt())
        );
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
    }


    public List<Schedule> findAll(String author, String date) {
        StringBuilder sql = new StringBuilder("SELECT * FROM schedule WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (author != null && !author.isBlank()) {
            sql.append(" AND author = ?");
            params.add(author);
        }
        if (date != null && !date.isBlank()) {
            sql.append(" AND DATE(updated_at) = ?");
            params.add(date);
        }

        sql.append(" ORDER BY updated_at DESC");

        return jdbcTemplate.query(sql.toString(), scheduleRowMapper(), params.toArray());
    }

    public Optional<Schedule> findById(Long id) {
        String sql = "SELECT * FROM schedule WHERE id = ?";
        List<Schedule> result = jdbcTemplate.query(sql, scheduleRowMapper(), id);
        return result.stream().findFirst();
    }

    private RowMapper<Schedule> scheduleRowMapper() {
        return (rs, rowNum) -> {
            Schedule s = new Schedule();
            s.setId(rs.getLong("id"));
            s.setTask(rs.getString("task"));
            s.setAuthor(rs.getString("author"));
            s.setPassword(rs.getString("password"));
            s.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            s.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            return s;
        };
    }

    public void update(Schedule schedule) {
        String sql = "UPDATE schedule SET task = ?, author = ?, updated_at = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                schedule.getTask(),
                schedule.getAuthor(),
                Timestamp.valueOf(schedule.getUpdatedAt()),
                schedule.getId()
        );
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM schedule WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
