<#import "../parts/common.ftl" as c>
<#import "../parts/pager.ftl" as p>

<@c.commonPage>
    <@p.pager url users/>
    <#assign counter = 1 + users.getSize() * users.getNumber()/>

    <table class="table table-striped table-bordered table-dark">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Имя</th>
            <th scope="col">Фамилия</th>
            <th scope="col">Отчество</th>
            <th scope="col">Логин</th>
            <th scope="col">Роли</th>
            <th scope="col">Email</th>
            <th scope="col">Действие</th>
        </tr>
        </thead>

        <tbody>
        <#list users.content as usr>
            <tr>
                <th scope="row">${counter}</th>
                <#assign counter = counter + 1>
                <td>${usr.firstName}</td>
                <td>${usr.lastName}</td>
                <td>
                    <#if usr.patronymic?? && usr.patronymic != "">
                        ${usr.patronymic}
                    <#else>
                        Не задано
                    </#if>
                </td>

                <td>${usr.login}</td>

                <td>
                    <#list usr.roles as role>
                        ${role}<br/>
                    </#list>
                </td>

                <td>${usr.email}</td>

                <td>
                    <#if !usr.isManager()>
                        <a href="/admin/addManager/${usr.id}">Установить менеджером</a>
                    </#if>
                    <#if usr.isManager() && !usr.isAdmin()>
                        <a href="/admin/removeManager/${usr.id}">Открепить менеджера</a>
                    </#if>

                    <#if !usr.isAdmin()>
                        <a href="/admin/addAdmin/${usr.id}">Установить администратором</a>
                    </#if>

                    <#if usr.isAccountNonLocked() && !usr.isAdmin()>
                        <a href="/admin/lockAccount/${usr.id}">Заблокировать пользователя</a>
                    <#elseif !usr.isAdmin()>
                        <a href="/admin/unlockAccount/${usr.id}">Разблокировать пользователя</a>
                    </#if>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>

    <@p.pager url users/>
</@c.commonPage>