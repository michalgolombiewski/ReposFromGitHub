package com.github.reposfromgithub;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GithubService {

    private final OkHttpClient client = new OkHttpClient();
    private static final String TOKEN = "";

    public JSONArray getReposForUser(String username) throws IOException {
        String url = "https://api.github.com/users/" + username + "/repos";
        Request request = new Request.Builder()
                .url(url)
                .header("Accept", "application/vnd.github+json")
                .header("Authorization", "Bearer "+TOKEN)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            return new JSONArray(response.body().string());
        }
    }

    public List<Map<String, String>> getBranchesForRepo(String owner, String repo) throws IOException {
        String url = "https://api.github.com/repos/" + owner + "/" + repo + "/branches";

        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            JSONArray branchesArray = new JSONArray(response.body().string());
            List<Map<String, String>> branches = new ArrayList<>();
            for (int i = 0; i < branchesArray.length(); i++) {
                JSONObject branch = branchesArray.getJSONObject(i);
                Map<String, String> branchData = new HashMap<>();
                branchData.put("name", branch.getString("name"));
                branchData.put("Last commit sha", branch.getJSONObject("commit").getString("sha"));
                branches.add(branchData);
            }
            return branches;
        }
    }
}

