<h3>${view.name}</h3>
<hr>
<p>type:${view.type}</p>
<p>generator:${view.generator}</p>
<!-- no such info in database schema? <p>created:</p> -->
<p><a href="<@s.url action="edit"><@s.param name="uuid">${uuid}</@s.param></@s.url>">Edit Me</a></p>
</body>
</html>