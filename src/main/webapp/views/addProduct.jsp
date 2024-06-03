<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Product</title>
</head>
<body>
    <h1>Add a new product</h1>
    <form:form action="/products/addProduct" method="POST" modelAttribute="product">
        <form:label path="productName">Product Name:</form:label>
        <form:input path="productName" type="text" />

        <form:label path="productYear">Product Year:</form:label>
        <form:input path="productYear" type="number" />

        <form:label path="price">Price:</form:label>
        <form:input path="price" type="number" step="0.01" />

        <form:label path="url">URL:</form:label>
        <form:input path="url" type="text" />
        <br />

        <form:label path="category">Category:</form:label>
        <select name="categoryId" id="categoryId">
            <c:forEach var="category" items="${categories}">
                <option value="${category.getId()}">${category.getCategoryName()}</option>
            </c:forEach>
        </select>

        <input type="submit" value="Add">
    </form:form>
</body>
</html>
