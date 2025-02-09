INSERT INTO stock_price_monitoring.role (role_type)
VALUES
    ('USER'),
    ('ADMIN');

INSERT INTO stock_price_monitoring.permission (name)
VALUES
    ('get_user_stock_info_permission'),
    ('save_stock_info_request_permission');


insert into stock_price_monitoring.role_permission(role_id, permission_id)
select r.id, p.id
from stock_price_monitoring.role r
         join stock_price_monitoring.permission p
              on (r.role_type = 'USER'
                  and p.name = 'get_user_stock_info_permission'
                  );

insert into stock_price_monitoring.role_permission(role_id, permission_id)
select r.id, p.id
from stock_price_monitoring.role r
         join stock_price_monitoring.permission p
              on (r.role_type = 'USER'
                  and p.name = 'save_stock_info_request_permission'
                  );



