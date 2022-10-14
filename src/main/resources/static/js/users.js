$("#isAdmin, #isUser, #isEditor, #enabled").on("click", function () {
    const btn = $(this);
    if (btn.is('[checked]')) {
        btn.removeAttr('checked')
    } else {
        btn.attr('checked', 'checked')
    }
});