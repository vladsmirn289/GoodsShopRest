<#import "../parts/common.ftl" as c>
<#import "../parts/customMessage.ftl" as m>

<@c.commonPage>
    <@m.customMessage
        "Ваш заказ успешно создан!<br/>
            Для того, чтобы отслеживать его состояние, вы можете перейти на вкладку мои заказы."
    >
        <br/> <a href="/">На главную</a>
    </@m.customMessage>
</@c.commonPage>