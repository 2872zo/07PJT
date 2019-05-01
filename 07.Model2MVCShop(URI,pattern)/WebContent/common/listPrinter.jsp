<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
						${title}											
					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


<c:import url="../common/searchPrinter.jsp">
	<c:param name="domainName" value="${param.domainName}"/>
</c:import>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="8" >전체 ${resultPage.totalCount} 건수, 현재 ${resultPage.currentPage} 페이지</td>
		<td align="right">
			표시 개수
			<select name="pageSize" class="ct_input_g" style="width:80px" id="pageSize" onchange="javascript:fncGet${param.domainName}List(${resultPage.currentPage});">
				<option ${(empty search || search.pageSize eq 3)?"selected='selected'":""}>3</option>
				<option value="5"  ${(!empty search && search.pageSize eq 5)?"selected='selected'":""}>5</option>
				<option value="10" ${(!empty search && search.pageSize eq 10)?"selected='selected'":""}>10</option>
				<option value="15" ${(!empty search && search.pageSize eq 15)?"selected='selected'":""}>15</option>
			</select>
		</td>
	</tr>
	<tr>
		<c:set var="i" value="0"/>
		<c:forEach var="columName" items="${columList}">
			<c:set var="i" value="${i+1}"/>
			<c:choose>
				<c:when test="${i eq 1}"><td class="ct_list_b" width="100"></c:when>
				<c:when test="${i eq 2 || i eq 3}"><td class="ct_list_b" width="150"></c:when>
				<c:otherwise><td class="ct_list_b"></c:otherwise>
			</c:choose>
			${columName}</td>
			<td class="ct_line02"></td>
		</c:forEach>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	<c:set var="i" value="0"/>
	<c:forEach var="list" items="${unitList}">
		<c:set var="i" value="${i+1}"/>
		<tr class="ct_list_pop">
			<c:forEach var="detailUnit" items="${list}">
				<td align="center">${detailUnit}</td>
				<td></td>
			</c:forEach>
		</tr>	
		<tr>
			<td colspan="11" bgcolor="D6D7D6" height="1"></td>
		</tr>
	</c:forEach>
</table>



