$("#isAdmin, #isUser, #isEditor, #enabled").on("click", function () {
    if ($(this).is('[checked]')) {
        $(this).removeAttr('checked')
    } else {
        $(this).attr('checked', 'checked')
    }
});

$('.delete-user-btn').on('click', function () {
    let rowId = $(this).parents('tr').attr('id')
    let $row = $('#' + rowId);

    if (confirm('Are you sure?')) {
        $.ajax({
            url: "/api/admin/users/" + rowId.substring(4),
            method: "DELETE",
            success: function () {
                successNotify('user successfully deleted');
                $row.fadeOut(1000, function () {
                    $row.remove();

                })
            },
            error: function (xhr) {
                let message = JSON.parse(xhr.responseText)['message'];
                warningNotify(message)
            }
        });
    }
})