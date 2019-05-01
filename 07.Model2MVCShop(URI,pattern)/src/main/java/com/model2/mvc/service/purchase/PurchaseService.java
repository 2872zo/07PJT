package com.model2.mvc.service.purchase;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;

public interface PurchaseService {
	public int addPurchase(Purchase purchase); 
	
	public Purchase getPurchase(int tranNo);
	
	public int updatePurchase(Purchase purchase);
	
	public int updateTranCode(Purchase purchase);
	
	public int deletePurchase(int tranNo);
	
	public Map<String,Object> getPurchaseList(Search search);
}
