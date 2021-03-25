<!DOCTYPE html>
<html>
<head>
    <title>Code</title>
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet"
          href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>

<span id="load_date">${load_date}</span>
<pre id="code_snippet"><code>${code_snippet}</code></pre>
<#--            //GET /code/UUID should contain the following elements:
            //1.<span id="time_restriction"> ... </span> in case the time restriction is applied.
            //2. <span id="views_restriction"> ... </span> in case the views restriction is applied.
-->

<#if time_restriction?has_content>
    <span id="time_restriction"> ${time_restriction} </span>
</#if>
<#if views_restriction?has_content>
    <span id="views_restriction">${views_restriction}</span>
</#if>

</body>
</html>

