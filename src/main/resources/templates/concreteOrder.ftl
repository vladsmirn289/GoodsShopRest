<#import "parts/common.ftl" as c>

<#assign counter = 1
         generalPrice = 0/>

<@c.commonPage>
    <table class="table table-striped table-bordered table-dark">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Код товара</th>
            <th scope="col">Фото</th>
            <th scope="col">Название</th>
            <th scope="col">Цена за 1 шт.</th>
            <th scope="col">Заказано шт.</th>
        </tr>
        </thead>

        <tbody>
        <#list order.clientItems as cItem>
            <tr>
                <th scope="row">${counter}</th>
                <#assign counter = counter + 1>
                <td><a href="/item/${cItem.item.id}">${cItem.item.id}</a></td>
                <td><img src="/item/${cItem.item.id}/image" alt="${cItem.item.id}"/></td>
                <td>${cItem.item.name}</td>
                <td>${cItem.item.price}</td>
                <td>${cItem.quantity}</td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.commonPage>