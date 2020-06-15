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

create sequence hibernate_sequence start 69 increment 1