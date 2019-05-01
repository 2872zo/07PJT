package com.model2.mvc.service.product;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;

public interface ProductService {
	public int addProduct(Product product); 
	
	public Product getProduct(int prodNo);
	
	public int updateProduct(Product product);
	
	public int deleteProduct(int prodNo);
	
	public Map<String,Object> getProductList(Search search);
}
