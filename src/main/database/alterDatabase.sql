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
