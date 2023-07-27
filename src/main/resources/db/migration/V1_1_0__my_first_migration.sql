create table if not exist person(
      id  int not null primary key identity,
      first_name varchar,
      last_name varchar,
      city varchar,
      country varchar,
      date date
);
create table if not exist user(
      userid  int not null primary key identity,
      login varchar,
      personid int,
      foreign key (personid) references person(id)
);
create table if not exist customertype(
      customer_type_id  int not null primary key identity,
      name varchar
);
create table if not exist customercategory(
      category_id  int not null primary key identity,
      name varchar
);
create table if not exist nature(
      nature_id  int not null primary key identity,
      name varchar
);
create table if not exist customer(
      customer_id int not null primary key identity,
      company_name varchar,
      fullname varchar,
      registration_date date,
      customer_type_id int,
      datekyc date,
      customer_cat int,
      nature int,
      contract_id int,
      foreign key (customer_type_id) references customertype(customer_type_id);
      foreign key (customer_cat) references customercategory(category_id);
      foreign key (nature) references nature(nature_id);
);
create table if not exist product(
      id  int not null primary key identity,
      name varchar
);
create table if not exist service_type(
     service_type_id  int not null primary key identity,
     name varchar
);
create table if not exist country(
     id  int not null primary key identity,
     name varchar,
     code varchar
);
create table if not exist assets(
     id  int not null primary key identity,
     name varchar,
     product_id int,
     active varchar,
     country_id int,
     foreign key (product_id) references product(id);
     foreign key (country_id) references country(id);
);
create table if not exist portfolio(
     portfolio_id  int not null primary key identity,
     code varchar,
     service_type int,
     currency varchar,
     officiel Boolean,
     risk_calculation Boolean,
     quantity int
     foreign key (service_type) references service_type_id(service_type_id);
);
create table if not exist contract(
     contract_id  int not null primary key identity,
     contract_name varchar,
     contract_code int,
     account_number int,
     registration_date date,
     portfolio_id int,
     advising_clause varchar,
     rate varchar
     foreign key (portfolio_id) references portfolio(portfolio_id);
);
create table if not exist contract_customer(
     contract_id  int not null primary key identity,
     customer_id  int not null primary key identity,
     foreign key (contract_id) references contract(contract_id);
     foreign key (customer_id) references customer(customer_id);
);
create table if not exist position(
     asset_id  int not null primary key identity,
     portfolio_id  int not null primary key identity,
     foreign key (asset_id) references assets(id);
     foreign key (portfolio_id) references portfolio(portfolio_id);
);
create table if not exist price(
     id  int not null primary key identity,
     name varchar,
     date date,
     asset_id int,
     price integer,
     foreign key (asset_id) references assets(id);
);
insert into product values (1,'BndCupZero'),(2,'BondOblig'),(3,'CashFlow'),(4,'CFuture'),(5,'Governement');

insert into service_type values (1,'intermediated'),(2,'managed'),(3,'advised not intermediated'),(4,'advised intermediated'),(5,'external consultancy');

insert into nature values (1,'minor'),(2,'normal'),(3,'disabled'),(4,'unsorted');

insert into country values (1,'morocco','ma'),(2,'french','fr');

insert into customercategory values (1,'Retailers'),(2,'Retail opt in'),(3,'Well informed investor'),(4,'Professionals'),(5,'Professionals opt in'),(6,'Qualifying'),(6,'unclassified')

insert into customertype values (1,'Legal Person'),(2,'Natural Person');