package com.example.firstapp

import com.google.gson.JsonObject

class ErrorResponse(
    var type:String,
    var title: String,
    var status: Number,
    var errors: JsonObject,
    var traceId: String
)

