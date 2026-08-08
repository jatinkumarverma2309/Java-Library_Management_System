package com.smartlibrary.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LibraryService {
    private final JdbcTemplate jdbcTemplate;

    public LibraryService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addAuthor(int aId, String aName, String aEmail) {
        jdbcTemplate.update("INSERT INTO author VALUES (?, ?, ?)", aId, aName, aEmail);
    }

    public void addPublisher(int pId, String pName, String pEmail) {
        jdbcTemplate.update("INSERT INTO publisher VALUES (?, ?, ?)", pId, pName, pEmail);
    }

    public List<Map<String, Object>> searchAuthor(String name) {
        return jdbcTemplate.queryForList("SELECT * FROM author WHERE a_name LIKE ?", "%" + name + "%");
    }

    public List<Map<String, Object>> searchPublisher(String name) {
        return jdbcTemplate.queryForList("SELECT * FROM publisher WHERE p_name LIKE ?", "%" + name + "%");
    }

    public List<Map<String, Object>> getHistory(int memberId) {
        if (memberId == 0) {
            return jdbcTemplate.queryForList("SELECT * FROM history");
        } else {
            return jdbcTemplate.queryForList("SELECT * FROM history WHERE m_id = ?", memberId);
        }
    }

    public List<Map<String, Object>> getBorrows(int memberId) {
        if (memberId == 0) {
            return jdbcTemplate.queryForList("SELECT * FROM borrows");
        } else {
            return jdbcTemplate.queryForList("SELECT * FROM borrows WHERE m_id = ?", memberId);
        }
    }

    public void issueBook(int bId, int mId) {
        jdbcTemplate.update("INSERT INTO borrows (b_id, m_id, borrow_date, return_date) " +
                            "VALUES (?, ?, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 14 DAY))", bId, mId);
    }

    public void returnBook(int bId, int mId) {
        // Move to history
        jdbcTemplate.update("INSERT INTO history (m_id, b_id, borrow_date, return_date) " +
                            "SELECT m_id, b_id, borrow_date, CURDATE() FROM borrows WHERE b_id = ? AND m_id = ?", bId, mId);
        // Delete from borrows
        jdbcTemplate.update("DELETE FROM borrows WHERE b_id = ? AND m_id = ?", bId, mId);
    }
    
    public int checkUserInBorrows(int mId) {
        return jdbcTemplate.queryForObject("SELECT check_user_inBorrows(?)", Integer.class, mId);
    }
}
