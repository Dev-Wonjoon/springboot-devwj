package net.likelion.bebc25.board3.repository;

import net.likelion.bebc25.board3.post.PostDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PureJdbcPostRepository implements PostRepository {

    private static String DB_URL = "jdbc:mysql://localhost:3306/board_db?serverTimezone=UTC&useSSL=false";
    private static String DB_USER = "root";
    private final String DB_PASSWORD;
    public PureJdbcPostRepository(@Value("${DB_PASSWORD}") String dbPassword) {
        this.DB_PASSWORD = dbPassword;
    }

    @Override
    public List<PostDto> findAll() {
        String sql = "SELECT id, title, content, author, secret, created_at AS createdAt FROM post2";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        List<PostDto> result = new ArrayList<>();
        try { // 플랜 A
            // 1. 데이터베이스 연결(Connection 객체 생성)
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // 2. SQL 실행 객체 생성(Statement 객체 생성)
            stmt = conn.createStatement();

            // 3. SQL 실행
            rs = stmt.executeQuery(sql);

            // 4. 결과 처리(ResultSet 사용)
            while (rs.next()) {
                PostDto post = new PostDto();
                post.setId(rs.getInt("id"));
                post.setTitle(rs.getString("title"));
                post.setAuthor(rs.getString("author"));
                post.setCreatedAt(rs.getObject("createdAt", LocalDateTime.class));
                post.setContent(rs.getString("content"));
                post.setSecret(rs.getBoolean("secret"));

                result.add(post);
            }

        } catch (Exception e) { // 플랜 B
            System.out.println("에러 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 5. 생성된 리소스를 생성의 역순으로 해제
            try {
                if (rs != null) rs.close();
            } catch (Exception e) {
            }
            try {
                if (stmt != null) stmt.close();
            } catch (Exception e) {
            }
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
            }
        }

        return result;
    }

    @Override
    public PostDto findById(long id) {
        String sql = "SELECT * FROM post2 WHERE id = ?;";
        try (
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    PostDto post = new PostDto();
                    post.setId(rs.getInt("id"));
                    post.setTitle(rs.getString("title"));
                    post.setAuthor(rs.getString("author"));
                    post.setCreatedAt(rs.getObject("createdAt", LocalDateTime.class));
                    post.setContent(rs.getString("content"));
                    post.setSecret(rs.getBoolean("secret"));

                    return post;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void save(PostDto postDto) {
        String sql = """
                INSERT INTO post (secret, title, content, created_at, author)
                VALUES (?, ?, ?, ?, ?)""";
        try (
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {

            stmt.setBoolean(1, postDto.isSecret());
            stmt.setString(2, postDto.getTitle());
            stmt.setString(3, postDto.getContent());
            stmt.setObject(4, postDto.getCreatedAt());
            stmt.setString(5, postDto.getAuthor());

            int result = stmt.executeUpdate();

            System.out.println(result + "개의 데이터가 저장되었습니다.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void update(PostDto postDto) {
        String sql = """
                UPDATE post2
                SET secret = ?,
                    title = ?,
                    content = ?,
                    created_at = ?,
                    author = ?
                WHERE id = ?;
                """;
        try (
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {

            stmt.setBoolean(1, postDto.isSecret());
            stmt.setString(2, postDto.getTitle());
            stmt.setString(3, postDto.getContent());
            stmt.setObject(4, postDto.getCreatedAt());
            stmt.setString(5, postDto.getAuthor());
            stmt.setLong(6, postDto.getId());

            int result = stmt.executeUpdate();

            if (result == 1) {
                System.out.println("게시글 수정 성공");
            } else {
                System.out.println("수정할 게시글이 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM post2 WHERE id = ?;";
        try (
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, id);

            int result = stmt.executeUpdate();

            if (result == 1) {
                System.out.println("게시글 삭제 성공");
            } else {
                System.out.println("삭제할 게시글이 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
