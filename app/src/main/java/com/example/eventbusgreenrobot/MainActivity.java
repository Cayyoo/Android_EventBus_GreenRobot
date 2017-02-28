package com.example.eventbusgreenrobot;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
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
public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView textView;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        EventBus.getDefault().register(this);//注册

        button= (Button) this.findViewById(R.id.button);
        textView= (TextView) this.findViewById(R.id.tv_receive_msg);
        textView2= (TextView) this.findViewById(R.id.tv_receive_msg2);
        textView3= (TextView) this.findViewById(R.id.tv_receive_msg3);
        textView4= (TextView) this.findViewById(R.id.tv_receive_msg4);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
            }
        });

    }

    /**
     * 必须使用@Subscribe标记符。
     * 这表明onEventMainThread要在UI线程中处理
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(Bean event) {
        String msg = "onEventMainThread收到了消息：" + event.getMsg();
        textView.setText(msg);

        Log.d("tag", msg);
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    /**
     * 必须使用@Subscribe标记符。
     * 这表明onEvent要在与发布线程相同的线程中处理
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEvent(Bean event) {
        textView2.setText("OnEvent收到了消息：" +event.getMsg());

        Log.d("tag", "OnEvent收到了消息：" + event.getMsg());
    }

    /**
     * 必须使用@Subscribe标记符。
     * 这表明onEventBackgroundThread如果是在UI线程中发布，则在新线程中运行
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEventBackgroundThread(Bean event){
        textView3.setText("onEventBackground收到了消息：" + event.getMsg());

        Log.d("tag", "onEventBackground收到了消息：" + event.getMsg());
    }

    /**
     * 必须使用@Subscribe标记符。onEventAsync在新线程中运行
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEventAsync(Bean event){
        textView4.setText("onEventAsync收到了消息：" +event.getMsg());

        Log.d("tag", "onEventAsync收到了消息：" + event.getMsg());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册
    }

}
