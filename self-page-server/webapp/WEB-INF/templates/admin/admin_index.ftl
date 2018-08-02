<#import "../frame_new.ftl" as main>

<#assign html_other_script in main>
	<script>
	</script>
</#assign>

<@main.page title="首页" currentPage='index' activeTag=1 breadCrumb="">
<style>
	.col-xs-12{
		padding:0;
	}
</style>

<link href="http://vjs.zencdn.net/5.0.2/video-js.css" rel="stylesheet">
<script src="http://vjs.zencdn.net/ie8/1.1.0/videojs-ie8.min.js"></script>
<script src="http://vjs.zencdn.net/5.0.2/video.js"></script>


	<div class="card card-block">
		<h4 class="card-title">图文页</h4>
		<p class="card-text">
			<p><img alt="" style="width:100%" /></p>

			<p>&nbsp;</p>
			
			<p>与心仪的他初次约会可不能草率，要让自己获得美好的开局，打开恋爱的门，要学会把握住细节，这样才能不让爱情悄悄溜走。那么初次约会如何牢牢抓住男人心？</p>
			
			<p>　　<strong>1、充分利用肢体语言</strong></p>
			
			<p>&nbsp; 经验表明，&ldquo;肢体语言&rdquo;常常可以向对方传递更多的信息并表达出更为丰富的情感。如果你表达能力不好，就要运用自己的肢体语言，据测算，在第一次约会时，您留给对方的第一影响有55%取决于外表和&ldquo;肢体语言&rdquo;，有38%取决于讲话的技巧，而只有7%取决于您说话的内容。</p>
			
			<p>　　<strong>2、共同欢笑</strong></p>
			
			<p>　　爱情与欢笑总是相伴而行。在人们传统上认为的那些会使男女间产生爱慕的因素中，幽默感总是占据着非常重要的位置。共同体验欢笑会在陌生人之间营造出一种亲近感，在第一次约会时，试着讲些笑话却是要做到的。</p>
			
			<p>&nbsp;</p>
			
			<p><strong>&nbsp;</strong></p>
			
			<p><strong>&nbsp;3、选择合适音乐的餐厅</strong></p>
			
			<p>　　初次约会，总是有点紧张的，所以要挑选安静一定的餐厅，又温馨的音乐那就更好了！试验表明，轻柔的音乐更容易使女性对男性产生好感，而爵士乐或着完全寂静的环境。</p>
			
			<p>&nbsp;<strong>4、善发短信</strong></p>
			
			<p>　　发短信是现代人重要的联系方式之一，有些情况下使用短信，弊多利少，要尽量避免。</p>
			
			<p>　　（1）、有意见分歧时。恋人或夫妻出现分歧或者矛盾时，短信沟通反而容易产生误解，甚至会使关系进一步恶化。此时最好的方式是当面沟通。</p>
			
			<p>　　（2）、初次约会刚结束。在和男性进行了愉快的初次约会后，你可能很想与他联系，但请不要马上给他发短信。刚刚分开后就发短信，可能减弱男性对你的兴趣度和期待感。</p>
			
			<p>　　（3）、喝醉的时候。喝多的时候可能丧失理智。如果不想让他看到你语无伦次的样子，请在喝醉后关掉手机，不要打电话，更不要一时冲动发短信。</p>
			
			<p>　　（4）、男性觉得难堪后。如果他做了某些觉得丢面子的事，例如认错路、薪水发得少、工作上遇到困难等，请不要马上发短信安慰他。这会让他觉得你记住了他懦弱的一面。此刻最好陪伴在他周围或者换个话题。</p>
			
			<p>&nbsp;</p>
			

			<p>&nbsp;</p>
					
		</p>
	</div>



<script>

	$(".fullScreenButton").click(function(){
		var myPlayer = videojs('example_video_1');
		videojs("example_video_1").ready(function(){
		  myPlayer = this;
		});
		myPlayer.play();
		//myPlayer.requestFullscreen();
	});

</script>

</@main.page>