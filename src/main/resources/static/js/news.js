function changeLike(newsId, likeType) {
    $.ajax({
        url: "/api/news/changeLike",
        method: "POST",
        data: {
            'newsId': newsId,
            'likeType': likeType
        }
    });
}

$('.send-msg-btn').on('click', function () {
    let id = $(this).attr('id');
    let url = "/news/" + id + "/comments";
    let message = $('#send-message' + id).val();

    if (message) {
        $.ajax({
            url: url,
            type: "POST",
            data: {
                message, id
            }
        });

        $('#send-message' + id).val("");
        $("#message-list" + id).append("<li class='list-group-item mb-1'>" + message + "</li>");
    } else {
        warningNotify('type something')
    }
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

$('.like, .dislike').on('click', function () {
    let clickedBtn = $(this).children();
    let oppositeBtn = clickedBtn.hasClass('bi-hand-thumbs-up-fill') ? $(this).parent().find('.dislike').children() : $(this).parent().find('.like').children();
    let likeType = $(this).hasClass('like') ? 1 : -1;
    let newsId = $(this).parent().attr('id');

    if (clickedBtn.attr('style') === 'fill: black;') {
        clickedBtn.css('fill', 'darkgrey');
        changeLike(newsId, 0)
    } else if (clickedBtn.attr('style') === 'fill: darkgrey;') {
        changeLike(newsId, likeType)

        if (oppositeBtn.attr('style') === 'fill: black;') {
            oppositeBtn.css('fill', 'darkgrey');
        }
        clickedBtn.css('fill', 'black');
    }
});