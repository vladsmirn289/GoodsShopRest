<#import "parts/common.ftl" as c>

<#assign counter = 1
         generalPrice = 0/>

<@c.commonPage>
    <table class="table table-striped table-bordered table-dark">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Номер заказа</th>
            <th scope="col">Дата оформления</th>
            <th scope="col">Общая стоимость</th>
            <th scope="col">Состояние заказа</th>
        </tr>
        </thead>

        <tbody>
        <#list orders as order>
            <tr>
                <th scope="row">${counter}</th>
                <#assign counter = counter + 1>
                <td><a href="/order/${order.id}">${order.id}</a></td>
                <td>${order.createdOn?datetime}</td>

                <#list order.getClientItems() as cItem>
                    <#assign generalPrice += (cItem.quantity * cItem.item.price)/>
                </#list>

                <td>
                    ${generalPrice}
                </td>

                <#assign generalPrice = 0/>

                <td>${order.orderStatus.name()}</td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.commonPage>