package com.janle.jms.product.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.janle.jms.product.dao.ProductDao;

@Repository
public class ProductDaoImpl implements ProductDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	@Transactional(readOnly = false)
	public void insert(final String name) {
		jdbcTemplate.update("insert into spring_jms_test(name) values(?)", name);
	}

	@Override
	public List<Map<String, Object>> findAll() {
		String sql = "select * from spring_jms_test";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Map<String, Object>>>() {
			@Override
			public List<Map<String, Object>> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				while (rs.next()) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", rs.getInt("id"));
					map.put("name", rs.getString("name"));
					list.add(map);
				}
				return list;
			}
		});

	}

	@Override
	public Map<String, Object> findById(int id) {
		String sql = "select * from spring_jms_test where id= ?";
		return jdbcTemplate.queryForMap(sql, id);
	}

}
