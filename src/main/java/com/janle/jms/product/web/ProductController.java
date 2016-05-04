package com.janle.jms.product.web;

import java.util.Date;

import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.janle.jms.product.dao.ProductDao;
import com.janle.jms.product.service.ProducerService;

@Controller
@RequestMapping("product")
public class ProductController {
	@Autowired
	@Qualifier("queueDestination")
	private Destination destination;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private ProducerService producerService;

	@RequestMapping("first")
	public String first(ModelMap model) {
		producerService.sendMessage(destination, "你好，现在是：" + new Date().toLocaleString());
		return "test/first";
	}

	@RequestMapping("all")
	@ResponseBody
	public Object findAll() {
		return productDao.findAll();
	}
}
