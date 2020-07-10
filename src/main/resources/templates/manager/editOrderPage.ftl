<#import "../parts/common.ftl" as c>

<@c.commonPage>
    <div class="mx-auto bg-dark text-center text-white" style="padding: 15px; width: 40%; border-radius: 10px;">
        <div class="mb-3">Контактная информация</div>
        <div class="mb-1">Имя: ${order.client.firstName}</div>
        <div class="mb-1">Фамилия: ${order.client.lastName}</div>
        <div class="mb-1">Отчество: <#if order.client.patronymic?? && order.client.patronymic != "">
            ${order.client.patronymic}
        <#else>
            Не указано
        </#if></div>
        <div class="mb-1">Email: ${order.client.email}</div>
        <div class="mb-1">Номер телефона: ${order.contacts.phoneNumber}</div>
        <div class="mb-1">Город: ${order.contacts.city}</div>
        <div class="mb-1">Адрес: ${order.contacts.street}</div>
        <div class="mb-5">Почтовый индекс: ${order.contacts.zipCode}</div>

        <form action="/order/changeOrderStatus/${order.id}" method="post">
            <div class="form-group">
                <label for="inputOrderStatus">Изменить статус заказа</label>
                <select id="inputOrderStatus" name="orderStatus" class="form-control">
                    <option selected="selected">${order.orderStatus.name()}</option>
                    <#list statuses as status>
                        <option>${status}</option>
                    </#list>
                </select>
            </div>

            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <div class="row justify-content-md-center mb-2">
                <button type="submit" class="btn btn-success">Применить</button>
            </div>
        </form>
    </div>
</@c.commonPage>