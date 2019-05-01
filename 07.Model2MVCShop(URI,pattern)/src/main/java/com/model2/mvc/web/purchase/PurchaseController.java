package com.model2.mvc.web.purchase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;

@Controller
@RequestMapping("/purchase/*")
public class PurchaseController {
	@Autowired
	@Qualifier("purchaseService")
	PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productService")
	ProductService productService;

	@Value("#{commonProperties['pageUnit']}")
	// @Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;

	@Value("#{commonProperties['pageSize']}")
	// @Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;

	public PurchaseController() {
		System.out.println(this.getClass());
	}

	@RequestMapping("addPurchase")
	public ModelAndView addPurchase(@ModelAttribute("purchase") Purchase purchase, HttpSession session, @RequestParam("prodNo") int prodNo) throws Exception {
		User user = (User) session.getAttribute("user");
		Product product = new Product();
		product.setProdNo(prodNo);
		
		//puchase 설정
		purchase.setBuyer(user);
		purchase.setPurchaseProd(product);
		purchase.setTranCode("1");
		
		//실행
		purchaseService.addPurchase(purchase);
		
		
		return new ModelAndView("forward:/purchase/confirmPurchase.jsp");
	}
	
	@RequestMapping("addPurchaseView")
	public ModelAndView addPurchaseView(@RequestParam("prodNo") int prodNo) throws Exception {		
		Product product = null;
		System.out.println("전 : " + product);
		product = productService.getProduct(prodNo);
		System.out.println("후 : " + product);	
		
		ModelAndView modelAndView = new ModelAndView("forward:/purchase/addPurchaseView.jsp");
		modelAndView.addObject("product", product);
		
		return modelAndView;
	}

	@RequestMapping("getPurchase")
	public ModelAndView getPurchase(@RequestParam("tranNo") int tranNo) throws Exception {
		System.out.println("\n==>getPurchase Start.........");
		
		Purchase purchase = purchaseService.getPurchase(tranNo);
				
		List<String> purchaseList = purchase.toList();
		System.out.println("getPurchaseAction의 getTranCode값 : "+purchase.getTranCode());
		
		ModelAndView modelAndView = new ModelAndView("forward:/purchase/getPurchase.jsp");
		modelAndView.addObject("list", purchaseList);
		modelAndView.addObject("purchase", purchase);
		
		System.out.println("\n==>getPurchase End.........");
		
		return modelAndView;
	}
	
	@RequestMapping(value="listPurchase")
	public ModelAndView getPurchaseList(@RequestParam(value = "page", defaultValue = "1") int page, 
								  @RequestParam(value = "currentPage", defaultValue= "1") int currentPage,
								  HttpServletRequest request, HttpSession session) throws Exception {
		
		System.out.println("\n==>listPurchase Start.........");
		
		if(request.getMethod().equals("GET")) {
			currentPage = page;
		}
		
		
				
		User user = (User)session.getAttribute("user");		

		//3.DB 접속을 위한 search
		Search search = new Search();
		search.setCurrentPage(currentPage);
		search.setPageSize(pageSize);
		search.setUserId(user.getUserId());

		///4.DB에 접속하여 결과값을 Map으로 가져옴
		Map<String, Object> map = purchaseService.getPurchaseList(search);

		
		///5.pageView를 위한 객체
		Page resultPage = new Page(currentPage, ((Integer) map.get("totalCount")).intValue(),
				pageUnit, pageSize);
		
		System.out.println("ListPurchaseAction-resultPage : " + resultPage);
		System.out.println("ListPurchaseAction-list.size() : " + ((List)map.get("list")).size());
		
		
		///6.JSP에 출력을 하기위한 설정들
		//title 설정
		String title = "구매 목록 조회";
		
		//colum 설정
		List<String> columList = new ArrayList<String>();
		columList.add("No");
		columList.add("회원ID");
		columList.add("회원명");
		columList.add("전화번호");
		columList.add("거래상태");
		columList.add("정보수정");
		
		//UnitList 설정
		List unitList = makePurchaseList(currentPage, (List<Purchase>)map.get("list"), user);
		
		
		//출력을 위한 Object
		ModelAndView modelAndView = new ModelAndView("forward:/purchase/listPurchase.jsp");
		modelAndView.addObject("title", title);
		modelAndView.addObject("columList", columList);
		modelAndView.addObject("unitList", unitList);
		modelAndView.addObject("resultPage", resultPage);

		System.out.println("\n==>listPurchase End.........");
		
		return modelAndView;
	}

	
	@RequestMapping("updatePurchase")
	public ModelAndView updatePurchase(@ModelAttribute Purchase purchase, Map<String,Object> map) throws Exception {
		System.out.println("\n==>updatePurchase Start.........");
			
		purchaseService.updatePurchase(purchase);
		
		ModelAndView modelAndView = new ModelAndView("forward:/purchase/getPurchase");
		modelAndView.addObject("tranNo", purchase.getTranNo());
		
		System.out.println("\n==>updatePurchase End.........");
		return modelAndView;
	}

	@RequestMapping("updatePurchaseView")
	public ModelAndView updatePurchaseView(@RequestParam("tranNo") int tranNo, Map<String, Object> map) throws Exception {
		Purchase purchase = purchaseService.getPurchase(tranNo);

		ModelAndView modelAndView = new ModelAndView("forward:/purchase/updatePurchaseView.jsp");
		modelAndView.addObject("purchase", purchase);

		return modelAndView;
	}
	
	@RequestMapping("updateTranCode")
	public ModelAndView updateTranCode(@RequestParam("tranCode") String tranCode, @RequestParam("tranNo") int tranNo) throws Exception{
		Purchase purchase = new Purchase();
		purchase.setTranNo(tranNo);
		purchase.setTranCode(tranCode);

		System.out.println(purchase);
		
		purchaseService.updateTranCode(purchase);
		
		return new ModelAndView("forward:/purchase/listPurchase?");
	}
	
	@RequestMapping("updateTranCodeByProd")
	public ModelAndView updateTranCodeByProd(@RequestParam("prodNo") int prodNo, @RequestParam("tranCode") String tranCode, 
			@RequestParam("page") String page, @RequestParam("menu") String menu, Search search) throws Exception{		
		Product product = new Product();
		Purchase purchase = new Purchase();
		product.setProdNo(prodNo);
		purchase.setPurchaseProd(product);
		purchase.setTranCode(tranCode);

		purchaseService.updateTranCode(purchase);

		ModelAndView modelAndView = new ModelAndView("forward:/product/listProduct?");
		
		modelAndView.addObject("page", page);
		modelAndView.addObject("tranCode", tranCode);
		modelAndView.addObject("menu", menu);
		if(search.getSearchCondition() != null) {
			modelAndView.addObject("searchCondition", search.getSearchCondition());
			modelAndView.addObject("searchKeyword", search.getSearchKeyword());
		}
		
		return modelAndView;
	}
	
	@RequestMapping("cancelPurchase")
	public ModelAndView cancelPurchase(@RequestParam("tranNo") int tranNo) {
		Purchase purchase = new Purchase();
		purchase.setTranNo(tranNo);
		purchase.setTranCode("0");

		purchaseService.updateTranCode(purchase);

		return new ModelAndView("forward:/puchase/listPurchase");
	}
	
	private List makePurchaseList(int currentPage, List<Purchase> purchaseList, User user) {

		List<List> unitList = new Vector<List>();
		List<String> UnitDetail = null;
		for(int i =0; i<purchaseList.size();i++) {
			UnitDetail = new Vector<String>();
			
			String aTag = "<a href='/purchase/getPurchase?tranNo="+purchaseList.get(i).getTranNo()+"'>";
			String aTagEnd = "</a>";
			UnitDetail.add(aTag + String.valueOf(i+1) + aTagEnd);
			
			String getUserTagStart = "<a href='/user/getUser?userId="+user.getUserId()+"'>";
			String getUserTagEnd = "</a>";
			UnitDetail.add(getUserTagStart + purchaseList.get(i).getBuyer().getUserId() + getUserTagEnd);
			
			UnitDetail.add(purchaseList.get(i).getReceiverName() != null? purchaseList.get(i).getReceiverName():"");
			UnitDetail.add(purchaseList.get(i).getReceiverPhone() != null? purchaseList.get(i).getReceiverPhone():"");
			
			//tranCode에 따른 상태값
			switch(purchaseList.get(i).getTranCode()) {
			case "0":
				UnitDetail.add("구매취소");
				break;
			case "1":
				UnitDetail.add("배송준비중");
				break;
			case "2":
				UnitDetail.add("배송중");
				break;
			case "3":
				UnitDetail.add("거래완료");
				break;
			}
			//배송중인 경우에만 수취완료 표기
			if(purchaseList.get(i).getTranCode().equals("2")) {
				UnitDetail.add("<a href='javascript:fncUpdatePurchaseCode(" + currentPage + "," + purchaseList.get(i).getTranNo() + ");'>" + "수취확인</a>");
			}
			unitList.add(UnitDetail);
		}
		
		return unitList;
	}
}

