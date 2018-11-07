package com.unlimiteduniverse.cat.fetation.mvp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.unlimiteduniverse.cat.fetation.AppConstants;
import com.unlimiteduniverse.cat.fetation.R;
import com.unlimiteduniverse.cat.fetation.mvp.base.BaseFragment;
import com.unlimiteduniverse.cat.fetation.mvp.ui.cats.fragment.EditNameFragment;
import com.unlimiteduniverse.cat.fetation.mvp.ui.cats.fragment.UserSignatureFragment;
import com.unlimiteduniverse.common.utils.StatusBarUtil;


/**
 * Created by Irvin
 * on 2017/8/23 0023.
 */

public class ContentActivity extends AppCompatActivity {

    private Fragment resultFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode(this);
        setContentView(R.layout.content_activity);
        Intent intent = getIntent();

        switchFragment(intent);
    }

    private void switchFragment(Intent intent) {
        int fragmentKey = intent.getIntExtra(AppConstants.KEY_FRAGMENT, 0);
        switch (fragmentKey) {
//            case AppConstants.PHOTO_EDIT_FRAGMENT:
////                replaceFragment(PhotoEditFragment.newInstance());
//                break;
//            case AppConstants.TEMPLATE_PRINT_FRAGMENT:
//                replaceFragment(TemplatePrintFragment.newInstance());
//                break;
//            case AppConstants.PEINTER_SEARCH_FRAGMENT://打印机搜索页面
//                replaceFragment(new PrinterSearchFragment());
//                break;
//            case AppConstants.PEINTER_MANAGE_FRAGMENT://打印机管理页面
//                replaceFragment(new PrinterManageFragment());
//                break;
//            case AppConstants.PEINTER_INFO_FRAGMENT://打印机管理的打印机信息页面
//                replaceFragment(new PrinterInfoFragment());
//                break;
//            case AppConstants.PEINTER_SELECT_FRAGMENT://打印机管理的打印机信息页面
//                replaceFragment(new SelectPrinterFragment());
//                break;
//            case AppConstants.PEINTER_NAME_FRAGMENT://打印机名称更改页面
//                replaceFragment(new PrinterNameFragment());
//                break;
//            case AppConstants.PEINTER_WIFI_INFO_FRAGMENT://打印机网络信息页面
//                replaceFragment(new PrinterWifiInfoFragment());
//                break;
//            case AppConstants.PEINTER_WIFI_LIST_FRAGMENT://打印机配网的wifi列表页面页面
//                replaceFragment(new SelectWifiFragment());
//                break;
//            case AppConstants.PEINTER_WIFI_SURE_FRAGMENT://打印机wifi配网的确认连接页面
//                replaceFragment(new WifiConnectFragment());
//                break;
//            case AppConstants.PEINTER_INK_INFO_FRAGMENT://打印机墨水信息页面
//                replaceFragment(new PrinterInkInfoFragment());
//                break;
//            case AppConstants.PEINTER_SELECT_PRINTER_HISTORY_FRAGMENT://选择打印机的带历史打印机的页面
//                replaceFragment(new PrinterHistoryFragment());
//                break;
//            case AppConstants.PEINTER_PREVIEW_FRAGMENT://打印预览
//                replaceFragment(new PrintPreviewFragment());
//                break;
//            case AppConstants.PEINTER_MANUAL_ALIGNMENT_FRAGMENT://打印头手动校准
//                replaceFragment(new ManualAlignmentFragment());
//                break;
//            case AppConstants.PEINTER_CLEAN_HEAD_FRAGMENT://打印头清洁
//                replaceFragment(new CleanPrintheadFragment());
//                break;
//            case AppConstants.PEINTER_SMEAR_PREVIEW_FRAGMENT://diy的打印预览
//                replaceFragment(new SmearPrintPreviewFragment());
//                break;
//            case AppConstants.PEINTER_ID_PHOTO_FRAGMENT://模板打印的证件照列表页面
//                replaceFragment(new IdPhotoFragment());
//                break;
//            case AppConstants.PEINTER_CROP_PREVIEW_FRAGMENT://模板打印的证件照的预览页面
//                replaceFragment(CropPreviewFragment.newInstance());
//                break;
//            case AppConstants.PEINTER_SPLICE_PREVIEW_FRAGMENT://拼接大图的预览页面
//                replaceFragment(SplicePreviewFragment.newInstance());
//                break;
//            case AppConstants.NOTICE_DETAIL_FRAGMENT:
//                replaceFragment(NoticeDetailFragment.newInstance());
//                break;
//            case AppConstants.USER_INFO_SETTING_FRAGMENT:
//                replaceFragment(UserInfoSettingFragment.newInstance());
//                break;
//            case AppConstants.USER_HOME_FRAGMENT://用户个人中心页面
//                String checkedId = intent.getStringExtra(KEY_USER_HOME_FRAGMENT_CHECKEDID);
//                replaceFragment(UserHomeFragment.newInstance(checkedId));
//                break;
//            case AppConstants.COMMUNITY_SEARCH_FRAGMENT://社区搜索界面
//                replaceFragment(CommunitySearchFragment.newInstance());
//                break;
            case AppConstants.FRAGMENT_EDIT_NAME:
                replaceFragment(EditNameFragment.newInstance());
                break;
            case AppConstants.FRAGMENT_EDIT_SIGNATURE:
                replaceFragment(UserSignatureFragment.newInstance());
                break;
//            case AppConstants.SETTING_BASE_FRAGMENT:
//                replaceFragment(SettingBaseFragment.newInstance());
//                break;
//            case AppConstants.USER_UPLOAD_FODDER://上传素材页面
//                replaceFragment(UploadFodderFragemnt.newInstance());
//                break;
//            case AppConstants.PRINT_SELECT_DRAFTS://选择草稿页面
//                replaceFragment(SelectDraftsFragment.newInstance());
//                break;
//            case AppConstants.COMMUNITY_PUBLISH_FRAGMENT://发布素材页面
//                replaceFragment(PublishFragment.newInstance());
//                break;
//            case AppConstants.COMMUNITY_ADD_FAVORITE_FRAGMENT://添加分组页面
//                replaceFragment(CreateFavoriteGroupFragment.newInstance());
//                break;
//            case AppConstants.FANS_AND_FOCUS_FRAGMENT://发布素材页面
//                replaceFragment(FansAndFocusFragment.newInstance());
//                break;
//            case AppConstants.COMMUNITY_RENAME_FAVORITE_FRAGMENT://收藏分组重命名页面
//                replaceFragment(RenameFavoriteGroupFragment.newInstance());
//                break;
//            case AppConstants.COMMUNITY_MANAGE_FAVORITE_FRAGMENT://收藏分组管理页面
//                replaceFragment(ManageFavoriteGroupFragment.newInstance());
//                break;
//            case AppConstants.COMMUNITY_MOVE_FAVORITE_FRAGMENT://收藏分组管理页面 的移动分组页面
//                replaceFragment(MoveFavoriteGroupFragment.newInstance());
//                break;
//            case AppConstants.COMMUNITY_SEARCH_RESULT_FRAGMENT://搜索结果页面 的移动分组页面
//                replaceFragment(SearchResultFragment.newInstance());
//                break;
//            case AppConstants.ACCOUNT_SECURITY_FRAGMENT:
//                replaceFragment(AccountSecurityFragment.newInstance());
//                break;
//            case AppConstants.FRAGMENT_NEW_MESSAGE_NOTIFY:
//                replaceFragment(NewMessageNotifyFragment.newInstance());
//                break;
//            case AppConstants.FRAGMENT_UPDATE_PHONE_HINT:
//                replaceFragment(UpdatePhoneNumberHintFragment.newInstance());
//                break;
//            case AppConstants.FRAGMENT_CHECK_PASSWORD:
//                replaceFragment(CheckPasswordFragment.newInstance());
//                break;
//            case AppConstants.FRAGMENT_UPDATE_PHONE:
//                replaceFragment(UpdatePhoneNumberFragment.newInstance());
//                break;
//            case AppConstants.FRAGMENT_UPDATE_PASSWORD:
//                replaceFragment(UpdatePasswordFragment.newInstance());
//                break;
//            case AppConstants.UPDATE_PASSWORD_USE_NUMBER:
//                replaceFragment(UpdatePasswordUseNumberFragment.newInstance());
//                break;
//            case AppConstants.INFORM_AGAINST_FRAGMENT:
//                replaceFragment(InformAgainstFragment.newInstance());
//                break;
            default:
                finish();
                break;
        }
    }

    public void replaceFragment(Fragment fragment) {
        resultFragment = fragment;
        replaceFragment(R.id.content, fragment);
    }

    public void replaceFragment(int id, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(id, fragment).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultFragment != null) {
            Fragment fragment = resultFragment;
            fragment.onActivityResult(requestCode, resultCode, intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content);
            if (fragment instanceof BaseFragment) {
                BaseFragment bsfragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.content);
                if (bsfragment.isFragmentHandleBack()) {
                    bsfragment.onBackClick();
                    return true;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /*//返回键处理
    @Override
    public void onBackPressed() {
        if (!HandleBackUtil.handleBackPress(this)) {
            super.onBackPressed();
        }
    }*/
}
