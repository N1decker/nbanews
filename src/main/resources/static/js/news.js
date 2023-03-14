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
    let url = "/api/news/" + id + "/comments";
    let message = $('#send-message' + id).val();
    if (message) {
        $.ajax({
            url: url,
            type: "POST",
            data: {
                message, id
            },
            success: function (user) {

                $('#send-message' + id).val("");
                let date = new Date();
                $("#message-list" + id).append(
                    "<li class='list-group-item mb-1'>" +
                    "<div class='row m-0'>" +
                    "<div class='row m-0'>" +
                    "<img height='25' class='rounded-circle' src='data:image/jpeg;base64," + user.image.avatar + "'>" +
                    "<div class='ml-2'>" + user.nickname + "</div>" +
                    "</div>" +
                    "<div style='right: 5px; position: absolute; color: grey'>" +
                    date.getFullYear() + "/" + date.getMonth() + "/" + date.getDate() + " " + date.getHours() + ":" + String(date.getMinutes()).padStart(2, '0') +
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
        let url = "/api/news/" + id + "/comments";
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
                        "<div class='row m-0'>" +
                        "<img height='25'  class='rounded-circle' src='data:image/jpeg;base64," + item.user.image.avatar + "'>" +
                        "<div class='ml-2'>" + item.user.nickname + "</div>" +
                        "</div>" +
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

$('.delete-news-btn').on('click', function () {
    let newsId = $(this).closest('.card-container').attr('id').substring(4)
    if (confirm('Are you sure?')) {
        $(this).closest('.card-container').fadeOut(1000)
        $.ajax({
            url: '/api/news/' + newsId,
            type: 'DELETE',
            data: {
                'newsId': newsId
            },
            success: function () {
                successNotify('news successfully deleted')
            },
            error: function (xhr) {
                warningNotify(parseMessageToUnorderedListByOneDotForNotify(JSON.parse(xhr.responseText)['message']))
            }
        });
    }
})

let newsId;
$('.update-news-btn').on('click', function () {
    newsId = $(this).closest('.card-container').attr('id').substring(4)
    const title = $(this).closest('.card-container').find('.card-title').text();
    const subtitle = $(this).closest('.card-container').find('.card-subtitle').text();
    const imgUrl = $(this).closest('.card-container').find('.card-img-top').attr('src');
    const contentAuthor = $(this).closest('.card-container').find('.content-author-info').find('.text-center').text();

    let $update = $('#update-news-modal');
    $update.modal('show');
    $update.find('#update-news-title').val(title);
    $update.find('#update-news-subtitle').val(subtitle);
    $update.find('#update-news-content-author').val(contentAuthor);
    $update.find('#update-news-image-url').val(imgUrl);
})
$('#save-update-news-btn').on('click', function () {
    let $update = $('#update-news-modal');
    const updatedTitle = $update.find('#update-news-title').val();
    const updatedSubtitle = $update.find('#update-news-subtitle').val();
    const updatedImageUrl = $update.find('#update-news-image-url').val();
    const updatedContentAuthor = $update.find('#update-news-content-author').val();

    $.ajax({
        url: '/api/news/' + newsId,
        type: 'PUT',
        data: {
            'title': updatedTitle,
            'subtitle': updatedSubtitle,
            'contentAuthor': updatedContentAuthor,
            'imageUrl': updatedImageUrl
        },
        success: function () {
            successNotify('news successfully updated')
            newsId = null;
            $('#update-news-modal').modal('hide');
        },
        error: function (xhr) {
            warningNotify(parseMessageToUnorderedListByOneDotForNotify(JSON.parse(xhr.responseText)['message']))
        }
    });
})

$('#close-update-news-btn').on('click', function () {
    $('#update-news-modal').modal('hide');
    newsId = null;
})
