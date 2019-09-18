# wutbiolab
### 武汉理工大学智能生物信息实验室
```sql
truncate wutbiolab.all_category;

insert into wutbiolab.all_category(discriminator, id, category) values ('lab', 1, '机构概况');	
insert into wutbiolab.all_category(discriminator, id, category) values ('lab', 2, '研究方向');	
insert into wutbiolab.all_category(discriminator, id, category) values ('lab', 3, '研究团队');	
insert into wutbiolab.all_category(discriminator, id, category) values ('lab', 4, '毕业生');	
insert into wutbiolab.all_category(discriminator, id, category) values ('scientific', 10, '论文发表');	
insert into wutbiolab.all_category(discriminator, id, category) values ('scientific', 11, '学术会议');	


insert into wutbiolab.all_category(discriminator, id, category) values ('news', 30, '头条新闻');	
insert into wutbiolab.all_category(discriminator, id, category) values ('news', 31, '综合新闻');	
insert into wutbiolab.all_category(discriminator, id, category) values ('news', 32, '科研动态');	
insert into wutbiolab.all_category(discriminator, id, category) values ('news', 33, '学术活动');	
insert into wutbiolab.all_category(discriminator, id, category) values ('notice', 34, '规章制度');	
insert into wutbiolab.all_category(discriminator, id, category) values ('notice', 35, '教育培养');	
insert into wutbiolab.all_category(discriminator, id, category) values ('notice', 36, '招聘招生');	
insert into wutbiolab.all_category(discriminator, id, category) values ('resource', 37, '在线数据库');	
insert into wutbiolab.all_category(discriminator, id, category) values ('resource', 38, '公共数据集');	
insert into wutbiolab.all_category(discriminator, id, category) values ('resource', 39, '软件下载');
insert into wutbiolab.all_category(discriminator, id, category) values ('graduate', 40, '毕业生');		
insert into wutbiolab.all_category(discriminator, id, category) values ('mentor', 41, '博士生导师');		
insert into wutbiolab.all_category(discriminator, id, category) values ('mentor', 42, '硕士生导师');		
insert into wutbiolab.all_category(discriminator, id, category) values ('academic', 43, '学术会议');	

truncate wutbiolab.home_info;
insert into wutbiolab.home_info(discriminator, context, publishStatus) values ('academic_picture', '[]', 0);
insert into wutbiolab.home_info(discriminator, context, publishStatus) values ('news_picture', '[]', 0);


```