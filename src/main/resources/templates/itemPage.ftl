<#import "parts/common.ftl" as c>

<@c.commonPage>
    <div class="card mb-3 bg-secondary text-white">
        <div class="row no-gutters">
            <div class="col-md-4">
                <img src="/item/${item.id}/image" class="card-img" alt="${item.name}"/>
            </div>
            <div class="col-md-6">
                <div class="card-body">
                    <h5 class="card-title">${item.name}</h5>
                    <p class="card-text" style="overflow-x: scroll">${item.characteristics?replace("\n", "<br/>")}</p>
                </div>
            </div>
            <div class="col-md-2">
                <div class="card-body">
                    <p class="card-text">Цена: ${item.price}</p>

                    <form action="/item/${item.id}/addToBasket" method="post">
                        <input name="quantity" type="number" size="10" value="1" style="width: 50px" /> шт.<br/> <br/>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <input class="btn btn-primary" type="submit" value="В корзину" />
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="card text-center bg-secondary text-white">
        <div class="card-body">
            <h5 class="card-title">Описание</h5>
            <p class="card-text">${item.description?replace("\n", "<br/>")}</p>
        </div>
    </div>
</@c.commonPage>