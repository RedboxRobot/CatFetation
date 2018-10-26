package com.unlimiteduniverse.cat.fetation.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

/**
 * @author ChengXinPing
 * @time 2017/11/27 18:06
 */

public class FileOperateUtils {
    private static final FileOperateUtils ourInstance = new FileOperateUtils();

    public static FileOperateUtils getInstance() {
        return ourInstance;
    }

    private FileOperateUtils() {
    }

    /**
     * 删除方法整个文件夹里面的文件
     *
     * @param file 文件夹
     */
    public void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                deleteFile(f);
            }
            // file.delete();//如要保留文件夹，只删除文件，请注释这行
        } else if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 删除方法整个文件夹里面的文件
     *
     * @param file 文件夹
     */
    public void deleteFileWithFolder(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                deleteFile(f);
            }
            file.delete();//如要保留文件夹，只删除文件，请注释这行
        } else if (file.exists()) {
            file.delete();
        }
    }

    public static void createFileDirectory(String dir) {
        File fileDir = new File(dir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
    }

    /**
     * 重复文件重命名
     *
     * @param path     目标文件夹
     * @param fileName 需要判断的文件名
     * @return 新名字
     */
    public static String reNameFile(String path, String fileName) {
        String fileNameNoEx = getFileNameNoEx(fileName);
        String ext = getExtensionName(fileName);
        List<String> repeatFileName = getRepeatFileName(fileNameNoEx, ext, getFileNames(path));
        int max = max(fileNameNoEx, ext, repeatFileName);
        int index = max + 1;

        if (repeatFileName != null && repeatFileName.size() > 0) {
            return fileNameNoEx + "(" + index + ")" + "." + getExtensionName(fileName);
        } else {
            return fileName;
        }
    }

    // 获取文件名
    public static String getFileNameFromPath(String filepath) {
        if ((filepath != null) && (filepath.length() > 0)) {
            int sep = filepath.lastIndexOf('/');
            if ((sep > -1) && (sep < filepath.length() - 1)) {
                return filepath.substring(sep + 1);
            }
        }
        return filepath;
    }

    // 获取不带扩展名的文件名
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    // 获取文件扩展名
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return "";
    }

    // 获取带.的文件扩展名
    public static String getReplaceExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot);
            }
        }
        return "";
    }

    /**
     * 获取目录下全部文件的文件名list
     *
     * @param fileAbsolutePath 目标文件夹
     * @return list
     */

    public static Vector<String> getFileNames(String fileAbsolutePath) {
        Vector<String> vecFile = new Vector<String>();
        File file = new File(fileAbsolutePath);
        File[] subFile = file.listFiles();
        if (subFile == null) {
            return vecFile;
        }

        for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
            // 判断是否为文件夹
            if (!subFile[iFileLength].isDirectory()) {
                String filename = subFile[iFileLength].getName();
                vecFile.add(filename);
            }
        }
        return vecFile;
    }

    /**
     * 获取重名文件list中括号里面的最大值
     * <p>
     * 比如 1(1).txt,1(200).txt,1.txt  会找出200
     * 比如  1(200).txt,1(200)(2).txt,1(200)(1).txt 会找出2
     *
     * @param fileName  不包含后缀的文件名
     * @param ext       文件格式
     * @param fileNames 重名文件list
     * @return
     */
    public static int max(String fileName, String ext, List<String> fileNames) {
        int max = 0;

        for (String name : fileNames) {
            String s = name.replaceFirst(fileName, "").replace("." + ext, "");
            if (!TextUtils.isEmpty(s)) {
                s = s.replace("(", "").replace(")", "");
                int i = 0;
                try {
                    i = Integer.parseInt(s);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                if (i > max) {
                    max = i;
                }
            }
        }

        return max;
    }

    public static String hasSameFile(String md5, String fileName, String path) {
        String sameFilePath = "";
        List<File> allFiles = getGivenPathAllFiles(path, new ArrayList<File>());
        List<File> repeatFiles = getRepeatFile(fileName, allFiles);

        if (repeatFiles == null || repeatFiles.isEmpty()) {
            return sameFilePath;
        }
        for (File file : repeatFiles) {
            //文件太大转MD5太慢 可以转为异步获取这里默认MD5相等
            if (file.length() > 50 * 1024 *1024) {
                return file.getPath();
            }
            String fileMd5 = getFileMD5Sync(file);
            if (TextUtils.isEmpty(fileMd5)) {
                continue;
            }
            if (fileMd5.toLowerCase().equals(md5.toLowerCase())) {
                sameFilePath = file.getPath();
                return sameFilePath;
            }
        }

        return sameFilePath;
    }

    public static void hasSameFileAsync(final String md5, String fileName, String path, final FilePathCallback callback) {
        final String[] sameFilePath = {""};
        List<File> allFiles = getGivenPathAllFiles(path, new ArrayList<File>());
        List<File> repeatFiles = getRepeatFile(fileName, allFiles);

        if (repeatFiles == null || repeatFiles.isEmpty()) {
            callback.onFilePathDone(sameFilePath[0]);
            return;
        }
        for (final File file : repeatFiles) {
            getFileMD5Async(file, new MD5Callback() {
                @Override
                public void onGetMd5Done(String innerMd5) {
                    if (innerMd5.toLowerCase().equals(md5.toLowerCase())) {
                        sameFilePath[0] = file.getPath();
                        if (callback != null) {
                            callback.onFilePathDone(sameFilePath[0]);
                        }
                    }
                }
            });
        }
    }

    //获取指定路径下的所有文件名
    public static List<File> getGivenPathAllFiles(String path, List<File> files) {
        File file = new File(path);
        // get the folder list
        File[] array = file.listFiles();

        for (int i = 0; i < array.length; i++) {
            if (array[i].isFile()) {
                // only take file name
                files.add(array[i]);
            } else if (array[i].isDirectory()) {
                getGivenPathAllFiles(array[i].getPath(), files);
            }
        }
        return files;
    }

    public static List<File> getRepeatFile(String fileName, List<File> allFiles) {
        List<File> fileNameList = new ArrayList<>();
        for (File s : allFiles) {
            if (s.getName().contains(getFileNameNoEx(fileName))
                    && (getExtensionName(s.getName()).toLowerCase()).equals(getExtensionName(fileName).toLowerCase())) {
                fileNameList.add(s);
            }
        }
        return fileNameList;
    }


    /**
     * 获取重名文件list
     * <p>
     * 比如 文件名 1,扩展格式txt ,文件真名 = 1.txt,  会找出 1(1).txt,1(2).txt,1.txt
     * 比如 文件名 1(1),扩展格式txt ,文件真名 = 1(1).txt, 会找出 1(1).txt,1(1)(2).txt,1(1)(2).txt
     *
     * @param fileName 不包含后缀的文件名
     * @param ext      文件格式
     * @param allFiles 文件夹下全部文件名的list
     * @return
     */
    public static List<String> getRepeatFileName(String fileName, String ext, List<String> allFiles) {
        String regex = "\\([0-9]+\\)";
        String fileNameWithReplace = escapeString(fileName);
        String myRegex = fileNameWithReplace + regex + "\\." + ext;
        String newRegex = "(" + fileNameWithReplace + "\\." + ext + ")";
        myRegex = myRegex + "|" + newRegex;
        Pattern p = Pattern.compile(myRegex);

        List<String> fileNameList = new ArrayList<>();
        for (String s : allFiles) {
            if (p.matcher(s).find()) {
                fileNameList.add(s);
            }
        }
        return fileNameList;
    }

    /**
     * 转义字符串   (    [     {    /    ^    -    $     ¦    }    ]    )    ?    *    +    .
     *
     * @param s 需要转义的字符串
     * @return 转义完之后的字符串
     */
    public static String escapeString(String s) {
        if (!TextUtils.isEmpty(s)) {

            return s.replace("(", "\\(")
                    .replace("[", "\\[")
                    .replace("{", "\\{")
                    .replace("/", "\\/")
                    .replace("^", "\\^")
                    .replace("-", "\\-")
                    .replace("$", "\\$")
                    .replace("¦", "\\¦")
                    .replace("}", "\\}")
                    .replace("]", "\\]")
                    .replace(")", "\\)")
                    .replace("?", "\\?")
                    .replace("*", "\\*")
                    .replace("+", "\\+")
                    .replace(".", "\\.");

        } else {
            return s;
        }
    }

    //判断文件是否存在
    public static boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public static String getFileMD5Sync(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest;
        FileInputStream in;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bytesToHexString(digest.digest());
    }

    public static void getFileMD5Async(final File file, final MD5Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!file.isFile()) {
                    callback.onGetMd5Done(null);
                    return;
                }
                MessageDigest digest;
                FileInputStream in;
                byte buffer[] = new byte[1024];
                int len;
                try {
                    digest = MessageDigest.getInstance("MD5");
                    in = new FileInputStream(file);
                    while ((len = in.read(buffer, 0, 1024)) != -1) {
                        digest.update(buffer, 0, len);
                    }
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onGetMd5Done(null);
                    return;
                }
                callback.onGetMd5Done(bytesToHexString(digest.digest()));

            }
        }).start();
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 文件转base64字符串
     * @param file
     * @return
     */
    public static String fileToBase64(File file) {
        String base64 = null;
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] bytes = new byte[in.available()];
            int length = in.read(bytes);
            base64 = Base64.encodeToString(bytes, 0, length, Base64.DEFAULT);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return base64;
    }

    /**
     * base64字符串转文件
     * @param base64
     * @return
     */
    public static File base64ToFile(String base64) {
        File file = null;
        String fileName = "/Petssions/record/testFile.amr";
        FileOutputStream out = null;
        try {
            // 解码，然后将字节转换为文件
            file = new File(Environment.getExternalStorageDirectory(), fileName);
            if (!file.exists())
                file.createNewFile();
            byte[] bytes = Base64.decode(base64, Base64.DEFAULT);// 将字符串转换为byte数组
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            byte[] buffer = new byte[1024];
            out = new FileOutputStream(file);
            int bytesum = 0;
            int byteread = 0;
            while ((byteread = in.read(buffer)) != -1) {
                bytesum += byteread;
                out.write(buffer, 0, byteread); // 文件写操作
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (out!= null) {
                    out.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return file;
    }

    interface MD5Callback {
        void onGetMd5Done(String md5);
    }

    public interface FilePathCallback {
        void onFilePathDone(String path);
    }
}
