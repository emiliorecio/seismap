<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<title>SeisMap Server</title>

<script type="text/javascript"
	src="/js/mootools/mootools-core-1.3-full-nocompat.js"></script>
<script type="text/javascript">
//<![CDATA[
    function load(file) {
        var jsonRequest = new Request.JSON({url: '/view/admin/load-data-file', onSuccess: function(response){
               if (response) {
                   alert('Successfully loaded!');
               } else {
            	   alert('Problem while loading')
               }s
            }}).get({file: file});
    }
//]]>
</script>

</head>

<body onload="">
<table border="1">
    <c:if test="${message}!=null">
        <p><c:out value="${file.name}" /></p>
    </c:if>
    <h2>Pick a data file to load</h2>
	<tr>
        <th>File</th>
	</tr>
	<c:forEach items="${files}" var="file">
		<tr>
            <td><a href="javascript:load('${file.name}')"><c:out value="${file.name}" /></a></td>
		</tr>
	</c:forEach>

</table>
</body>
</html>

