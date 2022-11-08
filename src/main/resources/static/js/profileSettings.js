$("#profile-email-input, #profile-nickname-input").on("click", function () {
    let inputField = $(this)
    let beforeUpdate = inputField.val();
    let changeDataBtnConfirm = inputField.attr('id') === 'profile-email-input' ? $('#change-email-btn') : $('#change-nickname-btn');
    changeDataBtnConfirm.show();
    changeDataBtnConfirm.one("click", function () {
        if (inputField.val() === '') {
            warningNotify('cannot be empty')
        } else if (inputField.val() === beforeUpdate) {
            changeDataBtnConfirm.hide();
        } else {
            $.ajax({
                url: "/api/profile",
                method: "POST",
                data: {
                    inputName: inputField.attr('name'),
                    changeTo: inputField.val()
                },
                success: function () {
                    successNotify(inputField.attr('name') + ' has been successfully changed')
                },
                error: function (xhr) {
                    let message = JSON.parse(xhr.responseText)['message'];
                    inputField.val(beforeUpdate);
                    warningNotify(message)
                }
            });
            changeDataBtnConfirm.hide();
        }
    })
})


$('#profile-new-pass-input').on('click', function () {
    let oldPassword = $('#profile-old-pass-input')
    let newPassword = $(this)
    let changeDataBtnConfirm = $('#change-pass-btn');
    changeDataBtnConfirm.show();
    changeDataBtnConfirm.one("click", function () {
        if (oldPassword.val() === '') {
            warningNotify('enter your current password')
        }
        if (newPassword.val() === '') {
            warningNotify('enter your new password')
        } else {
            $.ajax({
                url: "/api/profile/change-password",
                method: "POST",
                data: {
                    newPassword: newPassword.val(),
                    oldPassword: oldPassword.val()
                },
                success: function () {
                    successNotify('password has been successfully changed')
                },
                error: function (xhr) {
                    // let messageList = '<ul>'
                    // let message = JSON.parse(xhr.responseText)['message'].replaceAll('. ', '.. ').split('. ')
                    // $(message).each(function () {
                    //     messageList += "<li>" + this + "</li>";
                    // })
                    // messageList += '</ul>'
                    newPassword.val('');
                    warningNotify(parseMessageToUnorderedListByOneDotForNotify(JSON.parse(xhr.responseText)['message']))
                }
            });
            changeDataBtnConfirm.hide();
        }
    })
})

function parseMessageToUnorderedListByOneDotForNotify(text) {
    let messageList = '<ul>'
    let message = text.replaceAll('. ', '.. ').split('. ')
    $(message).each(function () {
        messageList += "<li>" + this + "</li>";
    })
    messageList += '</ul>'
    return messageList;
}

$("#change-avatar-btn").on("click",function (e){
    let form = $('<input type="file" name="avatar">');
    form.click()
    form.on("change",onFileSelected);
});

let onFileSelected = function(e){
    let formData = new FormData();
    formData.append('avatar', $(this)[0].files[0])
    $.ajax({
        url: "/api/profile/change-avatar",
        method: "POST",
        type: "POST",
        dataType: 'text',
        contentType: false,
        processData: false,
        cache: false,
        data: formData,
        success: function (response) {
            successNotify("avatar will change after the page is reloaded");
        },
        error: function () {
            warningNotify("something went wrong");
        }
    })
};