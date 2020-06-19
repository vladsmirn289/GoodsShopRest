<#import "../parts/common.ftl" as c>

<@c.commonPage>
    <div class="card text-white bg-dark mb-3 text-center">
        <div class="card-body">
            <h5 class="card-title">Упс...</h5>
            <p class="card-text">Дата и время: ${timestamp?date}, ${timestamp?time}</p>
            <p class="card-text">Статус: ${status}</p>
            <p class="card-text">Описание: ${path} ${error}</p>
            <p class="card-text">Сообщение:
                <#if message?contains("No message available")>
                    Что-то не так...
                <#else>
                    ${message}
                </#if>
            </p>
        </div>
    </div>
</@c.commonPage>