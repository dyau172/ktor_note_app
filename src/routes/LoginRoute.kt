package com.androiddevs.routes

import com.androiddevs.data.checkPasswordForEmail
import com.androiddevs.data.requests.AccountRequest
import com.androiddevs.data.responses.SimpleResponse
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.netty.handler.codec.http.HttpStatusClass
import sun.java2d.pipe.SpanShapeRenderer

fun Route.loginRoute(){
    route("/login"){
        post {
            val request = try{
                call.receive<AccountRequest>()
            }catch(e: ContentTransformationException){
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val isPasswordCorrect = checkPasswordForEmail(request.email, request.password)
            if(isPasswordCorrect){
                call.respond(OK, SimpleResponse(true, "You are now logged in"))
            }else{
                call.respond(OK, SimpleResponse(false, "The Email or Password is incorrect"))
            }
        }
    }
}