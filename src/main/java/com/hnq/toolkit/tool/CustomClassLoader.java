package com.hnq.toolkit.tool;

import java.io.*;
import java.util.HashSet;

/**
 * 自定义ClassLoader动态加载Class
 *
 * @author henengqiang
 * @date 2019/10/28
 */
public class CustomClassLoader extends ClassLoader {

    /**
     * 需要该类加载器直接加载的类文件的基目录
     */
    private String baseDir;

    /**
     * 需要由该类加载器直接加载的类名
     */
    private HashSet<String> dynaClazzs;

    public CustomClassLoader(String baseDir, HashSet<String> dynaClazzs) {
        // 指定父类加载器为null
        super(null);
        this.baseDir = baseDir;
        this.dynaClazzs = new HashSet<>();
    }

    private void loadClassByMe(String[] clazzs) {
        for (String clazz : clazzs) {
            loadDirectory(clazz);
            dynaClazzs.add(clazz);
        }
    }

    private Class loadDirectory(String name) {
        Class cls = null;
        StringBuilder sb = new StringBuilder(baseDir);
        String className = name.replace('.', File.separatorChar) + ".class";
        sb.append(File.separator).append(className);
        File classF = new File(sb.toString());
        try {
            cls = instantiateClass(name, new FileInputStream(classF), classF.length());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return cls;
    }

    private Class instantiateClass(String name, InputStream fin, long len) {
        byte[] raw = new byte[(int) len];
        try {
            fin.read();
            fin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(name, raw, 0, raw.length);
    }

    @Override
    protected Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class cls = findLoadedClass(name);
        if (!this.dynaClazzs.contains(name) && cls == null) {
            cls = getSystemClassLoader().loadClass(name);
        }
        if (cls == null) {
            throw new ClassNotFoundException(name);
        }
        if (resolve) {
            resolveClass(cls);
        }
        return cls;
    }
}
