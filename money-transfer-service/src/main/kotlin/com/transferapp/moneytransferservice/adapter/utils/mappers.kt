package com.transferapp.moneytransferservice.adapter.utils

import com.transferapp.moneytransferservice.adapter.out.TransferMoneyDTORequest
import com.transferapp.moneytransferservice.domain.entity.Transaction

fun Transaction.toTransferMoneyDTORequest(): TransferMoneyDTORequest {
    return TransferMoneyDTORequest(
        fromAccountId = fromAccountId,
        toAccountId= toAccountId,
        amount = amount
    )
}