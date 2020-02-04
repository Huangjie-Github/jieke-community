$(document).ready(function () {

})

function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    console.log(questionId)
    console.log(content)
    $.ajax({
        type: "POST",
        url: "comment",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({
            "parentId": questionId,
            "content": content,
            "type": 1
        }),
        success: function (response) {
            if (response.code == 200){
                $("#comment_content").val("");
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