<#import "../parts/common.ftl" as c>

<@c.commonPage>
    <div class="mx-auto bg-dark" style="padding: 15px; width: 40%; border-radius: 10px;">
        <form action="/client/changePassword" method="post">
            <div class="form-group">
                <label for="newPassword" class="text-white">Новый пароль</label>
                <input type="password" class="form-control ${(lengthPasswordError??)?string('is-invalid', '')}" id="newPassword" name="newPassword"/>
                <#if lengthPasswordError??>
                    <div class="text-danger">
                        ${lengthPasswordError}
                    </div>
                </#if>
            </div>

            <div class="form-group">
                <label for="retypePassword" class="text-white">Повторите пароль</label>
                <input type="password" class="form-control ${(retypePasswordError??)?string('is-invalid', '')}" id="retypePassword" name="retypePassword"/>
                <#if retypePasswordError??>
                    <div class="text-danger">
                        ${retypePasswordError}
                    </div>
                </#if>
            </div>

            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <div class="row justify-content-md-center mb-2">
                <button type="submit" class="btn btn-success">Подтвердить</button>
            </div>
        </form>

        <div class="row justify-content-md-center">
            <a href="/registration">Зарегестрироваться</a>
        </div>
    </div>
</@c.commonPage>