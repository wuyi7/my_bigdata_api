package com.wuyi.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

import java.io.IOException;

/**
 * @Author: WuYi at 2023-02-15 14:44
 * @Description:
 * @Version:1.0
 */
public class HadoopTest2 {
    public static void main(String[] args) throws IOException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","file:///");
        FileSystem fileSystem = FileSystem.get(conf);
    }
}
