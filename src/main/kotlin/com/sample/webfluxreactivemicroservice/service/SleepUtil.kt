package com.sample.webfluxreactivemicroservice.service

class SleepUtil {

    companion object {
        fun sleepSeconds(seconds: Int) {
            Thread.sleep(seconds.toLong() * 1000)
        }
    }
}