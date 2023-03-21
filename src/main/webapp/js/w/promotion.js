

/* ========================================================================
 * UI : 퀵메뉴
 * ======================================================================== */
+function ($) {
    'use strict';

    var Quick = function(element){
        this.$element = $(element);

        this.winWidth = this.getWinSize();
        this.timer = null;

		this.CONTENTWIDTH = 1320;
		this.HEADERHEIGHT = 178;
        this.ORIGINTOP = 0;
        this.GAP = 30;
        
		this.setStyle();
		this.$element.show();

        $(window)
            .on('resize.ui.quick', $.proxy(this.setStyle, this))
			 .on('scroll.ui.quick', $.proxy(this.updatePosition, this));
			
		// $(".recent_view").recentView();

    };

    Quick.prototype = {
    	update: function(){
    		this.HEADERHEIGHT -= 80
    	},
    	//스타일
        setStyle : function(){

            var bodyWidth = $('#wrap').width();
            var temp = 0;
            
            this.winWidth = this.getWinSize();
            
            if($('#global_banner').length){
            	this.HEADERHEIGHT  += 80
			}
            
            if($('.breadcrumbs').length){
            	this.HEADERHEIGHT  += 46
            	this.ORIGINTOP  += 46
            }
            
            this.$element.css({
				left : this.CONTENTWIDTH + (bodyWidth - this.CONTENTWIDTH)/2 + this.GAP,
				top: this.ORIGINTOP
            })

        },
        //브라우저 크기
        getWinSize : function(){
            return $(window).width();
        },
        getBodySize : function(){
        	return $('body').width();
        },
        //스크롤시 위치 갱신
        updatePosition : function(){
            var that = this,
                posY = null,
                currentY = $(document).scrollTop(),
                max = $(document).height() - this.$element.height();
			
            if(currentY >= that.HEADERHEIGHT){
            	that.$element.addClass('fixed');
            }else{
            	that.$element.removeClass('fixed');
            }
            
        }
    };

    $.fn.quick = function(option){
        var $this = $(this);
        var data = $this.data('ui.quick');
        if(!data) data = $this.data('ui.quick', new Quick(this));
        if (typeof option == 'string') data[option](true);
    };

}(jQuery);


$(function(){
	
	$('#quickMenu').quick();


	$('area[data-scroll]').on('click', function(){
		var $this = $(this);
		
		if($this.attr('data-scroll') == 'up') {
			$(document).scrollTop(0);
		}else{
			$(document).scrollTop($(document).height());
		}
	});

	

})