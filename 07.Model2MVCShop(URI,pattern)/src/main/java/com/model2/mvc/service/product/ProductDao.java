package com.model2.mvc.service.product;

import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;

public interface ProductDao {
	public int addProduct(Product product); 
	
	public Product getProduct(int prodNo);
	
	public int updateProduct(Product product);
	
	public int deleteProduct(int prodNo);
	
	public List<Product> getProductList(Search search);

	public int makeTotalCount(Search search);
}
