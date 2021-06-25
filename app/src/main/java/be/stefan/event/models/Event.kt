package be.stefan.event.models

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class Event {

    var id : Long? = null
    var title : String
    var category : Int
    var time : String
    var desc : String
    var address : String

    constructor(id: Long? = null, title: String, category: Int, time: String, desc: String, address: String) {
        if (id != null) { this.id = id }
        this.title = title
        this.category = category
        this.time = time
        this.desc = desc
        this.address = address
    }




}