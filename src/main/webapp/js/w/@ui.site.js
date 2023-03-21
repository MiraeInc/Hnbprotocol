$.fn.formControl = function(){
	var temp = "<button type='button' class='clear-form'></button>";
	$(this).parent(".form-group").append(temp).addClass("hasClear");

	$(this).siblings(".clear-form").on("touchstart click", function(e) {
		e.preventDefault();
		$(this).siblings("input").val("");
	});
};

$.fn.infoOpen = function(){
	var sibling = $(".info-layer");
	action = function(target){
		if($(target).hasClass("active")){
			$(target).removeClass("active");
		}else{
			sibling.removeClass("active");
			$(target).addClass("active");
		};
	};

	$(this).bind("click", function(){
		action($(this).parent(".info-layer"));
	});
};

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

$.fn.zoom = function(){
	this.zoooom = function(z){
		z.thumb = z.find(".detail-thumbs"); //썸네일
		z.thumb.idx = z.thumb.find(".active").index();
		z.screen = z.find(".detail-screen"); //중간이미지박스
		z.target = z.find(".detail-view"); //큰이미지박스
		z.pointer = z.find(".detail-pointer"); //포인터박스
		z.img = z.screen.children("img"); //중간이미지
		z.prev = z.screen.find(".btn-screen-prev"); //이전버튼
		z.next = z.screen.find(".btn-screen-next"); //다음버튼
		
		z.switchImg = function(src){
			if(!src) return false;
			z.target.html("<img src='"+src+"' alt='원본이미지'>");
		};

		z.setImage = function(idx){
			var temp = z.thumb.find("li:eq("+idx+") > img").data("source");
			z.img = z.screen.find(">img").remove().end().prepend("<img src='"+temp+"' alt='제품 상세이미지'>").children("img");
		};

		z.screen.on("mouseenter", function(e){
			e.preventDefault();
			z.switchImg(z.img.attr("src"));
			z.pointer.show();
			z.target.show();
		}).on("mouseleave", function(e){
			e.preventDefault();
			z.target.empty();
			z.pointer.hide();
			z.target.hide();
		}).on("mousemove", function(e){
			z.screen.w = $(this).width(); //중간이미지 가로
			z.screen.h = $(this).height(); //중간이미지 세로
			z.screen.l = $(this).offset().left; //마우스 위치계산용 y좌표
			z.screen.t = $(this).offset().top; //마우스 위치계산용 x좌표
			z.pointer.w = z.pointer.width() + 2; //포인터박스 넓이(+보더값)
			z.pointer.h = z.pointer.height() + 2; //포인터박스 높이(+보더값)
			z.screen.x = Math.floor(e.pageX - z.screen.l - (z.pointer.w / 2)); //마우스x위치-이미지x좌표-(포인터박스 중간넓이) = 박스내 x좌표
			z.screen.y = Math.floor(e.pageY - z.screen.t - (z.pointer.h / 2)); //마우스y위치-이미지y좌표-(포인터박스 중간높이) = 박스내 y좌표
			z.screen.x = (z.screen.x < 0) ? 0 : z.screen.x > z.screen.w - z.pointer.w ? z.screen.w - z.pointer.w : z.screen.x; //포인트 박스 x위치보정
			z.screen.y = (z.screen.y < 0) ? 0 : z.screen.y > z.screen.h - z.pointer.h ? z.screen.h - z.pointer.h : z.screen.y; //포인트 박스 y위치보정
			z.target.w = z.target.width();
			z.target.h = z.target.height();
			z.target.x = z.screen.x / (z.screen.w-z.pointer.w) * 100; //이미지 가로 위치 퍼센트
			z.target.y = z.screen.y / (z.screen.h-z.pointer.h) * 100; //이미지 세로 위치 퍼센트

			//포인터박스 위치 설정
			z.pointer.css({
				'left' : z.screen.x+'px',
				'top' : z.screen.y+'px'
			});

			//줌이미지 위치 설정
			z.target.children('img').css({
				'left' : -1 * ((z.img[0].naturalWidth - z.target.w) * (z.target.x / 100))  +'px',
				'top' : -1 * ((z.img[0].naturalHeight - z.target.h) * (z.target.y / 100)) +'px'
			});
		});

		_setControl = function(){
			if(z.thumb.find("li:not(.thumb-video)").length>1){
				z.prev.addClass("active").bind("click", function(){
					_move(-1);
				});
				z.next.addClass("active").bind("click", function(){
					_move(1);
				});
			}
		};

		_move = function(dir){
			/**
			 * dir : -1(왼쪽) / 1(오른쪽)
			 */
			z.thumb.idx = z.thumb.idx + dir < 0 ? 5 : z.thumb.idx + dir > 5 ? 0 : z.thumb.idx + dir;
			z.setImage(z.thumb.idx);
			z.thumb.find("li:not(.thumb-video)").removeClass("active").eq(z.thumb.idx).addClass("active");
			z.screen.trigger("mouseleave").trigger("mouseenter").trigger("mousemove");
		};

		_init = function(){
			if(z.thumb.idx < 0){
				z.thumb.idx = 0;
			}
			z.setImage(z.thumb.idx);
			z.thumb.find("li:not(.thumb-video)").click(function(e){
				e.preventDefault();
				z.thumb.idx = $(this).index();
				z.setImage(z.thumb.idx);
				$(this).addClass("active").siblings("li").removeClass("active");
			});
			_setControl();
		}(this);
	}(this);
};

$.fn.tabControl = function(){
	tabEl = {
		box: $(this),
		btns: $(this).find(".tab-buttons > button"),
	};

	setActive = function(el){
		if($(el).hasClass("active")){
			return false;
		};
		$(el).addClass("active").siblings("button").removeClass("active");
		id = $(el).data("tab-id");
		$.each($(el).parent(".tab-buttons").siblings(".tab-panel"), function(i,v){
			if($(v).is("#"+id)){
				$(v).show();
			}else{
				$(v).hide();
			};
		});
	};

	_init = function(){
		tabEl.btns.click(function(){
			setActive(this);
		});
	}(this);
};

$.fn.customForm = function(){
	$(this).filter(function(i,v){
		return $(this).children("input").is("input[type='checkbox']");
	}).children("label").click(function(){
		$(this).prev("input").trigger("click");
	});

	$(this).filter(function(i,v){
		return $(this).children("input").is("input[type='radio']");
	}).children("label").click(function(){
		$(this).prev("input").trigger("click");
	});
};

$.fn.search = function(){
	sb = {
		box: $(this),
		btn: $(".btn_search"),
		form: $(".search_box"),
		form_i: $(".search_input"),
		keyword: $(".search_keywords"),
		helper: $(".search_helper")

	};

	help_switch = function(el){
		var img = $(el).parent("a").data("img"),
			txt = $(el).parent("a").data("title");

		sb.helper.find(".showcase_img > img").attr("src", img);
		sb.helper.find(".showcase_title > p").text(txt);
		$(el).parents("li").addClass("active").siblings("li").removeClass("active");
	};

	_init = function(){
		sb.btn.bind("click", function(e){
			e.preventDefault();
			sb.box.toggleClass("active").removeClass("searching");
			sb.form_i.val("");
		});

		sb.form_i.bind("keyup", function(e){
			if(sb.form_i.val() != ""){
				sb.box.addClass("searching");
				help_switch(sb.helper.find(".showcase_item a > span:first()"));
			}else{
				sb.box.removeClass("searching");
			}
		});

		sb.helper.find(".showcase_item a > span").on("mouseover", function(e){
			e.preventDefault();
			help_switch($(this));
		});

		if($("#search").hasClass("active searching")){
			help_switch(sb.helper.find(".showcase_item a > span:first()"));
		}
	}(this);
};

$.fn.recentView = function(){
	rv = {
		box: $(this),
		slider: $(this).find("ul"),
		prev: $(this).find(".btn_prev"),
		next: $(this).find(".btn_next"),
		step: 90,
		speed: 250,
		flag: false,
		clone: false
	};

	rv.next.click(function(e){
		e.preventDefault();
		if(rv.flag==true) return false;

		rv.flag = true;
		if(rv.clone){
			rv.slider.find(".clone").remove().end().children("li:last").clone(true).addClass("clone").appendTo(rv.slider);
		}
		rv.slider.animate({
			"right": -1*rv.step
		},rv.speed, function(){
			rv.slider.children("li:not(.clone)").first().appendTo(rv.slider);
			rv.slider.css({
				"right": 0
			});
			if(rv.clone){
				rv.slider.find(".clone").remove();
			}
			rv.flag = false;
		});
	});

	rv.prev.click(function(e){
		e.preventDefault();
		if(rv.flag==true) return false;

		rv.flag = true;
		if(rv.clone){
			rv.slider.find(".clone").remove().end().children("li:last").clone(true).addClass("clone").appendTo(rv.slider);
		}
		rv.slider.animate({
			"right": rv.step
		},rv.speed, function(){
			rv.slider.children("li:not(.clone)").last().prependTo(rv.slider);
			rv.slider.css({
				"right": 0
			});
			if(rv.clone){
				rv.slider.find(".clone").remove();
			}
			rv.flag = false;
		});
	});

	rv.bulid = function(){
		if(rv.slider.find("li").length < 5){ //4개 : 기능중지
			rv.box.addClass("inactive");
		}else if(rv.slider.find("li").length < 6){ //5개 : cloning
			rv.box.removeClass("inactive");
			rv.clone = true;
		}else{
			rv.box.removeClass("inactive");
			rv.slider.find(".clone").remove();
			rv.clone = false;
		}
	};

	_init = function(){
		rv.bulid();
	}(this);


};

$.fn.scrollMove = function(){
	$(this).click(function(e){
		e.preventDefault();
		var dir = $(this).data("scroll") == "up" ? 0 : $(this).data("scroll") == "down" ? $(document).height() : 0;

		$(document).scrollTop(dir);
	});
};

$.fn.setWing = function(){
	sW = {
		win_w: $(window).width(),
		win_h: $(window).height(),
		doc_w: $(document).width(),
		doc_h: $(document).height(),
		wing: $("#wing .inner").height(),
		foot: $("#footer").height()
	};

	window.GAP = sW.doc_w - sW.win_w;
	window.DIS = sW.doc_h - sW.wing - sW.foot;
};

$.fn.scrollWing = function(){
	sc = {
		left: $(window).scrollLeft(),
		top: $(window).scrollTop()
	};
	$(window).setWing();

	if(window.GAP != 0){
		var calc = -1*(window.GAP-sc.left)+35;
		$("#wing .inner").css({
			"right":calc
		});
	}else if(window.GAP == 0){
		$("#wing .inner").css({
			"right":"auto"
		});
	}

	if(sc.top - window.DIS > 0){
		$("#wing .inner").css({
			"top":-1*(sc.top - window.DIS)
		});
	}else{
		$("#wing .inner").css({
			"top":"auto"
		});
	}
};

$.fn.checkAll = function(){
	$(this).change(function(e){
		var group = $(this).data("radio-all");
		var targets = $("input[data-radio-check="+group+"]:not(:disabled)");
		if($(this).prop("checked")){
			targets.prop("checked", true);
		}else{
			targets.prop("checked", false);
		}
	});

	$("input[data-radio-check="+$(this).data("radio-all")+"]").bind("change", function(){
		if($("input[data-radio-check="+$(this).data("radio-check")+"]:not(:disabled)").length == $("input[data-radio-check="+$(this).data("radio-check")+"]:checked").length){
			$("input[data-radio-all="+$(this).data("radio-check")+"]").prop("checked", true);
		}else{
			$("input[data-radio-all="+$(this).data("radio-check")+"]").prop("checked", false);
		}
	});
};

$.fn.recommendList = function(){
	rL = {
		box: $(this),
		slider: $(this).find("ul"),
		prev: $(this).find(".btn_prev"),
		next: $(this).find(".btn_next"),
		step: 380,
		speed: 250,
		flag: false,
		clone: false
	};

	rL.next.click(function(e){
		e.preventDefault();
		if(rL.flag==true) return false;

		rL.flag = true;
		if(rL.clone){
			rL.slider.find(".clone").remove().end().children("li:last").clone(true).addClass("clone").appendTo(rL.slider);
		}
		rL.slider.animate({
			"left": -1*rL.step
		},rL.speed, function(){
			rL.slider.children("li:not(.clone)").first().appendTo(rL.slider);
			rL.slider.css({
				"left": 0
			});
			if(rL.clone){
				rL.slider.find(".clone").remove();
			}
			rL.flag = false;
		});
	});

	rL.prev.click(function(e){
		e.preventDefault();
		if(rL.flag==true) return false;

		rL.flag = true;
		if(rL.clone){
			rL.slider.find(".clone").remove().end().children("li:last").clone(true).addClass("clone").appendTo(rL.slider);
		}
		rL.slider.animate({
			"left": rL.step
		},rL.speed, function(){
			rL.slider.children("li:not(.clone)").last().prependTo(rL.slider);
			rL.slider.css({
				"left": 0
			});
			if(rL.clone){
				rL.slider.find(".clone").remove();
			}
			rL.flag = false;
		});
	});

	_init = function(){
		if(rL.slider.find("li").length < 4){ //3개 : 기능중지
			rL.box.addClass("inactive");
		}else if(rL.slider.find("li").length < 5){ //4개 : cloning
			rL.clone = true;
		}
	}(this);
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

$.fn.qna = function(){
	$(this).find(".qna-tab-title").bind("click", function(e){
		e.preventDefault();
		$(this).parent("li").toggleClass("active");
	});
};

$(window).bind("resize scroll", function(){
	$(window).scrollWing();
});

$(document).ready(function() {
	$(".tab").tabControl();
	$(".form-custom, .form-wax, .form-hash").customForm();
	$(".form-radiobox").customForm();
	$("#search").search();
	$(".recent_view").recentView();
	$(".recommend-list").recommendList();
	$(".wing-util > button").scrollMove();
	$(".qna-tab").qna();
	$(".top-banner ul").lightSlider({
		item:1,
		slideMargin:0,
		controls:true,
		auto:false,
		loop:true,
		pager:false,
		addClass:'top-banner-slide',
		onSliderLoad: function(el){
			// console.log("work")
		}
	});
	$.each($("select.form-control").has("option[data-etc-target]"), function(i,v){
		$(v).etcView();
	});
	$.each($("input[data-radio-all]"), function(i,v){
		$(v).checkAll();
	});
	$.each($(".form-group input.form-control:not([readonly])"), function(i,v){
		$(v).formControl();
	});

	$("#cart > a").click(function(e){
		e.preventDefault();
		$("#cart").toggleClass("active");
	});
	$(window).trigger("resize");

	$(".detail-image").zoom();
	$(".detail-timer, .sale-timer").timesale();
	$(".info-layer > p").infoOpen();

	$(".truncate").dotdotdot({
		ellipsis:"...",
		watch:true,
		callback: function(){
			console.log('work');
		}
	});

	$(".form-datepicker").datepicker();

	$(document).on('opening', '.remodal', function () {
		if($(this).data("slide") == "user-image"){
			$(".user-image ul").lightSlider({
				adaptiveHeight:true,
				item:1,
				slideMargin:0,
				controls:true,
				auto:false,
				loop:true,
				pager:false,
				addClass:'user-slide',
				onSliderLoad: function(el){
					$(el).next(".lSAction").appendTo($(".user-slide"));
				}
			});
		};
	});			
});
window.REMODAL_GLOBALS = {
	DEFAULTS: {
		hashTracking: false
	}
};