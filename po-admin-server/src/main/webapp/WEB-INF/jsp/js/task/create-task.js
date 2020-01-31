var createtask = function () {
    var sendToServer = function(result) {
        var authJsonObject = {
            "name": document.getElementById("name").value,
            "taskProcessType": document.getElementById("taskProcessType").value,
            "version": document.getElementById("version").value,
            "os": document.getElementById("os").value,
            "osType": document.getElementById("osType").value,
            "pathToRunFile": document.getElementById("pathToRunFile").value,
            "torrentFile": result
        };
        $.ajax({
            type: "POST",
            url: "/api/admin/create-task",
            contentType: "application/json; charset=utf-8",
            headers: {"Authorization" : getCookie("token")},
            data: JSON.stringify(authJsonObject),
            dataType: 'text',
            async: false,
            success: function (response) {
                document.location.href = '/api/admin/all-tasks';
            },
            error: function (exception) {
                alert("Error:" + exception.responseText);
            }
        })
    };

    var file = document.getElementById("torrentFile").files[0];
    var reader = new FileReader();
    if (file != null) {
        reader.readAsDataURL(file);
        reader.onloadend = res => sendToServer(res.target.result.substr(res.target.result.indexOf(',') + 1));
    } else {
        sendToServer(null);
    }
};