Drop database if exists KrowdFunFund
go
Create database KrowdFunFund
go
Use KrowdFunFund;
go
drop table if exists ServiceWalletTransaction
drop table if exists Payment
drop table if exists PeriodRevenue
drop table if exists InvestmentTransaction
drop table if exists ProjectWallet
drop table if exists ServiceWallet
drop table if exists MonthlyReport
drop table if exists Stage
drop table if exists Investment
drop table if exists Risk
drop table if exists Project
drop table if exists RiskType
drop table if exists Field
drop table if exists Area
drop table if exists PersonalWalletTransaction
drop table if exists PersonalWallet
drop table if exists WalletType
drop table if exists ProjectOwner
drop table if exists Investor
drop table if exists [Usertbl]
drop table if exists Roletbl

create table Roletbl(
    role_id varchar(20),
    role_name varchar(100),
    [description] nvarchar(1000),
    Primary key(role_id)
)
create table [Usertbl](
    [user_id] int Identity(1, 1),
    role_id varchar(20) not null,
    full_name nvarchar(200),
    email varchar(200),
    phone varchar(20),
    avatar varchar(1000),
    id_card varchar(20),
    gender varchar(10),
    birthdate datetime,
    tax_identification varchar(20),
    [address] nvarchar(200),
    bank_name nvarchar(50),
    bank_account nvarchar(20),
    momo varchar(20),
    created_at datetime not null,
    [status] varchar(50) not null,
    [enabled] bit not null,
    Primary key([user_id]),
    Foreign key(role_id) references RoleTbl(role_id)
)
create table Investor (
    [user_id] int,
    Primary key([user_id]),
    Foreign key(user_id) references [Usertbl](user_id)
)
create table ProjectOwner (
    [user_id] int,
    Primary key([user_id]),
    Foreign key(user_id) references [Usertbl](user_id)
)
create table WalletType(
    wallet_type_id varchar(100),
    wallet_type_name varchar(100) not null,
    [wallet_type_description] varchar(1000),
    mode varchar(20),
    Primary Key(wallet_type_id)
)
create table PersonalWallet(
    wallet_id uniqueidentifier ,
    wallet_type_id varchar(100) not null ,
    [user_id] int ,
    balance decimal not null default 0 ,
    created_at datetime not null,
    Primary key(wallet_id),
    Foreign key([user_id]) references [Usertbl]([user_id]),
    Foreign key(wallet_type_id) references WalletType(wallet_type_id)
)
create table PersonalWalletTransaction(
    personal_wallet_transaction_id int Identity(1, 1),
    from_wallet_id uniqueidentifier  ,
    to_wallet_id uniqueidentifier,
    created_by int,
    created_at datetime not null,
    amount decimal not null,
    fee decimal not null default 0 ,
    [personal_wallet_description] nvarchar(1000),
    Primary Key(personal_wallet_transaction_id),
    Foreign Key(from_wallet_id) references PersonalWallet(wallet_id),
    Foreign Key(to_wallet_id) references PersonalWallet(wallet_id),
    Foreign Key(created_by) references [Usertbl]([user_id])
)
create table Area(
    area_id int Identity(1, 1),
    city nvarchar(100),
    district nvarchar(100),
    details nvarchar(1000) ,
    [status] bit default 1
    Primary Key(area_id)
)
create table Field(
    field_id int Identity(1, 1),
    [name] nvarchar(100),
    [field_description] text,
    [status] bit not null default 1,
    primary key(field_id)
)
create table RiskType(
    risk_type_id int Identity(1, 1),
    [name] nvarchar(200),
    [risk_type_description] nvarchar(1000),
    [status] bit not null default 1
    Primary Key(risk_type_id)
)
create table Project(
    project_id int Identity(1, 1),
    created_by int not null,
    created_at datetime not null,
    field_id int not null,
    area_id int,
    investment_target_capital decimal not null default 0,
    invested_capital decimal not null default 0,
    shared_revenue decimal not null,
    multiplier decimal not null,
    paid_amount decimal not null,
    remaining_amount decimal not null,
    number_of_stage int not null,
    duration int not null,
    [start_date] datetime not null,
    end_date datetime not null,
    [image] varchar(1000),
    [project_description] nvarchar(100),
    business_license varchar(50),
    approved_by int,
    approved_at datetime,
    updated_by int,
    updated_at datetime,
    [status] varchar(20) not null default 'PENDING'
    Primary Key(project_id),
    Foreign key(created_by) references [Usertbl]([user_id]),
    Foreign key(approved_by) references [Usertbl]([user_id]),
    Foreign key(updated_by) references [Usertbl]([user_id]),
    Foreign key(area_id) references Area(area_id),
    Foreign key(field_id) references Field(field_id)
)
create table Risk(
    risk_id int Identity(1, 1),
    risk_type_id int not null,
    project_id int not null,
    [name] nvarchar(200),
    [description] nvarchar(1000)
    Primary Key(risk_id)
    Foreign Key(risk_type_id) references RiskType(risk_type_id),
    Foreign Key(project_id) references Project(project_id)
)
create table Investment(
    investment_id uniqueidentifier,
    [user_id] int not null,
    project_id int not null,
    total_money decimal not null,
    created_at datetime not null,
    updated_at datetime not null,
    [contract] varchar(1000) not null,
    [status] varchar(50) not null default 'SUCCEED'
    Primary key(investment_id),
    Foreign key([user_id]) references [Usertbl]([user_id]),
    Foreign key(project_id) references Project(project_id)
)
create table Stage(
    stage_id int Identity(1, 1),
    project_id int not null,
    [name] nvarchar(50),
    [description] text,
    [start_date] datetime not null,
    [end_date] datetime not null,
    [is_over_due] bit not null default 0 ,
    [status] bit not null default 1,
    primary key(stage_id),
    foreign key(project_id) references Project(project_id)
)
create table MonthlyReport(
    monthly_report_id int Identity(1, 1),
    stage_id int not null,
    revenue decimal not null,
    created_by int not null,
    created_at datetime not null ,
    updated_by int not null ,
    updated_at datetime not null,
    report varchar(1000) ,
    [status] bit not null default 1
    Primary key(monthly_report_id),
    Foreign key(created_by) references [Usertbl]([user_id]),
    Foreign key(updated_by) references [Usertbl]([user_id]),
)
create table ServiceWallet(
    service_wallet_id uniqueidentifier ,
    investment_id uniqueidentifier  not null,
    wallet_type_id varchar(100) not null,
    stage_id int not null,
    balance decimal not null default 0,
    created_at datetime not null,
    [status] bit not null default 1
    Primary key(service_wallet_id),
    Foreign key(investment_id) references Investment(investment_id),
    Foreign key(stage_id) references Stage(stage_id)
)
create table ProjectWallet(
    project_wallet_id uniqueidentifier,
    wallet_type_id varchar(100) not null,
    project_id int not null ,
    balance decimal not null default 0 ,
    updated_at datetime,
    [status] bit not null default 1,
    Primary key(project_wallet_id),
    Foreign key(wallet_type_id) references WalletType(wallet_type_id),
    Foreign key(project_id) references  Project(project_id)
)
create table InvestmentTransaction(
    investment_transaction_id int Identity(1, 1),
    personal_wallet_id uniqueidentifier not null,
    project_wallet_id uniqueidentifier not null,
    investment_id uniqueidentifier,
    [type] varchar(50) not null,
    created_by int not null,
    created_at datetime not null,
    amount decimal not null,
    [description] text,
    primary key(investment_transaction_id),
    Foreign key(personal_wallet_id) references PersonalWallet(wallet_id),
    Foreign key(investment_id) references Investment(investment_id),
    Foreign key(project_wallet_id) references ProjectWallet(project_wallet_id)
)
create table PeriodRevenue(
    period_revenue_id int Identity(1, 1),
    stage_id int not null,
    actual_amount decimal not null,
    shared_amount decimal not null,
    paid_amount decimal not null,
    [optimistic_expected_amount] decimal not null,
    pessimistic_expected_amount decimal not null,
    normal_expected_amount decimal not null,
    optimistic_expected_ratio decimal not null,
    pessimistic_expected_ratio decimal not null,
    created_by int not null,
    created_at datetime not null,
    updated_by int not null,
    updated_at datetime not null,
    [status] bit not null default 1,
    Primary key(period_revenue_id),
    Foreign key(stage_id) references Stage(stage_id),
    Foreign key(created_by) references [Usertbl]([user_id]),
    Foreign key(updated_by) references [Usertbl]([user_id])
)
create table Payment(
    payment_id int Identity(1, 1),
    period_revenue_id int not null,
    investment_id uniqueidentifier not null,
    amount decimal not null,
    [description] nvarchar(1000),
    created_at datetime not null,
    [status] bit not null default 1,
    Primary key(payment_id),
    Foreign key(period_revenue_id) references PeriodRevenue(period_revenue_id),
    Foreign key(investment_id) references Investment(investment_id)
)

create table ServiceWalletTransaction(
    service_wallet_transaction_id int Identity(1, 1),
    service_wallet_id uniqueidentifier not null,
    created_by int not null,
    created_at datetime not null,
    amount decimal not null,
    [description] nvarchar(1000),
    Primary key(service_wallet_transaction_id),
    Foreign key(service_wallet_id) references ServiceWallet(service_wallet_id),
    Foreign key(created_by) references [Usertbl]([user_id]),
)

insert into [Roletbl] (role_id, role_name, description)
values
('ADMIN', 'Admin', ''),
('INVESTOR', 'Investor', ''),
('PO', 'Project Owner', '')

insert into [Usertbl] (role_id, email, phone, created_at, status, enabled)
values ('ADMIN', 'viettien1602@gmail.com', '0123456789', getdate(), 'APPROVED', 1)

 Alter table Project 
 Add project_name varchar(255)
 Alter table Project 
 Add brand varchar(255)
