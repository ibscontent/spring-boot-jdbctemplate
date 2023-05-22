package com.uscode.demo.org.uscode.respository;

import com.uscode.demo.org.uscode.dto.Gift;
import com.uscode.demo.org.uscode.mapper.GiftRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class GiftJdbcRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(Gift gift) {
        String sql = "insert into Gift (id, name, price, created_date) values (?,?,?,?)";
        return jdbcTemplate.update(sql, gift.getId(), gift.getName(), gift.getPrice(), LocalDateTime.now());
    }

    public int getCount(){
        return jdbcTemplate.queryForObject("select count(*) from gift", Integer.class);
    }

    public Double getPriceById(Long id){
        String sql = "select price from gift where id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, Double.class);
    }

    public String getGiftNameById(Long id){
        String sql = "select name from gift where id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, String.class);
    }

   public List<Gift> findAllByRowMapper(){
        String sql = "select * from gift";
        return jdbcTemplate.query(sql, new GiftRowMapper());
    }

    public List<Gift> findAllUsingLambdaExpression(){
        String sql = "select * from gift";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new Gift(
                        rs.getLong("id"), rs.getString("name"), rs.getDouble("price"),
                        rs.getTimestamp("created_date").toLocalDateTime()
                ));
    }

    public List<Gift> findAllGiftsByBeanPropertyRowMapper(){
        String sql = "select * from gift";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Gift.class));
    }

    public List<Gift> findAllGiftsByQueryForList(){
        String sql = "select * from gift";
        List<Gift> res = new ArrayList<>();
        List<Map<String, Object>> records = jdbcTemplate.queryForList(sql);
        // Note: spring returns Integer and BigDecimal. Multiple steps of casting is needed.
        for(Map record : records){
            Gift gift = new Gift();
            gift.setId(((Integer) record.get("id")).longValue());
            gift.setName((String) record.get("name"));
            gift.setPrice(((BigDecimal) record.get("price")).doubleValue());
            gift.setCreatedDate(((Timestamp) record.get("created_date")).toLocalDateTime());
            res.add(gift);
        }
        return res;
    }

    public List<Gift> findAllGiftsOverPrice(Double price){
        String sql = "select * from gift where price > ?";
        return jdbcTemplate.query(sql, new Object[]{price}, new GiftRowMapper());
    }

    public List<Gift> findAllGiftsWithNameByRowMapper(String name){
        String sql = "select * from gift where name like ?";
        return jdbcTemplate.query(sql, new Object[]{"%"+name+"%"}, new GiftRowMapper());
    }

}

