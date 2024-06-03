<!DOCTYPE html>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Category</title>
</head>
<body>
    <style>
        table, th, td {
            border: 1px solid black;
        }
    </style>
    <h1>Category List</h1>
    <!-- <h3>Name: ${name}, Age: ${age}</h3> -->
    <table>
        <thead>
           <tr>
            <td>ID</td>
            <td>Name</td>
            <td>Description</td>
            <td>Action</td>
           </tr>
        </thead>
        <tbody>
            <c:forEach var="category" items="${categories}">
                <tr>
                    <td>${category.getId()}</td>
                    <td>${category.getCategoryName()}</td>
                    <td>${category.getDescription()}</td>
                    <td>
                        <a href="products/getProductsByCategoryId/${category.getId()}">View All Product</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>