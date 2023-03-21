    var ajax_flag = true;
    var req01_AJAX;
    var PayUrl="";


    function kcp_AJAX()
    {
        // 해당 소스는 jQuery를 사용 하지 않는 업체에서도 작동 할수 있도록 구현되었습니다.
        // 이 부분은 javascript로 구현시 jQuery를 이용하면 더욱 쉽게 구현 할수 있습니다. 
        // 또는
        // javascript를 통한 ajax 통신을 할 필요 없이 소스내에 order_approval.jsp를 연동해서 사용 하셔도 관계 없으며
        // 제공되는 jar 파일에 직접 전달된 데이터를 사용할수 있도록 method가 구현 되어 있습니다.
        if(ajax_flag)
        {
        	/*
            var url    = "order_approval.jsp";
            var form = document.order_info;
            var params = "?site_cd=" + form.site_cd.value
                       + "&ordr_idxx=" + form.ordr_idxx.value
                       + "&good_mny=" + form.good_mny.value
                       + "&pay_method=" + form.pay_method.value
                       + "&escw_used=" + form.escw_used.value
                       + "&good_name=" + form.good_name.value
                       + "&response_type=" + form.response_type.value
                       + "&Ret_URL=" + form.Ret_URL.value;
            sendRequest( url + params );
            */
            ajax_flag = false;
        	var objStr =$("#frm_kcp").serialize();
    		$.ajax({			
    			url: getContextPath()+"/ajax/order/kcpOrderApprovalAjax.do",
    		 	data: objStr,
    		 	type: "post",
    		 	async: false,
    		 	cache: false,
    	      //  dataType : 'json',
    	      //  contentType: "application/json", 
    		 	error: function(request, status, error){ 
    		 		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error); 
					ajax_flag=true;
	                didSubmit = true;
    			},
    			success: function(data){
//    				console.log(data);
    				var json = JSON.parse(data);    

    				if(json.Code == "0000")
    				{
                        var form = document.frm_kcp;
                        StateChangeForJSON( json ); 		                    
		                    
    				}else{
    					if(json.Message != ""){
    						alert(json.Message);
    					}
    					ajax_flag=true;
		                didSubmit = true;
    				}
    			 }
    		});
        }
        else
        {
            alert("통신 중입니다. 잠시만 기다려 주세요.");
        }
            
    }
    
    function StateChangeForJSON( json )
    {
        if( json.Code == '0000' )
        {
        	$("#frm_kcp input:hidden[name='approval_key']").val(json.approvalKey);
        	$("#frm_kcp input:hidden[name='traceNo']").val(json.traceNo);
        	$("#frm_kcp input:hidden[name='approval_key']").val(json.approvalKey);
            PayUrl = json.PayUrl;
            
            //document.getElementById( "PayUrl"  ).value = json.request_URI;
            document.getElementById( "traceNo" ).value = json.traceNo;
            
            call_pay_form();
        }
        else
        {
            ajax_flag=true;
            didSubmit = true;
            alert("실패 되었습니다.[" + json.Message + "]");
        }
    }