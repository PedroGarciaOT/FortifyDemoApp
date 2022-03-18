<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<title>Fortify Demo App</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/lib/bootstrap.min.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/lib/fontawesome.all.css"/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/lib/icomoon/style.css"/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/app.css"/>">
</head>

<body>

	<div id="app" class="d-flex flex-column min-vh-100 site-wrap">

		<!-- header -->
		<div class="site-navbar py-2">
			<div class="container">
				<div class="d-flex align-items-center justify-content-between">
					<div class="logo">
						<div class="site-logo">
							<a class="navbar-brand js-logo-clone" href="#">
								<img src="<c:url value="/img/logo.png"/>" alt="IWA Pharmacy Direct">
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
					<div class="col-md-12 mb-0"><a href="#">Shop</a> </div>
				</div>
			</div>
		</div>

		<!-- content -->
		<div class="site-section-sm">

			<div class="container">

				<c:if test="${haveKeywords}">
					<div class="row pt-2">
						<div class="col-md-12 text-center">
							<h2>Searching for:
								<span class="bg-light text-dark"><span id="search-keywords"><%= request.getParameter("keywords") %></span>
							</h2>
						</div>
					</div>
				</c:if>

				<!-- search box -->
				<div class="row d-flex justify-content-center">
					<form id="search" class="col-md-10" action="#" method="GET">
						<div class="card p-3 py-4">
							<div class="row g-3 mt-2">
								<div class="input-group">
									<div class="col-md-3">
										<div class="dropdown"> <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-expanded="false"> Category </button>
											<ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
												<li><a class="dropdown-item" href="#">Vitamins & supplements</a></li>
												<li><a class="dropdown-item" href="#">Skincare</a></li>
												<li><a class="dropdown-item" href="#">Electricals</a></li>
												<li><a class="dropdown-item" href="#">Health & wellbeing</a></li>
											</ul>
										</div>
									</div>
									<div class="col-md-6">
										<input type="text" class="form-control" placeholder="Enter search keywords" name="keywords"
											   id="keywords" value="<%= request.getParameter("keywords") != null ? request.getParameter("keywords") : "" %>"/>
									</div>
									<div class="col-md-3">
										<button class="btn btn-secondary btn-block" type="submit">Search</button>
									</div>
								</div>
							</div>
							<div class="mt-3"> <a data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample" class="advanced"> Advance Search With Filters <i class="fa fa-angle-down"></i> </a>
								<div class="collapse" id="collapseExample">
									<div class="card card-body">
										<div class="row">
											<div class="col-md-4"> <input type="text" placeholder="Product ID" class="form-control"> </div>
											<div class="col-md-4"> <input type="text" class="form-control" placeholder="Medical Condition"> </div>
											<div class="col-md-4"> <input type="text" class="form-control" placeholder="Search by Brand"> </div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>

				<div class="row pt-5">
					<c:forEach items="${products}" var="p">
						<div class="col-sm-6 col-lg-4 text-center item mb-4">
							<c:choose>
								<c:when test="${p.onSale}">
									<span class="tag">Sale</span>
								</c:when>
							</c:choose>
							<a href="#">
								<c:choose>
									<c:when test="${not empty p.image}">
										<img src="<c:url value="/img/products/${p.image}"/>" alt="Image" class="img-thumbnail">
									</c:when>
									<c:otherwise>
										<img src="<c:url value="/img/awaiting-image-sm.png"/>" alt="Awaiting Image" class="img-thumbnail">
									</c:otherwise>
								</c:choose>
							</a>
							<h3 class="text-dark"><a href="#"><c:out value="${p.name}" /></a></h3>
							<p class="price">
								<c:choose>
									<c:when test="${p.onSale}">
										<del>
											<span>
												<fmt:formatNumber value="${p.price}" type="currency" />
											</span>
										</del>
										<span>&ndash;</span>
										<span>
											<fmt:formatNumber value="${p.salePrice}" type="currency" />
										</span>
									</c:when>
									<c:otherwise>
										<span>
											<fmt:formatNumber value="${p.price}" type="currency" />
										</span>
									</c:otherwise>
								</c:choose>
							</p>
						</div>
					</c:forEach>

				</div>

				<div class="row pt-4">
					<div class="col-md-12">
						<c:choose>
							<c:when test="${haveKeywords}">
								<h5>Showing <span><c:out value="${productsCount}"/></span> of <span><c:out value="${productsTotal}"/></span> products</h5>
							</c:when>
							<c:otherwise>
								<h5>Showing <span><c:out value="${productsCount}"></c:out> products</span></h5>
							</c:otherwise>
						</c:choose>
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
					<p>© <a href="https://www.microfocus.com/">Micro Focus</a> 2022. All Rights Reserved | Template by
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

		<script type="text/javascript" src="<c:url value="/js/lib/jquery.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/lib/bootstrap.bundle.min.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/SubscribeNewsletter.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/js/app.js"/>"></script>

		<script type="text/javascript">
			(function ($) {
				$(document).ready(function () {
					$('#subscribe-newsletter').SubscribeNewsletter();
				});
			})(jQuery);
		</script>

	</div>

</body>

</html>
