/**
 * 展开二级评论
 * @param e 控件本身
 */
function collapseComments(e) {
    var id = $(e).data("id");
    var sub_comment = $("#comment-" + id);
    sub_comment.toggleClass("in");
    if (sub_comment.hasClass("in")) {
        if (sub_comment.children().length <= 1) {
            $.getJSON("comment/" + id, function (data) {
                console.log(data.data)
                var subCommentContainer = $("#comment-" + id);
                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object list_user_log img-rounded",
                        "src": comment.user.avatarUrl,
                        "alt": "头像"
                    }));
                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h4/>", {
                        "class": "media-heading",
                        "text": comment.user.name
                    })).append($("<div/>", {
                        "text": comment.content
                    })).append($("<span/>", {
                        "class": "pull-right",
                        // "text":new Date(comment.gmtCreate).format('yyyy-MM-dd')
                        "text": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    }));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement).append($("<hr/>", {
                        "class": "col-lg-12 col-md-12 col-sm-1a2 col-xs-12"
                    }));

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-1a2 col-xs-12"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
            });
        }
    }
}

/**
 * 一级评论提交的POST请求
 */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2Target(questionId, 1, content);
}

/**
 * 一二级评论具体实现请求内部
 * @param targetId 评论的父id
 * @param type 评论类型
 * @param content 内容
 */
function comment2Target(targetId, type, content) {
    if (!content.trim()) {
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
            if (response.code == 200) {
                $("#comment_content").val("");
                history.go(0)
            } else if (response.code == 2008) {
                alert(response.message)
            } else {
                if (response.code == 2002) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=b8785f06838f19e663ce&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true)
                    }
                } else {
                    alert(response.message)
                }
            }
        },
        dataType: "json"
    });
}

/**
 * 二级评论POST请求
 * @param e 控件本身
 */
function comment(e) {
    var commentId = $(e).data("id");
    var content = $("#input-" + commentId).val();
    comment2Target(commentId, 2, content);
}