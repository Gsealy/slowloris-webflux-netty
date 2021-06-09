# Slowloris for Webflux

## What is Slowloris？

> Copy from wikipedia
>
> link: https://en.wikipedia.org/wiki/Slowloris_(computer_security)

**Slowloris** is a type of denial of service attack tool which allows a single machine to take down another machine's web server with minimal bandwidth and side effects on unrelated services and ports.

Slowloris tries to keep many connections to the target web server open and hold them open as long as possible. It accomplishes this by opening connections to the target web server and sending a partial request. Periodically, it will send subsequent HTTP headers, adding to, but never completing, the request. Affected servers will keep these connections open, filling their maximum concurrent connection pool, eventually denying additional connection attempts from clients.

The program was named after slow lorises, a group of primates which are known for their slow movement.

## How to reproduce

1. start server

   ```
   mvn spring-boot:run
   ```

   server will run on `9000` port.

2. use tool scan web server

   you can use [slowhttptest](https://github.com/shekyan/slowhttptest) or other tools scan `http://localhost:9000/`

3. remove ServerConfig `@Configuration` annotation and restart server, re-scan it.

   