<%@page import="java.awt.Toolkit"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
  Object nick = session.getAttribute("current.user.nick");
  boolean loggedIn = (nick != null);

  String username;
  if (loggedIn) {
      username = session.getAttribute("current.user.fn") + " " + session.getAttribute("current.user.ln");
  } else {
      username = "Anonymous";
  }
%>

<html>
  <head>
    <link type="text/css" rel="stylesheet" href="<c:url value="/stylesheet.css" />" />
    <title>Register</title>
  </head>
  <body>
    <c:choose>
    <c:when test="<%= !loggedIn %>">
    <!-- When the user is not logged in -->

    <h3>Registration form</h3>
    <form action="register" method="post">

      First name <input type="text" name="fn" value='<c:out value="${form.firstName}"/>' size="30">
      <c:if test="${form.hasError('fn')}">
      <div class="error"><c:out value="${form.getError('fn')}"/></div>
      </c:if><br>

      Last name <input type="text" name="ln" value='<c:out value="${form.lastName}"/>' size="30">
      <c:if test="${form.hasError('ln')}">
      <div class="error"><c:out value="${form.getError('ln')}"/></div>
      </c:if><br>

      Nickname <input type="text" name="nick" value='<c:out value="${form.nick}"/>' size="30">
      <c:if test="${form.hasError('nick')}">
      <div class="error"><c:out value="${form.getError('nick')}"/></div>
      </c:if><br>

      Email <input type="text" name="email" value='<c:out value="${form.email}"/>' size="50">
      <c:if test="${form.hasError('email')}">
      <div class="error"><c:out value="${form.getError('email')}"/></div>
      </c:if><br>

      Password <input type="password" name="password" size="30">
      <c:if test="${form.hasError('password')}">
      <div class="error"><c:out value="${form.getError('password')}"/></div>
      </c:if><br>

      <input type="submit" name="method" value="Submit">

    </form>

    </c:when>
    <c:otherwise>
    <!-- When the user is not logged in -->

    <h3>Already registered</h3>
    <p>Our data show that you are already registered and logged in. To register another account, please <a href="logout">log out</a>.</p>

    </c:otherwise>
    </c:choose>


    <div id="toolbar">
      <p><%=username%></p>
      <p><a href="/blog/servleti/main">Home</a></p>
      <c:if test="<%= loggedIn %>">
      <p><a href="/blog/servleti/logout">Logout</a></p>
      </c:if>
    </div>

  </body>
</html>
