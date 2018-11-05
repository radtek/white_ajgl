(function($){
	"use strict";
	var pos = 0;
	var canvas = document.getElementById("canvas");
	var ctx = canvas.getContext("2d");
    var img = ctx.getImageData(0, 0, 358, 441);
    $(document).ready(function() {
    	
    	$(document).on("click",".qiezi",function(){
    		webcam.capture();
		});
    	
    	jQuery("#webcam2").webcam({
            width: 359,
            height: 441,
            mode: "callback",
            swffile: context + "/common/library/jqueryWebcam/jscam.swf",
            onTick: function(remain) {
                if (0 == remain) {
                    jQuery("#status").text("Cheese!");
                } else {
                    jQuery("#status").text(remain + " seconds remaining...");
                }
            },
            onSave: function(data) {
                var col = data.split(";");
    		    for(var i = 0; i < 358; i++) {
    		        var tmp = parseInt(col[i]);
    		        img.data[pos + 0] = (tmp >> 16) & 0xff;
    		        img.data[pos + 1] = (tmp >> 8) & 0xff;
    		        img.data[pos + 2] = tmp & 0xff;
    		        img.data[pos + 3] = 0xff;
    		        pos+= 4;
    		    }
    		    if (pos >= 4 * 358 * 441) {
    		        ctx.putImageData(img, 0, 0);
    		        pos = 0;
    		    }
            // Work with the picture. Picture-data is encoded as an array of arrays... Not really nice, though =/
            },
            onCapture: function () {
                webcam.save();
              // Show a flash for example
            },
            debug: function (type, string) {
                // Write debug information to console.log() or a div, ...
            },
            onLoad: function () {
            // Page load
                var cams = webcam.getCameraList();
                for(var i in cams) {
                    jQuery("#cams").append("<li>" + cams[i] + "</li>");
                }
                //webcam.setCamera(1);
            }
        });
	});
    
	jQuery.extend($.common, { 
		closeWindow:function(index){
			window.top.layer.close(index);
		},
		getPicture:function(){
			var str = canvas.toDataURL("image/png");
			return str.substr(22,str.length-22);
		}
	});
})(jQuery);