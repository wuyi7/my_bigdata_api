package com.wuyi.hive;


import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

/**
 * @Author: WuYi at 2022-11-23 16:51
 * @Description:
 * 写好改方法之后打包上传到任意目录，然后再hive中上传
 * add jar /目录地址
 * 然后在hive中注册该方法
 * create temporary function myf as "com.wuyi.hive.MyUDF";
 * 此为临时模式，加上类全路径名即可
 * @Version:1.0
 */

public class MyUDF extends GenericUDF {
    @Override
    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
        if (arguments.length != 1) {
            throw new UDFArgumentException();
        }
        return PrimitiveObjectInspectorFactory.javaIntObjectInspector;
    }

    @Override
    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        String str = arguments[0].get().toString();

        //直接返回参数长度
        return str == null ? 0 : str.length();
    }

    //执行计划中显示的语句
    @Override
    public String getDisplayString(String[] children) {
        return "wuyi_hive_UDF_test";
    }
}
