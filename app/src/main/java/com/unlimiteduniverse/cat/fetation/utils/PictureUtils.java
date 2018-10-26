package com.unlimiteduniverse.cat.fetation.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Base64;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by Irvin
 * on 2018/2/3 0003.
 */
public class PictureUtils {

    /**
     * 从相机拍照获取图片
     *
     * @param fragment
     * @param requestCode
     * @return
     */
    public static Uri takePictureFromCamera(Fragment fragment, int requestCode) {
        Uri uri = null;
        // 启动系统相机
        // 判断7.0android系统
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (PermissionsUtil.hasCameraPermissions(fragment) && PermissionsUtil.hasWritePermissions(fragment)) {
                uri = takePictureFromCameraForN(fragment, requestCode);
            }
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri imageCaptureUri = Uri.fromFile(
                    new File(Environment.getExternalStorageDirectory(),
                            "/Deli/photos/temp.jpg"));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageCaptureUri);
            fragment.startActivityForResult(intent, requestCode);
        }
        return uri;
    }

    public static Uri takePictureFromCamera(Activity activity, int requestCode) {
        Uri uri = null;
        // 启动系统相机
        // 判断7.0android系统
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (PermissionsUtil.hasCameraPermissions(activity) && PermissionsUtil.hasWritePermissions(activity)) {
                uri = takePictureFromCameraForN(activity, requestCode);
            }
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri imageCaptureUri = Uri.fromFile(
                    new File(Environment.getExternalStorageDirectory(),
                            "/Deli/photos/temp.jpg"));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageCaptureUri);
            activity.startActivityForResult(intent, requestCode);
        }
        return uri;
    }

    /**
     * 从相册选择图片
     *
     * @param activity
     * @param requestCode
     */
    public static void takePictureFromAlbum(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(
                MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
        //返回结果和标识
        activity.startActivityForResult(intent, requestCode);
    }

    public static void takePictureFromAlbum(Fragment fragment, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(
                MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
        //返回结果和标识
        fragment.startActivityForResult(intent, requestCode);
    }

    public static Uri takePictureFromCameraForN(Activity activity, int requestCode) {
        // 启动系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //临时添加一个临时权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //通过FileProvider获取uri
        Uri contentUri = FileProvider.getUriForFile(activity,
                "com.delicloud.app.smartoffice.FileProvider",
                new File(Environment.getExternalStorageDirectory(),
                        "/Deli/photos/temp.jpg"));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        activity.startActivityForResult(intent, requestCode);
        return contentUri;
    }

    public static Uri takePictureFromCameraForN(Fragment fragment, int requestCode) {
        // 启动系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //临时添加一个临时权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //通过FileProvider获取uri
        Uri contentUri = FileProvider.getUriForFile(fragment.getActivity(),
                "com.delicloud.app.smartoffice.FileProvider",
                new File(Environment.getExternalStorageDirectory(),
                        "/Deli/photos/temp.jpg"));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        fragment.startActivityForResult(intent, requestCode);
        return contentUri;
    }

    /**
     * 裁剪图片的方法.
     * 用于拍照完成或者选择本地图片之后
     */
    public static Uri startPhotoZoom(Fragment fragment, Uri inputUri, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(inputUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //开启临时权限
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //重点:针对7.0以上的操作
            intent.setClipData(ClipData.newRawUri(MediaStore.EXTRA_OUTPUT, inputUri));
        }

        Uri outputUri = getOutputUri();
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        try {
            fragment.startActivityForResult(intent, requestCode);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return outputUri;
    }

    public static void pictureZip(Context context, final Uri uri, final Callback callback) {
        final File inputFile = SystemFileUtils.uriToFile(uri);
        if (inputFile == null) {
            return;
        }
        Luban.with(context.getApplicationContext())
                .load(inputFile)                                   // 传入要压缩的图片列表
                .ignoreBy(100)                                // 忽略不压缩图片的大小
                .setTargetDir(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Deli/photos/")
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        callback.onPictureZipDone(Uri.parse(SystemFileUtils.fileToUri(file).toString()));
                        if (file.compareTo(inputFile) != 0) {
                            FileOperateUtils.getInstance().deleteFile(inputFile);
                        }
                        FileOperateUtils.getInstance().deleteFile(new File(Environment.getExternalStorageDirectory(),
                                "/Deli/photos/temp.jpg"));
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        FileOperateUtils.getInstance().deleteFile(inputFile);
                        FileOperateUtils.getInstance().deleteFile(new File(Environment.getExternalStorageDirectory(),
                                "/Deli/photos/temp.jpg"));
                    }
                }).launch();
    }

    public static void pictureZip(Context context, final String path, final Callback callback) {
        final File inputFile = new File(path);
        if (inputFile == null) {
            return;
        }
        Luban.with(context.getApplicationContext())
                .load(inputFile)                                   // 传入要压缩的图片列表
                .ignoreBy(100)                                // 忽略不压缩图片的大小
                .setTargetDir(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Deli/photos/")
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        callback.onPictureZipDone(Uri.parse(SystemFileUtils.fileToUri(file).toString()));
//                        if (file.compareTo(inputFile) != 0) {
//                            FileUtils.getInstance().deleteFile(inputFile);
//                        }
                        FileOperateUtils.getInstance().deleteFile(new File(Environment.getExternalStorageDirectory(),
                                "/Deli/photos/temp.jpg"));
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        FileOperateUtils.getInstance().deleteFile(inputFile);
                        FileOperateUtils.getInstance().deleteFile(new File(Environment.getExternalStorageDirectory(),
                                "/Deli/photos/temp.jpg"));
                    }
                }).launch();
    }

    public static Uri startPhotoZoom(Activity activity, Uri inputUri, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(inputUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //开启临时权限
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //重点:针对7.0以上的操作
            intent.setClipData(ClipData.newRawUri(MediaStore.EXTRA_OUTPUT, inputUri));
        }

        Uri outputUri = getOutputUri();
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        try {
            activity.startActivityForResult(intent, requestCode);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return outputUri;
    }

    private static Uri getOutputUri() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss", Locale.CHINA);
        String name = dateFormat.format(new java.util.Date());

        return Uri.fromFile(
                new File(Environment.getExternalStorageDirectory(),
                        "/Deli/thumb_" + name + ".jpg"));
    }

    public static String uriToBase64(Uri uri) {
        InputStream inputStream = PictureUtils.getBmpInputStream(uri.getPath());
        byte[] imageBytes = new byte[(int) new File(uri.getPath()).length()];
        try {
            inputStream.read(imageBytes, 0, imageBytes.length);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return new String(Base64.encode(imageBytes, Base64.DEFAULT));
    }

    /**
     * 从图片路径获取图片输入流
     *
     * @param filePath 图片路径
     * @return 获取失败返回null
     */
    private static InputStream getBmpInputStream(String filePath) {
        try {
            return new FileInputStream(new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Uri base64StringToUri(String base64String) {
        byte[] decode = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);

        //在SD卡上创建目录
        String sdcardPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Deli/img/";
        File tmpDir = new File(sdcardPath);
        if (!tmpDir.exists()) {
            tmpDir.mkdir();
        }

        File path = new File(sdcardPath + "/decodeImage.jpg");
        try {
            FileOutputStream fos = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.flush();
            fos.close();
            return Uri.fromFile(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据byte数组生成文件
     *
     * @param bytes 生成文件用到的byte数组
     */
    public static void createFileWithByte(Context context, byte[] bytes, String path, String fileName) {
        /**
         * 创建File对象，其中包含文件所在的目录以及文件的命名
         */
        FileOperateUtils.createFileDirectory(path);
        File file = new File(path, fileName);
        // 创建FileOutputStream对象
        FileOutputStream outputStream = null;
        // 创建BufferedOutputStream对象
        BufferedOutputStream bufferedOutputStream = null;
        try {
            // 如果文件存在则删除
            if (file.exists()) {
                file.delete();
            }
            // 在文件系统中根据路径创建一个新的空文件
            file.createNewFile();
            // 获取FileOutputStream对象
            outputStream = new FileOutputStream(file);
            // 获取BufferedOutputStream对象
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            // 往文件所在的缓冲输出流中写byte数据
            bufferedOutputStream.write(bytes);
            // 刷出缓冲输出流，该步很关键，要是不执行flush()方法，那么文件的内容是空的。
            bufferedOutputStream.flush();
        } catch (Exception e) {
            // 打印异常信息
            e.printStackTrace();
        } finally {
            // 关闭创建的流对象
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), path + fileName, fileName, null);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            context.sendBroadcast(intent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public interface Callback {
        void onPictureZipDone(Uri uri);
    }
}
