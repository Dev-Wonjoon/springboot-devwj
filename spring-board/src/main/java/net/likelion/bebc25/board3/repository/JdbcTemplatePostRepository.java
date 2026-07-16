package net.likelion.bebc25.board3.repository;

import net.likelion.bebc25.board3.post.PostDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcTemplatePostRepository implements PostRepository {

    private final JdbcTemplate jdbcTemplate;


    public JdbcTemplatePostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    private final RowMapper<PostDto> postListRowMapper =
            (ResultSet rs, int rowNum) -> {
        return PostDto.builder()
                .id(rs.getInt("id"))
                .title(rs.getString("title"))
                .content(rs.getString("content"))
                .author(rs.getString("author"))
                .secret(rs.getBoolean("secret"))
                .createdAt(rs.getObject("created_at", LocalDateTime.class))
                .build();
    };

    @Override
    public List<PostDto> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM post2", postListRowMapper);
    }

    @Override
    public PostDto findById(long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM post2 WHERE id = ?", postListRowMapper, id);
    }

    @Override
    public void save(PostDto post) {
        jdbcTemplate.update(
                "INSERT INTO post2 (title, author, content) VALUES (?, ?, ?)",
                post.getTitle(), post.getAuthor(), post.getContent());
    }

    @Override
    public void update(PostDto post) {
        jdbcTemplate.update(
                "UPDATE post2 SET title = ?, author = ?, content = ? WHERE id = ?",
                post.getTitle(), post.getAuthor(), post.getContent(), post.getId()
        );
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update("DELETE FROM post2 WHERE id = ?", id);
    }
}
