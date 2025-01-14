--
-- PostgreSQL database dump
--

-- Dumped from database version 14.13
-- Dumped by pg_dump version 16.4

-- Started on 2025-01-10 08:20:57

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
-- TOC entry 3761 (class 0 OID 0)
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
-- TOC entry 3763 (class 0 OID 0)
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
-- TOC entry 3764 (class 0 OID 0)
-- Dependencies: 3
-- Name: EXTENSION tablefunc; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION tablefunc IS 'functions that manipulate whole tables, including crosstab';


--
-- TOC entry 308 (class 1255 OID 18503)
-- Name: invnhap_xd(date, date, text, integer, text, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.invnhap_xd(sd date, ed date, tt text, lxd_id integer, lp text, dvin_id integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$
declare
	total integer;
BEGIN
   select soluong into total from (select loaixd_id, sum(so_luong) as soluong 
   from ledgers l join ledger_details ld on l.id=ld.ledger_id 
   where loai_phieu like lp and tructhuoc like tt and loaixd_id=lxd_id and dvi_nhap_id=dvin_id and sd <= l.from_date and l.from_date <= ed 
   group by loaixd_id limit 1) a;
   RETURN total;
END;
$$;


ALTER FUNCTION public.invnhap_xd(sd date, ed date, tt text, lxd_id integer, lp text, dvin_id integer) OWNER TO postgres;

--
-- TOC entry 307 (class 1255 OID 18502)
-- Name: invxuat_xd(date, date, text, integer, text, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.invxuat_xd(sd date, ed date, tt text, lxd_id integer, lp text, dvix_id integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$
declare
	total integer;
BEGIN
   select soluong into total from (select loaixd_id, sum(so_luong) as soluong 
   from ledgers l join ledger_details ld on l.id=ld.ledger_id 
   where loai_phieu like lp and tructhuoc like tt and loaixd_id=lxd_id and dvi_xuat_id=dvix_id and sd <= l.from_date and l.from_date <= ed 
   group by loaixd_id limit 1) a;
   RETURN total;
END;
$$;


ALTER FUNCTION public.invxuat_xd(sd date, ed date, tt text, lxd_id integer, lp text, dvix_id integer) OWNER TO postgres;

--
-- TOC entry 300 (class 1255 OID 17903)
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
-- TOC entry 306 (class 1255 OID 17904)
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
-- TOC entry 301 (class 1255 OID 17905)
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
-- TOC entry 302 (class 1255 OID 17906)
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
-- TOC entry 303 (class 1255 OID 17907)
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
-- TOC entry 304 (class 1255 OID 17908)
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
-- TOC entry 305 (class 1255 OID 17909)
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
    nhap_nvdx bigint DEFAULT 0,
    nhap_sscd bigint DEFAULT 0,
    xuat_nvdx bigint DEFAULT 0,
    xuat_sscd bigint DEFAULT 0,
    price bigint DEFAULT 0,
    create_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    sd date,
    ed date
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
    sd date,
    ed date
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
-- TOC entry 224 (class 1259 OID 17972)
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
-- TOC entry 225 (class 1259 OID 17973)
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
-- TOC entry 226 (class 1259 OID 17974)
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
-- TOC entry 227 (class 1259 OID 17979)
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
-- TOC entry 3765 (class 0 OID 0)
-- Dependencies: 227
-- Name: chi_tiet_nhiemvu_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.chi_tiet_nhiemvu_id_seq OWNED BY public.nhiemvu.id;


--
-- TOC entry 228 (class 1259 OID 17980)
-- Name: chitiet_nhiemvu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.chitiet_nhiemvu (
    id integer NOT NULL,
    nhiemvu_id integer,
    nhiemvu text
);


ALTER TABLE public.chitiet_nhiemvu OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 17985)
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
-- TOC entry 3766 (class 0 OID 0)
-- Dependencies: 229
-- Name: chitiet_nhiemvu_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.chitiet_nhiemvu_id_seq OWNED BY public.chitiet_nhiemvu.id;


--
-- TOC entry 230 (class 1259 OID 17986)
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
-- TOC entry 231 (class 1259 OID 17994)
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
-- TOC entry 232 (class 1259 OID 17995)
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
-- TOC entry 233 (class 1259 OID 18004)
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
-- TOC entry 277 (class 1259 OID 18459)
-- Name: configuration; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.configuration (
    id integer NOT NULL,
    parameter character varying(50) NOT NULL,
    value text
);


ALTER TABLE public.configuration OWNER TO postgres;

--
-- TOC entry 276 (class 1259 OID 18458)
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
-- TOC entry 234 (class 1259 OID 18005)
-- Name: dinhmuc; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dinhmuc (
    id integer NOT NULL,
    dm_tk_gio integer,
    dm_md_gio integer,
    dm_xm_km integer,
    dm_xm_gio integer,
    phuongtien_id integer,
    years integer DEFAULT 2024
);


ALTER TABLE public.dinhmuc OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 18008)
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
-- TOC entry 236 (class 1259 OID 18009)
-- Name: donvi_tructhuoc; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.donvi_tructhuoc (
    id integer NOT NULL,
    root_id integer,
    dvi_tructhuoc_id integer
);


ALTER TABLE public.donvi_tructhuoc OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 18012)
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
-- TOC entry 238 (class 1259 OID 18017)
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
-- TOC entry 3767 (class 0 OID 0)
-- Dependencies: 238
-- Name: dvi_nv_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.dvi_nv_id_seq OWNED BY public.dvi_nv.id;


--
-- TOC entry 239 (class 1259 OID 18030)
-- Name: hanmuc_nhiemvu2; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.hanmuc_nhiemvu2 (
    id bigint NOT NULL,
    nhiemvu_id bigint DEFAULT 0,
    dvi_id bigint DEFAULT 0,
    diezel bigint DEFAULT 0,
    daubay bigint DEFAULT 0,
    xang bigint DEFAULT 0,
    years integer DEFAULT 2024
);


ALTER TABLE public.hanmuc_nhiemvu2 OWNER TO postgres;

--
-- TOC entry 240 (class 1259 OID 18038)
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
-- TOC entry 241 (class 1259 OID 18039)
-- Name: hanmuc_nhiemvu_taubay; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.hanmuc_nhiemvu_taubay (
    id bigint NOT NULL,
    dvi_xuat_id bigint,
    pt_id bigint,
    ctnv_id bigint,
    tk text DEFAULT '00:00'::text,
    md text DEFAULT '00:00'::text,
    nhienlieu bigint DEFAULT 0,
    years integer DEFAULT 0
);


ALTER TABLE public.hanmuc_nhiemvu_taubay OWNER TO postgres;

--
-- TOC entry 242 (class 1259 OID 18047)
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
-- TOC entry 243 (class 1259 OID 18058)
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
-- TOC entry 244 (class 1259 OID 18078)
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
-- TOC entry 245 (class 1259 OID 18083)
-- Name: lichsuxnk; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.lichsuxnk (
    id integer NOT NULL,
    ten_xd text,
    loai_phieu text,
    tontruoc bigint DEFAULT 0,
    soluong integer DEFAULT 0,
    tonsau bigint DEFAULT 0,
    "timestamp" timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    type character varying(5) DEFAULT 'NVDX'::character varying,
    so bigint DEFAULT 0,
    dvn text,
    dvx text,
    chungloaixd text,
    gia bigint DEFAULT 0,
    sd date
);


ALTER TABLE public.lichsuxnk OWNER TO postgres;

--
-- TOC entry 246 (class 1259 OID 18096)
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
-- TOC entry 3768 (class 0 OID 0)
-- Dependencies: 246
-- Name: lichsuxnk_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.lichsuxnk_id_seq OWNED BY public.lichsuxnk.id;


--
-- TOC entry 247 (class 1259 OID 18097)
-- Name: loai_nhiemvu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.loai_nhiemvu (
    id integer NOT NULL,
    task_name text
);


ALTER TABLE public.loai_nhiemvu OWNER TO postgres;

--
-- TOC entry 248 (class 1259 OID 18102)
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
-- TOC entry 249 (class 1259 OID 18103)
-- Name: tcn; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tcn (
    id integer NOT NULL,
    name text,
    loaiphieu character varying(10)
);


ALTER TABLE public.tcn OWNER TO postgres;

--
-- TOC entry 250 (class 1259 OID 18108)
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
-- TOC entry 3769 (class 0 OID 0)
-- Dependencies: 250
-- Name: loai_nx_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.loai_nx_id_seq OWNED BY public.tcn.id;


--
-- TOC entry 251 (class 1259 OID 18115)
-- Name: loai_phuongtien; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.loai_phuongtien (
    id integer NOT NULL,
    type_name text,
    type text
);


ALTER TABLE public.loai_phuongtien OWNER TO postgres;

--
-- TOC entry 252 (class 1259 OID 18120)
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
-- TOC entry 253 (class 1259 OID 18121)
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
-- TOC entry 254 (class 1259 OID 18127)
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
-- TOC entry 3770 (class 0 OID 0)
-- Dependencies: 254
-- Name: loaixd2_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.loaixd2_id_seq OWNED BY public.loaixd2.id;


--
-- TOC entry 255 (class 1259 OID 18139)
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
-- TOC entry 256 (class 1259 OID 18140)
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
-- TOC entry 257 (class 1259 OID 18146)
-- Name: tructhuoc_loaiphieu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tructhuoc_loaiphieu (
    id integer NOT NULL,
    tructhuoc_id integer,
    loaiphieu_id integer
);


ALTER TABLE public.tructhuoc_loaiphieu OWNER TO postgres;

--
-- TOC entry 258 (class 1259 OID 18149)
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
-- TOC entry 259 (class 1259 OID 18150)
-- Name: nguonnx_pt; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.nguonnx_pt (
    id bigint NOT NULL,
    nguonnx_id bigint,
    pt_id bigint
);


ALTER TABLE public.nguonnx_pt OWNER TO postgres;

--
-- TOC entry 260 (class 1259 OID 18153)
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
-- TOC entry 261 (class 1259 OID 18162)
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
-- TOC entry 262 (class 1259 OID 18165)
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
-- TOC entry 3771 (class 0 OID 0)
-- Dependencies: 262
-- Name: nhiemvu_tcn_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.nhiemvu_tcn_id_seq OWNED BY public.nhiemvu_tcn.id;


--
-- TOC entry 263 (class 1259 OID 18177)
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
-- TOC entry 264 (class 1259 OID 18183)
-- Name: phuongtien_nhiemvu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.phuongtien_nhiemvu (
    id integer NOT NULL,
    phuongtien_id integer,
    nvu_id integer
);


ALTER TABLE public.phuongtien_nhiemvu OWNER TO postgres;

--
-- TOC entry 265 (class 1259 OID 18186)
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
-- TOC entry 3772 (class 0 OID 0)
-- Dependencies: 265
-- Name: phuongtien_nhiemvu_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.phuongtien_nhiemvu_id_seq OWNED BY public.phuongtien_nhiemvu.id;


--
-- TOC entry 266 (class 1259 OID 18193)
-- Name: quarter; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.quarter (
    id integer NOT NULL,
    start_date date,
    end_date date,
    year text,
    index text,
    status character varying(20)
);


ALTER TABLE public.quarter OWNER TO postgres;

--
-- TOC entry 267 (class 1259 OID 18199)
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
-- TOC entry 3773 (class 0 OID 0)
-- Dependencies: 267
-- Name: quarter_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.quarter_id_seq OWNED BY public.quarter.id;


--
-- TOC entry 268 (class 1259 OID 18200)
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
-- TOC entry 3774 (class 0 OID 0)
-- Dependencies: 268
-- Name: so_cai_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.so_cai_id_seq OWNED BY public.ledger_details.id;


--
-- TOC entry 269 (class 1259 OID 18201)
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
-- TOC entry 270 (class 1259 OID 18205)
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
-- TOC entry 271 (class 1259 OID 18210)
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
-- TOC entry 272 (class 1259 OID 18217)
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
-- TOC entry 273 (class 1259 OID 18223)
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
-- TOC entry 3775 (class 0 OID 0)
-- Dependencies: 273
-- Name: tructhuoc_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tructhuoc_id_seq OWNED BY public.tructhuoc.id;


--
-- TOC entry 274 (class 1259 OID 18224)
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
-- TOC entry 275 (class 1259 OID 18229)
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
-- TOC entry 3776 (class 0 OID 0)
-- Dependencies: 275
-- Name: vehicels_for_plan_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.vehicels_for_plan_id_seq OWNED BY public.phuongtien.id;


--
-- TOC entry 3378 (class 2604 OID 18230)
-- Name: chitiet_nhiemvu id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chitiet_nhiemvu ALTER COLUMN id SET DEFAULT nextval('public.chitiet_nhiemvu_id_seq'::regclass);


--
-- TOC entry 3389 (class 2604 OID 18231)
-- Name: dvi_nv id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dvi_nv ALTER COLUMN id SET DEFAULT nextval('public.dvi_nv_id_seq'::regclass);


--
-- TOC entry 3403 (class 2604 OID 18232)
-- Name: ledger_details id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ledger_details ALTER COLUMN id SET DEFAULT nextval('public.so_cai_id_seq'::regclass);


--
-- TOC entry 3421 (class 2604 OID 18233)
-- Name: lichsuxnk id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lichsuxnk ALTER COLUMN id SET DEFAULT nextval('public.lichsuxnk_id_seq'::regclass);


--
-- TOC entry 3430 (class 2604 OID 18234)
-- Name: loaixd2 id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.loaixd2 ALTER COLUMN id SET DEFAULT nextval('public.loaixd2_id_seq'::regclass);


--
-- TOC entry 3377 (class 2604 OID 18237)
-- Name: nhiemvu id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nhiemvu ALTER COLUMN id SET DEFAULT nextval('public.chi_tiet_nhiemvu_id_seq'::regclass);


--
-- TOC entry 3433 (class 2604 OID 18238)
-- Name: nhiemvu_tcn id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nhiemvu_tcn ALTER COLUMN id SET DEFAULT nextval('public.nhiemvu_tcn_id_seq'::regclass);


--
-- TOC entry 3434 (class 2604 OID 18239)
-- Name: phuongtien id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.phuongtien ALTER COLUMN id SET DEFAULT nextval('public.vehicels_for_plan_id_seq'::regclass);


--
-- TOC entry 3436 (class 2604 OID 18240)
-- Name: phuongtien_nhiemvu id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.phuongtien_nhiemvu ALTER COLUMN id SET DEFAULT nextval('public.phuongtien_nhiemvu_id_seq'::regclass);


--
-- TOC entry 3437 (class 2604 OID 18241)
-- Name: quarter id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.quarter ALTER COLUMN id SET DEFAULT nextval('public.quarter_id_seq'::regclass);


--
-- TOC entry 3429 (class 2604 OID 18242)
-- Name: tcn id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tcn ALTER COLUMN id SET DEFAULT nextval('public.loai_nx_id_seq'::regclass);


--
-- TOC entry 3438 (class 2604 OID 18243)
-- Name: tructhuoc id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tructhuoc ALTER COLUMN id SET DEFAULT nextval('public.tructhuoc_id_seq'::regclass);


--
-- TOC entry 3696 (class 0 OID 17943)
-- Dependencies: 218
-- Data for Name: accounts; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.accounts (id, username, surname, roles, passwd, color, status, create_at, sd, ed) FROM stdin;
2	admin	tga	ADMIN	5dd4ebdac62609c834f7768f02286b798bd82a38	#ffffff	ACTIVE	2024-12-22 00:00:00	2025-01-05	2025-01-07
1	user_1	chain	USER	7c4a8d09ca3762af61e59520943dc26494f8941b	#ffffff	ACTIVE	2024-12-22 00:00:00	2025-01-07	2025-01-15
\.


--
-- TOC entry 3698 (class 0 OID 17950)
-- Dependencies: 220
-- Data for Name: activated_active; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.activated_active (id, status_name) FROM stdin;
1	IN_ACTIVE
2	ACTIVE
3	WATING
\.


--
-- TOC entry 3700 (class 0 OID 17956)
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
-- TOC entry 3701 (class 0 OID 17961)
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
-- TOC entry 3706 (class 0 OID 17980)
-- Dependencies: 228
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
-- TOC entry 3708 (class 0 OID 17986)
-- Dependencies: 230
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
-- TOC entry 3710 (class 0 OID 17995)
-- Dependencies: 232
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
-- TOC entry 3755 (class 0 OID 18459)
-- Dependencies: 277
-- Data for Name: configuration; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.configuration (id, parameter, value) FROM stdin;
1	ROOT_ID	54
\.


--
-- TOC entry 3712 (class 0 OID 18005)
-- Dependencies: 234
-- Data for Name: dinhmuc; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.dinhmuc (id, dm_tk_gio, dm_md_gio, dm_xm_km, dm_xm_gio, phuongtien_id, years) FROM stdin;
3	0	0	30	30	31	2025
7	0	0	18	18	3	2025
13	0	0	15	15	9	2025
24	0	0	35	35	19	2025
9	0	0	15	15	5	2025
21	0	0	32	32	16	2025
29	5527	3060	0	0	24	2025
31	987	612	0	0	26	2025
14	0	0	7	7	10	2025
15	0	0	10	10	11	2025
16	0	0	10	10	12	2025
17	0	0	4	4	13	2025
18	0	0	7	7	14	2025
19	0	0	12	12	15	2025
25	0	0	6	6	20	2025
26	0	0	12	12	21	2025
27	0	0	17	17	22	2025
28	0	0	27	27	23	2025
1	0	0	17	17	28	2025
4	0	0	34	34	32	2025
6	0	0	16	16	2	2025
8	0	0	12	12	4	2025
10	0	0	15	15	6	2025
22	0	0	14	14	17	2025
23	0	0	32	32	18	2025
5	0	0	12	12	1	2025
12	0	0	12	12	8	2025
11	0	0	15	15	7	2025
30	5034	2368	0	0	25	2025
\.


--
-- TOC entry 3714 (class 0 OID 18009)
-- Dependencies: 236
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
-- TOC entry 3715 (class 0 OID 18012)
-- Dependencies: 237
-- Data for Name: dvi_nv; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.dvi_nv (id, dv_id, nv_id, createtime) FROM stdin;
\.


--
-- TOC entry 3717 (class 0 OID 18030)
-- Dependencies: 239
-- Data for Name: hanmuc_nhiemvu2; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.hanmuc_nhiemvu2 (id, nhiemvu_id, dvi_id, diezel, daubay, xang, years) FROM stdin;
100	16	54	200	0	600	2025
101	17	54	181480	6350000	264236	2025
102	25	54	2081	0	2618	2025
103	26	54	752	0	753	2025
104	27	54	10500	0	10000	2025
105	28	54	19830	0	14000	2025
106	35	54	7500	0	11870	2025
108	38	54	1930	0	1370	2025
109	39	54	21600	0	5200	2025
110	40	54	15600	0	14000	2025
111	41	54	4700	0	3450	2025
112	42	54	1000	0	2000	2025
113	43	54	400	0	200	2025
114	44	54	120	0	200	2025
115	45	54	1000	0	1300	2025
116	46	54	11800	0	7975	2025
117	47	54	46460	1180200	40650	2025
118	49	54	1000	0	500	2025
119	6	54	2440	29310	5340	2025
120	7	54	244870	1302506	181345	2025
121	31	54	6300	0	7450	2025
122	37	54	1111	0	1100	2025
123	11	54	244870	1302506	99999	2025
124	15	54	22000	390000	22222	2025
125	20	54	123	0	500	2025
126	13	54	6000	0	3333	2025
127	10	54	244870	1302506	154888	2025
128	34	54	88888	0	36920	2025
129	21	54	300	0	450	2025
130	52	54	322	3333	1234	2025
131	8	54	244870	1302506	181345	2025
132	9	54	244870	1302506	181345	2025
133	12	54	123456	123456	12345	2025
134	23	54	555	0	555	2025
135	22	54	12323	0	12323	2025
136	24	54	244870	0	154888	2025
137	29	54	12348	0	2315	2025
138	30	54	2222	0	22222	2025
139	48	54	2545	0	2545	2025
140	3	54	2000	0	2000	2025
141	5	54	0	545345	0	2025
142	14	54	4555	0	5345	2025
143	32	54	5555	0	5455	2025
144	33	54	4444	0	54544	2025
145	4	54	4444	0	4544	2025
107	36	54	1111	0	1111	2025
\.


--
-- TOC entry 3719 (class 0 OID 18039)
-- Dependencies: 241
-- Data for Name: hanmuc_nhiemvu_taubay; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.hanmuc_nhiemvu_taubay (id, dvi_xuat_id, pt_id, ctnv_id, tk, md, nhienlieu, years) FROM stdin;
81	5	24	8	00:00	00:00	0	2025
82	6	24	8	00:00	00:00	0	2025
83	3	25	7	00:00	00:00	0	2025
84	3	25	8	00:00	00:00	0	2025
67	5	24	7	00:00	00:00	0	2025
68	6	24	7	00:00	00:00	0	2025
69	4	34	4	00:00	00:00	10100	2025
70	4	36	3	00:00	00:00	142857	2025
71	4	37	26	222:00	00:00	142857	2025
72	5	24	26	132:00	00:00	142857	2025
73	6	24	3	359:00	11:00	142857	2025
74	28	0	4	00:00	00:00	1650	2025
75	10	0	3	00:00	00:00	1190	2025
76	9	0	4	00:00	00:00	1190	2025
77	8	0	4	00:00	00:00	2850	2025
78	54	0	4	00:00	00:00	2258	2025
79	4	35	8	12:00	23:00	665858	2025
80	3	25	9	00:00	00:00	0	2025
\.


--
-- TOC entry 3692 (class 0 OID 17910)
-- Dependencies: 214
-- Data for Name: inventory; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.inventory (id, tdk_sscd, tdk_nvdx, status, petro_id, nhap_nvdx, nhap_sscd, xuat_nvdx, xuat_sscd, price, create_at, sd, ed) FROM stdin;
\.


--
-- TOC entry 3721 (class 0 OID 18058)
-- Dependencies: 243
-- Data for Name: ledger_details; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ledger_details (ma_xd, ten_xd, chung_loai, chat_luong, phai_xuat, thuc_xuat, don_gia, id, loaixd_id, phuongtien_id, ledger_id, thuc_xuat_tk, so_luong, thuc_nhap, phai_nhap, thanhtien, haohut_sl, nl_km, nl_gio, so_luong_px, sscd_nvdx, ty_trong, nhiet_do_tt, he_so_vcf, nhap_nvdx, nhap_sscd, xuat_nvdx, xuat_sscd) FROM stdin;
\.


--
-- TOC entry 3722 (class 0 OID 18078)
-- Dependencies: 244
-- Data for Name: ledger_map; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ledger_map (id, loaixd_id, header_id, soluong, mucgia_id, quarter_id, status) FROM stdin;
\.


--
-- TOC entry 3694 (class 0 OID 17924)
-- Dependencies: 216
-- Data for Name: ledgers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ledgers (id, bill_id, amount, from_date, end_date, status, sl_tieuthu_md, sl_tieuthu_tk, dvi_nhan_id, dvi_xuat_id, loai_phieu, dvi_nhan, dvi_xuat, loaigiobay, nguoi_nhan, so_xe, lenh_so, nhiemvu, nhiemvu_id, so_km, tcn_id, "timestamp", giohd_md, giohd_tk, loainv, tructhuoc, lpt, lpt_2, version, create_by, root_id, pt_id) FROM stdin;
\.


--
-- TOC entry 3723 (class 0 OID 18083)
-- Dependencies: 245
-- Data for Name: lichsuxnk; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.lichsuxnk (id, ten_xd, loai_phieu, tontruoc, soluong, tonsau, "timestamp", type, so, dvn, dvx, chungloaixd, gia, sd) FROM stdin;
\.


--
-- TOC entry 3725 (class 0 OID 18097)
-- Dependencies: 247
-- Data for Name: loai_nhiemvu; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.loai_nhiemvu (id, task_name) FROM stdin;
1	NV_BAY
2	KHAC
3	HAOHUT
\.


--
-- TOC entry 3729 (class 0 OID 18115)
-- Dependencies: 251
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
-- TOC entry 3731 (class 0 OID 18121)
-- Dependencies: 253
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
72	A83	Xăng A83	ACTIVE	2025-01-04 08:50:20.805223	7
\.


--
-- TOC entry 3734 (class 0 OID 18140)
-- Dependencies: 256
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
5	e923	NORMAL	29	e923
6	e927	NORMAL	29	e927
18	Kho K99	NORMAL	3	KHO_K99
8	d Kiến An	NORMAL	29	d_KIENAN
17	Kho 671	NORMAL	3	KHO_671
90	QK2	NORMAL	13	QK_2
4	e916	NORMAL	29	e916
\.


--
-- TOC entry 3737 (class 0 OID 18150)
-- Dependencies: 259
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
-- TOC entry 3704 (class 0 OID 17974)
-- Dependencies: 226
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
-- TOC entry 3739 (class 0 OID 18162)
-- Dependencies: 261
-- Data for Name: nhiemvu_tcn; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.nhiemvu_tcn (id, nvu_id, tcn_id, phuongtien_id) FROM stdin;
\.


--
-- TOC entry 3741 (class 0 OID 18177)
-- Dependencies: 263
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
-- TOC entry 3742 (class 0 OID 18183)
-- Dependencies: 264
-- Data for Name: phuongtien_nhiemvu; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.phuongtien_nhiemvu (id, phuongtien_id, nvu_id) FROM stdin;
\.


--
-- TOC entry 3744 (class 0 OID 18193)
-- Dependencies: 266
-- Data for Name: quarter; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.quarter (id, start_date, end_date, year, index, status) FROM stdin;
\.


--
-- TOC entry 3727 (class 0 OID 18103)
-- Dependencies: 249
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
31	2000	XUAT
\.


--
-- TOC entry 3748 (class 0 OID 18205)
-- Dependencies: 270
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
-- TOC entry 3750 (class 0 OID 18217)
-- Dependencies: 272
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
-- TOC entry 3735 (class 0 OID 18146)
-- Dependencies: 257
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
-- TOC entry 3777 (class 0 OID 0)
-- Dependencies: 215
-- Name: Inventory_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Inventory_id_seq"', 4592, true);


--
-- TOC entry 3778 (class 0 OID 0)
-- Dependencies: 217
-- Name: Ledgers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Ledgers_id_seq"', 772, true);


--
-- TOC entry 3779 (class 0 OID 0)
-- Dependencies: 219
-- Name: accounts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.accounts_id_seq', 2, true);


--
-- TOC entry 3780 (class 0 OID 0)
-- Dependencies: 221
-- Name: activated_active_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.activated_active_id_seq', 3, true);


--
-- TOC entry 3781 (class 0 OID 0)
-- Dependencies: 224
-- Name: category_assignment_id_seq1; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.category_assignment_id_seq1', 11, true);


--
-- TOC entry 3782 (class 0 OID 0)
-- Dependencies: 225
-- Name: category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.category_id_seq', 61, true);


--
-- TOC entry 3783 (class 0 OID 0)
-- Dependencies: 227
-- Name: chi_tiet_nhiemvu_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.chi_tiet_nhiemvu_id_seq', 60, true);


--
-- TOC entry 3784 (class 0 OID 0)
-- Dependencies: 229
-- Name: chitiet_nhiemvu_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.chitiet_nhiemvu_id_seq', 52, true);


--
-- TOC entry 3785 (class 0 OID 0)
-- Dependencies: 231
-- Name: chitieu_pt_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.chitieu_pt_id_seq', 34, true);


--
-- TOC entry 3786 (class 0 OID 0)
-- Dependencies: 233
-- Name: chungloaixd_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.chungloaixd_id_seq', 13, true);


--
-- TOC entry 3787 (class 0 OID 0)
-- Dependencies: 276
-- Name: configuration_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.configuration_id_seq', 1, true);


--
-- TOC entry 3788 (class 0 OID 0)
-- Dependencies: 235
-- Name: dinhmuc_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.dinhmuc_id_seq', 101, true);


--
-- TOC entry 3789 (class 0 OID 0)
-- Dependencies: 238
-- Name: dvi_nv_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.dvi_nv_id_seq', 127, true);


--
-- TOC entry 3790 (class 0 OID 0)
-- Dependencies: 240
-- Name: hanmuc_nhiemvu2_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hanmuc_nhiemvu2_id_seq', 145, true);


--
-- TOC entry 3791 (class 0 OID 0)
-- Dependencies: 242
-- Name: hanmuc_nhiemvu_taubay_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hanmuc_nhiemvu_taubay_id_seq', 84, true);


--
-- TOC entry 3792 (class 0 OID 0)
-- Dependencies: 246
-- Name: lichsuxnk_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.lichsuxnk_id_seq', 1321, true);


--
-- TOC entry 3793 (class 0 OID 0)
-- Dependencies: 248
-- Name: loai_nhiemvu_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.loai_nhiemvu_id_seq', 3, true);


--
-- TOC entry 3794 (class 0 OID 0)
-- Dependencies: 250
-- Name: loai_nx_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.loai_nx_id_seq', 31, true);


--
-- TOC entry 3795 (class 0 OID 0)
-- Dependencies: 252
-- Name: loai_phuongtien_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.loai_phuongtien_id_seq', 7, true);


--
-- TOC entry 3796 (class 0 OID 0)
-- Dependencies: 254
-- Name: loaixd2_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.loaixd2_id_seq', 72, true);


--
-- TOC entry 3797 (class 0 OID 0)
-- Dependencies: 255
-- Name: myseq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.myseq', 90, true);


--
-- TOC entry 3798 (class 0 OID 0)
-- Dependencies: 258
-- Name: nguonnx_loaiphieu_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.nguonnx_loaiphieu_id_seq', 20, true);


--
-- TOC entry 3799 (class 0 OID 0)
-- Dependencies: 260
-- Name: nguonnx_pt_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.nguonnx_pt_id_seq', 9, true);


--
-- TOC entry 3800 (class 0 OID 0)
-- Dependencies: 262
-- Name: nhiemvu_tcn_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.nhiemvu_tcn_id_seq', 1, false);


--
-- TOC entry 3801 (class 0 OID 0)
-- Dependencies: 265
-- Name: phuongtien_nhiemvu_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.phuongtien_nhiemvu_id_seq', 3, true);


--
-- TOC entry 3802 (class 0 OID 0)
-- Dependencies: 267
-- Name: quarter_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.quarter_id_seq', 55, true);


--
-- TOC entry 3803 (class 0 OID 0)
-- Dependencies: 268
-- Name: so_cai_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.so_cai_id_seq', 1570, true);


--
-- TOC entry 3804 (class 0 OID 0)
-- Dependencies: 269
-- Name: splog_adfarm_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.splog_adfarm_seq', 1, false);


--
-- TOC entry 3805 (class 0 OID 0)
-- Dependencies: 271
-- Name: team_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.team_id_seq', 5, true);


--
-- TOC entry 3806 (class 0 OID 0)
-- Dependencies: 273
-- Name: tructhuoc_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tructhuoc_id_seq', 34, true);


--
-- TOC entry 3807 (class 0 OID 0)
-- Dependencies: 274
-- Name: tructhuocf_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tructhuocf_id_seq', 9, true);


--
-- TOC entry 3808 (class 0 OID 0)
-- Dependencies: 275
-- Name: vehicels_for_plan_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.vehicels_for_plan_id_seq', 38, true);


--
-- TOC entry 3441 (class 2606 OID 18245)
-- Name: inventory Inventory_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.inventory
    ADD CONSTRAINT "Inventory_pkey" PRIMARY KEY (id);


--
-- TOC entry 3443 (class 2606 OID 18247)
-- Name: ledgers Ledgers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ledgers
    ADD CONSTRAINT "Ledgers_pkey" PRIMARY KEY (id);


--
-- TOC entry 3445 (class 2606 OID 18249)
-- Name: accounts accounts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT accounts_pkey PRIMARY KEY (id);


--
-- TOC entry 3447 (class 2606 OID 18251)
-- Name: accounts accounts_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT accounts_username_key UNIQUE (username);


--
-- TOC entry 3449 (class 2606 OID 18253)
-- Name: activated_active activated_active_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.activated_active
    ADD CONSTRAINT activated_active_pkey PRIMARY KEY (id);


--
-- TOC entry 3455 (class 2606 OID 18255)
-- Name: category_assignment category_assignment_code_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category_assignment
    ADD CONSTRAINT category_assignment_code_key UNIQUE (code);


--
-- TOC entry 3457 (class 2606 OID 18259)
-- Name: category_assignment category_assignment_pkey1; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category_assignment
    ADD CONSTRAINT category_assignment_pkey1 PRIMARY KEY (id);


--
-- TOC entry 3451 (class 2606 OID 18261)
-- Name: category category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);


--
-- TOC entry 3453 (class 2606 OID 18263)
-- Name: category category_type_title_tructhuoc_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_type_title_tructhuoc_id_key UNIQUE (type_title, tructhuoc_id);


--
-- TOC entry 3459 (class 2606 OID 18265)
-- Name: nhiemvu chi_tiet_nhiemvu_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nhiemvu
    ADD CONSTRAINT chi_tiet_nhiemvu_pkey PRIMARY KEY (id);


--
-- TOC entry 3461 (class 2606 OID 18267)
-- Name: chitiet_nhiemvu chitiet_nhiemvu_nhiemvu_id_nhiemvu_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chitiet_nhiemvu
    ADD CONSTRAINT chitiet_nhiemvu_nhiemvu_id_nhiemvu_key UNIQUE (nhiemvu_id, nhiemvu);


--
-- TOC entry 3463 (class 2606 OID 18269)
-- Name: chitiet_nhiemvu chitiet_nhiemvu_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chitiet_nhiemvu
    ADD CONSTRAINT chitiet_nhiemvu_pkey PRIMARY KEY (id);


--
-- TOC entry 3465 (class 2606 OID 18271)
-- Name: chitieu_pt chitieu_pt_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chitieu_pt
    ADD CONSTRAINT chitieu_pt_pkey PRIMARY KEY (id);


--
-- TOC entry 3467 (class 2606 OID 18273)
-- Name: chungloaixd chungloaixd_code_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chungloaixd
    ADD CONSTRAINT chungloaixd_code_key UNIQUE (code);


--
-- TOC entry 3469 (class 2606 OID 18275)
-- Name: chungloaixd chungloaixd_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chungloaixd
    ADD CONSTRAINT chungloaixd_pkey PRIMARY KEY (id);


--
-- TOC entry 3537 (class 2606 OID 18467)
-- Name: configuration configuration_id_parameter_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.configuration
    ADD CONSTRAINT configuration_id_parameter_key UNIQUE (id, parameter);


--
-- TOC entry 3539 (class 2606 OID 18465)
-- Name: configuration configuration_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.configuration
    ADD CONSTRAINT configuration_pkey PRIMARY KEY (id);


--
-- TOC entry 3471 (class 2606 OID 18279)
-- Name: dinhmuc dinhmuc_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dinhmuc
    ADD CONSTRAINT dinhmuc_pkey PRIMARY KEY (id);


--
-- TOC entry 3475 (class 2606 OID 18281)
-- Name: dvi_nv dvi_nv_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dvi_nv
    ADD CONSTRAINT dvi_nv_pkey PRIMARY KEY (id);


--
-- TOC entry 3477 (class 2606 OID 18285)
-- Name: hanmuc_nhiemvu2 hanmuc_nhiemvu2_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.hanmuc_nhiemvu2
    ADD CONSTRAINT hanmuc_nhiemvu2_pkey PRIMARY KEY (id);


--
-- TOC entry 3479 (class 2606 OID 18496)
-- Name: hanmuc_nhiemvu2 hanmuc_nhiemvu2_years_nhiemvu_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.hanmuc_nhiemvu2
    ADD CONSTRAINT hanmuc_nhiemvu2_years_nhiemvu_id_key UNIQUE (years, nhiemvu_id);


--
-- TOC entry 3481 (class 2606 OID 18500)
-- Name: hanmuc_nhiemvu_taubay hanmuc_nhiemvu_taubay_dvi_xuat_id_pt_id_ctnv_id_years_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.hanmuc_nhiemvu_taubay
    ADD CONSTRAINT hanmuc_nhiemvu_taubay_dvi_xuat_id_pt_id_ctnv_id_years_key UNIQUE (dvi_xuat_id, pt_id, ctnv_id, years);


--
-- TOC entry 3483 (class 2606 OID 18289)
-- Name: hanmuc_nhiemvu_taubay hanmuc_nhiemvu_taubay_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.hanmuc_nhiemvu_taubay
    ADD CONSTRAINT hanmuc_nhiemvu_taubay_pkey PRIMARY KEY (id);


--
-- TOC entry 3487 (class 2606 OID 18303)
-- Name: ledger_map ledger_map_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ledger_map
    ADD CONSTRAINT ledger_map_pkey PRIMARY KEY (id, loaixd_id, header_id, quarter_id);


--
-- TOC entry 3489 (class 2606 OID 18307)
-- Name: lichsuxnk lichsuxnk_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lichsuxnk
    ADD CONSTRAINT lichsuxnk_pkey PRIMARY KEY (id);


--
-- TOC entry 3491 (class 2606 OID 18309)
-- Name: loai_nhiemvu loai_nhiemvu_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.loai_nhiemvu
    ADD CONSTRAINT loai_nhiemvu_pkey PRIMARY KEY (id);


--
-- TOC entry 3493 (class 2606 OID 18311)
-- Name: loai_nhiemvu loai_nhiemvu_task_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.loai_nhiemvu
    ADD CONSTRAINT loai_nhiemvu_task_name_key UNIQUE (task_name);


--
-- TOC entry 3499 (class 2606 OID 18315)
-- Name: loai_phuongtien loai_phuongtien_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.loai_phuongtien
    ADD CONSTRAINT loai_phuongtien_pkey PRIMARY KEY (id);


--
-- TOC entry 3501 (class 2606 OID 18317)
-- Name: loaixd2 loaixd2_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.loaixd2
    ADD CONSTRAINT loaixd2_pkey PRIMARY KEY (id);


--
-- TOC entry 3503 (class 2606 OID 18321)
-- Name: nguon_nx nguon_nx_code_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nguon_nx
    ADD CONSTRAINT nguon_nx_code_key UNIQUE (code);


--
-- TOC entry 3505 (class 2606 OID 18323)
-- Name: nguon_nx nguon_nx_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nguon_nx
    ADD CONSTRAINT nguon_nx_pkey PRIMARY KEY (id);


--
-- TOC entry 3509 (class 2606 OID 18325)
-- Name: tructhuoc_loaiphieu nguonnx_loaiphieu_nguonnx_id_loaiphieu_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tructhuoc_loaiphieu
    ADD CONSTRAINT nguonnx_loaiphieu_nguonnx_id_loaiphieu_id_key UNIQUE (tructhuoc_id, loaiphieu_id);


--
-- TOC entry 3511 (class 2606 OID 18327)
-- Name: tructhuoc_loaiphieu nguonnx_loaiphieu_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tructhuoc_loaiphieu
    ADD CONSTRAINT nguonnx_loaiphieu_pkey PRIMARY KEY (id);


--
-- TOC entry 3513 (class 2606 OID 18329)
-- Name: nguonnx_pt nguonnx_pt_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nguonnx_pt
    ADD CONSTRAINT nguonnx_pt_pkey PRIMARY KEY (id);


--
-- TOC entry 3515 (class 2606 OID 18337)
-- Name: nhiemvu_tcn nhiemvu_tcn_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nhiemvu_tcn
    ADD CONSTRAINT nhiemvu_tcn_pkey PRIMARY KEY (id);


--
-- TOC entry 3517 (class 2606 OID 18343)
-- Name: phuongtien phuongtien_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.phuongtien
    ADD CONSTRAINT phuongtien_name_key UNIQUE (name);


--
-- TOC entry 3521 (class 2606 OID 18345)
-- Name: phuongtien_nhiemvu phuongtien_nhiemvu_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.phuongtien_nhiemvu
    ADD CONSTRAINT phuongtien_nhiemvu_pkey PRIMARY KEY (id);


--
-- TOC entry 3519 (class 2606 OID 18347)
-- Name: phuongtien phuongtien_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.phuongtien
    ADD CONSTRAINT phuongtien_pkey PRIMARY KEY (id);


--
-- TOC entry 3523 (class 2606 OID 18486)
-- Name: quarter quarter_index_year_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.quarter
    ADD CONSTRAINT quarter_index_year_key UNIQUE (index, year);


--
-- TOC entry 3525 (class 2606 OID 18353)
-- Name: quarter quarter_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.quarter
    ADD CONSTRAINT quarter_pkey PRIMARY KEY (id);


--
-- TOC entry 3485 (class 2606 OID 18355)
-- Name: ledger_details so_cai_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ledger_details
    ADD CONSTRAINT so_cai_pkey PRIMARY KEY (id);


--
-- TOC entry 3495 (class 2606 OID 18357)
-- Name: tcn tcn_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tcn
    ADD CONSTRAINT tcn_name_key UNIQUE (name);


--
-- TOC entry 3497 (class 2606 OID 18359)
-- Name: tcn tcn_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tcn
    ADD CONSTRAINT tcn_pkey PRIMARY KEY (id);


--
-- TOC entry 3527 (class 2606 OID 18361)
-- Name: team team_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team
    ADD CONSTRAINT team_pkey PRIMARY KEY (id);


--
-- TOC entry 3529 (class 2606 OID 18363)
-- Name: team team_team_code_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.team
    ADD CONSTRAINT team_team_code_key UNIQUE (team_code);


--
-- TOC entry 3507 (class 2606 OID 18365)
-- Name: nguon_nx ten_uni; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nguon_nx
    ADD CONSTRAINT ten_uni UNIQUE (ten);


--
-- TOC entry 3531 (class 2606 OID 18369)
-- Name: tructhuoc tructhuoc_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tructhuoc
    ADD CONSTRAINT tructhuoc_name_key UNIQUE (name);


--
-- TOC entry 3533 (class 2606 OID 18371)
-- Name: tructhuoc tructhuoc_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tructhuoc
    ADD CONSTRAINT tructhuoc_pkey PRIMARY KEY (id);


--
-- TOC entry 3535 (class 2606 OID 18373)
-- Name: tructhuoc tructhuoc_type_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tructhuoc
    ADD CONSTRAINT tructhuoc_type_key UNIQUE (type);


--
-- TOC entry 3473 (class 2606 OID 18375)
-- Name: donvi_tructhuoc tructhuocf_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.donvi_tructhuoc
    ADD CONSTRAINT tructhuocf_pkey PRIMARY KEY (id);


--
-- TOC entry 3543 (class 2606 OID 18376)
-- Name: dinhmuc dinhmuc_phuongtien_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dinhmuc
    ADD CONSTRAINT dinhmuc_phuongtien_id_fkey FOREIGN KEY (phuongtien_id) REFERENCES public.phuongtien(id) NOT VALID;


--
-- TOC entry 3544 (class 2606 OID 18381)
-- Name: dvi_nv dvi_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dvi_nv
    ADD CONSTRAINT dvi_fkey FOREIGN KEY (dv_id) REFERENCES public.nguon_nx(id) NOT VALID;


--
-- TOC entry 3540 (class 2606 OID 18386)
-- Name: inventory inventory_petro_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.inventory
    ADD CONSTRAINT inventory_petro_id_fkey FOREIGN KEY (petro_id) REFERENCES public.loaixd2(id) NOT VALID;


--
-- TOC entry 3546 (class 2606 OID 18396)
-- Name: ledger_details ledger_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ledger_details
    ADD CONSTRAINT ledger_fkey FOREIGN KEY (ledger_id) REFERENCES public.ledgers(id) NOT VALID;


--
-- TOC entry 3547 (class 2606 OID 18406)
-- Name: ledger_details loaixd_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ledger_details
    ADD CONSTRAINT loaixd_fkey FOREIGN KEY (loaixd_id) REFERENCES public.loaixd2(id) NOT VALID;


--
-- TOC entry 3550 (class 2606 OID 18411)
-- Name: phuongtien nguonnx_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.phuongtien
    ADD CONSTRAINT nguonnx_fkey FOREIGN KEY (nguonnx_id) REFERENCES public.nguon_nx(id) NOT VALID;


--
-- TOC entry 3548 (class 2606 OID 18421)
-- Name: nhiemvu_tcn nhiemvu_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nhiemvu_tcn
    ADD CONSTRAINT nhiemvu_fkey FOREIGN KEY (nvu_id) REFERENCES public.nhiemvu(id) NOT VALID;


--
-- TOC entry 3542 (class 2606 OID 18426)
-- Name: chitiet_nhiemvu nhiemvu_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.chitiet_nhiemvu
    ADD CONSTRAINT nhiemvu_id FOREIGN KEY (nhiemvu_id) REFERENCES public.nhiemvu(id) NOT VALID;


--
-- TOC entry 3551 (class 2606 OID 18431)
-- Name: phuongtien_nhiemvu nhiemvu_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.phuongtien_nhiemvu
    ADD CONSTRAINT nhiemvu_id FOREIGN KEY (nvu_id) REFERENCES public.nhiemvu(id) NOT VALID;


--
-- TOC entry 3541 (class 2606 OID 18436)
-- Name: nhiemvu nhiemvu_team_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nhiemvu
    ADD CONSTRAINT nhiemvu_team_id_fkey FOREIGN KEY (team_id) REFERENCES public.team(id) NOT VALID;


--
-- TOC entry 3545 (class 2606 OID 18441)
-- Name: dvi_nv nvu_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dvi_nv
    ADD CONSTRAINT nvu_fkey FOREIGN KEY (nv_id) REFERENCES public.nhiemvu(id) NOT VALID;


--
-- TOC entry 3552 (class 2606 OID 18446)
-- Name: phuongtien_nhiemvu phuongtien_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.phuongtien_nhiemvu
    ADD CONSTRAINT phuongtien_id FOREIGN KEY (phuongtien_id) REFERENCES public.phuongtien(id) NOT VALID;


--
-- TOC entry 3549 (class 2606 OID 18451)
-- Name: nhiemvu_tcn tcn_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nhiemvu_tcn
    ADD CONSTRAINT tcn_fkey FOREIGN KEY (tcn_id) REFERENCES public.tcn(id) NOT VALID;


--
-- TOC entry 3762 (class 0 OID 0)
-- Dependencies: 7
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2025-01-10 08:20:58

--
-- PostgreSQL database dump complete
--

