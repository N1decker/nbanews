$("#profile-email-input, #profile-nickname-input").on("click", function () {
    let inputField = $(this)
    let beforeUpdate = inputField.val();
    console.log(beforeUpdate)
    let changeDataBtnConfirm = inputField.attr('id') === 'profile-email-input' ? $('#change-email-btn') : $('#change-nickname-btn');
    changeDataBtnConfirm.show();
    changeDataBtnConfirm.one("click", function () {
        if (inputField.val() === '') {
            warningNotify('cannot be empty')
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
                    let message = JSON.parse(xhr.responseText)['message'].replaceAll('.\s', '<br>')
                    newPassword.val('');
                    warningNotify(message)
                }
            });
            changeDataBtnConfirm.hide();
        }
    })
})