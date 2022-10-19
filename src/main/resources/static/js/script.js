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

$("#logout-btn").on('click', function (e) {
    e.preventDefault();
    $.ajax({
        url: "/logout",
        method: "POST",
        success: function () {
            window.location.href = "/";
        }
    });
});

$("#signIn-btn").on("click", function () {
    let email = $('#username').val();
    let password = $('#password').val();
    signIn(email, password);
})

function signIn(email, password) {
    $.ajax({
        url: "/login",
        method: "POST",
        data: {
            'username': email,
            'password': password
        },
        success: function () {
            window.location.href = "/news";
        }
    });
}

$('.like, .dislike').on('click', function () {
    let clickedBtn = $(this).children();
    let oppositeBtn = clickedBtn.hasClass('bi-hand-thumbs-up-fill') ? $(this).parent().find('.dislike').children() : $(this).parent().find('.like').children();
    let likeType = $(this).hasClass('like') ? 1 : -1;
    if (clickedBtn.attr('style') === 'fill: black;') {
        clickedBtn.css('fill', 'darkgrey');
        $.ajax({
            url: "/changeLike",
            method: "POST",
            data: {
                'newsId': $(this).parent().attr('id'),
                'likeType': 0
            }
        });
    } else if (clickedBtn.attr('style') === 'fill: darkgrey;') {
        $.ajax({
            url: "/changeLike",
            method: "POST",
            data: {
                'newsId': $(this).parent().attr('id'),
                'likeType': likeType
            }
        });
        if (oppositeBtn.attr('style') === 'fill: black;') {
            oppositeBtn.css('fill', 'darkgrey');
        }
        clickedBtn.css('fill', 'black');
    }

})
