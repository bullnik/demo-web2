<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${name}</title>
</head>
<body>
<h2>${date}</h2>
<h1>${path}</h1>
<hr width="300px" align="left">
<form method="POST">
    <div>
        <input type="submit" value="Выйти из аккаунта">
    </div>
</form>
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
                    <a href = "download?path=${r.getPath()}">${r.getName()}</a>
                </c:if>
                <c:if test="${r.isDirectory()}">
                    <span>📂</span>
                    <a href = "files?path=${r.getPath()}">${r.getName()}</a>
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