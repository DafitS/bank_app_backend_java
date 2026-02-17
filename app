2026-02-16T19:07:09.278+01:00  INFO 19768 --- [main] com.example.demo.BankingAppApplication   : Starting BankingAppApplication using Java 21.0.9 with PID 19768 (C:\Users\OGMW3\Downloads\projekt bank\demo\demo\target\classes started by OGMW3 in C:\Users\OGMW3\Downloads\projekt bank)
2026-02-16T19:07:09.278+01:00  INFO 19768 --- [main] com.example.demo.BankingAppApplication   : No active profile set, falling back to 1 default profile: "default"
2026-02-16T19:07:10.069+01:00  INFO 19768 --- [main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2026-02-16T19:07:10.141+01:00  INFO 19768 --- [main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 63 ms. Found 4 JPA repository interfaces.
2026-02-16T19:07:10.799+01:00  INFO 19768 --- [main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 8080 (http)
2026-02-16T19:07:10.825+01:00  INFO 19768 --- [main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2026-02-16T19:07:10.826+01:00  INFO 19768 --- [main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.33]
2026-02-16T19:07:10.900+01:00  INFO 19768 --- [main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2026-02-16T19:07:10.900+01:00  INFO 19768 --- [main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 1581 ms
2026-02-16T19:07:11.159+01:00  INFO 19768 --- [main] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
2026-02-16T19:07:11.294+01:00  INFO 19768 --- [main] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 6.5.3.Final
2026-02-16T19:07:11.323+01:00  INFO 19768 --- [main] o.h.c.internal.RegionFactoryInitiator    : HHH000026: Second-level cache disabled
2026-02-16T19:07:11.655+01:00  INFO 19768 --- [main] o.s.o.j.p.SpringPersistenceUnitInfo      : No LoadTimeWeaver setup: ignoring JPA class transformer
2026-02-16T19:07:11.678+01:00  INFO 19768 --- [main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2026-02-16T19:07:11.899+01:00  INFO 19768 --- [main] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Added connection com.mysql.cj.jdbc.ConnectionImpl@45ad3cd8
2026-02-16T19:07:11.900+01:00  INFO 19768 --- [main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2026-02-16T19:07:12.641+01:00  INFO 19768 --- [main] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000489: No JTA platform available (set 'hibernate.transaction.jta.platform' to enable JTA platform integration)
2026-02-16T19:07:12.694+01:00  INFO 19768 --- [main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
2026-02-16T19:07:13.072+01:00  INFO 19768 --- [main] eAuthenticationProviderManagerConfigurer : Global AuthenticationManager configured with AuthenticationProvider bean with name customAuthenticationProvider
2026-02-16T19:07:13.073+01:00  WARN 19768 --- [main] r$InitializeUserDetailsManagerConfigurer : Global AuthenticationManager configured with an AuthenticationProvider bean. UserDetailsService beans will not be used for username/password login. Consider removing the AuthenticationProvider bean. Alternatively, consider using the UserDetailsService in a manually instantiated DaoAuthenticationProvider.
2026-02-16T19:07:13.293+01:00  WARN 19768 --- [main] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
2026-02-16T19:07:13.935+01:00  INFO 19768 --- [main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path '/'
2026-02-16T19:07:13.943+01:00  INFO 19768 --- [main] com.example.demo.BankingAppApplication   : Started BankingAppApplication in 5.09 seconds (process running for 5.37)
2026-02-16T19:09:06.510+01:00  INFO 19768 --- [SpringApplicationShutdownHook] j.LocalContainerEntityManagerFactoryBean : Closing JPA EntityManagerFactory for persistence unit 'default'
2026-02-16T19:09:06.511+01:00  INFO 19768 --- [SpringApplicationShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown initiated...
2026-02-16T19:09:06.515+01:00  INFO 19768 --- [SpringApplicationShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown completed.
