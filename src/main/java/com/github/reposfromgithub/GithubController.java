package com.github.reposfromgithub;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GithubController {

    @Autowired
    private GithubService githubService;

    @GetMapping("/repos/{username}")
    public ResponseEntity<Object> getRepos(@PathVariable String username, @RequestHeader("Accept") String acceptHeader) {
        if ("application/xml".equals(acceptHeader)) {
            return ResponseEntity.status(406).body(Map.of("status", "406", "Message", "Only application/json is supported"));
        }

        try {
            JSONArray reposArray = githubService.getReposForUser(username);
            List<Map<String, Object>> responseList = new ArrayList<>();

            for (int i = 0; i < reposArray.length(); i++) {
                JSONObject repo = reposArray.getJSONObject(i);
                if (!repo.getBoolean("fork")) {
                    Map<String, Object> repoDetails = new HashMap<>();
                    repoDetails.put("Repository Name", repo.getString("name"));
                    repoDetails.put("Owner Login", repo.getJSONObject("owner").getString("login"));
                    repoDetails.put("Branches", githubService.getBranchesForRepo(repo.getJSONObject("owner").getString("login"), repo.getString("name")));
                    responseList.add(repoDetails);
                }
            }
            return ResponseEntity.ok(responseList);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(Map.of("status", "404", "Message", "User not found"));
        }
    }
}


