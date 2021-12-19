package com.qaprosoft.carina.demo.api.repositories;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.carina.core.foundation.utils.Configuration;
import com.qaprosoft.carina.core.foundation.utils.R;

public class PostRepositoryMethod extends AbstractApiMethodV2 {

    public PostRepositoryMethod() {
        super("api/repositories/_post/rq.json", "api/repositories/_post/rs.json", "api/repositories/_post/repository.properties");
        replaceUrlPlaceholder("base_url", Configuration.getEnvArg("api_url"));
        request.header("Authorization", R.CONFIG.get("github_access_token"));
    }
}
