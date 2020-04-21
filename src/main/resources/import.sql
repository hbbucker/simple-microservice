create extension  IF NOT EXISTS pgcrypto;

insert into cliente values(default, '1979-08-24', 'Av. Mato Grosso, 1290', 'HUGO');
insert into cliente values(default, '1980-02-20', 'Av. Afonso Pena,1000', 'LUIZ');
insert into cliente values(default, '2006-01-07', 'Rua Alagoas, 123', 'JOSÉ');

insert into produto values(default, 'ARROZ', 10);
insert into produto values(default, 'FEIJÃO', 8);
insert into produto values(default, 'JILÓ', 3.5);

insert into pedido values (default, CURRENT_DATE, 0, 1);
insert into itempedido values(default, 1, 10, 10, 1, 1);
insert into itempedido values(default, 2, 16, 8, 1, 2);

update pedido
   set valortotal = (select sum(valortotal) from itempedido where pedido_id = pedido.id)
 where pedido.id = 1;

insert into pedido values (default, CURRENT_DATE, 0, 1);
insert into itempedido values(default, 1, 10, 10, 2, 1);
insert into itempedido values(default, 2, 16, 8, 2, 2);

insert into usuario values (default, 'admin', crypt('admin' , gen_salt('bf', 8)), 'admin');
insert into usuario values (default, 'user', crypt('user' , gen_salt('bf', 8)), 'user');


update pedido
   set valortotal = (select sum(valortotal) from itempedido where pedido_id = pedido.id)
 where pedido.id = 2;
