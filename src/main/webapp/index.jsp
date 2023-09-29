<!DOCTYPE html>
<html lang="en" xmlns:th="http://thymeleaf.org">
<head>

    <meta charset="UTF-8">
    <title>Main Page</title>
    <style>
        .btn {
            display: inline-block; /* Строчно-блочный элемент */
            background: #8C959D; /* Серый цвет фона */
            color: #ffffff; /* Белый цвет текста */
            padding: 1rem 1.5rem; /* Поля вокруг текста */
            text-decoration: none; /* Убираем подчёркивание */
            border-radius: 3px; /* Скругляем уголки */
        }
    </style>

</head>
<body>
<div>
    <button onclick="document.location='http://localhost:8080/users'"
            class="btn">Show All Users
    </button>
    <button onclick="document.location='http://localhost:8080/books'"
            class="btn">Show All Books
    </button>
</div>


</body>
</html>


