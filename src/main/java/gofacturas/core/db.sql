CREATE TABLE  printers(
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  name varchar(300), state integer default 1,priority integer default 0,created_at date default CURRENT_TIMESTAMP,modification_at date
)
CREATE TABLE  settings(
  id INTEGER PRIMARY KEY AUTOINCREMENT,
   code varchar(100) not null,
   value text,
   type varchar(45),
  state integer default 1,
  created_at date default CURRENT_TIMESTAMP,
  modification_at date
)

insert into printers(name,state,priority,created_at) values("L775 SERIES RED",1,1,CURRENT_TIMESTAMP)

insert into settings(code,value,type) values('TOKEN','eyJ1c2VyIjoiYWRtaW5AZ29mYWN0dXJhcy5jb20iLCJpZCI6MSwiY29kZSI6InJvb3QifQ==','String');