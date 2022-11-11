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
            },
            success: function (data) {

                $('#send-message' + id).val("");
                let date = new Date();
                $("#message-list" + id).append(
                    "<li class='list-group-item mb-1'>" +
                    "<div class='row m-0'>" +
                    "<div>" +
                    data +
                    "</div>" +
                    "<div style='right: 5px; position: absolute; color: grey'>" +
                    date.getFullYear() + "/" + date.getMonth() + "/" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() +
                    "</div>" +
                    "</div>" +
                    "<span style='margin-left: 20px'>" + message + "</span>" +
                    "</li>");
            }
        });
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
                    list +=
                        "<li class='list-group-item mb-1'>" +
                        "<div class='row m-0'>" +
                        "<div>" + item.user.nickname + "</div>" +
                        "<div style='right: 5px; position: absolute; color: grey'>" +
                        item.dateTime.replace('T', ' ').replaceAll('-', '/').substring(0, 16) +
                        "</div>" +
                        "</div>" +
                        "<span style='margin-left: 20px'>" + item.comment + "</span>" +
                        "</li>";
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

$(function () {
    $("#js-select2").select2({
        minimumResultsForSearch: -1,
        templateResult: function (option) {
            return $("<span><img src='/images/sourceLogos/" + option.id + ".png' width='60px' alt='+" + option.text + " +'  /> " + option.text + "</span>");
        },
        templateSelection: function (option) {
            return $("<span><img src='/images/sourceLogos/" + option.id + ".png' width='60px' alt='+" + option.text + " +'/> " + option.text + "</span>");
        }
    });
})