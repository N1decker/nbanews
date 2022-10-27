$("#profile-email-input, #profile-nickname-input").on("click", function () {
    let inputField = $(this)
    let changeDataBtnConfirm = inputField.attr('id') === 'profile-email-input' ? $('#change-email-btn') : $('#change-nickname-btn');
    changeDataBtnConfirm.show();
    changeDataBtnConfirm.one("click", function () {
        if (inputField.val() === '') {
            warningNotify('cannot be empty')
        } else {
            successNotify(inputField.val());
        changeDataBtnConfirm.hide();
        }
    })
})