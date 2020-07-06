<#import "../parts/common.ftl" as c>

<@c.commonPage>
    <div class="mx-auto bg-dark text-center text-white" style="padding: 15px; width: 40%; border-radius: 10px;">
        <form action="/admin/createCategory" method="post">
            <div class="form-group">
                <label for="inputParentCategory">Выберите родительскую категорию</label>
                <select id="inputParentCategory" name="category" class="form-control">
                    <option selected="selected">Родительская категория</option>
                    <#list parents as parent>
                        <option>${parent}</option>
                    </#list>
                </select>
            </div>

            <div class="form-group">
                <label for="name">Название категории</label>
                <input type="text" class="form-control ${(nameError??)?string('is-invalid', '')}" id="name" name="name"
                    <#if name??>
                        value="${name}"
                    </#if>/>
                <#if nameError??>
                    ${nameError}
                </#if>
            </div>

            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <div class="row justify-content-md-center mb-2">
                <button type="submit" class="btn btn-success">Создать категорию</button>
            </div>
        </form>
    </div>
</@c.commonPage>