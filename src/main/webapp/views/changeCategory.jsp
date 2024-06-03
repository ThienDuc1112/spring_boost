<!DOCTYPE html>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Category</title>
</head>
<body>
    <h1>Change category of product: ${product.getProductName()}</h1>
        <form action="/products/updateProduct/${product.getId()}" method="POST" modelAttribute="product">
            <select name="categoryId" id="categoryId">
                <c:forEach var="category" items="${categories}">
                    <option value="${category.getId()}">${category.getCategoryName()}</option>
                </c:forEach>
            </select>
            <input type="submit" value="Update">
        </form>
</body>
</html>