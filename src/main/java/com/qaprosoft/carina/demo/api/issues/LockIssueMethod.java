package com.qaprosoft.carina.demo.api.issues;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.carina.core.foundation.utils.Configuration;
import com.qaprosoft.carina.core.foundation.utils.R;

public class LockIssueMethod extends AbstractApiMethodV2 {

    public LockIssueMethod(String owner, String repoName, int issueNumber) {
        super("api/issues/_put/rq.json", null);
        replaceUrlPlaceholder("base_url", Configuration.getEnvArg("api_url"));
        replaceUrlPlaceholder("owner", owner);
        replaceUrlPlaceholder("repo_name", repoName);
        replaceUrlPlaceholder("issue_number", String.valueOf(issueNumber));
        request.header("Authorization", R.CONFIG.get("github_access_token"));
    }
}
