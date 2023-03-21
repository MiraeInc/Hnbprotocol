 /**
  * gatsby utils
  */
var gatsby  = (function() {
    'use strict';

    return {

        /**
         * translate3d
         * @param translations
         * @param speed
         * @param easing
         * @param complete
         * @returns {*}
         */
        translate3d: function(element, translations, speed, easing, complete) {
            var delay = 0;
            var opt = $.speed(speed, easing, complete);
            opt.easing = opt.easing || 'ease';
            translations = $.extend({x: 0, y: 0, z: 0}, translations);

            return $(element).each(function() {
                var $this = $(this);

                $this.css({
                    transitionDuration: opt.duration + 'ms',
                    transitionTimingFunction: opt.easing,
                    transform: 'translate3d(' + translations.x + 'px, ' + translations.y + 'px, ' + translations.z + 'px)'
                });

                setTimeout(function() {
                    $this.css({
                        transitionDuration: '0s',
                        transitionTimingFunction: 'ease'
                    });
                    opt.complete();
                }, opt.duration + (delay || 0));
            });
        },

        /**
         * 문자열 바이트 체크
         * @param s 체크할 문자열
         * @param b
         * @param i
         * @param c
         * @returns {*}
         */
        getByteLength: function(s,b,i,c){
            for (b = i = 0; c = s.charCodeAt(i++); b += c >> 11 ? 3 : c >> 7 ? 2 : 1);
            return b;
        }

    };
})();



/**
 * 파일업로드 UI
 * @param obj 대상
 */
$.fn.filebox = function(){

	$element = $(this);
    
	$element.each(function(){

		var $this = $(this);

        $this.on('change', '.file', function (e) {
        	
        	var $file = $(this);
        	var target = $file.attr('data-target');
            
        	if(window.FileReader){  // modern browser
                var filename = $(this)[0].files[0].name;
            }
            else {  // old IE
                var filename = $(this).val().split('/').pop().split('\\').pop();  // 파일명만 추출
            }

            // 추출한 파일명 삽입
        	$('[data-id='+ target +']').val(filename);
        })
    })

},



/**
 * 터치슬라이드 (탭메뉴 대응)
 */
$.touchflow = function(option){

	var $element = (typeof option == 'string') ? $(option) : $(option.wrap);

	$element.each(function(){
		
		var
			$this = $(this),
			$container = null,
			$list = null;
			$tablink = null;

		var
			min = 0,
			max = 0,
			current = 0,
			tx = 0;

		if(typeof option == 'string'){
			$container = $('.touch_container', $this);
			$list = $('.touch_list', $this);
		}else{
			$container = $(option.container, $this);
			$list = $(option.list, $this);
			$tablink = $(option.tablink, $this);
		}


		//탭링크 옵션이 있을경우
		if($tablink){
			$tablink.on('click', function(e){
				
				e.preventDefault();

				var $this = $(this);
				var href = $this.attr('href');
				
				$this.addClass('active').parent().siblings().find(option.tablink).removeClass('active')

				$(href).show().siblings('.tab-panel').hide();

			})
		}

		var getTabWidth = function () {
			var width = 0;
			$list.find('li').each(function() {
				width = width + $(this).outerWidth();
			});
			return width;
		};
		

		if($container.width() >= getTabWidth()){
			return false;
		}

		$container.width(getTabWidth);

		max = getTabWidth() - $this.outerWidth();

		var touch = new Hammer($this.get(0));

		var dragEvent = function (event) {

			tx = current + event.deltaX;

			$container.css({ 'transform': 'translate3d(' + tx + 'px, 0px, 0)' });

			if (event.type == "panend" && tx < min && Math.abs(tx) < max) {
				current = current + event.deltaX;
			} else if (event.type == "panend" && tx >= min) {
				current = 0;
				gatsby.translate3d($container.get(0), {x: current, y: 0, z: 0}, 500);
			} else if (event.type == "panend" && Math.abs(tx) >= max) {
				current = max * -1;
				gatsby.translate3d($container.get(0), {x: -max, y: 0, z: 0}, 500);
			}

		};

		touch.on("panleft panright panend", dragEvent);

		

	});

}


/**
 * 글로벌 메뉴
 */
$.fn.gnb = function(){
    
    var $this = $(this);
    var $gnb = $('#gnb');
    var $tab = $gnb.find('#gnbTab');
    
    var isOpen = false;
    
    $this.on('click', function(){
        _gnbOpen();
    });

    _gnbOpen = function(){
        
        if(!isOpen){
            isOpen = true;
            $gnb.addClass('active');
            
            //이벤트 중복실행  방지
            $tab.off('click.gnb.close');
            $tab.off('click.gnb.tab');

            //이벤트
            $gnb.one('click.gnb.close', '#btnMenuClose', function(){
                _gnbClose();
            })
            _gnbTab();
		}


        var menu = [];
        var $list = $('.gnb-list');
        
        $list.find('.item').each(function(){
        	
        	var $this = $(this);
        	
        	menu.push(parseInt($this.height()));
        	
        })
        
        for(var i=1; i<menu.length; i += 2){
//        	console.log(i, menu[i], menu[i-1])
        	if(menu[i] < menu[i-1]){
//        		console.log('앞에것이 더큼')
        		$list.find('.item').eq(i).height(menu[i-1]);
        	}else{
//        		console.log('뒤에것이 더큼');
        		$list.find('.item').eq(i-1).height(menu[i]);
        	}
        	
        }
        
        
        
//		$('.gnb-list').masonry({
//			itemSelector: '.item',
//		});

    }

    _gnbClose = function(){
        if(isOpen){
            isOpen = false;
            $gnb.removeClass('active')
        }
    }

    _gnbTab = function(){
        $tab.on('click.gnb.tab', '.tab-link', function(e){
           
            e.preventDefault();

            var $this = $(this);
            var target = $this.attr('href');
            
            $this.closest('li').addClass('active').siblings('li').removeClass('active');
            $(target).addClass('active').siblings('.aside-pane').removeClass('active');
            
        });
    }

    
};

/**
 * 타임세일
 */
$.fn.timesale = function(){
    
    $time = {
		clock : $(this).find(".detail-clock"),
		h : 0,
		m : 0,
		s : 0,
		speed : 1000,
		end : false
	};

	getTime = function(){
		$time.h = Number($time.clock.data("time-hour"));
		$time.m = Number($time.clock.data("time-min"));
		$time.s = Number($time.clock.data("time-sec"));
	};

	setTime = function(h, m, s){
		if(h/10<1){
			h = "0" + h;
		};
		if(m/10<1){
			m = "0" + m;
		};
		if(s/10<1){
			s = "0" + s;
		};
		$time.clock.data("time-hour", h).attr("data-time-hour", h);
		$time.clock.data("time-min", m).attr("data-time-min", m);
		$time.clock.data("time-sec", s).attr("data-time-sec", s);
	};

	_calc = function(){
		if($time.s-1 >= 0){
			$time.s-=1;
		}else{
			if($time.m-1 >= 0){
				$time.m-=1;
				$time.s=59;
			}else{
				if($time.h-1 >= 0){
					$time.h-=1;
					$time.m=59;
					$time.s=59;
				}else{
					$time.end = true;
					_finish();
				}
			}
		}
		setTime($time.h, $time.m, $time.s);
	};

	_loopTimer = setInterval(function(){
		if($time.end){
			clearInterval(_loopTimer);
			return false;
		}
		_calc();
	}, $time.speed);

	_finish = function(){
		$time.h=0;
		$time.m=0;
		$time.s=0;
		setTime($time.h, $time.m, $time.s);
	};

	_init = function(){
		getTime();
	}(this);
};

/**
 * 아코디언
 */
$.collapse = function(element){
	
	var $element = element;
	
	// console.log(element)
	var $trigger = $('[data-toggle="collapse"][href="#' + element[0].id + '"],' + 
					 '[data-toggle="collapse"][data-target="#' + element[0].id + '"]');

	if($element.is(':visible')){
		$trigger.removeClass('active');
		$element.hide();
	}else{
		$trigger.addClass('active');
		$element.show();
	}

}

/**
 * 입력폼 바이트 제한
 * @param obj 입력폼
 * @param display 바이트를 표시해 줄 dom
 * @param max 제한 바이트수
 */
$.fn.limitWrite = function(display, max){

	var $form = $(this);
	var $display = !display ? $(display) : null;

	$form.on('keyup', function(){
		var byte = gatsby.getByteLength(this.value);

		if(byte > max){
			var str = this.value;
			var nbyte = gatsby.getByteLength(str);
			var leng = str.length;

			while (nbyte > max) {
				str = str.substr(0, leng--);
				nbyte = gatsby.getByteLength(str);
			}

			alert("한글 "+(max/3)+"자 / 영문 "+max+"자를 초과 입력할 수 없습니다.");
			this.value = str;
		}else{
			// console.log(byte, max)
			if(!$display){
				
				$display.text(byte)
			}
		}
	});
};

/**
 * 페이지 상단 현재위치
 */
$.fn.breadcrumb = function(){

	var $element = $(this);
	
	if(!$element) return;
	
	$element.append('<div class="bg"></div>');

	$element.on('click', '.cate-link', function(e){
		e.preventDefault();
		
		var $this = $(this);
		var $parent = $this.parent();
		var $list = $this.next('.cate-list');


		if($this.hasClass('active')){
			$this.removeClass('active')
			$list.hide();
			$element.find('.bg').hide();
		}else{
			$parent.siblings().find('.cate-list').hide();
			$parent.siblings().find('.cate-link').removeClass('active');
			$this.addClass('active')
			$list.show();

			$element.find('.bg').show().css({
				height: $list.outerHeight() + 1
			});
			
		}

	});


}


/**
 * 별점
 */
$.fn.rating = function(){
	
	var $element = $(this);
	$element.each(function(){
	
		var $form = $(this);
		var group = $form.find('.star').attr('name');
		
		$form.on('change', '.star[name='+group+']', function(){
			var $this = $(this);
			var val = $this.val();
			$form.attr('data-rate', val);
		})
		
	});
	
}





$.fn.dropdownAction = function(){
    $(this).find("li").on("click", function(e){
        e.preventDefault();
        $(this).addClass("active").siblings().removeClass("active");
        $(this).parent("ul").toggleClass("active");
    });
};

$.fn.faqAction = function(){
    $(this).find(".faq-q").on("click", function(){
        $(this).parent(".faq-box").toggleClass("active");
    });
};

$.fn.etcView = function(){
	$(this).change(function(e){
		if($(this).children("option").eq(e.currentTarget.selectedIndex).is("[data-etc-target]")){
			var name = $(this).children("option[data-etc-target]").data("etc-target");
			$("[data-etc-name="+name+"]").show();
		}else{
			var name = $(this).children("option[data-etc-target]").data("etc-target");
			$("[data-etc-name="+name+"]").hide();
		}
	});
};

$.fn.tabControl = function(){
	tabEl = {
		box: $(this),
		btns: $(this).find(".tab-link"),
	};

	setActive = function(el){
		if($(el).parent("li").hasClass("active")){
			return false;
		};
		$(el).parent("li").addClass("active").siblings("li").removeClass("active");
		id = $(el).attr("href");

		$.each(tabEl.btns, function(i,v){
			console.log(v)
			$($(v).attr("href")).not(id).hide();
			$(id).show();
		});
		
	};

	_init = function(){
		tabEl.btns.click(function(e){
			e.preventDefault();
			setActive(this);
		});
	}(this);
};

$.fn.termBox = function(){
	$(this).find(".term-btn").click(function(){
		$(this).parent(".term-box").toggleClass("active");
		$(this).siblings(".term-wrap").slideToggle();
	});
};

$.fn.formControl = function(){
	var temp = "<button type='button' class='clear-form' tabindex='-1'></button>";

	if($(this).is("input[type='text']") || $(this).is("input[type='password']") || $(this).is("input[type='tel']")){
		$(this).parent().append(temp).addClass("hasClear");

		$(this).siblings(".clear-form").on("touchstart click", function(e) {
			e.preventDefault();
			$(this).siblings("input").val("");
		});
	}
};

//유틸버튼
$.fn.utils = function(){
	ut = {
		that: $(this),
		btn_top: "<button type='button' data-dir='up'>위로</button>",
		btn_bottom: "<button type='button' data-dir='down'>아래로</button>",
		flag: false
	};
	
	_init = function(){
		ut.that.append("<div class='util-box'></div>")
		var up = ut.that.find(".util-box").append(ut.btn_top).find("[data-dir='up']");
		var down = ut.that.find(".util-box").append(ut.btn_bottom).find("[data-dir='down']");

		$(window).bind('scroll', function(e) {
			var val = $(this).scrollTop();
			if (val >= $(window).height()/4) {
				up.addClass("active");
			}else if (val < $(window).height()/4 || val > $(document).height()) {
				up.removeClass("active");
			}
			if (val < $(document).height() - $(window).height()*1.5) {
				down.addClass("active");
			}else{
				down.removeClass("active");
			};
		});

		up.bind("click", function(){
			if(ut.flag) return false;
			ut.flag = true;
			$("html, body").animate({
				scrollTop: 0
			}, 500, function(){
				ut.flag = false;
			});
		});

		down.bind("click", function(){
			if(ut.flag) return false;
			ut.flag = true;
			$("html, body").animate({
				scrollTop: $(document).height()
			}, 500, function(){
				ut.flag = false;
			});
		});

		$(window).triggerHandler("scroll");
	}(this)
};


/**
 * 공지성 팝업
 * @param target {string} 컨텐츠가 포함되어있는 DOM
 * @param setting {object} 팝업을 위한 세팅
 * @example 
 * noticePopup("popNotice1") 
 */
function noticePopup(target){
	
	var MODAL = null;

	var $overlay = null;
	var $popup = $('[data-remodal-id='+target+']');

	//remodal 오픈
	MODAL = $popup['remodal']({
		hashTracking: false,
		modifier: 'type-notice'
	});
	
	$popup.closest('.remodal-wrapper').css({
		'padding-top': noticePopupInit
	})
	
	
	MODAL.open();
	
	
	$(window).on('resize', function(){
		$popup.closest('.remodal-wrapper').css({
			'padding-top': noticePopupInit
		})
	})
	
	$("body").scrollTop(0);
	
}


function noticePopupInit(){
	
	var $banner = $('#global-banner');
	var $header = $('#header');
	
	// return $banner.outerHeight()+ $header.outerHeight() + 10;
	return 0;
	
}


//Define modal settings
window.REMODAL_GLOBALS = {
	DEFAULTS: {
		hashTracking: false
	}
};


//DOM IS READY!!
$(function(){
	
	//Init Default UI
	$('#btnMenu').gnb();
	$('.breadcrumb').breadcrumb();
	$(".term-box").termBox();
	$.each($(".form-control input:not([readonly])"), function(i,v){
		$(v).formControl();
	});

	// Collapse data-api
	// $(document).on('click.ui.collapse', '[data-toggle="collapse"]', function(e){
	$('[data-toggle="collapse"]').click(function(e){

		e.preventDefault();

		var $this = $(this);
		var href = $this.attr('data-target') || $this.attr('href');
		var $target = $(href) || $('[data-id='+ href +']');
		$.collapse($target);

	});

	// pagefilter
	$(document).on('click.ui.filter', '.page-filter a', function(e){
		
		// e.preventDefault();

		var $this = $(this);
		$this.addClass('active').closest('li').siblings().find('a').removeClass('active');
	})
	
	
	$(document).on('click.ui.popup', '[data-toggle="popup"]', function(e){
		
		e.preventDefault();
		
		var $this = $(this);
		var target = $this.attr('data-target');
		
		$(target).addClass('active');
		
		$(document).one('click', '[data-dismiss="popup"]', function(e){
			e.preventDefault();
			$(target).removeClass('active');
		})
		
	});
	
	$(document).on('click.ui.toggle', '[data-toggle="toggle"]', function(e){
		e.preventDefault();
		var $this = $(this);
		$this.toggleClass('active');
	});
	
	$.each($(".opt_select select").has("option[data-etc-target]"), function(i,v){
		$(v).etcView();
	});


})