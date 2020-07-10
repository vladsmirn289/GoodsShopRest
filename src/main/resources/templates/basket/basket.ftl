<#import "../parts/common.ftl" as c>

<#assign counter = 1>

<@c.commonPage>
    <table class="table table-striped table-bordered table-dark">
        <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Фото</th>
                <th scope="col">Название</th>
                <th scope="col">Цена</th>
                <th scope="col">Количество</th>
                <th scope="col">
                    <#if basketItems?size != 0>
                        <a href="/basket/delete">Удалить всё</a>
                    <#else>
                        Удалить всё
                    </#if>
                </th>
                <th scope="col">
                    <#if basketItems?size != 0>
                        <a href="/order/checkout">Оформить всё</a>
                    <#else>
                        Оформить всё
                    </#if>
                </th>
            </tr>
        </thead>

        <tbody>
            <#list basketItems as basketItem>
                <tr>
                    <th scope="row">${counter}</th>
                    <#assign counter = counter + 1>
                    <td><img src="/item/${basketItem.item.id}/image" alt="${basketItem.item.name}"/></td>
                    <td>${basketItem.item.name}</td>
                    <td>${basketItem.item.price} рублей</td>
                    <td>${basketItem.quantity} шт.</td>
                    <td><a href="/basket/delete/${basketItem.id}">Удалить</a></td>
                    <td><a href="/order/checkout/${basketItem.id}">Оформить</a></td>
                </tr>
            </#list>
        </tbody>
    </table>
</@c.commonPage>