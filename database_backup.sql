PGDMP  4    /            
    |            postgres    14.13    16.4 Z    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    13754    postgres    DATABASE     �   CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE postgres;
                postgres    false            �          0    17028    activated_active 
   TABLE DATA           ;   COPY public.activated_active (id, status_name) FROM stdin;
    public          postgres    false    282   �P       z          0    16873    assignment_type 
   TABLE DATA           3   COPY public.assignment_type (id, name) FROM stdin;
    public          postgres    false    259   �P       t          0    16797    category 
   TABLE DATA           j   COPY public.category (id, header_lv1, header_lv2, header_lv3, type_title, tructhuoc_id, code) FROM stdin;
    public          postgres    false    253   �P       �          0    17060    category_assignment 
   TABLE DATA           [   COPY public.category_assignment (title_1, title_2, title_3, title_4, id, code) FROM stdin;
    public          postgres    false    288   IR       �          0    16945    team 
   TABLE DATA           3   COPY public.team (id, name, team_code) FROM stdin;
    public          postgres    false    267   S       I          0    16395    nhiemvu 
   TABLE DATA           ^   COPY public.nhiemvu (id, ten_nv, createtime, status, team_id, assignment_type_id) FROM stdin;
    public          postgres    false    210   �S       K          0    16401    chitiet_nhiemvu 
   TABLE DATA           B   COPY public.chitiet_nhiemvu (id, nhiemvu_id, nhiemvu) FROM stdin;
    public          postgres    false    212   .V       X          0    16445    nguon_nx 
   TABLE DATA           7   COPY public.nguon_nx (id, ten, createtime) FROM stdin;
    public          postgres    false    225   �X       ]          0    16471 
   phuongtien 
   TABLE DATA           l   COPY public.phuongtien (id, name, quantity, status, "timestamp", nguonnx_id, loaiphuongtien_id) FROM stdin;
    public          postgres    false    230   �Z       �          0    17022    dinhmuc 
   TABLE DATA           k   COPY public.dinhmuc (id, dm_tk_gio, dm_md_gio, dm_xm_km, dm_xm_gio, phuongtien_id, quarter_id) FROM stdin;
    public          postgres    false    280   n]       �          0    17076    donvi_nhiemvu 
   TABLE DATA           ?   COPY public.donvi_nhiemvu (id, dvi_id, nhiemvu_id) FROM stdin;
    public          postgres    false    292   <^       M          0    16407    dvi_nv 
   TABLE DATA           >   COPY public.dvi_nv (id, dv_id, nv_id, createtime) FROM stdin;
    public          postgres    false    214   Y^       �          0    16972    group_title 
   TABLE DATA           A   COPY public.group_title (id, group_name, group_code) FROM stdin;
    public          postgres    false    271   v^       �          0    17052    hanmuc 
   TABLE DATA           a   COPY public.hanmuc (id, quarter_id, hanmuc_md, hanmuc_km, hanmuc_tk, soluong, pt_id) FROM stdin;
    public          postgres    false    286   �^       �          0    17082    hanmuc_nhiemvu 
   TABLE DATA           e   COPY public.hanmuc_nhiemvu (id, quarter_id, unit_id, nhiemvu_id, ct_tk, ct_md, consumpt) FROM stdin;
    public          postgres    false    294   �_       p          0    16776 
   inv_report 
   TABLE DATA           s   COPY public.inv_report (id, petroleum_id, quarter_id, inventory_id, report_header, quantity, price_id) FROM stdin;
    public          postgres    false    249   :a       x          0    16850    inv_report_detail 
   TABLE DATA           �   COPY public.inv_report_detail (id, loaixd, soluong, title_lv1, title_lv2, title_lv3, title_lxd_lv1, title_lxd_lv2, title_lxd_lv3, xd_id, title_id, quarter_id) FROM stdin;
    public          postgres    false    257   Wa       |          0    16890    petroleum_type 
   TABLE DATA           @   COPY public.petroleum_type (id, name, type, r_type) FROM stdin;
    public          postgres    false    261   �       S          0    16430    loaixd2 
   TABLE DATA           |   COPY public.loaixd2 (id, maxd, tenxd, chungloai, status, "timestamp", ut, type, r_type, ut2, petroleum_type_id) FROM stdin;
    public          postgres    false    220   ��       `          0    16481    quarter 
   TABLE DATA           �   COPY public.quarter (id, name, start_date, end_date, year, status, convey, tdk_sscd, tdk_nvdx, tdk_sum, tck_sum, tck_sscd, tck_nvdx) FROM stdin;
    public          postgres    false    233   0�       ~          0    16904 	   inventory 
   TABLE DATA           �   COPY public.inventory (id, tdk_sscd, tdk_nvdx, total, status, tck_sscd, tck_nvdx, petro_id, quarter_id, import_total, export_total, pre_nvdx, pre_sscd) FROM stdin;
    public          postgres    false    263   ��       r          0    16782    ledgers 
   TABLE DATA           m   COPY public.ledgers (id, quarter_id, bill_id, amount, from_date, end_date, status, bill_type_id) FROM stdin;
    public          postgres    false    251   K�       b          0    16487    ledger_details 
   TABLE DATA           G  COPY public.ledger_details (dvi, ngay, ma_xd, ten_xd, chung_loai, loai_phieu, so, theo_lenh_so, nhiem_vu, nguoi_nhan_hang, so_xe, chat_luong, phai_xuat, nhiet_do_tt, ty_trong, he_so_vcf, thuc_xuat, don_gia, thanh_tien, so_km, so_gio, dvvc, id, loaixd_id, nguonnx_id, cmt, nguonnx_dvvc_id, sscd, denngay, quarter_id, "timestamp", tcn_id, nhiemvu_id, nvu_tcn_id, nvu_tructhuoc, phuongtien_id, phuongtien_nvu_id, so_phut, tonkhotong_id, tonkho_id, ledger_id, import_unit_id, export_unit_id, loaigiobay, dur, dur_text, thuc_xuat_tk, dur_text_tk, nhiemvu_hanmuc_id, so_luong) FROM stdin;
    public          postgres    false    235   X�       l          0    16752 
   ledger_map 
   TABLE DATA           f   COPY public.ledger_map (id, loaixd_id, header_id, soluong, mucgia_id, quarter_id, status) FROM stdin;
    public          postgres    false    245   D�       O          0    16413 	   lichsuxnk 
   TABLE DATA           w   COPY public.lichsuxnk (id, ten_xd, loai_phieu, tontruoc, soluong, tonsau, createtime, mucgia, "timestamp") FROM stdin;
    public          postgres    false    216   a�       �          0    17014    loai_nhiemvu 
   TABLE DATA           @   COPY public.loai_nhiemvu (id, assignment_type_name) FROM stdin;
    public          postgres    false    278   H�       n          0    16760 
   loai_phieu 
   TABLE DATA           .   COPY public.loai_phieu (id, type) FROM stdin;
    public          postgres    false    247   n�       �          0    17006    loai_phuongtien 
   TABLE DATA           >   COPY public.loai_phuongtien (id, type_name, type) FROM stdin;
    public          postgres    false    276   ��       U          0    16437    mucgia 
   TABLE DATA           |   COPY public.mucgia (id, price, amount, quarter_id, item_id, status, "timestamp", asssign_type_id, inventory_id) FROM stdin;
    public          postgres    false    222   ��       �          0    16964    nguonnx_title 
   TABLE DATA           K   COPY public.nguonnx_title (id, nguonnx_id, title_id, group_id) FROM stdin;
    public          postgres    false    269   �       Y          0    16455    nguonnx_tructhuoc 
   TABLE DATA           I   COPY public.nguonnx_tructhuoc (id, nguonnx_id, tructhuoc_id) FROM stdin;
    public          postgres    false    226   ��       �          0    17043    nhiemvu_reporter 
   TABLE DATA           �   COPY public.nhiemvu_reporter (id, title_1, title_2, title_3, title_4, soluong, nhiemvu_id, phuongtien_id, ten_nv_1, ten_nv_2, ten_nv_3) FROM stdin;
    public          postgres    false    284   ��       Q          0    16420    tcn 
   TABLE DATA           F   COPY public.tcn (id, name, concert, status, loaiphieu_id) FROM stdin;
    public          postgres    false    218   ɚ       [          0    16459    nhiemvu_tcn 
   TABLE DATA           H   COPY public.nhiemvu_tcn (id, nvu_id, tcn_id, phuongtien_id) FROM stdin;
    public          postgres    false    228   ��       �          0    16986    nxt_reporter 
   TABLE DATA           �   COPY public.nxt_reporter (petro_name, petro_title_lv1, petro_title_lv2, title_lv1, title_lv2, title_lv3, title_lv4, petro_title_lv3, petro_title_lv4, xd_id, quarter_id, quantity) FROM stdin;
    public          postgres    false    272   ��       ^          0    16477    phuongtien_nhiemvu 
   TABLE DATA           G   COPY public.phuongtien_nhiemvu (id, phuongtien_id, nvu_id) FROM stdin;
    public          postgres    false    231   ʛ                 0    16928    price_status 
   TABLE DATA           0   COPY public.price_status (id, name) FROM stdin;
    public          postgres    false    264   ��       �          0    17089    tab1 
   TABLE DATA           )   COPY public.tab1 (pkey, dur) FROM stdin;
    public          postgres    false    295   �       �          0    16992    titles_category 
   TABLE DATA           ?   COPY public.titles_category (id, title_name, type) FROM stdin;
    public          postgres    false    274   K�       e          0    16495    tonkho 
   TABLE DATA           t   COPY public.tonkho (id, loai_xd, soluong, mucgia, createtime, status, quarter_id, loaixd_id, mucgia_id) FROM stdin;
    public          postgres    false    238   %�       g          0    16501    tonkho_tong 
   TABLE DATA           �   COPY public.tonkho_tong (id, id_quarter, id_xd, amount, sscd, nvdx, update_numb, note, "timestamp", tck_nvdx, tck_sscd, tck_sum, tdk_sum, tdk_sscd, tdk_nvdx) FROM stdin;
    public          postgres    false    240   B�       i          0    16508 	   tructhuoc 
   TABLE DATA           @   COPY public.tructhuoc (id, name, type, "timestamp") FROM stdin;
    public          postgres    false    242   _�       v          0    16842    tructhuoc_loaiphieu 
   TABLE DATA           M   COPY public.tructhuoc_loaiphieu (id, tructhuoc_id, loaiphieu_id) FROM stdin;
    public          postgres    false    255   �       �          0    17070 
   tructhuocf 
   TABLE DATA           C   COPY public.tructhuocf (id, root_id, dvi_tructhuoc_id) FROM stdin;
    public          postgres    false    290   H�       �           0    0    Inventory_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public."Inventory_id_seq"', 1225, true);
          public          postgres    false    262            �           0    0    Ledgers_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public."Ledgers_id_seq"', 398, true);
          public          postgres    false    250            �           0    0    activated_active_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.activated_active_id_seq', 3, true);
          public          postgres    false    281            �           0    0    assignment_type_id_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.assignment_type_id_seq', 2, true);
          public          postgres    false    258            �           0    0    category_assignment_id_seq    SEQUENCE SET     K   SELECT pg_catalog.setval('public.category_assignment_id_seq', 3209, true);
          public          postgres    false    283            �           0    0    category_assignment_id_seq1    SEQUENCE SET     J   SELECT pg_catalog.setval('public.category_assignment_id_seq1', 11, true);
          public          postgres    false    287            �           0    0    category_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.category_id_seq', 61, true);
          public          postgres    false    252            �           0    0    chi_tiet_nhiemvu_id_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.chi_tiet_nhiemvu_id_seq', 57, true);
          public          postgres    false    211            �           0    0    chitiet_nhiemvu_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.chitiet_nhiemvu_id_seq', 49, true);
          public          postgres    false    213            �           0    0    dinhmuc_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.dinhmuc_id_seq', 101, true);
          public          postgres    false    279            �           0    0    donvi_nhiemvu_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.donvi_nhiemvu_id_seq', 1, false);
          public          postgres    false    291            �           0    0    dvi_nv_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.dvi_nv_id_seq', 127, true);
          public          postgres    false    215            �           0    0    group_title_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.group_title_id_seq', 2, true);
          public          postgres    false    270            �           0    0    hanmuc_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.hanmuc_id_seq', 70, true);
          public          postgres    false    285            �           0    0    hanmuc_nhiemvu_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.hanmuc_nhiemvu_id_seq', 351, true);
          public          postgres    false    293            �           0    0    inv_report_detail_id_seq    SEQUENCE SET     J   SELECT pg_catalog.setval('public.inv_report_detail_id_seq', 67427, true);
          public          postgres    false    256            �           0    0    inv_report_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.inv_report_id_seq', 38359, true);
          public          postgres    false    248            �           0    0    lichsuxnk_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.lichsuxnk_id_seq', 1015, true);
          public          postgres    false    217            �           0    0    loai_nhiemvu_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.loai_nhiemvu_id_seq', 1, true);
          public          postgres    false    277            �           0    0    loai_nx_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.loai_nx_id_seq', 24, true);
          public          postgres    false    219            �           0    0    loai_phieu_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.loai_phieu_id_seq', 2, true);
          public          postgres    false    246            �           0    0    loai_phuongtien_id_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.loai_phuongtien_id_seq', 7, true);
          public          postgres    false    275            �           0    0    loaixd2_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.loaixd2_id_seq', 70, true);
          public          postgres    false    221            �           0    0    mucgia_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.mucgia_id_seq', 3425, true);
          public          postgres    false    223            �           0    0    myseq    SEQUENCE SET     4   SELECT pg_catalog.setval('public.myseq', 88, true);
          public          postgres    false    224            �           0    0    nguonnx_loaiphieu_id_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.nguonnx_loaiphieu_id_seq', 20, true);
          public          postgres    false    254            �           0    0    nguonnx_title_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.nguonnx_title_id_seq', 41, true);
          public          postgres    false    268            �           0    0    nguonnx_tructhuoc_id_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('public.nguonnx_tructhuoc_id_seq', 108, true);
          public          postgres    false    227            �           0    0    nhiemvu_tcn_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.nhiemvu_tcn_id_seq', 1, false);
          public          postgres    false    229            �           0    0    petroleum_type_id_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.petroleum_type_id_seq', 13, true);
          public          postgres    false    260            �           0    0    phuongtien_nhiemvu_id_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.phuongtien_nhiemvu_id_seq', 3, true);
          public          postgres    false    232            �           0    0    price_status_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.price_status_id_seq', 1, false);
          public          postgres    false    265            �           0    0    quarter_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.quarter_id_seq', 20, true);
          public          postgres    false    234            �           0    0    so_cai_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.so_cai_id_seq', 1088, true);
          public          postgres    false    236            �           0    0    splog_adfarm_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.splog_adfarm_seq', 1, false);
          public          postgres    false    237            �           0    0    team_id_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.team_id_seq', 5, true);
          public          postgres    false    266            �           0    0    titles_category_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.titles_category_id_seq', 14, true);
          public          postgres    false    273            �           0    0    tonkho_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.tonkho_id_seq', 813, true);
          public          postgres    false    239            �           0    0    tonkho_tong_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.tonkho_tong_id_seq', 1068, true);
          public          postgres    false    241            �           0    0    tructhuoc_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.tructhuoc_id_seq', 28, true);
          public          postgres    false    243            �           0    0    tructhuocf_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.tructhuocf_id_seq', 9, true);
          public          postgres    false    289            �           0    0    vehicels_for_plan_id_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.vehicels_for_plan_id_seq', 32, true);
          public          postgres    false    244            �   %   x�3���wt�s�2�2�9�C<�ܹb���� �V�      z      x�3�vv�2��s������� '��      t   N  x��R�N�@=�~�|�Zh9V�i�H�tm8x�Di�ƔD?�1�ك����G�O�e7t7��	03;��oֱ�>���>�	+���I�C�'�ؽ����
V~� �S�8�6��I=�S��W�Y�ʪ�wI�4��@<�ČUo)��/��Ap%��ͫ�ؗ#�V���}ɵ �'9�K���;|���s>�K&���7��L�B�"b�b�����@d� �&�e(q��pU�pv�[�76+�#��;|�Ϻ����˃��x�e��=��rLX�3GO��7^��{*����u�,�v��s\�j�*���Ζ�!�	A	}sF���$h      �   �   x����x�{i	����Ghg�rzxp�ed^�����pw{)��T����=2����?��1*l�������&a�����;2��.�U~>!0{���6���[�ґe�!��z=\�r&@&�>��)H�����@qT�X�����24 iC�Ar��!�! �=... |�[:      �   �   x�3���x�{������;B<B�Z�������C��`j�=��y(�=��Q�������eS����{l}(D���eS��"T���e~%���Ύ~\����@��KB@�x���=... ��9�      I   �  x���=k�P���_q�\c��ט�����E�Yd$���$���6CV��C�@Tc��Y"���q�IϽj��!`l������/Yu��0����,!�ePﾡ�Y7-8��w��`�q�b0���Ӑ�qsM
VŁ�s��	��0��c���,�8ҟ��W�J0�X�UX�n.e�0ٟ���-݄�|�g�;�߰@���q�G.�*%�7�3��=-ߖ|�o��ʤ���LcI��6p�o��5J��Y���[`�|�`(��G&k�4�	�HV��x�6�{�⌮�h�6��ؒ��D~���e�ՙ�=�DGJSֵlug��80M�GFu��׷@�6�G��>$�'�!O�Zނ:�����q'!_�ub��N$�,f)}����m[�i$Q�7�c���6�kS�/��W.Y�p��^k%�]Ǜ��,����n�Ĕ�F*��H�%^�f�����>-�=��v�0a��,U�L��P6��e��T,�헄�$��zS��B�ץ�XJ�ܽ�'�sNH^�Ӆ��^ �w#O�U����P�Ӥ�ҳ�dwϦn�(vՔ��Q1�N���kP�)�/�"I6�/��8����m"��=i;_��.SS�i�.z3U���{�#mT�k�J���@�      K   �  x�eSMk�@=���9�`�%���U�ar��q���&�L}m9֦���B�J
���R�Ѓ�����]�`�vf߼y3or��h%:���l*�*�d�\�%Uv��8Y!��/��Q�z=y�����A�*�knR���$��h�-����&ǡ�H�2G�[�f3��3�OPVYq�$U�I�E���b\n�i�Ӧ�3G~w���{~�\Z�x�v��\Wم�����eÖ9�yM�i�jS?�^q�'�&,n􄝖��U�#�q���E�U�:��wPT�Rs��~��۠�i�C bvѾB��*���?�����م�&��Q��fB��E�9�В��ʴ�.�4α�D���ʷ�6	\���	��Tk�0.���^W��v��*6�_&Vv��s�.p2�ɲ8��0��j�n�@X�}�Ό������\�e�/6�%��=��8������Q�v����
���tj����=�<g��qXlu���=E@��]�4	����}n���P���"`zU�M�����sQ��n
�ѽ-O�y��	{~���~�K �!�(K|{ͪA�XW�`�,���r���8X�@�Un0\�b�I�Ь<l������᤺&�>��گ�_�y�#�_��彇ٱMF:d�+
���n*�IT�sƋPf�39�s��kh���ʬ��͞�a��.�wO�;U����sv�      X   �  x�]��j�@�ϣ��c{p�]ɒ���P�c������PK`Km�y�^�C="J95������&�4���gg���%L�����"(���}ه28�@���:͑�N�fT��0M>�%��C�C�!����5��5��I�Dέ��U�lq��1уKk���]����__�E��zV��q������l�!��x�W]����V�K5\.���wk~���5������4/2�*Sv\�t�`zt��)nlcX�<����b�N��`l�:Ͱ*ۺxM�2مx/���� "��m�[<s�6�6��.�ˎ~;+�X<"�gǻ�O��}�'��y.���$,iO,N�`Df���Q]��Ґ�y{�3\cfV�8)1��b<8�S�<��z����5���יּK�}�^eH�L�Sjʦ��e_�՚o���"���8�2��.      ]   �  x���KnA��=��e"��ѯٙ��cP�D �ˉ�l)2���b��:�(��M����A�ը���������&ۼ�5ޙMNwZ�M#mS-�q��C�XA�^�.��������D����H������.R�2ޡ���U��DQ�>[S0����
@��6�U�ȸd�Op`P5o_�h�:P�5��c9�L�Xq�`4�wD,�{w19��p!�B�bW�=�V��gK����Q��u�*��(��fC�0f��ċ�&����2�)��sa��}aQ��ڵ�[Y��jN&B1J�0�����۟�W˅>07��}�n/�ԦKr�BN8I�1���/��Z;�['���G��.�KlY���j�qy{�9ݱv}�	1P���L��N����dC�=!���Z�R�>;����9���8k9bAM'��~3��'/AJ���=&��[]��G�r��_���)���B�J�
�j�3-%(�؟@\#p�� Eu��p1�Gq.�()��T�~i5nl�}�Y���H�!+ݘ���À��h<X�A�z)�^Ko��Z�]w�W�8�+ӣx~��FW�T3��� ��]�$s��.u���&�)Bo�����tK$Ԉ����e8�{�U�����w�ޙ��~�mWl      �   �   x�M�� C�u�����r��qК�_Olgq&%MG� rR3�LK�$���RtK��FX�)q���tG�\�O���g7%Dm�cc��tZ+\��;� c0�"� �P+�p���A�P���[6�F��o[;��RZ�M�i��$y\�=oǮ��G�1�$�x�6µ!��#��6Mo��'�~z�?2IF      �      x������ � �      M      x������ � �      �   R   x�3�LJ�/�<��T�$���@25_!/#����\�2�gII|^�P��_��]k*J�ZZ�P�p��<�����=... H] �      �   �   x�m�Kr� е�0)���g����f�*VOEc�&�Z�8Sj>�.ឤ�S����݀HڊC��0��i�ڻ`��1�9�2̻
ꈦ
��ˮtAwSe��Zw�u�jx��VOHt�nXWS����舎���%qݐ�7t� x���
�S3�P�@�stx��<S8�����dS      �   �  x��ֻu�0D�X*f~$�����a���L�K��H�yL���#���W��Q����X"��Pt&���;�GyfȌg��yȂg���D6y����]��
۪Cs���E8NCƅӑq�d\82.��,�¹Nǅ3�Ex�D]�K�⾥]q޲��nyW��+n[9mMd\�,8l��}����.��uѮ(��uEa]�+
��Ha]2�ٕ	
��'�uɮ.�����h^`U��j]qXul��8�XpX�Ÿ����jvu���8���}����/�f��a͑qXXpX�E\`mv�amu�a�f�lu�a]�Ⱞ��aݐqXwXpX߅_`}t�a}v�a}u�a=���VW69l(2~��y�G�g������B�u#�]x�,��?��}��]      p      x������ � �      x      x�ŝM�G���տ�nȀkq�{IQ=ԌDJ�ⴴ��-s�BS�@�pkv�,�����c ^�Us����g��d|df|��;��&5���}�*o�i���⛿���N����l������g/O�~w��?���^��������������������o�����o��M�w:�~���/��]���/Nϯ޿�Oo.@^Hs�W�Z'.���_��B^~u��Z\H�bh����7˿�W���ϱ�O>:�x���qz�������o`�#�o�߿�����?�i����(�-i/����������/���$\���_��ѳ��??�_nqa�Me�^�����BX�&����W/�����M������ۋo>\�	��D~I�;+�r�
Zpy3b��kѯ���	�眘��A�A�,�K�Ɉ���g j@���/5?��ce�[y��,���__�^?�;��2���l
��>�X�����������_tB����@诺L�MU�5���k�잘�K��A������e^pdzJƭ��/�|�����v�D?$�(RN��ǧo�����O�?��	%ʑ��7���_2�](g��'/O×z��~�$+���w/����k�Pw�j%n��%��J�Y%��3+�b�ܬ:����ׂ�Z%Y�R���J�Ue�QK�d�ʮo���N%��rcZ��J%Z�J�b�F%ڨ
I9.�h�:�2��h�:�1�h�:�3۔d�:�0&˔d��^|��I�a���#�����?<}�#+
�ڧE+FyT*�6�K�|�Rq_�}�#K<*m�ȴ��{k�")���A4��b��p9ށ:��������d�7�q���80����U��u���ѹL6�uԈ[�fC��WG=�f�ߤ��ƥ�~[�w~[tdL����?�>,V�2!EfeM�P�~@(�"��T����>�n��D�qC[�]�C���z�[ӵر��őp"�~o7P�d��x�h�֥�L��#lHU:2�~�8�.s������Ar�!a`�E���!ѯ��2kH��_''�=�l,��� 3���\CU>���(n/[�jV1z����O_߻����W{�j�][M����� �&{��0�p7��z�sX��+��Q�	��z�4�Dc6����@"�~6&kdr�[=qcY#�C�sd�H�P�7�5�9�~��g�|���g���~��C��p�u�}�=�	7���뮵���փ�P���I)�7.���4�"v�;q%B	��[1��b�$�ݘ���P2���L�Yȁp�{2M�%����2�{�
q�o���߻����b��1+dc�dM���X�꛷^G5u��vkv���$ty�n��pM��xCu���-��P��5[�6���o���Y=�ۤ�6����EӍсJ�*Y�l�V�X��Ѧ����˗3�p�sP����V�wP,�pYN�!ꎶE���������'/Ύ����g�`G2��d��_�|�y"-=�G8�]�}�� �� qPiy�8��|?B!L�XOg���bw�� Wma�����b�!6 ����*��Y`8��Ʊ�i��9�C�#T�l�9�*�D��`w�{2O��c���?Uح�~<Y��U`pQO.�e��a���TC��丹�T��z=���j�ջCG���lu1o�MU�S��}U��ؼ�z�V]�謻ztW]k��z4XSN�����x���I8�����O]OjV�����;lGwئ��iy��rA�3dMj?�DZ9�$RG�8���O�MG�8�Ɨ���Sb��kDV�X�m�"ܠW{i�B��҃]#�,d�y��q�16��j�[�`�'[n�t�u� YS�m�^�֋J�#4�]'�Ж�PAa{]����ر�p�J7h@�R�Rn5T�A�������>��Ʉ��i��S8s 0�	𜷭],^��`8W�c��i���)�m��S|�s��E��`�zx��+��	������q���+��و��#��A�����詉���V�P|�!8��z��K{�}���n��U�~��Ќ�����R�/y��z����Z�~ @
�^=�	�^w�߸�A��/oW�y�!f̮�K*St]=�	��9�'B5�X�<,�g@!��"9���	r�!'V��
L|0��j�9IχL�ku)���w��A�_�,�ĸ�7����?����T����#��Y�/1=~$'V=q�����X93�=�sb�ɜ76� �	�at�;�2��p-N7%�;�_����W/����ǋ�ח����ԉ�!6���0�-{�&6}�CO`�����"n��P��%�������A�!z\뤮
N���[��^�����[�,�;%|���k���	�:Yw���'|��d�8Xo��A���s0�U�sau��2>&vP�6>�Scu��D5=$uP���P��LԜ�v ���{����W���K��@�^��E�V5��O��u P/�*=�u\u%�K��@`V2br  +H��@�JU�v� VzU�<�^ejͰ7 pU:��Z�:+`V���ր`U��O 5 Pu:�l R�N9'@T�Ќ#,8�j��)��Z_|��/�N�|}��H]Q@�ӦMgd-�;ms�Yr*
#v��U�W�׈���B���/B���/Ӛ����sg�V������M*���
��ݲ���p�Ta��3�/�Ua�3�pBUa�3��qUa�3�!��Ua�3�p�UQ$�3�0VQ,���o����(������4%����o�������^�R<���Eeh5���Te�E��ٮ=�7.�ͮ/\)��Y�*1��©����X�Άz��~�N����휬vÊT��v.�	�b�s�ȳ�#���f����\6�(�xl粩g)�d;��;'F)*�9�5<�L�<9Z�gJ.��_���x��ɋ����?*���:|�M�E�;/��֖�����i�R���Ad���m��օ��-�;o6$��K�����)S"���@`� ���
�����|�o�����LP#�]�2V\c��[n��m��wa�V& ���.l�	�)Jޅ-'�D:�̻P���	]����@��"]����{����	���_�R��BhZa
����ʅ��N��^���ݛX��ċq
��Bb� ��}/t!6�n
���l��:B���>�1t���+�1tߋ�!&���^���
g���tF,c辗�5L C��,=����e����}/s+8~)t���An����c	�̥���K��^�F��U��}/}!4^���2��>� �e�/��{��3x)b�bS�|}j��`�^�Y����=���X����J�� /f�{(����tF�b����&���^���^�W��tN�R��W������U� �^�U�˟<y���O�x�Ïm�}���O�����Ë�v�(��/nQJ�O)�W�Li�}��e(�S��>�Rm��S�2�F���E�"S�·�6�Wˍ��}#�5$�y���v��5�K1N�ν���q�tnM�ۀQ*��w	E
��$*�*����!���NS�-���*���R�'F6O׬d4bs�$��h{h��C��y��FW�������� 2�(�1`Ca�3�v���b�2�/�1�s�Ǐe:f�c��Jo�t�����J����1����x���tϲ'n<�$oK��4�D�nD�$oK���4�K�4�9L��$oK��5�R��
fT�a%os�8��̒�������w�v���Z��%sv�WX�&�NWZ��t���|����7D�5]_/n\k:��c��J�ך�4�z�қĵ�cM>f�V��������X�\��x���$Բ'f\k<��}�S��x����.�q����_��Z�q'�K�µ�#Oޗ���kMǞ�/��ך�>y�Źp���������5���_<x��Ճ����[�5��>4)��.��K�T��t��S����W��t,����#��F��3�Y@��(�F����&��g<&僫wč    f<*�C�s`��R>����e<2D��P�cSA�&1�d<:D��@��SA���c:B�f�).�1� L�t�J�(�ܔ�|����k,J-���- ��R���f��U��TA����4U�Pe����LI�ڱ���S)�����Uaњ�,��DU�jWa�3Dt�+
g8��
����ōq<Td�g�`������
7��XU�u_�;��
�n0sxǃU���	y:Y��)̨��Uj�r6��٪ 5�5��� ���gO^<;}|����vq:P�4O���x:ؤ�&�JF� �Y��K\X�^�6D7��D�����H��=.��U�Z��;ī�U9^�w�M�ZM=�ä�I� �V��*�&:6l�X=��׎�S�V�w\wv:���k���өl!ZC���`��%E��ˬe�R���y�q����*���Q��v��Q�����0��tQ�f�bo���6��<l�3DA�u}&4ѱ�������PСV��7�T�n��灂��X��G��I�s�Jx�'�dY��}�I摁Ix�'�d0���y�I&r�Ht�'�d9�D�v��<���tN'�жۇD��]���۟O�R�L������ߟ�r���ϯwD�TN������Zu�D@]3q%R!Tw�8���L�(=�u\���!�/����p�M"t�n�8���I\	e�-�4��b��`����bY.��v�̈́Z3���C���OH�[3�K� y�[,����˾^��@��|�ޚq2\:�;4��@qJ\:��X�V��c��f���np;�Ʃ�R�>�n�/>���/�B�4]hZMK������%xz�[~�������QK��c�%�z]�M��L��,=�5�۬�y�j��m5�
V�`��CL��"\}i����P:#d-B6��0Z���'���lC����ܐ[�y�k	�!w���%����n����՛�O�^�"��8�npm:Ck����:��uD������a���J�׈��.2wђ�3r]D���q]$�RYmU���Ke�V>o��c6�aŭ����W��m�m������]�,>��Zw��E�0�	����;!'`#-�~Q)<���J�E��i�z�b��!�>MS��n�ͭƃx��ǧ��3�_�H�f��u�W�/R�"5�[|1�"�F?n_�X���D]*r���^�)�zb/ȝ⃝#~���!�G��n��!5ט�GC�$XQ��Ps	F{�1��b��5�`c�'&C�!8��	˪�����U�*�gOpV��0?�g����@pV�y�<�g�{�S�@pV�q�³�@pV�{���@pV��L?�g������@pֹo��o@8��6n�Io@8�kL�9 �u�$X�κ��pg]��	8������Y��΁�K{8��Y��r;pg#.������^=}�H>z��f#��F�*�����E�P�%�D�Eh��h�q,B�b���E�P�Ų��E�P���De�k��P6>�}&�L^.�VG�H6�l+^1Nd#�ȶb�@6�l+���c#�Ƕb�86ql+��Ec#�ƶ��06�`lS8�� ��Kn�F����&�����˶s67�LO����k�O?[kH��M�W�[5"~��46�ݴV�>��N塞�r;ȯK�;}�t�;��4�ΧM'��ә�ƛ|��"`�٨:�+��٤*��{���^\���W�4i�Yt��W���T$����m���wӻz�$"<}>����4����	oxCO�։����n{9O�H�4^�K�-9m��N0��2.�ӯl��5x@x��I����}�1�G <��Sy���A�ׅ�@x2���#d:�x�cPUGzE<�T=�L�><�1��>�G@<�|����ǐ�7��A�c�rR�΃G�Z��f�#f-"�6,���<���<-�b6�E���R�����$�H�����<�E¦sl�T����.<�1.��.
#1ah�	�'1g�I)R�q.b��RT֋�t�(%�����1yhiEU[�b�Z��)ebL!Z*fC͇ĘE�hdS�EĘH�(d�=
D�@��\��Pe6�l8T�C\C2(E4�:%�Z�-!���|��Rr���	���D�WPA�"sHԄD0{���Ƶ�ťaĢ&,�˅8��	��s�q4jB#���#�"�H˞���F3|��HUL�("5"R��I��TŤsaR#&U1��JU�:*5�RS�KM�T�oh�	�*�O͸Z�Q���O?������G��\���i�Z����!pj�	���<�:*?�}\}������ �MM���@�mMl��`��^鑮�ڗ�� U�P�a�!>���:�(\��T��k���Y��5Uk�AК�'���lM�؀k��bg��!蚊��x��ě�{4�����������������%�Ц�,g�JD���&n�kG��r��X�q�-W��%.�Jq��ʕZ_�Ҫ���\��ƺ�Xk������6���r��v���M�+Y��t��j/bi,�S�|��WZ7ڍ�+f��+�"����ګV��	q���`�u�iqż7�V�U&n �����N��vpO��限������7�~���շ/����>��3s㏰E�p����u+%{�Q���R{�}Q?����;��[���+5�q_�����h�}���ÎZ[�}^��UC����^o־�l��%��w4���+�	�{^ђ������a,E�؀Qj�F2�s��]�g-	�y�8Aa�������)���P�~!7V�6�[��/Z��$l{�d2!�8�{O_�������O���տ��w<0� &̀V��R�vWk��$�kV������b�2�1�v�x�LiL˖��3�1��M��z�h;����B��āgLd�����H&�v��h�d����P&��яiLe�����X&�^��j�eZ@xK��MN
f��/���d&��M�I����}�%�)�	����YY�d�7uFM�L MS���q��kĂf�b�6�x�L9L ����)�	d8���3��F�;E1�/ȣ~80��K ۾Џ`^ض�b���̣#� ��������ȥ�%�m`�-%-l����b� �|����*�������w,����Jv�t���|%P��3�YK�J�TS�����+�}�80k)Y	���b���\%PvSk ��R�@���}=#f�ߨ|�����*�ÀY�yJ��}��Ӕ@o� f-f)����i�ZLR�mݘ���z�	&1k)E	���c�R��-�K�ZJP�������:~�B*�\��8�J�I�C[�����$0"/?�R
L#��vv�@q!X�I1I`T.�LJH�s�NR8�U��Cģ�i�;�"F"�qmp�Ӑ����@BS+�0	l1�����#���������:�7
=[L*�(����n�P�X�>#:����x�K� ��G�D	H��)���R�Y;J:�7TF�FYG`CC�ξ�Z;�uuX8GyG���(�l(�P�2�����}�"���ֽSb�8��
1�ܖ����m�<1��������m?&1	����Ғ2��o�;4)	|}�������$$��:�:}��g�Гr���V����$�e&e �w�e;�������BJ�?JHR�Q��𑲏 ��}]"�%�{Am6�DL=�P��~b��� s`� T&x���w�2���ô#���eA�L/�(�H�bt�v�s�D1񬠣�#%�;m*M�FJ���=+E�FJ�u�Q�Q��f�`g���6��,(��"%ܺ<�(�H	�.?�/�'R"Ԋ�u����bw�,�"RRVZ��&)�Le?�0wH�d9 �iCJ&S8�&�R2�~(a�������L&�D�"������`�� %C�.ш.�b���t�<��(%H�l)޳B�����E�)P�%;��k:�,��&�%�(����(�G�����a~]�Nq�)>
B�	 ar�R٤�#	�z���J�УT6��X�T���&�(���,�( �  }G�l*�D�;JY�MߍJ�Qʵ����o���G�Z�iySb�4����߽�7+�Z:�)���C�
�ZƉ���-vvW/�����7����|\��v���^D�P>�n|!��7OC$�w���:C ���^c}��q؂����2�c�8::����Ǖpc'C��ȆmrS�$Sx�~���C&��Ǖ�N6���a�]m�	lx�cC���F�l�⅋=�⚶]>�]��a�P�2�t��P�2~]z>3�L�*��a\;+��
�QV������c��u��PT���V��3���T�N�a0ʚJ���e���ƽ(�!|0�E�d
�Ⴡ.�&��LpQ.��Y�Pd�r�2��2Z��M�
eQN�����b�f�?���&�6���LK����lZ|A����.��%���.�(:E��
����R����(EyY/����úܝ��O�W�&8��y'�g�ڏ$8Q>L(a����d�c	#L��F�L�Y�|6��h��峩d���� �6}7:(�Dٺ�GT�����q)q
,���TS��u��tV}�Q�,��٩��'��ͯ�(ED�)�`��CT���(1D��Q���*-DR�NI�� Z�z��L-��g&�h��(�0�C�|F�i��Z��ڏ+���"��Y^Q�|2 �r=��l���%��Eh��#27f�����_�q���G�_vx��߾����B;��ǅ�Ӿ��*�u�ڗ��ő�j�\Wwq٤N>�����b�u��/64�#�l�U��K-�"��t�R=]!���Rl_fh� �����"�	L���R'ndH��M[#VN&n��-u#q ��-u�΅d����д�qC2m_Xh��Kny6u�5��pI]ۮ�W�ū��_��W���La�G      |   �   x�U�K�0��]�H�wi�E�S$������JLX�y�=ְ�fd))��-�G��ݺ��r/y-��(���a�z��Kh��0=.��#x8�8rk�[���=�:ҭ��q"G�_ȼS�[����>)�����/V�G      S   �  x���Mr�F���S�&UP�v���� �1bc�f�$�X�r��L��	r��r��2��"�I�@�c���J�5]��V��F1���-�Bbge9���%��C����KBa3���7J�;����ӳ���L�#�Ӳ�I֔)��d��zDܖD?�c�Z@ո2�]�"�hE�dk�56�z���t�8�&C<Px�M�$ ����l�d��{>L��A������~��3ɛh�6�7��d�7x/7� f�@e���d�(���) �2g4�N���X����< �p����Z�I�5:L= M������4k�/�D��������kH�5���"9�b�5U_��x1�~0n?�x�B��R��b<��\I+Z��Q�����I[�x�	��������Ī�/�a7,�	%Es���x�)�lP"Z.7O�t^�8��hÈ��4�yf(�qh��O�;"N$�r�VP4���&�����(3�:�����v��W��T�'K)92u2d�����!�I����/��ۂ�)����ۛ`~;�0�8̲��w��\/w@G)%t�iE��ku�Ѻ�����}�h��)�|�o�[0�B��Qʳ u��yҹ�oC�	��]|]0�<64����X��Ne_���4�*8l����&�J�$���^�"���!r�Ug�ˇ{�/O��z�UyY�Be��1�e}<�N2�t�P6�?��a�Dʡ�0�n�*adS��z����?�T4o���q����d�l?�I�ˊ/\s�]����j����&��1���&���])j��	kR��q�����@����z�Y�Z�J�ѯ����<��<���[�i��9��$U��u�q����J����K�f�1M7�����C�C��8�AgI�8^Xl��̠>oɠEA�N7��j��Ym�%����m���(���x�r�����N�Y��QOcV0�p�Ҵ�U���m��Q?cBhw�w1]t�:	h~ˊ��L��4G�]�p�;���u�<YmlB%C"�
�;��^����4p�2u�,�̏�R3�L�l��
���Đ��5
��c�r���vr$�iC��k7��t<���L:/@��z"�a�fmUS���`���\����S�!��;!��✢v��饾;?;;�vVb/      `   r   x�34�,�T0�4202�50"(�X�����,.(��K�P��X�T�	B����Binnj6��`��PM�����K2J�i72 k7��14�k74Bv~yf^	V�c���� %�9I      ~   �  x����j�@D��ǔ�V�W綔^Zȹ��u��i@��ll�G�Hk��E��9V{����������ӵ�������Uds1��?�B�����ڗ����������E��0�'b#=��O�q��0�@H3���1��3��l�}~!�~"�1*�kW6�K�<�pŠ���<��$@.��/��@�ƫH�� �j�����Ƨ�PA5�\"�@�̼8@�=ґ
�Z]@.�tB��ʹDh��i ��W�j�|E��Z�|.���|Q���x�{m-m| ��va��Q�U8R��k�����ڝ�xw:���� ��7��H�@����`p�"���Z���7{�f����o� �R����|ETD�NЩ�[k+����/�FS�      r   �   x����N1EkϿ,�+�]"DA������������q�\qF � w�P��'����_�o�@��((8�P=�c�OI~F�o
���0Dt̜t߿�dZ����<I�]O����攙&�3�_�=z����;�uS_/pAKy2��C++�e,�O�<�xA:�O�CRoRb�گ�z�ܡ�D:$#��s3��lPUB�����l�O�^5b]&� ���jQ�'J�S��=0U#df�j���knk���t�'U��W      b   �  x���;s�D���_���UMw�S��Q<̭�8C]��8;8GՅ1!\Q�; ��#�O�I���j傲k5��F��{.�
�`���\���'ũ-�<_GL_�.F�J]�{]\��n���+���՛��~}Y��yx��<����/�`�^�G�d^}[�7��G���U��W(/���&�L� W�(�Vd*�2:D��f�&?��5iL��6Z=9Q*k*��e7�]��R%9�U�������������'��p�m��sq}���������MMo����-:���H}9(�52%�0IlDd�DT�:bĆ�w��ř�T!6�����2/�4���8b3��&=`���)�_R����?����x�y��5:�V'@�_M�H����ͫ¹i���'����n�t���������od���l֑��Mm��_�^��g�NCb0d�͝�F�10*�����]��>9/���~T<�och�ACɼ7Y�m4��v�L➏c��a����3'�u��~�А���כ�w?n[�[Ұ�g���Z�\�5����	^�ȗ��G�����.��"�.ԁ7� �/��\�E#R+��8R��A�$Ik�\�ʙ�*�n%lc=�2�V�r�Z�&�Ij�vΣ�*jk��k��n�L?N�b�2/I�����p?�싧��|�:5=�K���a#�Mt����헮)�J�R��S~�+��^�b}��o�X�=�;�5�8�.���۫�bc�[~���bs0+�4��0:���i�u���l�]���QHs	���??�X-�,�GQ�<���	[�Q �0; \���D;���"� [�p#MH �F�T�[�rC6ٲq��}8��3{�'�}����\r�~�֢c�iS�5k�󰯆�e��~����ƹA��������������� ��%B��M�I����͏�Ujl�.�jm=[|����գs�=z���5�1�	�X�)#�Q�gm���a6��M<Nt�e^"���&zÍ��\�[;�`��Cr�S|M>��2P�lf�\'�%�=r;&g�n��%�FE��F8'�[�!w��	��G.��l�(�Mżќ�J"������6ͮ���gS�5�f{�]�{�`'�5|���dg����( �E�C�0��j�y8���H���P��x8 ��8���T{/���ܩa�A{��r��:D�E�\�m�C�M���@..u�KKҳ���\,��ɷ,      l      x������ � �      O   �  x���;��0��:��t11O��N�5�Yp��v�r�&wH���&!)�%�v!��q�Jf�]�ӻ�����ȝ�OO���#9nG�⁀�%.�ճzqD͌Y�Jd03w�����8%��Ca��bB�볏�ނ���"	#;iǕX`�'�!Q%qM^�M�����S�U��4ޒ�9z� �HZ�n��XؑY�{EI��|3��
��� vHΈi%e��)$0G),TG����]���߿~���.dPAc���v=cL��ˇ�Og<<OG���_�fx�Nܔ8�V<m%�=!�#���}<^�~�)Qچ�;d>*�+i{�L�MD��N<fRo�%zQ��u	N���,�t�z��h�����YF���.{yg��k#�eA�h�̻�l�C*"���	yfQk�e��V ���,`�;:*V��S������ѥr���K+��+[H�?�?�|,3      �      x�3��SpJ������ p�      n      x�3���p�2�u����� '/�      �   M   x�3��u�uv��u�tr��2�p�w�p��w�t�r�r�L�b�~� 3�r$! ��dPH� S��I E1z\\\ r{*      U   �  x���͎,5F��O�0�ǉ�B�,�x�����}���0�4?uƕI�z�� �5惦�Wn�ǯ����_����3��D�[�oԾ暪���%��/�b�9��τE"�6a��?��P�E4P��v�Q�x#���*��hͬ�1��; ����H�����Ѝ����_��R	p>L�%"惵" l��D�@̊��d�������5�S�FXEB��hVa1�� ���*6�
�+B�HZтP���ak���n2奉�&�U�!L�8�C.s
;D����9Ec�ˈs8�r�S��>�3�؈:���s_���>�K�C������^hB��#Vt�\��ճ�M�`� ,�d^d�\�"��c�FԸ� 6�Lj#�����&FX�A6���A��A�τDV��&Ft�L�D\���*�ca�7�����fG�M4�S���Ȃ:�P����ʫM-k\B"ƚg(6�"'
=���Ĳ�q�"�5�pq5��h�]Dd�^܄��z�i"����l*�""󴪉	s�E(UMA=�Z�cE��V51��@l@5SP�-��a��<�fb�p5����f
�5��.�"󴚉6Ί�����3�i��,2oV51���F\�D-f�h�_���1��፸���&f�9��Л7��eY�F\�{���3z�b'D�R�*6�b'؜X����H�y�"��ꍸ�	R�|�=�-Ro]���3#Q� ���te{�ډSW�*6��9@�y��������A���b#�����Y,���[�N��ȹ	U�r�|I�#���[UN��95�؈*� �g��%��ܳ*'Fxԝو�� ��W��q2E��EN���$r9Q�I���J�{v�"|A_Y�F\�D�燙�3B�{v�"��g,6�b'�=Y��iW�g;!·���%�b'ʽ᝚qA�{�.vB��~Fg"���ro|�s�ډ�%{$�NE���^vj ���*���v*�=�Ό�@T;1Bu�وj���S��;�@T;!b��E"����o�'^V��'T��W���F\�D���vr��N�X44�H��N|Kz�.��p���l�D\�D��[p��d�EN�0b;Ul�EN�{&�8oW�q�"�(�L�EN�{�G�SE�_����/�3Y��JD�s~̽@�a,[�#�./40bL�MN"�c���?�Fd�/z�#�}}�Ͽ��J͸~�q{�o6�2�n��k�O�?�h>���!�_.���oTz{���Ͽ����r+�      �   �   x�5���0��az��ػt�9*���/Ȗ 
��$��BBk&��G6̪j��M�#	+�k59|��w��N��M/|,+��y�����j�ĉ�U��o�!ƞ��RQe�Ȟ�M��;�%�̐v3.%v���������-S,�AO��/�v��~D���1	      Y      x������ � �      �      x������ � �      Q   �   x�3���x�km��\����p��dNCNG��0WNC.#Έ҇���`�7�2��/�8�(O!�� Y��̀�����^������4P7���R���w/�KGQbS��qx!��-av�Iq�t%g�+��f��ِ3/#�@�$��E�!�`�"c̙WR��"dW�X�PR��bx� �>z�      [      x������ � �      �      x������ � �      ^      x�3�42�4�2�44RƜ���\1z\\\ *�%            x������ � �      �   %   x�340䴴��24�21�240�440�#�=... e��      �   �   x�-�;
�@�zr�9��y�jM
A�X�ѨIwU��g���!)׋�M�Ymv������k���oX8K���T�b�d��/��H��,
�9��~�4v!�Ļ���c�j�V܎��Wq���j��z�}���0��sG`� (��o^,�f�/�K�u�Z�ZX�gFmկߴa2q=�B��?�f$Q�0�/e9UM      e      x������ � �      g      x������ � �      i   s  x����J#A�s�S�l����>��`�h�L��]&�>�o~ �����7�GA6�ea���������d޼,e]��'9-*���!�����j���t�K�IcrN!S(0°h���P���:$��e���i���ʙ�apx2,N�_�cELQGA�Y�����I'��`�da�nnʮ���ލB9/��V;�e�P��湜�V7���O��ѓ �/�m���䤃���@?�n*W�׹�y�~^~��B�6���C���R�j��4���Jv7R�:M��<#[�{�������S�U�tָd�b�QAF��v.��[^���S#J��S2yrDm�0�������66����pp��G+!�	�1      v   V   x����0ߢ�L��%�בՇ��dHU�����z�id,�V1�����:̤�<	�N�ڇ��e�K���M����&N�q_�����      �   5   x���  ��[�Q�^�s�&��%&�`��Eb�Ep��bQ?�ہ`<	�     