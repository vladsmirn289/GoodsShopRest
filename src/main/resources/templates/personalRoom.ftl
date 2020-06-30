<#import "parts/common.ftl" as c>

<@c.commonPage>
    <div class="mx-auto bg-dark" style="padding: 15px; width: 40%; border-radius: 10px;">
        <form action="/client/personalRoom" name="changedPerson" method="post">
            <input type="hidden" name="id" value="${client.id}"/>
            <div class="form-group">
                <label for="first_name" class="text-white">Имя</label>
                <input type="text" class="form-control ${(firstNameError??)?string('is-invalid', '')}" id="first_name" name="firstName"
                            value="${client.firstName}"/>
                <#if firstNameError??>
                    <div class="text-danger">
                        ${firstNameError}
                    </div>
                </#if>
            </div>

            <div class="form-group">
                <label for="last_name" class="text-white">Фамилия</label>
                <input type="text" class="form-control ${(lastNameError??)?string('is-invalid', '')}" id="last_name" name="lastName"
                           value="${client.lastName}"/>
                <#if lastNameError??>
                    <div class="text-danger">
                        ${lastNameError}
                    </div>
                </#if>
            </div>

            <div class="form-group">
                <label for="patronymic" class="text-white">Отчество</label>
                <input type="text" class="form-control" id="patronymic" name="patronymic"
                        <#if client.patronymic??>
                            value="${client.patronymic}"
                        </#if>/>
            </div>

            <div class="form-group">
                <label for="username" class="text-white">Логин</label>
                <input type="text" class="form-control ${(loginError?? || userExistsError??)?string('is-invalid', '')}" id="username" name="login"
                            value="${client.login}"/>
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

            <div class="form-row">
                <div class="form-group col-md-8">
                    <label for="email" class="text-white">Email</label>
                    <input type="email" class="form-control" id="email" name="email"
                           value="${client.email}" readonly="readonly"/>
                </div>
                <div class="form-group col-md-4">
                    <a class="btn btn-primary" href="#">Сменить электронную почту</a>
                </div>
            </div>

            <div class="form-group">
                <a class="btn btn-primary" href="/client/changePassword">Сменить пароль</a>
            </div>

            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <div class="row justify-content-md-center mb-2">
                <button type="submit" class="btn btn-success">Сохранить изменения</button>
            </div>
        </form>
    </div>
</@c.commonPage>