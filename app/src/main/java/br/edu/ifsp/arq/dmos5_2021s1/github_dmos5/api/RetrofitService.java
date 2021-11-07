package br.edu.ifsp.arq.dmos5_2021s1.github_dmos5.api;

import java.util.List;

import br.edu.ifsp.arq.dmos5_2021s1.github_dmos5.adapter.RepoName;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitService {

    @GET("{user}/repos")
    Call<List<RepoName>> get(@Path("user") String user);
}
