package quickindexbar.chinasoft.com.quickindexbar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    private ArrayList<Friend> friends = new ArrayList<Friend>();
    private Context context;


    public MyAdapter(Context context, ArrayList<Friend> friends) {
        this.friends = friends;
        this.context = context;
    }


    @Override
    public int getCount() {
        return friends.size();
    }

    @Override
    public Object getItem(int position) {
        return friends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = View.inflate(context,R.layout.adapter_friend, null);
        }
        ViewHolder viewHolder = ViewHolder.getHolder(context, convertView);
        //设置数据
        Friend friend = friends.get(position);
        viewHolder.name.setText(friend.getName());

        String currentWord = friend.getPinyin().charAt(0) + "";
        if(position > 0) {
            //获取上一个item的首字母
            String lastWord = friends.get(position - 1).getPinyin().charAt(0) + "";
            //拿当前的首字母和上一个字母比较
            if(currentWord.equals(lastWord)) {
                viewHolder.first_word.setVisibility(View.GONE);
            }else {
                viewHolder.first_word.setVisibility(View.VISIBLE);
                viewHolder.first_word.setText(currentWord);
            }
        }else {
            viewHolder.first_word.setVisibility(View.VISIBLE);
            viewHolder.first_word.setText(currentWord);
        }
        return convertView;
    }

    public static class ViewHolder {
        private TextView first_word;
        private TextView name;

        public static ViewHolder getHolder(Context context, View convertView) {
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            if(viewHolder == null) {
                viewHolder = new ViewHolder();
                viewHolder.first_word = (TextView) convertView.findViewById(R.id.first_word);
                viewHolder.name = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(viewHolder);
            }
            return viewHolder;
        }
    }
}
