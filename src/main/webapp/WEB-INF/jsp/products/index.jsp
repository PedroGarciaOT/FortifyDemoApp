<%@page import="com.microfocus.app.DbService" %>
<%@page import="com.microfocus.app.Product" %>
<%@page import="java.util.ArrayList" %>
<%@ page import="com.microfocus.app.DbService" %>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<title>Web App Demo</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="css/fontawesome.all.css"/>
	<link rel="stylesheet" type="text/css" href="css/icomoon/style.css"/>
	<link rel="stylesheet" type="text/css" href="css/app.css">
</head>

<body>
	<%
		DbService da=new DbService();
		String keywords = request.getParameter("keywords");
		if (keywords == null) keywords = "";
		ArrayList<Product> products = da.getProducts(keywords);
	%>

	<div id="app" class="d-flex flex-column min-vh-100 site-wrap">

		<!-- header -->
		<div class="site-navbar py-2">
			<div class="container">
				<div class="d-flex align-items-center justify-content-between">
					<div class="logo">
						<div class="site-logo">
							<a class="navbar-brand js-logo-clone" href="#">
								<img src="/img/logo.png" alt="IWA Pharmacy Direct">
							</a>
						</div>
					</div>
					<div class="main-nav d-none d-lg-block">
						<nav class="site-navigation text-right text-md-center" role="navigation">
							<ul class="site-menu js-clone-nav d-none d-lg-block">
								<li class="has-children">
									<a href="#">Shop</a>
									<ul class="dropdown">
										<li class="has-children">
											<a href="#">Health &amp; wellbeing</a>
											<ul class="dropdown">
												<li><a href="#">First aid</a></li>
												<li><a href="#">Health conditions</a></li>
												<li><a href="#">Stop smoking</a></li>
											</ul>
										</li>
										<li class="has-children">
											<a href="#">Medicines &amp; treatments</a>
											<ul class="dropdown">
												<li><a href="#">Allergy &amp; hayfever</a></li>
												<li><a href="#">Children's medicines</a></li>
												<li><a href="#">Cough, cold &amp; flu</a></li>
												<li><a href="#">Pain relief</a></li>
											</ul>
										</li>
										<li class="has-children">
											<a href="#">Vitamins &amp; supplements</a>
											<ul class="dropdown">
												<li><a href="#">Everyday vitamins &amp; supplements</a></li>
												<li><a href="#">Children's vitamins &amp; supplements</a></li>
												<li><a href="#">Mens supplements</a></li>
												<li><a href="#">Womens supplements</a></li>
											</ul>
										</li>
										<li><a href="#">Weight management</a></li>
										<li><a href="#">Toiletries</a></li>
									</ul>
								</li>
								<li class="has-children">
									<a href="#">Prescriptions</a>
									<ul class="dropdown">
										<li>
											<a href="#">One-off prescriptions</a>
											<a href="#">Repeat prescriptions</a>
										</li>
									</ul>
								</li>
								<li class="has-children">
									<a href="#">Services</a>
									<ul class="dropdown">
										<li>
											<a href="#">Flu vaccinations</a>
											<a href="#">Stop smoking</a>
											<a href="#">Travel vaccinations</a>
										</li>
									</ul>
								</li>
								<li>
									<a href="#">Advice</a>
								</li>
							</ul>
						</nav>
					</div>

					<div class="site-top-icons">
						<ul>
							<li>
								<div class="has-children">
									<a href="#" class="icons-menu d-inline-block">
										<span class="icon-account_circle"></span>
										<span class="my-account-text">My Account</span>
									</a>
									<ul class="dropdown">
										<li>
											<a href="#">
												<i class="fas fa-sign-in-alt fa-fw"></i> Login
											</a>
										</li>
										<li>
											<a class="nav-link" href="#">
												<i class="fas fa-code fa-fw"></i> API Explorer
											</a>
										</li>
									</ul>
								</div>
							</li>
							<li>
								<a href=#" class="icons-btn d-inline-block bag">
									<span class="icon-shopping-bag"></span>
									<span id="shopping-cart-count" class="number">0</span>
								</a>
							</li>
							<li>
								<a href="#" class="site-menu-toggle js-menu-toggle ml-3 d-inline-block d-lg-none">
									<span class="icon-menu"></span>
								</a>
							</li>
						</ul>
					</div>

				</div>
			</div>
		</div>

		<!-- page title -->
		<div class="bg-light py-3">
			<div class="container">
				<div class="row">
					<div class="col-md-12 mb-0"><a href="#">Products</a> </div>
				</div>
			</div>
		</div>

		<!-- content -->
		<div class="site-section-sm">

			<div class="container">

				<div class="row mb-1">
					<form id="search" action="#" class="col-md-12" method="GET">
						<div class="input-group">
							<input type="text" class="form-control" placeholder="Search products" name="keywords"
								   id="keywords" value="${param.keywords}">
							<div class="input-group-append">
								<button class="btn btn-secondary" type="submit">
									<i class="fa fa-search"></i>
								</button>
							</div>
						</div>
					</form>
				</div>

				<div class="row pt-5">

					<table class="table table-hover table-sm table-striped">
						<thead>
						<tr>
							<th>Code</th>
							<th>Name</th>
							<th>Summary</th>
							<th>Price</th>
						</tr>
						</thead>
						<tbody>
						<% for (int i=0; i< products.size(); i++) { Product p=products.get(i); %>
						<tr>
							<td>
								<a href="#"><%= p.Code %></a>
							</td>
							<td>
								<%= p.Name %>
							</td>
							<td>
								<%= p.Summary %>
							</td>
							<td>
								&#164;<%= p.Price %>
							</td>
						</tr>
						<% } %>
						</tbody>
					</table>

					<div class="row pt-4">
						<div class="col-md-12">
							<% if (keywords.length() > 0) { %>
							<p>
								Searching for "<%=keywords %>" - found <em><%= products.size() %></em> result(s).
							</p>
							<% } %>
						</div>
					</div>

				</div>

			</div>

		</div>

		<!-- footer -->
		<footer class="site-footer mt-auto">
			<div class="row">
				<div class="col-md-3 footer-brand animated fadeInLeft">
					<h4>IWA Pharmacy Direct</h4>
					<p>IWA (Insecure Web App) Pharmacy Direct is a deliberately insecure web application for use in
						<a href="https://www.microfocus.com/en-us/cyberres/application-security">Fortify Application Security</a> demonstrations.</p>
					<p>© <a href="https://www.microfocus.com/">Micro Focus</a> 2021. All Rights Reserved | Template by
						<a href="https://colorlib.com" target="_blank" class="text-info">Colorlib</a></p>
				</div>
				<div class="col-md-4 footer-nav animated fadeInUp">
					<div class="row">
						<div class="col-md-6">
							<h4>Menu ?</h4>
							<ul class="pages">
								<li><a href="#">Shop</a></li>
								<li><a href="#">Prescriptions</a></li>
								<li><a href="#">Services</a></li>
								<li><a href="#">Advice</a></li>
							</ul>
						</div>
						<div class="col-md-6">
							<ul class="list">
								<li><a href="#">About Us</a></li>
								<li><a href="#">Contacts</a></li>
								<li><a href="#">Terms & Condition</a></li>
								<li><a href="#">Privacy Policy</a></li>
							</ul>
						</div>
					</div>
				</div>
				<div class="col-md-2 footer-social animated fadeInDown">
					<h4>Follow Us</h4>
					<ul>
						<li><a href="#">Facebook</a></li>
						<li><a href="#">Twitter</a></li>
						<li><a href="#">Instagram</a></li>
						<li><a href="#">RSS</a></li>
					</ul>
				</div>
				<div class="col-md-3 footer-ns animated fadeInRight">
					<h4>Newsletter</h4>
					<p>Sign-up for our newsletter by entering your email address below:</p>
					<p>
					<div id="subscribe-newsletter">
						<div class="email-confirmation-input">
							<div role="group" class="input-group">
								<input type="text" placeholder="Your email address" class="form-control" id="email-subscribe-input" required />
								<div class="input-group-append">
									<button type="button" class="btn btn-secondary" id="email-subscribe-button">
										<i class="fa fa-envelope"></i>
									</button>
								</div>
							</div>

							<div class="confirmation-modal">
								<div id="email-confirmation-modal">
									<div id="confirmation-modal" role="dialog" aria-hidden="true" class="modal fade" style="display: none;">
										<div class="modal-dialog modal-md">
											<div tabindex="-1" role="document" aria-labelledby="confirmation-header" aria-describedby="confirmation-modal-body" class="modal-content">
												<header id="confirmation-modal-header" class="modal-header">
													<h5 class="modal-title">Newsletter Registration</h5>
													<button type="button" aria-label="Close" class="close" data-dismiss="modal">×</button>
												</header>
												<div id="confirmation-modal-body" class="modal-body">
													<div class="m-4 text-center">
														<h4></h4>
													</div>
												</div>
												<footer id="confirmation-modal-footer" class="modal-footer">
													<button type="button" class="btn btn-primary" data-dismiss="modal">OK</button>
												</footer>
											</div>
										</div>
									</div>
								</div>
							</div>

						</div>
					</div>
					</p>
				</div>
			</div>
		</footer>

		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/bootstrap.bundle.min.js"></script>
		<script type="text/javascript" src="js/app.js"></script>

	</div>

</body>

</html>
