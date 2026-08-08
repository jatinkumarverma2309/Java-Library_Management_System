package com.smartlibrary.repository;

import com.smartlibrary.model.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class BookRepository {
    private final JdbcTemplate jdbcTemplate;

    public BookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Book> rowMapper = (rs, rowNum) -> {
        Book b = new Book();
        b.setbId(rs.getInt("b_id"));
        b.setbName(rs.getString("b_name"));
        b.setGenre(rs.getString("genre"));
        b.setAisle(rs.getInt("aisle"));
        b.setlId(rs.getInt("L_id"));
        b.setaId(rs.getInt("A_id"));
        b.setpId(rs.getInt("P_id"));
        return b;
    };

    public List<Map<String, Object>> searchBooks(String name) {
        String sql = "SELECT b_id, b_name, genre, aisle FROM books WHERE b_name LIKE ?";
        return jdbcTemplate.queryForList(sql, "%" + name + "%");
    }

    public List<Map<String, Object>> findAllBooks() {
        return jdbcTemplate.queryForList("SELECT * FROM books");
    }

    public void insertBook(Book b) {
        String sql = "INSERT INTO books (b_id, b_name, genre, aisle, L_id, A_id, P_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, b.getbId(), b.getbName(), b.getGenre(), b.getAisle(), b.getlId(), b.getaId(), b.getpId());
    }

    public void deleteBook(int bId) {
        jdbcTemplate.update("DELETE FROM books WHERE b_id = ?", bId);
    }

    public void updateAisle(int bId, int aisle) {
        jdbcTemplate.update("UPDATE books SET aisle = ? WHERE b_id = ?", aisle, bId);
    }
}
