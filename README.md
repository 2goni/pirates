1. 설치 및 환경설정 가이드
    * Java 8
    * gradle 6.8.3
    * IntelliJ 2021.1
    * Spring Boot 2.4.5
   Git clone 후 실행 하시면됩니다. 

2. 테이블 생성 SQL
   * JPA 자동생성
   * create table business_times (business_times_id bigint not null auto_increment, close varchar(255), day varchar(255), open varchar(255), store_info_id bigint, primary key (business_times_id))
   * create table holidays (holidays_id bigint not null auto_increment, holiday date, store_info_id bigint, primary key (holidays_id))
   * create table store_info (store_info_id bigint not null auto_increment, address varchar(255), description varchar(255), level integer, name varchar(255), owner varchar(255), phone varchar(255), primary key (store_info_id))
   * alter table business_times add constraint FKqy6vgqr7mtluw1k4p624di1sy foreign key (store_info_id) references store_info (store_info_id) on delete cascade
   * alter table holidays add constraint FK9b7idhji3kco0hdfnqkn6b8h4 foreign key (store_info_id) references store_info (store_info_id) on delete cascade
   
3. API 사용방법
   * 점포추가 API
```json
{
"name": "해적수산",
"owner": "박해적",
"description": "노량진 시장 광어, 참돔 등 싱싱한 고퀄 활어 전문 횟집",
"level": 1,
"address": "서울 동작구 노량진동 13-8 노량진수산시장 활어 001",
"phone": "010-1234-1234",
"businessTimes": [
{"day": "Sunday", "open": "09:00", "close": "24:00"}, {"day": "Satuerday", "open": "09:00", "close": "24:00"}, {"day": "Wednesday", "open": "09:00", "close": "24:00"}, {"day": "Thursday", "open": "09:00", "close": "24:00"}, {"day": "Friday", "open": "09:00", "close": "24:00"} ]
}
```   
   상위 양식 json을 http:localhost:8080/addStore Post 전송
   DB에 json 양식에 맞게 저장됨

   * 점포 휴무일 등록 API
```json
{
"id":1,
"holidays": [
"2021-05-07",
"2021-05-08"]
}
```
   상위 양식 json을 http:localhost:8080/addHolidays Post 전송
   DB에 json 양식에 맞게 저장됨

   * 점포 목록 조회 API
```json
   [
   {
      "name": "해적수산",
      "description": "노량진 시장 광어, 참돔 등 싱싱한 고퀄 활어 전문 횟집",
      "level": 1,
      "businessStatus": "OPEN"
   },{
      "name": "인어수산",
      "description": "노량진 시장 광어, 참돔 등 싱싱한 고퀄 활어 전문 횟집",
      "level": 2,
      "businessStatus": "HOLIDAY"
   }
   
]
```
   http:localhost:8080/listStore Get 요청시 해당 json 반환  

   * 점포 상세 정보 조회 API
   
```json
{
   "id": 1
}
```
상위 json 양식으로 http:localhost:8080/storeDetail Post 요청시 
```json
{
  "id": 1,
  "name": "해적수산",
  "description": "노량진 시장 광어, 참돔 등 싱싱한 고퀄 활어 전문 횟집",
  "level": 1,
  "address": "서울 동작구 노량진동 13-8 노량진수산시장 활어 001",
  "phone": "010-1234-1234",
  "businessDays": [
    {
      "day": "Friday",
      "open": "09:00",
      "close": "24:00",
      "status": "OPEN"
    },
    {
      "day": "Saturday",
      "open": "09:00",
      "close": "24:00",
      "status": "OPEN"
    },
    {
      "day": "Sunday",
      "open": "09:00",
      "close": "24:00",
      "status": "OPEN"
    }
  ]
}
```
   상위 json 반환
   
   * 점포 삭제 API
```json
{
   "id": 1
}
```
   상위 json 양식으로 http:localhost:8080/deleteStore Post 요청시 
   해당 id값 DB삭제
   