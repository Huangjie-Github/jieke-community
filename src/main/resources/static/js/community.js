$(document).ready(function () {

})

/**
 * 展开二级评论
 */
function collapseComments(e) {
    var id = $(e).data("id");
    var sub_comment = $("#comment-"+id);
    sub_comment.toggleClass("in");
    if (sub_comment.hasClass("in")){
        $.getJSON( "comment/"+id, function( data ) {
            console.log(data.data)
            var subCommentContainer = $("#comment-"+id);
            $.each(data.data.reverse(), function(index ,comment) {
                console.log(comment);
                var c = $("<div/>",{
                    "class":"col-lg-12 col-md-12 col-sm-1a2 col-xs-12",
                    html: comment.content+"<hr class=\"col-lg-12 col-md-12 col-sm-1a2 col-xs-12\"/>"
                });
                subCommentContainer.prepend(c);
            });
        });
    }
}

/**
 * 一级评论提交的POST请求
 */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2Target(questionId,1,content);
}
function comment2Target(targetId,type,content) {
    if (!content.trim()){
        alert("前端校验->回复内容不能为空~~");
        return;
    }
    $.ajax({
        type: "POST",
        url: "comment",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200){
                $("#comment_content").val("");
                history.go(0)
            }else if (response.code == 2008){
                alert(response.message)
            }else{
                if (response.code==2002){
                    var isAccepted = confirm(response.message);
                    if (isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=b8785f06838f19e663ce&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true)
                    }
                }else {
                    alert(response.message)
                }
            }
        },
        dataType: "json"
    });
}
function comment(e) {
    var commentId = $(e).data("id");
    var content = $("#input-"+commentId).val();
    comment2Target(commentId,2,content);
}