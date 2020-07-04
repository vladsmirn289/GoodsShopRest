<#assign
    isExist = Session.SPRING_SECURITY_CONTEXT??
>

<#if isExist>
    <#assign
        client = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        login_name = client.getUsername()
        isAdmin = client.isAdmin()
        isManager = client.isManager()
    >
<#else>
    <#assign
        login_name = "Аноним"
        isManager = false
        isAdmin = false
    >
</#if>