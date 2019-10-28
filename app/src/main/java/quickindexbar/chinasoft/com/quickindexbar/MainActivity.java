package quickindexbar.chinasoft.com.quickindexbar;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private QuickIndexBar quickIndexBar;
    private ListView listView;
    private MyAdapter myAdapter;
    private TextView currentWord;

    private ArrayList<Friend> friends = new ArrayList<Friend>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        quickIndexBar = (QuickIndexBar) findViewById(R.id.quickIndexBar);
        currentWord = (TextView) findViewById(R.id.currentWord);
        //1.准备数据
        fillList();
        //2.对数据排序
        Collections.sort(friends);
        //3.设置Adapter
        myAdapter = new MyAdapter(this, friends);
        listView.setAdapter(myAdapter);

        quickIndexBar.setOnTouchListener(new QuickIndexBar.OnTouchLetterListener() {
            @Override
            public void onTouchLetter(String letter) {
                //根据当前触摸的字母，设置listView的位置
                for (int i = 0; i < friends.size(); i++) {
                    String currentWord = friends.get(i).getPinyin().charAt(0) + "";
                    if (currentWord.equals(letter)) {
                        listView.setSelection(i);
                        break;
                    }
                }
                //显示当前字母
                showCurrentWord(letter);
            }

            @Override
            public void onTouchMoveUp(String letter) {
                //抬起的时候，隐藏当前字母
                inVisibleCurrentWord(letter);
            }
        });
        //通过缩小隐藏
        ViewHelper.setScaleX(currentWord, 0f);
        ViewHelper.setScaleY(currentWord, 0f);
    }


    private boolean isScale = true;
    private Handler handler = new Handler();

    private void showCurrentWord(String letter) {
        currentWord.setText(letter);
        if (isScale) {
            ViewPropertyAnimator.animate(currentWord).setDuration(350).scaleX(1.0f).start();
            ViewPropertyAnimator.animate(currentWord).setDuration(350).scaleY(1.0f).start();
            isScale = false;
        }
    }

    private void inVisibleCurrentWord(String letter) {
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewPropertyAnimator.animate(currentWord).setDuration(350).scaleX(0f).start();
                ViewPropertyAnimator.animate(currentWord).setDuration(350).scaleY(0f).start();
            }
        }, 1000);
        isScale = true;
    }

    private void fillList() {
        // 虚拟数据
        friends.add(new Friend("李伟"));
        friends.add(new Friend("张三"));
        friends.add(new Friend("阿三"));
        friends.add(new Friend("阿四"));
        friends.add(new Friend("段誉"));
        friends.add(new Friend("段正淳"));
        friends.add(new Friend("张三丰"));
        friends.add(new Friend("陈坤"));
        friends.add(new Friend("林俊杰1"));
        friends.add(new Friend("陈坤2"));
        friends.add(new Friend("王二a"));
        friends.add(new Friend("林俊杰a"));
        friends.add(new Friend("张四"));
        friends.add(new Friend("林俊杰"));
        friends.add(new Friend("王二"));
        friends.add(new Friend("王二b"));
        friends.add(new Friend("赵四"));
        friends.add(new Friend("杨坤"));
        friends.add(new Friend("赵子龙"));
        friends.add(new Friend("杨坤1"));
        friends.add(new Friend("李伟1"));
        friends.add(new Friend("宋江"));
        friends.add(new Friend("宋江1"));
        friends.add(new Friend("李伟3"));
    }
}
