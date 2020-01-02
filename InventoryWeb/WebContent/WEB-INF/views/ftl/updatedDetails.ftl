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
  <link href="css/custTooltip.css" rel="stylesheet">

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
<!-- "boxed": boxed layout mode e.g. <body "projDetails"boxed"> -->
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
        <!-- header-container start -->
        <div class="header-container">
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
          <header class="header fixed fixed-desktop clearfix">
            <div class="container">
              <div class="row">
                 <div class="col-md-8">
                   <div class="navbar navbar-default navbar-static-top container" style="margin-left: 0px; margin-right: 0px;">
                    <div class="navbar-header" style="width: 200px;">
                   <img src="./loginPage/img/Hamdule-Logo1.png" alt="" style="width: 250px;height: 55px;margin-top: 20px;margin-left: -150px;">
                </div>
                </div>
                </div> 
                <div class="col-md-2">
                  </div>
                  <div class="col-md-2"> 
                <form class="form-horizontal" action="logout" method="POST" style="margin-left: 100px;margin-top: 30px;">
             
                  <button type="submit" class="btn btn-default btn-animated">Log Out <i class="fa fa-user"></i></button>
                </form>
              </div>
            </div>
                <div class="col-lg-8 ml-auto">

                  <!-- header-second start -->
                  <!-- ================ -->
                  <div class="header-second clearfix">

                    <!-- main-navigation start -->
                    <!-- classes: -->
                    <!-- "onclick": Makes the dropdowns open on click, this the default bootstrap behavior e.g. class="main-navigation onclick" -->
                    <!-- "animated": Enables animations on dropdowns opening e.g. class="main-navigation animated" -->
                    <!-- ================ -->
                    <div class="main-navigation main-navigation--mega-menu  animated">
                      <nav class="navbar navbar-expand-lg navbar-light p-0">
                        <div class="navbar-brand clearfix hidden-lg-up">

                          <!-- logo -->
                         

                          <!-- name-and-slogan -->
                         
                        

                        <div class="collapse navbar-collapse" id="navbar-collapse-1">
                        </div>
                      </nav>
                    </div>
                    <!-- main-navigation end -->
                  </div>
                  <!-- header-second end -->

                </div>
                <div class="col-auto hidden-md-down">
                </div>
              </div>
            </div>
          </header>
          <!-- header end -->
        </div>
        <!-- header-container end -->
        <!-- breadcrumb start -->
        <!-- ================ -->

        <!-- breadcrumb end -->

        <!-- main-container start -->
        <!-- ================ -->
        <section class="main-container padding-bottom-clear">
          <!-- section -->
          <!-- ================ -->
          <section class="light-gray-bg pv-30 padding-bottom-clear clearfix">
            <div class="container" style="max-width:98%;">
              <div class="row">
                <div class="col-md-7 ">
                  <div class="pv-30 ph-20 feature-box bordered shadow text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">

                    <h3>${projectName}</h3>
                    <div class="separator clearfix"></div>
                    <p>${projectDesc}</p>

                  </div>

                </div> 
                <aside class="col-md-5 col-xl-5 ml-xl-auto">
                  <div class="sidebar">
                    <div class="block clearfix">
                      <h3 class="title">Project Details</h3>
                      <div class="separator-2"></div>
                      <ul class="list margin-clear">
                        <li><strong>PO Date: </strong> <span class="text-right">${poDate}</span>  <strong style="padding-left: 29%;">PO Number:</strong> <span>${poNumber}</span></li>
                        <li><strong>Contact Person: </strong> <span class="text-right">${contactName}</span>  <strong style="padding-left: 25%;">Contact Number: </strong> <span class="text-right">${contactPhone}</span></li>
                        <li><strong>Address: </strong> <span class="text-right">${address}</span> <strong style="padding-left: 20%;">Contact Email: </strong> <span class="text-right">${contactEmail}</span></li>                    
                        <li><strong>GST #: </strong> <span class="text-right">${gstNumber}</span></li>
                      </ul>

                    </div>
                  </div>
                </aside>			  
              </div>
              <br>

              <table id="payDetailsSection" class="table table-striped table-colored" style="display:none;">
               <thead>
                 <tr>
                   <th>Payment ID</th>
                   <th>TaxInvoice Number</th>
                   <th>Amount</th>
                   <th>Date Received</th>
                 </tr>
               </thead>
               ${paymentDetails}
               <tbody>
               </tbody>
             </table>

             <!-- Update project Section START -->

             <form action="updateProject" class="projDetails" style="display:none;" method="POST">
              <div class="row" >
                <div class="col-md-4 ">
                  <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
                    <div class="form-group has-feedback">
                      <label>PO Date</label>
                      <input type="text" name="poDate" class="form-control" value=" ">                   
                    </div>
                  </div>
                </div>
                <div class="col-md-4 ">
                  <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
                    <label>PO Number</label>
                    <input type="text" name="poNumber" class="form-control" value=" ">
                  </div>
                </div>
                <div class="col-md-4 ">
                  <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
                   <label>Contact Person Name</label>
                   <input type="text" name="contactName" class="form-control" >
                 </div>
               </div>
             </div>
             <br>
             <div class="row">
              <div class="col-md-4 ">
                <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
                  <label>Contact Email</label>
                  <input type="text" name="contactEmail" class="form-control">
                </div>
              </div>
              <div class="col-md-4 ">
                <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
                 <label>Contact Phone</label>
                 <input type="text" name="contactPhone" class="form-control">
               </div>
             </div>
             <div class="col-md-4 ">
              <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
               <label>GST Number</label>
               <input type="text" name="gstNumber" class="form-control" value=" ">
             </div>
           </div>
         </div>
         <br>
         <div class="row">
          <div class="col-md-4 ">
            <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
             <label>Address</label>
             <input type="text" name="address" class="form-control">
           </div>
         </div>

         <div class="col-md-4 ">
           <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
            <label></label>

          </div>					
        </div>
        <div class="col-md-4 ">
         <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
          <label></label>
          <br>
          <button type="submit" class="btn btn-default">Update Project</button>
        </div>					
      </div>
      <input type="hidden" name="projectId" value="${projectId}"/>
    </div>
  </form>

  <!-- Update project Section END -->

  <form name="generateBOQ" id="generateBOQ" action="generate" method="POST" >
   <div class="row importBOQ2" style="display:none;">
    <div class="col-md-3 ">
     <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
      <label>D Name</label>
      <input type="text" name="dName" value="" />
    </div>
  </div>
  <div class="col-md-3 ">
   <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
    <label>Utility  &ensp;</label>
    <input type="text" name="utility" value="" />
  </div>
</div>
<div class="col-md-3 ">
 <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
  <label>Pressure</label>
  <input type="text" name="pressure" value="" />
</div>
</div>
<div class="col-md-3 ">
 <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
  <label>Temperature</label>
  <input type="text" name="temperature" value="" />
</div>
</div>
</div>
<div class="row importBOQ2" style="display:none;">
  <div class="col-md-3 ">
   <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
    <label>D Number</label>
    <input type="text" name="dNo" value="" />
  </div>
</div>
<div class="col-md-3 ">
 <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
  <label>Client &ensp;</label>
  <input type="text" name="client" value="" />
</div>
</div>
<div class="col-md-3 ">
 <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
  <label>Site &ensp;&ensp;&ensp;</label>
  <input type="text" name="site" value="" />
</div>
</div>
<div class="col-md-3 ">
 <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
  <label>Project &ensp;</label>
  <input type="text" name="project" value="" />
</div>
</div>
</div>
<input type="hidden" name="projectId" value="${projectId}" />
<div class="row inventoryTableHeader" style="display:none;">
  <div class="col-md-12 ">

    <div class="table">                
      <table class="table table-colored">
        <thead id="tableHeader">
          <tr>
           <th>Select</th>
           <th>Inventory</th>
           <th>Material</th>
           <th>Type</th>
           <th>Manifacturing Method</th>
           <th>Grade/Class</th>
           <th>Ends</th>
           <th>Size</th>
           <th>Available Quantity</th>
           <th>Quantity</th>
           <th>Base Supply Rate</th>
           <th>Supply Rate</th>
           <th>Base Erection Rate</th>
           <th>Erection Rate</th>
           <th>Supply Amount</th>
           <th>Erection Amount</th>
         </tr>
         <tr>
          <th></th>
          <th></th>
          <th></th>
          <th></th>
          <th></th>
          <th></th>
          <th></th>
          <th></th>
          <th></th>
          <th></th>
          <th><input type="text" style="width:45px;" name="supplyPrsnt" onChange="updateSupplyRate($(this));"/></th>
          <th></th>
          <th><input type="text" style="width:45px;" name="erectionPrsnt" onChange="updateErectionRate($(this));"/></th>
          <th></th>
          <th></th>
          <th></th>
        </tr>
      </thead>

      <tbody id="tableContent">

      </tbody>

    </table>
  </div>

</div>
</div>
<br>
<div class="row">
  <div class="col-md-3 ">
    
     <h4>Quotation Revisions</h4>
     <select class="form-control" id="revisionSection" onChange="download($('#revisionSection').val());">
      <option></option>
    </select>                
  
</div>
<div class="col-md-3 ">
  
    <h4>Inquiry List</h4>
    <select class="form-control" onChange="download($('#offerRevisionSection').val());" id="offerRevisionSection">
      <option></option>
    </select>
  
</div>
<div class="col-md-3 ">
  
    <h4>TaxInvoice List</h4>
    <select class="form-control" onChange="downloadInvoice($('#taxInvoiceList').val());" id="taxInvoiceList">
      <option></option>
    </select>
  
</div>
<div class="col-md-3 ">
  
    <h4>PO List:</h4> 
    <select class="form-control" onChange="downloadPO($('#poList').val());" id="poList">
      <option></option>
    </select>
 
</div>
</div>
<div class="row" style="margin-top: 10px">
  <div class="col-md-3">
   
    
    <button type="button" onClick="generatePO();" class="btn btn-default">Generate PO</button>
					
</div>

<div class="col-md-3" id="generate">
 
   
   <button type="Submit" class="btn btn-default">Generate</button>

</div>
<div class="col-md-3 ">

<div class="row" style="margin-top: 10px; margin-left: 10px">

  <h4>BOQ Name :</h4> 
  <input type="text" name="boqName" value="${projectName}" ></input>
</div>
</div>
<div class="col-md-3" id="generateQuot">
 
  <label>
    <input type="hidden" name="boqNameList" value="${boqNameList}" >
    <input type="hidden" name="quotationNamesList" value="${quotationNamesList}" >
  </label>
<button type="button" onClick="createInquiry();" class="btn btn-default">Generate Inquiry</button>		
</div>					
</div>
</div>
</form>

<!--Create BOQ Start-->
<!-- Start Create BOQ -->

<form name="generateBOQ" action="generateNew" class="createBOQ" style="display:none;" method="POST">
  <div class="row">
    <div class="col-md-3 ">
     <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
      <label>D Name</label>
      <input type="text" name="dName" value="" />
    </div>
  </div>
  <div class="col-md-3 ">
   <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
    <label>Utility  &ensp;</label>
    <input type="text" name="utility" value="" />
  </div>
</div>
<div class="col-md-3 ">
 <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
  <label>Pressure</label>
  <input type="text" name="pressure" value="" />

</div>
</div>
<div class="col-md-3 ">
 <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
  <label>Temperature</label>
  <input type="text" name="temperature" value="" />

</div>
</div>
</div>
<div class="row">
  <div class="col-md-3 ">
   <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
    <label>D Number</label>
    <input type="text" name="dNo" value="" />
  </div>
</div>
<div class="col-md-3 ">
 <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
  <label>Client &ensp;</label>
  <input type="text" name="client" value="" />
</div>
</div>
<div class="col-md-3 ">
 <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
  <label>Site &ensp;&ensp;&ensp;</label>
  <input type="text" name="site" value="" />

</div>
</div>
<div class="col-md-3 ">
 <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">
  <label>Project &ensp;</label>
  <input type="text" name="project" value="" />

</div>
</div>
</div>
<div class="row">
 <div class="col-md-3">
  <div class="ph-20 feature-box text-center object-non-visible" data-animation-effect="fadeInDownSmall" data-effect-delay="100">

   <label>BOQ Name : 
    <input type="text" name="boqName" value=${projectName} />
    <input type="hidden" name="boqNameList" value=${boqNameList} >
    <input type="hidden" name="quotationNamesList" value=${quotationNamesList} >
    <input type="hidden" name="taxInvoiceNamesList" value=${taxInvoiceNamesList} >
    <input type="hidden" name="poNamesList" value=${poNamesList} >
  </label>

</div>
</div>
</div>
<input type="hidden" id="projectId" name="projectId" value="${projectId}"/>
<div class="row">
  <div class="col-md-12 ">
    <div class="table-responsive">                
      <table class="table table-colored inventoryDetails" style="display:none;">
        <thead>
          <tr>
            <th>Inventory</th>
            <th>Material</th>
            <th>Type</th>
            <th>Manifacturing Method</th>
            <th>Class/Schedule</th>
            <th>Ends</th>
            <th>Size</th>
            <th>Quantity</th>
            <th/>
            <th>Supply Rate</th>
            <th/>
            <th>Erection Rate</th>
            <th>Supply Amount</th>
            <th>Erection Amount</th>
          </tr>
        </thead>
        <tbody id="tableContentDetails">
        </tbody>
      </table>
    </div>
  </div>
</div>
<div class="row">
  <div class="col-md-4 ">
   
  </div>
  <div class="col-md-4 ">
   	
  </div>
  <div class="col-md-4 ">
    
      <button type="submit" class="btn btn-default generateBOQButton" style="display:none;">Gnerate Quotation</button>
    </div>	
 	
</div>

</form>

<table class="table table-colored createBOQ" style="display:none;">
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
         <option value="Socket">Socket</option>
         <option value="Barrel Nipple">Barrel Nipple</option>
         <option value="Coupling">Coupling</option>
         <option value="Reducer">Reducer</option>
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


<div class="form-row createBOQ" style="display:none;">
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
    <br>
    <button type="button" class="btn btn-default" onClick="appendInventory();">Add Inventory</button>		
  </div>					
</div>
</div>
				

<!-- End Create BOQ -->


<!--Create BOQ End-->
<div class="container" style="min-width: 98%">
<div class="row">
  <div class="col-md-3">
   
    <button type="button" class="btn btn-default" onClick="toggleCreateBOQSec();">Create BOQ</button>
    				
</div>							
<div class="col-md-3">
 
  <form name="fileUploadForm" action="import" method="post" enctype="multipart/form-data" style="margin: 0px">
       <button class="btn btn-default" name="uploadFile">Upload</button>
   <label for="choose">Choose File</label>
   <input id="choose" type="file" name="file" onClick="toggleImportSec();" style="display:none;" class="btn btn-default">

 </form>    					
					
</div>
<div class="col-md-3 ">

  <button type="button" class="btn btn-default" onClick="toggleProjSec();">Project Details Section</button>
					
</div>
<div class="col-md-3 ">

  <button type="button" class="btn btn-default" onClick="togglePayDetailsSec();">Received Payment Details</button>
</div>					
</div>
</div>

<div class="form-row">
  <div class="col-md-3">
    <br><button type="button" class="btn btn-default" style="margin-left:8%;" data-toggle="collapse" data-target="#assignedInventory">Show Assigned Inventory</button>
</div>
<div class="col-md-3">
    <br><button type="button" class="btn btn-default" style="margin-left:6%;" data-toggle="collapse" data-target="#consumedInventory">Show Consumed Inventory</button>
</div>
</div>	
<!-- </div> -->

<br>
<!-- </div> -->

<div class="container collapse" style="max-width:98%;" id="assignedInventory" >
  <div class="col-md-12 ">
    <div class="table-responsive">                

      <table class="table table-colored assignedInventorySec" style="display:none;">
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
           <th>Mark as</th>
         </tr>
       </thead>
       <tbody id="tableContentDetails">
        ${assignedInventory}
      </tbody>
    </table>
    <table class="table table-colored assignedInventorySec" style="display:none;">
      <thead>
        <tr>
         <th></th>
         <th>Desc 1</th>
         <th>Desc 2</th>
         <th>Desc 3</th>
         <th>Desc 4</th>
         <th>Desc 5</th>
         <th>Accesory Name</th>
         <th></th>
         <th>Quantity</th>
         <th>Purchase Rate</th>
         <th>Project</th>
         <th>Location</th>
         <th>Mark as</th>
       </tr>
     </thead>
     <tbody id="tableContentDetails">
       ${assignedAccessory}
     </tbidy>
   </table>
 </div>	
</div>
</div>
<!-- Assugned Inventory Section ENDS-->

<!-- Consumed Inventory Sesion STARTS -->
<div class="container collapse" style="max-width:98%;" id="consumedInventory" >
  <div class="col-md-12 ">
    <div class="table-responsive">                

      <table class="table table-colored" >
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
         </tr>
       </thead>
       <tbody >
        ${consumedInventory}
      </tbody>
    </table>
    <table class="table table-colored">
      <thead>
        <tr>
         <th></th>
         <th>Desc 1</th>
         <th>Desc 2</th>
         <th>Desc 3</th>
         <th>Desc 4</th>
         <th>Desc 5</th>
         <th>Accesory Name</th>
         <th></th>
         <th>Quantity</th>
         <th>Purchase Rate</th>
         <th>Project</th>
         <th>Location</th>
       </tr>
     </thead>
     <tbody>
       ${consumedAccessory}
     </tbidy>
   </table>
 </div> 
</div>
</div>
<!-- Consumed Inventory Sesion STARTS -->




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
            <p class="text-center">Powered By Social Angels Digital Solution Pvt Ltd.</p>
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

<form action="generateOrderForm" id="generateOffer" method="POST" style="display:none;">
	
</form>
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
  function captureFileLocation() {
    var fileLocation;
    var entrerLocationRequest = prompt("Please Enter the file location", "BOQ File Location");
    if (entrerLocationRequest == null || entrerLocationRequest == "") {
      fileLocation = "User cancelled the prompt.";
    } else {
      fileLocation = entrerLocationRequest;
    }
    console.log("File Location : "+fileLocation);

    if(fileLocation != "User cancelled the prompt.")
    {
     $.ajax({
       type : 'POST',
       data : {'location' : fileLocation},
       url : 'import',
       success : function(data) {

        $('#tableContent').html(data);
        adjustWidth();
      }
    });

     var imp = document.getElementById("importBoq");
        //imp.style.display = "none";
        
        var generate = document.getElementById("generate");
        generate.style.display = "block";
        
        var generateInq = document.getElementById("generateQuot");
        generateInq.style.display = "block";
      }
    }
    function adjustWidth() 
    {
     console.log('Inside adjustWidth');
/* var inputArray = $('input');
var i;
for(i=0;i<inputArray.length;i++)
{
	$('input')[i].style.width = parseFloat(inputArray[i].value.length)*2 + "ch";
	console.log($('input')[i].css);
}
*/
}

</script>
<script>
  function myFunction(value, tagName, nextTagName) {

    var tag = '#'+nextTagName;
    var inventory = $('#inventoryName')[0].value;
    
    $.ajax({
     type : 'POST',
     data :  {'value' : value,'currentTag' : tagName,'nextTagName' : nextTagName, 'inventory' : inventory},
     url : 'getDropdown',
     success : function(data) {

      console.log(data);
      console.log($(tag));

      var blank = "<option> </option>";
      $(tag).html(blank);
      $(tag).append(data);
    }
  });
  }
</script>
<script>
  function updateSupplyRate(thisObj)
  {

    var rate = [];
    var quantity = [];

    $("input[name='baseSupplyRate']").each(function() {

      var sRate = parseFloat($(this).val()) + parseFloat($("[name='supplyPrsnt']").val()*$(this).val()/100);

      if(sRate % 5 != 0)
      {
        sRate + 5 - sRate % 5
      }
      rate.push(sRate);
    });

    var int = 0;
    $("input[name='supplyRate']").each(function() {
      $(this).val(rate[int]);
      int++;
    });

    $("input[name='quantity']").each(function() {
     quantity.push($(this).val());
   });

    var i = 0;

    $("input[name='supplyAmount']").each(function() {
     console.log(rate[i]);
     console.log(quantity[i]);
     $(this).val(rate[i]*quantity[i]);
     i++;
   });

  }
</script>        
<script>
  function updateErectionRate(thisObj)
  {

    var rate = [];
    var quantity = [];

    $("input[name='baseErectionRate']").each(function() {
      var eRate = parseFloat($(this).val()) + parseFloat($("[name='erectionPrsnt']").val()*$(this).val()/100);

      if(eRate % 5 != 0)
      {
        eRate + 5 - eRate % 5
      }

      rate.push(eRate);
    });

    var int = 0;
    $("input[name='erectionRate']").each(function() {
      $(this).val(rate[int]);
      int++;
    });

    $("input[name='quantity']").each(function() {
     quantity.push($(this).val());
   });

    var i = 0;

    $("input[name='erectionAmount']").each(function() {
     console.log(rate[i]);
     console.log(quantity[i]);
     $(this).val(rate[i]*quantity[i]);
     i++;
   });
  }
</script>

<script>
  $(document).ready(function(){
   // we define and invoke a function
   (function(){

     var inputArray = $("input[name='boqNameList']")[0].value.split(",");
     
     var names = [];
     $.each(inputArray, function(i, el){
      if($.inArray(el, names) === -1) 
      {
       names.push(el);
     }
   });

     var dummy = "<option value=\"BOQRevisions\"><h5>BOQRevisions</h5></option>";
     
     $.each(names,function(i){

      var dummy1 = dummy.replace("BOQRevisions",names[i]);

      var tags = dummy1.replace("BOQRevisions",names[i]);

      $('#revisionSection').append(tags);

    });
     
   })();
   
   console.log("Table content is : "+$('#tableContentDetails tr').length);
   if($('#tableContentDetails tr').length > 0)
   {
   	$('.assignedInventorySec').toggle();
   }
   
   
 });

  $(document).ready(function(){
   // we define and invoke a function
   (function(){

     var inputArray = $("input[name='quotationNamesList']")[0].value.split(",");
     
     var names = [];
     $.each(inputArray, function(i, el){
      if($.inArray(el, names) === -1) 
      {
        names.push(el);
      }
    });

     var dummy = "<option value=\"QuotationRevisions\"><h5>QuotationRevisions</h5></option>";
     
     $.each(names,function(i){

      var dummy1 = dummy.replace("QuotationRevisions",names[i]);

      var tags = dummy1.replace("QuotationRevisions",names[i]);

      $('#offerRevisionSection').append(tags);

    });
     
   })();
 });

  $(document).ready(function(){
   // we define and invoke a function
   (function(){

     var inputArray = $("input[name='taxInvoiceNamesList']")[0].value.split(",");
     
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

      $('#taxInvoiceList').append(tags);

    });
     
   })();
 });

  $(document).ready(function(){
   // we define and invoke a function
   (function(){

     var inputArray = $("input[name='poNamesList']")[0].value.split(",");
     
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
  function download(name) 
  {
   console.log("Download being called.");

    $('.inventoryTableHeader').css('display','block');
    //$('.importBOQ2').show();
    $('[name="boqName"]')[0].value = name;
   var projectId = $('#projectId').val();  

   var formData = $(this).serializeArray();

   formData.push({name: 'projectId', value: $('#projectId').val()});
   formData.push({name: 'boqName', value: name});

   $.ajax({
     type : 'GET',
     data :  formData,
     url : 'downloadBoq',
     success : function(data)
     {            
       $('#tableContent').html(data);

       adjustWidthDn();
     }

   });

   var generateQuot = document.getElementById("generateQuot");                                         
   generateQuot.style.display = "block";

        //var generate = document.getElementById("offer");
        //generate.style.display = "block";


        var generateBOQ = document.getElementById("generate");
        generateBOQ.style.display = "block";

      }
      function adjustWidthDn() 
      {
       console.log('Inside adjustWidth');
      }
  </script>


  <script>
    function createInquiry() 
    {

     console.log("calling createInquiry()...")
     var CheckeleCount = document.forms["generateBOQ"].getElementsByClassName("checkbox").length;

     var selectedElements = [];
     var i;

     for(i=0; i < CheckeleCount; i++)
     {
      if(document.forms["generateBOQ"].getElementsByClassName("checkbox")[i].checked)
      {
       selectedElements[i] = i;
     }
   }

   var eleCount = document.forms["generateBOQ"].getElementsByTagName("input").length;


   var inventoryName       = [];
   var material            = [];
   var type                = [];
   var manifacturingMethod = [];
   var classOrGrade        = [];
   var ends                = [];
   var size                = [];
   var quantity            = [];
   var baseSupplyRate      = [];
   var supplyRate          = [];
   var baseErectionRate    = [];
   var erectionRate        = [];
   var supplyAmount        = [];
   var erectionAmount      = [];

   var j;
   var k = 0;
   var n = 1;
   for(k=0;k<selectedElements.length;k++)
   {

     if(selectedElements[k] != undefined)
     {
      var start = 12 + 15*parseFloat(selectedElements[k]);
      console.log('Start is : '+start);

      quantity[k]           = $('#generateBOQ input')[start++].value;
      baseSupplyRate[k]     = $('#generateBOQ input')[start++].value;
      supplyRate[k]         = $('#generateBOQ input')[start++].value;
      baseErectionRate[k]   = $('#generateBOQ input')[start++].value;
      erectionRate[k]       = $('#generateBOQ input')[start++].value;
      supplyAmount[k]       = $('#generateBOQ input')[start++].value;
      erectionAmount[k]     = $('#generateBOQ input')[start++].value;
      inventoryName[k] 	  = $('#generateBOQ input')[start++].value;							      
      material[k]      	  = $('#generateBOQ input')[start++].value;     
      type[k]               = $('#generateBOQ input')[start++].value;
      manifacturingMethod[k]= $('#generateBOQ input')[start++].value;
      classOrGrade[k]       = $('#generateBOQ input')[start++].value;
      ends[k]               = $('#generateBOQ input')[start++].value;
      size[k]               = $('#generateBOQ input')[start++].value;
    }	
  }

  var lastArray = 3 + 20*parseFloat(selectedElements[parseFloat(selectedElements.length) - 1]);


  var formData = $(this).serializeArray();

  formData.push({name: 'projectId', value: $('#projectId').val()});
  formData.push({name: 'boqName', value: $('[name="boqName"]').val()});


  var inventoryName_string = cleanArray(inventoryName);
  formData.push({name: 'inventoryName', value: inventoryName_string});
  var material_string = cleanArray(material);
  formData.push({name: 'material', value: material_string});
  var type_string = cleanArray(type);
  formData.push({name: 'type', value: type_string});
  var manifacturingMethod_string = cleanArray(manifacturingMethod);
  formData.push({name: 'manifMetod', value: manifacturingMethod_string});
  var classOrGrade_string = cleanArray(classOrGrade);
  formData.push({name: 'classOrGrade', value: classOrGrade_string});
  var ends_string = cleanArray(ends);
  formData.push({name: 'ends', value: ends_string});
  var size_string = cleanArray(size);
  formData.push({name: 'size', value: size_string});
  var quantity_string = cleanArray(quantity);
  formData.push({name: 'quantity', value: quantity_string});

  var baseSupplyRate_string = cleanArray(baseSupplyRate);
  formData.push({name: 'baseSupplyRate', value: baseSupplyRate_string});
  var supplyRate_string = cleanArray(supplyRate);
  formData.push({name: 'supplyRate', value: supplyRate_string});
  var baseErectionRate_string = cleanArray(baseErectionRate);
  formData.push({name: 'baseErectionRate', value: baseErectionRate_string});
  var erectionRate_string = cleanArray(erectionRate);
  formData.push({name: 'erectionRate', value: erectionRate_string});

  var supplyAmount_string = cleanArray(supplyAmount);
  formData.push({name: 'supplyAmount', value: supplyAmount_string});
  var erectionAmount_string = cleanArray(erectionAmount);
  formData.push({name: 'erectionAmount', value: erectionAmount_string});

  formData.push({name: 'isOffer', value: 'true'});

  $.ajax({
   url: "generate",
   data: formData,
   type: 'post',
   success: function(data) {
    console.log(data);
  }
});
}

function cleanArray(actual)
{
  var newArray = new Array();
  for(var i = 0; i<actual.length; i++)
  {
    if (actual[i])
    {
      newArray.push(actual[i]);
    }
  }
  return newArray;
}
</script>

<script>

  function generatePO()
  {	
    var length = $('#generateBOQ input').length;

    var line;
    var i;
    for(i=4;i<length;i++)
    {
     if($('#generateBOQ input')[i].name == "inventoryName")
     {
      var temp = $('#generateBOQ input')[i];
      line = $(temp).clone();
    } 
    else if($('#generateBOQ input')[i].name == "material")
    {
      var temp = $('#generateBOQ input')[i];
      line = $(temp).clone();
    }
    else if($('#generateBOQ input')[i].name == "type")
    {
      var temp = $('#generateBOQ input')[i];
      line = $(temp).clone();
    }
    else if($('#generateBOQ input')[i].name == "manifMetod")
    {
      var temp = $('#generateBOQ input')[i];
      line = $(temp).clone();
    }
    else if($('#generateBOQ input')[i].name == "classOrGrade")
    {
      var temp = $('#generateBOQ input')[i];
      line = $(temp).clone();
    }
    else if ($('#generateBOQ input')[i].name == "quantity")
    {
      var temp = $('#generateBOQ input')[i];
      line = $(temp).clone();
    }
    else if ($('#generateBOQ input')[i].name == "supplyRate")
    {
      var temp = $('#generateBOQ input')[i];
      line = $(temp).clone();
    }

    $('#generateOffer').append($(line));		
  }

  var temp = $('#projectId');
  line = $(temp).clone();
  $('#generateOffer').append($(line));

  $('#generateOffer').submit();
}
</script>
<script>
  function toggleCreateBOQSec()
  {
   $('.createBOQ').toggle();

 }    
</script>
<script>
  function toggleImportSec()
  {
    console.log('calling toggleImportSec');
    $('.importBOQ1').toggle();
    $('.importBOQ2').toggle();

    	/*
    	var imp = document.getElementsByClassName("importBOQ");
        imp.style.display = "block";
        */
      }    
    </script>
    <script>
      function appendInventory() 
      {

        $('.generateBOQButton').css('display','block');
        $('.inventoryTableHeader').css('display','block');

        var inventoryName = $('#inventoryName').children("option:selected").val();
        var material = $('#material').children("option:selected").val();
        var type = $('#type').children("option:selected").val();
        var manifacturingMethod = $('#manifacturingMethod').children("option:selected").val();
        var classOrGrade = $('#classOrGrade').children("option:selected").val();
        var ends = $('#ends').children("option:selected").val();
        var size = $('#size').val();

        console.log(type);

        var	template = "<tr>" 
        + "    <td> <input type='hidden' name='inventoryName' value='"+inventoryName+"'></input>"+inventoryName+"</td>"
        + "    <td>  <input type='hidden' name='material' value='"+material+"'></input>"+material+"</td>"
        + "    <td> <input type='hidden' name='type' value='"+type+"'></input>"+type+"</td>"
        + "    <td> <input type='hidden' name='manifMetod' value='"+manifacturingMethod+"'></input>"+manifacturingMethod+"</td>"
        + "    <td> <input type='hidden' name='classOrGrade' value='"+classOrGrade+"'></input>"+classOrGrade+"</td>"
        + "    <td> <input type='hidden' name='ends' value='"+ends+"'></input>"+ends+"</td>"
        + "    <td> <input type='hidden' name='size' value='"+size+"'></input>"+size+"</td>"
        + "	   <td><input type='text' name='quantity' value=''></input></td>"
        + "	   <td><input type='text' name='baseSupplyRate' value=''></input></td>"
        + "	   <td><input type='text' name='supplyRate' value=''></input></td>"
        + "	   <td><input type='text' name='baseErectionRate' value=''></input></td>"
        + "	   <td><input type='text' name='erectionRate' value=''></input></td>"
        + "	   <td><input type='text' name='supplyAmount' value=''></input></td>"
        + "	   <td><input type='text' name='erectionAmount' value=''></input></td>";

        console.log(template);
        $('.inventoryDetails').css("display","block");          	
        $('#tableContentDetails').append(template);
      }

    </script>

    <script>

      function downloadInvoice( invoiceName )
      {
       window.location.assign("http://localhost:8080/InventoryWeb/showInvoice?invoiceName="+invoiceName);
     }

     function downloadPO( poName )
     {
       window.location.assign("http://localhost:8080/InventoryWeb/showPO?poName="+poName);
     }
   </script>
   <script>
    $(document).ready(function(){
      $('[data-toggle="tooltip"]').tooltip(); 
    });
  </script>
  <script>
    function toggleProjSec()
    {
    	$('.projDetails').toggle();
    }
    
    function togglePayDetailsSec()
    {
    	$('#payDetailsSection').toggle();
    }
  </script>
  <script>
    function updateVal(){
     var promptVal = prompt("Enter %","0%");
     console.log(promptVal);
   }
 </script>
 <script type="text/javascript">
  $(function() {
    $('button[name=uploadFile]').click(function(e) {
      e.preventDefault();
    //Disable submit button
    $(this).prop('disabled',true);
    $('.inventoryTableHeader').css('display','block');
    var form = document.forms[name="fileUploadForm"];
    
    var formData = new FormData(form);

    console.log(formData);	
    // Ajax call for file uploaling
    var ajaxReq = $.ajax({
      url : 'import',
      type : 'POST',
      data : formData,
      cache : false,
      contentType : false,
      processData : false,
      
      success: function(data) 
      {
       $('#tableContent').html(data);
       adjustWidth();

       var imp = document.getElementById("importBoq");
	        //imp.style.display = "none";
	        
	        var generate = document.getElementById("generate");
	        generate.style.display = "block";
	        
	        var generateInq = document.getElementById("generateQuot");
	        generateInq.style.display = "block";
	        
	        $('[name="client"]')[0].value  	= $('#iDclient')[0].value; 	
         $('[name="site"]')[0].value 	= $('#iDsite')[0].value; 		
         $('[name="project"]')[0].value 	= $('#iDproject')[0].value; 	
         $('[name="dName"]')[0].value 	= $('#iDdName')[0].value; 		
         $('[name="utility"]')[0].value 	= $('#iDutility')[0].value; 	
         $('[name="pressure"]')[0].value	= $('#iDpressure')[0].value; 	
         $('[name="temperature"]')[0].value 	= $('#iDtemp')[0].value; 		
         $('[name="dNo"]')[0].value 		= $('#iDdNo')[0].value;

       }
     });

    // Called on success of file upload
    ajaxReq.done(function(msg) {
      $('input[type=file]').val('');
      $('button[type=submit]').prop('disabled',false);
    });
    
    // Called on failure of file upload
    ajaxReq.fail(function(jqXHR) {
      $('button[type=submit]').prop('disabled',false);
    });
    
    $(this).prop('disabled',false);
  });
  });
</script>
<script>
  $('.statusTo').on('change',function() {

   var quantity 		= $(this.form.elements)[0].value;
   var inventoryStr    = $(this.form.elements)[2].value;
   var materialStr		= $(this.form.elements)[3].value;
   var typeStr			= $(this.form.elements)[4].value;
   var manifMethodStr	= $(this.form.elements)[5].value;
   var gradeOrClassStr = $(this.form.elements)[6].value;
   var endsStr			= $(this.form.elements)[7].value;
   var sizeStr			= $(this.form.elements)[8].value;
   var purchaseRateStr = $(this.form.elements)[9].value;
   var projectStr		= $(this.form.elements)[10].value;
   var locationStr		= $(this.form.elements)[11].value;
   var projectId		= $(this.form.elements)[13].value;
   var projectName		= $(this.form.elements)[14].value;
   var projectDesc		= $(this.form.elements)[15].value;
   var statusTo		= $(this).val();

   if(statusTo !== '')
   {
    $.ajax({
     url: "release",
     data: { 'inventoryStr' : 	inventoryStr,
     'materialStr' : 	materialStr,		
     'typeStr' : 		typeStr,			
     'manifMethodStr' : 	manifMethodStr,	
     'gradeOrClassStr' : gradeOrClassStr, 
     'endsStr' : 		endsStr,			
     'sizeStr' : 		sizeStr,			
     'purchaseRateStr' : purchaseRateStr, 
     'projectStr' : 		projectStr,		
     'locationStr' : 	locationStr,
     'quantity'	 : 		quantity,		
     'projectId' : 		projectId,		
     'projectName' : 	projectName,		
     'projectDesc' : 	projectDesc,
     'statusTo' : 		statusTo},
     type: 'post',
     success: function(data) {
      console.log(data);
    }
  });
  }	

});
</script>
<script>
  $('.accessoryStatusTo').on('change',function() {

   var quantity			= $(this.form.elements)[0].value;
   var accessoryStatusTo	= $(this.form.elements)[1].value;
   var desc1 				= $(this.form.elements)[2].value;
   var desc2    			= $(this.form.elements)[3].value;
   var desc3				= $(this.form.elements)[4].value;
   var desc4				= $(this.form.elements)[5].value;
   var desc5				= $(this.form.elements)[6].value;
   var accessoryName 		= $(this.form.elements)[7].value;	
   var project 			= $(this.form.elements)[8].value;	
   var locationStr			= $(this.form.elements)[9].value;	
   var projectId			= $(this.form.elements)[10].value;
   var projectName			= $(this.form.elements)[11].value;
   var projectDesc			= $(this.form.elements)[12].value;

   if(accessoryStatusTo !== '')
   {
    $.ajax({
     url: "releaseAccessory",
     data: { 'quantity' 			: quantity,
     'accessoryStatusTo' : accessoryStatusTo,
     'desc1' 			: desc1,
     'desc2' 			: desc2,
     'desc3' 			: desc3,
     'desc4' 			: desc4,
     'desc5' 			: desc5,
     'accessoryName' 	: accessoryName,
     'project' 			: project,
     'locationStr' 		: locationStr,
     'projectId' 		: projectId,
     'projectName' 		: projectName,
     'projectDesc' 		: projectDesc},
     type: 'post',
     success: function(data) {
      console.log(data);
    }
  });
  }	

});
</script>

</body>
</html>