package com.uscode.demo.org.uscode.mapper;

import org.springframework.jdbc.core.RowMapper;
import com.uscode.demo.org.uscode.dto.Gift;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GiftRowMapper implements RowMapper<Gift> {

    @Override
    public Gift mapRow(ResultSet rs, int rowNum) throws SQLException {
        Gift gift = new Gift();
        gift.setId(rs.getLong("id"));
        gift.setName(rs.getString("name"));
        gift.setPrice(rs.getDouble("price"));
        gift.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
        return gift;
    }

}
