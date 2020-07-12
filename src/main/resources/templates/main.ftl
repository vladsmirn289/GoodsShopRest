<#import "parts/common.ftl" as c>

<@c.commonPage>
    <div class="row row-cols-3">
        <#list treeOfCategories as parent, value>

            <div class="mb-4 col">
                <button class="btn btn-dark" type="button" data-toggle="collapse" data-target="#collapseExample${parent.getId()}" aria-expanded="false" aria-controls="collapseExample">
                    ${parent.getName()}
                </button> <br/>

                <div class="ml-5 mt-4 collapse" id="collapseExample${parent.getId()}">
                    <#list value as subcategories>
                        <a class="btn btn-warning mt-1" href="/category/${subcategories.getId()}">
                            ${subcategories.getName()}
                        </a> <br/>
                    </#list>
                </div>
            </div>

        </#list>
    </div>
</@c.commonPage>