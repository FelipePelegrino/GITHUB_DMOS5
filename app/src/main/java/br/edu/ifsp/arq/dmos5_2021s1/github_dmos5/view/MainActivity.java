package br.edu.ifsp.arq.dmos5_2021s1.github_dmos5.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.edu.ifsp.arq.dmos5_2021s1.github_dmos5.R;
import br.edu.ifsp.arq.dmos5_2021s1.github_dmos5.adapter.ItemGitAdapter;
import br.edu.ifsp.arq.dmos5_2021s1.github_dmos5.adapter.RepoName;
import br.edu.ifsp.arq.dmos5_2021s1.github_dmos5.api.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText mEditUsername;
    private Button mButtonSearch;
    private RecyclerView mRecyclerGit;
    private ItemGitAdapter mAdapter;
    private List<RepoName> mRepos;
    private Retrofit mRetrofit;

    private final int REQUEST_PERMISSION = 1;
    private static final String BASE_URL = "https://api.github.com/users/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVariables();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equalsIgnoreCase(Manifest.permission.INTERNET) && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    searchRepos();
                }
            }
        }
    }

    public boolean isPermissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission(String permission) {
        final Activity activity = this;
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            new AlertDialog.Builder(this)
                    .setMessage(R.string.explain_internet_permission)
                    .setPositiveButton(R.string.button_positive_permission_text,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ActivityCompat.requestPermissions(activity, new String[]{permission}, REQUEST_PERMISSION);
                                }
                            })
                    .setNegativeButton(R.string.button_negative_permission_text,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                    .show();
        } else {
            ActivityCompat.requestPermissions(
                    activity,
                    new String[]{
                            permission
                    },
                    REQUEST_PERMISSION);
        }
    }

    private void initVariables() {
        mEditUsername = findViewById(R.id.edit_username);
        mButtonSearch = findViewById(R.id.button_search);
        mAdapter = new ItemGitAdapter(mRepos);
        mRecyclerGit = findViewById(R.id.recycler_git_repos);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerGit.setLayoutManager(layoutManager);
        mRecyclerGit.setAdapter(mAdapter);
        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == mButtonSearch) {
                    if (isPermissionGranted(Manifest.permission.INTERNET)) {
                        searchRepos();
                    } else {
                        requestPermission(Manifest.permission.INTERNET);
                    }
                }
            }
        });
    }

    private void searchRepos() {
        if(mEditUsername.getText() != null && !mEditUsername.getText().toString().isEmpty()) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitService retrofitService = mRetrofit.create(RetrofitService.class);
            Call<List<RepoName>> myCall = retrofitService.get(mEditUsername.getText().toString());

            myCall.enqueue(new Callback<List<RepoName>>() {
                @Override
                public void onResponse(Call<List<RepoName>> call, Response<List<RepoName>> response) {
                    updateView(response.body());
                }

                @Override
                public void onFailure(Call<List<RepoName>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, R.string.error_api, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, R.string.search_repos_invalid_input, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateView(List<RepoName> repoNames) {
        mAdapter.setmData(repoNames);
        mAdapter.notifyDataSetChanged();
    }
}