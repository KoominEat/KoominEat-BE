------------------------------------------------------------
-- 1. 카테고리 등록
------------------------------------------------------------
INSERT INTO store_category (name, image) VALUES
                                      ('카페', '/categories/cafe.png'),
                                      ('햄버거', '/categories/burger.png'),
                                      ('빵', '/categories/bread.png'),
                                      ('분식/식사', '/categories/bunsik.png'),
                                      ('샐러드/샌드위치', '/categories/salad.png');
------------------------
-- location 등록

INSERT INTO location (name) VALUES
                                ('본부관'),
                                ('북악관'),
                                ('법학관'),
                                ('예술관'),
                                ('복지관'),
                                ('공학관'),
                                ('성곡도서관'),
                                ('과학관');


------------------------------------------------------------
-- 2. 스토어 등록  (x,y 제거)
-- location 은 건물명 그대로 유지
------------------------------------------------------------

-- ===== 카페 =====
INSERT INTO store(name, location_id, category_id, image, background_image) VALUES
                                                   ('카페-K', 1, 1, '/stores/mir.png', '/stores/mir-back.jpg'),
                                                   ('카페미르(북악)', 2, 1, '/stores/mir.png', '/stores/mir-back.jpg'),
                                                   ('공차', 2, 1, '/stores/gong.jpg', '/stores/gong-back.jpg'),
                                                   ('스무디킹', 3, 1, '/stores/smoothie-king.png', '/stores/smoothie-king-back.jpg'),
                                                   ('Cafe Namu(예술관)', 4, 1, '/stores/cafe-namu.png', '/stores/mir-back.jpg'),
                                                   ('Cafe Namu(복지관)', 5, 1, '/stores/cafe-namu.png', '/stores/mir-back.jpg'),
                                                   ('카페미르(공학관)', 6, 1, '/stores/mir.png', '/stores/mir-back.jpg'),
                                                   ('할리스커피', 7, 1, '/stores/hollys.jpg', '/stores/hollys-back.webp'),
                                                   ('카페(과학관)', 8, 1, '/stores/mir.png', '/stores/mir-back.jpg');

-- ===== 햄버거 =====
INSERT INTO store(name, location_id, category_id, image, background_image) VALUES
                                                   ('버거운버거', 5, 2, '/stores/burgeronburger.jpg', ''),
                                                   ('맘스터치', 6, 2, '/stores/momstouch.jpg', '/stores/momstouch-back.jpg');

-- ===== 빵 =====
INSERT INTO store(name, location_id, category_id, image, background_image) VALUES
    ('플레이스엔', 5, 3, '/stores/placeN.jpg', '/stores/placeN-back.jpg');

-- ===== 분식/식사 =====
INSERT INTO store(name, location_id, category_id, image, background_image) VALUES
                                                   ('K-GIMBOB+', 2, 4, '/stores/kgimbob.png', '/stores/kgimbob-back.png'),
                                                   ('K-BOB+', 5, 4, '/stores/kbob.webp', '');

-- ===== 샐러드/샌드위치 =====
INSERT INTO store(name, location_id, category_id, image, background_image) VALUES
                                                   ('써브웨이', 2, 5, '/stores/subway.jpg', '/stores/subway-back.jpg'),
                                                   ('샐러디', 5, 5, '/stores/salady.png', '');


------------------------------------------------------------
-- 3. 메뉴 등록
------------------------------------------------------------

-- ===== 카페-K =====
INSERT INTO menu_item(name, price, store_id, image) VALUES
                                                ('카라멜 마키야또', 6100, 1, '/menus/1cmd.png'),
                                                ('카페모카', 5700, 1, '/menus/2cmk.png'),
                                                ('바닐라 라떼', 6100, 1, '/menus/3bld.png'),
                                                ('카페 라떼', 5200, 1, '/menus/4ld.png'),
                                                ('카푸치노', 5200, 1, '/menus/5cpq.png'),
                                                ('아메리카노', 4500, 1, '/menus/6ame.png'),
                                                ('디카페인 아메리카노', 4900, 1, '/menus/7decame.png'),
                                                ('에스프레소', 4000, 1, '/menus/8esp.png');


-- ===== 카페미르(북악) =====
INSERT INTO menu_item(name, price, store_id, image) VALUES
                                                ('카라멜 마키야또', 6100, 2, '/menus/1cmd.png'),
                                                ('카페모카', 5700, 2, '/menus/2cmk.png'),
                                                ('바닐라 라떼', 6100, 2, '/menus/3bld.png'),
                                                ('카페 라떼', 5200, 2, '/menus/4ld.png'),
                                                ('카푸치노', 5200, 2, '/menus/5cpq.png'),
                                                ('아메리카노', 4500, 2, '/menus/6ame.png'),
                                                ('디카페인 아메리카노', 4900, 2, '/menus/7decame.png'),
                                                ('에스프레소', 4000, 2, '/menus/8esp.png');

-- ===== 공차 =====
INSERT INTO menu_item(name, price, store_id, image) VALUES
                                                ('블랙 밀크티', 4000, 3, '/menus/17bmt.png'),
                                                ('얼그레이 밀크티', 4000, 3, '/menus/18amt.png'),
                                                ('타로 밀크티', 4000, 3, '/menus/19tmt.png'),
                                                ('우롱티', 3500, 3, '/menus/20urt.png'),
                                                ('망고 요구르트', 5000, 3, '/menus/21myr.png'),
                                                ('딸키 쿠키 스무디', 5300, 3, '/menus/22dks.png'),
                                                ('청포도 스무디', 4800, 3, '/menus/23cps.png'),
                                                ('제주 그린 밀크티', 4700, 3, '/menus/24jgmt.png');

-- ===== 스무디킹 =====
-- 이미지 없음.
INSERT INTO menu_item(name, price, store_id) VALUES
                                                 ('엔젤 푸드', 5500, 4),
                                                 ('스트로베리 익스트림', 5500, 4),
                                                 ('망고 페스티벌', 5500, 4),
                                                 ('레몬 트위스트 스트로베리', 5500, 4),
                                                 ('오렌지 레볼루션', 5500, 4),
                                                 ('오렌지 카밤', 4900, 4),
                                                 ('스트로베리 키스', 4900, 4),
                                                 ('그릭요거트 스트로베리 블루베리', 5500, 4);

-- ===== Cafe Namu(예술관) =====
INSERT INTO menu_item(name, price, store_id, image) VALUES
                                                ('카라멜 마키야또', 6100, 5, '/menus/1cmd.png'),
                                                ('카페모카', 5700, 5, '/menus/2cmk.png'),
                                                ('바닐라 라떼', 6100, 5, '/menus/3bld.png'),
                                                ('카페 라떼', 5200, 5, '/menus/4ld.png'),
                                                ('카푸치노', 5200, 5, '/menus/5cpq.png'),
                                                ('아메리카노', 4500, 5, '/menus/6ame.png'),
                                                ('디카페인 아메리카노', 4900, 5, '/menus/7decame.png'),
                                                ('에스프레소', 4000, 5, '/menus/8esp.png');

-- ===== Cafe Namu(복지관) =====
INSERT INTO menu_item(name, price, store_id, image) VALUES
                                                ('카라멜 마키야또', 6100, 6, '/menus/1cmd.png'),
                                                ('카페모카', 5700, 6, '/menus/2cmk.png'),
                                                ('바닐라 라떼', 6100, 6, '/menus/3bld.png'),
                                                ('카페 라떼', 5200, 6, '/menus/4ld.png'),
                                                ('카푸치노', 5200, 6, '/menus/5cpq.png'),
                                                ('아메리카노', 4500, 6, '/menus/6ame.png'),
                                                ('디카페인 아메리카노', 4900, 6, '/menus/7decame.png'),
                                                ('에스프레소', 4000, 6, '/menus/8esp.png');

-- ===== 카페미르(공학관) =====
INSERT INTO menu_item(name, price, store_id, image) VALUES
                                                ('카라멜 마키야또', 6100, 7, '/menus/1cmd.png'),
                                                ('카페모카', 5700, 7, '/menus/2cmk.png'),
                                                ('바닐라 라떼', 6100, 7, '/menus/3bld.png'),
                                                ('카페 라떼', 5200, 7, '/menus/4ld.png'),
                                                ('카푸치노', 5200, 7, '/menus/5cpq.png'),
                                                ('아메리카노', 4500, 7, '/menus/6ame.png'),
                                                ('디카페인 아메리카노', 4900, 7, '/menus/7decame.png'),
                                                ('에스프레소', 4000, 7, '/menus/8esp.png');

-- ===== 할리스커피 =====
INSERT INTO menu_item(name, price, store_id, image) VALUES
                                                ('카라멜 마키야또', 6100, 8, '/menus/1cmd.png'),
                                                ('카페모카', 5700, 8, '/menus/2cmk.png'),
                                                ('바닐라 라떼', 6100, 8, '/menus/3bld.png'),
                                                ('카페 라떼', 5200, 8, '/menus/4ld.png'),
                                                ('카푸치노', 5200, 8, '/menus/5cpq.png'),
                                                ('아메리카노', 4500, 8, '/menus/6ame.png'),
                                                ('디카페인 아메리카노', 4900, 8, '/menus/7decame.png'),
                                                ('에스프레소', 4000, 8, '/menus/8esp.png');

-- ===== 카페(과학관) =====
INSERT INTO menu_item(name, price, store_id, image) VALUES
                                                ('카라멜 마키야또', 6100, 9, '/menus/1cmd.png'),
                                                ('카페모카', 5700, 9, '/menus/2cmk.png'),
                                                ('바닐라 라떼', 6100, 9, '/menus/3bld.png'),
                                                ('카페 라떼', 5200, 9, '/menus/4ld.png'),
                                                ('카푸치노', 5200, 9, '/menus/5cpq.png'),
                                                ('아메리카노', 4500, 9, '/menus/6ame.png'),
                                                ('디카페인 아메리카노', 4900, 9, '/menus/7decame.png'),
                                                ('에스프레소', 4000, 9, '/menus/8esp.png');


------------------------------------------------------------
-- ===== 햄버거 =====
------------------------------------------------------------

-- 버거운버거
-- 이미지 없음.
INSERT INTO menu_item(name, price, store_id) VALUES
                                                 ('버거운 치킨버거', 4600, 10),
                                                 ('갈릭마요 치킨버거', 5500, 10),
                                                 ('딥치즈 치킨버거', 5000, 10),
                                                 ('화이트어니언 치킨버거', 5300, 10),
                                                 ('데리마요 치킨버거', 5000, 10),
                                                 ('핫치킨버거', 5000, 10),
                                                 ('불닭치즈 카츠버거', 8000, 10),
                                                 ('스노우 치킨버거', 5000, 10);

-- 맘스터치
INSERT INTO menu_item(name, price, store_id, image) VALUES
                                                ('인크레더블버거', 4700, 11, '/menus/81incre.png'),
                                                ('새우불고기버거', 4500, 11, '/menus/82sub.png'),
                                                ('딥치즈버거', 3800, 11, '/menus/83deep.png'),
                                                ('화이트갈릭버거', 3900, 11, '/menus/84white.png'),
                                                ('휠렛버거', 3400, 11, '/menus/85whilet.png'),
                                                ('싸이플렉스버거', 11000, 11, '/menus/86flec.png'),
                                                ('싸이버거', 3200, 11, '/menus/87psy.png'),
                                                ('불싸이버거', 3400, 11, '/menus/88firepsy.png');



------------------------------------------------------------
-- ===== 빵 (플레이스엔) =====
------------------------------------------------------------
-- 이미지 없음.
INSERT INTO menu_item(name, price, store_id) VALUES
                                                 ('연유바게트', 3000, 12),
                                                 ('감자바게트', 4000, 12),
                                                 ('보스턴 오꼬노미', 3800, 12),
                                                 ('대파 명란 베이컨', 3800, 12),
                                                 ('메론빵', 3000, 12),
                                                 ('블루베리소보로', 3000, 12),
                                                 ('뺑오 쇼콜라', 4000, 12),
                                                 ('부추 베이글', 3800, 12);


------------------------------------------------------------
-- ===== 분식/식사 =====
------------------------------------------------------------

-- K-GIMBOB+
INSERT INTO menu_item(name, price, store_id, image) VALUES
                                                ('국민김밥', 3300, 13, '/menus/97kimbob.png'),
                                                ('치즈김밥', 3900, 13, '/menus/98cheesekimbob.png'),
                                                ('참치김밥', 4000, 13, '/menus/99chamkimbob.png'),
                                                ('크래미김밥', 4300, 13, '/menus/100crekimbob.png'),
                                                ('국민우동', 5000, 13, '/menus/101wu.png'),
                                                ('떡볶이', 3500, 13, '/menus/102dbg.png'),
                                                ('국물떡볶이', 3500, 13, '/menus/103gookdbg.png'),
                                                ('짜장국물떡볶이', 3000, 13, '/menus/104jadbg.png');


-- K-BOB+
-- 이미지 없음.
INSERT INTO menu_item(name, price, store_id) VALUES
                                                 ('통스팸김피덮밥', 6500, 14),
                                                 ('핵불닭덮밥', 7500, 14),
                                                 ('돈가스마요덮밥', 7500, 14),
                                                 ('우삼겹 오므라이스', 9000, 14),
                                                 ('흑돼지스팸김치찌개', 7900, 14),
                                                 ('육회비빔밥', 7700, 14),
                                                 ('우삼겹비빔밥', 7900, 14),
                                                 ('통큰소세지 오므라이스', 8000, 14);


------------------------------------------------------------
-- ===== 샐러드/샌드위치 =====
------------------------------------------------------------

-- 써브웨이
INSERT INTO menu_item(name, price, store_id, image) VALUES
                                                ('에그마요', 4300, 15, '/menus/113eggmayo.png'),
                                                ('햄', 4700, 15, '/menus/114ham.png'),
                                                ('치킨 데리야끼', 5400, 15, '/menus/115chicken.png'),
                                                ('안창비프', 6400, 15, '/menus/116anchang.png'),
                                                ('스파이시 이탈리안', 5600, 15, '/menus/117spicy_italian.png'),
                                                ('비엘티', 5300, 15, '/menus/118blt.png'),
                                                ('폴드포크', 5900, 15, '/menus/119ppk.png'),
                                                ('튜나', 5500, 15, '/menus/120tuna.png');


-- 샐러디
-- 이미지 없음.
INSERT INTO menu_item(name, price, store_id) VALUES
                                                 ('시저치킨 샐러디', 5100, 16),
                                                 ('멕시칸 샐러디', 5300, 16),
                                                 ('연어 샐러디', 6500, 16),
                                                 ('탄단지 샐러디', 6200, 16),
                                                 ('차돌박이 웜볼', 6700, 16),
                                                 ('칠리베이컨 웜볼', 6200, 16),
                                                 ('차돌박이 웜랩', 5800, 16),
                                                 ('칠리베이컨 웜랩', 5300, 16);