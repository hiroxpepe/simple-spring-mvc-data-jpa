<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Total</title>
        <link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
        <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
    </head>
    <body>
        <div class="generic-container">
            <div class="well lead">Product Order List Statistics</div>
            <table class="table table-hover table-striped">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>Product name</th>
                        <th>Total order count</th>
                        <th>Total quantity</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="total" items="${totalModelList}" varStatus="status">
                        <tr>
                            <td>${status.count}</td>
                            <td><c:out value="${total.productName}" /></td>
                            <td><c:out value="${total.orderCount}" /></td>
                            <td><c:out value="${total.quantity}" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <span class="well floatRight">
                <a href="<c:url value='/index' />">Home</a>
            </span>
            <span class="well floatRight">
                <a href="<c:url value='order/list' />">Order list</a>
            </span>
            <span class="well floatRight">
                <a href="<c:url value='/order/add' />">Buy More</a>
            </span>
        </div>
    </body>
</html>
