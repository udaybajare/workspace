<!DOCTYPE html>
<html dir="ltr" lang="zxx">

<head>
  <meta charset="utf-8">
  <title>The Project | Tables</title>
  <meta name="description" content="The Project a Bootstrap-based, Responsive HTML5 Template">
  <meta name="author" content="author">

  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

  <link rel="shortcut icon" href="images/favicon.ico">

  <link href="https://fonts.googleapis.com/css?family=Roboto:300,300i,400,400i,500,500i,700,700i" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css?family=Raleway:300,400,700" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css?family=Pacifico" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css?family=PT+Serif" rel="stylesheet">

  <link href="bootstrap/css/bootstrap.css" rel="stylesheet">

  <link href="fonts/font-awesome/css/font-awesome.css" rel="stylesheet">

  <link href="plugins/magnific-popup/magnific-popup.css" rel="stylesheet">
  <link href="css/animations.css" rel="stylesheet">
  <link href="plugins/slick/slick.css" rel="stylesheet">
  
  <link href="css/style.css" rel="stylesheet" >
  <link href="css/typography-default.css" rel="stylesheet" >
  <link href="css/skins/light_blue.css" rel="stylesheet">

  <link href="css/custom.css" rel="stylesheet">
  
</head>

<body class="" cz-shortcut-listen="true">

  <div class="scrollToTop circle fadeToTop"><i class="fa fa-angle-up"></i></div>

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
                   <img src="./images/img/Hamdule-Logo1.png" alt="" style="width: 250px;height: 55px;margin-top: 20px;margin-left: -150px;">
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
</div>
<div class="subfooter">
  <div class="container">
    <div class="subfooter-inner">
      <div class="row">
        <div class="col-md-8">
          <table class="table table-colored table-bordered">
            <thead>
              <tr>
                <th colspan="4"><b><h3>Delivery Challan</h3></b></th>
              </tr>
            </thead>
            <tbody>
              <tr><td class="font-weight-bold">Delivery Challan No</td><td>${challanNo}</td><td class="font-weight-bold">Purchase Order No</td><td>${poNo}</td></tr>
              <tr><td class="font-weight-bold">Date</td><td>${date}</td><td class="font-weight-bold">Purchase Order Date</td><td>${poDate}</td></tr>
              <tr><td class="font-weight-bold" colspan="2">From</td><td class="font-weight-bold" colspan="2">Consignee</td></tr>
              <tr><td colspan="2">${from1}</td><td colspan="2">${consignee1}</td></tr>
              <tr><td colspan="2">${from2}</td><td colspan="2">${consignee2}</td></tr>
              <tr><td colspan="2">${from3}</td><td colspan="2">${consignee3}</td></tr>
              <tr><td class="font-weight-bold">Transport Mode</td><td class="font-weight-bold">LR# & Date</td><td class="font-weight-bold">Vheicle Number</td><td></td></tr>
              <tr><td>${transportMode}</td><td>${lrNo}</td><td>${vheicleNumber}</td><td></td></tr>
              <tr><td class="font-weight-bold">SrNO&#8195;Size</td> <td class="font-weight-bold">Description</td><td class="font-weight-bold">Quantity</td><td class="font-weight-bold">Units</td></tr>
              ${itemList}				
              <tr><td class="font-weight-bold">GST Number</td><td>${gstNo}</td><td></td><td class="font-weight-bold">For<br/>Hamdule Industries</td></tr>
              <tr><td colspan="2" rowspan="5" ></td><td colspan="2" rowspan="5"></td></tr>
              <tr><td></td> </tr>
              <tr><td></td></tr>
              <tr><td></td></tr>
              <tr><td></td></tr>
              <tr><td rowspan="4"></td><td></td><td class="font-weight-bold">Receiver Sign</td><td class="font-weight-bold">Authorised Signatory</td></tr>
              <tr><td class="font-weight-bold" colspan="3">Regd. Address : Shop # 3, Blgd C2,Manish Gardan, Udyam Nagar, Pimpri,Pune 411018</td></tr>
              <tr><td class="font-weight-bold" colspan="3">Works: Plot No. 55,Gat 55 & 57, Chakan, Askhed, Tal. Khed, Pune - 410501</td></tr>
              <tr><td  class="font-weight-bold" colspan="3">Cell: +91 20 2750 2200 | Email:business@hamduleindustries.com | www.hamduleindustries.com</td></tr>
            </tbody>				
          </table>				
        </div>
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-md-4">
        <input type="button" class="btn btn-default printBtn" value="Print Challan" onClick="printThis();" style="margin-left:28%;">
    </div>
</div>
</div>

</footer>
</div>

<script src="plugins/jquery.min.js"></script>
<script src="bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="plugins/magnific-popup/jquery.magnific-popup.min.js"></script>
<script src="plugins/waypoints/jquery.waypoints.min.js"></script>
<script src="plugins/waypoints/sticky.min.js"></script>
<script src="plugins/countTo/jquery.countTo.js"></script>
<script src="plugins/slick/slick.min.js"></script>
<script src="js/template.js"></script>
<script src="js/custom.js"></script>

<script src="template/plugins/rs-plugin-5/js/extensions/revolution.extension.slideanims.min.js"></script>
<script src="template/plugins/rs-plugin-5/js/extensions/revolution.extension.slideanims.min.js"></script>
<script src="template/plugins/rs-plugin-5/js/extensions/revolution.extension.actions.min.js"></script>
<script src="template/plugins/rs-plugin-5/js/extensions/revolution.extension.layeranimation.min.js"></script>
<script src="template/plugins/rs-plugin-5/js/extensions/revolution.extension.kenburn.min.js"></script>

<script>
function printThis()
{
  $('.printBtn').css('display','none');
  $('.page-wrapper').css('display','none');
  window.print();
  $('.printBtn').css('display','block');
  $('.page-wrapper').css('display','block');
}
</script>
</body>
</html>