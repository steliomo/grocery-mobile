package mz.co.commandline.grocery.generics.dto

data class ErrorMessage(val statusCode: Int, val message: String, val developerMessage: String)