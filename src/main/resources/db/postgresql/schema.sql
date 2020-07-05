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
    patronymic character varying(255),
    is_non_locked boolean DEFAULT true NOT NULL
);

--
-- Name: item; Type: TABLE; Schema: public
--

CREATE TABLE public.item (
    id bigint NOT NULL,
    characteristics character varying(50000),
    code character varying(255),
    count bigint,
    created_on timestamp without time zone,
    description character varying(50000),
    image oid,
    name character varying(255),
    price double precision,
    weight double precision,
    category_id bigint NOT NULL
);

--
-- Name: client_item; Type: TABLE; Schema: public
--

CREATE TABLE public.client_item (
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
    client_id bigint,
    manager_id bigint
);

--
-- Name: image; Type: TABLE; Schema: public
--

CREATE TABLE public.image (
  id bigint NOT NULL,
  image oid
);

--
-- Name: item_additional_images; Type: TABLE; Schema: public
--

CREATE TABLE public.item_additional_images (
    item_id bigint NOT NULL,
    additional_images_id bigint NOT NULL
);

--
-- Name: client_roles; Type: TABLE; Schema: public
--

CREATE TABLE public.client_roles (
    client_id bigint NOT NULL,
    roles character varying(255)
);

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

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
-- Name: ordered_item client_item_pkey; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.client_item
    ADD CONSTRAINT client_item_pkey PRIMARY KEY (id);


--
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- Name: image image_pkey; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.image
    ADD CONSTRAINT image_pkey PRIMARY KEY (id);


--
-- Name: item_additional_images item_additional_images_pkey; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.item_additional_images
    ADD CONSTRAINT item_additional_images_pkey PRIMARY KEY (item_id, additional_images_id);


--
-- Name: item_additional_images uk_additional_images_id; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.item_additional_images
    ADD CONSTRAINT uk_additional_images_id UNIQUE (additional_images_id);


--
-- Name: orders fk_client_id; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fk_client_id FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: orders fk_manager_id; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fk_manager_id FOREIGN KEY (manager_id) REFERENCES public.client(id);


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

ALTER TABLE ONLY public.client_item
    ADD CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES public.orders(id);


--
-- Name: ordered_item fk_item_id; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.client_item
    ADD CONSTRAINT fk_ordered_item_id FOREIGN KEY (item_id) REFERENCES public.item(id);


--
-- Name: basket_items fk_basket_items_id; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.basket_items
    ADD CONSTRAINT fk_basket_items_id FOREIGN KEY (item_id) REFERENCES public.client_item(id);


--
-- Name: basket_items fk_user_id; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.basket_items
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.client(id);


--
-- Name: item_additional_images fk_additional_images_id; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.item_additional_images
    ADD CONSTRAINT fk_additional_images_id FOREIGN KEY (additional_images_id) REFERENCES public.image(id);


--
-- Name: item_additional_images fk_item_id; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.item_additional_images
    ADD CONSTRAINT fk_item_id FOREIGN KEY (item_id) REFERENCES public.item(id);

--
-- Name: client_roles fk_client_id; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.client_roles
    ADD CONSTRAINT fk_client_id FOREIGN KEY (client_id) REFERENCES public.client(id);
