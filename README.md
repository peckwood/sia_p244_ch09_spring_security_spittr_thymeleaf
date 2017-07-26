### commit 28b24f4:

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

---

The next commit:

### Changes

- changed password length to char(60) for BC

### Features added

- 9.2.2 authenticating against databases
  - `/main/resources/schema.sql` - password changed to char(60)
  - `spittr.config.DataConfig`: configures data source
  - `spittr.config.SecurityConfig`: sql statements
- 9.3.1 Securing with Spring Expressions
  - `spittr.config.SecurityConfig.configure(HttpSecurity http)`
- 9.3.2 Enforcing channel security
  - tried but tomcat doesn't support
- 9.3.3 Preventing cross-site request forgery
  - CSRF protection is enabled by default
  - any request that is not GET, HEAD, OPTIONS or TRACE will be checked for CSRF token
  - `/main/webapp/WEB-INF/views/registerForm.html` has input tag for CSRF token
  - It looks like a login-in request doesn't need token as the customized login page doesn't have one and it works
- 9.4 Authenticating users
  - login
    - `/main/webapp/WEB-INF/views/login.html` is the login page
    - `spittr.web.WebConfig.addViewControllers()` binds /login GET with the login page
    - `spittr.config.SecurityConfig.configure(HttpSecurity http)` enables form login with `.formLogin()` and `.loginPage()` sets where to send the user if you want him to log in, `.loginProcessingUrl` sets which url should process the login request(Spring will bind the login implementation to this url)
  - logout
    - `.logoutUrl("/logout1")` sets the logout url, Spring will automatically implement logout funcionality
  - remember-me functionality
- 9.5.2 Working with Thymeleaf’s Spring Security dialect
  - added `SpringSecurityDialect` in `spittr.web.WebConfig.templateEngine(TemplateResolver)`
  - `/main/webapp/WEB-INF/views/home.html` used the tags, look for `sec:` attributes
  - `sec:authrize-url` doesn't seem to work

### What's ignored in this chapter

- 9.2.1 Working with an in-memory user store
- 9.2.3 Applying LDAP-backed authentication
- 9.2.4 Configuring a custom user service (that's for NoSQL databases)
- 9.4.2 Enabling HTTP Basic authentication (didn't try)
- 9.5.1 Using Spring Security’s JSP tag library (view was Thymeleaf, didn't try)

### How to run:

- /spitter/me and /spitters(POST) are authenticated requests, try them and get redirected to the custom login page
- login
- try again and it goes through
- check authentication with http://localhost:8080/sia_p244_ch09_spring_security_spittr_thymeleaf/

### Specify the login page:

 -  Specify its request path in SecurityConfig.java `http.formLogin.loginPage("/login")`
 -  map the request path with view in WebConfig.java 

```java
@Override
  public void addViewControllers(ViewControllerRegistry registry) {
	  registry.addViewController("/login").setViewName("login");
  }
```



 -  leave it to Spring to implement the login logic, Spring will encode the password as well

### Remaining questions

- When I am not logged in with Spring Security, my authentication's currentPrincipalName is `anonymousUser` and its method `.authenticated()` returns `true`. However, my configuration asks certain requests to be authenticated and anonymousUser was denied.https://stackoverflow.com/questions/45299789/spring-security-anonymoususer-blocked-from-authenticated-configuration


- [solved]https://stackoverflow.com/questions/45300759/spring-security-the-authentication-stays-after-server-restart#45301120

Resources

- https://github.com/thymeleaf/thymeleaf-extras-springsecurity