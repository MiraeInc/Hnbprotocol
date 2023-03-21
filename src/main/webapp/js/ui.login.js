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

$.fn.etcView2 = function(){
	if($(this).is("[type='radio']")){
		$("input[name="+$(this).attr("name")+"]").change(function(e){
			if($("input[name="+$(this).attr("name")+"][data-etc-target]").prop("checked")){
				var name = $("input[name="+$(this).attr("name")+"][data-etc-target]").data("etc-target");
				$("[data-etc-name="+name+"]").show();
			}else{
				var name = $("input[name="+$(this).attr("name")+"][data-etc-target]").data("etc-target");
				$("[data-etc-name="+name+"]").hide().children("input").val("");
			}
		});
	}else{
		$(this).change(function(e){
			if($(this).prop("checked")){
				var name = $(this).data("etc-target");
				$("[data-etc-name="+name+"]").show();
			}else{
				var name = $(this).data("etc-target");
				$("[data-etc-name="+name+"]").hide().children("input").val("");
			}
		});		
	}
};

$.fn.checkAll = function(){
	$(this).change(function(e){
		var group = $(this).data("radio-all");
		var targets = $("input[data-radio-check="+group+"]");
		if($(this).prop("checked")){
			targets.prop("checked", true);
		}else{
			targets.prop("checked", false);
		}
	});

	$("input[data-radio-check="+$(this).data("radio-all")+"]").bind("change", function(){
		if($("input[data-radio-check="+$(this).data("radio-check")+"]").length == $("input[data-radio-check="+$(this).data("radio-check")+"]:checked").length){
			$("input[data-radio-all="+$(this).data("radio-check")+"]").prop("checked", true);
		}else{
			$("input[data-radio-all="+$(this).data("radio-check")+"]").prop("checked", false);
		}
	});
};

$(document).ready(function() {
	$("html").fitText(1.5, { minFontSize: '10px', maxFontSize: '28px' });
	$(".tab").tabControl();
	$(".form-custom").customForm();
	$(".form-radiobox").customForm();
	$(".textarea-box").textareaBox();
	$.each($("select.form-control").has("option[data-etc-target]"), function(i,v){
		$(v).etcView();
	});
	$.each($("input[data-etc-target].form-control"), function(i,v){
		$(v).etcView2();
	});
	$.each($("input[data-radio-all]"), function(i,v){
		$(v).checkAll();
	});
	$.each($(".form-group input.form-control:not([readonly])"), function(i,v){
		$(v).formControl();
	});
});


window.REMODAL_GLOBALS = {
	DEFAULTS: {
		hashTracking: false
	}
};