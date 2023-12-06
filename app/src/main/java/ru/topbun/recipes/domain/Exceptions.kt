package ru.topbun.recipes.domain

open class AppException: RuntimeException()

class ConnectException: AppException()
class NotFoundRecipesException: AppException()



