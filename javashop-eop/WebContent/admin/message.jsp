<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<div    class='success'>
	<c:forEach var="msg" items="${msgs}">
	  <h4>${msg }</h4>
	</c:forEach>     
  <div style="clear:both;"> 

   <c:forEach var="url" items="${blankUrls}">
      <p>
    	
		 
		    <a href="${url.value}" target="_blank" >${url.key}</a>
		    
		    
    </p>
    
    </c:forEach>
    
   <c:forEach var="url" items="${urls}">
      <p>
    	
		    
		    <a href="${url.value}" target="_self" >${url.key}</a>
		   
		    
    </p>
    
    </c:forEach>
    
   </div>
</div>
