<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page session="true"%>
<c:set var="baseUrl" value="${pageContext.request.contextPath}"
  scope="request" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Seismap <c:if test="${fn:length(param.title) > 0}"> - <c:out
    value="${param.title}" />
</c:if></title>
<link rel="shortcut icon" href="${baseUrl}/resources/icon/seismap.ico"
  type="image/x-icon"></link>
<link rel="stylesheet" href="${baseUrl}/resources/css/seismap.css" type="text/css"></link>
<link href="${baseUrl}/resources/css/lib/moodialog/MooDialog.css"
  rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript"
  src="${baseUrl}/resources/js/lib/mootools/mootools-core-1.3.2-full-compat.js"></script>
<script type="text/javascript"
  src="${baseUrl}/resources/js/lib/mootools/mootools-more-1.3.2.1.js"></script>
<script src="${baseUrl}/resources/js/lib/moodialog/MooDialog.js"
  type="text/javascript"></script>
<script src="${baseUrl}/resources/js/lib/moodialog/MooDialog.Alert.js"
  type="text/javascript"></script>
<script src="${baseUrl}/resources/js/lib/moodialog/MooDialog.Error.js"
  type="text/javascript"></script>
<script src="${baseUrl}/resources/js/lib/moodialog/MooDialog.Confirm.js"
  type="text/javascript"></script>
<script src="${baseUrl}/resources/js/lib/moodialog/MooDialog.Prompt.js"
  type="text/javascript"></script>
<script src="${baseUrl}/resources/js/lib/moodialog/Overlay.js"
  type="text/javascript"></script>
<script src="${baseUrl}/resources/js/lib/moodialog/MooDialog.Fx.js"
  type="text/javascript"></script>
<script type="text/javascript" src="${baseUrl}/resources/js/seismap.js"></script>
<script type="text/javascript">
seismap.init('${baseUrl}', ${param.page}, {id: 1});
</script>
