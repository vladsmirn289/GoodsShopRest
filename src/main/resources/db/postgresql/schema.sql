--
-- Name: basket_items; Type: TABLE; Schema: public
--

CREATE TABLE public.basket_items (
    user_id bigint NOT NULL,
    item_id bigint NOT NULL
);

--
-- Name: category; Type: TABLE; Schema: public
--

CREATE TABLE public.category (
    id bigint NOT NULL,
    name character varying(255),
    parent_id bigint
);

--
-- Name: client; Type: TABLE; Schema: public
--

CREATE TABLE public.client (
    id bigint NOT NULL,
    email character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    login character varying(255),
    password character varying(255),
    patronymic character varying(255)
);

--
-- Name: item; Type: TABLE; Schema: public
--

CREATE TABLE public.item (
    id bigint NOT NULL,
    characteristics character varying(5000),
    code character varying(255),
    count bigint,
    created_on timestamp without time zone,
    description character varying(5000),
    image oid,
    name character varying(255),
    price double precision,
    weight double precision,
    category_id bigint NOT NULL
);

--
-- Name: ordered_item; Type: TABLE; Schema: public
--

CREATE TABLE public.ordered_item (
    id bigint NOT NULL,
    quantity integer NOT NULL,
    item_id bigint NOT NULL,
    order_id bigint
);

--
-- Name: orders; Type: TABLE; Schema: public
--

CREATE TABLE public.orders (
    id bigint NOT NULL,
    city character varying(255),
    country character varying(255),
    phone_number character varying(255),
    street character varying(255),
    zip_code character varying(255),
    created_on timestamp without time zone,
    last_update timestamp without time zone,
    order_status character varying(255),
    payment_method character varying(255),
    track_number character varying(255),
    client_id bigint
);

--
-- Name: basket_items basket_items_pkey; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.basket_items
    ADD CONSTRAINT basket_items_pkey PRIMARY KEY (user_id, item_id);


--
-- Name: category category_pkey; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);


--
-- Name: client client_pkey; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pkey PRIMARY KEY (id);


--
-- Name: item item_pkey; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.item
    ADD CONSTRAINT item_pkey PRIMARY KEY (id);


--
-- Name: ordered_item ordered_item_pkey; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.ordered_item
    ADD CONSTRAINT ordered_item_pkey PRIMARY KEY (id);


--
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- Name: orders fk_client_id; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fk_client_id FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: item fk_category_id; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.item
    ADD CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES public.category(id);


--
-- Name: category fk_parent_id; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT fk_parent_id FOREIGN KEY (parent_id) REFERENCES public.category(id);


--
-- Name: ordered_item fk_order_id; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.ordered_item
    ADD CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES public.orders(id);


--
-- Name: ordered_item fk_item_id; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.ordered_item
    ADD CONSTRAINT fk_ordered_item_id FOREIGN KEY (item_id) REFERENCES public.item(id);


--
-- Name: basket_items fk_basket_items_id; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.basket_items
    ADD CONSTRAINT fk_basket_items_id FOREIGN KEY (item_id) REFERENCES public.item(id);


--
-- Name: basket_items fk_user_id; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.basket_items
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.client(id);