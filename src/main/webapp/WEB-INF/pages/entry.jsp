<%@ page import="hr.fer.zemris.java.hw15.dao.DAOProvider"%>
<%@ page import="hr.fer.zemris.java.hw15.model.BlogUser"%>
<%@ page import="java.util.Objects"%>
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

  BlogUser author = (BlogUser) request.getAttribute("author");
%>

<html>
  <head>
    <link type="text/css" rel="stylesheet" href="<c:url value="/stylesheet.css" />" />
    <title>Blog entry<c:if test="${blogEntry!=null}"> - ${blogEntry.title}</c:if></title>
  </head>
  <body>
    <h2><c:out value="${blogEntry.title}"/></h2>
    <hr width="60%" align="left" />
    <div class="balloon">
      <p class="smaller">Posted by: ${blogEntry.creator.nick}</p>
      <pre><c:out value="${blogEntry.text}" escapeXml="false"/></pre>
      <div class="date">
        <p class="smaller">Date created: <c:out value="${blogEntry.createdAt}"/></p>
        <c:if test="${blogEntry.lastModifiedAt != null}"><p class="smaller"> | Last modified: <c:out value="${blogEntry.lastModifiedAt}"/></p></c:if>
        <c:if test='<%= Objects.equals(nick, author.getNick()) %>'><p class="smaller"> | <a href="/blog/servleti/author/${blogEntry.creator.nick}/edit?id=${blogEntry.id}">Edit</a></p></c:if>
      </div>
    </div>
    <br><br>
    <c:if test="${!blogEntry.comments.isEmpty()}">
    <table class="comments">
      <tr align="left" style="border-bottom: 1px solid #000;"><th>Number of comments: ${blogEntry.comments.size()}</th></tr>
        <c:forEach var="comment" items="${blogEntry.comments}">
        <tr><td class="balloon" id="balloon-comment">
          <p class="smaller">Posted by: <c:out value="${comment.usersEMail}"/></p>
          <p style="padding-left: 10px;"><c:out value="${comment.message}"/>
          <div class="date">
            <p class="smaller">Posted on: <c:out value="${comment.postedOn}"/></p>
            <c:if test='<%= Objects.equals(nick, author.getNick()) %>'>
            <p class="smaller"> | <a href="/blog/servleti/author/${blogEntry.creator.nick}/removeComment?commentID=${comment.id}&nick=<%=nick%>&entryID=${blogEntry.id}">Remove</a></p>
            </c:if>
          </div>
        </td></tr>
        </c:forEach>
    </table>
    </c:if>
    <form action="comment?id=${blogEntry.id}" method="post">
      <c:choose>
      <c:when test="<%= !loggedIn %>">
      Your E-mail <input type="text" name="email" size="30" value='<c:out value="${form.usersEMail}"/>' required>
      <c:if test="${form.hasError('email')}">
      <div class="error"><c:out value="${form.getError('email')}"/></div>
      </c:if><br>
      </c:when>
      <c:otherwise>
      <input type="hidden" name="email" value="<%= DAOProvider.getDAO().getUserByNick((String) nick).getEmail() %>">
      <input type="hidden" name="loggedIn" value="true">
      </c:otherwise>
      </c:choose>
      <input type="hidden" name="entryID" value="${blogEntry.id}">
      <div>
        <label for="textarea">Add comment</label>
        <textarea name="message" rows="4" cols="50" required>${form.message}</textarea>
        <c:if test="${form.hasError('message')}">
        <div class="error"><c:out value="${form.getError('message')}"/></div>
        </c:if><br>
      </div>

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
