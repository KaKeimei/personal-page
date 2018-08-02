var defaultUserImg = "";
var kfUserImg = "";
var checkerTimer;

var currentSessionCount = 0;

//会话历史弹出显示
function showHistoryDetail(sessionId) {
	$.ajax({
	  url: "session_chatlog?sessionId=" + sessionId,
	  cache:false,
	  success: function(json){
	  	$("#chat_history_view").html("");
	  	appendMsgToContainer("#chat_history_view", json.msgList);
	    
	    $("#historyDetailDlg").modal("show");
	  },
	  error: function(json){
		  bootbox.alert("获取会话历史记录失败，请重试。");
	  },
	  dataType: "json"
	});
}

function appendMsgToContainer(containerId, msgList) {
	for(var i=0; i < msgList.length; i++) {
    	var cls = "chat-primary";
    	var imgSrc = defaultUserImg;
    	if (msgList[i].isReply) {
    		cls = "me";
    		imgSrc = kfUserImg;
    	} else if (msgList[i].imgUrl && msgList[i].imgUrl != null) {
    		imgSrc = msgList[i].imgUrl;
    	}
    	$(containerId).append("<div class='chat-message " + cls + "'>"
								 + "<div class='chat-contact'><img src='" + imgSrc + "' alt=''></div><div class='chat-text'>"
								 + "<div><small>" + msgList[i].name + " (" + msgList[i].time + "): </small></div>"
								 + msgList[i].content + "</div></div>");
    }
}

function appendMsg(msgList) {
	appendMsgToContainer("#chat", msgList);
    
    $("img", $(".chat-message")).load(function(){	//等图片加载完成滚动到底
	    $("#chat").scrollTop($("#chat")[0].scrollHeight);
	});  
}

function refreshChatUserList(userList) {
	var unreadmsg = 0;
	currentSessionCount = userList.length;
	$("#user_count").html(currentSessionCount);
    for(var i=0; i < userList.length; i++) {
    	var chater = userList[i];
    	var obj = document.getElementById("li_session_" + chater.sessionId);
    	
    	var bg = "";
    	var href = "changeSession(" + chater.sessionId + ")";
    	var spanHtml = chater.clientName;
    	if (chater.isCurrent) {
    		href = "void(0)";
    		bg = "style='background:#eee'";
    	} else if (chater.unreadCount > 0) {
    		unreadmsg += chater.unreadCount;
    		spanHtml = spanHtml + "<label class='badge badge-primary'>" + chater.unreadCount + "</label>";
    	}
    	//console.info(spanHtml);
    	if (obj) {
    		$("span", obj).html(spanHtml);
    	} else {
    		var imgSrc = defaultUserImg;
    		if (chater.imgUrl && chater.imgUrl != null) {
	    		imgSrc = chater.imgUrl;
	    	}
    		$("#chat_user_list").append("<li data-stats='online' id='li_session_" + chater.sessionId 
    								+ "'><a href='javascript:" + href + ";' " 
    								+ bg + "><img src='" + imgSrc + "'><span>" 
    								+ spanHtml + "</span></a></li>");
    	}
    }
    var disabled = userList.length==0;
    $("#group_info_btn").attr("disabled", disabled);
    $("#transfer_session_btn").attr("disabled", disabled);
    $("#close_session_btn").attr("disabled", disabled);
    $("#send_btn").attr("disabled", disabled);
    $("#quick_reply_btn").attr("disabled", disabled);
    $("#msg_input").attr("disabled", disabled);
    
    return unreadmsg;
}

function refreshQueueUserList(queueList) {
	$("#queue_count").html(queueList.length);
	$("#queue_user_list").html("");
    for(var i=0; i < queueList.length; i++) {
    	var chater = queueList[i];
    	var imgSrc = defaultUserImg;
		if (chater.imgUrl && chater.imgUrl != null) {
    		imgSrc = chater.imgUrl;
    	}
		$("#queue_user_list").append("<li data-stats='busy'><a href='javascript:;'><img src='"
								+ imgSrc + "'><span>" + chater.clientName + "</span></a></li>");
    }
}

function setFieldValue(fieldName, value) {
	if (value && value != null) {
		$(fieldName).html(value);
	} else {
		$(fieldName).html("--");
	}
}

function initByJson(json) {
	
  	$("#chat").html("");
  	$("#chat_user_list").html("");
  	
  	$.get("chat_history",function(result){
  		$("#chat_history").html(result);
	});
  	
  	lastUnreadCount = refreshChatUserList(json.chatUserList);
    refreshQueueUserList(json.queueUserList);
    appendMsg(json.msgList);
    
    //set user info
    if (json.userInfo && json.userInfo != null) { 
    	var uinfo = json.userInfo.user;
    	setFieldValue("#user_nick", uinfo.nick);
    	setFieldValue("#user_wxid", uinfo.userId);
    	setFieldValue("#user_remark", uinfo.remark);
    	
    	if (uinfo.gender && uinfo.gender != null) {
    		if (uinfo.gender == 1) { $("#user_gender").html("男"); }
    		else { $("#user_gender").html("女"); }
    	} else {
    		$("#user_gender").html("--");
    	}
    	$("#user_area").html(uinfo.province + " " + uinfo.city);
    	setFieldValue("#user_bindid", uinfo.bindId);
    	
    	var extStr = "";
    	var extendInfo = json.userInfo.extendInfo;
    	if (extendInfo && extendInfo != null) {
    		if (extendInfo.customerName && extendInfo.customerName!=null){
    			extStr+="<b>客户名称: </b>" + extendInfo.customerName + "<br>";
    		}
    		if (extendInfo.email && extendInfo.email!=null){
    			extStr+="<b>Email: </b>" + extendInfo.email + "<br>";
    		}
    		if (extendInfo.mobile && extendInfo.mobile!=null){
    			extStr+="<b>手机: </b>" + extendInfo.mobile + "<br>";
    		}
    		if (extendInfo.birth && extendInfo.birth!=null){
    			extStr+="<b>生日: </b>" + extendInfo.birth + "<br>";
    		}
    		if (extendInfo.registerDate && extendInfo.registerDate!=null){
    			extStr+="<b>注册日期: </b>" + extendInfo.registerDate + "<br>";
    		}
    		if (extendInfo.customerGroup && extendInfo.customerGroup!=null){
    			extStr+="<b>客户组: </b>" + extendInfo.customerGroup + "<br>";
    		}
    	}
    	if (extStr=="") extStr="无";
		setFieldValue("#user_extend", extStr);
    	
    	var groupStr = null;
    	if (json.userInfo.groups && json.userInfo.groups.length > 0) {
    		groupStr = "";
    		for(var i=0; i < json.userInfo.groups.length; i++) {
    			groupStr += json.userInfo.groups[i] + "<br>";
    		}
    	}
    	setFieldValue("#user_group", groupStr);
    	
    	if (uinfo.headImg && uinfo.headImg != null) {
    		$("#user_head").attr("src", uinfo.headImg);
    	} else {
    		$("#user_head").attr("src", defaultUserImg);
    	}
    } else {
    	if (json.chatUserList.length==0) {
    		setFieldValue("#user_nick", "[没有用户]");
    	} else {
    		setFieldValue("#user_nick", "[未知名称]");
    	}
    	setFieldValue("#user_wxid", null);
    	setFieldValue("#user_remark", null);
    	setFieldValue("#user_gender", null);
    	setFieldValue("#user_area", null);
    	setFieldValue("#user_bindid", null);
    	
    	setFieldValue("#user_group", null);
    	setFieldValue("#user_extend", "无");
    	
    	$("#user_head").attr("src", defaultUserImg);
    }
}

function changeSession(sessionId) {
	//alert(sessionId);
	$.ajax({
	  url: "chat_change?sessionId=" + sessionId,
	  cache:false,
	  success: function(json){
	  	initByJson(json);
	  },
	  dataType: "json"
	});
}

function setReceptionAway(away) {
	$.ajax({
	  url: "reception_status?away=" + away,
	  cache:false,
	  success: function(html){
		  if (away) {
			  $("#away_hint").show();
		  } else {
			  $("#away_hint").hide();
		  }
	  },
	  dataType: "html"
	});
}

function msgChecker(){ 
	if (currentSessionCount == 0) {	//如果没有会话了，则需要重新刷新
		refreshData(true);
	} else {
	   $.ajax({
		  url: "chat_msg?delta=true",
		  cache:false,
		  success: function(json){
			$("#offline_hint").hide();
			var msgcount = json.msgList.length;
			if (msgcount > 0 && json.msgList[msgcount -1].isReply) {
				msgcount = 0	//filter myself new msg
			}
		    appendMsg(json.msgList);
		    var unreadcount = refreshChatUserList(json.chatUserList);
		    refreshQueueUserList(json.queueUserList);
		    showNotify(true, msgcount, unreadcount);	//alert title & sound
		  },
		  error: function(json){
			  //process error
			  $("#offline_hint").show();
		  },
		  dataType: "json"
		});
	}
} 

//for close session & transfer
function refreshData(doNotify) {	
	// get all msg
	$.ajax({
	  url: "chat_msg?delta=false",
	  cache:false,
	  success: function(json){
	  	initByJson(json);
	  	if (doNotify && currentSessionCount > 0) {	//show notify at first time when session count is 0
	  		showNotify(true, currentSessionCount, 0);
	  	}
	  },
	  error: function(json){
		  //process error
		  $("#offline_hint").show();
	  },
	  dataType: "json"
	});
}

function sendImg() {
   $("#send_pic_form").submit();
   $('#send_pic_btn').replaceWith('<input name="img" type="file" id="send_pic_btn" onchange="sendImg();" />');
}

function sendQuickReply(btn) {
	$('#msg_input').val($(btn).attr("title"));
}

(function($) {
	$.extend({
		/**
		 * 调用方法： var timerArr = $.blinkTitle.show();
		 * $.blinkTitle.clear(timerArr);
		 */
		blinkTitle : {
			show : function() { // 有新消息时在title处闪烁提示
				var step = 0, _title = document.title;
				var timer = setInterval(function() {
					step++;
					if (step == 3) { step = 1; }
					if (step == 1) { document.title = '【　　　】' + _title; }
					if (step == 2) { document.title = '【新消息】' + _title; }
				}, 500);
				return [ timer, _title ];
			},
			/**
			 * @param timerArr[0],
			 *            timer标记
			 * @param timerArr[1],
			 *            初始的title文本内容
			 */
			clear : function(timerArr) { // 去除闪烁提示，恢复初始title文本
				if (timerArr) {
					clearInterval(timerArr[0]);
					document.title = timerArr[1];
				}
			}
		}
	});
})(jQuery);

var timerArr = null;
var lastUnreadCount = 0;
function showNotify(start, msgCount, unreadCount) {
	console.log("show notify: " + start + ", msgCount: " + msgCount + ", lastUnreadCount:" + lastUnreadCount);
	if (start && (msgCount > 0 || unreadCount != lastUnreadCount)){
		lastUnreadCount = unreadCount;
		window.focus();
		if (timerArr == null) timerArr = $.blinkTitle.show();
		if (bgSound) bgSound.play();
	}
	if (!start && timerArr != null) {
		$.blinkTitle.clear(timerArr);
		timerArr = null;
	}
}
		
$(function () {

	refreshData();
	
	checkerTimer = setInterval("msgChecker()",3000); 
	
	//send msg
	$("#send_btn").click(function() {
		var txt = $("#msg_input").val();
		if (!txt || txt =="") return;
		
		$("#send_btn").attr("disabled",true);
		$("#msg_input").attr("disabled",true);
		
		$.post("chat_send",{msg:txt},function(result){
		    if ("0" == result) {
		    	$("#msg_input").val("");
		    	msgChecker();
		    } else {
		    	var txt = "消息发送失败，";
		    	if ("-1" == result) {
		    		txt += "微信服务器繁忙导致连接异常，请稍候再试";
			    } else if ("40001" == result) {
			    	txt += "access_token无效，请联系管理员进行重置";
			    } else if ("43004" == result) {
			    	txt += "对方已经取消关注";
			    } else if ("45015" == result) {
			    	txt += "回复时间超过限制，该用户可能超过48小时未活跃";
			    } else if ("46004" == result) {
			    	txt += "该用户不存在";
			    } else if ("48001" == result) {
			    	txt += "api功能未授权";
			    } else {
			    	txt += "错误码：" + code;
			    }
		    	bootbox.alert(txt);
			}
		    $("#send_btn").attr("disabled",false);
		    $("#msg_input").attr("disabled",false);
		});
	});
	
	$("#msg_input").bind('keyup', function(event){
	   if (event.keyCode=="13"){
	    	$("#send_btn").click();
	   }
	});
	
	$("body").click(function() {
		showNotify(false);
	});
});