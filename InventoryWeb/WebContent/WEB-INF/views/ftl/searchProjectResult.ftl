<!DOCTYPE html>
<html dir="ltr" lang="zxx">

<head>
  <meta charset="utf-8">
  <title>The Project | Icon Boxes</title>
  <meta name="description" content="The Project a Bootstrap-based, Responsive HTML5 Template">
  <meta name="author" content="author">

  <!-- Mobile Meta -->
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

  <!-- Favicon -->
  <link rel="shortcut icon" href="images/favicon.ico">

  <!-- Web Fonts -->
  <link href="https://fonts.googleapis.com/css?family=Roboto:300,300i,400,400i,500,500i,700,700i" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css?family=Raleway:300,400,700" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css?family=Pacifico" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css?family=PT+Serif" rel="stylesheet">

  <!-- Bootstrap core CSS -->
  <link href="bootstrap/css/bootstrap.css" rel="stylesheet">

  <!-- Font Awesome CSS -->
  <link href="fonts/font-awesome/css/font-awesome.css" rel="stylesheet">

  <!-- Plugins -->
  <link href="plugins/magnific-popup/magnific-popup.css" rel="stylesheet">
  <link href="css/animations.css" rel="stylesheet">
  <link href="plugins/slick/slick.css" rel="stylesheet">
  
  <!-- The Project's core CSS file -->
  <!-- Use css/rtl_style.css for RTL version -->
  <link href="css/style.css" rel="stylesheet" >
  <!-- The Project's Typography CSS file, includes used fonts -->
  <!-- Used font for body: Roboto -->
  <!-- Used font for headings: Raleway -->
  <!-- Use css/rtl_typography-default.css for RTL version -->
  <link href="css/typography-default.css" rel="stylesheet" >
  <!-- Color Scheme (In order to change the color scheme, replace the blue.css with the color scheme that you prefer) -->
  <link href="css/skins/light_blue.css" rel="stylesheet">

  <!-- Custom css -->
  <link href="css/custom.css" rel="stylesheet">
  
</head>

<!-- body classes:  -->
<!-- "boxed": boxed layout mode e.g. <body class="boxed"> -->
  <!-- "pattern-1 ... pattern-9": background patterns for boxed layout mode e.g. <body class="boxed pattern-1"> -->
    <!-- "transparent-header": makes the header transparent and pulls the banner to top -->
    <!-- "gradient-background-header": applies gradient background to header -->
    <!-- "page-loader-1 ... page-loader-6": add a page loader to the page (more info @components-page-loaders.html) -->
    <body class=" ">

      <!-- scrollToTop -->
      <!-- ================ -->
      <div class="scrollToTop circle"><i class="fa fa-angle-up"></i></div>

      <!-- page wrapper start -->
      <!-- ================ -->
      <div class="page-wrapper">
        <div class="header-container">
          <header class="header fixed fixed-desktop clearfix">
            <div class="container">
              <div class="col-md-auto hidden-md-down">
                <div class="header-first clearfix">
                 <div class="row">
                   <div class="col-md-8">
                     <div class="navbar navbar-default navbar-static-top container" style="margin-left: 0px; margin-right: 0px;">
                      <div class="navbar-header" style="width: 200px;">
                       <img src="./loginPage/img/Hamdule-Logo1.png" alt="" style="width: 250px;height: 55px;margin-top: 20px;margin-left: -150px;">
                     </div>
                   </div>
                 </div> 
                 <div class="col-md-2">
                   <form class="form-horizontal" action="home" method="POST" style="margin-left: 100px;margin-top: 30px;">
                    <button type="submit" class="btn btn-default btn-animated">Home<i class="fa fa-user"></i></button>
                  </form>
                </div>
                <div class="col-md-2"> 
                  <form class="form-horizontal" action="logout" method="POST" style="margin-left: 100px;margin-top: 30px;">
                    <button type="submit" class="btn btn-default btn-animated">Log Out <i class="fa fa-user"></i></button>
                  </form>
                </div>
                <div class="site-slogan">
                </div>
              </div>
            </div>
            
            <div class="col-auto hidden-md-down">
            </div>
          </div>
        </div>
      </header>
    </div>
    <section class="main-container padding-bottom-clear">
      <!-- section -->
      <!-- ================ -->
      <section class="light-gray-bg pv-30 padding-bottom-clear clearfix">
        <div class="container">           
         ${projects}
         <br>
         <br>
       </div>
     </section>
     
   </section>
   <!-- main-container end -->


   

   <!-- footer start (Add "dark" class to #footer in order to enable dark footer) -->
   <!-- ================ -->
   <footer id="footer" class="clearfix ">

    <!-- .subfooter start -->
    <!-- ================ -->
    <div class="subfooter">
      <div class="container">
        <div class="subfooter-inner">
          <div class="row">
            <div class="col-md-12">
              <p class="text-center">Powewred By Social Angels Digital Solution Pvt Ltd.</p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- .subfooter end -->

  </footer>
  <!-- footer end -->
</div>
<!-- page-wrapper end -->

<!-- JavaScript files placed at the end of the document so the pages load faster -->
<!-- ================================================== -->
<!-- Jquery and Bootstap core js files -->
<script src="plugins/jquery.min.js"></script>
<script src="bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- Magnific Popup javascript -->
<script src="plugins/magnific-popup/jquery.magnific-popup.min.js"></script>
<!-- Appear javascript -->
<script src="plugins/waypoints/jquery.waypoints.min.js"></script>
<script src="plugins/waypoints/sticky.min.js"></script>
<!-- Count To javascript -->
<script src="plugins/countTo/jquery.countTo.js"></script>
<!-- Slick carousel javascript -->
<script src="plugins/slick/slick.min.js"></script>
<!-- Initialization of Plugins -->
<script src="js/template.js"></script>
<!-- Custom Scripts -->
<script src="js/custom.js"></script>
</body>
</html>
