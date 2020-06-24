<#import "parts/common.ftl" as c>

<@c.commonPage>
    <#if RequestParameters.error??>
        <h3>Неверный логин или пароль!</h3>
    </#if>
    <form action="/login" method="post">
        <label>Логин: <input type="text" name="username"/> </label>
        <label>Пароль: <input type="password" name="password"/> </label>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="submit" value="Войти"/>
    </form>
</@c.commonPage>