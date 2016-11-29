<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- 
Menu principale della Web-App, che viene caricato subito dopo il Login.
Permette di accedere alle diverse sezioni, quali "Localizza veicolo" nella quale
si potrà vedere la simulazione di un veicolo in movimento; "Profilo Utente",
dove si vedrà il report dei dati di registrazione dell'utente e dove è 
possibile cambiare la propria password; "Settaggio Soglie", dove è possibile
settare la prima e seconda soglia utente, in modo che al superamente di essere,
nella sezione "Localizzazione", verrà inviata una mail nel caso di superamento
della prima ed una simulazione di invio SMS per la seconda (quindi un presunto
furto di auto; infine il menu "Telecamera di sorveglianza" dove si vedrà il 
video del presunto ladro che sta utilizzando il video 
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
		<ul class="nav nav-list pull-right">
			<li>
				<a class="modal-close-iframe" href="#tmid">
					<span class="access-hide">Close</span>
					<span class="fa fa-times-circle"></span>
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
	<div class="content">
		<div class="content-heading">
			<div class="container">
				<h1 class="heading">Simulazione T.M.I.D</h1>
			</div>
		</div>
		<div class="content-inner">
			<div class="container">
				<div class="card-wrap">
					<div class="row">
						<div class="col-lg-4 col-sm-15">
							<div class="card">
								<div class="card-main">
									<div class="card-img">
										<a href="TMIDServlet"><img alt="alt text" src="images/samples/simul_tmid.jpg"></a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<footer class="footer">
		<div class="container">
			<p>&alpha;lfada progetti</p>
		</div>
	</footer>
	
	<script src="js/base.min.js" type="text/javascript"></script>
</body>
</html>