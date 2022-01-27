<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>메인</title>
</head>
<body>
    <h1>메인</h1>
    <div>
        <form action="/result" method="post">
            <div>
                <label>
                    연도 :
                    <select name="year">
                        <c:forEach begin="2000" end="2022" var="year">
                            <option value="${year}">${year}년</option>
                        </c:forEach>
                    </select>
                </label>
                <label>
                    월 :
                    <select name="month">
                        <c:forEach begin="1" end="12" var="month">
                            <option value="${month}">${month}월</option>
                        </c:forEach>
                    </select>
                </label>
                <label>
                    지역 :
                    <select name="excd">
                        <c:forEach items="${requestScope.locationList}" var="item">
                            <option value="${item.excd}">${item.nm}</option>
                        </c:forEach>
                    </select>
                </label>
                <input type="submit" value="검색">
            </div>
        </form>
    </div>
</body>
</html>