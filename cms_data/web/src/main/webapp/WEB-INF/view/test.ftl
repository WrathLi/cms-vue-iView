<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title></title>
<style>
    .navbar {
        min-height: 50px;
        border: 1px solid transparent;
    }
    .navbar {
        border: none;
        /*border-radius: 0;*/
        /*margin: 0;*/
        background: #333;
    }
    .navbar-fixed-top {
        /*positon:fixed;*/
        /*top: 0;*/
        /*border-width: 0 0 1px;*/
    }
    .navbar-fixed-top {
        position: fixed;
        right: 0;
        left: 0;
        top: 0;
        z-index: 1030;
    }
</style>
</head>
<body>
<header id="head" class="navbar navbar-static-top navbar-fixed-top">
</header>
<nav style="background: grey; width: 100px; position: fixed; top: 50px; left:0; bottom: 0;">
    nav
</nav>
<div style="background: sienna; border: 1px solid red; position: fixed; overflow-y: auto;overflow-x: auto; top: 50px; left:100px; bottom: 0px; right:
0px;">
    <div style="width: 100px; height: 2024px; background:silver;">
        <a href="list">Logout</a>
    </div>
</div>
</body>
</html>