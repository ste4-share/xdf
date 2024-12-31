--
-- PostgreSQL database dump
--

-- Dumped from database version 14.13
-- Dumped by pg_dump version 16.4

-- Started on 2024-12-30 17:40:30

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 7 (class 2615 OID 17879)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

-- *not* creating schema, since initdb creates it


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 3796 (class 0 OID 0)
-- Dependencies: 7
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS '';


--
-- TOC entry 2 (class 3079 OID 16384)
-- Name: adminpack; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;


--
-- TOC entry 3798 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION adminpack; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';


--
-- TOC entry 3 (class 3079 OID 17881)
-- Name: tablefunc; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS tablefunc WITH SCHEMA public;


--
-- TOC entry 3799 (class 0 OID 0)
-- Dependencies: 3
-- Name: EXTENSION tablefunc; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION tablefunc IS 'functions that manipulate whole tables, including crosstab';


--
-- TOC entry 306 (class 1255 OID 17903)
-- Name: tonkhonhap_xd2(integer, text, integer, text, integer, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.tonkhonhap_xd2(q_id integer, tt text, lxd_id integer, lp text, dvin_id integer, st text) RETURNS integer
    LANGUAGE plpgsql
    AS $$
declare
	total integer;
BEGIN
   select soluong into total 
   from (select loaixd_id, sum(so_luong) as soluong 
   from ledgers l join ledger_details ld on l.id=ld.ledger_id 
   where loai_phieu like lp and tructhuoc like tt and quarter_id=q_id 
   and loaixd_id=lxd_id and dvi_nhan_id=dvin_id and l.status like st group by loaixd_id limit 1) a;
   RETURN total;
END;
$$;


ALTER FUNCTION public.tonkhonhap_xd2(q_id integer, tt text, lxd_id integer, lp text, dvin_id integer, st text) OWNER TO postgres;

--
-- TOC entry 312 (class 1255 OID 17904)
-- Name: tonkhoxuat_xd2(integer, text, integer, text, integer, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.tonkhoxuat_xd2(q_id integer, tt text, lxd_id integer, lp text, dvix_id integer, st text) RETURNS integer
    LANGUAGE plpgsql
    AS $$
declare
	total integer;
BEGIN
   select soluong into total from (select loaixd_id, 
   sum(so_luong) as soluong from ledgers l 
   join ledger_details ld on l.id=ld.ledger_id where 
   loai_phieu like lp and tructhuoc like tt and 
   quarter_id=q_id and loaixd_id=lxd_id and root_id=dvix_id 
   and l.status like st group by loaixd_id limit 1) a;
   RETURN total;
END;
$$;


ALTER FUNCTION public.tonkhoxuat_xd2(q_id integer, tt text, lxd_id integer, lp text, dvix_id integer, st text) OWNER TO postgres;

--
-- TOC entry 307 (class 1255 OID 17905)
-- Name: totalloaixd(integer, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.totalloaixd(q_id integer, dvi_x integer, lxd_id integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$
declare
	total integer;
BEGIN
   select loaixd_id, sum(so_luong) into total from ledgers l join ledger_details ld on l.id=ld.ledger_id where loai_phieu like 'NHAP' and dvi_xuat_id=dvi_x and quarter_id=q_id and loaixd_id=lxd_id group by loaixd_id limit 1;
   RETURN total;
END;
$$;


ALTER FUNCTION public.totalloaixd(q_id integer, dvi_x integer, lxd_id integer) OWNER TO postgres;

--
-- TOC entry 308 (class 1255 OID 17906)
-- Name: totalloaixd2(integer, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.totalloaixd2(q_id integer, dvi_x integer, lxd_id integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$
declare
	total integer;
BEGIN
   select soluong into total from (select loaixd_id, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id where loai_phieu like 'NHAP' and dvi_xuat_id=dvi_x and quarter_id=q_id and loaixd_id=lxd_id group by loaixd_id limit 1) a;
   RETURN total;
END;
$$;


ALTER FUNCTION public.totalloaixd2(q_id integer, dvi_x integer, lxd_id integer) OWNER TO postgres;

--
-- TOC entry 309 (class 1255 OID 17907)
-- Name: totalloaixd2(integer, text, integer, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.totalloaixd2(q_id integer, tt text, lxd_id integer, lp text) RETURNS integer
    LANGUAGE plpgsql
    AS $$
declare
	total integer;
BEGIN
   select soluong into total from (select loaixd_id, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id where loai_phieu like lp and tructhuoc like tt and quarter_id=q_id and loaixd_id=lxd_id group by loaixd_id limit 1) a;
   RETURN total;
END;
$$;


ALTER FUNCTION public.totalloaixd2(q_id integer, tt text, lxd_id integer, lp text) OWNER TO postgres;

--
-- TOC entry 310 (class 1255 OID 17908)
-- Name: totalrecords(integer, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.totalrecords(q_id integer, dvi_xuat integer, lxd_id integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$
declare
	total integer;
BEGIN
   select loaixd_id, sum(so_luong) into total from ledgers l join ledger_details ld on l.id=ld.ledger_id where loai_phieu like 'NHAP' and l.dvi_xuat_id=dvi_xuat and quarter_id=q_id and loaixd_id=lxd_id group by loaixd_id limit 1;
   RETURN total;
END;
$$;


ALTER FUNCTION public.totalrecords(q_id integer, dvi_xuat integer, lxd_id integer) OWNER TO postgres;

--
-- TOC entry 311 (class 1255 OID 17909)
-- Name: totalttxd_xmt(integer, integer, integer, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.totalttxd_xmt(q_id integer, dvx_id integer, nv_id integer, loaipt text) RETURNS TABLE(sl integer, sokm integer)
    LANGUAGE plpgsql
    AS $$
declare
	total integer;
BEGIN
    return query select soluong,km from (select nhiemvu_id,nhiemvu,lpt, sum(so_luong)::int as soluong,sum(so_km)::int as km  
	from ledgers l join ledger_details ld on l.id=ld.ledger_id 
	where quarter_id=q_id and dvi_xuat_id=dvx_id and nhiemvu_id=nv_id and lpt like loaipt
	group by nhiemvu_id,nhiemvu,lpt limit 1) a;
END;
$$;


ALTER FUNCTION public.totalttxd_xmt(q_id integer, dvx_id integer, nv_id integer, loaipt text) OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 214 (class 1259 OID 17910)
-- Name: inventory; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.inventory (
    id bigint NOT NULL,
    tdk_sscd bigint DEFAULT 0 NOT NULL,
    tdk_nvdx bigint DEFAULT 0 NOT NULL,
    status text,
    petro_id integer,
    quarter_id integer,
    nhap_nvdx bigint DEFAULT 0,
    nhap_sscd bigint DEFAULT 0,
    xuat_nvdx bigint DEFAULT 0,
    xuat_sscd bigint DEFAULT 0,
    price bigint DEFAULT 0,
    create_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.inventory OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 17923)
-- Name: Inventory_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.inventory ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Inventory_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 216 (class 1259 OID 17924)
-- Name: ledgers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ledgers (
    id integer NOT NULL,
    quarter_id integer DEFAULT 0,
    bill_id integer,
    amount bigint DEFAULT 0,
    from_date date,
    end_date date,
    status text NOT NULL,
    sl_tieuthu_md bigint DEFAULT 0,
    sl_tieuthu_tk bigint DEFAULT 0,
    dvi_nhan_id integer DEFAULT 0,
    dvi_xuat_id integer DEFAULT 0,
    loai_phieu character varying(10),
    dvi_nhan text,
    dvi_xuat text,
    loaigiobay character varying(10),
    nguoi_nhan text,
    so_xe text,
    lenh_so text,
    nhiemvu text,
    nhiemvu_id bigint DEFAULT 0,
    so_km bigint DEFAULT 0,
    tcn_id smallint DEFAULT 0,
    "timestamp" timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    giohd_md character varying(20) DEFAULT '00:00:00'::character varying,
    giohd_tk character varying(20) DEFAULT '00:00:00'::character varying,
    loainv character varying(20),
    tructhuoc character varying(20),
    lpt character varying(20),
    lpt_2 character varying(20),
    version smallint DEFAULT 0,
    create_by smallint DEFAULT 0,
    root_id smallint DEFAULT 0,
    pt_id smallint DEFAULT 0
);


ALTER TABLE public.ledgers OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 17942)
-- Name: Ledgers_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.ledgers ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Ledgers_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 218 (class 1259 OID 17943)
-- Name: accounts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.accounts (
    id smallint NOT NULL,
    username text,
    surname text,
    roles character varying(20),
    passwd text,
    color character varying(10),
    status character varying(10),
    create_at timestamp without time zone DEFAULT now(),
    path text
);


ALTER TABLE public.accounts OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 17949)
-- Name: accounts_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.accounts ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.accounts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 220 (class 1259 OID 17950)
-- Name: activated_active; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.activated_active (
    id integer NOT NULL,
    status_name text
);


ALTER TABLE public.activated_active OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 17955)
-- Name: activated_active_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.activated_active ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.activated_active_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 222 (class 1259 OID 17956)
-- Name: category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.category (
    id integer NOT NULL,
    header_lv1 text,
    header_lv2 text,
    header_lv3 text,
    type_title text,
    tructhuoc_id integer,
    code text
);


ALTER TABLE public.category OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 17961)
-- Name: category_assignment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.category_assignment (
    title_1 text,
    title_2 text,
    title_3 text,
    title_4 text,
    id integer NOT NULL,
    code text
);


ALTER TABLE public.category_assignment OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 17966)
-- Name: nhiemvu_reporter; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.nhiemvu_reporter (
    id integer NOT NULL,
    title_1 text,
    title_2 text,
    title_3 text,
    title_4 text,
    soluong text,
    nhiemvu_id integer,
    phuongtien_id integer,
    ten_nv_1 text,
    ten_nv_2 text,
    ten_nv_3 text
);


ALTER TABLE public.nhiemvu_reporter OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 17971)
-- Name: category_assignment_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.nhiemvu_reporter ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.category_assignment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 226 (class 1259 OID 17972)
-- Name: category_assignment_id_seq1; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.category_assignment ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.category_assignment_id_seq1
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 227 (class 1259 OID 17973)
-- Name: category_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.category ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.category_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 228 (class 1259 OID 17974)
-- Name: nhiemvu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.nhiemvu (
    id integer NOT NULL,
    ten_nv text,
    status text,
    team_id integer,
    assignment_type_id integer,
    priority integer,
    priority_bc2 integer
);


ALTER TABLE public.nhiemvu OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 17979)
-- Name: chi_tiet_nhiemvu_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.chi_tiet_nhiemvu_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.chi_tiet_nhiemvu_id_seq OWNER TO postgres;

--
-- TOC entry 3800 (class 0 OID 0)
-- Dependencies: 229
-- Name: chi_tiet_nhiemvu_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.chi_tiet_nhiemvu_id_seq OWNED BY public.nhiemvu.id;


--
-- TOC entry 230 (class 1259 OID 17980)
-- Name: chitiet_nhiemvu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.chitiet_nhiemvu (
    id integer NOT NULL,
    nhiemvu_id integer,
    nhiemvu text
);


ALTER TABLE public.chitiet_nhiemvu OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 17985)
-- Name: chitiet_nhiemvu_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.chitiet_nhiemvu_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.chitiet_nhiemvu_id_seq OWNER TO postgres;

--
-- TOC entry 3801 (class 0 OID 0)
-- Dependencies: 231
-- Name: chitiet_nhiemvu_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.chitiet_nhiemvu_id_seq OWNED BY public.chitiet_nhiemvu.id;


--
-- TOC entry 232 (class 1259 OID 17986)
-- Name: chitieu_pt; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.chitieu_pt (
    id integer NOT NULL,
    dvi_id integer DEFAULT 0,
    ctnv_id integer DEFAULT 0,
    quy_id integer DEFAULT 0,
    md character varying(20),
    tk character varying(20),
    nl bigint DEFAULT 0,
    pt_id integer DEFAULT 0
);


ALTER TABLE public.chitieu_pt OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 17994)
-- Name: chitieu_pt_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.chitieu_pt ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.chitieu_pt_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 234 (class 1259 OID 17995)
-- Name: chungloaixd; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.chungloaixd (
    id integer NOT NULL,
    loai text,
    chungloai text,
    tinhchat text,
    code character varying(10) NOT NULL,
    priority_1 integer DEFAULT 0,
    priority_2 integer DEFAULT 0,
    priority_3 integer DEFAULT 0,
    tinhchat2 text,
    stt_index integer DEFAULT 0,
    code2 text
);


ALTER TABLE public.chungloaixd OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 18004)
-- Name: chungloaixd_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.chungloaixd ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.chungloaixd_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 283 (class 1259 OID 18459)
-- Name: configuration; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.configuration (
    id integer NOT NULL,
    parameter character varying(50) NOT NULL,
    value text
);


ALTER TABLE public.configuration OWNER TO postgres;

--
-- TOC entry 282 (class 1259 OID 18458)
-- Name: configuration_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.configuration ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.configuration_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 236 (class 1259 OID 18005)
-- Name: dinhmuc; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dinhmuc (
    id integer NOT NULL,
    dm_tk_gio integer,
    dm_md_gio integer,
    dm_xm_km integer,
    dm_xm_gio integer,
    phuongtien_id integer,
    quarter_id integer
);


ALTER TABLE public.dinhmuc OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 18008)
-- Name: dinhmuc_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.dinhmuc ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.dinhmuc_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 238 (class 1259 OID 18009)
-- Name: donvi_tructhuoc; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.donvi_tructhuoc (
    id integer NOT NULL,
    root_id integer,
    dvi_tructhuoc_id integer
);


ALTER TABLE public.donvi_tructhuoc OWNER TO postgres;

--
-- TOC entry 239 (class 1259 OID 18012)
-- Name: dvi_nv; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dvi_nv (
    id integer NOT NULL,
    dv_id integer,
    nv_id integer,
    createtime text
);


ALTER TABLE public.dvi_nv OWNER TO postgres;

--
-- TOC entry 240 (class 1259 OID 18017)
-- Name: dvi_nv_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.dvi_nv_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.dvi_nv_id_seq OWNER TO postgres;

--
-- TOC entry 3802 (class 0 OID 0)
-- Dependencies: 240
-- Name: dvi_nv_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.dvi_nv_id_seq OWNED BY public.dvi_nv.id;


--
-- TOC entry 241 (class 1259 OID 18024)
-- Name: hanmuc; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.hanmuc (
    id integer NOT NULL,
    quarter_id integer,
    hanmuc_md text,
    hanmuc_km bigint,
    hanmuc_tk text,
    soluong bigint,
    pt_id integer
);


ALTER TABLE public.hanmuc OWNER TO postgres;

--
-- TOC entry 242 (class 1259 OID 18029)
-- Name: hanmuc_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.hanmuc ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.hanmuc_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 243 (class 1259 OID 18030)
-- Name: hanmuc_nhiemvu2; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.hanmuc_nhiemvu2 (
    id bigint NOT NULL,
    quarter_id bigint DEFAULT 0,
    nhiemvu_id bigint DEFAULT 0,
    dvi_id bigint DEFAULT 0,
    diezel bigint DEFAULT 0,
    daubay bigint DEFAULT 0,
    xang bigint
);


ALTER TABLE public.hanmuc_nhiemvu2 OWNER TO postgres;

--
-- TOC entry 244 (class 1259 OID 18038)
-- Name: hanmuc_nhiemvu2_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.hanmuc_nhiemvu2 ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.hanmuc_nhiemvu2_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 245 (class 1259 OID 18039)
-- Name: hanmuc_nhiemvu_taubay; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.hanmuc_nhiemvu_taubay (
    id bigint NOT NULL,
    dvi_xuat_id bigint,
    pt_id bigint,
    ctnv_id bigint,
    quy_id bigint,
    tk text DEFAULT '00:00'::text,
    md text DEFAULT '00:00'::text,
    nhienlieu bigint DEFAULT 0
);


ALTER TABLE public.hanmuc_nhiemvu_taubay OWNER TO postgres;

--
-- TOC entry 246 (class 1259 OID 18047)
-- Name: hanmuc_nhiemvu_taubay_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.hanmuc_nhiemvu_taubay ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.hanmuc_nhiemvu_taubay_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 247 (class 1259 OID 18058)
-- Name: ledger_details; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ledger_details (
    ma_xd text,
    ten_xd text,
    chung_loai text,
    chat_luong text,
    phai_xuat integer DEFAULT 0,
    thuc_xuat bigint DEFAULT 0,
    don_gia integer DEFAULT 0,
    id integer NOT NULL,
    loaixd_id integer,
    phuongtien_id integer,
    ledger_id integer NOT NULL,
    thuc_xuat_tk bigint DEFAULT 0,
    so_luong bigint DEFAULT 0,
    thuc_nhap bigint DEFAULT 0,
    phai_nhap bigint DEFAULT 0,
    thanhtien bigint DEFAULT 0,
    haohut_sl bigint DEFAULT 0,
    nl_km bigint DEFAULT 0,
    nl_gio bigint DEFAULT 0,
    so_luong_px bigint DEFAULT 0,
    sscd_nvdx character varying(10) DEFAULT 'NVDX'::character varying,
    ty_trong double precision DEFAULT 0,
    nhiet_do_tt double precision DEFAULT 0,
    he_so_vcf double precision DEFAULT 0,
    nhap_nvdx bigint DEFAULT 0,
    nhap_sscd bigint DEFAULT 0,
    xuat_nvdx bigint DEFAULT 0,
    xuat_sscd bigint DEFAULT 0
);


ALTER TABLE public.ledger_details OWNER TO postgres;

--
-- TOC entry 248 (class 1259 OID 18078)
-- Name: ledger_map; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ledger_map (
    id integer NOT NULL,
    loaixd_id integer NOT NULL,
    header_id integer NOT NULL,
    soluong integer,
    mucgia_id integer,
    quarter_id integer NOT NULL,
    status text
);


ALTER TABLE public.ledger_map OWNER TO postgres;

--
-- TOC entry 249 (class 1259 OID 18083)
-- Name: lichsuxnk; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.lichsuxnk (
    id integer NOT NULL,
    ten_xd text,
    loai_phieu text,
    tontruoc integer DEFAULT 0,
    soluong integer DEFAULT 0,
    tonsau integer DEFAULT 0,
    "timestamp" timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    type character varying(5) DEFAULT 'NVDX'::character varying,
    so smallint DEFAULT 0,
    dvn text,
    dvx text,
    chungloaixd text,
    quy_id smallint DEFAULT 0,
    gia bigint DEFAULT 0
);


ALTER TABLE public.lichsuxnk OWNER TO postgres;

--
-- TOC entry 250 (class 1259 OID 18096)
-- Name: lichsuxnk_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.lichsuxnk_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.lichsuxnk_id_seq OWNER TO postgres;

--
-- TOC entry 3803 (class 0 OID 0)
-- Dependencies: 250
-- Name: lichsuxnk_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.lichsuxnk_id_seq OWNED BY public.lichsuxnk.id;


--
-- TOC entry 251 (class 1259 OID 18097)
-- Name: loai_nhiemvu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.loai_nhiemvu (
    id integer NOT NULL,
    task_name text
);


ALTER TABLE public.loai_nhiemvu OWNER TO postgres;

--
-- TOC entry 252 (class 1259 OID 18102)
-- Name: loai_nhiemvu_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.loai_nhiemvu ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.loai_nhiemvu_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 253 (class 1259 OID 18103)
-- Name: tcn; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tcn (
    id integer NOT NULL,
    name text,
    loaiphieu character varying(10)
);


ALTER TABLE public.tcn OWNER TO postgres;

--
-- TOC entry 254 (class 1259 OID 18108)
-- Name: loai_nx_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.loai_nx_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.loai_nx_id_seq OWNER TO postgres;

--
-- TOC entry 3804 (class 0 OID 0)
-- Dependencies: 254
-- Name: loai_nx_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.loai_nx_id_seq OWNED BY public.tcn.id;


--
-- TOC entry 255 (class 1259 OID 18115)
-- Name: loai_phuongtien; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.loai_phuongtien (
    id integer NOT NULL,
    type_name text,
    type text
);


ALTER TABLE public.loai_phuongtien OWNER TO postgres;

--
-- TOC entry 256 (class 1259 OID 18120)
-- Name: loai_phuongtien_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.loai_phuongtien ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.loai_phuongtien_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 257 (class 1259 OID 18121)
-- Name: loaixd2; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.loaixd2 (
    id integer NOT NULL,
    maxd text,
    tenxd text,
    status text,
    "timestamp" timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    petroleum_type_id integer
);


ALTER TABLE public.loaixd2 OWNER TO postgres;

--
-- TOC entry 258 (class 1259 OID 18127)
-- Name: loaixd2_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.loaixd2_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.loaixd2_id_seq OWNER TO postgres;

--
-- TOC entry 3805 (class 0 OID 0)
-- Dependencies: 258
-- Name: loaixd2_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.loaixd2_id_seq OWNED BY public.loaixd2.id;


--
-- TOC entry 259 (class 1259 OID 18128)
-- Name: mucgia; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mucgia (
    id integer NOT NULL,
    price integer DEFAULT 0 NOT NULL,
    amount bigint DEFAULT 0 NOT NULL,
    quarter_id integer NOT NULL,
    item_id integer NOT NULL,
    status text NOT NULL,
    "timestamp" timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    nvdx bigint DEFAULT 0,
    sscd bigint DEFAULT 0
);


ALTER TABLE public.mucgia OWNER TO postgres;

--
-- TOC entry 260 (class 1259 OID 18138)
-- Name: mucgia_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.mucgia_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.mucgia_id_seq OWNER TO postgres;

--
-- TOC entry 3806 (class 0 OID 0)
-- Dependencies: 260
-- Name: mucgia_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.mucgia_id_seq OWNED BY public.mucgia.id;


--
-- TOC entry 261 (class 1259 OID 18139)
-- Name: myseq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.myseq
    START WITH 2
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.myseq OWNER TO postgres;

--
-- TOC entry 262 (class 1259 OID 18140)
-- Name: nguon_nx; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.nguon_nx (
    id integer DEFAULT nextval('public.myseq'::regclass) NOT NULL,
    ten text,
    status character varying(10),
    tructhuoc_id integer,
    code character varying(20)
);


ALTER TABLE public.nguon_nx OWNER TO postgres;

--
-- TOC entry 263 (class 1259 OID 18146)
-- Name: tructhuoc_loaiphieu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tructhuoc_loaiphieu (
    id integer NOT NULL,
    tructhuoc_id integer,
    loaiphieu_id integer
);


ALTER TABLE public.tructhuoc_loaiphieu OWNER TO postgres;

--
-- TOC entry 264 (class 1259 OID 18149)
-- Name: nguonnx_loaiphieu_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.tructhuoc_loaiphieu ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.nguonnx_loaiphieu_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 265 (class 1259 OID 18150)
-- Name: nguonnx_pt; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.nguonnx_pt (
    id bigint NOT NULL,
    nguonnx_id bigint,
    pt_id bigint
);


ALTER TABLE public.nguonnx_pt OWNER TO postgres;

--
-- TOC entry 266 (class 1259 OID 18153)
-- Name: nguonnx_pt_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.nguonnx_pt ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.nguonnx_pt_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 267 (class 1259 OID 18162)
-- Name: nhiemvu_tcn; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.nhiemvu_tcn (
    id integer NOT NULL,
    nvu_id integer,
    tcn_id integer,
    phuongtien_id integer
);


ALTER TABLE public.nhiemvu_tcn OWNER TO postgres;

--
-- TOC entry 268 (class 1259 OID 18165)
-- Name: nhiemvu_tcn_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.nhiemvu_tcn_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.nhiemvu_tcn_id_seq OWNER TO postgres;

--
-- TOC entry 3807 (class 0 OID 0)
-- Dependencies: 268
-- Name: nhiemvu_tcn_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.nhiemvu_tcn_id_seq OWNED BY public.nhiemvu_tcn.id;


--
-- TOC entry 269 (class 1259 OID 18177)
-- Name: phuongtien; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.phuongtien (
    id integer NOT NULL,
    name text,
    quantity integer,
    status character varying(50),
    "timestamp" timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    nguonnx_id integer,
    loaiphuongtien_id integer
);


ALTER TABLE public.phuongtien OWNER TO postgres;

--
-- TOC entry 270 (class 1259 OID 18183)
-- Name: phuongtien_nhiemvu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.phuongtien_nhiemvu (
    id integer NOT NULL,
    phuongtien_id integer,
    nvu_id integer
);


ALTER TABLE public.phuongtien_nhiemvu OWNER TO postgres;

--
-- TOC entry 271 (class 1259 OID 18186)
-- Name: phuongtien_nhiemvu_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.phuongtien_nhiemvu_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.phuongtien_nhiemvu_id_seq OWNER TO postgres;

--
-- TOC entry 3808 (class 0 OID 0)
-- Dependencies: 271
-- Name: phuongtien_nhiemvu_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.phuongtien_nhiemvu_id_seq OWNED BY public.phuongtien_nhiemvu.id;


--
-- TOC entry 272 (class 1259 OID 18193)
-- Name: quarter; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.quarter (
    id integer NOT NULL,
    start_date date,
    end_date date,
    year text,
    index text
);


ALTER TABLE public.quarter OWNER TO postgres;

--
-- TOC entry 273 (class 1259 OID 18199)
-- Name: quarter_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.quarter_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.quarter_id_seq OWNER TO postgres;

--
-- TOC entry 3809 (class 0 OID 0)
-- Dependencies: 273
-- Name: quarter_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.quarter_id_seq OWNED BY public.quarter.id;


--
-- TOC entry 274 (class 1259 OID 18200)
-- Name: so_cai_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.so_cai_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.so_cai_id_seq OWNER TO postgres;

--
-- TOC entry 3810 (class 0 OID 0)
-- Dependencies: 274
-- Name: so_cai_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.so_cai_id_seq OWNED BY public.ledger_details.id;


--
-- TOC entry 275 (class 1259 OID 18201)
-- Name: splog_adfarm_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.splog_adfarm_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.splog_adfarm_seq OWNER TO postgres;

--
-- TOC entry 276 (class 1259 OID 18205)
-- Name: team; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.team (
    id integer NOT NULL,
    name text,
    team_code text,
    tt character varying(5),
    priority integer
);


ALTER TABLE public.team OWNER TO postgres;

--
-- TOC entry 277 (class 1259 OID 18210)
-- Name: team_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.team ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.team_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 278 (class 1259 OID 18217)
-- Name: tructhuoc; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tructhuoc (
    id integer NOT NULL,
    name text,
    type text,
    "timestamp" timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    nhom_tructhuoc text,
    tennhom_tructhuoc text
);


ALTER TABLE public.tructhuoc OWNER TO postgres;

--
-- TOC entry 279 (class 1259 OID 18223)
-- Name: tructhuoc_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tructhuoc_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tructhuoc_id_seq OWNER TO postgres;

--
-- TOC entry 3811 (class 0 OID 0)
-- Dependencies: 279
-- Name: tructhuoc_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tructhuoc_id_seq OWNED BY public.tructhuoc.id;


--
-- TOC entry 280 (class 1259 OID 18224)
-- Name: tructhuocf_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.donvi_tructhuoc ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.tructhuocf_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 281 (class 1259 OID 18229)
-- Name: vehicels_for_plan_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.vehicels_for_plan_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.vehicels_for_plan_id_seq OWNER TO postgres;

--
-- TOC entry 3812 (class 0 OID 0)
-- Dependencies: 281
-- Name: vehicels_for_plan_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.vehicels_for_plan_id_seq OWNED BY public.phuongtien.id;


--
-- TOC entry 3392 (class 2604 OID 18230)
-- Name: chitiet_nhiemvu id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chitiet_nhiemvu ALTER COLUMN id SET DEFAULT nextval('public.chitiet_nhiemvu_id_seq'::regclass);


--
-- TOC entry 3402 (class 2604 OID 18231)
-- Name: dvi_nv id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dvi_nv ALTER COLUMN id SET DEFAULT nextval('public.dvi_nv_id_seq'::regclass);


--
-- TOC entry 3414 (class 2604 OID 18232)
-- Name: ledger_details id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ledger_details ALTER COLUMN id SET DEFAULT nextval('public.so_cai_id_seq'::regclass);


--
-- TOC entry 3432 (class 2604 OID 18233)
-- Name: lichsuxnk id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lichsuxnk ALTER COLUMN id SET DEFAULT nextval('public.lichsuxnk_id_seq'::regclass);


--
-- TOC entry 3442 (class 2604 OID 18234)
-- Name: loaixd2 id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.loaixd2 ALTER COLUMN id SET DEFAULT nextval('public.loaixd2_id_seq'::regclass);


--
-- TOC entry 3444 (class 2604 OID 18235)
-- Name: mucgia id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mucgia ALTER COLUMN id SET DEFAULT nextval('public.mucgia_id_seq'::regclass);


--
-- TOC entry 3391 (class 2604 OID 18237)
-- Name: nhiemvu id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nhiemvu ALTER COLUMN id SET DEFAULT nextval('public.chi_tiet_nhiemvu_id_seq'::regclass);


--
-- TOC entry 3451 (class 2604 OID 18238)
-- Name: nhiemvu_tcn id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nhiemvu_tcn ALTER COLUMN id SET DEFAULT nextval('public.nhiemvu_tcn_id_seq'::regclass);


--
-- TOC entry 3452 (class 2604 OID 18239)
-- Name: phuongtien id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.phuongtien ALTER COLUMN id SET DEFAULT nextval('public.vehicels_for_plan_id_seq'::regclass);


--
-- TOC entry 3454 (class 2604 OID 18240)
-- Name: phuongtien_nhiemvu id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.phuongtien_nhiemvu ALTER COLUMN id SET DEFAULT nextval('public.phuongtien_nhiemvu_id_seq'::regclass);


--
-- TOC entry 3455 (class 2604 OID 18241)
-- Name: quarter id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.quarter ALTER COLUMN id SET DEFAULT nextval('public.quarter_id_seq'::regclass);


--
-- TOC entry 3441 (class 2604 OID 18242)
-- Name: tcn id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tcn ALTER COLUMN id SET DEFAULT nextval('public.loai_nx_id_seq'::regclass);


--
-- TOC entry 3456 (class 2604 OID 18243)
-- Name: tructhuoc id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tructhuoc ALTER COLUMN id SET DEFAULT nextval('public.tructhuoc_id_seq'::regclass);


--
-- TOC entry 3725 (class 0 OID 17943)
-- Dependencies: 218
-- Data for Name: accounts; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.accounts (id, username, surname, roles, passwd, color, status, create_at, path) FROM stdin;
2	admin	tga	SUPER_USER	5dd4ebdac62609c834f7768f02286b798bd82a38	#ffffff	ACTIVE	2024-12-22 00:00:00	C:\\Users\\tga\\Downloads\\bc
1	user_1	chain	USER	7c4a8d09ca3762af61e59520943dc26494f8941b	#ffffff	ACTIVE	2024-12-22 00:00:00	C:\\Users\\tga\\Downloads\\bc
\.


--
-- TOC entry 3727 (class 0 OID 17950)
-- Dependencies: 220
-- Data for Name: activated_active; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.activated_active (id, status_name) FROM stdin;
1	IN_ACTIVE
2	ACTIVE
3	WATING
\.


--
-- TOC entry 3729 (class 0 OID 17956)
-- Dependencies: 222
-- Data for Name: category; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.category (id, header_lv1, header_lv2, header_lv3, type_title, tructhuoc_id, code) FROM stdin;
52	Trong QC	Nhập khác	Nhập	NHAP	20	NHAP
53	Trong QC	Nhập khác	Xuất	XUAT	20	XUAT
54	Khác	Khác	Nhập	NHAP	19	NHAP
55	Khác	Khác	Xuất	XUAT	19	XUAT
56	Tổn thất	Tổn thất	Xuất	XUAT	24	XUAT
59	F nội bộ	f nội bộ	Xuất	XUAT	26	XUAT
60	CXD	CXD	Nhập	NHAP	3	NHAP
61	CXD	CXD	Xuất	XUAT	3	XUAT
4	QC	QC	Nhập	NHAP	1	NHAP
5	Mua P/cấp	Mua P/cấp	Nhập	NHAP	2	NHAP
8	TT Xe máy	TT Xe máy	Xuất	XUAT	6	XUAT
9	BQ	BQ	Xuất	XUAT	18	XUAT
10	Hao hụt	Hao hụt	Xuất	XUAT	7	XUAT
12	Ngoài QC	Xuất khác	Xuất	XUAT	21	XUAT
\.


--
-- TOC entry 3730 (class 0 OID 17961)
-- Dependencies: 223
-- Data for Name: category_assignment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.category_assignment (title_1, title_2, title_3, title_4, id, code) FROM stdin;
H.hụt	HAO_HUT	\N	\N	1	HH
Nhiên liệu	NL	\N	\N	6	NL
MĐ	GIO_BAY	\N	\N	7	GB_MD
TK	GIO_BAY	\N	\N	8	GB_TK
M.Đất	NLTT	\N	\N	2	NLTT_MD
T.không	NLTT	\N	\N	3	NLTT_TK
M.Đất	HD	\N	\N	4	HD_MD
T.không	HD	\N	\N	5	HD_TK
Cộng	GIO_BAY	\N	\N	9	GB_SUM
Cộng	HD	\N	\N	10	HD_SUM
Cộng	NLTT	\N	\N	11	NLTT_SUM
\.


--
-- TOC entry 3737 (class 0 OID 17980)
-- Dependencies: 230
-- Data for Name: chitiet_nhiemvu; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.chitiet_nhiemvu (id, nhiemvu_id, nhiemvu) FROM stdin;
3	6	HH DTCĐ
4	6	HH T.Xuyên
5	9	C.gia bay
6	9	VN bay
7	11	Tác chiến cho bay
8	11	Tác chiến còn lại
9	11	Nổ máy sscđ
10	17	HL bay
11	17	HL NV còn lại
12	17	HL NV PO 6
13	28	CT Đảng, CTCT
14	28	Chiếu phim
16	47	Cắt cỏ sân bay
20	8	Tổn thất
21	14	Cứu hộ cứu nạn
22	15	Khai thác thông tin
23	16	Cơ yếu
24	20	Tác chiến điện tử
25	21	Công nghệ thông tin
26	22	HL nhà trường
27	23	Công tác Quân báo
28	24	Quân ra, vào, phép
29	25	Đào tạo thợ
30	26	Đ. viên thời chiến
31	27	N.vụ khác (T tra bay)
32	30	Kiểm tra đảng
33	31	Công tác chính sách
34	32	Ăn dưỡng
35	33	Nghiệp vụ cán bộ
36	34	Hậu cần đời sống
37	35	Công tác xăng dầu
38	36	VC Xăng dầu
39	37	Công tác vật tư
40	38	Đảo hạt HC
41	39	Hậu cần khác
42	40	Ô tô trạm nguồn
43	41	Vũ khí đạn, VKHK
44	42	Kỹ thuật thông tin
45	43	Kỹ thuật công binh
46	44	Tăng thiết giáp
47	45	Đo lường
48	46	KT Ra đa, tên lửa
49	49	Kỹ thuạt khác
52	60	bay qha
15	47	KT_Hàng không
\.


--
-- TOC entry 3739 (class 0 OID 17986)
-- Dependencies: 232
-- Data for Name: chitieu_pt; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.chitieu_pt (id, dvi_id, ctnv_id, quy_id, md, tk, nl, pt_id) FROM stdin;
1	3	7	20	00:00	20:00	67690	24
2	3	10	20	91:00	303:00	1420000	24
3	3	15	20	00:00	00:00	145000	24
4	3	3	20	00:00	00:00	6350	24
5	3	4	20	00:00	00:00	10300	24
6	4	15	20	00:00	00:00	201200	26
7	4	26	20	00:00	00:00	390000	26
8	4	10	20	130:35	653:00	417987	26
9	4	7	20	00:00	110:00	164382	26
10	4	4	20	00:00	00:00	10100	26
11	4	3	20	00:00	00:00	3400	26
12	5	4	20	00:00	00:00	34000	25
13	5	3	20	00:00	00:00	6400	25
14	5	15	20	00:00	00:00	407000	25
15	5	7	20	00:00	30:30	427310	25
16	5	10	20	102:09	338:30	2200163	25
20	6	4	20	00:00	00:00	6670	25
21	6	3	20	00:00	00:00	23850	25
22	6	15	20	00:00	00:00	643124	25
23	6	7	20	00:00	00:00	3511850	25
24	6	10	20	102:09	338:30	427000	25
25	8	4	20	00:00	00:00	2850	0
26	8	3	20	00:00	00:00	2850	0
27	9	4	20	00:00	00:00	1200	0
28	9	3	20	00:00	00:00	500	0
29	10	4	20	00:00	00:00	0	0
30	10	3	20	00:00	00:00	50	0
31	28	4	20	00:00	00:00	1650	0
32	28	3	20	00:00	00:00	1190	0
33	54	4	20	00:00	00:00	1050	0
34	54	3	20	00:00	00:00	1900	0
\.


--
-- TOC entry 3741 (class 0 OID 17995)
-- Dependencies: 234
-- Data for Name: chungloaixd; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.chungloaixd (id, loai, chungloai, tinhchat, code, priority_1, priority_2, priority_3, tinhchat2, stt_index, code2) FROM stdin;
1	Dầu Khác	DMN Mặt đất	DM nhờn	MD_DK	2	1	7	DM nhờn	3	DMN Mặt đất - Dầu Khác
2	Dầu truyền động	DMN Mặt đất	DM nhờn	MD_DTD	2	1	6	DM nhờn	2	DMN Mặt đất - Dầu truyền động
3	Dầu Đ.cơ ô tô	DMN Mặt đất	DM nhờn	MD_DCOTO	2	1	5	DM nhờn	1	DMN Mặt đất - Dầu Đ.cơ ô tô
4	Dầu Hạ cấp	Dầu Hạ cấp	Nhiên liệu	HA_CAP	1	2	4	Nhiên liệu	4	Dầu Hạ cấp - Dầu Hạ cấp
5	Dầu bay	Dầu bay	Nhiên liệu	DAU_BAY	1	2	3	Nhiên liệu	3	Dầu bay - Dầu bay
6	Diezel	Diezel	Nhiên liệu	DIEZEL	1	2	2	Nhiên liệu	2	Diezel - Diezel
7	Xăng ô tô	Xăng	Nhiên liệu	XANG_O_TO	1	2	1	Nhiên liệu	1	Xăng - Xăng ô tô
8	Mỡ giảm ma sát	DMN Mặt đất	DM nhờn	MD_MGMS	2	1	8	DM nhờn	4	DMN Mặt đất - Mỡ giảm ma sát
10	Dầu Đ.cơ	DMN Hàng không	DM nhờn	TK_DDC	2	2	10	DM nhờn	2	DMN Hàng không - Dầu Đ.cơ
11	Dầu thủy lực	DMN Hàng không	DM nhờn	TK_DTL	2	2	11	DM nhờn	3	DMN Hàng không - Dầu thủy lực
12	Dầu Khác	DMN Hàng không	DM nhờn	TK_DK	2	2	12	DM nhờn	4	DMN Hàng không - Dầu Khác
13	Mỡ nhờn	DMN Hàng không	DM nhờn	TK_MN	2	2	13	DM nhờn	5	DMN Hàng không - Mỡ nhờn
9	Dung môi	DMN Hàng không	DM nhờn	TK_DM	2	2	9	DM nhờn	1	DMN Hàng không - Dung môi
\.


--
-- TOC entry 3790 (class 0 OID 18459)
-- Dependencies: 283
-- Data for Name: configuration; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.configuration (id, parameter, value) FROM stdin;
1	ROOT_ID	54
\.


--
-- TOC entry 3743 (class 0 OID 18005)
-- Dependencies: 236
-- Data for Name: dinhmuc; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.dinhmuc (id, dm_tk_gio, dm_md_gio, dm_xm_km, dm_xm_gio, phuongtien_id, quarter_id) FROM stdin;
3	0	0	30	30	31	20
7	0	0	18	18	3	20
13	0	0	15	15	9	20
24	0	0	35	35	19	20
9	0	0	15	15	5	20
21	0	0	32	32	16	20
29	5527	3060	0	0	24	20
31	987	612	0	0	26	20
14	0	0	7	7	10	20
15	0	0	10	10	11	20
16	0	0	10	10	12	20
17	0	0	4	4	13	20
18	0	0	7	7	14	20
19	0	0	12	12	15	20
25	0	0	6	6	20	20
26	0	0	12	12	21	20
27	0	0	17	17	22	20
28	0	0	27	27	23	20
1	0	0	17	17	28	20
4	0	0	34	34	32	20
6	0	0	16	16	2	20
8	0	0	12	12	4	20
10	0	0	15	15	6	20
22	0	0	14	14	17	20
23	0	0	32	32	18	20
5	0	0	12	12	1	20
12	0	0	12	12	8	20
11	0	0	15	15	7	20
30	5034	2368	0	0	25	20
\.


--
-- TOC entry 3745 (class 0 OID 18009)
-- Dependencies: 238
-- Data for Name: donvi_tructhuoc; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.donvi_tructhuoc (id, root_id, dvi_tructhuoc_id) FROM stdin;
1	54	28
2	54	10
3	54	9
4	54	8
5	54	6
6	54	5
7	54	4
8	54	3
9	54	54
\.


--
-- TOC entry 3746 (class 0 OID 18012)
-- Dependencies: 239
-- Data for Name: dvi_nv; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.dvi_nv (id, dv_id, nv_id, createtime) FROM stdin;
\.


--
-- TOC entry 3748 (class 0 OID 18024)
-- Dependencies: 241
-- Data for Name: hanmuc; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.hanmuc (id, quarter_id, hanmuc_md, hanmuc_km, hanmuc_tk, soluong, pt_id) FROM stdin;
38	20	200:40	0	727:00	6416447	24
40	20	130:35	0	763:00	1173569	26
39	20	130:35	0	323:00	1632690	25
32	20	12:31	0		700	10
34	20	12:31	0		700	11
35	20	12:31	0		700	12
36	20	12:31	0		700	13
37	20	12:31	0		700	14
27	20	12:31	0		700	15
28	20	12:31	0		700	20
29	20	12:31	0		700	21
30	20	12:31	0		700	22
31	20	12:31	0		700	23
2	20	2:31	200		900	28
3	20	2:31	200		900	32
4	20	2:31	200		900	2
5	20	2:31	200		900	4
6	20	2:31	200		900	6
7	20	2:31	200		900	7
8	20	2:31	200		900	17
9	20	2:31	200		900	18
19	20	2:31	200		900	8
22	20	2:31	200		900	1
20	20	2:31	200		900	31
23	20	2:31	200		900	3
25	20	2:31	200		900	9
1	20	2:31	200		900	19
24	20	2:31	200		900	5
21	20	2:31	200		900	16
\.


--
-- TOC entry 3750 (class 0 OID 18030)
-- Dependencies: 243
-- Data for Name: hanmuc_nhiemvu2; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.hanmuc_nhiemvu2 (id, quarter_id, nhiemvu_id, dvi_id, diezel, daubay, xang) FROM stdin;
4	20	16	54	200	0	600
5	20	17	54	181480	6350000	264236
10	20	25	54	2081	0	2618
11	20	26	54	752	0	753
12	20	27	54	10500	0	10000
13	20	28	54	19830	0	14000
18	20	35	54	7500	0	11870
19	20	36	54	19150	0	9850
21	20	38	54	1930	0	1370
22	20	39	54	21600	0	5200
23	20	40	54	15600	0	14000
24	20	41	54	4700	0	3450
25	20	42	54	1000	0	2000
26	20	43	54	400	0	200
27	20	44	54	120	0	200
28	20	45	54	1000	0	1300
29	20	46	54	11800	0	7975
30	20	47	54	46460	1180200	40650
31	20	49	54	1000	0	500
32	20	6	54	2440	29310	5340
33	20	7	54	244870	1302506	181345
35	20	31	54	6300	0	7450
37	20	37	54	1111	0	1100
3	20	11	54	244870	1302506	99999
8	20	15	54	22000	390000	22222
6	20	20	54	123	0	500
1	20	13	54	6000	0	3333
34	20	10	54	244870	1302506	154888
17	20	34	54	88888	0	36920
7	20	21	54	300	0	450
39	20	52	54	322	3333	1234
42	20	8	54	244870	1302506	181345
43	20	9	54	244870	1302506	181345
44	20	12	54	123456	123456	12345
9	20	23	54	555	0	555
45	20	22	54	12323	0	12323
40	20	24	54	244870	0	154888
46	20	29	54	12348	0	2315
47	20	30	54	2222	0	22222
48	20	48	54	2545	0	2545
49	20	3	54	2000	0	2000
50	20	5	54	0	545345	0
51	20	14	54	4555	0	5345
15	20	32	54	5555	0	5455
52	20	33	54	4444	0	54544
53	20	4	54	4444	0	4544
\.


--
-- TOC entry 3752 (class 0 OID 18039)
-- Dependencies: 245
-- Data for Name: hanmuc_nhiemvu_taubay; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.hanmuc_nhiemvu_taubay (id, dvi_xuat_id, pt_id, ctnv_id, quy_id, tk, md, nhienlieu) FROM stdin;
2	4	34	7	20	110:00	00:00	164382
3	4	34	10	20	653:00	130:35	417987
5	4	34	15	20	00:00	00:00	201200
6	4	34	3	20	00:00	00:00	3400
7	4	34	4	20	00:00	00:00	10100
8	4	35	4	20	00:00	00:00	142857
9	4	35	3	20	00:00	00:00	142857
10	4	35	7	20	100:00	20:00	142857
12	4	35	15	20	12:00	20:00	142857
13	4	35	26	20	120:00	00:00	142857
14	4	36	26	20	120:00	00:00	142857
15	4	36	15	20	120:00	00:00	142857
16	4	36	10	20	120:00	00:00	142857
17	4	36	7	20	120:00	00:00	142857
18	4	36	4	20	00:00	00:00	142857
19	4	36	3	20	00:00	00:00	142857
20	4	37	3	20	00:00	00:00	142857
21	4	37	4	20	00:00	00:00	142857
22	4	37	7	20	222:00	00:00	142857
23	4	37	10	20	222:00	00:00	142857
24	4	37	15	20	222:00	00:00	142857
25	4	37	26	20	222:00	00:00	142857
26	5	24	3	20	00:00	00:00	142857
27	5	24	4	20	00:00	00:00	142857
28	5	24	5	20	00:00	00:00	0
29	5	24	6	20	00:00	00:00	0
30	5	24	7	20	132:00	00:00	142857
31	5	24	10	20	132:00	00:00	142857
32	5	24	15	20	132:00	00:00	142857
33	5	24	26	20	132:00	00:00	142857
34	6	24	26	20	132:00	00:00	142857
35	6	24	15	20	132:00	00:00	142857
36	6	24	15	20	359:00	11:00	142857
37	6	24	10	20	359:00	11:00	142857
38	6	24	7	20	359:00	11:00	142857
39	6	24	6	20	359:00	11:00	142857
40	6	24	5	20	359:00	11:00	142857
41	6	24	4	20	359:00	11:00	142857
42	6	24	3	20	359:00	11:00	142857
43	3	25	3	20	359:00	11:00	142857
44	3	25	4	20	359:00	11:00	142857
45	3	25	5	20	359:00	11:00	142857
46	3	25	6	20	359:00	11:00	142857
47	3	25	7	20	359:00	11:00	142857
48	3	25	10	20	359:00	11:00	142857
49	3	25	15	20	359:00	11:00	142857
50	3	25	20	20	00:00	00:00	142857
51	3	25	26	20	321:00	00:00	142857
52	28	0	3	20	00:00	00:00	1190
53	28	0	4	20	00:00	00:00	1650
54	10	0	4	20	00:00	00:00	1650
55	10	0	3	20	00:00	00:00	1190
56	9	0	3	20	00:00	00:00	1190
57	9	0	4	20	00:00	00:00	1190
58	8	0	9	20	00:00	00:00	0
59	8	0	3	20	00:00	00:00	2850
60	8	0	4	20	00:00	00:00	2850
61	54	0	3	20	00:00	00:00	2258
62	54	0	4	20	00:00	00:00	2258
4	4	34	26	20	00:22	00:00	390000
63	4	35	8	20	12:00	23:00	665858
11	4	35	10	20	325:00	22:00	142857
64	3	25	9	20	222:00	324:00	315488
\.


--
-- TOC entry 3721 (class 0 OID 17910)
-- Dependencies: 214
-- Data for Name: inventory; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.inventory (id, tdk_sscd, tdk_nvdx, status, petro_id, quarter_id, nhap_nvdx, nhap_sscd, xuat_nvdx, xuat_sscd, price, create_at) FROM stdin;
1178	20000	20000	IN_STOCK	23	20	20000	20000	26	0	142857	2024-12-19 17:11:16.129373
1337	0	0	OUT_OF_STOCK_ALL	34	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1338	0	0	OUT_OF_STOCK_ALL	55	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1339	0	0	OUT_OF_STOCK_ALL	70	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1340	0	0	OUT_OF_STOCK_ALL	56	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1341	0	0	OUT_OF_STOCK_ALL	57	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1342	0	0	OUT_OF_STOCK_ALL	58	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1343	0	0	OUT_OF_STOCK_ALL	59	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1344	0	0	OUT_OF_STOCK_ALL	60	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1345	0	0	OUT_OF_STOCK_ALL	61	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1346	0	0	OUT_OF_STOCK_ALL	62	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1347	0	0	OUT_OF_STOCK_ALL	63	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1348	0	0	OUT_OF_STOCK_ALL	64	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1349	0	0	OUT_OF_STOCK_ALL	65	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1350	0	0	OUT_OF_STOCK_ALL	66	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1351	0	4	IN_STOCK	67	24	4	0	0	0	23423	2024-12-30 16:34:02.222852
1352	0	0	OUT_OF_STOCK_ALL	68	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1353	0	0	OUT_OF_STOCK_ALL	69	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1358	644	-86	IN_STOCK	21	24	5432	0	0	0	34322	2024-12-30 17:35:27.786986
1193	20000	20000	IN_STOCK	35	20	20000	20000	0	0	142857	2024-12-19 00:00:00
1202	20000	20000	IN_STOCK	47	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1200	20000	20000	IN_STOCK	45	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1206	20000	20000	IN_STOCK	51	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1188	20000	20000	IN_STOCK	30	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1191	20000	20000	IN_STOCK	33	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1210	20000	20000	IN_STOCK	55	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1203	20000	20000	IN_STOCK	48	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1219	20000	20000	IN_STOCK	64	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1221	20000	20000	IN_STOCK	66	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1222	20000	20000	IN_STOCK	67	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1224	20000	20000	IN_STOCK	69	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1223	20000	20000	IN_STOCK	68	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1300	20000	20000	IN_STOCK	21	20	22222	0	0	0	22222	2024-12-30 09:34:16.840238
1355	0	0	OUT_OF_STOCK_ALL	20	24	4999	0	0	0	123828	2024-12-30 16:34:57.118246
1354	322	-43	OUT_OF_STOCK_NVDX	21	24	22388	0	0	0	19282	2024-12-30 16:34:57.09181
1357	0	0	IN_STOCK	20	24	19310	0	0	0	9182	2024-12-30 16:47:31.611083
1205	20000	20000	IN_STOCK	50	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1208	20000	20000	IN_STOCK	53	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1225	20000	20000	IN_STOCK	70	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1184	20000	20000	IN_STOCK	26	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1192	20000	20000	IN_STOCK	34	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1187	20000	20000	IN_STOCK	29	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1218	20000	20000	IN_STOCK	63	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1209	20000	20000	IN_STOCK	54	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1186	20000	20000	IN_STOCK	28	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1183	20000	20000	IN_STOCK	25	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1190	20000	20000	IN_STOCK	32	20	20000	20000	0	0	142857	2024-12-19 00:00:00
1194	20000	20000	IN_STOCK	39	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1185	20000	20000	IN_STOCK	27	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1195	20000	20000	IN_STOCK	40	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1189	20000	20000	IN_STOCK	31	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1196	20000	20000	IN_STOCK	41	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1197	20000	20000	IN_STOCK	42	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1198	20000	20000	IN_STOCK	43	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1220	20000	20000	IN_STOCK	65	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1199	20000	20000	IN_STOCK	44	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1201	20000	20000	IN_STOCK	46	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1214	20000	20000	IN_STOCK	59	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1211	20000	20000	IN_STOCK	56	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1217	20000	20000	IN_STOCK	62	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1212	20000	20000	IN_STOCK	57	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1215	20000	20000	IN_STOCK	60	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1213	20000	20000	IN_STOCK	58	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1216	20000	20000	IN_STOCK	61	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1207	20000	20000	IN_STOCK	52	20	20000	20000	0	0	142857	2024-12-19 17:11:16.129373
1182	20000	20000	IN_STOCK	38	20	20000	20000	0	0	142857	2024-12-19 00:00:00
1177	20000	20000	IN_STOCK	22	20	20000	20000	0	0	142857	2024-12-19 00:00:00
1175	20000	20000	IN_STOCK	20	20	20000	20000	0	0	142857	2024-12-19 00:00:00
1179	20000	20000	IN_STOCK	24	20	20000	20000	0	0	142857	2024-12-19 00:00:00
1204	20000	20000	IN_STOCK	49	20	20000	20000	0	0	142857	2024-12-19 00:00:00
1176	20000	20000	IN_STOCK	21	20	20000	20000	2342	0	142857	2024-12-19 00:00:00
1180	20000	20000	IN_STOCK	36	20	20000	20000	921	0	142857	2024-12-19 00:00:00
1181	20000	20000	IN_STOCK	37	20	20000	20000	4455	0	142857	2024-12-19 00:00:00
1301	0	0	OUT_OF_STOCK_ALL	45	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1302	0	0	OUT_OF_STOCK_ALL	46	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1303	0	0	OUT_OF_STOCK_ALL	47	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1304	0	3333	IN_STOCK	48	24	3333	0	0	0	34232	2024-12-30 16:34:02.222852
1305	0	0	OUT_OF_STOCK_ALL	51	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1306	0	0	OUT_OF_STOCK_ALL	52	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1307	0	0	OUT_OF_STOCK_ALL	53	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1308	0	0	OUT_OF_STOCK_ALL	54	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1309	0	0	OUT_OF_STOCK_ALL	43	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1310	0	0	OUT_OF_STOCK_ALL	44	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1311	0	0	OUT_OF_STOCK_ALL	23	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1312	0	0	OUT_OF_STOCK_ALL	24	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1313	0	0	OUT_OF_STOCK_ALL	36	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1314	0	0	OUT_OF_STOCK_ALL	37	24	0	0	0	0	142857	2024-12-30 16:34:02.222852
1315	0	0	OUT_OF_STOCK_ALL	38	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1316	0	0	OUT_OF_STOCK_ALL	22	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1317	0	0	OUT_OF_STOCK_ALL	20	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1318	322	0	IN_STOCK	21	24	0	322	0	0	2322	2024-12-30 16:34:02.222852
1319	0	-43	OUT_OF_STOCK_ALL	21	24	0	0	43	0	23423	2024-12-30 16:34:02.222852
1320	0	0	OUT_OF_STOCK_ALL	21	24	0	0	0	0	142857	2024-12-30 16:34:02.222852
1321	0	0	OUT_OF_STOCK_ALL	35	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1322	0	0	OUT_OF_STOCK_ALL	39	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1323	0	0	OUT_OF_STOCK_ALL	40	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1324	0	0	OUT_OF_STOCK_ALL	41	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1325	0	0	OUT_OF_STOCK_ALL	42	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1326	0	0	OUT_OF_STOCK_ALL	25	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1327	0	0	OUT_OF_STOCK_ALL	26	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1328	0	0	OUT_OF_STOCK_ALL	27	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1329	0	5000	IN_STOCK	28	24	5000	0	0	0	12458	2024-12-30 16:34:02.222852
1330	0	3000	IN_STOCK	29	24	3000	0	0	0	16485	2024-12-30 16:34:02.222852
1331	0	0	OUT_OF_STOCK_ALL	30	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1332	0	333	IN_STOCK	31	24	333	0	0	0	23422	2024-12-30 16:34:02.222852
1333	0	0	OUT_OF_STOCK_ALL	32	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1334	0	0	OUT_OF_STOCK_ALL	49	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1335	0	0	OUT_OF_STOCK_ALL	50	24	0	0	0	0	0	2024-12-30 16:34:02.222852
1336	0	0	OUT_OF_STOCK_ALL	33	24	0	0	0	0	0	2024-12-30 16:34:02.222852
\.


--
-- TOC entry 3754 (class 0 OID 18058)
-- Dependencies: 247
-- Data for Name: ledger_details; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ledger_details (ma_xd, ten_xd, chung_loai, chat_luong, phai_xuat, thuc_xuat, don_gia, id, loaixd_id, phuongtien_id, ledger_id, thuc_xuat_tk, so_luong, thuc_nhap, phai_nhap, thanhtien, haohut_sl, nl_km, nl_gio, so_luong_px, sscd_nvdx, ty_trong, nhiet_do_tt, he_so_vcf, nhap_nvdx, nhap_sscd, xuat_nvdx, xuat_sscd) FROM stdin;
A80	Xăng A80	Xăng	\N	2342	2342	142857	1380	21	0	618	0	2342	0	0	334571094	0	0	0	2342	NVDX	0	0	0	0	0	0	0
TC-01	Dầu TC-1	Dầu bay	\N	4443	4443	142857	1381	37	0	618	0	4443	0	0	634713651	0	0	0	4443	NVDX	0	0	0	0	0	0	0
JETA-01	Dầu JETA-01	Dầu bay	\N	921	0	142857	1382	36	24	619	921	921	0	0	131571297	0	0	921	921	NVDX	0	0	0	0	0	0	0
TC-1	DầU TC-1	Dầu Hạ cấp	\N	26	0	142857	1383	23	24	619	26	26	0	0	3714282	0	0	0	26	NVDX	0	0	0	0	0	0	0
TC-01	Dầu TC-1	Dầu bay	\N	12	12	142857	1384	37	34	620	0	12	0	0	1714284	0	0	0	12	NVDX	0	0	0	0	0	0	0
CastrolCRB200W-50	CastrolCRB200W-50	DMN Mặt đất	\N	0	0	12458	1385	28	0	621	0	5000	5000	5000	62290000	0	\N	\N	5000	NVDX	0	0	0	5000	\N	\N	\N
HelixHX-3	HelixHX-3	DMN Mặt đất	\N	0	0	16485	1386	29	0	621	0	3000	3000	3000	49455000	0	\N	\N	3000	NVDX	0	0	0	3000	\N	\N	\N
Mỡ Gzeose GL2	Mỡ Gzeose GL2	DMN Mặt đất	\N	0	0	34232	1387	48	0	622	0	3333	3333	4432	114095256	0	\N	\N	4432	NVDX	0	0	0	3333	\N	\N	\N
Mỡ 221	Mỡ 221	DMN Hàng không	\N	0	0	23423	1388	67	0	622	0	4	4	4	93692	0	\N	\N	4	NVDX	0	0	0	4	\N	\N	\N
A80	Xăng A80	Xăng	\N	0	0	23423	1389	21	0	623	0	222	222	222	5199906	0	\N	\N	222	NVDX	0	0	0	0	0	0	0
Rimula R4X	Rimula R4X	DMN Mặt đất	\N	0	0	23422	1390	31	0	624	0	333	333	333	7799526	0	\N	\N	333	NVDX	0	0	0	333	0	0	0
A80	Xăng A80	Xăng	\N	0	0	2322	1391	21	0	625	0	322	322	232	747684	0	\N	\N	232	SSCD	0	0	0	0	322	0	0
A80	Xăng A80	Xăng	\N	43	43	23423	1392	21	0	626	0	43	0	0	1007189	0	0	0	43	NVDX	0	0	0	0	0	43	0
A80	Xăng A80	Xăng	\N	0	0	22222	1393	21	0	627	0	22222	22222	22222	493817284	0	\N	\N	22222	NVDX	0	0	0	22222	0	0	0
A80	Xăng A80	Xăng	\N	0	0	213212	1394	21	0	629	0	23	23	23	4903876	0	\N	\N	23	NVDX	0	0	0	23	0	0	0
A80	Xăng A80	Xăng	\N	0	0	19282	1395	21	0	630	0	20000	20000	20000	385640000	0	\N	\N	20000	NVDX	0	0	0	20000	0	0	0
E5-RON92	XANG E5 RON92	Xăng	\N	0	0	123828	1396	20	0	630	0	4999	4999	4999	619016172	0	\N	\N	4999	NVDX	0	0	0	4999	0	0	0
A80	Xăng A80	Xăng	\N	0	0	19282	1397	21	0	631	0	39939	39939	39939	770103798	0	\N	\N	39939	NVDX	0	0	0	39939	0	0	0
A80	Xăng A80	Xăng	\N	0	0	19282	1398	21	0	632	0	2388	2388	2328	46045416	0	\N	\N	2328	NVDX	0	0	0	2388	0	0	0
E5-RON92	XANG E5 RON92	Xăng	\N	0	0	9182	1399	20	0	632	0	19310	19310	3213	177304420	0	\N	\N	3213	NVDX	0	0	0	19310	0	0	0
A80	Xăng A80	Xăng	\N	0	0	34322	1400	21	0	633	0	5432	5432	5432	186437104	0	\N	\N	5432	NVDX	0	0	0	5432	0	0	0
\.


--
-- TOC entry 3755 (class 0 OID 18078)
-- Dependencies: 248
-- Data for Name: ledger_map; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ledger_map (id, loaixd_id, header_id, soluong, mucgia_id, quarter_id, status) FROM stdin;
\.


--
-- TOC entry 3723 (class 0 OID 17924)
-- Dependencies: 216
-- Data for Name: ledgers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ledgers (id, quarter_id, bill_id, amount, from_date, end_date, status, sl_tieuthu_md, sl_tieuthu_tk, dvi_nhan_id, dvi_xuat_id, loai_phieu, dvi_nhan, dvi_xuat, loaigiobay, nguoi_nhan, so_xe, lenh_so, nhiemvu, nhiemvu_id, so_km, tcn_id, "timestamp", giohd_md, giohd_tk, loainv, tructhuoc, lpt, lpt_2, version, create_by, root_id, pt_id) FROM stdin;
618	20	3382	969284745	\N	\N	ACTIVE	0	0	25	54	XUAT	Công ty XDQD	f Bộ						0	0	2	2024-12-29 15:48:16.792784	00:00:00	00:00:00	\N	PC	\N	\N	0	2	54	0
620	20	3222	1714284	\N	\N	ACTIVE	0	0	0	4	XUAT		e916	MD				Cắt cỏ sân bay	16	0	0	2024-12-29 16:15:17.201763	\N	\N	\N	TT_XM	MB-TT	MAYBAY	0	2	54	34
619	20	2883	135285579	\N	\N	IN_ACTIVE	0	0	0	6	XUAT		e927	TK				Nổ máy sscđ	9	0	0	2024-12-29 15:49:13.291132	00:00:00	00:10:00	\N	TT_XM	MB-CD	MAYBAY	0	2	54	24
621	20	7732	111745000	2024-12-24	2024-12-25	ACTIVE	0	0	54	22	NHAP	f Bộ	Trường SQKQ	\N				\N	0	0	1	2024-12-29 20:26:17.183181	\N	\N	\N	DVK	\N	\N	0	2	54	0
622	20	2123	114188948	2024-12-29	2024-12-30	ACTIVE	0	0	54	47	NHAP	f Bộ	Hạ cấp	\N				\N	0	0	1	2024-12-29 20:30:54.788149	\N	\N	\N	K	\N	\N	0	2	54	0
623	20	3422	5199906	\N	\N	ACTIVE	0	0	54	22	NHAP	f Bộ	Trường SQKQ	\N				\N	0	0	3	2024-12-29 20:32:42.416991	\N	\N	\N	DVK	\N	\N	0	2	54	0
624	20	2343	7799526	2024-12-29	2024-12-30	ACTIVE	0	0	54	3	NHAP	f Bộ	e921	\N				\N	0	0	3	2024-12-29 20:34:51.214475	\N	\N	\N	FNB	\N	\N	0	2	54	0
625	20	54232	747684	\N	\N	ACTIVE	0	0	54	37	NHAP	f Bộ	Đổi hàng DT	\N	23			\N	0	0	1	2024-12-29 20:35:34.125236	\N	\N	\N	K	\N	\N	0	2	54	0
626	20	5489	1007189	\N	\N	ACTIVE	0	0	90	54	XUAT	QK2	f Bộ						0	0	2	2024-12-29 20:37:01.37364	00:00:00	00:00:00	\N	QK2	\N	\N	0	2	54	0
627	20	3423	493817284	\N	\N	IN_ACTIVE	0	0	54	22	NHAP	f Bộ	Trường SQKQ	\N				\N	0	0	1	2024-12-30 09:34:16.800555	\N	\N	\N	DVK	\N	\N	0	2	54	0
629	24	4322	4903876	2024-12-30	\N	ACTIVE	0	0	54	22	NHAP	f Bộ	Trường SQKQ	\N				\N	0	0	1	2024-12-30 14:44:36.777498	\N	\N	\N	DVK	\N	\N	0	2	54	0
631	24	7368	1406134270	2024-12-30	\N	ACTIVE	0	0	54	22	NHAP	f Bộ	Trường SQKQ	\N				\N	0	0	1	2024-12-30 16:42:51.982277	\N	\N	\N	DVK	\N	\N	0	2	54	0
632	24	2873	223349836	2024-12-30	\N	ACTIVE	0	0	54	22	NHAP	f Bộ	Trường SQKQ	\N				\N	0	0	1	2024-12-30 16:47:31.508821	\N	\N	\N	DVK	\N	\N	0	2	54	0
630	24	7732	1004656172	2024-12-30	\N	IN_ACTIVE	0	0	54	22	NHAP	f Bộ	Trường SQKQ	\N				\N	0	0	1	2024-12-30 16:34:57.068862	\N	\N	\N	DVK	\N	\N	0	2	54	0
633	24	3243	186437104	2024-12-30	\N	IN_ACTIVE	0	0	54	34	NHAP	f Bộ	Nhà máy A32	\N				\N	0	0	1	2024-12-30 17:35:27.745436	\N	\N	\N	K	\N	\N	0	2	54	0
\.


--
-- TOC entry 3756 (class 0 OID 18083)
-- Dependencies: 249
-- Data for Name: lichsuxnk; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.lichsuxnk (id, ten_xd, loai_phieu, tontruoc, soluong, tonsau, "timestamp", type, so, dvn, dvx, chungloaixd, quy_id, gia) FROM stdin;
1138	Xăng A80	XUAT	20000	2342	17658	2024-12-29 15:48:16.850383	NVDX	3382	Công ty XDQD	f Bộ	Xăng	20	142857
1139	Dầu TC-1	XUAT	20000	4443	15557	2024-12-29 15:48:16.866243	NVDX	3382	Công ty XDQD	f Bộ	Dầu bay	20	142857
1140	Dầu JETA-01	XUAT	20000	921	19079	2024-12-29 15:49:13.309492	NVDX	2883		e927	Dầu bay	20	142857
1141	DầU TC-1	XUAT	20000	26	19974	2024-12-29 15:49:13.328165	NVDX	2883		e927	Dầu Hạ cấp	20	142857
1142	Dầu TC-1	XUAT	15557	12	15545	2024-12-29 16:15:17.262351	NVDX	3222		e916	Dầu bay	20	142857
1143	CastrolCRB200W-50	NHAP	0	5000	5000	2024-12-29 20:26:17.221479	NVDX	7732	f Bộ	Trường SQKQ	DMN Mặt đất	20	12458
1144	HelixHX-3	NHAP	0	3000	3000	2024-12-29 20:26:17.245793	NVDX	7732	f Bộ	Trường SQKQ	DMN Mặt đất	20	16485
1145	Mỡ Gzeose GL2	NHAP	0	3333	3333	2024-12-29 20:30:54.841765	NVDX	2123	f Bộ	Hạ cấp	DMN Mặt đất	20	34232
1146	Mỡ 221	NHAP	0	4	4	2024-12-29 20:30:54.866042	NVDX	2123	f Bộ	Hạ cấp	DMN Hàng không	20	23423
1147	Xăng A80	NHAP	0	222	222	2024-12-29 20:32:42.448459	NVDX	3422	f Bộ	Trường SQKQ	Xăng	20	23423
1148	Rimula R4X	NHAP	0	333	333	2024-12-29 20:34:51.251358	NVDX	2343	f Bộ	e921	DMN Mặt đất	20	23422
1149	Xăng A80	XUAT	222	43	179	2024-12-29 20:37:01.432376	NVDX	5489	QK2	f Bộ	Xăng	20	23423
1150	Xăng A80	NHAP	0	22222	22222	2024-12-30 09:34:16.844119	NVDX	3423	f Bộ	Trường SQKQ	Xăng	20	22222
1151	Xăng A80	NHAP	0	20000	20000	2024-12-30 16:34:57.096253	NVDX	7732	f Bộ	Trường SQKQ	Xăng	24	19282
1152	XANG E5 RON92	NHAP	0	4999	4999	2024-12-30 16:34:57.121635	NVDX	7732	f Bộ	Trường SQKQ	Xăng	24	123828
1153	Xăng A80	NHAP	22388	2388	24776	2024-12-30 16:47:31.596509	NVDX	2873	f Bộ	Trường SQKQ	Xăng	24	19282
1154	XANG E5 RON92	NHAP	0	19310	19310	2024-12-30 16:47:31.614356	NVDX	2873	f Bộ	Trường SQKQ	Xăng	24	9182
1155	Xăng A80	NHAP	0	5432	5432	2024-12-30 17:35:27.793972	NVDX	3243	f Bộ	Nhà máy A32	Xăng	24	34322
\.


--
-- TOC entry 3758 (class 0 OID 18097)
-- Dependencies: 251
-- Data for Name: loai_nhiemvu; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.loai_nhiemvu (id, task_name) FROM stdin;
1	NV_BAY
2	KHAC
3	HAOHUT
\.


--
-- TOC entry 3762 (class 0 OID 18115)
-- Dependencies: 255
-- Data for Name: loai_phuongtien; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.loai_phuongtien (id, type_name, type) FROM stdin;
1	MB-CD	MAYBAY
3	XE_CHAY_DIEZEL	XE
4	XE_CHAY_XANG	XE
6	MAY_CHAY_XANG	MAY
7	MB-TT	MAYBAY
5	MAY_CHAY_DIEZEL	MAY
\.


--
-- TOC entry 3764 (class 0 OID 18121)
-- Dependencies: 257
-- Data for Name: loaixd2; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.loaixd2 (id, maxd, tenxd, status, "timestamp", petroleum_type_id) FROM stdin;
45	Mỡ 1-13	Mỡ 1-13	ACTIVE	2024-09-19 11:19:54.953742	8
46	Mỡ SOLE DON	Mỡ SOLE DON	ACTIVE	2024-09-19 11:19:54.958084	8
47	Caxilium No2	Caxilium No2	ACTIVE	2024-09-19 11:19:54.962469	8
48	Mỡ Gzeose GL2	Mỡ Gzeose GL2	ACTIVE	2024-09-19 11:19:54.966833	8
51	Dầu MC-8P	Dầu MC-8P	ACTIVE	2024-09-19 11:19:54.981124	10
52	Dầu MC-20	Dầu MC-20	ACTIVE	2024-09-19 11:19:54.986015	10
53	Dầu B-3V	Dầu B-3V	ACTIVE	2024-09-19 11:19:54.991908	10
54	Dầu IPM-10	Dầu IPM-10	ACTIVE	2024-09-19 11:19:54.997883	10
43	Phanh BCK	Phanh BCK	ACTIVE	2024-09-19 11:19:54.945126	1
44	MIL PC06	MIL PC06	ACTIVE	2024-09-19 11:19:54.949414	1
23	TC-1	DầU TC-1	ACTIVE	2024-09-19 11:19:54.858032	4
24	JetA-1K	DầU JetA-1K	ACTIVE	2024-09-19 11:19:54.863934	4
36	JETA-01	Dầu JETA-01	ACTIVE	2024-09-19 11:19:54.914126	5
37	TC-01	Dầu TC-1	ACTIVE	2024-09-19 11:19:54.918393	5
38	JETA-1K	Dầu JETA-1K	ACTIVE	2024-09-19 11:19:54.922623	5
22	DO 0,05% S	DO 0,05% S	ACTIVE	2024-09-19 11:19:54.853521	6
20	E5-RON92	XANG E5 RON92	ACTIVE	2024-09-19 11:19:54.844181	7
21	A80	Xăng A80	ACTIVE	2024-09-19 11:19:54.848967	7
35	Galube90eps	Galube90eps	ACTIVE	2024-09-19 11:19:54.910241	2
39	MILPC02-SAE90	MILPC02-SAE90	ACTIVE	2024-09-19 11:19:54.926926	2
40	MILPC03-SAE90	MILPC03-SAE90	ACTIVE	2024-09-19 11:19:54.931112	2
41	GearGL4 W90	GearGL4 W90	ACTIVE	2024-09-19 11:19:54.935417	2
42	Morrisong 140ef90	Morrisong 140ef90	ACTIVE	2024-09-19 11:19:54.940886	2
25	MILPCO1-SAE40	MILPCO1-SAE40	ACTIVE	2024-09-19 11:19:54.868552	3
26	MILPCO1-S-SAE40	MILPCO1-S-SAE40	ACTIVE	2024-09-19 11:19:54.873271	3
27	MT-16P	MT-16P	ACTIVE	2024-09-19 11:19:54.877324	3
28	CastrolCRB200W-50	CastrolCRB200W-50	ACTIVE	2024-09-19 11:19:54.881614	3
29	HelixHX-3	HelixHX-3	ACTIVE	2024-09-19 11:19:54.88599	3
30	Lukoi 15W-40	Lukoi 15W-40	ACTIVE	2024-09-19 11:19:54.890177	3
31	Rimula R4X	Rimula R4X	ACTIVE	2024-09-19 11:19:54.894442	3
32	Niwanano ios32-HG32	Niwanano ios32-HG32	ACTIVE	2024-09-19 11:19:54.898443	3
49	Mỡ Gzeose GL3	Mỡ Gzeose GL3	ACTIVE	2024-09-19 11:19:54.971408	8
50	Opalgrease No3	Opalgrease No3	ACTIVE	2024-09-19 11:19:54.976181	8
33	QUATVNM 20W50	QUATVNM 20W50	ACTIVE	2024-09-19 11:19:54.90239	3
34	QUAT9000-0W20	QUAT9000-0W20	ACTIVE	2024-09-19 11:19:54.906315	3
55	Hypôit (TC Gip)	Hypôit (TC Gip)	ACTIVE	2024-09-19 11:19:55.00423	10
70	Xăng CN	Xăng CN	ACTIVE	2024-09-19 11:19:55.098301	9
56	Dầu AMG-10	Dầu AMG-10	ACTIVE	2024-09-19 11:19:55.010107	11
57	Turbonicoil210A(IPM-10)	Turbonicoil210A(IPM-10)	ACTIVE	2024-09-19 11:19:55.016126	12
58	Aeroshell Fluid41(AMG-10)	Aeroshell Fluid41(AMG-10)	ACTIVE	2024-09-19 11:19:55.022395	12
59	Turbonicoil 321(MC8P)	Turbonicoil 321(MC8P)	ACTIVE	2024-09-19 11:19:55.028316	12
60	Turbonicoil 35M (B3V)	Turbonicoil 35M (B3V)	ACTIVE	2024-09-19 11:19:55.035721	12
61	Dầu 132-25	Dầu 132-25	ACTIVE	2024-09-19 11:19:55.042341	12
62	Grease28 (Mỡ 221)	Grease28 (Mỡ 221)	ACTIVE	2024-09-19 11:19:55.048678	12
63	Grease33 (OKB)	Grease33 (OKB)	ACTIVE	2024-09-19 11:19:55.054736	12
64	Grease22	Grease22	ACTIVE	2024-09-19 11:19:55.061197	12
65	OKB122-7-5	OKB122-7-5	ACTIVE	2024-09-19 11:19:55.067343	12
66	Mỡ 201	Mỡ 201	ACTIVE	2024-09-19 11:19:55.073611	13
67	Mỡ 221	Mỡ 221	ACTIVE	2024-09-19 11:19:55.079972	13
68	Mỡ HK-50	Mỡ HK-50	ACTIVE	2024-09-19 11:19:55.085885	13
69	Mỡ số 9	Mỡ số 9	ACTIVE	2024-09-19 11:19:55.09204	13
\.


--
-- TOC entry 3766 (class 0 OID 18128)
-- Dependencies: 259
-- Data for Name: mucgia; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.mucgia (id, price, amount, quarter_id, item_id, status, "timestamp", nvdx, sscd) FROM stdin;
3543	142857	40000	20	45	IN_STOCK	2024-12-16 13:48:16.399881	20000	20000
3544	142857	40000	20	46	IN_STOCK	2024-12-16 13:48:16.402984	20000	20000
3545	142857	40000	20	47	IN_STOCK	2024-12-16 13:48:16.405965	20000	20000
3546	142857	40000	20	48	IN_STOCK	2024-12-16 13:48:16.409112	20000	20000
3547	142857	40000	20	51	IN_STOCK	2024-12-16 13:48:16.411349	20000	20000
3548	142857	40000	20	52	IN_STOCK	2024-12-16 13:48:16.413634	20000	20000
3549	142857	40000	20	53	IN_STOCK	2024-12-16 13:48:16.41713	20000	20000
3550	142857	40000	20	54	IN_STOCK	2024-12-16 13:48:16.420169	20000	20000
3551	142857	40000	20	43	IN_STOCK	2024-12-16 13:48:16.422421	20000	20000
3552	142857	40000	20	44	IN_STOCK	2024-12-16 13:48:16.424659	20000	20000
3553	142857	40000	20	23	IN_STOCK	2024-12-16 13:48:16.426896	20000	20000
3554	142857	40000	20	24	IN_STOCK	2024-12-16 13:48:16.429948	20000	20000
3556	142857	40000	20	37	IN_STOCK	2024-12-16 13:48:16.43618	20000	20000
3561	142857	40000	20	35	IN_STOCK	2024-12-16 13:48:16.451107	20000	20000
3562	142857	40000	20	39	IN_STOCK	2024-12-16 13:48:16.453416	20000	20000
3563	142857	40000	20	40	IN_STOCK	2024-12-16 13:48:16.456462	20000	20000
3564	142857	40000	20	41	IN_STOCK	2024-12-16 13:48:16.459565	20000	20000
3565	142857	40000	20	42	IN_STOCK	2024-12-16 13:48:16.461994	20000	20000
3566	142857	40000	20	25	IN_STOCK	2024-12-16 13:48:16.464285	20000	20000
3567	142857	40000	20	26	IN_STOCK	2024-12-16 13:48:16.467251	20000	20000
3568	142857	40000	20	27	IN_STOCK	2024-12-16 13:48:16.47038	20000	20000
3570	142857	40000	20	29	IN_STOCK	2024-12-16 13:48:16.47655	20000	20000
3571	142857	40000	20	30	IN_STOCK	2024-12-16 13:48:16.478981	20000	20000
3572	142857	40000	20	31	IN_STOCK	2024-12-16 13:48:16.48127	20000	20000
3559	142857	38768	20	20	IN_STOCK	2024-12-16 00:00:00	18768	20000
3560	142857	33254	20	21	IN_STOCK	2024-12-16 00:00:00	13254	20000
3557	142857	39996	20	38	IN_STOCK	2024-12-16 00:00:00	19996	20000
3558	142857	39673	20	22	IN_STOCK	2024-12-16 00:00:00	19673	20000
3569	142857	39996	20	28	IN_STOCK	2024-12-16 00:00:00	19996	20000
3555	142857	34290	20	36	IN_STOCK	2024-12-16 00:00:00	14290	20000
3573	142857	40000	20	32	IN_STOCK	2024-12-16 13:48:16.483511	20000	20000
3574	142857	40000	20	49	IN_STOCK	2024-12-16 13:48:16.487722	20000	20000
3575	142857	40000	20	50	IN_STOCK	2024-12-16 13:48:16.490705	20000	20000
3576	142857	40000	20	33	IN_STOCK	2024-12-16 13:48:16.493669	20000	20000
3577	142857	40000	20	34	IN_STOCK	2024-12-16 13:48:16.496851	20000	20000
3578	142857	40000	20	55	IN_STOCK	2024-12-16 13:48:16.500148	20000	20000
3579	142857	40000	20	70	IN_STOCK	2024-12-16 13:48:16.502386	20000	20000
3580	142857	40000	20	56	IN_STOCK	2024-12-16 13:48:16.505443	20000	20000
3581	142857	40000	20	57	IN_STOCK	2024-12-16 13:48:16.507681	20000	20000
3582	142857	40000	20	58	IN_STOCK	2024-12-16 13:48:16.510654	20000	20000
3583	142857	40000	20	59	IN_STOCK	2024-12-16 13:48:16.513676	20000	20000
3584	142857	40000	20	60	IN_STOCK	2024-12-16 13:48:16.516929	20000	20000
3585	142857	40000	20	61	IN_STOCK	2024-12-16 13:48:16.519166	20000	20000
3586	142857	40000	20	62	IN_STOCK	2024-12-16 13:48:16.521402	20000	20000
3587	142857	40000	20	63	IN_STOCK	2024-12-16 13:48:16.52438	20000	20000
3588	142857	40000	20	64	IN_STOCK	2024-12-16 13:48:16.527403	20000	20000
3589	142857	40000	20	65	IN_STOCK	2024-12-16 13:48:16.530421	20000	20000
3590	142857	40000	20	66	IN_STOCK	2024-12-16 13:48:16.532655	20000	20000
3591	142857	40000	20	67	IN_STOCK	2024-12-16 13:48:16.534892	20000	20000
3592	142857	40000	20	68	IN_STOCK	2024-12-16 13:48:16.537871	20000	20000
3593	142857	40000	20	69	IN_STOCK	2024-12-16 13:48:16.540124	20000	20000
3594	12092	2000	20	51	IN_STOCK	2024-12-16 15:41:08.299626	2000	0
3595	23948	32	20	52	IN_STOCK	2024-12-16 15:41:08.299626	32	0
3596	23911	20	20	43	IN_STOCK	2024-12-16 15:46:02.421584	20	0
3598	12399	1231	20	47	IN_STOCK	2024-12-16 16:14:18.004349	1231	0
3599	12345	4578	20	21	IN_STOCK	2024-12-16 19:58:01.951754	4578	0
3597	7723	18	20	45	IN_STOCK	2024-12-16 00:00:00	18	0
3601	1242	233	20	20	IN_STOCK	2024-12-17 10:14:33.990719	233	0
3602	543423	22	20	22	IN_STOCK	2024-12-17 10:14:33.990719	22	0
3603	12312	2	20	21	IN_STOCK	2024-12-17 10:41:39.018219	2	0
3604	2322	122	20	21	IN_STOCK	2024-12-17 10:42:07.901562	122	0
3600	3548	4882	20	20	IN_STOCK	2024-12-16 00:00:00	4882	0
3605	21248	20	20	25	IN_STOCK	2024-12-18 15:58:31.89579	20	0
\.


--
-- TOC entry 3769 (class 0 OID 18140)
-- Dependencies: 262
-- Data for Name: nguon_nx; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.nguon_nx (id, ten, status, tructhuoc_id, code) FROM stdin;
22	Trường SQKQ	NORMAL	31	TRUONG_SQKQ
24	Lữ đoàn 918	NORMAL	31	LU_DOAN918
28	d Nội Bài	NORMAL	29	d_NOIBAI
33	QPQT-22	NORMAL	32	QPQT_22
34	Nhà máy A32	NORMAL	32	NHA_MAY_A32
36	Viettel S125VT	NORMAL	32	Viettel_S125VT
37	Đổi hàng DT	NORMAL	32	DOI_HANG_DT
40	Hao hụt	NORMAL	7	HAO_HUT
47	Hạ cấp	NORMAL	32	HA_CAP
51	Dự trữ Quốc Gia	NORMAL	32	DU_TRU_QUOC_GIA
52	Tổn thất	NORMAL	24	TON_THAT
53	Bảo quản	NORMAL	18	BAO_QUAN
54	f Bộ	ROOT	33	F_BO
23	Bộ tham mưu	NORMAL	31	BO_THAM_MUU
29	Cty XD Minh Đức	NORMAL	2	Cty_XD_MINH_DUC
31	Cty XD Lưu Nga	NORMAL	2	Cty_XD_LUU_NGA
1	Kho 190	NORMAL	3	KHO_190
2	Kho 661	NORMAL	3	KHO_661
9	d Vinh	NORMAL	29	d_VINH
10	d Nà Sản	NORMAL	29	d_NASAN
19	Kho K14 cap	NORMAL	30	Kho_K14_CAP
25	Công ty XDQD	NORMAL	2	CTY_XDQD
26	Cty XD PVOil HP	NORMAL	2	Cty_XD_PVOil_HP
32	Hạch toán(NM)	NORMAL	32	HACH_TOAN_NM
20	f370	NORMAL	31	f370
21	f372	NORMAL	31	f372
49	Viettel	NORMAL	32	Viettel
3	e921	NORMAL	29	e921
4	e916	NORMAL	29	e916
5	e923	NORMAL	29	e923
6	e927	NORMAL	29	e927
18	Kho K99	NORMAL	3	KHO_K99
8	d Kiến An	NORMAL	29	d_KIENAN
17	Kho 671	NORMAL	3	KHO_671
90	QK2	NORMAL	13	QK_2
\.


--
-- TOC entry 3772 (class 0 OID 18150)
-- Dependencies: 265
-- Data for Name: nguonnx_pt; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.nguonnx_pt (id, nguonnx_id, pt_id) FROM stdin;
1	4	37
2	4	36
3	4	35
4	4	34
5	6	33
6	6	24
7	5	24
8	3	25
9	4	26
\.


--
-- TOC entry 3735 (class 0 OID 17974)
-- Dependencies: 228
-- Data for Name: nhiemvu; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.nhiemvu (id, ten_nv, status, team_id, assignment_type_id, priority, priority_bc2) FROM stdin;
11	Tác chiến, A2..	ACTIVE	3	1	99	1
17	Huấn luyện chiến đấu	ACTIVE	3	1	99	2
22	HL nhà trường	ACTIVE	3	1	99	4
47	KT Hàng không	ACTIVE	1	1	99	5
14	Cứu hộ cứu nạn	ACTIVE	3	2	99	2
15	Khai thác thông tin	ACTIVE	3	2	99	3
16	Cơ yếu	ACTIVE	3	2	99	4
20	Tác chiến điện tử	ACTIVE	3	2	99	6
21	Công nghệ thông tin	ACTIVE	3	2	99	7
23	Công tác Quân báo	ACTIVE	3	2	99	9
24	Quân ra, vào, phép	ACTIVE	3	2	99	10
25	Đào tạo thợ	ACTIVE	3	2	99	11
26	Đ. viên thời chiến	ACTIVE	3	2	99	12
27	N.vụ khác (T tra bay)	ACTIVE	3	2	99	13
40	Ô tô trạm nguồn	ACTIVE	1	2	99	1
41	Vũ khí đạn, VKHK	ACTIVE	1	2	99	2
42	Kỹ thuật thông tin	ACTIVE	1	2	99	3
43	Kỹ thuật công binh	ACTIVE	1	2	99	4
44	Tăng thiết giáp	ACTIVE	1	2	99	5
45	Đo lường	ACTIVE	1	2	99	6
46	KT Ra đa, tên lửa	ACTIVE	1	2	99	7
49	Kỹ thuạt khác	ACTIVE	1	2	99	9
34	Hậu cần đời sống	ACTIVE	4	2	99	1
35	Công tác xăng dầu	ACTIVE	4	2	99	2
36	VC Xăng dầu	ACTIVE	4	2	99	3
37	Công tác vật tư	ACTIVE	4	2	99	4
38	Đảo hạt HC	ACTIVE	4	2	99	5
39	Hậu cần khác	ACTIVE	4	2	99	6
6	Bù hao hụt	ACTIVE	5	3	6	6
8	Tổn thất	ACTIVE	3	1	7	7
9	Bay đề cao	ACTIVE	3	1	3	3
28	Công tác Đảng, CTCT	ACTIVE	2	2	99	1
30	Kiểm tra đảng	ACTIVE	2	2	99	2
31	Công tác chính sách	ACTIVE	2	2	99	3
32	Ăn dưỡng	ACTIVE	2	2	99	4
33	Nghiệp vụ cán bộ	ACTIVE	2	2	99	5
60	Bay BD	ACTIVE	1	1	99	99
\.


--
-- TOC entry 3731 (class 0 OID 17966)
-- Dependencies: 224
-- Data for Name: nhiemvu_reporter; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.nhiemvu_reporter (id, title_1, title_2, title_3, title_4, soluong, nhiemvu_id, phuongtien_id, ten_nv_1, ten_nv_2, ten_nv_3) FROM stdin;
\.


--
-- TOC entry 3774 (class 0 OID 18162)
-- Dependencies: 267
-- Data for Name: nhiemvu_tcn; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.nhiemvu_tcn (id, nvu_id, tcn_id, phuongtien_id) FROM stdin;
\.


--
-- TOC entry 3776 (class 0 OID 18177)
-- Dependencies: 269
-- Data for Name: phuongtien; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.phuongtien (id, name, quantity, status, "timestamp", nguonnx_id, loaiphuongtien_id) FROM stdin;
10	MJI	3	ACTIVE	2024-09-21 09:00:37.178071	54	6
11	GEM 100	8	ACTIVE	2024-09-21 09:00:59.769172	54	6
12	MHYK 80	3	ACTIVE	2024-09-21 09:01:20.143689	54	6
13	AB -4	4	ACTIVE	2024-09-21 09:01:42.342793	54	6
14	AB -8	4	ACTIVE	2024-09-21 09:02:13.589519	54	6
15	AB -10	4	ACTIVE	2024-09-21 09:02:32.452698	54	6
20	AD-10	23	ACTIVE	2024-09-21 09:05:26.884232	54	5
21	AD-20	17	ACTIVE	2024-09-21 09:05:44.384241	54	5
22	AD-30	16	ACTIVE	2024-09-21 09:06:00.726663	54	5
23	AD-50	14	ACTIVE	2024-09-21 09:06:26.214652	54	5
28	Uoat	43	ACTIVE	2024-11-01 10:27:49.973215	54	4
27	C/Thương H.Đai Starex	3	ACTIVE	2024-11-01 09:17:31.357527	54	4
32	Gat 52 (UPM)	4	ACTIVE	2024-11-01 15:06:34.521301	54	4
2	Fotunơ	1	ACTIVE	2024-09-21 08:56:26.292273	54	4
4	INNOVA	5	ACTIVE	2024-09-21 08:57:26.230778	54	4
6	Toyota 15 chỗ	4	ACTIVE	2024-09-21 08:58:12.500483	54	4
17	Ford Rangger	6	ACTIVE	2024-09-21 09:03:59.024248	54	3
18	MAT 500	35	ACTIVE	2024-09-21 09:04:23.155308	54	3
1	Maza 4 chỗ	3	ACTIVE	2024-09-21 08:47:29.034675	54	4
31	Gat 66	5	ACTIVE	2024-11-01 15:04:18.610233	54	4
3	Maza 12 chỗ (e2000)	1	ACTIVE	2024-09-21 08:57:01.569416	54	4
9	C/Thương H.đai Starex	6	ACTIVE	2024-09-21 08:59:41.176218	54	4
19	MAT 6317	12	ACTIVE	2024-09-21 09:04:44.981481	54	3
5	Toyota 16 chỗ	4	ACTIVE	2024-09-21 08:57:49.730122	54	4
16	Mat 5430	13	ACTIVE	2024-09-21 09:03:28.109463	54	3
24	SU 30MK-2	1	ACTIVE	2024-09-21 09:09:31.575152	3	1
25	SU 22M3+4	1	ACTIVE	2024-09-21 09:10:13.652617	3	1
26	Trực thăng	1	ACTIVE	2024-09-21 09:11:06.164423	4	7
33	Su-22	1	ACTIVE	2024-12-06 07:37:21.341003	6	1
34	Mi-172	1	ACTIVE	2024-12-06 07:39:56.357238	4	7
35	Mi-171	1	ACTIVE	2024-12-06 07:39:56.357238	4	7
36	Mi-8	1	ACTIVE	2024-12-06 07:39:56.357238	4	7
37	Mi-7	1	ACTIVE	2024-12-06 07:39:56.357238	4	7
8	Jolie	2	ACTIVE	2024-09-21 08:58:58.814673	54	4
7	NISAN X-TRAIL	5	ACTIVE	2024-09-21 08:58:39.4906	54	4
\.


--
-- TOC entry 3777 (class 0 OID 18183)
-- Dependencies: 270
-- Data for Name: phuongtien_nhiemvu; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.phuongtien_nhiemvu (id, phuongtien_id, nvu_id) FROM stdin;
\.


--
-- TOC entry 3779 (class 0 OID 18193)
-- Dependencies: 272
-- Data for Name: quarter; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.quarter (id, start_date, end_date, year, index) FROM stdin;
17	2024-01-01	2024-03-31	2024	1
18	2024-04-01	2024-06-30	2024	2
19	2024-07-01	2024-09-30	2024	3
20	2024-10-01	2024-12-29	2024	4
24	2024-12-29	2024-12-30	2024	quy1
\.


--
-- TOC entry 3760 (class 0 OID 18103)
-- Dependencies: 253
-- Data for Name: tcn; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tcn (id, name, loaiphieu) FROM stdin;
1	Nhập hạn mức	NHAP
2	Xuất hạn mức	XUAT
3	Nhập phân cấp	NHAP
10	Xuất báo nợ	XUAT
11	Nhập báo nợ	NHAP
13	Nhập Quân Chủng	NHAP
18	Nhập khác	NHAP
19	Xuất khác	XUAT
25	xuat han muc 2	XUAT
29	4322	XUAT
30	Nhập hạ cấp	XUAT
\.


--
-- TOC entry 3783 (class 0 OID 18205)
-- Dependencies: 276
-- Data for Name: team; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.team (id, name, team_code, tt, priority) FROM stdin;
1	KHỐI KỸ THUẬT	KHOI_KY_THUAT	IV	4
2	KHỐI CHÍNH TRỊ	KHOI_CHINH_TRI	II	2
3	KHỐI THAM MƯU	KHOI_THAM_MUU	I	1
4	KHỐI HẬU CẦN	KHOI_HAU_CAN	III	3
5	HAO HỤT	HAO_HUT	V	5
\.


--
-- TOC entry 3785 (class 0 OID 18217)
-- Dependencies: 278
-- Data for Name: tructhuoc; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tructhuoc (id, name, type, "timestamp", nhom_tructhuoc, tennhom_tructhuoc) FROM stdin;
2	Phân cấp	PC	2024-09-28 15:58:07.297347	PC	Phân cấp
3	Cục xăng dầu	CXD	2024-09-28 15:58:37.603457	CXD	Cục xăng dầu
6	Tiêu thụ cho xe, máy	TT_XM	2024-09-28 16:01:55.182771	TT_XM	Tiêu thụ cho xe, máy
7	Hao hụt	HH	2024-09-28 16:02:53.568184	HH	Hao hụt
13	QK2	QK2	2024-09-28 16:27:23.886258	QK2	QK2
18	Bảo quản	BQ	2024-10-03 16:35:34.866107	BQ	Bảo quản
21	Ngoài QC	N_QC	2024-10-04 07:20:35.897443	N_QC	Ngoài QC
24	Tổn thất	TT	2024-10-04 07:21:52.984058	TT	Tổn thất
29	F bộ	FNB	2024-11-25 10:15:58.898535	T_QC	Trong QC
30	Quân chủng	QC	2024-11-25 10:19:48.993567	QC	Quân chủng
31	ĐV Khác	DVK	2024-11-25 14:07:47.843315	T_QC	Trong QC
32	Khác	K	2024-11-25 14:11:28.716855	KHAC	Khác
33	Trong QC	TQC	2024-11-25 14:14:55.364573	T_QC	Trong QC
\.


--
-- TOC entry 3770 (class 0 OID 18146)
-- Dependencies: 263
-- Data for Name: tructhuoc_loaiphieu; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tructhuoc_loaiphieu (id, tructhuoc_id, loaiphieu_id) FROM stdin;
1	21	2
2	21	1
3	20	2
4	20	1
5	3	1
6	2	1
7	1	1
8	6	2
9	7	2
10	13	1
11	18	2
12	19	1
13	19	2
14	24	2
15	26	1
16	26	2
17	27	1
18	27	2
19	28	1
20	28	2
\.


--
-- TOC entry 3813 (class 0 OID 0)
-- Dependencies: 215
-- Name: Inventory_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Inventory_id_seq"', 1358, true);


--
-- TOC entry 3814 (class 0 OID 0)
-- Dependencies: 217
-- Name: Ledgers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Ledgers_id_seq"', 633, true);


--
-- TOC entry 3815 (class 0 OID 0)
-- Dependencies: 219
-- Name: accounts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.accounts_id_seq', 2, true);


--
-- TOC entry 3816 (class 0 OID 0)
-- Dependencies: 221
-- Name: activated_active_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.activated_active_id_seq', 3, true);


--
-- TOC entry 3817 (class 0 OID 0)
-- Dependencies: 225
-- Name: category_assignment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.category_assignment_id_seq', 3209, true);


--
-- TOC entry 3818 (class 0 OID 0)
-- Dependencies: 226
-- Name: category_assignment_id_seq1; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.category_assignment_id_seq1', 11, true);


--
-- TOC entry 3819 (class 0 OID 0)
-- Dependencies: 227
-- Name: category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.category_id_seq', 61, true);


--
-- TOC entry 3820 (class 0 OID 0)
-- Dependencies: 229
-- Name: chi_tiet_nhiemvu_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.chi_tiet_nhiemvu_id_seq', 60, true);


--
-- TOC entry 3821 (class 0 OID 0)
-- Dependencies: 231
-- Name: chitiet_nhiemvu_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.chitiet_nhiemvu_id_seq', 52, true);


--
-- TOC entry 3822 (class 0 OID 0)
-- Dependencies: 233
-- Name: chitieu_pt_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.chitieu_pt_id_seq', 34, true);


--
-- TOC entry 3823 (class 0 OID 0)
-- Dependencies: 235
-- Name: chungloaixd_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.chungloaixd_id_seq', 13, true);


--
-- TOC entry 3824 (class 0 OID 0)
-- Dependencies: 282
-- Name: configuration_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.configuration_id_seq', 1, true);


--
-- TOC entry 3825 (class 0 OID 0)
-- Dependencies: 237
-- Name: dinhmuc_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.dinhmuc_id_seq', 101, true);


--
-- TOC entry 3826 (class 0 OID 0)
-- Dependencies: 240
-- Name: dvi_nv_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.dvi_nv_id_seq', 127, true);


--
-- TOC entry 3827 (class 0 OID 0)
-- Dependencies: 242
-- Name: hanmuc_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hanmuc_id_seq', 70, true);


--
-- TOC entry 3828 (class 0 OID 0)
-- Dependencies: 244
-- Name: hanmuc_nhiemvu2_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hanmuc_nhiemvu2_id_seq', 53, true);


--
-- TOC entry 3829 (class 0 OID 0)
-- Dependencies: 246
-- Name: hanmuc_nhiemvu_taubay_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hanmuc_nhiemvu_taubay_id_seq', 64, true);


--
-- TOC entry 3830 (class 0 OID 0)
-- Dependencies: 250
-- Name: lichsuxnk_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.lichsuxnk_id_seq', 1155, true);


--
-- TOC entry 3831 (class 0 OID 0)
-- Dependencies: 252
-- Name: loai_nhiemvu_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.loai_nhiemvu_id_seq', 3, true);


--
-- TOC entry 3832 (class 0 OID 0)
-- Dependencies: 254
-- Name: loai_nx_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.loai_nx_id_seq', 30, true);


--
-- TOC entry 3833 (class 0 OID 0)
-- Dependencies: 256
-- Name: loai_phuongtien_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.loai_phuongtien_id_seq', 7, true);


--
-- TOC entry 3834 (class 0 OID 0)
-- Dependencies: 258
-- Name: loaixd2_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.loaixd2_id_seq', 71, true);


--
-- TOC entry 3835 (class 0 OID 0)
-- Dependencies: 260
-- Name: mucgia_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.mucgia_id_seq', 3605, true);


--
-- TOC entry 3836 (class 0 OID 0)
-- Dependencies: 261
-- Name: myseq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.myseq', 90, true);


--
-- TOC entry 3837 (class 0 OID 0)
-- Dependencies: 264
-- Name: nguonnx_loaiphieu_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.nguonnx_loaiphieu_id_seq', 20, true);


--
-- TOC entry 3838 (class 0 OID 0)
-- Dependencies: 266
-- Name: nguonnx_pt_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.nguonnx_pt_id_seq', 9, true);


--
-- TOC entry 3839 (class 0 OID 0)
-- Dependencies: 268
-- Name: nhiemvu_tcn_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.nhiemvu_tcn_id_seq', 1, false);


--
-- TOC entry 3840 (class 0 OID 0)
-- Dependencies: 271
-- Name: phuongtien_nhiemvu_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.phuongtien_nhiemvu_id_seq', 3, true);


--
-- TOC entry 3841 (class 0 OID 0)
-- Dependencies: 273
-- Name: quarter_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.quarter_id_seq', 24, true);


--
-- TOC entry 3842 (class 0 OID 0)
-- Dependencies: 274
-- Name: so_cai_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.so_cai_id_seq', 1400, true);


--
-- TOC entry 3843 (class 0 OID 0)
-- Dependencies: 275
-- Name: splog_adfarm_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.splog_adfarm_seq', 1, false);


--
-- TOC entry 3844 (class 0 OID 0)
-- Dependencies: 277
-- Name: team_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.team_id_seq', 5, true);


--
-- TOC entry 3845 (class 0 OID 0)
-- Dependencies: 279
-- Name: tructhuoc_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tructhuoc_id_seq', 34, true);


--
-- TOC entry 3846 (class 0 OID 0)
-- Dependencies: 280
-- Name: tructhuocf_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tructhuocf_id_seq', 9, true);


--
-- TOC entry 3847 (class 0 OID 0)
-- Dependencies: 281
-- Name: vehicels_for_plan_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.vehicels_for_plan_id_seq', 38, true);


--
-- TOC entry 3459 (class 2606 OID 18245)
-- Name: inventory Inventory_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.inventory
    ADD CONSTRAINT "Inventory_pkey" PRIMARY KEY (id);


--
-- TOC entry 3463 (class 2606 OID 18247)
-- Name: ledgers Ledgers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ledgers
    ADD CONSTRAINT "Ledgers_pkey" PRIMARY KEY (id);


--
-- TOC entry 3467 (class 2606 OID 18249)
-- Name: accounts accounts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT accounts_pkey PRIMARY KEY (id);


--
-- TOC entry 3469 (class 2606 OID 18251)
-- Name: accounts accounts_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT accounts_username_key UNIQUE (username);


--
-- TOC entry 3471 (class 2606 OID 18253)
-- Name: activated_active activated_active_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.activated_active
    ADD CONSTRAINT activated_active_pkey PRIMARY KEY (id);


--
-- TOC entry 3477 (class 2606 OID 18255)
-- Name: category_assignment category_assignment_code_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category_assignment
    ADD CONSTRAINT category_assignment_code_key UNIQUE (code);


--
-- TOC entry 3481 (class 2606 OID 18257)
-- Name: nhiemvu_reporter category_assignment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nhiemvu_reporter
    ADD CONSTRAINT category_assignment_pkey PRIMARY KEY (id);


--
-- TOC entry 3479 (class 2606 OID 18259)
-- Name: category_assignment category_assignment_pkey1; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category_assignment
    ADD CONSTRAINT category_assignment_pkey1 PRIMARY KEY (id);


--
-- TOC entry 3473 (class 2606 OID 18261)
-- Name: category category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);


--
-- TOC entry 3475 (class 2606 OID 18263)
-- Name: category category_type_title_tructhuoc_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_type_title_tructhuoc_id_key UNIQUE (type_title, tructhuoc_id);


--
-- TOC entry 3483 (class 2606 OID 18265)
-- Name: nhiemvu chi_tiet_nhiemvu_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nhiemvu
    ADD CONSTRAINT chi_tiet_nhiemvu_pkey PRIMARY KEY (id);


--
-- TOC entry 3485 (class 2606 OID 18267)
-- Name: chitiet_nhiemvu chitiet_nhiemvu_nhiemvu_id_nhiemvu_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chitiet_nhiemvu
    ADD CONSTRAINT chitiet_nhiemvu_nhiemvu_id_nhiemvu_key UNIQUE (nhiemvu_id, nhiemvu);


--
-- TOC entry 3487 (class 2606 OID 18269)
-- Name: chitiet_nhiemvu chitiet_nhiemvu_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chitiet_nhiemvu
    ADD CONSTRAINT chitiet_nhiemvu_pkey PRIMARY KEY (id);


--
-- TOC entry 3489 (class 2606 OID 18271)
-- Name: chitieu_pt chitieu_pt_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chitieu_pt
    ADD CONSTRAINT chitieu_pt_pkey PRIMARY KEY (id);


--
-- TOC entry 3491 (class 2606 OID 18273)
-- Name: chungloaixd chungloaixd_code_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chungloaixd
    ADD CONSTRAINT chungloaixd_code_key UNIQUE (code);


--
-- TOC entry 3493 (class 2606 OID 18275)
-- Name: chungloaixd chungloaixd_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chungloaixd
    ADD CONSTRAINT chungloaixd_pkey PRIMARY KEY (id);


--
-- TOC entry 3565 (class 2606 OID 18467)
-- Name: configuration configuration_id_parameter_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.configuration
    ADD CONSTRAINT configuration_id_parameter_key UNIQUE (id, parameter);


--
-- TOC entry 3567 (class 2606 OID 18465)
-- Name: configuration configuration_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.configuration
    ADD CONSTRAINT configuration_pkey PRIMARY KEY (id);


--
-- TOC entry 3495 (class 2606 OID 18277)
-- Name: dinhmuc dinhmuc_phuongtien_id_quarter_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dinhmuc
    ADD CONSTRAINT dinhmuc_phuongtien_id_quarter_id_key UNIQUE (phuongtien_id, quarter_id);


--
-- TOC entry 3497 (class 2606 OID 18279)
-- Name: dinhmuc dinhmuc_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dinhmuc
    ADD CONSTRAINT dinhmuc_pkey PRIMARY KEY (id);


--
-- TOC entry 3501 (class 2606 OID 18281)
-- Name: dvi_nv dvi_nv_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dvi_nv
    ADD CONSTRAINT dvi_nv_pkey PRIMARY KEY (id);


--
-- TOC entry 3503 (class 2606 OID 18285)
-- Name: hanmuc_nhiemvu2 hanmuc_nhiemvu2_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.hanmuc_nhiemvu2
    ADD CONSTRAINT hanmuc_nhiemvu2_pkey PRIMARY KEY (id);


--
-- TOC entry 3505 (class 2606 OID 18287)
-- Name: hanmuc_nhiemvu2 hanmuc_nhiemvu2_quarter_id_nhiemvu_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.hanmuc_nhiemvu2
    ADD CONSTRAINT hanmuc_nhiemvu2_quarter_id_nhiemvu_id_key UNIQUE (quarter_id, nhiemvu_id);


--
-- TOC entry 3507 (class 2606 OID 18289)
-- Name: hanmuc_nhiemvu_taubay hanmuc_nhiemvu_taubay_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.hanmuc_nhiemvu_taubay
    ADD CONSTRAINT hanmuc_nhiemvu_taubay_pkey PRIMARY KEY (id);


--
-- TOC entry 3527 (class 2606 OID 18291)
-- Name: mucgia id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mucgia
    ADD CONSTRAINT id PRIMARY KEY (id);


--
-- TOC entry 3461 (class 2606 OID 18301)
-- Name: inventory inventory_petro_id_quarter_id_price_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.inventory
    ADD CONSTRAINT inventory_petro_id_quarter_id_price_key UNIQUE (petro_id, quarter_id, price);


--
-- TOC entry 3511 (class 2606 OID 18303)
-- Name: ledger_map ledger_map_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ledger_map
    ADD CONSTRAINT ledger_map_pkey PRIMARY KEY (id, loaixd_id, header_id, quarter_id);


--
-- TOC entry 3465 (class 2606 OID 18488)
-- Name: ledgers ledgers_bill_id_loai_phieu_quarter_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ledgers
    ADD CONSTRAINT ledgers_bill_id_loai_phieu_quarter_id_key UNIQUE (bill_id, loai_phieu, quarter_id);


--
-- TOC entry 3513 (class 2606 OID 18307)
-- Name: lichsuxnk lichsuxnk_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lichsuxnk
    ADD CONSTRAINT lichsuxnk_pkey PRIMARY KEY (id);


--
-- TOC entry 3515 (class 2606 OID 18309)
-- Name: loai_nhiemvu loai_nhiemvu_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.loai_nhiemvu
    ADD CONSTRAINT loai_nhiemvu_pkey PRIMARY KEY (id);


--
-- TOC entry 3517 (class 2606 OID 18311)
-- Name: loai_nhiemvu loai_nhiemvu_task_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.loai_nhiemvu
    ADD CONSTRAINT loai_nhiemvu_task_name_key UNIQUE (task_name);


--
-- TOC entry 3523 (class 2606 OID 18315)
-- Name: loai_phuongtien loai_phuongtien_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.loai_phuongtien
    ADD CONSTRAINT loai_phuongtien_pkey PRIMARY KEY (id);


--
-- TOC entry 3525 (class 2606 OID 18317)
-- Name: loaixd2 loaixd2_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.loaixd2
    ADD CONSTRAINT loaixd2_pkey PRIMARY KEY (id);


--
-- TOC entry 3529 (class 2606 OID 18319)
-- Name: mucgia mucgia_price_quarter_id_item_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mucgia
    ADD CONSTRAINT mucgia_price_quarter_id_item_id_key UNIQUE (price, quarter_id, item_id);


--
-- TOC entry 3531 (class 2606 OID 18321)
-- Name: nguon_nx nguon_nx_code_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nguon_nx
    ADD CONSTRAINT nguon_nx_code_key UNIQUE (code);


--
-- TOC entry 3533 (class 2606 OID 18323)
-- Name: nguon_nx nguon_nx_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nguon_nx
    ADD CONSTRAINT nguon_nx_pkey PRIMARY KEY (id);


--
-- TOC entry 3537 (class 2606 OID 18325)
-- Name: tructhuoc_loaiphieu nguonnx_loaiphieu_nguonnx_id_loaiphieu_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tructhuoc_loaiphieu
    ADD CONSTRAINT nguonnx_loaiphieu_nguonnx_id_loaiphieu_id_key UNIQUE (tructhuoc_id, loaiphieu_id);


--
-- TOC entry 3539 (class 2606 OID 18327)
-- Name: tructhuoc_loaiphieu nguonnx_loaiphieu_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tructhuoc_loaiphieu
    ADD CONSTRAINT nguonnx_loaiphieu_pkey PRIMARY KEY (id);


--
-- TOC entry 3541 (class 2606 OID 18329)
-- Name: nguonnx_pt nguonnx_pt_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nguonnx_pt
    ADD CONSTRAINT nguonnx_pt_pkey PRIMARY KEY (id);


--
-- TOC entry 3543 (class 2606 OID 18337)
-- Name: nhiemvu_tcn nhiemvu_tcn_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nhiemvu_tcn
    ADD CONSTRAINT nhiemvu_tcn_pkey PRIMARY KEY (id);


--
-- TOC entry 3545 (class 2606 OID 18343)
-- Name: phuongtien phuongtien_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.phuongtien
    ADD CONSTRAINT phuongtien_name_key UNIQUE (name);


--
-- TOC entry 3549 (class 2606 OID 18345)
-- Name: phuongtien_nhiemvu phuongtien_nhiemvu_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.phuongtien_nhiemvu
    ADD CONSTRAINT phuongtien_nhiemvu_pkey PRIMARY KEY (id);


--
-- TOC entry 3547 (class 2606 OID 18347)
-- Name: phuongtien phuongtien_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.phuongtien
    ADD CONSTRAINT phuongtien_pkey PRIMARY KEY (id);


--
-- TOC entry 3551 (class 2606 OID 18486)
-- Name: quarter quarter_index_year_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.quarter
    ADD CONSTRAINT quarter_index_year_key UNIQUE (index, year);


--
-- TOC entry 3553 (class 2606 OID 18353)
-- Name: quarter quarter_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.quarter
    ADD CONSTRAINT quarter_pkey PRIMARY KEY (id);


--
-- TOC entry 3509 (class 2606 OID 18355)
-- Name: ledger_details so_cai_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ledger_details
    ADD CONSTRAINT so_cai_pkey PRIMARY KEY (id);


--
-- TOC entry 3519 (class 2606 OID 18357)
-- Name: tcn tcn_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tcn
    ADD CONSTRAINT tcn_name_key UNIQUE (name);


--
-- TOC entry 3521 (class 2606 OID 18359)
-- Name: tcn tcn_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tcn
    ADD CONSTRAINT tcn_pkey PRIMARY KEY (id);


--
-- TOC entry 3555 (class 2606 OID 18361)
-- Name: team team_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team
    ADD CONSTRAINT team_pkey PRIMARY KEY (id);


--
-- TOC entry 3557 (class 2606 OID 18363)
-- Name: team team_team_code_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team
    ADD CONSTRAINT team_team_code_key UNIQUE (team_code);


--
-- TOC entry 3535 (class 2606 OID 18365)
-- Name: nguon_nx ten_uni; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nguon_nx
    ADD CONSTRAINT ten_uni UNIQUE (ten);


--
-- TOC entry 3559 (class 2606 OID 18369)
-- Name: tructhuoc tructhuoc_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tructhuoc
    ADD CONSTRAINT tructhuoc_name_key UNIQUE (name);


--
-- TOC entry 3561 (class 2606 OID 18371)
-- Name: tructhuoc tructhuoc_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tructhuoc
    ADD CONSTRAINT tructhuoc_pkey PRIMARY KEY (id);


--
-- TOC entry 3563 (class 2606 OID 18373)
-- Name: tructhuoc tructhuoc_type_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tructhuoc
    ADD CONSTRAINT tructhuoc_type_key UNIQUE (type);


--
-- TOC entry 3499 (class 2606 OID 18375)
-- Name: donvi_tructhuoc tructhuocf_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.donvi_tructhuoc
    ADD CONSTRAINT tructhuocf_pkey PRIMARY KEY (id);


--
-- TOC entry 3572 (class 2606 OID 18376)
-- Name: dinhmuc dinhmuc_phuongtien_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dinhmuc
    ADD CONSTRAINT dinhmuc_phuongtien_id_fkey FOREIGN KEY (phuongtien_id) REFERENCES public.phuongtien(id) NOT VALID;


--
-- TOC entry 3573 (class 2606 OID 18381)
-- Name: dvi_nv dvi_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dvi_nv
    ADD CONSTRAINT dvi_fkey FOREIGN KEY (dv_id) REFERENCES public.nguon_nx(id) NOT VALID;


--
-- TOC entry 3568 (class 2606 OID 18386)
-- Name: inventory inventory_petro_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.inventory
    ADD CONSTRAINT inventory_petro_id_fkey FOREIGN KEY (petro_id) REFERENCES public.loaixd2(id) NOT VALID;


--
-- TOC entry 3569 (class 2606 OID 18391)
-- Name: inventory inventory_quarter_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.inventory
    ADD CONSTRAINT inventory_quarter_id_fkey FOREIGN KEY (quarter_id) REFERENCES public.quarter(id) NOT VALID;


--
-- TOC entry 3575 (class 2606 OID 18396)
-- Name: ledger_details ledger_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ledger_details
    ADD CONSTRAINT ledger_fkey FOREIGN KEY (ledger_id) REFERENCES public.ledgers(id) NOT VALID;


--
-- TOC entry 3576 (class 2606 OID 18406)
-- Name: ledger_details loaixd_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ledger_details
    ADD CONSTRAINT loaixd_fkey FOREIGN KEY (loaixd_id) REFERENCES public.loaixd2(id) NOT VALID;


--
-- TOC entry 3579 (class 2606 OID 18411)
-- Name: phuongtien nguonnx_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.phuongtien
    ADD CONSTRAINT nguonnx_fkey FOREIGN KEY (nguonnx_id) REFERENCES public.nguon_nx(id) NOT VALID;


--
-- TOC entry 3577 (class 2606 OID 18421)
-- Name: nhiemvu_tcn nhiemvu_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nhiemvu_tcn
    ADD CONSTRAINT nhiemvu_fkey FOREIGN KEY (nvu_id) REFERENCES public.nhiemvu(id) NOT VALID;


--
-- TOC entry 3571 (class 2606 OID 18426)
-- Name: chitiet_nhiemvu nhiemvu_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chitiet_nhiemvu
    ADD CONSTRAINT nhiemvu_id FOREIGN KEY (nhiemvu_id) REFERENCES public.nhiemvu(id) NOT VALID;


--
-- TOC entry 3580 (class 2606 OID 18431)
-- Name: phuongtien_nhiemvu nhiemvu_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.phuongtien_nhiemvu
    ADD CONSTRAINT nhiemvu_id FOREIGN KEY (nvu_id) REFERENCES public.nhiemvu(id) NOT VALID;


--
-- TOC entry 3570 (class 2606 OID 18436)
-- Name: nhiemvu nhiemvu_team_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nhiemvu
    ADD CONSTRAINT nhiemvu_team_id_fkey FOREIGN KEY (team_id) REFERENCES public.team(id) NOT VALID;


--
-- TOC entry 3574 (class 2606 OID 18441)
-- Name: dvi_nv nvu_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dvi_nv
    ADD CONSTRAINT nvu_fkey FOREIGN KEY (nv_id) REFERENCES public.nhiemvu(id) NOT VALID;


--
-- TOC entry 3581 (class 2606 OID 18446)
-- Name: phuongtien_nhiemvu phuongtien_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.phuongtien_nhiemvu
    ADD CONSTRAINT phuongtien_id FOREIGN KEY (phuongtien_id) REFERENCES public.phuongtien(id) NOT VALID;


--
-- TOC entry 3578 (class 2606 OID 18451)
-- Name: nhiemvu_tcn tcn_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nhiemvu_tcn
    ADD CONSTRAINT tcn_fkey FOREIGN KEY (tcn_id) REFERENCES public.tcn(id) NOT VALID;


--
-- TOC entry 3797 (class 0 OID 0)
-- Dependencies: 7
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2024-12-30 17:40:31

--
-- PostgreSQL database dump complete
--

