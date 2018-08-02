<#macro page firstId cate>
<#--使用示例-->
<#--<novel-append-item v-for="(index, item) in items" v-bind:nid="item.id" v-bind:ntitle="item.title" v-bind:time-str="item.timeStr"></novel-append-item>-->
<#--<novel-load-more></novel-load-more>-->
<template id="novel-append-item">
	<a href="/web/article!detail?aid={{nid}}" class="list-group-item">
		<div class="row">
			<div class="col-md-8 list-group-item-heading"><h4>{{ntitle}}</h4></div>
			<div class="col-md-4 list-group-item-text "><h5>{{timeStr}}</h5></div>
		</div>
	</a>
</template>
<template id="novel-load-more">
	<button @click="getMore" class="btn btn-danger btn-lg load-more col-xs-12 col-md-6 col-md-offset-3" style="margin-top:20px" >加载更多<i style="margin-left:20px" class="fa fa-refresh fa-spin hide loading"></i></button>
	<div class="col-xs-12 hide no-more list-group-item" style="text-align:center;" >无更多记录</div>
	<div class="row"></div>
</template>
<script>
	var novelItem = {
		novelComponent: {  //单行列表组件
			template: '#novel-append-item',
			props: [ 'ntitle', 'nid', 'time-str'],
		},
		novelLoadComponent: { //按钮组件
			template: '#novel-load-more',
            methods: {
                getMore: function(){
                    vueVm.getMore();
                }
            },
		},
	}
	Vue.component('novel-append-item', novelItem.novelComponent);

	Vue.component('novel-load-more', novelItem.novelLoadComponent);

    var vueVm = new Vue({  //要调用vue先实例化
        el: '#vueContainer',
        data: {
            items: [],
            page : 1,
            offset: #{firstId}

        },
        methods: {
            addNew: function (id, date, title) {
                var item = {id:id, timeStr:date, title: title};
                this.items.push(item);
            },
            hideButton: function(){
                $(".load-more").hide();
                $(".no-more").removeClass("hide");
            },

            setOffset: function(offset) {
                this.offset = offset;
            },
            getMore: function(){
                if (!$(".loading").hasClass("hide")){
                    return;
                }
                $(".loading").removeClass("hide")
                var vm = this;
                $.ajax({
                    type: 'POST',
                    url: "/web/article!more" ,
                    data: {
                        offset: this.offset,
                        cate: '${cate!}'

                    } ,

                    timeout: 5000,
                    success: function(list){
                        $(".loading").addClass("hide");
                        if(list.length === 0) {
                            vm.hideButton();
                            return;
                        }else if(list.length < 10){
                            vm.hideButton();
                        }
                        for(var i = 0; i < list.length; i++) {
                            if(i == list.length - 1) {
                                vm.setOffset(list[i].id)
                            }
                            vm.addNew(list[i].id, new Date(list[i].createTime).Format("yyyy-MM-dd"), list[i].title);
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
