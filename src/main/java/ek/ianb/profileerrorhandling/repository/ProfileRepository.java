package ek.ianb.profileerrorhandling.repository;


import ek.ianb.profileerrorhandling.model.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ProfileRepository {

    private final JdbcTemplate jdbc;

    private final RowMapper<Profile> mapper = (rs, i) ->
            new Profile(rs.getInt("id"), rs.getString("name"), rs.getString("email"));

    public ProfileRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Profile> findAll() {
        return jdbc.query("SELECT id,name,email FROM profiles ORDER BY id DESC", mapper);
    }

    public Profile findById(int id) {
        var list = jdbc.query("SELECT id,name,email FROM profiles WHERE id=?", mapper, id);
        return list.isEmpty() ? null : list.get(0);
    }

    public Profile insert(Profile p) {
        String sql = "INSERT INTO profiles(name,email) VALUES (?,?)";
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, p.getName());
            ps.setString(2, p.getEmail());
            return ps;
        }, kh);
        p.setId(kh.getKey().intValue());
        return p;
    }

    public int update(Profile p) {
        return jdbc.update("UPDATE profiles SET name=?, email=? WHERE id=?",
                p.getName(), p.getEmail(), p.getId());
    }

    public int deleteById(int id) {
        return jdbc.update("DELETE FROM profiles WHERE id=?", id);
    }
}