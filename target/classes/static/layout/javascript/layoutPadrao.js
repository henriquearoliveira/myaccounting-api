// Swipe do menu Slide
$(document).ready(function() {

	// on load
	hideDiv();

	// on resize
	$(window).resize(function() {
		hideDiv();
	});

});

function hideDiv() {

	if ($(window).width() < 768) {

		slide();
	}
}

function slide() {
	$("#navBarHide")
			.swiperight(
					function() {
						$("#navBarHide")
								.removeClass('skin-blue sidebar-mini')
								.addClass(
										'skin-blue sidebar-mini sidebar-collapse sidebar-open');
					});
	$("#navBarHide").swipeleft(
			function() {
				$("#navBarHide").removeClass(
						'skin-blue sidebar-mini sidebar-collapse sidebar-open')
						.addClass('skin-blue sidebar-mini');
			});
}

$("a.sidebar-toggle").click(
		function() {
			$("body").removeClass('skin-blue sidebar-mini');
			$("body").toggleClass(
					'skin-blue sidebar-mini sidebar-collapse sidebar-open');
			$("body, a.sidebar-toggle").addClass('show');
		});

$("a.sidebar-toggle.show").click(function() {
	alert("teste");
	$("body").removeClass('loaded skin-blue sidebar-mini show sidebar-open');
	$("body").toggleClass('skin-blue sidebar-mini');
	$("body, a.sidebar-toggle").removeClass('show');
});
/*
 * $(".sidebar-collapse").click(function(){ alert("teste");
 * $(this).removeClass('loaded skin-blue sidebar-mini sidebar-open');
 * $(this).addClass('skin-blue sidebar-mini'); });
 */
/*
 * $("#navBarHide").click(function(){ $(this).removeClass('loaded skin-blue
 * sidebar-mini sidebar-collapse sidebar-open').addClass( 'skin-blue
 * sidebar-mini loaded'); });
 */

$(function() {
	$("#valorConversao").maskMoney({
		prefix : 'R$ ',
		allowNegative : true,
		thousands : '.',
		decimal : ',',
		affixesStay : false
	});
});

// Date picker
/*
 * $('#dataHoraLancamento').datepicker({ format: 'dd/mm/yyyy', forceParse:
 * false, autoclose: true }).datepicker("setDate", new Date());
 */

$("#dataHoraLancamento").datepicker({
	format : 'yyyy-mm-dd',
	forceParse : false,
	autoclose : true
});

$("#dataHoraVencimento").datepicker({
	format : 'yyyy-mm-dd',
	forceParse : false,
	autoclose : true
});

// Month And Year Listagem Lancamento
$('#monthYearListagem').datepicker({
	format : 'MM yyyy',
	startView : "months",
	minViewMode : "months",
	forceParse : false,
	autoclose : true
}).datepicker("setDate", new Date());

function readURL(input) {

	if (input.files && input.files[0]) {
		var reader = new FileReader();

		reader.onload = function(e) {
			$('#myImg').attr('src', e.target.result);
		}

		reader.readAsDataURL(input.files[0]);
	}
}

$("#imgInput").change(function() {
	readURL(this);
	alteraUrl();
});

var imagem = null;

function alteraUrl() {
	imagem = $('#myImg').attr('src');
}

$('#myModal').on('shown.bs.modal', function(e) {
	alteraUrl();
	$('#imgModal').attr('src', imagem);
});

//Add slideDown animation to Bootstrap dropdown when expanding.
/*$('.dropdown').on('show.bs.dropdown', function() {
	$(this).find('.dropdown-menu').first().stop(true, true).slideDown();
});

$('body').on("click", '#teste',function() {
	alert("aer");
});

// Add slideUp animation to Bootstrap dropdown when collapsing.
$('.dropdown').on('hide.bs.dropdown', function() {
  $(this).find('.dropdown-menu').first().stop(true, true).slideUp();
});*/

/*
 * $('#monthYearListagemm').datepicker({ format: 'MM yyyy', startView: "months",
 * minViewMode: "months", forceParse: false, autoclose: true });
 */

