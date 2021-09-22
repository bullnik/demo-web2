<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Stas74
  Date: 21.09.2021
  Time: 16:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Devcolibri.com</title>
</head>
<body>
<h2>${date}</h2>
<h1>${path}</h1>
<hr width="300px" align="left">
<a href="files?path=${pathAdv}">Вверх</a>
<table cellspacing="7">
    <tr>
        <th>Файл</th>
        <th>Размер</th>
    </tr>
    <c:forEach var="r" items="${files}">
        <tr>
            <td>
                <c:if test="${!r.isDirectory()}">
                    <span>📝</span>
                    <a href = "download?path=${r.getPath().replace('\\','/')}">${r.getName()}</a>
                </c:if>
                <c:if test="${r.isDirectory()}">
                    <span>📂</span>
                    <a href = "files?path=${r.getPath().replace('\\','/')}">${r.getName()}</a>
                </c:if>
            </td>

        <td>
            <c:if test="${!r.isDirectory()}">
                ${r.length()} byte
            </c:if>
        </td>
    </tr>
    </c:forEach>
</table>
</body>
</html>