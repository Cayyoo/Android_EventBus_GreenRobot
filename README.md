# EventBus_GreenRobot

```java
/**
 * EventBus是一款针对Android优化的发布/订阅事件总线。
 * 主要功能是替代Intent,Handler,BroadCast在Fragment，Activity，Service，线程之间传递消息。
 * 优点是开销小，代码更优雅，以及将发送者和接收者解耦。
 *
 *
 * 使用greenrobot:eventbus框架，必须在EventBus的4个调用函数前加上@Subscribe，以注明接收。
 *
 * 使用不同版本的EventBus包，其用法略有区别，大同小异。
 */

/**
 * onEvent:
 * 使用onEvent作为订阅函数，该事件在哪个线程发布出来的，onEvent就会在这个线程中运行，也就是说发布事件和接收事件线程在同一个线程。
 * 使用这个方法时，在onEvent方法中不能执行耗时操作，如果执行耗时操作容易导致事件分发延迟。
 *
 * onEventMainThread:
 * 使用onEventMainThread作为订阅函数，不论事件是在哪个线程中发布出来的，onEventMainThread都会在UI线程中执行，即接收事件会在UI线程中运行。
 * 这个在Android中是非常有用的，因为在Android中只能在UI线程中更新UI，所以在onEvnetMainThread方法中是不能执行耗时操作的。
 *
 * onEventBackground:
 * 使用onEventBackgrond作为订阅函数，如果事件是在UI线程中发布出来的，那么onEventBackground会在子线程中运行。
 * 如果事件本来就是子线程中发布出来的，那么onEventBackground函数直接在该子线程中执行。
 *
 * onEventAsync：
 * 使用这个函数作为订阅函数，无论事件在哪个线程发布，都会创建新的子线程再执行onEventAsync.
 *
 * EventBus怎么指定调用哪个函数呢？
 * 发送时发送的是这个类的实例，接收时的参数就是这个类实例。
 * 识别调用EventBus中四个函数中的哪个函数，是通过参数中的实例来决定的。
 * 即，哪个函数传进去的参数是这个类的实例，就调哪个。如果有两个，那两个都会被调用！
 *
 * EventBus接收消息是根据参数中类的实例的类型来判定的。
 * 所以如果我们在接收时，同一个类的实例参数有两个或以上函数来接收会怎样？答案是，这两个或多个函数都会执行。
 *
 * 消息的接收是根据参数中的类名来决定执行哪一个的；
 */

/**
 * 基本使用
 * 1、Implement any number of event handling methods in the subscriber:接受发送的消息
 *      public void onEvent(AnyEventType event) {}
 *
 * 2、Register subscribers:注册
 *      eventBus.register(this);
 *
 * 3、Post events to the bus:发送消息
 *      eventBus.post(event);
 *
 * 4、Unregister subscriber:解除注册
 *      eventBus.unregister(this);
 */
```

![screenshot](https://github.com/ykmeory/EventBus_GreenRobot/blob/master/screenshot.jpg "截图")
