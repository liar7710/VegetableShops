create database store;
create user store identified by 'store';
grant all on store.* to store;
use store;
create table users (  #用户表
					   id int primary key auto_increment,
					   username varchar(40),
					   password varchar(100),
					   nickname varchar(40),
					   email varchar(100),
					   sex tinyint default 0,
					   privilege int default 1,  #权限
					   state int default 0, #激活状态
					   active_code varchar(100), #激活码
                       updatetime timestamp #创建时间
);

create table addr(  #用户收货地址表
					 id int primary key auto_increment,
					 address varchar(255), #收货地址
					 tel varchar(20), #联系方式
                     consignee varchar(100), #收货人
                     u_id int references users(id)  #外键, 关联用户表
	);

create table products(  #商品表
						 id int primary key auto_increment,
						 name varchar(40),
						 price double,
						 category varchar(40), #商品分类
						 pnum int , #商品库存
						 imgurl varchar(100),
	description varchar(255) #商品描述
	);
create table orders(  #订单表
					   id int primary key auto_increment,
					   total_prices double,
					   total_quanity int,
					   paystate int,  #支付状态
					   ordertime timestamp, #订单创建时间
					   user_id int references users(id),
	consignee varchar(100),  #收货人
                       tel varchar(20),
	address varchar(255) #收货地址
	);
create table goods(  #订单中的商品表
					  id int primary key ,
					  name varchar(40),
					  price double,
					  category varchar(40),
					  quantity int ,  #购买数量
					  imgurl varchar(100),
	description varchar(255)
	);
create table orderitem(
						  order_id int,
						  goods_id int,
						  product_id int,
						  buynum int ,
						  primary key(order_id,product_id), #联合主键,两列的值加在一起作为这张表的主键使用
							  foreign key(order_id) references orders(id),
						  foreign key(product_id) references goods(id)
);