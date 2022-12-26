package com.wuyi.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.yarn.api.records.URL;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.net.URI;

/**
 * @Author: WuYi at 2022-11-23 11:30
 * @Description:
 * @Version:1.0
 */
public class HadoopTest {
    FileSystem fileSystem = null;

    @Before
    public void setUp() throws Exception {
        fileSystem = FileSystem.get(new URI("hdfs://hadoop102:8020"), new Configuration());
    }

    @After
    public void tearDown() throws Exception {
        fileSystem.close();
    }

    @Test
    public void listMyFiles() throws Exception {
        RemoteIterator<LocatedFileStatus> locatedFileStatusRemoteIterator =
                fileSystem.listFiles(new Path("/test_wuyi"), true);
        while (locatedFileStatusRemoteIterator.hasNext()) {
            LocatedFileStatus next = locatedFileStatusRemoteIterator.next();
            System.out.println(next.getPath().toString());
        }
    }

    @Test
    public void mkdir() throws Exception {
        fileSystem.mkdirs(new Path("/test_wuyi/test"));
    }


}
