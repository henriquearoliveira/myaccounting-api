$(function(){
	setTimeout(function(){
		$('#senhaIncorreta').fadeOut('slow');
	}, 5000);
});

$(function(){
	setTimeout(function(){
		$('#hideComponent').fadeOut('slow');
	}, 5000);
});


/*setInterval(function() {
	$.ajax({
	    type: 'get',
	    url: 'https://myaccounting.herokuapp.com/login',
	    context: this,
	    success: this.mySuccess,
	    error: this.myError,
	    cache: false,
	});
}, 300000);*/

/*setInterval(function(){
	alert("teste");
}, 10000);*/

/*setInterval(function(request){
	  request({
	      url: "http://localhost:8080/log",
	      method: "GET",
	      timeout: 600,
	      followRedirect: true,
	      maxRedirects: 10
	  });
	}, 600);*/


/*var $loginMsg = $('.loginMsg'),
  $login = $('.login'),
  $signupMsg = $('.signupMsg'),
  $signup = $('.signup'),
  $frontbox = $('.frontbox');

$('#switch1').on('click', function() {
  $loginMsg.toggleClass("visibility");
  $frontbox.addClass("moving");
  $signupMsg.toggleClass("visibility");

  $signup.toggleClass('hide');
  $login.toggleClass('hide');
});

$('#switch2').on('click', function() {
  $loginMsg.toggleClass("visibility");
  $frontbox.removeClass("moving");
  $signupMsg.toggleClass("visibility");

  $signup.toggleClass('hide');
  $login.toggleClass('hide');
});*/

/*
ou
setTimeout(function(){
	alert("Teste");
	$('#senhaIncorreta').fadeOut('slow');}, 5000);
*/


// EVITA TRANSIÇAO AUTOMÁTICA

/*
setTimeout(function(){
  $('#switch1').click()
},1000)

setTimeout(function(){
  $('#switch2').click()
},3000)*/
