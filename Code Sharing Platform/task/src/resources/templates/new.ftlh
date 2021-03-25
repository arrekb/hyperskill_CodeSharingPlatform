<!DOCTYPE html>
<html>
<head>
    <title>Create</title>
    <script>
        function send() {
            let object = {
                "code": document.getElementById("code_snippet").value,
                "time": document.getElementById("time_restriction").value,
                "views": document.getElementById("views_restriction").value
            };

            let json = JSON.stringify(object);

            let xhr = new XMLHttpRequest();
            xhr.open("POST", '/api/code/new', false)
            xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
            xhr.send(json);

            if (xhr.status == 200) {
                alert("Success!");
            }
        }
    </script>
    <link rel="stylesheet" href="/css/style.css">

</head>
<body>
Code: <br>
<textarea id="code_snippet"></textarea> <br><br>
Time: <br>
<input id="time_restriction" type="text"/><br><br>
Views: <br>
<input id="views_restriction" type="text"/>

<button id="send_snippet" type="submit" onclick="send()">Submit</button>

</body>
</html>
