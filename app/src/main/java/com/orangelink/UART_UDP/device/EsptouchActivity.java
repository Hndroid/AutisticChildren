package com.orangelink.UART_UDP.device;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.espressif.iot.esptouch.EsptouchTask;
import com.espressif.iot.esptouch.IEsptouchListener;
import com.espressif.iot.esptouch.IEsptouchResult;
import com.espressif.iot.esptouch.IEsptouchTask;
import com.espressif.iot.esptouch.demo_activity.EspWifiAdminSimple;
import com.espressif.iot.esptouch.task.__IEsptouchTask;
import com.orangelink.UART_UDP.base.BaseActivity;
import com.zhuxiyungu.autisticchildren.R;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;





/*
* 关于这次自闭症儿童互动装置的项目，我和杨总大致沟通技术架构：
* 1，手机和装置使用wifi连接
* 2、手机----》装置，使用udp广播的方式，即手机发送udp广播：255.255.255.255：2002
* 3、装置会拿到手机的局域网ip，装置通过udp会返回数据到手机
* 4、手机使用广播"online"，检测局域网中是否存在装置。
* 5、如果你们还有精力，可以注意一下这个wifi控制板，上面还有一个串口。手机通过udp和控制板的所有交互都会透传到这个串口上，串口数据也可以发送到手机上。如果你们会用单片机，也可以自己写一个简单的51单片机
* 用串口驱动。这样51就可以和手机交互，你们就可以做出更复杂的功能了。
*
*
* 这个wifi控制板和这个app，涉及到Orangelink公司的知识产权，非公开项目。请不要将其利用到这个项目（自闭症儿童互动装置）之外的商业活动中。
*
* Oranglink 罗仁通
* 2017.02.19
* */





public class EsptouchActivity extends BaseActivity implements OnClickListener {

    private static final String TAG = "laoluo";
    private TextView mTvApSsid;
    public TextView mget;
    private TextView msend;
    private TextView monline;
    private EditText mEdtApPassword;
    private Button mBtnConfirm;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private LinearLayout mBack;
    private CheckBox mCheckboxIsSsidHidden;
    private EspWifiAdminSimple mWifiAdmin;

    private String remo_ip;
    private static final int UDP_SERVER_PORT = 2012;
    private String ApSsid;
    private DatagramSocket My_UDP_Socket;
    private UDP_SEND udp_client;
    ExecutorService exec2;
    private int Online=0;
    private String Myip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banding_device_without);

        mWifiAdmin = new EspWifiAdminSimple(this);
        mTvApSsid = (TextView) findViewById(R.id.tvApSssidConnected);
        mEdtApPassword = (EditText) findViewById(R.id.edtApPassword);


        mBtnConfirm = (Button) findViewById(R.id.btnConfirm);
        btn1 = (Button) findViewById(R.id.btn1);

        mBtnConfirm.setOnClickListener(this);
        btn1.setOnClickListener(this);



        mget = (TextView) findViewById(R.id.get);
        msend = (TextView) findViewById(R.id.send);
        monline = (TextView) findViewById(R.id.online);

        mCheckboxIsSsidHidden = (CheckBox) findViewById(R.id.switchIsSsidHidden);

        UDP_init();


        /*
        * udp 不是面向连接的，不管是配置前还是配置后，app都无法像tcp协议一样可以保持连接通道，所以需要设计一个udp心跳包，检测局域网是否存在设备
        * */
        handler.postDelayed(runnable, 500);
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            try {

                if(Online>0){
                    Online--;
                }

                if(Online==0){
                    monline.setText("设备不在线，请打开设备，或者配置");
                    monline.setTextColor(Color.parseColor("red"));
                    handler.postDelayed(this, 1000);//没在线的时候，1秒钟检测一次
                }else{
                    handler.postDelayed(this, 5000);//发现设备之后，5秒钟检测一次
                }
                sent_udp("online");//所谓心跳，就是app发送一个"online"字符串，广播到局域网中

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("exception...");
            }
        }
    };




    //进入页面时，判断手机是否处于wifi状态中，配置必须要在wifi环境中，
    @Override
    protected void onResume() {
        super.onResume();
        ApSsid = mWifiAdmin.getWifiConnectedSsid();
        if (ApSsid != null) {
            mTvApSsid.setText("当前路由器：" + ApSsid);

        } else {
            mTvApSsid.setText("手机需要先连接到wifi上");
            mBtnConfirm.setEnabled(false);
        }
    }




    //转动装置的马达，请确保设备在线后调用（即收到心跳online后，再调用）
    public void start_MOTOR()
    {
        String tosend="good";
        sent_udp(tosend);

        SimpleDateFormat formatter=new SimpleDateFormat("HH:mm:ss");
        Date curDate =  new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate)+":"+tosend;
        msend.setText(str);
    }




    @Override
    public void onClick(View v) {

        //wifi配置
        if (v == mBtnConfirm) {
            Log.v("laoluo", "click me! OK");
            String apSsid = ApSsid;
            String apPassword = mEdtApPassword.getText().toString();

            String apBssid = mWifiAdmin.getWifiConnectedBssid();

            Boolean isSsidHidden = mCheckboxIsSsidHidden.isChecked();
            String isSsidHiddenStr;

            if (isSsidHidden) {
                isSsidHiddenStr = "YES";
            } else {
                isSsidHiddenStr = "NO";
            }
            String taskResultCountStr = "1";
            new EsptouchAsyncTask3().execute(apSsid, apBssid, apPassword, isSsidHiddenStr, taskResultCountStr);
        }

        //例子，手机发送一个"good"数据到装置，装置转动一次
        else if (v == btn1) {
            Log.i("btn1 :::::::", "OK");
            start_MOTOR();

        }

    }



    //配置wifi使用
    private void onEsptoucResultAddedPerform(final IEsptouchResult result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
    //配置wifi使用
    private IEsptouchListener myListener = new IEsptouchListener() {
        @Override
        public void onEsptouchResultAdded(final IEsptouchResult result) {
            onEsptoucResultAddedPerform(result);
        }
    };
    //配置wifi使用
    private class EsptouchAsyncTask3 extends AsyncTask<String, Void, List<IEsptouchResult>> {

        private ProgressDialog mProgressDialog;
        private IEsptouchTask mEsptouchTask;
        private final Object mLock = new Object();

        @Override
        protected void onPreExecute() {
            mProgressDialog = new ProgressDialog(EsptouchActivity.this);
            mProgressDialog.setMessage("正在配置中, 耐心等待...");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setOnCancelListener(new OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    synchronized (mLock) {
                        if (__IEsptouchTask.DEBUG) {
                            Log.i(TAG, "progress dialog is canceled");
                        }
                        if (mEsptouchTask != null) {
                            mEsptouchTask.interrupt();
                        }
                    }
                }
            });
            mProgressDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                    "Waiting...", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            mProgressDialog.show();
            mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
        }

        //配置wifi使用
        @Override
        protected List<IEsptouchResult> doInBackground(String... params) {
            int taskResultCount = -1;
            synchronized (mLock) {
                String apSsid = params[0];
                String apBssid = params[1];
                String apPassword = params[2];
                String isSsidHiddenStr = params[3];
                String taskResultCountStr = params[4];
                boolean isSsidHidden = false;
                if (isSsidHiddenStr.equals("YES")) {
                    isSsidHidden = true;
                }
                taskResultCount = Integer.parseInt(taskResultCountStr);
                mEsptouchTask = new EsptouchTask(apSsid, apBssid, apPassword, isSsidHidden, EsptouchActivity.this);
                mEsptouchTask.setEsptouchListener(myListener);
            }
            List<IEsptouchResult> resultList = mEsptouchTask.executeForResults(taskResultCount);
            return resultList;
        }

        //配置wifi使用
        @Override
        protected void onPostExecute(List<IEsptouchResult> result) {
            mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(true);
            mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE).setText("确认");
            IEsptouchResult DevResult = result.get(0);

            if ((!DevResult.isCancelled()) && (DevResult.isSuc())) {
//                remo_ip = DevResult.getInetAddress().getHostAddress().toString();
                sent_udp("online");
                sent_udp("online");
                sent_udp("online");
                mProgressDialog.setMessage("配置成功！！！ mac地址:" + DevResult.getBssid() + "\n");
            } else {
                mProgressDialog.setMessage("配置失败，请保证设备进入黄灯快闪的配置状态，并在需要配置的路由器附近");
            }
        }
    }







    /*
    *
    * 以下是udp部分的工作业务逻辑
    *
    * */
    public void UDP_init()
    {
        My_UDP_Socket = null;
        try {
            My_UDP_Socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        Thread thread = new Thread(new UDP_Receive(My_UDP_Socket), "thread1");
        thread.start();
    }


    public void sent_udp(String tosend)
    {
        Thread thread = new Thread(new UDP_SEND(My_UDP_Socket,tosend,UDP_SERVER_PORT), "thread1");
        thread.start();
    }



    //udp接收
    public class UDP_Receive implements Runnable {

        private byte[] msg = new byte[1024];

        private DatagramPacket get_Packet;
        private DatagramSocket the_socket;

        public UDP_Receive(DatagramSocket new_socket) {
            this.the_socket = new_socket;
        }

        @Override
        public void run() {
            get_Packet = new DatagramPacket(msg, msg.length);
            String getdata;
            while(true) try {
                the_socket.receive(get_Packet);//此处阻塞

                getdata = new String(get_Packet.getData(), 0 ,get_Packet.getLength());
                getdata = getdata.replaceAll("[\u0000-\u001f]", "");
                Log.i("msg sever received", getdata);


                SocketAddress dev_address = get_Packet.getSocketAddress();
                Myip = dev_address.toString();

                EsptouchActivity.this.runOnUiThread(new ChangeText(getdata));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    class ChangeText implements Runnable {
        String text;
        ChangeText(String text) {
            this.text = text;
        }
        @Override
        public void run() {
            Online=2;
            monline.setText("设备已经在线");
            monline.setTextColor(Color.parseColor("green"));

            if(!this.text.equals("online")) {
                SimpleDateFormat formatter=new SimpleDateFormat("HH:mm:ss");
                Date curDate =  new Date(System.currentTimeMillis());//获取当前时间
                String str = formatter.format(curDate)+":"+this.text;
                mget.setText(str);
            }
        }
    }


    //udp发送广播
    public class UDP_SEND implements Runnable {

        private DatagramPacket bcast_send_packet;
        private DatagramSocket the_socket;

        private int re_port;
        private String buf;

        public UDP_SEND(DatagramSocket new_socket,String buf,int re_port) {
            this.the_socket = new_socket;
            this.buf = buf;
            this.re_port = re_port;
        }


        @Override
        public void run() {

            InetAddress cast_local = null;
            try {
                cast_local = InetAddress.getByName("255.255.255.255");//向局域网广播
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            bcast_send_packet = new DatagramPacket(buf.getBytes(), buf.length(), cast_local, re_port);
            //广播数据到局域网
            try {
                the_socket.send(bcast_send_packet);
                Log.i("msg send:", buf);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }
}
