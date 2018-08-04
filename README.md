# SpringCloud是什么？能解决什么问题？
> 当我们只是一个单体服务的时候，我们自然不用考虑SpringCloud。但是随着业务体量的不断变大，一些高并发情况的出现，发现单体服务已经不能满足我们的需求。这个时候我们考虑将服务进行拆分，拆分成很多的微服务，慢慢的，微服务会越来越多，几十个甚至几百个，这就给运维人员和开发人员带来了很大的挑战。怎么样有效的更好的将这些微服务管理起来呢？这个时候就出现了很多优秀的rpc框架，其中开源框架中最成熟，功能最强大的就是SpringCloud。
基本上SpringCloud的核心包括Eureka注册中心，Ribbon负载均衡，Feign伪装接口，Hystrix断路器，服务降级，Zuul路由，Config文件统一配置管理等。也就是说SpringCloud本身并不是一项具体的技术，它是将这些技术整合起来，形成一套框架集合。
**就是SpringCloud。**

# SpringBoot
Springcloud为父pom项目，为了方便创建了三个子模块。项目中有一张部门（Dept），其中loc保存的数据库的名字。provider-dept-8001为微服务提供方，有三个接口服务：get(),list(),add()。测试：http://dept-8001.com:8001/dept/get/1。
consumer-80为服务消费方。启动80消费方，测试：http://client.com:8080/consumer/dept/list 
此时证明我们的服务是没有问题的。

# SpringCloud
**SpringCloud整体核心架构只有一点：Rest服务。**
既然核心是Rest架构，那么如果想更好的去使用Rest服务需要考虑如下问题：
1：所有的微服务地址一定非常多，所以为了统一管理这些地址服务，也为了即时的告诉用户哪些服务不可用，所以应该准备一个分布式注册中心，并且该注册中心支持有HA机制，为了告诉并且方便的进行微服务的注册操作，在SpringCloud里面提供有一个Eureka 的注册中心。
2：对于整个WEB端的架构可以轻松方便的实现WEB程序的编写，而后利用Nginx或者Apache实现负载均衡，但是WEB端出现了负载均衡，业务端呢？应该也提供有多个业务进行负载均衡，那么这个时候需要将需要参与到负载均衡的业务端在Eureka中进行注册，负责均衡可以使用Ribbon实现。
3：在进行客户端使用Rest架构调用的时候，往往都需要一个调用地址，即使使用了Eureka作为注册中心，那么它也需要一个明确的调用地址，可以所有的操作如果都用调用地址的方式来处理，程序的开发者最方便的应用工具是接口，所以现在就希望可以将所有的Rest服务的内容以接口的方式出现调用，所以它又提供一个feign技术，利用此技术可以伪造接口。
4：在进行微服务的设计过程里面还需要考虑一个问题：如果客户端调用了微服务A，而后微服务A又调用了微服务B，微服务B又调用了微服务C，如果现在C坏了，但是A和B没坏，那么此时A和B一定也无法进行正常操作，所以需要提供有熔断机制（Hystrix）。
5：随后还需要去考虑访问的路由处理机制，所以又提供有zuul配置。
6：如果微服务数量特别多的时候，就会写大量的配置文件，并且配置文件会有大量的重复，可以使用spring-cloud-config结合Git将我们的配置文件抽离成一个微服务统一管理起来。
7：既然是微服务，就必须考虑到负载的监控。

## SpringSecurity安全访问
首先微服务要考虑到安全的处理。本次我们使用springsecurity做安全控制。这是一个安全框架，主要用于权限的分配与管理。在本项目中只是为了分配一个安全用户，这个不是我们今天的重点啊。

要想进行安全的验证处理，首先一定要先在服务的提供方上（dept-8001）进行处理。追加SpringSecurity相关依赖包。修改application.yml文件。追加username和password。而在消费端所有的认证处理操作应该已请求头的模式进行处理，而后使用Base64进行加密处理后才可以得到一个正确的路径。所以在消费端（consumer-8002）上写一个配置bean，进行头文件信息的配置，然后在controller中注入。这样访问：http://dept-8001.com:8001/dept/list/就OK了。很多时候所有的web容器一般都会提供有一个Session的机制，正常情况下，认为该用户应该一直被服务器保存状态，如果出现并发，Rest服务器就会受到严重的内存困扰。所以最好将session设置为无状态：在配置文件中修改：security.sessions：stateless；
另外通过application.yml进行安全认证，如果开发的微服务数量很多就会很不方便，所以最简单的方法就是定义一个安全配置的程序类。

## Eureka服务治理
对于服务发现框架可以简单的理解为服务的注册和使用的操作步骤，Eureka和zookeeper的作用基本相同，在SpringCloud之中所有的微服务都向Eureka进行注册，而后客户端直接利用Eureka进行服务的获取。
1：建立一个Eureka-7001的子项目，追加依赖包spring-cloud-starter-eureka-server，配置端口号，修改Eureka的启动类，加入Eureka注解@EnableEurekaDerver。随后启动类，进入网页：http://eureka-7001.com:7001/
2：向Eureka中注册部门微服务：
部门微服务：dept-8001，追加依赖包：spring-cloud-starter-eureka，随后修改application.yml文件进行客户端注册的配置，随后在dept-8001主类中添加注解@EnableEurekaClient，这样服务就自动注册到Eureka之中了。也可以修改application文件设置微服务的名字：springcloud-provider-dept；也可以通过添加配置instance-id：dept-8001.com 来设置微服务的主机名字。如果要想查看微服务的详细信息，可以追加监控配置依赖：spring-boot-starter-actuator。随后还可以追加info的相关信息：info：，由于项目之中可能会有很多的微服务，所有信息最好要认真填写，负责维护起来就很麻烦。
服务注册实现：“服务提供者”在启动的时候会通过发送Rest请求的方式将自己注册到Eureka Server上，同时带上了自身服务的一些元数据信息。Eureka Server接收到这个Rest请求之后，将元数据信息存储在一个双层结构Map中，其中第一层的key是服务名，第二层的key是具体服务的实例名。
3：Eureka发现管理：
在实际的项目之中，通过Eureka作为微服务的监控处理，必须面临以下问题：新服务追加的时候应该立刻进行注册，当某一个服务下线之后需要理解进行清理。
这些都可以通过application.yml进行配置修改。对于注册到Eureka上的微服务也可以通过发现服务进行一些服务信息的获得，修改Dept的控制类，追加有控制调用的方法，随后在provide启动类中添加注解@EnableDiscoveryClient，这样访问：
http://dept-8001.com:8001/dept/discover就可以看到我们的微服务信息了。

4：Eureka的安全配置：
一般自己公司的Eureka服务应该可以注册的服务仅限于能够满足认证的微服务，所以这样我们需要安全认证。同样需要引入spring-boot-starter-security依赖。随后进入application配置文件进行密码配置项。随后在微服务方进行授权的配置链接：defaultZone：列出授权的主机Kafka地址。
5：Erueka-HA机制：
复制eureka-7001为三份：eureka-7002，eureka-7005，修改一下application.yml文件，进行交叉注册。随后进入微服务提供者修改配置文件添加所有的主机地址。
此时访问http://eureka-7001.com:7001/,http://eureka-7002.com:7002/,http://eureka-7005.com:7005/；会发现有副本的存在。
服务同步：由于这三台服务注册中心之间互相注册为服务，当服务提供者发送注册请求到一个服务注册中心时，它会将请求转发给集群之中相连的其他注册中心，从而实现注册中心之间的服务同步。通过服务同步，服务提供者的服务信息就可以通过这三台服务注册中心中的任意一台获取到。

## Ribbon负载均衡
//现在所有的服务都通过了Erueka进行了注册，这样所有的微服务汇集到了Eureka之中，而客户端的调用也应该通过Eureka完成， //这种调用可以通过Ribbon技术来实现。
//Spring Cloud Ribbon是一个基于HTTP和TCP的客户端负载均衡工具，它基于Netflix Ribbon实现。通过Spring Cloud的封装，  
//可以让我们轻松的将面向服务的Rest模板请求自动转换成客户端负载均衡的服务调用。Spring Cloud Ribbon虽然是一个工具框
//架，它不像服务注册中心，配置中心，API网关那样需要独立部署。但是它几乎存在于每一个Spring Cloud构建的微服务和基础
//设施中。因为微服务的调用，API网关的请求转发等内容，实际上都是通过Ribbon来实现的。包括我们后续介绍到的Feign，也
//是通过Ribbon来实现的。
现在要考虑一个问题就是Eureka提供了高可用的机制，我们的微服务肯定也要提供高可用。所以我们将我们的服务提供方复制三份，dept-8002.com，dept-8003.com。这样同时启动这两个微服务，发现Eureka中能显示这三个微服务了。
Ribbon是一个服务调用组件，并且是一个客户端实现负载均衡的处理的组件。
一：Ribbon的基本使用：
1：因为是要实现客户端的负载均衡，所以我们在consumer中添加Ribbon的依赖包：spring-cloud-starter-ribbon。
2：修改RestConfig配置类，在获取RestTemplate对象的时候加入Ribbon的配置。加入注解@LoadBalanced
3：随后修改配置文件，追加Eureka的服务注册配置：service-url。
4：修改项目的启动类，添加Eureka注解：@EnableEurekaClient
5：修改控制器调用类：这个时候有了Ribbon和Eureka整合之后用户就不用关心具体的Rest服务的地址与端口了，所有的信息都通过Eureka获取完成。修改controller，将调用地址改为我们Eureka中的服务名称：SPRINGCLOUD-PROVIDER-DEPT。随后访问消费地址：http://client.com:8080/consumer/dept/list/发现依旧可以正常获取想要的资源。

//二：Ribbon负载均衡：
刚才我们发现有一个注解@LoadBalanced。其实这个就是一个负载均衡的注解。也就意味着现在可以实现负载均衡的处理了。
1：新建两个服务提供端：dept-8002.com，dept-8003.com。然后每个服务方对应一个数据库。这样就有了三个数据库，但是有一点在appllication配置文件中这三个服务提供方的application.name一定都要是相同的，否则会被认为是不同的服务，无法进行负载均衡了。（关于数据的同步有很多方式，主从配置，或者使用Mycat进行管理等）这样访问Eureka注册中心http://eureka-7001.com:7001/就会发现我们的服务提供方变成了三个。
随后我们观察消费端consumer-80：http://client.com:8080/consumer/dept/list/。会发现每一次获取数据都是通过不同的微服务顺序获得，这样一个消费端就可以通过Ribbon实现了负载均衡的配置处理了。
另外我们还可以自定义Ribbon的配置，实现对负载均衡策略的修改。

## Feign接口转换
但是现在所进行的Rest服务都是通过url调用的。例如：consumer-80中的调用。所有数据的调用和转换都必须由用户自己来完成，而我们本身不擅长这些，我们习惯于通过接口来实现业务操作，而不是通过具体的Rest数据，说的直白一点就是JSON数据。在springcloud中，将Rest技术转换为接口的技术是用Feign来实现的。
一：Feign基本使用
1：为了方便，建立新模块springcloud-consumer-feign 和springcloud-consumer-80一样。添加spring-cloud-starter-feign依赖。
2：建立新的模块：springcloud-service，这个模块负责客户端接口的定义。
3：另外还要考虑到认证的问题，有了feign结合springcloud之后，认证更简单，不需要通过头信息，直接写一个认证处理的bean：FeignClientConfig就可以。
3：在service建立一个IDeptClientService接口，还是原来我们的三个方法。
4：在consumer-feign上我们就可以不用原来的RestTemelate进行Rest数据调用，而直接用我们最熟悉的接口就可以了。（有一点要注意，在启动类上要加上Feign的注解@EnableFeignClients）
5：随后测试：使用feign消费端访问：http://client.com:8080/consumer/dept/list/。可以发现同样可以访问到数据并且自带负载均衡。
**总结：
还可以使用Feign进行配置文件的修改实现Rest数据的压缩，Feign最核心的作用是将Rest服务的信息转换为接口，并且自动实现负载均衡。所有Feign=RestTemplate+HttpHeader+Ribbon的综合体。**

## Hystrix熔断机制
一：断路器
所谓的熔断机制就是指当我们的服务提供方出现了问题之后整个程序出现的信息显示，不要是默认的白板信息：错误，500之类的，而是替换为我们希望为用户展现的页面。

新建一个服务提供方dept-hystrix-8001和服务消费方consumer-hystrix。
1：dept-hystrix-8001中引入Hystrix依赖包：spring-cloud-starter-hystrix；
2：修改服务提供方程序控制类，设置一个错误页面的返回数据方法：getFallback，此方法的参数与get()一致，并设置好返回的错误信息。随后在get()方法加入注解@HystrixCommand，表示如果当前调用get()方法出现了错误，则执行fallback。
3：随后在程序主类上添加注解@EnableCircuitBreaker，表示启动熔断机制。随后访问：http://client.com:8080/consumer/dept/get?id=100会提示我们想要显示的错误信息，现在的处理情况是服务器出现了错误，但不表示提供方服务关闭。

二：服务降级
负载均衡和服务降级是RPC技术里面最为重要的一个话题，所谓的服务降级指的是当服务提供方不可使用的时候，程序不会出现异常，而是我们故意设计的错误提示。而服务的降级是在客户端实现的，与你的服务器端没有关系。此时我们要结合Feign一起使用所以这次我们在Feign客户端进行熔断的配置。
1：springcloud-service模块中扩充一个IDeptService的失败调用处理：
建立一个新接口要实现FallbackFactory，此接口要实现一个create()方法，我理解为创建错误信息。
2：随后在IDeptClientService接口中追加本地的fallback配置：@fallbackFactory = IDeptClientServiceFallbackFactory.class，此时当服务不可用过的时候，就会返回fallback信息。
3：在消费端consumer-hystrix启动类中追加注解：@ComponentScan，追击扫描包。
此时关掉服务提供方dept8001，只启动消费端访问http://client.com:8080/consumer/dept/get?id=1 get()方法会返回我们提供好的错误信息。

三：Hystrix Dashboard
在Hystrix里面提供有一种监控的功能，这个功能就是“Hystrix Dashboard”，可以利用它进行微服务的监控操作。
为了方便观察新建立一个consumer-hystrix-dashboard模块：
1：添加依赖：spring-cloud-starter-hystrix-dashboard；这个时候还要确保你要监控的微服务提供方有spring-boot-starter-actuator的监控服务依赖。
2：配置端口：9001，创建一个监控主类。此时服务运行地址：dashboard.com:9001/hystrix，会看到爆炸熊监控页面。
3：如果要想查看微服务的监控信息，可以浏览：http://jiashun:jiashun@dept-8001.com:8001/hystrix.stream ，但是这个页面监控谁也看不懂，看懂了也累。所以可以将监控微服务的地址输入到爆炸熊页面查看。此时我们访问http://client.com:8080/consumer/dept/get?id=1 调用服务在监控页面就可以看到监控信息。

## Zuul路由访问：
路由是微服务架构不可或缺的一部分。
到现在为止，所有的微服务都是通过Eureka完成，但是在很多的开发之中，为了规范，提供有一个路由的处理控制组件：Zuul。
也就是Zuul大部分时间只作为中间的一个代理层出现。例如：”/” 可能映射到你应用主页，/api/users映射到用户服务，/api/device映射到设备服务。
为了方便演示，我们新建一个springcloud-provider-company的微服务，这个微服务的只提供一个接口，显示公司名字，我们测试一些：http://company.com:8101/company/get/zkak 通过此路径可以得到我们想要的数据。这是没有问题的。结合zuul怎么使用呢？
我们再新建一个springcloud-zuul-gateway的模块。追加zuul的相关依赖包：spring-cloud-starter-zuul,因为zuul也要想eureka注册，所以还要加上eureka的依赖。然后我们修改application的配置文件，配置好端口和eureka的地址。然后启动此项目。
随后我们访问http://gateway.com:9501/springcloud-provider-company/company/get/zkak 地址发现同样可以调用我们的获取公司名字的微服务。
但是此刻有一个问题，如果使用路由的话必须要知道我们微服务的名字，但是这个名字是不应该让用户看到的。所以在使用之中就需要为zuul设置一些路由规则。在配置文件中加入如下内容：zuul: routes:springcloud-provider-company: /api/** 此时就代表api代理了springcloud-provider-company。此时重启路由zuul访问http://gateway.com:9501/api-company/company/get/zkak 发现依旧可以访问到我们想要的资源。如果加上此配置ignored-services: “*"  代表所有的微服务使用名称都不能访问了。只能通过路由api的路径访问。
在实际使用之中，所有的微服务一定都有自己的认证信息，那么就必须访问的时候追加有认证的头部信息，这样的功能也可以通过Zuul的过滤功能实现。
此时我们继续追加dept微服务的路由配置springcloud-provider-dept: /api-dept/** 。但是此刻有一个问题，dpet微服务是有密码的。此时我们访问http://gateway.com:9501/api-dept/dept/get/1 发现输入正确密码依旧不能访问。因为此刻路由是只能代理地址，但是它不能讲用户名密码等信息传递到我们微服务的。所以我们应该怎么操作呢？通过过滤器的方式将密码传递过去。此时重启再访问发现可以正常访问了。
考虑到zuul也需要安全访问呢。这时添加spring-boot-starter-security的依赖，配置application文件即可。

## 分布式配置中心：Spring Cloud Config
在操作中发现，我们的配置文件有大量的重复，这个时候可以考虑将配置文件做成一个配置中心，服务从配置中心读取文件，配置中从远程Git读取配置文件，当服务很多时，都需要同时从配置中心读取文件的时候，可以考虑将配置中心做成一个单独的微服务。
1：首先我们准备一个Git仓库，比如本文准备的仓库示例：https://github.com/jia-shun/springcloud-config。在仓库中添加我们的配置文件，比如我添加了一个application.yml 和 ReadMe的文件。
2：创建一个springcloud-config的项目，这个项目非常简单。在pom添加依赖spirng-cloud-config-server。
3：在application.yml配置文件中添加Git仓库的相关信息。随后启动项目，我们可以先启动浏览器测试一下：http://localhost:9090/springcloud-config/master 
4：创建一个bootstrap.yml的文件，来指定获取配置文件的git地址：http://localhost:9090/。这样就相当于将Git中的配置文件引入到项目之中。之后将application.yml隐藏。随后重启测试：http://gateway.com:9501/api-company/company/get/zkak 
##消息总线：Spring Cloud Bus
Spring Cloud Bus 将分布式的节点和轻量的消息代理连接起来，这可以用于广播配置文件的更改或者其他的管理工作。消息总线还可以为微服务做监控，也可以作为应用程序之间相互通讯。
##Spring Cloud Sleuth
服务链路追踪。











