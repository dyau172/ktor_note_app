package com.androiddevs.routes

import com.androiddevs.data.checkIfUserExists
import com.androiddevs.data.collections.User
import com.androiddevs.data.registerUser
import com.androiddevs.data.requests.AccountRequest
import com.androiddevs.data.responses.SimpleResponse
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import javax.security.auth.callback.ConfirmationCallback.OK

fun Route.registerRoute(){
    route("/register"){
        post{
            val request = try{
                call.receive<AccountRequest>()
            }catch (e: ContentTransformationException){
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val userExists = checkIfUserExists(request.email)
            if(!userExists){
                if(registerUser(User(request.email, request.password))){
                    call.respond(HttpStatusCode.OK, SimpleResponse(true, "Successfully created account!"))
                }else{
                    call.respond(HttpStatusCode.OK, SimpleResponse(false, "An unknown error occured"))
                }
            }else {
                call.respond(HttpStatusCode.OK, SimpleResponse(false, "User with that emails already exists"))
            }
        }
    }
}