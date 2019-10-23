# wutbiolab
### 武汉理工大学智能生物信息实验室
```sql
-- truncate wutbiolab.all_category;
-- insert into wutbiolab.all_category(discriminator, id, category) values ('lab', 1, '机构概况');	
-- insert into wutbiolab.all_category(discriminator, id, category) values ('lab', 2, '研究方向');	
-- insert into wutbiolab.all_category(discriminator, id, category) values ('lab', 3, '研究团队');	
-- insert into wutbiolab.all_category(discriminator, id, category) values ('lab', 4, '毕业生');	
-- insert into wutbiolab.all_category(discriminator, id, category) values ('scientific', 10, '论文发表');	
-- insert into wutbiolab.all_category(discriminator, id, category) values ('scientific', 11, '学术会议');	
-- insert into wutbiolab.all_category(discriminator, id, category) values ('news', 30, '头条新闻');	
-- insert into wutbiolab.all_category(discriminator, id, category) values ('news', 31, '综合新闻');	
-- insert into wutbiolab.all_category(discriminator, id, category) values ('news', 32, '科研动态');	
-- insert into wutbiolab.all_category(discriminator, id, category) values ('news', 33, '学术活动');	
-- insert into wutbiolab.all_category(discriminator, id, category) values ('notice', 34, '规章制度');	
-- insert into wutbiolab.all_category(discriminator, id, category) values ('notice', 35, '教育培养');	
-- insert into wutbiolab.all_category(discriminator, id, category) values ('notice', 36, '招聘招生');	
-- insert into wutbiolab.all_category(discriminator, id, category) values ('resource', 37, '公共数据集');	
-- insert into wutbiolab.all_category(discriminator, id, category) values ('resource', 38, '在线数据库');	
-- insert into wutbiolab.all_category(discriminator, id, category) values ('resource', 39, '软件下载');
-- insert into wutbiolab.all_category(discriminator, id, category) values ('graduate', 40, '毕业生');		
-- insert into wutbiolab.all_category(discriminator, id, category) values ('mentor', 41, '博士生导师');		
-- insert into wutbiolab.all_category(discriminator, id, category) values ('mentor', 42, '硕士生导师');		
-- insert into wutbiolab.all_category(discriminator, id, category) values ('academic', 43, '学术会议');	
-- truncate wutbiolab.home_info;
-- insert into wutbiolab.home_info(discriminator, context, publishStatus) values ('academic_picture', '[]', 0);
-- insert into wutbiolab.home_info(discriminator, context, publishStatus) values ('news_picture', '[]', 0);
-- truncate wutbiolab.user;
-- insert into wutbiolab.user(password,username) values ('$2a$10$.5M5RIXuuE0GWS.IvRWeWugTrUkAtxw1WkgeFLeff8QozRHiWj.Aa','admin');
-- insert into wutbiolab.user(password,username) values ('$2a$10$.5M5RIXuuE0GWS.IvRWeWugTrUkAtxw1WkgeFLeff8QozRHiWj.Aa','wp');
-- insert into wutbiolab.user(password,username) values ('$2a$10$.5M5RIXuuE0GWS.IvRWeWugTrUkAtxw1WkgeFLeff8QozRHiWj.Aa','gyq');
-- insert into wutbiolab.user(password,username) values ('$2a$10$.5M5RIXuuE0GWS.IvRWeWugTrUkAtxw1WkgeFLeff8QozRHiWj.Aa','wjy');



truncate wutbiolab.all_category;
insert into wutbiolab.all_category(discriminator, id, category) values ('lab', 1, 'Lab Introduction');	
insert into wutbiolab.all_category(discriminator, id, category) values ('lab', 2, 'Lab Introduction');	
insert into wutbiolab.all_category(discriminator, id, category) values ('lab', 3, 'Group Members');	
insert into wutbiolab.all_category(discriminator, id, category) values ('lab', 4, 'Former Members');	
insert into wutbiolab.all_category(discriminator, id, category) values ('scientific', 10, 'Publications');	
insert into wutbiolab.all_category(discriminator, id, category) values ('scientific', 11, 'Conference Deadlines');	
insert into wutbiolab.all_category(discriminator, id, category) values ('news', 30, 'Spotlight');	
insert into wutbiolab.all_category(discriminator, id, category) values ('news', 31, 'General News');	
insert into wutbiolab.all_category(discriminator, id, category) values ('news', 32, 'Academic News');	
insert into wutbiolab.all_category(discriminator, id, category) values ('news', 33, 'Academic Lecture');	
insert into wutbiolab.all_category(discriminator, id, category) values ('notice', 34, 'Lab Regulations');	
insert into wutbiolab.all_category(discriminator, id, category) values ('notice', 35, 'Education');	
insert into wutbiolab.all_category(discriminator, id, category) values ('notice', 36, 'Recruitment');	
insert into wutbiolab.all_category(discriminator, id, category) values ('resource', 37, 'Data set');	
insert into wutbiolab.all_category(discriminator, id, category) values ('resource', 38, 'Online Database');	
insert into wutbiolab.all_category(discriminator, id, category) values ('resource', 39, 'Software');
insert into wutbiolab.all_category(discriminator, id, category) values ('graduate', 40, 'Former Members');		
insert into wutbiolab.all_category(discriminator, id, category) values ('mentor', 41, '博士生导师');		
insert into wutbiolab.all_category(discriminator, id, category) values ('academic', 42, '学术会议');	
truncate wutbiolab.home_info;
insert into wutbiolab.home_info(discriminator, context, publishStatus) values ('academic_picture', '[]', 0);
insert into wutbiolab.home_info(discriminator, context, publishStatus) values ('news_picture', '[]', 0);
truncate wutbiolab.user;
insert into wutbiolab.user(password,username) values ('$2a$10$.5M5RIXuuE0GWS.IvRWeWugTrUkAtxw1WkgeFLeff8QozRHiWj.Aa','admin');
insert into wutbiolab.user(password,username) values ('$2a$10$.5M5RIXuuE0GWS.IvRWeWugTrUkAtxw1WkgeFLeff8QozRHiWj.Aa','wp');
insert into wutbiolab.user(password,username) values ('$2a$10$.5M5RIXuuE0GWS.IvRWeWugTrUkAtxw1WkgeFLeff8QozRHiWj.Aa','gyq');
insert into wutbiolab.user(password,username) values ('$2a$10$.5M5RIXuuE0GWS.IvRWeWugTrUkAtxw1WkgeFLeff8QozRHiWj.Aa','wjy');



INSERT INTO wutbiolab.`all_category`(discriminator, id, category,`categoryOrder`) VALUES ('scientific', '12', 'Patents', '3');
INSERT INTO wutbiolab.`all_category`(discriminator, id, category,`categoryOrder`) VALUES ('contactus', '13', 'Join us', '0');
UPDATE wutbiolab.`all_category` SET `discriminator`='lab',`categoryOrder`=1 WHERE id = 1;
UPDATE wutbiolab.`all_category` SET `discriminator`='scientific',`category`='Research projects',`categoryOrder`=1 WHERE id = 2;
UPDATE wutbiolab.`all_category` SET `category`='Our team',`categoryOrder`=2 WHERE id = 3;
UPDATE wutbiolab.`all_category` SET `category`='Lab photos',`categoryOrder`=3 WHERE id = 4;
UPDATE wutbiolab.`all_category` SET `categoryOrder`=2 WHERE id = 10;
UPDATE wutbiolab.`all_category` SET `discriminator`='resource',`category`='Lectures',`categoryOrder`=4 WHERE id = 11;
UPDATE wutbiolab.`all_category` SET `categoryOrder`=1 WHERE id = 30;
UPDATE wutbiolab.`all_category` SET `categoryOrder`=3 WHERE id = 31;
UPDATE wutbiolab.`all_category` SET `discriminator`='news22222' WHERE id = 32;
UPDATE wutbiolab.`all_category` SET `category`='Research activities',`categoryOrder`=2 WHERE id = 33;
UPDATE wutbiolab.`all_category` SET `categoryOrder`=2 WHERE id = 37;
UPDATE wutbiolab.`all_category` SET `category`='Database',`categoryOrder`=3 WHERE id = 38;
UPDATE wutbiolab.`all_category` SET `categoryOrder`=1 WHERE id = 39;
UPDATE wutbiolab.`all_category` SET `discriminator`='graduate',`category`='Lab photos',`categoryOrder`=1 WHERE id = 40;
UPDATE wutbiolab.`all_category` SET `categoryOrder`=1 WHERE id = 41;


INSERT INTO wutbiolab.scientific_research_detail (categoryId,context,publishStatus)
  SELECT categoryId,context,publishStatus
  FROM wutbiolab.lab_detail 
  WHERE categoryId = 2;

INSERT INTO wutbiolab.resource_detail (categoryId,context,publishStatus,publishDate,title,category)
  SELECT categoryId,context,publishStatus,publishDate,title,'Lectures' AS category
  FROM wutbiolab.scientific_research_detail  
  WHERE categoryId = 11;

UPDATE wutbiolab.news_detail SET category='Research activities' WHERE categoryId=33;
UPDATE wutbiolab.resource_detail SET category='Database' WHERE categoryId=38;

```