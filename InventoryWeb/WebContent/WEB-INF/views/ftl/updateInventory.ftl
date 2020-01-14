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
      
        <!-- header-container start -->
        
          <!-- header-top start -->
          <!-- classes:  -->
          <!-- "dark": dark version of header top e.g. class="header-top dark" -->
          <!-- "colored": colored version of header top e.g. class="header-top colored" -->
          <!-- ================ -->
          
          <!-- header-top end -->

          <!-- header start -->
          <!-- classes:  -->
          <!-- "fixed": enables fixed navigation mode (sticky menu) e.g. class="header fixed clearfix" -->
          <!-- "fixed-desktop": enables fixed navigation only for desktop devices e.g. class="header fixed fixed-desktop clearfix" -->
          <!-- "fixed-all": enables fixed navigation only for all devices desktop and mobile e.g. class="header fixed fixed-desktop clearfix" -->
          <!-- "dark": dark version of header e.g. class="header dark clearfix" -->
          <!-- "centered": mandatory class for the centered logo layout -->
          <!-- ================ -->
          <header class="header-section">
            <div class="container">
              <div class="row">
                  <div class="col-md-8">
                
                 <img src="./loginPage/img/Hamdule-Logo1.png"  style="width: 250px;height: 55px;margin-top: 20px;margin-left: -130px;">
                   </div>
        
      
                <div class="col-md-2">
                  </div>
                  <div class="col-md-2"> 
                <form class="form-horizontal" action="logout" method="POST" style="margin-left: 60px;margin-top: 30px;">
             
                  <button type="submit" class="btn btn-default btn-animated">Log Out <i class="fa fa-user"></i></button>
                </form>
              </div>
              </div>  

                  <!-- header-second start -->
                  <!-- ================ -->
                 
          </header>
       
          <!-- header end -->
        
        <!-- header-container end -->
        <!-- breadcrumb start -->
        <!-- ================ -->
        
        <!-- breadcrumb end -->

        <!-- main-container start -->
        <!-- ================ -->
        
        
          <!-- section -->
          <!-- ================ -->
          <section class="light-gray-bg pv-30 padding-bottom-clear clearfix">
            <div class="container" style="max-width:98%;">
              
                <div class="row">
                  <div class="col-md-12 ">
                    <div class="table-responsive">                
                      
                      <table class="table table-colored inventoryDetails" style="display:none;">
                        <thead>
                          <tr>
                           <th></th>
                           <th>Inventory</th>
                           <th>Material</th>
                           <th>Type</th>
                           <th>Manifacturing Method</th>
                           <th>Class/Schedule</th>
                           <th>Ends</th>
                           <th>Size</th>
                           <th>Quantity</th>
                           <th>Purchase Rate</th>
                           <th>Project</th>
                           <th>Location</th>
                           <th>Status</th>
                         </tr>
                       </thead>
                       <tbody id="tableContentDetails">
                       </tbody>
                     </table>
                   </div>	
                 </div>
               </div>
               <div class="row">
                <div class="col-md-3">
                 <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
                  
                  <button type="button" class="btn btn-default" onClick="showInventory();" style="margin-left: 90px;">Show Available Inventory</button>
                </div>
              </div>
            </div>
            <form action="updateInventory" id="updateInventory" method="POST">
            <div class="row">
                  <div class="col-md-12 ">
                    <div class="table-responsive">                
                      
                      <table class="table table-colored inventoryToBeUpdated" style="display:none;">
                        <thead>
                          <tr>
                           <th></th>
                           <th>Inventory</th>
                           <th>Material</th>
                           <th>Type</th>
                           <th>Manifacturing Method</th>
                           <th>Class/Schedule</th>
                           <th>Ends</th>
                           <th>Size</th>
                           <th>Quantity</th>
                           <th>Purchase Rate</th>
                           <th>Project</th>
                           <th>Location</th>
                           <th>Status</th>
                         </tr>
                       </thead>
                       <tbody id="tableContentInventoryUpdateDetails">
                       </tbody>
                     </table>
                   </div> 
                 </div>
               </div>
               <!-- Challan Section Starts-->
               
               <div class="collapse" id="challan" style="margin-left: 80px">
                 <label><h3></h3></label>
                 <div class="form-row">
                  <div class="form-group col-md-3">
                    <label>PO No</label>
                    <input type="text" class="form-control" placeholder="Po No" name="poNo">
                  </div>
                  <div class="form-group col-md-3">
                    <label>PO Date</label>
                    <input type="text" class="form-control" placeholder="PO Date" name="poDate">
                  </div>
                  <div class="form-group col-md-3">
                    <label>From</label>
                    <input type="text" class="form-control" name="receivedFrom">
                  </div>
                  <div class="form-group col-md-3">
                    <label>Consignee</label>
                    <input type="text" class="form-control" name="Consignee">
                  </div>
                   </div>
                <div class="form-row">
                </div>
                <div class="form-row">
                  <div class="form-group col-md-3">
                    <label>Transport Mode</label>
                    <input type="text" class="form-control" name="transportMode">
                  </div>
                  <div class="form-group col-md-3">
                    <label>Vheicle Number</label>
                    <input type="text" class="form-control" name="vheicleNumber">
                  </div>
                  <div class="form-group col-md-3">
                    <label>GST No</label>
                    <input type="text" class="form-control" name="gstNo">
                  </div>
                </div>
              </div>
              <!-- Challan Section Ends--> 
            
              
              <!-- Bill Section Starts -->
              <div class="collapse" id="bill" style="margin-left: 80px">
               <label><h3></h3></label>
               <div class="form-row">
                <div class="form-group col-md-3">
                  <label>Bill Number</label>
                  <input type="text" class="form-control" name="billNumber">
                </div>
                <div class="form-group col-md-3">
                  <label>Bill Date</label>
                  <input type="text" class="form-control" name="billDate">
                </div>
                <div class="form-group col-md-3">
                  <label>PO Number</label>
                  <input type="text" class="form-control" name="poNumber">
                </div>
                <div class="form-group col-md-3">
                  <label>GST Number</label>
                  <input type="text" class="form-control" name="gstNumber">
                </div>
              </div>
              <div class="form-row">
                
              </div>
            </div>
            <!-- Bill Section Ends -->
            
            
            <!-- Invoice Section Starts--> 
            
            <div class="collapse" id="invoice" style="margin-left: 80px"> 
              <label><h3></h3></label>	
              <div class="form-row">	
               <div class="form-group col-md-3">
                <label>TaxInvoice No</label>
                <input type="text" class="form-control" name="taxInvoiceNo">
              </div>
              <div class="form-group col-md-3">
                <label>Addressed To</label>
                <input type="text" class="form-control" name="addressedto1">
              </div>
              <div class="form-group col-md-3">
                <label>Order Date</label>
                <input type="text" class="form-control" name="orderDate">
              </div>
              <div class="form-group col-md-3">
                <label>Rate</label>
                <input type="text" class="form-control" name="rate">
              </div>
            </div>
            <div class="form-row">              
              <div class="form-group col-md-3">
                <label>Contact Name</label>
                <input type="text" class="form-control" name="contactName">
              </div>
              <div class="form-group col-md-3">
                <label>Mobile No</label>
                <input type="text" class="form-control" name="mobileNo">
              </div>
              <div class="form-group col-md-3">
                <label>Hsn/Sac</label>
                <input type="text" class="form-control" name="hsnOrSac">
              </div>
              <div class="form-group col-md-3">
                <label>Amount In Words</label>
                <input type="text" class="form-control" name="amtInwrd1">
              </div>
            </div>
            <div class="form-row">              
              <div class="form-group col-md-3">
                <label>Email</label>
                <input type="text" class="form-control" name="emailAddress">
              </div>
              <div class="form-group col-md-3">
                <label>Invoice Type</label>
                <select class="form-control" name="invoiceType" id="invoiceType">
                  <option></option>
                  <option value="Supply">Supply</option>
                  <option value="Labour">Labour</option>
                  <option value="Supply&Labour">Supply&Labour</option>
                </select>
              </div>
              <div class="form-group col-md-3">
                <label>Purchase Order No</label>
                <input type="text" class="form-control" name="orderNo">
              </div>
            </div>
          </div>
          <!-- Invoice Section Ends-->

          <!-- Accessories Section Starts--> 
          
          <div class="collapse" id="accessory" style="margin-left: 80px"> 
            <label><h3></h3></label>	
            <div class="form-row">	
             <div class="form-group col-md-3">
              <label>Accessory Name</label>
              <input type="text" class="form-control" name="accessoryName">
            </div>
            <div class="form-group col-md-3">
              <label>Description 1</label>
              <input type="text" class="form-control" name="desc1">
            </div>
            <div class="form-group col-md-3">
              <label>Description 2</label>
              <input type="text" class="form-control" name="desc2">
            </div>
            <div class="form-group col-md-3">
              <label>Description 3</label>
              <input type="text" class="form-control" name="desc3">
            </div>
          </div>
          <div class="form-row">
            
            <div class="form-group col-md-3">
              <label>Description 4</label>
              <input type="text" class="form-control" name="desc4">
            </div>
            <div class="form-group col-md-3">
              <label>Description 5</label>
              <input type="text" class="form-control" name="desc5">
            </div>
            <div class="form-group col-md-3">
              <label>Assigned Project</label>
              <select class='form-control' name='project' name='assignedProject' id='projectNm'><option></option>
              </select>
            </div>
            <div class="form-group col-md-3">
              <label>Location</label>
              <input type="text" class="form-control" name="location">
            </div>
          </div>
          <div class="form-row">
            
            <div class="form-group col-md-4">
              <input type="hidden" class="form-control" name="status" value"assigned">
            </div>
            <div class="form-group col-md-4" style="margin-left: 100px">
              <br>
              <button type="button" onClick="saveAccessory();" class="btn btn-default">Save Accessory</button>
            </div>
            <div class="form-group col-md-4">
              <input type="hidden" class="form-control" name="status" value"assigned">
            </div>
          </div>
        </div>
        <!-- Accessories Section Ends-->
        <div class="form-row">
         <div class="col-md-3 ">
           <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
            <br>
            <div class="form-check">
             <input class="form-check-input" type="checkbox" id="generateChallan" value="">
             <label class="form-check-label">
               Generate Challan
             </label>
           </div>
         </div>
       </div>
       <div class="col-md-3 ">
         <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
          <br>
          <div class="form-check">
           <input class="form-check-input" type="checkbox" id="addBillDetails" value="">
           <label class="form-check-label">
             Add Bill Details
           </label>
         </div>
       </div>
     </div>
     <div class="col-md-3 ">
       <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
        <br>
        <div class="form-check">
         <input class="form-check-input" type="checkbox" id="generateInvoice" value="">
         <label class="form-check-label">
           Generate Invoice
         </label>
       </div>
     </div>
   </div>				
   <div class="col-md-3 ">
     <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
      <br>
      <div class="form-check">
       <input class="form-check-input" type="checkbox" id="addAccessory" value="">
       <label class="form-check-label">
         Add Accessory
       </label>
     </div>
   </div>
 </div>
</div>       

<input type="hidden" name="generateChallan" id="gC1" value=""/>
<input type="hidden" name="generateInvoice" id="gI1" value=""/>
<input type="hidden" name="addBillDetails" id="aBD" value=""/>
<input type="hidden" name="addAccessory" id="aAcc" value=""/>

<div class="form-row">
	<div class="col-md-3 ">
   <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
     <br>
     <button type="button" onClick="hideOthers('challan')" data-toggle="collapse" data-target="#challan" class="btn btn-default">Challan Details</button>
   </div>
 </div>
 <div class="col-md-3 ">
   <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
     <br>
     <button type="button" onClick="hideOthers('bill')" data-toggle="collapse" data-target="#bill" class="btn btn-default">Bill Details</button>
   </div>
 </div>
 <div class="col-md-3 ">
   <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
     <br>
     <button type="button" onClick="hideOthers('invoice')" data-toggle="collapse" data-target="#invoice" class="btn btn-default">Invoice Details</button>
   </div>	
 </div>
 
 <div class="col-md-3 ">
   <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
     <br>
     <button type="button" onClick="hideOthers('accessory')" data-toggle="collapse" data-target="#accessory" class="btn btn-default">Add Accessory</button>
   </div>	
 </div>
 
</div>

<div class="form-row">
	<div class="col-md-3 ">
   <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
     <br>
     <button type="submit" id="updateButton" class="btn btn-default" Style="display: none;margin-left: 100px">Update Inventory</button>
   </div>	
 </div>	
</div>

</form>
<div class="row" style="max-width:97%;">
  <div class="col-md-12">
    <table class="table table-colored">
      <thead>
        <tr>
          <th>Inventory</th>
          <th>Material</th>
          <th>Type</th>
          <th>Manifacturing Method</th>
          <th>Class/Schedule</th>
          <th>Ends</th>
          <th>Size</th>
        </tr>
      </thead>
      <tbody id="tableContent">
       <tr>
        <td>
          <div class="form-group">
            <select class="form-control" name="inventoryName" id="inventoryName" onChange="myFunction(this.value,'inventoryName','material');">
              <option></option>
              <option value="Pipe">Pipe</option>
              <option value="Flange">Flange</option>
              <option value="Elbow">Elbow</option>
              <option value="Barrel Nipple">Barrel Nipple</option>
              <option value="Socket">Socket</option>
              <option value="Reducer">Reducer</option>
              <option value="Coupling">Coupling</option>
              <option value="Tee">Tee</option>
              <option value="Gasket">Gasket</option>
              <option value="Nut Bolt">Nut Bolt</option>
              <option value="Support">Support</option>
            </select>
          </div>
        </td>
        <td>
          <div class="form-group">
            <select class="form-control" name="material" id="material" onChange="myFunction(this.value,'material','type');">
              <option></option>
            </select>
          </div>
        </td>
        <td>
          <div class="form-group">
            <select class="form-control" name="type" id="type" onChange="myFunction($('#material').val(),'material','classOrGrade');">
              <option></option>
            </select>
          </div>
        </td>
        <td>
          <div class="form-group">
            <select class="form-control" name="manifacturingMethod" id="manifacturingMethod">
              <option></option>
              <option>Seamless</option>
              <option>ERW</option>
              <option>Centrifuge</option>
            </select>
          </div>
        </td>
        <td>
          <div class="form-group">
            <select class="form-control" name="classOrGrade" id="classOrGrade">
              <option></option>
            </select>
          </div>
        </td>
        <td>
          <div class="form-group">
            <select class="form-control" name="ends" id="ends">
              <option></option>
              <option>Buttweld</option>
              <option>Socket Weld/Threaded</option>
              <option>Threaded</option>
              <option>Plain End</option>
            </select>
          </div>
        </td>
        <td>
          <div class="form-group">
            <select class="form-control" name="size" id="size">
              <option></option>
              <option>1</option>
              <option>1.25</option>
              <option>1.5</option>
              <option>2</option>
              <option>2.5</option>
            </select>
          </div>
        </td>
      </tr>                 
    </tbody>
  </table>
</div>
</div>
</div>
<br>
<div class="row">
  <div class="col-md-4 ">
    <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
    </div>
  </div>
  <div class="col-md-4 ">
    <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
    </div>
  </div>
  <div class="col-md-4 ">
    <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
    </div>
  </div>
</div>
<div class="form-row">
  <div class="col-md-3 ">
   <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
   </div>
 </div>
 <div class="col-md-3 ">
   <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
    <br>
    
  </div>					
</div>
<div class="col-md-3">
 <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
  <label></label>
  <br>
  <button type="button" class="btn btn-default" onClick="appendInventory();">Add Inventory</button>		
</div>					
</div>											
</div>

<div class="form-row">
  <div class="col-md-4 ">
   <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
   </div>
 </div>
 <div class="col-md-4 ">
   <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
   </div>					
 </div>
 <div class="col-md-4 ">
   <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
    <label></label>
    
  </div>					
</div>
</div>
<br>
</div>
</div>
</div>
</section>

</section>
<!-- main-container end -->

<form action="saveAccessory" id="saveAccessory" method="POST" style="display:none;">
	
</form>
<input type="hidden" id="projectNamesList" value="${projectNames}" >


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
            <p class="text-left">Powered By Social Angels Digital Solution Pvt Ltd</p>
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

<script>
  function showInventory() 
  {
   $.ajax({
     type : 'POST',
     url : 'showInventory',
     success : function(data) {
       $('.inventoryDetails').css("display","block");
       $('#tableContentDetails').html(data);
       addProjectsToAvailableInventory();
     }
   });
 }
</script>
<script>
  function myFunction(value, tagName, nextTagName, inventory) {
    
    var tag = '#'+nextTagName;
    var inventory = $('#inventoryName')[0].value;
    $.ajax({
     type : 'POST',
     data :  {'value' : value,'currentTag' : tagName,'nextTagName' : nextTagName, 'inventory' : inventory},
     url : 'getDropdown',
     success : function(data) {
      
      console.log(data);
      console.log($(tag));
      $(tag).html("<option></option>");
      $(tag).append(data);
    }
  });
  }
</script>
<script>
  var i = 0;
  function appendInventory() 
  {

    var inventoryName = $('#inventoryName').children("option:selected").val();
    var material = $('#material').children("option:selected").val();
    var type = $('#type').children("option:selected").val();
    var manifacturingMethod = $('#manifacturingMethod').children("option:selected").val();
    var classOrGrade = $('#classOrGrade').children("option:selected").val();
    var ends = $('#ends').children("option:selected").val();
    var size = $('#size').val();
    
    var	template = "<tr>"
    + "    <td> <input type='button' value='X' onClick='removeRow($(this));'></td>" 
    + "    <td> <input type='hidden' name='inventoryName' value='"+inventoryName+"'></input>"+inventoryName+"</td>"
    + "    <td>  <input type='hidden' name='material' value='"+material+"'></input>"+material+"</td>"
    + "    <td> <input type='hidden' name='type' value='"+type+"'></input>"+type+"</td>"
    + "    <td> <input type='hidden' name='manifMethod' value='"+manifacturingMethod+"'></input>"+manifacturingMethod+"</td>"
    + "    <td> <input type='hidden' name='gradeOrClass' value='"+classOrGrade+"'></input>"+classOrGrade+"</td>"
    + "    <td> <input type='hidden' name='ends' value='"+ends+"'></input>"+ends+"</td>"
    + "    <td> <input type='hidden' name='size' value='"+size+"'></input>"+size+"</td>"
    + "	   <td><input type='text' name='quantity' value=''></input></td>"
    + "	   <td><input type='text' name='purchaseRate' value=''></input></td>"
    + "	   <td><select class='form-control' name='project' name='projectName' id='projectNm' onChange='getCompanyDetails($(this));' ><option></option></td>"
    + "	   <td><input type='text' name='location' value=''></input></td>"
    + "	   <td><select class='form-control' name='status' value='$(this).selected' id='status'><option value='assigned'>Assigned</option></td>";

    
    $('.inventoryToBeUpdated').css("display","block");          	
    $('#tableContentInventoryUpdateDetails').append(template);
    
    $('#updateButton').css("display","block");
    
    addProjects();       
  }

  function removeRow(thisObj)
  {
   console.log(thisObj.parent().parent().remove());
 }
</script>

<script>

  function saveAccessory()
  {				
    var line;
    
    var temp = $('[name="accessoryName"]');
    line = $(temp).clone();
    $('#saveAccessory').append($(line));
    
    var temp = $('[name="desc1"]');
    line = $(temp).clone();
    $('#saveAccessory').append($(line));
    var temp = $('[name="desc2"]');
    line = $(temp).clone();
    $('#saveAccessory').append($(line));
    var temp = $('[name="desc3"]');
    line = $(temp).clone();
    $('#saveAccessory').append($(line));
    var temp = $('[name="desc4"]');
    line = $(temp).clone();
    $('#saveAccessory').append($(line));
    var temp = $('[name="desc5"]');
    line = $(temp).clone();
    $('#saveAccessory').append($(line));
    
    var selectedProj = $('#projectNm').val();
    
    $('<input>').attr({
      type: 'hidden',
      name: 'assignedProject',
      value: selectedProj
    }).appendTo('#saveAccessory');
    
    
    $('#saveAccessory').append($(line));
    var temp = $('[name="location"]');
    line = $(temp).clone();
    $('#saveAccessory').append($(line));
    

    var temp = $('#projectId');
    line = $(temp).clone();
    $('#saveAccessory').append($(line));
    
    $('#saveAccessory').submit();
  }
</script>

<script>
  function addProjects()
  {
   // we define and invoke a function
   (function(){
    
     var inputArray = $('#projectNamesList').val().split(",");
     
     var names = [];
     $.each(inputArray, function(i, el){
      if($.inArray(el, names) === -1) 
      {
        names.push(el);
      }
    });

     var dummy = "<option value=\"taxInvoiceName\"><h5>taxInvoiceName</h5></option>";
     
     $.each(names,function(i){
      
      var dummy1 = dummy.replace("taxInvoiceName",names[i]);
      
      var tags = dummy1.replace("taxInvoiceName",names[i]);
      
      $('#projectNm').append(tags);
      $('#currentProjectList').append(tags);
      
    });
     
   })();
 }

</script>

<script>
  function addProjectsToAvailableInventory()
  {
   // we define and invoke a function
   (function(){
    
     var inputArray = $('#projectNamesList').val().split(",");
     
     var names = [];
     $.each(inputArray, function(i, el){
      if($.inArray(el, names) === -1) 
      {
        names.push(el);
      }
    });

     var dummy = "<option value=\"taxInvoiceName\"><h5>taxInvoiceName</h5></option>";
     
     $.each(names,function(i){
      
      var dummy1 = dummy.replace("taxInvoiceName",names[i]);
      
      var tags = dummy1.replace("taxInvoiceName",names[i]);
      
      $('.currentProjectList').append(tags);
      
    });
     
   })();
 }

</script>


<script>
  $(document).ready(function()
  {
   // we define and invoke a function
   (function(){
    
     var inputArray = $('#projectNamesList').val().split(",");
     
     var names = [];
     $.each(inputArray, function(i, el){
      if($.inArray(el, names) === -1) 
      {
        names.push(el);
      }
    });

     var dummy = "<option value=\"taxInvoiceName\"><h5>taxInvoiceName</h5></option>";
     
     $.each(names,function(i){
      
      var dummy1 = dummy.replace("taxInvoiceName",names[i]);
      
      var tags = dummy1.replace("taxInvoiceName",names[i]);
      
      $('#projectNm').append(tags);
      
    });
     
   })();
 });

</script>


<script>

  function getCompanyDetails(thisObj)
  {
   var projectName = $('#projectNm').val();
   
   $.ajax({
     type : 'POST',
     data :  {'projectName' : projectName},
     url : 'getProjectDetails',
     success : function(data) 
     {
      var inputArray = data.split(":");
      $.each(inputArray,function(i, el)
      {
       var pair = el.split('=');
       var ele = pair[0].trim();
       var valu = pair[1].trim();
       
       console.log(ele+":"+valu);
       
       if(ele=='poDate')
       {
         $('[name="poDate"]').val(valu);
       }
       else if(ele=='poNumber')
       {
         $('[name="poNo"]').val(valu);
       }
       else if(ele=='contactName')
       {
         $('[name="contactName"]').val(valu);
       }
       else if(ele=='contactPhone')
       {
         $('[name="mobileNo"]').val(valu);
       }
       else if(ele=='contactEmail')
       {
         $('[name="emailAddress"]').val(valu);
       }
       else if(ele=='gstNumber')
       {
         $('[name="gstNo"]').val(valu);
       }
       else if(ele=='finalRate')
       {
         $('[name="rate"]').val(valu);
       }
       else if(ele=='address')
       {
         $('[name="Consignee"]').val(valu);
         $('[name="addressedto1"]').val(valu);
       }
       
     });       
    }
  });
 }
</script>
<script>
	
	$('#updateInventory').submit(function(event) {

 	event.preventDefault(); //this will prevent the default submit

   if($('#generateChallan:checked').val() != undefined)
   {
    $('#gC1').val('1');
  }
  
  if($('#generateInvoice:checked').val() != undefined)
  {
    $('#gI1').val('1');
  }
  
  if($('#addBillDetails:checked').val() != undefined)
  {
    $('#aBD').val('1');
  }
  
  if($('#addAccessory:checked').val() != undefined)
  {
    $('#aAcc').val('1');
  }
 	$(this).unbind('submit').submit(); // continue the submit unbind preventDefault

 })
	
</script>

<script>
function statusTo(inventoryId) {

  var length = $('#'+inventoryId).find('input').length;
  var nameValMap = new Map();
    
  for( var i=0;i<length;i++)
  {
    var name = $('#'+inventoryId).find('input')[i].name;
    var valu = $('#'+inventoryId).find('input')[i].value;
    
    nameValMap.set(name,valu);
  }
  
  nameValMap.set($('#'+inventoryId).find('select')[0].name,$('#'+inventoryId).find('select')[0].value);
  
  var quantity    = nameValMap.get('quantity');
  var inventoryStr    = nameValMap.get('inventoryStr');
  var materialStr   = nameValMap.get('materialStr');
  var typeStr     = nameValMap.get('typeStr');
  var manifMethodStr  = nameValMap.get('manifMethodStr');
  var gradeOrClassStr = nameValMap.get('gradeOrClassStr');
  var endsStr     = nameValMap.get('endsStr');
  var sizeStr     = nameValMap.get('sizeStr');
  var purchaseRateStr = nameValMap.get('purchaseRateStr');
  var projectStr    = nameValMap.get('project');
  var locationStr   = nameValMap.get('locationStr');
  var statusTo    = nameValMap.get('status');
  
  if(statusTo !== '')
  {
    $.ajax({
      url: "release",
      data: { 'inventoryStr' :  inventoryStr,
          'materialStr' :   materialStr,    
          'typeStr' :     typeStr,      
          'manifMethodStr' :  manifMethodStr, 
          'gradeOrClassStr' : gradeOrClassStr, 
          'endsStr' :     endsStr,      
          'sizeStr' :     sizeStr,      
          'purchaseRateStr' : purchaseRateStr, 
          'projectStr' :    projectStr,   
          'locationStr' :   locationStr,
          'quantity'   :    quantity,   
          'statusTo' :    statusTo},
      type: 'post',
      success: function(data) {
        console.log(data);
      }
    });
  } 

};
</script>
<script>
function accessoryStatusTo(accessoryId){
  
  var length = $('#'+accessoryId).find('input').length;
  var nameValMap = new Map();
    
  for( var i=0;i<length;i++)
  {
    var name = $('#'+accessoryId).find('input')[i].name;
    var valu = $('#'+accessoryId).find('input')[i].value;
    
    nameValMap.set(name,valu);
  }
  
  nameValMap.set($('#'+accessoryId).find('select')[0].name,$('#'+accessoryId).find('select')[0].value);
   
  var quantity      = nameValMap.get('quantity');
  var accessoryStatusTo = nameValMap.get('status');
  var desc1         = nameValMap.get('desc1');
  var desc2         = nameValMap.get('desc2');
  var desc3       = nameValMap.get('desc3');
  var desc4       = nameValMap.get('desc4');
  var desc5       = nameValMap.get('desc5');
  var accessoryName     = nameValMap.get('accessoryName'); 
  var project       = nameValMap.get('assignedProject');
  var locationStr     = nameValMap.get('locationStr');

  
  if(accessoryStatusTo !== '')
  {
    $.ajax({
      url: "releaseAccessory",
      data: { 'quantity'      : quantity,
          'accessoryStatusTo' : accessoryStatusTo,
          'desc1'       : desc1,
          'desc2'       : desc2,
          'desc3'       : desc3,
          'desc4'       : desc4,
          'desc5'       : desc5,
          'accessoryName'   : accessoryName,
          'project'       : project,
          'locationStr'     : locationStr},
      type: 'post',
      success: function(data) {
        console.log(data);
      }
    });
  } 

};
</script>

<script>
function hideOthers(idToBeOpen)
{

  var idVals = ["challan","bill","invoice","accessory"];
  
  for(var i=0; i< idVals.length; i++)
  {
    if(idToBeOpen !== idVals[i])
    {
      $('#'+idVals[i]).hide();
    
    }
    else
    {
      $('#'+idVals[i]).show();
    }
  
  }


}
</script>
</body>
</html>