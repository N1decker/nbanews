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