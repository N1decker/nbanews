$("#isAdmin, #isUser, #isEditor, #enabled").on("click", function () {
    if ($(this).is('[checked]')) {
        $(this).removeAttr('checked')
    } else {
        $(this).attr('checked', 'checked')
    }
});