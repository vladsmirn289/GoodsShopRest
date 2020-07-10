<#import "../parts/common.ftl" as c>

<@c.commonPage>
    <#if mailError??>
        <div class="alert alert-danger" role="alert">
            ${mailError}
        </div>
    </#if>

    <div class="bg-dark text-white text-center" style="padding: 5px">
        <form action="/client/changeEmail" method="post">
            <div class="form-group">
                <label for="email" class="text-white">Новый email: </label>
                <input type="email" id="email" name="email"/>
            </div>

            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <div class="row justify-content-md-center mb-2">
                <button type="submit" class="btn btn-success">Сменить email</button>
            </div>
        </form>
    </div>
</@c.commonPage>