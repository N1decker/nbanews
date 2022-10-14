$('.send-msg-btn').on('click', function () {
    let id = $(this).attr('id');
    let url = "/news/" + id + "/comments";
    let message = $('#send-message' + id).val();
    $.ajax({
        url: url,
        type: "POST",
        data: {
            message,
            id
        }
    });
    $('#send-message' + id).val("");
    $("#message-list" + id).append("<li class='list-group-item mb-1'>" + message + "</li>");
});

$('.chat').on('click', function () {
    if (this.classList.contains("collapsed")) {
        this.classList.remove("collapsed")
    } else {
        let id = $(this).attr('id');
        let url = "/news/" + id + "/comments";
        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'json',
            contentType: "application/Json; Charset= Utf-8",
            success: function (data) {
                let list = "";
                $.each(data, function (index, item) {
                    list += "<li class='list-group-item mb-1'>" + item.comment + "</li>";
                })
                $("#message-list" + id).html(list);
            }
        });
        this.classList.add("collapsed");
    }
});

function getFileName(inputName) {
    let filename = $('#' + inputName).val();
    const lastIndex = filename.lastIndexOf("\\");
    if (lastIndex >= 0) {
        filename = filename.substring(lastIndex + 1);
    }
    $('#' + inputName + '-label').html(filename);
};

$("#logout-btn").on('click', function(e){
    e.preventDefault();
    $.ajax({
        url : "/logout",
        method : "POST",
        success : function() {
            window.location.href = "/";
        }
    });
});

$("#signIn-btn").on("click", function () {
    $.ajax({
        url: "/login",
        method: "POST",
        data: $('#loginForm').serialize(),
        success : function() {
            window.location.href = "/news";
        }
    }).error(function (res, status){
        debugger;
        console.log(res);
        $('#wrongPass').show()
    });
})

function signIn(email, password) {
    
}