package com.smartlibrary.repository;

import com.smartlibrary.model.Librarian;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LibrarianRepository {
    private final JdbcTemplate jdbcTemplate;

    public LibrarianRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Librarian> rowMapper = (rs, rowNum) -> {
        Librarian lib = new Librarian();
        lib.setLibId(rs.getInt("lib_id"));
        lib.setLibName(rs.getString("lib_name"));
        lib.setLibPassword(rs.getString("lib_password"));
        return lib;
    };

    public Librarian findByIdAndPassword(int id, String password) {
        String sql = "SELECT * FROM LIBRARIAN WHERE lib_id = ? AND lib_password = ?";
        List<Librarian> list = jdbcTemplate.query(sql, rowMapper, id, password);
        return list.isEmpty() ? null : list.get(0);
    }
}
