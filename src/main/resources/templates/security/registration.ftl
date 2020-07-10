<#import "../parts/common.ftl" as c>

<@c.commonPage>
    <div class="mx-auto bg-dark" style="padding: 15px; width: 40%; border-radius: 10px;">
        <#if mailError??>
            <div class="alert alert-danger" role="alert">
                ${mailError}
            </div>
        </#if>

        <form action="/registration" name="newClient" method="post">
            <div class="form-group">
                <label for="first_name" class="text-white">Имя</label>
                <input type="text" class="form-control ${(firstNameError??)?string('is-invalid', '')}" id="first_name" name="firstName"
                    <#if client??>
                        value="${client.firstName}"
                    </#if>/>
                <#if firstNameError??>
                    <div class="text-danger">
                        ${firstNameError}
                    </div>
                </#if>
            </div>

            <div class="form-group">
                <label for="last_name" class="text-white">Фамилия</label>
                <input type="text" class="form-control ${(lastNameError??)?string('is-invalid', '')}" id="last_name" name="lastName"
                    <#if client??>
                        value="${client.lastName}"
                    </#if>/>
                <#if lastNameError??>
                    <div class="text-danger">
                        ${lastNameError}
                    </div>
                </#if>
            </div>

            <div class="form-group">
                <label for="patronymic" class="text-white">Отчество (не обязательно)</label>
                <input type="text" class="form-control" id="patronymic" name="patronymic"
                    <#if client??>
                        <#if client.patronymic??>
                            value="${client.patronymic}"
                        </#if>
                    </#if>/>
            </div>

            <div class="form-group">
                <label for="username" class="text-white">Логин</label>
                <input type="text" class="form-control ${(loginError?? || userExistsError??)?string('is-invalid', '')}" id="username" name="login"
                    <#if client??>
                        value="${client.login}"
                    </#if>/>
                <#if loginError??>
                    <div class="text-danger">
                        ${loginError}
                    </div>
                </#if>
                <#if userExistsError??>
                    <div class="text-danger">
                        ${userExistsError}
                    </div>
                </#if>
            </div>

            <div class="form-group">
                <label for="email" class="text-white">Email</label>
                <input type="text" class="form-control ${(emailError??)?string('is-invalid', '')}" id="email" name="email"
                    <#if client??>
                        value="${client.email}"
                    </#if>/>
                <#if emailError??>
                    <div class="text-danger">
                        ${emailError}
                    </div>
                </#if>
            </div>

            <div class="form-group">
                <label for="password" class="text-white">Пароль</label>
                <input type="password" class="form-control ${(passwordError??)?string('is-invalid', '')}" id="password" name="password"
                    <#if client??>
                        value="${client.password}"
                    </#if>/>
                <#if passwordError??>
                    <div class="text-danger">
                        ${passwordError}
                    </div>
                </#if>
            </div>

            <div class="form-group">
                <label for="password_repeat" class="text-white">Повторите пароль</label>
                <input type="password" class="form-control ${(passwordRepeatError??)?string('is-invalid', '')}" id="password_repeat" name="passwordRepeat"/>
                <#if passwordRepeatError??>
                    <div class="text-danger">
                        ${passwordRepeatError}
                    </div>
                </#if>
            </div>

            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <div class="row justify-content-md-center mb-2">
                <button type="submit" class="btn btn-success">Зарегестрироваться</button>
            </div>
        </form>
    </div>
</@c.commonPage>