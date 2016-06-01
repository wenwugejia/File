package frist.newer.com.file;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    ListView listView;
    TextView textView;
    File currentparent;
    File[] currentfiles;
    FileAdapter adapter;
    ArrayList<File> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listView = (ListView) findViewById(R.id.list);

        textView = (TextView) findViewById(R.id.path);
        File root = Environment.getExternalStorageDirectory();
        if (root.exists()) {
            currentparent = root;
            currentfiles = root.listFiles();

            inflatelistview(currentfiles);

        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentfiles[position].isFile()) return;
                File[] tmp = currentfiles[position].listFiles();
                if (tmp == null || tmp.length == 0) {
                    Toast.makeText(Main2Activity.this, "当前路径下没有文件", Toast.LENGTH_SHORT).show();
                } else {
                    currentparent = currentfiles[position];
                    currentfiles = tmp;

                    inflatelistview(currentfiles);

                }
            }
        });
        Button parent = (Button) findViewById(R.id.parent);
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!currentparent.getCanonicalPath().equals("/")) {
                        currentparent = currentparent.getParentFile();
                        currentfiles = currentparent.listFiles();

                        inflatelistview(currentfiles);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ;
            }
        });

    }

    private void inflatelistview(File[] file)  {
        if(data != null){
            data.clear();
        }
        data = new ArrayList<>();
        for (File file1 : file) {
            data.add(file1);
        }
        adapter = new FileAdapter(Main2Activity.this, data);
        listView.setAdapter(adapter);
        try {
            textView.setText("当前路径为" + currentparent.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
