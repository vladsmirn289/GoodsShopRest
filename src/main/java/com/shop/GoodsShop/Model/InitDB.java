package com.shop.GoodsShop.Model;

import com.shop.GoodsShop.Service.CategoryService;
import com.shop.GoodsShop.Service.ClientService;
import com.shop.GoodsShop.Service.ItemService;
import com.shop.GoodsShop.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/* TODO: add images to items*/
@Component
public class InitDB {
    private CategoryService categoryService;
    private ItemService itemService;
    private ClientService clientService;
    private OrderService orderService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public void init() {
        Category books = new Category("Книги");
        categoryService.save(books);

        /* --- Books Categories --- */

        Category fiction = new Category("Художественная литература", books);
        Category scienceBooks = new Category("Научная литература", books);
        Category computerBooks = new Category("Компьютерная литература", books);

        categoryService.save(fiction);
        categoryService.save(scienceBooks);
        categoryService.save(computerBooks);

        /* --- --- */

        Category stationery = new Category("Канцелярия");
        categoryService.save(stationery);

        /* --- Stationery Categories --- */

        Category notebooks = new Category("Блокноты, тетради, альбомы", stationery);
        Category writing = new Category("Письменные принадлежности", stationery);

        categoryService.save(notebooks);
        categoryService.save(writing);

        /* --- --- */

        Category clothes = new Category("Одежда");
        categoryService.save(clothes);

        /* --- Clothes Categories --- */

        Category babyClothes = new Category("Детская одежда", clothes);
        Category womenClothes = new Category("Женская одежда", clothes);
        Category menClothes = new Category("Мужская одежда", clothes);

        categoryService.save(babyClothes);
        categoryService.save(womenClothes);
        categoryService.save(menClothes);

        /* --- --- */

        Category electronics = new Category("Электроника");
        categoryService.save(electronics);

        /* --- Electronics Categories --- */

        Category computers = new Category("Компьютеры", electronics);
        Category peripherals = new Category("Периферия", electronics);

        categoryService.save(computers);
        categoryService.save(peripherals);

        /* --- --- */


        /* --- Book Items --- */

        String description = "Говард Филлипс Лавкрафт, не опубликовавший при жизни ни одной книги, " +
                "стал маяком и ориентиром жанра литературы ужасов, кумиром как широких читательских масс, " +
                "так и рафинированных интеллектуалов. Влияние его признавали такие мастера, как Борхес, " +
                "и такие кумиры миллионов, как Стивен Кинг, его рассказы неоднократно экранизировались, " +
                "а само имя писателя стало нарицательным. Франсуа Баранже — французский художник, дизайнер " +
                "компьютерных игр, концептуалист и футурист. Мечту оформить Лавкрафта он вынашивал много лет — " +
                "и вот наконец мечта сбылась: вашему вниманию предлагается классическая повесть «Зов Ктулху» с " +
                "иллюстрациями французского мастера. Наконец вы воочию увидите, что может быть, если Ктулху проснется…";
        String code = UUID.randomUUID().toString().substring(0, 8);
        Item callOfCthulhu = new Item("Зов Ктулху", 3000L, 0.57D, 800D, description, code, fiction);

        description = "Жизнь Алисии Беренсон кажется идеальной. Известная художница вышла замуж за востребованного " +
                "модного фотографа. Она живет в одном из самых привлекательных и дорогих районов Лондона в роскошном " +
                "доме с большими окнами, выходящими в парк. Однажды поздним вечером, когда ее муж Габриэль возвращается " +
                "домой с очередной съемки, Алисия пять раз стреляет ему в лицо. И с тех пор не произносит ни слова.\n" +
                "Отказ Алисии говорить или давать какие-либо объяснения будоражит общественное воображение. Тайна делает " +
                "художницу знаменитой. И в то время как сама она находится на принудительном лечении, цена ее последней " +
                "работы – автопортрета с единственной надписью по-гречески \"АЛКЕСТА\" – стремительно растет.\n" +
                "Тео Фабер – криминальный психотерапевт. Он долго ждал возможности поработать с Алисией, заставить ее говорить. " +
                "Но что скрывается за его одержимостью безумной мужеубийцей, и к чему приведут все эти психологические эксперименты? " +
                "Возможно, к истине, которая угрожает поглотить и его самого...";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item silentPatient = new Item("Безмолвный пациент", 1500L, 0.294D, 390D, description, code, fiction);

        description = "Книга знакомит читателя с творчеством известного английского писателя Артура Конан Дойла. " +
                "На страницах книги вы встретитесь со знакомыми персонажами и сможете проследить за раскрытием таинственных преступлений.";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item sherlockHolmesTales = new Item("Рассказы о Шерлоке Холмсе", 1500L, 0.246D, 150D, description, code, fiction);

        description = "Юрий Никулин, великий артист, замечательный клоун и чрезвычайно остроумный собеседник, " +
                "очень трепетно относился к читателям своих книжек. Он к ним обращался только на «вы» и просил принять " +
                "его самые лучшие пожелания. Он и анекдоты, которых знал тысячи, старался подбирать на любой вкус. Он был уверен, " +
                "что каждый найдет «свой анекдот» и станет смеяться, а то и просто сильно хохотать. Потом анекдот нужно запомнить и " +
                "рассказывать всем везде и всюду, но хорошо бы «к месту»: от этого анекдот только выиграет. А если кто-то, утверждал " +
                "Юрий Никулин, просто улыбнется, то и это будет очень хорошо и просто прекрасно. Итогом же прочтения этой книги " +
                "будет самый лучший и самый остроумный анекдот Юрия Никулина.";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item jokesFromNikulin = new Item("Анекдоты от Никулина", 3000L, 0.449D, 500D, description, code, fiction);

        description = "Старший лейтенант Ибрагим Крушинин командует ротой спецназа на Северном Кавказе. " +
                "Он смел и беспощаден в бою. Ядовит – как шутят сослуживцы. Не случайно за старлеем закрепился " +
                "позывной Анчар. Во время очередной операции группа Крушинина попадает в засаду. Ибрагим подрывается " +
                "на мине и, раненный, оказывается в плену у бандитов. Неожиданно в главаре моджахедов он узнает своего " +
                "старшего брата, которого потерял в раннем детстве. Что делать – уничтожить бандитского эмира, " +
                "захватить его в плен или… Времени на размышления у Анчара не остается: на помощь своему командиру уже спешат бойцы спецназа…";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item likeTwoDropsOfBlood = new Item("Как две капли крови", 2400L, 0.262D, 400D, description, code, fiction);

        itemService.save(callOfCthulhu);
        itemService.save(silentPatient);
        itemService.save(sherlockHolmesTales);
        itemService.save(jokesFromNikulin);
        itemService.save(likeTwoDropsOfBlood);

        description = "Если раньше дифференциальные и интегральные исчисления были только уделом математиков, " +
                "сегодня эту тему уже проходят в старших классах школы. Однако те, кто в дальнейшем не связывает " +
                "свою жизнь с математикой, с трудом представляют, в какой сфере можно применить эти знания.\n" +
                "\n" +
                "В этой книге производные и интегралы рассматриваются не только в историческом, но и в " +
                "практическом контексте. Читатель узнает о том, какую роль они сыграли в наблюдении за звездами, " +
                "какова связь между функциями и выражением наклона, между интегрированием и делением земель. " +
                "Иллюстрации помогают представить математические задачи образно, а любопытные факты из жизни " +
                "ученых удачно дополняют изложение теории.\n" +
                "\n" +
                "Издание предназначено для старшеклассников, студентов вузов и всех любителей математики.";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item derivativesAndIntegrals = new Item("Производные и интегралы", 5000L, 0.15D, 850D, description, code, scienceBooks);

        description = "Книга представляет собой учебник по функциональному анализу. " +
                "Этот учебник годится для первоначального изучения линейного функционального анализа, " +
                "но будет полезен и для углубленного изучения, поскольку содержит материал, который " +
                "обычно не включают в учебники по функциональному анализу. Несмотря на краткое изложение, " +
                "в учебнике все теоремы приведены с полными доказательствами. Многие понятия и утверждения " +
                "демонстрируются на модельных примерах. Книга будет полезна студентам и аспирантам, а " +
                "также всем желающим познакомиться с современной абстрактной математикой.\n";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item functionalAnalysisFromZeroToUnits = new Item("Функциональный анализ от нуля до единиц", 4000L, 0.29D, 520D, description, code, scienceBooks);

        description = "Энциклопедия Анатомия 4D от Devar - это не просто книга, она представляет собой " +
                "полноценный научный справочник по анатомии человека. С помощью бесплатного приложения " +
                "DEVAR books в дополненной реальности читатель увидит в мельчайших подробностях строение " +
                "кровеносной системы, легкие, мышцы… Он увидит, как бьется самый главный орган - сердце " +
                "человека и даже сможет подержать его в руках. При этом все изображения книги адаптированы " +
                "для детского восприятия. Никогда изучение анатомии не было столь увлекающим и натуралистичным!";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item encyclopedia4DAnatomy = new Item("Энциклопедия. Анатомия 4D", 500L, 0.59D, 450D, description, code, scienceBooks);

        description = "Новые открытия в астрономии совершаются ежегодно, и новостные издания пестрят " +
                "сообщениями об очередных космических разработках. Но, хотя прошли времена, когда " +
                "астрономические явления порождали суеверия и страхи, научный подход ко Вселенной " +
                "пока еще не стал всеобщим уделом: ведь научно-популярной литературы о космосе, " +
                "рассчитанной на неспециалистов, очень мало.\n" +
                "Книга, которую вы держите в руках, призвана восполнить этот пробел. О происхождении " +
                "Земли и загадках Луны и Солнца, о планетах и галактиках, о Млечном Пути и новейших " +
                "данных из области космологии здесь рассказывается доступно и увлекательно. Для " +
                "наглядности текст сопровождается многочисленными иллюстрациями.\n" +
                "Издание предназначено для всех, кто интересуется астрономией, космологией и с" +
                "овременными научными изысканиями в этих областях.";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item universeInQuestionsAndAnswers = new Item("Вселенная в вопросах и ответах", 800L, 0.14D, 852D, description, code, scienceBooks);

        description = "\"Книга о пути жизни\" Лао-цзы, называемая по-китайски \"Дао-Дэ цзин\", занимает " +
                "после Библии второе место в мире по числу иностранных переводов. Происхождение этой " +
                "книги и личность ее автора окутаны множеством легенд, о которых известный переводчик " +
                "Владимир Малявин подробно рассказывает в своем предисловии. Само слово \"дао\" означает " +
                "путь, и притом одновременно путь мироздания, жизни и человеческого совершенствования. " +
                "А \"дэ\" – это внутренняя полнота жизни, незримо, но прочно связывающая все живое. " +
                "Секрет чтения Лао-цзы в том, чтобы постичь ту внутреннюю глубину смысла, которую внушает " +
                "мудрость, открывая в каждом суждении иной и противоположный смысл.\n";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item bookAboutTheWayOfLife = new Item("Книга о пути жизни", 600L, 0.28D, 370D, description, code, scienceBooks);

        itemService.save(derivativesAndIntegrals);
        itemService.save(functionalAnalysisFromZeroToUnits);
        itemService.save(encyclopedia4DAnatomy);
        itemService.save(universeInQuestionsAndAnswers);
        itemService.save(bookAboutTheWayOfLife);

        /* --- --- */
    }
}
