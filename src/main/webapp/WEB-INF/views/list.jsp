<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>List</title>
        <link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
        <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
    </head>
    <body>
        <div class="generic-container">
            <div class="well lead">Product Order List</div>
            <table class="table table-hover table-striped">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>Name</th>
                        <th>Quantity</th>
                        <th>Operation</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${orderModelList}" varStatus="status">
                        <%-- create an url for edit --%>
                        <c:url var="editUrl" value="/order/update/${order.orderId}" />
                        <%-- create an url for delete --%>
                        <c:url var="deleteUrl" value="/order/delete/${order.orderId}" />
                        <%-- create an id value for delete --%>
                        <c:set var="deleteForm" value="order${status.count}" />
                        <%-- create a form for delete --%>
                        <form id="${deleteForm}" action="${deleteUrl}" method="POST"></form>
                        <tr>
                            <td>${status.count}</td>
                            <td><c:out value="${order.productName}" /></td>
                            <td><c:out value="${order.quantity}" /></td>
                            <td>
                                <a href='<c:out value="${editUrl}" />'>[EDIT]</a>
                                <a href="javascript:document.forms['${deleteForm}'].submit();">[Delete]</a>
                            </td>
                            <td><c:out value="${order.statusText}" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <span class="well floatRight">
                <a href="<c:url value='/order/add' />">Buy More</a>
            </span>
        </div>
    </body>
</html>
