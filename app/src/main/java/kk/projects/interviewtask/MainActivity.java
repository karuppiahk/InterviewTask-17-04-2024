package kk.projects.interviewtask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import kk.projects.interviewtask.adapter.task_adapter;
import kk.projects.interviewtask.check_internet.Internet_connection_checking;
import kk.projects.interviewtask.model.task_model;
import kk.projects.interviewtask.rest.ApiClient;
import kk.projects.interviewtask.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private Context mContext = MainActivity.this;
    /*---------------------------check internet connection----------------------------------------------------*/
    boolean isShown = false, Connection;
    Internet_connection_checking int_chk;
    Dialog dialog;


    ImageView no_data;
    RecyclerView view_image_card;

    EditText top_editTextSearch;
    task_adapter image_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTheme().applyStyle(R.style.OverlayThemeLime, true);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        /*---------------------------hind actionbar----------------------------------------------------*/
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        overridePendingTransition(R.anim.enter, R.anim.exit);

        view_image_card = findViewById(R.id.view_image_card);
        no_data = findViewById(R.id.no_data);


        top_editTextSearch = findViewById(R.id.top_editTextSearch);
        top_editTextSearch.addTextChangedListener(mDateEntryWatcher);



        /*---------------------------check internet connection----------------------------------------------------*/
        int_chk = new Internet_connection_checking(MainActivity.this);
        Connection = int_chk.checkInternetConnection();
        if (!Connection) {

            ViewDialog alert = new ViewDialog();
            alert.showDialog(MainActivity.this);


            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        } else {
            imageget();
        }

    }


    private TextWatcher mDateEntryWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String words = s.toString();
            Log.e("wordlenth1", "" + s.toString());
            if (words.length() >= 1) {
                //Filter here
                if (image_adapter != null) {
                    image_adapter.getFilter().filter(s);
                }
            } else if (words.length() == 0) {
                View view = MainActivity.this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                imageget();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
    };


    /*---------------------------Get Images----------------------------------------------------*/
    private void imageget() {
        loadingshow();
        // get user data from session
        ApiInterface apiService = ApiClient.getInstance().getClient().create(ApiInterface.class);
        Call<List<task_model.ResponseItem>> call = apiService.taskload();

        call.enqueue(new Callback<List<task_model.ResponseItem>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)

            @Override
            public void onResponse(Call<List<task_model.ResponseItem>> call, Response<List<task_model.ResponseItem>> response) {
                //response.headers().get("Set-Cookie");
                int statusCode = response.code();
                Log.e("statusCode", "" + statusCode);
                if (statusCode == 200) {
                    hideloading();

                    if (response.body().isEmpty()) {

                        no_data.setVisibility(View.VISIBLE);
                        view_image_card.setVisibility(View.GONE);

                    } else {
                        no_data.setVisibility(View.GONE);
                        view_image_card.setVisibility(View.VISIBLE);


                         image_adapter = new task_adapter(mContext, response.body());
                        view_image_card.setHasFixedSize(true);
                        view_image_card.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                        view_image_card.setItemAnimator(new DefaultItemAnimator());
                        view_image_card.setAdapter(image_adapter);


                    }
                }
            }

            @Override
            public void onFailure(Call<List<task_model.ResponseItem>> call, Throwable t) {
                hideloading();
                Log.e("bugcode", "" + t.toString());
                Snackbar.make(MainActivity.this.findViewById(android.R.id.content), R.string.somthinnot_right, Snackbar.LENGTH_LONG).show();

            }
        });
    }


    /*---------------------------check internet connection----------------------------------------------------*/
    public class ViewDialog {

        public void showDialog(Activity activity) {
            Rect displayRectangle = new Rect();
            Window window = MainActivity.this.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.CustomAlertDialog);
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(activity).inflate(R.layout.nonnetdialog, viewGroup, false);

            builder.setView(dialogView);
            final AlertDialog alertDialog = builder.create();
            Button buttonOk = dialogView.findViewById(R.id.retry);
            buttonOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    int_chk = new Internet_connection_checking(MainActivity.this);
                    Connection = int_chk.checkInternetConnection();
                    if (Connection) {
                        Intent intent2 = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent2);
                        alertDialog.dismiss();
                    }


                }
            });
            alertDialog.show();

        }
    }

    /*-------------------Loading Images------------------*/
    public void loadingshow() {

        dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //...set cancelable false so that it's never get hidden
        dialog.setCancelable(false);
        //...that's the layout i told you will inflate later
        dialog.setContentView(R.layout.custom_loading_layout);

        //...initialize the imageView form infalted layout
        ImageView gifImageView = dialog.findViewById(R.id.custom_loading_imageView);

        /*
        it was never easy to load gif into an ImageView before Glide or Others library
        and for doing this we need DrawableImageViewTarget to that ImageView
        */
        // GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(gifImageView);

        //...now load that gif which we put inside the drawble folder here with the help of Glide

        Glide.with(MainActivity.this)
                .load(R.drawable.loader)
                .placeholder(R.drawable.loader)
                .centerCrop()
                .into(gifImageView);

        //...finaly show it
        dialog.show();
    }

    //..also create a method which will hide the dialog when some work is done
    public void hideloading() {
        dialog.dismiss();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);


                    }
                }).create().show();
    }
}