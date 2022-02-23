<%@page import="com.microfocus.app.DbAccess" %>
<%@page import="com.microfocus.app.Product" %>
<%@page import="java.util.ArrayList" %>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Products</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
		<script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
		<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	<![endif]-->
	<style>
		body {
			padding-top: 50px;
		}

		.product-template {
			padding: 40px 15px;
		}

		#custom-search-input {
			padding: 3px;
			border: solid 1px #E4E4E4;
			border-radius: 6px;
			background-color: #fff;
			margin-bottom: 20px;
		}

		#custom-search-input input {
			border: 0;
			box-shadow: none;
		}

		#custom-search-input button {
			margin: 2px 0 0 0;
			background: none;
			box-shadow: none;
			border: 0;
			color: #666666;
			padding: 0 8px 0 10px;
			border-left: solid 1px #ccc;
		}

		#custom-search-input button:hover {
			border: 0;
			box-shadow: none;
			border-left: solid 1px #ccc;
		}

		#custom-search-input .glyphicon-search {
			font-size: 23px;
		}
	</style>
</head>

<body>
	<% 
		DbAccess da=new DbAccess(); 
		String keywords = request.getParameter("keywords");
		if (keywords == null) keywords = "";
		ArrayList<Product> products = da.getProducts(keywords);
	%>

	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
					data-target="#navbar" aria-expanded="false" aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Products</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">Home</a></li>
					<li><a href="#about">About</a></li>
					<li><a href="#contact">Contact</a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</nav>

	<div class="container">

		<div class="product-template">
			<div class="row">

				<form id="search" method = "GET">
					<div class="col-md-12">
						<div id="custom-search-input">
							<div class="input-group col-md-12">
								<input id="keywords" name="keywords" type="text" class="form-control input-lg" placeholder="Enter search keywords" />
								<span class="input-group-btn">
									<button class="btn btn-info btn-lg" type="submit">
										<i class="glyphicon glyphicon-search"></i>
									</button>
								</span>
							</div>
						</div>
					</div>
				</form>

			</div>
			<table class="table table-hover">
				<thead>
					<tr>
						<th>Id</th>
						<th>Title</th>
						<th>Category</th>
						<th>Description</th>
					</tr>
				</thead>
				<tbody>
					<% for (int i=0; i< products.size(); i++) { Product p=products.get(i); %>
						<tr>
							<td>
								<%= p.Id %>
							</td>
							<td>
								<%= p.Title %>
							</td>
							<td>
								<%= p.Category %>
							</td>
							<td>
								<%= p.Description %>
							</td>
						</tr>
						<% } %>
				</tbody>
			</table>

			<% if (keywords.length() > 0) { %>
			<p>
				Searching for: "<%=keywords %>" - found <%= products.size() %> result(s).
			</p>
			<% } %>

		</div>

	</div>

</body>

</html>