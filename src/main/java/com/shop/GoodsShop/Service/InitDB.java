package com.shop.GoodsShop.Service;

import com.shop.GoodsShop.Model.*;
import com.shop.GoodsShop.Utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.util.*;

@Service
@Transactional
public class InitDB {
    Logger logger = LoggerFactory.getLogger(InitDB.class);

    private CategoryService categoryService;
    private ItemService itemService;
    private ClientService clientService;
    private OrderService orderService;
    private OrderedItemService orderedItemService;

    @PersistenceContext
    private EntityManager entityManager;

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

    @Autowired
    public void setOrderedItemService(OrderedItemService orderedItemService) {
        this.orderedItemService = orderedItemService;
    }

    public void init() {
        logger.info("Initializing database...");

        logger.debug("Initialize categories");
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

        Category electronics = new Category("Электроника");
        categoryService.save(electronics);

        /* --- Electronics Categories --- */

        Category computers = new Category("Компьютеры", electronics);
        Category peripherals = new Category("Периферия", electronics);

        categoryService.save(computers);
        categoryService.save(peripherals);

        /* --- --- */

        FileUtil fileUtil = new FileUtil();

        logger.debug("Initialize books");
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
        File image = new File("src/main/resources/static/images/InitBooks/callOfCthulhu.jpg");
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
        image = new File("src/main/resources/static/images/InitBooks/silentPatient.jpg");
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
        image = new File("src/main/resources/static/images/InitBooks/sherlockHolmesTales.jpg");
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
        image = new File("src/main/resources/static/images/InitBooks/jokesFromNikulin.jpg");
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
        image = new File("src/main/resources/static/images/InitBooks/likeTwoDropsOfBlood.jpg");
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
        image = new File("src/main/resources/static/images/InitBooks/derivativesAndIntegrals.jpg");
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
        image = new File("src/main/resources/static/images/InitBooks/functionalAnalysisFromZeroToUnits.jpg");
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
        image = new File("src/main/resources/static/images/InitBooks/surgeonTactics.jpg");
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
        image = new File("src/main/resources/static/images/InitBooks/universeInQuestionsAndAnswers.jpg");
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
        image = new File("src/main/resources/static/images/InitBooks/bookAboutTheWayOfLife.jpg");
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
        image = new File("src/main/resources/static/images/InitBooks/postgreSQL.jpg");
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
        image = new File("src/main/resources/static/images/InitBooks/spring5ForProfessionals.jpg");
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
        image = new File("src/main/resources/static/images/InitBooks/proGit.jpg");
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
        image = new File("src/main/resources/static/images/InitBooks/perfectCode.jpg");
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
        image = new File("src/main/resources/static/images/InitBooks/javaConcurrencyInPractice.jpg");
        javaConcurrencyInPractice.setImage(fileUtil.fileToBytes(image));

        itemService.save(postgreSQL);
        itemService.save(spring5ForProfessionals);
        itemService.save(proGit);
        itemService.save(perfectCode);
        itemService.save(javaConcurrencyInPractice);

        /* --- --- */

        logger.debug("Initialize stationery items");
        /* --- Stationery Items --- */

        description = "18 листов.\n" +
                "Формат: А6.";
        characteristics = "Издательство....................Шанс\n" +
                "Цвет............................Серый\n" +
                "Разметка........................Без линковки\n" +
                "Количество листов...............18\n" +
                "Формат..........................А6\n" +
                "Внутренний блок.................Белый\n" +
                "ISBN............................978-5-907173-81-1";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item notebookLandscape = new Item("Блокнот \"Пейзаж\"", 700L, 0.07D, 50D, description, characteristics, code, notebooks);
        image = new File("src/main/resources/static/images/InitStationery/notebookLandscape.jpg");
        notebookLandscape.setImage(fileUtil.fileToBytes(image));

        description = "Блокнот на картонной подложке.\n" +
                "28 листов.\n" +
                "Формат: 115х165 мм.";
        characteristics = "Издательство....................Шанс\n" +
                "Цвет............................Мультиколор\n" +
                "Разметка........................Без линковки\n" +
                "Крепление.......................Скрепка\n" +
                "Количество листов...............28\n" +
                "Формат..........................А6\n" +
                "Внутренний блок.................Белый";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item notebookDragonfly = new Item("Блокнот \"Стрекоза\"", 700L, 0.07D, 100D, description, characteristics, code, notebooks);
        image = new File("src/main/resources/static/images/InitStationery/notebookDragonfly.jpg");
        notebookDragonfly.setImage(fileUtil.fileToBytes(image));

        description = "18 листов.\n" +
                "Формат: А6.";
        characteristics = "Издательство....................Шанс\n" +
                "Цвет............................Красный\n" +
                "Разметка........................Без линковки\n" +
                "Крепление.......................Скрепка\n" +
                "Количество листов...............18\n" +
                "Формат..........................А6\n" +
                "Внутренний блок.................Белый\n" +
                "ISBN............................978-5-907173-77-4";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item notebookMillenniumFlowers = new Item("Блокнот \"Тысячелетние цветы\"", 700L, 0.07D, 50D, description, characteristics, code, notebooks);
        image = new File("src/main/resources/static/images/InitStationery/notebookMillenniumFlowers.jpg");
        notebookMillenniumFlowers.setImage(fileUtil.fileToBytes(image));

        description = "Тетрадь школьная ученическая формата А5 с обложкой из высококачественного " +
                "мелованного картона плотностью 170 г/м2. В блоке 12 листов в клетку с полями. " +
                "Бумага офсетная плотностью 60 г/м2, белая. Цвет линовки - синий. Скругленные " +
                "уголки обложки и блока. Тип скрепления - скоба. Качественная тетрадь - залог успешной учёбы ребенка!\n" +
                "Размер тетради: 170х203 мм.";
        characteristics = "Производитель...................ErichKrause\n" +
                "Крепление.......................Скрепка\n" +
                "Количество листов...............12\n" +
                "Формат..........................А5\n" +
                "Разметка........................Клетка\n" +
                "Особенности.....................С полями";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item notebookSchoolCage = new Item("Тетрадь школьная, 12 листов, клетка, зеленая", 5000L, 0.038D, 20D, description, characteristics, code, notebooks);
        image = new File("src/main/resources/static/images/InitStationery/notebookSchoolCage.png");
        notebookSchoolCage.setImage(fileUtil.fileToBytes(image));

        description = "Формат: А5.\n" +
                "Количество листов: 12, в частую косую линейку.\n" +
                "Материал обложки: бумага.\n" +
                "Скрепление: на скобе.\n" +
                "Офсет.";
        characteristics = "Производитель...................Hatber\n" +
                "Крепление.......................Скрепка\n" +
                "Количество листов...............12\n" +
                "Формат..........................А5\n" +
                "Разметка........................Косая линейка\n" +
                "Особенности.....................С полями";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item notebookSchoolSlantingRuler = new Item("Тетрадь школьная, 12 листов, клетка, зеленая", 5000L, 0.035D, 20D, description, characteristics, code, notebooks);
        image = new File("src/main/resources/static/images/InitStationery/notebookSchoolSlantingRuler.png");
        notebookSchoolSlantingRuler.setImage(fileUtil.fileToBytes(image));

        description = "Серия стильных альбомов с повышенной плотностью бумаги станет " +
                "помощником в развитии творческих способностей! 25 листов плотной белой " +
                "бумаги позволят рисовать всем, чем хотите: от карандаша и маркера до " +
                "акварели! Выбирайте лучшее!\n" +
                "Формат А4, офсет 160 гр., 50 страниц, евроспираль.";
        characteristics = "Производитель...................Бомбора (Эксмо)\n" +
                "Редактор........................Сабанова Залина Олеговна\n" +
                "Крепление.......................Спираль, гребень\n" +
                "Количество листов...............25\n" +
                "Формат..........................А4\n" +
                "Цвет............................Мульти\n" +
                "Тип бумаги......................Офсетная\n" +
                "Плотность бумаги, г/м2..........160\n" +
                "ISBN............................978-5-04-095366-0";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item designArtSketchbook = new Item("Design Art. Альбом для рисования", 2000L, 0.298D, 250D, description, characteristics, code, notebooks);
        image = new File("src/main/resources/static/images/InitStationery/designArtSketchbook.jpg");
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
        characteristics = "Производитель...................Erichkrause\n" +
                "Цвет............................Оранжевый\n" +
                "Диаметр пишущего узла, мм.......0.7\n" +
                "Цвет чернил.....................Синий\n" +
                "Тип.............................Шариковые\n" +
                "Форма корпуса...................Шестигранная";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item ballpointPenR301OrangeStick = new Item("Ручка шариковая \"R-301 Orange Stick\", 0,7 мм, синие чернила", 5000L, 0.006D, 15D, description, characteristics, code, writing);
        image = new File("src/main/resources/static/images/InitStationery/ballpointPenR301OrangeStick.jpg");
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
        characteristics = "Производитель...................Pilot\n" +
                "Цвет............................Прозрачный\n" +
                "Диаметр пишущего узла, мм.......0.7\n" +
                "Цвет чернил.....................Синий\n" +
                "Тип.............................Шариковые\n" +
                "Форма корпуса...................Круглая";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item ballpointPenPilotBPS = new Item("Ручка шариковая \"Pilot BPS\", синяя", 5000L, 0.025D, 50D, description, characteristics, code, writing);
        image = new File("src/main/resources/static/images/InitStationery/ballpointPenPilotBPS.jpg");
        ballpointPenPilotBPS.setImage(fileUtil.fileToBytes(image));

        itemService.save(ballpointPenR301OrangeStick);
        itemService.save(ballpointPenPilotBPS);

        /* --- --- */

        logger.debug("Initialize electronic items");
        /* --- Electronic Items --- */

        description = "Тонкий дисплей с диагональю 35,56 см (14\"), FHD (1920x1080), IPS, eDP, антибликовое покрытие.\n" +
                "Процессор Intel Core i3-10110U (базовая частота 2,1 ГГц, до 4,1 ГГц при " +
                "использовании технологии Intel Turbo Boost, кэш-память 4 Мб, 2 ядра).\n" +
                "Графическая карта Intel UHD Graphics 620.\n" +
                "Память DDR4-2400 SDRAM, 8 Гб.\n" +
                "Твердотельный накопитель (SSD): 256 Гб, М.2, PCIe NVMe.\n" +
                "Оптический привод: нет.\n" +
                "Веб-камера, встроенные динамики, микрофон.\n" +
                "Клавиатура HP Premium Keyboard. Защита от проникновения воды с дополнительной функцией подсветки.\n" +
                "Сенсорная панель Clickpad с поддержкой мультисенсорного ввода.\n" +
                "Встроенная сетевая карта Realtek GbE 10/100/1000.\n" +
                "Комбинированный модуль беспроводной связи Intel AX201 Wi-Fi 6 (2x2) и Bluetooth 5.\n" +
                "Порты:\n" +
                "1 - разъем USB 3.1 Type-C Gen 1 (подача электропитания, DisplayPort).\n" +
                "2 - USB 3.1 Gen 1.\n" +
                "1 - USB 2.0 (порт с питанием).\n" +
                "1 - HDMI 1.4b.\n" +
                "1 - порт RJ-45/Ethernet.\n" +
                "1 - комбинированный разъем для наушников/микрофона.\n" +
                "1 - порт питания переменного тока.\n" +
                "1 - стандартный замок безопасности.\n" +
                "Аккумулятор HP большой емкости, 3-элементный, 45 Вт/ч, литий-ионный.\n" +
                "Размеры: 32,42x23,77x1,80 см.";
        characteristics = "Производитель...................Hewlett Packard (HP)\n" +
                "Цвет............................Серый\n" +
                "Страна изготовления.............Китай\n" +
                "Вес без упаковки................1600 г\n" +
                "Гарантийный срок................12 мес.";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item laptopHPProBook = new Item("Ноутбук HP ProBook 440 G7, 14\", Intel Core i3 10110U, 8192 Мб, Windows 10 Pro, арт. 9VZ38EA#ACB", 200L, 2.9D, 59320D, description, characteristics, code, computers);
        image = new File("src/main/resources/static/images/InitElectronics/laptopHPProBook.jpg");
        laptopHPProBook.setImage(fileUtil.fileToBytes(image));

        description = "Ноутбук Lenovo ThinkBook 14 разработан для тех, кому " +
                "приходится работать в дороге. Толщина устройства всего 17,9 мм, поэтому его удобно носить в рюкзаке или в руках.\n" +
                "Экран 14\", FHD (1920x1080), IPS, 250 nits, матовый.\n" +
                "Процессор Intel Core i5-1035G4 (1,1 ГГц, до 3,7 ГГц, 6 Мб кэш-память, 4 ядра).\n" +
                "Встроенная графика Intel Iris Plus Graphics.\n" +
                "Память 8 Гб, DDR4-2666.\n" +
                "Твердотельный накопитель (SSD): 512 Гб, M.2, 2242 PCIe NVMe 3.0x2.\n" +
                "Оптический привод: нет.\n" +
                "Дискретная видеокарта AMD Radeon 630 (2 Гб GDDR5).\n" +
                "Веб-камера 720p with ThinkShutter, встроенные динамики 2x2 Вт, микрофон.\n" +
                "4-in-1 media reader (MMC, SD, SDHC, SDXC).\n" +
                "Клавиатура с подсветкой. Сканер отпечатков пальцев.\n" +
                "Ethernet 100/1000M.\n" +
                "Беспроводные коммуникации: Wi-Fi 802.11ac 2x2, Bluetooth 5.0.\n" +
                "Порты: 1 - hidden USB 2.0, 2 - USB 3.1 Gen 1 (one with Always On),\n" +
                "1 - USB-C 3.1 Gen 1, 1 - USB-C 3.1 Gen 2 (with the function of DisplayPort and power delivery), HDMI 1.4b, Ethernet (RJ45).\n" +
                "Аккумулятор: 45 Вт/ч.\n" +
                "Размеры: 326x230x17.9 мм.";
        characteristics = "Производитель...................Lenovo\n" +
                "Цвет............................Серый\n" +
                "Страна изготовления.............Китай\n" +
                "Вес без упаковки................1500 г\n" +
                "Гарантийный срок................12 мес.";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item laptopLenovoThinkBook = new Item("Ноутбук Lenovo ThinkBook 14-IIL, 14.0\", Intel Core i5 1035G4, 8 Гб, DOS, арт. 20SL004BRU", 200L, 2.7D, 54220D, description, characteristics, code, computers);
        image = new File("src/main/resources/static/images/InitElectronics/laptopLenovoThinkBook.jpg");
        laptopLenovoThinkBook.setImage(fileUtil.fileToBytes(image));

        description = "Экран 39.6 см (15.6\"), HD (1366x768), светодиодная подсветка, технология ComfyView.\n" +
                "Процессор Intel Core i5-6300U с графическим ядром Intel UHD Graphics " +
                "520 (2,4 ГГц, до 3 ГГц в режиме Turbo Boost, 3 Мб кэш-памяти, 2 ядра).\n" +
                "Память 8 Гб, DDR4-2133.\n" +
                "Жесткий диск (HDD): 1 Тб, 5400 об./мин., SATA.\n" +
                "Оптический привод: нет.\n" +
                "Сетевой интерфейс: Gigabit Ethernet (10/100/1000 Мбит/с).\n" +
                "Беспроводные коммуникации: Wi-Fi (IEEE 802.11ac), Bluetooth 4.2.\n" +
                "Веб камера, встроенные стерео динамики, цифровой микрофон.\n" +
                "Порты: 2 - USB 2.0, USB 3.0 - 1, HDMI, RJ-45.\n" +
                "Аккумулятор: 2 ячейки, Li-Ion, 36,7 Вт/ч.\n" +
                "Размеры: 363.4x247.5x19.9 мм.\n";
        characteristics = "Производитель...................Acer\n" +
                "Цвет............................Чёрный\n" +
                "Страна изготовления.............Китай\n" +
                "Вес без упаковки................1900 г\n" +
                "Гарантийный срок................12 мес.";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item laptopAcerExtensa = new Item("Ноутбук Acer Extensa EX215-51K-55C5, 15.6\", Intel Core i5-6300U, 8 Гб, Windows 10 Home, арт. NX.EFPER.00Y", 200L, 3.3D, 52609D, description, characteristics, code, computers);
        image = new File("src/main/resources/static/images/InitElectronics/laptopAcerExtensa.jpg");
        laptopAcerExtensa.setImage(fileUtil.fileToBytes(image));

        itemService.save(laptopHPProBook);
        itemService.save(laptopLenovoThinkBook);
        itemService.save(laptopAcerExtensa);

        description = "Type of connection: wired.\n" +
                "Number of additional keys (functions): 12 (+ FN).\n" +
                "Keyboard Design: Classic.\n" +
                "Type of keyboard: mechanical.\n" +
                "Number of keys: 104 pcs.\n" +
                "Switches: Redragon + RED.\n" +
                "Number of clicks: 50 million.\n" +
                "Key design: scissor.\n" +
                "Size: 12.8x3.6x44.0 cm.\n" +
                "Weight: 1.1 kg.";
        characteristics = "Производитель...................Redragon\n" +
                "Цвет............................Чёрный\n" +
                "Материал........................Пластик\n" +
                "Гарантийный срок................18 мес.";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item dedragonSuraraMechanicalKeyboard = new Item("Механическая клавиатура Redragon Surara", 500L, 1.31D, 5000D, description, characteristics, code, peripherals);
        image = new File("src/main/resources/static/images/InitElectronics/dedragonSuraraMechanicalKeyboard.jpg");
        dedragonSuraraMechanicalKeyboard.setImage(fileUtil.fileToBytes(image));

        description = "Проводная клавиатура: да.\n" +
                "Тип клавиш: механические.\n" +
                "Сила нажатия: 45 г.\n" +
                "Цвет русской раскладки: белый.\n" +
                "Цвет английской раскладки: белый.\n" +
                "Цвет игровых клавиш: черный.\n" +
                "Программируемые клавиши: 5 штук.\n" +
                "Создание макросов: да.\n" +
                "Интерфейс связи с ПК: USB 2.0.\n" +
                "Порт USB 2.0 тип A: 1 шт.\n" +
                "Длина кабеля: 1.8 м.\n" +
                "Тканевая оплетка кабеля: да.\n" +
                "Материал корпуса: пластик/металл.\n" +
                "Цвет: черный.\n" +
                "Габаритные размеры (ВхШхГ): 2.2х47.5х15 см.\n" +
                "Вес: 1145 г.";
        characteristics = "Производитель...................Logitech\n" +
                "Цвет............................Чёрный\n" +
                "Материал........................Пластик, металл";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item keyboardGamingLogitech = new Item("Клавиатура игровая Logitech G815 Linear, арт. 920-009007", 500L, 1.2D, 16124D, description, characteristics, code, peripherals);
        image = new File("src/main/resources/static/images/InitElectronics/keyboardGamingLogitech.jpg");
        keyboardGamingLogitech.setImage(fileUtil.fileToBytes(image));

        itemService.save(dedragonSuraraMechanicalKeyboard);
        itemService.save(keyboardGamingLogitech);

        description = "Тип подключения: проводной.\n" +
                "Интерфейс подключения: USB.\n" +
                "Модель сенсора: Pixart P3325.\n" +
                "Количество кнопок: 9 + колесо-кнопка.\n" +
                "Количество нажатий: 20 миллионов.\n" +
                "Тип сенсора: IR-оптический.\n" +
                "Разрешение: 10000 dpi.\n" +
                "Совместимость: Windows 2000/XP/Vista/7/8/10, Mac OS X10.\n" +
                "Частота опроса: 1000 Гц.\n" +
                "Максимальное ускорение: 20 g.\n" +
                "Скорость IPS: 220 дюйм/с.\n" +
                "Переключатели: Omron.\n" +
                "Цвет: чёрный.\n" +
                "Размер: 9.0x4.25x12.0 см.\n" +
                "Вес: 0.25 кг.";
        characteristics = "Производитель...................Redragon\n" +
                "Цвет............................Чёрный\n" +
                "Материал........................Пластик\n" +
                "Гарантийный срок................18 мес.";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item wiredGamingMouseRedragonVampire = new Item("Мышь проводная игровая Redragon Vampire", 500L, 0.35D, 2150D, description, characteristics, code, peripherals);
        image = new File("src/main/resources/static/images/InitElectronics/wiredGamingMouseRedragonVampire.jpg");
        wiredGamingMouseRedragonVampire.setImage(fileUtil.fileToBytes(image));

        description = "Тип соединения: беспроводное.\n" +
                "Интерфейс подключения: USB.\n" +
                "Тип беспроводной связи: радиоканал.\n" +
                "Тип сенсора: оптический.\n" +
                "Дизайн мыши: для правой и левой руки.\n" +
                "Разрешение сенсора: 1600 dpi.\n" +
                "Количество клавиш: 3.\n" +
                "Источник питания мыши: 1xAA.\n" +
                "Цвет: синий.\n";
        characteristics = "Производитель...................Hewlett Packard (HP)\n" +
                "Цвет............................Синий\n" +
                "Тип элементов питания...........пальчиковые (AA; LR6; FR6; ZR6; R6; 316)\n" +
                "Количество элементов питания....1 шт.\n" +
                "Разрешение сенсора, dpi.........1600\n" +
                "Интерфейс подключения...........USB\n" +
                "Принцип работы..................Светодиодная";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item hpWirelessMouse = new Item("Компьютерная мышь HP Wireless Mouse 220 Blue, арт. 7KX11AA#ABB", 500L, 0.22D, 1462D, description, characteristics, code, peripherals);
        image = new File("src/main/resources/static/images/InitElectronics/hpWirelessMouse.jpg");
        hpWirelessMouse.setImage(fileUtil.fileToBytes(image));

        itemService.save(wiredGamingMouseRedragonVampire);
        itemService.save(hpWirelessMouse);

        description = "Модель подходит для работы дома и в офисе. Она предназначена " +
                "для комфортного общения через Интернет, записи слов и фраз при " +
                "дистанционном изучении иностранных языков и контроля правильности " +
                "их произношения. Высокая чувствительность и эффективная ширина " +
                "диаграммы направленности микрофона обеспечивают качественный " +
                "четкий звук во время разговора. \"Sven MK-150\" имеет клипсу, с " +
                "помощью которой микрофон крепится на одежду (например, галстук, " +
                "отворот или карман пиджака), а также съемный крепеж, предназначенный " +
                "для фиксации модели на столе или на корпусе монитора. Таким образом, " +
                "пользователь может освободить себя от лишних проводов и полностью " +
                "сконцентрироваться на общении. Демократичная цена модели особенно " +
                "порадует студентов и школьников.\n" +
                "Тип подключения: штекер 3,5 мм.\n" +
                "Длина шнура: 1,8 метра.\n" +
                "Частотный диапазон микрофона: 15-16 000 Гц.\n" +
                "Дальность действия: 1,8 метра.";
        characteristics = "Производитель...................Sven\n" +
                          "Страна изготовителя.............Китай";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item computerMicrophoneSvenMK150 = new Item("Микрофон компьютерный \"Sven MK-150\", цвет черный", 400L, 0.04D, 1500D, description, characteristics, code, peripherals);
        image = new File("src/main/resources/static/images/InitElectronics/computerMicrophoneSvenMK150.jpg");
        computerMicrophoneSvenMK150.setImage(fileUtil.fileToBytes(image));

        description = "Эта модель станет незаменимым помощником на Вашем рабочем " +
                "столе в случае, если Вы общаетесь через Интернет при помощи " +
                "различных программ-мессенджеров, участвуете в видеоконференциях, " +
                "дистанционно изучаете иностранные языки. Высокая чувствительность " +
                "микрофона гарантирует качественный четкий звук во время разговора " +
                "с собеседником, а также при записи голоса на ПК или ноутбук.\n" +
                "Устройство очень функционально и просто в использовании. Гибкая " +
                "ножка, на которой установлен микрофонный капсюль, позволяет менять " +
                "положение микрофона. Массивное основание обеспечивает устойчивость " +
                "модели. Управление очень удобное: включать или выключать микрофон " +
                "можно с помощью кнопки On/Off, расположенной на его основании, " +
                "без дополнительных действий на компьютере.\n" +
                "\"Sven MK-490\" выполнен в классическом черном цвете и имеет " +
                "элегантный дизайн. Модель будет гармонично смотреться рядом с " +
                "любым монитором ПК или ноутбуком.\n" +
                "Тип подключения: штекер 3,5 мм.\n" +
                "Длина шнура: 2,4 метра.\n" +
                "Частотный диапазон микрофона: 20-16 000 Гц.\n" +
                "Дальность действия: 2,4 метра.";
        characteristics = "Производитель...................Sven\n" +
                "Страна изготовителя.............Китай";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item computerMicrophoneSvenMK490 = new Item("Микрофон компьютерный \"Sven MK-490\", цвет черный", 400L, 0.22D, 730D, description, characteristics, code, peripherals);
        image = new File("src/main/resources/static/images/InitElectronics/computerMicrophoneSvenMK490.jpg");
        computerMicrophoneSvenMK490.setImage(fileUtil.fileToBytes(image));

        itemService.save(computerMicrophoneSvenMK150);
        itemService.save(computerMicrophoneSvenMK490);

        description = "Tenda C50S Беспроводная поворотная HD камера " +
                "(APP для Apple и Android, 720P@25fps, микрофон, поддержка SD/TF карт).\n" +
                "Компактная и стильная IP-камера Tenda C50s обеспечит " +
                "профессиональное видеонаблюдение внутри помещений. " +
                "Благодаря 1,3 Мпикс. CMOS-сенсору видеокамера обеспечит " +
                "видео HD-качества с разрешением 720p с высокой детализацией, четкостью и естественной " +
                "цветопередачей. Благодаря PTZ-функции видеокамера может поворачиваться и " +
                "охватывать большие территории наблюдения, а встроенный ИК-фильтр и детектор " +
                "движения позволят вести видеосъемку даже в полной темноте на расстоянии до 10 метров.\n" +
                "Кроме того, видеокамера оснащена встроенным микрофоном и " +
                "имеет слот TF для карты памяти, а встроенное P2P приложение " +
                "предоставит возможность удаленно наблюдать за событиями через " +
                "iPhone, IPad, Android-смартфон или ПК с любой точки мира без " +
                "сложных настроек. В случае возникновения тревожных событий, " +
                "видеокамера Tenda C50s автоматически отправит тревожное " +
                "сообщение по электронной почте или FTP или сообщит по " +
                "домофону о негативной ситуации, максимально обеспечивая безопасность. " +
                "Встроенный WI-FI позволит легко подключать камеру к вашей беспроводной сети. " +
                "Современный дизайн камеры впишется практически в любой интерьер.\n" +
                "Основные характеристики:\n" +
                "WI-FI стандарты: IEEE802.11/b/g/n.\n" +
                "Скорость Wi-Fi: 150 Мбит/с.\n" +
                "Управление со смартфона: через приложение TendaCamera.\n" +
                "Кнопка WPS: да.\n" +
                "Интерфейс: 1*FE порт: 10/100 Мбит/с Поддержка WI-FI Флэш-карта памяти TF. " +
                "Разъем питания: кнопка Сброс/WPS Микрофон (встроенный) Динамик " +
                "(встроенный) 10 инфракрасных светодиодов (расстояние 10 м).\n" +
                "Диапазон частот: 2.4 ГГц.\n" +
                "Безопасность беспроводной сети: WPA / WPA2-PSK WPA / WPA2-AES WEP.\n" +
                "Максимальная частота кадров: 30 к/c.\n" +
                "Линза: 960P HD, f=3.6 мм, F=2,0.\n" +
                "Дополнительно: подключение по Ethernet. Сопутствующие продукты: любые беспроводные маршрутизаторы Tenda.\n" +
                "Сертификаты: CE, RoHS, FCC.\n" +
                "Электропитание: выход: постоянный ток 9 В, ==1.0 A.\n" +
                "Протоколы и стандарты: TCP/IP, DHCP, ARP, ICMP, FTP, SMTP, DDNS, " +
                "NTP, UPnP, RTSP, RTP, HTTP, TCP, UDP, 3GPP, P2P, ONVIF.\n" +
                "Тип матрицы: 1/4 Color CMOS.\n" +
                "Слот карты памяти: MicroSD/SDHC до 32 ГБ.\n" +
                "Режим Night Vision: 10 IRLEDs, до 10 м.\n" +
                "Формат видео: H.264, MJPEG.\n" +
                "Разрешение: 1280x720 (HD); 640x480 (VGA); 320x240 (QVGA).\n" +
                "Аудио: встроенные динамик и микрофон.\n" +
                "Панорама / Наклон: 355° / V=120°.\n" +
                "Габариты (ДxШxВ): 118х107х106 мм.";
        characteristics = "Производитель...................Tenda\n" +
                "Страна изготовителя.............Китай\n" +
                "Материал........................Пластик\n" +
                "Цвет............................Белый\n" +
                "Разрешение (видео)..............1280x720\n" +
                "Интерфейс.......................Беспроводная\n" +
                "Число мегапикселов матрицы......1.3\n" +
                "Микрофон........................Встроенный";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item tendaC50SWirelessHDSwivelCamera = new Item("Беспроводная поворотная HD камера Tenda C50S", 300L, 0.5D, 3000D, description, characteristics, code, peripherals);
        image = new File("src/main/resources/static/images/InitElectronics/tendaC50SWirelessHDSwivelCamera.jpg");
        tendaC50SWirelessHDSwivelCamera.setImage(fileUtil.fileToBytes(image));

        description = "Формат 4k hd: не упустите ни одной детали\n" +
                "Осуществляйте потоковое воспроизведение видео с " +
                "великолепным разрешением, высокой частотой кадров, " +
                "чистыми цветами и точной передачей деталей. " +
                "Камера Logitech BRIO поддерживает формат 4K Ultra HD " +
                "при частоте 30 кадров в секунду или 1080p при частоте " +
                "30 и 60 кадров в секунду, что обеспечивает высочайшее " +
                "качество при выполнении любых задач. Рассмотрите " +
                "объект в деталях, воспользовавшись пятикратным зумом. " +
                "Что бы ни попало в кадр, камера BRIO может снять это.\n" +
                "\n" +
                "Отличное изображение при любом освещении\n" +
                "Вы можете организовать совещание, транслировать или " +
                "записать видео при любой освещенности — и будете " +
                "всегда выглядеть прекрасно. И в полутемном помещении, " +
                "и при ярком солнце камера Logitech BRIO с поддержкой " +
                "технологии RightLight 3 и расширенного динамического " +
                "диапазона (HDR) автоматически настроит свет, чтобы " +
                "выгодно показать самый важный объект — вас.\n" +
                "\n" +
                "Повышенная безопасность без усилий\n" +
                "Инфракрасный датчик и сертификация для работы с ПО для " +
                "распознавания лиц Windows Hello — это надежная защита от " +
                "несанкционированного входа в систему и доступа к " +
                "конфиденциальной информации. Забудьте о сложных паролях. " +
                "Просто посмотрите в объектив BRIO, и вы мгновенно войдете в систему.\n" +
                "\n" +
                "Порядок в кадре\n" +
                "Воспользуйтесь уникальной технологией, которая применяется в камерах " +
                "BRIO, чтобы свести к минимуму фоновый шум и беспорядок на видео. " +
                "Даже если окружающая вас обстановка хаотична и не способствует " +
                "плодотворной работе, вы можете получить чистое и четкое " +
                "изображение. Используйте настройки поля обзора и пятикратный " +
                "цифровой зум, чтобы сосредоточиться на самом важном.\n" +
                "\n" +
                "Разрешение: 4096x2160 пикс.\n" +
                "Фокусировка: автоматическая.\n" +
                "Максимальная частота кадров: 90 Гц.\n" +
                "Угол обзора объектива: 90 град.\n" +
                "Микрофон: встроенный.\n" +
                "Крепление: на мониторе.\n" +
                "Подключение: USB 3.0.\n" +
                "Длина кабеля: 1.5 м.\n" +
                "Рамеры: 102x27x27 мм.\n" +
                "Вес: 63 г.";
        characteristics = "Производитель...................Logitech\n" +
                "Материал........................Пластик\n" +
                "Цвет............................Чёрный\n" +
                "Разрешение (видео)..............4096x2160\n" +
                "Интерфейс.......................USB\n" +
                "Микрофон........................Встроенный";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item webLogitechCamera = new Item("Камера Web Logitech Webcam BRIO 960-001106", 300L, 0.35D, 18963D, description, characteristics, code, peripherals);
        image = new File("src/main/resources/static/images/InitElectronics/webLogitechCamera.jpg");
        webLogitechCamera.setImage(fileUtil.fileToBytes(image));

        itemService.save(tendaC50SWirelessHDSwivelCamera);
        itemService.save(webLogitechCamera);

        description = "Тип подключения: проводной.\n" +
                "Тип наушников: накладные полноразмерные.\n" +
                "Акустическое оформление: закрытое.\n" +
                "Тип крепления: оголовье.\n" +
                "Диаметр мембраны: 50 мм.\n" +
                "Импеданс (наушники): 16 Ом.\n" +
                "Импеданс (микрофон): 2.2 кОм.\n" +
                "Чувствительность (наушники): 116 дБ.\n" +
                "Чувствительность (микрофон): 38 дБ.\n" +
                "Частотный диапазон (наушники): 20–20000 Гц.\n" +
                "Частотный диапазон (микрофон): 50–16000 Гц.\n" +
                "Длина кабеля: 2 м.\n" +
                "Разъемы: USB, 2 x 3,5-мм джек.\n" +
                "Цвет: чёрный + красный.";
        characteristics = "Производитель...................Redragon\n" +
                "Материал........................Пластик\n" +
                "Цвет............................Чёрный, красный\n" +
                "Вес без упаковки................380 г\n" +
                "Гарантийный срок................18 мес.";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item dedragonThemisGamingHeadset = new Item("Игровая гарнитура Redragon Themis, красный + черный", 600L, 0.46D, 1650D, description, characteristics, code, peripherals);
        image = new File("src/main/resources/static/images/InitElectronics/dedragonThemisGamingHeadset.jpg");
        dedragonThemisGamingHeadset.setImage(fileUtil.fileToBytes(image));

        description = "Тип: гарнитура.\n" +
                "Конструкция наушников: накладные.\n" +
                "Тип подключения: проводное.\n" +
                "Тип наушников: динамические.\n" +
                "Тип акустического оформления: закрытые.\n" +
                "Диапазон воспроизводимых частот: 20 - 20000 Гц.\n" +
                "Импеданс: 16 Ом.\n" +
                "Чувствительность: 115 дБ.\n" +
                "Максимальная мощность: 180 мВт.\n" +
                "Тип крепления: оголовье.\n" +
                "Диаметр мембраны: 50 мм.\n" +
                "Интерфейс/разъём подключения: USB.\n" +
                "Длина провода: 2.3 м.\n" +
                "Чувствительность микрофона: -38 дБ.\n" +
                "Частотный диапазон микрофона: 100 - 16000 Гц.\n" +
                "Цвет: чёрный.\n";
        characteristics = "Производитель...................Cooler Master\n" +
                "Цвет............................Чёрный, красный";
        code = UUID.randomUUID().toString().substring(0, 8);
        Item coolerMasterHeadset = new Item("Гарнитура Cooler Master CH-321", 600L, 0.5D, 3811D, description, characteristics, code, peripherals);
        image = new File("src/main/resources/static/images/InitElectronics/coolerMasterHeadset.jpg");
        coolerMasterHeadset.setImage(fileUtil.fileToBytes(image));

        itemService.save(dedragonThemisGamingHeadset);
        itemService.save(coolerMasterHeadset);

        /* --- --- */

        logger.debug("Initialize clients");
        /* --- Users init --- */

        //Without items
        Client yakovMaurov = new Client("condricka@hotmail.com", "25oMTtm3", "Яков", "Маюров", "YakovMaurov");
        Client prokofiyKravchuk = new Client("hane.ayla@yahoo.com", "1Rhm47zO", "Прокофий", "Кравчук", "ProkofiyKravchuk");
        Client timofeyBarshev = new Client("norene04@yahoo.com", "Yn865FbJ", "Тимофей", "Барышев", "TimofeyBarshev");

        yakovMaurov.setRoles(Collections.singleton(Role.USER));
        clientService.save(yakovMaurov);
        prokofiyKravchuk.setRoles(Collections.singleton(Role.USER));
        clientService.save(prokofiyKravchuk);
        timofeyBarshev.setRoles(Collections.singleton(Role.USER));
        clientService.save(timofeyBarshev);

        //With items in basket
        Client egorSolomonov = new Client("tianna94@gmail.com", "92zoKcG5", "Егор", "Соломонов", "EgorSolomonov");
        Client vladislavPutilin = new Client("hassan66@yahoo.com", "2s1L8lPC", "Владислав", "Путилин", "VladislavPutilin");
        Client borislavPotemkin = new Client("dylan80@yahoo.com", "UBq9H13C", "Борислав", "Потёмкин", "BorislavPotemkin");

        Set<Item> basket1 = new HashSet<>(Arrays.asList(callOfCthulhu, bookAboutTheWayOfLife, jokesFromNikulin));
        Set<Item> basket2 = new HashSet<>(Arrays.asList(proGit, javaConcurrencyInPractice, ballpointPenPilotBPS, notebookLandscape));
        Set<Item> basket3 = new HashSet<>(Arrays.asList(computerMicrophoneSvenMK490, dedragonThemisGamingHeadset, keyboardGamingLogitech, wiredGamingMouseRedragonVampire));

        egorSolomonov.setBasket(basket1);
        vladislavPutilin.setBasket(basket2);
        borislavPotemkin.setBasket(basket3);

        egorSolomonov.setRoles(Collections.singleton(Role.USER));
        clientService.save(egorSolomonov);
        vladislavPutilin.setRoles(Collections.singleton(Role.USER));
        clientService.save(vladislavPutilin);
        borislavPotemkin.setRoles(Collections.singleton(Role.USER));
        clientService.save(borislavPotemkin);

        //With orders
        Client lianaKraevska = new Client("electa54@lowe.biz", "Z1BY5O6c", "Лиана", "Краевская", "LianaKraevska");
        Client albinaBudanova = new Client("nickolas49@ohara.org", "v7gIe11t", "Альбина", "Буданова", "AlbinaBudanova");

        //first
        OrderedItem orderedItem1 = new OrderedItem(spring5ForProfessionals, 1);
        OrderedItem orderedItem2 = new OrderedItem(perfectCode, 2);

        Contacts contactsLiana = new Contacts("457043", "Россия", "Южноуральск", "Зелёная д5 к64", "8 (632) 452-72-98");
        Order order1 = new Order(new HashSet<>(Arrays.asList(orderedItem1, orderedItem2)), contactsLiana, "C.O.D");
        order1.setClient(lianaKraevska);
        order1.setOrderStatus(OrderStatus.ACCEPTED);
        orderService.save(order1);

        orderedItem1.setOrder(order1);
        orderedItem2.setOrder(order1);
        orderedItemService.save(orderedItem1);
        orderedItemService.save(orderedItem2);

        lianaKraevska.setOrders(new HashSet<>(Collections.singletonList(order1)));
        lianaKraevska.setRoles(Collections.singleton(Role.USER));
        clientService.save(lianaKraevska);

        //second
        OrderedItem orderedItem3 = new OrderedItem(notebookDragonfly, 3);
        OrderedItem orderedItem4 = new OrderedItem(designArtSketchbook, 5);
        OrderedItem orderedItem5 = new OrderedItem(ballpointPenR301OrangeStick, 10);

        Contacts contactsAlbina = new Contacts("115569", "Россия", "Москва", "Баженова д4А", "8 (499) 387-62-54");
        Order order2 = new Order(new HashSet<>(Collections.singletonList(orderedItem3)), contactsAlbina, "C.O.D");
        order2.setClient(albinaBudanova);
        order2.setOrderStatus(OrderStatus.COMPLETED);
        Order order3 = new Order(new HashSet<>(Collections.singletonList(orderedItem4)), contactsAlbina, "C.O.D");
        order3.setClient(albinaBudanova);
        order3.setOrderStatus(OrderStatus.ON_THE_WAY);
        order3.setTrackNumber("1234567891011");
        Order order4 = new Order(new HashSet<>(Collections.singletonList(orderedItem5)), contactsAlbina, "C.O.D");
        order4.setClient(albinaBudanova);
        order4.setOrderStatus(OrderStatus.PAYMENT);
        order4.setTrackNumber("121314151617");
        orderService.save(order2);
        orderService.save(order3);
        orderService.save(order4);

        orderedItem3.setOrder(order2);
        orderedItem4.setOrder(order3);
        orderedItem5.setOrder(order4);
        orderedItemService.save(orderedItem3);
        orderedItemService.save(orderedItem4);
        orderedItemService.save(orderedItem5);

        albinaBudanova.setOrders(new HashSet<>(Arrays.asList(order2, order3, order4)));
        albinaBudanova.setRoles(Collections.singleton(Role.USER));
        clientService.save(albinaBudanova);

        //Test user
        Client vladislavSmirnov = new Client("vladsmirn289@gmail.com", "12345", "Владислав", "Смирнов", "VladislavSmirnov");

        vladislavSmirnov.setRoles(Collections.singleton(Role.USER));
        clientService.save(vladislavSmirnov);

        //Manager, who manage orders and create items
        Client manager1 = new Client("bonnie99@grimes.com", "25oMTtm3", "Роман", "Гусев", "RomanGusev");
        Client manager2 = new Client("kyle67@grady.com", "25oMTtm3", "Регина", "Рудова", "ReginaRudova");
        Client manager3 = new Client("xwitting@powlowski.com", "25oMTtm3", "Вячеслав", "Юнкин", "VyacheslavYunkin");

        Client admin = new Client("goconnell@bernhard.com", "25oMTtm3", "Семён", "Буков", "CemenBukov");

        manager1.setRoles(new HashSet<>(Arrays.asList(Role.USER, Role.MANAGER)));
        clientService.save(manager1);
        manager2.setRoles(new HashSet<>(Arrays.asList(Role.USER, Role.MANAGER)));
        clientService.save(manager2);
        manager3.setRoles(new HashSet<>(Arrays.asList(Role.USER, Role.MANAGER)));
        clientService.save(manager3);
        admin.setRoles(new HashSet<>(Arrays.asList(Role.USER, Role.MANAGER, Role.ADMIN)));
        clientService.save(admin);

        /* --- --- */

        entityManager.createNativeQuery("create sequence hibernate_sequence start 69 increment 1");
    }
}
