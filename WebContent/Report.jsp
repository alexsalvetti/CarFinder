<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- 

-->

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta content="IE=edge" http-equiv="X-UA-Compatible">
	<meta content="initial-scale=1.0, width=device-width" name="viewport">
	<title>Menù</title>

	<!-- css -->
	<link href="css/base.min.css" rel="stylesheet">

	<!-- favicon -->
	<!-- ... -->

	<!-- ie -->
		<!--[if lt IE 9]>
			<script src="js/html5shiv.js" type="text/javascript"></script>
			<script src="js/respond.js" type="text/javascript"></script>
		<![endif]-->
</head>
<body>
	<header class="header">
		<a class="header-logo" href="index.jsp"><span class="icon icon-home"></span> Menù</a>
		<ul class="nav nav-list pull-right">
			<li>
				<a class="menu-toggle" href="#profile">
					<span class="access-hide"></span>
					<span class="avatar avatar-sm"><img alt="alt text for John Smith avatar" src="images/users/avatar-001.jpg"></span>
					<span class="header-close icon icon-close icon-lg"></span>
				</a>
			</li>
		</ul>
	</header>
	<%
		//permette l'accesso alla pagina solo se esiste la sessione
		String usern = null;
		if (session.getAttribute("targa") == null) {
			response.sendRedirect("LoginPage.jsp");
		} else
			usern = (String) session.getAttribute("targa");
		String Targa = null;
		String sessionID = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("targa"))
					Targa = cookie.getValue();
				if (cookie.getName().equals("JSESSIONID"))
					sessionID = cookie.getValue();
			}
		}
	%>
	<nav class="menu menu-right" id="profile">
		<div class="menu-scroll">
			<div class="menu-wrap">
				<div class="menu-top">
					<div class="menu-top-img">
						<img alt="John Smith" src="images/samples/landscape.jpg">
					</div>
					<div class="menu-top-info">
						<a class="menu-top-user"><span class="avatar pull-left"><img alt="alt text for John Smith avatar" src="images/users/avatar-001.jpg"></span><%=session.getAttribute("nome") %></a>
					</div>
				</div>
				<div class="menu-content">
					<ul class="nav">
						<li>
							<a href="Utente.jsp"><span class="icon icon-account-box"></span>Profilo utente</a>
						</li>
						<li>
						<form name="logoutForm" method="POST" action="LogoutServlet">
							<input type="hidden" name="param1" value="Logout">
							<A HREF="javascript:document.logoutForm.submit()"><span class="icon icon-exit-to-app"></span>Logout</A>
						</form> 
					</ul>
				</div>
			</div>
		</div>
	</nav>
	<div class="content">
		<div class="content-heading">
			<div class="container">
				<h1 class="heading">Report</h1>
			</div>
		</div>
		<div class="content-inner">
			<div class="container">
				<div class="row">
					<div class="col-lg-8 col-md-15">
						<h2 class="content-sub-heading">Ultimi tragitti veicolo</h2>
						<div class="table-responsive">
							<table class="table table-hover table-stripe" title="Tragitto">
								<thead>
									<tr>
									    <th>Tragitto</th>
										<th>Posizione di Partenza</th>
										<th>Posizione di Arrivo</th>
									</tr>
								</thead>
								<tbody>
									<tr>
								
									<td>1</td>
										<td><%=session.getAttribute("init1")%></td>
										<td><%=session.getAttribute("finale1")%></td>
									</tr>
									<tr>
										<td>2</td>
										<td><%=session.getAttribute("init2")%></td>
										<td><%=session.getAttribute("finale2")%></td>
									</tr>
									<tr>
										<td>3</td>
										<td><%=session.getAttribute("init3")%></td>
										<td><%=session.getAttribute("finale3")%></td>
									</tr>
									<tr>
										<td>4</td>
										<td><%=session.getAttribute("init4")%></td>
										<td><%=session.getAttribute("finale4")%></td>
									</tr>
									<tr>
										<td>5</td>
										<td><%=session.getAttribute("init5")%></td>
										<td><%=session.getAttribute("finale5")%></td>
									</tr>
								</tbody>
							</table>
						</div>
						
						
							
						
					</div>
				</div>
			</div>
		</div>
	</div>
	<footer class="footer">
		<div class="container">
			<p>αlfada progetti</p>
		</div>
	</footer>	

	<script src="js/base.min.js" type="text/javascript"></script>
</body>
</html>