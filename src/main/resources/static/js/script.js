$('.send-msg-btn').on('click', function () {
    let id = $(this).attr('id');
    let url = "/news/" + id + "/comments";
    let message = $('#send-message'+id).val();
    $.ajax({
        url: url,
        type: "POST",
        data: {
            message,
            id
        }
    });
    $('#send-message'+id).val("");
    $("#message-list" + id).append("<li class='list-group-item mb-1'>" + message + "</li>");
});

let loadChat = $('.chat').on('click', function () {
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
});