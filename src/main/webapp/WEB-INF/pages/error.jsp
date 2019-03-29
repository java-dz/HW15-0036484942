<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
  <title>Error</title>
  </head>
  <body>
    <h2>Error</h2>
    <p><%= request.getAttribute("error") %></p>
    <p>Go <a href="javascript: history.go(-1)">Back</a> or go <a href="/blog/servleti/main">Home</a></p>

    <div id="toolbar">
      <p><%=username%></p>
      <p><a href="javascript: history.go(-1)">Back</a></p>
      <p><a href="/blog/servleti/main">Home</a></p>
      <c:if test="<%= loggedIn %>">
      <p><a href="/blog/servleti/logout">Logout</a></p>
      </c:if>
    </div>
  </body>
</html>
