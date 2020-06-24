<#assign
    isExist = Session.SPRING_SECURITY_CONTEXT??
>

<#if isExist>
    <#assign
        client = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        login_name = client.getUsername()
        isAdmin = client.isAdmin()
    >
<#else>
    <#assign
        login_name = "Аноним"
        isAdmin = false
    >
</#if>