package com.pro.shopfee.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffeeshop.R;
import com.pro.shopfee.activity.admin.AdminMainActivity;
import com.pro.shopfee.model.User;
import com.pro.shopfee.prefs.DataStoreManager;
import com.pro.shopfee.utils.Constant;
import com.pro.shopfee.utils.GlobalFunction;
import com.pro.shopfee.utils.StringUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends BaseActivity {

    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;
    private LinearLayout layoutRegister;
    private TextView tvForgotPassword;

    private boolean isEnableButtonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        initUi();
        initListener();
    }

    private void initUi() {
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        layoutRegister = findViewById(R.id.layout_register);
        tvForgotPassword = findViewById(R.id.tv_forgot_password);
    }

    private void initListener() {

        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!StringUtil.isEmpty(s.toString())) {
                    edtEmail.setBackgroundResource(R.drawable.bg_white_corner_16_border_main);
                } else {
                    edtEmail.setBackgroundResource(R.drawable.bg_white_corner_16_border_gray);
                }

                String strPassword = edtPassword.getText().toString().trim();
                if (!StringUtil.isEmpty(s.toString()) && !StringUtil.isEmpty(strPassword)) {
                    isEnableButtonLogin = true;
                    btnLogin.setBackgroundResource(R.drawable.bg_button_enable_corner_16);
                } else {
                    isEnableButtonLogin = false;
                    btnLogin.setBackgroundResource(R.drawable.bg_button_disable_corner_16);
                }
            }
        });

        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!StringUtil.isEmpty(s.toString())) {
                    edtPassword.setBackgroundResource(R.drawable.bg_white_corner_16_border_main);
                } else {
                    edtPassword.setBackgroundResource(R.drawable.bg_white_corner_16_border_gray);
                }

                String strEmail = edtEmail.getText().toString().trim();
                if (!StringUtil.isEmpty(s.toString()) && !StringUtil.isEmpty(strEmail)) {
                    isEnableButtonLogin = true;
                    btnLogin.setBackgroundResource(R.drawable.bg_button_enable_corner_16);
                } else {
                    isEnableButtonLogin = false;
                    btnLogin.setBackgroundResource(R.drawable.bg_button_disable_corner_16);
                }
            }
        });

        layoutRegister.setOnClickListener(
                v -> GlobalFunction.startActivity(this, RegisterActivity.class));

        btnLogin.setOnClickListener(v -> onClickValidateLogin());
        tvForgotPassword.setOnClickListener(
                v -> GlobalFunction.startActivity(this, ForgotPasswordActivity.class));
    }

    private void onClickValidateLogin() {
        if (!isEnableButtonLogin) return;

        String strEmail = edtEmail.getText().toString().trim();
        String strPassword = edtPassword.getText().toString().trim();
        if (StringUtil.isEmpty(strEmail)) {
            showToastMessage(getString(R.string.msg_email_require));
        } else if (StringUtil.isEmpty(strPassword)) {
            showToastMessage(getString(R.string.msg_password_require));
        } else if (!StringUtil.isValidEmail(strEmail)) {
            showToastMessage(getString(R.string.msg_email_invalid));
        } else {
            if(strEmail.contains(Constant.ADMIN_EMAIL_FORMAT)){
                loginUserFirebase(strEmail, strPassword);
            }
            else{
                loginUserFirebase(strEmail, strPassword);
            }
//            if (rdbAdmin.isChecked()) {
//                if (!strEmail.contains(Constant.ADMIN_EMAIL_FORMAT)) {
//                    Toast.makeText(this, getString(R.string.msg_email_invalid_admin), Toast.LENGTH_SHORT).show();
//                } else {
//                    loginUserFirebase(strEmail, strPassword);
//                }
//            } else {
//                if (strEmail.contains(Constant.ADMIN_EMAIL_FORMAT)) {
//                    Toast.makeText(this, getString(R.string.msg_email_invalid_user), Toast.LENGTH_SHORT).show();
//                } else {
//                    loginUserFirebase(strEmail, strPassword);
//                }
//            }
        }
    }

    private void loginUserFirebase(String email, String password) {
        showProgressDialog(true);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    showProgressDialog(false);
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            User userObject = new User(user.getEmail(), password);
                            if (user.getEmail() != null && user.getEmail().contains(Constant.ADMIN_EMAIL_FORMAT)) {
                                userObject.setAdmin(true);
                            }
                            DataStoreManager.setUser(userObject);
                            goToMainActivity();
                        }
                    } else {
                        showToastMessage(getString(R.string.msg_login_error));
                    }
                });
    }

    private void goToMainActivity() {
        if (DataStoreManager.getUser().isAdmin()) {
            GlobalFunction.startActivity(this, AdminMainActivity.class);
        } else {
            GlobalFunction.startActivity(this, MainActivity.class);
        }
        finishAffinity();
    }
}