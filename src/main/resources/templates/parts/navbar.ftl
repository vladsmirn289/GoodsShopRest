<#include "security.ftl">

<nav class="navbar fixed-top navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="/">GoodsShop</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/">Каталог <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/basket">Корзина <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/myOrders">Мои заказы <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/client/personalRoom">Личный кабинет <span class="sr-only">(current)</span></a>
            </li>
        </ul>

        <div class="navbar-text mr-2">
            ${login_name}
        </div>

        <#if isExist>
            <form class="mr-2" action="/logout" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button type="submit" class="btn btn-outline-success">Выйти</button>
            </form>
        <#else>
            <form class="mr-2" action="/logout" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button type="submit" class="btn btn-outline-success">Войти</button>
            </form>
        </#if>

        <form class="form-inline my-2 my-lg-0" action="/item" method="get">
            <input class="form-control mr-sm-2" type="search" placeholder="Поиск"
                <#if keyword??>
                   value="${keyword}"
                </#if> name="keyword" aria-label="Search"/>
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Найти</button>
        </form>
    </div>
</nav>