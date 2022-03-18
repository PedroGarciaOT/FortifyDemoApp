package com.microfocus.app.repository;

import com.microfocus.app.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> findAll() {
        String sqlQuery = "select * from products";
        return jdbcTemplate.query(sqlQuery, new ProductMapper());
    }

    public int count() {
        String sqlQuery = "select count(*) from products";
        return jdbcTemplate.queryForObject(sqlQuery, Integer.class);
    }

    public List<Product> findByName(String keywords) {
        String query = keywords.toLowerCase();
        String sqlQuery = "SELECT * FROM " + getTableName() +
                " WHERE lower(name) LIKE '%" + query + "%' " +
                " OR lower(summary) LIKE '%" + query + "%'" +
                " OR lower(description) LIKE '%" + query + "%'";
        return jdbcTemplate.query(sqlQuery, new ProductMapper());
    }

    String getTableName() {
        return Product.TABLE_NAME;
    }

}
