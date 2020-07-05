<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Index</title>
        <link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
        <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
    </head>
    <body>
        <div class="generic-container">
            <div class="well lead">Welcome to the Shop</div>
            <span class="well floatRight">
                <a href="<c:url value='order/list' />">Order list</a>
            </span>
            <span class="well floatRight">
                <a href="<c:url value='order/add' />">Place an order</a>
            </span>
        </div>
    </body>
</html>
