<!DOCTYPE html>
<html dir="ltr" lang="zxx">

<head>
  <meta charset="utf-8">
  <title>Update Inventory</title>
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
<body class=" ">
  <div class="scrollToTop circle"><i class="fa fa-angle-up"></i></div>
  <div class="page-wrapper">
    <div class="header-container">
      <div class="sticky-wrapper" style="height: 111px;"><div class="sticky-wrapper" style="height: 110px;"><header class="header fixed fixed-desktop clearfix object-visible">
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
<div class="banner padding-bottom-clear" style="width:80%;margin-left: 10%;margin-top: 2%;">
  <div class="container">
    <div class="row justify-content-lg-center">
      <div class="col-lg-8 text-center pv-20">
        <h2 class="title object-non-visible" data-animation-effect="fadeIn" data-effect-delay="100">Select a Project and PO</h2>
        <div class="separator object-non-visible mt-10" data-animation-effect="fadeIn" data-effect-delay="100"></div>
      </div>
    </div>
  </div>
  <div class="section" style="background-color: #09afdf;">
    <div class="container-fluid">
      <div class="sorting-filters text-center mb-20 d-flex justify-content-center">
        <form class="form-inline">
          <div class="form-group ml-2">
            <h3 style="color: #ffffff;">Project</h3>
            <select class="form-control" id="projectId" onChange="populatePOList($('#projectId').val());">                 
            </select>
          </div>
          <div class="form-group ml-2">
          </div>
          <div class="form-group ml-5">
            <h3 style="color: #ffffff;">Purchase Order to Vendors</h3>
            <select class="form-control" id="poList" onChange="populatePODetails($('#poList').val());">
              <option></option>
            </select>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>
<section class="main-container">
  <div class="container">
    <form action="updateInventory" id="updateInventory" method="POST">
      <dir class="row" style="width:120%;margin-left: -11%;">          
        <table class="table table-colored">
          <thead>
           <tr>
            <th></th>
            <th>Item</th>
            <th>Material</th>
            <th>Type</th>
            <th>Manifacturing Method</th>
            <th>Class/Schedule</th>
            <th>Ends</th>
            <th>Size</th>
            <th>Purchase Rate</th>
            <th>PO Quantity</th>
            <th>Received Quantity</th>
            <th>Location</th>
          </tr>
        </thead>
        <tbody id="poDetailsTable">              
        </tbody>
      </table>        
    </dir>
    <div class="form-row border border-info" style="width:115%; margin-left: -7%; padding : 1%;">
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
       <!--<input class="form-check-input" type="checkbox" id="addBillDetails" value="">
       <label class="form-check-label">
         Add Bill Details
       </label>-->
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
<input type="hidden" name="generateChallan" id="gC1" value=""/>
<input type="hidden" name="generateInvoice" id="gI1" value=""/>
<input type="hidden" name="addBillDetails" id="aBD" value=""/>
<input type="hidden" name="project" id="project" value=""/>
</div>
<div class="row" style="margin-left: 0%;">
  <div class="col-md-3">
   <div class="ph-20 feature-box text-center">
     <br>
     <button type="submit" id="updateButton" class="btn btn-default">Update Item</button>
   </div> 
 </div> 
 <div class="col-md-3 ">
  <div class="ph-20 feature-box text-center" >
   <br>
   <button type="button" onClick="hideOthers('challan')" data-toggle="collapse" data-target="#challan" class="btn btn-default">Challan Details</button>
 </div>
</div>
   <!-- <div class="col-md-2 ">
 <div class="ph-20 feature-box text-center " >
   <br>
   <button type="button" onClick="hideOthers('bill')" data-toggle="collapse" data-target="#bill" class="btn btn-default">Bill Details</button>
 </div>
</div>-->
<div class="col-md-3 ">
 <div class="ph-20 feature-box text-center" >
   <br>
   <button type="button" onClick="hideOthers('invoice')" data-toggle="collapse" data-target="#invoice" class="btn btn-default">Invoice Details</button>
 </div> 
</div>
<div class="col-md-3">
  <div class="ph-20 feature-box text-center" >
   <br>
   <button type="button" data-toggle="modal" data-target="#availableInventoryModal" class="btn btn-default" onclick="showInventory();">Show Available Item</button>
 </div>
</div>
</div>
<div class="collapse border border-info" id="challan" style="width:115%; margin-left: -8%; padding: 1%;">
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
    <input type="text" class="form-control" name="receivedFrom" value="Hamdule Industries">
  </div>
  <div class="form-group col-md-3">
    <label>Consignee</label>
    <input type="text" class="form-control" name="Consignee" value="">
  </div>
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
<div class="collapse border border-info" id="invoice" style="width:115%; margin-left: -8%; padding: 1%;"> 
  <label><h3></h3></label>  
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
      <label>Addressed To</label>
      <input type="text" class="form-control" name="addressedto1">
    </div>
    <div class="form-group col-md-3">
      <label>Order Date</label>
      <input type="text" class="form-control" name="orderDate">
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
    <label>Hsn/Sac</label>
    <input type="text" class="form-control" name="hsnOrSac">
  </div>
  <div class="form-group col-md-3">
    <br/>
  </div>
  <input type="hidden" name="orderNo" value=""/>
</div>
</div>
</form>
<div class="separator object-non-visible mt-10 animated object-visible fadeIn" data-animation-effect="fadeIn" data-effect-delay="100"></div>
<div class="modal fade bd-example-modal-lg" id="availableInventoryModal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
 <div class="modal-dialog modal-lg" role="document">
  <div class="modal-content" style="width:100%;">
   <div class="modal-header">
    <h4 class="modal-title" id="myModalLabel">Available Inventory</h4>
  </div>
  <div class="modal-body">
    <div class="table-responsive">
      <table class="table table-colored inventoryDetails" style="display:none;">
        <thead>
          <tr>
           <th></th>
           <th>Item</th>
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
</div>
</div>
<div class="row" style="margin-left: -11%;">

</div>
</div>
</section>
<footer id="footer" class="clearfix ">
  <div class="subfooter">
    <div class="container">
      <div class="subfooter-inner">
        <div class="row">
          <div class="col-md-12">
            <p class="text-center">Copyright � 2020 Project Inventory Manager. All rights reserved.</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</footer>
</div>
<input type="hidden" id="projectNamesList" value="${projectNames}" >
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

<script type="text/javascript">
  $(document).ready(function(){
   (function(){
     var inputArray = $('#projectNamesList')[0].value.split(",");
     var names = [];
     $.each(inputArray, function(i, el){
      if($.inArray(el, names) === -1) 
      {
        names.push(el);
      }
    });
     var dummy = "<option value=\"poName\"><h5>poName</h5></option>";
     $.each(names,function(i){
      var dummy1 = dummy.replace("poName",names[i]);
      var tags = dummy1.replace("poName",names[i]);
      $('#poList').append(tags);
    });

   })();
 });
</script>
<script>
  function populatePOList(projectId)
  {
    var dummy = "<option value=\"poName\"><h5>poName</h5></option>";
    $('#LoadingImage').show();

    $.ajax({
     type : 'POST',
     data :  {'projectName' : projectId},
     url : 'getPoList',
     success : function(data) {
      
      var blank = "<option> </option>";
      $('#poList').html(blank);

      var poVals = data.split("::");
      var inputArray = poVals[0].split(",");

      console.log(poVals[1]);

      var projectDetails = poVals[1].substring(poVals[1].indexOf('['), poVals[1].indexOf(']'));

      var projectDetailsArray = projectDetails.split(':');


      for(var k =0;k<projectDetailsArray.length;k++)
      {
        if(projectDetailsArray[k].indexOf('contactName') !== -1)
        {
          $('[name="contactName"]').attr('value',projectDetailsArray[k].substring(projectDetailsArray[k].indexOf('=')+1));
        }
        else if(projectDetailsArray[k].indexOf('contactPhone') !== -1)
        {
          $('[name="mobileNo"]').attr('value',projectDetailsArray[k].substring(projectDetailsArray[k].indexOf('=')+1));
        }
        else if(projectDetailsArray[k].indexOf('contactEmail') !== -1)
        {
          $('[name="emailAddress"]').attr('value',projectDetailsArray[k].substring(projectDetailsArray[k].indexOf('=')+1));
        }
        else if(projectDetailsArray[k].indexOf('address') !== -1)
        {
          $('[name="addressedto1"]').attr('value',projectDetailsArray[k].substring(projectDetailsArray[k].indexOf('=')+1));
        }
        else if(projectDetailsArray[k].indexOf('gstNumber') !== -1)
        {
          $('[name="gstNo"]').attr('value',projectDetailsArray[k].substring(projectDetailsArray[k].indexOf('=')+1));
        }
        
      }

      var names = [];
      $.each(inputArray, function(i, el){
        if($.inArray(el, names) === -1) 
        {
          names.push(el);
        }
      });
      var dummy = "<option value=\"poName\"><h5>poName</h5></option>";
      $.each(names,function(i){
        var dummy1 = dummy.replace("poName",names[i]);
        var tags = dummy1.replace("poName",names[i]);
        $('#poList').append(tags);      
      });
    }
  });
    $('#LoadingImage').hide();
  }
</script>
<script type="text/javascript">
  function populatePODetails(poNo)
  {
   $.ajax({
     type : 'POST',
     data :  {'poNumber' : poNo},
     url : 'getPoDetails',
     success : function(data) {
            
      $("[name='orderNo']")[0].value = poNo;

      var htmlData = data.split('::');
      $('#poDetailsTable').html(htmlData[0]);

      console.log(htmlData[1]);

      var poDetailsArray = htmlData[1].split(':');
      for(var k=0;k < poDetailsArray.length;k++)
      {
        if(poDetailsArray[k].indexOf('poNumber') !== -1)
        {
          $('[name="poNo"]').attr('value',poDetailsArray[k].substring(poDetailsArray[k].indexOf('=')+1));
        }
        else if(poDetailsArray[k].indexOf('poDate') !== -1)
        {
          $('[name="poDate"]').attr('value',poDetailsArray[k].substring(poDetailsArray[k].indexOf('=')+1));
        }
      }
    }
  });
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
      
      $('#projectId').append(tags);
      
    });
     
   })();
 });

</script>

<script>
  function hideOthers(idToBeOpen)
  {

    var idVals = ["challan","bill","invoice","accessory"];

    for(var i=0; i< idVals.length; i++)
    {
      if(idToBeOpen !== idVals[i])
      {
        $('#'+idVals[i]).collapse("hide");

      }
      else
      {
        if($('#'+idToBeOpen).hasClass('collapse'))
        {
          $('#'+idToBeOpen).collapse("show");  
        }
        else
        {
         $('#'+idToBeOpen).collapse("hide"); 
       }

     }

   }
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
/*    if($('#addBillDetails:checked').val() != undefined)
    {
      $('#aBD').val('1');
    }*/
    if($('#addAccessory:checked').val() != undefined)
    {
      $('#aAcc').val('1');
    }

    var projectName = $('#projectId').val();
    $('#project').val(projectName);

  $(this).unbind('submit').submit(); // continue the submit unbind preventDefault
})
</script>
<script type="text/javascript">
  function removeRow(thisObj)
  {
   console.log(thisObj.parent().parent().remove());
 }
</script>
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
</body>
</html>