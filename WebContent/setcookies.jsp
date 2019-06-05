<%
	String u = request.getRequestURL().toString();
	String t = request.getParameter("t");
	Cookie c = new Cookie("UserTicket", t);
	response.addCookie(c);
	response.sendRedirect(u.substring(0, u.lastIndexOf("/")));
%>