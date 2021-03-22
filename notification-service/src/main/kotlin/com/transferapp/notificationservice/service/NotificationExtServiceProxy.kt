package com.transferapp.notificationservice.service

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody


@FeignClient(value = "notification-ext-service", url = "\${notificationExt.url}")
interface NotificationExtServiceProxy {

    @PostMapping(value = ["/b19f7b9f-9cbf-4fc6-ad22-dc30601aec04"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun sendNotification(@RequestBody message: String)

    @PostMapping(value = ["/b19f7b9f-9cbf-4fc6-ad22-dc30601aec04"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun healthcheck()
}