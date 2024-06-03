<!DOCTYPE html>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product</title>
</head>
<body>
    <h1>Product List</h1>
    <table>
        <thead>
           <tr>
            <td>name</td>
            <td>year</td>
            <td>Price</td>
            <td>url</td>
            <td>action</td>
           </tr>
        </thead>
        <tbody>
            <c:forEach var="product" items="${products}">
                <tr>
                    <td>${product.getProductName()}</td>
                    <td>${product.getProductYear()}</td>
                    <td>${String.format("%.2f",product.getPrice())}</td>
                    <td>
                       ${product.getUrl()}
                    </td>
                    <td>
                        <a href="/products/changeCategory/${product.getId()}">change</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <a href="../../categories">back</a>
</body>
</html>