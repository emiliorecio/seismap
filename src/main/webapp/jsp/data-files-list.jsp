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
<link rel="stylesheet" type="text/css"
  href="${baseUri}/resources/css/lib/extjs/css/ext-all.css" />
<script type="text/javascript"
  src="${baseUri}/resources/js/lib/extjs/ext-base-debug.js"></script>
<script type="text/javascript"
  src="${baseUri}/resources/js/lib/extjs/ext-all-debug.js"></script>
<script type="text/javascript" src="${baseUri}/resources/js/constants.js"></script>
<script type="text/javascript">
//<![CDATA[
    function load(file) {
      Ext.Ajax.request({
        url: 'http://localhost:7000/admin/load-data-file',
        method: 'POST',
        params: {file: file},
        success: function(response, opts) {
          var response = Ext.decode(response.responseText);
          Ext.Msg.alert('Data load', 'Datos cargados');
        },
        failure: function(response, opts) {
          Ext.Msg.alert('Data load', 'Uy.. hubo un error!');
        }
      });
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

