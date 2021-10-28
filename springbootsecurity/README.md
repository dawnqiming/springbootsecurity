一、WebSecurityConfigurerAdapter类：可以通过重载该类的三个configure()方法来制定Web安全的细节。

     1、configure(WebSecurity)：通过重载该方法，可配置Spring Security的Filter链。

     2、configure(HttpSecurity)：通过重载该方法，可配置如何通过拦截器保护请求。
二、保护路径的配置方法:
方法	                    能够做什么
access(String)	            如果给定的SpEL表达式计算结果为true，就允许访问
anonymous()	                允许匿名用户访问
authenticated()	            允许认证过的用户访问
denyAll()	                无条件拒绝所有访问
fullyAuthenticated()	    如果用户是完整认证的话（不是通过Remember-me功能认证的），就允许访问
hasAnyAuthority(String…)	如果用户具备给定权限中的某一个的话，就允许访问
hasAnyRole(String…)	        如果用户具备给定角色中的某一个的话，就允许访问
hasAuthority(String)	    如果用户具备给定权限的话，就允许访hasAnyAuthority问
hasIpAddress(String)	    如果请求来自给定IP地址的话，就允许访问
hasRole(String)	            如果用户具备给定角色的话，就允许访问
not()	                    对其他访问方法的结果求反
permitAll()	                无条件允许访问
rememberMe()	            如果用户是通过Remember-me功能认证的，就允许访问
antMatchers()               匹配路径

三、Spring Security 支持的所有SpEL表达式如下：
安全表达式　	                计算结果
authentication　　	        用户认证对象
denyAll　　	                结果始终为falsefullyAuthenticated
hasAnyRole(list of roles)　 如果用户被授权指定的任意权限，结果为true
hasRole(role)	            如果用户被授予了指定的权限，结果 为true
hasIpAddress(IP Adress)	    用户地址
isAnonymous()　　	        是否为匿名用户
isAuthenticated()　　	    不是匿名用户
isFullyAuthenticated　　	不是匿名也不是remember-me认证
isRemberMe()　　	        remember-me认证
permitAll	                始终true
principal	                用户主要信息对象

四、configure(AuthenticationManagerBuilder)：通过重载该方法，可配置user-detail（用户详细信息）服务。
配置用户详细信息的方法
方法	                        描述
accountExpired(boolean)	        定义账号是否已经过期
accountLocked(boolean)	        定义账号是否已经锁定
and()	                        用来连接配置
authorities(GrantedAuthority…)	授予某个用户一项或多项权限
authorities(List)	            授予某个用户一项或多项权限
authorities(String…)	        授予某个用户一项或多项权限
credentialsExpired(boolean)	    定义凭证是否已经过期
disabled(boolean)	            定义账号是否已被禁用
password(String)	            定义用户的密码
roles(String…)	                授予某个用户一项或多项角色

