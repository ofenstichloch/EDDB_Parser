
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="">
  <title>Jumbotron Template for Bootstrap</title>

  <!-- Bootstrap core CSS -->
  <link href="css/bootstrap.css" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="css/edmarket.css" rel="stylesheet">
</head>

<body>

  <nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
      <div class="navbar-header">
        <a class="navbar-brand" href="index.php">Elite Dangerous Market</a>
      </div>
      <div id="navbar" class="navbar-collapse collapse">
        <ul class="nav navbar-nav">
          <li><a href="index.php">Home</a></li>
          <li class="active"><a href="#">Distance calculator</a></li>
          <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button">Prices</a>
            <ul class="dropdown-menu" role="menu">
              <li><a href="#">By Station</a></li>
              <li><a href="#">By Commodity</a></li>
              <li><a href="#">By Area</a></li>

            </ul>
          </li>
        </ul>
      </div>
    </div>
  </nav>

  <div class="jumbotron">
    <div class="container">
      <h2>Distance Calculator</h2>
      <p>Calculate the distance between two systems.</p>
    </div>
  </div>
  <div class="container">
    <div class="row">
      <div class="col-md-4">
        <form id="distform">
          <div class="form-group">
            <label for="system1">First System</label>
            <input type="text" class="form-control" id="system1" placeholder="First System"/>
          </div>
          <div class="form-group">
            <label for="system2">Second System</label>
            <input type="text" class="form-control" id="system2" placeholder="Second System"/>
          </div>
          <button type="submit" class="btn btn-default" id="calc">Submit</button>
        </form>
      </div>
      <div class="col-md-8">
        <label for="result">Distance: </label>
        <pre id="result">0 ly</pre>
      </div>
    </div>
    
    <hr>
    <footer>
      <p>&copy; Me 2015</p>
    </footer>
  </div>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="/js/bootstrap.js"></script>
    <script src="/js/distance.js"></script>
  </body>
  </html>
