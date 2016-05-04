package com.janle.jms.product.dao;

import java.util.List;
import java.util.Map;

public interface ProductDao {
	public void insert(String name);

	public List<Map<String, Object>> findAll();

	public Map<String, Object> findById(int id);
}
