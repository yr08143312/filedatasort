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
                    DataVo vo = new DataVo(strArr[0].trim(),strArr[1].trim(),Float.parseFloat(strArr[2].trim()));
                    sortResources.produceData(vo);
                }
                //！！！！！讲解：此处需要将剩余文件数减少。
                sortResources.fileSizeDecrement();
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
        }
        //System.out.println("生产线程---"+Thread.currentThread().getName()+"结束----"+SortResources.restFileCount.get());

    }

    public void addFile(File file){
        files.add(file);
    }
}
