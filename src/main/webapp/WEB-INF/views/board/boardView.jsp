<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@include file="/WEB-INF/views/common/common.jsp"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>boardView</title>
</head>
<!-- Yi's code -->
<script type="text/javascript">
$j(document).ready(function(){ 

	$j("#btn_delete").on("click",function(){
		var $frm = $j('.boardView :input');
		var param = $frm.serialize(); 
		alert(param); 
		$j.ajax({
			// (Yi's code)
			// to send send-file form-data to ajax, we need to set enctype, processData, ContentType
	 		// enctype : 'multipart/form-data',
			// processData : false,
			// contentType : false,
			// cache : false,   
			//
		    url : "/board/boardDelete.do",
		    dataType: "json",
		    type: "POST",
		    data : param,
		    success: function(data, textStatus, jqXHR)
	  	 	{ 	
				alert("�����Ϸ�");
				alert("�޼���:"+data.success);
				location.href = "/board/boardList.do";  
			
	    	},
	    	error: function (jqXHR, textStatus, errorThrown)
	    	{
	    		alert("����");
	   	 	} 
		});
	}); 
});

</script>


<body>
<form class="boardView" id="boardView" name="boardView" action="board/boardDelete.do" >  <!-- (Yi's modification): original code: <form class="boardWrite"> -->
	<table align="center">
		<tr>
			<td align="right"> 																<!-- I'm not sure whehter I can leave pageNo=1 instead of pageNo=${pageNo}   '/board/${board.boardType}/${board.boardNum}/boardUpdate.do?pageNo=${pageNo}'> -->
			<input id ="btn_update" type="button" class="btn_update" value="����" onclick="location.href='/board/${board.boardType}/${board.boardNum}/boardUpdate.do?pageNo=1';" />
			<input id="btn_delete" type="button" class="btn_delete" value="����">
			<input type= "hidden" name="boardNum" value="${board.boardNum}">		
			</td>
		</tr>
		<tr>
			<td>
				<table border ="1"> 
				
					<tr>
						<td width="120" align="center">
						Title
						</td>
						<td width="400">
						<input name="boardTitle" type="text" size="50" value="${board.boardTitle}"> 
						</td>
					</tr>
					<tr>
						<td height="300" align="center">
						Comment
						</td>
						<td valign="top">
						<textarea name="boardComment"  rows="20" cols="55">${board.boardComment}</textarea>
						</td>
					</tr>
					<tr>
						<td align="center">
						Writer
						</td>
						<td>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="right">
				<a href="/board/boardList.do">List</a>
			</td>
		</tr>
	</table>
</form>	
</body>
</html>