package com.transferapp.moneytransferservice.domain.port.out.dto

import com.transferapp.moneytransferservice.adapter.out.TransferMoneyDTORequest

interface TransactionTransferMoneyServicePort {

    fun transactionTransferMoney(transferMoneyDTORequest: TransferMoneyDTORequest)

}