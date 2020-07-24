<#import "../parts/common.ftl" as c>

<@c.commonPage>
    <div class="mx-auto bg-dark" style="padding: 15px; width: 40%; border-radius: 10px;">
        <#if mailError??>
            <div class="alert alert-danger" role="alert">
                ${mailError}
            </div>
        </#if>

        <form action="/client/resetPassword" method="post">
            <div class="form-group mb-2">
                <label for="email" class="text-white">Email</label>
                <input type="text" id="email" name="email" required="required"
                    <#if email??>
                        value="${email}"
                    </#if>/>
            </div>

            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <div class="row justify-content-md-center mb-2">
                <button type="submit" class="btn btn-success">Отправить письмо</button>
            </div>
        </form>
    </div>
</@c.commonPage>