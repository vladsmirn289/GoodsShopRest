package com.shop.GoodsShop.Model;

import com.shop.GoodsShop.Service.CategoryService;
import com.shop.GoodsShop.Service.ClientService;
import com.shop.GoodsShop.Service.ItemService;
import com.shop.GoodsShop.Service.OrderService;
import com.shop.GoodsShop.Utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.UUID;

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
        Category programmingBooks = new Category("Программирование", books);

        categoryService.save(fiction);
        categoryService.save(scienceBooks);
        categoryService.save(programmingBooks);

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

        FileUtil fileUtil = new FileUtil();

        /* --- Book Items --- */

        String description = "Говард Филлипс Лавкрафт, не опубликовавший при жизни ни одной книги, " +
                "стал маяком и ориентиром жанра литературы ужасов, кумиром как широких читательских масс, " +
                "так и рафинированных интеллектуалов. Влияние его признавали такие мастера, как Борхес, " +
                "и такие кумиры миллионов, как Стивен Кинг, его рассказы неоднократно экранизировались, " +
                "а само имя писателя стало нарицательным. Франсуа Баранже — французский художник, дизайнер " +
                "компьютерных игр, концептуалист и футурист. Мечту оформить Лавкрафта он вынашивал много лет — " +
                "и вот наконец мечта сбылась: вашему вниманию предлагается классическая повесть «Зов Ктулху» с " +
                "иллюстрациями французского мастера. Наконец вы воочию увидите, что может быть, если Ктулху проснется…";
        String characteristics = "Автор....................Лавкрафт Г.Ф.\n" +
                "Издательство.............Азбука\n" +
                "Страниц..................64\n" +
                "Переплёт.................Твёрдый\n" +
                "ISBN.....................978-5-389-17639-3";
        String code = UUID.randomUUID().toString().substring(0, 8);
        Item callOfCthulhu = new Item("Зов Ктулху", 3000L, 0.57D, 800D, description, characteristics, code, fiction);
        File image = new File("static/images/InitBooks/callOfCthulhu.jpg");
        callOfCthulhu.setImage(fileUtil.fileToBytes(image));

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
        characteristics = "Автор....................Михаэлидес Алекс\n" +
                "Издательство.............Эксмо\n" +
                "Страниц..................352\n" +
                "Переплёт.................Мягкий\n" +
                "ISBN.....................978-5-04-105311-6";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item silentPatient = new Item("Безмолвный пациент", 1500L, 0.294D, 390D, description, characteristics, code, fiction);
        image = new File("static/images/InitBooks/silentPatient.jpg");
        silentPatient.setImage(fileUtil.fileToBytes(image));

        description = "Книга знакомит читателя с творчеством известного английского писателя Артура Конан Дойла. " +
                "На страницах книги вы встретитесь со знакомыми персонажами и сможете проследить за раскрытием таинственных преступлений.";
        characteristics = "Автор....................Конан Дойл Артур\n" +
                "Издательство.............Самовар\n" +
                "Страниц..................175\n" +
                "Переплёт.................Твёрдый\n" +
                "ISBN.....................978-5-9781-0119-5";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item sherlockHolmesTales = new Item("Рассказы о Шерлоке Холмсе", 1500L, 0.246D, 150D, description, characteristics, code, fiction);
        image = new File("static/images/InitBooks/sherlockHolmesTales.jpg");
        sherlockHolmesTales.setImage(fileUtil.fileToBytes(image));

        description = "Юрий Никулин, великий артист, замечательный клоун и чрезвычайно остроумный собеседник, " +
                "очень трепетно относился к читателям своих книжек. Он к ним обращался только на «вы» и просил принять " +
                "его самые лучшие пожелания. Он и анекдоты, которых знал тысячи, старался подбирать на любой вкус. Он был уверен, " +
                "что каждый найдет «свой анекдот» и станет смеяться, а то и просто сильно хохотать. Потом анекдот нужно запомнить и " +
                "рассказывать всем везде и всюду, но хорошо бы «к месту»: от этого анекдот только выиграет. А если кто-то, утверждал " +
                "Юрий Никулин, просто улыбнется, то и это будет очень хорошо и просто прекрасно. Итогом же прочтения этой книги " +
                "будет самый лучший и самый остроумный анекдот Юрия Никулина.";
        characteristics = "Автор....................Никулин Юрий Владимирович\n" +
                "Издательство.............Зебра Е\n" +
                "Страниц..................416\n" +
                "Переплёт.................Твёрдый\n" +
                "ISBN.....................978-5-907164-32-1";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item jokesFromNikulin = new Item("Анекдоты от Никулина", 3000L, 0.449D, 500D, description, characteristics, code, fiction);
        image = new File("static/images/InitBooks/jokesFromNikulin.jpg");
        jokesFromNikulin.setImage(fileUtil.fileToBytes(image));

        description = "Старший лейтенант Ибрагим Крушинин командует ротой спецназа на Северном Кавказе. " +
                "Он смел и беспощаден в бою. Ядовит – как шутят сослуживцы. Не случайно за старлеем закрепился " +
                "позывной Анчар. Во время очередной операции группа Крушинина попадает в засаду. Ибрагим подрывается " +
                "на мине и, раненный, оказывается в плену у бандитов. Неожиданно в главаре моджахедов он узнает своего " +
                "старшего брата, которого потерял в раннем детстве. Что делать – уничтожить бандитского эмира, " +
                "захватить его в плен или… Времени на размышления у Анчара не остается: на помощь своему командиру уже спешат бойцы спецназа…";
        characteristics = "Автор....................Самаров Сергей Васильевич\n" +
                "Издательство.............Эксмо\n" +
                "Страниц..................352\n" +
                "Переплёт.................Твёрдый\n" +
                "ISBN.....................978-5-04-109399-0";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item likeTwoDropsOfBlood = new Item("Как две капли крови", 2400L, 0.262D, 400D, description, characteristics, code, fiction);
        image = new File("static/images/InitBooks/likeTwoDropsOfBlood.jpg");
        likeTwoDropsOfBlood.setImage(fileUtil.fileToBytes(image));

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
        characteristics = "Автор....................Огами Т.\n" +
                "Издательство.............ДМК Пресс\n" +
                "Страниц..................130\n" +
                "Переплёт.................Мягкий\n" +
                "ISBN.....................978-5-97060-814-2";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item derivativesAndIntegrals = new Item("Производные и интегралы", 5000L, 0.15D, 850D, description, characteristics, code, scienceBooks);
        image = new File("static/images/InitBooks/derivativesAndIntegrals.jpg");
        derivativesAndIntegrals.setImage(fileUtil.fileToBytes(image));

        description = "Книга представляет собой учебник по функциональному анализу. " +
                "Этот учебник годится для первоначального изучения линейного функционального анализа, " +
                "но будет полезен и для углубленного изучения, поскольку содержит материал, который " +
                "обычно не включают в учебники по функциональному анализу. Несмотря на краткое изложение, " +
                "в учебнике все теоремы приведены с полными доказательствами. Многие понятия и утверждения " +
                "демонстрируются на модельных примерах. Книга будет полезна студентам и аспирантам, а " +
                "также всем желающим познакомиться с современной абстрактной математикой.\n";
        characteristics = "Автор....................Шамин Р.В.\n" +
                "Издательство.............URSS\n" +
                "Страниц..................272\n" +
                "Переплёт.................Мягкий\n" +
                "ISBN.....................978-5-9710-7813-5";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item functionalAnalysisFromZeroToUnits = new Item("Функциональный анализ от нуля до единиц", 4000L, 0.29D, 520D, description, characteristics, code, scienceBooks);
        image = new File("static/images/InitBooks/functionalAnalysisFromZeroToUnits.jpg");
        functionalAnalysisFromZeroToUnits.setImage(fileUtil.fileToBytes(image));

        description = "Практическое руководство по хирургии содержит современную и актуальную " +
                "информацию о диагностике, лечении и профилактике основных заболеваний и " +
                "синдромов, наиболее часто встречающихся в практике врача-хирурга амбулаторного звена.\n" +
                "Предназначено врачам-хирургам, врачам общей практики, клиническим ординаторам " +
                "и студентам старших курсов медицинских вузов.\n" +
                "Книга имеет электронную версию, активировав доступ к которой можно получить " +
                "дополнительные информационные материалы (уточняющие рекомендации, развернутые " +
                "речевые модули, нюансы взаимодействия лекарственных препаратов).\n";
        characteristics = "Автор....................Шабунин А.В., Маер Р.Ю.\n" +
                "Издательство.............ГЭОТАР-Медиа\n" +
                "Страниц..................296\n" +
                "Переплёт.................Мягкий\n" +
                "ISBN.....................978-5-9704-5523-4";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item surgeonTactics = new Item("Тактика врача-хирурга", 800L, 0.65D, 1780D, description, characteristics, code, scienceBooks);
        image = new File("static/images/InitBooks/surgeonTactics.jpg");
        surgeonTactics.setImage(fileUtil.fileToBytes(image));

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
        characteristics = "Автор....................Ватанабэ Д.\n" +
                "Издательство.............ДМК Пресс\n" +
                "Страниц..................128\n" +
                "Переплёт.................Мягкий\n" +
                "ISBN.....................978-5-97060-816-6";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item universeInQuestionsAndAnswers = new Item("Вселенная в вопросах и ответах", 800L, 0.14D, 852D, description, characteristics, code, scienceBooks);
        image = new File("static/images/InitBooks/universeInQuestionsAndAnswers.jpg");
        universeInQuestionsAndAnswers.setImage(fileUtil.fileToBytes(image));

        description = "\"Книга о пути жизни\" Лао-цзы, называемая по-китайски \"Дао-Дэ цзин\", занимает " +
                "после Библии второе место в мире по числу иностранных переводов. Происхождение этой " +
                "книги и личность ее автора окутаны множеством легенд, о которых известный переводчик " +
                "Владимир Малявин подробно рассказывает в своем предисловии. Само слово \"дао\" означает " +
                "путь, и притом одновременно путь мироздания, жизни и человеческого совершенствования. " +
                "А \"дэ\" – это внутренняя полнота жизни, незримо, но прочно связывающая все живое. " +
                "Секрет чтения Лао-цзы в том, чтобы постичь ту внутреннюю глубину смысла, которую внушает " +
                "мудрость, открывая в каждом суждении иной и противоположный смысл.\n";
        characteristics = "Автор....................Лао-цзы\n" +
                "Издательство.............АСТ\n" +
                "Страниц..................288\n" +
                "Переплёт.................Твёрдый\n" +
                "ISBN.....................978-5-17-122669-5";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item bookAboutTheWayOfLife = new Item("Книга о пути жизни", 600L, 0.28D, 370D, description, characteristics, code, scienceBooks);
        image = new File("static/images/InitBooks/bookAboutTheWayOfLife.jpg");
        bookAboutTheWayOfLife.setImage(fileUtil.fileToBytes(image));

        itemService.save(derivativesAndIntegrals);
        itemService.save(functionalAnalysisFromZeroToUnits);
        itemService.save(surgeonTactics);
        itemService.save(universeInQuestionsAndAnswers);
        itemService.save(bookAboutTheWayOfLife);

        description = "Учебно-практическое пособие охватывает первую, базовую, часть " +
                "учебного курса по языку SQL, созданного при участии российской компании " +
                "Postgres Professional. Учебный материал излагается в расчете на использование " +
                "системы управления базами данных PostgreSQL. Рассмотрено создание рабочей " +
                "среды, описан язык определения данных и основные операции выборки и изменения " +
                "данных. Показаны примеры использования транзакций, уделено внимание методам " +
                "оптимизации запросов. Материал сопровождается многочисленными практическими " +
                "примерами. Пособие может использоваться как для самостоятельного обучения, " +
                "так и проведения занятий под руководством преподавателя.";
        characteristics = "Автор....................Моргунов Е.П.\n" +
                "Издательство.............БХВ-Петербург\n" +
                "Страниц..................336\n" +
                "Переплёт.................Мягкий\n" +
                "ISBN.....................978-5-9775-4022-3";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item postgreSQL = new Item("PostgreSQL. Основы языка SQL",900L, 0.35D, 690D, description, characteristics, code, programmingBooks);
        image = new File("static/images/InitBooks/postgreSQL.jpg");
        postgreSQL.setImage(fileUtil.fileToBytes(image));

        description = "Эта книга воплощает знания и опыт работы авторов с каркасом Spring " +
                "Framework и сопутствующими технологиями удаленного взаимодействия, " +
                "Hibernate, EJB и пр. Она дает возможность читателю не только усвоить " +
                "основные понятия и принципы работы с Spring Framework, но и научиться " +
                "рационально пользоваться этим каркасом для построения различных уровней и частей корпоративных приложений на языке Java, включая обработку транзакций, представление веб-содержимого и прочего содержимого, развертывание и многое другое. Полноценные примеры подобных приложений, представленные в этой книге, наглядно демонстрируют особенности совместного применения различных технологий и методик разработки приложений в Spring.\n" +
                "Пятое издание этой книги, давно уже пользующейся успехом у читателей, " +
                "обновлено по новой версии Spring Framework 5 и является самым " +
                "исчерпывающим и полным руководством по применению Spring среди всех " +
                "имеющихся. В нем представлен новый функциональный каркас веб-приложений, " +
                "микрослужбы, совместимость с версией Java 9 и прочие функциональные " +
                "возможности Spring. Прочитав эту обстоятельную книгу, вы сможете включить в " +
                "арсенал своих средств весь потенциал Spring для основательного построения " +
                "сложных приложений. Гибкий, легковесный каркас Spring Framework с открытым " +
                "кодом продолжает оставаться фактически ведущим в области разработки корпоративных " +
                "приложений на языке Java и самым востребованным среди разработчиков и " +
                "программирующих на Java. Он превосходно взаимодействует с другими гибкими, " +
                "легковесными технологиями Java с открытым кодом, включая Hibernate, Groovy, " +
                "MyBatis и прочие, а также с платформами Java ЕЕ и JPA 2.\n" +
                "Эта книга поможет вам:\n" +
                "• Выявить новые функциональные возможности в версии Spring Framework 5\n" +
                "• Научиться пользоваться Spring Framework вместе с Java 9\n" +
                "• Овладеть механизмом доступа к данным и обработки транзакций\n" +
                "• Освоить новый функциональный каркас веб-приложений\n" +
                "• Научиться создавать микрослужбы и другие веб-службы";
        characteristics = "Автор....................Харроп Роб, Шефер Крис, Козмина Юлиана\n" +
                "Издательство.............Диалектика / Вильямс\n" +
                "Страниц..................1120\n" +
                "Переплёт.................Твёрдый\n" +
                "ISBN.....................978-5-907114-07-4, 978-1-4842-2807-4";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item spring5ForProfessionals = new Item("Spring 5 для профессионалов", 300L, 1.592D, 4000D, description, characteristics, code, programmingBooks);
        image = new File("static/images/InitBooks/spring5ForProfessionals.jpg");
        spring5ForProfessionals.setImage(fileUtil.fileToBytes(image));

        description = "Эта книга представляет собой обновленное руководство по использованию Git в " +
                "современных условиях. С тех пор как проект Git — распределенная система " +
                "управления версиями — был создан Линусом Торвальдсом, прошло много лет, и " +
                "система Git превратилась в доминирующую систему контроля версий, как для " +
                "коммерческих целей, так и для проектов с открытым исходным кодом. Эффективный и " +
                "хорошо реализованный контроль версий необходим для любого успешного веб-проекта. " +
                "Постепенно эту систему приняли на вооружение практически все сообщества разработчиков " +
                "ПО с открытым исходным кодом. Появление огромного числа графических интерфейсов для всех " +
                "платформ и поддержка IDE позволили внедрить Git в операционные системы семейства Windows. " +
                "Второе издание книги было обновлено для Git-версии 2.0 и уделяет большое внимание GitHub.";
        characteristics = "Автор....................Чакон Скотт, Штрауб Бен\n" +
                "Издательство.............Питер\n" +
                "Страниц..................496\n" +
                "Переплёт.................Мягкий\n" +
                "ISBN.....................978-5-496-01763-3, 978-5-4461-1131-2";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item proGit = new Item("Git для профессионального программиста", 700L, 0.51D, 1075D, description, characteristics, code, programmingBooks);
        image = new File("static/images/InitBooks/proGit.jpg");
        proGit.setImage(fileUtil.fileToBytes(image));

        description = "Более 10 лет первое издание этой книги считалось одним из " +
                "лучших практических руководств по программированию. Сейчас эта книга " +
                "полностью обновлена с учетом современных тенденций и технологий и " +
                "дополнена сотнями новых примеров, иллюстрирующих искусство и науку " +
                "программирования. Опираясь на академические исследования, с одной " +
                "стороны, и практический опыт коммерческих разработок ПО — с другой, " +
                "автор синтезировал из самых эффективных методик и наиболее эффективных " +
                "принципов ясное прагматичное руководство. Каков бы ни был ваш " +
                "профессиональный уровень, с какими бы средствами разработками вы ни " +
                "работали, какова бы ни была сложность вашего проекта, в этой книге " +
                "вы найдете нужную информацию, она заставит вас размышлять и поможет создать совершенный код.";
        characteristics = "Автор....................Макконнелл Стив\n" +
                "Издательство.............Русская Редакция\n" +
                "Страниц..................896\n" +
                "Переплёт.................Твёрдый\n" +
                "ISBN.....................978-5-7502-0064-1, 978-5-9909805-1-8";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item perfectCode = new Item("Совершенный код", 900L, 1.201D, 1275D, description, characteristics, code, programmingBooks);
        image = new File("static/images/InitBooks/perfectCode.jpg");
        perfectCode.setImage(fileUtil.fileToBytes(image));

        description = "Потоки являются фундаментальной частью платформы Java. " +
                "Многоядерные процессоры — это обыденная реальность, а эффективное " +
                "использование параллелизма стало необходимым для создания любого " +
                "высокопроизводительного приложения. Улучшенная виртуальная машина " +
                "Java, поддержка высокопроизводительных классов и богатый набор строительных " +
                "блоков для задач распараллеливания стали в свое время прорывом в разработке " +
                "параллельных приложений. В «Java Concurrency на практике» сами создатели " +
                "прорывной технологии объясняют не только принципы работы, но и рассказывают о паттернах проектирования.\n" +
                "Легко создать конкурентную программу, которая вроде бы будет работать. " +
                "Однако разработка, тестирование и отладка многопоточных программ " +
                "доставляют много проблем. Код перестает работать именно тогда, как это " +
                "важнее всего: при большой нагрузке. В «Java Concurrency на практике» вы " +
                "найдете как теорию, так и конкретные методы создания надежных, масштабируемых " +
                "и поддерживаемых параллельных приложений. Авторы не предлагают перечень API и " +
                "механизмов параллелизма, они знакомят с правилами проектирования, паттернами и " +
                "моделями, которые не зависят от версии Java и на протяжении многих лет остаются актуальными и эффективными.";
        characteristics = "Автор....................Гетц Б.\n" +
                "Издательство.............Питер\n" +
                "Страниц..................464\n" +
                "Переплёт.................Мягкий\n" +
                "ISBN.....................978-5-4461-1314-9";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item javaConcurrencyInPractice = new Item("Java Concurrency на практике", 1500L, 0.48D, 1430D, description, characteristics, code, programmingBooks);
        image = new File("static/images/InitBooks/javaConcurrencyInPractice.jpg");
        javaConcurrencyInPractice.setImage(fileUtil.fileToBytes(image));

        itemService.save(postgreSQL);
        itemService.save(spring5ForProfessionals);
        itemService.save(proGit);
        itemService.save(perfectCode);
        itemService.save(javaConcurrencyInPractice);

        /* --- --- */

        /* --- Stationery Items --- */

        description = "18 листов.\n" +
                "Формат: А6.";
        characteristics = "Издательство....................Шанс" +
                "Цвет............................Серый" +
                "Разметка........................Без линковки" +
                "Количество листов...............18" +
                "Формат..........................А6" +
                "Внутренний блок.................Белый" +
                "ISBN............................978-5-907173-81-1";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item notebookLandscape = new Item("Блокнот \"Пейзаж\"", 700L, 0.07D, 50D, description, characteristics, code, notebooks);
        image = new File("static/images/InitStationery/notebookLandscape.jpg");
        notebookLandscape.setImage(fileUtil.fileToBytes(image));

        description = "Блокнот на картонной подложке.\n" +
                "28 листов.\n" +
                "Формат: 115х165 мм.";
        characteristics = "Издательство....................Шанс" +
                "Цвет............................Мультиколор" +
                "Разметка........................Без линковки" +
                "Крепление.......................Скрепка" +
                "Количество листов...............28" +
                "Формат..........................А6" +
                "Внутренний блок.................Белый";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item notebookDragonfly = new Item("Блокнот \"Стрекоза\"", 700L, 0.07D, 100D, description, characteristics, code, notebooks);
        image = new File("static/images/InitStationery/notebookDragonfly.jpg");
        notebookDragonfly.setImage(fileUtil.fileToBytes(image));

        description = "18 листов.\n" +
                "Формат: А6.";
        characteristics = "Издательство....................Шанс" +
                "Цвет............................Красный" +
                "Разметка........................Без линковки" +
                "Крепление.......................Скрепка" +
                "Количество листов...............18" +
                "Формат..........................А6" +
                "Внутренний блок.................Белый" +
                "ISBN............................978-5-907173-77-4";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item notebookMillenniumFlowers = new Item("Блокнот \"Тысячелетние цветы\"", 700L, 0.07D, 50D, description, characteristics, code, notebooks);
        image = new File("static/images/InitStationery/notebookMillenniumFlowers.jpg");
        notebookMillenniumFlowers.setImage(fileUtil.fileToBytes(image));

        description = "Тетрадь школьная ученическая формата А5 с обложкой из высококачественного " +
                "мелованного картона плотностью 170 г/м2. В блоке 12 листов в клетку с полями. " +
                "Бумага офсетная плотностью 60 г/м2, белая. Цвет линовки - синий. Скругленные " +
                "уголки обложки и блока. Тип скрепления - скоба. Качественная тетрадь - залог успешной учёбы ребенка!\n" +
                "Размер тетради: 170х203 мм.";
        characteristics = "Производитель...................ErichKrause" +
                "Крепление.......................Скрепка" +
                "Количество листов...............12" +
                "Формат..........................А5" +
                "Разметка........................Клетка" +
                "Особенности.....................С полями";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item notebookSchoolCage = new Item("Тетрадь школьная, 12 листов, клетка, зеленая", 5000L, 0.038D, 20D, description, characteristics, code, notebooks);
        image = new File("static/images/InitStationery/notebookSchoolCage.png");
        notebookSchoolCage.setImage(fileUtil.fileToBytes(image));

        description = "Формат: А5.\n" +
                "Количество листов: 12, в частую косую линейку.\n" +
                "Материал обложки: бумага.\n" +
                "Скрепление: на скобе.\n" +
                "Офсет.";
        characteristics = "Производитель...................Hatber" +
                "Крепление.......................Скрепка" +
                "Количество листов...............12" +
                "Формат..........................А5" +
                "Разметка........................Косая линейка" +
                "Особенности.....................С полями";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item notebookSchoolSlantingRuler = new Item("Тетрадь школьная, 12 листов, клетка, зеленая", 5000L, 0.035D, 20D, description, characteristics, code, notebooks);
        image = new File("static/images/InitStationery/notebookSchoolSlantingRuler.png");
        notebookSchoolSlantingRuler.setImage(fileUtil.fileToBytes(image));

        description = "Серия стильных альбомов с повышенной плотностью бумаги станет " +
                "помощником в развитии творческих способностей! 25 листов плотной белой " +
                "бумаги позволят рисовать всем, чем хотите: от карандаша и маркера до " +
                "акварели! Выбирайте лучшее!\n" +
                "Формат А4, офсет 160 гр., 50 страниц, евроспираль.";
        characteristics = "Производитель...................Бомбора (Эксмо)" +
                "Редактор........................Сабанова Залина Олеговна" +
                "Крепление.......................Спираль, гребень" +
                "Количество листов...............25" +
                "Формат..........................А4" +
                "Цвет............................Мульти" +
                "Тип бумаги......................Офсетная" +
                "Плотность бумаги, г/м2..........160" +
                "ISBN............................978-5-04-095366-0";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item designArtSketchbook = new Item("Design Art. Альбом для рисования", 2000L, 0.298D, 250D, description, characteristics, code, notebooks);
        image = new File("static/images/InitStationery/designArtSketchbook.jpg");
        designArtSketchbook.setImage(fileUtil.fileToBytes(image));

        itemService.save(notebookLandscape);
        itemService.save(notebookDragonfly);
        itemService.save(notebookMillenniumFlowers);
        itemService.save(notebookSchoolCage);
        itemService.save(notebookSchoolSlantingRuler);
        itemService.save(designArtSketchbook);

        description = "Материал корпуса: цветной пластик.\n" +
                "Пишущий узел: 0,7 мм.\n" +
                "Сменный стержень.\n" +
                "Цвет чернил: синий.\n";
        characteristics = "Производитель...................Erichkrause" +
                "Цвет............................Оранжевый" +
                "Диаметр пишущего узла, мм.......0.7" +
                "Цвет чернил.....................Синий" +
                "Тип.............................Шариковые" +
                "Форма корпуса...................Шестигранная";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item ballpointPenR301OrangeStick = new Item("Ручка шариковая \"R-301 Orange Stick\", 0,7 мм, синие чернила", 5000L, 0.006D, 15D, description, characteristics, code, writing);
        image = new File("static/images/InitStationery/ballpointPenR301OrangeStick.jpg");
        ballpointPenR301OrangeStick.setImage(fileUtil.fileToBytes(image));

        description = "Шариковая ручка \"Pilot BPS\" одна из самых известных моделей. " +
                "Удобный прорезиненный трехгранный уплотнитель позволяет не выскальзывать " +
                "ручке при интенсивном письме. Чернила на масляной основе подаются к " +
                "пишущему узлу непрерывным слоем. Благодаря прозрачному корпусу можно " +
                "проследить уровень количества чернил и заменить при необходимости " +
                "стержень. Плотно закрывающийся колпачок защитит от высыхания и " +
                "протекания чернил. Шариковая ручка \"Pilot BPS\" обеспечит качественное " +
                "и ровное письмо, и станет незаменимым помощником в учёбе, офисе или дома.\n" +
                "Пишущий узел: 0,7 мм.\n" +
                "Толщина линии: 0,32 мм.\n" +
                "Диаметр корпуса: 9,2 мм.";
        characteristics = "Производитель...................Pilot" +
                "Цвет............................Прозрачный" +
                "Диаметр пишущего узла, мм.......0.7" +
                "Цвет чернил.....................Синий" +
                "Тип.............................Шариковые" +
                "Форма корпуса...................Круглая";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item ballpointPenPilotBPS = new Item("Ручка шариковая \"Pilot BPS\", синяя", 5000L, 0.025D, 50D, description, characteristics, code, writing);
        image = new File("static/images/InitStationery/ballpointPenPilotBPS.jpg");
        ballpointPenPilotBPS.setImage(fileUtil.fileToBytes(image));

        itemService.save(ballpointPenR301OrangeStick);
        itemService.save(ballpointPenPilotBPS);

        /* --- --- */
    }
}
