package com.example.last.calendermemo.model

import java.time.chrono.ChronoLocalDateTime

class Memo (id: Int, title: String, memo: String, datetime: Long) : java.io.Serializable {

    val id = id;
    val title = title
    val memo = memo
    val datetime = datetime
}