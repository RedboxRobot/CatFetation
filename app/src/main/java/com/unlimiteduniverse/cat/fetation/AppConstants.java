package com.unlimiteduniverse.cat.fetation;

/**
 * Created by Irvin
 * Date: on 2018/7/16 0016.
 */

public class AppConstants {

    /**
     * path
     */
    public static final String IMAGE_SAVE_PATH = "image";

    /**
     * fragment key
     **/
    public static final String KEY_FRAGMENT = "key_fragment";
    public static final String KEY_PHOTO_BROWSER_EXTRA = "key_phone_browser_extra";

    /**
     * 正则表达式
     */
    public static final String LETTER_NUMBER = "[a-zA-Z\\d]*";  //字母或数字
    public static final String LETTER_NUMBER_CHINESE_ENGLISH = "^[\\u4E00-\\u9FA5A-Za-z0-9]+$";  //中英文数字
    public static final String LETTER_NUMBER_CHINESE_ENGLISH_BLOCK = "^[\\u4E00-\\u9FA5A-Za-z0-9\\s]+$";  //中英文数字加空格

    //Content Fragments
    public static final int PHOTO_EDIT_FRAGMENT = 0x00;
    public static final int TEMPLATE_PRINT_FRAGMENT = 0x01;//模板打印的
    public static final int PEINTER_SEARCH_FRAGMENT = 0x02;//搜索打印机
    public static final int PEINTER_MANAGE_FRAGMENT = 0x03;//打印机管理页面
    public static final int PEINTER_INFO_FRAGMENT = 0x04;//打印机管理的信息页面
    public static final int PEINTER_SELECT_FRAGMENT = 0x05;//打印机多台进行选择的页面
    public static final int PEINTER_NAME_FRAGMENT = 0x06;//打印机名称更改
    public static final int PEINTER_WIFI_INFO_FRAGMENT = 0x07;//打印机网络信息页面
    public static final int PEINTER_WIFI_LIST_FRAGMENT = 0x08;//打印机配网的wifi列表页面页面
    public static final int PEINTER_WIFI_SURE_FRAGMENT = 0x09;//打印机wifi配网的确认连接页面
    public static final int PEINTER_INK_INFO_FRAGMENT = 0x10;//打印机墨水信息页面
    public static final int PEINTER_SELECT_PRINTER_HISTORY_FRAGMENT = 0x11;//选择打印机的带历史打印机的页面
    public static final int PEINTER_PREVIEW_FRAGMENT = 0x12;//打印预览
    public static final int PEINTER_MANUAL_ALIGNMENT_FRAGMENT = 0x13;//打印头手动校准
    public static final int PEINTER_CLEAN_HEAD_FRAGMENT = 0x14;//打印头清洁
    public static final int PEINTER_SMEAR_PREVIEW_FRAGMENT = 0x15;//diy打印的打印预览页面
    public static final int PEINTER_ID_PHOTO_FRAGMENT = 0x16;//模板打印的证件照列表页面
    public static final int PEINTER_CROP_PREVIEW_FRAGMENT = 0x17;//模板打印的证件照的预览页面
    public static final int PEINTER_SPLICE_PREVIEW_FRAGMENT = 0x18;//拼接大图的预览页面
    public static final int NOTICE_DETAIL_FRAGMENT = 0x19;//拼接大图的预览页面
    public static final int USER_INFO_SETTING_FRAGMENT = 0x20;//用户的个人资料设置页面
    public static final int PHOTO_BROWSER_FRAGMENT = 0x21;//大图预览的页面
    public static final int USER_HOME_FRAGMENT = 0x22;//用户个人中心页面
    public static final int COMMUNITY_SEARCH_FRAGMENT = 0x23;//社区搜索界面
    public static final int FRAGMENT_EDIT_NAME = 0x24;//编辑名称
    public static final int FRAGMENT_EDIT_SIGNATURE = 0x25;//编辑个性签名
    public static final int SETTING_BASE_FRAGMENT = 0x26;//设置
    public static final int PRINT_SELECT_DRAFTS = 0x27;//选择草稿页面
    public static final int COMMUNITY_PUBLISH_FRAGMENT = 0x28;//发布素材页面
    public static final int USER_UPLOAD_FODDER = 0x29;//上传素材页面
    public static final int FANS_AND_FOCUS_FRAGMENT = 0x30;//粉丝和关注页面
    public static final int COMMUNITY_ADD_FAVORITE_FRAGMENT = 0x31;//添加分组页面
    public static final int COMMUNITY_MANAGE_FAVORITE_FRAGMENT = 0x32;//收藏分组管理页面
    public static final int COMMUNITY_MOVE_FAVORITE_FRAGMENT = 0x33;//收藏分组管理页面的移动分组页面
    public static final int COMMUNITY_RENAME_FAVORITE_FRAGMENT = 0x34;//收藏分组重命名页面
    public static final int COMMUNITY_SEARCH_RESULT_FRAGMENT = 0x35;//搜索结果页面
    public static final int ACCOUNT_SECURITY_FRAGMENT = 0x36;//账号安全
    public static final int FRAGMENT_NEW_MESSAGE_NOTIFY = 0x37;//设置的通知界面
    public static final int FRAGMENT_UPDATE_PHONE_HINT = 0x38;//验证手机号码界面
    public static final int FRAGMENT_CHECK_PASSWORD = 0x39;//验证密码界面
    public static final int FRAGMENT_UPDATE_PHONE = 0x40;//更改手机号码界面
    public static final int FRAGMENT_UPDATE_PASSWORD = 0x41;//用旧密码更换密码界面
    public static final int UPDATE_PASSWORD_USE_NUMBER = 0x42;//用手机号更换密码界面
    public static final int INFORM_AGAINST_FRAGMENT = 0x43;//举报

    //sp的key
    public static final String KEY_NEED_IMPROVE = "key_need_improve";

    //The key of Bundle
    public static final String KEY_PRINTER_SEARCH_TYPE = "key_printer_search_type";
    public static final String KEY_PRINTER_NAME = "key_printer_name";
    public static final String KEY_PEINTER_WIFI_SURE = "key_peinter_wifi_sure";
    public static final String KEY_PEINT_PREVIEW = "key_peint_preview";
    public static final String KEY_DIY_PREVIEW_DEGREE = "key_diy_preview_degree";
    public static final String KEY_DIY_PREVIEW_PATH = "key_diy_preview_path";
    //获取选择的图片角度信息,DIY打印的预览页面的背景图片
    public static final String KEY_DIY_PREVIEW_SMEAR_BG = "key_diy_preview_smear_bg";
    //获取选择的图片角度信息,DIY打印的预览页面的涂抹图片
    public static final String KEY_DIY_PREVIEW_SMEAR_TOP = "key_diy_preview_smear_top";
    //获取选择的图片角度信息,DIY打印的预览页面使用
    public static final String KEY_DIY_PREVIEW_SMEAR_TOP_DEGREE = "key_diy_preview_smear_top_degree";
    //模板打印的证件照的预览页面,宽
    public static final String KEY_CROP_PREVIEW_WIDTH = "key_crop_preview_width";
    //模板打印的证件照的预览页面,高
    public static final String KEY_CROP_PREVIEW_HEIGHT = "key_diy_preview_height";
    //模板打印的证件照的预览页面,数量
    public static final String KEY_CROP_PREVIEW_SUM = "key_diy_preview_sum";
    //模板打印的证件照的预览页面,uri
    public static final String KEY_CROP_PREVIEW_URI = "key_diy_preview_uri";
    ///拼接大图的预览页面 路径集合
    public static final String SPLICE_PATH_LIST = "key_splice_path_list";
    //用户昵称
    public static final String KEY_USER_NAME = "key_user_name";
    //签名
    public static final String KEY_USER_SIGNATURE = "key_user_signature";

    //用户的个人信息
    public static final String KEY_USER_INFO = "key_user_info";
    //是否提交用户资料
    public static final String KEY_USER_INFO_UPLOAD = "key_user_info_upload";
    //登录获取的鉴权信息
    public static final String KEY_USER_KEY = "key_user_key";
    ///拼接大图的预览页面 类型1是黑色的toolbar 上传素材和社交的图片预览时使用
    public static final String KEY_PHOTO_BROWSER_TYPE = "key_photo_browser_type";
    //选择草稿的
    public static final String KEY_SELECT_DRAFTS_LIST = "key_select_drafts_list";
    //选择草稿的类型的
    public static final String KEY_SELECT_DRAFTS_TYPE_LIST = "key_select_drafts_type_list";
    //选择草稿的最大数量
    public static final String KEY_SELECT_DRAFTS_LIST_MAX = "key_select_drafts_list_max";
    //发布页面的历史标签
    public static final String KEY_PUBLISH_FRAGMENT_HIS_LIST = "key_publish_fragment_his_list";
    //收藏分组重命名的key
    public static final String KEY_RENAME_FAVORITE = "key_rename_favorite";
    //收藏分组重命名的返回值的key
    public static final String KEY_RENAME_FRAGMENT_RESULT = "key_rename_fragment_result";
    //发现页面的不同布局页面的类型的key
    public static final String KEY_COMMUNITY_TYPE = "key_community_type";
    //发现页面的不同布局页面的类型的key   组id的
    public static final String KEY_COMMUNITY_TYPE_ID = "key_community_type_id";
    //发现页面的不同布局页面的类型的key 推荐的布局
    public static final String KEY_COMMUNITY_TYPE_COMM = "key_community_type_comm";
    //发现页面的不同布局页面的类型的key 关注的布局
    public static final String KEY_COMMUNITY_TYPE_FOUS = "key_community_type_fous";
    //发现页面的不同布局页面的类型的key 搜索的布局
    public static final String KEY_COMMUNITY_TYPE_SEARCH = "key_community_type_search";
    //发现页面的不同布局页面的类型的key 个人中心的布局
    public static final String KEY_COMMUNITY_TYPE_ME = "key_community_type_me";
    //上传素材页面的图片集合
    public static final String KEY_COMMUNITY_UPLOAD_IMAGE = "key_community_upload_image";
    //上传素材页面的图片集合
    public static final String KEY_COMMUNITY_UPLOAD_PROJECT = "key_community_upload_project";
    //上传素材页面的图片集合的类型
    public static final String KEY_COMMUNITY_UPLOAD_PROJECT_TYPE = "key_community_upload_project_type";
    //上传素材页面的图片集合
    public static final String KEY_COMMUNITY_UPLOAD_CONTENT = "key_community_upload_content";
    //上传素材页面的图片集合service的
    public static final String KEY_COMMUNITY_UPLOAD_IMAGE_SERVICE = "key_community_upload_image_service";
    //上传素材页面的图片集合service的
    public static final String KEY_COMMUNITY_UPLOAD_PROJECT_SERVICE = "key_community_upload_project_service";
    //上传素材页面的图片集合service的类型
    public static final String KEY_COMMUNITY_UPLOAD_PROJECT_TYPE_SERVICE = "key_community_upload_project_type_service";
    //上传素材页面的图片集合service的
    public static final String KEY_COMMUNITY_UPLOAD_CONTENT_SERVICE = "key_community_upload_content_service";
    //上传素材页面的图片集合service的
    public static final String KEY_COMMUNITY_UPLOAD_TAG_SERVICE = "key_community_upload_tag_service";
    //个人信息页面的分组信息的MD5
    public static final String KEY_COMMUNITY_USERINFO_GROUP_MD5 = "key_community_userinfo_group_md5";
    //移动分组的wbid
    public static final String KEY_COMMUNITY_MOVE_GROUP_WBID = "key_community_move_group_wbid";;
    //移动分组的cgid
    public static final String KEY_COMMUNITY_MOVE_GROUP_CGID = "key_community_move_group_cgid";
    //用户主页的用户id
    public static final String KEY_USER_CHECKEDID = "key_user_checkedid";
    //用户主页的用户id
    public static final String KEY_USER_HOME_FRAGMENT_CHECKEDID = "key_user_home_fragment_checkedid";
    //搜索的标签
    public static final String KEY_SEARCH_TAG = "key_search_tag";
}
