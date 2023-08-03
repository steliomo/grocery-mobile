package mz.co.commandline.grocery.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import java.io.File;

import javax.inject.Inject;

import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.files.FileService;
import mz.co.commandline.grocery.generics.dto.ErrorMessage;
import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.module.GroceryComponent;
import mz.co.commandline.grocery.user.dto.UserRole;
import mz.co.commandline.grocery.user.service.UserService;
import mz.co.commandline.grocery.util.FileUtil;
import mz.co.commandline.grocery.util.alert.AlertType;
import okhttp3.ResponseBody;

public abstract class BaseAuthActivity extends BaseActivity {

    @Inject
    UserService userService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GroceryComponent component = application.getComponent();
        component.inject(this);

        if (!userService.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        onGroceryCreate(savedInstanceState);
    }

    public abstract void onGroceryCreate(Bundle bundle);

    public boolean hasRole(UserRole userRole) {

        if (userRole.equals(userService.getUnitUser().getUserRole())) {
            return true;
        }

        return false;
    }

    public void downLoadFile(FileService fileService, String filePath) {
        fileService.loadPdfFile(filePath, new ResponseListner<ResponseBody>() {
            @Override
            public void success(ResponseBody fileResponse) {
                displayFile(fileResponse, filePath);
            }

            @Override
            public void businessError(ErrorMessage errorMessage) {
                dialogManager.dialog(AlertType.ERROR, errorMessage.getMessage(), null);
                Log.e("LOAD_FILE", errorMessage.getDeveloperMessage());
            }

            @Override
            public void error(String message) {
                dialogManager.dialog(AlertType.ERROR, getString(R.string.error_loading_file), null);
                Log.e("LOAD_FILE", message);
            }
        });
    }

    public void displayFile(ResponseBody body, String fileName) {
        FileUtil fileUtil = new FileUtil(this);
        File file = fileUtil.save(body.byteStream(), fileName);

        Intent target = new Intent(Intent.ACTION_VIEW);
        Uri uriForFile = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", file);
        target.setDataAndType(uriForFile, "application/pdf");
        target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        Intent chooser = Intent.createChooser(target, getString(R.string.open_with));

        startActivity(chooser);
    }

}
