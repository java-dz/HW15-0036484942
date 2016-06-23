<%@ page import="java.util.Objects"%>
<%@ page import="hr.fer.zemris.java.hw15.model.BlogUser"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <title>Working on blog entry (${option})<c:if test="${!form.title.isEmpty()}"> - ${form.title}</c:if></title>
  </head>
  <body>
    <form action="${option}" method="post">
    
      Title <input type="text" name="title" value='<c:out value="${form.title}"/>' size="80">
      <c:if test="${form.hasError('title')}">
      <div class="error"><c:out value="${form.getError('title')}"/></div>
      </c:if><br>

      <div>
        <label for="textarea">Text</label>
        <textarea name="text" rows="15" cols="82">${form.text}</textarea>
      </div>
      <c:if test="${form.hasError('text')}">
      <div class="error"><c:out value="${form.getError('text')}"/></div>
      </c:if><br>

      <input type="hidden" name="option" value="${option}">
      <input type="submit" value="Submit">
      
    </form>

    <div id="toolbar">
      <p><%=username%></p>
      <p><a href=".">Back</a></p>
      <p><a href="/blog/servleti/main">Home</a></p>
      <c:if test="<%= loggedIn %>">
      <p><a href="/blog/servleti/logout">Logout</a></p>
      </c:if>
    </div>
  </body>
</html>