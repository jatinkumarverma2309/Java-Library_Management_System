package com.smartlibrary.repository;

import com.smartlibrary.model.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Member> rowMapper = (rs, rowNum) -> {
        Member m = new Member();
        m.setmId(rs.getInt("m_id"));
        m.setmName(rs.getString("m_name"));
        m.setmEmail(rs.getString("m_email"));
        m.setmPassword(rs.getString("m_password"));
        m.setContactInfo(rs.getString("contact_info"));
        m.setStreet(rs.getString("street"));
        m.setCity(rs.getString("city"));
        m.setZipcode(rs.getString("zipcode"));
        return m;
    };

    public Member findByIdAndPassword(int id, String password) {
        String sql = "SELECT * FROM member WHERE m_id = ? AND m_password = ?";
        List<Member> list = jdbcTemplate.query(sql, rowMapper, id, password);
        return list.isEmpty() ? null : list.get(0);
    }
    
    public List<Member> findAll() {
        return jdbcTemplate.query("SELECT * FROM member", rowMapper);
    }

    public void insertMember(Member m) {
        String sql = "INSERT INTO member (m_id, m_name, m_email, m_password, contact_info, street, city, zipcode) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, m.getmId(), m.getmName(), m.getmEmail(), m.getmPassword(), 
                            m.getContactInfo(), m.getStreet(), m.getCity(), m.getZipcode());
    }

    public Integer getNextId() {
        Integer max = jdbcTemplate.queryForObject("SELECT MAX(m_id) FROM member", Integer.class);
        return (max == null ? 0 : max) + 1;
    }
    
    public void resetPenalty(int mId) {
        jdbcTemplate.update("UPDATE borrows SET penalty = 0 WHERE m_id = ?", mId);
    }
    
    public void calculatePenalty(int mId) {
        jdbcTemplate.update("CALL calc_penalty(?)", mId);
    }
}
