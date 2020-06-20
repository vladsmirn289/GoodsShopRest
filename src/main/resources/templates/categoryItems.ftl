<#import "parts/common.ftl" as c>

<@c.commonPage>
    <div class="row row-cols-1 row-cols-md-2" id="itemsBlock">
        <#list items as item>
            <div class="col-md-6">

            <div class="card mb-3 bg-secondary border-dark text-white h-">
                <div class="row no-gutters">
                    <div class="col-md-4">
                        <img src="/item/image/${item.id}" class="card-img" alt="${item.name}"/>
                    </div>
                    <div class="col-md-8 text-center">
                        <div class="card-body">
                            <h4 class="card-title">${item.name}</h4>
                            <p class="card-text">
                                <#if (item.description?length >= 250)>
                                    ${item.description?substring(0, 250)}...
                                <#else>
                                    ${item.description}
                                </#if>
                            </p>
                            <hr/>
                            <p class="card-text"><small>${item.price} руб.</small></p>
                        </div>
                    </div>
                </div>
            </div>

            </div>
        </#list>
    </div>
</@c.commonPage>