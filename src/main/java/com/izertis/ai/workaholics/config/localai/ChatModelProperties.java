package com.izertis.ai.workaholics.config.localai;

import java.time.Duration;

public record ChatModelProperties (

    String baseUrl,

    String modelName,

    Double temperature,

    Duration timeout

){}
