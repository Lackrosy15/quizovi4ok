<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body>
<%
    double num = Math.random(); if (num > 0.1) {
%>
<h2>Ты счастливчик, user!</h2>
<p>(<%= num %>)</p>
<%
    }
%>
</body>
</html>