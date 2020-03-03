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

<body class="fixed-header-on" cz-shortcut-listen="true">
  <div class="scrollToTop circle fadeToTop"><i class="fa fa-angle-up"></i></div>
  <div class="subfooter">
    <div class="container">
      <div class="subfooter-inner">
        <div class="row">
          <div class="col-md-12 border border-dark" style="padding-left: 0%;padding-right: 0%;">
            <table class="table table-colored table-bordered" style="margin-bottom:0rem;">
              <thead >
                <tr >
                  <th colspan="4"style="background-color: #c2cdd1;border-color:#c2cdd1;"><b><h3 class="text-center text-light font-weight-bold" style="margin-top: 1%;">RETURNABLE CHALLAN</h3></b></th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td class="font-weight-bold">Delivery Challan No:</td>
                  <td>${challanNo}</td>
                  <td class="font-weight-bold">Purchase Order No:</td>
                  <td>${poNo}</td>
                </tr>
                <tr>
                  <td class="font-weight-bold">Date:</td>
                  <td>${date}</td>
                  <td class="font-weight-bold">Purchase Order Date:</td>
                  <td>${poDate}</td>
                </tr>
                <tr>
                  <td class="font-weight-bold bg-light" colspan="2">From</td>
                  <td class="font-weight-bold bg-light" colspan="2">Consignee</td>
                </tr>
                <tr>
                  <td class=" bg-light" colspan="2">
                    <div style="width:50%;height:17px;">${from1}</div>
                  </td>
                  <td class=" bg-light" colspan="2">
                    <div style="width:50%;height:17px;">${consignee1}</div>
                  </td>
                </tr>
                <tr>
                  <td class=" bg-light" colspan="2">
                    <div style="height:17px;">${from2}</div>
                  </td>
                  <td class=" bg-light" colspan="2">${consignee2}</td>
                </tr>
                <tr>
                  <td class=" bg-light" colspan="2">
                    <div style="height:17px;">${from3}</div>
                  </td>
                  <td class=" bg-light" colspan="2">${consignee3}</td>
                </tr>
              </tbody>
            </table>
            <table class="table table-colored table-bordered" style="margin-bottom:0rem;margin-top:0rem;">
              <tbody>
                <tr>
                  <td class="font-weight-bold" style="width:30%;text-align:center;">Transport Mode</td>
                  <td class="font-weight-bold" style="width:40%;text-align:center;">LR# & Date</td>
                  <td class="font-weight-bold" style="width:30%;text-align:center;" colspan="2">Vheicle Number</td>
                </tr>
                <tr>
                  <td style="width:30%;text-align:center;">${transportMode}</td>
                  <td style="width:30%;text-align:center;">${lrNo}</td>
                  <td style="width:30%;text-align:center;" colspan="2">${vheicleNumber}</td>
                </tr>
              </tbody>
            </table>  
            <table class="table table-colored table-bordered" style="margin-bottom:0rem;margin-top:0rem;">
              <tbody>
                <tr>
                  <td class="font-weight-bold bg-light" style="width:10%;text-align:center;">SrNO&#8195;Size</td>
                  <td class="font-weight-bold bg-light" style="width:70%;text-align:center;">Description</td>
                  <td class="font-weight-bold bg-light" style="width:10%;text-align:center;">Qty.</td>
                  <td class="font-weight-bold bg-light" style="width:10%;text-align:center;">Unit</td>
                </tr>
                ${itemList}
              </tbody>
            </table>
            <table class="table table-colored table-bordered" style="margin-bottom:0rem;margin-top:0rem;">
              <tbody>				
                <tr>
                  <td class="font-weight-bold" colspan="3" style="width:60%;">GST Number: ${gstNo}</td>
                  <td class="font-weight-bold text-right" colspan="1">For Hamdule Industries</td>
                </tr>
                <tr>
                  <td class="font-weight-bold" colspan="2" style="width:60%;"></td>
                  <td class="font-weight-bold text-right" colspan="1"><div style="height:17px;"></div></td>
                  <td class="font-weight-bold text-right" colspan="1"><div style="height:17px;"></div></td>
                </tr>
                <tr>
                  <td class="font-weight-bold" colspan="2" style="width:60%;"></td>
                  <td class="font-weight-bold text-right" colspan="1"><div style="height:17px;"></div></td>
                  <td class="font-weight-bold text-right" colspan="1"><div style="height:17px;"></div></td>
                </tr>
                <tr>
                  <td class="font-weight-bold" colspan="2" style="width:60%;"></td>
                  <td class="font-weight-bold text-right" colspan="1"><div style="height:17px;"></div></td>
                  <td class="font-weight-bold text-right" colspan="1"><div style="height:17px;"></div></td>
                </tr>
                <tr>
                  <td class="font-weight-bold" colspan="2" style="width:60%;"></td>
                  <td class="font-weight-bold text-right" colspan="1"><div style="height:17px;"></div></td>
                  <td class="font-weight-bold text-right" colspan="1"><div style="height:17px;"></div></td>
                </tr>
                <tr>
                  <td style="width:30%;"></td>
                  <td style="width:30%;"></td>
                  <td class="font-weight-bold">Receiver's Stamp & Sign</td>
                  <td class="font-weight-bold">Authorised Signatory</td>
                </tr>
                <tr>
                  <td class="font-weight-bold text-center" colspan="4">Regd. Address : Shop # 3, Blgd C2,Manish Gardan, Udyam Nagar, Pimpri,Pune 411018</td>
                </tr>
                <tr>
                  <td class="font-weight-bold text-center" colspan="4">Works: Plot No. 55,Gat 55 &amp; 57, Chakan, Askhed, Tal. Khed, Pune - 410501</td>
                </tr>
                <tr>
                  <td class="font-weight-bold text-center" colspan="4">Cell: +91 20 2750 2200 | Email:business@hamduleindustries.com | www.hamduleindustries.com</td></tr>
                </tbody>				
              </table>				
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-md-4">
       <input type="button" class="btn btn-default printBtn" value="Print Challan" onClick="printThis();" style="margin-left:28%;">
     </div>
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