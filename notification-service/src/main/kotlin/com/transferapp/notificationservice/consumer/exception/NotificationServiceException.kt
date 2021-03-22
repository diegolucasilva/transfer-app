package com.transferapp.notificationservice.consumer.exception

import java.lang.Exception


class NotificationServiceException(exception: Exception):
    RuntimeException(exception) {
}