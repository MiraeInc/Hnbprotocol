$.fn.formControl = function(){
	var temp = "<button type='button' class='clear-form' tabindex='-1'></button>";

	if($(this).parent("div").hasClass("form-group")){
		$(this).parent(".form-group").append(temp).addClass("hasClear");

		$(this).siblings(".clear-form").on("touchstart click", function(e) {
			e.preventDefault();
			$(this).siblings("input").val("");
		});
	}else if($(this).parent("div").hasClass("form-holder")){
		$(this).parent(".form-holder").append("<div class='cf-wrap'>"+temp+"</div>").addClass("hasClear");	

		$(this).siblings(".cf-wrap").children(".clear-form").on("touchstart click", function(e) {
			e.preventDefault();
			$(this).parent(".cf-wrap").siblings("input").val("");
		});
	}
};

$.fn.textareaBox = function(){
	$(this).find(".textarea-btn").click(function(){
		$(this).parent(".textarea-box").toggleClass("active");
		$(this).siblings(".textarea-wrap").slideToggle();
	});

	$(this).find(".textarea-up").click(function(){
		var target = $(this).parent(".textarea-control").siblings(".textarea-wrap").find("textarea"),
			step = target.height()/target.attr("rows"),
			moveline = 1;
		target.scrollTop(target.scrollTop() - (step * moveline));
	});

	$(this).find(".textarea-down").click(function(){
		var target = $(this).parent(".textarea-control").siblings(".textarea-wrap").find("textarea"),
			step = target.height()/target.attr("rows"),
			moveline = 1;
		target.scrollTop(target.scrollTop() + (step * moveline));
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

	/*
		수정 : 2017-09-11 (정진택)
		- 닫기 이벤트 추가 
	*/
	$(this).bind("click", function(e){
		
		e.stopPropagation();
		
		var that = this;
		var $this = $(this);
		var $parent = $this.parent(".info-layer") 
		
		
		action($parent);
		
		if($parent.hasClass('active')){
			
			$parent.off('.info.close');
			$(document).off('.info.close');
			
			$parent.one('click.info.close', '.btn-close', function(e){
				action($parent);
			})
			
			$(document).one('click.info.close', function(e){
				var $this = $(e.target);
				if($this.closest('.info-layer').length < 1){
					action($parent);
				}
			})
		}
		
		
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

		// 170801 : 임시 비활성화
		// z.screen.on("mouseenter", function(e){
		// 	e.preventDefault();
		// 	z.switchImg(z.img.attr("src"));
		// 	z.pointer.show();
		// 	z.target.show();
		// }).on("mouseleave", function(e){
		// 	e.preventDefault();
		// 	z.target.empty();
		// 	z.pointer.hide();
		// 	z.target.hide();
		// }).on("mousemove", function(e){
		// 	z.screen.w = $(this).width(); //중간이미지 가로
		// 	z.screen.h = $(this).height(); //중간이미지 세로
		// 	z.screen.l = $(this).offset().left; //마우스 위치계산용 y좌표
		// 	z.screen.t = $(this).offset().top; //마우스 위치계산용 x좌표
		// 	z.pointer.w = z.pointer.width() + 2; //포인터박스 넓이(+보더값)
		// 	z.pointer.h = z.pointer.height() + 2; //포인터박스 높이(+보더값)
		// 	z.screen.x = Math.floor(e.pageX - z.screen.l - (z.pointer.w / 2)); //마우스x위치-이미지x좌표-(포인터박스 중간넓이) = 박스내 x좌표
		// 	z.screen.y = Math.floor(e.pageY - z.screen.t - (z.pointer.h / 2)); //마우스y위치-이미지y좌표-(포인터박스 중간높이) = 박스내 y좌표
		// 	z.screen.x = (z.screen.x < 0) ? 0 : z.screen.x > z.screen.w - z.pointer.w ? z.screen.w - z.pointer.w : z.screen.x; //포인트 박스 x위치보정
		// 	z.screen.y = (z.screen.y < 0) ? 0 : z.screen.y > z.screen.h - z.pointer.h ? z.screen.h - z.pointer.h : z.screen.y; //포인트 박스 y위치보정
		// 	z.target.w = z.target.width();
		// 	z.target.h = z.target.height();
		// 	z.target.x = z.screen.x / (z.screen.w-z.pointer.w) * 100; //이미지 가로 위치 퍼센트
		// 	z.target.y = z.screen.y / (z.screen.h-z.pointer.h) * 100; //이미지 세로 위치 퍼센트

		// 	//포인터박스 위치 설정
		// 	z.pointer.css({
		// 		'left' : z.screen.x+'px',
		// 		'top' : z.screen.y+'px'
		// 	});

		// 	//줌이미지 위치 설정
		// 	z.target.children('img').css({
		// 		'left' : -1 * ((z.img[0].naturalWidth - z.target.w) * (z.target.x / 100))  +'px',
		// 		'top' : -1 * ((z.img[0].naturalHeight - z.target.h) * (z.target.y / 100)) +'px'
		// 	});
		// });

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

		$.each($(el).parents(".tab-buttons").siblings(".tab-panel"), function(i,v){
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
	}).children("label").unbind("click").click(function(e){
		console.log(e)

		$(this).prev("input").trigger("click");
	});

	$(this).filter(function(i,v){
		return $(this).children("input").is("input[type='radio']");
	}).children("label").unbind("click").click(function(){
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
			txt = $(el).parent("a").data("title"),
			goodscd = $(el).parent("a").data("goodscd"),
			goodscd_url = getContextPath()+"/product/productView.do?goodsCd="+goodscd;

		if (goodscd != "") {
			sb.helper.find(".showcase_img img").attr("src", img);
			if (goodscd == "" || goodscd=="undefined") {
				sb.helper.find(".showcase_img> a").attr("href", "#");
			} else {
				sb.helper.find(".showcase_img> a").attr("href", goodscd_url);
			}
			sb.helper.find(".showcase_title > p").text(txt);
		}
		$(el).parents("li").addClass("active").siblings("li").removeClass("active");
	};

	topsearchAjax = function (query) {

		$.ajaxSetup({cache:false});
		$.ajax({			
			url: getContextPath()+"/ajax/common/topSearchAjax.do",
			data: {"query":query},
		 	type: "post",
		 	async: false,
		 	cache: false,
		 	contentType: "application/x-www-form-urlencoded; charset=UTF-8",  
		 	error: function(request, status, error){ 
		 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
			},
			success: function(data){
				$(".helper_inner").html(data);
				sb.helper.find(".showcase_item a > span").on("mouseover", function(e){
					e.preventDefault();
					help_switch($(this));
				});
				
				sb.helper.find(".showcase_tag a > span").on("mouseover", function(e){
					e.preventDefault();
					help_switch($(this));
				});

				sb.helper.find(".showcase_item a").on("click", function(e){
					var keyword =$(this).data("keyword");
					goTopSearch(keyword);
				//	location.href = getContextPath()+"/etc/searchResult.do?keyword="+encodeURIComponent(keyword);
				});
				
				sb.helper.find(".showcase_tag a").on("click", function(e){
					var keyword =$(this).data("keyword");
					goTopSearch(keyword);
				//	location.href = getContextPath()+"/etc/searchResult.do?keyword="+ encodeURIComponent(keyword);
				});
				
				help_switch(sb.helper.find(".showcase_list a > span:first()"));
			 }
		});

	};
	
	_init = function(){
		sb.btn.bind("click", function(e){
			e.preventDefault();
			sb.box.toggleClass("active").removeClass("searching");
			//sb.form_i.val("");
			
			if(sb.form_i.val() != ""){
				sb.box.addClass("searching");
				
				topsearchAjax(sb.form_i.val());  //자동완성검색어
			}else{
				sb.box.removeClass("searching");
			}
		});

		sb.form_i.bind("keyup", function(e){
			if(sb.form_i.val() != ""){
				sb.box.addClass("searching");
				
				topsearchAjax(sb.form_i.val());  //자동완성검색어
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

		$(".search_close").bind("click", function(){
			sb.box.removeClass("active searching");
		});
		
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

$.fn.qna2 = function(){
	$(this).bind("click", function(e){
		e.preventDefault();
		$(this).parents("tr").toggleClass("qna-no-border");
		$("[data-aid='"+$(this).data("qid")+"']").toggle();
		window.qna_slider;

		if($("[data-aid='"+$(this).data("qid")+"']").is(":visible")){
			window.qna_slider = $("[data-aid='"+$(this).data("qid")+"']").find(".qna-image ul").lightSlider({
				adaptiveHeight:true,
				item:1,
				slideMargin:0,
				controls:true,
				auto:false,
				loop:true,
				pager:false,
				addClass:'qna-slide',
				onSliderLoad: function(el){
					console.log(el)
					$(el).next(".lSAction").appendTo(el.parent().parent());
				}
			});
		}else{
			window.qna_slider.destroy();
		}
	});
}

$.fn.rating = function(){
	$(this).find("b").unbind("click").bind("click", function(e){
		e.preventDefault();
		$(this).parent(".detail-rate").attr("data-rate", $(this).index()+1);
	});
}

$.fn.stylingMotion = function(){
	sM = {
		prev : $(this).find("button.prev"),
		next : $(this).find("button.next"),
		slide_box : $(this).find(".styling-wrap"),
		slide_nav : $(this).find(".styling-nav"),
		slide_idx : 0,
		slide_speed : 250,
		slide_w : 1160,
		slide_item : $(this).find(".styling-wrap").children(".styling-slide:not(.clone)"),
		slide_max : $(this).find(".styling-wrap").children(".styling-slide:not(.clone)").length-1,
		slide_lock : false
	};

	_moveLeft = function(){
		if(sM.slide_lock) return false;
		if(sM.slide_idx - 1 >= 0){
			sM.slide_idx = sM.slide_idx - 1;
			_move(sM.slide_idx);
		}else{
			sM.slide_lock = true;
			sM.slide_box.css({
				"-webkit-transform": "translateX(" + sM.slide_w + "px)",
				"transform": "translateX(" + sM.slide_w + "px)"
			});
			_setNav(sM.slide_max);
			setTimeout(function(){
				sM.slide_box.addClass("cloneMove");
				sM.slide_idx = sM.slide_max;
				_move(sM.slide_idx);
				setTimeout(function(){
					sM.slide_box.removeClass("cloneMove");
				}, sM.slide_speed);
			}, sM.slide_speed);
		}
	};

	_moveRight = function(){
		if(sM.slide_lock) return false;
		if(sM.slide_idx + 1 <= sM.slide_max){
			sM.slide_idx = sM.slide_idx + 1;
			_move(sM.slide_idx);
		}else{
			sM.slide_lock = true;
			sM.slide_box.css({
				"-webkit-transform": "translateX(" + (sM.slide_w * (sM.slide_max + 1) * -1) + "px)",
				"transform": "translateX(" + (sM.slide_w * (sM.slide_max + 1) * -1) + "px)"
			});
			_setNav(0);
			setTimeout(function(){
				sM.slide_box.addClass("cloneMove");
				sM.slide_idx = 0;
				_move(sM.slide_idx);
				setTimeout(function(){
					sM.slide_box.removeClass("cloneMove");
				}, sM.slide_speed);
			}, sM.slide_speed);
		}
	};

	//슬라이드 이동
	_move = function(idx){
		sM.slide_lock = true;
		sM.slide_box.css({
			"-webkit-transform": "translateX(" + (idx * sM.slide_w * -1) + "px)",
			"transform": "translateX(" + (idx * sM.slide_w * -1) + "px)",
		});
		_unlock();
		_setNav(idx);
	};

	// 슬라이드 락 해제
	_unlock = function(){
		setTimeout(function(){
			sM.slide_lock = false;
		}, sM.slide_speed);
	};

	// 사이드네비 활성화
	_setNav = function(idx){
		// idx != 0 ? sM.slide_nav.addClass("active") : sM.slide_nav.removeClass("active");
		if(idx != 0){
			sM.slide_nav.addClass("active");
			$.each(sM.slide_nav.find("li"), function(i,v){
				if(idx-1 == i){
					$(v).css({
						"background": $(v).children("a").data("slide-color")
					});
					_setStatAction(i);
				}else{
					$(v).css({
						"background": "none"
					});
				}
			});
		}else{
			sM.slide_nav.removeClass("active");
			_setStatAction(-1);
		}
	};

	//circle액션 활성화
	_setStatAction = function(idx){
		$.each(sM.slide_item, function(i,v){
			if(idx == i-1){
				// console.log(i+"번 활성화")
				$(v).find(".styling-product-stat").addClass("active");
			}else{
				// console.log(i+"번 비활성화")
				$(v).find(".styling-product-stat").removeClass("active");
			}
		});
	};

	// 슬라이드 순서정렬
	_setPosition = function(){
		$.each(sM.slide_item, function(i, v){
			$(v).css({
				"position": "absolute",
				"left": sM.slide_w * i
			});
		});
		_move(sM.slide_idx);
	};

	// 처음과 끝 이동 모션용 더미
	_makeClone = function(){
		start = sM.slide_item.first().clone().addClass("clone").css({
			"position": "absolute",
			"left": (sM.slide_max + 1) * sM.slide_w
		});
		last = sM.slide_item.last().clone().addClass("clone").css({
			"position": "absolute",
			"left": -1 * sM.slide_w
		});

		sM.slide_box.append(start);
		sM.slide_box.prepend(last);
	};

	// 아이템 슬라이드링크 연결
	_makeLink = function(){
		$.each(sM.slide_box.find("[data-slide-move]"), function(i, v){
			$(v).bind("click", function(e){
				e.preventDefault();
				sM.slide_idx = $(this).data("slide-move");
				_move(sM.slide_idx);
			});
		});
		$.each(sM.slide_nav.find("[data-slide-move]"), function(i, v){
			$(v).bind("click", function(e){
				e.preventDefault();
				sM.slide_idx = $(this).data("slide-move");
				_move(sM.slide_idx);
			});
		});
	};

	_init = function(){
		_makeLink();
		_makeClone();
		_setPosition();
		sM.prev.bind("click", function(){
			_moveLeft();
		});
		sM.next.bind("click", function(){
			_moveRight();
		});
	}(this);
};

$.fn.vs = function(data){
	_opt = {
		menu : "",
		slot_1 : null,
		slot_2 : null,
		data : null
	};

	_msg = {
		// "selected" : "이미 선택된 상품 입니다.",
		"full" : "비교 상품을 더 추가할 수 없습니다."
	}

	// _ajax = function(URL){
	// 	//리스트 업데이트
	// 	$.ajax({
	// 		url: URL,
	// 		cache: false,
	// 		dataType: "json",
	// 		success: function(data){
	// 			_opt.data = data;
	// 			_ready();
	// 		},
	// 		error: function(err){
	// 			alert("ERROR : "+err);
	// 		}
	// 	});
	// };

	_menu = function(m){
		if(_opt.menu == m) return false;

		//슬롯 초기화
		var temp = _cunstruct("empty");
		$("#slot-1").empty().append(temp);
		$("#slot-2").empty().append(temp);
		_opt.slot_1 = null;
		_opt.slot_2 = null;

		//메뉴 초기화
		_opt.menu = m;
		_ready();
	};

	_ready = function(){
		//상품 버튼 제작
		$(".vs-list-item").empty();
		$.each(_opt.data[_opt.menu], function(i,v){
			data = {
				"idx" : i,
				"img_s" : v.img_s
			};
			var temp = _cunstruct("button", data);
			$(".vs-list-item").append(temp).find("button:eq(" + i + ")").bind("click", function(){
				if(_plug($(this).data("idx"))){
					//슬롯액션 발생시에만 클래스 할당
					$(this).toggleClass("active");
				};
			});
		});
		//항상 첫 아이템 셋팅
		$(".vs-list-item").find("button:first").triggerHandler("click");
	};

	_plug = function(target){
		//슬롯에 해당상품이 없을때 계속 진행
		// if(_opt.slot_1 == target || _opt.slot_2 == target){
		// 	alert(_msg["selected"]);
		// 	return false;
		// };
		
		//선택상품인 경우 슬롯 빼기
		if(_opt.slot_1 == target || _opt.slot_2 == target){
			var temp = _cunstruct("empty");
			if(_opt.slot_1 == target){
				$("#slot-1").empty().append(temp);
				_opt.slot_1 = null;
			}else{
				$("#slot-2").empty().append(temp);
				_opt.slot_2 = null;
			};
			return true;
		};

		//빈 슬롯이 없으면 종료
		if(_opt.slot_1 != null && _opt.slot_2 != null){
			alert(_msg["full"]);
			return false;
		};

		//슬롯 채우기
		var temp = _cunstruct("slot", _opt.data[_opt.menu][target]);
		if(_opt.slot_1 == null){
			$("#slot-1").empty().append(temp);
			_opt.slot_1 = target;
		}else{
			$("#slot-2").empty().append(temp);
			_opt.slot_2 = target;
		};
		return true;
	};

	_cunstruct = function(type, data){
		var temp = "";
		
		switch(type){
			case "button":
				temp += '<button type="button" data-idx="' + data.idx + '">';
				temp += '	<img src="' + data.img_s + '" alt="상품이미지">';
				temp += '</button>';
			break;

			case "slot":
				temp += '<div class="vs-img">';
				temp += '	<img src="' + data.img_l + '" alt="상품이미지">';
				temp += '	<div class="styling-product-link">';
				temp += '		<a href="javascript:void(0);" onclick="buyNow(\'' + data.link_cart + '\');" class="btn_view">바로구매</a>';
				temp += '		<a href="javascript:void(0);" onclick="goCart(\'' + data.link_cart + '\');" class="btn_cart">장바구니</a>';
				temp += '	</div>';
				temp += '</div>';
				temp += '<div class="vs-info">';
				temp += '	<a href="' + data.link_goodsCd + '">';
				temp += '		<div class="vs-info-1">' + data.item_pub + '</div>';
				temp += '		<div class="vs-info-2">' + data.item_name + '</div>';
				temp += '		<div class="vs-price">';
				if(data.item_discount != 0 && data.item_discount != ""){
					temp += '		<div class="vs-price-dc">' + data.item_discount + '</div>';
				}
				temp += '			<div class="vs-price-total">' + comma(data.item_total) + '</div>';
				temp += '		</div>';
				temp += '	</a>';
				temp += '</div>';
				temp += '<div class="vs-stat">';
				if(data.item_stat_setting != 0 && data.item_stat_setting != ""){
					temp += '	<div class="vs-stat-box">';
					temp += '		<strong>셋팅력</strong>';
					temp += '		<div class="vs-stat-val">';
					temp += '			<div class="vs-stat-horizon">';
					temp += '				<figure class="circle">';
					temp += '					<figcaption data-step="' + data.item_stat_setting + '"></figcaption>';
					temp += '					<svg>';
					temp += '						<circle class="line" cx="35" cy="35" r="34" transform="rotate(-90, 35, 35)"></circle>';
					temp += '					</svg>';
					temp += '				</figure>';
					temp += '			</div>';
					temp += '		</div>';
					temp += '	</div>';
				};
				if(data.item_stat_setting != 0 && data.item_stat_setting != ""){
					temp += '	<div class="vs-stat-box">';
					temp += '		<strong>윤기</strong>';
					temp += '		<div class="vs-stat-val">';
					temp += '			<div class="vs-stat-horizon">';
					temp += '				<figure class="circle">';
					temp += '					<figcaption data-step="' + data.item_stat_glossy + '"></figcaption>';
					temp += '					<svg>';
					temp += '						<circle class="line" cx="35" cy="35" r="34" transform="rotate(-90, 35, 35)"></circle>';
					temp += '					</svg>';
					temp += '				</figure>';
					temp += '			</div>';
					temp += '		</div>';
					temp += '	</div>';
				};
				if(data.item_stat_setting != 0 && data.item_stat_setting != ""){
					temp += '	<div class="vs-stat-box">';
					temp += '		<strong>머리길이</strong>';
					temp += '		<div class="vs-stat-val">';
					temp += '			<div class="vs-stat-horizon">';
					//헤어타입 루프
					data.item_stat_hair = data.item_stat_hair.replace(/HAIR_STYLE10/gi, "VERY SHORT");
					data.item_stat_hair = data.item_stat_hair.replace(/HAIR_STYLE20/gi, "SHORT");
					data.item_stat_hair = data.item_stat_hair.replace(/HAIR_STYLE30/gi, "MEDIUM SHORT");
					data.item_stat_hair = data.item_stat_hair.replace(/HAIR_STYLE40/gi, "MEDIUM");
					
					hair_type = data.item_stat_hair.split(",");
					for(var i = 0; i < hair_type.length; i++){
						temp += '				<strong>' + hair_type[i] + '</strong>';
					};
					temp += '			</div>';
					temp += '		</div>';
					temp += '	</div>';
				};
				temp += '</div>';
			break;

			case "empty":
				temp += '<div class="vs-img"></div>';
			break;
		}

		return temp;
	};

	_init = function(){
		_opt.menu = "mr";
		// _ajax("http://222.112.106.57:8080/mandom/web/@page/1_product/items.json");
		_opt.data = data;
		var temp = _cunstruct("empty");
		
		$("#slot-1").empty().append(temp);
		$("#slot-2").empty().append(temp);
		_opt.slot_1 = null;
		_opt.slot_2 = null;
		_ready();

		$(".vs-nav").find("a[data-menu]").bind("click", function(e){
			e.preventDefault();
			_menu($(this).data("menu"));
		});
	}(this);
};


$.fn.photoreview = function(){
	$(this).unbind("click").click(function(){
		$(this).next(".photoreview").show();
		$(this).remove();
	});
};

$.fn.tabSlide = function(){
	tabs = {
		that: $(this),
		ts_prev: "<a href='#' class='tabSlide-prev'>이전</a>",
		ts_next: "<a href='#' class='tabSlide-next'>다음</a>",
		idx: 0,
		max: $(this).find("button").length - 1,
		flag: false
	};

	_scroll = function(dir){
		scrollY = tabs.that.find(".tabSlide").find("button").eq(tabs.idx)[0].offsetLeft - 80; //좌측 패딩값 80
		if(tabs.that.find(".tabSlide").scrollLeft() >= tabs.that.find(".tabSlide")[0].scrollWidth - tabs.that.find(".tabSlide").width() && dir == -1){
			tabs.idx = tabs.idx + dir; //idx 복구
			return false; //이동할 수 없으면 종료
		};

		tabs.flag = true;
		tabs.that.find(".tabSlide").animate({
			scrollLeft: scrollY
		}, 500, function(){
			tabs.flag = false;
		});
	};

	tabs.that.find(".tab-buttons").wrapInner("<div class='tabSlide'></div>").append(tabs.ts_prev).append(tabs.ts_next);
	tabs.that.find(".tabSlide-prev").bind("click", function(e){
		e.preventDefault();
		if(tabs.flag == true) return false;
		tabs.idx = tabs.idx > 0 ? tabs.idx - 1 : 0;
		_scroll(1);
	})
	tabs.that.find(".tabSlide-next").bind("click", function(e){
		e.preventDefault();
		if(tabs.flag == true) return false;
		tabs.idx = tabs.idx < tabs.max ? tabs.idx + 1 : tabs.max;
		_scroll(-1);
	});
};

$.ajaxRebind = function(){
	$(".btn-photoreview").photoreview();
	$(".form-custom").customForm();
	$(".form-radiobox").customForm();
	$(".detail-rate[data-rate]").rating();
};

$(window).bind("resize scroll", function(){
	$(window).scrollWing();
});



/**
 * 이미지 확대 레이어팝업
 * @param path 실제 이미지 경로
 * @param mode null or 'multiple'
 * @returns
 */
function imageZoom(path, mode){
	
	var PLUGIN_NAME = 'remodal';
	var MODAL = null;
	
	var $tmpl = '';
	var $popup = null;
	
	var id = 'remodal' + Math.floor(Math.random() * 9999) + 1;
	
		
	$tmpl += '<div class="remodal popup popup-zoom" data-remodal-id='+ id +'>';
	$tmpl += '	<a href="javascript:;" class="ico_pop_close" data-remodal-action="close"><img src="../images/w/contents/ico_pop_close.png" alt="close"></a>';
	$tmpl += '	<div class="pop-mid">';
	$tmpl += '	<img src="' + path + '" alt="" />';
	$tmpl += '	</div>';
	$tmpl += '</div>';
	
	$('body').append($tmpl);
	
	$popup = $('[data-remodal-id='+id+']');
	
	if(mode){
		$modal.attr('data-remodal-mode', mode)
	}
	
	MODAL = $popup[PLUGIN_NAME]();
	MODAL.open();
	
	$(document).one('closed', '[data-remodal-id='+id+']', function (e) {
		MODAL.destroy();
	});
	
}


/**
 * 공지성 팝업
 * @param target {string} 컨텐츠가 포함되어있는 DOM
 * @param setting {object} 팝업을 위한 세팅
 * @example 
 * noticePopup("popNotice1", {position: true, x:100, y:100}) 
 */
function noticePopup(target, setting){
	
	var MODAL = null;
	var DEFAULTS = {
			poisition: false,	// {boolean} 위치를 직접 지정할 경우 true로 설정하고 x,y값을 반드시 지정해야함
			x: 0,				// {number} x축 픽셀값 
			y: 0				// {number} y축 픽셀값
	}
	
	
	var $popup = null;
	
	var options = $.extend({}, DEFAULTS, setting);
	
	//대상팝업
	$popup = $('[data-remodal-id='+target+']');
	
	//위치설정
	if(options.position){
		$popup.css({
			position: 'absolute',
			left: options.x,
			top: options.y
		})
	}
	
	//remodal 오픈
	MODAL = $popup['remodal']({
		lock: false,
		hashTracking: false,
		closeOnEscape: false,
		closeOnOutsideClick: false,
		modifier: 'type-notice'
	});

	MODAL.open();
	

}


var reviewSlider; //리뷰

$(document).ready(function() {
	
	$(".tab").tabControl();
	$(".form-custom, .form-wax, .form-hash").customForm();
	$(".form-radiobox").customForm();
	$("#search").search();
	$(".recent_view").recentView();
	$(".recommend-list").recommendList();
	$(".wing-util > button").scrollMove();
	$(".qna-tab").qna();
	$("[data-qid]").qna2();
	$(".detail-rate[data-rate]").rating();
	$(".textarea-box").textareaBox();
	$(".btn-photoreview").photoreview();
	$(".tab-slide").tabSlide();
	$(".btn-share > span:not(.box-share)").click(function(){
		$(this).next(".box-share").toggle();
	});
	$(".btn_share_close").click(function(e){
		e.preventDefault();
		$(".box-share").hide();
	});
	$(".mainplay_wrap > button").click(function(){
		$(this).parent(".mainplay_wrap").toggleClass("active");
	});

	$(".top-banner ul").lightSlider({
		item:1,
		slideMargin:0,
		controls:true,
		auto:true,
		loop:true,
		pager:false,
		pauseOnHover:true,
		pause:3000,
		addClass:'top-banner-slide',
		onSliderLoad: function(el){
			// console.log("work")
		}
	});
	
	$(".main-stylemovie ul").lightSlider({
		item:1,
		slideMargin:0,
		controls:true,
		auto:false,
		loop:true,
		pager:false,
		addClass:'movie-items-slide',
		onSliderLoad: function(el){
			// console.log("work")
		}
	});
	
	reviewSlider =  $(".review-container ul").lightSlider({
		item:2,
		slideMargin:68,
		controls:true,
		auto:false,
		// loop:true,
		pager:false,
		// slideMove:2,
		addClass:'review-slide',
		onSliderLoad: function(el){
			$(el).next(".lSAction").appendTo($(".review-container"));
		}
	});

	$(".eventbanner ul").lightSlider({
		item:1,
		slideMargin:0,
		controls:false,
		auto:true,
		loop:true,
		pager:false,
		pauseOnHover:true,
		pause:3000,
		addClass:'eventbanner-slide'
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

	// $("#cart > a").click(function(e){
	// 	e.preventDefault();
	// 	$("#cart").toggleClass("active");
	// });
	$(window).trigger("resize");

	$(".detail-image").zoom();
	$(".detail-timer, .sale-timer").timesale();
	$(".info-layer > p").infoOpen();

	$(".truncate").dotdotdot({
		ellipsis:"...",
		watch:true,
		wrap:"letter",
		callback: function(){
			// console.log('work');
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

		//헤어스타일링 오픈시 인터렉션 기능 실행
		if($(this).hasClass("type-style")){
			$(this).stylingMotion();
		};

		//가격비교 오픈시 인터렉션 기능 실행
		// if($(this).data("remodal-id") == "item_vs"){
		// 	$(this).vs(vs_data);
		// };
	});

	$(".banner-close").click(function(e){
		e.preventDefault();
		$("#global_banner").hide();
	});
});

window.REMODAL_GLOBALS = {
	DEFAULTS: {
		hashTracking: false
	}
};

jQuery(function($){
	$.datepicker.regional['ko'] = {
		closeText: '닫기',
		prevText: '이전달',
		nextText: '다음달',
		currentText: '오늘',
		monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		dayNames: ['일','월','화','수','목','금','토'],
		dayNamesShort: ['일','월','화','수','목','금','토'],
		dayNamesMin: ['일','월','화','수','목','금','토'],
		weekHeader: '주',
		dateFormat: 'yy-mm-dd',
		firstDay: 0,
		isRTL: false,
		showMonthAfterYear: true,
		showOtherMonths: true,
		yearSuffix: '년',
		maxDate: '+0d'
	};
	$.datepicker.setDefaults($.datepicker.regional['ko']);
});




/**
 * 2018 리뉴얼
 */
$(function(){ //dom is ready!

	/**
	 * 카테고리
	 */
	$('#cateMenu').on('mouseenter', function(){
		var $this = $(this);
		var width = 0;
		var height = 0;
		
		$this.find('.sub-list').each(function(idx){
			var _h = $(this).outerHeight();
			if(height < _h){
				height = _h
			}
		})

		$this.stop().animate({'height': height + 70}, {'duration': 200});
	});

	//카테고리 - 닫기
	$('#cateMenu').on('mouseleave', function(){
		var $this = $(this);
		$this.stop().animate({'height': 70}, {'duration': 200});
	})

	
	/**
	 * 기타메뉴
	 */
	$('#cateMenuEtc').on('mouseenter', '.menu-link', function(){
		var $this = $(this);
		var temp = 0;
	
		$this.closest('li').siblings()
			.find('.menu-link').removeClass('active')
			.next('.sub-list').hide();
		
		$this.addClass('active');
		$this.next('.sub-list').show();
	});

	//기타메뉴 - 닫기
	$('#cateMenuEtc').on('mouseleave', function(e){
		var $this = $(this);
		$this.find('.sub-list').hide();
	})

})