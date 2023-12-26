package com.board.rest.dto.response

data class HealthCheckResponse(
        var application: String,
        var profiles: List<String>,
        var health: String
)