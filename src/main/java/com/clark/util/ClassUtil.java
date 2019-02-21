package com.clark.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.stream.Collectors;

/**
 * @Author: ClarkRao
 * @Date: 2019/2/21 21:25
 * @Description: 类操作工具类
 */
@Slf4j
public class ClassUtil {

    /**
     * file形式url协议
     */
    public static final String FILE_PROTOCOL = "file";

    /**
     * jar形式url协议
     */
    public static final String JAR_PROTOCOL = "jar";

    /**
     * 获取ClassLoader
     * @return
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 获取Class
     * @param className
     * @return
     */
    public static Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            log.error("load class error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 实例化class
     * @param className
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(String className) {
        try {
            Class<?> clazz = loadClass(className);
            return ((T) clazz.newInstance());
        } catch (Exception e) {
            log.error("newInstance error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 实例化class
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<?> clazz) {
        try {
            return (T) clazz.newInstance();
        } catch (Exception e) {
            log.error("newInstance error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置类的属性值
     * @param filed
     * @param target
     * @param value
     */
    public static void setFiled(Field filed, Object target, Object value) {
        setFiled(filed,target,value,true);
    }

    /**
     * 设置类的属性值
     * @param filed
     * @param target
     * @param value
     * @param accessible
     */
    private static void setFiled(Field filed, Object target, Object value, boolean accessible) {
        //filed.setAccessible(true);得作用就是让我们在用反射时访问私有变量
        filed.setAccessible(accessible);
        try {
            filed.set(target,value);
        } catch (IllegalAccessException e) {
            log.error("setField error", e);
            throw new RuntimeException(e);
        }
    }

    public static Set<Class<?>> getPackageClass(String basePackage) {
        URL url = getClassLoader().getResource(basePackage.replace(".", "/"));
        if (null == url) {
            throw new RuntimeException("无法获取项目路径文件");
        }
        try {
            if (url.getProtocol().equals(FILE_PROTOCOL)) {
                // 若为普通文件夹，则遍历
                File file = new File(url.getFile());
                Path basePath = file.toPath();
                //walk()产生该目录下由整个目录树中所有文件构成的List<File>
                return Files.walk(basePath)
                        .filter(path -> path.toFile().getName().endsWith(".class"))
                        .map(path -> getClassByPath(path, basePath, basePackage))
                        .collect(Collectors.toSet());
            }else  if (url.getProtocol().equalsIgnoreCase(JAR_PROTOCOL)){
                // 若在 jar 包中，则解析 jar 包中的 entry
                JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                return jarURLConnection.getJarFile().stream()
                        .filter(jarEntry -> jarEntry.getName().endsWith(".class"))
                        .map(ClassUtil::getClassByJar)
                        .collect(Collectors.toSet());
            }
            return Collections.emptySet();
        } catch (IOException e) {
            log.error("load package error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 从path获取class
     * @param classPath
     * @param basePath
     * @param basePackage
     * @return
     */
    private static Class<?> getClassByPath(Path classPath, Path basePath, String basePackage) {
        String packageName  = classPath.toString().replace(basePath.toString(), "");
        String className = (basePackage + packageName)
                .replace("/", ".")
                .replace("\\", ".")
                .replace(".class", "");
        return loadClass(className);
    }

    /**
     * 从jar包获取Class
     */
    private static Class<?> getClassByJar(JarEntry jarEntry) {
        String jarEntryName = jarEntry.getName();
        // 获取类名
        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
        return loadClass(className);
    }
}
