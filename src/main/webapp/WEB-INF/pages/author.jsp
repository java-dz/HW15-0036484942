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
    <title>Author: ${author.nick}</title>
  </head>
  <body>
    <h1>Author: ${author.nick}</h1>
    <h3>Blog entries</h3>
    <hr width="60%" align="left"/>
    <table class="values-table">
      <c:forEach var="entry" items="${author.blogEntries}">
      <tr class="button">
        <td><a href="/blog/servleti/author/${author.nick}/${entry.id}">${entry.title}</a></td>
        <c:if test="${editFlag}"><td><a href="/blog/servleti/author/${author.nick}/edit?id=${entry.id}">Edit</a></td></c:if>
      </tr>
      </c:forEach>
    </table>
    
    <c:if test="${authorOptions}">
    <div class="author-options">
      <p><a href="/blog/servleti/author/${author.nick}/new">New</a></p>
      <p><a href="/blog/servleti/author/${author.nick}/edit">Edit</a></p>
    </div>
    </c:if>


    <div id="toolbar">
      <p><%=username%></p>
      <p><a href="/blog/servleti/main">Home</a></p>
      <c:if test="<%= loggedIn %>">
      <p><a href="/blog/servleti/logout">Logout</a></p>
      </c:if>
    </div>
  </body>
</html>
