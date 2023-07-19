Use KrowdFunFund;
go

-- Updated by Tien
alter table [Usertbl]
alter column birthdate date

-- Updated by Thuong
Alter table Project
    Add project_name varchar(255)
Alter table Project
    Add brand varchar(255)

-- Updated by Tien
insert into [WalletType]
values
('SYSTEM_WALLET', 'System wallet', 'System Wallet is managed be Admins to obtain commission', ''),
('GENERAL_WALLET', 'General wallet', 'General Wallet is for deposit and withdraw money', ''),
('COLLECTION_WALLET', 'Collection wallet', 'Collection Wallet is for receiving money, withdraw or transact to General Wallet', ''),
('PROJECT_INVESTMENT_WALLET', 'Project investment wallet', 'Project investment wallet is possessed by each project', ''),
('PROJECT_PAYMENT_WALLET', 'Project payment wallet', 'Project payment wallet is possessed by each project', ''),
('SERVICE_WALLET', 'Service wallet', 'Service Wallet is for investor to use service of each project', '')
-- update By Thuong
alter table [Stage]
    Add stage_status varchar(255)

-- updated by Tien
insert into PersonalWallet (wallet_id, wallet_type_id, balance, created_at)
values ('e7e98621-b16a-4226-a31d-bdf7b8cf0dc2', 'SYSTEM_WALLET', 0, GETDATE())

-- updated by Tien on 5/7/2023
insert into Field (name, field_description, status)
values
('Drink', 'Drink field', 1),
('Food', 'Food field', 1),
('Medical', 'Medical field', 1),
('Studying', 'Studying field', 1),
('Technology', 'Technology field', 1),
('Real Estate', 'Real Estate field', 1)

insert into Area (city, district, details, status)
values
('HCM', 'Q1', 'Ho Chi Minh', 1),
('HCM', 'Q3', 'Ho Chi Minh', 1),
('HCM', 'Q4', 'Ho Chi Minh', 1),
('HCM', 'Q5', 'Ho Chi Minh', 1),
('HCM', 'Q6', 'Ho Chi Minh', 1),
('HCM', 'Q7', 'Ho Chi Minh', 1),
('HCM', 'Q8', 'Ho Chi Minh', 1),
('HCM', 'Q10', 'Ho Chi Minh', 1),
('HCM', 'Q11', 'Ho Chi Minh', 1),
('HCM', 'Q12', 'Ho Chi Minh', 1)

-- updated by Tien on 6/7/2023
alter table Project
alter column project_description text

-- updated by Tien on 14/7/2023
create table DepositTransaction(
    deposit_transaction_id int Identity(1, 1),
    to_wallet_id uniqueidentifier,
    created_by int,
    created_at datetime not null,
    amount decimal not null,
    order_id varchar(255) not null,
    verified bit not null default 0,
    Primary Key(deposit_transaction_id),
    Foreign Key(to_wallet_id) references PersonalWallet(wallet_id),
    Foreign Key(created_by) references [Usertbl]([user_id])
)

