package frist.newer.com.file;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String path;
    //视图
    ListView listView;
    //数据
    ArrayList<File> data;
    //适配器
    FileAdapter Adapter;
    Button button;
    TextView textView;
    File f;
    File parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载布局
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        textView= (TextView) findViewById(R.id.textView2);

        initView();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File sdPath = Environment.getExternalStorageDirectory();
                if(sdPath != f.getParentFile()){

                    parent = f.getParentFile();
                    File f1 = parent.getParentFile();
                    File[] file2= f1.listFiles()[1].listFiles();
                    try {
                        String path = f.getCanonicalPath();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    textView.setText(path);
                    data.clear();
                    for(File file:file2){
                        data.add(file);
                        Adapter = new FileAdapter(MainActivity.this, data);
                        listView.setAdapter(Adapter);
                    }
                }
                Adapter.notifyDataSetChanged();

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (data.get(position).isFile()) {
                    return;
                } else {
                  f =data.get(position);
                    String path= null;
                    try {
                        path = f.getCanonicalPath();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    textView.setText(path);

                    File[] file = data.get(position).listFiles();

                    if (file.length == 0) {
                        return;
                    }
                    data.clear();
                    for (File file1 : file) {
                        data.add(file1);
                        Adapter = new FileAdapter(MainActivity.this, data);
                        listView.setAdapter(Adapter);

                    }}

                    Adapter.notifyDataSetChanged();
            }


        });


    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listView);
        data = new ArrayList<>();
        //加载sd卡内容
        loadData();
        Adapter = new FileAdapter(this, data);
        listView.setAdapter(Adapter);


    }

    private void loadData() {
        //外部存储文件
        File sdpath = Environment.getExternalStorageDirectory();

        //使用过滤器
        //      File[] files = sdpath.listFiles(new Myfiter());
        //获得目录中的所有文件
        File[] files = sdpath.listFiles();
        //文件添加到数据中
        for (File f : files) {
            data.add(f);
        }


//        if(!sdpath.exists()){
//            //新建文件
//            sdpath.createNewFile();
//            //新建目录
//            sdpath.mkdir();
//            sdpath.mkdirs();
//        }
    }

    /**
     * 文件过滤器
     */
    class Myfiter implements FileFilter {
        /**
         * @param pathname 返回true出现在数组否则就排除了
         * @return
         */
        @Override
        public boolean accept(File pathname) {
            //return pathname.isFile();
            return pathname.getName().endsWith(".java");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
