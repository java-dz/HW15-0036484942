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
    <title>Main</title>
  </head>
  <body>
    <c:if test="<%= !loggedIn %>">
    <!-- When the user is not logged in -->

    <h3>Have an account? Login here:</h3>
    <form action="main" method="post">

      Nickname <input type="text" name="nick" value='<c:out value="${form.nick}"/>' size="30">
      <c:if test="${form.hasError('nick')}">
      <div class="error"><c:out value="${form.getError('nick')}"/></div>
      </c:if><br>

      Password <input type="password" name="password" size="30">
      <c:if test="${form.hasError('password')}">
      <div class="error"><c:out value="${form.getError('password')}"/></div>
      </c:if><br>

      <input type="submit" value="Login">

    </form>

    <p>New on this page? Register <a href="register">here</a></p>
    </c:if>

    <h3>Blog authors</h3>
    <table class="values-table">
      <c:forEach var="user" items="${users}">
      <tr><td class="button"><a href="author/${user.nick}">${user.nick}</a></td></tr>
      </c:forEach>
    </table>


    <div id="toolbar">
      <c:choose>
      <c:when test="<%= loggedIn %>">
      <p>Welcome, <a href="author/<%=nick%>"><%=username%></a>!</p>
      <p><a href="/blog/servleti/logout">Logout</a></p>
      </c:when>
      <c:otherwise>
      <p><%=username%></p>
      </c:otherwise>
      </c:choose>
    </div>
  </body>
</html>
