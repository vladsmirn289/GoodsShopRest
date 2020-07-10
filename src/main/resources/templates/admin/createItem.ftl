<#import "../parts/common.ftl" as c>

<@c.commonPage>
    <div class="mx-auto bg-dark text-center text-white" style="padding: 15px; width: 50%; border-radius: 10px;">
        <#if item??>
            <#if item.id??>
                <div class="alert alert-warning">
                    Для того, чтобы обновить товар, вам необходимо заново указать цену, вес, изображение и категорию!
                </div>
            </#if>
        </#if>

        <form action="/admin/createOrUpdateItem" enctype="multipart/form-data" method="post">
            <#if item??>
                <#if item.id??>
                    <input type="hidden" name="id" value="${item.id}"/>
                </#if>
            </#if>

            <div class="form-group">
                <label for="inputName">Наименование товара</label>
                <input type="text" class="form-control ${(nameError??)?string('is-invalid', '')}"
                       id="inputName" name="name"
                        <#if item??>
                            value="${item.name}"
                        </#if>/>
                <#if nameError??>
                    <div class="text-danger">
                        ${nameError}
                    </div>
                </#if>
            </div>

            <div class="form-group">
                <label for="inputCount">Доступное количество</label>
                <input type="number" class="form-control ${(countError??)?string('is-invalid', '')}"
                       id="inputCount" name="count" min="0" required="required"
                        <#if item??>
                            value="${item.count?long}"
                        </#if>/>
                <#if countError??>
                    <div class="text-danger">
                        ${countError}
                    </div>
                </#if>
            </div>

            <div class="form-group">
                <label for="inputWeight">Вес одного товара в кг</label>
                <input type="number" class="form-control ${(weightError??)?string('is-invalid', '')}"
                       id="inputWeight" name="weight" min="0" step="any" required="required"
                        <#if item??>
                            value="${item.weight?double}"
                        </#if>/>
                <#if weightError??>
                    <div class="text-danger">
                        ${weightError}
                    </div>
                </#if>
            </div>

            <div class="form-group">
                <label for="inputPrice">Цена одного товара в рублях</label>
                <input type="number" class="form-control ${(priceError??)?string('is-invalid', '')}"
                       id="inputPrice" name="price" min="1" step="any" required="required"
                        <#if item??>
                            value="${item.price}"
                        </#if>/>
                <#if priceError??>
                    <div class="text-danger">
                        ${priceError}
                    </div>
                </#if>
            </div>

            <div class="form-group">
                <label for="inputDescription">Описание товара</label>
                <#if item??>
                    <textarea class="form-control ${(descriptionError??)?string('is-invalid', '')}"
                              rows="10" id="inputDescription"
                              name="description">${item.description}</textarea>
                <#else>
                    <textarea class="form-control ${(descriptionError??)?string('is-invalid', '')}"
                              rows="10" id="inputDescription"
                              name="description"></textarea>
                </#if>
                <#if descriptionError??>
                    <div class="text-danger">
                        ${descriptionError}
                    </div>
                </#if>
            </div>

            <div class="form-group">
                <label for="inputCharacteristics">Характеристики товара</label>
                <#if item??>
                    <textarea class="form-control ${(characteristicsError??)?string('is-invalid', '')}"
                              rows="10" id="inputCharacteristics"
                              name="characteristics">${item.characteristics}</textarea>
                <#else>
                    <textarea class="form-control ${(characteristicsError??)?string('is-invalid', '')}"
                              rows="10" id="inputCharacteristics"
                              name="characteristics"></textarea>
                </#if>
                <#if characteristicsError??>
                    <div class="text-danger">
                        ${characteristicsError}
                    </div>
                </#if>
            </div>

            <div class="form-group">
                <div class="custom-file">
                    <label for="customFile">Изображение товара</label>
                    <input type="file" id="customFile" name="file"/>
                </div>
                <#if fileExtError??>
                    <div class="text-danger">
                        ${fileExtError}
                    </div>
                </#if>
            </div>

            <div class="form-group">
                <label for="inputCategory">Выберите категорию товара</label>
                <select id="inputCategory" name="category" class="form-control">
                    <#list categories as category>
                        <option>${category}</option>
                    </#list>
                </select>
            </div>

            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <div class="row justify-content-md-center mb-2">
                <#if item?? && item.id??>
                    <button type="submit" class="btn btn-success">Обновить товар</button>
                <#else>
                    <button type="submit" class="btn btn-success">Создать товар</button>
                </#if>
            </div>
        </form>
    </div>
</@c.commonPage>