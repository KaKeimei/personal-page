<#macro page>
<#--使用示例-->
<#--<gif-append-item v-for="(index, item) in items" v-bind:gid="item.id" v-bind:time-str="item.timeStr"></gif-append-item>-->
<#--<gif-load-more></gif-load-more>-->
<template id="gif-append-item">
	<a href="/web/evilItem?seriesId={{gid}}" class="list-group-item">
		<div class="row">
			<div class="col-md-8 list-group-item-heading"><h4>邪恶动态图第{{gid}}期</h4></div>
			<div class="col-md-4 list-group-item-text "><h5>{{timeStr}}</h5></div>
		</div>
	</a>
</template>
<template id="gif-load-more">
	<button @click="getMore" class="btn btn-danger btn-lg load-more col-xs-12 col-md-6 col-md-offset-3" style="margin-top:20px" >加载更多<i style="margin-left:20px" class="fa fa-refresh fa-spin hide loading"></i></button>
	<div class="col-xs-12 hide no-more list-group-item" style="text-align:center;" >无更多记录</div>
	<div class="row"></div>
</template>
<script>
	var gifItem = {
		gifComponent: {  //单行列表组件
			template: '#gif-append-item',
			props: [ 'gid', 'time-str'],
		},
		gifLoadComponent: { //按钮组件
			template: '#gif-load-more',
            methods: {
                getMore: function(){
                    vueVm.getMore();
                }
            },
		},
	}
	Vue.component('gif-append-item', gifItem.gifComponent);

	Vue.component('gif-load-more', gifItem.gifLoadComponent);

    var vueVm = new Vue({  //要调用vue先实例化
        el: '#vueContainer',
        data: {
            items: [],
            page : 1,
        },
        methods: {
            addNew: function (id, date) {
                var item = {id:id, timeStr:date};
                this.items.push(item);
            },
            hideButton: function(){
                $(".load-more").hide();
                $(".no-more").removeClass("hide");
            },
            addPage: function(){
                this.page = this.page + 1;
            },
            getMore: function(){
                if (!$(".loading").hasClass("hide")){
                    return;
                }
                $(".loading").removeClass("hide")
                var vm = this;
                vm.addPage();
                $.ajax({
                    type: 'POST',
                    url: "evilGif!more" ,
                    data: {page: this.page} ,
                    timeout: 5000,
                    success: function(list){
                        $(".loading").addClass("hide");
                        if(list.length == 0) {
                            vm.hideButton();
                            return;
                        }
                        for(var i = 0;i < list.length; i++) {
                            vm.addNew(list[i].id, new Date(list[i].createTime).Format("yyyy-MM-dd"));
                        }

                    } ,
                    error: function(){
						$(".loading").addClass("hide");
                    },
                    dataType: "json"
                });
            }

        }
    })

	Date.prototype.Format = function (fmt) { //author: meizz
		var o = {
			"M+": this.getMonth() + 1, //月份
			"d+": this.getDate(), //日
			"h+": this.getHours(), //小时
			"m+": this.getMinutes(), //分
			"s+": this.getSeconds(), //秒
			"q+": Math.floor((this.getMonth() + 3) / 3), //季度
			"S": this.getMilliseconds() //毫秒
		};
		if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		for (var k in o)
			if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		return fmt;
	}
</script>

</#macro>
