# rabbitmq_demo
消息中间件 -- 学习RabbitMQ

#### 第一种模型（直连）

<img src="https://www.rabbitmq.com/img/tutorials/python-one-overall.png"/>

在上图的模型中，有以下概念：

- P：生产者，也就是发送消息的程序。
- C：消费者：消息的接受者，会一直等待消息的到来。
- queue：消息队列。图中红色部分。可以看作是生活中的邮箱，可以缓存消息；生产者向其中投递消息，消费者从中取出消息。



#### 第二种模型（work queue）

​		Work queues，也就称为（Task queues），任务模型。当消息处理比较耗时的时候，可能生产消息的速度会远远大于消息的消费速度。长此以往，消息就会堆积越来越多，无法及时处理。此时可以使用 work 模型：**让多个消费绑定到一个队列，共同消费队列里面的消息。**队列中的消息一旦消费，就会消失，因此任务是不会被重复执行的。

​	                         <img src="https://www.rabbitmq.com/img/tutorials/python-two.png"  />

角色：

- P：生产者 -- 任务的发布者
- C1：消费者1 -- 领取任务并且完成任务，假设完成速度较慢
- C2：消费者2 -- 领取任务并完成任务，假设完成速度快

总结：默认情况下，RabbitMQ将按顺序将每个消息把每个消息发送给下一个使用者。平均而言，每个消费者都会受到相同数量的消息。这种分发消息的方式称为循环。

#### 消息自动确认机制

​		完成一项任务可能需要几秒钟，你可能会想，如果其中一个消费者开始了一项长期的任务，却只完成了一部分就死了，会发生什么呢？在我们当前的代码中，一旦RabbitMQ讲消息传递给消费者，它就会立即将其标记为删除。在这种情况下，如果你杀死了一个消费者，我们将丢失正在处理的消息。我们还将丢失发送给此特定工作进程但尚未处理的所有消息。但是我们不想死去任何任务。如果一个消费者死了，我们希望把任务交给另一个消费者

#### 第三种模型（fanout）

fanout  扇出 也称为广播

![](https://www.rabbitmq.com/img/tutorials/exchanges.png)

在广播模式下，消息发送流程是这样的：

- 可以有多个消费者
- 每个**消费者有自己的queue（队列）**
- 每个队列都要绑定到Exchange（交换机）
- **生产者发送的消息，只能发送到交换机**，交换机来决定要发送给哪个队列，生产者无法决定
- 交换机把消息发送给绑定过的所有队列
- 队列的消费者都能拿到消息，实现一条消息被多个消费者消费

#### 第四种模型（Routing）

**1.Routing之订阅模型-Direct（直连）**

​		在上一个模型中，我们构建了一个简单的日志系统，我们能够向许多接收器广播日志消息。在接下来的模型中，我们将添加一个特性 -- 我们将使它能够只订阅消息的一个子集。例如，我们将只能将严重错误消息定向到日志文件（以节省磁盘空间），同时仍然能够在控制台上打印所有的日志消息

​		在 Fanout 模型中，会被所有订阅的队列都消费。但是，在某些场景下，我们希望不同的消息被不同的队列消费。这时就需要用到Direct类型的Exchange。

​		在Direct模型下：

- 队列与交换机的绑定，不能是任意绑定了，而是要指定一个RoutingKey（路由key）
- 消息的发送方在向Exchange发送消息时，也必须指定消息的RoutingKey。
- Exchange不再把消息交给每一个绑定的队列，而是更具消息的Routing Key进行判断，只有队列的Routing Key与消息的Routing Key完全一致，才会接收到消息

流程：

![](https://www.rabbitmq.com/img/tutorials/direct-exchange.png)

图解：

- P：生产者，向Exchange发送消息，发送消息时，会指定一个Routing Key
- X：Exchange（交换机），接收生产者的消息，然后把消息递交给与Routing Key完全匹配的队列
- C1：消费者，其所在队列指定了需要Routing Key为error的消息
- C2：消费者，其所在队列指定了需要Routing Key为info、error、warning的消息

**2.Routing 之订阅模型-Topic**

​		Topic类型的Exchange与Direct相比，都是可以根据RoutingKey把消息路由到不同的队列，只不过Topic类型Exchange可以让队列绑定在绑定RoutingKey的时候使用通配符！这种模型RoutingKey一般都是由一个或多个单词组成，多个单词之间以“.”分割，例如：item.insert

![](https://www.rabbitmq.com/img/tutorials/python-five.png)

```
# 通配符
		*（star）can substitute for exactly one word. 匹配不多不少恰好1个词
		#（hash）can substitute for zero or more words. 匹配一个或多个词
# 如
		audit.#  匹配audit.irs.corporate 或者 audit.irs等
		audit.*  只能匹配 audit.irs	
```

----

#### MQ的应用场景及原理

找到一篇讲的不错的blog，可以看看

https://blog.csdn.net/whoamiyang/article/details/54954780

