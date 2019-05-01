<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<title>구매 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
	function fncGetPurchaseList(currentPage) {
		document.detailForm.currentPage.value = currentPage;
		document.detailForm.menu.value =  "${param.menu}";
		
		document.detailForm.submit();
	}
	
	function fncUpdatePurchaseCode(currentPage,tranNo){		
		var URI = "/purchase/updateTranCode?page="+currentPage+"&tranNo="+tranNo+"&tranCode=3";
		
		console.log(URI);
		
		location.href = URI;
	}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<form name="detailForm" action="/purchase/listPurchase" method="post">

<%-- list출력 부분 --%>
<c:import url="../common/listPrinter.jsp">
	<c:param name="domainName" value="Purchase"/>
</c:import>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
	<tr>
		<td align="center">
		 <input type="hidden" id="currentPage" name="currentPage" value=""/>
		 <input type="hidden" id="menu" name="menu" value=""/>
		
		 <c:import url="../common/pageNavigator.jsp">
			<c:param name="domainName" value="Purchase"/>
		</c:import>	
			
		</td>
	</tr>
</table>

<!--  페이지 Navigator 끝 -->
</form>

</div>

</body>
</html>