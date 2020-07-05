<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order</title>
        <link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
        <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
    </head>
    <body>
        <div class="generic-container">
            <div class="well lead">Product Order Page</div>
            <form:form method="POST" modelAttribute="orderForm" class="form-horizontal">
                <form:hidden path="status" />
                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-3 control-lable" for="productName">Product Name</label>
                        <div class="col-md-7">
                            <form:input type="text" path="productName" id="productName" class="form-control input-sm" required="required"/>
                            <div class="has-error">
                                <form:errors path="productName" class="help-inline"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-3 control-lable" for="quantity">Quantity</label>
                        <div class="col-md-7">
                            <form:input type="text" path="quantity" id="quantity" class="form-control input-sm" required="required"/>
                            <div class="has-error">
                                <form:errors path="quantity" class="help-inline"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-actions floatRight">
                        <c:if test='${state == "insert"}'>
                            <input type="submit" value="Place Order" class="btn btn-primary btn-sm"/> or <a href="<c:url value='/index' />">Cancel</a>
                        </c:if>
                        <c:if test='${state == "update"}'>
                            <input type="submit" value="Update Order" class="btn btn-primary btn-sm"/> or <a href="<c:url value='/index' />">Cancel</a>
                        </c:if>
                    </div>
                </div>
            </form:form>
        </div>
    </body>
</html>
