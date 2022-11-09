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
                    newPassword.val('');
                    warningNotify(parseMessageToUnorderedListByOneDotForNotify(JSON.parse(xhr.responseText)['message']))
                }
            });
            changeDataBtnConfirm.hide();
        }
    })
})

$("#change-avatar-btn").on("click", function (e) {
    let form = $('<input type="file" name="avatar">');
    form.click()
    form.on("change", onFileSelected);
});

let onFileSelected = function (e) {
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

$('#delete-own-account-confirm-input').on('keyup', function () {
    let deleteBtn = $('#delete-own-account-confirm-input')
    let userEmail = deleteBtn.attr('user-email')
    let userNickname = deleteBtn.attr('user-nickname')
    let finalDeleteBtn = $('#final-delete-own-account-btn')
    if ($(this).val() === (userEmail + '/' + userNickname)) {
        finalDeleteBtn.prop('disabled', false)
        finalDeleteBtn.on('click', function () {
            $.ajax({
                url: "/api/profile/",
                method: "DELETE",
                success: function () {
                    window.location.href = "/";
                },
                error: function (xhr) {
                    warningNotify(parseMessageToUnorderedListByOneDotForNotify(JSON.parse(xhr.responseText)['message']))
                }
            });
        })
    } else {
        finalDeleteBtn.prop('disabled', true)
    }
})
