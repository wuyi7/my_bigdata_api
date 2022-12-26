package com.wuyi.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * @Author: WuYi at 2022-11-23 09:54
 * @Description:
 * @Version:1.0
 */
public class HadoopClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private FileSystem fs;

    public HadoopClient() {
        init();
    }

    private void init() {
        //1 to get the FileSystem
        Configuration conf = new Configuration();
        conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        conf.set("dfs.nameservices", "hadoop102");
        conf.set("fs.defaultFS", "hdfs://hadoop102:8020");
        try {
            fs = FileSystem.get(conf);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void showThePath(String pathStr) throws IOException {
        Path path = new Path(pathStr);
        try {
            RemoteIterator<LocatedFileStatus> list = fs.listFiles(path, true);
            while(list.hasNext()){
                System.out.println("FilePath = "+list.next().getPath());
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new IOException();
        }


    }


    public void testListFiles() throws Exception {
        //1 to get the FileSystem
        Configuration conf = new Configuration();
        conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        conf.set("dfs.nameservices", "hadoop102");
        conf.set("fs.defaultFS", "hdfs://hadoop102:8020");
        FileSystem fs = FileSystem.get(conf);

        //2 to get the file details
        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), true);
        while (listFiles.hasNext()) {
            LocatedFileStatus status = listFiles.next();
            // print out the details
            System.out.println(
                    status.getPath().getName() +
                            status.getLen() +
                            status.getPermission() +
                            status.getGroup()
            );
            //3 to get the block imformation
            BlockLocation[] blockLocations = status.getBlockLocations();
            for (BlockLocation blockLocation : blockLocations) {
                // get the host
                String[] hosts = blockLocation.getHosts();
                for (String host : hosts) {
                    System.out.println(host);
                }
            }
        }
        fs.close();
    }

}
