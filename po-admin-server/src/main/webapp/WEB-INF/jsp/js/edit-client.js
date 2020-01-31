var editClientState = function (id, blocked) {
    if (blocked === null) {
        return;
    }
    var clientObject = {
        "id" : id,
        "blocked" : blocked
    };
        $.ajax({
            type: "POST",
            url: "/api/admin/block",
            contentType: "application/json; charset=utf-8",
            headers: {"Authorization": getCookie("token")},
            data: JSON.stringify(clientObject),
            dataType: 'text',
            async: false,
            success: function (response) {
                document.location.href = '/api/admin/all-clients';
            },
            error: function (exception) {
                alert("Error:" + exception.responseText);
            }
        })
    };