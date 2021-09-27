<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@include file="/WEB-INF/views/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>list</title>
</head>
<script type="text/javascript">
	
 $j(function() {
 	$j("#allCheck").click(function() {
 		alert("allCheck clicked!"); 
 		if($j("#allCheck").prop("checked")){
 			$j("input[type=checkbox]").prop("checked", true);
 		}
 		else{
 			$j("input[type=checkbox]").prop("checked",false);
 		}
 	});
 });
 
 $j(document).ready(function() {
	 var codeNameLength = ${fn:length(codeName)};
	 $j(".check").click(function(){
		 /* if($j(this).prop('checked')) {
			 $j('input[type="checkbox"][name="allCheck"]').prop("checked", false);
			 $j(this).prop("checked", true); 
		 } */
		 if($j("input[name='codeId']:checked").length == codeNameLength){
			 $j("#allCheck").prop("checked", true);
		 }else{
			 $j("#allCheck").prop("checked", false);
		 } 
	 });
 }); 
 $j(document).ready(function() {
 
	 $j("#btn_read").click(function() {
		 $j('#selectKind').submit(); 
	 });
 });
 	

 
</script>
<body>
<!-- <form class="boardList" id="boardList" name="boardList" action="board/boardList.do">   -->

<table  align="center">
	<tr>
		<td align="left">
			<a href ="/board/boardLogin.do">login</a>
			<a href ="/board/boardRegister.do">join</a>
		</td>
		
		<td align="right">
			total : ${totalCnt}
		</td>
	</tr>
	<tr>
		<td>
			<table id="boardTable" border = "1">
				<tr>
					<td width="80" align="center">
						Type
					</td>
					<td width="40" align="center">
						No
					</td>
					<td width="300" align="center">
						Title
					</td>
				</tr>
				<c:forEach items="${boardList}" var="list">
					<tr>
						<td align="center">
							${list.codeName} <!-- original: ${list.boardType} -->
						</td>
						<td>
							${list.boardNum}
						</td>
						<td>
							<a href = "/board/${list.boardType}/${list.boardNum}/boardView.do?pageNo=${pageNo}">${list.boardTitle}</a>
						</td>
					</tr>	
				</c:forEach>
				
			</table>
		</td>
	</tr>
		<td align="right">
			<a href ="/board/boardWrite.do">�۾���</a>
		</td>
	<tr>
		
		<td align="left">
			<br/> 
			<form id="selectKind" class="selectKind" method="get" action="/board/boardList.do">
				<input type="checkbox" class="check"  id="allCheck" name="allCheck">��ü
				<c:forEach items="${selectKindList}" var="comCode" varStatus="status">
					<input type="checkbox" class="check" name="codeId" id="codeId" value="${comeCode.codeId}">${comCode.codeName}
				</c:forEach>
			
				<input type="button" id="btn_read" class="btn_read" name="btn_read" value="Temporary">
			</form> 
		</td>

	</tr>
		
</table>	
<!-- </form> -->
</body>

</html>