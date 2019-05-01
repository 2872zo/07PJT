package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDao;
import com.model2.mvc.service.purchase.PurchaseService;

@Service("purchaseService")
public class PurchaseServiceImpl implements PurchaseService {
	@Autowired
	@Qualifier("purchaseDao")
	private PurchaseDao purchaseDao;
	public void setPurchaseDao(PurchaseDao purchaseDao) {
		this.purchaseDao = purchaseDao;
	}

	public PurchaseServiceImpl() {
		System.out.println(this.getClass());
	}
	
	@Override
	public int addPurchase(Purchase purchase) {
		return purchaseDao.addPurchase(purchase);
	}

	@Override
	public Purchase getPurchase(int tranNo) {
		return purchaseDao.getPurchase(tranNo);
	}

	@Override
	public int updatePurchase(Purchase purchase) {
		return purchaseDao.updatePurchase(purchase);
	}
	
	@Override
	public int updateTranCode(Purchase purchase) {
		return purchaseDao.updateTranCode(purchase);
	}

	@Override
	public int deletePurchase(int tranNo) {
		return purchaseDao.deletePurchase(tranNo);
	}

	@Override
	public Map<String, Object> getPurchaseList(Search search) {
		List<Purchase> list = purchaseDao.getPurchaseList(search);
		int totalCount = purchaseDao.makeTotalCount(search);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("totalCount", totalCount);

		return map;
	}

}
