PGDMP                         |            postgres    14.15    14.15 -   #           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            $           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            %           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            &           1262    13754    postgres    DATABASE     l   CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'English_United States.1252';
    DROP DATABASE postgres;
                postgres    false            '           0    0    DATABASE postgres    COMMENT     N   COMMENT ON DATABASE postgres IS 'default administrative connection database';
                   postgres    false    3878                        3079    16384 	   adminpack 	   EXTENSION     A   CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;
    DROP EXTENSION adminpack;
                   false            (           0    0    EXTENSION adminpack    COMMENT     M   COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';
                        false    2                        3079    16394 	   tablefunc 	   EXTENSION     =   CREATE EXTENSION IF NOT EXISTS tablefunc WITH SCHEMA public;
    DROP EXTENSION tablefunc;
                   false            )           0    0    EXTENSION tablefunc    COMMENT     `   COMMENT ON EXTENSION tablefunc IS 'functions that manipulate whole tables, including crosstab';
                        false    3            :           1255    16415    get_sum(numeric, numeric)    FUNCTION     �   CREATE FUNCTION public.get_sum(a numeric, b numeric) RETURNS numeric
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN a + b;
END; 
$$;
 4   DROP FUNCTION public.get_sum(a numeric, b numeric);
       public          postgres    false            L           1255    25180 ;   tonkhonhap_xd2(integer, text, integer, text, integer, text)    FUNCTION       CREATE FUNCTION public.tonkhonhap_xd2(q_id integer, tt text, lxd_id integer, lp text, dvin_id integer, st text) RETURNS integer
    LANGUAGE plpgsql
    AS $$
declare
	total integer;
BEGIN
   select soluong into total from (select loaixd_id, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id where loai_phieu like lp and tructhuoc like tt and quarter_id=q_id and loaixd_id=lxd_id and dvi_xuat_id=dvin_id and l.status like st group by loaixd_id limit 1) a;
   RETURN total;
END;
$$;
 o   DROP FUNCTION public.tonkhonhap_xd2(q_id integer, tt text, lxd_id integer, lp text, dvin_id integer, st text);
       public          postgres    false            K           1255    25178 ;   tonkhoxuat_xd2(integer, text, integer, text, integer, text)    FUNCTION       CREATE FUNCTION public.tonkhoxuat_xd2(q_id integer, tt text, lxd_id integer, lp text, dvix_id integer, st text) RETURNS integer
    LANGUAGE plpgsql
    AS $$
declare
	total integer;
BEGIN
   select soluong into total from (select loaixd_id, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id where loai_phieu like lp and tructhuoc like tt and quarter_id=q_id and loaixd_id=lxd_id and dvi_xuat_id=dvix_id and l.status like st group by loaixd_id limit 1) a;
   RETURN total;
END;
$$;
 o   DROP FUNCTION public.tonkhoxuat_xd2(q_id integer, tt text, lxd_id integer, lp text, dvix_id integer, st text);
       public          postgres    false            ;           1255    16418 &   totalloaixd(integer, integer, integer)    FUNCTION     �  CREATE FUNCTION public.totalloaixd(q_id integer, dvi_x integer, lxd_id integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$
declare
	total integer;
BEGIN
   select loaixd_id, sum(so_luong) into total from ledgers l join ledger_details ld on l.id=ld.ledger_id where loai_phieu like 'NHAP' and dvi_xuat_id=dvi_x and quarter_id=q_id and loaixd_id=lxd_id group by loaixd_id limit 1;
   RETURN total;
END;
$$;
 O   DROP FUNCTION public.totalloaixd(q_id integer, dvi_x integer, lxd_id integer);
       public          postgres    false            <           1255    16419 '   totalloaixd2(integer, integer, integer)    FUNCTION     �  CREATE FUNCTION public.totalloaixd2(q_id integer, dvi_x integer, lxd_id integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$
declare
	total integer;
BEGIN
   select soluong into total from (select loaixd_id, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id where loai_phieu like 'NHAP' and dvi_xuat_id=dvi_x and quarter_id=q_id and loaixd_id=lxd_id group by loaixd_id limit 1) a;
   RETURN total;
END;
$$;
 P   DROP FUNCTION public.totalloaixd2(q_id integer, dvi_x integer, lxd_id integer);
       public          postgres    false            @           1255    16420 *   totalloaixd2(integer, text, integer, text)    FUNCTION     �  CREATE FUNCTION public.totalloaixd2(q_id integer, tt text, lxd_id integer, lp text) RETURNS integer
    LANGUAGE plpgsql
    AS $$
declare
	total integer;
BEGIN
   select soluong into total from (select loaixd_id, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id where loai_phieu like lp and tructhuoc like tt and quarter_id=q_id and loaixd_id=lxd_id group by loaixd_id limit 1) a;
   RETURN total;
END;
$$;
 S   DROP FUNCTION public.totalloaixd2(q_id integer, tt text, lxd_id integer, lp text);
       public          postgres    false            A           1255    16421 '   totalrecords(integer, integer, integer)    FUNCTION     �  CREATE FUNCTION public.totalrecords(q_id integer, dvi_xuat integer, lxd_id integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$
declare
	total integer;
BEGIN
   select loaixd_id, sum(so_luong) into total from ledgers l join ledger_details ld on l.id=ld.ledger_id where loai_phieu like 'NHAP' and l.dvi_xuat_id=dvi_xuat and quarter_id=q_id and loaixd_id=lxd_id group by loaixd_id limit 1;
   RETURN total;
END;
$$;
 S   DROP FUNCTION public.totalrecords(q_id integer, dvi_xuat integer, lxd_id integer);
       public          postgres    false            J           1255    16422 .   totalttxd_xmt(integer, integer, integer, text)    FUNCTION       CREATE FUNCTION public.totalttxd_xmt(q_id integer, dvx_id integer, nv_id integer, loaipt text) RETURNS TABLE(sl integer, sokm integer)
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
 ^   DROP FUNCTION public.totalttxd_xmt(q_id integer, dvx_id integer, nv_id integer, loaipt text);
       public          postgres    false            �            1259    16423 	   inventory    TABLE     �  CREATE TABLE public.inventory (
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
    create_at timestamp without time zone DEFAULT now()
);
    DROP TABLE public.inventory;
       public         heap    postgres    false            �            1259    16432    Inventory_id_seq    SEQUENCE     �   ALTER TABLE public.inventory ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Inventory_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    214            �            1259    16433    ledgers    TABLE     �  CREATE TABLE public.ledgers (
    id integer NOT NULL,
    quarter_id integer,
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
    version smallint DEFAULT 0
);
    DROP TABLE public.ledgers;
       public         heap    postgres    false            �            1259    16450    Ledgers_id_seq    SEQUENCE     �   ALTER TABLE public.ledgers ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Ledgers_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    216            .           1259    25154    accounts    TABLE     �   CREATE TABLE public.accounts (
    id smallint NOT NULL,
    username text,
    surname text,
    roles character varying(20),
    passwd text
);
    DROP TABLE public.accounts;
       public         heap    postgres    false            -           1259    25153    accounts_id_seq    SEQUENCE     �   ALTER TABLE public.accounts ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.accounts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    302            �            1259    16451    activated_active    TABLE     X   CREATE TABLE public.activated_active (
    id integer NOT NULL,
    status_name text
);
 $   DROP TABLE public.activated_active;
       public         heap    postgres    false            �            1259    16456    activated_active_id_seq    SEQUENCE     �   ALTER TABLE public.activated_active ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.activated_active_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    218            �            1259    16457    category    TABLE     �   CREATE TABLE public.category (
    id integer NOT NULL,
    header_lv1 text,
    header_lv2 text,
    header_lv3 text,
    type_title text,
    tructhuoc_id integer,
    code text
);
    DROP TABLE public.category;
       public         heap    postgres    false            �            1259    16462    category_assignment    TABLE     �   CREATE TABLE public.category_assignment (
    title_1 text,
    title_2 text,
    title_3 text,
    title_4 text,
    id integer NOT NULL,
    code text
);
 '   DROP TABLE public.category_assignment;
       public         heap    postgres    false            �            1259    16467    nhiemvu_reporter    TABLE       CREATE TABLE public.nhiemvu_reporter (
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
 $   DROP TABLE public.nhiemvu_reporter;
       public         heap    postgres    false            �            1259    16472    category_assignment_id_seq    SEQUENCE     �   ALTER TABLE public.nhiemvu_reporter ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.category_assignment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    222            �            1259    16473    category_assignment_id_seq1    SEQUENCE     �   ALTER TABLE public.category_assignment ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.category_assignment_id_seq1
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    221            �            1259    16474    category_id_seq    SEQUENCE     �   ALTER TABLE public.category ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.category_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    220            �            1259    16475    nhiemvu    TABLE     �   CREATE TABLE public.nhiemvu (
    id integer NOT NULL,
    ten_nv text,
    createtime text,
    status text,
    team_id integer,
    assignment_type_id integer,
    priority integer,
    priority_bc2 integer
);
    DROP TABLE public.nhiemvu;
       public         heap    postgres    false            �            1259    16480    chi_tiet_nhiemvu_id_seq    SEQUENCE     �   CREATE SEQUENCE public.chi_tiet_nhiemvu_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.chi_tiet_nhiemvu_id_seq;
       public          postgres    false    226            *           0    0    chi_tiet_nhiemvu_id_seq    SEQUENCE OWNED BY     J   ALTER SEQUENCE public.chi_tiet_nhiemvu_id_seq OWNED BY public.nhiemvu.id;
          public          postgres    false    227            �            1259    16481    chitiet_nhiemvu    TABLE     k   CREATE TABLE public.chitiet_nhiemvu (
    id integer NOT NULL,
    nhiemvu_id integer,
    nhiemvu text
);
 #   DROP TABLE public.chitiet_nhiemvu;
       public         heap    postgres    false            �            1259    16486    chitiet_nhiemvu_id_seq    SEQUENCE     �   CREATE SEQUENCE public.chitiet_nhiemvu_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.chitiet_nhiemvu_id_seq;
       public          postgres    false    228            +           0    0    chitiet_nhiemvu_id_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public.chitiet_nhiemvu_id_seq OWNED BY public.chitiet_nhiemvu.id;
          public          postgres    false    229            �            1259    16487 
   chitieu_pt    TABLE     	  CREATE TABLE public.chitieu_pt (
    id integer NOT NULL,
    dvi_id integer DEFAULT 0,
    ctnv_id integer DEFAULT 0,
    quy_id integer DEFAULT 0,
    md character varying(20),
    tk character varying(20),
    nl bigint DEFAULT 0,
    pt_id integer DEFAULT 0
);
    DROP TABLE public.chitieu_pt;
       public         heap    postgres    false            �            1259    16495    chitieu_pt_id_seq    SEQUENCE     �   ALTER TABLE public.chitieu_pt ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.chitieu_pt_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    230            �            1259    16496    chungloaixd    TABLE     G  CREATE TABLE public.chungloaixd (
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
    DROP TABLE public.chungloaixd;
       public         heap    postgres    false            �            1259    16505    chungloaixd_id_seq    SEQUENCE     �   ALTER TABLE public.chungloaixd ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.chungloaixd_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    232            �            1259    16506    dinhmuc    TABLE     �   CREATE TABLE public.dinhmuc (
    id integer NOT NULL,
    dm_tk_gio integer,
    dm_md_gio integer,
    dm_xm_km integer,
    dm_xm_gio integer,
    phuongtien_id integer,
    quarter_id integer
);
    DROP TABLE public.dinhmuc;
       public         heap    postgres    false            �            1259    16509    dinhmuc_id_seq    SEQUENCE     �   ALTER TABLE public.dinhmuc ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.dinhmuc_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    234            �            1259    16510    donvi_tructhuoc    TABLE     t   CREATE TABLE public.donvi_tructhuoc (
    id integer NOT NULL,
    root_id integer,
    dvi_tructhuoc_id integer
);
 #   DROP TABLE public.donvi_tructhuoc;
       public         heap    postgres    false            �            1259    16513    dvi_nv    TABLE     s   CREATE TABLE public.dvi_nv (
    id integer NOT NULL,
    dv_id integer,
    nv_id integer,
    createtime text
);
    DROP TABLE public.dvi_nv;
       public         heap    postgres    false            �            1259    16518    dvi_nv_id_seq    SEQUENCE     �   CREATE SEQUENCE public.dvi_nv_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.dvi_nv_id_seq;
       public          postgres    false    237            ,           0    0    dvi_nv_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.dvi_nv_id_seq OWNED BY public.dvi_nv.id;
          public          postgres    false    238            �            1259    16519    group_title    TABLE        CREATE TABLE public.group_title (
    id integer NOT NULL,
    group_name text,
    group_code text,
    a text,
    b text
);
    DROP TABLE public.group_title;
       public         heap    postgres    false            �            1259    16524    group_title_id_seq    SEQUENCE     �   ALTER TABLE public.group_title ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.group_title_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    239            �            1259    16525    hanmuc    TABLE     �   CREATE TABLE public.hanmuc (
    id integer NOT NULL,
    quarter_id integer,
    hanmuc_md text,
    hanmuc_km bigint,
    hanmuc_tk text,
    soluong bigint,
    pt_id integer
);
    DROP TABLE public.hanmuc;
       public         heap    postgres    false            �            1259    16530    hanmuc_id_seq    SEQUENCE     �   ALTER TABLE public.hanmuc ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.hanmuc_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    241            �            1259    16534    hanmuc_nhiemvu2    TABLE     �   CREATE TABLE public.hanmuc_nhiemvu2 (
    id bigint NOT NULL,
    quarter_id bigint DEFAULT 0,
    nhiemvu_id bigint DEFAULT 0,
    dvi_id bigint DEFAULT 0,
    diezel bigint DEFAULT 0,
    daubay bigint DEFAULT 0,
    xang bigint
);
 #   DROP TABLE public.hanmuc_nhiemvu2;
       public         heap    postgres    false            �            1259    16542    hanmuc_nhiemvu2_id_seq    SEQUENCE     �   ALTER TABLE public.hanmuc_nhiemvu2 ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.hanmuc_nhiemvu2_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    243            �            1259    16544    hanmuc_nhiemvu_taubay    TABLE     �   CREATE TABLE public.hanmuc_nhiemvu_taubay (
    id bigint NOT NULL,
    dvi_xuat_id bigint,
    pt_id bigint,
    ctnv_id bigint,
    quy_id bigint,
    tk text DEFAULT '00:00'::text,
    md text DEFAULT '00:00'::text,
    nhienlieu bigint DEFAULT 0
);
 )   DROP TABLE public.hanmuc_nhiemvu_taubay;
       public         heap    postgres    false            �            1259    16552    hanmuc_nhiemvu_taubay_id_seq    SEQUENCE     �   ALTER TABLE public.hanmuc_nhiemvu_taubay ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.hanmuc_nhiemvu_taubay_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    245            �            1259    16553 
   inv_report    TABLE     �   CREATE TABLE public.inv_report (
    id integer NOT NULL,
    petroleum_id integer,
    quarter_id integer,
    inventory_id integer,
    report_header integer,
    quantity integer,
    price_id integer
);
    DROP TABLE public.inv_report;
       public         heap    postgres    false            �            1259    16556    inv_report_detail    TABLE     -  CREATE TABLE public.inv_report_detail (
    id integer NOT NULL,
    loaixd text,
    soluong bigint,
    title_lv1 text,
    title_lv2 text,
    title_lv3 text,
    title_lxd_lv1 text,
    title_lxd_lv2 text,
    title_lxd_lv3 text,
    xd_id integer,
    title_id integer,
    quarter_id integer
);
 %   DROP TABLE public.inv_report_detail;
       public         heap    postgres    false            �            1259    16561    inv_report_detail_id_seq    SEQUENCE     �   ALTER TABLE public.inv_report_detail ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.inv_report_detail_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    248            �            1259    16562    inv_report_id_seq    SEQUENCE     �   ALTER TABLE public.inv_report ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.inv_report_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    247            �            1259    16563    ledger_details    TABLE     �  CREATE TABLE public.ledger_details (
    ma_xd text,
    ten_xd text,
    chung_loai text,
    chat_luong text,
    phai_xuat integer DEFAULT 0,
    nhiet_do_tt text,
    ty_trong integer DEFAULT 0,
    he_so_vcf integer DEFAULT 0,
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
    sscd_nvdx character varying(10) DEFAULT 'NVDX'::character varying
);
 "   DROP TABLE public.ledger_details;
       public         heap    postgres    false            �            1259    16582 
   ledger_map    TABLE     �   CREATE TABLE public.ledger_map (
    id integer NOT NULL,
    loaixd_id integer NOT NULL,
    header_id integer NOT NULL,
    soluong integer,
    mucgia_id integer,
    quarter_id integer NOT NULL,
    status text
);
    DROP TABLE public.ledger_map;
       public         heap    postgres    false            �            1259    16587 	   lichsuxnk    TABLE     �  CREATE TABLE public.lichsuxnk (
    id integer NOT NULL,
    ten_xd text,
    loai_phieu text,
    tontruoc integer DEFAULT 0,
    soluong integer DEFAULT 0,
    tonsau integer DEFAULT 0,
    createtime text,
    "timestamp" timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    type character varying(5) DEFAULT 'NVDX'::character varying,
    so smallint DEFAULT 0,
    dvn text,
    dvx text,
    chungloaixd text,
    quy_id smallint DEFAULT 0,
    gia bigint DEFAULT 0
);
    DROP TABLE public.lichsuxnk;
       public         heap    postgres    false            �            1259    16593    lichsuxnk_id_seq    SEQUENCE     �   CREATE SEQUENCE public.lichsuxnk_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.lichsuxnk_id_seq;
       public          postgres    false    253            -           0    0    lichsuxnk_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.lichsuxnk_id_seq OWNED BY public.lichsuxnk.id;
          public          postgres    false    254            �            1259    16594    loai_nhiemvu    TABLE     R   CREATE TABLE public.loai_nhiemvu (
    id integer NOT NULL,
    task_name text
);
     DROP TABLE public.loai_nhiemvu;
       public         heap    postgres    false                        1259    16599    loai_nhiemvu_id_seq    SEQUENCE     �   ALTER TABLE public.loai_nhiemvu ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.loai_nhiemvu_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    255                       1259    16600    tcn    TABLE     i   CREATE TABLE public.tcn (
    id integer NOT NULL,
    name text,
    loaiphieu character varying(10)
);
    DROP TABLE public.tcn;
       public         heap    postgres    false                       1259    16605    loai_nx_id_seq    SEQUENCE     �   CREATE SEQUENCE public.loai_nx_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.loai_nx_id_seq;
       public          postgres    false    257            .           0    0    loai_nx_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.loai_nx_id_seq OWNED BY public.tcn.id;
          public          postgres    false    258                       1259    16606 
   loai_phieu    TABLE     K   CREATE TABLE public.loai_phieu (
    id integer NOT NULL,
    type text
);
    DROP TABLE public.loai_phieu;
       public         heap    postgres    false                       1259    16611    loai_phieu_id_seq    SEQUENCE     �   ALTER TABLE public.loai_phieu ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.loai_phieu_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    259                       1259    16612    loai_phuongtien    TABLE     d   CREATE TABLE public.loai_phuongtien (
    id integer NOT NULL,
    type_name text,
    type text
);
 #   DROP TABLE public.loai_phuongtien;
       public         heap    postgres    false                       1259    16617    loai_phuongtien_id_seq    SEQUENCE     �   ALTER TABLE public.loai_phuongtien ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.loai_phuongtien_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    261                       1259    16618    loaixd2    TABLE     �   CREATE TABLE public.loaixd2 (
    id integer NOT NULL,
    maxd text,
    tenxd text,
    status text,
    "timestamp" timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    petroleum_type_id integer
);
    DROP TABLE public.loaixd2;
       public         heap    postgres    false                       1259    16624    loaixd2_id_seq    SEQUENCE     �   CREATE SEQUENCE public.loaixd2_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.loaixd2_id_seq;
       public          postgres    false    263            /           0    0    loaixd2_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.loaixd2_id_seq OWNED BY public.loaixd2.id;
          public          postgres    false    264            	           1259    16625    mucgia    TABLE     Z  CREATE TABLE public.mucgia (
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
    DROP TABLE public.mucgia;
       public         heap    postgres    false            
           1259    16631    mucgia_id_seq    SEQUENCE     �   CREATE SEQUENCE public.mucgia_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.mucgia_id_seq;
       public          postgres    false    265            0           0    0    mucgia_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.mucgia_id_seq OWNED BY public.mucgia.id;
          public          postgres    false    266                       1259    16632    myseq    SEQUENCE     n   CREATE SEQUENCE public.myseq
    START WITH 2
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    DROP SEQUENCE public.myseq;
       public          postgres    false                       1259    16633    nguon_nx    TABLE     �   CREATE TABLE public.nguon_nx (
    id integer DEFAULT nextval('public.myseq'::regclass) NOT NULL,
    ten text,
    status character varying(10),
    tructhuoc_id integer,
    code character varying(20)
);
    DROP TABLE public.nguon_nx;
       public         heap    postgres    false    267                       1259    16639    tructhuoc_loaiphieu    TABLE     y   CREATE TABLE public.tructhuoc_loaiphieu (
    id integer NOT NULL,
    tructhuoc_id integer,
    loaiphieu_id integer
);
 '   DROP TABLE public.tructhuoc_loaiphieu;
       public         heap    postgres    false                       1259    16642    nguonnx_loaiphieu_id_seq    SEQUENCE     �   ALTER TABLE public.tructhuoc_loaiphieu ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.nguonnx_loaiphieu_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    269                       1259    16643 
   nguonnx_pt    TABLE     d   CREATE TABLE public.nguonnx_pt (
    id bigint NOT NULL,
    nguonnx_id bigint,
    pt_id bigint
);
    DROP TABLE public.nguonnx_pt;
       public         heap    postgres    false                       1259    16646    nguonnx_pt_id_seq    SEQUENCE     �   ALTER TABLE public.nguonnx_pt ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.nguonnx_pt_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    271                       1259    16647    nguonnx_title    TABLE     �   CREATE TABLE public.nguonnx_title (
    id integer NOT NULL,
    nguonnx_id integer,
    title_id integer,
    group_id integer
);
 !   DROP TABLE public.nguonnx_title;
       public         heap    postgres    false                       1259    16650    nguonnx_title_id_seq    SEQUENCE     �   ALTER TABLE public.nguonnx_title ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.nguonnx_title_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    273                       1259    16651    nguonnx_tructhuoc    TABLE     �   CREATE TABLE public.nguonnx_tructhuoc (
    id integer NOT NULL,
    nguonnx_id integer NOT NULL,
    tructhuoc_id integer NOT NULL
);
 %   DROP TABLE public.nguonnx_tructhuoc;
       public         heap    postgres    false                       1259    16654    nguonnx_tructhuoc_id_seq    SEQUENCE     �   CREATE SEQUENCE public.nguonnx_tructhuoc_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.nguonnx_tructhuoc_id_seq;
       public          postgres    false    275            1           0    0    nguonnx_tructhuoc_id_seq    SEQUENCE OWNED BY     U   ALTER SEQUENCE public.nguonnx_tructhuoc_id_seq OWNED BY public.nguonnx_tructhuoc.id;
          public          postgres    false    276                       1259    16655    nhiemvu_tcn    TABLE     �   CREATE TABLE public.nhiemvu_tcn (
    id integer NOT NULL,
    nvu_id integer,
    tcn_id integer,
    phuongtien_id integer
);
    DROP TABLE public.nhiemvu_tcn;
       public         heap    postgres    false                       1259    16658    nhiemvu_tcn_id_seq    SEQUENCE     �   CREATE SEQUENCE public.nhiemvu_tcn_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.nhiemvu_tcn_id_seq;
       public          postgres    false    277            2           0    0    nhiemvu_tcn_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.nhiemvu_tcn_id_seq OWNED BY public.nhiemvu_tcn.id;
          public          postgres    false    278                       1259    16659    nxt_reporter    TABLE     D  CREATE TABLE public.nxt_reporter (
    petro_name text,
    petro_title_lv1 text,
    petro_title_lv2 text,
    title_lv1 text,
    title_lv2 text,
    title_lv3 text,
    title_lv4 text,
    petro_title_lv3 text,
    petro_title_lv4 text,
    xd_id integer NOT NULL,
    quarter_id integer NOT NULL,
    quantity bigint
);
     DROP TABLE public.nxt_reporter;
       public         heap    postgres    false                       1259    16664    petroleum_type    TABLE     o   CREATE TABLE public.petroleum_type (
    id integer NOT NULL,
    name text,
    type text,
    r_type text
);
 "   DROP TABLE public.petroleum_type;
       public         heap    postgres    false                       1259    16669    petroleum_type_id_seq    SEQUENCE     �   ALTER TABLE public.petroleum_type ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.petroleum_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    280                       1259    16670 
   phuongtien    TABLE       CREATE TABLE public.phuongtien (
    id integer NOT NULL,
    name text,
    quantity integer,
    status character varying(50),
    "timestamp" timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    nguonnx_id integer,
    loaiphuongtien_id integer
);
    DROP TABLE public.phuongtien;
       public         heap    postgres    false                       1259    16676    phuongtien_nhiemvu    TABLE     s   CREATE TABLE public.phuongtien_nhiemvu (
    id integer NOT NULL,
    phuongtien_id integer,
    nvu_id integer
);
 &   DROP TABLE public.phuongtien_nhiemvu;
       public         heap    postgres    false                       1259    16679    phuongtien_nhiemvu_id_seq    SEQUENCE     �   CREATE SEQUENCE public.phuongtien_nhiemvu_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 0   DROP SEQUENCE public.phuongtien_nhiemvu_id_seq;
       public          postgres    false    283            3           0    0    phuongtien_nhiemvu_id_seq    SEQUENCE OWNED BY     W   ALTER SEQUENCE public.phuongtien_nhiemvu_id_seq OWNED BY public.phuongtien_nhiemvu.id;
          public          postgres    false    284                       1259    16680    price_status    TABLE     M   CREATE TABLE public.price_status (
    id integer NOT NULL,
    name text
);
     DROP TABLE public.price_status;
       public         heap    postgres    false                       1259    16685    price_status_id_seq    SEQUENCE     �   ALTER TABLE public.price_status ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.price_status_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    285                       1259    16686    quarter    TABLE     �   CREATE TABLE public.quarter (
    id integer NOT NULL,
    name text,
    start_date date,
    end_date date,
    year text,
    index smallint DEFAULT 0
);
    DROP TABLE public.quarter;
       public         heap    postgres    false                        1259    16691    quarter_id_seq    SEQUENCE     �   CREATE SEQUENCE public.quarter_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.quarter_id_seq;
       public          postgres    false    287            4           0    0    quarter_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.quarter_id_seq OWNED BY public.quarter.id;
          public          postgres    false    288            !           1259    16692    so_cai_id_seq    SEQUENCE     �   CREATE SEQUENCE public.so_cai_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.so_cai_id_seq;
       public          postgres    false    251            5           0    0    so_cai_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.so_cai_id_seq OWNED BY public.ledger_details.id;
          public          postgres    false    289            "           1259    16693    splog_adfarm_seq    SEQUENCE     y   CREATE SEQUENCE public.splog_adfarm_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.splog_adfarm_seq;
       public          postgres    false            #           1259    16694    tab1    TABLE     S   CREATE TABLE public.tab1 (
    pkey integer,
    dur interval hour to second(0)
);
    DROP TABLE public.tab1;
       public         heap    postgres    false            $           1259    16697    team    TABLE     �   CREATE TABLE public.team (
    id integer NOT NULL,
    name text,
    team_code text,
    tt character varying(5),
    priority integer
);
    DROP TABLE public.team;
       public         heap    postgres    false            %           1259    16702    team_id_seq    SEQUENCE     �   ALTER TABLE public.team ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.team_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    292            &           1259    16703    titles_category    TABLE     e   CREATE TABLE public.titles_category (
    id integer NOT NULL,
    title_name text,
    type text
);
 #   DROP TABLE public.titles_category;
       public         heap    postgres    false            '           1259    16708    titles_category_id_seq    SEQUENCE     �   ALTER TABLE public.titles_category ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.titles_category_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    294            (           1259    16709 	   tructhuoc    TABLE     �   CREATE TABLE public.tructhuoc (
    id integer NOT NULL,
    name text,
    type text,
    "timestamp" timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    nhom_tructhuoc text,
    tennhom_tructhuoc text
);
    DROP TABLE public.tructhuoc;
       public         heap    postgres    false            )           1259    16715    tructhuoc_id_seq    SEQUENCE     �   CREATE SEQUENCE public.tructhuoc_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.tructhuoc_id_seq;
       public          postgres    false    296            6           0    0    tructhuoc_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.tructhuoc_id_seq OWNED BY public.tructhuoc.id;
          public          postgres    false    297            *           1259    16716    tructhuocf_id_seq    SEQUENCE     �   ALTER TABLE public.donvi_tructhuoc ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.tructhuocf_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    236            +           1259    16717    v1    VIEW     K   CREATE VIEW public.v1 AS
 SELECT sum(tab1.dur) AS sum
   FROM public.tab1;
    DROP VIEW public.v1;
       public          postgres    false    291            ,           1259    16721    vehicels_for_plan_id_seq    SEQUENCE     �   CREATE SEQUENCE public.vehicels_for_plan_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.vehicels_for_plan_id_seq;
       public          postgres    false    282            7           0    0    vehicels_for_plan_id_seq    SEQUENCE OWNED BY     N   ALTER SEQUENCE public.vehicels_for_plan_id_seq OWNED BY public.phuongtien.id;
          public          postgres    false    300            p           2604    16722    chitiet_nhiemvu id    DEFAULT     x   ALTER TABLE ONLY public.chitiet_nhiemvu ALTER COLUMN id SET DEFAULT nextval('public.chitiet_nhiemvu_id_seq'::regclass);
 A   ALTER TABLE public.chitiet_nhiemvu ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    229    228            z           2604    16723 	   dvi_nv id    DEFAULT     f   ALTER TABLE ONLY public.dvi_nv ALTER COLUMN id SET DEFAULT nextval('public.dvi_nv_id_seq'::regclass);
 8   ALTER TABLE public.dvi_nv ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    238    237            �           2604    16724    ledger_details id    DEFAULT     n   ALTER TABLE ONLY public.ledger_details ALTER COLUMN id SET DEFAULT nextval('public.so_cai_id_seq'::regclass);
 @   ALTER TABLE public.ledger_details ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    289    251            �           2604    16725    lichsuxnk id    DEFAULT     l   ALTER TABLE ONLY public.lichsuxnk ALTER COLUMN id SET DEFAULT nextval('public.lichsuxnk_id_seq'::regclass);
 ;   ALTER TABLE public.lichsuxnk ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    254    253            �           2604    16726 
   loaixd2 id    DEFAULT     h   ALTER TABLE ONLY public.loaixd2 ALTER COLUMN id SET DEFAULT nextval('public.loaixd2_id_seq'::regclass);
 9   ALTER TABLE public.loaixd2 ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    264    263            �           2604    16727 	   mucgia id    DEFAULT     f   ALTER TABLE ONLY public.mucgia ALTER COLUMN id SET DEFAULT nextval('public.mucgia_id_seq'::regclass);
 8   ALTER TABLE public.mucgia ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    266    265            �           2604    16728    nguonnx_tructhuoc id    DEFAULT     |   ALTER TABLE ONLY public.nguonnx_tructhuoc ALTER COLUMN id SET DEFAULT nextval('public.nguonnx_tructhuoc_id_seq'::regclass);
 C   ALTER TABLE public.nguonnx_tructhuoc ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    276    275            o           2604    16729 
   nhiemvu id    DEFAULT     q   ALTER TABLE ONLY public.nhiemvu ALTER COLUMN id SET DEFAULT nextval('public.chi_tiet_nhiemvu_id_seq'::regclass);
 9   ALTER TABLE public.nhiemvu ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    227    226            �           2604    16730    nhiemvu_tcn id    DEFAULT     p   ALTER TABLE ONLY public.nhiemvu_tcn ALTER COLUMN id SET DEFAULT nextval('public.nhiemvu_tcn_id_seq'::regclass);
 =   ALTER TABLE public.nhiemvu_tcn ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    278    277            �           2604    16731    phuongtien id    DEFAULT     u   ALTER TABLE ONLY public.phuongtien ALTER COLUMN id SET DEFAULT nextval('public.vehicels_for_plan_id_seq'::regclass);
 <   ALTER TABLE public.phuongtien ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    300    282            �           2604    16732    phuongtien_nhiemvu id    DEFAULT     ~   ALTER TABLE ONLY public.phuongtien_nhiemvu ALTER COLUMN id SET DEFAULT nextval('public.phuongtien_nhiemvu_id_seq'::regclass);
 D   ALTER TABLE public.phuongtien_nhiemvu ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    284    283            �           2604    16733 
   quarter id    DEFAULT     h   ALTER TABLE ONLY public.quarter ALTER COLUMN id SET DEFAULT nextval('public.quarter_id_seq'::regclass);
 9   ALTER TABLE public.quarter ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    288    287            �           2604    16734    tcn id    DEFAULT     d   ALTER TABLE ONLY public.tcn ALTER COLUMN id SET DEFAULT nextval('public.loai_nx_id_seq'::regclass);
 5   ALTER TABLE public.tcn ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    258    257            �           2604    16735    tructhuoc id    DEFAULT     l   ALTER TABLE ONLY public.tructhuoc ALTER COLUMN id SET DEFAULT nextval('public.tructhuoc_id_seq'::regclass);
 ;   ALTER TABLE public.tructhuoc ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    297    296                       0    25154    accounts 
   TABLE DATA           H   COPY public.accounts (id, username, surname, roles, passwd) FROM stdin;
    public          postgres    false    302   �t      �          0    16451    activated_active 
   TABLE DATA           ;   COPY public.activated_active (id, status_name) FROM stdin;
    public          postgres    false    218   u      �          0    16457    category 
   TABLE DATA           j   COPY public.category (id, header_lv1, header_lv2, header_lv3, type_title, tructhuoc_id, code) FROM stdin;
    public          postgres    false    220   Hu      �          0    16462    category_assignment 
   TABLE DATA           [   COPY public.category_assignment (title_1, title_2, title_3, title_4, id, code) FROM stdin;
    public          postgres    false    221   Rv      �          0    16481    chitiet_nhiemvu 
   TABLE DATA           B   COPY public.chitiet_nhiemvu (id, nhiemvu_id, nhiemvu) FROM stdin;
    public          postgres    false    228   w      �          0    16487 
   chitieu_pt 
   TABLE DATA           T   COPY public.chitieu_pt (id, dvi_id, ctnv_id, quy_id, md, tk, nl, pt_id) FROM stdin;
    public          postgres    false    230   �y      �          0    16496    chungloaixd 
   TABLE DATA           �   COPY public.chungloaixd (id, loai, chungloai, tinhchat, code, priority_1, priority_2, priority_3, tinhchat2, stt_index, code2) FROM stdin;
    public          postgres    false    232   �z      �          0    16506    dinhmuc 
   TABLE DATA           k   COPY public.dinhmuc (id, dm_tk_gio, dm_md_gio, dm_xm_km, dm_xm_gio, phuongtien_id, quarter_id) FROM stdin;
    public          postgres    false    234   �|      �          0    16510    donvi_tructhuoc 
   TABLE DATA           H   COPY public.donvi_tructhuoc (id, root_id, dvi_tructhuoc_id) FROM stdin;
    public          postgres    false    236   ~}      �          0    16513    dvi_nv 
   TABLE DATA           >   COPY public.dvi_nv (id, dv_id, nv_id, createtime) FROM stdin;
    public          postgres    false    237   �}      �          0    16519    group_title 
   TABLE DATA           G   COPY public.group_title (id, group_name, group_code, a, b) FROM stdin;
    public          postgres    false    239   �}      �          0    16525    hanmuc 
   TABLE DATA           a   COPY public.hanmuc (id, quarter_id, hanmuc_md, hanmuc_km, hanmuc_tk, soluong, pt_id) FROM stdin;
    public          postgres    false    241   Z~      �          0    16534    hanmuc_nhiemvu2 
   TABLE DATA           c   COPY public.hanmuc_nhiemvu2 (id, quarter_id, nhiemvu_id, dvi_id, diezel, daubay, xang) FROM stdin;
    public          postgres    false    243         �          0    16544    hanmuc_nhiemvu_taubay 
   TABLE DATA           k   COPY public.hanmuc_nhiemvu_taubay (id, dvi_xuat_id, pt_id, ctnv_id, quy_id, tk, md, nhienlieu) FROM stdin;
    public          postgres    false    245   z�      �          0    16553 
   inv_report 
   TABLE DATA           s   COPY public.inv_report (id, petroleum_id, quarter_id, inventory_id, report_header, quantity, price_id) FROM stdin;
    public          postgres    false    247   �      �          0    16556    inv_report_detail 
   TABLE DATA           �   COPY public.inv_report_detail (id, loaixd, soluong, title_lv1, title_lv2, title_lv3, title_lxd_lv1, title_lxd_lv2, title_lxd_lv3, xd_id, title_id, quarter_id) FROM stdin;
    public          postgres    false    248   $�      �          0    16423 	   inventory 
   TABLE DATA           �   COPY public.inventory (id, tdk_sscd, tdk_nvdx, status, petro_id, quarter_id, nhap_nvdx, nhap_sscd, xuat_nvdx, xuat_sscd, price, create_at) FROM stdin;
    public          postgres    false    214   A�      �          0    16563    ledger_details 
   TABLE DATA           $  COPY public.ledger_details (ma_xd, ten_xd, chung_loai, chat_luong, phai_xuat, nhiet_do_tt, ty_trong, he_so_vcf, thuc_xuat, don_gia, id, loaixd_id, phuongtien_id, ledger_id, thuc_xuat_tk, so_luong, thuc_nhap, phai_nhap, thanhtien, haohut_sl, nl_km, nl_gio, so_luong_px, sscd_nvdx) FROM stdin;
    public          postgres    false    251   ȃ      �          0    16582 
   ledger_map 
   TABLE DATA           f   COPY public.ledger_map (id, loaixd_id, header_id, soluong, mucgia_id, quarter_id, status) FROM stdin;
    public          postgres    false    252   U�      �          0    16433    ledgers 
   TABLE DATA           L  COPY public.ledgers (id, quarter_id, bill_id, amount, from_date, end_date, status, sl_tieuthu_md, sl_tieuthu_tk, dvi_nhan_id, dvi_xuat_id, loai_phieu, dvi_nhan, dvi_xuat, loaigiobay, nguoi_nhan, so_xe, lenh_so, nhiemvu, nhiemvu_id, so_km, tcn_id, "timestamp", giohd_md, giohd_tk, loainv, tructhuoc, lpt, lpt_2, version) FROM stdin;
    public          postgres    false    216   r�      �          0    16587 	   lichsuxnk 
   TABLE DATA           �   COPY public.lichsuxnk (id, ten_xd, loai_phieu, tontruoc, soluong, tonsau, createtime, "timestamp", type, so, dvn, dvx, chungloaixd, quy_id, gia) FROM stdin;
    public          postgres    false    253   �      �          0    16594    loai_nhiemvu 
   TABLE DATA           5   COPY public.loai_nhiemvu (id, task_name) FROM stdin;
    public          postgres    false    255   ��      �          0    16606 
   loai_phieu 
   TABLE DATA           .   COPY public.loai_phieu (id, type) FROM stdin;
    public          postgres    false    259   ��      �          0    16612    loai_phuongtien 
   TABLE DATA           >   COPY public.loai_phuongtien (id, type_name, type) FROM stdin;
    public          postgres    false    261    �      �          0    16618    loaixd2 
   TABLE DATA           Z   COPY public.loaixd2 (id, maxd, tenxd, status, "timestamp", petroleum_type_id) FROM stdin;
    public          postgres    false    263   }�      �          0    16625    mucgia 
   TABLE DATA           i   COPY public.mucgia (id, price, amount, quarter_id, item_id, status, "timestamp", nvdx, sscd) FROM stdin;
    public          postgres    false    265   k�      �          0    16633    nguon_nx 
   TABLE DATA           G   COPY public.nguon_nx (id, ten, status, tructhuoc_id, code) FROM stdin;
    public          postgres    false    268   ��                0    16643 
   nguonnx_pt 
   TABLE DATA           ;   COPY public.nguonnx_pt (id, nguonnx_id, pt_id) FROM stdin;
    public          postgres    false    271   (�                0    16647    nguonnx_title 
   TABLE DATA           K   COPY public.nguonnx_title (id, nguonnx_id, title_id, group_id) FROM stdin;
    public          postgres    false    273   p�                0    16651    nguonnx_tructhuoc 
   TABLE DATA           I   COPY public.nguonnx_tructhuoc (id, nguonnx_id, tructhuoc_id) FROM stdin;
    public          postgres    false    275   �      �          0    16475    nhiemvu 
   TABLE DATA           v   COPY public.nhiemvu (id, ten_nv, createtime, status, team_id, assignment_type_id, priority, priority_bc2) FROM stdin;
    public          postgres    false    226   :�      �          0    16467    nhiemvu_reporter 
   TABLE DATA           �   COPY public.nhiemvu_reporter (id, title_1, title_2, title_3, title_4, soluong, nhiemvu_id, phuongtien_id, ten_nv_1, ten_nv_2, ten_nv_3) FROM stdin;
    public          postgres    false    222   ��                0    16655    nhiemvu_tcn 
   TABLE DATA           H   COPY public.nhiemvu_tcn (id, nvu_id, tcn_id, phuongtien_id) FROM stdin;
    public          postgres    false    277   �      
          0    16659    nxt_reporter 
   TABLE DATA           �   COPY public.nxt_reporter (petro_name, petro_title_lv1, petro_title_lv2, title_lv1, title_lv2, title_lv3, title_lv4, petro_title_lv3, petro_title_lv4, xd_id, quarter_id, quantity) FROM stdin;
    public          postgres    false    279   6�                0    16664    petroleum_type 
   TABLE DATA           @   COPY public.petroleum_type (id, name, type, r_type) FROM stdin;
    public          postgres    false    280   S�                0    16670 
   phuongtien 
   TABLE DATA           l   COPY public.phuongtien (id, name, quantity, status, "timestamp", nguonnx_id, loaiphuongtien_id) FROM stdin;
    public          postgres    false    282   �                0    16676    phuongtien_nhiemvu 
   TABLE DATA           G   COPY public.phuongtien_nhiemvu (id, phuongtien_id, nvu_id) FROM stdin;
    public          postgres    false    283   ʗ                0    16680    price_status 
   TABLE DATA           0   COPY public.price_status (id, name) FROM stdin;
    public          postgres    false    285   �                0    16686    quarter 
   TABLE DATA           N   COPY public.quarter (id, name, start_date, end_date, year, index) FROM stdin;
    public          postgres    false    287   �                0    16694    tab1 
   TABLE DATA           )   COPY public.tab1 (pkey, dur) FROM stdin;
    public          postgres    false    291   j�      �          0    16600    tcn 
   TABLE DATA           2   COPY public.tcn (id, name, loaiphieu) FROM stdin;
    public          postgres    false    257   ��                0    16697    team 
   TABLE DATA           A   COPY public.team (id, name, team_code, tt, priority) FROM stdin;
    public          postgres    false    292   S�                0    16703    titles_category 
   TABLE DATA           ?   COPY public.titles_category (id, title_name, type) FROM stdin;
    public          postgres    false    294   ��                0    16709 	   tructhuoc 
   TABLE DATA           c   COPY public.tructhuoc (id, name, type, "timestamp", nhom_tructhuoc, tennhom_tructhuoc) FROM stdin;
    public          postgres    false    296   Ӛ                 0    16639    tructhuoc_loaiphieu 
   TABLE DATA           M   COPY public.tructhuoc_loaiphieu (id, tructhuoc_id, loaiphieu_id) FROM stdin;
    public          postgres    false    269   ��      8           0    0    Inventory_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public."Inventory_id_seq"', 1226, true);
          public          postgres    false    215            9           0    0    Ledgers_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public."Ledgers_id_seq"', 529, true);
          public          postgres    false    217            :           0    0    accounts_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.accounts_id_seq', 2, true);
          public          postgres    false    301            ;           0    0    activated_active_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.activated_active_id_seq', 3, true);
          public          postgres    false    219            <           0    0    category_assignment_id_seq    SEQUENCE SET     K   SELECT pg_catalog.setval('public.category_assignment_id_seq', 3209, true);
          public          postgres    false    223            =           0    0    category_assignment_id_seq1    SEQUENCE SET     J   SELECT pg_catalog.setval('public.category_assignment_id_seq1', 11, true);
          public          postgres    false    224            >           0    0    category_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.category_id_seq', 61, true);
          public          postgres    false    225            ?           0    0    chi_tiet_nhiemvu_id_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.chi_tiet_nhiemvu_id_seq', 57, true);
          public          postgres    false    227            @           0    0    chitiet_nhiemvu_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.chitiet_nhiemvu_id_seq', 49, true);
          public          postgres    false    229            A           0    0    chitieu_pt_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.chitieu_pt_id_seq', 34, true);
          public          postgres    false    231            B           0    0    chungloaixd_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.chungloaixd_id_seq', 13, true);
          public          postgres    false    233            C           0    0    dinhmuc_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.dinhmuc_id_seq', 101, true);
          public          postgres    false    235            D           0    0    dvi_nv_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.dvi_nv_id_seq', 127, true);
          public          postgres    false    238            E           0    0    group_title_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.group_title_id_seq', 2, true);
          public          postgres    false    240            F           0    0    hanmuc_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.hanmuc_id_seq', 70, true);
          public          postgres    false    242            G           0    0    hanmuc_nhiemvu2_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.hanmuc_nhiemvu2_id_seq', 33, true);
          public          postgres    false    244            H           0    0    hanmuc_nhiemvu_taubay_id_seq    SEQUENCE SET     K   SELECT pg_catalog.setval('public.hanmuc_nhiemvu_taubay_id_seq', 62, true);
          public          postgres    false    246            I           0    0    inv_report_detail_id_seq    SEQUENCE SET     J   SELECT pg_catalog.setval('public.inv_report_detail_id_seq', 67427, true);
          public          postgres    false    249            J           0    0    inv_report_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.inv_report_id_seq', 38359, true);
          public          postgres    false    250            K           0    0    lichsuxnk_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.lichsuxnk_id_seq', 1020, true);
          public          postgres    false    254            L           0    0    loai_nhiemvu_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.loai_nhiemvu_id_seq', 3, true);
          public          postgres    false    256            M           0    0    loai_nx_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.loai_nx_id_seq', 28, true);
          public          postgres    false    258            N           0    0    loai_phieu_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.loai_phieu_id_seq', 2, true);
          public          postgres    false    260            O           0    0    loai_phuongtien_id_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.loai_phuongtien_id_seq', 7, true);
          public          postgres    false    262            P           0    0    loaixd2_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.loaixd2_id_seq', 71, true);
          public          postgres    false    264            Q           0    0    mucgia_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.mucgia_id_seq', 3605, true);
          public          postgres    false    266            R           0    0    myseq    SEQUENCE SET     4   SELECT pg_catalog.setval('public.myseq', 89, true);
          public          postgres    false    267            S           0    0    nguonnx_loaiphieu_id_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.nguonnx_loaiphieu_id_seq', 20, true);
          public          postgres    false    270            T           0    0    nguonnx_pt_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.nguonnx_pt_id_seq', 9, true);
          public          postgres    false    272            U           0    0    nguonnx_title_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.nguonnx_title_id_seq', 41, true);
          public          postgres    false    274            V           0    0    nguonnx_tructhuoc_id_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('public.nguonnx_tructhuoc_id_seq', 108, true);
          public          postgres    false    276            W           0    0    nhiemvu_tcn_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.nhiemvu_tcn_id_seq', 1, false);
          public          postgres    false    278            X           0    0    petroleum_type_id_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.petroleum_type_id_seq', 13, true);
          public          postgres    false    281            Y           0    0    phuongtien_nhiemvu_id_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.phuongtien_nhiemvu_id_seq', 3, true);
          public          postgres    false    284            Z           0    0    price_status_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.price_status_id_seq', 1, false);
          public          postgres    false    286            [           0    0    quarter_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.quarter_id_seq', 20, true);
          public          postgres    false    288            \           0    0    so_cai_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.so_cai_id_seq', 1263, true);
          public          postgres    false    289            ]           0    0    splog_adfarm_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.splog_adfarm_seq', 1, false);
          public          postgres    false    290            ^           0    0    team_id_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.team_id_seq', 5, true);
          public          postgres    false    293            _           0    0    titles_category_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.titles_category_id_seq', 14, true);
          public          postgres    false    295            `           0    0    tructhuoc_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.tructhuoc_id_seq', 34, true);
          public          postgres    false    297            a           0    0    tructhuocf_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.tructhuocf_id_seq', 9, true);
          public          postgres    false    298            b           0    0    vehicels_for_plan_id_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.vehicels_for_plan_id_seq', 37, true);
          public          postgres    false    300            �           2606    16737    inventory Inventory_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.inventory
    ADD CONSTRAINT "Inventory_pkey" PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.inventory DROP CONSTRAINT "Inventory_pkey";
       public            postgres    false    214            �           2606    16739    ledgers Ledgers_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.ledgers
    ADD CONSTRAINT "Ledgers_pkey" PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.ledgers DROP CONSTRAINT "Ledgers_pkey";
       public            postgres    false    216            *           2606    25160    accounts accounts_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT accounts_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.accounts DROP CONSTRAINT accounts_pkey;
       public            postgres    false    302            ,           2606    25162    accounts accounts_username_key 
   CONSTRAINT     ]   ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT accounts_username_key UNIQUE (username);
 H   ALTER TABLE ONLY public.accounts DROP CONSTRAINT accounts_username_key;
       public            postgres    false    302            �           2606    16741 &   activated_active activated_active_pkey 
   CONSTRAINT     d   ALTER TABLE ONLY public.activated_active
    ADD CONSTRAINT activated_active_pkey PRIMARY KEY (id);
 P   ALTER TABLE ONLY public.activated_active DROP CONSTRAINT activated_active_pkey;
       public            postgres    false    218            �           2606    16743 0   category_assignment category_assignment_code_key 
   CONSTRAINT     k   ALTER TABLE ONLY public.category_assignment
    ADD CONSTRAINT category_assignment_code_key UNIQUE (code);
 Z   ALTER TABLE ONLY public.category_assignment DROP CONSTRAINT category_assignment_code_key;
       public            postgres    false    221            �           2606    16745 )   nhiemvu_reporter category_assignment_pkey 
   CONSTRAINT     g   ALTER TABLE ONLY public.nhiemvu_reporter
    ADD CONSTRAINT category_assignment_pkey PRIMARY KEY (id);
 S   ALTER TABLE ONLY public.nhiemvu_reporter DROP CONSTRAINT category_assignment_pkey;
       public            postgres    false    222            �           2606    16747 -   category_assignment category_assignment_pkey1 
   CONSTRAINT     k   ALTER TABLE ONLY public.category_assignment
    ADD CONSTRAINT category_assignment_pkey1 PRIMARY KEY (id);
 W   ALTER TABLE ONLY public.category_assignment DROP CONSTRAINT category_assignment_pkey1;
       public            postgres    false    221            �           2606    16749    category category_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.category DROP CONSTRAINT category_pkey;
       public            postgres    false    220            �           2606    16751 -   category category_type_title_tructhuoc_id_key 
   CONSTRAINT     |   ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_type_title_tructhuoc_id_key UNIQUE (type_title, tructhuoc_id);
 W   ALTER TABLE ONLY public.category DROP CONSTRAINT category_type_title_tructhuoc_id_key;
       public            postgres    false    220    220            �           2606    16753    nhiemvu chi_tiet_nhiemvu_pkey 
   CONSTRAINT     [   ALTER TABLE ONLY public.nhiemvu
    ADD CONSTRAINT chi_tiet_nhiemvu_pkey PRIMARY KEY (id);
 G   ALTER TABLE ONLY public.nhiemvu DROP CONSTRAINT chi_tiet_nhiemvu_pkey;
       public            postgres    false    226            �           2606    16755 $   chitiet_nhiemvu chitiet_nhiemvu_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public.chitiet_nhiemvu
    ADD CONSTRAINT chitiet_nhiemvu_pkey PRIMARY KEY (id);
 N   ALTER TABLE ONLY public.chitiet_nhiemvu DROP CONSTRAINT chitiet_nhiemvu_pkey;
       public            postgres    false    228            �           2606    16757    chitieu_pt chitieu_pt_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.chitieu_pt
    ADD CONSTRAINT chitieu_pt_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.chitieu_pt DROP CONSTRAINT chitieu_pt_pkey;
       public            postgres    false    230            �           2606    16759     chungloaixd chungloaixd_code_key 
   CONSTRAINT     [   ALTER TABLE ONLY public.chungloaixd
    ADD CONSTRAINT chungloaixd_code_key UNIQUE (code);
 J   ALTER TABLE ONLY public.chungloaixd DROP CONSTRAINT chungloaixd_code_key;
       public            postgres    false    232            �           2606    16761    chungloaixd chungloaixd_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.chungloaixd
    ADD CONSTRAINT chungloaixd_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.chungloaixd DROP CONSTRAINT chungloaixd_pkey;
       public            postgres    false    232            �           2606    16763 ,   dinhmuc dinhmuc_phuongtien_id_quarter_id_key 
   CONSTRAINT     |   ALTER TABLE ONLY public.dinhmuc
    ADD CONSTRAINT dinhmuc_phuongtien_id_quarter_id_key UNIQUE (phuongtien_id, quarter_id);
 V   ALTER TABLE ONLY public.dinhmuc DROP CONSTRAINT dinhmuc_phuongtien_id_quarter_id_key;
       public            postgres    false    234    234            �           2606    16765    dinhmuc dinhmuc_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.dinhmuc
    ADD CONSTRAINT dinhmuc_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.dinhmuc DROP CONSTRAINT dinhmuc_pkey;
       public            postgres    false    234            �           2606    16767    dvi_nv dvi_nv_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.dvi_nv
    ADD CONSTRAINT dvi_nv_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.dvi_nv DROP CONSTRAINT dvi_nv_pkey;
       public            postgres    false    237            �           2606    16769    group_title group_title_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.group_title
    ADD CONSTRAINT group_title_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.group_title DROP CONSTRAINT group_title_pkey;
       public            postgres    false    239            �           2606    16771 $   hanmuc_nhiemvu2 hanmuc_nhiemvu2_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public.hanmuc_nhiemvu2
    ADD CONSTRAINT hanmuc_nhiemvu2_pkey PRIMARY KEY (id);
 N   ALTER TABLE ONLY public.hanmuc_nhiemvu2 DROP CONSTRAINT hanmuc_nhiemvu2_pkey;
       public            postgres    false    243            �           2606    16775 0   hanmuc_nhiemvu_taubay hanmuc_nhiemvu_taubay_pkey 
   CONSTRAINT     n   ALTER TABLE ONLY public.hanmuc_nhiemvu_taubay
    ADD CONSTRAINT hanmuc_nhiemvu_taubay_pkey PRIMARY KEY (id);
 Z   ALTER TABLE ONLY public.hanmuc_nhiemvu_taubay DROP CONSTRAINT hanmuc_nhiemvu_taubay_pkey;
       public            postgres    false    245            �           2606    16777 	   mucgia id 
   CONSTRAINT     G   ALTER TABLE ONLY public.mucgia
    ADD CONSTRAINT id PRIMARY KEY (id);
 3   ALTER TABLE ONLY public.mucgia DROP CONSTRAINT id;
       public            postgres    false    265            �           2606    16779 (   inv_report_detail inv_report_detail_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.inv_report_detail
    ADD CONSTRAINT inv_report_detail_pkey PRIMARY KEY (id);
 R   ALTER TABLE ONLY public.inv_report_detail DROP CONSTRAINT inv_report_detail_pkey;
       public            postgres    false    248            �           2606    16781 A   inv_report_detail inv_report_detail_xd_id_quarter_id_title_id_key 
   CONSTRAINT     �   ALTER TABLE ONLY public.inv_report_detail
    ADD CONSTRAINT inv_report_detail_xd_id_quarter_id_title_id_key UNIQUE (xd_id, quarter_id, title_id);
 k   ALTER TABLE ONLY public.inv_report_detail DROP CONSTRAINT inv_report_detail_xd_id_quarter_id_title_id_key;
       public            postgres    false    248    248    248            �           2606    16783 ?   inv_report inv_report_petroleum_id_quarter_id_report_header_key 
   CONSTRAINT     �   ALTER TABLE ONLY public.inv_report
    ADD CONSTRAINT inv_report_petroleum_id_quarter_id_report_header_key UNIQUE (petroleum_id, quarter_id, report_header);
 i   ALTER TABLE ONLY public.inv_report DROP CONSTRAINT inv_report_petroleum_id_quarter_id_report_header_key;
       public            postgres    false    247    247    247            �           2606    16785    inv_report inv_report_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.inv_report
    ADD CONSTRAINT inv_report_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.inv_report DROP CONSTRAINT inv_report_pkey;
       public            postgres    false    247            �           2606    25182 1   inventory inventory_petro_id_quarter_id_price_key 
   CONSTRAINT     �   ALTER TABLE ONLY public.inventory
    ADD CONSTRAINT inventory_petro_id_quarter_id_price_key UNIQUE (petro_id, quarter_id, price);
 [   ALTER TABLE ONLY public.inventory DROP CONSTRAINT inventory_petro_id_quarter_id_price_key;
       public            postgres    false    214    214    214            �           2606    16789    ledger_map ledger_map_pkey 
   CONSTRAINT     z   ALTER TABLE ONLY public.ledger_map
    ADD CONSTRAINT ledger_map_pkey PRIMARY KEY (id, loaixd_id, header_id, quarter_id);
 D   ALTER TABLE ONLY public.ledger_map DROP CONSTRAINT ledger_map_pkey;
       public            postgres    false    252    252    252    252            �           2606    25170 1   ledgers ledgers_quarter_id_bill_id_loai_phieu_key 
   CONSTRAINT     �   ALTER TABLE ONLY public.ledgers
    ADD CONSTRAINT ledgers_quarter_id_bill_id_loai_phieu_key UNIQUE (quarter_id, bill_id, loai_phieu);
 [   ALTER TABLE ONLY public.ledgers DROP CONSTRAINT ledgers_quarter_id_bill_id_loai_phieu_key;
       public            postgres    false    216    216    216            �           2606    16793    lichsuxnk lichsuxnk_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.lichsuxnk
    ADD CONSTRAINT lichsuxnk_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.lichsuxnk DROP CONSTRAINT lichsuxnk_pkey;
       public            postgres    false    253            �           2606    16795    loai_nhiemvu loai_nhiemvu_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.loai_nhiemvu
    ADD CONSTRAINT loai_nhiemvu_pkey PRIMARY KEY (id);
 H   ALTER TABLE ONLY public.loai_nhiemvu DROP CONSTRAINT loai_nhiemvu_pkey;
       public            postgres    false    255            �           2606    25168 '   loai_nhiemvu loai_nhiemvu_task_name_key 
   CONSTRAINT     g   ALTER TABLE ONLY public.loai_nhiemvu
    ADD CONSTRAINT loai_nhiemvu_task_name_key UNIQUE (task_name);
 Q   ALTER TABLE ONLY public.loai_nhiemvu DROP CONSTRAINT loai_nhiemvu_task_name_key;
       public            postgres    false    255            �           2606    16797    loai_phieu loai_phieu_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.loai_phieu
    ADD CONSTRAINT loai_phieu_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.loai_phieu DROP CONSTRAINT loai_phieu_pkey;
       public            postgres    false    259            �           2606    16799 $   loai_phuongtien loai_phuongtien_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public.loai_phuongtien
    ADD CONSTRAINT loai_phuongtien_pkey PRIMARY KEY (id);
 N   ALTER TABLE ONLY public.loai_phuongtien DROP CONSTRAINT loai_phuongtien_pkey;
       public            postgres    false    261            �           2606    16801    loaixd2 loaixd2_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.loaixd2
    ADD CONSTRAINT loaixd2_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.loaixd2 DROP CONSTRAINT loaixd2_pkey;
       public            postgres    false    263            �           2606    25166 *   mucgia mucgia_price_quarter_id_item_id_key 
   CONSTRAINT     {   ALTER TABLE ONLY public.mucgia
    ADD CONSTRAINT mucgia_price_quarter_id_item_id_key UNIQUE (price, quarter_id, item_id);
 T   ALTER TABLE ONLY public.mucgia DROP CONSTRAINT mucgia_price_quarter_id_item_id_key;
       public            postgres    false    265    265    265            �           2606    16805    nguon_nx nguon_nx_code_key 
   CONSTRAINT     U   ALTER TABLE ONLY public.nguon_nx
    ADD CONSTRAINT nguon_nx_code_key UNIQUE (code);
 D   ALTER TABLE ONLY public.nguon_nx DROP CONSTRAINT nguon_nx_code_key;
       public            postgres    false    268            �           2606    16807    nguon_nx nguon_nx_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.nguon_nx
    ADD CONSTRAINT nguon_nx_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.nguon_nx DROP CONSTRAINT nguon_nx_pkey;
       public            postgres    false    268                       2606    16809 A   tructhuoc_loaiphieu nguonnx_loaiphieu_nguonnx_id_loaiphieu_id_key 
   CONSTRAINT     �   ALTER TABLE ONLY public.tructhuoc_loaiphieu
    ADD CONSTRAINT nguonnx_loaiphieu_nguonnx_id_loaiphieu_id_key UNIQUE (tructhuoc_id, loaiphieu_id);
 k   ALTER TABLE ONLY public.tructhuoc_loaiphieu DROP CONSTRAINT nguonnx_loaiphieu_nguonnx_id_loaiphieu_id_key;
       public            postgres    false    269    269                       2606    16811 *   tructhuoc_loaiphieu nguonnx_loaiphieu_pkey 
   CONSTRAINT     h   ALTER TABLE ONLY public.tructhuoc_loaiphieu
    ADD CONSTRAINT nguonnx_loaiphieu_pkey PRIMARY KEY (id);
 T   ALTER TABLE ONLY public.tructhuoc_loaiphieu DROP CONSTRAINT nguonnx_loaiphieu_pkey;
       public            postgres    false    269                       2606    16813    nguonnx_pt nguonnx_pt_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.nguonnx_pt
    ADD CONSTRAINT nguonnx_pt_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.nguonnx_pt DROP CONSTRAINT nguonnx_pt_pkey;
       public            postgres    false    271                       2606    16815 3   nguonnx_title nguonnx_title_nguonnx_id_group_id_key 
   CONSTRAINT     ~   ALTER TABLE ONLY public.nguonnx_title
    ADD CONSTRAINT nguonnx_title_nguonnx_id_group_id_key UNIQUE (nguonnx_id, group_id);
 ]   ALTER TABLE ONLY public.nguonnx_title DROP CONSTRAINT nguonnx_title_nguonnx_id_group_id_key;
       public            postgres    false    273    273            
           2606    16817     nguonnx_title nguonnx_title_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.nguonnx_title
    ADD CONSTRAINT nguonnx_title_pkey PRIMARY KEY (id);
 J   ALTER TABLE ONLY public.nguonnx_title DROP CONSTRAINT nguonnx_title_pkey;
       public            postgres    false    273                       2606    16819 ?   nguonnx_tructhuoc nguonnx_tructhuoc_nguonnx_id_tructhuoc_id_key 
   CONSTRAINT     �   ALTER TABLE ONLY public.nguonnx_tructhuoc
    ADD CONSTRAINT nguonnx_tructhuoc_nguonnx_id_tructhuoc_id_key UNIQUE (nguonnx_id, tructhuoc_id);
 i   ALTER TABLE ONLY public.nguonnx_tructhuoc DROP CONSTRAINT nguonnx_tructhuoc_nguonnx_id_tructhuoc_id_key;
       public            postgres    false    275    275                       2606    16821    nhiemvu_tcn nhiemvu_tcn_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.nhiemvu_tcn
    ADD CONSTRAINT nhiemvu_tcn_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.nhiemvu_tcn DROP CONSTRAINT nhiemvu_tcn_pkey;
       public            postgres    false    277                       2606    16823    nxt_reporter nxt_reporter_pkey 
   CONSTRAINT     k   ALTER TABLE ONLY public.nxt_reporter
    ADD CONSTRAINT nxt_reporter_pkey PRIMARY KEY (quarter_id, xd_id);
 H   ALTER TABLE ONLY public.nxt_reporter DROP CONSTRAINT nxt_reporter_pkey;
       public            postgres    false    279    279                       2606    16825 "   petroleum_type petroleum_type_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.petroleum_type
    ADD CONSTRAINT petroleum_type_pkey PRIMARY KEY (id);
 L   ALTER TABLE ONLY public.petroleum_type DROP CONSTRAINT petroleum_type_pkey;
       public            postgres    false    280                       2606    25192    phuongtien phuongtien_name_key 
   CONSTRAINT     Y   ALTER TABLE ONLY public.phuongtien
    ADD CONSTRAINT phuongtien_name_key UNIQUE (name);
 H   ALTER TABLE ONLY public.phuongtien DROP CONSTRAINT phuongtien_name_key;
       public            postgres    false    282                       2606    16827 *   phuongtien_nhiemvu phuongtien_nhiemvu_pkey 
   CONSTRAINT     h   ALTER TABLE ONLY public.phuongtien_nhiemvu
    ADD CONSTRAINT phuongtien_nhiemvu_pkey PRIMARY KEY (id);
 T   ALTER TABLE ONLY public.phuongtien_nhiemvu DROP CONSTRAINT phuongtien_nhiemvu_pkey;
       public            postgres    false    283                       2606    16829    phuongtien phuongtien_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.phuongtien
    ADD CONSTRAINT phuongtien_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.phuongtien DROP CONSTRAINT phuongtien_pkey;
       public            postgres    false    282                       2606    16831    price_status price_status_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.price_status
    ADD CONSTRAINT price_status_pkey PRIMARY KEY (id);
 H   ALTER TABLE ONLY public.price_status DROP CONSTRAINT price_status_pkey;
       public            postgres    false    285                       2606    25175    quarter quarter_index_year_key 
   CONSTRAINT     `   ALTER TABLE ONLY public.quarter
    ADD CONSTRAINT quarter_index_year_key UNIQUE (index, year);
 H   ALTER TABLE ONLY public.quarter DROP CONSTRAINT quarter_index_year_key;
       public            postgres    false    287    287                       2606    16833    quarter quarter_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.quarter
    ADD CONSTRAINT quarter_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.quarter DROP CONSTRAINT quarter_pkey;
       public            postgres    false    287            �           2606    16835    ledger_details so_cai_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.ledger_details
    ADD CONSTRAINT so_cai_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.ledger_details DROP CONSTRAINT so_cai_pkey;
       public            postgres    false    251            �           2606    16837    tcn tcn_name_key 
   CONSTRAINT     K   ALTER TABLE ONLY public.tcn
    ADD CONSTRAINT tcn_name_key UNIQUE (name);
 :   ALTER TABLE ONLY public.tcn DROP CONSTRAINT tcn_name_key;
       public            postgres    false    257            �           2606    16839    tcn tcn_pkey 
   CONSTRAINT     J   ALTER TABLE ONLY public.tcn
    ADD CONSTRAINT tcn_pkey PRIMARY KEY (id);
 6   ALTER TABLE ONLY public.tcn DROP CONSTRAINT tcn_pkey;
       public            postgres    false    257                        2606    16841    team team_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY public.team
    ADD CONSTRAINT team_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.team DROP CONSTRAINT team_pkey;
       public            postgres    false    292            "           2606    16843    team team_team_code_key 
   CONSTRAINT     W   ALTER TABLE ONLY public.team
    ADD CONSTRAINT team_team_code_key UNIQUE (team_code);
 A   ALTER TABLE ONLY public.team DROP CONSTRAINT team_team_code_key;
       public            postgres    false    292                        2606    16845    nguon_nx ten_uni 
   CONSTRAINT     J   ALTER TABLE ONLY public.nguon_nx
    ADD CONSTRAINT ten_uni UNIQUE (ten);
 :   ALTER TABLE ONLY public.nguon_nx DROP CONSTRAINT ten_uni;
       public            postgres    false    268            $           2606    16847 $   titles_category titles_category_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public.titles_category
    ADD CONSTRAINT titles_category_pkey PRIMARY KEY (id);
 N   ALTER TABLE ONLY public.titles_category DROP CONSTRAINT titles_category_pkey;
       public            postgres    false    294            &           2606    16849    tructhuoc tructhuoc_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.tructhuoc
    ADD CONSTRAINT tructhuoc_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.tructhuoc DROP CONSTRAINT tructhuoc_pkey;
       public            postgres    false    296            (           2606    16851    tructhuoc tructhuoc_type_key 
   CONSTRAINT     W   ALTER TABLE ONLY public.tructhuoc
    ADD CONSTRAINT tructhuoc_type_key UNIQUE (type);
 F   ALTER TABLE ONLY public.tructhuoc DROP CONSTRAINT tructhuoc_type_key;
       public            postgres    false    296            �           2606    16853    donvi_tructhuoc tructhuocf_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.donvi_tructhuoc
    ADD CONSTRAINT tructhuocf_pkey PRIMARY KEY (id);
 I   ALTER TABLE ONLY public.donvi_tructhuoc DROP CONSTRAINT tructhuocf_pkey;
       public            postgres    false    236            1           2606    16854 "   dinhmuc dinhmuc_phuongtien_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.dinhmuc
    ADD CONSTRAINT dinhmuc_phuongtien_id_fkey FOREIGN KEY (phuongtien_id) REFERENCES public.phuongtien(id) NOT VALID;
 L   ALTER TABLE ONLY public.dinhmuc DROP CONSTRAINT dinhmuc_phuongtien_id_fkey;
       public          postgres    false    3606    282    234            2           2606    16859    dvi_nv dvi_fkey    FK CONSTRAINT     y   ALTER TABLE ONLY public.dvi_nv
    ADD CONSTRAINT dvi_fkey FOREIGN KEY (dv_id) REFERENCES public.nguon_nx(id) NOT VALID;
 9   ALTER TABLE ONLY public.dvi_nv DROP CONSTRAINT dvi_fkey;
       public          postgres    false    3582    268    237            -           2606    16864 !   inventory inventory_petro_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.inventory
    ADD CONSTRAINT inventory_petro_id_fkey FOREIGN KEY (petro_id) REFERENCES public.loaixd2(id) NOT VALID;
 K   ALTER TABLE ONLY public.inventory DROP CONSTRAINT inventory_petro_id_fkey;
       public          postgres    false    214    3574    263            .           2606    16869 #   inventory inventory_quarter_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.inventory
    ADD CONSTRAINT inventory_quarter_id_fkey FOREIGN KEY (quarter_id) REFERENCES public.quarter(id) NOT VALID;
 M   ALTER TABLE ONLY public.inventory DROP CONSTRAINT inventory_quarter_id_fkey;
       public          postgres    false    287    214    3614            4           2606    16874    ledger_details ledger_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.ledger_details
    ADD CONSTRAINT ledger_fkey FOREIGN KEY (ledger_id) REFERENCES public.ledgers(id) NOT VALID;
 D   ALTER TABLE ONLY public.ledger_details DROP CONSTRAINT ledger_fkey;
       public          postgres    false    216    3508    251            6           2606    16879 &   loaixd2 loaixd2_petroleum_type_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.loaixd2
    ADD CONSTRAINT loaixd2_petroleum_type_id_fkey FOREIGN KEY (petroleum_type_id) REFERENCES public.petroleum_type(id) NOT VALID;
 P   ALTER TABLE ONLY public.loaixd2 DROP CONSTRAINT loaixd2_petroleum_type_id_fkey;
       public          postgres    false    263    3602    280            5           2606    16884    ledger_details loaixd_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.ledger_details
    ADD CONSTRAINT loaixd_fkey FOREIGN KEY (loaixd_id) REFERENCES public.loaixd2(id) NOT VALID;
 D   ALTER TABLE ONLY public.ledger_details DROP CONSTRAINT loaixd_fkey;
       public          postgres    false    251    3574    263            :           2606    16894    phuongtien nguonnx_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.phuongtien
    ADD CONSTRAINT nguonnx_fkey FOREIGN KEY (nguonnx_id) REFERENCES public.nguon_nx(id) NOT VALID;
 A   ALTER TABLE ONLY public.phuongtien DROP CONSTRAINT nguonnx_fkey;
       public          postgres    false    282    3582    268            7           2606    16899 )   nguonnx_title nguonnx_title_group_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.nguonnx_title
    ADD CONSTRAINT nguonnx_title_group_id_fkey FOREIGN KEY (group_id) REFERENCES public.group_title(id) NOT VALID;
 S   ALTER TABLE ONLY public.nguonnx_title DROP CONSTRAINT nguonnx_title_group_id_fkey;
       public          postgres    false    273    3542    239            8           2606    16904    nhiemvu_tcn nhiemvu_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.nhiemvu_tcn
    ADD CONSTRAINT nhiemvu_fkey FOREIGN KEY (nvu_id) REFERENCES public.nhiemvu(id) NOT VALID;
 B   ALTER TABLE ONLY public.nhiemvu_tcn DROP CONSTRAINT nhiemvu_fkey;
       public          postgres    false    277    3524    226            0           2606    16909    chitiet_nhiemvu nhiemvu_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.chitiet_nhiemvu
    ADD CONSTRAINT nhiemvu_id FOREIGN KEY (nhiemvu_id) REFERENCES public.nhiemvu(id) NOT VALID;
 D   ALTER TABLE ONLY public.chitiet_nhiemvu DROP CONSTRAINT nhiemvu_id;
       public          postgres    false    228    3524    226            ;           2606    16914    phuongtien_nhiemvu nhiemvu_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.phuongtien_nhiemvu
    ADD CONSTRAINT nhiemvu_id FOREIGN KEY (nvu_id) REFERENCES public.nhiemvu(id) NOT VALID;
 G   ALTER TABLE ONLY public.phuongtien_nhiemvu DROP CONSTRAINT nhiemvu_id;
       public          postgres    false    283    3524    226            /           2606    16919    nhiemvu nhiemvu_team_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.nhiemvu
    ADD CONSTRAINT nhiemvu_team_id_fkey FOREIGN KEY (team_id) REFERENCES public.team(id) NOT VALID;
 F   ALTER TABLE ONLY public.nhiemvu DROP CONSTRAINT nhiemvu_team_id_fkey;
       public          postgres    false    292    226    3616            3           2606    16924    dvi_nv nvu_fkey    FK CONSTRAINT     x   ALTER TABLE ONLY public.dvi_nv
    ADD CONSTRAINT nvu_fkey FOREIGN KEY (nv_id) REFERENCES public.nhiemvu(id) NOT VALID;
 9   ALTER TABLE ONLY public.dvi_nv DROP CONSTRAINT nvu_fkey;
       public          postgres    false    237    3524    226            <           2606    16929     phuongtien_nhiemvu phuongtien_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.phuongtien_nhiemvu
    ADD CONSTRAINT phuongtien_id FOREIGN KEY (phuongtien_id) REFERENCES public.phuongtien(id) NOT VALID;
 J   ALTER TABLE ONLY public.phuongtien_nhiemvu DROP CONSTRAINT phuongtien_id;
       public          postgres    false    282    3606    283            9           2606    16939    nhiemvu_tcn tcn_fkey    FK CONSTRAINT     z   ALTER TABLE ONLY public.nhiemvu_tcn
    ADD CONSTRAINT tcn_fkey FOREIGN KEY (tcn_id) REFERENCES public.tcn(id) NOT VALID;
 >   ALTER TABLE ONLY public.nhiemvu_tcn DROP CONSTRAINT tcn_fkey;
       public          postgres    false    257    277    3568                5   x�3�L�H�� ��y����A���FFF&\F�%�`��*���� �w      �   %   x�3���wt�s�2�2�9�C<�ܹb���� �V�      �   �   x�uQK
�0]ON1'��~�K�� �
�p�E[[]x�t���eO���6ATH23����t�g	N|R�|�p���X�!�����C���y?@4�Ҋ\Y��0���W>ӓ���f���ʊs����@+kK��XqYタ�RM�
�k����h�]�bKhm��x/C'g��q�a3條�k��P�������jIٮ�Iu�euf0�瘲�#O��%�N�򺮾R࿾��Y��qK��      �   �   x����x�{i	����Ghg�rzxp�ed^�����pw{)��T����=2����?��1*l�������&a�����;2��.�U~>!0{���6���[�ґe�!��z=\�r&@&�>��)H�����@qT�X�����24 iC�Ar��!�! �=... |�[:      �   �  x�eSMk�@=���=�`�%�������~ L�8
X���Ē���!�:�=�P�BI�4�\*zX5�c�I߮	q(�;�oޛ7��:4�gQXoX�.Q� _���ԧ�9M'�p��n�;v��(�E,�$5���������_J��H�o�#S}3]��r������ใ#�����@��|!:���(�D�1奚6D�{҅s.I:c�M�K�H􅚊���US�:6��g&bS}K�M9~�zA�Ybʫ�}h�x[]�"1��QA�b��4L&)�۶3G �����bm��u����穩�@e�-�mW��j
��G�:2x�}!���S}E+~�@�Ce�������9���'��X�y����}a�A�e1��V��T��)V)��E����9�����TW�M>� ���S|B�!�:��h}��A@��/3N�V%0��$Y�W���UX@��M��B8�X�3�C�L��1����\��ܢ��{t��S��4�G��Cq��@{��g"��a�"@ܪͱe��A��#`�%8;X����*�� �ggn���e@���^۷[gN�b<YJth���<ߑ?�_b�����%S���>K�\;��T&��.�5�v�sq|�1�Gx�O��"�s>�
NX�	�����M���X9�"      �      x�m�ˍ!D�&���@ǲ�Ǳ60�Q� ^�)�A�E��}�Z�܅I	%'RFĥ�T��=���
�xa[�i�l����s����o.sG�6ҿ�#n+�R���b��y�=������[@����De�jN�	��f�PZ�W����+{v����v��N)�=rIM0�	�����d�1�	��u	$��oD�
�.���%���� �x�q	x`�yy��:G���&�g�D�Ao�d'_�M9iq�E�+\���j��+��Ok�h���      �   �  x����N�0���S� v��l�84&���^�����o&$&�q���X�J7�i�������0 ԟOQkz}@t���u���_. gD�w��"-� *BTM���P����tF�Gg5+xs�y*�pסVv�d:Tc�����P�@n��s�:f�[�B�n�a)V6��>�x�#;�v�ئ��45���2�JR*[[7!X�g]�f�h�D�s�X��Uʩ��5��!�=x���D<�_���H)%�X�� V@w����/RQW3V�b�Ǽ��Y
H�*�iࡡM��	���}�9�7�3n�
�R��f�!>��S��~�O�����ٲ������,I{	b��V1��1~�y*��p�`f�D4�J��cjE"%տLQt`5Z�x�~�nDU��2e�I���~@�������?���"��],�      �   �   x�M�� C�u�����r��qК�_Olgq&%MG� rR3�LK�$���RtK��FX�)q���tG�\�O���g7%Dm�cc��tZ+\��;� c0�"� �P+�p���A�P���[6�F��o[;��RZ�M�i��$y\�=oǮ��G�1�$�x�6µ!��#��6Mo��'�~z�?2IF      �   5   x���  ��[�Q�^�s�&��%&�`��Eb�Ep��bQ?�ہ`<	�      �      x������ � �      �   j   x�3�LJ�/�<��T�$���@25_!/#����\�2�gII|^�����������P��_��]k*J�ZZ�P�p��<�Yy%�FFV&f��fV�\1z\\\ 3�%      �   �   x�m�Kr� е�0)���g����f�*VOEc�&�Z�8Sj>�.ឤ�S����݀HڊC��0��i�ڻ`��1�9�2̻
ꈦ
��ˮtAwSe��Zw�u�jx��VOHt�nXWS����舎���%qݐ�7t� x���
�S3�P�@�stx��<S8�����dS      �   L  x�u�Kn1D��a"����#Pt��D�5ůF[��	<��z����-��씵eή?�,V>z�&$XVM%(�+�T��m-a���P�J蚊ɦ����KEcw�z�lS-�\�Sp�Jʟ([�#ʖz� Z��V�%����=B��v��#dvZ��Qf��n�T���_��ܺa����Ӹy�"$��S��"������=5u�C`1��������jd$���hrD�)#0�(`#0�=���F`�,��1�� 6~e1Bc�O%0�G]X�����-���y�H.h��ae�rr�~4��rb�{���z]J8*D�������}%      �   }  x�}�I�� E�p��G�:K��E�D�
�,�yc;�"ɒZ�I(1Ӈ(�|s1m�����DJ�d\{��`H�zD�N�ɾ"�/b���v�����x�vQ?P��5w�-�£��Q���S`(^�wC�1�c�������+o�ø/9eF�!�%w�2�Yc��Q|𙠈�B@�GÖ��0��Q�8}�ƛW��;e�@۞PV���=-e��Y����X�v2fr���x��<�>?��
���B�P"�B��1������ �W8�8�!.�BeB�k���p�!̼����nH�A���>~
��T�A礄�A��%��	���t���]�����Bǿ�ұ���v<*�r���E��r�_�67r      �      x������ � �      �      x������ � �      �   w  x����n�@C��+�	�:Iw��)��X�����Y�L�6� II���m����������v���.�u��6c��;�^Z[���c��?���1
cV�V}���1D"&���5�11��8�1Ѻ�ք؛J,v�j��u�Z[#��)�X��t�h�3e"Ia�7'Ē�D���iAܣ:��L�7R�S8�2��E��U�cR��eR���\7B��2jf0�2&�Hf��5N�)L� Ȝ�zf�q��?�B�H��L�tL(Bt����ty�*bDW�ֈ2.+S$~.ǯHct}dk��"Z��j�
��
�u�#��z��=���@Lȋ#H�Hy)����S��)�2!�z�����dSAT��,�����      �   }   x���,O�K��W��/66��p76���"�Bphdlhh�ihdf�	�5�45��&0didbbR��s��rO�)MJ�4H-(�Dfc�nil	6ݘ��n���%���YX�m �������� �c-�      �      x������ � �      �   �   x�35��42�02��4�4�44� �Lt�t�LNG��0WN 45�42���p�LSpz�{&gHѱw��KW����tIV�H������022�5	�4�52P02�2��20׳0�4�0�� �0o(ˀ+F��� ��&~      �   �   x�340����,O�K��W��/66��p76���p�42001�" �3��02�54�52P02�2��20׳0�4�0��s��02��LSpz�{&gHѱw��KW����42644�24 2�sJ�R-R�aցt�����u�4�4����� W;�      �   &   x�3���wr��2���pt�2��p������� g7      �      x�3���p�2�u����� '/�      �   M   x�3��u�uv��u�tr��2�p�w�p��w�t�r�r�L�b�~� 3�r$! ��dPH� S��I E1z\\\ r{*      �   �  x����N#G��=OїH��A]��{3^ǰ���8��M&�(^�X��'�#��){L^�7I�x�=f��@�_�~�����jc����r�{�ן�^*4��9D	�
�+k���ޠ�q�rr>��'ト�*�d�E��\��r\a;��A�<u�kQ�
9<�g1k��dcA�~��ϵ��0mi h���A��,�ؚ�s��}���
5m�t:�A��������bz;������=ű�:�1btz.�}�v��*Z�Z�����oe-,P�h&���)z9�5�6�`��6k'�f�\m��6����fڧ�����8B��&.l�d�<��8":L0RgM��Zٯ��dk�-�p�����qDq����M���@ �3��%n����L��Bt� m�p�X�+�*�W-�֊A`�cj"�!�/{��"�b�~��l!ݲ�_`��6t� 1,��ṑׄ�k�|�����rUQ����ǔųv���ˠݤ?����՞"�烥cIg�vС��x����Yn���BD�:�W�jѿ8F��s�>��YpP[EqR,ʏ'7��S,jc$R+q���*%���x�@\�֋��07����1��th�˟�w�J��Jc~2|a���m���ă���\_yj����������5��aȚ�t@��F�o{���H��&�v�Y(���15�R��Fu�N��ԙ�����*dg֗���|�E#{���z��Q��Ơi�̺�\����Џ ��ɲ^�����r��z������z4cf���ju[,�ź��@g�X�����l��s��3�i��U�3h��u�Mۑ���������W���k�mF�i+��-��Ű�d��l��������nX�egrv�=9k����l3���H}MZA����-��^�����֏��O�#)_�:���J�K�k��埜�i�'9�n���tsd�?�.ۚ�ިL¿;ʲ�?�E*F      �   -  x��WKnA]�O��ŧ���fEJ�2R���5��5C2�,[�g
`i�a#��5��F�5پ~���׏/��WjoHo���G�u�1���t���KkG��4�a-�H�������*��H�'��ۈ8�q��a唟�q����F ô� 5>�s��\�C�(��s�p�x�J�'ә�xԆF�Lg*�ëp2��3+F�i�K� B�89�<*nӣՀ
G���҅jT�%CR��B5��R�zE�\.��C�ɪA�j'�m�Y5��O���۩*{W���VU�6Ҭ�VU7�Hf�K![W��Z�p̯/�+���0��	����2�"���g��� �/ � ��3�5o?��'��_��a�[-�L� ��bf)���]�")RPq�z�()=K�T$�!�}���$�iB�,\M�����/�B� �D�y��E~�-� [l��	��7ˢ"��	x��X)>AP�#�2������S~2��ʳ�W�c��Z���@M�d>kѧB�;z��|֢/�ZI��E[��>��Z�C^@G��Vtf�3rd:kEgn6b�G��Vt�n=�+�Y+:7@�m�\anO� 9`�o�J7n�3��̴=9~��Og=���؞�����(�9;��{��'£�Rl���n�O���_��T��[�C� ܇`wa���o>�����}�iI9��I~)����j�_Π���CǴ�����t��\�w�y�|�{>|���Z���Nߛ�����> �U������I���_�i7�^���-p������n��� �������C7{      �   p  x�eS�n�0�y��c;�0�W4RVk�H+��(@����!�2HPd���d�?�'��cWj&J��{yx�9I/���8��d���c1$#�~�dO�M�u��>]/Vw��^C8�t��D�9���nr�����HK�D,�$�$}���_#�s�l"�;z�����jJd$t,5�`�d����3�L҆��,�<}��o9źxȰ�U_G�`wH4]�y]=�[�G"�t�!���������F�+F�0��/Z^��˺���^>m��4:��Luu�/�A�q���s��k�N�J�4)8	����r�K������dB�c���d_����u�����֞�������u�T�u�e��-��AH㼘S�ԏ�n��P�}�0��~��&���W�a�i���`�����:�A�4�ה�6� ���z����h�g�� ��I'8j���|�:&z��qӫM���2o������ -5�������l:�����A5�� p�tW�1_k{���Kz��ݚ7����F�W�	��$�6��r��/���m+t�H��䴌�wȉ�ul~��Wކ9�����~2`�c���u�l\��B��!����,^���{ ���B�         8   x�ʱ�0���Ϙd���밈6�W)/bh<9$��M_"Y��ž%�� ?7��         �   x�5���0��az��ػt�9*���/Ȗ 
��$��BBk&��G6̪j��M�#	+�k59|��w��N��M/|,+��y�����j�ĉ�U��o�!ƞ��RQe�Ȟ�M��;�%�̐v3.%v���������-S,�AO��/�v��~D���1	            x������ � �      �   �  x��UMk�@=���=��蘈��K�E�zQ�D�uH$S�rmi{�!5��B �\"z������uК�m�����yc� ����IڔU�n�~��������/A �F�ü)/���Mu�Z(]|�׹�	�0|MUR����N��^/��`�a}�&�CR�tV�e�C����.s�4�O�GՔ��l��Wb��(�Y�U�L��k0�0���Αu7]��><�J�`YS]�q.�s5A*'O��G��Ge�盼���N]L���e�aԣ��lڣI}y`ǰ�,�`-�(��;��0�]��,��(S~������x�ݟ5Յ69=ql"�͟��	"PGn����:�M�����`��R7�2�\`ƣ�Ȏ�Dr5�-R�@\e�}b��H��N�;
� !\�Ӎp��u���=�!R�4�{���vफs�6B�8��ig4�c<"�E������0D�9&��m��������y9�:#�չ�hD��#��'[k�〾{J� �[�0[zywm�H"6Pܦ<���쇁��!�D�&�l�pa���I�O�.��R������IU�Y�w��v��͍�hu/�_A�ƪ����ha�?�4����}�Q��N_��El�O��S	=����8,>+����X� =�=I��=�f��u�+��(���B��_k      �      x������ � �            x������ � �      
      x������ � �         �   x�U�K�0��]�H�wi�E�S$������JLX�y�=ְ�fd))��-�G��ݺ��r/y-��(���a�z��Kh��0=.��#x8�8rk�[���=�:ҭ��q"G�_ȼS�[����>)�����/V�G         �  x���KnA��=��e"4Mף_�3!Ơ؉@b3
�	�R�H����un �l��#7��"������T�_�_��ñ"5ڙ�OvZ�ܦA�TX[P0��+����R��*2.���T���W:��:P�5��c�y�B�x�`4�u7D$� 2.&�5\C�}!4�Ч�"h��eM��$�@obd�fz�!Ԉ�fC�0t�	�/2���{��'��,�n���o,��U�V��( �Yp���dR �����y>;��u{�\�s�:��uu1���>"e!�\p7]���v���ߖ��w�]�/�qd����V���핂�c��b�a5�Lޜ�� BC�!��W�՗պ�������~o@���j2��&�]>;�_+)Nַ��bou�QU��b~��VNu��'�U� �r4�҂����ɀsd;(��է��#͹h���^BPe���ܩ�k�X�)ݑp�W�5{���Â��h<X���� �Zzk�����u�����I�6��������7R� �#t��P��I��*5�Jb����������m�P15bW��ؒ��>tU��h�&n�N��k���*�ٓ�D�d�j��g<I��Lʑ�"@I$.�����ͷ�bɬ�r�d���Z��9n��[��������@F�����"�d�>&H�-�Z�N���O�����?�,��  ]��            x������ � �            x������ � �         V   x�E�A
�0D�u�^��I���p�5\x{�)d1���\ϻ���W�1�ڜ��9�F�j2��}(�Ԗ�*C�L�T���w ��         %   x�340䴴��24�21�240�440�#�=... e��      �   �   x�3���x�km��\����p��dN?� .#Έ҇�����D�:�p��d^���TU �ch Ӕtxa�B��݋!Zazp�KA�9g<ܽ</*i���8��.CK�A��F����@�&]Z��`6C�LV0�
�����*e�	a��qqq c�kt         �   x�5��	�0�4�'(�o ㋄�A

ާP���B�%>��7���M���ԁ���l|N#�yI���K����}����@F֜�:�@QVf�qhR��f����#�Y:��P���3TFV���l18٥���㊤Lx9!�U?=�         �   x�-�;
�@�zr�9��y�jM
A�X�ѨIwU��g���!)׋�M�Ymv������k���oX8K���T�b�d��/��H��,
�9��~�4v!�Ļ���c�j�V܎��Wq���j��z�}���0��sG`� (��o^,�f�/�K�u�Z�ZX�gFmկߴa2q=�B��?�f$Q�0�/e9UM         �  x�u��j1�ϣ���B�ѿխ��&"��S��ۖ�s_��zkZB��`7/�oR�N����@���o$�ٲ�Y�y�~� g%�D=��=W&�X8�.�{fFPv��9��R/�M�~ZAyqr\�����q[�b�m�{��I��e��߽��ۇ���ś��6H�ʣs���?�9��mx>���dX�!a�W^g��2EPMq�tIxo����<�����\���q���I��@Zxk�tY�w3T0[4�[^�0�����2���_8�i��v�b��V端�c��P^��oR��8����;���/���J��ϥTo�@̩�S�~,���j���XO���{�ڋ� c]V�<=��e�0�����Ӽ��D�(aG�z����t���QC��(��l�L$]
���W��          V   x����0ߢ�L��%�בՇ��dHU�����z�id,�V1�����:̤�<	�N�ڇ��e�K���M����&N�q_�����     