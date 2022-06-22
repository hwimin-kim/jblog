-- scheme
show tables;
desc user;
desc blog;
desc category;
desc post;

-- select user
select * from user;
select count(id) from user where id = 'test01';
-- select blog 
select * from blog;

-- select category
select * from category;

select name, description
	from category
where blog_id = 'test04';

-- select left join (category, post) 
select a.no, a.name, a.description, a.blog_id, ifnull(b.categoryNum, 0) as categoryCount 
	from category a left join 
		(select category_no, count(category_no) as categoryNum from post group by category_no) b
	on a.no = b.category_no;

-- select post
select * from post;
select category_no, count(category_no) as categoryCount from post group by category_no;



