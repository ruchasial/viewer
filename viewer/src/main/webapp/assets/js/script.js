$(function(){

    var ul = $('ul');
    var cnt =0;
    var res ={};
    $('#drop a').click(function(){
        // Simulate a click on the file input button
        // to show the file browser dialog
        $(this).parent().find('input').click();
    });


    // Initialize the jQuery File Upload plugin
    $('#upload').fileupload({

        // This element will accept file drag/drop uploading
        dropZone: $('#drop'),

        // This function is called when a file is added to the queue;
        // either via the browse button, or via drag/drop:
        add: function (e, data) {
        	
            var tpl = $('<li class="working"><input type="text" value="0" data-width="48" data-height="48"'+
                ' data-fgColor="#0788a5" data-readOnly="1" data-bgColor="#3e4043" /><p></p><button id="buttonid'+parseInt(cnt)+'" >View</button><span></span></li>');
           
            
          tpl.find('button').hide();
		
            // Append the file name and file size
            tpl.find('p').text(data.files[0].name).append('<i>' + formatFileSize(data.files[0].size) + '</i>');
               
    		// Add the HTML to the UL element
            data.context = tpl.appendTo(ul);

            // Initialize the knob plugin
            tpl.find('input').knob();

            // Listen for clicks on the cancel icon
            tpl.find('span').click(function(){

                if(tpl.hasClass('working')){
                    jqXHR.abort();
                }

                tpl.fadeOut(function(){
                    tpl.remove();
                });

            });
         
           // fileReader.readAsArrayBuffer(blob);

          
          /*  var control = document.getElementById("upfile");
			
			control.addEventListener("change", function(event) {
			    // When the control has changed, there are new files
			    var files = control.files;
			    for (var i = 0; i < files.length; i++) {
			      // console.log("Filename: " + files[i].name);
			        alert("file uploaded Type#########  : " + files[i].type);
			        //console.log("Size: " + files[i].size + " bytes");
			    }
			}, false);*/
           
            // Automatically upload the file once it is added to the queue
            var jqXHR = data.submit();
            jqXHR.done(function (result, textStatus, jqXHR) {
            	res[(parseInt(+cnt)-1)]=result;
            	//$('#buttonid'+(parseInt(cnt)-1)).prop();
            	$('#buttonid'+(parseInt(cnt)-1)).show();
            	// $('#buttonid'+(parseInt(cnt)-1)).prop("disabled",false);
            	var pathname = window.location.pathname; 
            	var url      = window.location.href;
            	$('#buttonid'+(parseInt(cnt)-1)).attr('onclick','window.open("'+url+ res[(parseInt(cnt)-1)]+'");');
            	//alert("data is : "+(parseInt(cnt)-1) +"   "+ result);
            	
            	
            })
             
         
             /* $('#buttonid'+cnt).click(function(){
            	  e.preventDefault();
            	 // alert("data is : "+cnt +"   "+ res);
            	window.open('http://localhost:8080/viewer/'+res.cnt);
              });*/
       
            cnt=cnt+1;
        },

        progress: function(e, data){

            // Calculate the completion percentage of the upload
            var progress = parseInt(data.loaded / data.total * 100, 10);

            // Update the hidden input field and trigger a change
            // so that the jQuery knob plugin knows to update the dial
            data.context.find('input').val(progress).change();

            if(progress == 100){
                data.context.removeClass('working');
            }
        },

        fail:function(e, data){
            // Something has gone wrong!
            data.context.addClass('error');
        }
        

    });
    


    // Prevent the default action when a file is dropped on the window
    $(document).on('drop dragover', function (e) {
        e.preventDefault();
    });

    // Helper function that formats the file sizes
    function formatFileSize(bytes) {
        if (typeof bytes !== 'number') {
            return '';
        }

        if (bytes >= 1000000000) {
            return (bytes / 1000000000).toFixed(2) + ' GB';
        }

        if (bytes >= 1000000) {
            return (bytes / 1000000).toFixed(2) + ' MB';
        }

        return (bytes / 1000).toFixed(2) + ' KB';
    }

});