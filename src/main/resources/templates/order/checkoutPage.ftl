<#import "../parts/common.ftl" as c>

<@c.commonPage>
    <div class="mx-auto bg-dark text-white" style="padding: 15px; width: 50%; border-radius: 10px;">

        <div class="alert alert-success text-center" role="alert">
            Итоговая стоимость вашего заказа составит: ${generalPrice} рублей<br/>
            Общий вес вашего заказа: ${generalWeight} кг
        </div>

        <form action="/order/checkout" name="orderContacts" method="post">
            <div class="form-row">
                <div class="form-group col-md-8">
                    <label for="inputCity">Город</label>
                    <input type="text" class="form-control ${(cityError??)?string('is-invalid', '')}" id="inputCity" name="city"
                            <#if contactsData??>
                                value="${contactsData.city}"
                            </#if>>
                    <#if cityError??>
                        <div class="text-danger">
                            ${cityError}
                        </div>
                    </#if>
                </div>
                <div class="form-group col-md-4">
                    <label for="inputZip">Почтовый индекс</label>
                    <input type="text" class="form-control ${(zipCodeError??)?string('is-invalid', '')}" id="inputZip" name="zipCode"
                            <#if contactsData??>
                                value="${contactsData.zipCode}"
                            </#if>>
                    <#if zipCodeError??>
                        <div class="text-danger">
                            ${zipCodeError}
                        </div>
                    </#if>
                </div>
                <input type="hidden" name="country" value="Россия"/>
            </div>
            <div class="form-group">
                <label for="inputPhoneNumber">Номер телефона: </label>
                <input type="text" class="form-control ${(phoneNumberError??)?string('is-invalid', '')}" id="inputPhoneNumber" name="phoneNumber"
                        <#if contactsData??>
                            value="${contactsData.phoneNumber}"
                        </#if>>
                <#if phoneNumberError??>
                    <div class="text-danger">
                        ${phoneNumberError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <label for="inputAddress">Адрес</label>
                <input type="text" class="form-control ${(streetError??)?string('is-invalid', '')}" id="inputAddress" name="street"
                        <#if contactsData??>
                            value="${contactsData.street}"
                        </#if>>
                <#if streetError??>
                    <div class="text-danger">
                        ${streetError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <label for="inputPaymentMethod">Способ оплаты</label>
                <select id="inputPaymentMethod" name="payment" class="form-control">
                    <option selected>Наложенный платёж</option>
                    <option>Карта</option>
                </select>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary">Оформить заказ</button>
        </form>
    </div>
</@c.commonPage>