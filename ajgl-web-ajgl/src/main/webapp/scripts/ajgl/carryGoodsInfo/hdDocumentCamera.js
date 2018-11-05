var iVideo = document.iVideo; 
(function($){
	$(document).ready(function() {
		initCamera();
	});
	
	function initCamera(){
		iVideo.OpenDevices();
		iVideo.SetImageFormat("png");
		iVideo.SetVideoResolution(7);
		iVideo.SetRotate(180);
		//0:3742x2806
		//1:2592x1944
		//2:2048x1536
		//3:1920x1080
		//4:1600x1200
		//5:1280x960
		//6:1280x720
		//7:1024x768
		//8:800x600
		//9:640x480
	}
	jQuery.extend($.common, { 
		getPicture:function(obj){
			iVideo.GetSnap();
			return iVideo.GetSnapBase64();
		}
	});	
})(jQuery);

