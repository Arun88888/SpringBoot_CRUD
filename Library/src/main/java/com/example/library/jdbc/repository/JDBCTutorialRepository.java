package com.example.library.jdbc.repository;

import java.util.List;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.library.jdbc.model.Tutorial;

@Repository
public class JDBCTutorialRepository implements TutorialRepository {

    private final JdbcTemplate jdbcTemplate;

    public JDBCTutorialRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Tutorial tutorial) {
        return jdbcTemplate.update("INSERT INTO tutorial (title, description, published) VALUES(?,?,?)",
                tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished());
    }

    @Override
    public int update(Tutorial tutorial) {
        return jdbcTemplate.update("UPDATE tutorial SET title=?, description=?, published=? WHERE id=?",
                tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished(), tutorial.getId());
    }

    @Override
    public Tutorial findById(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM tutorial WHERE id=?",
                    BeanPropertyRowMapper.newInstance(Tutorial.class), id);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM tutorial WHERE id=?", id);
    }

    @Override
    public List<Tutorial> findAll() {
        return jdbcTemplate.query("SELECT * from tutorial", BeanPropertyRowMapper.newInstance(Tutorial.class));
    }

    @Override
    public List<Tutorial> findByPublished(boolean published) {
        return jdbcTemplate.query("SELECT * from tutorial WHERE published=?",
                BeanPropertyRowMapper.newInstance(Tutorial.class), published);
    }

    @Override
    public List<Tutorial> findByTitleContaining(String title) {

        String sql = "SELECT * FROM tutorial WHERE title LIKE ?";

        return jdbcTemplate.query(
                sql,
                BeanPropertyRowMapper.newInstance(Tutorial.class),
                "%" + title + "%"
        );
    }


}
