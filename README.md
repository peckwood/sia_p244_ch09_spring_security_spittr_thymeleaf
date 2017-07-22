



**Database(H2) based Spring Security, intercepts ALL requests, uses CSRF**



This project is based on [`sia_p187_chap6_web_view_thymeleaf`](https://github.com/peckwood/sia_p187_chap6_web_view_thymeleaf) and added Spring Security features to it.

#### Changes introduced by Security

- some security configuration files
  - spittr.config.SecurityWebApplicationInitializer
  - spittr.config.SecurityConfig
- in forms, added csrf input tags
- SpitterWebInitializer's `characterEncodingFilter` is moved to `SecurityConfig` to ensure it is run before Security filters
- /main/resources/schema.sql: add SQL for inserting the default user fxkill2000/123123 to the in-memory database H2
- added attributes `role` and `enable` to model `Spitter`

Log in username is fxkill2000, password is 123123

