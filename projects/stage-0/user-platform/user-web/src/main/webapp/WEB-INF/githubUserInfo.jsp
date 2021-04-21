<head>
    <jsp:directive.include
            file="/WEB-INF/jsp/prelude/include-head-meta.jspf" />
    <title>My Home Page</title>
</head>
<body>
<div class="container-lg">
    <h1>三方登录Demo</h1>
    <div>
        用户信息：${requestScope.userInfo}
    </div>
</div>
</body>