$(".answer-write input[type=submit]").click(addAnswer);
function addAnswer(e) {
	e.preventDefault();
	console.log("click!!");

	var queryString = $(".answer-write").serialize();
	console.log("query : " + queryString);
	
	var url = $(".answer-write").attr("action");
	console.log("url : " + url);
	
	$.ajax({
		type : 'post',
		url : url,
		date : queryString,
		dataType : 'json',
		error : onError,
		success : onSuccess});

}

function onError() {

}
function onSuccess(data, status) {
	console.log(data);
	var answerTemplate = $("#answerTemplate").html();
	var template = answerTemplate.format(data.writer.id, data.formattedCreateDate, data.contents, data.question.userId, data.userId);
	$(".qna-comment-slipp-articles").prepend(template);
	$("textarea[name=contents]").val("");
}

$(".link-delete-article").click(deleteAnswer);
function deleteAnswer(e){
	e.preventDefault();
	var url = $(this).attr("href");
	console.log("url : "+url);
	
	$.ajax({
		type : 'delete',
		url : url,
		dateType : 'json',
		error : function(xhr, status){
			console.log("error");
		},
		success : function(data,status){
			console.log(data);
		}
	});
}

String.prototype.format = function() {
	 var args = arguments;
	 return this.replace(/{(\d+)}/g, function(match, number) {
	  return typeof args[number] != 'undefined' ? args[number] : match;
	 });
	};