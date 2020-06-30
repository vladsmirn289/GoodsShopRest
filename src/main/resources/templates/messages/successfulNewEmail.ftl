<#import "../parts/common.ftl" as c>
<#import "../parts/customMessage.ftl" as m>

<@c.commonPage>
    <@m.customMessage
        "Новая электронная почта успешно подтверждена.<br/>
            Вы можете продолжить сессию в раннее открытом окне, или заново войти."
    >
        <br/> <a href="/login">Войти</a>
    </@m.customMessage>
</@c.commonPage>