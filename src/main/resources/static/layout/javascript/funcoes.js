$(function() {
	setTimeout(function() {
		$('#hideComponent').fadeOut('slow', function(){
			$(this).remove();
		});
	}, 5000);
});

/*$("body").on("change", '#hideComponent', function() { 
	alert("teste");
});

*/

/*$(document).ready(*/
$(function() {
	$("#tabela1").DataTable();
	/*$('#example2').DataTable({
		"paging" : true,
		"lengthChange" : false,
		"searching" : false,
		"ordering" : true,
		"info" : true,
		"autoWidth" : false
	});*/
});

// EXCLUIR WITHOUT RELOAD PAGE
/*$(document).ready(function(e) {
	$(".btn-danger").on("click", function(e) {  Não funfa = Forma Padrão.
	$("body").on("click", '.btn-danger', function() {
		var id = $(this).attr("id");
		
		var endereco = "remover/";
		
		// teste
		$.ajax({
			url : endereco + id,
			type : "DELETE",

			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},

			success : function() {

				var tr = $(e.target).closest("tr");
				tr.css("background-color", "#000000");
				tr.fadeIn(1000).fadeOut(200, function() {
					tr.remove();
				})
				
				var li = $(e.target).closest("li");;
				li.css("backgroud-color","#000000");
				li.fadeIn(1000).fadeOut(200,function(){
					li.remove();
				})
				
				$('#bodyteste').append(erroDiv); // VARIÁVEL LOCALIZADA EM OUTRO ARQUIVO -> erroDivMessage.js
			},

			error : function(e) {

				$('#bodyteste').append(erroDiv); // VARIÁVEL LOCALIZADA EM OUTRO ARQUIVO -> erroDivMessage.js

			}
		});
	});
});*/
/*var tr = null;
var li = null;
$(document).ready(function(e) {
	$(".btn-danger").on("click", function(e){
			tr = $(e.target).closest("tr");
			li = $(e.target).closest("li");;
		});
});*/

$(document).ready(function(e) {
	$("#botaoExcluirRegistro").on("click", function(e) {

		var id = $('.idValueModal').val();
		var liSelect = 'li' + id; // PEGA O VALOR QUE TÁ NO ID DE CADA LINHA
		var trSelect = 'tr' + id;
		
		var li = $("#"+liSelect); // ATRIBUI O SELETOR PARA UMA VARIÁVEL
		
		var tr = $("#"+trSelect);
		
		var endereco = "remover/";
		
		$.ajax({
			url : endereco + id,
			type : "DELETE",

			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
			},

			success : function() {

				tr.css("background-color", "#000000");
				tr.fadeIn(1000).fadeOut(500, function() {
					tr.remove();
				})
				
				li.css("backgroud-color","#000000");
				li.fadeIn(1000).fadeOut(500,function(){
					li.remove();
				})
				
				$('#bodyteste').append(erroDiv); // VARIÁVEL LOCALIZADA EM OUTRO ARQUIVO -> erroDivMessage.js
				
				$(function() {
					setTimeout(function() {
						$('#hideComponent').fadeOut('slow');
					}, 5000);
				});
			},

			error : function(e) {

				$('#bodyteste').append(erroDiv); // VARIÁVEL LOCALIZADA EM OUTRO ARQUIVO -> erroDivMessage.js

			}
		});
	});
});

$('#confirmaExclusaoModal').on('show.bs.modal', function (event) {
	var botao = $(event.relatedTarget);
	var id = botao.attr("id")
	/*var modal = $(this);*/
	$('.idValueModal').val(id);
});

$('#confirmaLancamentoOuDeposito').on('show.bs.modal', function () {
	var valorData = $('#inputMonthYear').val();
	/*var modal = $(this);*/
	$('.valueModal').val(valorData);
	
});

$('#formProximoMes').on('submit', function(e){
	
	var endereco = "saldo";
	
	var form = this;
	
	var saldo = null;
	
	e.preventDefault(e);
	
	$.ajax({
		
		url : endereco,
		type : "GET",

		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},

		success : function(data) {
			
			saldo = data;
			
			verificaValorDeposito(saldo, form);
		},

		error : function(e) {

			$('#bodyteste').append(erroInsersao); // VARIÁVEL LOCALIZADA EM OUTRO ARQUIVO -> erroDivMessage.js

		}
	});
	
});

function verificaValorDeposito(saldo, form){
	
	var valorDepositoDigitado = $('#valorConversao').val().replace(/\./g, '').replace(/\,/g, '.');
	
	var valorDigitado = Number(valorDepositoDigitado);
	
	var idValorError = $('#idContaSelecao');
	var idContaError = $('#idContaModal');
	
	var tipoDeOpcao = $('#opcao').val(); 
	var contaSelecionada = $('#conta').val();
	
	if(verificaTipoDeOpcaoSelecionada(tipoDeOpcao, contaSelecionada, idContaError)){
		return;
	}
	
	if(verificaValor(idValorError, saldo, valorDigitado)){
		return;
	}
	
	form.submit();
	
}

function verificaValor(idValorError, saldo, valorDigitado) {
	
	if (valorDigitado <= 0) {
		
		configuraError(idValorError, valorDigitadoNegativo)
		
		return true;
		
	} else if (saldo < valorDigitado) {
		
		configuraError(idValorError, valorDigitadoIncorreto)
		
		return true;
		
	}
	
	return false;
	
}

function verificaTipoDeOpcaoSelecionada(tipoDeOpcao, contaSelecionada, idContaError) {
	
	if (tipoDeOpcao != '' && tipoDeOpcao == 'DEPOSITO_CONTA' && contaSelecionada == '') {
		configuraError(idContaError, contaNaoSelecionada)
		return true;
	}
	
	return false;
	
}

function configuraError(idContaError, tipoDeErro) {
	$('#valorDigitadoIncorretoError').append(tipoDeErro);
	idContaError.addClass('has-error');
	
	setTimeout(function() {
		idContaError.removeClass('has-error');
		$('#hideComponent').fadeOut('slow', function(){
			$(this).remove();
		});
	}, 3000);
}

/*$('#conta').on('change', function(){
	alert('teste');
});*/

/*$('body').on('appened', '#hideComponent', function(){
	alert('teste');
});*/

/* FAZ O PROCESSO DINAMICO DOS LANCAMENTOS, CARREGAMENTO DA TABELA */

var contaOptionsDeposito = null;
var contaTextDeposito = null;

$(document).ready(function(){
	
	var options = $('#conta option');

	contaOptionsDeposito = $.map(options ,function(option) {
	    return option.value;
	});
	
	contaTextDeposito = $.map(options ,function(option) {
	    return option.text;
	});
});

var conta = null;

$('#idConta').on("change", function(e){
	
	conta = this.value;
	var date = $('#inputMonthYear').val();
	
	if (conta == '' || date == ''){
		return;
	}
	
	deletaTabelaSeNecessário();
	enviaLancamentos(date, conta);
	
});

$('#inputMonthYear').on("change", function(e){
	
	var date = this.value;
	var conta = $('#idConta').val();
	
	if (conta == '' || date == ''){
		return;
	}
	
	deletaTabelaSeNecessário();
	enviaLancamentos(date, conta);
	
}); // .change() FAZ ELE ENVIAR A AÇÃO

function enviaLancamentos(date, conta){
	
	
	var urlTabela = '/lancamento/tabela';
	
	if($('#inputMonthYear').val() != ''){
		urlTabela = urlTabela + '?date=' + encodeURIComponent(date) + '&conta=' + conta;
	}
	
	$("#ajax-loading").html(" " +
			" <div class='row'>" +
			" 	<div class='col-xs-12'>" +
			" 		<img src='/layout/imgs/loading-gif/spinner-loading.gif' alt='loading...' />" +
			" 	</div>" +
			" </div");
	
	/*$.ajax({ DO THE SAME THING
	    url: urlTabela,
	    type: 'POST',
	    success: function(data){
	        $(data).find('#tabelaBlock').appendTo('#tabelaBlock');
	    },
	    error: function(data) {
	        alert('woops!'); //or whatever
	    }
	});*/
	
	$("#tabelaBlock").load(urlTabela, {limit: 25}, function(responseText, textStatus, req){
		/*$.getScript("https://code.jquery.com/jquery-2.2.3.min.js");
		$.getScript("/layout/javascript/funcoes.js");*/
		// REMOVE O LOADING ASSIM QUE TERMINAR DE CARREGAR A(S) TABELA(S)
		
		if (textStatus == "error") {
			
			if(!$('#hideComponent').is(':visible')) {
				$('#bodyteste').append(parametrosIncorretos);
			}
			
			setTimeout(function() {
				$('#hideComponent').fadeOut('slow', function(){ // DENTRO DA MENSAGEM QUE APARECE NO MODAL
					$(this).remove();
				});
			}, 3000);
		}
		
		configuraComboConta(conta);
		
		$("#ajax-loading").remove();
		$('#ajaxLoadReferencia').html("<div id='ajax-loading' class='text-center'>" +
							"</div>");
	});
	
	$("#tabelaBlockMobile").load(urlTabela + '&mobile=mobile', {limit: 25}, function(){
		
		configuraApariçãoDaOpcaoDepositoConta(conta);

		$("#ajax-loading").remove();
		$('#ajaxLoadReferencia').html("<div id='ajax-loading' class='text-center'>" +
		"</div>");
	});
	
	// TREINAMENTO.. RSRS
	/*$jq.when(
			$jq.getScript('/layout/javascript/jquery-2.2.3.min.js'),
			$jq.getScript('https://code.jquery.com/ui/1.11.4/jquery-ui.min.js'),
			$jq.getScript('/layout/bootstrap/js/bootstrap.min.js'),
			$jq.getScript('/layout/plugins/datatables/jquery.dataTables.min.js'),
			$jq.getScript('/layout/plugins/datatables/dataTables.bootstrap.min.js'),
		    $jq.Deferred(function( deferred ){
		    	$jq( deferred.resolve );
		    })
		).done(function(){

			$jq("#tabelaBlock").load(urlTeste);

		});
	
	
	$.ajax({
        
		type: "GET",
        url: urlTeste,
        datatype : "html",
        success: function(html) {
            console.log(html);
            alert('ddd');
             $("#tabelaBlock").load(html);
        },
		error : function(e) {
	
			alert('sdf');
	
		}
    });
	
	var urlSecond = '/lancamento/tabela';
	
	if($('#inputMonthYear').val() != ''){
		urlSecond = urlSecond + '/' + encodeURIComponent($('#inputMonthYear').val());
	}
	
	$.ajax({
	    url: '/lancamento/Tabela.html',
	    dataType: 'html',
	    success: function(html) {
	        var div = $('#tabelaBlock', $(html)).addClass('done');
	        $('#targetDiv').html(div);
	    }
	});*/
}

function deletaTabelaSeNecessário(){
	
	var tabelaCompleta = $('#idTabelaCompleta');
	
	var tabelaMobile = $('#idTabelaMobile');

	deletaTabelas(tabelaCompleta);
	deletaTabelas(tabelaMobile);

}

function deletaTabelas(tabela){
	
	if (tabela != null || tabela != ''){
		
		tabela.detach();
		
	}
}

function configuraApariçãoDaOpcaoDepositoConta(conta) {
	
	if (conta == 0){
		$("#idDeposito").remove();
	}
	
}

function configuraComboConta(conta){
	
	/*var options = $('#conta option');

	var contaOptionsDeposito = $.map(options ,function(option) {
	    return option.value;
	});
	
	var contaTextDeposito = $.map(options ,function(option) {
	    return option.text;
	});*/
	
	$('#conta')
    	.find('option')
    	.remove();
	
	var contaOptionsDepositoClone = contaOptionsDeposito.slice(0);
	var contaTextDepositoClone = contaTextDeposito.slice(0);
	
	$.each(contaOptionsDepositoClone, function (index, value) {
		
		$('#conta').append($('<option>', {
		    value: value,
		    text: contaTextDepositoClone[index]
		}));;
		  
	});

	/*var i;
	
	console.log(contaOptionsDepositoClone);
	
	for (i = 0; i < contaOptionsDepositoClone.lenght; i++){
		console.log(contaOptionsDepositoClone[i]);
	}*/
	
	/*$('#conta').append($('<option>', {
	    value: 1,
	    text: 'My option'
	}));*/

	contaOptionsDepositoClone = jQuery.grep(contaOptionsDepositoClone, function(value) {
		return value != conta;
	});
	
	/*contaTextDepositoClone = jQuery.grep(contaTextDepositoClone, function(value) {
		return value != 'TODAS';
	});*/
	
	$("#conta option[value='"+0+"']").each(function() {
	    $(this).remove();
	});
	
	$("#conta option[value='"+conta+"']").each(function() {
	    $(this).remove();
	});
	
}

/* FAZ O PROCESSO DINAMICO, CARREGAMENTO DAS TABELAS VENCIDAS */
$('#inputDateVencidos').on("change", function(){
	
	var date = this.value;
	var conta = $('#idContaVencidos').val();
	
	if (conta == '' || date == ''){
		return;
	}
	
	deletaTabelaSeNecessário();
	enviaVencidos(date, conta);
	
});

$('#idContaVencidos').on("change", function(e){
	
	var contaVencida = this.value;
	exibeDatas(contaVencida);
	
});

function exibeDatas(contaVencida) {
	
	if (contaVencida == null || contaVencida == '')
		return;
	
	var endereco = "vencidosComConta";
	
	$.ajax({
		
		url : endereco+'?conta='+contaVencida,
		type : "GET",

		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},

		success : function(data) {
			
			preencheDatasAndExibeContas(data, contaVencida);
		},

		error : function(e) {

			$('#bodyteste').append(erroInsersao); // VARIÁVEL LOCALIZADA EM OUTRO ARQUIVO -> erroDivMessage.js

		}
	});
	
}

function preencheDatasAndExibeContas(datesVencidas, contaVencida){
	
	if (datesVencidas == '') {
		
		$('#inputDateVencidos')
			.find('option')
			.remove();
		
		$('#idDateVencidos').hide('slow');
		$('#idContaVencidas').removeClass();
		$('#idContaVencidas').addClass('col-sm-12 col-md-offset-2 col-md-8');
		deletaTabelaSeNecessário();

		return;
	}
	
	$('#idDateVencidos').show('slow');
	
	$('#idContaVencidas').removeClass(); // SE UMA HORA NECESSÁRIO TER UM EFEITO DE FADE .fadeOut(0).fadeIn(1000);
	$('#idContaVencidas').addClass('col-sm-12 col-md-offset-2 col-md-4');
	
	$('#inputDateVencidos')
		.find('option')
		.remove();

	$.each(datesVencidas, function (index, value) {
		
		var valueOption = formataDateValue(value);
		var valueText = formataDateText(value);
		
		
		$('#inputDateVencidos').append($('<option>', {
		    value: valueOption,
		    text: valueText
		}));;
		  
	});
	
	var date = $('#inputDateVencidos').val();

	console.log(date);
	if (contaVencida == '' || date == null || date == ''){
		return;
	}
	
	deletaTabelaSeNecessário();
	
	enviaVencidos(date, contaVencida);
	
}

function formataDateValue(value) {
	
	var valorFormatado = value.toString().replace(/\,/g, '/');
	
	var dateValueFormatada = new Date(valorFormatado);
	
	var mes = ('0' + (dateValueFormatada.getMonth()+1)).slice(-2);
	
	return dateValueFormatada.getFullYear() + '-' + mes + '-' + dateValueFormatada.getDate();

}

function formataDateText(value) {
	
	var valorFormatado = value.toString().replace(/\,/g, '/');
	
	var dateFormatada = new Date(valorFormatado);
	
	var mes = ('0' + (dateFormatada.getMonth()+1)).slice(-2); // MENOS DOIS PRA PULAR DE TRAS PRA FRENTE
															  // ASSIM O MES 9 FICA 09 E O MES 10 CONTINUA 10.
	return dateFormatada.getDate() + '/' + mes + '/' + dateFormatada.getFullYear();
	
	
}

function enviaVencidos(date, conta){
	
	var urlTabela = '/lancamento/tabelaVencidos';
	
	if($('#inputDateVencidos').val() != ''){
		urlTabela = urlTabela + '?dataVencido=' + encodeURIComponent(date) + '&conta=' + conta;
	}
	
	$("#ajax-loading").html(" <img src='/layout/imgs/loading-gif/spinner-loading5.gif' alt='loading...' /> ");
	
	$("#tabelaVencidos").load(urlTabela, function(){
		
		// REMOVE O LOADING ASSIM QUE TERMINAR DE CARREGAR A(S) TABELA(S)
		$("#ajax-loading").remove();
	});
	
	$("#tabelaVencidosMobile").load(urlTabela + '&mobile=mobile', function(){
		$("#ajax-loading").remove();
	});
}





/* DECIDE SE NECESSÁRIO EXIBIR IS_PAGO -------- MOVIDO PARA ARQUIVO SEPARADO */
/*$(document).ready(function() {
	
	var idCategoria = $("#cat").val();
	
	desabilitaChecked(idCategoria);
	
});

$("#cat").on("change",function(){
	
	var idCategoria = $(this).val();
	
	desabilitaChecked(idCategoria);

});

function desabilitaChecked(idCategoria) {
	
	var endereco = "/categoria/json/";
	
	$.ajax({
		url : endereco + idCategoria,
		type : "GET",

		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json"); TIREI DEVIDO ESTAR RETORNANDO APENAS UMA STRING
		},

		success : function(data) {

			checked(data);
			
		},

		error : function(e) {

			$('#bodyteste').append(erroDiv); // VARIÁVEL LOCALIZADA EM OUTRO ARQUIVO -> erroDivMessage.js

		}
		
	});
}

function checked(resposta){
	
	alert("aqui");
	console.log(resposta);
	
	if (resposta == 'DESPESA') {
		$('#valorPago').show('slow');
	} else {
		$('#valorPago').hide('slow');
	}
}*/

/*function getValueSelected () {
	
	var valor = ('#conta : selected').text();
	
	alert(valor);
}*/

/*$(function(){
	var teste = $('#inputMonthYear').val();
	
		
		alert(teste);
		
});


function sendData() {

	alert('teste');
	
	$('#tabela1 .btn').click(function(){ // var id = $(this).attr("id");
	
	alert('haha');

});

var tr = $('#testee').closest("tr"); tr.css("background-color", "#000000");
tr.fadeIn(1000).fadeOut(200, function() { tr.remove(); }); };*/


