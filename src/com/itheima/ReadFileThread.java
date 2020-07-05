package com.itheima;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadFileThread implements Runnable{
    private List<File> files = new ArrayList<>();
    private SortResources sortResources;


    public ReadFileThread(SortResources sortResources){
        this.sortResources = sortResources;
    }

    @Override
    public void run() {
        for(File file : files){
            Reader reader = null;
            BufferedReader br = null;
            try {
                reader = new InputStreamReader(new FileInputStream(file));
                br = new BufferedReader(reader);
                String tempStr = "";
                while((tempStr = br.readLine()) != null){
                    if("".equals(tempStr)){
                        continue;
                    }
                    String[] strArr = tempStr.split(",");
                    DataVo vo = new DataVo(strArr[0],strArr[1],Float.parseFloat(strArr[2]));
                    sortResources.produceData(vo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    reader.close();
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            SortResources.restFileCount.decrementAndGet();
        }

    }

    public void addFile(File file){
        files.add(file);
    }
}
