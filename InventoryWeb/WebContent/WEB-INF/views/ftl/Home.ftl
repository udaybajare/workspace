<!DOCTYPE html>
<html dir="ltr" lang="zxx">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="Developed By M Abdur Rokib Promy">
  <meta name="author" content="cosmic">
  <meta name="keywords" content="Bootstrap 3, Template, Theme, Responsive, Corporate, Business">
  <link rel="shortcut icon" href="./loginPage/img/favicon.png">

  <title>
    HamDule INDUSTRIES | Home
  </title>

  <!-- Bootstrap core CSS -->
  <link href="./loginPage/cssfiles/bootstrap.min.css" rel="stylesheet">
  <link href="./loginPage/cssfiles/theme.css" rel="stylesheet">
  <link href="./loginPage/cssfiles/bootstrap-reset.css" rel="stylesheet">
  <!-- <link href="./loginPage/cssfiles/bootstrap.min.css" rel="stylesheet">-->

  <!--external css-->
  <link href="./loginPage/assets/font-awesome/./loginPage/cssfiles/font-awesome.css" rel="stylesheet" />
  <link rel="stylesheet" href="./loginPage/cssfiles/flexslider.css"/>
  <link rel="stylesheet" href="./loginPage/cssfiles/overlay.css"/>
  <link href="./loginPage/assets/bxslider/jquery.bxslider.css" rel="stylesheet" />
  <link rel="stylesheet" href="./loginPage/cssfiles/animate.css">
  <link rel="stylesheet" href="./loginPage/assets/owlcarousel/owl.carousel.css">
  <link rel="stylesheet" href="./loginPage/assets/owlcarousel/owl.theme.css">

  <link href="./loginPage/cssfiles/superfish.css" rel="stylesheet" media="screen">
  <link href='http://fonts.googleapis.com/css?family=Lato' rel='stylesheet' type='text/css'>
  <!-- <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'> -->


  <!-- Custom styles for this template -->
  <link rel="stylesheet" type="text/css" href="./loginPage/cssfiles/component.css">
  <link href="./loginPage/cssfiles/style.css" rel="stylesheet">
  <link href="./loginPage/cssfiles/style-responsive.css" rel="stylesheet" />

    <link rel="stylesheet" type="text/css" href="./loginPage/cssfiles/parallax-slider/parallax-slider.css" />
    <script type="text/javascript" src="./loginPage/js/parallax-slider/modernizr.custom.28468.js">
    </script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 tooltipss and media queries -->
    <!--[if lt IE 9]>
    <script src="./loginPage/js/html5shiv.js">
    </script>
    <script src="./loginPage/js/respond.min.js">
    </script>
  <![endif]-->
</head>

  <body>
    <!--header start-->
    <header class="head-section">
   <div class="row warning-bg">
     <div class="col-md-8">
      <div class="navbar navbar-default navbar-static-top container" style="margin-left: 0px; margin-right: 0px;">
          <div class="navbar-header" style="width: 200px;">
              <button class="navbar-toggle" data-target=".navbar-collapse" data-toggle="collapse" type="button">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
                  <img src="./loginPage/img/Hamdule-Logo1.png" alt="" style="width: 200px;height: 55px;margin-top: 20px;margin-left: -20px;">
          </div>
        </div>
      </div>
      <div class="col-md-2">
                  </div>
                  <div class="col-md-2"> 
                <form class="form-horizontal" action="logout" method="POST" style="margin-left: 30px;margin-top: 30px;">
             
                  <button type="submit" class="btn btn-primary btn-animated">Log Out <i class="fa fa-user"></i></button>
                </form>
              </div>

    </header>
    <!--header end-->

    <!-- Sequence Modern Slider -->
    
    <div class="container">
      <div class="row mar-b-50">
        <div class="col-md-12">
          <div class="font-weight-bold text-center text-primary">
            <h1 class="">
              Welcome to Hamdule Industries
            </h1>

        </div>


        <div class="feature-box">
          <div class="col-md-4 col-sm-4 text-center">
            <div class="feature-box-heading">
              <em>
                <img src="./loginPage/img/1.png" alt="" width="200" height="200">

              </em>
              <h4>
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#createProjectModal">
                  Create Project
                </button>

                <div class="modal fade" id="createProjectModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                  <div class="modal-dialog" role="document">
                    <div class="modal-content">
                      <div class="modal-header">
                        <h4 class="modal-title" id="myModalLabel">Enter Project Details</h4>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                      </div>
                      <form action="createProject" method="POST">
                        <div class="modal-body">
                          <p>Project Name</p>
                          <input type="text" class="form-control" placeholder="Type a project Name Here" name="projectName">
                          <br/>
                          <p>Company Name</p>
                          <input type="text" class="form-control" placeholder="Enter Company Name Here" name="companyName">
                          <br/>
                          <p>Project Description</p>
                          <input type="text" class="form-control" placeholder="Type in the description Here" name="projectDesc">
                        </div>
                        <div class="modal-footer">
                          <button type="button" class="btn btn-sm btn-dark" data-dismiss="modal">Close</button>
                          <button type="submit" class="btn btn-sm btn-default">Create</button>
                        </div>
                      </form>
                    </div>
                  </div>
                </div>

              </h4>
            </div>

          </div>
          <div class="col-md-4 col-sm-4 text-center">
            <div class="feature-box-heading">
              <em>
                <img src="./loginPage/img/2.png" alt="" width="200" height="200">
              </em>
              <h4>

               <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#searchProjectModal">
                 Search Project
               </button>

               <div class="modal fade" id="searchProjectModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                  <div class="modal-content">
                    <div class="modal-header">
                      <h4 class="modal-title" id="myModalLabel">Please enter any of the following details to search </h4>
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    </div>
                    <form action="searchProject" method="POST">
                      <div class="modal-body">
                        <p>Project Name</p>
                        <input type="text" class="form-control" placeholder="Type a project Name Here" name="projectName">
                        <br/>
                        <p>Company Name</p>
                        <input type="text" class="form-control" placeholder="Enter Company Name Here" name="companyName">
                        <br/>
                        <p>Project Description</p>
                        <input type="text" class="form-control" placeholder="Type in the description Here" name="projectDesc">
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-sm btn-dark" data-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-sm btn-default">Search</button>
                      </div>
                    </form>
                  </div>
                </div>
              </div>
                  
                </h4>
              </div>
             
            </div>
            <div class="col-md-4 col-sm-4 text-center wow fadeInUp">
              <div class="feature-box-heading">
                <em>
                  <img src="./loginPage/img/3.png" alt="" width="200" height="200">
                </em>
                <div>
                <form action="updateInventoryForm" method="GET" style="margin-top: 10px;">
          <button type="submit" class="btn btn-primary" data-toggle="modal" data-target="#updateInventoryForm">
           Update Inventory
         </button>
          </form>
       </div>
     </div>

     <!--feature end-->
   </div>
 </div>
</div>
 <div class="row mar-b-50">
 </div>
  <div class="row mar-b-50">
 </div>
 <div class="row mar-b-50">
 </div>
  <div class="row mar-b-50">
 </div> 
 <div class="row mar-b-50">
 </div>
   <footer id="footer">

  <!-- .subfooter start -->
  <!-- ================ -->
  
        
        
            <p class="text-left"></p>
          
        
     
  <!-- .subfooter end -->

</footer>






<!--container end-->

<!--footer start-->

<!-- footer end -->
<!--small footer start -->

<!--small footer end-->

    <!-- js placed at the end of the document so the pages load faster
<script src="./loginPage/js/jquery.js">
</script>
-->
<script src="./loginPage/js/jquery-1.8.3.min.js">
</script>
<script src="./loginPage/js/bootstrap.min.js">
</script>
<script type="text/javascript" src="./loginPage/js/hover-dropdown.js">
</script>
<script defer src="./loginPage/js/jquery.flexslider.js">
</script>
<script type="text/javascript" src="./loginPage/assets/bxslider/jquery.bxslider.js">
</script>

<script type="text/javascript" src="./loginPage/js/jquery.parallax-1.1.3.js">
</script>
<script src="./loginPage/js/wow.min.js">
</script>
<script src="./loginPage/assets/owlcarousel/owl.carousel.js">
</script>

<script src="./loginPage/js/jquery.easing.min.js">
</script>
<script src="./loginPage/js/link-hover.js">
</script>
<script src="./loginPage/js/superfish.js">
</script>
<script type="text/javascript" src="./loginPage/js/parallax-slider/jquery.cslider.js">
</script>
<script type="text/javascript">
  $(function() {

    $('#da-slider').cslider({
      autoplay    : true,
      bgincrement : 100
    });

  });
</script>



<!--common script for all pages-->
<script src="./loginPage/js/common-scripts.js">
</script>

<script type="text/javascript">
  jQuery(document).ready(function() {


    $('.bxslider1').bxSlider({
      minSlides: 5,
      maxSlides: 6,
      slideWidth: 360,
      slideMargin: 2,
      moveSlides: 1,
      responsive: true,
      nextSelector: '#slider-next',
      prevSelector: '#slider-prev',
      nextText: 'Onward →',
      prevText: '← Go back'
    });

    var toggle = true;    
    $(".selectable").click(function(){
      if(toggle){
        document.getElementById("textOverlay").style.display = "block";
      }else{
        document.getElementById("textOverlay").style.display = "none";
      }
      toggle = !toggle;
    });

    var searchToggle = true;    
    $(".searchProjectForm").click(function(){
      if(searchToggle){
        document.getElementById("textOverlaySearch").style.display = "block";
      }else{
        document.getElementById("textOverlaySearch").style.display = "none";
      }
      searchToggle = !searchToggle;
    });

  });


</script>


<script>
  $('a.info').tooltip();

  $(window).load(function() {
    $('.flexslider').flexslider({
      animation: "slide",
      start: function(slider) {
        $('body').removeClass('loading');
      }
    });
  });

  $(document).ready(function() {

    $("#owl-demo").owlCarousel({

      items : 4

    });

  });

  jQuery(document).ready(function(){
    jQuery('ul.superfish').superfish();
  });

  new WOW().init();


</script>
<script>

  function closeOverlay(overLayId)
  {
   var idVal = overLayId;
   document.getElementById(overLayId).style.display = "none";
 }

</script>


</body>
</html>