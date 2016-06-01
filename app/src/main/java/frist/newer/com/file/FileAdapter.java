package frist.newer.com.file;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * File Adapter 显示文件列表
 */
public class FileAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<File> data;
    /**
     * 实例化 xml布局文件
     */
    private LayoutInflater layoutInflater;

    /**
     * 创建文件适配器(数据与视图间的桥)
     *
     * @param context
     * @param data
     */

    public FileAdapter(Context context, ArrayList<File> data) {
        this.context = context;
        this.data = data;
        layoutInflater = LayoutInflater.from(context);
    }

    /**
     * 获得总数
     *
     * @return 数据的总数
     */
    @Override
    public int getCount() {

        return data.size();
    }

    /**
     * 获得特定位置的数据项
     *
     * @param position int 位置
     * @return 数据项
     */
    @Override
    public File getItem(int position) {
        return data.get(position);
    }

    /**
     * 获得特定位置的数据编号
     *
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }



    /**
     * 获得特定位置的视图项
     *
     * @param position    int 位置
     * @param convertView View 可重用的视图项
     * @param parent      ListView父元素
     * @return 加载了数据的视图项
     */
    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder viewholder;
        /**
         * 获得视图项的结构
         */

        if(convertView == null){
            //加载xml布局，实例化，并返回视图
            convertView = layoutInflater.inflate(R.layout.file_item, parent, false);
            // 当前的 ViewHolder 保存视图的结构
            viewholder = new Viewholder(convertView);
            // 视图绑定一个对象
            convertView.setTag(viewholder);
        }

     else {
        viewholder = (Viewholder) convertView.getTag();
    }
    viewholder.binddata(data.get(position));
    return convertView;
}

/**
 * 适配器视图中{视图项}的结构
 */
private class Viewholder {
    /**
     * 图标
     */
    ImageView imageView;
    /**
     * 文件名
     */
    TextView textViewtitle;
    /**
     * 文件信息
     */
    TextView textViewinfo;
    /**
     * 操作按钮
     */
    ImageButton imageButton;

    Popmenulistener popmenulistener;

    /**
     * 保存视图项的结构
     *
     * @param view 视图项
     */
    public Viewholder(View view) {
//        imageView = (ImageView) view.findViewById(R.id.imageView_icon);
        imageView = (ImageView) view.findViewById(R.id.imageView_icon);
        textViewinfo = (TextView) view.findViewById(R.id.textView_info);
        textViewtitle = (TextView) view.findViewById(R.id.textView_tilet);
        imageButton = (ImageButton) view.findViewById(R.id.imageButton_more);
        popmenulistener = new Popmenulistener();
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建弹出菜单
                PopupMenu menu = new PopupMenu(context, v);
                //加载xml文件
                menu.inflate(R.menu.file_item_popu);
                //注册监听器
                menu.setOnMenuItemClickListener(popmenulistener);
                //显示
                menu.show();
            }
        });
    }

    public void binddata(File data) {
        imageView.setImageResource(data.isFile() ? R.drawable.ic_attachment_24dp : R.drawable.ic_folder_open_24dp);
        textViewtitle.setText(data.getName());
        int size = (data.list() == null) ? 0 : data.list().length;
        textViewinfo.setText(data.isFile() ? String.format("文件：%d 字节", data.length()) : String.format("目录:%d 文件", size));
        //设置要操作的数据
        popmenulistener.setFile(data);
    }


}

/**
 * 菜单监听器
 */
class Popmenulistener implements PopupMenu.OnMenuItemClickListener {
    File file;

    /**
     * 设置要操作的数据
     *
     * @param file
     */

    private void setFile(File file) {
        this.file = file;

    }

    /**
     * 菜单项点击
     *
     * @param item 事件源
     * @return true 事件处理完毕
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actin_copy:
                doCopy();
                break;
            case R.id.actin_remove:
                doRemove();
                break;
            case R.id.actin_rename:
                doRename();
                break;

        }
        return true;
    }

    private void doRename() {

        View v =layoutInflater.inflate(R.layout.rname,null);
        TextView tv= (TextView) v.findViewById(R.id.textView3);
        tv.setText(file.getName());
        new AlertDialog.Builder(context)
                .setTitle("重命名")
                .setView(v)
                .setPositiveButton("确定",null)
                .setNegativeButton("取消",null)
                 .create()
                .show();


        showTost("重命名" + file.getName());
    }

    private void showTost(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    private void doRemove() {
        View v = layoutInflater.inflate(R.layout.remove,null);
        TextView view = (TextView) v.findViewById(R.id.textView6);
        view.setText(file.getName());

        showTost("删除" + file.getName());
        new AlertDialog.Builder(context)
                .setTitle("删除")
                .setView(v)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })

                .setCancelable(false)
                .setNegativeButton("取消",null)
                .create()
                .show();
    }

    private void doCopy() {
        showTost("复制" + file.getName());
    }
}

}
