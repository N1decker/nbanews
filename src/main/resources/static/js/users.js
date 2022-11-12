function changeCheckbox(chkbox) {
    if ($(chkbox).is('[checked]')) {
        $(chkbox).removeAttr('checked')
    } else {
        $(chkbox).attr('checked', 'checked')
    }
}

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

$('.isActive').on('click', function () {
    changeCheckbox($(this))

    let id = $(this).closest('tr').attr('id').substring(4)
    let isActive = this.hasAttribute('checked');

    $.ajax({
        url: "/api/admin/users/" + id + '/block',
        method: "POST",
        data: {
            'locked': !isActive,
        },
        success: function () {
            successNotify(isActive === false ? 'user disabled' : 'user enabled')
        },
        error: function (xhr) {
            let message = JSON.parse(xhr.responseText)['message'];
            warningNotify(message)
        }
    });
    $(this).closest('tr').attr("data-user-enabled", isActive);
})