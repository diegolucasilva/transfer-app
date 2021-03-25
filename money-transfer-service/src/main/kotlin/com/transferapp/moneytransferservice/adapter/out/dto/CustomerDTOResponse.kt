package com.transferapp.moneytransferservice.adapter.out.dto



data class CustomerDTOResponse(val id:String?,
                               val name:String,
                               val email:String,
                               val documentId: String,
                               val status: Int?,
                               val role: RoleType
){
    enum class RoleType{
        USER, SHOP_KEEPER
    }
}
